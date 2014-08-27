package org.xvolks.jnative.misc.registry;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.FILETIME;

/**
 * 
 * $Id: TimedRegData.java,v 1.2 2006/06/09 20:44:05 mdenty Exp $
 * 
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 * 
 */
public abstract class TimedRegData extends BasicRegData {

	private FILETIME lpftLastWriteTime;

	protected TimedRegData(int sizeData) throws NativeException {
		super(sizeData);
		lpftLastWriteTime = new FILETIME();
	}

	public FILETIME getLpLastWriteTime() {
		return lpftLastWriteTime;
	}

	@Override
	public String toString() {
		return super.toString() + "lpftLastWriteTime : "
				+ lpftLastWriteTime.toString() + "\n";
	}

}
