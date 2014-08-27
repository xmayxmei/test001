/*
 * EVENT.java
 *
 * Created on 22. September 2008, 09:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.util.constants.winuser;

/**
 *
 * @author Administrator
 */
public class EVENT
{
	public static final int EVENT_OBJECT_ACCELERATORCHANGE = 0x8012;
	public static final int EVENT_OBJECT_CREATE = 0x8000;
	public static final int EVENT_OBJECT_DEFACTIONCHANGE = 0x8011;
	public static final int EVENT_OBJECT_DESCRIPTIONCHANGE = 0x800D;
	public static final int EVENT_OBJECT_DESTROY = 0x8001;
	public static final int EVENT_OBJECT_FOCUS = 0x8005;
	public static final int EVENT_OBJECT_HELPCHANGE = 0x8010;
	public static final int EVENT_OBJECT_HIDE = 0x8003;
	public static final int EVENT_OBJECT_LOCATIONCHANGE = 0x800B;
	public static final int EVENT_OBJECT_NAMECHANGE = 0x800C;
	public static final int EVENT_OBJECT_PARENTCHANGE = 0x800F;
	public static final int EVENT_OBJECT_REORDER = 0x8004;
	public static final int EVENT_OBJECT_SELECTION = 0x8006;
	public static final int EVENT_OBJECT_SELECTIONADD = 0x8007;
	public static final int EVENT_OBJECT_SELECTIONREMOVE = 0x8008;
	public static final int EVENT_OBJECT_SELECTIONWITHIN = 0x8009;
	public static final int EVENT_OBJECT_SHOW = 0x8002;
	public static final int EVENT_OBJECT_STATECHANGE = 0x800A;
	public static final int EVENT_OBJECT_VALUECHANGE = 0x800E;
	public static final int EVENT_S_FIRST = 0x40200;
	public static final int EVENT_S_LAST = 0x4021F;
	public static final int EVENT_S_NOSUBSCRIBERS = (0x40202);
	public static final int EVENT_S_SOME_SUBSCRIBERS_FAILED = (0x40200);
	public static final int EVENT_SYSTEM_ALERT = 0x2;
	public static final int EVENT_SYSTEM_CAPTUREEND = 0x9;
	public static final int EVENT_SYSTEM_CAPTURESTART = 0x8;
	public static final int EVENT_SYSTEM_CONTEXTHELPEND = 0xD;
	public static final int EVENT_SYSTEM_CONTEXTHELPSTART = 0xC;
	public static final int EVENT_SYSTEM_DIALOGEND = 0x11;
	public static final int EVENT_SYSTEM_DIALOGSTART = 0x10;
	public static final int EVENT_SYSTEM_DRAGDROPEND = 0xF;
	public static final int EVENT_SYSTEM_DRAGDROPSTART = 0xE;
	public static final int EVENT_SYSTEM_FOREGROUND = 0x3;
	public static final int EVENT_SYSTEM_MENUEND = 0x5;
	public static final int EVENT_SYSTEM_MENUPOPUPEND = 0x7;
	public static final int EVENT_SYSTEM_MENUPOPUPSTART = 0x6;
	public static final int EVENT_SYSTEM_MENUSTART = 0x4;
	public static final int EVENT_SYSTEM_MINIMIZEEND = 0x17;
	public static final int EVENT_SYSTEM_MINIMIZESTART = 0x16;
	public static final int EVENT_SYSTEM_MOVESIZEEND = 0xB;
	public static final int EVENT_SYSTEM_MOVESIZESTART = 0xA;
	public static final int EVENT_SYSTEM_SCROLLINGEND = 0x13;
	public static final int EVENT_SYSTEM_SCROLLINGSTART = 0x12;
	public static final int EVENT_SYSTEM_SOUND = 0x1;
	public static final int EVENT_SYSTEM_SWITCHEND = 0x15;
	public static final int EVENT_SYSTEM_SWITCHSTART = 0x14;
	
	public static final int WINEVENT_OUTOFCONTEXT = 0x0000;
	public static final int WINEVENT_SKIPOWNTHREAD = 0x0001;
	public static final int WINEVENT_SKIPOWNPROCESS = 0x0002;
	public static final int WINEVENT_INCONTEXT = 0x0004;
}
