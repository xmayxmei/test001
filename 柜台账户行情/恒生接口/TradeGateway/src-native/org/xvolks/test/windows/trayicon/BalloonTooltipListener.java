/*
 * BalloonTooltipListener.java
 *
 * Created on 15. Mai 2007, 09:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.test.windows.trayicon;

/**
 *
 * @author osthop
 */
public interface BalloonTooltipListener
{
	void balloonHide(TrayIcon source);
	void balloonShow(TrayIcon source);
	void balloonClick(TrayIcon source);
	void balloonClose(TrayIcon source);
}
