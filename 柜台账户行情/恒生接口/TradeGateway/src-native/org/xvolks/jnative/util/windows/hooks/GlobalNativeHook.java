/*
 * MouseHook.java
 *
 * Created on 24. Januar 2008, 15:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/*
 SetWindowsHookEx takes 4 parameters: 
 
1) the hook type 
2) callback 
3) hModule 
4) dwProcessId 
 
I changed the GlobalNativeHook in a way that you have complete control over those 4 parameters. Previously the latter 2 parameters were set automatically assuming that you want to use JNative's hModule and NULL as dwProcessId. 
 
You now have to set these parameters manually either by using the constructor that sets the latter 3 parameters or use the setter methods before calling hook(): 
 
this.mouseHook = new GlobalNativeHook(this.mouseCallback.getCallbackAddress(), new DWORD(0), JNative.getCurrentModule()); 
if (this.mouseHook.hook(WH.WH_MOUSE_LL)) 
{  
    mouseOk = true;  
}  
 
or you can do the following: 
 
this.mouseHook = new GlobalNativeHook(); 
this.mouseHook.setCallback(this.mouseCallback.getCallbackAddress()); 
this.mouseHook.setHModule(JNative.getCurrentModule()); 
if (this.mouseHook.hook(WH.WH_MOUSE_LL))
{  
    mouseOk = true;  
}  
 
 
Both should work fine. 
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
import org.xvolks.jnative.util.constants.winuser.WH;
import org.xvolks.jnative.util.constants.winuser.WM;

/**
 *
 * @author __USER__
 */
public class GlobalNativeHook implements Runnable, Callback
{
    private WH hookType;
    private int myAddress = -1;
    //private int lpfn;
    private int hHook;
    private boolean doHook = false;
    private DWORD hookThreadId;
    private int callbackAddress;
    private DWORD dwProcessId;
    private int hModule;
    private Thread thread;
    public static final int HC_ACTION = 0;
    private volatile Object syncObj = new Object();
    
    // uses this instance as callbackAddress
    public GlobalNativeHook()
    {
        this(0, null, 0);
    }
    // you can either pass Callback.getCallbackAddress() or the native callback address
    public GlobalNativeHook(int callbackAddress)
    {
        this(callbackAddress, null, 0);
    }
    // you can additionally pass processId to hook in
    public GlobalNativeHook(int callbackAddress, DWORD dwProcessId)
    {
        this(callbackAddress, dwProcessId, 0);
    }
    // you can additionally pass hModule which is a handle to a dll that contains the native callback function
    public GlobalNativeHook(int callbackAddress, DWORD dwProcessId, int hModule)
    {
        this.dwProcessId = dwProcessId;
        this.hModule = hModule;
        this.callbackAddress = callbackAddress;
        
        //addShutDownHook();
    }
    // call when hook is no longer needed.
    public void dispose() throws Exception
    {
        unHook();
        if(myAddress != 0)
        {
            JNative.releaseCallback(this);
        }
    }
    
    // Dont know if it makes sense to register a shutdown hook for each registered hook or let the user do the clean up...
    @SuppressWarnings("unused")
    protected void addShutDownHook()
    {
        Runtime.getRuntime().addShutdownHook(
                new Thread()
        {
            public void run()
            {
                try
                {
                    dispose();
                    JNative.unLoadAllLibraries();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        );
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
    public void setCallback(int callbackAddr) throws NativeException, IllegalAccessException, InterruptedException
    {
        this.callbackAddress = callbackAddr;
    }
    public boolean isHooked()
    {
        return doHook;
    }
    // returns true if the hook was successfully established
    public synchronized boolean hook(WH hookType) throws NativeException, IllegalAccessException, InterruptedException
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
        
        if(isHooked())
        {
            unHook();
        }
        
        this.hookType = hookType;
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
            hHook = User32.SetWindowsHookEx(hookType.getValue(), callbackAddress, hModule, dwProcessId);
            
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
            hookThreadId = null;
            if(hHook != 0)
            {
                try
                {
                    // unhook the hook
                    User32.UnhookWindowsHookEx(hHook);
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
    // do not forget to call super.callback() in your implementation.
    public int callback(long values[])
    {
        //JNative.getLogger().log(values[0]+" "+values[1]+" "+values[2]);
        try
        {
            return User32.CallNextHookEx(hHook, (int)values[0], (int)values[1], (int)values[2]);
            // you have to check MSDN manuals wether to return CallNextHookEx() or your own value here
        }
        catch (NativeException ex)
        {
            ex.printStackTrace();
        }
        catch (IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }
    public int getCallbackAddress() throws NativeException
    {
        if(myAddress == -1)
        {
            myAddress = JNative.createCallback(3,this);
        }
        return myAddress;
    }
}
