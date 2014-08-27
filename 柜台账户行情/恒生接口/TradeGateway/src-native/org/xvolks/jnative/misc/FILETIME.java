package org.xvolks.jnative.misc;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 * FILETIME

Contains a 64-bit value representing the number of 100-nanosecond intervals since January 1, 1601 (UTC).

typedef struct _FILETIME {
  DWORD dwLowDateTime;
  DWORD dwHighDateTime;
} FILETIME, 
 *PFILETIME;

Members

dwLowDateTime
    The low-order part of the file time.
dwHighDateTime
    The high-order part of the file time.

Remarks

To convert a FILETIME structure into a time that is easy to display to a user, use the FileTimeToSystemTime function.

It is not recommended that you add and subtract values from the FILETIME structure to obtain relative times. Instead, you should

    * Copy the resulting FILETIME structure to a ULARGE_INTEGER structure using memcpy (using memcpy instead of direct assignment can prevent alignment faults on 64-bit Windows).
    * Use normal 64-bit arithmetic on the ULARGE_INTEGER value.

Not all file systems can record creation and last access time and not all file systems record them in the same manner. For example, on NT FAT, create time has a resolution of 10 milliseconds, write time has a resolution of 2 seconds, and access time has a resolution of 1 day (really, the access date). On NTFS, access time has a resolution of 1 hour. Therefore, the GetFileTime function may not return the same file time information set using the SetFileTime function. Furthermore, FAT records times on disk in local time. However, NTFS records times on disk in UTC. For more information, see File Times.

 * 
 *
 * $Id: FILETIME.java,v 1.1 2006/06/04 21:44:58 mdenty Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */

public class FILETIME extends AbstractBasicData<FILETIME> {
	
	public FILETIME() {
		super(null);
		try {
			createPointer();
			mValue = this;
		} catch (NativeException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public int getLowDateTime() throws NativeException {
		offset = 0;
		return getNextInt();
	}
	public int getHighDateTime() throws NativeException {
		offset = 4;
		return getNextInt();
	}
	
	public FILETIME getValueFromPointer() {
		return this;
	}

	public int getSizeOf() {
		return 8;
	}

	public Pointer createPointer() throws NativeException {
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
		return pointer;
	}

	@Override
	public String toString() {
		try {
			return getLowDateTime() + "/" + getHighDateTime();
		} catch (NativeException e) {
			e.printStackTrace();
			return e.toString();
		}
	}
	
}
