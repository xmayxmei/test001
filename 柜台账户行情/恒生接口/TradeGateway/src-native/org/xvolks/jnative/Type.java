
package org.xvolks.jnative;

/**
 * This enumeration manages the different types of data nedeed by JNative.<br>
 *
 *  $Id: Type.java,v 1.8 2008/03/01 18:07:13 thubby Exp $;
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */

public enum Type {
	/** Used only for return values*/
	VOID("Void", -1),
	/** Used for <b>in</b> params of type bool, byte to long (32 bits) */
	INT("Int", 0),
	/** Used for <b>in</b> params of type int64 (64 bits) */
	LONG("Long", 9),
	/** Used for <b>in</b> params of type double */
	DOUBLE("Double", 7),
	/** Used for <b>in</b> params of type float */
	FLOAT("FLOAT", 8),
	/** Used for <b>in</b> params of type const char*, LPCSTR */
	STRING("String", 4),
//	PSTRING("String*"),
//	PINT("Int*"),
//	PLONG("Long*"),
	/** Not use directly, use a Pointer object instead <br> This is used internaly for <b>out</b> parameters.*/
	PSTRUCT("Stuct*", 6);
	
	private String mValue;
	private int mNativeType;
	Type(String val, int nativeType) {
		mValue = val;
		mNativeType = nativeType;
	}
	
	public String getType() {
		return mValue;
	}

	public int getNativeType() {
		return mNativeType;
	}
}
