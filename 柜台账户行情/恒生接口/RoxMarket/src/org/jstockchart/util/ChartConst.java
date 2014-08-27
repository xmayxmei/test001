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

package org.jstockchart.util;

import java.awt.Color;
import java.awt.Font;

/**
 * Some constants.
 * 
 * @author Sha Jiang
 */
public class ChartConst {

	public static final double DEFAULT_NUMBER_AXIS_MARGIN = 0.0D;

	public static final Font DEFAULT_NUMBER_TICK_LABEL_FONT = new Font(
			"Courier New", Font.PLAIN, 10);

	public static final Font DEFAULT_DATE_TICK_LABEL_FONT = DEFAULT_NUMBER_TICK_LABEL_FONT;

	public static final Color DEFAULT_NUMBER_TICK_LABEL_COLOR = Color.BLACK;

	public static final Color DEFAULT_DATE_TICK_LABEL_COLOR = DEFAULT_NUMBER_TICK_LABEL_COLOR;

	public static final Color DEFAULT_PRICE_COLOR = Color.RED;

	public static final Color DEFAULT_AVERAGE_COLOR = Color.LIGHT_GRAY;

	public static final Color DEFAULT_CENTRALPRICE_COLOR = Color.GRAY;

	public static final Color DEFAULT_VOLUME_COLOR = Color.BLUE;
}
