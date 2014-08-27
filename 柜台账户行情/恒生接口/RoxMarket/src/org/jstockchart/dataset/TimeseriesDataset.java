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

package org.jstockchart.dataset;

import java.util.List;
import java.util.TimeZone;

import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jstockchart.model.TimeseriesItem;
import org.jstockchart.time.SegmentedTimeSeries;

/**
 * Dataset for the timeseries chart.
 * 
 * @author Sha Jiang
 */
public class TimeseriesDataset {

	private static final String DEFAULT_PRICE_DESCRIPTION = "Price";

	private static final String DEFAULT_AVERAGE_DESCRIPTION = "Average";

	private static final String DEFAULT_VOLUME_DESCRIPTION = "Volume";

	private SegmentedTimeSeries priceTimeSeries = null;

	private SegmentedTimeSeries averageTimeSeries = null;

	private TimeSeries volumeTimeSeries = null;

	private Number maxPrice = null;

	private Number minPrice = null;

	private Number maxVolume = null;

	private Number minVolume = null;

	private Class<? extends TimePeriod> timePeriodClass = null;

	private TimeZone timeZone = null;

	private Average average = null;

	/**
	 * Creates a new <code>TimeseriesDataset</code> instance.
	 * 
	 * @param priceName
	 *            the price chart name.
	 * @param averageName
	 *            the average chart name.
	 * @param volumeName
	 *            the volume chart name.
	 * @param timePeriodClass
	 *            the <code>TimePeriod</code> class.
	 * @param step
	 *            the step of the time period.
	 * @param timeline
	 *            a "segmented" timeline.
	 * @param timeZone
	 *            the time zone.
	 * @param createAverage
	 *            indicates whether or not the average chart should be created.
	 */
	public TimeseriesDataset(String priceName, String averageName,
			String volumeName, Class<? extends TimePeriod> timePeriodClass,
			int step, SegmentedTimeline timeline, TimeZone timeZone,
			boolean createAverage) {
		priceTimeSeries = new SegmentedTimeSeries(priceName, step, timeline);
		averageTimeSeries = new SegmentedTimeSeries(averageName, step, timeline);
		volumeTimeSeries = new TimeSeries(volumeName);
		this.timePeriodClass = timePeriodClass;
		if (timeZone != null) {
			this.timeZone = timeZone;
		} else {
			this.timeZone = TimeZone.getDefault();
		}

		if (createAverage) {
			average = new Average();
		}
	}

	/**
	 * Creates a new <code>TimeseriesDataset</code> instance.
	 * 
	 * @param timePeriodClass
	 *            the <code>TimePeriod</code> class.
	 * @param step
	 *            the step of the time period.
	 * @param timeline
	 *            a "segmented" timeline.
	 * @param createAverage
	 *            indicates whether or not the average chart should be created.
	 */
	public TimeseriesDataset(Class<? extends TimePeriod> timePeriodClass,
			int step, SegmentedTimeline timeline, boolean createAverage) {
		this(DEFAULT_PRICE_DESCRIPTION, DEFAULT_AVERAGE_DESCRIPTION,
				DEFAULT_VOLUME_DESCRIPTION, timePeriodClass, step, timeline,
				TimeZone.getDefault(), createAverage);
	}

	/**
	 * Adds a list of <code>TimeseriesItem</code> instances to the data set.
	 * 
	 * @param dataItems
	 *            the list of <code>TimeseriesItem</code> instances.
	 */
	public void addDataItems(List<TimeseriesItem> dataItems) {
		for (int i = 0; i < dataItems.size(); i++) {
			addDataItem(dataItems.get(i));
		}
	}

	/**
	 * Adds a <code>TimeseriesItem</code> instance to the data set.
	 * 
	 * @param item
	 *            the <code>TimeseriesItem</code> instance.
	 */
	public void addDataItem(TimeseriesItem item) {
		RegularTimePeriod time = RegularTimePeriod.createInstance(
				timePeriodClass, item.getTime(), timeZone);
		priceTimeSeries.addItem(new TimeSeriesDataItem(time, item.getPrice()));
		volumeTimeSeries.addOrUpdate(new TimeSeriesDataItem(time, item.getVolume()));
		if (average != null) {
			average.setPriceVolume(item.getPrice(), item.getVolume());
			averageTimeSeries.addItem(new TimeSeriesDataItem(time,
					average.value));
		}

		if (maxPrice == null || maxPrice.doubleValue() < item.getPrice()) {
			maxPrice = new Double(item.getPrice());
		}
		if (minPrice == null || minPrice.doubleValue() > item.getPrice()) {
			minPrice = new Double(item.getPrice());
		}

		if (maxVolume == null || maxVolume.doubleValue() < item.getVolume()) {
			maxVolume = new Double(item.getVolume());
		}
		if (minVolume == null || minVolume.doubleValue() > item.getVolume()) {
			minVolume = new Double(item.getVolume());
		}
	}

	public Number getMaxPrice() {
		return maxPrice;
	}

	public Number getMinPrice() {
		return minPrice;
	}

	public Number getMaxVolume() {
		return maxVolume;
	}

	public Number getMinVolume() {
		return minVolume;
	}

	public int size() {
		return priceTimeSeries.size();
	}

	public SegmentedTimeSeries getPriceTimeSeries() {
		return priceTimeSeries;
	}

	public SegmentedTimeSeries getAverageTimeSeries() {
		return averageTimeSeries;
	}

	public TimeSeries getVolumeTimeSeries() {
		return volumeTimeSeries;
	}

	/**
	 * The <code>Average</code> is used for calculating average values.
	 */
	private static class Average {

		private double value = 0.0;

		private double volume = 0.0;

		private double amount = 0.0;

		void setPriceVolume(double price, double volume) {
			this.amount += price * volume;
			this.volume += volume;
			if (this.volume == 0) {
				this.value = price;
			} else {
				this.value = this.amount / this.volume;
			}
		}
	}
}
