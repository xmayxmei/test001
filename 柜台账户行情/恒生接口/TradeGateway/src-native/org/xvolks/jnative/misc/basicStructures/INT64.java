package org.xvolks.jnative.misc.basicStructures;

/**
 * $Id: INT64.java,v 1.5 2008/03/09 09:22:56 thubby Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
import org.xvolks.jnative.exceptions.*;
import org.xvolks.jnative.misc.machine.*;
import org.xvolks.jnative.pointers.*;
import org.xvolks.jnative.pointers.memory.*;

public class INT64 extends AbstractBasicData<Long>
{
	public INT64(long value)
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
		pointer.setLongAt(0, mValue);
		return pointer;
	}
	
	/**
	 * Method getValueFromPointer
	 *
	 * @return   a T
	 *
	 */
	public Long getValueFromPointer() throws NativeException
	{
		mValue = pointer.getAsLong(0);
		return mValue;
	}
	
	
	
	/**
	 * Method sizeOf
	 * @return   the size of this structure
	 */
	public static int sizeOf()
	{
		return Machine.SIZE * 8;
	}
}
