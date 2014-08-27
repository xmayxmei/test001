package org.xvolks.jnative.misc.registry;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.LONG;

/**
 * 
 * $Id: RegQueryKey.java,v 1.3 2006/06/09 20:44:05 mdenty Exp $
 * 
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class RegQueryKey extends TimedRegData {

	LONG lpcSubKeys;
	LONG lpcMaxSubKeyLen;
	LONG lpcMaxClassLen;
	LONG lpcValues;
	LONG lpcMaxValueNameLen;
	LONG lpcMaxValueLen;
	LONG lpcbSecurityDescriptor;	
	
	public RegQueryKey(int sizeData) throws NativeException {
		super(sizeData);
		lpcSubKeys = new LONG(0);
		lpcMaxSubKeyLen = new LONG(0);
		lpcMaxClassLen = new LONG(0);
		lpcValues = new LONG(0);
		lpcMaxValueNameLen = new LONG(0);
		lpcMaxValueLen = new LONG(0);
		lpcbSecurityDescriptor = new LONG(0);
	}

	public LONG getLpcbSecurityDescriptor() {
		return lpcbSecurityDescriptor;
	}

	public LONG getLpcMaxClassLen() {
		return lpcMaxClassLen;
	}

	public LONG getLpcMaxSubKeyLen() {
		return lpcMaxSubKeyLen;
	}

	public LONG getLpcSubKeys() {
		return lpcSubKeys;
	}

	public LONG getLpcValues() {
		return lpcValues;
	}

	public LONG getLpcMaxValueNameLen() {
		return lpcMaxValueNameLen;
	}
	
	public LONG getLpcMaxValueLen() {
		return lpcMaxValueLen;
	}
}
