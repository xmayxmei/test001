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

package org.jstockchart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer2;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jstockchart.area.PriceArea;
import org.jstockchart.area.TimeseriesArea;
import org.jstockchart.area.VolumeArea;
import org.jstockchart.axis.CFXNumberAxis;
import org.jstockchart.axis.TimeseriesDateAxis;
import org.jstockchart.axis.TimeseriesNumberAxis;
import org.jstockchart.axis.logic.CentralValueAxis;
import org.jstockchart.axis.logic.LogicDateAxis;
import org.jstockchart.axis.logic.LogicNumberAxis;
import org.jstockchart.dataset.TimeseriesDataset;
import org.jstockchart.util.DateUtils;

/**
 * Creates <code>CombinedDomainXYPlot</code> and <code>XYPlot</code> for the
 * timeseries chart.
 * 
 * @author Sha Jiang
 */
public class TimeseriesPlot {

	private static final long serialVersionUID = 8799771872991017065L;

	private TimeseriesDataset dataset = null;

	private SegmentedTimeline timeline = null;

	private TimeseriesArea timeseriesArea = null;

	/**
	 * Creates a new <code>TimeseriesPlot</code> instance.
	 * 
	 * @param dataset
	 *            timeseries data set(<code>null</code> not permitted).
	 * @param timeline
	 *            a "segmented" timeline.
	 * @param timeseriesArea
	 *            timeseries area.
	 */
	public TimeseriesPlot(TimeseriesDataset dataset,
			SegmentedTimeline timeline, TimeseriesArea timeseriesArea) {
		if (dataset == null) {
			throw new IllegalArgumentException("Null 'dataset' argument.");
		}
		this.dataset = dataset;

		this.timeline = timeline;

		if (timeseriesArea == null) {
			throw new IllegalArgumentException(
					"Null 'timeseriesArea' argument.");
		}
		this.timeseriesArea = timeseriesArea;
	}

	private CombinedDomainXYPlot createCombinedXYPlot() {
		Font axisFont=new Font("Arial",0,12);
		Stroke stroke = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.CAP_SQUARE, 0.0f, new float[] { 1.0f,  1.0f }, 1.0f);
		LogicDateAxis logicDateAxis = timeseriesArea.getlogicDateAxis();
		TimeseriesDateAxis dateAxis = new TimeseriesDateAxis(logicDateAxis.getLogicTicks());
		if (timeline != null) {
			dateAxis.setTimeline(timeline);
		}
		dateAxis.setTickLabelFont(axisFont);
		dateAxis.setTickMarkStroke(stroke);
		List <String> hideTick=new ArrayList<String>();
		hideTick.add("10:00");
		hideTick.add("11:00");
		hideTick.add("13:30");
		//hideTick.add("14:30");
		hideTick.add("14:30");
		dateAxis.setHideTickLabel(hideTick);
		dateAxis.setTickMarkPosition(DateTickMarkPosition.START);
		dateAxis.setTickMarksVisible(false);
		Date startTime = DateUtils.createDate(2008, 1, 1, 9, 30, 0);
		Date endTime   = DateUtils.createDate(2008, 1, 1, 15, 0, 0);
		dateAxis.setRange(timeseriesArea.getStartDate(), timeseriesArea.getEndDate());
		dateAxis.setAxisLineVisible(false);
		CFXCombinedPlot combinedDomainXYPlot = new CFXCombinedPlot(dateAxis);
		combinedDomainXYPlot.setInsets(new RectangleInsets(5,2,4,2));

		AxisSpace axisSpace=new AxisSpace();
		axisSpace.setBottom(22);
		axisSpace.setLeft(0);
		axisSpace.setRight(0);
		axisSpace.setTop(0);
		combinedDomainXYPlot.setFixedDomainAxisSpace(axisSpace);
		combinedDomainXYPlot.setGap(0);
		combinedDomainXYPlot.setOrientation(timeseriesArea.getOrientation());
		combinedDomainXYPlot.setDomainAxis(dateAxis);
		combinedDomainXYPlot.setDomainAxisLocation(timeseriesArea.getDateAxisLocation());
		
