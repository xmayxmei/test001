package org.xvolks.test.bug;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.util.Callback;
import org.xvolks.jnative.util.User32;
import org.xvolks.test.JNativeTester;

/**
 * Test case was given by Thubby, TrayIconCallback was renamed to _1715554_ncdfe
 *
 * @author Thubby
 *
 * WND_PROC.java
 *
 * Created on 12. März 2007, 10:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

public class _1715554_ncdfe implements Callback {

    private int myAddress = -1;
    private static _1715554_ncdfe callback = null;
    
    public static void main(String[] args) throws NativeException, IllegalAccessException, IOException {
		System.getProperties().put("jnative.debug", "true");
		System.getProperties().put("jnative.loadNative", "manual");
		JNative.getLogger().log(SEVERITY.INFO, "1715554 bug tester");
    	JNativeTester.loadLib();
    	final JFrame f = new JFrame(_1715554_ncdfe.class.getName()) {

			@Override
			public void dispose() {
				super.dispose();
				System.exit(0);
			}
    		
    	};
    	f.add(new JButton("Quit") {
    		{
    			addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						f.dispose();
					}
    			});
    		}
    	});
    	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	f.pack();
    	f.setVisible(true);
    	
    	HWND hWnd = User32.FindWindow(null,f.getTitle());
    	int prevWindowProc = User32.SetWindowLong(hWnd, -4, new
    			LONG(_1715554_ncdfe.getInstance().getCallbackAddress()));
    	System.err.println("prevWindowProc "+prevWindowProc);
	}
    
    private _1715554_ncdfe() {
        
    }
    
    public static _1715554_ncdfe getInstance() {
        if(callback == null) {
            callback = new _1715554_ncdfe();
        }
        return callback;        
    }
    
    public int callback(long[] values) { 
        System.out.println(values[0]);
        return 0;
    }

    public int getCallbackAddress() throws NativeException {
        if(myAddress == -1) {
            myAddress = JNative.createCallback(4, this);
        }
        return myAddress;
    }
    
}
