package org.jstockchart.axis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.NumberTick;
import org.jfree.chart.axis.Tick;
import org.jfree.chart.axis.TickType;
import org.jfree.chart.axis.TickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.axis.ValueTick;
import org.jfree.data.Range;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.util.Log;
import org.jstockchart.axis.logic.LogicNumberTick;

public class CFXNumberAxis extends TimeseriesNumberAxis {
	public CFXNumberAxis(List<LogicNumberTick> logicTicks) {
		super(logicTicks);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int customTickCount=5;

	private double openPrice;
	
	private boolean isShowUD=false;
	
	public boolean isShowUD() {
		return this.isShowUD;
	}

	public void setShowUD(boolean isShowUD) {
		this.isShowUD = isShowUD;
	}

	public double getOpenPrice() {
		return this.openPrice;
	}

	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}

	public int getCustomTickCount() {
		return customTickCount;
	}

	public void setCustomTickCount(int customTickCount) {
		this.customTickCount = customTickCount;
	}


	 public AxisState drawTickMarksAndLabels(Graphics2D g2,
	            double cursor, Rectangle2D plotArea, Rectangle2D dataArea,
	            RectangleEdge edge) {

	        AxisState state = new AxisState(cursor);

	        if (isAxisLineVisible()) {
	            drawAxisLine(g2, cursor, dataArea, edge);
	        }

	        List ticks = refreshTicks(g2, state, dataArea, edge);
	        state.setTicks(ticks);
	        g2.setFont(getTickLabelFont());
	        Iterator iterator = ticks.iterator();
	        while (iterator.hasNext()) {
	            ValueTick tick = (ValueTick) iterator.next();
	            if (isTickLabelsVisible()) {
	            	if(isShowUD){
	            		if(tick.getValue()>this.openPrice){
		            		g2.setPaint(Color.red);
		            	}else if(tick.getValue()<this.openPrice){
		            		g2.setPaint(Color.green);
		            	}else{
		            		 g2.setPaint(getTickLabelPaint());
		            	}
	            	}else{
	            		g2.setPaint(getTickLabelPaint());
	            	}
	            	
	               
	                float[] anchorPoint = calculateAnchorPoint(tick, cursor,
	                        dataArea, edge);
	                //System.out.println("NumberLabel:"+tick.getText());
	                TextUtilities.drawRotatedString(tick.getText(), g2,
	                        anchorPoint[0], anchorPoint[1], tick.getTextAnchor(),
	                        tick.getAngle(), tick.getRotationAnchor());
	            }

	            if ((isTickMarksVisible() && tick.getTickType().equals(
	                    TickType.MAJOR)) || (isMinorTickMarksVisible()
	                    && tick.getTickType().equals(TickType.MINOR))) {

	                double ol = (tick.getTickType().equals(TickType.MINOR)) 
	                        ? getMinorTickMarkOutsideLength()
	                        : getTickMarkOutsideLength();

	                double il = (tick.getTickType().equals(TickType.MINOR)) 
	                        ? getMinorTickMarkInsideLength()
	                        : getTickMarkInsideLength();

	                float xx = (float) valueToJava2D(tick.getValue(), dataArea,
	                        edge);
	                Line2D mark = null;
	                g2.setStroke(getTickMarkStroke());
	                g2.setPaint(getTickMarkPaint());
	                if (edge == RectangleEdge.LEFT) {
	                    mark = new Line2D.Double(cursor - ol, xx, cursor + il, xx);
	                }
	                else if (edge == RectangleEdge.RIGHT) {
	                    mark = new Line2D.Double(cursor + ol, xx, cursor - il, xx);
	                }
	                else if (edge == RectangleEdge.TOP) {
	                    mark = new Line2D.Double(xx, cursor - ol, xx, cursor + il);
	                }
	                else if (edge == RectangleEdge.BOTTOM) {
	                    mark = new Line2D.Double(xx, cursor + ol, xx, cursor - il);
	                }
	                g2.draw(mark);
	            }
	        }

	        // need to work out the space used by the tick labels...
	        // so we can update the cursor...
	        double used = 0.0;
	        if (isTickLabelsVisible()) {
	            if (edge == RectangleEdge.LEFT) {
	                used += findMaximumTickLabelWidth(ticks, g2, plotArea,
	                        isVerticalTickLabels());
	                state.cursorLeft(used);
	            }
	            else if (edge == RectangleEdge.RIGHT) {
	                used = findMaximumTickLabelWidth(ticks, g2, plotArea,
	                        isVerticalTickLabels());
	                state.cursorRight(used);
	            }
	            else if (edge == RectangleEdge.TOP) {
	                used = findMaximumTickLabelHeight(ticks, g2, plotArea,
	                        isVerticalTickLabels());
	                state.cursorUp(used);
	            }
	            else if (edge == RectangleEdge.BOTTOM) {
	                used = findMaximumTickLabelHeight(ticks, g2, plotArea,
	                        isVerticalTickLabels());
	                state.cursorDown(used);
	            }
	        }

	        return state;
	    }
}