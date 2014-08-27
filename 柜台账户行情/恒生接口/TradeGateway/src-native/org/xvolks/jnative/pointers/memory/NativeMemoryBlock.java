package org.xvolks.jnative.pointers.memory;

/**
 * $Id: NativeMemoryBlock.java,v 1.5 2008/08/31 15:28:21 thubby Exp $
 *
 * <p>This class represents a memory block known by its pointer</p>
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
import org.xvolks.jnative.exceptions.NativeException;

public class NativeMemoryBlock extends AbstractMemoryBlock
{
	
	public NativeMemoryBlock(int address, int size)
	{
		super(size);
		setPointer(address);
	}
	
	/**
	 * Method reserveMemory allocate a block of native memory
	 * @param    size                in bytes of the block
	 * @return   the address of the reserved memory
	 * @exception   NativeException
	 */
	public int reserveMemory(@SuppressWarnings("unused") int size)
	{
		return getPointer();
	}
	
	/**
	 * Method dispose provides a way to free the memory
	 * <p>This implementation does nothing as we do not know how this memory block was allocated</p>
	 * @exception   NativeException
	 */
	public void dispose()
	{
	}
	
}
