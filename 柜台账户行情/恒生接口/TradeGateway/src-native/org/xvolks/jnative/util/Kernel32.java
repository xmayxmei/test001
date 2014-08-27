package org.xvolks.jnative.util;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.com.utils.Basic;
import org.xvolks.jnative.com.utils.ByRef;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.misc.FreeDiskSpace;
import org.xvolks.jnative.misc.MemoryStatusEx;
import org.xvolks.jnative.misc.PROCESSENTRY32;
import org.xvolks.jnative.misc.SecurityAttributes;
import org.xvolks.jnative.misc.SystemInfo;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.basicStructures.DWORD;
import org.xvolks.jnative.misc.basicStructures.HANDLE;
import org.xvolks.jnative.misc.basicStructures.INT64;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.NullPointer;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.HeapMemoryBlock;
import org.xvolks.jnative.pointers.memory.MemoryBlock;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 * Kernel32 this is the class wrapper to Kernel32.dll.<br>
 * When a developper needs a function of this DLL (s)he should add it here.
 *
 * $Id: Kernel32.java,v 1.47 2008/12/24 10:22:09 thubby Exp $;
 *
 * This software is released under the LGPL.
 *
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class Kernel32
{
	
	public static final String DLL_NAME = "Kernel32.dll";
	
	public static final DWORD IOCTL_STORAGE_EJECT_MEDIA = new DWORD(2967560);
	
	public static final int TH32CS_SNAPHEAPLIST = 0x1;
	public static final int TH32CS_SNAPPROCESS = 0x2;
	public static final int TH32CS_SNAPTHREAD = 0x4;
	public static final int TH32CS_SNAPMODULE = 0x8;
	public static final int TH32CS_SNAPALL = (TH32CS_SNAPHEAPLIST | TH32CS_SNAPPROCESS | TH32CS_SNAPTHREAD | TH32CS_SNAPMODULE);
	
	public static final int PAGE_NOACCESS             = 1;
	public static final int PAGE_EXECUTE_READ         = 0x20;
	public static final int PAGE_EXECUTE_READWRITE    = 0x40;
	public static final int MEM_COMMIT                = 0x1000;
	public static final int MEM_RESERVE               = 0x2000;
	public static final int MEM_RELEASE               = 0x8000;
	public static final int PAGE_READONLY = 0x2;
	public static final int PAGE_READWRITE = 0x4;
	public static final int PAGE_WRITECOPY = 0x8;
	public static final int PAGE_EXECUTE = 0x10;
	public static final int PAGE_EXECUTE_WRITECOPY = 0x80;
	public static final int PAGE_GUARD = 0x100;
	public static final int PAGE_NOCACHE = 0x200;
	
	
	/*
	BOOL WINAPI SetFileAttributes(
	  __in  LPCTSTR lpFileName,
	  __in  DWORD dwFileAttributes
	);
	 */
	public static boolean SetFileAttributes(String FileName, DWORD dwFileAttributes) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "SetFileAttributesA");
		int pos = 0;
		jNative.setRetVal(Type.INT);
		Pointer p = Pointer.createPointerFromString(FileName);
		jNative.setParameter(pos++, p.getPointer());
		jNative.setParameter(pos++, dwFileAttributes.getValue());
		jNative.invoke();
		
		p.dispose();
		return jNative.getRetValAsInt() != 0;
	}
	
	/*
	BOOL WINAPI VirtualProtect(
	  __in   LPVOID lpAddress,
	  __in   SIZE_T dwSize,
	  __in   DWORD flNewProtect,
	  __out  PDWORD lpflOldProtect
	);
	 */
	public static boolean VirtualProtect(int lpAddress, int dwSize, DWORD flNewProtect, DWORD lpflOldProtect) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "VirtualProtect");
		int pos = 0;
		jNative.setRetVal(Type.INT);
		jNative.setParameter(pos++, lpAddress);
		jNative.setParameter(pos++, dwSize);
		jNative.setParameter(pos++, flNewProtect.getValue());
		jNative.setParameter(pos++, lpflOldProtect.getPointer());
		jNative.invoke();
		
		return jNative.getRetValAsInt() != 0;
	}
	
	/*
	BOOL WINAPI VirtualFreeEx(
	  __in  HANDLE hProcess,
	  __in  LPVOID lpAddress,
	  __in  SIZE_T dwSize,
	  __in  DWORD dwFreeType
	);
	 */
	public static boolean VirtualFreeEx(HANDLE hProcess, int lpAddress, int dwSize, DWORD dwFreeType) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "VirtualFreeEx");
		int pos = 0;
		jNative.setRetVal(Type.INT);
		jNative.setParameter(pos++, hProcess.getValue());
		jNative.setParameter(pos++, lpAddress);
		jNative.setParameter(pos++, dwSize);
		jNative.setParameter(pos++, dwFreeType.getValue());
		jNative.invoke();
		
		return jNative.getRetValAsInt() != 0;
	}
	
	/*
	 BOOL WINAPI GetExitCodeThread(
	  __in   HANDLE hThread,
	  __out  LPDWORD lpExitCode
	);
	 */
	public static boolean GetExitCodeThread(HANDLE hThread, DWORD lpExitCode) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "GetExitCodeThread");
		int pos = 0;
		jNative.setRetVal(Type.INT);
		jNative.setParameter(pos++, hThread.getValue());
		jNative.setParameter(pos++, lpExitCode.getPointer());
		jNative.invoke();
		
		return jNative.getRetValAsInt() != 0;
	}
	
	public static boolean Process32Next(HANDLE hSnapshot, PROCESSENTRY32 lppe) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "Process32Next");
		int pos = 0;
		jNative.setRetVal(Type.INT);
		jNative.setParameter(pos++, hSnapshot.getValue());
		jNative.setParameter(pos++, lppe.getPointer().getPointer());
		jNative.invoke();
		
		return jNative.getRetValAsInt() != 0;
	}
	
	/*
	 BOOL WINAPI Process32First(
	  __in     HANDLE hSnapshot,
	  __inout  LPPROCESSENTRY32 lppe
	);
	 */
	public static boolean Process32First(HANDLE hSnapshot, PROCESSENTRY32 lppe) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "Process32First");
		int pos = 0;
		jNative.setRetVal(Type.INT);
		jNative.setParameter(pos++, hSnapshot.getValue());
		jNative.setParameter(pos++, lppe.getPointer().getPointer());
		jNative.invoke();
		
		return jNative.getRetValAsInt() != 0;
	}
	
	/*
	HANDLE WINAPI CreateToolhelp32Snapshot(
	  __in  DWORD dwFlags,
	  __in  DWORD th32ProcessID
	);
	 */
	public static HANDLE CreateToolhelp32Snapshot(int dwFlags, int th32ProcessID) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "CreateToolhelp32Snapshot");
		int pos = 0;
		jNative.setRetVal(Type.INT);
		jNative.setParameter(pos++, dwFlags);
		jNative.setParameter(pos++, th32ProcessID);
		jNative.invoke();
		
		return new HANDLE(jNative.getRetValAsInt());
	}
	
	/*
	SIZE_T WINAPI VirtualQuery(
	  __in_opt  LPCVOID lpAddress,
	  __out     PMEMORY_BASIC_INFORMATION lpBuffer,
	  __in      SIZE_T dwLength
	);
	 */
	public static int VirtualQuery(int lpAddress,
			int lpBuffer,
			int dwLength) throws NativeException, IllegalAccessException
	{
		JNative GetProcAddress = new JNative(DLL_NAME, "VirtualQuery");
		int pos = 0;
		GetProcAddress.setRetVal(Type.INT);
		GetProcAddress.setParameter(pos++, lpAddress);
		GetProcAddress.setParameter(pos++, lpBuffer);
		GetProcAddress.setParameter(pos++, dwLength);
		GetProcAddress.invoke();
		
		return GetProcAddress.getRetValAsInt();
	}
	
	/*
	FARPROC WINAPI GetProcAddress(
	  __in  HMODULE hModule,
	  __in  LPCSTR lpProcName
	);
	 */
	public static int GetProcAddress(HANDLE hModule, String lpProcName) throws NativeException, IllegalAccessException
	{
		JNative GetProcAddress = new JNative(DLL_NAME, "GetProcAddress");
		
		int pos = 0;
		GetProcAddress.setRetVal(Type.INT);
		GetProcAddress.setParameter(pos++, hModule.getValue());
		GetProcAddress.setParameter(pos++, lpProcName);
		GetProcAddress.invoke();
		
		return GetProcAddress.getRetValAsInt();
		
		
	}
	/*
	LPVOID WINAPI VirtualAllocEx(
	  __in      HANDLE hProcess,
	  __in_opt  LPVOID lpAddress,
	  __in      SIZE_T dwSize,
	  __in      DWORD flAllocationType,
	  __in      DWORD flProtect
	);
	 */
	public static int VirtualAllocEx(HANDLE hProcess,
			int lpAddress,
			int dwSize,
			DWORD flAllocationType,
			DWORD flProtect) throws NativeException, IllegalAccessException
	{
		JNative VirtualAllocEx = new JNative(DLL_NAME, "VirtualAllocEx");
		
		int pos = 0;
		VirtualAllocEx.setRetVal(Type.INT);
		VirtualAllocEx.setParameter(pos++, hProcess.getValue());
		VirtualAllocEx.setParameter(pos++, lpAddress);
		VirtualAllocEx.setParameter(pos++, dwSize);
		VirtualAllocEx.setParameter(pos++, flAllocationType.getValue());
		VirtualAllocEx.setParameter(pos++, flProtect.getValue());
		VirtualAllocEx.invoke();
		
		return VirtualAllocEx.getRetValAsInt();
		
	}
	
	/*
	HANDLE WINAPI CreateRemoteThread(
	  __in   HANDLE hProcess,
	  __in   LPSECURITY_ATTRIBUTES lpThreadAttributes,
	  __in   SIZE_T dwStackSize,
	  __in   LPTHREAD_START_ROUTINE lpStartAddress,
	  __in   LPVOID lpParameter,
	  __in   DWORD dwCreationFlags,
	  __out  LPDWORD lpThreadId
	);
	 */
	public static HANDLE CreateRemoteThread(HANDLE hProcess,
			SecurityAttributes lpThreadAttributes,
			int dwStackSize,
			int lpStartAddress,
			int lpParameter,
			DWORD dwCreationFlags,
			DWORD lpThreadId) throws NativeException, IllegalAccessException
	{
		JNative CreateRemoteThread = new JNative(DLL_NAME, "CreateRemoteThread");
		
		int pos = 0;
		CreateRemoteThread.setRetVal(Type.INT);
		CreateRemoteThread.setParameter(pos++, hProcess.getValue());
		CreateRemoteThread.setParameter(pos++, lpThreadAttributes == null ? NullPointer.NULL : lpThreadAttributes.getPointer());
		CreateRemoteThread.setParameter(pos++, dwStackSize);
		CreateRemoteThread.setParameter(pos++, lpStartAddress);
		CreateRemoteThread.setParameter(pos++, lpParameter);
		CreateRemoteThread.setParameter(pos++, dwCreationFlags.getValue());
		CreateRemoteThread.setParameter(pos++, lpThreadId.getPointer().getPointer());
		CreateRemoteThread.invoke();
		
		return new HANDLE(CreateRemoteThread.getRetValAsInt());
	}
	
	/*
	HMODULE WINAPI LoadLibrary(
		__in  LPCTSTR lpFileName
	);
	 */
	public static HANDLE LoadLibrary(String lpFileName) throws NativeException, IllegalAccessException
	{
		JNative LoadLibrary = new JNative(DLL_NAME, "LoadLibraryA");
		LoadLibrary.setRetVal(Type.INT);
		LoadLibrary.setParameter(0, lpFileName);
		LoadLibrary.invoke();
		
		return new HANDLE(LoadLibrary.getRetValAsInt());
		
	}
	
	
	public static boolean SetPriorityClass(HANDLE process, DWORD priority) throws NativeException, IllegalAccessException
	{
		JNative jni = null;
		
		jni = new JNative(DLL_NAME, "SetPriorityClass");
		jni.setRetVal(Type.INT);
		
		jni.setParameter(0, process.getValue());
		jni.setParameter(1, priority.getValue());
		
		jni.invoke();
		
		return (jni.getRetValAsInt() != 0);
	}
	
	/*
	BOOL WINAPI Beep(
		DWORD dwFreq,
		DWORD dwDuration
	);
	 */
	public static boolean Beep(DWORD dwFreq, DWORD dwDuration) throws NativeException, IllegalAccessException
	{
		JNative Beep = new JNative(DLL_NAME, "Beep");
		
		Beep.setRetVal(Type.INT);
		Beep.setParameter(0, dwFreq.getValue());
		Beep.setParameter(1, dwDuration.getValue());
		Beep.invoke();
		
		return (Beep.getRetValAsInt() != 0);
	}
	
	
	public static void RtlMoveMemory(int dest, int source, int len) throws NativeException, IllegalAccessException
	{
		JNative RtlMoveMemory = new JNative(DLL_NAME, "RtlMoveMemory");
		RtlMoveMemory.setRetVal(Type.INT);
		RtlMoveMemory.setParameter(0, dest);
		RtlMoveMemory.setParameter(1, source);
		RtlMoveMemory.setParameter(2, len);
		RtlMoveMemory.invoke();
	}
	public static int GlobalFree(int pointer)  throws NativeException, IllegalAccessException
	{
		JNative globalFree = new JNative(DLL_NAME, "GlobalFree");
		globalFree.setRetVal(Type.INT);
		globalFree.setParameter(0, pointer);
		globalFree.invoke();
		return globalFree.getRetValAsInt();
	}
	
	public static int GlobalAlloc(int size, int type) throws NativeException, IllegalAccessException
	{
		JNative GlobalAlloc = new JNative(DLL_NAME, "GlobalAlloc");
		GlobalAlloc.setRetVal(Type.INT);
		GlobalAlloc.setParameter(0, type);
		GlobalAlloc.setParameter(1, size);
		GlobalAlloc.invoke();
		return GlobalAlloc.getRetValAsInt();
	}
	
	/*
	LPVOID GlobalLock(
	  HGLOBAL hMem
	);
	 */
	public static int GlobalLock(int hMem) throws NativeException, IllegalAccessException
	{
		JNative GlobalLock = new JNative(DLL_NAME, "GlobalLock");
		GlobalLock.setRetVal(Type.INT);
		GlobalLock.setParameter(0, hMem);
		GlobalLock.invoke();
		int pos = GlobalLock.getRetValAsInt();
		
		return pos;
	}
	
	/*
	BOOL GlobalUnlock(
	  HGLOBAL hMem
	);
	 */
	public static boolean GlobalUnlock(int hMem) throws NativeException, IllegalAccessException
	{
		JNative GlobalUnlock = new JNative(DLL_NAME, "GlobalUnlock");
		GlobalUnlock.setRetVal(Type.INT);
		GlobalUnlock.setParameter(0, hMem);
		GlobalUnlock.invoke();
		int pos = GlobalUnlock.getRetValAsInt();
		
		return (pos != 0);
	}
	
	/*
	HMODULE WINAPI GetModuleHandle(
	  LPCTSTR lpModuleName
	);
	 */
	public static int GetModuleHandle(String lpModuleName) throws NativeException, IllegalAccessException
	{
		JNative GetModuleHandle = new JNative(DLL_NAME, "GetModuleHandleA");
		GetModuleHandle.setRetVal(Type.INT);
		GetModuleHandle.setParameter(0, lpModuleName);
		GetModuleHandle.invoke();
		int pos = GetModuleHandle.getRetValAsInt();
		
		return pos;
	}
	
	/*
	DWORD WINAPI GetCurrentThreadId(void);
	 */
	public static DWORD GetCurrentThreadId() throws NativeException, IllegalAccessException
	{
		JNative GetCurrentThreadId = new JNative(DLL_NAME, "GetCurrentThreadId");
		GetCurrentThreadId.setRetVal(Type.INT);
		GetCurrentThreadId.invoke();
		int pos = GetCurrentThreadId.getRetValAsInt();
		
		return new DWORD(pos);
	}
	
	
	/*
	BOOL DeviceIoControl(
	  HANDLE hDevice,
	  DWORD dwIoControlCode,
	  LPVOID lpInBuffer,
	  DWORD nInBufferSize,
	  LPVOID lpOutBuffer,
	  DWORD nOutBufferSize,
	  LPDWORD lpBytesReturned,
	  LPOVERLAPPED lpOverlapped
	);
	 *
	 *Example:
	 *
	try {
		HANDLE handle = Kernel32.CreateFile("\\\\.\\d:",
								Kernel32.AccessMask.GENERIC_ALL,
								Kernel32.ShareMode.FILE_SHARE_VALID_FLAGS,
								new SecurityAttributes(),
								Kernel32.CreationDisposition.OPEN_EXISTING,
								Kernel32.FileAttribute.FILE_ATTRIBUTE_READONLY,0);
	 
		if(handle != null) {
			Kernel32.DeviceIoControl(handle, Kernel32.IOCTL_STORAGE_EJECT_MEDIA, NullPointer.NULL, new DWORD(0), NullPointer.NULL, new DWORD(0), NullPointer.NULL, NullPointer.NULL);
			Kernel32.CloseHandle(handle);
		}
	} catch(Exception e) {
		e.printStackTrace();
	}
	 */
	public static boolean DeviceIoControl(HANDLE hDevice,
			DWORD dwIoControlCode,
			Pointer lpInBuffer,
			DWORD nInBufferSize,
			Pointer lpOutBuffer,
			DWORD nOutBufferSize,
			Pointer lpBytesReturned,
			Pointer lpOverlapped)
			throws NativeException, IllegalAccessException
	{
		
		JNative DeviceIoControl = new JNative(DLL_NAME, "DeviceIoControl");
		DeviceIoControl.setRetVal(Type.INT);
		int pos = 0;
		Pointer p = null;
		
		DeviceIoControl.setParameter(pos++, hDevice.getValue());
		DeviceIoControl.setParameter(pos++, dwIoControlCode.getValue());
		DeviceIoControl.setParameter(pos++, lpInBuffer);
		DeviceIoControl.setParameter(pos++, nInBufferSize.getValue());
		DeviceIoControl.setParameter(pos++, lpOutBuffer);
		DeviceIoControl.setParameter(pos++, nOutBufferSize.getValue());
		
		// if lpOverlapped is NullPointer, lpBytesReturned must not be NullPointer!
		// TODO: Is it maybe better here to throw an Exception??
		if(lpOverlapped instanceof NullPointer && lpBytesReturned instanceof NullPointer)
		{
			JNative.getLogger().log(SEVERITY.WARN, "lpOverlapped is NullPointer, lpBytesReturned must not be NullPointer!");
			p = new Pointer(MemoryBlockFactory.createMemoryBlock(16));
			DeviceIoControl.setParameter(pos++, p);
		}
		else
			DeviceIoControl.setParameter(pos++, lpBytesReturned);
		
		DeviceIoControl.setParameter(pos++, lpOverlapped);
		DeviceIoControl.invoke();
		
		pos = DeviceIoControl.getRetValAsInt();
		
		if(p != null)
			p.dispose();
		
		return (pos != 0);
	}
	
	/*
	ATOM GlobalDeleteAtom(
		ATOM nAtom
	);
	 */
	public static int GlobalDeleteAtom(int atom) throws NativeException, IllegalAccessException
	{
		JNative GlobalDeleteAtom = new JNative(DLL_NAME, "GlobalDeleteAtom");
		GlobalDeleteAtom.setRetVal(Type.INT);
		int pos = 0;
		GlobalDeleteAtom.setParameter(pos++, atom);
		GlobalDeleteAtom.invoke();
		pos = GlobalDeleteAtom.getRetValAsInt();
		
		return pos;
	}
	/*
	ATOM GlobalAddAtom(
		LPCTSTR lpString
	);
	 */
	public static int GlobalAddAtom(String lpString) throws NativeException, IllegalAccessException
	{
		JNative GlobalAddAtom = new JNative(DLL_NAME, "GlobalAddAtomA");
		GlobalAddAtom.setRetVal(Type.INT);
		int pos = 0;
		GlobalAddAtom.setParameter(pos++, lpString);
		GlobalAddAtom.invoke();
		pos = GlobalAddAtom.getRetValAsInt();
		
		return pos;
	}
	
	/*
	 *returns some Information about the Windows Operating System.
	 *Look at class WindowsVersion for some convenience methodes
	 */
	public static DWORD GetVersion() throws NativeException, IllegalAccessException
	{
		JNative	nGetVersion = new JNative(DLL_NAME, "GetVersion");
		nGetVersion.setRetVal(Type.INT);
		
		nGetVersion.invoke();
		
		return new DWORD(nGetVersion.getRetValAsInt());
	}
	
	public static HANDLE GetCurrentProcess() throws NativeException, IllegalAccessException
	{
		JNative nGetCurrentProcess = new JNative(DLL_NAME, "GetCurrentProcess");
		nGetCurrentProcess.setRetVal(Type.INT);
		
		nGetCurrentProcess.invoke();
		
		return new HANDLE(nGetCurrentProcess.getRetValAsInt());
	}
	
	/*
	  HANDLE hProcess,
	  LPCVOID lpBaseAddress,
	  LPVOID lpBuffer,
	  SIZE_T nSize,
	  SIZE_T* lpNumberOfBytesRead
	 */
	
	public static boolean ReadProcessMemory(HANDLE hProcess,
			int lpBaseAddress,
			Pointer lpBuffer,
			int len)
			throws NativeException, IllegalAccessException
	{
		JNative gms = new JNative(DLL_NAME, "ReadProcessMemory");
		gms.setRetVal(Type.INT);
		
		int i = 0;
		gms.setParameter(i++, hProcess.getValue());
		gms.setParameter(i++, lpBaseAddress);
		gms.setParameter(i++, lpBuffer);
		gms.setParameter(i++, len);
		gms.setParameter(i++, 0);
		
		gms.invoke();
		
		i = gms.getRetValAsInt();
		
		return (i != 0);
	}
	
	/*
		HANDLE hProcess,
		LPVOID lpBaseAddress,
		LPCVOID lpBuffer,
		SIZE_T nSize,
		SIZE_T* lpNumberOfBytesWritten
	 */
	
	public static boolean WriteProcessMemory(HANDLE hProcess,
			int lpBaseAddress,
			Pointer lpBuffer,
			int len)
			throws NativeException, IllegalAccessException
	{
		
		JNative gms = new JNative(DLL_NAME, "WriteProcessMemory");
		gms.setRetVal(Type.INT);
		
		int i = 0;
		gms.setParameter(i++, hProcess.getValue());
		gms.setParameter(i++, lpBaseAddress);
		gms.setParameter(i++, lpBuffer);
		gms.setParameter(i++, len);
		gms.setParameter(i++, 0);
		
		gms.invoke();
		
		i = gms.getRetValAsInt();
		
		return (i != 0);
	}
	
	/**
	 * Method globalMemoryStatusEx
	 *
	 * <pre>
	 *   BOOL GlobalMemoryStatusEx(
	 *  	LPMEMORYSTATUSEX lpBuffer
	 *   );
	 *
	 *  	 Parameters
	 *
	 *  	 lpBuffer
	 *  	 [in, out] Pointer to a MEMORYSTATUSEX structure that receives information about current memory availability.
	 *
	 *  	 Return Values
	 *
	 *  	 If the function succeeds, the return value is nonzero.
	 *
	 *  	 If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 *  	 Remarks
	 *
	 *  	 You can use the GlobalMemoryStatusEx function to determine how much memory your application can allocate without severely impacting other applications.
	 *
	 *  	 The information returned by the GlobalMemoryStatusEx function is volatile. There is no guarantee that two sequential calls to this function will return the same information.
	 * </pre>
	 *
	 * @return a MemoryStatusEx
	 *
	 * @exception NativeException
	 * @exception IllegalAccessException
	 *
	 */
	public static final MemoryStatusEx GlobalMemoryStatusEx()
	throws NativeException, IllegalAccessException
	{
		JNative gms = new JNative(DLL_NAME, "GlobalMemoryStatusEx");
		gms.setRetVal(Type.INT);
		MemoryStatusEx mem = new MemoryStatusEx();
		gms.setParameter(0, mem.createPointer());
		gms.invoke();
		if (gms.getRetValAsInt() == 0)
		{
			throw new NativeException(
					"The function call has failed. To get extended error information, call GetLastError.");
		}
		return mem.getValueFromPointer();
	}
	
	/*
	BOOL SetFilePointerEx(
	  HANDLE hFile,
	  LARGE_INTEGER liDistanceToMove,
	  PLARGE_INTEGER lpNewFilePointer,
	  DWORD dwMoveMethod
	);
	 */
	public static final boolean SetFilePointerEx(HANDLE hFile, INT64 liDistanceToMove,
			INT64 lpNewFilePointer, MoveMode dwMoveMethod)
			throws NativeException, IllegalAccessException
	{
		
		JNative SetFilePointer = new JNative(DLL_NAME, "SetFilePointerEx");
		SetFilePointer.setRetVal(Type.INT);
		
		int i = 0;
		SetFilePointer.setParameter(i++, hFile.getValue());
		SetFilePointer.setParameter(i++, Type.LONG, ""+liDistanceToMove.getValue());
		SetFilePointer.setParameter(i++, lpNewFilePointer.getPointer());
		SetFilePointer.setParameter(i++, dwMoveMethod.getValue());
		
		SetFilePointer.invoke();
		
		return (SetFilePointer.getRetValAsInt() != 0);
	}
	/*
	BOOL ReadFile(
	  HANDLE hFile,
	  LPVOID lpBuffer,
	  DWORD nNumberOfBytesToRead,
	  LPDWORD lpNumberOfBytesRead,
	  LPOVERLAPPED lpOverlapped
	);
	 */
	public static final boolean ReadFile(HANDLE hFile, Pointer lpBuffer,
			int nNumberOfBytesToRead, DWORD lpNumberOfBytesRead,
			Pointer lpOverlapped)
			throws NativeException, IllegalAccessException
	{
		JNative	nReadFile = new JNative(DLL_NAME, "ReadFile");
		nReadFile.setRetVal(Type.INT);
		
		
		int i = 0;
		nReadFile.setParameter(i++, hFile.getValue());
		nReadFile.setParameter(i++, lpBuffer);
		nReadFile.setParameter(i++, nNumberOfBytesToRead);
		nReadFile.setParameter(i++, lpNumberOfBytesRead.getPointer());
		nReadFile.setParameter(i++, lpOverlapped);
		
		nReadFile.invoke();
		
		return (nReadFile.getRetValAsInt() != 0);
	}
	/*
	BOOL WriteFile(
	  HANDLE hFile,
	  LPCVOID lpBuffer,
	  DWORD nNumberOfBytesToWrite,
	  LPDWORD lpNumberOfBytesWritten,
	  LPOVERLAPPED lpOverlapped
	);
	 **/
	public static final boolean WriteFile(HANDLE hFile, Pointer lpBuffer,
			int nNumberOfBytesToWrite, DWORD lpNumberOfBytesWritten,
			Pointer lpOverlapped)
			throws NativeException, IllegalAccessException
	{
		
		JNative nWriteFile = new JNative(DLL_NAME, "WriteFile");
		nWriteFile.setRetVal(Type.INT);
		
		
		int i = 0;
		nWriteFile.setParameter(i++, hFile.getValue());
		nWriteFile.setParameter(i++, lpBuffer);
		nWriteFile.setParameter(i++, nNumberOfBytesToWrite);
		nWriteFile.setParameter(i++, lpNumberOfBytesWritten.getPointer());
		nWriteFile.setParameter(i++, lpOverlapped);
		
		nWriteFile.invoke();
		
		return (nWriteFile.getRetValAsInt() != 0);
	}
	/**
	 * HANDLE CreateFile( LPCTSTR lpFileName, DWORD dwDesiredAccess, DWORD
	 * dwShareMode, LPSECURITY_ATTRIBUTES lpSecurityAttributes, DWORD
	 * dwCreationDisposition, DWORD dwFlagsAndAttributes, HANDLE hTemplateFile );
	 */
	public static final HANDLE CreateFile(String fileName, int desiredAccess,
			int shareMode, SecurityAttributes securityAttributes,
			int creationDisposition, int flagsAndAttributes, int templateFile)
			throws NativeException, IllegalAccessException
	{
		
		JNative createFile = new JNative(DLL_NAME, "CreateFileA");
		createFile.setRetVal(Type.INT);
		int i = 0;
		createFile.setParameter(i++, Type.STRING, fileName);
		createFile.setParameter(i++, Type.INT, "" + desiredAccess);
		createFile.setParameter(i++, Type.INT, "" + shareMode);
		createFile.setParameter(i++, securityAttributes == null ? NullPointer.NULL : securityAttributes.getPointer());
		createFile.setParameter(i++, Type.INT, "" + creationDisposition);
		createFile.setParameter(i++, Type.INT, "" + flagsAndAttributes);
		createFile.setParameter(i++, Type.INT, "" + templateFile);
		createFile.invoke();
		return new HANDLE(createFile.getRetValAsInt());
	}
	
	public static final HANDLE CreateFile(String fileName,
			AccessMask desiredAccess, ShareMode shareMode,
			SecurityAttributes securityAttributes,
			CreationDisposition creationDisposition,
			FileAttribute flagsAndAttributes, int templateFile)
			throws NativeException, IllegalAccessException
	{
		return CreateFile(fileName, desiredAccess.getValue(),
				shareMode.getValue(), securityAttributes, creationDisposition
				.getValue(), flagsAndAttributes.getValue(),
				templateFile);
	}

    @Deprecated // use JNative.getLastError()
	public static final int GetLastError() throws NativeException,
			IllegalAccessException
	{
		JNative lastError = new JNative(DLL_NAME, "GetLastError");
		lastError.setRetVal(Type.INT);
		
		lastError.invoke();
		return lastError.getRetValAsInt();
	}
	
	/**
	 * typedef struct _SYSTEM_INFO { union { DWORD dwOemId; struct { WORD
	 * wProcessorArchitecture; WORD wReserved; }; }; DWORD dwPageSize; LPVOID
	 * lpMinimumApplicationAddress; LPVOID lpMaximumApplicationAddress;
	 * DWORD_PTR dwActiveProcessorMask; DWORD dwNumberOfProcessors; DWORD
	 * dwProcessorType; DWORD dwAllocationGranularity; WORD wProcessorLevel;
	 * WORD wProcessorRevision; } SYSTEM_INFO;
	 */
	
	public static final SystemInfo GetNativeSystemInfo()
	throws NativeException, IllegalAccessException
	{
		JNative info = new JNative(DLL_NAME, "GetNativeSystemInfo");
		Pointer lpSystemInfo = new Pointer(new HeapMemoryBlock(4 + 4 * 7 + 2 * 2));
		info.setParameter(0, lpSystemInfo);
		info.invoke();
		SystemInfo infoS = new SystemInfo(lpSystemInfo);
		lpSystemInfo.dispose();
		return infoS;
	}
	
	public static final String GetComputerName() throws NativeException,
			IllegalAccessException
	{
		JNative v = new JNative(DLL_NAME, "GetComputerNameA");
		int i = 0;
		v.setRetVal(Type.INT);
		Pointer pName = new Pointer(new HeapMemoryBlock(1024));
		LONG pSize = new LONG(1024);
		v.setParameter(i++, pName);
		v.setParameter(i++, pSize.createPointer());
		v.invoke();
		int ret = v.getRetValAsInt();
		if (ret == 0)
		{
			// return "null";
			throw new NativeException(
					"GetComputerNameA failed call GetLastError()");
		}
		else
		{
			// JNative.getLogger().log("pSize = " + pSize.getValueFromPointer());
			// JNative.getLogger().log("pName = " + pName.getAsString());
			String name = pName.getAsString().substring(0,
					pSize.getValueFromPointer());
			pName.dispose();
			return name;
		}
	}
	
	public static final FreeDiskSpace GetDiskFreeSpaceEx(String drive)
	throws NativeException, IllegalAccessException
	{
		if (drive == null)
			throw new NullPointerException("The drive name cannot be null !");
		
		INT64 lFreeBytesAvailable = new INT64(0);
		INT64 lTotalNumberOfBytes = new INT64(0);
		INT64 lTotalNumberOfFreeBytes = new INT64(0);
		
		int i = 0;
		JNative fs = new JNative(DLL_NAME, "GetDiskFreeSpaceExA");
		fs.setRetVal(Type.INT);
		fs.setParameter(i++, Type.STRING, drive);
		fs.setParameter(i++, lFreeBytesAvailable.createPointer());
		fs.setParameter(i++, lTotalNumberOfBytes.createPointer());
		fs.setParameter(i++, lTotalNumberOfFreeBytes.createPointer());
		fs.invoke();
		return new FreeDiskSpace(drive, lFreeBytesAvailable
				.getValueFromPointer(), lTotalNumberOfBytes
				.getValueFromPointer(), lTotalNumberOfFreeBytes
				.getValueFromPointer());
	}
	
	/**
	 * <pre>
	 *   Opens an existing process object.
	 *
	 *  	 HANDLE WINAPI OpenProcess(
	 *  	 DWORD dwDesiredAccess,
	 *  	 BOOL bInheritHandle,
	 *  	 DWORD dwProcessId
	 *  	 );
	 *
	 *  	 Parameters
	 *
	 *  	 dwDesiredAccess
	 *  	 [in] The access to the process object. This access right is checked against the security descriptor for the process. This parameter can be one or more of the process access rights.
	 *
	 *  	 If the caller has enabled the SeDebugPrivilege privilege, the requested access is granted regardless of the contents of the security descriptor.
	 *  	 bInheritHandle
	 *  	 [in] If this value is TRUE, processes created by this process will inherit the handle. Otherwise, the processes do not inherit this handle.
	 *  	 dwProcessId
	 *  	 [in] The identifier of the process to be opened.
	 *
	 *  	 Return Values
	 *
	 *  	 If the function succeeds, the return value is an open handle to the specified process.
	 *
	 *  	 If the function fails, the return value is NULL. To get extended error information, call GetLastError.
	 *  	 Remarks
	 *
	 *  	 To open a handle to another another process and obtain full access rights, you must enable the SeDebugPrivilege privilege. For more information, see Changing Privileges in a Token.
	 *
	 *  	 The handle returned by the OpenProcess function can be used in any function that requires a handle to a process, such as the wait functions, provided the appropriate access rights were requested.
	 *
	 *  	 When you are finished with the handle, be sure to close it using the CloseHandle function
	 *
	 * </pre>
	 *
	 * <b>dwDesiredAccess values</b>
	 *
	 * <pre>
	 *   The valid access rights for process objects include the standard access rights and some process-specific access rights. The following table lists the standard access rights used by all objects.
	 *  	 Value 	Meaning
	 *  	 DELETE (0x00010000L) 	Required to delete the object.
	 *  	 READ_CONTROL (0x00020000L) 	Required to read information in the security descriptor for the object, not including the information in the SACL. To read or write the SACL, you must request the ACCESS_SYSTEM_SECURITY access right. For more information, see SACL Access Right.
	 *  	 SYNCHRONIZE (0x00100000L) 	The right to use the object for synchronization. This enables a thread to wait until the object is in the signaled state.
	 *  	 WRITE_DAC (0x00040000L) 	Required to modify the DACL in the security descriptor for the object.
	 *  	 WRITE_OWNER (0x00080000L) 	Required to change the owner in the security descriptor for the object.
	 *
	 *  	 The following table lists the process-specific access rights.
	 *  	 Value 	Meaning
	 *  	 PROCESS_ALL_ACCESS (0x1F0FFF) 	All possible access rights for a process object.
	 *  	 PROCESS_CREATE_PROCESS (0x0080) 	Required to create a process.
	 *  	 PROCESS_CREATE_THREAD (0x0002) 	Required to create a thread.
	 *  	 PROCESS_DUP_HANDLE (0x0040) 	Required to duplicate a handle using DuplicateHandle.
	 *  	 PROCESS_QUERY_INFORMATION (0x0400) 	Required to retrieve certain information about a process, such as its token, exit code, and priority class (see OpenProcessToken, GetExitCodeProcess, GetPriorityClass, and IsProcessInJob).
	 *  	 PROCESS_QUERY_LIMITED_INFORMATION (0x1000) 	Required to retrieve certain information about a process (see QueryFullProcessImageName).
	 *  	 PROCESS_SET_QUOTA (0x0100) 	Required to set memory limits using SetProcessWorkingSetSize.
	 *  	 PROCESS_SET_INFORMATION (0x0200) 	Required to set certain information about a process, such as its priority class (see SetPriorityClass).
	 *  	 PROCESS_SUSPEND_RESUME (0x0800) 	Required to suspend or resume a process.
	 *  	 PROCESS_TERMINATE (0x0001) 	Required to terminate a process using TerminateProcess.
	 *  	 PROCESS_VM_OPERATION (0x0008) 	Required to perform an operation on the address space of a process (see VirtualProtectEx and WriteProcessMemory).
	 *  	 PROCESS_VM_READ (0x0010) 	Required to read memory in a process using ReadProcessMemory.
	 *  	 PROCESS_VM_WRITE (0x0020) 	Required to write to memory in a process using WriteProcessMemory.
	 *  	 SYNCHRONIZE (0x00100000L) 	Required to wait for the process to terminate using the wait functions.
	 *
	 *  	 To open a handle to another process and obtain full access rights, you must enable the SeDebugPrivilege privilege. For more information, see Changing Privileges in a Token.
	 *
	 *  	 The handle returned by the CreateProcess function has PROCESS_ALL_ACCESS access to the process object. When you call the OpenProcess function, the system checks the requested access rights against the DACL in the process's security descriptor. When you call the GetCurrentProcess function, the system returns a pseudohandle with the maximum access that the DACL allows to the caller.
	 *
	 *  	 You can request the ACCESS_SYSTEM_SECURITY access right to a process object if you want to read or write the object's SACL. For more information, see Access-Control Lists (ACLs) and SACL Access Right.
	 *
	 *  	 Warning  A process that has some of the access rights noted here can use them to gain other access rights. For example, if process A has a handle to process B with PROCESS_DUP_HANDLE access, it can duplicate the pseudo handle for process B. This creates a handle that has maximum access to process B. For more information on pseudo handles, see GetCurrentProcess.
	 *
	 *
	 * </pre>
	 */
	
	public static HANDLE OpenProcess(int dwDesiredAccess,
			boolean bInheritHandle, int dwProcessId) throws NativeException,
			IllegalAccessException
	{
		JNative	nOpenProcess = new JNative(DLL_NAME, "OpenProcess");
		nOpenProcess.setRetVal(Type.INT);
		
		nOpenProcess.setParameter(0, dwDesiredAccess);
		nOpenProcess.setParameter(1, bInheritHandle ? 1 : 0);
		nOpenProcess.setParameter(2, dwProcessId);
		nOpenProcess.invoke();
		
		return new HANDLE(nOpenProcess.getRetValAsInt());
	}
	
	/**
	 * <pre>
	 *   Closes an open object handle.
	 *
	 *  	 BOOL CloseHandle(
	 *  	 HANDLE hObject
	 *  	 );
	 *
	 *  	 Parameters
	 *
	 *  	 hObject
	 *  	 [in] A handle to an open object. This parameter can be a pseudo handle or INVALID_HANDLE_VALUE.
	 *
	 *  	 Return Values
	 *
	 *  	 If the function succeeds, the return value is nonzero.
	 *
	 *  	 If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 *
	 *  	 If the application is running under a debugger, the function will throw an exception if it receives either a handle value that is not valid or a pseudo-handle value. This can happen if you close a handle twice, or if you call CloseHandle on a handle returned by the FindFirstFile function.
	 *  	 Remarks
	 *
	 *  	 The CloseHandle function closes handles to the following objects:
	 *
	 *   Access token
	 *   Communications device
	 *   Console input
	 *   Console screen buffer
	 *   Event
	 *   File
	 *   File mapping
	 *   Job
	 *   Mailslot
	 *   Memory resource notification
	 *   Mutex
	 *   Named pipe
	 *   Pipe
	 *   Process
	 *   Semaphore
	 *   Socket
	 *   Thread
	 *   Waitable timer
	 *
	 *  	 CloseHandle invalidates the specified object handle, decrements the object's handle count, and performs object retention checks. After the last handle to an object is closed, the object is removed from the system.
	 *
	 *  	 Closing a thread handle does not terminate the associated thread. To remove a thread object, you must terminate the thread, then close all handles to the thread.
	 *
	 *  	 Use CloseHandle to close handles returned by calls to the CreateFile function. Use FindClose to close handles returned by calls to FindFirstFile.
	 * </pre>
	 */
	public static boolean CloseHandle(HANDLE handle) throws NativeException,
			IllegalAccessException
	{
		JNative	nCloseHandle = new JNative(DLL_NAME, "CloseHandle");
		nCloseHandle.setRetVal(Type.INT);
		
		nCloseHandle.setParameter(0, handle.getValue());
		nCloseHandle.invoke();
		return (nCloseHandle.getRetValAsInt() != 0);
	}
	
	/**
	 * <pre>
	 *   TerminateProcess
	 *
	 *  	 Terminates the specified process and all of its threads.
	 *
	 *  	 BOOL WINAPI TerminateProcess(
	 *  	 HANDLE hProcess,
	 *  	 UINT uExitCode
	 *  	 );
	 *
	 *  	 Parameters
	 *
	 *  	 hProcess
	 *  	 [in] A handle to the process to be terminated.
	 *
	 *  	 The handle must have the PROCESS_TERMINATE access right. For more information, see Process Security and Access Rights.
	 *  	 uExitCode
	 *  	 [in] The exit code to be used by the process and threads terminated as a result of this call. Use the GetExitCodeProcess function to retrieve a process's exit value. Use the GetExitCodeThread function to retrieve a thread's exit value.
	 *
	 *  	 Return Values
	 *
	 *  	 If the function succeeds, the return value is nonzero.
	 *
	 *  	 If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 *  	 Remarks
	 *
	 *  	 The TerminateProcess function is used to unconditionally cause a process to exit. The state of global data maintained by dynamic-link libraries (DLLs) may be compromised if TerminateProcess is used rather than ExitProcess.
	 *
	 *  	 TerminateProcess initiates termination and returns immediately. This stops execution of all threads within the process and requests cancellation of all pending I/O. The terminated process cannot exit until all pending I/O has been completed or canceled.
	 *
	 *  	 A process cannot prevent itself from being terminated.
	 *
	 * </pre>
	 */
	public static boolean TerminateProcess(HANDLE lHandle, int exitCode)
	throws NativeException, IllegalAccessException
	{
		JNative	nTerminateProcess = new JNative(DLL_NAME, "TerminateProcess");
		nTerminateProcess.setRetVal(Type.INT);
		
		nTerminateProcess.setParameter(0, lHandle.getValue());
		nTerminateProcess.setParameter(1, exitCode);
		nTerminateProcess.invoke();
		return (nTerminateProcess.getRetValAsInt() != 0);
	}
	
	/**
	 * <b>SetLastError</b>
	 *
	 * <pre>
	 *  	 Sets the last-error code for the calling thread.
	 *
	 *  	 void SetLastError(
	 *  	 DWORD dwErrCode
	 *  	 );
	 *
	 *  	 Parameters
	 *
	 *  	 dwErrCode
	 *  	 [in] The last-error code for the thread.
	 *
	 *  	 Return Values
	 *
	 *  	 This function does not return a value.
	 *  	 Remarks
	 *
	 *  	 The last-error code is kept in thread local storage so that multiple threads do not overwrite each other's values.
	 *
	 *  	 This function is intended primarily for use by dynamic-link libraries (DLL). A DLL can provide the applications that are using it with additional diagnostic information by calling this function after an error occurs. Most functions call SetLastError or SetLastErrorEx only when they fail. However, some system functions call SetLastError or SetLastErrorEx under conditions of success; those cases are noted in each function's documentation.
	 *
	 *  	 Applications can optionally retrieve the value set by this function by using the GetLastError function immediately after a function fails.
	 *
	 *  	 Error codes are 32-bit values (bit 31 is the most significant bit). Bit 29 is reserved for application-defined error codes; no system error code has this bit set. If you are defining an error code for your application, set this bit to indicate that the error code has been defined by your application and to ensure that your error code does not conflict with any system-defined error codes.
	 *
	 * </pre>
	 *
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	
	public static void SetLastError(int value) throws NativeException,
			IllegalAccessException
	{
		JNative	nSetLastError = new JNative(DLL_NAME, "SetLastError");
		nSetLastError.setParameter(0, value);
		nSetLastError.invoke();
	}
	
	/**
	 * <pre>
	 *    CreateFileMapping
	 *
	 *  	 Creates or opens a named or unnamed file mapping object for a specified file.
	 *
	 *  	 To specify the NUMA node for the physical memory, see CreateFileMappingNuma.
	 *
	 *  	 HANDLE CreateFileMapping(
	 *  	 HANDLE hFile,
	 *  	 LPSECURITY_ATTRIBUTES lpAttributes,
	 *  	 DWORD flProtect,
	 *  	 DWORD dwMaximumSizeHigh,
	 *  	 DWORD dwMaximumSizeLow,
	 *  	 LPCTSTR lpName
	 *  	 );
	 *
	 *  	 Parameters
	 *
	 *  	 hFile
	 *  	 [in] A handle to the file from which to create a mapping object.
	 *
	 *  	 The file must be opened with access rights that are compatible with the protection flags that the flProtect parameter specifies. It is not required, but it is recommended that files you intend to map be opened for exclusive access. For more information, see File Security and Access Rights.
	 *
	 *  	 If hFile is INVALID_HANDLE_VALUE, the calling process must also specify a mapping object size in the dwMaximumSizeHigh and dwMaximumSizeLow parameters. In this scenario, CreateFileMapping creates a file mapping object of a specified size that the operating system paging file backs, instead of by a named file in the file system.
	 *
	 *  	 The file mapping object can be shared by duplication, inheritance, or by name. The initial contents of the pages in a file mapping object are 0 (zero).
	 *  	 lpAttributes
	 *  	 [in] A pointer to a SECURITY_ATTRIBUTES structure that determines whether or not a returned handle can be inherited by child processes.
	 *
	 *  	 If lpAttributes is NULL, the handle cannot be inherited.
	 *
	 *  	 The lpSecurityDescriptor member of the structure specifies a security descriptor for a new file mapping object.
	 *
	 *  	 If lpAttributes is NULL, the file mapping object gets a default security descriptor. The access control lists (ACL) in the default security descriptor for a file mapping object come from the primary or impersonation token of the creator.
	 *  	 flProtect
	 *  	 [in] The protection for the file view, when the file is mapped.
	 *
	 *  	 This parameter can be one of the following values.
	 *  	 Value 	Meaning
	 *  	 PAGE_READONLY 	Gives read-only access to a specific region of pages.
	 *
	 *  	 An attempt to write to a specific region results in an access violation. The file that the hFile parameter specifies must be created with the GENERIC_READ access right.
	 *  	 PAGE_READWRITE 	Gives read/write access to a specific region of pages.
	 *
	 *  	 The file that hFile specifies must be created with the GENERIC_READ and GENERIC_WRITE access rights.
	 *  	 PAGE_WRITECOPY 	Gives copy-on-write access to a specific region of pages.
	 *
	 *  	 The files that the hFile parameter specifies must be created with the GENERIC_READ access right.
	 *  	 PAGE_EXECUTE_READ 	Gives read and execute access to a specific region of pages.
	 *
	 *  	 The file specified by hFile must be created with the GENERIC_READ and GENERIC_EXECUTE access rights.
	 *
	 *  	 Windows Server 2003 and Windows XP:  This feature is not available until Windows XP SP2 and Windows Server 2003 SP1.
	 *
	 *  	 PAGE_EXECUTE_READWRITE 	Gives read, write, and execute access to a specific region of pages.
	 *
	 *  	 The file that hFile specifies must be created with the GENERIC_READ, GENERIC_WRITE, and GENERIC_EXECUTE access rights.
	 *
	 *  	 Windows Server 2003 and Windows XP:  This feature is not available until Windows XP SP2 and Windows Server 2003 SP1.
	 *
	 *  	 An application can specify section attributes by combining (using the bitwise OR operator) one or more of the following section attribute values with one of the preceding page protection values.
	 *  	 Value 	Meaning
	 *  	 SEC_COMMIT 	Allocates physical storage in memory or the paging file on disk for all pages of a section.
	 *
	 *  	 This is the default setting.
	 *  	 SEC_IMAGE 	Sets the file that is specified for section file mapping to be an executable image file.
	 *
	 *  	 Because the mapping information and file protection are taken from the image file, no other attributes are valid with SEC_IMAGE.
	 *
	 *  	 Windows Me/98/95:  This flag is not supported.
	 *
	 *  	 SEC_LARGE_PAGES 	Enables large pages to be used when mapping images or pagefile-backed sections, but not when mapping data for regular-sized files. Be sure to specify the maximum size of the file mapping as the minimum size of a large page reported by the GetLargePageMinimum function and to enable the SeLockMemoryPrivilege privilege.
	 *
	 *  	 Windows XP/2000:  This flag is not supported until Windows Server 2003 SP1.
	 *
	 *  	 SEC_NOCACHE 	Sets all pages of a section to non-cachable.
	 *
	 *  	 Applications should not use this flag except when explicitly required for a device. Using the interlocked functions with memory mapped by a SEC_NOCACHE section can result in an EXCEPTION_ILLEGAL_INSTRUCTION exception.
	 *
	 *  	 SEC_NOCACHE requires either the SEC_RESERVE or SEC_COMMIT to be set.
	 *
	 *  	 Windows Me/98/95:  This flag is not supported.
	 *
	 *  	 SEC_RESERVE 	Reserves all pages of a section without allocating physical storage.
	 *
	 *  	 The reserved range of pages cannot be used by any other allocation operations until the range of pages is released.
	 *
	 *  	 Reserved pages can be identified in subsequent calls to the VirtualAlloc function. This attribute is valid only if the hFile parameter is INVALID_HANDLE_VALUE; that is, a file mapping object that the operating system paging file backs.
	 *  	 dwMaximumSizeHigh
	 *  	 [in] The high-order DWORD of the maximum size of a file mapping object.
	 *  	 dwMaximumSizeLow
	 *  	 [in] The low-order DWORD of the maximum size of a file mapping object.
	 *
	 *  	 If this parameter and dwMaximumSizeHigh are 0 (zero), the maximum size of the file mapping object is equal to the current size of the file that hFile identifies.
	 *
	 *  	 An attempt to map a file with a length of 0 (zero) fails with an error code of ERROR_FILE_INVALID. Applications should test for files with a length of 0 (zero) and reject those files.
	 *  	 lpName
	 *  	 [in] A pointer to a null-terminated string that specifies the name of a mapping object.
	 *
	 *  	 If this parameter matches the name of an existing mapping object that is named, the function requests access to the mapping object with the protection that flProtect specifies.
	 *
	 *  	 If this parameter is NULL, the mapping object is created without a name.
	 *
	 *  	 If lpName matches the name of an existing event, semaphore, mutex, waitable timer, or job object, the function fails, and the GetLastError function returns ERROR_INVALID_HANDLE. This occurs because these objects share the same namespace.
	 *
	 *  	 Terminal Services:   The name can have a &quot;Global\&quot; or &quot;Local\&quot; prefix to explicitly create the object in the global or session namespace. The remainder of the name can contain any character except the backslash character (\). Creating a file-mapping object in the global namespace requires the SeCreateGlobalPrivilege privilege. For more information, see Kernel Object Namespaces.
	 *
	 *  	 Windows XP:   Fast user switching is implemented by using Terminal Services sessions. The first user to log on uses session 0 (zero), the next user to log on uses session 1 (one), and so on. Kernel object names must follow the guidelines that are outlined for Terminal Services so that applications can support multiple users.
	 *
	 *  	 Windows 2000:   If Terminal Services is not running, the &quot;Global\&quot; and &quot;Local\&quot; prefixes are ignored. The remainder of the name can contain any character except the backslash character.
	 *
	 *  	 Windows NT:  The name can contain any character except the backslash character.
	 *
	 *  	 Windows Me/98/95:   The name can contain any character except the backslash character. An empty string (&quot;&quot;) is a valid object name.
	 *
	 *  	 Return Values
	 *
	 *  	 If the function succeeds, the return value is a handle to the file mapping object.
	 *
	 *  	 If the object exists before the function call, the function returns a handle to the existing object (with its current size, not the specified size), and GetLastError returns ERROR_ALREADY_EXISTS.
	 *
	 *  	 If the function fails, the return value is NULL. To get extended error information, call GetLastError.
	 *
	 * </pre>
	 *
	 * @throws IllegalAccessException
	 * @throws NativeException
	 */
	public static HANDLE CreateFileMapping(HANDLE hFile,
			SecurityAttributes lpAttributes, PageAccess flProtect,
			DWORD dwMaximumSizeHigh, DWORD dwMaximumSizeLow, String lpName)
			throws NativeException, IllegalAccessException
	{
		JNative	nCreateFileMapping = new JNative(DLL_NAME, "CreateFileMappingA");
		nCreateFileMapping.setRetVal(Type.INT);
		
		nCreateFileMapping.setParameter(0, hFile.getValue());
		nCreateFileMapping.setParameter(1,
				lpAttributes == null ? NullPointer.NULL : lpAttributes
				.getPointer());
		nCreateFileMapping.setParameter(2, flProtect.getValue());
		nCreateFileMapping.setParameter(3, dwMaximumSizeHigh.getValue());
		nCreateFileMapping.setParameter(4, dwMaximumSizeLow.getValue());
		nCreateFileMapping.setParameter(5, lpName);
		nCreateFileMapping.invoke();
		return new HANDLE(nCreateFileMapping.getRetValAsInt());
		
	}
	
	/**
	 * <pre>
	 *  MapViewOfFileEx
	 *
	 * 	 Maps a view of a file mapping into the address space of a calling process. A caller can optionally specify a suggested memory address for the view.
	 *
	 * 	 To specify the NUMA node for the physical memory, see MapViewOfFileExNuma.
	 *
	 * 	 LPVOID MapViewOfFileEx(
	 * 	 HANDLE hFileMappingObject,
	 * 	 DWORD dwDesiredAccess,
	 * 	 DWORD dwFileOffsetHigh,
	 * 	 DWORD dwFileOffsetLow,
	 * 	 SIZE_T dwNumberOfBytesToMap,
	 * 	 LPVOID lpBaseAddress
	 * 	 );
	 *
	 * 	 Parameters
	 *
	 * 	 hFileMappingObject
	 * 	 [in] A handle to an open handle to a file mapping object. The CreateFileMapping and OpenFileMapping functions return this handle.
	 * 	 dwDesiredAccess
	 * 	 [in] The type of access to a file mapping object, which ensures the page protection of the pages. This parameter can be one of the following values.
	 * 	 Value 	Meaning
	 * 	 FILE_MAP_WRITE 	Read-and-write access. The mapping object must be created with PAGE_READWRITE protection. A read/write view of the file is mapped.
	 * 	 FILE_MAP_READ 	Read-only access. The mapping object must be created with PAGE_READWRITE or PAGE_READONLY protection. A read-only view of the file is mapped.
	 * 	 FILE_MAP_COPY 	Copy-on-write access. The mapping object must be created with PAGE_WRITECOPY protection.
	 *
	 * 	 The system commits physical storage from the paging file at the time MapViewOfFileEx is called. The actual physical storage is not used until a thread in the process writes to an address in the view. At that point, the system copies the original page to a new page backed by the paging file, maps the page into the process address space, and changes the page protection to PAGE_READWRITE. The threads in the process can access only this local copy of the data, not the original data. If this page is trimmed from the working set of the process, it can be written to the paging file storage that is committed when MapViewOfFileEx is called.
	 *
	 * 	 This process only allocates physical memory when a virtual address is actually written to. Changes are never written back to the original file, and are freed when the thread in the process unmaps the view.
	 *
	 * 	 Paging file space for the entire view is committed when copy-on-write access is specified, because the thread in the process can write to every single page. Therefore, enough physical storage space must be obtained at the time MapViewOfFileEx is called.
	 * 	 FILE_MAP_EXECUTE 	Execute access. Code can be run from the mapped memory. The mapping object must be created with PAGE_EXECUTE_READWRITE or PAGE_EXECUTE_READ access.
	 *
	 * 	 Windows Server 2003 and Windows XP:  This feature is not available until Windows XP SP2 and Windows Server 2003 SP1.
	 *
	 * 	 dwFileOffsetHigh
	 * 	 [in] The high-order DWORD of the file offset where the view is to begin.
	 * 	 dwFileOffsetLow
	 * 	 [in] The low-order DWORD of the file offset where the view is to begin. The combination of the high and low offsets must specify an offset within the file that matches the memory allocation granularity of the system, or the function fails. That is, the offset must be a multiple of the allocation granularity. To obtain the memory allocation granularity of the system, use the GetSystemInfo function, which fills in the members of a SYSTEM_INFO structure.
	 * 	 dwNumberOfBytesToMap
	 * 	 [in] The number of bytes of a file mapping to map to a view. If this parameter is 0 (zero), the mapping extends from the specified offset to the end of the section.
	 * 	 lpBaseAddress
	 * 	 [in] A pointer to the memory address in the calling process address space where mapping begins. This must be a multiple of the system's memory allocation granularity, or the function fails. To determine the memory allocation granularity of the system, use the GetSystemInfo function. If there is not enough address space at the specified address, the function fails.
	 *
	 * 	 If lpBaseAddress is NULL, the operating system chooses the mapping address. In this scenario, the function is equivalent to the MapViewOfFile function.
	 *
	 * 	 While it is possible to specify an address that is safe now (not used by the operating system), there is no guarantee that the address will remain safe over time. Therefore, it is better to let the operating system choose the address. In this case, you would not store pointers in the memory mapped file, you would store offsets from the base of the file mapping so that the mapping can be used at any address.
	 *
	 * 	 Return Values
	 *
	 * 	 If the function succeeds, the return value is the starting address of the mapped view.
	 *
	 * 	 If the function fails, the return value is NULL. To get extended error information, call GetLastError.
	 *
	 * </pre>
	 * @throws IllegalAccessException
	 * @throws NativeException
	 */
	public static LONG MapViewOfFileEx(HANDLE hFileMappingObject,
			FileMap dwDesiredAccess, DWORD dwFileOffsetHigh,
			DWORD dwFileOffsetLow, DWORD dwNumberOfBytesToMap, LONG lpBaseAddress) throws IllegalAccessException, NativeException
	{
		JNative	nMapViewOfFileEx = new JNative(DLL_NAME, "MapViewOfFileEx");
		nMapViewOfFileEx.setRetVal(Type.INT);
		
		nMapViewOfFileEx.setParameter(0, hFileMappingObject.getValue());
		nMapViewOfFileEx.setParameter(1, dwDesiredAccess.getValue());
		nMapViewOfFileEx.setParameter(2, dwFileOffsetHigh.getValue());
		nMapViewOfFileEx.setParameter(3, dwFileOffsetLow.getValue());
		nMapViewOfFileEx.setParameter(4, dwNumberOfBytesToMap.getValue());
		nMapViewOfFileEx.setParameter(5, lpBaseAddress.getValue());
		nMapViewOfFileEx.invoke();
		return new HANDLE(nMapViewOfFileEx.getRetValAsInt());
		
	}
	
	/**
	 *
	 * <b>IsBadReadPtr</b>
	 * <pre>
	 * Verifies that the calling process has read access to the specified range of memory.
	 *
	 * BOOL IsBadReadPtr(
	 * public static final int VOID* lp,
	 * UINT_PTR ucb
	 * );
	 *
	 * Parameters
	 *
	 * lp
	 * [in] A pointer to the first byte of the memory block.
	 * ucb
	 * [in] The size of the memory block, in bytes. If this parameter is zero, the return value is zero.
	 *
	 * Return Values
	 *
	 * If the calling process has read access to all bytes in the specified memory range, the return value is zero.
	 *
	 * If the calling process does not have read access to all bytes in the specified memory range, the return value is nonzero.
	 *
	 * If theapplication is compiled as a debugging version, and the process does not have read access to all bytes in the specified memory range, the function causes an assertion and breaks into the debugger. Leaving the debugger, the function continues as usual, and returns a nonzero value. This behavior is by design, as a debugging aid.
	 * </pre>
	 * @throws IllegalAccessException
	 * @throws NativeException */
	public static boolean IsBadReadPtr(Pointer pointer) throws NativeException, IllegalAccessException
	{
		JNative nIsBadReadPtr = new JNative(DLL_NAME, "IsBadReadPtr");
		nIsBadReadPtr.setRetVal(Type.INT);
		nIsBadReadPtr.setParameter(0, pointer.getPointer());
		nIsBadReadPtr.setParameter(1, pointer.getSize());
		nIsBadReadPtr.invoke();
		return (nIsBadReadPtr.getRetValAsInt() == 0);
		
	}
	
	/**
	 * <b>UnmapViewOfFile</b>
	 * <pre>
	 *
	 *
	 * Unmaps a mapped view of a file from the calling process's address space.
	 *
	 * BOOL UnmapViewOfFile(
	 * LPCVOID lpBaseAddress
	 * );
	 *
	 * Parameters
	 *
	 * lpBaseAddress
	 * [in] A pointer to the base address of the mapped view of a file that is to be unmapped. This value must be identical to the value returned by a previous call to the MapViewOfFile or MapViewOfFileEx function.
	 *
	 * Return Values
	 *
	 * If the function succeeds, the return value is nonzero, and all dirty pages within the specified range are written "lazily" to disk.
	 *
	 * If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 * </pre>
	 * @throws IllegalAccessException
	 * @throws NativeException
	 */
	public static boolean UnmapViewOfFile(LONG lpBaseAddress) throws NativeException, IllegalAccessException
	{
		JNative nUnmapViewOfFile = new JNative(DLL_NAME, "UnmapViewOfFile");
		nUnmapViewOfFile.setRetVal(Type.INT);
		nUnmapViewOfFile.setParameter(0, lpBaseAddress.getValue());
		nUnmapViewOfFile.invoke();
		return !"0".equals(nUnmapViewOfFile.getRetVal());
	}
	
	
	
	/**
	 * <pre>
	 * SetConsoleCtrlHandler
	 *
	 * Adds or removes an application-defined HandlerRoutine function from the list of handler functions for the calling process.
	 *
	 * If no handler function is specified, the function sets an inheritable attribute that determines whether the calling process ignores CTRL+C signals.
	 *
	 * BOOL WINAPI SetConsoleCtrlHandler(
	 * PHANDLER_ROUTINE HandlerRoutine,
	 * BOOL Add
	 * );
	 *
	 * Parameters
	 *
	 * HandlerRoutine
	 * [in] Pointer to the application-defined HandlerRoutine function to add or remove. This parameter can be NULL.
	 *
	 * Windows Me/98/95:  This parameter cannot be NULL.
	 *
	 * Add
	 * [in] If this parameter is TRUE, the handler is added; if it is FALSE, the handler is removed.
	 *
	 * If the HandlerRoutine parameter is NULL, a TRUE value causes the calling process to ignore CTRL+C input, and a FALSE value restores normal processing of CTRL+C input. This attribute of ignoring or processing CTRL+C is inherited by child processes.
	 *
	 * Return Value
	 *
	 * If the function succeeds, the return value is nonzero.
	 *
	 * If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 * </pre>
	 * @throws IllegalAccessException
	 * @throws NativeException */
	public static boolean SetConsoleCtrlHandler(Callback callback, boolean Add) throws NativeException, IllegalAccessException
	{
		JNative SetConsoleCtrlHandler = new JNative(DLL_NAME, "SetConsoleCtrlHandler");
		SetConsoleCtrlHandler.setRetVal(Type.INT);
		
		SetConsoleCtrlHandler.setParameter(0, callback.getCallbackAddress());
		SetConsoleCtrlHandler.setParameter(1, Add ? "1" :"0");
		SetConsoleCtrlHandler.invoke();
		
		return SetConsoleCtrlHandler.getRetValAsInt() != 0;
	}
	
	
	/**
	 *
	 * <h3>FindFirstChangeNotification</h3>
	 * <pre>
	 *
	 *
	 * The FindFirstChangeNotification function creates a change notification handle and sets up initial change notification filter conditions. A wait on a notification handle succeeds when a change matching the filter conditions occurs in the specified directory or subtree. The function does not report changes to the specified directory itself.
	 *
	 * This function does not indicate the change that satisfied the wait condition. To retrieve information about the specific change as part of the notification, use the ReadDirectoryChangesW function.
	 *
	 * HANDLE FindFirstChangeNotification(
	 * LPCTSTR lpPathName,
	 * BOOL bWatchSubtree,
	 * DWORD dwNotifyFilter
	 * );
	 *
	 * Parameters
	 *
	 * lpPathName
	 * [in] Pointer to a null-terminated string that specifies the path of the directory to watch.
	 *
	 * In the ANSI version of this function, the name is limited to MAX_PATH characters. To extend this limit to 32,767 wide characters, call the Unicode version of the function and prepend "\\?\" to the path. For more information, see Naming a File.
	 *
	 * Windows Me/98/95:  This string must not exceed MAX_PATH characters.
	 *
	 * bWatchSubtree
	 * [in] Specifies whether the function will monitor the directory or the directory tree. If this parameter is TRUE, the function monitors the directory tree rooted at the specified directory; if it is FALSE, it monitors only the specified directory.
	 * dwNotifyFilter
	 * [in] Filter conditions that satisfy a change notification wait. This parameter can be one or more of the following values.
	 * Value 	Meaning
	 * FILE_NOTIFY_CHANGE_FILE_NAME 	Any file name change in the watched directory or subtree causes a change notification wait operation to return. Changes include renaming, creating, or deleting a file name.
	 * FILE_NOTIFY_CHANGE_DIR_NAME 	Any directory-name change in the watched directory or subtree causes a change notification wait operation to return. Changes include creating or deleting a directory.
	 * FILE_NOTIFY_CHANGE_ATTRIBUTES 	Any attribute change in the watched directory or subtree causes a change notification wait operation to return.
	 * FILE_NOTIFY_CHANGE_SIZE 	Any file-size change in the watched directory or subtree causes a change notification wait operation to return. The operating system detects a change in file size only when the file is written to the disk. For operating systems that use extensive caching, detection occurs only when the cache is sufficiently flushed.
	 * FILE_NOTIFY_CHANGE_LAST_WRITE 	Any change to the last write-time of files in the watched directory or subtree causes a change notification wait operation to return. The operating system detects a change to the last write-time only when the file is written to the disk. For operating systems that use extensive caching, detection occurs only when the cache is sufficiently flushed.
	 * FILE_NOTIFY_CHANGE_SECURITY 	Any security-descriptor change in the watched directory or subtree causes a change notification wait operation to return.
	 *
	 * Return Value
	 *
	 * If the function succeeds, the return value is a handle to a find change notification object.
	 *
	 * If the function fails, the return value is INVALID_HANDLE_VALUE. To get extended error information, call GetLastError.
	 * </pre>
	 * @throws IllegalAccessException
	 * @throws NativeException
	 */
	public static final HANDLE FindFirstChangeNotification(String lpPathName, boolean bWatchSubtree,  DWORD dwNotifyFilter) throws NativeException, IllegalAccessException
	{
		JNative FindFirstChangeNotification = new JNative(DLL_NAME, "FindFirstChangeNotificationA");
		FindFirstChangeNotification.setRetVal(Type.INT);
		FindFirstChangeNotification.setParameter(0, lpPathName);
		FindFirstChangeNotification.setParameter(1, bWatchSubtree ? -1 : 0);
		FindFirstChangeNotification.setParameter(2, dwNotifyFilter.getValue());
		FindFirstChangeNotification.invoke();
		return new HANDLE(FindFirstChangeNotification.getRetValAsInt());
	}
	
	/**
	 * <h3>FindNextChangeNotification</h3>
	 * <pre>
	 *
	 *
	 * The FindNextChangeNotification function requests that the operating system signal a change notification handle the next time it detects an appropriate change.
	 *
	 * BOOL FindNextChangeNotification(
	 * HANDLE hChangeHandle
	 * );
	 *
	 * Parameters
	 *
	 * hChangeHandle
	 * [in] Handle to a change notification handle created by the FindFirstChangeNotification function.
	 *
	 * Return Value
	 *
	 * If the function succeeds, the return value is nonzero.
	 *
	 * If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 * Remarks
	 *
	 * After the FindNextChangeNotification function returns successfully, the application can wait for notification that a change has occurred by using the wait functions.
	 *
	 * If a change occurs after a call to FindFirstChangeNotification but before a call to FindNextChangeNotification, the operating system records the change. When FindNextChangeNotification is executed, the recorded change immediately satisfies a wait for the change notification.
	 *
	 * FindNextChangeNotification should not be used more than once on the same handle without using one of the wait functions. An application may miss a change notification if it uses FindNextChangeNotification when there is a change request outstanding.
	 *
	 * When hChangeHandle is no longer needed, close it by using the FindCloseChangeNotification function.
	 * </pre>
	 * @return true if the function call succeed
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	public static final boolean FindNextChangeNotification(HANDLE hChangeHandle) throws NativeException, IllegalAccessException
	{
		JNative FindNextChangeNotification = new JNative(DLL_NAME, "FindNextChangeNotification");
		FindNextChangeNotification.setRetVal(Type.INT);
		FindNextChangeNotification.setParameter(0, hChangeHandle.getValue());
		FindNextChangeNotification.invoke();
		return FindNextChangeNotification.getRetValAsInt() == 0;
		
	}
	
	/**
	 * <h3>FindCloseChangeNotification</h3>
	 * <pre>
	 *
	 * The FindCloseChangeNotification function stops change notification handle monitoring.
	 *
	 * BOOL FindCloseChangeNotification(
	 * HANDLE hChangeHandle
	 * );
	 *
	 * Parameters
	 *
	 * hChangeHandle
	 * [in] Handle to a change notification handle created by the FindFirstChangeNotification function.
	 
	 *
	 * Return Values
	 *
	 * If the function succeeds, the return value is nonzero.
	 *
	 * If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 * </pre>
	 * @return true if the function call succeed
	 * @throws IllegalAccessException
	 * @throws NativeException
	 */
	
	public static final boolean FindCloseChangeNotification(HANDLE hChangeHandle) throws NativeException, IllegalAccessException
	{
		JNative FindCloseChangeNotification = new JNative(DLL_NAME, "FindCloseChangeNotification");
		FindCloseChangeNotification.setRetVal(Type.INT);
		FindCloseChangeNotification.setParameter(0, hChangeHandle.getValue());
		FindCloseChangeNotification.invoke();
		return FindCloseChangeNotification.getRetValAsInt() == 0;
	}
	
	/**
	 * <h3>WaitForSingleObjectEx</h3>
	 * <pre>
	 *
	 * Waits until the specified object is in the signaled state, an I/O completion routine or asynchronous procedure call (APC) is queued to the thread, or the time-out interval elapses.
	 *
	 * To wait for multiple objects, use the WaitForMultipleObjectsEx.
	 *
	 * DWORD WINAPI WaitForSingleObjectEx(
	 * HANDLE hHandle,
	 * DWORD dwMilliseconds,
	 * BOOL bAlertable
	 * );
	 *
	 * Parameters
	 *
	 * hHandle
	 * [in] A handle to the object. For a list of the object types whose handles can be specified, see the following Remarks section.
	 *
	 * If this handle is closed while the wait is still pending, the function's behavior is undefined.
	 *
	 * The handle must have the SYNCHRONIZE access right. For more information, see Standard Access Rights.
	 * dwMilliseconds
	 * [in] The time-out interval, in milliseconds. The function returns if the interval elapses, even if the object's state is nonsignaled and no completion routines or APCs are queued. If dwMilliseconds is zero, the function tests the object's state and checks for queued completion routines or APCs and then returns immediately. If dwMilliseconds is INFINITE, the function's time-out interval never elapses.
	 * bAlertable
	 * [in] If this parameter is TRUE and the thread is in the waiting state, the function returns when the system queues an I/O completion routine or APC, and the thread runs the routine or function. Otherwise, the function does not return, and the completion routine or APC function is not executed.
	 *
	 * A completion routine is queued when the ReadFileEx or WriteFileEx function in which it was specified has completed. The wait function returns and the completion routine is called only if bAlertable is TRUE, and the calling thread is the thread that initiated the read or write operation. An APC is queued when you call QueueUserAPC.
	 *
	 * Return Value
	 *
	 * If the function succeeds, the return value indicates the event that caused the function to return. It can be one of the following values.
	 * Return code/value 	Description
	 * WAIT_ABANDONED
	 * 0x00000080L 	The specified object is a mutex object that was not released by the thread that owned the mutex object before the owning thread terminated. Ownership of the mutex object is granted to the calling thread, and the mutex is set to nonsignaled.
	 *
	 * If the mutex was protecting persistent state information, you should check it for consistency.
	 * WAIT_IO_COMPLETION
	 * 0x000000C0L 	The wait was ended by one or more user-mode asynchronous procedure calls (APC) queued to the thread.
	 * WAIT_OBJECT_0
	 * 0x00000000L 	The state of the specified object is signaled.
	 * WAIT_TIMEOUT
	 * 0x00000102L 	The time-out interval elapsed, and the object's state is nonsignaled.
	 *
	 * If the function fails, the return value is WAIT_FAILED ((DWORD)0xFFFFFFFF). To get extended error information, call GetLastError.
	 *
	 * </pre>
	 * <h4>Remarks</h4>
	 * <pre>
	 * The WaitForSingleObjectEx function determines whether the wait criteria have been met. If the criteria have not been met, the calling thread enters the wait state until the conditions of the wait criteria have been met or the time-out interval elapses.
	 *
	 * The function modifies the state of some types of synchronization objects. Modification occurs only for the object whose signaled state caused the function to return. For example, the count of a semaphore object is decreased by one.
	 *
	 * The WaitForSingleObjectEx function can wait for the following objects:
	 *
	 * Change notification
	 * Console input
	 * Event
	 * Memory resource notification
	 * Mutex
	 * Process
	 * Semaphore
	 * Thread
	 * Waitable timer
	 *
	 * Use caution when calling the wait functions and code that directly or indirectly creates windows. If a thread creates any windows, it must process messages. Message broadcasts are sent to all windows in the system. A thread that uses a wait function with no time-out interval may cause the system to become deadlocked. Two examples of code that indirectly creates windows are DDE and the CoInitialize function. Therefore, if you have a thread that creates windows, use MsgWaitForMultipleObjects or MsgWaitForMultipleObjectsEx, rather than WaitForSingleObjectEx.
	 * </pre>
	 * @throws IllegalAccessException
	 * @throws NativeException
	 */
	public static final int WaitForSingleObjectEx(HANDLE hHandle,  DWORD dwMilliseconds,  boolean bAlertable) throws NativeException, IllegalAccessException
	{
		if(WaitForSingleObjectEx == null)
		{
			WaitForSingleObjectEx = new JNative(DLL_NAME, "WaitForSingleObjectEx");
			WaitForSingleObjectEx.setRetVal(Type.INT);
		}
		WaitForSingleObjectEx.setParameter(0, hHandle.getValue());
		WaitForSingleObjectEx.setParameter(1, dwMilliseconds.getValue());
		WaitForSingleObjectEx.setParameter(2, bAlertable ? -1 : 0);
		WaitForSingleObjectEx.invoke();
		return WaitForSingleObjectEx.getRetValAsInt();
	}
	private static JNative WaitForSingleObjectEx = null;
	
	
	/**
	 * <h3>WaitForMultipleObjectsEx</h3>
	 <pre>
	 
	 Waits until one or all of the specified objects are in the signaled state, an I/O completion routine or asynchronous procedure call (APC) is queued to the thread, or the time-out interval elapses.
	 
	 DWORD WINAPI WaitForMultipleObjectsEx(
	 DWORD nCount,
	 public static final int HANDLE* lpHandles,
	 BOOL bWaitAll,
	 DWORD dwMilliseconds,
	 BOOL bAlertable
	 );
	 
	 Parameters
	 
	 nCount
	 [in] The number of object handles to wait for in the array pointed to by lpHandles. The maximum number of object handles is MAXIMUM_WAIT_OBJECTS.
	 lpHandles
	 [in] An array of object handles. For a list of the object types whose handles can be specified, see the following Remarks section. The array can contain handles of objects of different types. It may not contain the multiple copies of the same handle.
	 
	 If one of these handles is closed while the wait is still pending, the function's behavior is undefined.
	 
	 The handles must have the SYNCHRONIZE access right. For more information, see Standard Access Rights.
	 
	 Windows Me/98/95:  No handle may be a duplicate of another handle created using DuplicateHandle.
	 
	 bWaitAll
	 [in] If this parameter is TRUE, the function returns when the state of all objects in the lpHandles array is set to signaled. If FALSE, the function returns when the state of any one of the objects is set to signaled. In the latter case, the return value indicates the object whose state caused the function to return.
	 dwMilliseconds
	 [in] The time-out interval, in milliseconds. The function returns if the interval elapses, even if the criteria specified by the bWaitAll parameter are not met and no completion routines or APCs are queued. If dwMilliseconds is zero, the function tests the states of the specified objects and checks for queued completion routines or APCs and then returns immediately. If dwMilliseconds is INFINITE, the function's time-out interval never elapses.
	 bAlertable
	 [in] If this parameter is TRUE, the function returns when the system queues an I/O completion routine or APC, and the thread runs the routine or function. If this parameter is FALSE, the function does not return and the completion routine or APC function is not executed.
	 
	 A completion routine is queued when the ReadFileEx or WriteFileEx function in which it was specified has completed. The wait function returns and the completion routine is called only if bAlertable is TRUE and the calling thread is the thread that initiated the read or write operation. An APC is queued when you call QueueUserAPC.
	 
	 Return Value
	 
	 If the function succeeds, the return value indicates the event that caused the function to return. It can be one of the following values. (Note that WAIT_OBJECT_0 is defined as 0 and WAIT_ABANDONED_0 is defined as 0x00000080L.)
	 Return code/value 	Description
	 
	 WAIT_OBJECT_0 to (WAIT_OBJECT_0 + nCountâ€“ 1) 	If bWaitAll is TRUE, the return value indicates that the state of all specified objects is signaled.
	 
	 If bWaitAll is FALSE, the return value minus WAIT_OBJECT_0 indicates the lpHandles array index of the object that satisfied the wait. If more than one object became signaled during the call, this is the array index of the signaled object with the smallest index value of all the signaled objects.
	 
	 WAIT_ABANDONED_0 to (WAIT_ABANDONED_0 + nCountâ€“ 1) 	If bWaitAll is TRUE, the return value indicates that the state of all specified objects is signaled, and at least one of the objects is an abandoned mutex object.
	 
	 If bWaitAll is FALSE, the return value minus WAIT_ABANDONED_0 indicates the lpHandles array index of an abandoned mutex object that satisfied the wait. Ownership of the mutex object is granted to the calling thread, and the mutex is set to nonsignaled.
	 
	 If a mutex was protecting persistent state information, you should check it for consistency.
	 WAIT_IO_COMPLETION
	 0x000000C0L 	The wait was ended by one or more user-mode asynchronous procedure calls (APC) queued to the thread.
	 WAIT_TIMEOUT
	 0x00000102L 	The time-out interval elapsed, the conditions specified by the bWaitAll parameter were not satisfied, and no completion routines are queued.
	 
	 If the function fails, the return value is WAIT_FAILED ((DWORD)0xFFFFFFFF). To get extended error information, call GetLastError.
	 </pre>
	 
	 <h4>Remarks</h4>
	 <pre>
	 
	 The WaitForMultipleObjectsEx function determines whether the wait criteria have been met. If the criteria have not been met, the calling thread enters the wait state until the conditions of the wait criteria have been met or the time-out interval elapses.
	 
	 When bWaitAll is TRUE, the function's wait operation is completed only when the states of all objects have been set to signaled. The function does not modify the states of the specified objects until the states of all objects have been set to signaled. For example, a mutex can be signaled, but the thread does not get ownership until the states of the other objects are also set to signaled. In the meantime, some other thread may get ownership of the mutex, thereby setting its state to nonsignaled.
	 
	 When bWaitAll is FALSE, this function checks the handles in the array in order starting with index 0, until one of the objects is signaled. If multiple objects become signaled, the function returns the index of the first handle in the array whose object was signaled.
	 
	 The function modifies the state of some types of synchronization objects. Modification occurs only for the object or objects whose signaled state caused the function to return. For example, the count of a semaphore object is decreased by one. For more information, see the documentation for the individual synchronization objects.
	 
	 To wait on more than MAXIMUM_WAIT_OBJECTS handles, use one of the following methods:
	 
	 * Create a thread to wait on MAXIMUM_WAIT_OBJECTS handles, then wait on that thread plus the other handles. Use this technique to break the handles into groups of MAXIMUM_WAIT_OBJECTS.
	 * Call RegisterWaitForSingleObject to wait on each handle. A wait thread from the thread pool waits on MAXIMUM_WAIT_OBJECTS registered objects and assigns a worker thread after the object is signaled or the time-out interval expires.
	 
	 The WaitForMultipleObjectsEx function can specify handles of any of the following object types in the lpHandles array:
	 
	 * Change notification
	 * Console input
	 * Event
	 * Memory resource notification
	 * Mutex
	 * Process
	 * Semaphore
	 * Thread
	 * Waitable timer
	 
	 Use caution when calling the wait functions and code that directly or indirectly creates windows. If a thread creates any windows, it must process messages. Message broadcasts are sent to all windows in the system. A thread that uses a wait function with no time-out interval may cause the system to become deadlocked. Two examples of code that indirectly creates windows are DDE and the CoInitialize function. Therefore, if you have a thread that creates windows, use MsgWaitForMultipleObjects or MsgWaitForMultipleObjectsEx, rather than WaitForMultipleObjectsEx.
	 </pre>
	 * @throws IllegalAccessException
	 * @throws NativeException
	 */
	public static final int WaitForMultipleObjectsEx(DWORD nCount, HANDLE[] lpHandles, boolean bWaitAll, DWORD dwMilliseconds, boolean bAlertable) throws NativeException, IllegalAccessException
	{
		if(WaitForMultipleObjectsEx == null)
		{
			WaitForMultipleObjectsEx = new JNative(DLL_NAME, "WaitForMultipleObjectsEx");
			WaitForMultipleObjectsEx.setRetVal(Type.INT);
		}
		Pointer p = new Pointer(MemoryBlockFactory.createMemoryBlock(4*lpHandles.length));
		for(int i = 0; i<lpHandles.length; i++)
		{
			p.setIntAt(i*4, lpHandles[i].getValue());
		}
		
		WaitForMultipleObjectsEx.setParameter(0, nCount.getValue());
		WaitForMultipleObjectsEx.setParameter(1, p);
		WaitForMultipleObjectsEx.setParameter(2, bWaitAll ? -1 : 0);
		WaitForMultipleObjectsEx.setParameter(3, dwMilliseconds.getValue());
		WaitForMultipleObjectsEx.setParameter(4, bAlertable ? -1 : 0);
		WaitForMultipleObjectsEx.invoke();
		p.dispose();
		return WaitForMultipleObjectsEx.getRetValAsInt();
	}
	private static JNative WaitForMultipleObjectsEx = null;
	
	/**
	 * SystemTime
	 *
	 
	 * <pre>
	 * 	typedef struct _SYSTEMTIME {
	 * 		  WORD wYear;
	 * 		  WORD wMonth;
	 * 		  WORD wDayOfWeek;
	 * 		  WORD wDay;
	 * 		  WORD wHour;
	 * 		  WORD wMinute;
	 * 		  WORD wSecond;
	 * 		  WORD wMilliseconds;
	 * 		} SYSTEMTIME,
	 * </pre>
	 */
	public static class SystemTime extends AbstractBasicData<SystemTime>
	{
		public short wYear;
		public short wMonth;
		public short wDayOfWeek;
		public short wDay;
		public short wHour;
		public short wMinute;
		public short wSecond;
		public short wMilliseconds;
		
		public Pointer createPointer() throws NativeException
		{
			pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
			return pointer;
		}
		public int getSizeOf()
		{
			return 8*2; //8 WORDS of 2 bytes
		}
		public SystemTime getValueFromPointer() throws NativeException
		{
			wYear          = getNextShort();
			wMonth         = getNextShort();
			wDayOfWeek     = getNextShort();
			wDay           = getNextShort();
			wHour          = getNextShort();
			wMinute        = getNextShort();
			wSecond        = getNextShort();
			wMilliseconds  = getNextShort();
			return this;
		}
		
		public SystemTime() throws NativeException
		{
			super(null);
			createPointer();
			mValue = this;
		}
		
		@Override
		public String toString()
		{
			return wYear + "/" + wMonth+"/"+wDay+" at "+wHour+":"+wMinute+":"+wSecond+":"+wMilliseconds;
		}
	}
	public static SystemTime GetSystemTime() throws NativeException, IllegalAccessException
	{
		JNative nGetSystemTime = new JNative(DLL_NAME, "GetSystemTime");
		SystemTime systemTime = new SystemTime();
		nGetSystemTime.setParameter(0, systemTime.getPointer());
		nGetSystemTime.invoke();
		return systemTime.getValueFromPointer();
	}
	public static void main(String[] args) throws NativeException, IllegalAccessException
	{
		JNative.getLogger().log(GetSystemTime());
		Pointer p = new Pointer(MemoryBlockFactory.createMemoryBlock(2));
		p.setStringAt(0, "A");
		p.setByteAt(1, (byte)'B');
		
	}
	
	
	/**
	 * <div class="title" xmlns:msxsl="urn:schemas-microsoft-com:xslt">MultiByteToWideChar</div><!--Content type: PSDK_3. Transform: psdk2mtps.xslt.-->  <a name="_win32_multibytetowidechar"><!----></a> <p>Maps a character string to a wide character (Unicode UTF-16) string. The character string mapped by this function is not necessarily from a multibyte character set. </p> <p><b>Note:</b> The ANSI code pages can be different on different computers, or can be changed for a single computer, leading to data corruption. For the most consistent results, applications should use Unicode, such as UTF-8 (code page 65001) or UTF-16, instead of a specific code page, unless legacy standards or data formats prevent the use of Unicode. If use of Unicode is not possible, applications should tag the data stream with the appropriate encoding name when protocols allow it. HTML, XML, and HTTP files allow tagging, but text files do not.</p> <div class="" id="ctl00_rs1_mainContentContainer_ctl01_"><table><tbody><tr><td style="border: 0px none ; margin: 0px; padding: 0px;"><div class="CodeSnippetTitleBar"><div class="CodeDisplayLanguage"></div></div></td></tr><tr><td style="border: 0px none ; margin: 0px; padding: 0px;"><pre class="libCScode" id="ctl00_rs1_mainContentContainer_ctl01" space="preserve"><b>int MultiByteToWideChar(</b>
	 <b>  UINT</b><i> CodePage</i>,
	 
	 <b>  DWORD</b><i> dwFlags</i>,
	 <b>  LPCSTR</b><i> lpMultiByteStr</i>,
	 <b>  int</b><i> cbMultiByte</i>,
	 <b>  LPWSTR</b><i> lpWideCharStr</i>,
	 
	 <b>  int</b><i> cchWideChar</i>
	 <b>);</b></pre></td></tr></tbody></table></div> <h4>Parameters</h4> <dl> <dt><i>CodePage</i></dt> <dd>[in] Code page to use in performing the conversion. This parameter can be set to the value of any code page that is installed or available in the operating system. For a list of code pages, see <a id="ctl00_rs1_mainContentContainer_ctl02" onclick="javascript:Track('ctl00_rs1_mainContentContainer_ctl00|ctl00_rs1_mainContentContainer_ctl02',this);" href="http://msdn.microsoft.com/en-us/library/ms776446%28VS.85%29.aspx">Code Page Identifiers</a>. Your application can also specify one of the values shown in the following table.
	 <table> <tbody><tr valign="top"> <th width="30%" align="left">Value</th> <th width="70%" align="left">Meaning</th> </tr> <tr valign="top"> <td width="30%">CP_ACP</td> <td width="70%">The current system Windows ANSI code page. This value can be different on different computers, even on the same network. It can be changed on the same computer, leading to stored data becoming irrecoverably corrupted. This value is only intended for temporary use and permanent storage should be done using UTF-16 or UTF-8 if possible.</td> </tr> <tr valign="top"> <td width="30%">CP_MACCP</td> <td width="70%">The current system Macintosh code page. This value can be different on different computers, even on the same network. It can be changed on the same computer, leading to stored data becoming irrecoverably corrupted. This value is only intended for temporary use and permanent storage should be done using UTF-16 or UTF-8 if possible.
	 
	 <p><b>Note:</b> This value is used primarily in legacy code and should not generally be needed since modern Macintosh computers use Unicode for encoding.</p></td> </tr> <tr valign="top"> <td width="30%">CP_OEMCP</td> <td width="70%">The current system OEM code page. This value can be different on different computers, even on the same network. It can be changed on the same computer, leading to stored data becoming irrecoverably corrupted. This value is only intended for temporary use and permanent storage should be done using UTF-16 or UTF-8 if possible.</td> </tr> <tr valign="top"> <td width="30%">CP_SYMBOL</td> <td width="70%"><b>Windows 2000 and later:</b> Symbol code page (42).</td> </tr> <tr valign="top"> <td width="30%">CP_THREAD_ACP</td> <td width="70%"><b>Windows 2000 and later:</b> The Windows ANSI code page for the current thread. This value can be different on different computers, even on the same network. It can be changed on the same computer, leading to stored data becoming irrecoverably corrupted. This value is only intended for temporary use and permanent storage should be done using UTF-16 or UTF-8 if possible.</td> </tr> <tr valign="top"> <td width="30%">CP_UTF7</td> <td width="70%"><b>Windows 98/Me, Windows NT 4.0 and later:</b> UTF-7. Use this value only when forced by a 7-bit transport mechanism. Use of UTF-8 is preferred.</td> </tr> <tr valign="top"> <td width="30%">CP_UTF8</td> <td width="70%"><b>Windows 98/Me, Windows NT 4.0 and later:</b> UTF-8.</td> </tr> </tbody></table><br> <p><b>Note:</b> On Windows 95, the <a id="ctl00_rs1_mainContentContainer_ctl03" onclick="javascript:Track('ctl00_rs1_mainContentContainer_ctl00|ctl00_rs1_mainContentContainer_ctl03',this);" href="http://msdn.microsoft.com/en-us/library/ms812865.aspx">Microsoft Layer for Unicode</a> enables <b>MultiByteToWideChar</b> to support CP_UTF7 and CP_UTF8.
	 
	 </p></dd> <dt><i>dwFlags</i></dt> <dd>[in] Flags indicating the conversion type. The application can specify a combination of the following values, with MB_PRECOMPOSED being the default. MB_PRECOMPOSED and MB_COMPOSITE are mutually exclusive.  MB_USEGLYPHCHARS and MB_ERR_INVALID_CHARS can be set regardless of the state of the other flags.
	 
	 <table> <tbody><tr valign="top"> <th width="42%" align="left">Value</th> <th width="58%" align="left">Meaning</th> </tr> <tr valign="top"> <td width="42%">MB_PRECOMPOSED</td> <td width="58%">Default; do not use with MB_COMPOSITE. Always use precomposed characters, that is, characters having a single character value for a base or nonspacing character combination. For example, in the character <i>è</i>, the <i>e</i> is the base character and the accent grave mark is the nonspacing character. If a single Unicode code point is defined for a character, the application should use it instead of a separate base character and a nonspacing character. For example, Ä is represented by the single Unicode code point LATIN CAPITAL LETTER A WITH DIAERESIS (U+00C4).
	 
	 </td> </tr> <tr valign="top"> <td width="42%">MB_COMPOSITE</td> <td width="58%">Always use decomposed characters, that is, characters in which a base character and one or more nonspacing characters each have distinct code point values. For example, Ä is represented by A + ¨: LATIN CAPITAL LETTER A (U+0041) + COMBINING DIAERESIS (U+0308). Note that this flag cannot be used with MB_PRECOMPOSED.
	 
	 
	 </td> </tr> <tr valign="top"> <td width="42%">MB_ERR_INVALID_CHARS</td> <td width="58%"><b>Windows 2000 Service Pack 4, Windows XP and later:</b> Fail if an invalid input character is encountered. A call to <b>GetLastError</b> returns ERROR_NO_UNICODE_TRANSLATION.
	 
	 </td> </tr> <tr valign="top"> <td width="42%">MB_USEGLYPHCHARS</td> <td width="58%">Use glyph characters instead of control characters.</td> </tr> </tbody></table><br> <p>For the code pages listed below, <i>dwFlags</i> must be set to 0. Otherwise, the function fails with ERROR_INVALID_FLAGS.
	 
	 </p><table> <tbody><tr valign="top"> <td width="30%">50220
	 <p>50221</p> <p>50222</p> <p>50225</p> </td> <td width="35%">50227
	 
	 <p>50229</p> <p>52936</p> <p>54936</p> </td> <td width="35%">57002 through 57011
	 <p>65000 (UTF-7)</p> <p>42 (Symbol)</p> </td> </tr> </tbody></table><br> <p><b>Note:</b> For UTF-8, <i>dwFlags</i> must be set to either 0 or MB_ERR_INVALID_CHARS. Otherwise, the function fails with ERROR_INVALID_FLAGS.
	 
	 
	 </p></dd><dt><i>lpMultiByteStr</i></dt> <dd>[in] Pointer to the character string to convert. </dd> <dt><i>cbMultiByte</i></dt> <dd>[in] Size, in bytes, of the string indicated by the <i>lpMultiByteStr</i> parameter. Alternatively, this parameter can be set to -1 if the string is null-terminated. Note that, if <i>cbMultiByte</i> is 0, the function fails.
	 
	 <p>If this parameter is -1, the function processes the entire input string, including the null terminator. Therefore, the resulting wide character string has a null terminator, and the length returned by the function includes the terminating null character.</p> <p>If this parameter is set to a positive integer, the function processes exactly the specified number of bytes. If the provided size does not include a null terminator, the resulting wide character string is not null-terminated, and the returned length does not include the terminating null character.</p> </dd> <dt><i>lpWideCharStr</i></dt> <dd>[out] Pointer to a buffer that receives the converted string. </dd> <dt><i>cchWideChar</i></dt> <dd>[in] Size, in WCHAR values, of the buffer indicated by <i>lpWideCharStr</i>. If this value is 0, the function returns the required buffer size, in WCHAR values, including any terminating null character, and makes no use of the <i>lpWideCharStr</i> buffer.
	 
	 </dd> </dl> <h4>Return Values</h4> <p>Returns the number of WCHAR values written to the buffer indicated by <i>lpWideCharStr</i> if successful. If the function succeeds and <i>cchWideChar</i> is 0, the return value is the required size for the buffer indicated by <i>lpWideCharStr</i>. </p> <p>The function returns 0 if it does not succeed. To get extended error information, the application can call <a id="ctl00_rs1_mainContentContainer_ctl04" onclick="javascript:Track('ctl00_rs1_mainContentContainer_ctl00|ctl00_rs1_mainContentContainer_ctl04',this);" href="http://msdn.microsoft.com/en-us/library/cc428944.aspx"><b>GetLastError</b></a>. <b>GetLastError</b> can return one of the following error codes:</p> <ul> <li>ERROR_INSUFFICIENT_BUFFER</li> <li>ERROR_INVALID_FLAGS</li> <li>ERROR_INVALID_PARAMETER</li> <li>ERROR_NO_UNICODE_TRANSLATION</li> </ul> <p>ERROR_NO_UNICODE_TRANSLATION is returned if the input string is a UTF-8 string that contains invalid characters and the MB_ERR_INVALID_CHARS flag is set. </p>
	 
	 * @param CodePage
	 * @param dwFlags
	 * @param lpMultiByteStr
	 * @param cbMultiByte
	 * @param cchWideChar
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	public static Pointer MultiByteToWideChar(
			CodePage CodePage,
			DWORD dwFlags,
			String lpMultiByteStr,
			int cbMultiByte,
			int cchWideChar
			) throws NativeException, IllegalAccessException
	{
		
		ByRef<Pointer> lpWideCharStr = new ByRef<Pointer>(new Pointer(MemoryBlockFactory.createMemoryBlock(cbMultiByte*2+1)));
		ByRef<Integer> getLastErrorValue = new ByRef<Integer>(-1);
		
		int ret = Basic.MultiByteToWideChar(CodePage.getValue(), dwFlags.getValue(), lpMultiByteStr, cbMultiByte, lpWideCharStr, cchWideChar, getLastErrorValue);
		if(ret == 0) {
			throw new IllegalAccessException("MultiByteToWideChar failed cause " + getLastErrorValue.getValue());
		} else {
			if(cchWideChar == 0) {
				return new LONG(ret).getPointer();
			} else {
				return lpWideCharStr.getValue();
			}
		}
	}
	public static String WideCharToMultiByte(
			CodePage CodePage,
			int dwFlags,
			Pointer lpWideCharStr,
			int cchWideChar,
			int cbMultiByte
	) throws NativeException, IllegalAccessException
	{
		JNative WideCharToMultiByte = new JNative(DLL_NAME, "WideCharToMultiByte");
		WideCharToMultiByte.setRetVal(Type.INT);
		int pos = 0;
		Pointer lpMultiByteStr = new Pointer(MemoryBlockFactory.createMemoryBlock(cbMultiByte == 0 ? 64 : cbMultiByte + 1));
		WideCharToMultiByte.setParameter(pos++, CodePage.getValue());
		WideCharToMultiByte.setParameter(pos++, dwFlags);
		WideCharToMultiByte.setParameter(pos++, lpWideCharStr);
		WideCharToMultiByte.setParameter(pos++, cchWideChar);
		WideCharToMultiByte.setParameter(pos++, lpMultiByteStr);
		WideCharToMultiByte.setParameter(pos++, cbMultiByte);
		WideCharToMultiByte.setParameter(pos++, 0);
		WideCharToMultiByte.setParameter(pos++, 0);
		WideCharToMultiByte.invoke();
		if(cbMultiByte == 0)
		{
			return WideCharToMultiByte.getRetVal();
		}
		if(WideCharToMultiByte.getRetValAsInt() == 0)
		{
			JNative.getLogger().log(SEVERITY.WARN, String.format("WideCharToMultiByte failed cause : %x", GetLastError()));
			return null;
		}
		else
		{
			return lpMultiByteStr.getAsString();
		}
	}
	
	/* constants */
	
	// The following table lists the standard access rights used by all objects.
	public static final int DELETE = 0x00010000; // Required to delete the
	
	// object.
	
	public static final int READ_CONTROL = 0x00020000; // Required to read
	
	// information in the
	// security descriptor
	// for the object, not
	// including the
	// information in the
	// SACL. To read or
	// write the SACL, you
	// must request the
	// ACCESS_SYSTEM_SECURITY
	// access right. For
	// more information, see
	// SACL Access Right.
	
	public static final int SYNCHRONIZE = 0x00100000; // The right to use the
	
	// object for
	// synchronization. This
	// enables a thread to
	// wait until the object
	// is in the signaled
	// state.
	
	public static final int WRITE_DAC = 0x00040000; // Required to modify the
	
	// DACL in the security
	// descriptor for the
	// object.
	
	public static final int WRITE_OWNER = 0x00080000; // Required to change
	
	// the owner in the
	// security descriptor
	// for the object.
	
	// The following table lists the process-specific access rights.
	public static final int PROCESS_ALL_ACCESS = 0x1F0FFF; // All possible
	
	// access rights for
	// a process object.
	
	public static final int PROCESS_CREATE_PROCESS = 0x0080; // Required to
	
	// create a
	// process.
	
	public static final int PROCESS_CREATE_THREAD = 0x0002; // Required to
	
	// create a thread.
	
	public static final int PROCESS_DUP_HANDLE = 0x0040; // Required to
	
	// duplicate a
	// handle using
	// DuplicateHandle.
	
	public static final int PROCESS_QUERY_INFORMATION = 0x0400; // Required to
	
	// retrieve
	// certain
	// information
	// about a
	// process, such
	// as its token,
	// exit code,
	// and priority
	// class = see
	// OpenProcessToken,
	// GetExitCodeProcess,
	// GetPriorityClass,
	// and
	// IsProcessInJob).
	
	public static final int PROCESS_QUERY_LIMITED_INFORMATION = 0x1000; // Required
	
	// to
	// retrieve
	// certain
	// information
	// about
	// a
	// process
	// = see
	// QueryFullProcessImageName).
	
	public static final int PROCESS_SET_QUOTA = 0x0100; // Required to set
	
	// memory limits using
	// SetProcessWorkingSetSize.
	
	public static final int PROCESS_SET_INFORMATION = 0x0200; // Required to
	
	// set certain
	// information
	// about a
	// process, such
	// as its
	// priority
	// class = see
	// SetPriorityClass).
	
	public static final int PROCESS_SUSPEND_RESUME = 0x0800; // Required to
	
	// suspend or
	// resume a
	// process.
	
	public static final int PROCESS_TERMINATE = 0x0001; // Required to terminate
	
	// a process using
	// TerminateProcess.
	
	public static final int PROCESS_VM_OPERATION = 0x0008; // Required to
	
	// perform an
	// operation on the
	// address space of
	// a process = see
	// VirtualProtectEx
	// and
	// WriteProcessMemory).
	
	public static final int PROCESS_VM_READ = 0x0010; // Required to read
	
	// memory in a process
	// using
	// ReadProcessMemory.
	
	public static final int PROCESS_VM_WRITE = 0x0020; // Required to write to
	
	// memory in a process
	// using
	// WriteProcessMemory.
	// public static final int SYNCHRONIZE = 0x00100000; // Required to wait for
	// the process to terminate using the wait functions.
	
	public static enum FileMap
	{
		FILE_MAP_ALL_ACCESS(0xf001f), FILE_MAP_READ(4), FILE_MAP_WRITE(2), FILE_MAP_COPY(
				1), ;
		int value;
		
		private FileMap(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
	}
	
	/**
	 * The specific rights (bits 0 to 15). Depend on the type of the object
	 * being secured by the ACE. Specific rights for files and directories are
	 * as follows:
	 */
	public static enum AccessMask
	{
		/** Right to read data from the file. (FILE) */
		FILE_READ_DATA(0x00000001),
		/** Right to list contents of a directory. (DIRECTORY) */
		FILE_LIST_DIRECTORY(0x00000001),
		
		/** Right to write data to the file. (FILE) */
		FILE_WRITE_DATA(0x00000002),
		/** Right to create a file in the directory. (DIRECTORY) */
		FILE_ADD_FILE(0x00000002),
		
		/** Right to append data to the file. (FILE) */
		FILE_APPEND_DATA(0x00000004),
		/** Right to create a subdirectory. (DIRECTORY) */
		FILE_ADD_SUBDIRECTORY(0x00000004),
		
		/** Right to read extended attributes. (FILE/DIRECTORY) */
		FILE_READ_EA(0x00000008),
		
		/** Right to write extended attributes. (FILE/DIRECTORY) */
		FILE_WRITE_EA(0x00000010),
		
		/** Right to execute a file. (FILE) */
		FILE_EXECUTE(0x00000020),
		/** Right to traverse the directory. (DIRECTORY) */
		FILE_TRAVERSE(0x00000020),
		
		/**
		 * Right to delete a directory and all the files it contains (its
		 * children), even if the files are read-only. (DIRECTORY)
		 */
		FILE_DELETE_CHILD(0x00000040),
		
		/** Right to read file attributes. (FILE/DIRECTORY) */
		FILE_READ_ATTRIBUTES(0x00000080),
		
		/** Right to change file attributes. (FILE/DIRECTORY) */
		FILE_WRITE_ATTRIBUTES(0x00000100),
		
		/**
		 * The standard rights (bits 16 to 23). Are independent of the type of
		 * object being secured.
		 */
		
		/** Right to delete the object. */
		DELETE(0x00010000),
		
		/**
		 * Right to read the information in the object's security descriptor,
		 * not including the information in the SACL. I.e. right to read the
		 * security descriptor and owner.
		 */
		READ_CONTROL(0x00020000),
		
		/** Right to modify the DACL in the object's security descriptor. */
		WRITE_DAC(0x00040000),
		
		/** Right to change the owner in the object's security descriptor. */
		WRITE_OWNER(0x00080000),
		
		/**
		 * Right to use the object for synchronization. Enables a process to
		 * wait until the object is in the signalled state. Some object types do
		 * not support this access right.
		 */
		SYNCHRONIZE(0x00100000),
		
		/**
		 * The following STANDARD_RIGHTS_* are combinations of the above for
		 * convenience and are defined by the Win32 API.
		 */
		
		/** These are currently defined to READ_CONTROL. */
		STANDARD_RIGHTS_READ(0x00020000), STANDARD_RIGHTS_WRITE(0x00020000), STANDARD_RIGHTS_EXECUTE(
				0x00020000),
				
				/** Combines DELETE, READ_CONTROL, WRITE_DAC, and WRITE_OWNER access. */
				STANDARD_RIGHTS_REQUIRED(0x000f0000),
				
				/**
				 * Combines DELETE, READ_CONTROL, WRITE_DAC, WRITE_OWNER, and
				 * SYNCHRONIZE access.
				 */
				STANDARD_RIGHTS_ALL(0x001f0000),
				
				/**
				 * The access system ACL and maximum allowed access types (bits 24 to
				 * 25, bits 26 to 27 are reserved).
				 */
				ACCESS_SYSTEM_SECURITY(0x01000000), MAXIMUM_ALLOWED(0x02000000),
				
				/**
				 * The generic rights (bits 28 to 31). These map onto the standard and
				 * specific rights.
				 */
				
				/** Read, write, and execute access. */
				GENERIC_ALL(0x10000000),
				
				/** Execute access. */
				GENERIC_EXECUTE(0x20000000),
				
				/**
				 * Write access. For files, this maps onto: FILE_APPEND_DATA |
				 * FILE_WRITE_ATTRIBUTES | FILE_WRITE_DATA | FILE_WRITE_EA |
				 * STANDARD_RIGHTS_WRITE | SYNCHRONIZE For directories, the mapping has
				 * the same numberical value. See above for the descriptions of the
				 * rights granted.
				 */
				GENERIC_WRITE(0x40000000),
				
				/**
				 * Read access. For files, this maps onto: FILE_READ_ATTRIBUTES |
				 * FILE_READ_DATA | FILE_READ_EA | STANDARD_RIGHTS_READ | SYNCHRONIZE
				 * For directories, the mapping has the same numberical value. See above
				 * for the descriptions of the rights granted.
				 */
				GENERIC_READ(0x80000000),
				
				;
		int value;
		
		private AccessMask(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
	}
	
	public static enum ShareMode
	{
		FILE_SHARE_READ(0x01),
		FILE_SHARE_WRITE(0x02),
		FILE_SHARE_DELETE(0x04),
		FILE_SHARE_VALID_FLAGS(0x00000007);
		
		int value;
		
		private ShareMode(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
	}
	public static enum MoveMode
	{
		FILE_BEGIN(0x00),
		FILE_CURRENT(0x01),
		FILE_END(0x02);
		
		int value;
		
		private MoveMode(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
	}
	
	public static enum CreationDisposition
	{
		CREATE_NEW(1),
		CREATE_ALWAYS(2),
		OPEN_EXISTING(3),
		OPEN_ALWAYS(4),
		TRUNCATE_EXISTING(5);
		
		int value;
		
		private CreationDisposition(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
	}
	
	public static enum PageAccess
	{
		PAGE_NOACCESS(0x0001), PAGE_READONLY(0x0002), PAGE_READWRITE(0x0004), PAGE_WRITECOPY(
				0x0008), PAGE_EXECUTE(0x0010), PAGE_EXECUTE_READ(0x0020), PAGE_EXECUTE_READWRITE(
				0x0040), PAGE_EXECUTE_WRITECOPY(0x0080), PAGE_GUARD(0x0100), PAGE_NOCACHE(
				0x0200), PAGE_WRITECOMBINE(0x0400), ;
		int value;
		
		private PageAccess(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
	}
	
	public static enum FileAttribute
	{
		FILE_ATTRIBUTE_READONLY(0x00000001), FILE_ATTRIBUTE_HIDDEN(0x00000002), FILE_ATTRIBUTE_SYSTEM(
				0x00000004), FILE_ATTRIBUTE_DIRECTORY(0x00000010), FILE_ATTRIBUTE_ARCHIVE(
				0x00000020), FILE_ATTRIBUTE_DEVICE(0x00000040), FILE_ATTRIBUTE_NORMAL(
				0x00000080), FILE_ATTRIBUTE_TEMPORARY(0x00000100), FILE_ATTRIBUTE_SPARSE_FILE(
				0x00000200), FILE_ATTRIBUTE_REPARSE_POINT(0x00000400), FILE_ATTRIBUTE_COMPRESSED(
				0x00000800), FILE_ATTRIBUTE_OFFLINE(0x00001000), FILE_ATTRIBUTE_NOT_CONTENT_INDEXED(
				0x00002000), FILE_ATTRIBUTE_ENCRYPTED(0x00004000), FILE_ATTRIBUTE_VALID_FLAGS(
				0x00007fb7), FILE_ATTRIBUTE_VALID_SET_FLAGS(0x000031a7) ;
		int value;
		
		private FileAttribute(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
	}
	
	public static enum FileFlags
	{
		FILE_FLAG_DELETE_ON_CLOSE(0x4000000),
		FILE_FLAG_NO_BUFFERING(0x20000000),
		FILE_FLAG_OVERLAPPED(0x40000000),
		FILE_FLAG_POSIX_SEMANTICS(0x1000000),
		FILE_FLAG_RANDOM_ACCESS(0x10000000),
		FILE_FLAG_SEQUENTIAL_SCAN(0x8000000),
		FILE_FLAG_WRITE_THROUGH(0x80000000);
		int value;
		
		private FileFlags(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
	}
	
	public static enum CodePage
	{
		CP_ACP 			(0),
		CP_OEMCP 		(1),
		CP_MACCP 		(2),
		CP_THREAD_ACP 	(3),
		CP_SYMBOL 		(42),
		CP_UTF7 		(65000),
		CP_UTF8 		(65001),
		;
		private final int value;
		
		private CodePage(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
	}
}
