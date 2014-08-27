package org.xvolks.jnative.misc.basicStructures;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.pointers.Pointer;

/**
 * $Id: BasicData.java,v 1.5 2006/06/09 20:44:05 mdenty Exp $
 *
 * <p>Instances of BasicData are specialized blocks of memory</p>
 * <p>They are values, memory, pointers to the memory</p>
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public interface BasicData<T> {
	
	/**
	 * Method getValue gets the value of this BasicData (often this)
	 *
	 * @return   a T
	 *
	 */
	public T getValue();
	/**
	 * Method getValueFromPointer gets the value of this data from the native memory block
	 *
	 * @return   a T
	 *
	 * @exception   NativeException
	 *
	 */
	public T getValueFromPointer() throws NativeException;
	/**
	 * Method getPointer gets the Pointer created by createPointer()
	 *
	 * @return   a Pointer the pointer or null if createPointer() was not called
	 *
	 */
	public Pointer getPointer();
	
	/**
	 * Method getSizeOf
	 *
	 * @return   the size of this data
	 *
	 */
	public int getSizeOf();
	
	/**
	 * Method createPointer reserves a native MemoryBlock and copy its value in it
	 *
	 * @return   a Pointer on the reserved memory
	 *
	 * @exception   NativeException
	 *
	 */
	public Pointer createPointer() throws NativeException;
}
