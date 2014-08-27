package org.xvolks.jnative.com.interfaces.structures;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.com.interfaces.ITypeInfo;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.basicStructures.DWORD;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.pointers.memory.NativeMemoryBlock;
import org.xvolks.jnative.util.windows.structures.GUID;


/**
 * This structure is used to hold attributes of an <a id="ctl00_rs1_mainContentContainer_ctl01" onclick="javascript:Track('ctl00_rs1_mainContentContainer_ctl00|ctl00_rs1_mainContentContainer_ctl01',this);" href="http://msdn.microsoft.com/en-us/library/aa909031.aspx">ITypeInfo</a> interface. </span></p> <div id="ctl00_rs1_mainContentContainer_cpe558484" class="MTPS_CollapsibleRegion" xmlns:msxsl="urn:schemas-microsoft-com:xslt">
			<div id="ctl00_rs1_mainContentContainer_cpe558484_h" class="CollapseRegionLink">
				<img id="ctl00_rs1_mainContentContainer_cpe558484_i" src="http://i.msdn.microsoft.com/platform/Controls/CollapsibleArea/resources/minus.gif" style="border-width: 0px; vertical-align: middle;">&nbsp;Syntax
			</div><div style="overflow: visible; display: block; height: auto; width: 909px;" id="ctl00_rs1_mainContentContainer_cpe558484_c" class="MTPS_CollapsibleSection"><div style="display: block;" id="" class="MTPS_CollapsibleSection">
				<a name="syntaxToggle"><!----></a><div class="" id="ctl00_rs1_mainContentContainer_ctl05_other"><table><tbody><tr><td style="border: 0px none ; margin: 0px; padding: 0px;"><div class="CodeSnippetTitleBar"><div class="CodeDisplayLanguage"></div><div class="CopyCodeButton"><a class="copyCode" href="javascript:CopyCode('ctl00_rs1_mainContentContainer_ctl05other');"><img src="http://i.msdn.microsoft.com/platform/Controls/CodeSnippet/resources/copy_off.gif" align="middle" border="0" height="9"> Copy Code</a></div></div></td></tr><tr><td style="border: 0px none ; margin: 0px; padding: 0px;"><pre class="libCScode" id="ctl00_rs1_mainContentContainer_ctl05other" space="preserve"><span><span id="src3" class="srcSentence">

typedef struct FARSTRUCT tagTYPEATTR {
  GUID </span></span><i>guid</i><span><span id="src4" class="srcSentence">;
  LCID </span></span><i>lcid</i><span><span id="src5" class="srcSentence">;
  unsigned long </span></span><i>dwReserved</i><span><span id="src6" class="srcSentence">;
  MEMBERID </span></span><i>memidConstructor</i><span><span id="src7" class="srcSentence">;
  MEMBERID </span></span><i>memidDestructor</i><span><span id="src8" class="srcSentence">;
  OLECHAR FAR* </span></span><i>lpstrSchema</i><span><span id="src9" class="srcSentence">;
  unsigned long </span></span><i>cbSizeInstance</i><span><span id="src10" class="srcSentence">;
  TYPEKIND </span></span><i>typekind</i><span><span id="src11" class="srcSentence">;
  unsigned short </span></span><i>cFuncs</i><span><span id="src12" class="srcSentence">;
  unsigned short </span></span><i>cVars</i><span><span id="src13" class="srcSentence">;
  unsigned short </span></span><i>cImplTypes</i><span><span id="src14" class="srcSentence">;
  unsigned short </span></span><i>cbSizeVft</i><span><span id="src14" class="srcSentence">;
  unsigned short </span></span><i>cbAlignment</i><span><span id="src15" class="srcSentence">;
  unsigned short </span></span><i>wTypeFlags</i><span><span id="src16" class="srcSentence">;
  unsigned short </span></span><i>wMajorVerNum</i><span><span id="src17" class="srcSentence">;
  unsigned short </span></span><i>wMinorVerNum</i><span><span id="src18" class="srcSentence">;
  TYPEDESC </span></span><i>tdescAlias</i><span><span id="src19" class="srcSentence">;
  IDLDESC </span></span><i>idldescType</i><span><span id="src20" class="srcSentence">;
} TYPEATTR, FAR *LPTYPEATTR;</span></span></pre></td></tr></tbody></table></div>

			</div></div>
		</div><div id="ctl00_rs1_mainContentContainer_cpe558485" class="MTPS_CollapsibleRegion" xmlns:msxsl="urn:schemas-microsoft-com:xslt">
			<div id="ctl00_rs1_mainContentContainer_cpe558485_h" class="CollapseRegionLink">
				<img id="ctl00_rs1_mainContentContainer_cpe558485_i" src="http://i.msdn.microsoft.com/platform/Controls/CollapsibleArea/resources/minus.gif" style="border-width: 0px; vertical-align: middle;">&nbsp;Members
			</div><div style="overflow: visible; display: block; height: auto; width: 909px;" id="ctl00_rs1_mainContentContainer_cpe558485_c" class="MTPS_CollapsibleSection"><div style="display: block;" id="" class="MTPS_CollapsibleSection">
				<a name="sectionToggle0"><!----></a> <dl><dt> <b>guid</b> </dt><dd> <p><span id="src22" class="srcSentence">Globally unique identifier (GUID) of the type information. </span></p> </dd></dl><dl><dt> <b>lcid</b> </dt><dd> <p><span id="src23" class="srcSentence">Locale of member names and doc strings. </span></p> </dd></dl><dl><dt> <b>dwReserved</b> </dt><dd> <p><span id="src24" class="srcSentence">Reserved.</span></p> </dd></dl><dl><dt> <b>memidConstructor</b> </dt><dd> <p><span id="src25" class="srcSentence">Identifier of constructor, or MEMBERID_NIL if none. </span></p> </dd></dl><dl><dt> <b>memidDestructor</b> </dt><dd> <p><span id="src26" class="srcSentence">Identifier of destructor, or MEMBERID_NIL if none. </span></p> </dd></dl><dl><dt> <b>lpstrSchema</b> </dt><dd> <p><span id="src27" class="srcSentence">Reserved for future use. </span></p> </dd></dl><dl><dt> <b>cbSizeInstance</b> </dt><dd> <p><span id="src28" class="srcSentence">Size of an instance of this type. </span></p> </dd></dl><dl><dt> <b>typekind</b> </dt><dd> <p><span id="src29" class="srcSentence">Kind of type this information describes.</span></p> </dd></dl><dl><dt> <b>cFuncs</b> </dt><dd> <p><span id="src30" class="srcSentence">Number of functions. </span></p> </dd></dl><dl><dt> <b>cVars</b> </dt><dd> <p><span id="src31" class="srcSentence">Number of variables/data members. </span></p> </dd></dl><dl><dt> <b>cImplTypes</b> </dt><dd> <p><span id="src32" class="srcSentence">Number of implemented interfaces. </span></p> </dd></dl><dl><dt> <b>cbSizeVft</b> </dt><dd> <p><span id="src33" class="srcSentence">Size of this type's VTBL. </span></p> </dd></dl><dl><dt> <b>cbAlignment</b> </dt><dd> <p><span id="src34" class="srcSentence">Byte alignment for an instance of this type.</span></p> </dd></dl><dl><dt> <b>wTypeFlags</b> </dt><dd> <p><span id="src35" class="srcSentence"> <a id="ctl00_rs1_mainContentContainer_ctl09" onclick="javascript:Track('ctl00_rs1_mainContentContainer_cpe558485_c|ctl00_rs1_mainContentContainer_ctl09',this);" href="http://msdn.microsoft.com/en-us/library/aa910236.aspx">TYPEFLAGS</a> value describing this information.</span></p> </dd></dl><dl><dt> <b>wMajorVerNum</b> </dt><dd> <p><span id="src36" class="srcSentence">Major version number.</span></p> </dd></dl><dl><dt> <b>wMinorVerNum</b> </dt><dd> <p><span id="src37" class="srcSentence">Minor version number. </span></p> </dd></dl><dl><dt> <b>tdescAlias</b> </dt><dd> <p><span id="src38" class="srcSentence">If <b>typekind</b> == TKIND_ALIAS, specifies the type for which this type is an alias. </span></p> </dd></dl><dl><dt> <b>idldescType</b> </dt><dd> <p><span id="src39" class="srcSentence">IDL attributes of the described type.</span>
				
 * @author mdt
 *
 */
