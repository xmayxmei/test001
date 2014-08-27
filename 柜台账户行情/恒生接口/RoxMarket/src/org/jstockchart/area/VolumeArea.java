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
import org.jstockchart.axis.logic.LogicNumberAxis;
import org.jstockchart.util.ChartConst;

/**
 * <code>VolumeArea</code> represents volume part in timeseries chart.
 * 
 * @author Sha Jiang
 */
public class VolumeArea extends AbstractArea {

	private static final long serialVersionUID = -1931559782525499933L;

	private LogicNumberAxis logicVolumeAxis = null;

	private Color volumeColor = ChartConst.DEFAULT_VOLUME_COLOR;

	/**
	 * Creates a new <code>VolumeArea</code> instance.
	 * 
	 * @param logicVolumeAxis
	 *            the logic number axis that represents volume axis.
	 */
	public VolumeArea(LogicNumberAxis logicVolumeAxis) {
		if (logicVolumeAxis == null) {
			throw new IllegalArgumentException(
					"Null 'logicVolumeAxis' argument.");
		}
		this.logicVolumeAxis = logicVolumeAxis;
	}

	public LogicNumberAxis getLogicVolumeAxis() {
		return logicVolumeAxis;
	}

	public void setlogicVolumeAxis(LogicNumberAxis logicVolumeAxis) {
		if (logicVolumeAxis == null) {
			throw new IllegalArgumentException(
					"Null 'logicVolumeAxis' argument.");
		}
		this.logicVolumeAxis = logicVolumeAxis;
	}

	public Color getVolumeColor() {
		return volumeColor;
	}

	public void setVolumeColor(Color volumeColor) {
		this.volumeColor = volumeColor;
	}

	/**
	 * Returns the volume axis location that is determined by the plot
	 * orientation.
	 * 
	 * @return the volume axis location.
	 */
	public AxisLocation getVolumeAxisLocation() {
		PlotOrientation orientation = getOrientation();
		AxisLocation result = null;
		if (orientation.equals(PlotOrientation.VERTICAL)) {
			result = AxisLocation.BOTTOM_OR_LEFT;
		} else if (orientation.equals(PlotOrientation.HORIZONTAL)) {
			result = AxisLocation.TOP_OR_RIGHT;
		}

		return result;
	}
}
