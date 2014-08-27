package org.xvolks.jnative.util;

import org.xvolks.jnative.exceptions.NativeException;

/**
 * $Id: Callback.java,v 1.7 2008/03/01 18:07:12 thubby Exp $
 * <p>
 * Callback.java
 * </p>
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public interface Callback
{
	/**
	 * Method callback
	 *
	 * @param    values              an long[]
	 *
	 * @return   an int
	 *
	 * @version  3/27/2006
	 */
	public int callback(long[] values);
	
	/**
	 * This method should call JNative.createCallback() AND MUST allow multiple calls
	 * <p>
	 * Something like :
	 * <pre>
	 * abstract class MyCallback implements Callback {
	 * 		private int myAddress = -1;
	 * 		public int getCallbackAddress() throws NativeException {
	 * 			if(myAddress == -1) {
	 *				myAddress = JNative.createCallback(numParam, this);
	 *			}
	 *			return myAddress;
	 * 		}
	 * }
	 * </pre>
	 * </p>
	 * @return the address of the callback function
	 */
	public int getCallbackAddress() throws NativeException;
}