public class TYPEATTR extends AbstractBasicData<TYPEATTR> {
	public static enum TYPEKIND {
		TKIND_ENUM,TKIND_RECORD,TKIND_MODULE,TKIND_INTERFACE,TKIND_DISPATCH,
		TKIND_COCLASS,TKIND_ALIAS,TKIND_UNION,TKIND_MAX
	}
	
	  GUID guid; 					
	  DWORD lcid;					
	  int dwReserved;		
	  DWORD memidConstructor;	
	  DWORD memidDestructor;		
	  String lpstrSchema;		
	  DWORD cbSizeInstance;	
	  TYPEKIND typekind;			
	  int cFuncs;		
	  int cVars;			
	  int cImplTypes;	
	  int cbSizeVft;
	  int cbAlignment;	
	  int wTypeFlags;	
	  int wMajorVerNum;	
	  int wMinorVerNum;	
	  
	  /**
	   * TYPEDESC tdescAlias;			
	   * <pre>
		typedef struct tagTYPEDESC {
			_ANONYMOUS_UNION union {
				struct tagTYPEDESC *lptdesc;
				struct tagARRAYDESC *lpadesc;
				HREFTYPE hreftype;
			} DUMMYUNIONNAME;
			VARTYPE vt;
		}TYPEDESC;
		typedef struct tagARRAYDESC {
			TYPEDESC tdescElem;
			USHORT cDims;
			SAFEARRAYBOUND rgbounds[1];
		}ARRAYDESC;</pre>*/
	   DWORD union;
	   int vt;
	   /**
	    * sizeof(IDLDESC) renvoie 8 alors que sa taille est de 6 ??? alignement ?
	    * <pre>
	   typedef struct tagIDLDESC {
			ULONG dwReserved;
			USHORT wIDLFlags;
		}IDLDESC,*LPIDLDESC;
	    </pre>*/

