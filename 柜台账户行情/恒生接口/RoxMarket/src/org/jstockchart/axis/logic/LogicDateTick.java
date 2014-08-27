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

import java.util.Date;

import org.jstockchart.axis.TickAlignment;

/**
 * <code>LogicDateTick</code> represents logic date axis tick.
 * 
 * @author Sha Jiang
 */
public class LogicDateTick extends AbstractLogicTick {

	private static final long serialVersionUID = -8059438790652646317L;
	
	private boolean isShowTick=true;
	
	public boolean isShowTick() {
		return this.isShowTick;
	}

	public void setShowTick(boolean isShowTick) {
		this.isShowTick = isShowTick;
	}

	private Date tickDate;

	/**
	 * 
	 * 
	 * @param tickDate
	 * @param tickLabel
	 * @param tickAlignment
	 */
	public LogicDateTick(Date tickDate, String tickLabel,
			TickAlignment tickAlignment) {
		super(tickLabel, tickAlignment);
		this.tickDate = tickDate;
	}

	public LogicDateTick(Date tickDate, String tickLabel) {
		super(tickLabel);
		this.tickDate = tickDate;
	}

	public Date getTickDate() {
		return tickDate;
	}

	public void setTickDate(Date tickDate) {
		this.tickDate = tickDate;
	}
}
