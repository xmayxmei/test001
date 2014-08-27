package org.xvolks.jnative.misc.basicStructures;

/**
 * $Id$
 *
 * <p>DOUBLE is an implementation of the C DOUBLE data,</p>
 * <p>To get a LPDOUBLE call createPointer() or after getPointer().</p>
 * <p>To retreive the value pointed by this object call getValueFromPointer()</p>
 * <br>
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
import org.xvolks.jnative.exceptions.*;
import org.xvolks.jnative.misc.machine.*;
import org.xvolks.jnative.pointers.*;
import org.xvolks.jnative.pointers.memory.*;

public class DOUBLE extends AbstractBasicData<Double> {

	public DOUBLE(double value) {
		super(value);
		try {
			createPointer();
		} catch (NativeException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * Method getSizeOf
	 * @return   the size of this data
	 */
	public int getSizeOf() {
		return sizeOf();
	}
	
	/**
	 * Method createPointer
	 *
	 * @return   a MemoryBlock
	 *
	 */
	public Pointer createPointer() throws NativeException {
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(sizeOf()));
		pointer.setDoubleAt(0, mValue);
		return pointer;
	}
	
	/**
	 * Method getValueFromPointer
	 *
	 * @return   a T
	 *
	 */
	public Double getValueFromPointer() throws NativeException {
		mValue = pointer.getAsDouble(0);
		return mValue;
	}
	
	public void setValue(double lValue) throws NativeException {
		mValue = lValue;
		pointer.setDoubleAt(0, mValue);
	}
	
	@Override
	public Double getValue() {
		try {
			return getValueFromPointer();
		} catch (NativeException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Method sizeOf
	 * @return   the size of this structure
	 */
	public static int sizeOf() {
		return Machine.SIZE * 8;
	}
}
