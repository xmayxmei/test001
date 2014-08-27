
package org.xvolks.jnative.util;

/**
 * WindowProc this interface must be implemented to receive events from native window.<br>
 *
 *  $Id: WindowProc.java,v 1.4 2006/06/09 20:44:05 mdenty Exp $;
 * 
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public interface WindowProc {
	/**
	 * Method windowProc recieve events from native window
	 *
	 * @param    hwnd                an int [in] Handle to the window.
	 * @param    uMsg                an int [in] Specifies the message.
	 * @param    wParam              an int [in] Specifies additional message information. The contents of this parameter depend on the value of the uMsg parameter.
	 * @param    lParam              an int Specifies additional message information. The contents of this parameter depend on the value of the uMsg parameter.
	 *
	 * @return   an int The return value is the result of the message processing and depends on the message sent.
	 *
	 */
	public int windowProc(int hwnd, int uMsg, int wParam, int lParam);
}
