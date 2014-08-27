package org.xvolks.jnative.misc.registry;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 * $Id: RegKey.java,v 1.4 2008/12/24 10:22:53 thubby Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class RegKey extends TimedRegData
{

    private Pointer lpValueName;
    private LONG lpcValueName;

    public RegKey(int sizeValueName, int sizeData) throws NativeException
    {
        super(sizeData);
        if (sizeValueName < 4)
        {
            sizeValueName = 4;
        }
        lpcValueName = new LONG(sizeValueName);
        lpValueName = new Pointer(MemoryBlockFactory.createMemoryBlock(sizeValueName));
    }

    public LONG getLpcValueName()
    {
        return lpcValueName;
    }

    public Pointer getLpValueName()
    {
        return lpValueName;
    }

    @Override
    public String toString()
    {
        try
        {
            return super.toString() +
                    "lpValueName: " + lpValueName.getAsString() + "\n" +
                    "lpcValueName: " + lpcValueName.getValue() + "\n" +
                    "lastErrorCode: "+getErrorCode()+"\n";
        }
        catch (NativeException e)
        {
            e.printStackTrace();
            return e.toString();
        }
    }
}
