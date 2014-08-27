package org.xvolks.jnative.com.typebrowser.business;

import java.util.ArrayList;
import java.util.List;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.misc.HKEY;
import org.xvolks.jnative.misc.REGSAM;
import org.xvolks.jnative.misc.registry.RegKey;
import org.xvolks.jnative.util.Advapi32;

public class CLSID {
	private final String guid;
	private final boolean control;
	private final String description; 
	private final String progID; 
	private final String inprocServer32;
	private final String typeLib;
	private final List<Integer> majorVersion = new ArrayList<Integer>();
	private final List<Integer> minorVersion = new ArrayList<Integer>();
	
	
	public CLSID(String guid) throws NativeException, IllegalAccessException, TypeLibNotFoundException {
		this.guid = guid;
		//TODO : verify how to get this value from register database.
		typeLib 		= Advapi32.RegQueryDefaultValue(HKEY.HKEY_CLASSES_ROOT, "CLSID\\" + guid + "\\TypeLib");
		if(typeLib == null)
			throw new TypeLibNotFoundException();
		description 	= Advapi32.RegQueryDefaultValue(HKEY.HKEY_CLASSES_ROOT, "CLSID\\" + guid);
		control 		= Advapi32.doesKeyExist(HKEY.HKEY_CLASSES_ROOT, "CLSID\\" + guid + "\\Control");
		progID 			= Advapi32.RegQueryDefaultValue(HKEY.HKEY_CLASSES_ROOT, "CLSID\\" + guid + "\\ProgID");
		inprocServer32 	= Advapi32.RegQueryDefaultValue(HKEY.HKEY_CLASSES_ROOT, "CLSID\\" + guid + "\\InprocServer32");
		
		HKEY typeInfo 	= Advapi32.RegOpenKeyEx(HKEY.HKEY_CLASSES_ROOT, "TypeLib\\" + typeLib, REGSAM.KEY_READ);
		if(progID != null /*&& progID.indexOf("Shell.Explorer.2") != -1*/) {
			JNative.getLogger().log(guid);
		}
		if(typeInfo == null) {
			JNative.getLogger().log(SEVERITY.DEBUG, guid + " has no typeLib");
		} else {
			try  {
				//Get each Subkey under the CLSID subkey
				// We should be able to get version information
				int dwIndex = 0;
				RegKey lKey = Advapi32.RegEnumKeyEx(typeInfo, dwIndex++, null);
				while( lKey.getErrorCode() == 0) {
					//Get the version information
					String version = lKey.getLpValueName().getAsString();
					try {
						if(version.indexOf('.') != -1) {
							majorVersion.add(Integer.parseInt(version.substring(0, version.indexOf('.'))));
							minorVersion.add(Integer.parseInt(version.substring(1 + version.indexOf('.'))));
						} else {
							majorVersion.add(Integer.parseInt(version));
						}
					} catch (NumberFormatException e) {
						JNative.getLogger().log("Guid : "+ guid +", TypeLib : "+typeLib +" has no version information !!!");
						throw new TypeLibNotFoundException();
					}
					lKey = Advapi32.RegEnumKeyEx(typeInfo, dwIndex++, null);
				}
				
			} finally {
				if(typeInfo != null) {
					Advapi32.RegCloseKey(typeInfo);
				}
			}
		}
	}

	public String toString() {
		return 
		guid + "-" + 
		control + "-" + 
		description + "-" + 
		progID + "-" + 
		inprocServer32; 
	}
	public String getGuid() {
		return guid;
	}

	public boolean isControl() {
		return control;
	}

	public String getDescription() {
		return description;
	}

	public String getProgID() {
		return progID;
	}

	public String getInprocServer32() {
		return inprocServer32;
	}

	public void loadRegType() throws NativeException, IllegalAccessException {
		if(majorVersion.size() == 1 && minorVersion.size() < 2) {
//			Pointer pTypeLib = Oleaut32.LoadRegTypeLib(Ole32.CLSIDFromString(getGuid()).getPointer(), majorVersion.get(0), minorVersion.get(0), 0);
			
		} else {
			throw new IllegalArgumentException("Multi-version activex are not supported yet");
		}
		
	}

}
