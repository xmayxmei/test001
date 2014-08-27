package org.xvolks.jnative.misc.basicStructures;

/**
 * $Id: LONG.java,v 1.6 2008/03/09 09:22:56 thubby Exp $
 *
 * <p>LONG is an implementation of the C LONG data, an int in Java</p>
 * <p>To get a LPLONG call createPointer() or after getPointer().</p>
 * <p>To retreive the value pointed by this object call getValueFromPointer()</p>
 * <br>
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
import org.xvolks.jnative.exceptions.*;
import org.xvolks.jnative.misc.machine.*;
import org.xvolks.jnative.pointers.*;
import org.xvolks.jnative.pointers.memory.*;

public class LONG extends AbstractBasicData<Integer>
{
	public LONG(int value)
	{
		super(value);
		try
		{
			createPointer();
		}
		catch (NativeException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * Method getSizeOf
	 * @return   the size of this data
	 */
	public int getSizeOf()
	{
		return sizeOf();
	}
	
	/**
	 * Method createPointer
	 *
	 * @return   a MemoryBlock
	 *
	 */
	public Pointer createPointer() throws NativeException
	{
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(sizeOf()));
		pointer.setIntAt(0, mValue);
		return pointer;
	}
	
	/**
	 * Method getValueFromPointer
	 *
	 * @return   a T
	 *
	 */
	public Integer getValueFromPointer() throws NativeException
	{
		mValue = pointer.getAsInt(0);
		return mValue;
	}
	
	public void setValue(int lValue) throws NativeException
	{
		mValue = lValue;
		pointer.setIntAt(0, mValue);
	}
	
	@Override
	public Integer getValue()
	{
		try
		{
			return getValueFromPointer();
		}
		catch (NativeException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Method sizeOf
	 * @return   the size of this structure
	 */
	public static int sizeOf()
	{
		return Machine.SIZE * 4;
	}
}
