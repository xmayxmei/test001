
package org.xvolks.jnative.misc.machine;

/**
 * $Id: Machine.java,v 1.3 2006/06/09 20:44:05 mdenty Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class Machine {
	public static final int SIZE;
	
	static {
		//TODO find the current machine here to see if we should use 64 bits pointers
		SIZE = 1;
		//On AMD64 / Windows64 / Linux 64 set SIZE to 2
	}
}
