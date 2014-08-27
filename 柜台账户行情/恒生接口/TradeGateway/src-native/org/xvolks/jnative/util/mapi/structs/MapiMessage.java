/*
 * MapiMessage.java
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
     ULONG ulReserved;
     LPTSTR lpszSubject;
     LPTSTR lpszNoteText;
     LPTSTR lpszMessageType;
     LPTSTR lpszDateReceived;
     LPTSTR lpszConversationID;
     FLAGS flFlags;
     lpMapiRecipDesc lpOriginator;
     ULONG nRecipCount;
     lpMapiRecipDesc lpRecips;
     ULONG nFileCount;
     lpMapiFileDesc lpFiles;
} MapiMessage, FAR *lpMapiMessage;

 */
public class MapiMessage extends AbstractBasicData<MapiMessage>
{
    public LONG ulReserved = new LONG(0);
    public int lpszSubject;
    public int lpszNoteText;
    public int lpszMessageType;
    public int lpszDateReceived;
    public int lpszConversationID;
    public int flFlags;
    public int lpOriginator;
    public LONG nRecipCount = new LONG(0);
    public int lpRecips;
    public LONG nFileCount = new LONG(0);
    public int lpFiles;
     
    /** Creates a new instance of MapiMessage */
    public MapiMessage() throws NativeException
    {
        super(null);
        createPointer();
    }
     public MapiMessage(int nativeAddress) throws NativeException
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
    public MapiMessage getValue()
    {
        return this;
    }
    
    public MapiMessage getValueFromPointer() throws NativeException
    {
        offset = 0;
        
        ulReserved = new LONG(getNextInt());
        lpszSubject = getNextInt();
        lpszNoteText = getNextInt();
        lpszMessageType = getNextInt();
        lpszDateReceived = getNextInt();
        lpszConversationID = getNextInt();
        flFlags = getNextInt();
        lpOriginator = getNextInt();
        nRecipCount = new LONG(getNextInt());
        lpRecips = getNextInt();
        nFileCount = new LONG(getNextInt());
        lpFiles = getNextInt();
        
        offset = 0;
        return this;
    }

    public Pointer createPointer() throws NativeException
    {
        if(pointer != null)
        {
            int i = -4;
            pointer.setIntAt(i+=4, ulReserved.getValue());
            pointer.setIntAt(i+=4, lpszSubject);
            pointer.setIntAt(i+=4, lpszNoteText);
            pointer.setIntAt(i+=4, lpszMessageType);
            pointer.setIntAt(i+=4, lpszDateReceived);
            pointer.setIntAt(i+=4, lpszConversationID);
            pointer.setIntAt(i+=4, flFlags);
            pointer.setIntAt(i+=4, lpOriginator);
            pointer.setIntAt(i+=4, nRecipCount.getValue());
            pointer.setIntAt(i+=4, lpRecips);
            pointer.setIntAt(i+=4, nFileCount.getValue());
            pointer.setIntAt(i+=4, lpFiles);
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
        return 48;
    }
}
