/**
 * 
 */
package com.cfwx.rox.businesssync.market.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author J.C.J
 * 指数行情
 * 2013-11-18
 */
public class IndexMarket implements Serializable{

	private static final long serialVersionUID = 7422732565999456009L;
	
	private Date time;    //0.yyyyMMddHHMMss
	private int type;    //	1.		类别	Type	Int	1	
	private String code; //	2.		代码	Code	String	6	
	private String name; //	3.		指数名称	Name	String	4		上证指数
	private long close; //	4.		昨收指数	Close	long 		X1000	0-2147483点
	private long open;  //	5.		今开指数	Open	long		X1000	0-2147483点
	private long high; //	6.		最高指数	High	long		X1000	0-2147483点
	private long low;  //	7.		最低指数	Low	long		X1000	0-2147483点
	private long idxnew; //	8.		最新指数	Idxnew	long		X1000	0-2147483点
	private long volume; //	9.		总成交量	Volume	long			0-4294亿股
	private float amount; //10.		总成交金额	Amount	float			0—4294亿元
	private long vbuy; //	11.		总委买量	Vbuy	long			0—4294亿股
	private long vsell; //	12.		总委卖量	Vsell	long			0—4294亿股
	private int sec5; //	13.		加权指数	Sec5	Int		
	private int total; //	14.		总家数	Total	int		只	0-65535
	private int rise; //	15.		上涨家数	Rise	int	2	只	0-65535
	private int down; //	16.		下跌家数	Down	int	2	只	0-65535
	private float nowamt;//	17.		现额	Nowamt	Float	4	元	
	private long nowvol; //	18.		现量	Nowvol	long	4	百股	0—4294亿股
	private int property; //19.		属性	Properity	int	1		保留
	private int market;	//20.		交易市场	market	int			0:上海证券交易所 1:深圳证券交易所

	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getClose() {
		return close;
	}
	public void setClose(long close) {
		this.close = close;
	}
	public long getOpen() {
		return open;
	}
	public void setOpen(long open) {
		this.open = open;
	}
	public long getHigh() {
		return high;
	}
	public void setHigh(long high) {
		this.high = high;
	}
	public long getLow() {
		return low;
	}
	public void setLow(long low) {
		this.low = low;
	}
	public long getIdxnew() {
		return idxnew;
	}
	public void setIdxnew(long idxnew) {
		this.idxnew = idxnew;
	}
	public long getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public long getVbuy() {
		return vbuy;
	}
	public void setVbuy(long vbuy) {
		this.vbuy = vbuy;
	}
	public long getVsell() {
		return vsell;
	}
	public void setVsell(long vsell) {
		this.vsell = vsell;
	}
	public int getSec5() {
		return sec5;
	}
	public void setSec5(int sec5) {
		this.sec5 = sec5;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRise() {
		return rise;
	}
	public void setRise(int rise) {
		this.rise = rise;
	}
	public int getDown() {
		return down;
	}
	public void setDown(int down) {
		this.down = down;
	}
	public float getNowamt() {
		return nowamt;
	}
	public void setNowamt(float nowamt) {
		this.nowamt = nowamt;
	}
	public long getNowvol() {
		return nowvol;
	}
	public void setNowvol(long nowvol) {
		this.nowvol = nowvol;
	}
	public int getProperty() {
		return property;
	}
	public void setProperty(int property) {
		this.property = property;
	}
	public int getMarket() {
		return market;
	}
	public void setMarket(int market) {
		this.market = market;
	}

}
