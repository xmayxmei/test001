package org.xvolks.jnative.pointers;

import org.xvolks.jnative.exceptions.NativeException;

/**
 *  $Id: NullPointer.java,v 1.8 2006/06/09 20:44:04 mdenty Exp $;
 *
 * <p>This class encapsulate a native NULL pointer.</p>
 *<br>
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
@SuppressWarnings("unused")
public class NullPointer extends Pointer {
	public static Pointer NULL = new NullPointer();
	/**
	 * Constructor creates a null Pointer with a size of allocated memory set to zero
	 *
	 */
	public NullPointer() {
		super(null);
	}
	
	/**
	 * Method dispose does nothing because there is no need to free some memory
	 *
	 */
	@Override
	public void dispose() {
	}
	
	/**
	 * Method getPointer
	 * @return   the address of this pointer
	 */
	@Override
	public int getPointer() {
		return 0;
	}
	
	
	
	/**
	 * Method getSize
	 * @return   the size of the memory block addressed by this pointer
	 */
	@Override
	public int getSize() {
		return 4;
	}
	
	
	
	
	/**
	 * Method getAsByte
	 *
	 * @param    offset              not used
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	@Override
	public byte getAsByte(int offset) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method getAsInt
	 *
	 * @param    offset              not used
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	@Override
	public int getAsInt(int offset) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method getAsLong
	 *
	 * @param    offset              not used
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	@Override
	public long getAsLong(int offset) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method getAsShort
	 *
	 * @param    offset              not used
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	@Override
	public short getAsShort(int offset) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method getAsString
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	@Override
	public String getAsString() throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method getMemory
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	@Override
	public byte[] getMemory() throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setByteAt
	 *
	 * @param    offset              not used
	 * @param    value               not used
	 *
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	@Override
	public int setByteAt(int offset, byte value) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setIntAt
	 *
	 *
	 * @param    offset              not used
	 * @param    value               not used
	 *
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 *
	 */
	@Override
	public int setIntAt(int offset, int value) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setLongAt
	 *
	 * @param    offset              not used
	 * @param    value               not used
	 *
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	public int setLongAt(int offset, int value) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setMemory
	 *
	 *
	 * @param    buffer              not used
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	@Override
	public void setMemory(String buffer) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setMemory
	 *
	 * @param    buffer              not used
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	@Override
	public void setMemory(byte[] buffer) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setShortAt
	 *
	 *
	 * @param    offset              not used
	 * @param    value               not used
	 *
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	@Override
	public int setShortAt(int offset, short value) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method setStringAt
	 *
	 *
	 * @param    offset              not used
	 * @param    value               not used
	 *
	 *
	 * @return   nothing
	 *
	 * @exception   NativeException
	 * @exception   NullPointerException allways
	 *
	 */
	@Override
	public int setStringAt(int offset, String value) throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	/**
	 * Method zeroMemory
	 *
	 *
	 * @exception   NullPointerException allways
	 *
	 */
	@Override
	public void zeroMemory() throws NativeException {
		throw new NullPointerException("This pointer is null");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
