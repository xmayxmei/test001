/*
 * SHFILEINFO.java
 *
 * Created on 13. März 2007, 15:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.misc.basicStructures;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 *
 * @author Thubby
 */
public class FILEINFO extends AbstractBasicData<FILEINFO> {
    
    public static final int MAX_PATH = 256;
    
/*    
      typedef struct _SHFILEINFO {
        HICON hIcon;
        int iIcon;
        DWORD dwAttributes;
        TCHAR szDisplayName[MAX_PATH];
        TCHAR szTypeName[80];
    } SHFILEINFO;

Members

    hIcon
        Handle to the icon that represents the file. You are responsible for destroying this handle with DestroyIcon when you no longer need it. 
    iIcon
        Index of the icon image within the system image list. 
    dwAttributes
        Array of values that indicates the attributes of the file object. For information about these values, see the IShellFolder::GetAttributesOf method. 
    szDisplayName
        String that contains the name of the file as it appears in the Microsoft Windows Shell, or the path and file name of the file that contains the icon representing the file.
    szTypeName
        String that describes the type of file.
 
 *
 *  Example: 
 *
    SHFILEINFO fileInfo = new SHFILEINFO();
    Shell32.SHGetFileInfo("C:\\Anyfile.ext",new DWORD(0),fileInfo, fileInfo.getSizeOf(), SHFILEINFO.SHGFI_DISPLAYNAME | SHFILEINFO.SHGFI_TYPENAME | SHFILEINFO.SHGFI_ICON | SHFILEINFO.SHGFI_SMALLICON);
    JNative.getLogger().log(fileInfo.getDisplayName());
    JNative.getLogger().log(fileInfo.getIcon()); // Icon needs to be destroyed afterwards with DestroyIcon(LONG hIcon);

*/    
    /** Creates a new instance of SHFILEINFO */
    public FILEINFO() {
        super(null);
        try {
            createPointer();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
   
    public Pointer createPointer() throws NativeException {
        pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
        return pointer;
    }
    
    public int getSizeOf() {
        return 384;
    }
    public FILEINFO getValueFromPointer() throws NativeException {
        return this;
    }
    
    public int getIcon() throws NativeException {
        return pointer.getAsInt(0);
    }
    
    public int getIndex() throws NativeException {
        return pointer.getAsInt(4);
    }
    public String getDisplayName() throws NativeException {
        byte[] b = new byte[MAX_PATH];
        for(int i = 0; i<MAX_PATH-12; i++)
            b[i] = pointer.getAsByte(i+12);
        return new String(b).trim();
    }
    public String getTypeName() throws NativeException {
        byte[] b = new byte[80];
        for(int i = 0; i<80; i++)
            b[i] = pointer.getAsByte(i+12+MAX_PATH);
        return new String(b).trim();
    }
}
