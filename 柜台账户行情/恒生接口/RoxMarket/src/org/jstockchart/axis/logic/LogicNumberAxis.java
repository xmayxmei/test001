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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.data.Range;

/**
 * <code>LogicNumberAxis</code> represents logic number axis.
 * 
 * @author Sha Jiang
 */
public class LogicNumberAxis extends AbstractLogicAxis {

	private static final long serialVersionUID = -9038300070292452699L;

	private Range range = null;

	private int tickCount = 0;

	public void setTickCount(int tickCount) {
		this.tickCount = tickCount;
	}

	private NumberFormat formatter = null;

	private Number formattedStep = null;

	public LogicNumberAxis(Range range, int tickCount, NumberFormat formatter) {
		if (range == null) {
			throw new IllegalArgumentException("Null 'range' argumented.");
		}
		this.range = range;

		if (tickCount <= 1) {
			throw new IllegalArgumentException(
					"'tickCount <= 1' not permitted.");
		}
		this.tickCount = tickCount;

		if (formatter == null) {
			throw new IllegalArgumentException("Null 'formatter' argumented.");
		}
		this.formatter = formatter;
	}

	public List<LogicNumberTick> getLogicTicks() {
		List<LogicNumberTick> ticks = new ArrayList<LogicNumberTick>();
		if (tickCount < MAXIMUM_TICK_COUNT) {
			for (int i = 0; i < tickCount; i++) {
				double buf = range.getLowerBound() + i
						* getFormattedStep().doubleValue();
				String bufStr = formatter.format(buf);
				LogicNumberTick tick = new LogicNumberTick(new Double(buf),
						bufStr);
				ticks.add(tick);
			}
		}

		return ticks;
	}

	public double getUpperBound() {
		return Math.max(range.getUpperBound(), new Double(formatter
				.format(range.getLowerBound() + (tickCount - 1)
						* getFormattedStep().doubleValue())).doubleValue());
	}

	public double getLowerBound() {
		return Math.min(range.getLowerBound(), new Double(formatter
				.format(range.getLowerBound())).doubleValue());
	}

	private Number calculateStep() {
		double step = (range.getUpperBound() - range.getLowerBound())
				/ (tickCount - 1);
		return new Double(formatter.format(step));
	}

	private Number getFormattedStep() {
		if (formattedStep == null) {
			formattedStep = calculateStep();
		}

		return formattedStep;
	}

	public NumberFormat getFormatter() {
		return formatter;
	}

	public Range getRange() {
		return range;
	}

	public int getTickCount() {
		return tickCount;
	}
}
