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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.data.Range;

/**
 * A specific logic number axis that has central value.
 * 
 * @author Sha Jiang
 */
public class CentralValueAxis extends LogicNumberAxis {

	private static final long serialVersionUID = -961485454189652288L;

	private Number centralValue = null;

	private Number formattedStep = null;

	private NumberFormat rateFormatter = new DecimalFormat("0.00%");

	public CentralValueAxis(Number centralValue, Range range, int tickCount,
			NumberFormat priceFormatter) {
		super(range, tickCount, priceFormatter);
		if (centralValue == null) {
			throw new IllegalArgumentException(
					"Null 'centralValue' argumented.");
		}
		this.centralValue = centralValue;
	}

	public List<LogicNumberTick> getLogicTicks() {
		List<LogicNumberTick> ticks = new ArrayList<LogicNumberTick>();
		int count = getTickCount();
		int mid = count / 2;
		for (int i = 0; i < count; i++) {
			Number buf = new Double(centralValue.doubleValue() + (i - mid)
					* getFormattedStep().doubleValue());
			String bufStr = getFormatter().format(buf);
			ticks.add(new LogicNumberTick(buf, bufStr));
		}

		return ticks;
	}

	public List<LogicNumberTick> getRatelogicTicks() {
		List<LogicNumberTick> result = new ArrayList<LogicNumberTick>();
		List<LogicNumberTick> priceTicks = getLogicTicks();
		for (int i = 0; i < priceTicks.size(); i++) {
			LogicNumberTick buf = (LogicNumberTick) priceTicks.get(i);
			Number value = buf.getTickNumber();
			String rateLabel = rateFormatter.format((value.doubleValue() - centralValue.doubleValue())/
					centralValue.doubleValue());
			if (i == priceTicks.size() / 2) {
				rateLabel = " " + rateLabel;
			} else if (i > priceTicks.size() / 2) {
				rateLabel = " " + rateLabel;
			}
			result.add(new LogicNumberTick(value, rateLabel));
		}

		return result;
	}

	public double getUpperBound() {
		int midCount = getTickCount() / 2;
		return Math.max(getRange().getUpperBound(), centralValue.doubleValue()
				+ getFormattedStep().doubleValue() * midCount);
	}

	public double getLowerBound() {
		int midCount = getTickCount() / 2;
		return Math.min(getRange().getLowerBound(), centralValue.doubleValue()
				- getFormattedStep().doubleValue() * midCount);
	}

	private Number calculateStep() {
		double halfRange = 0.0D;
		if (centralValue.doubleValue() <= getRange().getCentralValue()) {
			halfRange = Math.abs(centralValue.doubleValue()
					- getRange().getUpperBound());
		} else {
			halfRange = Math.abs(centralValue.doubleValue()
					- getRange().getLowerBound());
		}

		int midCount = getTickCount() / 2;
		double step = halfRange / midCount;
		return new Double(getFormatter().format(step));
	}

	private Number getFormattedStep() {
		if (formattedStep == null) {
			formattedStep = calculateStep();
		}
		return formattedStep;
	}

	public Number getCentralValue() {
		return centralValue;
	}

	public NumberFormat getRateFormatter() {
		return rateFormatter;
	}
}
