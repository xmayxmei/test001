package org.xvolks.jnative.misc;

/**
 * $Id: MemoryStatusEx.java,v 1.3 2006/06/09 20:44:05 mdenty Exp $
 *
 * <pre>
 * typedef struct _MEMORYSTATUSEX {
 DWORD dwLength;
 DWORD dwMemoryLoad;
 DWORDLONG ullTotalPhys;
 DWORDLONG ullAvailPhys;
 DWORDLONG ullTotalPageFile;
 DWORDLONG ullAvailPageFile;
 DWORDLONG ullTotalVirtual;
 DWORDLONG ullAvailVirtual;
 DWORDLONG ullAvailExtendedVirtual;
 } MEMORYSTATUSEX,
 *LPMEMORYSTATUSEX;
 * </pre>
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
import org.xvolks.jnative.exceptions.*;
import org.xvolks.jnative.misc.basicStructures.*;
import org.xvolks.jnative.pointers.*;
import org.xvolks.jnative.pointers.memory.*;

public class MemoryStatusEx extends AbstractBasicData<MemoryStatusEx> {
	public int dwLength;
	public int dwMemoryLoad;
	public long ullTotalPhys;
	public long ullAvailPhys;
	public long ullTotalPageFile;
	public long ullAvailPageFile;
	public long ullTotalVirtual;
	public long ullAvailVirtual;
	public long ullAvailExtendedVirtual;
	
	
	
	public MemoryStatusEx() {
		super(null);
	}
	
	/**
	 * Method getValueFromPointer
	 *
	 * @return   a T
	 *
	 * @exception   NativeException
	 *
	 */
	public MemoryStatusEx getValueFromPointer() throws NativeException {
		offset = 0;
		dwLength = getNextInt();
		dwMemoryLoad = getNextInt();
		ullTotalPhys = getNextLong();
		ullAvailPhys = getNextLong();
		ullTotalPageFile = getNextLong();
		ullAvailPageFile = getNextLong();
		ullTotalVirtual = getNextLong();
		ullAvailVirtual = getNextLong();
		ullAvailExtendedVirtual = getNextLong();
		return this;
	}
	
	/**
	 * Method getSizeOf
	 * @return   the size of this data
	 */
	public int getSizeOf() {
		return sizeOf();
	}
	
	public static int sizeOf() {
		return 2*4 + 7*8;
	}
	
	
	/**
	 * Method createPointer reserves a native MemoryBlock and copy its value in it
	 * @return   a Pointer on the reserved memory
	 * @exception   NativeException
	 */
	public Pointer createPointer() throws NativeException {
		pointer = new Pointer(new GlobalMemoryBlock(sizeOf()));
		pointer.setIntAt(0, sizeOf());
		return pointer;
	}
	
	
	@Override
	public String toString() {
		return "dwLength : "+ dwLength
			+"\ndwMemoryLoad : "+ dwMemoryLoad
			+"\nullTotalPhys : "+ ullTotalPhys
			+"\nullAvailPhys : "+ ullAvailPhys
			+"\nullTotalPageFile : "+ ullTotalPageFile
			+"\nullAvailPageFile : "+ ullAvailPageFile
			+"\nullTotalVirtual : "+ ullTotalVirtual
			+"\nullAvailVirtual : "+ ullAvailVirtual
			+"\nullAvailExtendedVirtual : "+ ullAvailExtendedVirtual;
	}
}
