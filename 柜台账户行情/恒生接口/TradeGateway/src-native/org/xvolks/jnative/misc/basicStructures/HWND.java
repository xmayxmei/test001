
package org.xvolks.jnative.misc.basicStructures;
import org.xvolks.jnative.exceptions.*;
import org.xvolks.jnative.misc.machine.*;
import org.xvolks.jnative.pointers.*;
import org.xvolks.jnative.pointers.memory.*;

/**
 * $Id: HWND.java,v 1.7 2008/10/23 14:28:53 mdenty Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class HWND extends AbstractBasicData<Integer> {
	public HWND(int value) {
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
	 * Method createPointer
	 *
	 * @return   a MemoryBlock
	 *
	 */
	public Pointer createPointer() throws NativeException {
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
	public Integer getValueFromPointer() throws NativeException {
		mValue = pointer.getAsInt(0);
		return mValue;
	}
	
	
	
	/**
	 * Method getSizeOf
	 * @return   the size of this data
	 */
	public int getSizeOf() {
		return sizeOf();
	}
	
	public void setValue(int value) throws NativeException {
		pointer.setIntAt(0, value);
		mValue = value;
	}
	/**
	 * Method sizeOf
	 * @return   the size of this structure
	 */
	public static int sizeOf() {
		return Machine.SIZE * 4;
	}
}
