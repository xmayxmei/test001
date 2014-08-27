package org.xvolks.jnative.com.typebrowser.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import org.xvolks.jnative.com.typebrowser.gui.panels.ActiveXListPanel;
import org.xvolks.jnative.com.typebrowser.gui.panels.ProjectPropertiesPanel;

public class MainPanel extends javax.swing.JPanel {
    private static final long serialVersionUID = -7376243211023018052L;

	private static MainPanel instance;
	
	private JTabbedPane mainTabbedPane;
	private ActiveXListPanel activeXListPanel;
	private ProjectPropertiesPanel projectPropertiesPanel;
	private JPanel sp;
	private JButton Quit;

	public static MainPanel getInstance() {
		return instance;
	}
	
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new MainPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public MainPanel() {
		super();
		instance = this;
		initGUI();
	}
	
	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(437, 181));
			{
				mainTabbedPane = new JTabbedPane();
				this.add(mainTabbedPane, BorderLayout.CENTER);
				{
					activeXListPanel = new ActiveXListPanel();
					mainTabbedPane.addTab("ActiveX List", null, activeXListPanel, null);
					activeXListPanel.setPreferredSize(new java.awt.Dimension(432, 123));
				}
				{
					projectPropertiesPanel = new ProjectPropertiesPanel();
					mainTabbedPane.addTab(
						"Project properties",
						null,
						projectPropertiesPanel,
						null);
				}
			}
			{
				sp = new JPanel();
				this.add(sp, BorderLayout.SOUTH);
				FlowLayout spLayout = new FlowLayout();
				sp.setLayout(spLayout);
				{
					Quit = new JButton();
					Quit.setText("Quit");
					sp.add(Quit);
					Quit.addActionListener(new ActionListener() {
						public void actionPerformed(@SuppressWarnings("unused")ActionEvent evt) {
							System.exit(0);
						}
					});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ActiveXListPanel getActiveXListPanel() {
		return activeXListPanel;
	}

	public ProjectPropertiesPanel getProjectPropertiesPanel() {
		return projectPropertiesPanel;
	}

}
