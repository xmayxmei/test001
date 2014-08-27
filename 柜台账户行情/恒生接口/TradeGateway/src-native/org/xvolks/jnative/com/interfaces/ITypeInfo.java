package org.xvolks.jnative.com.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.xvolks.jnative.Convention;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.com.interfaces.structures.FUNCDESC;
import org.xvolks.jnative.com.interfaces.structures.TYPEATTR;
import org.xvolks.jnative.com.interfaces.structures.VARDESC;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.pointers.memory.NativeMemoryBlock;
import org.xvolks.jnative.util.Kernel32;
import org.xvolks.jnative.util.Kernel32.CodePage;
import org.xvolks.jnative.util.constants.WinError;
import org.xvolks.jnative.util.ole.Oleaut32;

public class ITypeInfo extends IUnknown {

	public ITypeInfo(int address) {
		super(address);
	}

	/**
	 *  GetNames
	 *  <pre>
	 *  Retrieves the variable with the specified member ID or the name of the property or method and the parameters that correspond to the specified function ID.

HRESULT GetNames(
  MEMBERID  memid,
  BSTR FAR  *rgBstrNames,
  unsigned int  cMaxNames,
  unsigned int FAR  *pcNames
);

 Parameters

memid

    The ID of the member whose name (or names) is to be returned. 
rgBstrNames

    Pointer to the caller-allocated array. On return, each of the BSTR elements contains the name (or names) associated with the member. 
cMaxNames

    Length of the passed-in rgBstrNamesarray. 
pcNames

    On return, points to the number of names in the rgBstrNamesarray. 
</pre>

	 * @param dispId
	 * @param count
	 * @return
	 * @throws IllegalAccessException 
	 * @throws NativeException 
	 */
	public List<String> GetNames(int memid, int cMaxNames) throws NativeException, IllegalAccessException {
		List<String> names = new ArrayList<String>(cMaxNames);
		int address = getVtblPointer(7);
		JNative GetDocumentation = new JNative(address, Convention.STDCALL);
		GetDocumentation.setRetVal(Type.INT);
		int pos = 0;
		Pointer rgBstrNames = new Pointer(MemoryBlockFactory.createMemoryBlock(cMaxNames*4));
		LONG pcNames = new LONG(0);
		GetDocumentation.setParameter(pos++, getPIUnknown());
		GetDocumentation.setParameter(pos++, memid);
		GetDocumentation.setParameter(pos++, rgBstrNames.getPointer());
		GetDocumentation.setParameter(pos++, cMaxNames);
		GetDocumentation.setParameter(pos++, pcNames.getPointer());
		GetDocumentation.invoke();
		if(GetDocumentation.getRetValAsInt() == WinError.S_OK) {
			for (int i = 0; i < pcNames.getValue(); i++) {
				int asInt = rgBstrNames.getAsInt(4*i);
				names.add(getBSTRString(asInt));
			}
		}
		return names;
		
	}
	
	public class Documentation {
		private final int index;
		private final String name;
		private final String doc;
		private final int helpContext;
		private final String helpFile;
		public Documentation(int index, String name, String doc, int helpContext, String helpFile) {
			super();
			this.name = name;
			this.index = index;
			this.doc = doc;
			this.helpContext = helpContext;
			this.helpFile = helpFile;
		}
		public int getIndex() {
			return index;
		}
		public String getName() {
			return name;
		}
		public String getDoc() {
			return doc;
		}
		public int getHelpContext() {
			return helpContext;
		}
		public String getHelpFile() {
			return helpFile;
		}
	}
	
	/**
	 * <b>GetDocumentation</b>
	 * <pre>
	 * 
	 *  HRESULT GetDocumentation(
	 *    MEMBERID  memid,
  	 *    BSTR FAR  *pBstrName,
	 *    BSTR FAR  *pBstrDocString,
	 *    unsigned long FAR  *pdwHelpContext,
	 *    BSTR FAR  *pBstrHelpFile
	 *  );
	 *  
	 * Parameters

memid

    ID of the member whose documentation is to be returned. 
pBstrName

    Pointer to a BSTR allocated by the callee into which the name of the specified item is placed. If the caller does not need the item name, pBstrName can be Null. 
pBstrDocString

    Pointer to a BSTR into which the documentation string for the specified item is placed. If the caller does not need the documentation string, pBstrDocString can be Null. 
pdwHelpContext

    Pointer to the Help context associated with the specified item. If the caller does not need the Help context, the pdwHelpContextcan be Null. 
pBstrHelpFile

    Pointer to a BSTR into which the fully qualified name of the Help file is placed. If the caller does not need the Help file name, pBstrHelpFile can be Null. 

</pre>
	 * @param index
	 * @throws IllegalAccessException 
	 * @throws NativeException 
	 */
	public Documentation GetDocumentation(int index) throws NativeException, IllegalAccessException {
		int address = getVtblPointer(12);
		JNative GetDocumentation = new JNative(address, Convention.STDCALL);
		GetDocumentation.setRetVal(Type.INT);
		int pos = 0;
		LONG pBstrName = new LONG(0);
		LONG pBstrDocString = new LONG(0);
		LONG pdwHelpContext = new LONG(0);
		LONG pBstrHelpFile = new LONG(0);
		GetDocumentation.setParameter(pos++, getPIUnknown());
		GetDocumentation.setParameter(pos++, index);
		GetDocumentation.setParameter(pos++, pBstrName.getPointer());
		GetDocumentation.setParameter(pos++, pBstrDocString.getPointer());
		GetDocumentation.setParameter(pos++, pdwHelpContext.getPointer());
		GetDocumentation.setParameter(pos++, pBstrHelpFile.getPointer());
		GetDocumentation.invoke();
		if(GetDocumentation.getRetValAsInt() == WinError.S_OK) {
			String name = getBSTRString(pBstrName);
			String doc = getBSTRString(pBstrDocString);
			int helpContext = pdwHelpContext.getValue();
			String helpFile = getBSTRString(pBstrHelpFile);
			return new Documentation(index, name, doc, helpContext, helpFile);
		} else {
			return null;
		}
	}

