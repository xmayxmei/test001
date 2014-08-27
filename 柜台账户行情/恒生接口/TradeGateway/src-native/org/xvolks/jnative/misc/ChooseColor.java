package org.xvolks.jnative.misc;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.misc.basicStructures.LPARAM;
import org.xvolks.jnative.pointers.NullPointer;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.GlobalMemoryBlock;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.util.Callback;

/**
 * $Id: ChooseColor.java,v 1.2 2006/06/09 20:44:05 mdenty Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class ChooseColor extends AbstractBasicData<ChooseColor> {
    LONG lStructSize;
    HWND hwndOwner;
    HWND hInstance;
    public LONG rgbResult;
    static Pointer lpCustColors;
    LONG Flags;
    LPARAM lCustData;
    int lpfnHook;
    Callback fnHook;
    Pointer lpTemplateName;
    
    static {
    	try {
			lpCustColors = new Pointer(MemoryBlockFactory.createMemoryBlock(16));
		} catch (NativeException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}    	
    }

	public ChooseColor() throws NativeException {
		super(null);
		mValue = this;
		createPointer();
	}

	public void removeCallback() throws NativeException {
		if(fnHook != null) {
			JNative.releaseCallback(fnHook);
			fnHook = null;
		}
	}
	public void addCallback(Callback lCallback) throws NativeException {
		removeCallback();
		fnHook = lCallback;
		//The callback object must handle 4 parameters from 
		//CCHookProc(HWND hdlg, UINT uiMsg, WPARAM wParam, LPARAM lParam)
		lpfnHook = JNative.createCallback(4, lCallback);
		pointer.setIntAt(28, lpfnHook);
	}
	
	public ChooseColor getValueFromPointer() throws NativeException {
		offset = 0;
		lStructSize = new LONG(getNextInt()); 	// 0
		hwndOwner = new HWND(getNextInt());   	// 4
		hInstance = new HWND(getNextInt());		// 8
		rgbResult = new LONG(getNextInt()); 	//12
		//Skip lpCustColor
		offset += 4; 							//16
		Flags = new LONG(getNextInt());			//20
		lCustData = new LPARAM(getNextInt());	//24
		//Skip callback 						//28
		//Skip TemplateName 					//32
												//36 = size
		return getValue();
	}

	public int getSizeOf() {
		return 36;
	}

	public Pointer createPointer() throws NativeException {
		pointer = new Pointer(new GlobalMemoryBlock(getSizeOf()));
		pointer.setIntAt(0, getSizeOf());
		pointer.setIntAt(16, lpCustColors.getPointer());
		return pointer;
	}

	public void setOwner(HWND owner) throws NativeException {
		hwndOwner = owner;
		pointer.setIntAt(4, hwndOwner.getValue());
	}

	public void setInstance(HWND instance) throws NativeException {
		hInstance = instance;
		pointer.setIntAt(8, hInstance.getValue());
	}

	public void setFlags(LONG flags) throws NativeException {
		Flags = flags;
		pointer.setIntAt(20, Flags.getValue());
	}

	public void setCustData(LPARAM custData) throws NativeException {
		lCustData = custData;
		pointer.setIntAt(24, lCustData.getValue());
	}

	public void setTemplateName(String templateName) throws NativeException {
		if(templateName == null) {
			lpTemplateName = new NullPointer();
		} else {
			lpTemplateName = new Pointer(MemoryBlockFactory.createMemoryBlock(templateName.length() + 1)); 
			lpTemplateName.setMemory(templateName);
		}
		pointer.setIntAt(32, lpTemplateName.getPointer());
	}
	
	
}
