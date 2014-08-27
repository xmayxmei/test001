package org.xvolks.jnative.util.windows.structures;

import java.util.ArrayList;
import java.util.List;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.DWORD;
import org.xvolks.jnative.misc.basicStructures.UINT;
import org.xvolks.jnative.pointers.Pointer;

public class DEV_BROADCAST_VOLUME extends DEV_BROADCAST_HDR<DEV_BROADCAST_VOLUME> {

	/** The logical unit mask identifying one or more logical units. Each bit in the mask corresponds to one logical drive. Bit 0 represents drive A, bit 1 represents drive B, and so on. */
	private DWORD dbcv_unitmask = new DWORD(0); 

	/** This parameter can be one of the following values. */
	private UINT dbcv_flags = new UINT(0); 


	public DEV_BROADCAST_VOLUME() {
	}

	public DEV_BROADCAST_VOLUME(Pointer ptr) {
		super(ptr);
		try {
			getValueFromPointer();
		} catch (NativeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public DEV_BROADCAST_HDR<DEV_BROADCAST_VOLUME> getValueFromPointer() throws NativeException {
		super.getValueFromPointer();
		dbcv_unitmask = new DWORD(getNextInt());
		dbcv_flags = new UINT(getNextShort());
		return this;
	}

	/** The logical unit mask identifying one or more logical units. Each bit in the mask corresponds to one logical drive. Bit 0 represents drive A, bit 1 represents drive B, and so on. */
	public DWORD getDbcv_unitmask() {
		return dbcv_unitmask;
	}

	/** This parameter can be one of the following values. */
	public UINT getDbcv_flags() {
		return dbcv_flags;
	}
	
	public List<String> getVolumes() {
		char unit = 'A';
		List<String> volumes = new ArrayList<String>();
		int bitMask = 1;
		int value = getDbcv_unitmask().getValue();
		for(int i=0; i<32; i++) {
			if((bitMask & value) > 0) {
				volumes.add(""+(char)(unit + i));
			}
			bitMask = bitMask << 1;
		}
		return volumes;
	}


}
