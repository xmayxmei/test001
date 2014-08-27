/*
 * WND_PROC.java
 *
 * Created on 12. März 2007, 10:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.test.windows.trayicon;

import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.POINT;
import org.xvolks.jnative.misc.NOTIFYICONDATA;
import org.xvolks.jnative.util.Callback;
import org.xvolks.jnative.util.User32;
import org.xvolks.jnative.util.constants.winuser.WM;

/**
 *
 * @author osthop
 */
public class TrayIconCallback implements Callback
{
	
	private int myAddress = -1;
	private TrayIcon owner;
	private POINT lpPoint = null;
	private int WM_TASKBAR_CREATED = 0;
	
	
	private TrayIconCallback()
	{
		
	}
	
	public TrayIconCallback(TrayIcon owner)
	{
		this.owner = owner;
		try
		{
			lpPoint = new POINT();
			WM_TASKBAR_CREATED = User32.RegisterWindowMessage("TaskbarCreated");
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	public int callback(long[] values)
	{
		if(values != null && values.length == 4)
		{
			if(owner.getHwnd().getValue() == (int)values[0])
			{
				try
				{
					final int message = (int)values[1];
					final int wParam = (int)values[2];
					final int lParam = (int)values[3];
					
					if(message == WM_TASKBAR_CREATED)
					{
						SwingUtilities.invokeLater(new Runnable()
						{
							public void run()
							{
								try
								{
									owner.setVisible(true);
								}
								catch (Throwable ex)
								{
									ex.printStackTrace();
								}
							}
						});
					}
					else if (message == WM.WM_COMMAND.getValue())
					{
						//System.out.println("WM_COMMAND!");
					}
					else if (message == WM.WM_ACTIVATEAPP.getValue())
					{
						if(wParam == 0 && owner.isMenuVisible())
						{
							SwingUtilities.invokeLater(new Runnable()
							{
								public void run()
								{
									owner.setMenuVisible(false,0,0);
								}
							});
						}
					}
					else if (message == owner.getCallbackMsg())
					{
						if( lParam == WM.WM_LBUTTONUP.getValue())
						{
							if(owner.hasMouseListener() && User32.GetCursorPos(lpPoint))
							{
								final int x = lpPoint.getX();
								final int y = lpPoint.getY();
								SwingUtilities.invokeLater(new Runnable()
								{
									public void run()
									{
										owner.getMouseListener().mouseClicked(new MouseEvent(owner.getMsgWindow(), MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),MouseEvent.BUTTON1_MASK, x, y, 1, false));
									}
								});
							}
						}
						else if(lParam ==  WM.WM_RBUTTONUP.getValue())
						{
							if(owner.hasMouseListener() && User32.GetCursorPos(lpPoint))
							{
								final int x = lpPoint.getX();
								final int y = lpPoint.getY();
								SwingUtilities.invokeLater(new Runnable()
								{
									public void run()
									{
										owner.getMouseListener().mouseClicked(new MouseEvent(owner.getMsgWindow(), MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),MouseEvent.BUTTON3_MASK, x, y, 1, true));
									}
								});
							}
						}
						else if(lParam == WM.WM_LBUTTONDBLCLK.getValue())
						{
							if(owner.hasMouseListener() && User32.GetCursorPos(lpPoint))
							{
								final int x = lpPoint.getX();
								final int y = lpPoint.getY();
								SwingUtilities.invokeLater(new Runnable()
								{
									public void run()
									{
										owner.getMouseListener().mouseClicked(new MouseEvent(owner.getMsgWindow(), MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),MouseEvent.BUTTON1_MASK, x, y, 2, false));
									}
								});
							}
						}
						else if(lParam == WM.WM_RBUTTONDBLCLK.getValue())
						{
							if(owner.hasMouseListener() && User32.GetCursorPos(lpPoint))
							{
								final int x = lpPoint.getX();
								final int y = lpPoint.getY();
								SwingUtilities.invokeLater(new Runnable()
								{
									public void run()
									{
										owner.getMouseListener().mouseClicked(new MouseEvent(owner.getMsgWindow(), MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(),MouseEvent.BUTTON3_MASK, x, y, 2, false));
									}
								});
							}
						}
						else if(lParam == NOTIFYICONDATA.NIN_BALLOONSHOW)
						{
							if(owner.hasBallonTooltipListener())
							{
								SwingUtilities.invokeLater(new Runnable()
								{
									public void run()
									{
										owner.getBallonTooltipListener().balloonShow(owner);
									}
								});
							}
						}
						else if(lParam == NOTIFYICONDATA.NIN_BALLOONUSERCLICK)
						{
							if(owner.hasBallonTooltipListener())
							{
								SwingUtilities.invokeLater(new Runnable()
								{
									public void run()
									{
										owner.getBallonTooltipListener().balloonClick(owner);
									}
								});
							}
						}
						else if(lParam == NOTIFYICONDATA.NIN_BALLOONHIDE)
						{
							if(owner.hasBallonTooltipListener())
							{
								SwingUtilities.invokeLater(new Runnable()
								{
									public void run()
									{
										owner.getBallonTooltipListener().balloonHide(owner);
									}
								});
							}
						}
						else if(lParam == NOTIFYICONDATA.NIN_BALLOONTIMEOUT)
						{
							if(owner.hasBallonTooltipListener())
							{
								SwingUtilities.invokeLater(new Runnable()
								{
									public void run()
									{
										owner.getBallonTooltipListener().balloonClose(owner);
									}
								});
							}
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		try
		{
			return User32.CallWindowProc(owner.getPrevWindowProc(), (int)values[0], (int)values[1], (int)values[2], (int)values[3]);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getCallbackAddress() throws NativeException
	{
		if(myAddress == -1)
		{
			myAddress = JNative.createCallback(4, this);
		}
		return myAddress;
	}
	
}
