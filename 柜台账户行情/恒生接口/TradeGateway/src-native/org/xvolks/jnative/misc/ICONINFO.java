/*
 * ICONINFO.java
 *
 * Created on 14. März 2007, 12:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.misc;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.*;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.util.Gdi32;

/**
 *
 * @author Thubby
 */
public class ICONINFO extends AbstractBasicData<ICONINFO>
{
	
	/*
		BOOL fIcon;
		DWORD xHotspot;
		DWORD yHotspot;
		HBITMAP hbmMask;
		HBITMAP hbmColor;
	 */
	
	/** Creates a new instance of ICONINFO */
	public ICONINFO()
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
	
	public void dispose() throws NativeException, IllegalAccessException
	{
		Gdi32.DeleteObject(getBitmapMask());
		Gdi32.DeleteObject(getBitmapColor());
		getPointer().dispose();
	}
	
	public boolean isIcon() throws NativeException
	{
		return (pointer.getAsByte(0) != 0);
	}
	public int getBitmapMask() throws NativeException
	{
		return pointer.getAsInt(12);
	}
	public int getBitmapColor() throws NativeException
	{
		return pointer.getAsInt(16);
	}
	
	public Pointer createPointer() throws NativeException
	{
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
		return pointer;
	}
	
	public int getSizeOf()
	{
		return 20;
	}
	
	public ICONINFO getValueFromPointer()
	{
		return this;
	}
	
}
