package org.xvolks.jnative.misc;
import org.xvolks.jnative.exceptions.*;
import org.xvolks.jnative.pointers.*;

/**
 * SystemInfo this utility class is used by Kernel32.getNativeSystemInfo().<br>
 * This is the native peer.
 * <pre>
 *typedef struct _SYSTEM_INFO {
 * &nbsp;	union {
 * &nbsp;		DWORD dwOemId;
 * &nbsp;		struct {
 * &nbsp;			WORD wProcessorArchitecture;
 * &nbsp;			WORD wReserved;
 * &nbsp;		};
 * &nbsp;	};
 * &nbsp;	DWORD dwPageSize;
 * &nbsp;	LPVOID lpMinimumApplicationAddress;
 * &nbsp;	LPVOID lpMaximumApplicationAddress;
 * &nbsp;	DWORD_PTR dwActiveProcessorMask;
 * &nbsp;	DWORD dwNumberOfProcessors;
 * &nbsp;	DWORD dwProcessorType;
 * &nbsp;	DWORD dwAllocationGranularity;
 * &nbsp;	WORD wProcessorLevel;
 * &nbsp;	WORD wProcessorRevision;
 * &nbsp;} SYSTEM_INFO;
 *
 *  $Id: SystemInfo.java,v 1.5 2006/06/09 20:44:05 mdenty Exp $;
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class SystemInfo {
	
	public short wProcessorArchitecture;
	public short wReserved;
	public int dwPageSize;
	public int lpMinimumApplicationAddress;
	public int lpMaximumApplicationAddress;
	public int  dwActiveProcessorMask;
	public int  dwNumberOfProcessors;
	public int  dwProcessorType;
	public int  dwAllocationGranularity;
	public short wProcessorLevel;
	public short wProcessorRevision;
	
	public SystemInfo(Pointer lpSystemInfo) throws NativeException {
		int offset = -4;
		wProcessorArchitecture = lpSystemInfo.getAsShort(offset +4);
		wReserved = lpSystemInfo.getAsShort(offset +6);
		dwPageSize = lpSystemInfo.getAsInt(offset +8);
		lpMinimumApplicationAddress = lpSystemInfo.getAsInt(offset +12);
		lpMaximumApplicationAddress = lpSystemInfo.getAsInt(offset +16);
		dwActiveProcessorMask = lpSystemInfo.getAsInt(offset +20);
		dwNumberOfProcessors = lpSystemInfo.getAsInt(offset +24);
		dwProcessorType = lpSystemInfo.getAsInt(offset +28);
		dwAllocationGranularity = lpSystemInfo.getAsInt(offset +32);
		wProcessorLevel = lpSystemInfo.getAsShort(offset +36);
		wProcessorRevision = lpSystemInfo.getAsShort(offset +38);
	}
	
	@Override
	public String toString() {
		return new StringBuilder("wProcessorArchitecture : " + wProcessorArchitecture).
			append("wReserved : " + wReserved).
			append("dwPageSize : " + dwPageSize).
			append("lpMinimumApplicationAddress : " + lpMinimumApplicationAddress).
			append("lpMaximumApplicationAddress : " + lpMaximumApplicationAddress).
			append("dwActiveProcessorMask : " + dwActiveProcessorMask).
			append("dwNumberOfProcessors : " + dwNumberOfProcessors).
			append("dwProcessorType : " + dwProcessorType).
			append("dwAllocationGranularity : " + dwAllocationGranularity).
			append("wProcessorLevel : " + wProcessorLevel).
			append("wProcessorRevision : " + wProcessorRevision).toString();
	}
}
