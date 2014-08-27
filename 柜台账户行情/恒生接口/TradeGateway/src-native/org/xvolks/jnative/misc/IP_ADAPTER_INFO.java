package org.xvolks.jnative.misc;

import java.util.LinkedList;
import java.util.List;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.basicStructures.DWORD;
import org.xvolks.jnative.misc.basicStructures.time_t;
import org.xvolks.jnative.pointers.NullPointer;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.pointers.memory.NativeMemoryBlock;

/**
 * <pre>
 * typedef struct _IP_ADAPTER_INFO {
  struct _IP_ADAPTER_INFO* Next;                        // 4
  DWORD ComboIndex;										// 4
  char AdapterName[MAX_ADAPTER_NAME_LENGTH+4];        	// 256 +4 = 260
  char Description[MAX_ADAPTER_DESCRIPTION_LENGTH+4];  	// 128 +4 = 132 
  UINT AddressLength; 									// 4
  BYTE Address[MAX_ADAPTER_ADDRESS_LENGTH]; 			// 8 
  DWORD Index;											// 4
  UINT Type; 											// 4
  UINT DhcpEnabled; 									// 4 
  PIP_ADDR_STRING CurrentIpAddress; 					// 4
  IP_ADDR_STRING IpAddressList; 						// 40
  IP_ADDR_STRING GatewayList;							// 40
  IP_ADDR_STRING DhcpServer;							// 40
  BOOL HaveWins;										// 2
  IP_ADDR_STRING PrimaryWinsServer;						// 40
  IP_ADDR_STRING SecondaryWinsServer;					// 40
  time_t LeaseObtained; 								// 4
  time_t LeaseExpires; 									// 4
} IP_ADAPTER_INFO, *PIP_ADAPTER_INFO;

 * </pre>
 * @author mdt
 *
 */
public class IP_ADAPTER_INFO extends AbstractBasicData<IP_ADAPTER_INFO> {

	public static final int MAX_ADAPTER_ADDRESS_LENGTH 		= 8;
	public static final int MAX_ADAPTER_DESCRIPTION_LENGTH 	= 128;
	public static final int MAX_ADAPTER_NAME_LENGTH 		= 256;

	private int Next;                        	// 4
	private DWORD ComboIndex = new DWORD(0); 	// 4
	private String AdapterName;        			// 256 +4 = 260
	private String Description;  				// 128 +4 = 132 
	private int AddressLength; 					// 4
	private byte[] Address = new byte[MAX_ADAPTER_ADDRESS_LENGTH]; 	// 8 
	private DWORD Index = new DWORD(0);			// 4
	private int Type; 							// 4
	private int DhcpEnabled; 					// 4 
	private final Pointer CurrentIpAddress = NullPointer.NULL; // 4
	private IP_ADDR_STRING IpAddressList; 		// 40
	private IP_ADDR_STRING GatewayList;			// 40
	private IP_ADDR_STRING DhcpServer;			// 40
	private boolean HaveWins;					// 4
	private IP_ADDR_STRING PrimaryWinsServer;	// 40
	private IP_ADDR_STRING SecondaryWinsServer;	// 40
	private time_t LeaseObtained = new time_t(0); 	// 4
	private time_t LeaseExpires = new time_t(0); 	// 4
	
