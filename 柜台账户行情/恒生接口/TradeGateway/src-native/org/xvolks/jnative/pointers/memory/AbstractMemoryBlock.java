package org.xvolks.jnative.pointers.memory;

/**
 * $Id: AbstractMemoryBlock.java,v 1.3 2006/06/09 20:44:04 mdenty Exp $
 *
 * <p>New Memory blocks should extends this class</p>
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public abstract class AbstractMemoryBlock implements MemoryBlock {
	
	private Integer pointer;
	private int size;
	
	AbstractMemoryBlock(int size) {
		this.size = size;
	}
	
	
	/**
	 * Method getSize
	 * @return   the size of this memory block
	 * @exception   NullPointerException if the pointer is null
	 */
	public final int getSize() throws NullPointerException {
		return size;
	}
	
	
	/**
	 * Method getPointer
	 * @return   the pointer that addresses the memory block
	 */
	public final Integer getPointer() throws NullPointerException {
		return pointer;
	}
	
	protected void setPointer(Integer pointer) {
		if(pointer == null)
			size = 0;
		this.pointer = pointer;
	}
	
	protected void setSize(int size) {
		this.size = size;
	}
	
}
