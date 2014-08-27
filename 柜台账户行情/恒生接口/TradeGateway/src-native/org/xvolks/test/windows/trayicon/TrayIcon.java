/*
 * TrayIcon.java
 *
 * Created on 5. M?rz 2007, 16:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.test.windows.trayicon;

import java.awt.Frame;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.misc.WindowsVersion;
import org.xvolks.jnative.util.*;
import org.xvolks.jnative.exceptions.*;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.misc.NOTIFYICONDATA;
import org.xvolks.jnative.misc.WNDCLASSEX;
import org.xvolks.jnative.util.constants.winuser.WindowsConstants;
import org.xvolks.jnative.util.windows.WindowsUtils;

/**
 *
 * @author osthop
 * This class only works properly if you call it with -Xbootclasspath/p:[Path to JNative.jar] added to VM arguments
 */
public final class TrayIcon
{
	
	private final MouseListener m_mouseListener = new TrayIconMouseListener();
	private MouseListener mouseListener = null;
	private NOTIFYICONDATA iconData = new NOTIFYICONDATA();
	private Frame owner = null;
	private JPopupMenu pMenu = null;
	private boolean leftMouseDoubleClick = false;
	private ImageIcon img = null;
	private String toolTip = "";
	private boolean isVisible = false;
	private HWND hWnd;
	private int prevWindowProc;
	private Window dummyWindow = null;
	private int trayIconId;
	private Callback callback = new TrayIconCallback(this);
	private BalloonTooltipListener balloonTooltipListener = null;
	private boolean disposed = false;
	private LONG hImage = null;
	private static int CUSTOM_CALLBACK_MSG = 100;
	
	/** Creates a new instance of TrayIcon */
	private TrayIcon()
	{}
	
