/**
 * 
 */
package com.cfwx.rox.businesssync.market.show;

import java.io.Serializable;

/**
 * @author J.C.J
 *
 *  2013-11-18
 */
public class TimeShare implements Serializable{

	private static final long serialVersionUID = 8343027952863342522L;

	private String time; //	时间	time	long			yyyyMMddHHMMss
	private String newPrice; //最新价*放大倍数
	private double zl; //交易总量
	private String volume; //分钟交易量
	private String average; //均价*放大倍数,每分钟的均价，每分钟的总额/总量
	private String amount;
	private double ze; //总价
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(String newPrice) {
		this.newPrice = newPrice;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getAverage() {
		return average;
	}
	public void setAverage(String average) {
		this.average = average;
	}
	public double getZl() {
		return zl;
	}
	public void setZl(double zl) {
		this.zl = zl;
	}
	public double getZe() {
		return ze;
	}
	public void setZe(double ze) {
		this.ze = ze;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
