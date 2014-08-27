package org.xvolks.jnative.com.interfaces.structures;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.com.interfaces.ITypeInfo;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.pointers.memory.NativeMemoryBlock;

/**
 * <pre class="libCScode" id="ctl00_rs1_mainContentContainer_ctl01other" space="preserve">typedef struct tagFUNCDESC {
   MEMBERID memid;                  // Function member ID.
   SCODE FAR *lprgscode;
   ELEMDESC FAR *lprgelemdescParam;   
   FUNCKIND funckind;               // Specifies whether the    
                                 // function is virtual, static,    
                                 // or dispatch-only.
   INVOKEKIND invkind;      // Invocation kind. Indicates if this is a    
                           // property function, and if so, what kind.
   CALLCONV callconv;      // Specifies the function's calling 
                     // convention.
   short cParams;         // Count of total number of parameters.
   short cParamsOpt;      // Count of optional parameters (detailed 
                     // description follows).
   short oVft;      // For FUNC_VIRTUAL, specifies the offset in the VTBL.
   short cScodes;      // Count of permitted return values. 
   ELEMDESC elemdescFunc;   // Contains the return type of the function.
   WORD wFuncFlags;    // Definition of flags follows.
}   FUNCDESC;
 
</pre></td></tr></tbody></table></div><p>
                    The <i>cParams</i> field specifies the total number of required and optional parameters.
                </p><p>
                    The <i>cParamsOpt</i> field specifies the form of optional parameters accepted by the function, as follows:  
                </p><ul><li><p>

                    A value of 0 specifies that no optional arguments are supported.
                </p></li><li><p>
                    A value of –1 specifies that the method's last parameter is a pointer to a safe array of variants. Any number of variant arguments greater than <i>cParams</i> –1 must be packaged by the caller into a safe array and passed as the final parameter. This array of optional parameters must be freed by the caller after control is returned from the call.
                </p></li><li><p>
                    Any other number indicates that the last <i>n</i> parameters of the function are variants and do not need to be specified by the caller explicitly. The parameters left unspecified should be filled in by the compiler or interpreter as variants of type VT_ERROR with the value DISP_E_PARAMNOTFOUND.
                </p>
 * @author Marc DENTY
 *
 */
public class FUNCDESC extends AbstractBasicData<FUNCDESC> {

	public static enum CALLCONV {
		CC_FASTCALL(0),
		CC_CDECL(1),
		CC_MSCPASCAL(2), CC_PASCAL(CC_MSCPASCAL.getValue()),
		CC_MACPASCAL(3),
		CC_STDCALL(4),
		CC_FPFASTCALL(5),
		CC_SYSCALL(6),
		CC_MPWCDECL(7),
		CC_MPWPASCAL(8), CC_MAX(CC_MPWPASCAL.getValue())
		;
		private final int value;
		private CALLCONV(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
		public static CALLCONV fromInt(int i) {
			for(CALLCONV conv : values()) {
				if(i == conv.value) {
					return conv;
				}
			}
			throw new IllegalArgumentException("this CALLCONV is unknown : " + i);
		}
	}
	public static enum FUNCKIND {
		FUNC_VIRTUAL,FUNC_PUREVIRTUAL,FUNC_NONVIRTUAL,
		FUNC_STATIC,FUNC_DISPATCH;
		
		public static FUNCKIND fromInt(int i) {
			for(FUNCKIND kind : values()) {
				if(i == kind.ordinal()) {
					return kind;
				}
			}
			throw new IllegalArgumentException("this FUNCKIND is unknown : " + i);
		}
		
	}
	public static enum INVOKEKIND {
		INVOKE_FUNC(1),
		INVOKE_PROPERTYGET(2),
		INVOKE_PROPERTYPUT(4),
		INVOKE_PROPERTYPUTREF(8)
		;
		private final int value;
		private INVOKEKIND(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
		public static INVOKEKIND fromInt(int i) {
			for(INVOKEKIND kind : values()) {
				if(i == kind.value) {
					return kind;
				}
			}
			throw new IllegalArgumentException("this INVOKEKIND is unknown : " + i);
		}
		
		public String toString() {
			switch (this) {
			case INVOKE_FUNC:
				return "METHOD";

			case INVOKE_PROPERTYGET:
				return "PROPERTY GET";

			case INVOKE_PROPERTYPUT:
				return "PROPERTY PUT";

			case INVOKE_PROPERTYPUTREF:
				return "PROPERTY PUT BY REF";
			}

			return "unknown " + name();
		}

	}
	

