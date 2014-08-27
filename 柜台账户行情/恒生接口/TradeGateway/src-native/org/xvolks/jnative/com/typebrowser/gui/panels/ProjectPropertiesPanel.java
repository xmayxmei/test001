package org.xvolks.jnative.com.typebrowser.gui.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.xvolks.jnative.com.typebrowser.business.CLSID;
import org.xvolks.jnative.com.typebrowser.business.export.ActiveXExporter;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.toolkit.FileToolkit;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ProjectPropertiesPanel extends javax.swing.JPanel {
    private static final long serialVersionUID = 8846184623906761127L;
	private CLSID clsid;
	private JLabel jLabel1;
	private JTextField tfPackage;
	private JLabel jLabel2;
	private JTextField tfDirectory;
	private JPanel spacer1;
	private JTextField tfSelectedActiveX;
	private JLabel selectedActiveXLb;
	private JButton Generate;
	private JPanel jPanel1;
	private JPanel spacer3;
	private JPanel spacer2;
	private JButton btDirectory;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new ProjectPropertiesPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public ProjectPropertiesPanel() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
			thisLayout.rowHeights = new int[] {20, 20, 7, 7, 20, 7};
			thisLayout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1, 0.1, 0.1};
			thisLayout.columnWidths = new int[] {20, 7, 7, 7, 7, 20};
			this.setLayout(thisLayout);
			setPreferredSize(new Dimension(400, 300));
			{
				jLabel1 = new JLabel();
				this.add(jLabel1, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel1.setText("Package");
			}
			{
				tfPackage = new JTextField();
				this.add(tfPackage, new GridBagConstraints(2, 2, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jLabel2 = new JLabel();
				this.add(jLabel2, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel2.setText("Directory");
			}
			{
				tfDirectory = new JTextField();
				this.add(tfDirectory, new GridBagConstraints(2, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				btDirectory = new JButton();
				this.add(btDirectory, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				btDirectory.setText("...");
				btDirectory.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						FileToolkit.selectDirectory(tfDirectory, "Select your source directory");
					}
				});
			}
			{
				spacer1 = new JPanel();
				this.add(spacer1, new GridBagConstraints(0, 0, 1, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				spacer2 = new JPanel();
				this.add(spacer2, new GridBagConstraints(1, 0, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				spacer3 = new JPanel();
				this.add(spacer3, new GridBagConstraints(1, 5, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				jPanel1 = new JPanel();
				this.add(jPanel1, new GridBagConstraints(5, 0, 1, 5, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			{
				Generate = new JButton();
				Generate.setEnabled(false);
				this.add(Generate, new GridBagConstraints(2, 4, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				Generate.setText("Generate");
				Generate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						if(clsid == null) {
							JOptionPane.showMessageDialog(ProjectPropertiesPanel.this, "Please, select an activeX control in the other pane before !", "Warning", JOptionPane.WARNING_MESSAGE);
						} else {
							try {
								new ActiveXExporter(clsid, tfPackage.getText(), tfDirectory.getText()).export();
							} catch (NativeException e) {
								JOptionPane.showMessageDialog(ProjectPropertiesPanel.this, e, "NativeException",JOptionPane.OK_OPTION);
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								JOptionPane.showMessageDialog(ProjectPropertiesPanel.this, e, "IllegalAccessException", JOptionPane.OK_OPTION);
								e.printStackTrace();
							} catch (Throwable e) {
								JOptionPane.showMessageDialog(ProjectPropertiesPanel.this, e, "Throwable", JOptionPane.OK_OPTION);
								e.printStackTrace();
							}
						}
					}
				});
			}
			{
				selectedActiveXLb = new JLabel();
				this.add(selectedActiveXLb, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				selectedActiveXLb.setText("Selected ActiveX");
			}
			{
				tfSelectedActiveX = new JTextField();
				this.add(tfSelectedActiveX, new GridBagConstraints(2, 1, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CLSID getClsid() {
		return clsid;
	}

	public void setClsid(CLSID clsid) {
		this.clsid = clsid;
		tfSelectedActiveX.setText(clsid.getDescription());
		tfPackage.setText("org.xvolks.jnative.interop."+clsid.getProgID());
		Generate.setEnabled(true);
	}

}
