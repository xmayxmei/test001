/*
 * RegTypes.java
 *
 * Created on 27. Februar 2007, 16:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.misc.registry;

/**
 *
 * @author Thubby
 */
public class REGTYPE
{
	
	public static final REGTYPE REG_NONE = new REGTYPE(0,0);
	public static final REGTYPE REG_SZ = new REGTYPE(1,2048);
	public static final REGTYPE REG_EXPAND_SZ = new REGTYPE(2,2048);
	public static final REGTYPE REG_BINARY = new REGTYPE(3,1);
	public static final REGTYPE REG_DWORD = new REGTYPE(4,4);
	public static final REGTYPE REG_MULTI_SZ = new REGTYPE(7,2048);
	
	private int mValue;
	private int mSize;
	
	
	/** Creates a new instance of RegTypes */
	private REGTYPE(int type, int size)
	{
		mValue = type;
		mSize = size;
	}
	
	public int getValue()
	{
		return mValue;
	}
	public int getSize()
	{
		return mSize;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null) return false;
		if (obj instanceof REGTYPE)
		{
			return mValue == ((REGTYPE)obj).mValue;
		}
		else
		{
			return false;
		}
	}
}

