package org.xvolks.jnative.com.typebrowser.gui.panels;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JTable;

public class TableColumnAdjuster implements ComponentListener {

	private JTable table;
	int oldWidth = -1;
	int oldHeight = -1;

	public TableColumnAdjuster(JTable table) {
		if (table.getParent() == null) {
			throw new IllegalStateException(
					"add table to JScrollPane before constructing TableColumnAdjuster");
		}
		table.getParent().getParent().addComponentListener(this);
		this.table = table;
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		adjustColumns();
	}

	public void componentResized(@SuppressWarnings("unused")ComponentEvent e) {
		if (oldWidth == table.getParent().getWidth()
				&& oldHeight == table.getParent().getHeight())
			return;
		adjustColumns();
		oldWidth = table.getParent().getWidth();
		oldHeight = table.getParent().getHeight();
	}

	public void componentHidden(@SuppressWarnings("unused")ComponentEvent e) {
	}

	public void componentMoved(@SuppressWarnings("unused")ComponentEvent e) {
	}

	public void componentShown(@SuppressWarnings("unused")ComponentEvent e) {
	}

	private void adjustColumns() {
		// set the widths
		int averageWidth = table.getParent().getWidth()
				/ table.getColumnCount();
		for (int c = 0; c < table.getColumnCount(); c++) {
			table.getColumnModel().getColumn(c).setPreferredWidth(averageWidth);
			table.getColumnModel().getColumn(c).setWidth(averageWidth);
		}
	}
}