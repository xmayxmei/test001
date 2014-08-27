/**
 * 
 */
package com.cfwx.rox.businesssync.market.show;

import com.cfwx.util.U.ARR.DeepCopyable;

/**
 * <code>CommonLine</code> is for Monthly and Weekly K line.
 * 
 * @author Jimmy
 *
 * @since Quote (June 13, 2013)
 */
public class CommonLine extends DayLine implements DeepCopyable{
	private static final long serialVersionUID = 1L;
	
	private String fromDate;
	
	private String toDate;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		CommonLine oLine = new CommonLine();
		oLine.setClose(this.getClose());
		oLine.setFromDate(this.getFromDate());
		oLine.setAmount(this.getAmount());
		oLine.setHigh(this.getHigh());
		oLine.setLow(this.getLow());
		oLine.setTime(this.getTime());
		oLine.setToDate(this.getToDate());
		oLine.setOpen(this.getOpen());
		oLine.setVolume(this.getVolume());
		return oLine;
	}
}
