package org.xvolks.jnative;

/**
 * Represents the call convention of the function (STDCALL, CDECL)
 * $Id$
 *
 * This software is released under the LGPL.
 *
 * @author Created by Marc DENTY - (c) 2006 JNative project
 *
 */
public enum Convention
{
	/**
	 * The callee cleans the stack
	 */
	STDCALL(0),
	/**
	 * The caller cleans the stack
	 */
	CDECL(1),
	
	;
	private final int value;
	
	static void setDefaultStyle(Convention convention)
	{
		DEFAULT = convention;
	}
	
	public static Convention DEFAULT = Convention.STDCALL;
	
	private Convention(int value)
	{
		this.value = value;
	}
	
	public int getValue()
	{
		return value;
	}
	
	/**
	 *
	 * @param v
	 * @return a valid enum value (by default STDCALL)
	 */
	public static Convention fromInt(int v)
	{
		switch (v)
		{
			case 0:
				return STDCALL;
			case 1:
				return CDECL;
				
			default:
				return STDCALL;
		}
	}
}
