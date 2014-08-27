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

import java.io.Serializable;

import org.jstockchart.axis.TickAlignment;

/**
 * Abstract logic tick.
 * 
 * @author Sha Jiang
 */
public abstract class AbstractLogicTick implements Serializable {

	private static final long serialVersionUID = 5675211554636810961L;

	private String tickLabel = null;

	private TickAlignment tickAlignment = null;

	public AbstractLogicTick(String tickLabel, TickAlignment tickAlignment) {
		this.tickLabel = tickLabel;
		this.tickAlignment = tickAlignment;
	}

	public AbstractLogicTick(String tickLabel) {
		this(tickLabel, TickAlignment.MID);
	}

	public TickAlignment getTickAlignment() {
		return tickAlignment;
	}

	public void setTickAlignment(TickAlignment tickAlignment) {
		this.tickAlignment = tickAlignment;
	}

	public String getTickLabel() {
		return tickLabel;
	}

	public void setTickLabel(String tickLabel) {
		this.tickLabel = tickLabel;
	}
}
