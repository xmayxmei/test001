/*
 * HotkeyCallback.java
 *
 * Created on 16. Mai 2007, 10:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.util.windows.hotkey;

import java.awt.Window;
import java.util.Random;
import java.util.Vector;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.misc.MSG;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.misc.basicStructures.LPARAM;
import org.xvolks.jnative.misc.basicStructures.UINT;
import org.xvolks.jnative.misc.basicStructures.WPARAM;
import org.xvolks.jnative.util.Callback;
import org.xvolks.jnative.util.Kernel32;
import org.xvolks.jnative.util.User32;
import org.xvolks.jnative.util.constants.winuser.WM;
import org.xvolks.jnative.util.constants.winuser.WindowsConstants;

/**
 *
 * @author thubby
 *
 *
 * EXAMPLE USAGE 1:
 *
 - Extend the Hotkey-class and overwrite "public int callback(long[] values)"
 - Don't forget to return super.callback(values) in case your HOTKEY was not called, else return 0 (see example 2).
 *
 final MyHotkey hotkey = new MyHotkey(Hotkey.MOD_CONTROL + Hotkey.MOD_ALT, 0x77);
 // register your hotkey
 h.registerHotkey();
 
 // somewhere else unregister the hotkey
 h.unregisterHotkey();
 
 If you do not overwrite "public int callback(long[] values)" nothing will happen...
 */

/*
 * EXAMPLE USAGE 2:
 */
/*
// First create a hotkey
// Every hotkey needs a new instance of "Hotkey",
// you cannot register more than one hotkey to a "Hotkey"-Instance.
final Hotkey hotkey = new Hotkey(Hotkey.MOD_CONTROL + Hotkey.MOD_ALT, 0x77);
 
// Then create your callback. You can get all needed values from the previously defined Hotkey instance!
  Callback c = new Callback() {
	private int myAddress = -1;
 
	// values[1] == message  (WM_HOTKEY)
	// values[2] == wParam   (iAtom)
	public int callback(long[] values) {
 
		final int message = (int)values[1];
		final int wParam = (int)values[2];
		final int lParam = (int)values[3];
 
		// check if the message is WM_HOTKEY and if our iAtom is called!
		// if both is true don't forget to return 0 so that other registered hotkeys don't receive the event!
		if(message == WM.WM_HOTKEY.getValue() && wParam == hotkey.getAtom()) {
			JNative.getLogger().log("h: "+wParam);
 
			// do whatever you need here!
 
			return 0;
		}
 
		try {
			return User32.CallWindowProc(new LONG(hotkey.getPrevWndProc()),new HWND((int)values[0]), new UINT((int)values[1]), new WPARAM((int)values[2]), new LPARAM((int)values[3])).getValue();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	public int getCallbackAddress() throws NativeException {
		if(myAddress == -1) {
			myAddress = JNative.createCallback(4, this);
		}
		return myAddress;
	}
 };
 // now apply the callback to the hotkey
 h.setCallback(c);
 
 // register your hotkey
 h.registerHotkey();
 
 */

public class Hotkey implements Callback, Runnable
{
	
	public static final int MOD_ALT = 0x1;      //Alt
	public static final int MOD_SHIFT = 0x4;    //Shift
	public static final int MOD_CONTROL = 0x2;	//CTRL
	public static final int MOD_WIN = 0x8;       //Windows
	
	private HWND messageWindow = null;
	private static final Vector<Hotkey> hotkeys = new Vector<Hotkey>();
	
	private int myAddress = -1;
	private int iAtom = 0;
	private int prevWndProc = 0;
	private int modifiers = -1;
	private int key = -1;
	private Callback callback = null;
	private boolean isRegistered = false;
	private Thread thread = null;
	private boolean loopStarted = false;
	private Window wind;
	
