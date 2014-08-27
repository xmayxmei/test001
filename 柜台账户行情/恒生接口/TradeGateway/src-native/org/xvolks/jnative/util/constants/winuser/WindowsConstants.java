/*
 * WindowsConstants.java
 *
 * Created on 25. Juli 2008, 10:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.util.constants.winuser;

/**
 *
 * @author Administrator
 */
public class WindowsConstants
{
	/*
	 * ShowWindow() Commands
	 */

	public static final int SW_HIDE             = 0;
	public static final int SW_SHOWNORMAL       = 1;
	public static final int SW_NORMAL           = 1;
	public static final int SW_SHOWMINIMIZED    = 2;
	public static final int SW_SHOWMAXIMIZED    = 3;
	public static final int SW_MAXIMIZE         = 3;
	public static final int SW_SHOWNOACTIVATE   = 4;
	public static final int SW_SHOW             = 5;
	public static final int SW_MINIMIZE         = 6;
	public static final int SW_SHOWMINNOACTIVE  = 7;
	public static final int SW_SHOWNA           = 8;
	public static final int SW_RESTORE          = 9;
	public static final int SW_SHOWDEFAULT      = 10;
	public static final int SW_FORCEMINIMIZE    = 11;
	public static final int SW_MAX              = 11;
	
	/*
	 * Window Styles
	 */
	public static final int WS_OVERLAPPED       = 0x00000000;
	public static final int WS_POPUP            = 0x80000000;
	public static final int WS_CHILD            = 0x40000000;
	public static final int WS_MINIMIZE         = 0x20000000;
	public static final int WS_VISIBLE          = 0x10000000;
	public static final int WS_DISABLED         = 0x08000000;
	public static final int WS_CLIPSIBLINGS     = 0x04000000;
	public static final int WS_CLIPCHILDREN     = 0x02000000;
	public static final int WS_MAXIMIZE         = 0x01000000;
	public static final int WS_CAPTION          = 0x00C00000;     /* WS_BORDER | WS_DLGFRAME  */
	public static final int WS_BORDER           = 0x00800000;
	public static final int WS_DLGFRAME         = 0x00400000;
	public static final int WS_VSCROLL          = 0x00200000;
	public static final int WS_HSCROLL          = 0x00100000;
	public static final int WS_SYSMENU          = 0x00080000;
	public static final int WS_THICKFRAME       = 0x00040000;
	public static final int WS_GROUP            = 0x00020000;
	public static final int WS_TABSTOP          = 0x00010000;
	
	public static final int WS_MINIMIZEBOX      = 0x00020000;
	public static final int WS_MAXIMIZEBOX      = 0x00010000;
	
	
	public static final int WS_TILED            = WS_OVERLAPPED;
	public static final int WS_ICONIC           = WS_MINIMIZE;
	public static final int WS_SIZEBOX          = WS_THICKFRAME;
	
	// Erzeugt ein Fenster, das Drag&Drop-Dateien akzeptiert
	public static final int WS_EX_ACCEPTFILES = 0x10;
	
	// Das Fenster soll in der Taskleiste angezeigt werden
	public static final int WS_EX_APPWINDOW = 0x40000;
	
	// Der Rahmen des Fensters hat einen vertieften Rand
	public static final int WS_EX_CLIENTEDGE = 0x200;
	
	// Anzeige des Hilfesymbols (Fragezeichen) in der Titelleiste
	public static final int WS_EX_CONTEXTHELP = 0x400;
	
	// Ermöglicht dem Anwender, sich mit Hilfe der TAB-Tasten
	// durch die Kindfenster des Elternfensters zu bewegen
	public static final int WS_EX_CONTROLPARENT = 0x10000;
	
	// Erzeugt ein Fenster Doppelrahmen (Dialog-Style)
	public static final int WS_EX_DLGMODALFRAME = 0x1;
	
	// Erzeugt ein Fenster mit linksausgerichteten Eigenschaften
	public static final int WS_EX_LEFT = 0x0;
	
