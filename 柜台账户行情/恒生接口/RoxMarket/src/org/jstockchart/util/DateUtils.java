/* ===========================================================
 * JStockChart : an extension of JFreeChart for financial market
 * ===========================================================
 *
 * Copyright (C) 2009, by Sha Jiang.
 *
 * Project Info:  http://code.google.com/p/jstockchart
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 */

package org.jstockchart.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Some useful methods for creating <code>java.util.Date</code> instance.
 * 
 * @author Sha Jiang
 */
public class DateUtils {

	private static final Calendar CALENDAR = Calendar.getInstance();

	private DateUtils() {
	}

	public static synchronized Date createDate(final int year, final int month,
			final int day, final int hour, final int minute, final int second,
			final int millisecond) {
		CALENDAR.clear();
		CALENDAR.set(year, month - 1, day, hour, minute, second);
		CALENDAR.set(Calendar.MILLISECOND, millisecond);
		return CALENDAR.getTime();
	}

	public static synchronized Date createDate(final int year, final int month,
			final int day) {
		createDate(year, month, day, 0, 0, 0, 0);
		return CALENDAR.getTime();
	}

	public static synchronized Date createDate(final int year, final int month,
			final int day, final int hour) {
		createDate(year, month, day, hour, 0, 0, 0);
		return CALENDAR.getTime();
	}

	public static synchronized Date createDate(final int year, final int month,
			final int day, final int hour, final int minute) {
		createDate(year, month, day, hour, minute, 0, 0);
		return CALENDAR.getTime();
	}

	public static synchronized Date createDate(final int year, final int month,
			final int day, final int hour, final int minute, final int second) {
		createDate(year, month, day, hour, minute, second, 0);
		return CALENDAR.getTime();
	}
}
