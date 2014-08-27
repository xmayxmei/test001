package org.xvolks.jnative.util.constants.winuser;

import org.xvolks.jnative.util.constants.Limits;


public enum WM
{
	
	/* WM_GETDLGCODE values */
	
	
	WM_NULL                 (0x0000),
	WM_CREATE               (0x0001),
	WM_DESTROY              (0x0002),
	WM_MOVE                 (0x0003),
	WM_SIZEWAIT             (0x0004),
	WM_SIZE                 (0x0005),
	WM_ACTIVATE             (0x0006),
	WM_SETFOCUS             (0x0007),
	WM_KILLFOCUS            (0x0008),
	WM_SETVISIBLE           (0x0009),
	WM_ENABLE               (0x000a),
	WM_SETREDRAW            (0x000b),
	WM_SETTEXT              (0x000c),
	WM_GETTEXT              (0x000d),
	WM_GETTEXTLENGTH        (0x000e),
	WM_PAINT                (0x000f),
	WM_CLOSE                (0x0010),
	WM_QUERYENDSESSION      (0x0011),
	WM_QUIT                 (0x0012),
	WM_QUERYOPEN            (0x0013),
	WM_ERASEBKGND           (0x0014),
	WM_SYSCOLORCHANGE       (0x0015),
	WM_ENDSESSION           (0x0016),
	WM_SYSTEMERROR          (0x0017),
	WM_SHOWWINDOW           (0x0018),
	WM_CTLCOLOR             (0x0019),
	WM_WININICHANGE         (0x001a),
	WM_SETTINGCHANGE        (WM_WININICHANGE.getValue()),
	WM_DEVMODECHANGE        (0x001b),
	WM_ACTIVATEAPP          (0x001c),
	WM_FONTCHANGE           (0x001d),
	WM_TIMECHANGE           (0x001e),
	WM_CANCELMODE           (0x001f),
	WM_SETCURSOR            (0x0020),
	WM_MOUSEACTIVATE        (0x0021),
	WM_CHILDACTIVATE        (0x0022),
	WM_QUEUESYNC            (0x0023),
	WM_GETMINMAXINFO        (0x0024),
	
	WM_PAINTICON            (0x0026),
	WM_ICONERASEBKGND       (0x0027),
	WM_NEXTDLGCTL           (0x0028),
	WM_ALTTABACTIVE         (0x0029),
	WM_SPOOLERSTATUS        (0x002a),
	WM_DRAWITEM             (0x002b),
	WM_MEASUREITEM          (0x002c),
	WM_DELETEITEM           (0x002d),
	WM_VKEYTOITEM           (0x002e),
	WM_CHARTOITEM           (0x002f),
	WM_SETFONT              (0x0030),
	WM_GETFONT              (0x0031),
	WM_SETHOTKEY            (0x0032),
	WM_GETHOTKEY            (0x0033),
	WM_FILESYSCHANGE        (0x0034),
	WM_ISACTIVEICON         (0x0035),
	WM_QUERYPARKICON        (0x0036),
	WM_QUERYDRAGICON        (0x0037),
	WM_QUERYSAVESTATE       (0x0038),
	WM_COMPAREITEM          (0x0039),
	WM_TESTING              (0x003a),
	
	WM_GETOBJECT            (0x003d),
	
	WM_ACTIVATESHELLWINDOW  (0x003e),
	
	WM_COMPACTING           (0x0041),
	
	WM_COMMNOTIFY           (0x0044),
	WM_WINDOWPOSCHANGING    (0x0046),
	WM_WINDOWPOSCHANGED     (0x0047),
	WM_POWER                (0x0048),
	
	/* Win32 4.0 messages */
	WM_COPYDATA             (0x004a),
	WM_CANCELJOURNAL        (0x004b),
	WM_NOTIFY               (0x004e),
	WM_INPUTLANGCHANGEREQUEST       (0x0050),
	WM_INPUTLANGCHANGE              (0x0051),
	WM_TCARD                (0x0052),
	WM_HELP                 (0x0053),
	WM_USERCHANGED          (0x0054),
	WM_NOTIFYFORMAT         (0x0055),
	
	WM_CONTEXTMENU          (0x007b),
	WM_STYLECHANGING        (0x007c),
	WM_STYLECHANGED         (0x007d),
	WM_DISPLAYCHANGE        (0x007e),
	WM_GETICON              (0x007f),
	WM_SETICON              (0x0080),
	
	/* Non-client system messages */
	WM_NCCREATE         (0x0081),
	WM_NCDESTROY        (0x0082),
	WM_NCCALCSIZE       (0x0083),
	WM_NCHITTEST        (0x0084),
	WM_NCPAINT          (0x0085),
	WM_NCACTIVATE       (0x0086),
	
	WM_GETDLGCODE       (0x0087),
	WM_SYNCPAINT        (0x0088),
	WM_SYNCTASK         (0x0089),
	
	/* Non-client mouse messages */
	WM_NCMOUSEMOVE      (0x00a0),
	WM_NCLBUTTONDOWN    (0x00a1),
	WM_NCLBUTTONUP      (0x00a2),
	WM_NCLBUTTONDBLCLK  (0x00a3),
	WM_NCRBUTTONDOWN    (0x00a4),
	WM_NCRBUTTONUP      (0x00a5),
	WM_NCRBUTTONDBLCLK  (0x00a6),
	WM_NCMBUTTONDOWN    (0x00a7),
	WM_NCMBUTTONUP      (0x00a8),
	WM_NCMBUTTONDBLCLK  (0x00a9),
	
