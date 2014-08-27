package org.xvolks.jnative.util.windows.structures;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.DWORD;
import org.xvolks.jnative.misc.basicStructures.HANDLE;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

public class DEV_BROADCAST_HANDLE extends DEV_BROADCAST_HDR<DEV_BROADCAST_HANDLE> {

	private HANDLE	dbch_handle = new HANDLE(0);
	private HANDLE	dbch_hdevnotify = new HANDLE(0);
	private LONG 	dbch_nameoffset = new LONG(0);
	private GUID 	dbch_eventguid = new GUID();
	private LONG 	dbch_data = new LONG(0);

	public DEV_BROADCAST_HANDLE(Pointer pointer) {
		super(pointer);
		try {
			dbch_eventguid = new GUID();
			getValueFromPointer();
		} catch (NativeException e) {
			e.printStackTrace();
		}
	}
	public DEV_BROADCAST_HANDLE(int DBT_DEVTYP, GUID guid) {
		super();
		dbch_devicetype = new DWORD(DBT_DEVTYP);
		dbch_eventguid = new GUID(guid);
		try {
			createPointer();
			int offset = 0;
			offset += pointer.setIntAt(offset, dbch_size.getValue());
			offset += pointer.setIntAt(offset, dbch_devicetype.getValue());
			offset += pointer.setIntAt(offset, dbch_reserved.getValue());
			offset += pointer.setIntAt(offset, dbch_handle.getValue());
			offset += pointer.setIntAt(offset, dbch_hdevnotify.getValue());
			Pointer pointerOrg = dbch_eventguid.getPointer();
			for(int i = 0; i<16; i++) {
				byte asByte = pointerOrg.getAsByte(i);
				JNative.getLogger().log("GUID("+i+") = " + Integer.toHexString(asByte & 0xFF));
				pointer.setByteAt(offset++, asByte);
			}
			offset += pointer.setIntAt(offset, dbch_nameoffset.getValue());
			offset += pointer.setByteAt(offset, dbch_data.getValue().byteValue());
		} catch (NativeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Pointer createPointer() throws NativeException {
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
		return pointer;
	}

	public static int getSize() {
		return 6 * 4 +16+1;
	}
	
	@Override
	public int getSizeOf() {
		return dbch_size.getValue();
	}

	@Override
	public DEV_BROADCAST_HANDLE getValueFromPointer() throws NativeException {
		offset = 0;
		super.getValueFromPointer();
		dbch_handle.setValue(getNextInt());
		dbch_hdevnotify.setValue(getNextInt());
		dbch_eventguid.setValue(pointer.getMemory(), offset);		offset += 16;
		dbch_nameoffset.setValue(getNextInt());
		dbch_data.setValue(getNextByte());
		return this;
	}

	@Override
	public DEV_BROADCAST_HANDLE getValue() {
		return this;
	}

	public HANDLE getDbch_handle() {
		return dbch_handle;
	}

	public HANDLE  getDbch_hdevnotify() {
		return dbch_hdevnotify;
	}

	public GUID getDbch_eventguid() {
		return dbch_eventguid;
	}

	public LONG getDbch_nameoffset() {
		return dbch_nameoffset;
	}

	public LONG getDbch_data() {
		return dbch_data;
	}

}
