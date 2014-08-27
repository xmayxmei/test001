/*
 * WindowsUtils.java
 *
 * Created on 26. Mai 2007, 17:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.util.windows;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.misc.BITMAP;
import org.xvolks.jnative.misc.BITMAPINFOHEADER;
import org.xvolks.jnative.misc.ICONINFO;
import org.xvolks.jnative.misc.PROCESSENTRY32;
import org.xvolks.jnative.misc.SHELLEXECUTEINFO;
import org.xvolks.jnative.misc.TOKEN_PRIVILEGES;
import org.xvolks.jnative.misc.basicStructures.DC;
import org.xvolks.jnative.misc.basicStructures.DWORD;
import org.xvolks.jnative.misc.basicStructures.HANDLE;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.misc.basicStructures.LPARAM;
import org.xvolks.jnative.misc.basicStructures.LRECT;
import org.xvolks.jnative.misc.basicStructures.UINT;
import org.xvolks.jnative.misc.basicStructures.WPARAM;
import org.xvolks.jnative.pointers.NullPointer;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.util.Advapi32;
import org.xvolks.jnative.util.Callback;
import org.xvolks.jnative.util.Gdi32;
import org.xvolks.jnative.util.Kernel32;
import org.xvolks.jnative.util.PsAPI;
import org.xvolks.jnative.util.StructConverter;
import org.xvolks.jnative.util.User32;
import org.xvolks.jnative.util.constants.winuser.WM;

/**
 *
 * @author Thubby
 */
public class WindowsUtils
{
	public static final int ABOVE_NORMAL_PRIORITY_CLASS = 0x800;
	public static final int BELOW_NORMAL_PRIORITY_CLASS = 0x400;
	public static final int HIGH_PRIORITY_CLASS = 0x8;
	public static final int IDLE_PRIORITY_CLASS = 0x4;
	public static final int NORMAL_PRIORITY_CLASS = 0x2;
	public static final int REALTIME_PRIORITY_CLASS = 0x10;
	
	/** Creates a new instance of WindowsUtils */
	public WindowsUtils()
	{
	}
	
	// converts native Bitmap bytes to a java int-array (used by iconHandleToImageIcon())
	public static int[] nativeImageToJavaImage(Pointer bitmap, int width, int height) throws NativeException
	{
		
		// Note: dont use bitmap.getAsInt(i*4)! This is very slow as everytime you call getAsInt()
		// it seems to fetch the complete native buffer which takes a lot of time.
		// So better grab the whole native memory first with .getMemory() and do the processing manually.
		int[] pixels = new int[width*height];
		byte[] b_Bitmap = bitmap.getMemory();
		
		// convert the buffer "bytes" to java ints
		for(int i = 0; i<=pixels.length-4; i++)
		{
			pixels[i] = StructConverter.bytesIntoInt(b_Bitmap, i*4);
		}
		
		return pixels;
	}
	
