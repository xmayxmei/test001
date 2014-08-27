package org.xvolks.jnative.com.utils;

import org.xvolks.jnative.pointers.Pointer;

public class Basic {
	public static final native int SysStringByteLen(int value);
	public static final native int SysStringLen(int value);
	public static final native void SysFreeString(int value);
	public static final native int SysAllocString(String value);
	public static final native int MultiByteToWideChar(int CodePage, int dwFlags, String lpMultiByteStr, int cbMultiByte, ByRef<Pointer> lpWideCharStr, int cchWideChar, ByRef<Integer> getLastErrorValue);

}
