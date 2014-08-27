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
 * <pre>
The VARDESC structure is used by an ITypeInfo server or ITypeComp server to describe a data member, constant, or ODL dispinterface property, as specified in sections 3.5.4.1 and 3.7.4.4.

typedef struct tagVARDESC {
  MEMBERID memid;
  LPOLESTR lpstrReserved;
  [switch_typVARKIND ), switch_is(varkind)] 
    union {
    [case(VAR_PERINSTANCE, VAR_DISPATCH, VAR_STATIC)]
      ULONG oInst;
    [case(VAR_CONST)]
      VARIANT* lpVarValue;
  } _vdUnion;
  ELEMDESC elemdescVar;
  WORD wVarFlags;
  VARKIND varkind;
} VARDESC, 
 *LPVARDESC;

memid: MUST be set to the MEMBERID (section 2.2.35) of the data member, the constant, or the ODL dispinterface property. MUST be set to MEMBERID_DEFAULTINST if the VARDESC describes an appobject coclass, as specified in section 2.2.49.8

lpstrReserved: MUST be set to NULL, and MUST be ignored by the recipient.

_vdUnion: MUST be set to an instance of the type, according to the value in the varkind field.

oInst:

    *

      VAR_PERINSTANCE: MUST be set to an implementation-specific value<22>
    * VAR_DISPATCH: MUST be set to 0.
    * VAR_STATIC: MUST be set to 0.

lpVarValue: MUST be set to a reference to a VARIANT that specifies the value of the constant.

elemdescVar: MUST contain an ELEMDESC that describes the data member, constant, or ODL dispinterface property and its type, as specified in section 2.2.41.

wVarFlags: MUST be set to a combination of the VARFLAGS bit flags (as specified in 2.2.18), or set to 0. MUST be set to 0 if the VARDESC describes an appobject coclass, as specified in section 2.2.49.8.

varkind: MUST be set to a value of the VARKIND enumeration. MUST be set to VAR_STATIC if the VARDESC describes an appobject coclass, as specified in section 2.2.49.8.
</pre>
 * @author mdt
 *
 */
public class VARDESC extends AbstractBasicData<VARDESC> {

	public static enum VARKIND {
		VAR_PERINSTANCE,VAR_STATIC,VAR_CONST,VAR_DISPATCH
	}

	private int memid;
	private int lpstrSchema; //LPOLESTR
	private int oInst; 		//union
//	ELEMDESC elemdescVar
//	TYPEDESC elemdescVar.tdesc
	private int elemdescVar_tdesc_union; //union
	private short elemdescVar_tdesc_vt;
//	PARAMDESC elemdescFunc.paramdesc
	private int elemdescVar_paramdesc_pparamdescex;
	private short elemdescVar_paramdesc_wParamFlags;
	private short wVarFlags;
	private VARKIND varkind;
	
	private ITypeInfo info;
	public VARDESC(ITypeInfo info, LONG address) {
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
	public static int sizeof() {
		return 36;
	}
	public VARDESC getValueFromPointer() throws NativeException {
		int pos = 0;
		memid = pointer.getAsInt(pos); 							pos += 4;
		lpstrSchema = pointer.getAsInt(pos); 					pos += 4;
		oInst = pointer.getAsInt(pos); 							pos += 4;
		elemdescVar_tdesc_union = pointer.getAsInt(pos); 		pos += 4;
		elemdescVar_tdesc_vt = pointer.getAsShort(pos); 		pos += 2;
		elemdescVar_paramdesc_pparamdescex= 
			pointer.getAsInt(pos); 								pos += 4;
		elemdescVar_paramdesc_wParamFlags = 
			pointer.getAsShort(pos);							pos += 2;
		wVarFlags = pointer.getAsShort(pos);					pos += 2;
		varkind = VARKIND.values()[pointer.getAsShort(pos)];	pos += 2;
		return this;
	}

	public int getMemid() {
		return memid;
	}

	public int getLpstrSchema() {
		return lpstrSchema;
	}

	public int getOInst() {
		return oInst;
	}

	public int getElemdescVar_tdesc_union() {
		return elemdescVar_tdesc_union;
	}

	public short getElemdescVar_tdesc_vt() {
		return elemdescVar_tdesc_vt;
	}

	public int getElemdescVar_paramdesc_pparamdescex() {
		return elemdescVar_paramdesc_pparamdescex;
	}

	public short getElemdescVar_paramdesc_wParamFlags() {
		return elemdescVar_paramdesc_wParamFlags;
	}

	public short getWVarFlags() {
		return wVarFlags;
	}

	public VARKIND getVarkind() {
		return varkind;
	}

	@Override
	protected void finalize() throws Throwable {
		try { dispose(); } catch (Exception e) { JNative.getLogger().log(SEVERITY.ERROR, e); }
	}
	
	public void dispose() throws NativeException, IllegalAccessException {
		if(info != null) {
			info.ReleaseVarDesc(this);
			info = null;
		}
	}

}
