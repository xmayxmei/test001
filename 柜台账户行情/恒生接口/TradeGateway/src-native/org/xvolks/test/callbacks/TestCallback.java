package org.xvolks.test.callbacks;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.util.User32;

/**
 * 
 * $Id: TestCallback.java,v 1.4 2007/03/16 21:06:13 thubby Exp $
 * 
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class TestCallback {
	public static void main(String[] args) {
		new Thread() {
			@Override
			public void run(){
				try {
					runIt();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.run();
	}
	public static void runIt() throws Exception {
		User32.MessageBox(0, "Callback Demo", "JNative", 0);
		JFrame f = new JFrame("TestCallback");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
		MemoryBlockFactory.createMemoryBlock(1).dispose();
		if(JNative.getAvailableCallbacks() > 0) {
			System.err.println("getAvailableCallbacks "  +JNative.getAvailableCallbacks());
			
			EnumCallback c = new EnumCallback();
			System.err.println("inactive callbacks : " + JNative.getAvailableCallbacks());
			System.err.println("getAvailableCallbacks "  +JNative.getAvailableCallbacks());
			
			
			if(User32.EnumWindows(c, 0)) {
				System.err.println("EnumWindows suceeded");
			} else {
				System.err.println("EnumWindows failed");
			}
			
			
			System.err.println("getAvailableCallbacks "  +JNative.getAvailableCallbacks());
			JNative.releaseCallback(c);
			System.err.println("getAvailableCallbacks "  +JNative.getAvailableCallbacks());
			User32.MessageBox(0, c.toString(), "List of windows (EnumWindows)", 0);
			JOptionPane.showMessageDialog(null, c.toString(), "List of windows (EnumWindows)", JOptionPane.OK_OPTION);
		} else {
			JOptionPane.showMessageDialog(null, "no more callbacks");
		}
	}
}
