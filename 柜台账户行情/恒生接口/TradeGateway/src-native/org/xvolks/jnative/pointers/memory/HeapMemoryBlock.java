package org.xvolks.jnative.pointers.memory;

/**
 * $Id: HeapMemoryBlock.java,v 1.6 2008/08/31 15:28:21 thubby Exp $
 *
 * <p><b>Win32 : </b>HeapMemoryBlock is a block of memory reserved from the heap
 * with the function : HeapAlloc (see MSDN)
 * <br> This allocation is the fastest du to its implementation. Seems to hang with some DLL.
 * </p>
 * <p><b>Linux : </b>Not implemented yet</p>
 * <br>
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
import org.xvolks.jnative.exceptions.*;
import org.xvolks.jnative.*;

public class HeapMemoryBlock extends AbstractMemoryBlock
{
	public HeapMemoryBlock(int size) throws NativeException
	{
		super(size);
		reserveMemory(size);
	}
	
	/**
	 * Method reserveMemory allocate a block of native memory
	 * @param    size                in bytes of the block
	 * @return   the address of the reserved memory
	 * @exception   NativeException
	 */
	public int reserveMemory(int size) throws NativeException
	{
		setSize(size);
		if (getPointer() != null)
		{
			dispose();
		}
		setPointer(JNative.allocMemory(size));
		return getPointer();
	}
	
	/**
	 * Method dispose provides a way to free the memory
	 * @exception   NativeException
	 */
	public void dispose() throws NativeException
	{
		if (getPointer() != null)
		{
			JNative.freeMemory(getPointer());
			setPointer(null);
		}
	}
	
}
