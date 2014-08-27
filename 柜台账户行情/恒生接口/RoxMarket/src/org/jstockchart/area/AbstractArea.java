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
import java.io.Serializable;

import org.jfree.chart.plot.PlotOrientation;

/**
 * Abstract area.
 * 
 * @author Sha Jiang
 */
public abstract class AbstractArea implements Serializable {

	private static final long serialVersionUID = 8084442413013963065L;

	public static final PlotOrientation DEFAULT_ORIENTATION = PlotOrientation.VERTICAL;

	public static final Color DEFAULT_BACKGROUD_COLOR = Color.WHITE;

	private PlotOrientation orientation = DEFAULT_ORIENTATION;

	private Color backgroudColor = DEFAULT_BACKGROUD_COLOR;

	public PlotOrientation getOrientation() {
		return orientation;
	}

	public void setOrientation(PlotOrientation orientation) {
		this.orientation = orientation;
	}

	public Color getBackgroudColor() {
		return backgroudColor;
	}

	public void setBackgroudColor(Color backgroudColor) {
		this.backgroudColor = backgroudColor;
	}
}
