package org.xvolks.jnative.com.typebrowser.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.misc.HKEY;
import org.xvolks.jnative.misc.REGSAM;
import org.xvolks.jnative.misc.registry.RegKey;
import org.xvolks.jnative.util.Advapi32;
import org.xvolks.test.JNativeTester;

/**
 * 
 * $Id: CLSIDs.java,v 1.1 2007/06/28 18:06:25 mdenty Exp $
 * 
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2007 JNative project
 */
public class CLSIDs {
	private final List<CLSID> cLSIDs = new ArrayList<CLSID>();
	public static void main(String[] args) throws NativeException, IllegalAccessException, IOException {
		System.getProperties().put("jnative.debug", "true");
		System.getProperties().put("jnative.loadNative", "manual");
		JNative.getLogger().log(SEVERITY.INFO, "CLSIDs list tester");
 		JNativeTester.loadLib();
		new CLSIDs().initialize();
	}
	public CLSIDs() throws NativeException, IllegalAccessException {
		initialize();
	}
	private void initialize() throws NativeException, IllegalAccessException {
		HKEY hKey = Advapi32.RegOpenKeyEx(HKEY.HKEY_CLASSES_ROOT, "CLSID", REGSAM.KEY_READ);
		int i = 0;
		RegKey lKey = null;
		long start = System.currentTimeMillis();
		while(true) {
			lKey = Advapi32.RegEnumKeyEx(hKey, i, null);
			if(lKey.getErrorCode() != 0) {
				JNative.getLogger().log("Exiting code "+lKey.getErrorCode()+", after "+i+" iterations.");
				break;
			}
			String guid = lKey.getLpValueName().getAsString();
			try {
				CLSID clsid = new CLSID(guid);
				cLSIDs.add(clsid);
			} catch (TypeLibNotFoundException e) {
				JNative.getLogger().log(SEVERITY.DEBUG, e);
			}
			i++;
		}
		long end = System.currentTimeMillis();
		JNative.getLogger().log("Took : " + (end-start));
	}
	
	public List<CLSID> getCLSIDs() {
		return cLSIDs;
	}

}
