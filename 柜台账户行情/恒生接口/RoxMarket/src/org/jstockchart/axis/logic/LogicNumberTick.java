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

package org.jstockchart.axis.logic;

import org.jstockchart.axis.TickAlignment;

/**
 * <code>LogicNumberTick</code> represents logic number axis tick.
 * 
 * @author Sha Jiang
 */
public class LogicNumberTick extends AbstractLogicTick {

	private static final long serialVersionUID = -4541854230708274172L;

	private Number tickNumber = null;

	public LogicNumberTick(Number tickNumber, String tickLabel,
			TickAlignment tickAlignment) {
		super(tickLabel, tickAlignment);
		this.tickNumber = tickNumber;
	}

	public LogicNumberTick(Number tickNumber, String tickLabel) {
		super(tickLabel);
		this.tickNumber = tickNumber;
	}

	public Number getTickNumber() {
		return tickNumber;
	}

	public void setTickNumber(Number tickValue) {
		this.tickNumber = tickValue;
	}
}
