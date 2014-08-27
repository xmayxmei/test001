package org.xvolks.jnative.com.typebrowser.gui.panels;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Vector;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import org.xvolks.jnative.com.typebrowser.business.CLSID;
import org.xvolks.jnative.com.typebrowser.business.CLSIDs;
import org.xvolks.jnative.com.typebrowser.gui.MainPanel;

public class ActiveXListPanel extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2952977019519847929L;
	protected static final String GUID_COL_NAME = "GUID";
	protected static final String CONTROL_COL_NAME = "Control";
	private JTable activeXTable;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new ActiveXListPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public ActiveXListPanel() {
		super();
		initGUI();
		addComponentListener(new ComponentAdapter() {
			public void componentResized(@SuppressWarnings("unused")ComponentEvent e) {
				// MainPanel.getRemainingSize();
			}
		});
	}

	private void initGUI() {
		try {
			Vector<String> headers = new Vector<String>();
			headers.add("ProgID");
			headers.add("Description");
			headers.add(CONTROL_COL_NAME);
			headers.add(GUID_COL_NAME);
			headers.add("InProcServer32");
			final CLSIDs clsids = new CLSIDs();

			Vector<Vector<Object>> datas = new Vector<Vector<Object>>();
			for (CLSID clsid : clsids.getCLSIDs()) {
				Vector<Object> data = new Vector<Object>();
				data.add(clsid.getProgID());
				data.add(clsid.getDescription());
				data.add(clsid.isControl());
				data.add(clsid.getGuid());
				data.add(clsid.getInprocServer32());
				datas.add(data);
			}
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] { 1, 0.1 };
			thisLayout.rowHeights = new int[] { 10, 1 };
			thisLayout.columnWeights = new double[] { 0.1 };
			thisLayout.columnWidths = new int[] { 7 };

			this.setLayout(thisLayout);
			{
				final DefaultTableModel activeXTableModel = new DefaultTableModel(
						datas, headers) {

					private static final long serialVersionUID = 6191716404387616674L;

					@Override
					public Class<?> getColumnClass(int c) {
						try {
							return getValueAt(0, c).getClass();
						} catch (NullPointerException e) {
							return String.class;
						}
					}

					@Override
					@SuppressWarnings("unused")
					public boolean isCellEditable(int arg0, int arg1) {
						return false;
					}

				};
				activeXTable = new JTable();
				final JScrollPane scrollPane = new JScrollPane(activeXTable);
				activeXTable.setAutoCreateRowSorter(true);
				activeXTable.setAutoCreateColumnsFromModel(true);
				activeXTable.setModel(activeXTableModel);
				final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(activeXTableModel);
				final RowFilter<DefaultTableModel, Object> rf = new RowFilter<DefaultTableModel, Object>() {

					@Override
					public boolean include(
							javax.swing.RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
						try {
							return (Boolean)entry.getValue(activeXTable.convertColumnIndexToModel(2));
						} catch (ClassCastException e) {
							e.printStackTrace();
							return false;
						}
					}
				};
				sorter.setRowFilter(rf);
				activeXTable.setRowSorter(sorter);
				
				activeXTable.getSelectionModel().setSelectionMode(
						DefaultListSelectionModel.SINGLE_SELECTION);
				activeXTable.setDefaultRenderer(String.class, new CourrierRenderer());
				activeXTable.getSelectionModel().addListSelectionListener(
						new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								if (e.getSource() instanceof DefaultListSelectionModel) {
									DefaultListSelectionModel dlsm = (DefaultListSelectionModel) e
											.getSource();
									if(dlsm.getMinSelectionIndex() >= 0) {
										int row = activeXTable.convertRowIndexToModel(dlsm.getMinSelectionIndex());
										MainPanel.getInstance().getProjectPropertiesPanel().setClsid(clsids.getCLSIDs().get(row));
									}
								}
							}
						});
				TableColumn column = null;
				for (int i = 0; i < 5; i++) {
					column = activeXTable.getColumnModel().getColumn(i);
					if (i == 2) {
						column.setPreferredWidth(50); // third column is
						// bigger
					} else {
						column.setPreferredWidth(250);
					}
				}
				this.add(new JCheckBox("Filter controls") {
					private static final long serialVersionUID = -1833339236458660684L;

					{
						setSelected(true);
						addActionListener(new ActionListener() {
							public void actionPerformed(@SuppressWarnings("unused")ActionEvent arg0) {
								sorter.setRowFilter(isSelected() ? rf : null);
							}
						});
					}
				}, new GridBagConstraints(0, 1, 1, 1, 0.0,
						0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

				this.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 0.0,
						0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public class CourrierRenderer extends DefaultTableCellRenderer {
    	private static final long serialVersionUID = 7282930193691161734L;
		Font f = new Font("Courier", Font.PLAIN, 12);
		
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if(value != null)
				if(value.toString().startsWith("{") &&  value.toString().endsWith("}"))
					c.setFont(f);
			return c;
		}
	}

}