	// Converts a native icon handle to a java compatible ImageIcon
	public static ImageIcon iconHandleToImageIcon(LONG hIcon, boolean destroyIconHandle) throws NativeException, IllegalAccessException
	{
		
		// get the handles to the icon bitmaps
		final ICONINFO info = new ICONINFO();
		if(!User32.GetIconInfo(hIcon, info))
		{
			throw new RuntimeException("User32.GetIconInfo: Could not retrieve IconInfo!");
		}
		// destroy icon
		if(destroyIconHandle)
			User32.DestroyIcon(hIcon);
		
		// get information about size of the icon
		// also contains a conveniance method for getting a pointer to buffer
		// that is large enough to copy the bitmap-bytes into...
		final BITMAP hBitmap = new BITMAP();
		Gdi32.GetObject(info.getBitmapColor(), hBitmap.getSizeOf(), hBitmap);
		
		// load the icon bitmap into buffer
		final Pointer bitmap = hBitmap.createBitmapBuffer();
		Gdi32.GetBitmapBits(info.getBitmapColor(), bitmap.getSize(), bitmap);
		
		/*
		// Note: dont use bitmap.getAsInt(i*4)! This is very slow as everytime you call getAsInt()
		// it seems to fetch the complete native buffer which takes a lot of time.
		// So better grab the whole native memory first with .getMemory() and do the processing manually.
		int[] pixels = new int[hBitmap.getRealBitmapSize()];
		byte[] b_Bitmap = bitmap.getMemory();
		 
		// convert the buffer "bytes" to java ints
		for(int i = 0; i<=pixels.length-4; i++) {
			pixels[i] = StructConverter.bytesIntoInt(b_Bitmap, i*4);
		}
		 */
		
		int[] pixels = nativeImageToJavaImage(bitmap, hBitmap.getWidth(), hBitmap.getHeight());
		
		// now create a new image from our converted icon-bytes
		MemoryImageSource mis = new MemoryImageSource(hBitmap.getWidth(), hBitmap.getHeight(), pixels, 0, hBitmap.getWidth());
		final Image _image = Toolkit.getDefaultToolkit().createImage(mis);
		mis = null;
		pixels = null;
		info.dispose();
		bitmap.dispose();
		hBitmap.getPointer().dispose();
		
		return new ImageIcon(_image);
	}
	
