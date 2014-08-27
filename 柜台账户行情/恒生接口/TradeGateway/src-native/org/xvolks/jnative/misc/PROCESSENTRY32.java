/*
 * PROCESSENTRY32.java
 *
 * Created on 21. Juli 2008, 16:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.misc;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.*;
import org.xvolks.jnative.pointers.Pointer;

/**
 *
 * typedef struct tagPROCESSENTRY32 {
 DWORD dwSize;
 DWORD cntUsage;
 DWORD th32ProcessID;
 ULONG_PTR th32DefaultHeapID;
 DWORD th32ModuleID;
 DWORD cntThreads;
 DWORD th32ParentProcessID;
 LONG pcPriClassBase;
 DWORD dwFlags;
 TCHAR szExeFile[MAX_PATH];
 } PROCESSENTRY32,
 */
public class PROCESSENTRY32 extends AbstractBasicData<PROCESSENTRY32>
{
	public DWORD dwSize = new DWORD(sizeOf());
	public DWORD cntUsage = new DWORD(0);
	public DWORD th32ProcessID = new DWORD(0);
	public LONG th32DefaultHeapID = new LONG(0);
	public DWORD th32ModuleID = new DWORD(0);
	public DWORD cntThreads = new DWORD(0);
	public DWORD th32ParentProcessID = new DWORD(0);
	public LONG pcPriClassBase = new LONG(0);
	public DWORD dwFlags = new DWORD(0);
	public String szExeFile = "";
		
	/** Creates a new instance of PROCESSENTRY32 */
	public PROCESSENTRY32() throws NativeException
	{
		super(null);
		createPointer();
		resetPointer();		
	}
	public PROCESSENTRY32(int nativeAddress) throws NativeException
	{
		this();
		pointer.setMemory(JNative.getMemory(nativeAddress, sizeOf()));
		getValueFromPointer();
	}
	
	public void resetPointer() throws NativeException
	{
		pointer.zeroMemory();
		// always set dwSize!
		pointer.setIntAt(0, dwSize.getValue());
	}
	
	public PROCESSENTRY32 getValue()
	{
		try
		{
			pointer.zeroMemory();
			
			int i = -4;
			pointer.setIntAt(i+=4, dwSize.getValue());
			pointer.setIntAt(i+=4, cntUsage.getValue());
			pointer.setIntAt(i+=4, th32ProcessID.getValue());
			pointer.setIntAt(i+=4, th32DefaultHeapID.getValue());
			pointer.setIntAt(i+=4, th32ModuleID.getValue());
			pointer.setIntAt(i+=4, cntThreads.getValue());
			pointer.setIntAt(i+=4, th32ParentProcessID.getValue());
			pointer.setIntAt(i+=4, pcPriClassBase.getValue());
			pointer.setIntAt(i+=4, dwFlags.getValue());
			
			pointer.setStringAt(i+=4, szExeFile);
		}
		catch (NativeException ex)
		{
			ex.printStackTrace();
		}
		return this;
	}
	
	public Pointer createPointer() throws NativeException
	{
		if(pointer == null)
		{
			pointer = Pointer.createPointer(sizeOf());
		}
		return pointer;
	}
	
	public int getSizeOf()
	{
		return sizeOf();
	}
	
	public PROCESSENTRY32 getValueFromPointer() throws NativeException
	{
		offset = 0;
		
		dwSize = new DWORD(getNextInt());
		cntUsage = new DWORD(getNextInt());
		th32ProcessID = new DWORD(getNextInt());
		th32DefaultHeapID = new LONG(getNextInt());
		th32ModuleID = new DWORD(getNextInt());
		cntThreads = new DWORD(getNextInt());
		th32ParentProcessID = new DWORD(getNextInt());
		pcPriClassBase = new LONG(getNextInt());
		dwFlags = new DWORD(getNextInt());
		
		byte[] b = new byte[256];
		for(int i = 0; i < b.length; i++)
		{
			b[i] = getNextByte();
			if(b[i] == 0)
			{
				break;
			}
		}
		szExeFile = new String(b).trim();

		offset = 0;
		return this;
	}
	
	public static int sizeOf()
	{
		return 296;
	}
	
	
}
