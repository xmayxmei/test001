package org.xvolks.jnative.util.constants;

import org.xvolks.jnative.com.utils.ByRef;





public class COM {
	
	public static enum DISPATCH_TYPE {
		DISPATCH_METHOD (0x1),
		DISPATCH_PROPERTYGET (0x2),
		DISPATCH_PROPERTYPUT (0x4),
		DISPATCH_PROPERTYPUTREF (0x8),
		;
		private final int value;
		private DISPATCH_TYPE(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
		
	}
	
	/* Ole Parameter Description flags */
	public static enum IDLFLAG {
		IDLFLAG_NONE 	(0),
		IDLFLAG_FIN 	(1),
		IDLFLAG_FOUT 	(2),
		IDLFLAG_FLCID 	(4),
		IDLFLAG_FRETVAL (8),
		;
		private final int value;
		private IDLFLAG(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
		public static IDLFLAG fromInt(int value) {
			for(IDLFLAG flag : values()) {
				if(flag.value == value) {
					return flag;
				}
			}
			return null;
		}
	}
	public static final int LOCALE_SYSTEM_DEFAULT = 1024;
	public static final int LOCALE_USER_DEFAULT = 2048;

	/* Ole Description types */
	public static final short VT_BOOL = 11;     // Boolean; True=-1, False=0.
	public static final short VT_BSTR = 8;      // Binary String.
	public static final short VT_BYREF = 16384; // By reference - must be combined with one of the other VT values
	public static final short VT_CY = 6;        // Currency.
	public static final short VT_DATE = 7;      // Date.
	public static final short VT_DISPATCH = 9;  // IDispatch
	public static final short VT_EMPTY = 0;     // Not specified.
	public static final short VT_ERROR = 10;    // Scodes.
	public static final short VT_I2 = 2;        // 2-byte signed int.
	public static final short VT_I4 = 3;        // 4-byte signed int.
	public static final short VT_NULL = 1;      // Null.
	public static final short VT_R4 = 4;        // 4-byte real.
	public static final short VT_R8 = 5;        // 8-byte real.
	public static final short VT_UI1 = 17;      // Unsigned char.
	public static final short VT_UI4 = 19;      // Unsigned int.
	public static final short VT_UNKNOWN = 13;  // IUnknown FAR*.
	public static final short VT_VARIANT = 12;  // VARIANT FAR*.
	public static final short VT_PTR = 26;
	public static final short VT_USERDEFINED = 29;
	public static final short VT_HRESULT = 25;
	public static final short VT_DECIMAL = 14;
	public static final short VT_I1 = 16;
	public static final short VT_UI2 = 18;
	public static final short VT_I8 = 20;
	public static final short VT_UI8 = 21;
	public static final short VT_INT = 22;
	public static final short VT_UINT = 23;
	public static final short VT_VOID = 24;
	public static final short VT_SAFEARRAY = 27;
	public static final short VT_CARRAY = 28;
	public static final short VT_LPSTR = 30;
	public static final short VT_LPWSTR = 31;
	public static final short VT_RECORD = 36;
	public static final short VT_FILETIME = 64;
	public static final short VT_BLOB = 65;
	public static final short VT_STREAM = 66;
	public static final short VT_STORAGE = 67;
	public static final short VT_STREAMED_OBJECT = 68;
	public static final short VT_STORED_OBJECT = 69;
	public static final short VT_BLOB_OBJECT = 70;
	public static final short VT_CF = 71;
	public static final short VT_CLSID = 72;
	public static final short VT_VERSIONED_STREAM = 73;
	public static final short VT_BSTR_BLOB = 0xfff;
	public static final short VT_VECTOR = 0x1000;
	public static final short VT_ARRAY = 0x2000;