	// Very important:
	//
	// - PopupMenu opens with right-mouse-click by default.
	//   If you want to change this you need to write your own MouseListener and
	//	 add it with addMouseListener(), this removes the default MouseListener
	//	 Just copy the code from the below TrayIconMouseListener and use it as basis
	//
	/* Example:
	 
		private TrayIcon trayIcon = new .TrayIcon([owner Frame], [ImageIcon], [Tooltip], [use default MouseListener]);
	 
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   doExit();
		   }
		});
	 
		JMenuItem show = new JMenuItem("Show");
		show.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   setVisible(true);
			   toFront();
			   requestFocus();
		   }
		});
		JMenuItem hide = new JMenuItem("Hide");
		hide.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   setVisible(false);
		   }
		});
		trayIcon.addMenuItem(show);
		trayIcon.addMenuItem(hide);
		trayIcon.addSeperator();
		trayIcon.addMenuItem(exit);
		// or set own predefined PopupMenu with setPopupMenu();
		// or even disable PopupMenu with setPopupMenu(null);
	 
		trayIcon.setLeftMouseDoubleClick(true);
		trayIcon.setToolTip(frame.getTitle());
	 **/
	public TrayIcon(ImageIcon img)
	{
		this(null, img, null, false);
	}
	public TrayIcon(LONG hImage)
	{
		this(null, hImage, null, false);
	}
	public TrayIcon(Frame owner, ImageIcon img)
	{
		this(owner, img, null, true);
	}
	public TrayIcon(Frame owner, LONG hImage)
	{
		this(owner, hImage, null, true);
	}
	public TrayIcon(Frame owner, LONG hImage, String toolTip, boolean addDefaultMouseListener)
	{
		if(hImage == null || hImage.getValue() == 0)
		{
			throw new RuntimeException("Image handle must not be null!");
		}
		
		trayIconId = new Random().nextInt();
		
		dummyWindow = new Window(new Frame(getMsgWindowTitle()));
		dummyWindow.setVisible(true);
		
		try
		{
			hWnd = User32.FindWindow(null, getMsgWindowTitle());
			// removes the window from taskswitch
			//User32.SetWindowLong(hWnd, MSG.WindowsConstants.GWL_EXSTYLE, new LONG(MSG.WindowsConstants.WS_EX_TOOLWINDOW));
		}
		catch (Exception ex)
		{
			throw new RuntimeException("Error creating TrayIcon: "+ex);
		}
		
		if(addDefaultMouseListener)
		{
			setMouseListener(m_mouseListener);
		}
		
		this.owner = owner;
		this.hImage = hImage;
		this.toolTip = toolTip;
		
		pMenu = new JPopupMenu();
		pMenu.setLightWeightPopupEnabled(false);
		
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				try
				{
					dispose();
				}
				catch (Throwable ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}
	
	public TrayIcon(Frame owner, ImageIcon img, String toolTip, boolean addDefaultMouseListener)
	{
		if(img == null)
		{
			throw new RuntimeException("Icon-image must not be null!");
		}
		
		trayIconId = new Random().nextInt();
		
		dummyWindow = new Window(new Frame(getMsgWindowTitle()));
		dummyWindow.setVisible(true);
		
		try
		{
			hWnd = User32.FindWindow(null, getMsgWindowTitle());
			// removes the window from taskswitch
			//User32.SetWindowLong(hWnd, MSG.WindowsConstants.GWL_EXSTYLE, new LONG(MSG.WindowsConstants.WS_EX_TOOLWINDOW));
		}
		catch (Exception ex)
		{
			throw new RuntimeException("Error creating TrayIcon: "+ex);
		}
		
		if(addDefaultMouseListener)
		{
			setMouseListener(m_mouseListener);
		}
		
		this.owner = owner;
		this.img = img;
		this.toolTip = toolTip;
		pMenu = new JPopupMenu();
		pMenu.setLightWeightPopupEnabled(false);
		
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				try
				{
					dispose();
				}
				catch (Throwable ex)
				{
					ex.printStackTrace();
				}
			}
		});
		
		/*
		try
		{
			//callback = new TrayIconCallback(this);
			WNDCLASSEX wndclass = new WNDCLASSEX();
			wndclass.setStyle(new LONG((0x20 | 0x2 | 0x1)));
			wndclass.setLpszClassName("Thubb");
			wndclass.setHInstance(new LONG(JNative.getCurrentModule()));
			wndclass.setHbrBackground(new LONG(12));
			wndclass.setLpfnWndProc(new LONG(callback.getCallbackAddress()));
			wndclass.setLpszMenuName("Test1234");
			LONG classAtom = User32.RegisterClassEx(wndclass);
			wndclass.getPointer().dispose();
			
			System.out.println("classAtom: "+classAtom.getValue());
			
			hWnd = new HWND(User32.CreateWindowEx(0, "Thubb", ""+trayIconId, 0, 0, 0, 0, 0, 0, 0, 0, 0));
			System.out.println("Hwnd: "+hWnd);
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		*/
	}
	
	public int getPrevWindowProc()
	{
		return prevWindowProc;
	}
	public HWND getHwnd()
	{
		return hWnd;
	}
	public int getTrayIconId()
	{
		return trayIconId;
	}
	public Window getMsgWindow()
	{
		return dummyWindow;
	}
	public String getMsgWindowTitle()
	{
		return "Trayicon_invisible_frame_"+trayIconId;
	}
	public void dispose() throws Throwable
	{
		if(disposed)
			return;
		
		setVisible(false);
		dummyWindow.dispose();
		if(callback != null)
			JNative.releaseCallback(callback);
		disposed = true;
	}
	public NOTIFYICONDATA getIconData()
	{
		return iconData;
	}
	public void setVisible(boolean enable) throws Throwable
	{
		if(disposed)
		{
			JNative.getLogger().log(SEVERITY.WARN, "TrayIcon is already disposed!!");
			return;
		}
		
		if(enable)
		{
			iconData.hWnd = hWnd.getValue().intValue();
			iconData.uID = trayIconId;
			iconData.szTip = toolTip;
			iconData.uFlags = (NOTIFYICONDATA.NIF_INFO | NOTIFYICONDATA.NIF_ICON | NOTIFYICONDATA.NIF_TIP | NOTIFYICONDATA.NIF_MESSAGE);
			iconData.uCallbackMessage = (NOTIFYICONDATA.WM_USER + CUSTOM_CALLBACK_MSG++);
			
			if(img != null)
			{
				iconData.hIcon = WindowsUtils.CreateIcon(new LONG(iconData.hWnd), 4, 8, img).getValue().intValue();
			}
			else
			{
				iconData.hIcon = hImage.getValue();
			}
			
			// create the icon
			if(Shell32.Shell_NotifyIcon(NOTIFYICONDATA.NIM_ADD,iconData))
			{
				isVisible = true;
				if(WindowsVersion.supportsNewVersion())
				{
					iconData.uTimeOutOrVersion = NOTIFYICONDATA.NOTIFYICON_VERSION;
					Shell32.Shell_NotifyIcon(NOTIFYICONDATA.NIM_SETVERSION, iconData);
				}
				prevWindowProc = User32.SetWindowLong(hWnd, WindowsConstants.GWL_WNDPROC, new LONG(callback.getCallbackAddress()));
			}
			else
			{
				JNative.getLogger().log(SEVERITY.ERROR, "Error while creating TrayIcon "+getTrayIconId()+"!");
			}
		}
		else
		{
			if(isVisible)
			{
				// remove the icon
				if(Shell32.Shell_NotifyIcon(NOTIFYICONDATA.NIM_DELETE, iconData))
				{
					isVisible = false;
					User32.DestroyIcon(new LONG(iconData.hIcon));
					if(prevWindowProc != 0)
					{
						User32.SetWindowLong(hWnd, WindowsConstants.GWL_WNDPROC, new LONG(prevWindowProc));
					}
				}
				else
				{
					JNative.getLogger().log(SEVERITY.ERROR, "Error while removing TrayIcon "+getTrayIconId()+"!");
				}
			}
		}
	}
	public boolean isVisible()
	{
		return isVisible;
	}
	public void setToolTip(String toolTip)
	{
		iconData.szTip = toolTip;
		iconData.uFlags = (NOTIFYICONDATA.NIF_ICON | NOTIFYICONDATA.NIF_TIP | NOTIFYICONDATA.NIF_MESSAGE);
		updateTrayIcon();
	}
	
	// BalloonTooltips are not working correctly for me on new v1.6.0_10!!
	public void setBalloonTooltip(String title, String message, NOTIFYICONDATA.ICON_TYPE icon, int timeOut)
	{
		iconData.szInfo = message;
		iconData.szInfoTitle = title;
		iconData.dwInfoFlags = icon.ordinal();
		iconData.uFlags = NOTIFYICONDATA.NIF_INFO;
		iconData.uTimeOutOrVersion = timeOut;
		/*
		try
		{
			Shell32.Shell_NotifyIcon(NOTIFYICONDATA.NIM_SETFOCUS, iconData);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		 */
		updateTrayIcon();
	}
	
	public void updateTrayIcon()
	{
		if(isVisible())
		{
			try
			{
				Shell32.Shell_NotifyIcon(NOTIFYICONDATA.NIM_MODIFY, iconData);
			}
			catch(Throwable e)
			{
				JNative.getLogger().log(SEVERITY.ERROR, e);
			}
		}
	}
	public int getCallbackMsg()
	{
		return iconData.uCallbackMessage;
	}
	public ImageIcon getIconImage()
	{
		return img;
	}
	public LONG getImageHandle()
	{
		return hImage;
	}
	public void setIconImage(ImageIcon img) throws InterruptedException, NativeException, IllegalAccessException
	{
		this.img = img;
		this.hImage = null;
		iconData.uFlags = NOTIFYICONDATA.NIF_ICON;
		iconData.hIcon = WindowsUtils.CreateIcon(new LONG(iconData.hWnd), 4, 8, img).getValue().intValue();
		updateTrayIcon();
	}
	public void setIconImage(LONG hImage)
	{
		this.hImage = hImage;
		this.img = null;
		iconData.uFlags = NOTIFYICONDATA.NIF_ICON;
		iconData.hIcon = hImage.getValue().intValue();
		updateTrayIcon();
	}
	public boolean isMenuVisible()
	{
		if(pMenu != null)
			return pMenu.isVisible();
		return false;
	}
	public void setPopupMenu(JPopupMenu pMenu)
	{
		this.pMenu = pMenu;
	}
	public void addMenuItem(JMenuItem menuItem)
	{
		if(pMenu != null && menuItem != null)
			pMenu.add(menuItem);
	}
	public void addSeperator()
	{
		if(pMenu != null)
			pMenu.addSeparator();
	}
	public void removeMenuItem(JMenuItem menuItem)
	{
		if(pMenu != null && menuItem != null)
			pMenu.remove(menuItem);
	}
	public void setLeftMouseDoubleClick(boolean b)
	{
		leftMouseDoubleClick = b;
	}
	public boolean isLeftMouseDoubleClick()
	{
		return leftMouseDoubleClick;
	}
	public MouseListener removeMouseListener()
	{
		MouseListener ml = mouseListener;
		this.mouseListener = null;
		return ml;
	}
	public MouseListener setMouseListener(MouseListener mouseListener)
	{
		MouseListener ml = mouseListener;
		this.mouseListener = mouseListener;
		return ml;
	}
	public MouseListener getMouseListener()
	{
		return this.mouseListener;
	}
	public boolean hasMouseListener()
	{
		return (mouseListener != null);
	}
	public BalloonTooltipListener removeBalloonTooltipListener()
	{
		BalloonTooltipListener ml = balloonTooltipListener;
		balloonTooltipListener = null;
		return balloonTooltipListener;
	}
	public BalloonTooltipListener setBallonTooltipListener(BalloonTooltipListener balloonTooltipListener)
	{
		BalloonTooltipListener ml = balloonTooltipListener;
		this.balloonTooltipListener = balloonTooltipListener;
		return ml;
	}
	public BalloonTooltipListener getBallonTooltipListener()
	{
		return balloonTooltipListener;
	}
	public boolean hasBallonTooltipListener()
	{
		return (balloonTooltipListener != null);
	}
	public void updateUI()
	{
		if(pMenu != null)
		{
			SwingUtilities.updateComponentTreeUI(pMenu);
		}
	}
	public void setMenuVisible(final boolean b, final int x, final int y)
	{
		if(!b)
		{
			if(pMenu != null)
			{
				pMenu.setVisible(false);
			}
			//WindowsUtils.setAlwaysOnTop(hWnd,false);
		}
		else
		{
			if(pMenu != null && pMenu.getSubElements().length > 0)
			{
				//WindowsUtils.setAlwaysOnTop(hWnd,true);
				
				try
				{
					WindowsUtils.SetForegroundWindowEx(hWnd);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JNative.getLogger().log(SEVERITY.ERROR, ex);
				}
                pMenu.pack();
				pMenu.show(dummyWindow,x,y);               
				
				//WindowsUtils.setAlwaysOnTop(hWnd,false);
			}
		}
	}
	final class TrayIconMouseListener extends MouseAdapter
	{
		public final void mouseClicked(MouseEvent e)
		{
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				setMenuVisible(false, e.getX(), e.getY());
				
				// if clickCount > 1 it is a double click
				if((isLeftMouseDoubleClick() && e.getClickCount() > 1) || !isLeftMouseDoubleClick() )
				{
					if(owner != null)
					{
						owner.setVisible(!owner.isVisible());
						if(owner.isVisible())
						{
							owner.setExtendedState(Frame.NORMAL);
							owner.toFront();
							owner.requestFocus();
						}
					}
				}
			}
			else if(e.getButton() == MouseEvent.BUTTON3)
			{
				String javaVersion = System.getProperty("java.version");
				if(System.getProperty("java.class.version").equals("50.0") && Integer.parseInt(javaVersion.substring(javaVersion.lastIndexOf("_")+1)) >= 10)
				{
                    if(pMenu.getHeight() != 0)
                    {
                        setMenuVisible(true, e.getX(), e.getY()-pMenu.getHeight());
                    }
                    else
                    {
                        setMenuVisible(true, e.getX(), e.getY()-(pMenu.getSubElements().length*20));
                    }
				}
				else
				{
					setMenuVisible(true, e.getX(), e.getY());
				}
				
			}
			/*else {
				try {
					Shell32.Shell_NotifyIcon(NOTIFYICONDATA.NIM_SETFOCUS, iconData);
				} catch(Exception ee) {
					ee.printStackTrace();
				}
			}*/
		}
	}
	
	// OLD CODE!! DONT USE!!!!
	/*
	class TrayIconMouseListener extends MouseAdapter {
		public final void mouseClicked(MouseEvent e) {
			owner.requestFocus();
			// discard all events that are not related to Button-clicks
			if(e.getModifiersEx() == 0) {
				if(!isVisible()) {
					try {
						Shell32.Shell_NotifyIcon(NOTIFYICONDATA.NIM_SETFOCUS, iconData);
					} catch(Exception ee) {
						ee.printStackTrace();
					}
				}
				e.consume();
				return;
			}
			clickCount++;
			// forget previous clickCount if last click was too much time before
			if((System.currentTimeMillis() - lastClicked) > 500L) {
				clickCount = 1;
			}
			// discard all clicks that were too fast in a row
			if((System.currentTimeMillis() - lastClicked) < 50L) {
				clickCount = 0;
			}
			// it is a left-mouse-click
			else if(e.getModifiersExText(e.getModifiersEx()).equalsIgnoreCase("button1")) {
				if(pMenu != null)
					pMenu.setVisible(false);
	 
				User32.setAlwaysOnTop(hWnd,false);
	 
				// if clickCount > 1 it is a double click
				if((leftMouseDoubleClick && clickCount > 1) || !leftMouseDoubleClick ) {
					// if no other mouseListener is registered use the default behaviour:
					if(mouseListener == null) {
						if(!owner.getTitle().equalsIgnoreCase("Trayicon invisible frame")) {
							owner.setVisible(!owner.isVisible());
							if(owner.isVisible()) {
								owner.setExtendedState(Frame.NORMAL);
								owner.toFront();
								owner.requestFocus();
							}
						}
					}
					// custom mouseListener is registered
					else {
						mouseListener.mouseClicked(e);
					}
					clickCount = 0;
				}
			}
			// it is a right-mouse-click
			else if(e.getModifiersExText(e.getModifiersEx()).equalsIgnoreCase("button3")) {
				if(pMenu != null && pMenu.getSubElements().length > 0) {
					// sets the menu on top
					User32.setAlwaysOnTop(hWnd,true);
	 
					pMenu.pack();
					pMenu.show(null,e.getXOnScreen(),e.getYOnScreen());
					pMenu.setVisible(true);
	 
					User32.setAlwaysOnTop(hWnd,false);
				}
				clickCount = 0;
			}
			lastClicked = System.currentTimeMillis();
			e.consume();
		}
	}
	 **/
}
