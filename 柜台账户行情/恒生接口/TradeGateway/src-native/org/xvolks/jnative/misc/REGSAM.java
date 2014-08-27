package org.xvolks.jnative.misc;

/**
 * <P>The following table lists the specific access rights for registry key objects. </P>

<TABLE CLASS="clsStd">
<TR class="data">
<TH>Value</TH>
<TH>Meaning</TH>
</TR>
<TR class="data">
<TD class="data">KEY_ALL_ACCESS (0xF003F)</TD>
<TD class="data">Combines the STANDARD_RIGHTS_REQUIRED, KEY_QUERY_VALUE, KEY_SET_VALUE, KEY_CREATE_SUB_KEY, KEY_ENUMERATE_SUB_KEYS, KEY_NOTIFY, and KEY_CREATE_LINK access rights.</TD>
</TR>
<TR class="data">
<TD class="data">KEY_CREATE_LINK (0x0020)</TD>
<TD class="data">Reserved for system use.</TD>

</TR>
<TR class="data">
<TD class="data">KEY_CREATE_SUB_KEY (0x0004)</TD>
<TD class="data">Required to create a subkey of a registry key.</TD>
</TR>
<TR class="data">
<TD class="data">KEY_ENUMERATE_SUB_KEYS (0x0008)</TD>
<TD class="data">Required to enumerate the subkeys of a registry key.</TD>
</TR>
<TR class="data">
<TD class="data">KEY_EXECUTE (0x20019)</TD>
<TD class="data">Equivalent to KEY_READ.</TD>

</TR>
<TR class="data">
<TD class="data">KEY_NOTIFY (0x0010)</TD>
<TD class="data">Required to request change notifications for a registry key or for subkeys of a registry key.</TD>
</TR>
<TR class="data">
<TD class="data">KEY_QUERY_VALUE (0x0001)</TD>
<TD class="data">Required to query the values of a registry key.</TD>
</TR>
<TR class="data">
<TD class="data">KEY_READ (0x20019)</TD>
<TD class="data">Combines the STANDARD_RIGHTS_READ, KEY_QUERY_VALUE, KEY_ENUMERATE_SUB_KEYS, and KEY_NOTIFY values.</TD>

</TR>
<TR class="data">
<TD class="data">KEY_SET_VALUE (0x0002)</TD>
<TD class="data">Required to create, delete, or set a registry value.</TD>
</TR>
<TR class="data">
<TD class="data">KEY_WOW64_32KEY (0x0200)</TD>
<TD class="data">Indicates that an application on 64-bit Windows should operate on the 32-bit registry view. For more information, see Accessing an Alternate Registry View in the MSDN. <P>This flag must be combined using the OR operator with the other flags in this table that either query or access registry values.</P>
<BLOCKQUOTE><B>Windows 2000:  </B>This flag is not supported.</BLOCKQUOTE>

</TD>
</TR>
<TR class="data">
<TD class="data">KEY_WOW64_64KEY (0x0100)</TD>
<TD class="data">Indicates that an application on 64-bit Windows should operate on the 64-bit registry view.  For more information, see Accessing an Alternate Registry View in the MSDN. <P>This flag must be combined using the OR operator with the other flags in this table that either query or access registry values.</P>
<BLOCKQUOTE><B>Windows 2000:  </B>This flag is not supported.</BLOCKQUOTE>
</TD>
</TR>
<TR class="data">
<TD class="data">KEY_WRITE (0x20006)</TD>

<TD class="data">Combines the STANDARD_RIGHTS_WRITE, KEY_SET_VALUE, and KEY_CREATE_SUB_KEY access rights.</TD>
</TR>
</TABLE>
<P>
 * $Id: REGSAM.java,v 1.1 2006/06/04 21:44:58 mdenty Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class REGSAM {
	public static final REGSAM KEY_ALL_ACCESS = new REGSAM(0xF003F);
	public static final REGSAM KEY_CREATE_LINK = new REGSAM(0x0020);
	public static final REGSAM KEY_CREATE_SUB_KEY = new REGSAM(0x0004);
	public static final REGSAM KEY_ENUMERATE_SUB_KEYS = new REGSAM(0x0008);
	public static final REGSAM KEY_EXECUTE = new REGSAM(0x20019);
	public static final REGSAM KEY_NOTIFY = new REGSAM(0x0010);
	public static final REGSAM KEY_QUERY_VALUE = new REGSAM(0x0001);
	public static final REGSAM KEY_READ = new REGSAM(0x20019);
	public static final REGSAM KEY_SET_VALUE = new REGSAM(0x0002);
	public static final REGSAM KEY_WOW64_32KEY = new REGSAM(0x0200);
	public static final REGSAM KEY_WOW64_64KEY = new REGSAM(0x0100);
	public static final REGSAM KEY_WRITE = new REGSAM(0x20006);

	private int mValue;
	
	private REGSAM(int value) {
		mValue = value;
	}
	public int getValue() {
		return mValue;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if (obj instanceof REGSAM) {
			return mValue == ((REGSAM)obj).mValue;
		} else {
			return false;
		}
	}

	/**
	 * Performs a logical or between two REGSAM objects
	 * @param value 
	 * @return a REGSAM or
	 */
	public REGSAM or(REGSAM value) {
		return new REGSAM(getValue() | value.getValue());
	}
}
