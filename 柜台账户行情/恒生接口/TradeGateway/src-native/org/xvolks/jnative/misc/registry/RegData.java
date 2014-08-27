package org.xvolks.jnative.misc.registry;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.LONG;

/**
 * $Id: RegData.java,v 1.1 2006/06/05 21:22:02 mdenty Exp $
 * 
 * This software is released under the LGPL.
 * 
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class RegData extends BasicRegData {
	/**
	 * A pointer to a variable that receives a code indicating the type of data
	 * stored in the specified value. For a list of the possible type codes, see
	 * Registry Value Types. The lpType parameter can be NULL if the type code
	 * is not required.
	 */
	private LONG lpType;

	public RegData(int lDataSize) throws NativeException {
		super(lDataSize);
		lpType = new LONG(0);
	}

	public LONG getLpType() {
		return lpType;
	}

	@Override
	public String toString() {
		return super.toString() + "Type : " + lpType.getValue() + "\n";
	}
}
