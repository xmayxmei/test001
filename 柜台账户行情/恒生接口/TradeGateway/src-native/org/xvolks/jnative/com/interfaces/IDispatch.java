package org.xvolks.jnative.com.interfaces;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.com.utils.COMActuator;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.util.constants.WinError;
import org.xvolks.jnative.util.constants.COM.DISPATCH_TYPE;
import org.xvolks.jnative.util.ole.Ole32;
import org.xvolks.jnative.util.windows.structures.GUID;


public class IDispatch extends IUnknown implements Runnable {		
	public static final GUID IIDIDispatch = new GUID("{00020400-0000-0000-c000-000000000046}");
	
	
	private Thread dispatchThread;
	

	private Object initedLock = new Object();
	private boolean disposed = false;
	private boolean messagePumpingStarted = false;
	private Object loopMutex = new Object();
	private Throwable initThrowable = null;

	private final String clsid;
	private final String iid;
	private String progId;




//	private final static int DISPATCH_METHOD = 0x1;
//	private final static int DISPATCH_PROPERTYGET = 0x2;
//	private final static int DISPATCH_PROPERTYPUT = 0x4;
	//private final static int DISPATCH_PROPERTYPUTREF = 0x8;
	
	private native long GetIDsOfNames(String name, int cNames) throws IllegalArgumentException;
	private native long GetTypeInfo(int iTInfo, int lcid) throws NativeException, IllegalArgumentException;

	public int getIDsOfNames(String name, int cNames) {
		int id = (int)GetIDsOfNames(name, cNames);
		return id;
	}
	public int getTypeInfo(int iTInfo, int lcid) throws IllegalArgumentException, NativeException {
		return (int)GetTypeInfo(iTInfo, lcid);
	}
		
	protected IDispatch(GUID iid, IUnknown parent, int iidAddress) throws Throwable {
		super(iidAddress);
		progId = null;
		clsid = null;
		this.iid = iid.toString();
		dispatchThread = new Thread(this);
		dispatchThread.setDaemon(true);
	}
	
