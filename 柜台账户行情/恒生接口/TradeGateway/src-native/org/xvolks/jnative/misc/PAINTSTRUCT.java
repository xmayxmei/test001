/*
 * PAINTSTRUCT.java
 *
 * Created on 25. Juni 2008, 11:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.misc;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.basicStructures.DC;
import org.xvolks.jnative.misc.basicStructures.LRECT;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.util.Macros;

/**
 *
 *
 typedef struct tagPAINTSTRUCT {
 HDC  hdc;
 BOOL fErase;
 RECT rcPaint;
 BOOL fRestore;
 BOOL fIncUpdate;
 BYTE rgbReserved[32];
 } PAINTSTRUCT, *PPAINTSTRUCT;
 */
public class PAINTSTRUCT extends AbstractBasicData<PAINTSTRUCT>
{
	public DC hdc;
	public boolean fErase;
	public LRECT rcPaint;
	public boolean fRestore;
	public boolean fIncUpdate;
	public byte rgbReserved[] = new byte[32];
	
	/** Creates a new instance of PAINTSTRUCT */
	public PAINTSTRUCT()
	{
		super(null);
		try
		{
			createPointer();
		}
		catch (NativeException ex)
		{
			ex.printStackTrace();
		}
	}
	public PAINTSTRUCT getValueFromPointer() throws NativeException
	{
		offset = 0;
		hdc = new DC(getNextInt());
		fErase = getNextByte() == (byte)1 ? true : false;
		rcPaint = new LRECT();
		rcPaint.setLeft(getNextInt());
		rcPaint.setTop(getNextInt());
		rcPaint.setRight(getNextInt());
		rcPaint.setBottom(getNextInt());
		
		fRestore = getNextByte() == (byte)1 ? true : false;
		fIncUpdate = getNextByte() == (byte)1 ? true : false;
		
		if(rgbReserved != null)
		{
			for(int i = 0; i < rgbReserved.length; i++)
			{
				rgbReserved[i] = getNextByte();
			}
		}
		offset = 0;
		return this;
	}
	public Pointer createPointer() throws NativeException
	{
		if(pointer == null)
		{
			pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(sizeOf()));
		}
		return pointer;
	}
	/**
	 * Method getValue
	 *
	 * @return   a T
	 *
	 */
	@Override
	public PAINTSTRUCT getValue()
	{
		try
		{
			pointer.setIntAt(0, hdc != null ? hdc.getValue() : 0);
			pointer.setByteAt(4, fErase ? (byte)1 : (byte)0);
			pointer.setIntAt(5, rcPaint.getLeft());
			pointer.setIntAt(9, rcPaint.getTop());
			pointer.setIntAt(13, rcPaint.getRight());
			pointer.setIntAt(17, rcPaint.getBottom());
			pointer.setByteAt(21, fRestore ? (byte)1 : (byte)0);
			pointer.setByteAt(22, fIncUpdate ? (byte)1 : (byte)0);
			
			if(rgbReserved != null)
			{
				for(int i = 0; i < rgbReserved.length; i++)
				{
					pointer.setByteAt(23+i, rgbReserved[i]);
				}
			}
		}
		catch (NativeException ex)
		{
			ex.printStackTrace();
		}
		
		return this;
	}
	
	/**
	 * Method getSizeOf
	 * @return   the size of this data
	 */
	public int getSizeOf()
	{
		return sizeOf();
	}
	
	public static int sizeOf()
	{
		return 55;
	}
	
	
}
