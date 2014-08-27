package org.xvolks.test.windows;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.MSG;
import org.xvolks.jnative.misc.WNDCLASS;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.misc.basicStructures.LPARAM;
import org.xvolks.jnative.misc.basicStructures.UINT;
import org.xvolks.jnative.misc.basicStructures.WPARAM;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.NativeMemoryBlock;
import org.xvolks.jnative.util.Callback;
import org.xvolks.jnative.util.Kernel32;
import org.xvolks.jnative.util.User32;
import org.xvolks.jnative.util.WindowProc;
import org.xvolks.jnative.util.constants.winuser.WM;
import org.xvolks.jnative.util.constants.winuser.WindowsConstants;
import org.xvolks.jnative.util.windows.structures.DEV_BROADCAST_HANDLE;
import org.xvolks.jnative.util.windows.structures.DEV_BROADCAST_HDR;
import org.xvolks.jnative.util.windows.structures.DEV_BROADCAST_VOLUME;

public class DeviceNotificationTester
{
	
	/** A device has been added to or removed from the system. */
	public static final int DBT_DEVNODES_CHANGED =	0x0007;
	
	/** Permission is requested to change the current configuration (dock or undock). */
	public static final int DBT_QUERYCHANGECONFIG  = 0x0017;
	
	/** The meaning of this message is user-defined. */
	public static final int DBT_USERDEFINED  = 0xFFFF;
	
	public static final int DBT_NO_DISK_SPACE = 0x0047;
	public static final int DBT_LOW_DISK_SPACE = 0x0048;
	public static final int DBT_CONFIGMGPRIVATE = 0x7FFF;
	public static final int DBT_DEVICEARRIVAL = 0x8000;
	public static final int DBT_DEVICEQUERYREMOVE = 0x8001;
	public static final int DBT_DEVICEQUERYREMOVEFAILED = 0x8002;
	public static final int DBT_DEVICEREMOVEPENDING = 0x8003;
	public static final int DBT_DEVICEREMOVECOMPLETE = 0x8004;
	public static final int DBT_DEVICETYPESPECIFIC = 0x8005;
	public static final int DBT_CUSTOMEVENT = 0x8006;
	
	
	public static LONG RegisterClass(String szAppName, Callback wndProc, LONG hInstance) throws NativeException, IllegalAccessException
	{
		WNDCLASS wndclass = new WNDCLASS();
		
		wndclass.setStyle(new LONG(WNDCLASS.CS_HREDRAW | WNDCLASS.CS_VREDRAW));
		wndclass.setLpfnWndProc(new LONG(wndProc.getCallbackAddress()));
		wndclass.setCbClsExtra(0);
		wndclass.setCbWndExtra(0);
		wndclass.setHInstance(hInstance);
		wndclass.setHIcon(new LONG(0));
		wndclass.setHCursor(new LONG(0));
		wndclass.setHbrBackground(new LONG(0));
		wndclass.setLpszMenuName(null);
		wndclass.setLpszClassName(szAppName);
		try
		{
			return User32.RegisterClass(wndclass);
		}
		finally
		{
			System.err.println("WNDCLASS : " + wndclass);
		}
		
	}
	
	/**
	 * @param args
	 * @since Feb 5, 2008
	 */
	public static void main(String[] args) throws NativeException, IllegalAccessException, InterruptedException
	{
		final MSG msg = new MSG();
		Thread t = new Thread()
		{
			public void run()
			{
				try
				{
					while(true)
					{
						try
						{
//							System.err.println("boucle");
							int message = User32.GetMessage(msg, new HWND(0), 0, 0);
							if(message == WM.WM_QUIT.getValue())
							{
								System.err.println("WM_QUIT recieved");
								throw new ThreadDeath();
							}
							else
							{
//								System.out.println("Le message : " + msg.getMessage());
								User32.TranslateMessage(msg);
								User32.DispatchMessage(msg);
							}
							
//							System.err.println("boucle 2");
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
				catch (Throwable e)
				{
					e.printStackTrace();
				}
				System.out.println("sortie");
			}
		};
		//t.start();
		
		
		final int currentModule = JNative.getCurrentModule(); //Kernel32.GetModuleHandle(null);
		System.err.println("Module handle : " + Integer.toHexString(currentModule));
		
		final int hWnd =
				User32.CreateWindowEx(
				WindowsConstants.WS_EX_TOOLWINDOW,
				"Button",
				"GetMessages",
				WindowsConstants.WS_OVERLAPPED,
				-100, -100,
				0, 0,
				0, 0,
				currentModule, 0);
		
		if(hWnd != 0)
		{
			System.err.println("hWnd: "+hWnd);
			JNative.registerWindowProc(hWnd, new WindowProc()
			{
				public int windowProc(int hwnd, int msg, int wparam, int lparam)
				{
					if(msg == WM.WM_DEVICECHANGE.getValue())
					{
						System.err.println("Device changed !!!");
						switch (wparam)
						{
							case DBT_DEVICEARRIVAL:
								Pointer pointer = new Pointer(new NativeMemoryBlock(lparam, DEV_BROADCAST_HDR.getSize()));
								System.err.println("Device arrived !!!");
								DEV_BROADCAST_HDR hdr = new DEV_BROADCAST_HDR(pointer);
								System.err.println("dbch_devicetype : " + hdr.getDbch_devicetype());
								switch (hdr.getDbch_devicetype().getValue())
								{
									case DEV_BROADCAST_HDR.DBT_DEVTYP_HANDLE:
										pointer = new Pointer(new NativeMemoryBlock(lparam, DEV_BROADCAST_HANDLE.getSize()));
										DEV_BROADCAST_HANDLE handle = new DEV_BROADCAST_HANDLE(pointer);
										System.err.println("Char = " + handle.getDbch_data());
										break;
									case DEV_BROADCAST_HDR.DBT_DEVTYP_VOLUME:
										pointer = new Pointer(new NativeMemoryBlock(lparam, DEV_BROADCAST_HANDLE.getSize()));
										DEV_BROADCAST_VOLUME volume = new DEV_BROADCAST_VOLUME(pointer);
										for(String s : volume.getVolumes())
										{
											System.err.println("Unit = " + s + ":");
										}
										break;
										
									default:
										break;
								}
								break;
							case DBT_DEVICEREMOVECOMPLETE:
								System.err.println("Device removed !!!");
								break;
								
							default:
								System.err.println("Event : " + wparam);
								break;
						}
					}
					if(hwnd == hWnd)
					{
						System.err.println("Window proc : " + msg + "-" +wparam + "-" +lparam);
					}
					else
					{
						System.err.println("other window " + hWnd);
					}
					try
					{
						return User32.DefWindowProc(
								hwnd,
								msg,
								wparam,
								lparam);
					}
					catch (NativeException e)
					{
						e.printStackTrace();
					}
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
					return 1;
				}
			});
			final HWND hwnd = new HWND(hWnd);
			
			System.err.println(User32.ShowWindow(hwnd, WindowsConstants.SW_SHOW));
			System.err.println(User32.UpdateWindow(hwnd));
			System.err.println("--------------------------------------");
			t.run();
			Thread.sleep(60000);
			System.err.println("--------------------------------------");
			User32.ShowWindow(hwnd, WindowsConstants.SW_HIDE);
		}
		else
		{
			System.err.println("Unable to create Window !");
			System.err.println("Last error : " + Kernel32.GetLastError());
		}
//		HANDLE handle = User32.RegisterDeviceNotification(new HANDLE(hWnd), User32.DEVICE_NOTIFY_WINDOW_HANDLE);
		System.exit(0);
	}
	
}
