/*
 * MapiRecipDesc.java
 *
 * Created on 26. Juni 2008, 16:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.util.mapi.structs;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 *
typedef struct {
     ULONG ulReserved
     ULONG ulRecipClass;
     LPTSTR lpszName;
     LPTSTR lpszAddress;
     ULONG ulEIDSize;
     LPVOID lpEntryID;
} MapiRecipDesc, FAR *lpMapiRecipDesc;
 
 */
public class MapiRecipDesc extends AbstractBasicData<MapiRecipDesc>
{
    public LONG ulReserved = new LONG(0);
    public LONG ulRecipClass = new LONG(0);
    public int lpszName;
    public int lpszAddress;
    public LONG ulEIDSize = new LONG(0);
    public int lpEntryID;
       
    /** Creates a new instance of MapiRecipDesc */
    public MapiRecipDesc() throws NativeException
    {
        super(null);
        createPointer();
    }
    public MapiRecipDesc(int nativeAddress) throws NativeException
    {
        this();
        pointer.setMemory(JNative.getMemory(nativeAddress, sizeOf()));
        getValueFromPointer();
    }
    /**
     * Method getValue
     *
     * @return   a T
     *
     */
    @Override
    public MapiRecipDesc getValue()
    {
        return this;
    }
    
    // set the struct-data with values from the pointer memory
    public MapiRecipDesc getValueFromPointer() throws NativeException
    {
        offset = 0;
        
        ulReserved = new LONG(getNextInt());
        ulRecipClass = new LONG(getNextInt());
        lpszName = getNextInt();
        lpszAddress = getNextInt();
        ulEIDSize = new LONG(getNextInt());
        lpEntryID = getNextInt();
               
        offset = 0;
        return this;
    }
    // create a pointer and fill it with values from the struct
    public Pointer createPointer() throws NativeException
    {
        if(pointer != null)
        {
            int i = -4;
            pointer.setIntAt(i+=4, ulReserved.getValue());
            pointer.setIntAt(i+=4, ulRecipClass.getValue());
            pointer.setIntAt(i+=4, lpszName);
            pointer.setIntAt(i+=4, lpszAddress);
            pointer.setIntAt(i+=4, ulEIDSize.getValue());
            pointer.setIntAt(i+=4, lpEntryID);          
        }
        else
        {
            pointer = Pointer.createPointer(sizeOf());
        }
        
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
}
