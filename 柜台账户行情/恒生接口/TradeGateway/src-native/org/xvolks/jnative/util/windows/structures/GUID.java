package org.xvolks.jnative.util.windows.structures;

import java.util.StringTokenizer;
import org.xvolks.jnative.JNative;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.basicStructures.DWORD;
import org.xvolks.jnative.misc.basicStructures.UINT;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

public class GUID extends AbstractBasicData<GUID> {

	public static final GUID GUID_IO_MEDIA_ARRIVAL = new GUID("A5DCBF10-6530-11D2-901F-00C04FB951ED");
	
	private DWORD data1 = new DWORD(0);
	private UINT  data2 = new UINT(0);
	private UINT  data3 = new UINT(0);
	private byte[] data4 = new byte[8];
	
	@Override
	public String toString() {
		return String.format("{%08x-%04x-%04x-%02x%02x-%02x%02x%02x%02x%02x%02x}", data1.getValue(), data2.getValue(), data3.getValue(),
				data4[0],data4[1],data4[2],data4[3],data4[4],data4[5],data4[6],data4[7]
				);
	}
	
	public GUID() {
		this((GUID)null);
	}
	
	public GUID(String value) throws NumberFormatException {
		super(null);
		try {
			createPointer();
			if(value.matches("\\{[\\-0-9a-fA-F]+\\}")) {
				value = value.substring(1, value.length() - 1);
			}
			StringTokenizer st = new StringTokenizer(value, "-");
			data1.setValue((int)Long.parseLong(st.nextToken(), 16));
			data2.setValue(Integer.parseInt(st.nextToken(), 16));
			data3.setValue(Integer.parseInt(st.nextToken(), 16));
			String l1 = st.nextToken();
			JNative.getLogger().log(l1);
			String l2 = st.nextToken();
			JNative.getLogger().log(l2);
			data4[0] = (byte)Short.parseShort(l1.substring(0, 2), 16);
			data4[1] = (byte)Short.parseShort(l1.substring(2), 16);
			for(int i=0; i<6; i++) {
				data4[i + 2] = (byte)Short.parseShort(l2.substring(2*i, 2*i +2), 16);
			}
			setValue(this);
		} catch (NativeException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Copy constructor (copied by values)
	 * @param value
	 */
	public GUID(GUID value) {
		super(value);
		try {
			createPointer();
			if(value != null) {
				data1.setValue(value.data1.getValue());
				data2.setValue(value.data2.getValue());
				data3.setValue(value.data3.getValue());
				System.arraycopy(value.data4, 0, data4, 0, 8);
				setValue(this);
			}
		} catch (NativeException e) {
			throw new RuntimeException(e);
		}
	}

	public Pointer createPointer() throws NativeException {
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
		return pointer;
	}

	public int getSizeOf() {
		return sizeOf();
	}
	public static int sizeOf() {
		return 4 + 2 + 2 + 8;
	}

	public GUID getValueFromPointer() throws NativeException {
		data1.setValue(getNextInt());
		data2.setValue((int)getNextShort());
		data3.setValue((int)getNextShort());
		System.arraycopy(pointer.getMemory(), offset, data4, 0, 8);
		return this;
	}
	
	public void setValue(GUID guid) throws NativeException {
		int offset = 0;
		offset += pointer.setIntAt(offset, guid.data1.getValue());
		offset += pointer.setShortAt(offset, guid.data2.getValueAsShort());
		offset += pointer.setShortAt(offset, guid.data3.getValueAsShort());
		for(int i=0; i<8; i++) {
			offset += pointer.setByteAt(offset, guid.data4[i]);
		}
	}
	
	public void setValue(byte[] src, int offset) throws NativeException {
		byte[] buffer = pointer.getMemory();
		System.arraycopy(src, offset, buffer, 0, 16);
		pointer.setMemory(buffer);
		getValueFromPointer();
	}

	public static GUID fromPointer(Pointer pointer) throws NativeException {
		GUID guid = new GUID();
		guid.pointer = pointer;
		return guid.getValueFromPointer();
	}
}
