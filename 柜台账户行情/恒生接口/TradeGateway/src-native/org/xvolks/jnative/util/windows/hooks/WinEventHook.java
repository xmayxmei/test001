/*
 * WinEventHook.java
 *
 * Created on 22. September 2008, 10:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.util.windows.hooks;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.MSG;
import org.xvolks.jnative.misc.basicStructures.DWORD;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.misc.basicStructures.LPARAM;
import org.xvolks.jnative.misc.basicStructures.UINT;
import org.xvolks.jnative.misc.basicStructures.WPARAM;
import org.xvolks.jnative.util.Callback;
import org.xvolks.jnative.util.Kernel32;
import org.xvolks.jnative.util.User32;
import org.xvolks.jnative.util.constants.winuser.WM;

/**
 *
 * @author Administrator
 */
public class WinEventHook implements Runnable, Callback
{
	private int startEvent;
	private int endEvent;
    private int myAddress = -1;
    private int hHook;
    private boolean doHook = false;
    private DWORD dwThreadId;
    private int callbackAddress;
    private DWORD dwProcessId;
    private int hModule;
	private UINT flags;
    private Thread thread;
    private volatile Object syncObj = new Object();
	private DWORD hookThreadId;

	// uses this instance as callbackAddress
    public WinEventHook()
    {
        this(0, null, 0);
    }
    // you can either pass Callback.getCallbackAddress() or the native callback address
    public WinEventHook(int callbackAddress)
    {
        this(callbackAddress, null, 0);
    }
    // you can additionally pass processId to hook in
    public WinEventHook(int callbackAddress, DWORD dwProcessId)
    {
        this(callbackAddress, dwProcessId, 0);
    }
    // you can additionally pass hModule which is a handle to a dll that contains the native callback function
    public WinEventHook(int callbackAddress, DWORD dwProcessId, int hModule)
    {
        this(callbackAddress, dwProcessId, hModule, new DWORD(0));
    }
	public WinEventHook(int callbackAddress, DWORD dwProcessId, int hModule, DWORD dwThreadId)
	{
		this(callbackAddress, dwProcessId, hModule, dwThreadId, new UINT(0));
	}
    public WinEventHook(int callbackAddress, DWORD dwProcessId, int hModule, DWORD dwThreadId, UINT flags)
    {
        this.dwProcessId = dwProcessId;
        this.hModule = hModule;
        this.callbackAddress = callbackAddress;
		this.dwThreadId = dwThreadId;
		this.flags = flags;
    }

	public int getHookHandle()
    {
        return hHook;
    }
    public void setHModule(int hModule)
    {
        this.hModule = hModule;
    }
    public void setDwProcessId(DWORD dwProcessId)
    {
        this.dwProcessId = dwProcessId;
    }
	public void setDwThreadId(DWORD dwThreadId)
    {
        this.dwThreadId = dwThreadId;
    }
    public void setCallback(int callbackAddr) throws NativeException, IllegalAccessException, InterruptedException
    {
        this.callbackAddress = callbackAddr;
    }
	public void setFlags(UINT flags)
    {
        this.flags = flags;
    }
    public boolean isHooked()
    {
        return doHook;
    }

    // returns true if the hook was successfully established
    public synchronized boolean hook(int startEvent, int endEvent) throws NativeException, IllegalAccessException, InterruptedException
    {
        // check Callback address
        if(callbackAddress == 0)
        {
            throw new IllegalArgumentException("Callback address must not be null!");
        }
        // check dwProcessId
        if(dwProcessId == null)
        {
            dwProcessId = new DWORD(0);
        }
		// check dwThreadId
        if(dwThreadId == null)
        {
            dwThreadId = new DWORD(0);
        }
		// check flags
        if(flags == null)
        {
            flags = new UINT(0);
        }

        if(isHooked())
        {
            unHook();
        }

        this.startEvent = startEvent;
		this.endEvent = endEvent;

        doHook = true;
        thread = new Thread(this);
        thread.start();

        synchronized(syncObj)
        {
            syncObj.wait();
        }
        return isHooked();
    }
    public synchronized boolean unHook() throws NativeException, IllegalAccessException, InterruptedException
    {
        if(!isHooked())
        {
            return false;
        }

        // post the quit-message
        if(hookThreadId != null)
        {
            User32.PostThreadMessage(hookThreadId, new UINT(WM.WM_QUIT.getValue()), new WPARAM(0), new LPARAM(0));
        }

        // wait till the thread died
        if(thread != null && thread.isAlive())
        {
            thread.join();
        }

        doHook = false;
        return true;
    }
    // do not call this manually!!
    public void run()
    {
        if(!isHooked())
        {
            throw new RuntimeException("Do not call run() manually, use hook() method instead!");
        }

        try
        {
			// establish the hook
            hHook = User32.SetWinEventHook(new UINT(startEvent), new UINT(endEvent), hModule, callbackAddress, dwProcessId, dwThreadId, flags);

			// hook was successfully established
            if(hHook != 0)
            {
                JNative.getLogger().log("Hook established: hHook = "+hHook+", callbackAddress = "+callbackAddress+", hModule = "+hModule+", dwProcessId = "+dwProcessId.getValue());

				// get the current threadId for later reference in unHook()
                hookThreadId = Kernel32.GetCurrentThreadId();

                synchronized(syncObj)
                {
                    syncObj.notify();
                }

                MSG msg = new MSG();
                HWND hwnd = new HWND(0);
                // start native message loop
                while(User32.GetMessage(msg, hwnd, 0, 0) > 0)
                {
                    User32.TranslateMessage(msg);
                    User32.DispatchMessage(msg);
                    msg.getPointer().zeroMemory();
                }
            }
            else
            {
                JNative.getLogger().log("Could not establish Hook!");
            }
        }
        catch (IllegalAccessException ex)
        {
            throw new RuntimeException(ex);
        }
        catch (NativeException ex)
        {
            throw new RuntimeException(ex);
        }
        finally
        {
            doHook = false;
            if(hHook != 0)
            {
                try
                {
                    // unhook the hook
                    User32.UnhookWinEvent(hHook);
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
            synchronized(syncObj)
            {
                syncObj.notify();
            }
        }
    }
    // you can either set your own callbackAddress or re-use the existing one by extending this class
    public int callback(long values[])
    {
        return 0;
    }
    public int getCallbackAddress() throws NativeException
    {
        if(myAddress == -1)
        {
            myAddress = JNative.createCallback(7,this);
        }
        return myAddress;
    }
}
