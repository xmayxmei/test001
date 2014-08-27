/*
 * PRINTPAGERANGE.java
 *
 * Created on 18. Mai 2007, 14:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.misc;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.basicStructures.DWORD;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 *
 * @author Thubby
 */
public class PRINTPAGERANGE extends AbstractBasicData<PRINTPAGERANGE>
{
	public DWORD nFromPage;
	public DWORD nToPage;
	
	/** Creates a new instance of PRINTPAGERANGE */
	public PRINTPAGERANGE()
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
	}
	public Pointer createPointer() throws NativeException
	{
		if(pointer == null)
		{
			pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
		}
		return pointer;
	}
	private void fromPointer() throws NativeException
	{
		offset = 0;
		nFromPage = new DWORD(getNextInt());
		nToPage = new DWORD(getNextInt());
		offset = 0;
	}
	private void toPointer() throws NativeException
	{
		offset = 0;
		offset += pointer.setIntAt(offset, nFromPage.getValue());
		pointer.setIntAt(offset, nToPage.getValue());
		offset = 0;
	}
	public int getSizeOf()
	{
		return sizeOf();
	}
	public static int sizeOf()
	{
		return DWORD.sizeOf()*2;
	}
	
	public PRINTPAGERANGE getValueFromPointer() throws NativeException
	{
		fromPointer();
		return this;
	}
	public PRINTPAGERANGE getValue()
	{
		try
		{
			toPointer();
		}
		catch (NativeException ex)
		{
			ex.printStackTrace();
		}
		return this;
	}
	
}