	public IDispatch(GUID clsId, GUID iid) throws Throwable {
		this(clsId, iid.toString());
	}
	public IDispatch(GUID clsId, GUID iid, Thread dispatchThread, HWND hWnd) throws Throwable {
		this(clsId, iid.toString(), dispatchThread, hWnd);		
	}
	public IDispatch(GUID clsId, String iid) throws Throwable {
		this(clsId, iid, null, null);
	}
	public IDispatch(final GUID clsId, final String iid, final Thread dispatchThread, final HWND hWnd) throws Throwable {
		this.iid = iid;
		this.clsid = clsId.toString();
		if(dispatchThread == null) {
			this.dispatchThread = new Thread(this);
			this.dispatchThread.setDaemon(true);
			synchronized (initedLock) {
				try {
					getLogger().debug("Starting IDispatch thread");
					this.dispatchThread.setName("IDispatchThread-"+this.dispatchThread.getId());
					this.dispatchThread.start();
					initedLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (initThrowable != null) {
				throw initThrowable;
			}
		} else {
			setHWnd(hWnd.getValue());
			JNative.getLogger().log("About to syncExec to create Instance using window "+hWnd.getValue());
			long id = dispatchThread.getId();
			IDispatch.this.setOwnerThreadId((int)id);
			COMActuator.syncExecute(this, new Runnable() {
				public void run() {
					IDispatch.this.dispatchThread = Thread.currentThread();
					getLogger().log(dispatchThread.getName());
					try {
						if(WinError.SUCCEEDED(Ole32.CoInitialize())) {
							Pointer pointer = Ole32.CoCreateInstance(clsId, null, Ole32.CLSCTX_ALL, new GUID(iid));
							setPIUnknown(pointer.getAsInt(0));
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NativeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}

		getLogger().debug("testing SyncExec");
		// on s'assure que le syncExec fonctionne correctement (la boucle de message est OK)
		COMActuator.syncExecute(this, new Runnable() {
			public void run() {
				getLogger().debug("Java code executed in syncExec");
			}
		});
		getLogger().debug("syncExec is working great.");
	}
	public IDispatch(String progId) throws Throwable {
		this(progId, IIDIDispatch.toString());
	}
	public IDispatch(String progId, GUID iid, Thread dispatchThread, HWND hwnd) throws Throwable {
		this(getClassID(progId), iid.toString(), dispatchThread, hwnd);
		this.progId = progId;
	}
	public IDispatch(String progId, GUID iid) throws Throwable {
		this(progId, iid.toString());
	}
	public IDispatch(String progId, String iid) throws Throwable {
		this(getClassID(progId), iid, null, null);
		this.progId = progId;
	}
	
	public String getIid() {
		return iid;
	}
	public String getProgId() {
		return progId;
	}

	protected static GUID getClassID(String clientName) throws NativeException, IllegalAccessException {
		// create a GUID struct to hold the result
		GUID guid = Ole32.CLSIDFromProgID(clientName);
		if (guid == null) {
			guid = Ole32.CLSIDFromString(clientName);
			if (guid == null) {
				throw new IllegalArgumentException("Illegal progId : " + clientName);
			}
		}
		return guid;
	}	
	
	
	public Object getProperty(int dispIdMember) {
		return invoke(dispIdMember, DISPATCH_TYPE.DISPATCH_PROPERTYGET, null);
	}
	
	public void setProperty(int dispIdMember, Object value) {
		invoke(dispIdMember, DISPATCH_TYPE.DISPATCH_PROPERTYPUT, new Object[] {value});
	}
	
	public Object invoke(int dispIdMember, Object[] params) {
		return invoke(dispIdMember, DISPATCH_TYPE.DISPATCH_METHOD, params);
	}

	public Object invoke(int dispIdMember, DISPATCH_TYPE wFlags, Object[] params) {
		return COMActuator.invoke(this, dispIdMember, wFlags.getValue(), params);
	}
	public Object syncExec(Runnable r) {
		return COMActuator.syncExecute(this, r);
	}
	
	public void run() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				COMActuator.dispose(IDispatch.this);
			}
		});
		if (clsid == null || iid ==null) { 
			initThrowable = new NullPointerException("clsid NOR iid cannot be null !!!");
		} else {
			try {
				getLogger().debug(String.format("Calling comNewInstance(%s, %s)", clsid, iid));
				COMActuator.initCOMObject(this, clsid, iid);
				addRef();
				getLogger().debug("comNewInstance() succeeded");
			} catch (Throwable t) {
				getLogger().debug("comNewInstance() failed " + t.toString());
				initThrowable = t;
			}
	
		}
		synchronized (initedLock) {
			initedLock.notify();
		}
		
		if (initThrowable == null) {
			doMessagePump();
		}
	}
	
	protected void beforeMessagePump() {};
	protected void afterMessagePump() {};
	/**
	 * this method does not return. It starts a loop on the native side.
	 *
	 */
	protected void doMessagePump() {
		if (clsid == null || iid ==null) return;
		
		boolean doLoop = false;
		synchronized (loopMutex) {
			if (!messagePumpingStarted) {
				messagePumpingStarted = true;
				doLoop = true;
			}
		}

		if (doLoop) {
			try {
				beforeMessagePump();
			} catch (Throwable t) {
				getLogger().error("Error in beforeMessagePump(). Exiting IDispatch Thread.", t);
				return;
			}
			try {
				COMActuator.doMessagePump(this);	// ne sort pas.
			} catch (Throwable t) {
				getLogger().error("Error in message pumping native loop", t);
			} finally {
				try {
					afterMessagePump();
				} catch (Throwable t) {
					getLogger().warn("Error in afterMessagePump().", t);
				}
			}
		}
	}
	
	protected synchronized void exitMessagePump() {
		if (progId == null || iid ==null) return;
		
		if (messagePumpingStarted) {
			COMActuator.exitMessagePump(this);
			messagePumpingStarted = false;
		}
	}
	
	public synchronized void dispose() {
		if (progId == null || iid ==null) return;
		
		if (!disposed) {
			COMActuator.dispose(this);
			exitMessagePump();
			try {
				release();
			} catch (Exception e) {
				e.printStackTrace();
			}
			disposed = true;
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}
	
}
