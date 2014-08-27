/*
 * MapiFileDesc.java
 *
 * Created on 26. Juni 2008, 17:28
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
 *typedef struct {
     ULONG ulReserved;
     ULONG flFlags;
     ULONG nPosition;
     LPSTR lpszPathName;
     LPSTR lpszFileName;
     LPVOID lpFileType;
} MapiFileDesc, FAR *lpMapiFileDesc;
 
 */
public class MapiFileDesc extends AbstractBasicData<MapiFileDesc>
{
    public LONG ulReserved = new LONG(0);
    public LONG flFlags = new LONG(0);
    public LONG nPosition = new LONG(0);
    public int lpszPathName;
    public int lpszFileName;
    public int lpFileType;
     
    /** Creates a new instance of MapiFileDesc */
    public MapiFileDesc() throws NativeException
    {
        super(null);
        createPointer();
    }
    public MapiFileDesc(int nativeAddress) throws NativeException
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
    public MapiFileDesc getValue()
    {
        return this;
    }
    
    public MapiFileDesc getValueFromPointer() throws NativeException
    {
        offset = 0;
        
        ulReserved = new LONG(getNextInt());
        flFlags = new LONG(getNextInt());
        nPosition = new LONG(getNextInt());
        lpszPathName = getNextInt();
        lpszFileName = getNextInt();
        lpFileType = getNextInt();
               
        offset = 0;
        return this;
    }

    public Pointer createPointer() throws NativeException
    {
        if(pointer != null)
        {
            int i = -4;
            pointer.setIntAt(i+=4, ulReserved.getValue());
            pointer.setIntAt(i+=4, flFlags.getValue());
            pointer.setIntAt(i+=4, nPosition.getValue());
            pointer.setIntAt(i+=4, lpszPathName);
            pointer.setIntAt(i+=4, lpszFileName);
            pointer.setIntAt(i+=4, lpFileType);          
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
