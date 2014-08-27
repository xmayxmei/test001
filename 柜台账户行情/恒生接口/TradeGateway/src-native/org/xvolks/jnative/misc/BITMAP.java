/*
 * BITMAP.java
 *
 * Created on 15. März 2007, 14:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.misc;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.*;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.HeapMemoryBlock;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 *
 * @author Thubby
 */
public class BITMAP extends AbstractBasicData<BITMAP>
{
    
    /** Creates a new instance of BITMAP */
    public BITMAP()
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
    
    public Pointer createBitmapBuffer() throws NativeException
    {
        return new Pointer(new HeapMemoryBlock(getNeededBufferSize()));
    }
    
    public int getWidth() throws NativeException
    {
        return pointer.getAsInt(4);
    }
    public int getHeight() throws NativeException
    {
        return pointer.getAsInt(8);
    }
    public int getNeededBufferSize() throws NativeException
    {
        return getWidth()*getHeight()*4;
    }
    public int getRealBitmapSize() throws NativeException
    {
        return getWidth()*getHeight();
    }
    public Pointer createPointer() throws NativeException
    {
        pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
        return pointer;
    }
    
    public int getSizeOf()
    {
        return sizeOf();
    }
    public static int sizeOf()
    {
        return 24;
    }
    
    public BITMAP getValueFromPointer()
    {
        return this;
    }
    
}
