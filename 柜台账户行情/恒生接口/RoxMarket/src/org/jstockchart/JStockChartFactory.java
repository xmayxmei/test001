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

package org.jstockchart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jstockchart.area.TimeseriesArea;
import org.jstockchart.dataset.TimeseriesDataset;
import org.jstockchart.plot.TimeseriesPlot;

/**
 * A set of utility methods for creating some charts with JStockChart.
 * 
 * @author Sha Jiang
 */
public abstract class JStockChartFactory {

	/**
	 * Creates timeseries chart.
	 * 
	 * @param title
	 *            the chart title (<code>null</code> permitted).
	 * @param dataset
	 *            timeseries data set(<code>null</code> not permitted).
	 * @param timeline
	 *            a "segmented" timeline.
	 * @param timeseriesArea
	 *            timeseries area.
	 * @param legend
	 *            a flag indicating whether or not a legend should be created.
	 * @return JFreeChart instance.
	 */
	public static JFreeChart createTimeseriesChart(String title,
			TimeseriesDataset dataset, SegmentedTimeline timeline,
			TimeseriesArea timeseriesArea, boolean legend) {
		TimeseriesPlot timeseriesPlot = new TimeseriesPlot(dataset, timeline,timeseriesArea);

		JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,timeseriesPlot.getTimeseriesPlot(), legend);
		chart.setBackgroundPaint(timeseriesArea.getBackgroudColor());

		return chart;
	}
}
