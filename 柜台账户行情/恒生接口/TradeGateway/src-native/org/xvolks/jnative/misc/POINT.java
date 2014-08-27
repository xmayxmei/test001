package org.xvolks.jnative.misc;
import org.xvolks.jnative.exceptions.*;
import org.xvolks.jnative.misc.basicStructures.*;
import org.xvolks.jnative.pointers.*;
import org.xvolks.jnative.pointers.memory.*;

/**
 * $Id: POINT.java,v 1.9 2008/08/31 12:04:08 thubby Exp $
 *
 *<pre>
 * Structure C
 * typedef struct tagPOINT {
 * &nbsp;	LONG  x;
 * &nbsp;	LONG  y;
 * } POINT, *PPOINT, NEAR *NPPOINT, FAR *LPPOINT;
 *
 * </pre>
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class POINT extends AbstractBasicData<POINT>
{
	protected int x, y;
	
	public POINT()
	{
		this(0, 0);
	}
	public POINT(int x, int y)
	{
		super(null);
		try
		{
			createPointer();
		}
		catch (NativeException ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Method createPointer reserves a native MemoryBlock and copy its value in it
	 * @return   a Pointer on the reserved memory
	 * @exception   NativeException
	 */
	public Pointer createPointer() throws NativeException
	{
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(sizeOf()));
		return pointer;
	}
	
	/**
	 * Method getValueFromPointer
	 *
	 * @return   a T
	 *
	 * @exception   NativeException
	 *
	 */
	public POINT getValueFromPointer() throws NativeException
	{
		x = pointer.getAsInt(0);
		y = pointer.getAsInt(4);
		return this;
	}
	
	/**
	 * Returns X, you must call getValueFromPointer before reading this value !!!
	 *
	 * @return    an int
	 */
	public int getX()
	{
		return x;
	}
	/**
	 * Returns Y, you must call getValueFromPointer before reading this value !!!
	 *
	 * @return    an int
	 */
	public int getY()
	{
		return y;
	}
	
	public void setX(int x) throws NativeException
	{
		this.x = x;
		pointer.setIntAt(0, x);
	}
	public void setY(int y) throws NativeException
	{
		this.y = y;
		pointer.setIntAt(4, y);
	}
	
	/**
	 * Method getValue
	 *
	 * @return   a T
	 *
	 */
	@Override
	public POINT getValue()
	{
		try
		{
			pointer.setIntAt(0, x);
			pointer.setIntAt(4, y);
		}
		catch (NativeException ex)
		{
			ex.printStackTrace();
		}
		return this;
	}
	
	/**
	 * Method getSizeOf
	 * @return   the size of this data
	 */
	public int getSizeOf()
	{
		return sizeOf();
	}
	
	public static int sizeOf()
	{
		return 8;
	}
	
}
