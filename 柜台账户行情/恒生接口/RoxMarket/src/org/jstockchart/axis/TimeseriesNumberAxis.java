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

package org.jstockchart.axis;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTick;
import org.jfree.chart.axis.Tick;
import org.jfree.chart.axis.TickType;
import org.jfree.chart.axis.ValueTick;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Log;
import org.jstockchart.axis.logic.LogicNumberTick;
import org.jstockchart.util.ChartConst;

/**
 * <code>TimeseriesNumberAxis</code> extends <code>NumberAxis</code>, and
 * provides <code>NumberTick</code> for the axis.
 * 
 * @author Sha Jiang
 */
public class TimeseriesNumberAxis extends NumberAxis {

	private static final long serialVersionUID = -5295734128660376162L;

	private List<LogicNumberTick> logicTicks = null;

	private double openPrice;
	
	public double getOpenPrice() {
		return this.openPrice;
	}


	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}


	/**
	 * Creates a new <code>TimeseriesNumberAxis</code> instance.
	 * 
	 * @param label
	 *            the axis label.
	 * @param logicTicks
	 *            the logic number ticks.
	 */
	public TimeseriesNumberAxis(String label, List<LogicNumberTick> logicTicks) {
		super(label);
		defaultSetting();

		if (logicTicks == null || logicTicks.size() == 0) {
			throw new IllegalArgumentException(
					"Null 'logicTicks' argement; or 'logicTicks.size' == 0.");
		}
		this.logicTicks = logicTicks;
	}

	
	/**
	 * Creates a new <code>TimeseriesNumberAxis</code> instance.
	 * 
	 * @param logicTicks
	 *            the logic number ticks.
	 */
	public TimeseriesNumberAxis(List<LogicNumberTick> logicTicks) {
		this(null, logicTicks);
	}

	public List<Tick> refreshTicksHorizontal(Graphics2D g2,
			Rectangle2D dataArea, RectangleEdge edge) {
		TextAnchor anchor = null;
		TextAnchor rotationAnchor = null;
		double angle = 0.0;
		if (isVerticalTickLabels()) {
			anchor = TextAnchor.CENTER_RIGHT;
			rotationAnchor = TextAnchor.CENTER_RIGHT;
			if (edge == RectangleEdge.TOP) {
				angle = Math.PI / 2.0;
			} else {
				angle = -Math.PI / 2.0;
			}
		} else {
			if (edge == RectangleEdge.TOP) {
				anchor = TextAnchor.BOTTOM_CENTER;
				rotationAnchor = TextAnchor.BOTTOM_CENTER;
			} else {
				anchor = TextAnchor.TOP_CENTER;
				rotationAnchor = TextAnchor.TOP_CENTER;
			}
		}
		return createTicks(logicTicks, anchor, rotationAnchor, angle);
	}

	
	
	public List<Tick> refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea,
			RectangleEdge edge) {
		TextAnchor anchor = null;
		TextAnchor rotationAnchor = null;
		double angle = 0.0;
		if (isVerticalTickLabels()) {
			anchor = TextAnchor.BOTTOM_CENTER;
			rotationAnchor = TextAnchor.BOTTOM_CENTER;
			if (edge == RectangleEdge.LEFT) {
				angle = -Math.PI / 2.0;
			} else {
				angle = Math.PI / 2.0;
			}
		} else {
			if (edge == RectangleEdge.LEFT) {
				anchor = TextAnchor.CENTER_RIGHT;
				rotationAnchor = TextAnchor.CENTER_RIGHT;
			} else {
				anchor = TextAnchor.CENTER_LEFT;
				rotationAnchor = TextAnchor.CENTER_LEFT;
			}
		}
		return createTicks(logicTicks, anchor, rotationAnchor, angle);
	}

	private static List<Tick> createTicks(
			List<LogicNumberTick> logicNumberTicks, TextAnchor anchor,
			TextAnchor rotationAnchor, double angle) {
		List<Tick> result = new ArrayList<Tick>();
		for (int i = 0; i < logicNumberTicks.size(); i++) {
			LogicNumberTick buf = (LogicNumberTick) logicNumberTicks.get(i);
			result.add(new NumberTick(buf.getTickNumber(), buf.getTickLabel(),
					anchor, rotationAnchor, angle));
		}

		return result;
	}

	
	
	private void defaultSetting() {
		setLowerMargin(ChartConst.DEFAULT_NUMBER_AXIS_MARGIN);
		setUpperMargin(ChartConst.DEFAULT_NUMBER_AXIS_MARGIN);
		setTickLabelFont(ChartConst.DEFAULT_NUMBER_TICK_LABEL_FONT);
		setTickLabelPaint(ChartConst.DEFAULT_NUMBER_TICK_LABEL_COLOR);
		setAutoRangeIncludesZero(false);
	}
}
