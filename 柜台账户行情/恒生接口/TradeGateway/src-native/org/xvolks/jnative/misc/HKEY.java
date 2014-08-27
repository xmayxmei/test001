package org.xvolks.jnative.misc;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;



/**
 * 	 HKEY Handles.
 * 
 * $Id: HKEY.java,v 1.2 2006/06/09 20:44:05 mdenty Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class HKEY extends AbstractBasicData<Integer> {
	public static final HKEY  HKEY_CLASSES_ROOT           = new HKEY(0x80000000) ;
	public static final HKEY  HKEY_CURRENT_USER           = new HKEY(0x80000001) ;
	public static final HKEY  HKEY_LOCAL_MACHINE          = new HKEY(0x80000002) ;
	public static final HKEY  HKEY_USERS                  = new HKEY(0x80000003) ;
	public static final HKEY  HKEY_PERFORMANCE_DATA       = new HKEY(0x80000004) ;
	public static final HKEY  HKEY_PERFORMANCE_TEXT       = new HKEY(0x80000050) ;
	public static final HKEY  HKEY_PERFORMANCE_NLSTEXT    = new HKEY(0x80000060) ;
	public static final HKEY  HKEY_CURRENT_CONFIG         = new HKEY(0x80000005) ;
	public static final HKEY  HKEY_DYN_DATA               = new HKEY(0x80000006) ;


	
	public HKEY(Integer value) {
		super(value);
		try {
			createPointer();
		} catch (NativeException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public Integer getValueFromPointer() throws NativeException {
		mValue = pointer.getAsInt(0);
		return mValue;
	}
	
	

	/**
	 * @throws RuntimeException
	 * @see org.xvolks.jnative.misc.basicStructures.BasicData#getValue()
	 */
	@Override
	public Integer getValue() {
		try {
			return getValueFromPointer();
		} catch (NativeException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public int getSizeOf() {
		return 4;
	}

	public Pointer createPointer() throws NativeException {
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
		pointer.setIntAt(0, mValue);
		return pointer;
	}
}

