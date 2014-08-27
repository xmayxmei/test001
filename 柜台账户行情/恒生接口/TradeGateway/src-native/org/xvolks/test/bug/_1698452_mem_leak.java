package org.xvolks.test.bug;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.misc.MemoryStatusEx;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.util.Kernel32;
import org.xvolks.test.JNativeTester;

/**
 * 
 * $Id: _1698452_mem_leak.java,v 1.6 2007/05/20 12:05:04 thubby Exp $
 * 
 * This software is released under the LGPL.
 * 
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class _1698452_mem_leak {
	
	private static JNative strcat;

	private static String strcat(String src1, String src2) throws NativeException, IllegalAccessException {
		if(strcat == null) {
			if(JNative.isWindows()) {
				strcat = new JNative("ntdll.dll", "strcat", false);
			} else if(JNative.isLinux()) {
				strcat = new JNative("/lib/libc.so.6", "strcat", false);
			}
			strcat.setRetVal(Type.INT);
		}
		Pointer p = new Pointer(MemoryBlockFactory.createMemoryBlock(100));
		p.zeroMemory();
		p.setStringAt(0, src1);
		//System.err.println("Before strcat");
		//System.err.println("Get string from pointer p: " + p.getAsString());
		strcat.setParameter(0, p);
		strcat.setParameter(1, Type.STRING, src2);
		strcat.invoke();
		try {
			return p.getAsString();
		} finally {
			p.dispose();
		}
	}
	

	/**
	 * @param args
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws NativeException 
	 */
	public static void main(String[] args) throws IOException, NativeException, IllegalAccessException {
		System.getProperties().put("jnative.debug", "true");
		System.getProperties().put("jnative.loadNative", "manual");
		JNative.getLogger().log(SEVERITY.INFO, "1698452 bug tester");
		JNativeTester.loadLib();
		System.err.println(JNative.getNativeSideVersion());
		
		JNative.setLoggingEnabled(false);
		while (true) {
			for (int i = 0; i < 100000; i++) {
				//JNative.getLogger().log(SEVERITY.INFO, "counter = "+i);
				try {
					strcat("toto", "" + i);
				} catch (NativeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}			
			System.gc();
//			System.out.println("Total Memory : "
//					+ Runtime.getRuntime().totalMemory());
			if(JNative.isWindows()) {
				MemoryStatusEx memStatus = Kernel32.GlobalMemoryStatusEx();
				System.out.println("Free Memory  : Physical = "
						+ memStatus.ullAvailPhys+ ", Virtual = " + memStatus.ullAvailVirtual);
			} else if(JNative.isLinux()) {
				BufferedReader br = new BufferedReader(new FileReader("/proc/meminfo"));
				String line;
				String l = "";
				while((line = br.readLine()) != null) {
					if(line.toLowerCase().indexOf("free") >= 0) {
						l += line + ", ";
					}
				}
				br.close();
				System.out.println(l);
			}
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
}