	public static enum VARENUM {
		VT_EMPTY		(0),
		VT_NULL			(1),
		VT_I2			(2),
		VT_I4			(3),
		VT_R4			(4),
		VT_R8			(5),
		VT_CY			(6),
		VT_DATE			(7),
		VT_BSTR			(8),
		VT_DISPATCH		(9),
		VT_ERROR		(10),
		VT_BOOL			(11),
		VT_VARIANT		(12),
		VT_UNKNOWN		(13),
		VT_DECIMAL		(14),
		VT_I1			(16),
		VT_UI1			(17),
		VT_UI2			(18),
		VT_UI4			(19),
		VT_I8			(20),
		VT_UI8			(21),
		VT_INT			(22),
		VT_UINT			(23),
		VT_VOID			(24),
		VT_HRESULT		(25),
		VT_PTR			(26),
		VT_SAFEARRAY	(27),
		VT_CARRAY		(28),
		VT_USERDEFINED	(29),
		VT_LPSTR		(30),
		VT_LPWSTR		(31),
		VT_RECORD		(36),
		VT_INT_PTR		(37),
		VT_UINT_PTR		(38),
		VT_FILETIME		(64),
		VT_BLOB			(65),
		VT_STREAM		(66),
		VT_STORAGE		(67),
		VT_STREAMED_OBJECT	(68),
		VT_STORED_OBJECT(69),
		VT_BLOB_OBJECT	(70),
		VT_CF			(71),
		VT_CLSID		(72),
		VT_VERSIONED_STREAM(73),
		VT_BSTR_BLOB	(0xfff),
		VT_VECTOR		(0x1000),
		VT_ARRAY		(0x2000),
		VT_BYREF		(0x4000),
		VT_RESERVED		(0x8000),
		VT_ILLEGAL		(0xffff),
		VT_ILLEGALMASKED(0xfff),
		VT_TYPEMASK		(0xfff),
		;
		private final int value;
		private VARENUM(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
		public static VARENUM fromInt(int value) {
			for(VARENUM vare : values()) {
				if(vare.getValue() == value) {
					return vare;
				}
			}
			throw new IllegalArgumentException(value + " is not a value VARENUM value");
		}
	};
	
	public static void main(String[] args) {
		for(VARENUM e : VARENUM.values()) {
			int val = e.ordinal();
			if(e.ordinal() >= 34) {
				val = e.ordinal() + 30;
			} else if(e.ordinal() >= 31) {
				val = e.ordinal() + 5;
			} else if(val >= 15) {
				val++;
			}
			System.err.println(e.name() + "\t\t("+val+"),");
		}
	}
	
	public static String getTypeName(VARENUM type) {
		return getTypeName(type.getValue());
	}

	public static String getTypeName(int type) {
		switch (type) {
		case VT_BOOL:
			return "Boolean";

		case VT_R4:
			return "Float";

		case VT_R8:
			return "Double";

		case VT_I4:
			return "Integer";

		case VT_DISPATCH:
			return "IDispatch";

		case VT_UNKNOWN:
			return "IUnknown";

		case VT_I2:
			return "Short";

		case VT_BSTR:
			return "String";

		case VT_VARIANT:
			return "Variant";

		case VT_CY:
			return "Currency";

		case VT_DATE:
			return "Date";

		case VT_UI1:
			return "/*uchar*/ Byte";

		case VT_UI4:
			return "/*uint32*/ Integer";

		case VT_USERDEFINED:
			return /*"UserDefined"*/ "Integer";

		case VT_HRESULT:
			return "Integer";

		case VT_VOID:
			return "void";

		case VT_BYREF | VT_BOOL:
			return ByRef.class.getName() +"<Boolean>";

		case VT_BYREF | VT_R4:
			return ByRef.class.getName() +"<Float>";

		case VT_BYREF | VT_R8:
			return ByRef.class.getName() +"<Double>";

		case VT_BYREF | VT_I4:
			return ByRef.class.getName() +"<Integer>";
			
		case VT_BYREF | VT_I2:
			return ByRef.class.getName() +"<Short>";
			
		case VT_BYREF | VT_UI1:
			return ByRef.class.getName() +"<Byte>";
			
		case VT_BYREF | VT_UI4:
			return ByRef.class.getName() +"<Integer>";
			
		case VT_BYREF | VT_BSTR:
			return ByRef.class.getName() +"<String>";
			
		case VT_BYREF | VT_DISPATCH:
		case VT_BYREF | VT_UNKNOWN:
		case VT_BYREF | VT_VARIANT:
		case VT_BYREF | VT_CY:
		case VT_BYREF | VT_DATE:
		case VT_BYREF | VT_USERDEFINED:
			return ByRef.class.getName() +"<Object>";
//			return Pointer.class.getName();
		}

		return "unknown " + type;
	}
	
	
}
