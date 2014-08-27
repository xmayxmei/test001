package org.xvolks.jnative.toolkit;

public class StringToolkit {
	private StringToolkit() {}

	/**
	 * <p>Writes the array of bytes <code>data</code> in hex format</p>
	 * @param data
	 * @return
	 */
	public static final String toHexString(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int twoHalfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while(twoHalfs++ < 1);
		}
		return buf.toString();
	}

	/**
	 * @param data String in hex format like 014fcb09 
	 * @return decoded array of bytes
	 * @throws java.lang.StringIndexOutOfBoundsException if the string length is odd
	 */
	public static final byte[] fromHexString(String data) {
		byte[] buffer = new byte[data.length()>>1];
		for(int i = 0, j = 0; i<data.length(); i+=2, j++) {
			buffer[j] = (byte)Integer.parseInt(data.substring(i, i+2), 16);
		}
		return buffer;
	}

}
