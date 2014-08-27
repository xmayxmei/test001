package org.jstockchart.axis;

import java.awt.Color;

import org.jfree.chart.axis.NumberTick;
import org.jfree.ui.TextAnchor;

public class CFXNumberTick extends NumberTick {
	private static final long serialVersionUID = 1L;
	private  Color labeColor;
	public CFXNumberTick(Number number, String label, TextAnchor textAnchor,
			TextAnchor rotationAnchor, double angle,Color labelColor) {
		super(number, label, textAnchor, rotationAnchor, angle);
		// TODO Auto-generated constructor stub
		this.labeColor=labelColor;
	}
	public Color getLabeColor() {
		return this.labeColor;
	}
	public void setLabeColor(Color labeColor) {
		this.labeColor = labeColor;
	}
}
