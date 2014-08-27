/*
 * BITMAPINFOHEADER.java
 *
 * Created on 14. März 2007, 15:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.misc;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.*;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 *
 * @author Thubby
 */
public class BITMAPINFOHEADER extends AbstractBasicData<BITMAPINFOHEADER>
{
	
	/*
	typedef struct tagBITMAPINFOHEADER{
	  DWORD  biSize;
	  LONG   biWidth;
	  LONG   biHeight;
	  WORD   biPlanes;
	  WORD   biBitCount;
	  DWORD  biCompression;
	  DWORD  biSizeImage;
	  LONG   biXPelsPerMeter;
	  LONG   biYPelsPerMeter;
	  DWORD  biClrUsed;
	  DWORD  biClrImportant;
	} BITMAPINFOHEADER, *PBITMAPINFOHEADER;
	 */
	
	/** Creates a new instance of BITMAPINFOHEADER */
	public BITMAPINFOHEADER()
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
	public Pointer createPointer() throws NativeException
	{
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(sizeOf()));
		return pointer;
	}
	
	public int getSizeOf()
	{
		return sizeOf();
	}
	
	public BITMAPINFOHEADER getValueFromPointer()
	{
		return this;
	}
	public static int sizeOf()
	{
		return 40;
	}
	
}
