/*
 * SHITEMID.java
 *
 * Created on 13. März 2007, 16:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.misc;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.*;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 *
 * @author Thubby
 */
public class ITEMID extends AbstractBasicData<ITEMID>
{
	
	/*
	typedef struct _SHITEMID {
		USHORT cb;
		BYTE abID[1];
	} SHITEMID;
	 */
	private int size = 5;
	
	public ITEMID()
	{
		super(null);
		try
		{
			createPointer();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Pointer createPointer() throws NativeException
	{
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
		return pointer;
	}
	public int getSizeOf()
	{
		return size;
	}
	public Pointer setData(byte data[]) throws NativeException
	{
		if(data != null)
		{
			size = data.length;
			pointer.zeroMemory();
			createPointer();
			pointer.setMemory(data);
		}
		return pointer;
	}
	public ITEMID getValueFromPointer() throws NativeException
	{
		return this;
	}
	
	public String getSpecialPath() throws NativeException
	{
		//JNative.getLogger().log(pointer.getAsString());
		return ""+pointer.getAsInt(0);
	}
	
	public int getID() throws NativeException
	{
		return pointer.getAsByte(4);
	}
}