	static
	{
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				try
				{
					unregisterAllHotkeys();
					//JNative.unLoadAllLibraries();
				}
				catch (NativeException ex)
				{
					ex.printStackTrace();
				}
				catch (IllegalAccessException ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}
	
	@SuppressWarnings("unused")
	private Hotkey()
	{}
	
	/** Creates a new instance of HotkeyCallback */
	public Hotkey(int modifiers, int key)
	{
		this.modifiers = modifiers;
		this.key = key;
	}
	public Hotkey(int modifiers, int key, Callback c)
	{
		this(modifiers, key);
		this.callback = c;
	}
	
	public void run()
	{
		try
		{
			createNativeWindow();
			if(callback == null)
			{
				setCallback(this);
			}
			prevWndProc = User32.SetWindowLong(messageWindow, WindowsConstants.GWL_WNDPROC, new LONG(callback.getCallbackAddress()));
			registerHotkeyInternal();
			startNativeMessageLoop();
		}
		catch (IllegalAccessException ex)
		{
			ex.printStackTrace();
		}
		catch (NativeException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void stopNativeMessageLoop()
	{
		loopStarted = false;
		try
		{
			User32.PostMessage(messageWindow, new UINT(WM.WM_QUIT.getValue()), new WPARAM(iAtom), new LPARAM(0));
		}
		catch (IllegalAccessException ex)
		{
			ex.printStackTrace();
		}
		catch (NativeException ex)
		{
			ex.printStackTrace();
		}
	}
	// This MUST be the last call in your public void main()-method as it only (maybe) returns when you call stopMessageLoop() somewhere else!
	private final void startNativeMessageLoop() throws NativeException, IllegalAccessException
	{
		if(loopStarted)
		{
			return;
		}
		
		loopStarted = true;
		try
		{
			MSG Msg = new MSG();
			while(User32.GetMessage(Msg, messageWindow, 0, 0) > 0)
			{
				User32.TranslateMessage(Msg);
				User32.DispatchMessage(Msg);
				Msg.getPointer().zeroMemory();
			}
		}
		finally
		{
			loopStarted = false;
		}
	}
	
	public static Vector<Hotkey> getInstalledHotkeys()
	{
		return hotkeys;
	}
	public static final void unregisterAllHotkeys() throws NativeException, IllegalAccessException
	{
		for(int i = hotkeys.size()-1; i>=0; i--)
		{
			final Hotkey k = hotkeys.get(i);
			JNative.getLogger().log(SEVERITY.INFO, "Trying to unregister hotkey: "+k.getKey());
			if(k.isRegistered())
			{
				k.unregisterHotkey();
			}
		}
	}
	public int getModifiers()
	{
		return modifiers;
	}
	public int getKey()
	{
		return key;
	}
	public int getPrevWndProc()
	{
		return prevWndProc;
	}
	public int getAtom()
	{
		return iAtom;
	}
	public boolean isRegistered()
	{
		return isRegistered;
	}
	public HWND getNativeHWND()
	{
		return messageWindow;
	}
	// this native window is needed for processing the native window events
	private void createNativeWindow() throws NativeException, IllegalAccessException
	{
		messageWindow = new HWND(User32.CreateWindowEx(0, "Button", ""+new Random().nextInt(Integer.MAX_VALUE), 0, 0, 0, 0, 0, 0, 0, 0, 0));
	}
	private void destroyNativeWindow() throws NativeException, IllegalAccessException
	{
		if(messageWindow != null)
		{
			User32.DestroyWindow(messageWindow);
		}
		messageWindow = null;
	}
	public final void registerHotkey()
	{
		thread = new Thread(this);
		thread.start();
	}
	private final boolean registerHotkeyInternal() throws NativeException, IllegalAccessException
	{
		iAtom = Kernel32.GlobalAddAtom("HotKey"+new Random().nextInt(Integer.MAX_VALUE));
		if(iAtom != 0)
		{
			isRegistered = User32.RegisterHotKey(messageWindow, iAtom, modifiers, key);
			
			if(isRegistered)
			{
				//prevWndProc = User32.SetWindowLong(messageWindow, MSG.WindowsConstants.GWL_WNDPROC, new LONG(callback.getCallbackAddress()));
				//if(prevWndProc != 0) {
				hotkeys.add(this);
				JNative.getLogger().log(SEVERITY.INFO,"Hotkey: "+getKey()+" successfully registered!");
				return true;
				//}
			}
			unregisterHotkey();
		}
		return false;
	}
	public final void unregisterHotkey() throws NativeException, IllegalAccessException
	{
		try
		{
			stopNativeMessageLoop();
			if(messageWindow != null)
			{
				User32.UnregisterHotKey(messageWindow, iAtom);
				User32.SetWindowLong(messageWindow, WindowsConstants.GWL_WNDPROC, new LONG(prevWndProc));
			}
			Kernel32.GlobalDeleteAtom(iAtom);
		}
		finally
		{
			try
			{
				destroyNativeWindow();
			}
			catch (IllegalAccessException ex)
			{
				ex.printStackTrace();
			}
			catch (NativeException ex)
			{
				ex.printStackTrace();
			}
			isRegistered = false;
			iAtom = 0;
			prevWndProc = 0;
			hotkeys.remove(this);
			JNative.getLogger().log(SEVERITY.INFO,"Hotkey: "+getKey()+" unregistered!");
		}
	}
	
	public void setCallback(Callback c) throws NativeException, IllegalAccessException
	{
		if(c == null)
		{
			JNative.getLogger().log("Callback is null!");
			return;
		}
		// unregister old callback if existing
		if(callback != null)
		{
			if(messageWindow != null)
			{
				User32.SetWindowLong(messageWindow, WindowsConstants.GWL_WNDPROC, new LONG(prevWndProc));
			}
			JNative.releaseCallback(callback);
		}
		
		this.callback = c;
	}
	public int callback(long[] values)
	{
		try
		{
			return User32.CallWindowProc(getPrevWndProc(),(int)values[0], (int)values[1], (int)values[2], (int)values[3]);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 1;
	}
	public int getCallbackAddress() throws NativeException
	{
		if(myAddress == -1)
		{
			myAddress = JNative.createCallback(4, this);
		}
		return myAddress;
	}
	
	public Callback getCallback()
	{
		return callback;
	}
}