	// takes a screenshot of a given HWND and writes it to disk. It may be chosen to use native or java I/O
	public static boolean takeScreenShotAndWriteToDisk(HWND hwnd, String path, boolean useNative) throws InterruptedException, IllegalAccessException, NativeException, FileNotFoundException, IOException
	{
		if(hwnd == null || hwnd.getValue() == 0)
		{
			JNative.getLogger().log(SEVERITY.ERROR, "HWND is null!");
			return false;
		}
		
		//JNative.getLogger().log(hwnd);
		
		// set the target window to foreground
		HWND foregroundHWND = User32.GetForegroundWindow();
		SetForegroundWindowEx(hwnd);
		
		Pointer bfheader = null;
		Pointer bitmap = null;
		DC hdcScreen = new DC(0);
		DC hdcTemp = new DC(0);
		
		int hbmScreen = 0;
		int hbmOld = 0;
		
		try
		{
			while(User32.GetForegroundWindow().getValue().intValue() != hwnd.getValue().intValue())
			{
				// sleep for a moment until the target window is in foreground
				Thread.sleep(10L);
			}
			
			LRECT screenDimension = new LRECT();
			if(!User32.GetWindowRect(hwnd, screenDimension))
			{
				JNative.getLogger().log(SEVERITY.ERROR, "Could not get screenDimension of HWND: "+hwnd);
				return false;
			}
			
			int width = screenDimension.getWidth();
			int height = screenDimension.getHeight();
			
			hdcScreen = User32.GetWindowDC(hwnd);
			hdcTemp = Gdi32.CreateCompatibleDC(hdcScreen);
			
			hbmScreen = Gdi32.CreateCompatibleBitmap(hdcScreen, width, height);
			hbmOld = Gdi32.SelectObject(hdcTemp, hbmScreen);
			
			if(!Gdi32.BitBlt( hdcTemp, 0, 0, width, height, hdcScreen, 0, 0, Gdi32.SRCCOPY ))
			{
				JNative.getLogger().log(SEVERITY.ERROR, "BitBlt was not successful!");
				return false;
			}
			
			BITMAPINFOHEADER infobmp = new BITMAPINFOHEADER();
			infobmp.getPointer().zeroMemory();
			infobmp.getPointer().setIntAt(0,infobmp.getSizeOf());
			infobmp.getPointer().setIntAt(4,width);
			infobmp.getPointer().setIntAt(8,height);
			infobmp.getPointer().setShortAt(12,(short)1);
			infobmp.getPointer().setShortAt(14,(short)24);
			infobmp.getPointer().setIntAt(16,0);
			infobmp.getPointer().setIntAt(20,0);
			infobmp.getPointer().setIntAt(24,0);
			infobmp.getPointer().setIntAt(28,0);
			infobmp.getPointer().setIntAt(32,0);
			infobmp.getPointer().setIntAt(36,0);
			
			bitmap = new Pointer(MemoryBlockFactory.createMemoryBlock(width*height*3));
			
			if(Gdi32.GetDIBits(hdcTemp, hbmScreen, 0, height, bitmap, infobmp, Gdi32.DIB_RGB_COLORS) == 0)
			{
				JNative.getLogger().log(SEVERITY.ERROR, "GetDIBits was not successful!");
				return false;
			}
			
			bfheader = new Pointer(MemoryBlockFactory.createMemoryBlock(14));
			bfheader.setShortAt(0,(short)19778);
			bfheader.setIntAt(2, bfheader.getSize() + bitmap.getSize() + infobmp.getSizeOf());
			bfheader.setShortAt(6,(short)0);
			bfheader.setShortAt(8,(short)0);
			bfheader.setIntAt(10, bfheader.getSize() + infobmp.getSizeOf());
			
			if(useNative)
			{
				HANDLE hfile = Kernel32.CreateFile(path,Kernel32.AccessMask.GENERIC_WRITE.getValue(),0,null,Kernel32.CreationDisposition.OPEN_ALWAYS.getValue(),0,0);
				if(hfile.getValue() != 0)
				{
					try
					{
						DWORD bytesWritten = new DWORD(0);
						Kernel32.WriteFile(hfile, bfheader, bfheader.getSize(), bytesWritten, NullPointer.NULL);
						Kernel32.WriteFile(hfile, infobmp.getPointer(), infobmp.getPointer().getSize(), bytesWritten, NullPointer.NULL);
						Kernel32.WriteFile(hfile, bitmap, bitmap.getSize(), bytesWritten, NullPointer.NULL);
					}
					finally
					{
						Kernel32.CloseHandle(hfile);
					}
				}
				else
					JNative.getLogger().log(SEVERITY.ERROR, "CreateFile was not successful!");
			}
			else
			{
				RandomAccessFile ra = new RandomAccessFile(path,"rw");
				try
				{
					ra.writeShort(StructConverter.swapShort((short)19778));
					ra.writeInt(StructConverter.swapInt(bfheader.getSize() + bitmap.getSize() + infobmp.getSizeOf()));
					
					ra.writeShort(StructConverter.swapShort((short)0));
					ra.writeShort(StructConverter.swapShort((short)0));
					ra.writeInt(StructConverter.swapInt(bfheader.getSize() + infobmp.getSizeOf()));
					
					ra.writeInt(StructConverter.swapInt(infobmp.getPointer().getAsInt(0)));
					ra.writeInt(StructConverter.swapInt(infobmp.getPointer().getAsInt(4)));
					ra.writeInt(StructConverter.swapInt(infobmp.getPointer().getAsInt(8)));
					ra.writeShort(StructConverter.swapShort(infobmp.getPointer().getAsShort(12)));
					ra.writeShort(StructConverter.swapShort(infobmp.getPointer().getAsShort(14)));
					ra.writeInt(StructConverter.swapInt(infobmp.getPointer().getAsInt(16)));
					ra.writeInt(StructConverter.swapInt(infobmp.getPointer().getAsInt(20)));
					ra.writeInt(StructConverter.swapInt(infobmp.getPointer().getAsInt(24)));
					ra.writeInt(StructConverter.swapInt(infobmp.getPointer().getAsInt(28)));
					ra.writeInt(StructConverter.swapInt(infobmp.getPointer().getAsInt(32)));
					ra.writeInt(StructConverter.swapInt(infobmp.getPointer().getAsInt(36)));
					
					ra.write(bitmap.getMemory());
				}
				finally
				{
					if(ra != null)
						ra.close();
				}
			}
		}
		finally
		{
			Gdi32.SelectObject( hdcTemp, hbmOld );
			Gdi32.DeleteObject( hbmScreen );
			Gdi32.DeleteDC( hdcTemp );
			User32.ReleaseDC( hwnd, hdcScreen );
			if(bitmap != null)
				bitmap.dispose();
			if(bfheader != null)
				bfheader.dispose();
			
			if(foregroundHWND.getValue() != 0 && foregroundHWND.getValue() != hwnd.getValue())
			{
				// set the old window to foreground
				SetForegroundWindowEx(foregroundHWND);
			}
		}
		return true;
	}
	
