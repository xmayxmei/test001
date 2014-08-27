package org.xvolks.jnative.misc.registry;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 * $Id: BasicRegData.java,v 1.1 2006/06/05 21:22:02 mdenty Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public abstract class BasicRegData {
	private int errorCode = -1; 
    /**
     * A pointer to a buffer that receives the value's data. This parameter can be NULL if the data is not required.
     */
	private Pointer lpData;
    
	/**
	 * A pointer to a variable that specifies the size of the buffer pointed to by the lpData parameter, in bytes. When the function returns, this variable contains the size of the data copied to lpData.
	 */
	private LONG lpcbData;
	
	protected BasicRegData(int sizeData) throws NativeException {
		if(sizeData < 4) {
			sizeData = 4;
		}
		lpcbData = new LONG(sizeData);
		lpData = new Pointer(MemoryBlockFactory.createMemoryBlock(sizeData));

	}

	public LONG getLpcbData() {
		return lpcbData;
	}

	public Pointer getLpData() {
		return lpData;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	@Override
	public String toString() {
		try {
			return 	"lpData : " + lpData.getAsString() + "\n" + 
			"lpcbData : " + lpcbData.getValue() + "\n";
		} catch (NativeException e) {
			e.printStackTrace();
			return e.toString();
		}

	}
}
