package org.xvolks.jnative.misc.registry;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 * $Id: RegValue.java,v 1.1 2006/06/05 21:22:02 mdenty Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class RegValue extends RegData {
	private Pointer lpValueName;
	private LONG lpcValueName;
	
	public RegValue(int sizeValueName, int sizeData) throws NativeException {
		super(sizeData);
		if(sizeValueName < 4) {
			sizeValueName = 4;
		}
		lpcValueName = new LONG(sizeValueName);
		lpValueName = new Pointer(MemoryBlockFactory.createMemoryBlock(sizeValueName));
	}

	public LONG getLpcValueName() {
		return lpcValueName;
	}

	public Pointer getLpValueName() {
		return lpValueName;
	}

	@Override
	public String toString() {
		try {
			return super.toString() +
			"lpValueName : "+lpValueName.getAsString() + "\n" +
			"lpcValueName : " + lpcValueName.getValue() + "\n";
		} catch (NativeException e) {
			e.printStackTrace();
			return e.toString();
		} 		
	}
}
