package org.xvolks.jnative.misc.basicStructures;

/**
 * $Id: HANDLE.java,v 1.2 2006/10/13 20:59:42 mdenty Exp $
 * <p>
 * HANDLE.java
 * </p>
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class HANDLE extends LONG {
	
	public static final HANDLE INVALID_HANDLE_VALUE = new HANDLE(-1);
	
	public HANDLE(int value) {
		super(value);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof HANDLE) {
			return obj.hashCode() == hashCode();
		} else {
			return false;
		}
	}


	@Override
	public int hashCode() {
		return getValue();
	}
	
	
}