	int memid;                  
	int lprgscode; // pointer			
	int lprgelemdescParam;	//ELEMDESC (14)
	FUNCKIND funckind;               
	INVOKEKIND invkind;      		
	CALLCONV callconv;      			
	int cParams;         			
	int cParamsOpt;      			
	int oVft;      				
	int cScodes;
	int elemDesc_typeDesc_desc_union;
	short elemDesc_typeDesc_desc_vt;
	int idldesc_paramdesc_dwReserved_pparamdescex;
	short idldesc_paramdesc_wIDLFlags_wParamFlags;
//	ELEMDESC elemdescFunc;   		
	int wFuncFlags;	
	
	ITypeInfo info;
	public FUNCDESC(ITypeInfo info, LONG address) {
		super(null);
		this.info = info;
		pointer = new Pointer(new NativeMemoryBlock(address.getValue(), getSizeOf()));		
	}

	public Pointer createPointer() throws NativeException {
		pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));		
		return pointer;
	}

	public int getSizeOf() {
		return sizeof();
	}
	
	/**
   MEMBERID memid;                  4
   SCODE FAR *lprgscode;			4
   ELEMDESC FAR *lprgelemdescParam;	4  
   FUNCKIND funckind;               4    
   INVOKEKIND invkind;      		4    
   CALLCONV callconv;      			4 
   short cParams;         			2
   short cParamsOpt;      			2 
   short oVft;      				2
   short cScodes;      				2 
   ELEMDESC elemdescFunc;   		8 + 6
   WORD wFuncFlags;    				2

	 * @return
	 */
	public static int sizeof() {
		return 6*4 + 4*2 + 8 + 8 + 2 +  2 /* alignement ? */;
	}

	public FUNCDESC getValueFromPointer() throws NativeException {
		int pos = 0;
//		System.err.println(StringToolkit.toHexString(pointer.getMemory()));

		memid = pointer.getAsInt(pos); 							pos += 4;
		lprgscode = pointer.getAsInt(pos); 						pos += 4;
		lprgelemdescParam = pointer.getAsInt(pos); 				pos += 4;
		funckind = FUNCKIND.fromInt(pointer.getAsInt(pos)); 	pos += 4;
		invkind = INVOKEKIND.fromInt(pointer.getAsInt(pos)); 	pos += 4;
		callconv= CALLCONV.fromInt(pointer.getAsInt(pos)); 		pos += 4;
		cParams = pointer.getAsShort(pos);						pos += 2;
		cParamsOpt = pointer.getAsShort(pos);					pos += 2;
		oVft = pointer.getAsShort(pos);							pos += 2;
		cScodes = pointer.getAsShort(pos);						pos += 2;
		elemDesc_typeDesc_desc_union = pointer.getAsInt(pos); 	pos += 4;
		elemDesc_typeDesc_desc_vt = pointer.getAsShort(pos); 	pos += 4; //ELEMDESC is 8
		idldesc_paramdesc_dwReserved_pparamdescex = pointer.getAsInt(pos); 	pos += 4;
		idldesc_paramdesc_wIDLFlags_wParamFlags = pointer.getAsShort(pos); 	pos += 4; //IDLDESC is 8
		wFuncFlags = pointer.getAsShort(pos);
		return this;
	}

	public int getMemid() {
		return memid;
	}

	public int getLprgscode() {
		return lprgscode;
	}

	public int getLprgelemdescParam() {
		return lprgelemdescParam;
	}

	public FUNCKIND getFunckind() {
		return funckind;
	}

	public INVOKEKIND getInvkind() {
		return invkind;
	}

	public CALLCONV getCallconv() {
		return callconv;
	}

	public int getCParams() {
		return cParams;
	}

	public int getCParamsOpt() {
		return cParamsOpt;
	}

	public int getOVft() {
		return oVft;
	}

	public int getCScodes() {
		return cScodes;
	}


	public int getElemDesc_typeDesc_desc_union() {
		return elemDesc_typeDesc_desc_union;
	}

	public short getElemDesc_typeDesc_desc_vt() {
		return elemDesc_typeDesc_desc_vt;
	}

	public int getIdldesc_paramdesc_dwReserved_pparamdescex() {
		return idldesc_paramdesc_dwReserved_pparamdescex;
	}

	public short getIdldesc_paramdesc_wIDLFlags_wParamFlags() {
		return idldesc_paramdesc_wIDLFlags_wParamFlags;
	}

	public int getWFuncFlags() {
		return wFuncFlags;
	}
	
	@Override
	protected void finalize() throws Throwable {
		try { dispose(); } catch (Exception e) { JNative.getLogger().log(SEVERITY.ERROR, e); }
	}
	
	public void dispose() throws NativeException, IllegalAccessException {
		if(info != null) {
			info.ReleaseFuncDesc(this);
			info = null;
		}
	}
}
