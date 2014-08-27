/*
 * TOKEN_PRIVILEGES.java
 *
 * Created on 28. Februar 2007, 14:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.misc;

import org.xvolks.jnative.misc.basicStructures.*;
import org.xvolks.jnative.pointers.*;
import org.xvolks.jnative.exceptions.*;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 *
 * @author Thubby
 */
public class TOKEN_PRIVILEGES extends AbstractBasicData<TOKEN_PRIVILEGES>
{
	
	public static final int TOKEN_ADJUST_PRIVILEGES = 0x00000020;
	public static final int TOKEN_QUERY = 0x00000008;
	public static final int SE_PRIVILEGE_ENABLED = 0x00000002;
	
	public int PrivilegeCount;
	public int ignoredLuid = 0;
	public int TheLuid;
	public int Attributes;
	
	public TOKEN_PRIVILEGES()
	{
		super(null);
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
	public TOKEN_PRIVILEGES getValueFromPointer() throws NativeException
	{
		return this;
	}
	public Pointer createPointer() throws NativeException
	{
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
		return pointer;
	}
	public int getSizeOf()
	{
		return (4+4+4+4);
	}
	public Pointer getPointer()
	{
		try
		{
			pointer.setIntAt(0,PrivilegeCount);
			pointer.setIntAt(4,TheLuid);
			pointer.setIntAt(8,ignoredLuid);
			pointer.setIntAt(12,Attributes);
		}
		catch(NativeException e)
		{
			e.printStackTrace();
		}
		return pointer;
	}
}
