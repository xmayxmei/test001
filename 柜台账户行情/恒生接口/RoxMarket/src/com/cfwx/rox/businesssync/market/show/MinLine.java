/**
 * 
 */
package com.cfwx.rox.businesssync.market.show;


/**
 * <code>CommonLine</code> is for minute K line such as 5 min, 10 min.
 * 
 * @author Jimmy
 *
 * @since Quote (June 19, 2014)
 */
public class MinLine extends DayLine{
	private static final long serialVersionUID = 1L;
	
	private String date;
	private String dateTime;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
}
