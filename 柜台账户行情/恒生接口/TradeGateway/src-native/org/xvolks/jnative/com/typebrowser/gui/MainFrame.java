package org.xvolks.jnative.com.typebrowser.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.com.utils.ByRef;
import org.xvolks.jnative.com.utils.COMActuator;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger;
import org.xvolks.jnative.misc.basicStructures.HWND;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = -2940301076138202381L;
	public static void main(String[] args) {
		System.setProperty(JNativeLogger.LOG_LEVEL, "3");
		JNative.setLoggingEnabled(true);
		HWND hwnd = new HWND(0);
		ByRef<HWND> phwnd = new ByRef<HWND>(hwnd);
		try {
			COMActuator.installMainMessagePumpLoopInThread(phwnd);
		} catch (NativeException e) {
			JOptionPane.showMessageDialog(null, "Native Error occured\n"+e.toString(), "Error...",JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		long start = System.currentTimeMillis(); 
		JWindow w = new JWindow(new Frame());
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.ORANGE),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
				));
		panel.setLayout(new BorderLayout());
		w.getContentPane().add(panel);
		JLabel lb = new JLabel("Loading typebrowser....");
		panel.add(lb, BorderLayout.CENTER);
		JProgressBar progress = new JProgressBar();
		progress.setIndeterminate(true);
		panel.add(progress, BorderLayout.SOUTH);
		lb.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		progress.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		w.setVisible(true);
		w.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		w.setLocation((dim.width - w.getWidth())/2, (dim.height - w.getHeight())/2);
		long end = System.currentTimeMillis();
		JNative.getLogger().log(""+(end-start));
		JFrame f = new MainFrame();
		f.setExtendedState(MAXIMIZED_BOTH);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setVisible(true);
		w.setVisible(false);
		w.dispose();
	}
	public MainFrame() throws HeadlessException {
		super("JNative's typebrowser");
		add(new MainPanel());
	}

}
