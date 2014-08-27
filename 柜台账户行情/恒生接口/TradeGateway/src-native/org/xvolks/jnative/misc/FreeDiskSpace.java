
package org.xvolks.jnative.misc;

/**
 * FreeDiskSpace this utility class is created and populated by Kernel32.getDiskFreeSpaceEx().<br>
 *
 *  $Id: FreeDiskSpace.java,v 1.6 2006/06/09 20:44:05 mdenty Exp $;
 *  
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class FreeDiskSpace {
	public long lFreeBytesAvailable;
	public long lTotalNumberOfBytes;
	public long lTotalNumberOfFreeBytes;
	public String drive;
	
	public FreeDiskSpace(String drive,
						 Long freeBytesAvailable,
						 Long totalNumberOfBytes,
						 Long totalNumberOfFreeBytes) {
		this.drive = drive;
		lFreeBytesAvailable = freeBytesAvailable;
		lTotalNumberOfBytes = totalNumberOfBytes;
		lTotalNumberOfFreeBytes = totalNumberOfFreeBytes;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Drive " + drive).
			append("\n\tFreeBytesAvailable :" + lFreeBytesAvailable).
			append("\n\tTotalNumberOfBytes :" + lTotalNumberOfBytes).
			append("\n\tTotalNumberOfFreeBytes : " + lTotalNumberOfFreeBytes).toString();
	}
	
}
