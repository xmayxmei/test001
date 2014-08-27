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

package org.jstockchart.area;

import java.awt.Color;

import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.plot.PlotOrientation;
import org.jstockchart.axis.logic.CentralValueAxis;
import org.jstockchart.util.ChartConst;

/**
 * <code>PriceArea</code> represents price part in timeseries chart.
 * 
 * @author Sha Jiang
 */
public class PriceArea extends AbstractArea {

	private static final long serialVersionUID = 7299189270613673891L;

	private CentralValueAxis logicPriceAxis = null;

	private Color priceColor = ChartConst.DEFAULT_PRICE_COLOR;

	private Color averageColor = ChartConst.DEFAULT_AVERAGE_COLOR;

	private Color centralPriceColor = ChartConst.DEFAULT_CENTRALPRICE_COLOR;

	private boolean averageVisible = true;

	private boolean rateVisible = true;

	private boolean markCentralPrice = true;

	/**
	 * Creates a new <code>PriceArea</code> instance.
	 * 
	 * @param logicPriceAxis
	 *            the logic number axis that represents price axis.
	 */
	public PriceArea(CentralValueAxis logicPriceAxis) {
		if (logicPriceAxis == null) {
			throw new IllegalArgumentException(
					"Null 'logicPriceAxis' argument.");
		}
		this.logicPriceAxis = logicPriceAxis;
	}

	public CentralValueAxis getLogicPriceAxis() {
		return logicPriceAxis;
	}

	public void setlogicPriceAxis(CentralValueAxis logicPriceAxis) {
		if (logicPriceAxis == null) {
			throw new IllegalArgumentException(
					"Null 'logicPriceAxis' argument.");
		}
		this.logicPriceAxis = logicPriceAxis;
	}

	public Color getAverageColor() {
		return averageColor;
	}

	public void setAverageColor(Color averageColor) {
		this.averageColor = averageColor;
	}

	public boolean isAverageVisible() {
		return averageVisible;
	}

	public void setAverageVisible(boolean averageVisible) {
		this.averageVisible = averageVisible;
	}

	public boolean isRateVisible() {
		return rateVisible;
	}

	public void setRateVisible(boolean rateVisible) {
		this.rateVisible = rateVisible;
	}

	public boolean isMarkCentralValue() {
		return markCentralPrice;
	}

	public void setMarkCentralValue(boolean markerVisible) {
		this.markCentralPrice = markerVisible;
	}

	public Color getCentralPriceColor() {
		return centralPriceColor;
	}

	public void setCentralPriceColor(Color centralPriceColor) {
		this.centralPriceColor = centralPriceColor;
	}

	public Color getPriceColor() {
		return priceColor;
	}

	public void setPriceColor(Color priceColor) {
		this.priceColor = priceColor;
	}

	/**
	 * Returns the price axis location that is determined by the plot
	 * orientation.
	 * 
	 * @return the price axis location.
	 */
	public AxisLocation getPriceAxisLocation() {
		PlotOrientation orientation = getOrientation();
		AxisLocation result = null;
		if (orientation.equals(PlotOrientation.VERTICAL)) {
			result = AxisLocation.BOTTOM_OR_LEFT;
		} else if (orientation.equals(PlotOrientation.HORIZONTAL)) {
			result = AxisLocation.TOP_OR_RIGHT;
		}

		return result;
	}

	/**
	 * Returns the rate axis location that is opposite to price axis location.
	 * 
	 * @return the rate axis location.
	 */
	public AxisLocation getRateAxisLocation() {
		return getPriceAxisLocation().getOpposite();
	}
}
