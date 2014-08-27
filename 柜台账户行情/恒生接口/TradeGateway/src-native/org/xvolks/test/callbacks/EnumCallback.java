package org.xvolks.test.callbacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.util.Callback;
import org.xvolks.jnative.util.User32;

/**
 * 
 * $Id: EnumCallback.java,v 1.5 2006/10/15 10:24:52 mdenty Exp $
 * 
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class EnumCallback implements Callback {

	private final List<Long> mList;

	public EnumCallback() {
		mList = new ArrayList<Long>();
	}

	@Override
	public String toString() {
		String val = "<html><table>";
		Map<Long, String> lMap = new TreeMap<Long, String>();
		for (Long key : mList) {
			try {
				System.err.println("Handle : " + key);
				String name = User32.GetWindowText(new HWND(key.intValue()));
				System.err.println("Name : " + name);
				if (name == null || name.length() == 0) {
					System.err.println("Skipping handle " + key);
				} else {
					lMap.put(key, name);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for(Long key : lMap.keySet()) {
			val += "<tr><td>" + key + "</td><td>" + lMap.get(key) + "</td></tr>";
		}
		val += "</html></table>";
		return val;
	}

	/**
	 * Method callback
	 * 
	 * @param values
	 *            an long[]
	 * @return an int
	 * @version 3/27/2006
	 */
	public int callback(long[] values) {
		if (values == null) {
			System.err.println("callback ret " + 3);
			return 3;
		}
		if (values.length == 2) {
			System.err.println("Handle " + values[0]);
			System.err.println("lParam " + values[1]);
			try {
				if (values[0] > 0) {
					mList.add(values[0]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.err.println("Taille de la liste : " + mList.size());
			System.err.println("callback ret " + 1);
			return 1;
		} else {
			System.err.println("Bad number of arguments ! 2 expected "+values.length+" found");
			System.err.println("callback ret " + 2);
			return 2;
		}
	}

	public int getCallbackAddress() throws NativeException {
		return JNative.createCallback(2, this);
	}
}
