package org.jstockchart.plot;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

public class CFXCombinedPlot extends CombinedDomainXYPlot {
	private static final long serialVersionUID = 1L;

	private double mainPlotHeigh=145;
	
	private double tiPlotHeigh=45;
	
	private AxisSpace space;

	public AxisSpace getSpace() {
		return space;
	}

	public void setSpace(AxisSpace space) {
		this.space = space;
	}

	public double getMainPlotHeigh() {
		return mainPlotHeigh;
	}

	public void setMainPlotHeigh(int mainPlotHeigh) {
		this.mainPlotHeigh = mainPlotHeigh;
	}

	public double getTiPlotHeigh() {
		return tiPlotHeigh;
	}

	public void setTiPlotHeigh(int tiPlotHeigh) {
		this.tiPlotHeigh = tiPlotHeigh;
	}

	public Rectangle2D[] getSubplotAreas() {
		return subplotAreas;
	}

	public void setSubplotAreas(Rectangle2D[] subplotAreas) {
		this.subplotAreas = subplotAreas;
	}

	/** Temporary storage for the subplot areas. */
	private transient Rectangle2D[] subplotAreas;

	public CFXCombinedPlot(ValueAxis axis) {
		super(axis);
	}

	@Override
	public AxisSpace calculateAxisSpace(Graphics2D g2, Rectangle2D plotArea) {
		AxisSpace space = new AxisSpace();
		PlotOrientation orientation = getOrientation();

		// work out the space required by the domain axis...
		AxisSpace fixed = getFixedDomainAxisSpace();
		if (fixed != null) {
			if (orientation == PlotOrientation.HORIZONTAL) {
				space.setLeft(fixed.getLeft());
				space.setRight(fixed.getRight());
			} else if (orientation == PlotOrientation.VERTICAL) {
				space.setTop(fixed.getTop());
				space.setBottom(fixed.getBottom());
			}
		} else {
			ValueAxis xAxis = getDomainAxis();
			RectangleEdge xEdge = Plot.resolveDomainAxisLocation(getDomainAxisLocation(), orientation);
			if (xAxis != null) {
				space = xAxis.reserveSpace(g2, this, plotArea, xEdge, space);
			}
		}

		Rectangle2D adjustedPlotArea = space.shrink(plotArea, null);

		// work out the maximum height or width of the non-shared axes...
		int n = super.getSubplots().size();
		int totalWeight = 0;

		this.subplotAreas = new Rectangle2D[n];
		double x = adjustedPlotArea.getX();
		double y = adjustedPlotArea.getY();
		double usableSize = 0.0;
		if (orientation == PlotOrientation.HORIZONTAL) {
			usableSize = adjustedPlotArea.getWidth() - super.getGap() * (n - 1);
		} else if (orientation == PlotOrientation.VERTICAL) {
			usableSize = adjustedPlotArea.getHeight() - super.getGap()* (n - 1);
		}

		for (int i = 0; i < 1; i++) {
			XYPlot plot = (XYPlot) super.getSubplots().get(i);

			// calculate sub-plot area
			if (orientation == PlotOrientation.HORIZONTAL) {
				double w = usableSize * plot.getWeight() / totalWeight;
				this.subplotAreas[i] = new Rectangle2D.Double(x, y, w,adjustedPlotArea.getHeight());
				x = x + w + super.getGap();
			} else if (orientation == PlotOrientation.VERTICAL) {
				double h = usableSize*0.8;
				if (i > 0) {
					tiPlotHeigh=h;
				}else{
					mainPlotHeigh=h;
				}
				this.subplotAreas[i] = new Rectangle2D.Double(x, y,
						adjustedPlotArea.getWidth(), h);
				if (i == 0) {
					y = y + mainPlotHeigh + super.getGap();
				} else {
					y = y + tiPlotHeigh + super.getGap();
				}

				AxisSpace subSpace = plot.calculateRangeAxisSpace(g2,this.subplotAreas[i], null);
				space.ensureAtLeast(subSpace);

			}
		}
		 y=y+space.getBottom();
		for (int i = 1; i < n; i++) {
			XYPlot plot = (XYPlot) super.getSubplots().get(i);

			// calculate sub-plot area
			if (orientation == PlotOrientation.HORIZONTAL) {
				double w = usableSize * plot.getWeight() / totalWeight;
				this.subplotAreas[i] = new Rectangle2D.Double(x, y, w,adjustedPlotArea.getHeight());
				x = x + w + super.getGap();
			} else if (orientation == PlotOrientation.VERTICAL) {
				double h = usableSize*0.2;
				tiPlotHeigh=h;
				this.subplotAreas[i] = new Rectangle2D.Double(x, y,adjustedPlotArea.getWidth(), h);
				if (i == 0) {
					y = y + mainPlotHeigh + super.getGap();
				} else {
					y = y + tiPlotHeigh + super.getGap();
				}

			}
			AxisSpace subSpace = plot.calculateRangeAxisSpace(g2,this.subplotAreas[i], null);
			space.ensureAtLeast(subSpace);
		}
		
		return space;
	}

	@Override
	public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor,
			PlotState parentState, PlotRenderingInfo info) {
		// TODO Auto-generated method stub
		if (info != null) {
			info.setPlotArea(area);
		}

		// adjust the drawing area for plot insets (if any)...
		RectangleInsets insets = getInsets();
		insets.trim(area);

		setFixedRangeAxisSpaceForSubplots(null);
		this.space = calculateAxisSpace(g2, area);
		Rectangle2D dataArea = this.space.shrink(area, null);

		// set the width and height of non-shared axis of all sub-plots
		setFixedRangeAxisSpaceForSubplots(space);

		 // draw the shared axis
        ValueAxis axis = getDomainAxis();
        RectangleEdge edge = getDomainAxisEdge();
        double cursor = RectangleEdge.coordinate(  this.subplotAreas[0], edge)+1;
        AxisState axisState = axis.draw(g2, cursor, area, dataArea, edge, info);
        if (parentState == null) {
            parentState = new PlotState();
        }
        parentState.getSharedAxisStates().put(axis, axisState);

		// draw all the subplots
		for (int i = 0; i < super.getSubplots().size(); i++) {
			XYPlot plot = (XYPlot) super.getSubplots().get(i);
			PlotRenderingInfo subplotInfo = null;
			if (info != null) {
				subplotInfo = new PlotRenderingInfo(info.getOwner());
				info.addSubplotInfo(subplotInfo);
			}
			plot.draw(g2, this.subplotAreas[i], anchor, parentState,subplotInfo);
		}

		if (info != null) {
			info.setDataArea(dataArea);
		}
	}

}