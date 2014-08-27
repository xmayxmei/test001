/**
 * 
 */
package com.cfwx.rox.businesssync.market.show;

import java.io.Serializable;

/**
 * @author J.C.J
 * K线
 *         2013-11-18
 */
public class DayLine implements Serializable {

	private static final long serialVersionUID = -5461609363263131136L;

	private String time;// 1. 日期 date int yyyyMMdd
	private String open;// 2. 开盘 open long X1000
	private String close;// 3. 收盘 close long X1000
	private String volume;// 7. 交易量 百股
	private String high;// 8. 最高价 high long X1000
	private String low;// 9. 最低价 low long X1000
	private String amount;// 9. 成交金额 千元
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getAmount() {
		return "0";
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
