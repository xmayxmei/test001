/*
 * LRECT.java
 *
 * Created on 10. Mai 2007, 11:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.misc.basicStructures;

import java.awt.geom.Rectangle2D;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.machine.Machine;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 *
 * @author Thubby
 */
public class LRECT extends AbstractBasicData<LRECT>
{
    
    /** Creates a new instance of LRECT */
    public LRECT()
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
    
    /** Creates a new instance of LRECT from a Java rectangle */
    public LRECT(Rectangle2D rectangle)
    {
        this();
        try
        {
            setTop((int)rectangle.getY());
            setLeft((int)rectangle.getX());
            setRight((int)(rectangle.getX() + rectangle.getWidth()));
            setBottom((int)(rectangle.getY() + rectangle.getHeight()));
        }
        catch (NativeException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
   
    public int getWidth() throws NativeException
    {
        return getRight() - getLeft();
    }
    public int getHeight() throws NativeException
    {
        return getBottom() - getTop();
    }
    public int getLeft() throws NativeException
    {
        return pointer.getAsInt(0);
    }
    public int getRight() throws NativeException
    {
        return pointer.getAsInt(8);
    }
    public int getTop() throws NativeException
    {
        return pointer.getAsInt(4);
    }
    public int getBottom() throws NativeException
    {
        return pointer.getAsInt(12);
    }
    
    public void setLeft(int value) throws NativeException
    {
        pointer.setIntAt(0, value);
    }
    public void setRight(int value) throws NativeException
    {
        pointer.setIntAt(8, value);
    }
    public void setTop(int value) throws NativeException
    {
        pointer.setIntAt(4, value);
    }
    public void setBottom(int value) throws NativeException
    {
        pointer.setIntAt(12, value);
    }
    
    public Pointer createPointer() throws NativeException
    {
        pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(sizeOf()));
        return pointer;
    }
    public LRECT getValueFromPointer() throws NativeException
    {
        return this;
    }
    
    public int getSizeOf()
    {
        return sizeOf();
    }
    public static int sizeOf()
    {
        return Machine.SIZE * 16;
    }
    
    
}
