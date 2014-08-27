package org.xvolks.jnative.util.constants.winuser;



public enum WH
{
	
	WH_CALLWNDPROC(4),
	WH_CBT(5),
	WH_DEBUG(9),
	WH_FOREGROUNDIDLE(11),
	WH_GETMESSAGE(3),
	WH_HARDWARE(8),
	WH_JOURNALPLAYBACK(1),
	WH_JOURNALRECORD(0),
	WH_KEYBOARD(2),
	WH_KEYBOARD_LL(13),
	WH_MOUSE(7),
	WH_MOUSE_LL(14),
	WH_MSGFILTER((-1)),
	WH_SHELL(10),
	WH_SYSMSGFILTER(6),
	WH_CALLWNDPROCRET(12)
	;
	private int mValue;
	public int getValue()
	{
		return mValue;
	}
	private WH(int lValue)
	{
		mValue = lValue;
	}
}
