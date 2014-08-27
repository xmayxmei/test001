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
 * An item representing candlestick data in the form (time, open, high, low,
 * close, volume).
 * 
 * @author Sha Jiang
 */
public class CandlestickItem implements Serializable,
		Comparable<CandlestickItem> {

	private static final long serialVersionUID = 5832087180291919076L;

	private Date time = null;

	private double open = 0.0D;

	private double high = 0.0D;

	private double low = 0.0D;

	private double close = 0.0D;

	private double volume = 0.0D;

	/**
	 * Creates a new <code>CandlestickItem</code> instance.
	 * 
	 * @param time
	 *            the time (<code>null</code> not permitted).
	 * @param open
	 *            the open value.
	 * @param high
	 *            the high value.
	 * @param low
	 *            the low high value.
	 * @param close
	 *            the close value.
	 * @param volume
	 *            the volume value.
	 */
	public CandlestickItem(Date time, double open, double high, double low,
			double close, double volume) {
		if (time == null) {
			throw new IllegalArgumentException("Null 'time' argument.");
		}
		this.time = time;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
	}

	public Date getTime() {
		return time;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
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
		long temp;
		temp = Double.doubleToLongBits(close);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(high);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(low);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(open);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));
		result = PRIME * result + ((time == null) ? 0 : time.hashCode());
		temp = Double.doubleToLongBits(volume);
		result = PRIME * result + (int) (temp ^ (temp >>> 32));
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
		final CandlestickItem other = (CandlestickItem) obj;
		if (Double.doubleToLongBits(close) != Double
				.doubleToLongBits(other.close)) {
			return false;
		}
		if (Double.doubleToLongBits(high) != Double
				.doubleToLongBits(other.high)) {
			return false;
		}
		if (Double.doubleToLongBits(low) != Double.doubleToLongBits(other.low)) {
			return false;
		}
		if (Double.doubleToLongBits(open) != Double
				.doubleToLongBits(other.open)) {
			return false;
		}
		if (time == null) {
			if (other.time != null) {
				return false;
			}
		} else if (!time.equals(other.time)) {
			return false;
		}
		if (Double.doubleToLongBits(volume) != Double
				.doubleToLongBits(other.volume)) {
			return false;
		}
		return true;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("[time=").append(time).append(", open=").append(open)
				.append(", high=").append(high).append(", low=").append(low)
				.append(", close=").append(close).append(", volume=").append(
						volume).append("]");
		return buf.toString();
	}

	public int compareTo(CandlestickItem item) {
		return this.time.compareTo(item.time);
	}
}