	WM_NCXBUTTONDOWN    (0x00ab),
	WM_NCXBUTTONUP      (0x00ac),
	WM_NCXBUTTONDBLCLK  (0x00ad),
	
	/* Keyboard messages */
	WM_KEYDOWN          (0x0100),
	WM_KEYUP            (0x0101),
	WM_CHAR             (0x0102),
	WM_DEADCHAR         (0x0103),
	WM_SYSKEYDOWN       (0x0104),
	WM_SYSKEYUP         (0x0105),
	WM_SYSCHAR          (0x0106),
	WM_SYSDEADCHAR      (0x0107),
	WM_KEYFIRST         (WM_KEYDOWN.getValue()),
	WM_KEYLAST          (0x0108),
	
	/* Win32 4.0 messages for IME */
	WM_IME_STARTCOMPOSITION     (0x010d),
	WM_IME_ENDCOMPOSITION       (0x010e),
	WM_IME_COMPOSITION          (0x010f),
	WM_IME_KEYLAST              (0x010f),
	
	WM_INITDIALOG       (0x0110),
	WM_COMMAND          (0x0111),
	WM_SYSCOMMAND       (0x0112),
	WM_TIMER            (0x0113),
	WM_SYSTIMER         (0x0118),
	
	/* scroll messages */
	WM_HSCROLL          (0x0114),
	WM_VSCROLL          (0x0115),
	
	/* Menu messages */
	WM_INITMENU         (0x0116),
	WM_INITMENUPOPUP    (0x0117),
	
	WM_MENUSELECT       (0x011F),
	WM_MENUCHAR         (0x0120),
	WM_ENTERIDLE        (0x0121),
	
	WM_MENURBUTTONUP    (0x0122),
	WM_MENUDRAG         (0x0123),
	WM_MENUGETOBJECT    (0x0124),
	WM_UNINITMENUPOPUP  (0x0125),
	WM_MENUCOMMAND      (0x0126),
	
	WM_CHANGEUISTATE    (0x0127),
	WM_UPDATEUISTATE    (0x0128),
	WM_QUERYUISTATE     (0x0129),
	WM_USER				(1024),
	
	/* UI flags for WM_*UISTATE */
	/* for low-order word of wparam */
	UIS_SET                         (1),
	UIS_CLEAR                       (2),
	UIS_INITIALIZE                  (3),
	/* for hi-order word of wparam */
	UISF_HIDEFOCUS                  (0x1),
	UISF_HIDEACCEL                  (0x2),
	UISF_ACTIVE                     (0x4),
	
	WM_LBTRACKPOINT     (0x0131),
	
	/* Win32 CTLCOLOR messages */
	WM_CTLCOLORMSGBOX    (0x0132),
	WM_CTLCOLOREDIT      (0x0133),
	WM_CTLCOLORLISTBOX   (0x0134),
	WM_CTLCOLORBTN       (0x0135),
	WM_CTLCOLORDLG       (0x0136),
	WM_CTLCOLORSCROLLBAR(0x0137),
	WM_CTLCOLORSTATIC    (0x0138),
	
	MN_GETHMENU          (0x01E1),
	
	/* Mouse messages */
	WM_MOUSEMOVE        (0x0200),
	WM_LBUTTONDOWN      (0x0201),
	WM_LBUTTONUP        (0x0202),
	WM_LBUTTONDBLCLK    (0x0203),
	WM_RBUTTONDOWN      (0x0204),
	WM_RBUTTONUP        (0x0205),
	WM_RBUTTONDBLCLK    (0x0206),
	WM_MBUTTONDOWN      (0x0207),
	WM_MBUTTONUP        (0x0208),
	WM_MBUTTONDBLCLK    (0x0209),
	WM_MOUSEWHEEL       (0x020A),
	WM_XBUTTONDOWN      (0x020B),
	WM_XBUTTONUP        (0x020C),
	WM_XBUTTONDBLCLK    (0x020D),
	
	XBUTTON1            (0x0001),
	XBUTTON2            (0x0002),
	
	WM_MOUSEFIRST       (0x0200),
	WM_MOUSELAST        (0x020D),
	
	WHEEL_DELTA      	(120),
	WHEEL_PAGESCROLL  (Limits.UINT_MAX.getValue()),
	
	/** Non sense in Java*/
	//GET_WHEEL_DELTA_WPARAM(wParam)  ((short)HIWORD(wParam)),
	
	WM_PARENTNOTIFY     (0x0210),
	WM_ENTERMENULOOP    (0x0211),
	WM_EXITMENULOOP     (0x0212),
	WM_NEXTMENU         (0x0213),
	
	/* Win32 4.0 messages */
	WM_SIZING           (0x0214),
	WM_CAPTURECHANGED   (0x0215),
	WM_MOVING           (0x0216),
	WM_POWERBROADCAST   (0x0218),
	WM_DEVICECHANGE     (0x0219),
	
	WM_HOTKEY           (0x312)
	
	;
	private int mValue;
	public int getValue()
	{
		return mValue;
	}
	private WM(int lValue)
	{
		mValue = lValue;
	}
}