		if (timeseriesArea.getPriceWeight() <= 0
				&& timeseriesArea.getVolumeWeight() <= 0) {
			throw new IllegalArgumentException(
					"Illegal weight value: priceWeight="
							+ timeseriesArea.getPriceWeight()
							+ ", volumeWeight="
							+ timeseriesArea.getVolumeWeight());
		}

		if (timeseriesArea.getPriceWeight() > 0) {
			XYPlot pricePlot = createPricePlot();
			combinedDomainXYPlot.add(pricePlot, timeseriesArea.getPriceWeight());
		}

		if (timeseriesArea.getVolumeWeight() > 0) {
			XYPlot volumePlot = createVolumePlot();
			combinedDomainXYPlot.add(volumePlot, timeseriesArea.getVolumeWeight());
		}

		return combinedDomainXYPlot;
	}

	private XYPlot createPricePlot() {
		Font axisFont=new Font("Arial",0,12);
		Stroke stroke = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.CAP_SQUARE, 0.0f, new float[] { 1.0f, 1.0f }, 1.0f);
		PriceArea priceArea = timeseriesArea.getPriceArea();
		Color averageColor=new Color(243, 182, 117);
		priceArea.setAverageColor(averageColor);
		priceArea.setPriceColor(Color.BLUE);
		TimeSeriesCollection priceDataset = new TimeSeriesCollection();
		priceDataset.addSeries(dataset.getPriceTimeSeries().getTimeSeries());
		if (priceArea.isAverageVisible()) {
			priceDataset.addSeries(dataset.getAverageTimeSeries().getTimeSeries());
		}

		CentralValueAxis logicPriceAxis = priceArea.getLogicPriceAxis();
		
		logicPriceAxis.setTickCount(7);
		
		CFXNumberAxis priceAxis = new CFXNumberAxis(logicPriceAxis.getLogicTicks());
		priceAxis.setShowUD(true);
		priceAxis.setOpenPrice(logicPriceAxis.getCentralValue().doubleValue());
		priceAxis.setTickMarksVisible(false);
		XYLineAndShapeRenderer priceRenderer = new XYLineAndShapeRenderer(true,false);
		priceAxis.setUpperBound(logicPriceAxis.getUpperBound());
		priceAxis.setLowerBound(logicPriceAxis.getLowerBound());
		priceAxis.setAxisLineVisible(false);
		priceAxis.setTickLabelFont(axisFont);
		priceRenderer.setSeriesPaint(0, priceArea.getPriceColor());
		priceRenderer.setSeriesPaint(1, priceArea.getAverageColor());

		CFXNumberAxis rateAxis = new CFXNumberAxis(logicPriceAxis.getRatelogicTicks());
		rateAxis.setShowUD(true);
		rateAxis.setOpenPrice(logicPriceAxis.getCentralValue().doubleValue());
		rateAxis.setTickMarksVisible(false);;
		rateAxis.setTickLabelFont(axisFont);
		rateAxis.setAxisLineVisible(false);
		rateAxis.setUpperBound(logicPriceAxis.getUpperBound());
		rateAxis.setLowerBound(logicPriceAxis.getLowerBound());
		XYPlot plot = new XYPlot(priceDataset, null, priceAxis, priceRenderer);
		plot.setBackgroundPaint(priceArea.getBackgroudColor());
		plot.setOrientation(priceArea.getOrientation());
		plot.setRangeAxisLocation(priceArea.getPriceAxisLocation());
		plot.setRangeMinorGridlinesVisible(false);
		
		
		Stroke outLineStroke  = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.CAP_SQUARE, 0.0f, new float[] { 1.0f,1.0f }, 1.0f);
		Stroke gridLineStroke =new BasicStroke(0.5f,  BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.0f, new float[] {2.0f, 2.0f}, 1.0f);
		
		plot.setRangeGridlineStroke(gridLineStroke);
		plot.setDomainGridlineStroke(gridLineStroke);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);
		plot.setOutlineVisible(true);
		plot.setOutlineStroke(outLineStroke);
		plot.setOutlinePaint(Color.BLACK);

		if (priceArea.isRateVisible()) {
			plot.setRangeAxis(1, rateAxis);
			plot.setRangeAxisLocation(1, priceArea.getRateAxisLocation());
			plot.setDataset(1, null);
			plot.mapDatasetToRangeAxis(1, 1);
		}

		if (priceArea.isMarkCentralValue()) {
			Number centralPrice = logicPriceAxis.getCentralValue();
			if (centralPrice != null) {
				plot.addRangeMarker(new ValueMarker(centralPrice.doubleValue(),
						priceArea.getCentralPriceColor(), new BasicStroke()));
			}
		}
		return plot;
	}

	private XYPlot createVolumePlot() {
		Font axisFont=new Font("Arial",0,12);
		Stroke stroke =  new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.CAP_SQUARE, 0.0f, new float[] { 1.0f,1.0f }, 1.0f);
		VolumeArea volumeArea = timeseriesArea.getVolumeArea();
		LogicNumberAxis logicVolumeAxis = volumeArea.getLogicVolumeAxis();
		Color volumeColor=new Color(86, 126, 160);
		volumeArea.setVolumeColor(volumeColor);
		CFXNumberAxis volumeAxis = new CFXNumberAxis(logicVolumeAxis.getLogicTicks());
		volumeAxis.setAxisLineVisible(false);
		volumeAxis.setCustomTickCount(2);
		volumeAxis.setTickLabelPaint(volumeColor);
		volumeAxis.setUpperBound(logicVolumeAxis.getUpperBound());
		volumeAxis.setTickLabelFont(axisFont);
		volumeAxis.setTickMarkStroke(stroke);
		volumeAxis.setLowerBound(logicVolumeAxis.getLowerBound());
		volumeAxis.setAutoRangeIncludesZero(true);
		XYAreaRenderer2 volumeRenderer = new XYAreaRenderer2();
		volumeRenderer.setSeriesPaint(0, volumeArea.getVolumeColor());
		//volumeRenderer.setShadowVisible(false);
		volumeRenderer.setSeriesStroke(0, stroke);
		volumeRenderer.setBaseStroke(stroke);
		XYPlot plot = new XYPlot(new TimeSeriesCollection(dataset.getVolumeTimeSeries()), null, volumeAxis, volumeRenderer);
		plot.setBackgroundPaint(volumeArea.getBackgroudColor());
		plot.setOrientation(volumeArea.getOrientation());
		plot.setRangeAxisLocation(volumeArea.getVolumeAxisLocation());
		plot.setRangeMinorGridlinesVisible(false);
		
		Stroke outLineStroke  = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.CAP_SQUARE, 0.0f, new float[] { 1.0f,1.0f }, 1.0f);
		Stroke gridLineStroke =new BasicStroke(0.5f,  BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.0f, new float[] {2.0f, 2.0f}, 1.0f);
        // plot.setBackgroundPaint(Color.RED);
		plot.setRangeGridlineStroke(gridLineStroke);
		plot.setDomainGridlineStroke(gridLineStroke);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);
		plot.setOutlineVisible(true);
		plot.setOutlineStroke(outLineStroke);
		plot.setOutlinePaint(Color.black);
		plot.setRangeZeroBaselineVisible(true);
		return plot;
	}

	public CombinedDomainXYPlot getTimeseriesPlot() {
		return createCombinedXYPlot();
	}

	public TimeseriesDataset getDataset() {
		return dataset;
	}

	public void setDataset(TimeseriesDataset dataset) {
		if (dataset == null) {
			throw new IllegalArgumentException("Null 'dataset' argument.");
		}
		this.dataset = dataset;
	}

	public SegmentedTimeline getTimeline() {
		return timeline;
	}

	public void setTimeline(SegmentedTimeline timeline) {
		this.timeline = timeline;
	}

	public TimeseriesArea getTimeseriesArea() {
		return timeseriesArea;
	}

	public void setTimeseriesArea(TimeseriesArea timeseriesArea) {
		if (timeseriesArea == null) {
			throw new IllegalArgumentException(
					"Null 'timeseriesArea' argument.");
		}
		this.timeseriesArea = timeseriesArea;
	}
}
