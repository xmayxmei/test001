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

package org.jstockchart.time;

import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;

/**
 * Gets <code>TimeSeriesDataItem</code> and adds "missed" data automatically,
 * then creates <code>TimeSeries</code>.
 * 
 * @author Sha Jiang
 */
public class SegmentedTimeSeries {

	private static final long serialVersionUID = 8495465814277398614L;

	private TimeSeries timeseries = null;

	private TimeSeriesDataItem lastItem = null;

	private int step = 0;

	private SegmentedTimeline timeline = null;

	private int size = 0;

	/**
	 * Creates a new <code>SegmentedTimeSeries</code> instance.
	 * 
	 * @param name
	 *            the series name
	 * @param timePeriodClass
	 *            the type of time period.
	 * @param step
	 *            the time step.
	 * @param timeline
	 *            the segmented timeline(<code>null</code> is permitted).
	 */
	public SegmentedTimeSeries(String name, int step, SegmentedTimeline timeline) {
		timeseries = new TimeSeries(name);
		this.step = step;
		this.timeline = timeline;
	}

	/**
	 * Adds a <code>TimeSeriesDataItem</code> instance to the series.
	 * 
	 * @param item
	 *            the <code>TimeSeriesDataItem</code> instance.
	 */
	public void addItem(TimeSeriesDataItem item) {
		if (lastItem != null) {
			long lastStart = lastItem.getPeriod().getFirstMillisecond();
			long thisStart = item.getPeriod().getFirstMillisecond();
			long thisEnd = item.getPeriod().getLastMillisecond();
			int diff = 0;
			if (step > 0) {
				diff = (int) ((thisStart - lastStart) / (thisEnd - thisStart) - 1)
						/ step;
			}
			RegularTimePeriod bufPeriod = lastItem.getPeriod().next();
			Number bufValue = lastItem.getValue();
			for (int i = 0; i < diff; i++) {
				TimeSeriesDataItem bufItem = new TimeSeriesDataItem(bufPeriod,
						bufValue);
				if (timeline != null) {
					if (timeline.containsDomainValue(bufPeriod
							.getLastMillisecond())) {
						timeseries.addOrUpdate(bufItem);
						size++;
					}
				} else {
					timeseries.add(bufItem);
					size++;
				}
				bufPeriod = bufItem.getPeriod().next();
			}
		}
		timeseries.addOrUpdate(item);
		//timeseries.add(item);
		size++;
		lastItem = item;
	}

	public TimeSeries getTimeSeries() {
		return timeseries;
	}

	public int size() {
		return size;
	}
}