	public IP_ADAPTER_INFO() {
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

	private IP_ADAPTER_INFO(int address) {
		super(null);
		pointer = new Pointer(new NativeMemoryBlock(address, getSizeOf()));
	}
	public static IP_ADAPTER_INFO fromAddress(int address) throws NativeException {
		return new IP_ADAPTER_INFO(address).getValueFromPointer();
	}
	private IP_ADAPTER_INFO(boolean dummy) {
		super(null);
		if(dummy != false) {
			throw new IllegalArgumentException("Dummy must be false, AH! Ah! AH!");
		}
	}
	/**
	 * Creates a Memory place to call GetAdaptersInfo, then you need to get the list Of IP_ADAPTER_INFO
	 * from getListOf_IP_ADAPTER_INFO
	 * @param size size of the Array of IP_ADAPTER_INFO
	 * @return a NullPointer if the size < 1
	 * @throws NativeException 
	 */
	public static Pointer reserveMemoryOf_IP_ADAPTER_INFO(int size) throws NativeException {
		if(size<1) {
			return NullPointer.NULL;
		} else {
			return new Pointer(MemoryBlockFactory.createMemoryBlock(size * new IP_ADAPTER_INFO(false).getSizeOf()));
		}
	}
	/**
	 * Create a list of IP_ADAPTER_INFO from a pointer (see createArrayOf_IP_ADAPTER_INFO)
	 * @param p the native memory containing the structures
	 * @return the List
	 * @throws NativeException 
	 */
	public static List<IP_ADAPTER_INFO> getListOf_IP_ADAPTER_INFO(Pointer p) throws NativeException {
		IP_ADAPTER_INFO ip_adapter_info = new IP_ADAPTER_INFO(false);
		int size = p.getSize() / ip_adapter_info.getSizeOf();
		if(size * ip_adapter_info.getSizeOf() != p.getSize()) {
			throw new IllegalArgumentException("The pointer passed in has not a IP_ADAPTER_INFO size boundary "+p.getSize()+" vs. "+ip_adapter_info.getSizeOf()+". Call the other method if you are sure of what you are doing.");
		} else {
			return getListOf_IP_ADAPTER_INFO(p, size); 
		}
	}

	/**
	 * This method is potentially unsafe because it only checks that the pointer addresses
	 * a memory region that is at least equal size of size * sizeof(IP_ADAPTER_INFO) 
	 * @param p the native memory containing the structures
	 * @param size : max. number of IP_ADAPTER_INFO structures
	 * @return the list
	 * @throws NativeException 
	 */
	public static List<IP_ADAPTER_INFO> getListOf_IP_ADAPTER_INFO(Pointer p, int size) throws NativeException {
		IP_ADAPTER_INFO ip_adapter_info = new IP_ADAPTER_INFO(false);
		int totalSize = size*ip_adapter_info.getSizeOf();
		if(p.getSize() < totalSize) {
			throw new IllegalArgumentException("This pointer is addressing a too small memory region, size is "+p.getSize()+", needed size is "+totalSize);
		}
		
		List<IP_ADAPTER_INFO> array = new LinkedList<IP_ADAPTER_INFO>();
		int address = p.getPointer();
		for(int i = 0; address != 0 &&  i<size; i++) {
			ip_adapter_info = fromAddress(address);
			array.add(ip_adapter_info);
			address = ip_adapter_info.Next;
		}
		return array;
	}

	public Pointer createPointer() throws NativeException {
        pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
        return pointer;
	}

	public int getSizeOf() {
		return 4  + 4  + 260  + 132   + 4  + 8   + 4  + 4  + 4   + 4  + 40  + 40  + 40  + 4  + 40  + 40  + 4  + 4;
	}

	private String getString(byte[] buffer, int pos, int len) {
		String s = new String(buffer, pos, len);
		int pos0 = s.indexOf('\0');
		if(pos != -1)
			s = s.substring(0, pos0);
		return s;
	}
	
	public IP_ADAPTER_INFO getValueFromPointer() throws NativeException {
		int pos = 0;
		byte[] mem = pointer.getMemory();
		Next = pointer.getAsInt(pos); 												pos += 4;
		ComboIndex.setValue(pointer.getAsInt(pos));									pos += 4;
		AdapterName = getString(mem, pos, MAX_ADAPTER_NAME_LENGTH + 4); 			pos += MAX_ADAPTER_NAME_LENGTH + 4;
		Description = getString(mem, pos, MAX_ADAPTER_DESCRIPTION_LENGTH + 4); 		pos += MAX_ADAPTER_DESCRIPTION_LENGTH + 4;
		AddressLength = pointer.getAsInt(pos); 										pos += 4;
		System.arraycopy(mem, pos, Address, 0, AddressLength); 						pos += MAX_ADAPTER_ADDRESS_LENGTH;//AddressLength;
		Index.setValue(pointer.getAsInt(pos)); 										pos += 4;
		Type = pointer.getAsInt(pos); 												pos += 4;
		DhcpEnabled = pointer.getAsInt(pos); 										pos += 4;
		/*CurrentIpAddress = NullPointer.NULL;*/									pos += 4;
		IpAddressList = IP_ADDR_STRING.fromAddress(pointer.getPointer() + pos); 	pos += IpAddressList.getSizeOf(); 
		GatewayList = IP_ADDR_STRING.fromAddress(pointer.getPointer() + pos); 		pos += GatewayList.getSizeOf(); 
		DhcpServer = IP_ADDR_STRING.fromAddress(pointer.getPointer() + pos); 		pos += DhcpServer.getSizeOf(); 
		HaveWins = pointer.getAsInt(pos) != 0; 										pos += 4;

		PrimaryWinsServer = IP_ADDR_STRING.fromAddress(pointer.getPointer() + pos); pos += PrimaryWinsServer.getSizeOf(); 
		SecondaryWinsServer =IP_ADDR_STRING.fromAddress(pointer.getPointer() + pos);pos += SecondaryWinsServer.getSizeOf();
		
		LeaseObtained = new time_t(pointer.getAsInt(pos));							pos += 4;
		LeaseExpires  = new time_t(pointer.getAsInt(pos));
		
		return this;
	}

	public int getNext() {
		return Next;
	}

	public DWORD getComboIndex() {
		return ComboIndex;
	}

	public String getAdapterName() {
		return AdapterName;
	}

	public String getDescription() {
		return Description;
	}

	public int getAddressLength() {
		return AddressLength;
	}

	public String getAddressAsWindowsFormat() {
		return String.format("%02X-%02X-%02X-%02X-%02X-%02X", Address[0], Address[1], Address[2], Address[3], Address[4], Address[5]);
	}
	public String getAddressAsUnixFormat() {
		return String.format("%02x:%02x:%02x:%02x:%02x:%02x", Address[0], Address[1], Address[2], Address[3], Address[4], Address[5]);
	}
	public byte[] getAddress() {
		return Address;
	}

	public DWORD getIndex() {
		return Index;
	}

	public int getType() {
		return Type;
	}

	public int getDhcpEnabled() {
		return DhcpEnabled;
	}

	public Pointer getCurrentIpAddress() {
		return CurrentIpAddress;
	}

	public IP_ADDR_STRING getIpAddressList() {
		return IpAddressList;
	}

	public IP_ADDR_STRING getGatewayList() {
		return GatewayList;
	}

	public IP_ADDR_STRING getDhcpServer() {
		return DhcpServer;
	}

	public boolean isHaveWins() {
		return HaveWins;
	}

	public IP_ADDR_STRING getPrimaryWinsServer() {
		return PrimaryWinsServer;
	}

	public IP_ADDR_STRING getSecondaryWinsServer() {
		return SecondaryWinsServer;
	}

	public time_t getLeaseObtained() {
		return LeaseObtained;
	}

	public time_t getLeaseExpires() {
		return LeaseExpires;
	}
	
	

}
