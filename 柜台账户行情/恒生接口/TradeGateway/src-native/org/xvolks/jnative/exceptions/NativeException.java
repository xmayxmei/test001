
package org.xvolks.jnative.exceptions;

/**
 *  NativeException : this exception is thrown if something goes wrong in the native side.
 *  $Id: NativeException.java,v 1.4 2006/06/09 20:44:05 mdenty Exp $;
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class NativeException extends Exception {
	private static final long serialVersionUID = -386641562909984650L;

	public NativeException(String message) {
		super(message);
	}
}
