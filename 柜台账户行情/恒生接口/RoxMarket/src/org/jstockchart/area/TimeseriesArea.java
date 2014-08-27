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

import java.util.Date;

import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.plot.PlotOrientation;
import org.jstockchart.axis.logic.LogicDateAxis;

/**
 * <code>TimeseriesArea</code> represents timeseries chart.
 * 
 * @author Sha Jiang
 */
public class TimeseriesArea extends AbstractArea {

	private static final long serialVersionUID = 3455502526606973938L;

	private LogicDateAxis logicDateAxis = null;

	private PriceArea priceArea = null;

	private VolumeArea volumeArea = null;

	private int priceWeight = 2;

	private int volumeWeight = 1;

	private boolean createLegend = true;

	private double gap = 6;
	
	private Date startDate;
	
	private Date endDate;

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Creates a new <code>TimeseriesArea</code> instance.
	 * 
	 * @param priceArea
	 *            the price area.
	 * @param volumeArea
	 *            the volume area.
	 * @param logicDateAxis
	 *            the logic date axis.
	 */
	public TimeseriesArea(PriceArea priceArea, VolumeArea volumeArea,
			LogicDateAxis logicDateAxis) {
		if (priceArea == null) {
			throw new IllegalArgumentException("Null 'priceArea' argumented.");
		}
		this.priceArea = priceArea;

		if (volumeArea == null) {
			throw new IllegalArgumentException("Null 'volumeArea' argumented.");
		}
		this.volumeArea = volumeArea;

		if (logicDateAxis == null) {
			throw new IllegalArgumentException(
					"Null 'logicDateAxis' argumented.");
		}
		this.logicDateAxis = logicDateAxis;
	}

	public LogicDateAxis getlogicDateAxis() {
		return logicDateAxis;
	}

	public void setlogicDateAxis(LogicDateAxis logicDateAxis) {
		if (logicDateAxis == null) {
			throw new IllegalArgumentException(
					"Null 'logicDateAxis' argumented.");
		}
		this.logicDateAxis = logicDateAxis;
	}

	public boolean isCreateLegend() {
		return createLegend;
	}

	public void setCreateLegend(boolean createLegend) {
		this.createLegend = createLegend;
	}

	public PriceArea getPriceArea() {
		return priceArea;
	}

	public void setPriceArea(PriceArea priceArea) {
		if (priceArea == null) {
			throw new IllegalArgumentException("Null 'priceArea' argumented.");
		}
		this.priceArea = priceArea;
	}

	public VolumeArea getVolumeArea() {
		return volumeArea;
	}

	public void setVolumeArea(VolumeArea volumeArea) {
		if (volumeArea == null) {
			throw new IllegalArgumentException("Null 'volumeArea' argumented.");
		}
		this.volumeArea = volumeArea;
	}

	public int getPriceWeight() {
		return priceWeight;
	}

	public void setPriceWeight(int priceWeight) {
		this.priceWeight = priceWeight;
	}

	public int getVolumeWeight() {
		return volumeWeight;
	}

	public void setVolumeWeight(int volumeWeight) {
		this.volumeWeight = volumeWeight;
	}

	public double getGap() {
		return gap;
	}

	public void setGap(double gap) {
		this.gap = gap;
	}

	/**
	 * Returns the date axis location that is determined by the plot
	 * orientation.
	 * 
	 * @return the date axis location.
	 */
	public AxisLocation getDateAxisLocation() {
		PlotOrientation orientation = getOrientation();
		AxisLocation result = null;
		if (orientation.equals(PlotOrientation.VERTICAL)) {
			result = AxisLocation.BOTTOM_OR_LEFT;
		} else if (orientation.equals(PlotOrientation.HORIZONTAL)) {
			result = AxisLocation.TOP_OR_LEFT;
		}

		return result;
	}
}
