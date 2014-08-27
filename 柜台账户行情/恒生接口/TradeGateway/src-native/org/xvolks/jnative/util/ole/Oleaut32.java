package org.xvolks.jnative.util.ole;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.com.utils.Basic;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.pointers.memory.NativeMemoryBlock;
import org.xvolks.jnative.toolkit.StringToolkit;

/**
 *
 * $Id: Oleaut32.java,v 1.8 2008/10/23 14:32:39 mdenty Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2007 JNative project
 */
public class Oleaut32
{
	public static final String DLL_NAME = "Oleaut32.dll";
	
/*
	typedef struct _GUID
	{
	unsigned long Data1;
	unsigned short Data2;
	unsigned short Data3;
	unsigned char Data4[8];
	} GUID,*REFGUID,*LPGUID;
 */
	public static final Pointer LoadRegTypeLib(
			Pointer  rguid,
			int wVerMajor,
			int wVerMinor,
			int  lcid
			) throws NativeException, IllegalAccessException
	{
		Pointer p = new Pointer(MemoryBlockFactory.createMemoryBlock(4));
		JNative LoadRegTypeLib = new JNative(DLL_NAME, "LoadRegTypeLib");
		LoadRegTypeLib.setRetVal(Type.INT);
		LoadRegTypeLib.setParameter(0, rguid);
		LoadRegTypeLib.setParameter(1, wVerMajor);
		LoadRegTypeLib.setParameter(2, wVerMinor);
		LoadRegTypeLib.setParameter(3, lcid);
		LoadRegTypeLib.setParameter(4, p);
		LoadRegTypeLib.invoke();
		
		if(LoadRegTypeLib.getRetValAsInt() == 0)
		{
			return p;
		}
		else
		{
			return null;
		}
	}
	/**
	 * <h3>SysAllocString</h3>
	 * <pre>
	 
	 Allocates a new string and copies the passed string into it.
	 C++
	 
	 BSTR SysAllocString(
	 
	 const OLECHAR   *sz
	 
	 );
	 
	 Parameter
	 
	 sz
	 
	 A zero-terminated string to copy. The sz parameter must be a Unicode string in 32-bit applications, and an ANSI string in 16-bit applications. The argument sz may be NULL.
	 
	 Return Value
	 
	 If successful, returns a BSTR containing the string. If sz is a zero-length string, returns a zero-length BSTR. If sz is NULL or insufficient memory exists, returns NULL.
	 </pre>
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	
	public static final Pointer SysAllocString(String sz) throws NativeException, IllegalAccessException
	{
		int ret = Basic.SysAllocString(sz);
		if(ret == 0) {
			throw new OutOfMemoryError("Cannot allocate memory in SysAllocString("+sz+"");
		} else {
			return new Pointer(new NativeMemoryBlock(ret, 4))
			{
				@Override
				protected void finalize() throws Throwable {
					dispose();
				}
				public void dispose()
				{
					try
					{
						SysFreeString(this);
					}
					catch (NativeException e)
					{
						e.printStackTrace();
					}
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
				}
			};
		}
	}
	
	/**
	 * <h3>SysFreeString</h3>
	 * <pre>
	 
	 Deallocates a string allocated previously by SysAllocString, SysAllocStringByteLen, SysReAllocString, SysAllocStringLen, or SysReAllocStringLen.
	 C++
	 
	 VOID SysFreeString(
	 
	 BSTR  bstr
	 
	 );
	 
	 Parameter
	 
	 bstr
	 
	 Previously allocated BSTR. If bstr is NULL, the function simply returns.
	 </pre>
	 * @param sz
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	public static final void SysFreeString(Pointer bstr) throws NativeException, IllegalAccessException
	{
		Basic.SysFreeString(bstr.getPointer());
	}
	public static final int SysStringLen(Pointer bstr) throws NativeException, IllegalAccessException {
		return Basic.SysStringLen(bstr.getPointer());
	}
	public static final int SysStringByteLen(Pointer bstr) throws NativeException, IllegalAccessException {
		return Basic.SysStringByteLen(bstr.getPointer());
	}

	public static void main(String[] args) throws NativeException, InterruptedException, IllegalAccessException {
		System.setProperty(JNativeLogger.LOG_LEVEL, "5");
		JNative.setLoggingEnabled(true);
		Pointer p = SysAllocString("toto");
		Pointer pp = new Pointer(new NativeMemoryBlock(p.getPointer()-4, 4+8));
		System.err.println(StringToolkit.toHexString(p.getMemory()));
		System.err.println(StringToolkit.toHexString(pp.getMemory()));
		
		
		System.exit(0);
		for(String s : JNative.getDLLFileExports(System.getenv("windir")+"/system32/" + DLL_NAME)) {
			System.err.println(s);
		}
	}
	
}