	// sets a given hwnd always on top
	public static boolean setAlwaysOnTop(HWND hWnd, boolean b)
	{
		try
		{
			if(b)
			{
				return User32.SetWindowPos(hWnd, User32.HWND_TOPMOST, 0, 0, 0, 0, User32.SWP_NOMOVE | User32.SWP_NOSIZE);
			}
			return User32.SetWindowPos(hWnd, User32.HWND_NOTOPMOST, 0, 0, 0, 0, User32.SWP_NOACTIVATE | User32.SWP_NOMOVE | User32.SWP_NOSIZE);
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
		return false;
	}
	
	// extended version of SetForeGroundWindow(HWND hwnd)
	public static boolean SetForegroundWindowEx(HWND hWnd) throws NativeException, IllegalAccessException
	{
		User32.SwitchToThisWindow(hWnd, true);
		setAlwaysOnTop(hWnd, true);
		
		try
		{
			int lThreadWindow = User32.GetWindowThreadProcessId(hWnd);
			int lThreadForeWin = User32.GetWindowThreadProcessId(User32.GetForegroundWindow());
			if(lThreadWindow == lThreadForeWin)
			{
				return User32.SetForegroundWindow(hWnd);
			}
			else
			{
				User32.AttachThreadInput(lThreadForeWin, lThreadWindow, true);
				boolean b = User32.SetForegroundWindow(hWnd);
				User32.AttachThreadInput(lThreadForeWin, lThreadWindow, false);
				return b;
			}
		}
		finally
		{
			setAlwaysOnTop(hWnd, false);
		}
	}
	
	 /*
	 if the ImageIcon is not readable by PixelGrabber you need to make use of external libs to generate a valid ImageIcon
	 I.e. *.ico images cannot be grabbed. Use i.e. aclibico to generate a valid ImageIcon:
	 try {
		if(Class.forName("com.ctreber.aclib.image.ico.ICOFile") != null) {
			final ICOFile lICOFile = new ICOFile(path);
			final Iterator lDescIt = lICOFile.getDescriptors().iterator();
			while (lDescIt.hasNext()) {
				final BitmapDescriptor lDescriptor = (BitmapDescriptor) lDescIt.next();
				final Image img = lDescriptor.getImageRGB();
				if (img != null) {
					return new ImageIcon(img);
				}
			}
		}
	} catch(Exception e) {
		e.printStackTrace();
	}
	  **/
	public static int[] grabImage(ImageIcon icon) throws InterruptedException
	{
		
		int w = icon.getIconWidth();
		int h = icon.getIconHeight();
		int[] pixels = new int[w * h];
		
		// Collect pixel data in array
		PixelGrabber pg = new PixelGrabber(icon.getImage(), 0, 0, w, h, pixels, 0, w);
		pg.grabPixels();
		if ((pg.getStatus() & ImageObserver.ABORT) != 0)
		{
			return null;
		}
		return pixels;
	}
	// Converts a java images to a native image handle
	public static LONG CreateIcon(LONG hInstance, int cPlanes, int cBitsPixel, ImageIcon icon) throws InterruptedException, NativeException, IllegalAccessException
	{
		if(icon == null)
		{
			return new LONG(0);
		}
		int[] pixels = grabImage(icon);
		if(pixels == null)
		{
			return new LONG(0);
		}
		return User32.CreateIcon(hInstance, icon.getIconWidth(), icon.getIconHeight(), cPlanes, cBitsPixel, pixels, pixels);
	}
	
	public static boolean setSeDebugPrivilege() throws NativeException, IllegalAccessException
	{
		
		TOKEN_PRIVILEGES tkp = new TOKEN_PRIVILEGES();
		
		HANDLE hdlProcessHandle = Kernel32.GetCurrentProcess();
		HANDLE hdlTokenHandle = new HANDLE(0);
		
		if(!Advapi32.OpenProcessToken(hdlProcessHandle, (TOKEN_PRIVILEGES.TOKEN_ADJUST_PRIVILEGES | TOKEN_PRIVILEGES.TOKEN_QUERY), hdlTokenHandle))
		{
			return false;
		}
		
		try
		{
			LONG tmpLuid = new LONG(0);
			if(!Advapi32.LookupPrivilegeValue("", "SeDebugPrivilege", tmpLuid))
			{
				return false;
			}
			tkp.PrivilegeCount = 1;
			tkp.TheLuid = tmpLuid.getValue();
			tkp.Attributes = TOKEN_PRIVILEGES.SE_PRIVILEGE_ENABLED;
			
			return Advapi32.AdjustTokenPrivileges(hdlTokenHandle, false, tkp);
		}
		finally
		{
			Kernel32.CloseHandle(hdlTokenHandle);
		}
	}
	
	// returns a Map where the key is the proccessId and the value is the process' executable name
	// if you really want to get access to ALL existing processes set inclucdeSystemProcesses "true"
	// if you set inclucdeSystemProcesses "false" it will only return processes created by the user
	// if you set inclucdeSystemProcesses "false" AND call setSeDebugPrivilege() before then almost all existing processes are returned
	public static Map<DWORD, String> enumerateProcesses(boolean inclucdeSystemProcesses) throws NativeException, IllegalAccessException
	{
		Map<DWORD, String> processes = new HashMap<DWORD, String>();
		
		if(inclucdeSystemProcesses)
		{
			PROCESSENTRY32 Pc = new PROCESSENTRY32();
			HANDLE hSnapshot = Kernel32.CreateToolhelp32Snapshot(Kernel32.TH32CS_SNAPALL, 0);
			if(hSnapshot.getValue() != 0)
			{
				if(Kernel32.Process32First(hSnapshot, Pc))
				{
					do
					{
						if(Pc.getValueFromPointer().th32ProcessID.getValue() != 0)
						{
							processes.put(Pc.th32ProcessID, Pc.szExeFile);
						}
						Pc.resetPointer();
					}
					while(Kernel32.Process32Next(hSnapshot, Pc));
				}
			}
			Kernel32.CloseHandle(hSnapshot);
		}
		else
		{
			int[] pids = PsAPI.EnumProcess(1024);
			
			HANDLE handle = null;
			for (int i = 0; i < pids.length; i++)
			{
				handle = Kernel32.OpenProcess(Kernel32.PROCESS_ALL_ACCESS, false, pids[i]);
				if (handle.getValue() != 0)
				{
					Pointer hModuleEtoile = PsAPI.EnumProcessModules(handle, 1024);
					
					if(hModuleEtoile.equals(NullPointer.NULL))
					{
						continue;
					}
					
					final String name = PsAPI.GetModuleBaseName(handle, hModuleEtoile.getAsInt(0), 1024);
					DWORD dwProcessId = getProcessId(name);
					if(dwProcessId.getValue() != 0)
					{
						processes.put(dwProcessId, name);
					}
					hModuleEtoile.dispose();
				}
				Kernel32.CloseHandle(handle);
			}
			
		}
		return processes;
	}
	
	// you might need to call setSeDebugPrivilege() to access "more secure" processes
	public static boolean setProcessPriority(String processName, int priority) throws NativeException, IllegalAccessException
	{
		if(processName == null)
		{
			return false;
		}
		
		boolean ret = false;
		DWORD dwProcessId = getProcessId(processName);
		if(dwProcessId.getValue() != 0)
		{
			HANDLE hProc = Kernel32.OpenProcess(Kernel32.PROCESS_ALL_ACCESS, false, dwProcessId.getValue());
			if(hProc.getValue() != 0)
			{
				ret = Kernel32.SetPriorityClass(hProc, new DWORD(priority));
			}
			Kernel32.CloseHandle(hProc);
		}
		
		return ret;
	}
	
	// you might need to call setSeDebugPrivilege() to access "more secure" processes
	public static final DWORD getProcessId(String szExeName) throws NativeException, IllegalAccessException
	{
		/*
		if(!setSeDebugPrivilege())
		{
			throw new NativeException("Error setting SeDebugPrivilege!");
		}
		 */
		if(szExeName != null)
		{
			PROCESSENTRY32 Pc = new PROCESSENTRY32();
			
			HANDLE hSnapshot = Kernel32.CreateToolhelp32Snapshot(Kernel32.TH32CS_SNAPALL, 0);
			if(hSnapshot.getValue() != 0)
			{
				if(Kernel32.Process32First(hSnapshot, Pc))
				{
					do
					{
						if(Pc.getValueFromPointer().szExeFile.equalsIgnoreCase(szExeName))
						{
							return Pc.th32ProcessID;
						}
						Pc.resetPointer();
					}
					while(Kernel32.Process32Next(hSnapshot, Pc));
				}
			}
			Kernel32.CloseHandle(hSnapshot);
		}
		return new DWORD(0);
	}
	// you might need to call setSeDebugPrivilege() to access "more secure" processes
	public static boolean attachDllToProcess(String szModuleName, String szProcessName) throws NativeException, IllegalAccessException
	{
		DWORD dwProcessId = getProcessId(szProcessName);
		JNative.getLogger().log("getProcessId: "+dwProcessId.getValue());
		if(dwProcessId.getValue() != 0)
		{
			DWORD ExitCode = new DWORD(0);
			HANDLE hProc = Kernel32.OpenProcess(Kernel32.PROCESS_ALL_ACCESS, false, dwProcessId.getValue());
			JNative.getLogger().log("OpenProcess: "+hProc.getValue());
			try
			{
				if(hProc.getValue() != 0)
				{
					int AllocSpace = Kernel32.VirtualAllocEx(hProc, 0, szModuleName.length(), new DWORD(Kernel32.MEM_COMMIT), new DWORD(Kernel32.PAGE_READWRITE));
					if(Kernel32.WriteProcessMemory(hProc, AllocSpace, Pointer.createPointerFromString(szModuleName), szModuleName.length()))
					{
						HANDLE thread = Kernel32.CreateRemoteThread(hProc, null, 0, Kernel32.GetProcAddress(new HANDLE(Kernel32.GetModuleHandle("Kernel32")), "LoadLibraryA"), AllocSpace, new DWORD(0), new DWORD(0));
						JNative.getLogger().log("CreateRemoteThread: "+thread.getValue());
						try
						{
							if(thread.getValue() != 0)
							{
								JNative.getLogger().log("WaitForSingleObjectEx:" +Kernel32.WaitForSingleObjectEx(thread, new DWORD(SHELLEXECUTEINFO.INFINITE), false));
								JNative.getLogger().log("GetExitCodeThread: "+Kernel32.GetExitCodeThread(thread, ExitCode));
								return true;
							}
						}
						finally
						{
							Kernel32.VirtualFreeEx(hProc, AllocSpace, 0, new DWORD(Kernel32.MEM_RELEASE));
							Kernel32.CloseHandle(thread);
						}
					}
				}
			}
			finally
			{
				Kernel32.CloseHandle(hProc);
			}
		}
		return false;
	}
	public static boolean enumerateWindows(final List<HWND> list, final boolean onlyVisible) throws NativeException, IllegalAccessException
	{
		Callback c = null;
		try
		{
			c = new Callback()
			{
				private int myAddress = -1;
				public int callback(final long values[])
				{
					if(values.length == 2)
					{
						try
						{
							if(values[0] > 0L)
							{
								final HWND hwnd = new HWND(Long.valueOf(values[0]).intValue());
								if(onlyVisible)
								{
									if(User32.IsWindowVisible(hwnd))
									{
										list.add(hwnd);
									}
								}
								else
								{
									list.add(hwnd);
								}
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						return 1;
					}
					return 0;
				}
				public int getCallbackAddress() throws NativeException
				{
					if(myAddress == -1)
					{
						myAddress = JNative.createCallback(2,this);
					}
					return myAddress;
				}
			};
			return User32.EnumWindows(c, 0);
		}
		finally
		{
			JNative.releaseCallback(c);
		}
	}
	public static boolean enumerateChildWindows(HWND parent, final List<HWND> list) throws NativeException, IllegalAccessException
	{
		Callback c = null;
		try
		{
			c = new Callback()
			{
				private int myAddress = -1;
				public int callback(final long values[])
				{
					if(values.length == 2)
					{
						try
						{
							if(values[0] > 0L)
							{
								list.add(new HWND(Long.valueOf(values[0]).intValue()));
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						return 1;
					}
					return 0;
				}
				public int getCallbackAddress() throws NativeException
				{
					if(myAddress == -1)
					{
						myAddress = JNative.createCallback(2,this);
					}
					return myAddress;
				}
			};
			return User32.EnumChildWindows(parent, c, 0);
		}
		finally
		{
			JNative.releaseCallback(c);
		}
	}
	public static boolean terminateWin32App(HWND hwnd, DWORD dwTimeout) throws NativeException, IllegalAccessException
	{
		if(hwnd != null)
		{
			return terminateWin32App(new DWORD(User32.GetWindowThreadProcessId(hwnd)), dwTimeout);
		}
		return false;
	}
	
	public static boolean terminateWin32App(DWORD dwPID, DWORD dwTimeout) throws NativeException, IllegalAccessException
	{
		// If we can't open the process with PROCESS_TERMINATE rights,
	    // then we give up immediately.
		HANDLE hProc = Kernel32.OpenProcess(Kernel32.SYNCHRONIZE | Kernel32.PROCESS_TERMINATE, false, dwPID.getValue());		
		if(hProc.getValue() == 0)
		{
			return false;
		}
		
		Callback c = null;
		try
		{
			c = new Callback()
			{
				private int myAddress = -1;
				private HWND hwnd = null;
				private int dwID = 0;
				public int callback(final long values[])
				{
					if(values.length == 2)
					{
						try
						{
							if(values[0] > 0L)
							{
								hwnd = new HWND((int)values[0]);
								dwID = User32.GetWindowThreadProcessId(hwnd);
								if(dwID == (int) values[1])
								{
									User32.PostMessage(hwnd, new UINT(WM.WM_CLOSE.getValue()), new WPARAM(0), new LPARAM(0));
								}
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						return 1;
					}
					return 0;
				}
				public int getCallbackAddress() throws NativeException
				{
					if(myAddress == -1)
					{
						myAddress = JNative.createCallback(2,this);
					}
					return myAddress;
				}
			};
			// Callback posts WM_CLOSE to all windows whose PID matches the process's.
			User32.EnumWindows(c, dwPID.getValue());
			
			// Wait on the handle. If it signals, great. If it times out, then you kill it.
			if(Kernel32.WaitForSingleObjectEx(hProc, dwTimeout, false) != 0x0)
			{
				JNative.getLogger().log("Could not cleanly shutdown process with PID "+dwPID+", forcing termination.");
				return Kernel32.TerminateProcess(hProc,0);
			}
			else
			{
				return true;
			}			
		}
		finally
		{
			Kernel32.CloseHandle(hProc);
			JNative.releaseCallback(c);
		}
	}
}
