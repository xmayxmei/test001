package org.xvolks.jnative.misc;

import org.xvolks.jnative.exceptions.*;
import org.xvolks.jnative.pointers.*;
/**
 * SecurityAttributes this utility class is used by Kernel32.createFile().<br>
 * This is the native peer.
 * <pre>
 *	 typedef struct _SECURITY_ATTRIBUTES {
 * &nbsp;	DWORD nLength;
 * &nbsp;	LPVOID lpSecurityDescriptor;
 * &nbsp;	BOOL bInheritHandle;
 * &nbsp;} SECURITY_ATTRIBUTES,
 * &nbsp;*PSECURITY_ATTRIBUTES;</pre>
 *
 *  $Id: SecurityAttributes.java,v 1.6 2006/06/09 20:44:05 mdenty Exp $;
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class SecurityAttributes {
	/*
	 */
	
	private int nLength;
	private Pointer lpSecurityDescriptor;
	private boolean bInheritHandle;
	
	private Pointer self;
	
	public SecurityAttributes() {
		nLength = 12;
		lpSecurityDescriptor = new NullPointer();
		bInheritHandle = true;
//		self = new Pointer(nLength);
	}
	
	public Pointer getPointer() {
		return self;
	}

	public void dispose() throws NativeException {
		self.dispose();
		lpSecurityDescriptor.dispose();
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			dispose();
		}
		catch(NativeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets NLength
	 *
	 * @param    NLength             an int
	 */
	public void setNLength(int nLength) throws NativeException {
		this.nLength = nLength;
		self.setIntAt(0, nLength);
	}
	
	/**
	 * Returns NLength
	 *
	 * @return    an int
	 */
	public int getNLength() throws NativeException {
		nLength = self.getAsInt(0);
		return nLength;
	}
	
	/**
	 * Sets LpSecurityDescriptor
	 *
	 * @param    LpSecurityDescriptora  Pointer
	 */
	public void setLpSecurityDescriptor(Pointer lpSecurityDescriptor) throws NativeException {
		this.lpSecurityDescriptor = lpSecurityDescriptor;
		self.setIntAt(4, lpSecurityDescriptor.getPointer());
	}
	
	/**
	 * Returns LpSecurityDescriptor
	 *
	 * @return    a  Pointer
	 */
	public Pointer getLpSecurityDescriptor() {
		return lpSecurityDescriptor;
	}
	
	/**
	 * Sets BInheritHandle
	 *
	 * @param    BInheritHandle      a  boolean
	 */
	public void setBInheritHandle(boolean bInheritHandle) throws NativeException {
		this.bInheritHandle = bInheritHandle;
		lpSecurityDescriptor.setIntAt(8, bInheritHandle ? 1 : 0);
	}
	
	/**
	 * Returns BInheritHandle
	 *
	 * @return    a  boolean
	 */
	public boolean isBInheritHandle() throws NativeException {
		bInheritHandle = lpSecurityDescriptor.getAsInt(8) != 0;
		return bInheritHandle;
	}
	
}
