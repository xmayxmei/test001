package org.xvolks.test.com;

import javax.swing.JFrame;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.com.interfaces.IDispatch;
import org.xvolks.jnative.com.typebrowser.business.COMIntrospector;
import org.xvolks.jnative.com.utils.ByRef;
import org.xvolks.jnative.com.utils.COMActuator;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger;
import org.xvolks.jnative.misc.basicStructures.HWND;

public class XVipsLect extends IDispatch {
	public static String PROG_ID = "ACTIVIPS.XVipsLect";
//	public static String PROG_ID = "shell.explorer";
//	public static String PROG_ID = "DPOCX.DPOcxCtrl.1";

	public XVipsLect(Thread t, HWND hwnd) throws Throwable {
		super(PROG_ID, IIDIDispatch, t, hwnd);
//		System.err.println("REF = " + addRef());
	}

	@Override
	protected void afterMessagePump() {
		System.err.println("afterMessagePump");
	}

	@Override
	protected void beforeMessagePump() {
		System.err.println("beforeMessagePump");
	}
	
	public static void main(String[] args) throws Throwable {
		System.setProperty(JNativeLogger.LOG_LEVEL, "5");
		JNative.setLoggingEnabled(true);

		HWND hwnd = new HWND(0);
		ByRef<HWND> pHWND = new ByRef<HWND>(hwnd);
		Thread t = null;
//		Ole32.CoInitialize();
		t = COMActuator.installMainMessagePumpLoopInThread(pHWND);
		JFrame f = null;
		f = new JFrame();
		f.setVisible(true);
		System.err.println("SOP >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try {
			final XVipsLect lect = new XVipsLect(t, hwnd);
			lect.syncExec(new Runnable() {
				public void run() {
					try {
						COMIntrospector.introspectIDispatch(lect);
					} catch (NativeException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			});
//			lect.release();
		} finally {
			System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<EOP");
			f.setVisible(false);
		}
	}

}
