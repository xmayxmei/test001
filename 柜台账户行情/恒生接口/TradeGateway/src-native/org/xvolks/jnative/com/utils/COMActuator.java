package org.xvolks.jnative.com.utils;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.com.interfaces.IUnknown;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.misc.basicStructures.LPARAM;
import org.xvolks.jnative.misc.basicStructures.UINT;
import org.xvolks.jnative.misc.basicStructures.WPARAM;
import org.xvolks.jnative.util.Kernel32;
import org.xvolks.jnative.util.User32;
import org.xvolks.jnative.util.constants.winuser.WM;

public class COMActuator {
	private static native int CreateMessagePumpWindow();
	private static native void InstallMainMessagePumpLoop(int hwnd);
	private static native void comNewInstance(IUnknown unknown,String progId, String iid);
	private static native void comDispose(IUnknown unknown);
	private static native void comDoMessagePump(IUnknown unknown);
	private static native void comExitMessagePump(IUnknown unknown);
	private static native Object comInvoke(IUnknown u, int dispIdMember, int wFlags, Object[] params);
	private static native Object syncExec(IUnknown u, Runnable r);

	public static Object invoke(IUnknown u, int dispIdMember, int wFlags, Object[] params) {
		return comInvoke(u, dispIdMember, wFlags, params);
	}
	
	public static Object syncExecute(IUnknown u, Runnable r) {
		return syncExec(u, r);
	}
	
	public static <T extends IUnknown> T initCOMObject(T unknown, String progId, String iid) {
		comNewInstance(unknown, progId, iid);
		return unknown;		
	}

	public static void dispose(IUnknown unknown) {
		comDispose(unknown);
	}
	public static void doMessagePump(IUnknown unknown) {
		comDoMessagePump(unknown);
	}
	public static void exitMessagePump(IUnknown unknown) {
		comExitMessagePump(unknown);
	}
	public static Thread installMainMessagePumpLoopInThread(final ByRef<HWND> phWnd) throws NativeException {
		phWnd.getValue().setValue(-1);
		Thread t = new Thread() {
			Integer id;
			@Override
			public long getId() {
				return id.longValue();
			}
			public void run() {
				try {
					id = Kernel32.GetCurrentThreadId().getValue();
					setName("MainMessagePumpThread-"+id);
				} catch (NativeException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				try {
					phWnd.getValue().setValue(createMessagePumpWindow().getValue());
				} catch (NativeException e) {
					e.printStackTrace();
					try {
						phWnd.getValue().setValue(0);
					} catch (NativeException e1) {
						e1.printStackTrace();
					}
				}
				installMainMessagePumpLoop(new HWND(0)/*phWnd.getValue()*/);
			}
		};
		t.setDaemon(true);
		t.start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					User32.SendMessage(phWnd.getValue(), new UINT(WM.WM_CLOSE.getValue()), new WPARAM(0), new LPARAM(0));
				} catch (NativeException e) {
					JNative.getLogger().log(SEVERITY.ERROR, e);
				} catch (IllegalAccessException e) {
					JNative.getLogger().log(SEVERITY.ERROR, e);
				}
			}
		});
		while (phWnd.getValue().getValue() == -1) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return t;
	}
	/**
	 * This method does not return
	 */
	public static void installMainMessagePumpLoop(HWND hwnd) {
		InstallMainMessagePumpLoop(hwnd.getValue());
	}
	public static HWND createMessagePumpWindow() {
		return new HWND(CreateMessagePumpWindow());
	}
}