	private String getBSTRString(LONG pBstrValue) throws NativeException, IllegalAccessException {
		return getBSTRString(pBstrValue.getValue());
	}
	private String getBSTRString(int pBstrValue) throws NativeException, IllegalAccessException {
		String value = null;
		if(pBstrValue != 0) {
			//Beware where you put your feet 1024 is perhaps not enough but in most cases will be too big
			Pointer bstr = new Pointer(new NativeMemoryBlock(pBstrValue, 1024));
			int size = Oleaut32.SysStringByteLen(bstr);
			if (size > 0) {
				value = Kernel32.WideCharToMultiByte(CodePage.CP_UTF8, 0, bstr, size, 1024);
				// get the unicode character array from the global memory and create a String
//				byte[] buffer = bstr.getMemory(); //new char[(size + 1) /2]; // add one to avoid rounding errors
//				value = new String(buffer, 0, (size + 1) /2);
//					COM.MoveMemory(buffer, pBstrName[0], size);
//					name[0] = new String(buffer);
//				int subindex = value.indexOf("\0");
//				if (subindex > 0)
//					value = value.substring(0, subindex);
			}
			Oleaut32.SysFreeString(bstr);
		}
		return value;
	}
	
	public VARDESC GetVarDesc(int index) throws NativeException, IllegalAccessException {
		int address = getVtblPointer(6);
		JNative GetVarDesc = new JNative(address, Convention.STDCALL);
		GetVarDesc.setRetVal(Type.INT);
		LONG addr = new LONG(0);
		GetVarDesc.setParameter(0, getPIUnknown());
		GetVarDesc.setParameter(1, index);
		GetVarDesc.setParameter(2, addr.getPointer());
		GetVarDesc.invoke();
		if(GetVarDesc.getRetValAsInt() == WinError.S_OK) {
			return new VARDESC(this, addr).getValueFromPointer();
		} else {
			JNative.getLogger().log(SEVERITY.WARN, String.format("GetFuncDesc returned %08x", GetVarDesc.getRetValAsInt()));
			return null;
		}
	}
	public FUNCDESC GetFuncDesc(int index) throws NativeException, IllegalAccessException {
		int address = getVtblPointer(5);
		JNative GetFuncDesc = new JNative(address, Convention.STDCALL);
		GetFuncDesc.setRetVal(Type.INT);
		LONG addr = new LONG(0);
		GetFuncDesc.setParameter(0, getPIUnknown());
		GetFuncDesc.setParameter(1, index);
		GetFuncDesc.setParameter(2, addr.getPointer());
		GetFuncDesc.invoke();
		if(GetFuncDesc.getRetValAsInt() == WinError.S_OK) {
			return new FUNCDESC(this, addr).getValueFromPointer();
		} else {
			JNative.getLogger().log(SEVERITY.WARN, String.format("GetFuncDesc returned %08x", GetFuncDesc.getRetValAsInt()));
			return null;
		}
	}
	
	public TYPEATTR GetTypeAttr() throws NativeException, IllegalAccessException {
		int address = getVtblPointer(3);
		JNative GetTypeAttr = new JNative(address, Convention.STDCALL);
		GetTypeAttr.setRetVal(Type.INT);
		LONG addr = new LONG(0);
		GetTypeAttr.setParameter(0, getPIUnknown());
		GetTypeAttr.setParameter(1, addr.getPointer());
		GetTypeAttr.invoke();
		TYPEATTR attr = new TYPEATTR(this, addr);
		if(GetTypeAttr.getRetValAsInt() == WinError.S_OK) {
			System.err.println(String.format("Reading object at address : %08x", attr.getPointer().getPointer()));
			return attr.getValueFromPointer();
		} else {
			return null;
		}
	}
	
	public void ReleaseTypeAttr(TYPEATTR addr) throws NativeException, IllegalAccessException {
		int address = getVtblPointer(19);
		JNative ReleaseTypeAttr = new JNative(address, Convention.STDCALL);
		ReleaseTypeAttr.setRetVal(Type.VOID);
		ReleaseTypeAttr.setParameter(0, getPIUnknown());
		ReleaseTypeAttr.setParameter(1, addr.getPointer().getPointer());
		ReleaseTypeAttr.invoke();
	}

	public void ReleaseFuncDesc(FUNCDESC funcdesc) throws NativeException, IllegalAccessException {
		int address = getVtblPointer(20);
		JNative ReleaseFuncDesc = new JNative(address, Convention.STDCALL);
		ReleaseFuncDesc.setRetVal(Type.VOID);
		ReleaseFuncDesc.setParameter(0, getPIUnknown());
		ReleaseFuncDesc.setParameter(1, funcdesc.getPointer().getPointer());
		ReleaseFuncDesc.invoke();
	}

	public void ReleaseVarDesc(VARDESC vardesc) throws NativeException, IllegalAccessException {
		int address = getVtblPointer(21);
		JNative ReleaseVarDesc = new JNative(address, Convention.STDCALL);
		ReleaseVarDesc.setRetVal(Type.VOID);
		ReleaseVarDesc.setParameter(0, getPIUnknown());
		ReleaseVarDesc.setParameter(1, vardesc.getPointer().getPointer());
		ReleaseVarDesc.invoke();
	}

}
