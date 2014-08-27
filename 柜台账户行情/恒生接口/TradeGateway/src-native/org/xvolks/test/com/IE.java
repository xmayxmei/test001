/**
 * 
 */
package org.xvolks.test.com;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.com.interfaces.IDispatch;
import org.xvolks.jnative.com.typebrowser.business.COMIntrospector;
import org.xvolks.jnative.logging.JNativeLogger;

/**
 * @author Marc DENTY (mdt) - Jun 3, 2008
 * $Id: IE.java,v 1.4 2008/10/17 14:26:21 mdenty Exp $
 *
 */
public class IE extends IDispatch {

	/**
     * @throws Throwable
     */
    public IE() throws Throwable {
	    super("Shell.Explorer", IDispatch.IIDIDispatch.toString());
    }

	/* (non-Javadoc)
	 * @see org.xvolks.jnative.com.IDispatch#afterMessagePump()
	 */
	@Override
	protected void afterMessagePump() {
		System.out.println("afterMessagePump");
	}

	/* (non-Javadoc)
	 * @see org.xvolks.jnative.com.IDispatch#beforeMessagePump()
	 */
	@Override
	protected void beforeMessagePump() {
		System.out.println("beforeMessagePump");
	}

	/**
	 * @author Marc DENTY (mdt) - Jun 3, 2008
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
			System.setProperty(JNativeLogger.LOG_LEVEL, "4");
		JNative.setLoggingEnabled(true);

		System.err.println("SOP >>>>>>>>>>>>>>>>>>>>>>>>>>>");
		try {
			final IE ie = new IE();

			COMIntrospector.introspectIDispatch(ie);
			// lect.release();
		} finally {
			System.err.println("<<<<<<<<<<<<<<<<<<<<<<<<EOP");
		}
	}

}
