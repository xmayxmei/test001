package org.xvolks.jnative.misc;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.pointers.memory.NativeMemoryBlock;

/**
 * <pre>
 *
typedef struct {
  char String[16];
} IP_ADDRESS_STRING, *PIP_ADDRESS_STRING, IP_MASK_STRING, *PIP_MASK_STRING;
 
typedef struct _IP_ADDR_STRING {
  struct _IP_ADDR_STRING* Next;			// 4
  IP_ADDRESS_STRING IpAddress;			// 16
  IP_MASK_STRING IpMask;				// 16 
  DWORD Context;						// 4
} IP_ADDR_STRING, *PIP_ADDR_STRING;
 *	</pre>
 * @author mdt
 *
 */
public class IP_ADDR_STRING extends AbstractBasicData<IP_ADDR_STRING> {

	int next;
	String IpAddress;
	String IpMask;
	int Context;
	
	protected IP_ADDR_STRING() {
        super(null);
        try
        {
            createPointer();
        }
        catch (NativeException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
	}
	
	private IP_ADDR_STRING(int address) {
		super(null);
        pointer = new Pointer(new NativeMemoryBlock(address, getSizeOf()));
	}
	private IP_ADDR_STRING(Pointer pointer) {
		super(null);
		this.pointer = pointer;
	}
	public static IP_ADDR_STRING fromAddress(int address) throws NativeException {
		return new IP_ADDR_STRING(address).getValueFromPointer();
	}
	public static IP_ADDR_STRING fromPointer(Pointer pointer) throws NativeException {
		return new IP_ADDR_STRING(pointer).getValueFromPointer();
	}

	public Pointer createPointer() throws NativeException {
        pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
        return pointer;
	}

	public int getSizeOf() {
		return 4+16+16+4;
	}
	private String getString(byte[] buffer, int pos, int len) {
		String s = new String(buffer, pos, len);
		int pos0 = s.indexOf('\0');
		if(pos != -1)
			s = s.substring(0, pos0);
		return s;
	}

	public IP_ADDR_STRING getValueFromPointer() throws NativeException {
		next = pointer.getAsInt(0);
		byte[] buffer = pointer.getMemory();
		IpAddress = getString(buffer, 4, 16);
		IpMask = getString(buffer, 4+16, 16);
		Context = pointer.getAsInt(4+16+16);
		return this;
	}

	public int getNext() {
		return next;
	}

	public String getIpAddress() {
		return IpAddress;
	}

	public String getIpMask() {
		return IpMask;
	}

	public int getContext() {
		return Context;
	}

}
