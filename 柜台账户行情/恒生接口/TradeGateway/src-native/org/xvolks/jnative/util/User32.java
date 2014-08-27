package org.xvolks.jnative.util;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.MSG;
import org.xvolks.jnative.misc.PAINTSTRUCT;
import org.xvolks.jnative.misc.POINT;
import org.xvolks.jnative.misc.WNDCLASS;
import org.xvolks.jnative.misc.WNDCLASSEX;
import org.xvolks.jnative.misc.basicStructures.DC;
import org.xvolks.jnative.misc.basicStructures.DWORD;
import org.xvolks.jnative.misc.basicStructures.HANDLE;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.misc.ICONINFO;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.misc.basicStructures.LPARAM;
import org.xvolks.jnative.misc.basicStructures.LRECT;
import org.xvolks.jnative.misc.basicStructures.LRESULT;
import org.xvolks.jnative.misc.basicStructures.UINT;
import org.xvolks.jnative.misc.basicStructures.WPARAM;
import org.xvolks.jnative.pointers.NullPointer;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.util.windows.structures.DEV_BROADCAST_HANDLE;
import org.xvolks.jnative.util.windows.structures.GUID;

/**
 * User32 this is the class wrapper to User32.dll.<br>
 * When a developper needs a function of this DLL (s)he should add it here.
 *
 * $Id: User32.java,v 1.62 2008/10/11 13:48:05 thubby Exp $;
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class User32
{
	public static final String DLL_NAME = "User32.dll";
	
	public static final int SWP_FRAMECHANGED = 0x20;
	public static final int SWP_DRAWFRAME = SWP_FRAMECHANGED;
	public static final int SWP_HIDEWINDOW = 0x80;
	public static final int SWP_NOACTIVATE = 0x10;
	public static final int SWP_NOCOPYBITS = 0x100;
	public static final int SWP_NOMOVE = 0x2;
	public static final int SWP_NOOWNERZORDER = 0x200;
	public static final int SWP_NOREDRAW = 0x8;
	public static final int SWP_NOREPOSITION = SWP_NOOWNERZORDER;
	public static final int SWP_NOSIZE = 0x1;
	public static final int SWP_NOZORDER = 0x4;
	public static final int SWP_SHOWWINDOW = 0x40;
	
	public static final HWND HWND_TOP = new HWND(0);
	public static final HWND HWND_TOPMOST = new HWND(-1);
	public static final HWND HWND_NOTOPMOST = new HWND(-2);
	public static final HWND HWND_BOTTOM = new HWND(1);
	
	public static final int IDI_APPLICATION  =  32512;
	public static final int IDI_ASTERISK     =  32516;
	public static final int IDI_ERROR        =  32513;
	public static final int IDI_EXCLAMATION  =  32515;
	public static final int IDI_HAND         =  IDI_ERROR;
	public static final int IDI_INFORMATION  =  IDI_ASTERISK;
	public static final int IDI_QUESTION     =  32514;
	public static final int IDI_WARNING      =  IDI_EXCLAMATION;
	public static final int IDI_WINLOGO      =  32517;  // IDI_APPLICATION on WinXP
	public static final int IDI_SHIELD		 =	32518;  // Vista only
	
	public static final int MOD_ALT = 0x1;
	public static final int MOD_CONTROL = 0x2;
	public static final int MOD_SHIFT = 0x4;
	public static final int MOD_WIN = 0x8;
	
	public static final int CWP_ALL = 0x0;
	public static final int CWP_SKIPDISABLED = 0x2;
	public static final int CWP_SKIPINVISIBLE = 0x1;
	public static final int CWP_SKIPTRANSPARENT = 0x4;
	
	public static final int MF_BYCOMMAND = 0x0;
	public static final int MF_BYPOSITION = 0x400;
	public static final int MF_REMOVE = 0x1000;
	
	/*
	BOOL IsWindowVisible(
		HWND hWnd
	);
	 */
	public static boolean IsWindowVisible(HWND hWnd) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "IsWindowVisible");
		
		jNative.setRetVal(Type.INT);
		jNative.setParameter(0, hWnd.getValue());
		jNative.invoke();
		
		return (jNative.getRetValAsInt() != 0);
	}
	
	/*
	BOOL WINAPI UnhookWinEvent(
	  HWINEVENTHOOK hWinEventHook
	);
	 */
	public static boolean UnhookWinEvent(int hWinEventHook) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "UnhookWinEvent");
		
		jNative.setRetVal(Type.INT);
		jNative.setParameter(0, hWinEventHook);
		jNative.invoke();
		
		return (jNative.getRetValAsInt() != 0);
	}
	
	/*
	HWINEVENTHOOK WINAPI SetWinEventHook(
	  UINT eventMin,
	  UINT eventMax,
	  HMODULE hmodWinEventProc,
	  WINEVENTPROC lpfnWinEventProc,
	  DWORD idProcess,
	  DWORD idThread,
	  UINT dwflags
	);
	 */
	public static int SetWinEventHook(UINT eventMin, UINT eventMax, int hmodWinEventProc,
			int lpfnWinEventProc,
			DWORD idProcess,
			DWORD idThread,
			UINT dwflags) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "SetWinEventHook");
		
		int i = 0;
		jNative.setRetVal(Type.INT);
		jNative.setParameter(i++, eventMin.getValue());
		jNative.setParameter(i++, eventMax.getValue());
		jNative.setParameter(i++, hmodWinEventProc);
		jNative.setParameter(i++, lpfnWinEventProc);
		jNative.setParameter(i++, idProcess.getValue());
		jNative.setParameter(i++, idThread.getValue());
		jNative.setParameter(i++, dwflags.getValue());
		jNative.invoke();
		
		return jNative.getRetValAsInt();
	}
	
	/*
	BOOL IsIconic(
		HWND hWnd
	);
	 */
	public static boolean IsIconic(HWND hWnd) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "IsIconic");
		
		jNative.setRetVal(Type.INT);
		jNative.setParameter(0, hWnd.getValue());
		jNative.invoke();
		
		return (jNative.getRetValAsInt() != 0);
	}
	
	/*
	BOOL WINAPI FlashWindow(
	  __in  HWND hWnd,
	  __in  BOOL bInvert
	);
	 */
	public static boolean FlashWindow(HWND hWnd, boolean bInvert) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "FlashWindow");
		
		jNative.setRetVal(Type.INT);
		jNative.setParameter(0, hWnd.getValue());
		jNative.setParameter(1, Type.INT, bInvert ? "1" : "0");
		jNative.invoke();
		
		return (jNative.getRetValAsInt() != 0);
	}
	
	/*
	BOOL WINAPI MessageBeep(
		__in  UINT uType
	);
	 */
	public static boolean MessageBeep(int uType) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "MessageBeep");
		
		jNative.setRetVal(Type.INT);
		jNative.setParameter(0, uType);
		jNative.invoke();
		
		return (jNative.getRetValAsInt() != 0);
	}
	
	/*
	HMENU GetSystemMenu(
		HWND hWnd,
		BOOL bRevert
	);
	 */
	public static HANDLE GetSystemMenu(HWND hWnd, boolean bRevert) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "GetSystemMenu");
		
		jNative.setRetVal(Type.INT);
		jNative.setParameter(0, hWnd.getValue());
		jNative.setParameter(1, Type.INT, bRevert ? "1" : "0");
		jNative.invoke();
		
		return new HANDLE(jNative.getRetValAsInt());
	}
	/*
	BOOL DeleteMenu(
		HMENU hMenu,
		UINT uPosition,
		UINT uFlags
	);
	 */
	public static boolean DeleteMenu(HANDLE hMenu, UINT uPosition, UINT uFlags) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "DeleteMenu");
		
		jNative.setRetVal(Type.INT);
		jNative.setParameter(0, hMenu.getValue());
		jNative.setParameter(1, uPosition.getValue());
		jNative.setParameter(2, uFlags.getValue());
		jNative.invoke();
		
		return (jNative.getRetValAsInt() != 0);
		
	}
	/*
	BOOL RemoveMenu(
		HMENU hMenu,
		UINT uPosition,
		UINT uFlags
	);
	 */
	public static boolean RemoveMenu(HANDLE hMenu, UINT uPosition, UINT uFlags) throws NativeException, IllegalAccessException
	{
		JNative jNative = new JNative(DLL_NAME, "RemoveMenu");
		
		jNative.setRetVal(Type.INT);
		jNative.setParameter(0, hMenu.getValue());
		jNative.setParameter(1, uPosition.getValue());
		jNative.setParameter(2, uFlags.getValue());
		jNative.invoke();
		
		return (jNative.getRetValAsInt() != 0);
		
	}
	/*
	BOOL GetClientRect(
		HWND hWnd,
		LPRECT lpRect
	);
	 */
	public static boolean GetClientRect(HWND hWnd, LRECT lpRect) throws NativeException, IllegalAccessException
	{
		JNative GetClientRect = new JNative(DLL_NAME, "GetClientRect");
		
		GetClientRect.setRetVal(Type.INT);
		GetClientRect.setParameter(0, hWnd.getValue());
		GetClientRect.setParameter(1, lpRect.getPointer().getPointer());
		GetClientRect.invoke();
		
		return (GetClientRect.getRetValAsInt() != 0);
	}
	/*
	BOOL EndPaint(
	  HWND hWnd,                  // handle to window
	  CONST PAINTSTRUCT *lpPaint  // paint data
	);
	 */
	public static boolean EndPaint(HWND hWnd, PAINTSTRUCT lpPaint) throws NativeException, IllegalAccessException
	{
		JNative EndPaint = new JNative(DLL_NAME, "EndPaint");
		try
		{
			
			EndPaint.setRetVal(Type.INT);
			EndPaint.setParameter(0, hWnd.getValue());
			EndPaint.setParameter(1, lpPaint == null ? NullPointer.NULL.getPointer() : lpPaint.getPointer().getPointer());
			EndPaint.invoke();
			
			return (EndPaint.getRetValAsInt() != 0);
		}
		finally
		{
			if(EndPaint != null)
				EndPaint.dispose();
		}
	}
	
	/*
	HDC BeginPaint(
	  HWND hwnd,            // handle to window
	  LPPAINTSTRUCT lpPaint // paint information
	);
	 */
	public static DC BeginPaint(HWND hWnd, PAINTSTRUCT lpPaint) throws NativeException, IllegalAccessException
	{
		JNative BeginPaint = new JNative(DLL_NAME, "BeginPaint");
		try
		{
			
			BeginPaint.setRetVal(Type.INT);
			BeginPaint.setParameter(0, hWnd.getValue());
			BeginPaint.setParameter(1, lpPaint == null ? NullPointer.NULL.getPointer() : lpPaint.getPointer().getPointer());
			BeginPaint.invoke();
			
			return new DC(BeginPaint.getRetValAsInt());
		}
		finally
		{
			if(BeginPaint != null)
				BeginPaint.dispose();
		}
	}
	
	/*
	BOOL ValidateRect(
	  HWND hWnd,          // handle to window
	  CONST RECT *lpRect  // validation rectangle coordinates
	);
	 */
	public static boolean ValidateRect(HWND hWnd, LRECT lprcUpdate) throws NativeException, IllegalAccessException
	{
		JNative ValidateRect = new JNative(DLL_NAME, "ValidateRect");
		try
		{
			
			ValidateRect.setRetVal(Type.INT);
			ValidateRect.setParameter(0, hWnd.getValue());
			ValidateRect.setParameter(1, lprcUpdate == null ? NullPointer.NULL.getPointer() : lprcUpdate.getPointer().getPointer());
			ValidateRect.invoke();
			
			return (ValidateRect.getRetValAsInt() != 0);
		}
		finally
		{
			if(ValidateRect != null)
				ValidateRect.dispose();
		}
	}
	/*
	BOOL RedrawWindow(
	  HWND hWnd,               // handle to window
	  CONST RECT *lprcUpdate,  // update rectangle
	  HRGN hrgnUpdate,         // handle to update region
	  UINT flags               // array of redraw flags
	);
	 */
	public static boolean RedrawWindow(HWND hWnd, LRECT lprcUpdate, int hrgnUpdate, UINT flags) throws NativeException, IllegalAccessException
	{
		JNative RedrawWindow = new JNative(DLL_NAME, "RedrawWindow");
		try
		{
			
			RedrawWindow.setRetVal(Type.INT);
			RedrawWindow.setParameter(0, hWnd.getValue());
			RedrawWindow.setParameter(1, lprcUpdate == null ? NullPointer.NULL.getPointer() : lprcUpdate.getPointer().getPointer());
			RedrawWindow.setParameter(2, hrgnUpdate);
			RedrawWindow.setParameter(3, flags.getValue());
			RedrawWindow.invoke();
			
			return (RedrawWindow.getRetValAsInt() != 0);
		}
		finally
		{
			if(RedrawWindow != null)
				RedrawWindow.dispose();
		}
	}
	
	/*
	VOID SwitchToThisWindow(
		HWND hWnd,
		BOOL fAltTab
	);
	 */
	public static void SwitchToThisWindow(HWND hWnd, boolean fAltTab) throws NativeException, IllegalAccessException
	{
		JNative SwitchToThisWindow = new JNative(DLL_NAME, "SwitchToThisWindow");
		try
		{
			
			SwitchToThisWindow.setRetVal(Type.INT);
			SwitchToThisWindow.setParameter(0, hWnd.getValue());
			SwitchToThisWindow.setParameter(1, Type.INT, fAltTab ? "1" : "0");
			SwitchToThisWindow.invoke();
		}
		finally
		{
			if(SwitchToThisWindow != null)
				SwitchToThisWindow.dispose();
		}
	}
	
  /*
	BOOL BringWindowToTop(
		HWND hWnd
	);
   */
	public static boolean BringWindowToTop(HWND hWnd) throws NativeException, IllegalAccessException
	{
		JNative BringWindowToTop = new JNative(DLL_NAME, "BringWindowToTop");
		try
		{
			
			BringWindowToTop.setRetVal(Type.INT);
			BringWindowToTop.setParameter(0, hWnd.getValue());
			BringWindowToTop.invoke();
			return (BringWindowToTop.getRetValAsInt() != 0);
		}
		finally
		{
			if(BringWindowToTop != null)
				BringWindowToTop.dispose();
		}
	}
	
	/*
	BOOL PostThreadMessage(
		DWORD idThread,
		UINT Msg,
		WPARAM wParam,
		LPARAM lParam
	);
	 */
	public static boolean PostThreadMessage(DWORD idThread,
			UINT Msg,
			WPARAM wParam,
			LPARAM lParam) throws NativeException, IllegalAccessException
	{
		JNative PostThreadMessage = new JNative(DLL_NAME, "PostThreadMessageA");
		try
		{
			
			PostThreadMessage.setRetVal(Type.INT);
			PostThreadMessage.setParameter(0, idThread.getValue());
			PostThreadMessage.setParameter(1, Msg.getValue());
			PostThreadMessage.setParameter(2, wParam.getValue());
			PostThreadMessage.setParameter(3, lParam.getValue());
			PostThreadMessage.invoke();
			return (PostThreadMessage.getRetValAsInt() != 0);
		}
		finally
		{
			if(PostThreadMessage != null)
				PostThreadMessage.dispose();
		}
	}
	
	/*
		SHORT GetAsyncKeyState(
			int vKey
		);
	 */
	public static int GetAsyncKeyState(int vKey) throws NativeException, IllegalAccessException
	{
		JNative GetAsyncKeyState = new JNative(DLL_NAME, "GetAsyncKeyState");
		try
		{
			
			GetAsyncKeyState.setRetVal(Type.INT);
			GetAsyncKeyState.setParameter(0, vKey);
			GetAsyncKeyState.invoke();
			return GetAsyncKeyState.getRetValAsInt();
		}
		finally
		{
			if(GetAsyncKeyState != null)
				GetAsyncKeyState.dispose();
		}
	}
  /*
	HWND WindowFromPoint(
		POINT POINT
	);
   */
	public static HWND WindowFromPoint(POINT pt) throws NativeException, IllegalAccessException
	{
		JNative WindowFromPoint = new JNative(DLL_NAME, "WindowFromPoint");
		try
		{
			
			WindowFromPoint.setRetVal(Type.INT);
			WindowFromPoint.setParameter(0, pt.getX());
			WindowFromPoint.setParameter(1, pt.getY());
			WindowFromPoint.invoke();
			return new HWND(WindowFromPoint.getRetValAsInt());
		}
		finally
		{
			if(WindowFromPoint != null)
				WindowFromPoint.dispose();
		}
	}
	
	/*
	HWND ChildWindowFromPointEx(
		HWND hwndParent,
		POINT pt,
		UINT uFlags
	);
	 */
	public static HWND ChildWindowFromPointEx(HWND hwnd,
			POINT pt,
			int uFlags) throws NativeException, IllegalAccessException
	{
		JNative ChildWindowFromPointEx = new JNative(DLL_NAME, "ChildWindowFromPointEx");
		try
		{
			
			ChildWindowFromPointEx.setRetVal(Type.INT);
			
			int pos = 0;
			ChildWindowFromPointEx.setParameter(pos++, hwnd.getValue());
			ChildWindowFromPointEx.setParameter(pos++, pt.getX());
			ChildWindowFromPointEx.setParameter(pos++, pt.getY());
			ChildWindowFromPointEx.setParameter(pos++, uFlags);
			ChildWindowFromPointEx.invoke();
			return new HWND(ChildWindowFromPointEx.getRetValAsInt());
		}
		finally
		{
			if(ChildWindowFromPointEx != null)
				ChildWindowFromPointEx.dispose();
		}
	}
	
	/*
	BOOL InvalidateRect(
	  HWND hWnd,           // handle to window
	  CONST RECT* lpRect,  // rectangle coordinates
	  BOOL bErase          // erase state
	);
	 */
	public static boolean InvalidateRect(HWND hWnd,
			LRECT lpRect,
			boolean bErase) throws NativeException, IllegalAccessException
	{
		JNative InvalidateRect = new JNative(DLL_NAME, "InvalidateRect");
		try
		{
			
			InvalidateRect.setRetVal(Type.INT);
			
			int pos = 0;
			InvalidateRect.setParameter(pos++, hWnd.getValue());
			InvalidateRect.setParameter(pos++, lpRect == null ? NullPointer.NULL.getPointer() : lpRect.getPointer().getPointer());
			InvalidateRect.setParameter(pos++, Type.INT, bErase ? "1" : "0");
			InvalidateRect.invoke();
			return (InvalidateRect.getRetValAsInt() != 0);
		}
		finally
		{
			if(InvalidateRect != null)
				InvalidateRect.dispose();
		}
	}
	
	/*
	BOOL UpdateLayeredWindow(
		HWND hwnd,
		HDC hdcDst,
		POINT *pptDst,
		SIZE *psize,
		HDC hdcSrc,
		POINT *pptSrc,
		COLORREF crKey,
		BLENDFUNCTION *pblend,
		DWORD dwFlags
	);
	 */
	public static boolean UpdateLayeredWindow(HWND hwnd,
			DC hdcDst,
			POINT pptDst,
			int psize,
			DC hdcSrc,
			POINT pptSrc,
			DWORD crKey,
			int pblend,
			DWORD dwFlags) throws NativeException, IllegalAccessException
	{
		JNative UpdateLayeredWindow = new JNative(DLL_NAME, "UpdateLayeredWindow");
		try
		{
			
			UpdateLayeredWindow.setRetVal(Type.INT);
			
			int pos = 0;
			UpdateLayeredWindow.setParameter(pos++, hwnd.getValue());
			UpdateLayeredWindow.setParameter(pos++, hdcDst == null ? NullPointer.NULL.getPointer() : hdcDst.getValue().intValue());
			UpdateLayeredWindow.setParameter(pos++, pptDst == null ? NullPointer.NULL : pptDst.getValue().getPointer());
			UpdateLayeredWindow.setParameter(pos++, psize);
			UpdateLayeredWindow.setParameter(pos++, hdcSrc == null ? NullPointer.NULL.getPointer() : hdcSrc.getValue().intValue());
			UpdateLayeredWindow.setParameter(pos++, pptSrc == null ? NullPointer.NULL : pptSrc.getValue().getPointer());
			UpdateLayeredWindow.setParameter(pos++, crKey.getValue());
			UpdateLayeredWindow.setParameter(pos++, pblend);
			UpdateLayeredWindow.setParameter(pos++, dwFlags.getValue());
			UpdateLayeredWindow.invoke();
			return (UpdateLayeredWindow.getRetValAsInt() != 0);
		}
		finally
		{
			if(UpdateLayeredWindow != null)
				UpdateLayeredWindow.dispose();
		}
	}
	
	/*
	BOOL SetLayeredWindowAttributes(
		HWND hwnd,
		COLORREF crKey,
		BYTE bAlpha,
		DWORD dwFlags
	);
	 */
	public static boolean SetLayeredWindowAttributes(HWND hwnd,
			int crKey,
			byte bAlpha,
			DWORD dwFlags)	throws NativeException, IllegalAccessException
	{
		JNative SetLayeredWindowAttributes = new JNative(DLL_NAME, "SetLayeredWindowAttributes");
		try
		{
			
			SetLayeredWindowAttributes.setRetVal(Type.INT);
			
			int pos = 0;
			SetLayeredWindowAttributes.setParameter(pos++, hwnd.getValue());
			SetLayeredWindowAttributes.setParameter(pos++, crKey);
			SetLayeredWindowAttributes.setParameter(pos++, bAlpha);
			SetLayeredWindowAttributes.setParameter(pos++, dwFlags.getValue());
			SetLayeredWindowAttributes.invoke();
			return (SetLayeredWindowAttributes.getRetValAsInt() != 0);
		}
		finally
		{
			if(SetLayeredWindowAttributes != null)
				SetLayeredWindowAttributes.dispose();
		}
	}
	
	
	public static boolean GetCursorPos(POINT p) throws NativeException, IllegalAccessException
	{
		JNative	GetCursorPos = new JNative(DLL_NAME, "GetCursorPos");
		GetCursorPos.setRetVal(Type.INT);
		GetCursorPos.setParameter(0,p.getPointer());
		GetCursorPos.invoke();
		p.getValueFromPointer();
		return (GetCursorPos.getRetValAsInt() != 0);
	}
	
	/*
	 *HWND GetDesktopWindow(VOID);
	 */
	public static HWND GetDesktopWindow() throws NativeException, IllegalAccessException
	{
		JNative GetDesktopWindow = new JNative(DLL_NAME, "GetDesktopWindow");
		try
		{
			GetDesktopWindow.setRetVal(Type.INT);
			GetDesktopWindow.invoke();
			return new HWND(GetDesktopWindow.getRetValAsInt());
		}
		finally
		{
			if(GetDesktopWindow != null)
				GetDesktopWindow.dispose();
		}
	}
	
	/*
	 *BOOL GetIconInfo(
		HICON hIcon,
		PICONINFO piconinfo
	);
	 */
	public static boolean GetIconInfo(LONG hIcon, ICONINFO piconinfo) throws NativeException, IllegalAccessException
	{
		JNative GetIconInfo = new JNative(DLL_NAME, "GetIconInfo");
		try
		{
			GetIconInfo.setRetVal(Type.INT);
			GetIconInfo.setParameter(0, hIcon.getValue());
			GetIconInfo.setParameter(1, piconinfo.getPointer());
			GetIconInfo.invoke();
			return (GetIconInfo.getRetValAsInt() != 0);
		}
		finally
		{
			if(GetIconInfo != null)
				GetIconInfo.dispose();
		}
	}
	
	/*
	 int ReleaseDC(
		HWND hWnd,  // handle to window
		HDC hDC     // handle to DC
	);
	 */
	public static int ReleaseDC(HWND hwnd, DC hdc) throws NativeException, IllegalAccessException
	{
		JNative ReleaseDC = new JNative(DLL_NAME, "ReleaseDC");
		try
		{
			ReleaseDC.setRetVal(Type.INT);
			ReleaseDC.setParameter(0, hwnd.getValue());
			ReleaseDC.setParameter(1, hdc.getValue());
			ReleaseDC.invoke();
			return ReleaseDC.getRetValAsInt();
		}
		finally
		{
			if(ReleaseDC != null)
				ReleaseDC.dispose();
		}
	}
	/*
	HDC GetDC(
	  HWND hWnd   // handle to window
	);
	 **/
	public static DC GetDC(HWND hwnd) throws NativeException, IllegalAccessException
	{
		JNative	GetDC = new JNative(DLL_NAME, "GetDC");
		GetDC.setRetVal(Type.INT);
		
		GetDC.setParameter(0, hwnd.getValue());
		GetDC.invoke();
		return new DC(GetDC.getRetValAsInt());
	}
	
	/*
	HDC GetWindowDC(
	  HWND hWnd   // handle to window
	);
	 */
	public static DC GetWindowDC(HWND hwnd)	throws NativeException, IllegalAccessException
	{
		JNative GetWindowDC = new JNative(DLL_NAME, "GetWindowDC");
		GetWindowDC.setRetVal(Type.INT);
		
		
		GetWindowDC.setParameter(0, hwnd.getValue());
		GetWindowDC.invoke();
		return new DC(GetWindowDC.getRetValAsInt());
	}
	
	public static int GetWindowThreadProcessId(final HWND hwnd) throws NativeException, IllegalAccessException
	{
		if(hwnd == null)
		{
			return -1;
		}
		
		Pointer nBufferGWT = null;
		try
		{
			JNative getProcessHandle = new JNative("User32.dll","GetWindowThreadProcessId");
			getProcessHandle.setRetVal(Type.INT);
			
			nBufferGWT = new Pointer(MemoryBlockFactory.createMemoryBlock(4));
			getProcessHandle.setParameter(0, hwnd.getValue());
			getProcessHandle.setParameter(1, nBufferGWT);
			getProcessHandle.invoke();
			
			return nBufferGWT.getAsInt(0);
		}
		finally
		{
			try
			{
				if(nBufferGWT != null)
				{
					nBufferGWT.dispose();
				}
				nBufferGWT = null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * HWND FindWindow(
	 *
	 * LPCTSTR lpClassName, LPCTSTR lpWindowName );
	 */
	public static HWND FindWindow(String className, String windowName) throws NativeException, IllegalAccessException
	{
		JNative _FindWindow = new JNative(DLL_NAME, "FindWindowA");
		try
		{
			_FindWindow.setRetVal(Type.INT);
			_FindWindow.setParameter(0, Type.STRING, className);
			_FindWindow.setParameter(1, Type.STRING, windowName);
			_FindWindow.invoke();
			int valRet = Integer.parseInt(_FindWindow.getRetVal());
			return new HWND(valRet);
		}
		finally
		{
			if(_FindWindow != null)
				_FindWindow.dispose();
		}
	}
	/*
		WNDPROC lpPrevWndFunc,
		HWND hWnd,
		UINT Msg,
		WPARAM wParam,
		LPARAM lParam
	 */
	// Do _not_ use JNative's UINT for msg parameter. This makes windowprocs crash! Dont know why...
	public static LRESULT CallWindowProc(LONG lpPrevWndFunc, HWND hwnd, int msg, WPARAM wparam,
			LPARAM lparam)
			throws NativeException, IllegalAccessException
	{
		
		JNative	CallWindowProcA = new JNative(DLL_NAME, "CallWindowProcA");
		CallWindowProcA.setRetVal(Type.INT);
		
		
		int i = 0;
		CallWindowProcA.setParameter(i++, lpPrevWndFunc.getValue());
		CallWindowProcA.setParameter(i++, hwnd.getValue());
		CallWindowProcA.setParameter(i++, msg);
		CallWindowProcA.setParameter(i++, wparam.getValue());
		CallWindowProcA.setParameter(i++, lparam.getValue());
		CallWindowProcA.invoke();
		
		return new LRESULT(CallWindowProcA.getRetValAsInt());
		
	}
	public static int CallWindowProc(int lpPrevWndFunc, int hwnd, int msg, int wparam,
			int lparam)
			throws NativeException, IllegalAccessException
	{
		
		JNative	CallWindowProcA = new JNative(DLL_NAME, "CallWindowProcA");
		CallWindowProcA.setRetVal(Type.INT);
		
		int i = 0;
		CallWindowProcA.setParameter(i++, lpPrevWndFunc);
		CallWindowProcA.setParameter(i++, hwnd);
		CallWindowProcA.setParameter(i++, msg);
		CallWindowProcA.setParameter(i++, wparam);
		CallWindowProcA.setParameter(i++, lparam);
		CallWindowProcA.invoke();
		
		return CallWindowProcA.getRetValAsInt();
		
	}
	
	public static LRESULT DefWindowProc(HWND hwnd, UINT msg, WPARAM wparam,
			LPARAM lparam) throws NativeException, IllegalAccessException
	{
		JNative	DefWindowProc = new JNative(DLL_NAME, "DefWindowProcA");
		DefWindowProc.setRetVal(Type.INT);
		
		DefWindowProc.setParameter(0, Type.INT, hwnd.getValueAsString());
		DefWindowProc.setParameter(1, Type.INT, msg.getValueAsString());
		DefWindowProc.setParameter(2, Type.INT, wparam.getValueAsString());
		DefWindowProc.setParameter(3, Type.INT, lparam.getValueAsString());
		DefWindowProc.invoke();
		return new LRESULT(DefWindowProc.getRetValAsInt());
	}
	
	public static int DefWindowProc(int hwnd, int msg, int wparam,
			int lparam) throws NativeException, IllegalAccessException
	{
		JNative	DefWindowProc = new JNative(DLL_NAME, "DefWindowProcA");
		DefWindowProc.setRetVal(Type.INT);
		
		DefWindowProc.setParameter(0, hwnd);
		DefWindowProc.setParameter(1, msg);
		DefWindowProc.setParameter(2, wparam);
		DefWindowProc.setParameter(3, lparam);
		DefWindowProc.invoke();
		return DefWindowProc.getRetValAsInt();
	}
	
	public static boolean UpdateWindow(HWND hwnd) throws NativeException,
			IllegalAccessException
	{
		JNative dm = new JNative(DLL_NAME, "UpdateWindow");
		try
		{
			dm.setRetVal(Type.INT);
			dm.setParameter(0, hwnd.getValue());
			dm.invoke();
			return (dm.getRetValAsInt() != 0);
		}
		finally
		{
			if(dm != null)
				dm.dispose();
		}
	}
	
	public static void DispatchMessage(MSG msg) throws NativeException,
			IllegalAccessException
	{
		JNative dm = new JNative(DLL_NAME, "DispatchMessageA");
		// dm.setRetVal(Type.INT);
		dm.setParameter(0, msg.getValue().getPointer());
		dm.invoke();
		dm.dispose();
	}
	
	public static void TranslateMessage(MSG msg) throws NativeException,
			IllegalAccessException
	{
		JNative dm = new JNative(DLL_NAME, "TranslateMessage");
		// dm.setRetVal(Type.INT);
		dm.setParameter(0, msg.getValue().getPointer());
		dm.invoke();
		dm.dispose();
	}
	
	public static int PeekMessage(MSG msg, HWND hwnd, int minMSG, int maxMSG, int removeMsg)
	throws NativeException, IllegalAccessException
	{
		JNative gm = new JNative(DLL_NAME, "PeekMessageA");
		gm.setRetVal(Type.INT);
		gm.setParameter(0, msg.getValue().getPointer());
		gm.setParameter(1, Type.INT, hwnd.getValueAsString());
		gm.setParameter(2, Type.INT, "" + minMSG);
		gm.setParameter(3, Type.INT, "" + maxMSG);
		gm.setParameter(4, Type.INT, "" + removeMsg);
		gm.invoke();
		int ret = gm.getRetValAsInt();
		gm.dispose();
		return ret;
	}
	
	public static int GetMessage(MSG msg, HWND hwnd, int minMSG, int maxMSG)
	throws NativeException, IllegalAccessException
	{
		JNative gm = new JNative(DLL_NAME, "GetMessageA");
		gm.setRetVal(Type.INT);
		gm.setParameter(0, msg.getValue().getPointer());
		gm.setParameter(1, hwnd.getValue());
		gm.setParameter(2, minMSG);
		gm.setParameter(3, maxMSG);
		gm.invoke();
		int ret = gm.getRetValAsInt();
		gm.dispose();
		return ret;
	}
	
	public static boolean ShowWindow(HWND hwnd, int nCmdShow)
	throws NativeException, IllegalAccessException
	{
		JNative w = new JNative(DLL_NAME, "ShowWindow");
		w.setRetVal(Type.INT);
		w.setParameter(0, hwnd.getValue());
		w.setParameter(1, nCmdShow);
		w.invoke();
		return (w.getRetValAsInt() != 0);
		
	}
	/*
	BOOL DestroyWindow(
		HWND hWnd
	);
	 **/
	public static final boolean DestroyWindow(HWND hWnd) throws NativeException, IllegalAccessException
	{
		if(hWnd == null)
		{
			return false;
		}
		
		JNative n = null;
		try
		{
			n = new JNative(DLL_NAME, "DestroyWindow");
			n.setRetVal(Type.INT);
			n.setParameter(0, hWnd.getValue().intValue());
			n.invoke();
			return (n.getRetValAsInt() != 0);
		}
		finally
		{
			if (n != null)
			{
				n.dispose();
			}
		}
	}
	
	/**
	 * HWND CreateWindowEx( DWORD dwExStyle, LPCTSTR lpClassName, LPCTSTR
	 * lpWindowName, DWORD dwStyle, int x, int y, int nWidth, int nHeight, HWND
	 * hWndParent, -3 for HWND_MESSAGE (Message only windows) HMENU hMenu,
	 * HINSTANCE hInstance, LPVOID lpParam );
	 */
	public static final int CreateWindowEx(int dwExStyle, String lpClassName,
			String lpWindowName, int dwStyle, int x, int y, int nWidth,
			int nHeight, int hWndParent, int hMenu, int hInstance, int lParam)
			throws NativeException, IllegalAccessException
	{
		JNative n = null;
		try
		{
			n = new JNative(DLL_NAME, "CreateWindowExA");
			n.setRetVal(Type.INT);
			int i = 0;
			n.setParameter(i++, Type.INT, "" + dwExStyle);
			n.setParameter(i++, Type.STRING, lpClassName);
			if(lpWindowName == null)
			{
				n.setParameter(i++, 0);
			}
			else
			{
				n.setParameter(i++, Type.STRING, lpWindowName);
			}
			n.setParameter(i++, Type.INT, "" + dwStyle);
			n.setParameter(i++, Type.INT, "" + x);
			n.setParameter(i++, Type.INT, "" + y);
			n.setParameter(i++, Type.INT, "" + nWidth);
			n.setParameter(i++, Type.INT, "" + nHeight);
			n.setParameter(i++, Type.INT, "" + hWndParent);
			n.setParameter(i++, Type.INT, "" + hMenu);
			n.setParameter(i++, Type.INT, ""
					+ (hInstance == 0 ? JNative.getCurrentModule()
					: hInstance));
			n.setParameter(i++, Type.INT, "" + lParam);
			n.invoke();
			return Integer.parseInt(n.getRetVal());
		}
		finally
		{
			if (n != null)
			{
				n.dispose();
			}
		}
	}
	
	public static final int CreateWindowEx(int dwExStyle, LONG lpClassName,
			String lpWindowName, int dwStyle, int x, int y, int nWidth,
			int nHeight, int hWndParent, int hMenu, int hInstance, int lParam)
			throws NativeException, IllegalAccessException
	{
		JNative n = null;
		try
		{
			n = new JNative(DLL_NAME, "CreateWindowExA");
			n.setRetVal(Type.INT);
			int i = 0;
			n.setParameter(i++, dwExStyle);
			n.setParameter(i++, lpClassName.getValue());
			if(lpWindowName == null)
			{
				n.setParameter(i++, 0);
			}
			else
			{
				n.setParameter(i++, lpWindowName);
			}
			n.setParameter(i++, dwStyle);
			n.setParameter(i++, x);
			n.setParameter(i++, y);
			n.setParameter(i++, nWidth);
			n.setParameter(i++, nHeight);
			n.setParameter(i++, hWndParent);
			n.setParameter(i++, hMenu);
			n.setParameter(i++, (hInstance == 0 ? JNative.getCurrentModule() : hInstance));
			n.setParameter(i++, lParam);
			n.invoke();
			return n.getRetValAsInt();
		}
		finally
		{
			if (n != null)
			{
				n.dispose();
			}
		}
	}
	
	public static final int MessageBox(int parentHandle, String message,
			String caption, int buttons) throws NativeException, IllegalAccessException
	{
		JNative n = null;
		try
		{
			n = new JNative(DLL_NAME, "MessageBoxA");
			n.setRetVal(Type.INT);
			int i = 0;
			n.setParameter(i++, Type.INT, "" + parentHandle);
			n.setParameter(i++, Type.STRING, message);
			n.setParameter(i++, Type.STRING, caption);
			n.setParameter(i++, Type.INT, "" + buttons);
			n.invoke();
			return Integer.parseInt(n.getRetVal());
		}
		finally
		{
			if (n != null)
				n.dispose();
		}
	}
	
	/**
	 * <pre>
	 *  EnumWindows Function
	 *
	 * 	 The EnumWindows function enumerates all top-level windows on the screen by passing the handle to each window, in turn, to an application-defined callback function. EnumWindows continues until the last top-level window is enumerated or the callback function returns FALSE.
	 *
	 * 	 Syntax
	 *
	 * 	 BOOL EnumWindows(
	 *
	 * 	 WNDENUMPROC lpEnumFunc,
	 * 	 LPARAM lParam
	 * 	 );
	 *
	 * 	 Parameters
	 *
	 * 	 lpEnumFunc
	 * 	 [in] Pointer to an application-defined callback function. For more information, see EnumWindowsProc.
	 * 	 lParam
	 * 	 [in] Specifies an application-defined value to be passed to the callback function.
	 *
	 * 	 Return Value
	 *
	 * 	 If the function succeeds, the return value is nonzero.
	 *
	 * 	 If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 *
	 * 	 If EnumWindowsProc returns zero, the return value is also zero. In this case, the callback function should call SetLastError to obtain a meaningful error code to be returned to the caller of EnumWindows.
	 *
	 *
	 * 	 Remarks
	 *
	 * 	 The EnumWindows function does not enumerate child windows, with the exception of a few top-level windows owned by the system that have the WS_CHILD style.
	 *
	 * 	 This function is more reliable than calling the GetWindow function in a loop. An application that calls GetWindow to perform this task risks being caught in an infinite loop or referencing a handle to a window that has been destroyed.
	 *
	 * </pre>
	 *
	 * lpEnumFunc must be the address returned by JNative.createCallback()
	 */
	
	public static boolean EnumWindows(Callback lpEnumFunc, int lParam) throws NativeException, IllegalAccessException
	{
		JNative	nEnumWindows = new JNative(DLL_NAME, "EnumWindows", false);
		nEnumWindows.setRetVal(Type.INT);
		
		nEnumWindows.setParameter(0, lpEnumFunc.getCallbackAddress());
		nEnumWindows.setParameter(1, lParam);
		nEnumWindows.invoke();
		return !"0".equals(nEnumWindows.getRetVal());
	}
	
	public static String GetWindowText(HWND hwnd) throws NativeException, IllegalAccessException
	{
		JNative	nGetWindowText = new JNative(DLL_NAME, "GetWindowTextA");
		Pointer nBufferGWT = new Pointer(MemoryBlockFactory.createMemoryBlock(512));
		try
		{
			nGetWindowText.setRetVal(Type.INT);			
			nGetWindowText.setParameter(0, hwnd.getValue());
			nGetWindowText.setParameter(1, nBufferGWT);
			nGetWindowText.setParameter(2, nBufferGWT.getSize());
			
			nGetWindowText.invoke();
			if ("0".equals(nGetWindowText.getRetVal()))
			{
				return "";
			}
			return nBufferGWT.getAsString();
		}
		finally
		{
			if(nBufferGWT != null)
			{
				nBufferGWT.dispose();
			}
		}
	}
	
	/*
	HANDLE LoadImage(
		HINSTANCE hinst,
		LPCTSTR lpszName,
		UINT uType,
		int cxDesired,
		int cyDesired,
		UINT fuLoad
	);
	 */
	public static HANDLE LoadImage(LONG hinst,
			String lpszName,
			int uType,
			int cxDesired,
			int cyDesired,
			int fuLoad) throws Exception
	{
		JNative LoadImage = new JNative(DLL_NAME, "LoadImageA");
		LoadImage.setRetVal(Type.INT);
		
		int i = 0;
		LoadImage.setParameter(i++, hinst.getValue());
		LoadImage.setParameter(i++, lpszName);
		LoadImage.setParameter(i++, uType);
		LoadImage.setParameter(i++, cxDesired);
		LoadImage.setParameter(i++, cyDesired);
		LoadImage.setParameter(i++, fuLoad);
		
		LoadImage.invoke();
		int ret = LoadImage.getRetValAsInt();
		LoadImage.dispose();
		return new HANDLE(ret);
	}
	
	/*
	HCURSOR SetCursor(
		HCURSOR hCursor
	);
	 */
	public static LONG SetCursor(LONG hCursor) throws NativeException, IllegalAccessException
	{
		JNative SetCursor = new JNative(DLL_NAME, "SetCursor");
		SetCursor.setRetVal(Type.INT);
		
		SetCursor.setParameter(0, hCursor.getValue());
		SetCursor.invoke();
		int ret = SetCursor.getRetValAsInt();
		SetCursor.dispose();
		return new LONG(ret);
	}
	
	/*
	BOOL SetSystemCursor(
		HCURSOR hcur,
		DWORD id
	);
	 */
	public static boolean SetSystemCursor(LONG hcur, DWORD id) throws NativeException, IllegalAccessException
	{
		JNative SetSystemCursor = new JNative(DLL_NAME, "SetSystemCursor");
		SetSystemCursor.setRetVal(Type.INT);
		
		SetSystemCursor.setParameter(0, hcur.getValue());
		SetSystemCursor.setParameter(1, id.getValue());
		SetSystemCursor.invoke();
		int ret = SetSystemCursor.getRetValAsInt();
		SetSystemCursor.dispose();
		return (ret != 0);
	}
	
	/*
	 DWORD GetClassLong(
		HWND hWnd,
		int nIndex
	);
	 */
	public static DWORD GetClassLong(HWND hwnd, int nIndex) throws NativeException, IllegalAccessException
	{
		JNative GetClassLong = new JNative(DLL_NAME, "GetClassLongA");
		GetClassLong.setRetVal(Type.INT);
		
		GetClassLong.setParameter(0, hwnd.getValue());
		GetClassLong.setParameter(1, nIndex);
		GetClassLong.invoke();
		int ret = GetClassLong.getRetValAsInt();
		return new DWORD(ret);
	}
	
	/*
	DWORD SetClassLong(
		HWND hWnd,
		int nIndex,
		LONG dwNewLong
	);
	 */
	public static DWORD SetClassLong(HWND hwnd, int nIndex, LONG dwNewLong) throws NativeException, IllegalAccessException
	{
		JNative SetClassLong = new JNative(DLL_NAME, "SetClassLongA");
		SetClassLong.setRetVal(Type.INT);
		
		int i = 0;
		SetClassLong.setParameter(i++, hwnd.getValue());
		SetClassLong.setParameter(i++, nIndex);
		SetClassLong.setParameter(i++, dwNewLong.getValue());
		SetClassLong.invoke();
		
		return new DWORD(SetClassLong.getRetValAsInt());
	}
	
	/*
	HCURSOR LoadCursorFromFile(
		LPCTSTR lpFileName
	);
	 */
	public static LONG LoadCursorFromFile(String lpFileName) throws NativeException, IllegalAccessException
	{
		JNative LoadCursorFromFile = new JNative(DLL_NAME, "LoadCursorFromFileA");
		LoadCursorFromFile.setRetVal(Type.INT);
		LoadCursorFromFile.setParameter(0,lpFileName);
		LoadCursorFromFile.invoke();
		int ret = LoadCursorFromFile.getRetValAsInt();
		LoadCursorFromFile.dispose();
		return new LONG(ret);
	}
	
	
	/**
	 * LONG SetWindowLong(
	 *  HWND hWnd,
	 *  int nIndex,
	 *  LONG dwNewLong
	 * );
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	public static int SetWindowLong(HWND hwnd, int nIndex, LONG dwNewLong) throws NativeException, IllegalAccessException
	{
		if(hwnd == null)
		{
			return 0;
		}
		
		JNative _setWindowLong = new JNative(DLL_NAME, "SetWindowLongA");
		_setWindowLong.setRetVal(Type.INT);
		
		_setWindowLong.setParameter(0, hwnd.getValue());
		_setWindowLong.setParameter(1, nIndex);
		_setWindowLong.setParameter(2, dwNewLong.getValue());
		_setWindowLong.invoke();
		int ret = _setWindowLong.getRetValAsInt();
		_setWindowLong.dispose();
		return ret;
	}
	
	/*
	BOOL UnhookWindowsHookEx(
		HHOOK hhk
	);
	 */
	public static boolean UnhookWindowsHookEx(int hHook) throws NativeException, IllegalAccessException
	{
		JNative UnhookWindowsHookEx = new JNative(DLL_NAME, "UnhookWindowsHookEx");
		UnhookWindowsHookEx.setRetVal(Type.INT);
		
		UnhookWindowsHookEx.setParameter(0, hHook);
		UnhookWindowsHookEx.invoke();
		int ret = UnhookWindowsHookEx.getRetValAsInt();
		UnhookWindowsHookEx.dispose();
		return (ret != 0);
	}
	/*
	HHOOK SetWindowsHookEx(
		int idHook,
		HOOKPROC lpfn,
		HINSTANCE hMod,
		DWORD dwThreadId
	);
	 */
	public static int SetWindowsHookEx(int idHook, int lpfn, int hMod, DWORD dwThreadId) throws NativeException, IllegalAccessException
	{
		JNative SetWindowsHookEx = new JNative(DLL_NAME, "SetWindowsHookExA");
		SetWindowsHookEx.setRetVal(Type.INT);
		
		int pos = 0;
		SetWindowsHookEx.setParameter(pos++, idHook);
		SetWindowsHookEx.setParameter(pos++, lpfn);
		SetWindowsHookEx.setParameter(pos++, hMod);
		SetWindowsHookEx.setParameter(pos++, dwThreadId.getValue());
		SetWindowsHookEx.invoke();
		pos = SetWindowsHookEx.getRetValAsInt();
		SetWindowsHookEx.dispose();
		return pos;
	}
	/*
	LRESULT CallNextHookEx(
		HHOOK hhk,
		int nCode,
		WPARAM wParam,
		LPARAM lParam
	);
	 */
	public static int CallNextHookEx(int hhk, int nCode, int wParam, int lParam) throws NativeException, IllegalAccessException
	{
		JNative CallNextHookEx = new JNative(DLL_NAME, "CallNextHookEx");
		CallNextHookEx.setRetVal(Type.INT);
		
		int pos = 0;
		CallNextHookEx.setParameter(pos++, hhk);
		CallNextHookEx.setParameter(pos++, nCode);
		CallNextHookEx.setParameter(pos++, wParam);
		CallNextHookEx.setParameter(pos++, lParam);
		CallNextHookEx.invoke();
		pos = CallNextHookEx.getRetValAsInt();
		CallNextHookEx.dispose();
		return pos;
	}
	
	
	/**
	 * <pre>
	 * RegisterClass Function
	 
	 The RegisterClass function registers a window class for subsequent use in calls to the CreateWindow or CreateWindowEx function.
	 
	 The RegisterClass function has been superseded by the RegisterClassEx function. You can still use RegisterClass, however, if you do not need to set the class small icon.
	 
	 Syntax
	 
	 ATOM RegisterClass(
	 
	 public static final int WNDCLASS *lpWndClass
	 );
	 
	 Parameters
	 
	 lpWndClass
	 [in] Pointer to a WNDCLASS structure. You must fill the structure with the appropriate class attributes before passing it to the function.
	 
	 Return Value
	 
	 If the function succeeds, the return value is a class atom that uniquely identifies the class being registered. This atom can only be used by the CreateWindow, CreateWindowEx, GetClassInfo, GetClassInfoEx, FindWindow, FindWindowEx, and UnregisterClass functions and the IActiveIMMap::FilterClientWindows method.
	 
	 If the function fails, the return value is zero. To get extended error information, call GetLastError.
	 * </pre>
	 * @return
	 * @throws IllegalAccessException
	 * @throws NativeException
	 */
	public static LONG RegisterClass(WNDCLASS lpWndClass) throws NativeException, IllegalAccessException
	{
		JNative registerClass = new JNative(DLL_NAME, "RegisterClassA");
		registerClass.setRetVal(Type.INT);
		
		registerClass.setParameter(0, lpWndClass.createPointer());
		registerClass.invoke();
		int i = registerClass.getRetValAsInt();
		registerClass.dispose();
		return new LONG(i);
	}
	
	public static LONG RegisterClassEx(WNDCLASSEX lpWndClass) throws NativeException, IllegalAccessException
	{
		JNative RegisterClassEx = new JNative(DLL_NAME, "RegisterClassExA");
		RegisterClassEx.setRetVal(Type.INT);
		
		RegisterClassEx.setParameter(0, lpWndClass.createPointer());
		RegisterClassEx.invoke();
		int i = RegisterClassEx.getRetValAsInt();
		RegisterClassEx.dispose();
		return new LONG(i);
	}
	
	/*
	int GetClassName(
		HWND hWnd,
		LPTSTR lpClassName,
		int nMaxCount
	);
	 */
	public static int GetClassName(HWND hWnd, Pointer lpClassName, int nMaxCount) throws NativeException, IllegalAccessException
	{
		JNative GetClassName = new JNative(DLL_NAME, "GetClassNameA");
		GetClassName.setRetVal(Type.INT);
		
		int i = 0;
		GetClassName.setParameter(i++, hWnd.getValue());
		GetClassName.setParameter(i++, lpClassName);
		GetClassName.setParameter(i++, nMaxCount);
		GetClassName.invoke();
		i = GetClassName.getRetValAsInt();
		GetClassName.dispose();
		return i;
	}
	
	/*
	BOOL GetClassInfo(
		HINSTANCE hInstance,
		LPCTSTR lpClassName,
		LPWNDCLASS lpWndClass
	);
	 */
	public static boolean GetClassInfo(int hInstance, String lpClassName, WNDCLASS lpWndClass) throws NativeException, IllegalAccessException
	{
		JNative GetClassInfo = new JNative(DLL_NAME, "GetClassInfoA");
		GetClassInfo.setRetVal(Type.INT);
		
		int i = 0;
		GetClassInfo.setParameter(i++, hInstance);
		GetClassInfo.setParameter(i++, lpClassName);
		GetClassInfo.setParameter(i++, lpWndClass.createPointer());
		GetClassInfo.invoke();
		i = GetClassInfo.getRetValAsInt();
		GetClassInfo.dispose();
		return (i != 0);
	}
	
	public static boolean GetClassInfoEx(int hInstance, String lpClassName, WNDCLASSEX lpWndClass) throws NativeException, IllegalAccessException
	{
		JNative GetClassInfo = new JNative(DLL_NAME, "GetClassInfoExA");
		GetClassInfo.setRetVal(Type.INT);
		
		int i = 0;
		GetClassInfo.setParameter(i++, hInstance);
		GetClassInfo.setParameter(i++, lpClassName);
		GetClassInfo.setParameter(i++, lpWndClass.createPointer());
		GetClassInfo.invoke();
		i = GetClassInfo.getRetValAsInt();
		GetClassInfo.dispose();
		return (i != 0);
	}
	
	
	/**
	 * <pre>
	 HICON LoadIcon(
	 
	 HINSTANCE hInstance,
	 LPCTSTR lpIconName
	 );
	 
	 Parameters
	 
	 hInstance
	 [in] Handle to an instance of the module whose executable file contains the icon to be loaded. This parameter must be NULL when a standard icon is being loaded.
	 lpIconName
	 [in]
	 
	 Pointer to a null-terminated string that contains the name of the icon resource to be loaded. Alternatively, this parameter can contain the resource identifier in the low-order word and zero in the high-order word. Use the MAKEINTRESOURCE macro to create this value.
	 
	 To use one of the predefined icons, set the hInstance parameter to NULL and the lpIconName parameter to one of the following values.
	 
	 IDI_APPLICATION
	 Default application icon.
	 IDI_ASTERISK
	 Same as IDI_INFORMATION.
	 IDI_ERROR
	 Hand-shaped icon.
	 IDI_EXCLAMATION
	 Same as IDI_WARNING.
	 IDI_HAND
	 Same as IDI_ERROR.
	 IDI_INFORMATION
	 Asterisk icon.
	 IDI_QUESTION
	 Question mark icon.
	 IDI_WARNING
	 Exclamation POINT icon.
	 IDI_WINLOGO
	 Windows logo icon. Windows XP: Default application icon.
	 IDI_SHIELD
	 Security Shield icon.
	 
	 Return Value
	 
	 If the function succeeds, the return value is a handle to the newly loaded icon.
	 
	 If the function fails, the return value is NULL. To get extended error information, call GetLastError.
	 
	 </pre>
	 * @throws IllegalAccessException
	 * @throws NativeException
	 */
	
	public static LONG LoadIcon(LONG hInstance, String lpIconName) throws NativeException, IllegalAccessException
	{
		JNative loadIcon = new JNative(DLL_NAME, "LoadIconA");
		loadIcon.setRetVal(Type.INT);
		
		loadIcon.setParameter(0, hInstance.getValue());
		loadIcon.setParameter(1, lpIconName);
		loadIcon.invoke();
		int ret = loadIcon.getRetValAsInt();
		loadIcon.dispose();
		return new LONG(ret);
	}
	
	public static LONG LoadIcon(LONG hInstance, int ressource) throws NativeException, IllegalAccessException
	{
		JNative loadIcon = new JNative(DLL_NAME, "LoadIconA");
		loadIcon.setRetVal(Type.INT);
		
		loadIcon.setParameter(0, hInstance.getValue());
		loadIcon.setParameter(1, ressource);
		loadIcon.invoke();
		int ret = loadIcon.getRetValAsInt();
		loadIcon.dispose();
		
		return new LONG(ret);
	}
	/**
	 * <pre>
	 * SendMessage Function
	 
	 The SendMessage function sends the specified message to a window or windows. It calls the window procedure for the specified window and does not return until the window procedure has processed the message.
	 
	 To send a message and return immediately, use the SendMessageCallback or SendNotifyMessage function. To post a message to a thread's message queue and return immediately, use the PostMessage or PostThreadMessage function.
	 
	 Syntax
	 
	 LRESULT SendMessage(
	 
	 HWND hWnd,
	 UINT Msg,
	 WPARAM wParam,
	 LPARAM lParam
	 );
	 
	 Parameters
	 
	 hWnd
	 [in] Handle to the window whose window procedure will receive the message. If this parameter is HWND_BROADCAST, the message is sent to all top-level windows in the system, including disabled or invisible unowned windows, overlapped windows, and pop-up windows; but the message is not sent to child windows.
	 Msg
	 [in] Specifies the message to be sent.
	 wParam
	 [in] Specifies additional message-specific information.
	 lParam
	 [in] Specifies additional message-specific information.
	 
	 Return Value
	 
	 The return value specifies the result of the message processing; it depends on the message sent.
	 </pre>
	 * @throws IllegalAccessException
	 * @throws NativeException
	 */
	public static LRESULT SendMessage(HWND hWnd, UINT Msg, WPARAM wParam, LPARAM lParam) throws NativeException, IllegalAccessException
	{
		JNative SendMessage = new JNative(DLL_NAME, "SendMessageA");
		SendMessage.setRetVal(Type.INT);
		int pos = 0;
		SendMessage.setParameter(pos++, hWnd.getValue());
		SendMessage.setParameter(pos++, Msg.getValue());
		SendMessage.setParameter(pos++, wParam.getValue());
		SendMessage.setParameter(pos++, lParam.getValue());
		SendMessage.invoke();
		pos = SendMessage.getRetValAsInt();
		SendMessage.dispose();
		return new LRESULT(pos);
	}
	public static boolean PostMessage(HWND hWnd, UINT Msg, WPARAM wParam, LPARAM lParam) throws NativeException, IllegalAccessException
	{
		JNative SendMessage = new JNative(DLL_NAME, "PostMessageA");
		SendMessage.setRetVal(Type.INT);
		int pos = 0;
		SendMessage.setParameter(pos++, hWnd.getValue());
		SendMessage.setParameter(pos++, Msg.getValue());
		SendMessage.setParameter(pos++, wParam.getValue());
		SendMessage.setParameter(pos++, lParam.getValue());
		SendMessage.invoke();
		pos = SendMessage.getRetValAsInt();
		SendMessage.dispose();
		return (pos != 0);
	}
	/*
		HINSTANCE hInstance,
		int nWidth,
		int nHeight,
		BYTE cPlanes,
		BYTE cBitsPixel,
		public static final int BYTE *lpbANDbits,
		public static final int BYTE *lpbXORbits
	 */
	public static LONG CreateIcon(LONG hInstance, int nWidth, int nHeight, int cPlanes,
			int cBitsPixel, int[] lpbANDbits, int[] lpbXORbits)
			throws NativeException, IllegalAccessException
	{
		
		JNative createIcon = new JNative(DLL_NAME, "CreateIcon");
		Pointer p = new Pointer(MemoryBlockFactory.createMemoryBlock(lpbXORbits.length*4));
		for(int i = 0; i<lpbXORbits.length; i++)
		{
			p.setIntAt(i*4,lpbXORbits[i]);
		}
		createIcon.setRetVal(Type.INT);
		
		int i = 0;
		createIcon.setParameter(i++, hInstance.getValue());
		createIcon.setParameter(i++, nWidth);
		createIcon.setParameter(i++, nHeight);
		createIcon.setParameter(i++, cPlanes);
		createIcon.setParameter(i++, cBitsPixel);
		createIcon.setParameter(i++, p);
		createIcon.setParameter(i++, p);
		
		createIcon.invoke();
		i = createIcon.getRetValAsInt();
		createIcon.dispose();
		
		return new LONG(i);
	}
	/*
	LONG GetWindowLong(
		HWND hWnd,
		int nIndex
	);
	 */
	public static LONG GetWindowLong(HWND hWnd,
			int nIndex) throws NativeException, IllegalAccessException
	{
		
		JNative GetWindowLong = new JNative(DLL_NAME, "GetWindowLongA");
		GetWindowLong.setRetVal(Type.INT);
		int pos = 0;
		GetWindowLong.setParameter(pos++, hWnd.getValue());
		GetWindowLong.setParameter(pos++, nIndex);
		GetWindowLong.invoke();
		pos = GetWindowLong.getRetValAsInt();
		GetWindowLong.dispose();
		return new LONG(pos);
	}
	
	/*
	BOOL WINAPI AttachThreadInput(
		DWORD idAttach,
		DWORD idAttachTo,
		BOOL fAttach
	);
	 */
	public static boolean AttachThreadInput(int idAttach,
			int idAttachTo,
			boolean fAttach)
			throws NativeException, IllegalAccessException
	{
		JNative AttachThreadInput = new JNative(DLL_NAME, "AttachThreadInput");
		AttachThreadInput.setRetVal(Type.INT);
		int pos = 0;
		AttachThreadInput.setParameter(pos++, idAttach);
		AttachThreadInput.setParameter(pos++, idAttachTo);
		AttachThreadInput.setParameter(pos++, fAttach ? "1" : "0");
		AttachThreadInput.invoke();
		pos = AttachThreadInput.getRetValAsInt();
		
		AttachThreadInput.dispose();
		if(pos == 0)
			return false;
		return true;
	}
	/*
	 *HWND GetForegroundWindow(VOID);
	 */
	public static HWND GetForegroundWindow() throws NativeException, IllegalAccessException
	{
		JNative GetForegroundWindow = new JNative(DLL_NAME, "GetForegroundWindow");
		GetForegroundWindow.setRetVal(Type.INT);
		
		GetForegroundWindow.invoke();
		int ret = GetForegroundWindow.getRetValAsInt();
		GetForegroundWindow.dispose();
		return new HWND(ret);
	}
	
	/*
	 HWND GetActiveWindow(VOID);
	 */
	public static HWND GetActiveWindow() throws NativeException, IllegalAccessException
	{
		JNative GetActiveWindow = new JNative(DLL_NAME, "GetActiveWindow");
		GetActiveWindow.setRetVal(Type.INT);
		
		GetActiveWindow.invoke();
		int ret = GetActiveWindow.getRetValAsInt();
		GetActiveWindow.dispose();
		return new HWND(ret);
	}
	
	/*
	 *HWND SetActiveWindow(
		HWND hWnd
	);
	 */
	public static HWND SetActiveWindow(HWND hWnd) throws NativeException, IllegalAccessException
	{
		JNative SetActiveWindow = new JNative(DLL_NAME, "SetActiveWindow");
		SetActiveWindow.setRetVal(Type.INT);
		int pos = 0;
		SetActiveWindow.setParameter(pos++, hWnd.getValue());
		SetActiveWindow.invoke();
		pos = SetActiveWindow.getRetValAsInt();
		
		SetActiveWindow.dispose();
		return new HWND(pos);
	}
	
	/*
	 BOOL SetForegroundWindow(
		   HWND hWnd
		);
	 */
	public static boolean SetForegroundWindow(HWND hWnd) throws NativeException, IllegalAccessException
	{
		JNative SetForegroundWindow = new JNative(DLL_NAME, "SetForegroundWindow");
		SetForegroundWindow.setRetVal(Type.INT);
		int pos = 0;
		SetForegroundWindow.setParameter(pos++, hWnd.getValue());
		SetForegroundWindow.invoke();
		pos = SetForegroundWindow.getRetValAsInt();
		
		SetForegroundWindow.dispose();
		if(pos == 0)
			return false;
		return true;
	}
	
	 /*
	BOOL DestroyIcon(
		HICON hIcon
	);
	  */
	
	public static boolean DestroyIcon(LONG hIcon) throws NativeException, IllegalAccessException
	{
		JNative DestroyIcon = new JNative(DLL_NAME, "DestroyIcon");
		DestroyIcon.setRetVal(Type.INT);
		int pos = 0;
		DestroyIcon.setParameter(0, hIcon.getValue());
		DestroyIcon.invoke();
		pos = DestroyIcon.getRetValAsInt();
		
		DestroyIcon.dispose();
		
		return (pos != 0);
	}
	
	 /*
	 UINT RegisterWindowMessage(
		LPCTSTR lpString
	);
	  **/
	public static int RegisterWindowMessage(String lpString) throws NativeException, IllegalAccessException
	{
		JNative RegisterWindowMessage = new JNative(DLL_NAME, "RegisterWindowMessageA");
		RegisterWindowMessage.setRetVal(Type.INT);
		int pos = 0;
		RegisterWindowMessage.setParameter(pos++, lpString);
		RegisterWindowMessage.invoke();
		pos = RegisterWindowMessage.getRetValAsInt();
		
		RegisterWindowMessage.dispose();
		
		return pos;
	}
	
	/*
	 *BOOL SetWindowPos(
		HWND hWnd,
		HWND hWndInsertAfter,
		int X,
		int Y,
		int cx,
		int cy,
		UINT uFlags
	);
	 **/
	public static boolean SetWindowPos(HWND hWnd,
			HWND hWndInsertAfter,
			int X,
			int Y,
			int cx,
			int cy,
			int uFlags)
			throws NativeException, IllegalAccessException
	{
		JNative SetWindowPos = new JNative(DLL_NAME, "SetWindowPos");
		SetWindowPos.setRetVal(Type.INT);
		int pos = 0;
		SetWindowPos.setParameter(pos++, hWnd.getValue());
		SetWindowPos.setParameter(pos++, hWndInsertAfter.getValue());
		SetWindowPos.setParameter(pos++, X);
		SetWindowPos.setParameter(pos++, Y);
		SetWindowPos.setParameter(pos++, cx);
		SetWindowPos.setParameter(pos++, cy);
		SetWindowPos.setParameter(pos++, uFlags);
		SetWindowPos.invoke();
		pos = SetWindowPos.getRetValAsInt();
		
		SetWindowPos.dispose();
		if(pos == 0)
			return false;
		return true;
	}
	
	
	/*
	 BOOL RegisterHotKey(
		HWND hWnd,
		int id,
		UINT fsModifiers,
		UINT vk
	);
	 */
	public static boolean RegisterHotKey(HWND hWnd,
			int id,
			int fsModifiers,
			int vk)
			throws NativeException, IllegalAccessException
	{
		JNative RegisterHotKey = new JNative(DLL_NAME, "RegisterHotKey");
		RegisterHotKey.setRetVal(Type.INT);
		int pos = 0;
		RegisterHotKey.setParameter(pos++, hWnd.getValue());
		RegisterHotKey.setParameter(pos++, id);
		RegisterHotKey.setParameter(pos++, fsModifiers);
		RegisterHotKey.setParameter(pos++, vk);
		
		RegisterHotKey.invoke();
		
		pos = RegisterHotKey.getRetValAsInt();
		
		RegisterHotKey.dispose();
		
		return (pos != 0);
	}
	
	/*
	BOOL UnregisterHotKey(
		HWND hWnd,
		int id
	);
	 */
	public static boolean UnregisterHotKey(HWND hWnd, int id) throws NativeException, IllegalAccessException
	{
		JNative UnregisterHotKey = new JNative(DLL_NAME, "UnregisterHotKey");
		UnregisterHotKey.setRetVal(Type.INT);
		int pos = 0;
		UnregisterHotKey.setParameter(pos++, hWnd.getValue());
		UnregisterHotKey.setParameter(pos++, id);
		UnregisterHotKey.invoke();
		pos = UnregisterHotKey.getRetValAsInt();
		
		UnregisterHotKey.dispose();
		
		return (pos != 0);
	}
	/*
	BOOL GetWindowRect(
		HWND hWnd,
		LPRECT lpRect
	);
	 */
	public static boolean GetWindowRect(HWND hwnd, LRECT rect) throws NativeException, IllegalAccessException
	{
		JNative GetWindowRect = new JNative(DLL_NAME, "GetWindowRect");
		GetWindowRect.setRetVal(Type.INT);
		int pos = 0;
		GetWindowRect.setParameter(pos++, hwnd.getValue());
		GetWindowRect.setParameter(pos++, rect.getPointer());
		GetWindowRect.invoke();
		pos = GetWindowRect.getRetValAsInt();
		
		GetWindowRect.dispose();
		return (pos != 0);
	}
	
	public static final int DEVICE_NOTIFY_WINDOW_HANDLE = 0;
	public static final int DEVICE_NOTIFY_SERVICE_HANDLE = 1;
	public static final int DEVICE_NOTIFY_ALL_INTERFACE_CLASSES = 4;
	
	
	/**
	 * Registers the device or type of device for which a window will receive notifications.
	 *
	 * @param hRecipient :  A handle to the window or service that will receive device events for the devices specified in the NotificationFilter parameter. The same window handle can be used in multiple calls to RegisterDeviceNotification. <br> Services can specify either a window handle or service status handle.
	 * @param Flags : This parameter can be one of the following values : those constants are defined in User32 class<ul>
	 * <li><strong>DEVICE_NOTIFY_WINDOW_HANDLE (0x00000000)</strong> The <code>hRecipient</code> parameter is a window handle.</li>
	 * <li><strong>DEVICE_NOTIFY_SERVICE_HANDLE(0x00000001)</strong> The <code>hRecipient</code> parameter is a service status handle.</li>
	 * <li><strong>DEVICE_NOTIFY_ALL_INTERFACE_CLASSES (0x00000004)</strong> Notifies the recipient of device interface events for all device interface classes. (The dbcc_classguid member is ignored.) <p>This value can be used only if the dbch_devicetype member is DBT_DEVTYP_DEVICEINTERFACE.<br> Windows 2000:  This value is not supported.</p></li>
	 * </ul>
	 * @throws NativeException
	 * @throws IllegalAccessException
	 * @since Feb 5, 2008
	 */
	public static HANDLE RegisterDeviceNotification(HANDLE hRecipient, int Flags) throws NativeException, IllegalAccessException
	{
		JNative registerDeviceNotification = new JNative(DLL_NAME, "RegisterDeviceNotificationA");
		registerDeviceNotification.setRetVal(Type.INT);
		int pos = 0;
		
		DEV_BROADCAST_HANDLE dbh = new DEV_BROADCAST_HANDLE(DEV_BROADCAST_HANDLE.DBT_DEVTYP_DEVICEINTERFACE, GUID.GUID_IO_MEDIA_ARRIVAL);
		registerDeviceNotification.setParameter(pos++, hRecipient.getValue());
		registerDeviceNotification.setParameter(pos++, dbh.getPointer());
		registerDeviceNotification.setParameter(pos++, Flags);
		registerDeviceNotification.invoke();
		
		final int retValAsInt = registerDeviceNotification.getRetValAsInt();
		if(retValAsInt == 0)
		{
			JNative.getLogger().log("GetLastError "+Kernel32.GetLastError());
		}
		JNative.getLogger().log("return value : " + retValAsInt);
		return dbh.getDbch_hdevnotify();
	}
/*
	HCURSOR CreateCursor(
		HINSTANCE hInst,
		int xHotSpot,
		int yHotSpot,
		int nWidth,
		int nHeight,
		const VOID *pvANDPlane,
		const VOID *pvXORPlane
	);
 */
	public static int CreateCursor(HANDLE hInst,
			int xHotSpot,
			int yHotSpot,
			int nWidth,
			int nHeight,
			Pointer pvANDPlane,
			Pointer pvXORPlane) throws NativeException, IllegalAccessException
	{
		JNative CreateCursor = new JNative(DLL_NAME, "CreateCursor");
		CreateCursor.setRetVal(Type.INT);
		int pos = 0;
		CreateCursor.setParameter(pos++, hInst.getValue());
		CreateCursor.setParameter(pos++, xHotSpot);
		CreateCursor.setParameter(pos++, yHotSpot);
		CreateCursor.setParameter(pos++, nWidth);
		CreateCursor.setParameter(pos++, nHeight);
		CreateCursor.setParameter(pos++, pvANDPlane.getPointer());
		CreateCursor.setParameter(pos++, pvXORPlane.getPointer());
		
		CreateCursor.invoke();
		return CreateCursor.getRetValAsInt();
	}
	/*
	 BOOL DestroyCursor(
		HCURSOR hCursor
	);
	 */
	public static boolean DestroyCursor(int hCursor) throws NativeException, IllegalAccessException
	{
		JNative DestroyCursor = new JNative(DLL_NAME, "DestroyCursor");
		DestroyCursor.setRetVal(Type.INT);
		
		int pos = 0;
		DestroyCursor.setParameter(pos++, hCursor);
		
		DestroyCursor.invoke();
		pos = DestroyCursor.getRetValAsInt();
		
		return (pos != 0);
	}
	
	public static HWND FindWindowEx(HWND hwndParent, HWND hwndChildAfter,
			String className, String windowName) throws NativeException,
			IllegalAccessException
	{
		JNative FindWindowEx = new JNative(DLL_NAME, "FindWindowExA");
		FindWindowEx.setRetVal(Type.INT);
		FindWindowEx.setParameter(0, hwndParent.getValue());
		FindWindowEx.setParameter(1, hwndChildAfter.getValue());
		FindWindowEx.setParameter(2, className);
		FindWindowEx.setParameter(3, windowName);
		
		FindWindowEx.invoke();
		
		return new HWND(FindWindowEx.getRetValAsInt());
		
	}
	public static boolean EnumChildWindows(HWND hWndParent,
			Callback lpEnumFunc, int lParam) throws NativeException,
			IllegalAccessException
	{
		JNative nEnumWindows = new JNative(DLL_NAME, "EnumChildWindows", false);
		nEnumWindows.setRetVal(Type.INT);
		
		nEnumWindows.setParameter(0, hWndParent.getValue());
		nEnumWindows.setParameter(1, lpEnumFunc.getCallbackAddress());
		nEnumWindows.setParameter(2, lParam);
		nEnumWindows.invoke();
		return !"0".equals(nEnumWindows.getRetVal());
	}
	
	
	
}
