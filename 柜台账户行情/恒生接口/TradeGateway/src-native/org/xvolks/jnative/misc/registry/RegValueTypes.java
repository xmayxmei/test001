package org.xvolks.jnative.misc.registry;

/**
 * <P>The following registry value types are defined in Winnt.h.</P>
<TABLE CLASS="clsStd">

<TR class="data">
<TH>Value</TH>
<TH>Type</TH>
</TR>
<TR class="data">
<TD class="data">REG_BINARY</TD>
<TD class="data">Binary data in any form.</TD>
</TR>
<TR class="data">
<TD class="data">REG_DWORD</TD>
<TD class="data">A 32-bit number.</TD>

</TR>
<TR class="data">
<TD class="data">REG_DWORD_LITTLE_ENDIAN</TD>
<TD class="data">A 32-bit number in little-endian format. 


<P>Windows is designed to run on little-endian computer architectures. Therefore, this value is defined as REG_DWORD in the Windows header files.</P>
</TD>
</TR>
<TR class="data">
<TD class="data">REG_DWORD_BIG_ENDIAN</TD>
<TD class="data">A 32-bit number in big-endian format. 


<P>Some UNIX systems support big-endian architectures.</P>
</TD>
</TR>

<TR class="data">
<TD class="data">REG_EXPAND_SZ</TD>
<TD class="data">A null-terminated string that contains unexpanded references to environment variables (for example, "%PATH%"). It will be a Unicode or ANSI string depending on whether you use the Unicode or ANSI functions. To expand the environment variable references, use the 
<A HREF="http://msdn.microsoft.com/library/default.asp?url=/library/en-us/sysinfo/base/expandenvironmentstrings.asp"><b>ExpandEnvironmentStrings</b></A> function.</TD>
</TR>
<TR class="data">
<TD class="data">REG_LINK</TD>
<TD class="data">Reserved for system use.</TD>
</TR>
<TR class="data">
<TD class="data">REG_MULTI_SZ</TD>

<TD class="data">A sequence of null-terminated strings, terminated by an empty string (\0). <P>The following is an example:</P>
<P><I>String1</I>\0<I>String2</I>\0<I>String3</I>\0<I>LastString</I>\0\0</P>
<P>The first \0 terminates the first string, the second to the last \0 terminates the last string, and the final \0 terminates the sequence. Note that the final terminator must be factored into the length of the string.</P>
</TD>
</TR>
<TR class="data">

<TD class="data">REG_NONE</TD>
<TD class="data">No defined value type.</TD>
</TR>
<TR class="data">
<TD class="data">REG_QWORD</TD>
<TD class="data">A 64-bit number.</TD>
</TR>
<TR class="data">
<TD class="data">REG_QWORD_LITTLE_ENDIAN</TD>
<TD class="data">A 64-bit number in little-endian format. 


<P>Windows is designed to run on little-endian computer architectures. Therefore, this value is defined as REG_QWORD in the Windows header files.</P>

</TD>
</TR>
<TR class="data">
<TD class="data">REG_SZ</TD>
<TD class="data">A null-terminated string. This will be either a Unicode or an ANSI string, depending on whether you use the Unicode or ANSI functions.</TD>
</TR>
</TABLE>
</P>

 * $Id: RegValueTypes.java,v 1.1 2006/06/05 21:22:02 mdenty Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 *
 */
public enum RegValueTypes {
	/** No value type*/
	REG_NONE                    ( 0),   // No value type

	/** Unicode nul terminated string */
	REG_SZ                      ( 1),   // Unicode nul terminated string
	
	/** Unicode nul terminated string
     *  (with environment variable references) */
	REG_EXPAND_SZ               ( 2),   // Unicode nul terminated string
	                                            // (with environment variable references)
	REG_BINARY                  ( 3),   // Free form binary
	REG_DWORD                   ( 4),   // 32-bit number
	REG_DWORD_LITTLE_ENDIAN     ( 4),   // 32-bit number (same as REG_DWORD)
	REG_DWORD_BIG_ENDIAN        ( 5),   // 32-bit number
	REG_LINK                    ( 6),   // Symbolic Link (unicode)
	REG_MULTI_SZ                ( 7),   // Multiple Unicode strings
	REG_RESOURCE_LIST           ( 8),   // Resource list in the resource map
	REG_FULL_RESOURCE_DESCRIPTOR ( 9),  // Resource list in the hardware description
	REG_RESOURCE_REQUIREMENTS_LIST ( 10),
	REG_QWORD                   ( 11),  // 64-bit number
	REG_QWORD_LITTLE_ENDIAN     ( 11),  // 64-bit number (same as REG_QWORD)

	;
	private int mValue;
	private RegValueTypes(int value){
		mValue = value;
	}
	public int getValue() {
		return mValue;
	}
	public static RegValueTypes fromInt(Integer value) throws IllegalArgumentException {
		switch (value) {
		case 0:
			return REG_NONE; 
		case 1:
			return REG_SZ;
		case 2: 
			return REG_EXPAND_SZ;
		case 3:
			return REG_BINARY;
		case 4:
			return REG_DWORD;
		case 5:
			return REG_DWORD_LITTLE_ENDIAN;
		case 6:
			return REG_LINK;
		case 7:
			return REG_MULTI_SZ;
		case 8:	
			return REG_RESOURCE_LIST;
		case 9:
			return REG_FULL_RESOURCE_DESCRIPTOR;
		case 10:
			return REG_RESOURCE_REQUIREMENTS_LIST;
		case 11:
			return REG_QWORD;			
		default:
			throw new IllegalArgumentException(value + " is not a valid RegValueType");
		}
	}
}
