package org.xvolks.test.bug;

import javax.swing.JOptionPane;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.pointers.NullPointer;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.util.User32;


/**
 * 
 * $Id: _1456493_npe.java,v 1.3 2007/03/16 21:06:13 thubby Exp $
 * 
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */public class _1456493_npe {
	public static void main(String[] args) throws NativeException, IllegalAccessException {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
																	"Do want to test ?",
																	"Choose pls",
																	JOptionPane.YES_NO_CANCEL_OPTION)) {
			Pointer p = new NullPointer();
			User32.MessageBox(p.getPointer(), "Test successfull", _1456493_npe.class.getName(), 0);
		}
		
	}
}
