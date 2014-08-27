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

package org.jstockchart.axis;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Tick alignment, which will be transformed to <code>TextAnchor</code>.
 * 
 * @author Sha Jiang
 */
public final class TickAlignment implements Serializable {

	private static final long serialVersionUID = -8782988675590354918L;

	/**
	 * At the starting point(left/bottom <code>TextAnchor</code>) in the
	 * coordinate space.
	 */
	public static final TickAlignment START = new TickAlignment(
			"TickAlignment.START");

	/**
	 * At the mid point(center <code>TextAnchor</code>) in the coordinate
	 * space.
	 */
	public static final TickAlignment MID = new TickAlignment(
			"TickAlignment.MID");

	/**
	 * At the ending point(right/top <code>TextAnchor</code>) in the
	 * coordinate space.
	 */
	public static final TickAlignment END = new TickAlignment(
			"TickAlignment.END");

	private String name = null;

	/**
	 * Private constructor.
	 * 
	 * @param name
	 *            the name.
	 */
	private TickAlignment(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
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
		final TickAlignment other = (TickAlignment) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/**
	 * Ensures that serialization returns the unique instances.
	 */
	private Object readResolve() throws ObjectStreamException {
		Object result = null;
		if (this.equals(TickAlignment.START)) {
			result = TickAlignment.START;
		} else if (this.equals(TickAlignment.MID)) {
			result = TickAlignment.MID;
		} else if (this.equals(TickAlignment.END)) {
			result = TickAlignment.END;
		}
		return result;
	}
}
