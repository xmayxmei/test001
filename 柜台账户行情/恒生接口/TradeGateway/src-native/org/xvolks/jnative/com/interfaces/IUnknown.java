package org.xvolks.jnative.com.interfaces;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Native;
import org.xvolks.jnative.com.utils.Logger;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.NativeMemoryBlock;
import org.xvolks.jnative.util.StructConverter;
import org.xvolks.jnative.util.windows.structures.GUID;

public class IUnknown {
	public static final GUID IIDIUnknown = new GUID("{00000000-0000-0000-C000-000000000046}");
	
	@Native
	private int address;
	@Native
	private int hWnd;
	@Native
	private int ownerThreadId;

	private Logger logger;


	protected IUnknown() {
		this.logger = Logger.getInstance(this.getClass().getName());
	}
	public IUnknown(int address) {
		this();
		this.address = address;
	}

	protected IUnknown(int hWnd, int ownerThreadId) throws Throwable {
		this(0);
		this.hWnd = hWnd;
		this.ownerThreadId = ownerThreadId;
	}
	
	public Logger getLogger() {
		return logger;
	}

	public int getPIUnknown() {
		return address;
	}
	@Native
	protected void setPIUnknown(int dispatch) {
		address = dispatch;
	}
	@Native
	protected void setHWnd(int wnd) {
		hWnd = wnd;
	}
	protected int getHWnd() {
		return hWnd;
	}
	protected int getOwnerThreadId() {
		return ownerThreadId;
	}
	@Native
	protected void setOwnerThreadId(int ownerThreadId) {
		this.ownerThreadId = ownerThreadId;
	}

	
	
	public long addRef() throws NativeException {
		return AddRef();
	}
	public long release() throws NativeException {
		return Release();
	}
	
	/**
	 * Returns a pointer to a specified interface on an object to which a client currently holds an interface pointer.
	 * 
	 * @param refId the REFIID of the interface
	 * @return null if this object does not implements that interface
	 * @throws Throwable 
	 */
	public LONG queryInteface(GUID refId) throws Throwable {
		LONG vObject = new LONG(0);
		if(QueryInterface(refId, vObject.getPointer().getPointer())) {
			return vObject;
		} else {
			return null;
		}
	}

	private Pointer vtbl = null; 
	public int getVtblPointer(int vtblIndex) throws NativeException {
		int offset = (vtblIndex + 1) << 2;
		if(vtbl == null || vtbl.getSize() < offset) {
			int tabAddress = StructConverter.bytesIntoInt(JNative.getMemory(getPIUnknown(), 4), 0);
			System.err.println("TabAddress = " + tabAddress);
			vtbl = new Pointer(new NativeMemoryBlock(tabAddress, offset));
		}
		return vtbl.getAsInt(offset - 4);
	}
	


	
	private native long AddRef() throws NativeException;
	private native long Release() throws NativeException;
	private native boolean QueryInterface(GUID refId, int ppvObject) throws NativeException;
}
