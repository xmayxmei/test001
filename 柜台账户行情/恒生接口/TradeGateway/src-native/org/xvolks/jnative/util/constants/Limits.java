package org.xvolks.jnative.util.constants;

public enum Limits
{
	UINT_MAX(0xffffffff);
	private int mValue;
	public int getValue()
	{
		return mValue;
	}
	private Limits(int lValue)
	{
		mValue = lValue;
	}
	
	
}