	// Zeigt eine evtl. vorhandene vertikale Scrollleiste
	// auf der linken Seite an
	public static final int WS_EX_LEFTSCROLLBAR = 0x4000;
	
	// Zeigt den Text von links nach rechts an (Standard)
	public static final int WS_EX_LTRREADING = 0x0;
	
	// Erzeugt ein MDI-Kindfenster
	public static final int WS_EX_MDICHILD = 0x40;
	
	// (Win 2000) Das Fenster kann durch den Anwener nicht
	// in den Vordergrund gebracht werden.
	public static final int WS_EX_NOACTIVATE = 0x8000000;
	
	// Das Elternfenster wird nicht über das Erstellen oder Zerstören
	// des Fenster benachrichtet
	public static final int WS_EX_NOPARENTNOTIFY = 0x4;
	
	// Das Fenster hat einen Ramen eines Standard-Fensters
	public static final int WS_EX_OVERLAPPEDWINDOW = 0x300;
	
	// Das Fenster hat den Stil "Werkzeugleiste" und wird
	// immer im Vordergrund angezeigt
	public static final int WS_EX_PALETTEWINDOW = 0x188;
	
	// Erzeugt ein Fenster mit rechtsausgerichteten Eigenschaften
	public static final int WS_EX_RIGHT = 0x1000;
	
	// Zeigt eine evtl. vorhandene vertikale Scrollbar
	// auf der rechten Seite an (Standard)
	public static final int WS_EX_RIGHTSCROLLBAR = 0x0;
	
	// Zeigt den Text nach Möglichkeit von rechts nach links an
	public static final int WS_EX_RTLREADING = 0x2000;
	
	// Erzeugt ein Fenster mit 3D-Ramen (wird verwendet,
	// wenn keine Benutzereingaben verlangt werden)
	public static final int WS_EX_STATICEDGE = 0x20000;
	
	// Erzeugt ein Fenster mit schmaler Titelleiste
	public static final int WS_EX_TOOLWINDOW = 0x80;
	
	// Das Fenster ist immer im Vordergrund
	public static final int WS_EX_TOPMOST = 0x8;
	
	// Erzeugt ein transparentes Fenster
	public static final int WS_EX_TRANSPARENT = 0x20;
	
	// Erzeugt ein Fenster mit einer gehobenen Kante
	public static final int WS_EX_WINDOWEDGE = 0x100;
	
	public static final int WS_EX_LAYERED = 0x80000;
	
	public static final int LWA_ALPHA           = 0x2;
	
	public final static int GWL_EXSTYLE = -20;
	public final static int GWL_STYLE = -16;
	public final static int GWL_WNDPROC = -4;
	public final static int GWLP_WNDPROC = -4;
	public final static int GWL_HINSTANCE = -6;
	public final static int GWLP_HINSTANCE = -6;
	public final static int GWL_HWNDPARENT = -8;
	public final static int GWLP_HWNDPARENT = -8;
	public final static int GWL_ID = -12;
	public final static int GWLP_ID = -12;
	public final static int GWL_USERDATA = -21;
	public final static int GWLP_USERDATA = -21;
	
	public final static int CW_USEDEFAULT = ((int)0x80000000);
	
		/*
		 * Common Window Styles
		 */
	public static final int WS_OVERLAPPEDWINDOW = (WS_OVERLAPPED  |
			WS_CAPTION        |
			WS_SYSMENU        |
			WS_THICKFRAME     |
			WS_MINIMIZEBOX    |
			WS_MAXIMIZEBOX);
	
	public static final int WS_POPUPWINDOW      = (WS_POPUP          |
			WS_BORDER         |
			WS_SYSMENU);
	
	public static final int WS_CHILDWINDOW      = (WS_CHILD);
	
	public static final int WS_TILEDWINDOW      = WS_OVERLAPPEDWINDOW;
	
	public static final int SC_SIZE = 0xF000;
	public static final int SC_MAXIMIZE = 0xF030;
	public static final int SC_MINIMIZE = 0xF020;
	public static final int SC_CLOSE = 0xF060;
	
}