	   DWORD IDLDESC_dwReserved;			
	   int wIDLFlags;
	   
	   
	private ITypeInfo info;
	public TYPEATTR(ITypeInfo info, LONG addr) {
		super(null);
		this.info = info;
		pointer = new Pointer(new NativeMemoryBlock(addr.getValue(), getSizeOf()));
	}
	
	

	public Pointer createPointer() throws NativeException {
        pointer = new Pointer(MemoryBlockFactory.createMemoryBlock(getSizeOf()));
        return pointer;
	}

	
	/**
	 * <pre>
  GUID guid; 						sizeOf
  LCID lcid;						4
  unsigned long dwReserved;			4
  MEMBERID memidConstructor;		4
  MEMBERID memidDestructor;			4
  OLECHAR FAR* lpstrSchema;			4
  unsigned long cbSizeInstance;		4
  TYPEKIND typekind;				4
  unsigned short cFuncs;			2
  unsigned short cVars;				2
  unsigned short cImplTypes;		2
  unsigned short cbSizeVft;			2
  unsigned short cbAlignment;		2
  unsigned short wTypeFlags;		2
  unsigned short wMajorVerNum;		2
  unsigned short wMinorVerNum;		2
  TYPEDESC tdescAlias;				8 (union + vt)
  IDLDESC idldescType;				6 = (4+2)(IDLDESC_dwReserved + wIDLFlags)
</pre
	 */
	public int getSizeOf() {
		return GUID.sizeOf() + 7*4 + 8*2 + 8 + 6;
	}

	public TYPEATTR getValueFromPointer() throws NativeException {
		int pos = 0;
		guid = GUID.fromPointer(pointer); 						pos += GUID.sizeOf();
		lcid = new DWORD(pointer.getAsInt(pos));				pos += 4;
		dwReserved = 0;											pos += 4;
		memidConstructor = new DWORD(pointer.getAsInt(pos));	pos += 4;
		memidDestructor = new DWORD(pointer.getAsInt(pos));		pos += 4;
		lpstrSchema = null;										pos += 4;
		cbSizeInstance = new DWORD(pointer.getAsInt(pos));		pos += 4;
		typekind = TYPEKIND.values()[pointer.getAsInt(pos)];	pos += 4; //should be the ordinal, TOSEE : verify this
		cFuncs = pointer.getAsShort(pos);						pos += 2;
		cVars = pointer.getAsShort(pos);						pos += 2;
		cImplTypes = pointer.getAsShort(pos);					pos += 2;
		cbSizeVft = pointer.getAsShort(pos);					pos += 2;
		cbAlignment = pointer.getAsShort(pos);					pos += 2;
		wTypeFlags = pointer.getAsShort(pos);					pos += 2;
		wMajorVerNum = pointer.getAsShort(pos);					pos += 2;
		wMinorVerNum = pointer.getAsShort(pos);					pos += 2;
		union = new DWORD(pointer.getAsInt(pos));				pos += 4;
		vt = pointer.getAsInt(pos);								pos += 4;
		IDLDESC_dwReserved = null;								pos += 4;
		wIDLFlags  = pointer.getAsShort(pos);					pos += 2;
		return this;
	}

	public GUID getGuid() {
		return guid;
	}

	public DWORD getLcid() {
		return lcid;
	}

	public int getDwReserved() {
		return dwReserved;
	}

	public DWORD getMemidConstructor() {
		return memidConstructor;
	}

	public DWORD getMemidDestructor() {
		return memidDestructor;
	}

	public String getLpstrSchema() {
		return lpstrSchema;
	}

	public DWORD getCbSizeInstance() {
		return cbSizeInstance;
	}

	public TYPEKIND getTypekind() {
		return typekind;
	}

	public int getCFuncs() {
		return cFuncs;
	}

	public int getCVars() {
		return cVars;
	}

	public int getCImplTypes() {
		return cImplTypes;
	}

	public int getCbAlignment() {
		return cbAlignment;
	}

	public int getWTypeFlags() {
		return wTypeFlags;
	}

	public int getWMajorVerNum() {
		return wMajorVerNum;
	}

	public int getWMinorVerNum() {
		return wMinorVerNum;
	}

	public DWORD getUnion() {
		return union;
	}

	public int getVt() {
		return vt;
	}

	public DWORD getIDLDESC_dwReserved() {
		return IDLDESC_dwReserved;
	}

	public int getWIDLFlags() {
		return wIDLFlags;
	}

	@Override
	protected void finalize() throws Throwable {
		try { dispose(); } catch (Exception e) { JNative.getLogger().log(SEVERITY.ERROR, e); }
	}
	
	public void dispose() throws NativeException, IllegalAccessException {
		if(info != null)  {
			info.ReleaseTypeAttr(this);
			info = null;
		}
	}


}
