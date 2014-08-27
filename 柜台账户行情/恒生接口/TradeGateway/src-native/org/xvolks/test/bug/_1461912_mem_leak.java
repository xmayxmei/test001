package org.xvolks.test.bug;
import org.xvolks.jnative.pointers.memory.*;
import org.xvolks.jnative.exceptions.*;
import javax.swing.*;
import java.io.*;
import org.xvolks.jnative.pointers.*;
import org.xvolks.jnative.*;
/**
 * 
 * $Id: _1461912_mem_leak.java,v 1.2 2006/06/09 20:44:05 mdenty Exp $
 * 
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class _1461912_mem_leak {
	public static void main(String[] args) throws IOException, NativeException {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
																	"Do want to test ?",
																	"Choose pls",
																	JOptionPane.YES_NO_CANCEL_OPTION)) {
			Runtime.getRuntime().exec("taskmgr.exe");
			Pointer p = new Pointer(MemoryBlockFactory.createMemoryBlock(512));
			while(true) {
				JNative.setMemory(p.getPointer(), "blabladfsdfksdklfsdfsdmfldsmfsdlmfksdfksdmlfsldfksldfksmldfksdkflmdskfsdkfsm");
			}
		}
	}
}
