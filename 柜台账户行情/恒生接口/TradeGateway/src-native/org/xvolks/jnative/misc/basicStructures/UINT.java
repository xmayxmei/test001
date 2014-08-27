package org.xvolks.jnative.misc.basicStructures;

/**
 * $Id: UINT.java,v 1.6 2008/12/24 14:50:41 thubby Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
import org.xvolks.jnative.exceptions.*;
import org.xvolks.jnative.misc.machine.*;
import org.xvolks.jnative.pointers.*;
import org.xvolks.jnative.pointers.memory.*;

public class UINT extends AbstractBasicData<Integer>
{

    public UINT(int value)
    {
        super(value);
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

    public UINT(short value)
    {
        this((int)value);
    }

    /**
     * Method getSizeOf
     * @return   the size of this data
     */
    public int getSizeOf()
    {
        return sizeOf();
    }

    /**
     * Method createPointer
     *
     * @return   a Pointer
     *
     * @exception   NativeException
     *
     */
    public Pointer createPointer() throws NativeException
    {
        pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(sizeOf()));
        pointer.setIntAt(0, mValue);
        return pointer;
    }

    /**
     * Method getValueFromPointer
     *
     * @return   a T
     *
     */
    public Integer getValueFromPointer() throws NativeException
    {
        mValue = new Integer(pointer.getAsInt(0));
        return mValue;
    }

    public void setValue(Integer lValue) throws NativeException
    {
        mValue = lValue;
        pointer.setIntAt(0, mValue);
    }

    @Override
    public Integer getValue()
    {
        try
        {
            return getValueFromPointer();
        }
        catch (NativeException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Short getValueAsShort()
    {
        try
        {
            return getValueFromPointer().shortValue();
        }
        catch (NativeException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static int sizeOf()
    {
        return Machine.SIZE * 4;
    }
}
