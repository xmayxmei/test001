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

package org.jstockchart.model;

import java.io.Serializable;
import java.util.Date;

/**
 * An item representing timeseries data in the form (time, price, volume).
 * 
 * @author Sha Jiang
 */
public class TimeseriesItem implements Serializable, Comparable<TimeseriesItem> {

	private static final long serialVersionUID = 4023150768913277877L;

	private Date time = null;

	private double price = 0.0D;

	private double volume = 0.0D;

	/**
	 * Creates a new <code>TimeseriesItem</code> instance.
	 * 
	 * @param time
	 *            the time (<code>null</code> not permitted).
	 * @param price
	 *            the price value.
	 * @param volume
	 *            the volume value.
	 */
	public TimeseriesItem(Date time, double price, double volume) {
		if (time == null) {
			throw new IllegalArgumentException("Null 'time' argument.");
		}
		this.time = time;
		this.price = price;
		this.volume = volume;
	}

	public Date getTime() {
		return time;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TimeseriesItem other = (TimeseriesItem) obj;
		if (time == null) {
			if (other.time != null) {
				return false;
			}
		} else if (!time.equals(other.time)) {
			return false;
		}
		return true;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("[time=").append(time).append(", price=").append(price)
				.append(", volume=").append(volume).append("]");
		return buf.toString();
	}

	public int compareTo(TimeseriesItem item) {
		return this.time.compareTo(item.time);
	}
}
