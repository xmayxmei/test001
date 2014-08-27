package com.cfwx.dbf.hq.entity;

import java.io.Serializable;

/**
 * 深圳证券交易所股票行情信息
 * 
 * 每个交易日本库的第一条记录为特殊记录，HQZQDM 为“000000”，
		HQZQJC 存放当前日期CCYYMMDD，HQCJBS 存放当前时间HHMMSS，HQZRSP 存放“指
		数因子”，HQCJSL 存放行情状态信息。当本库的记录为指数记录时（HQZQDM 的
		最左两位为39），相应的字段HQZRSP、HQJRKP、HQZJCJ、HQZGCJ、HQZDCJ 等都必
		须乘上该“指数因子”，计算出的结果才为实际的指数值。HQCJSL 个位数存放收
		市行情标志（0：非收市行情；1：表示收市行情），HQCJSL 十位数存放正式行情
		与测试行情标志（0：正式行情；1：表示测试行情），即HQCJSL 值为0 时表示正
		式非收市行情，为1 时表示正式收市行情，为10 时表示测试的非收市行情；为
		11 时表示测试的收市行情，HQBSL4 存放当日最近一次发布信息公告的时间，格
		式为HHMMSSss，HQBSL5 存放SJSXXN.dbf 最近一次更新的时间，格式为HHMMSSss
 * @author J.C.J
 * 
 *         2013-9-17
 */
public class SZMarket implements Serializable {

	private static final long serialVersionUID = -2244077549113344428L;

	// 证券代码 0
	private String hqzqdm;
	// 证券名称1
	private String hqzqjc;
	// 昨日收盘价2
	private String hqzrsp;
	// 今日开盘价3
	private String hqjrkp;
	// 最近成交价4
	private String hqzjcj;
	// 成交数量5
	private String hqcjsl;
	// 成交金额6
	private String hqcjje;
	// 成交笔数7
	private String hqcjbs;
	// 最高成交价8
	private String hqzgcj;
	// 最低成交价9
	private String hqzdcj;
	// 市盈率1  10
	private String hqsyl1;
	// 市盈率2  11
	private String hqsyl2;
	// 价格升跌1  12
	private String hqjsd1;
	// 价格升跌2 13
	private String hqjsd2;
	// 合约持仓量  14
	private String hqhycc;
	// 卖价位五  15
	private String hqsjw5;
	// 卖数量五  16
	private String hqssl5;
	// 卖价位四  17
	private String hqsjw4;
	// 卖数量四  18
	private String hqssl4;
	// 卖价位三 19
	private String hqsjw3;
	// 卖数量三 20
	private String hqssl3;
	// 卖价位二 21
	private String hqsjw2;
	// 卖数量二 22
	private String hqssl2;
	// 卖价位一/叫卖揭示价 23
	private String hqsjw1;
	// 卖数量一 24
	private String hqssl1;
	// 买价位一/叫买揭示价
	private String hqbjw1;
	// 买数量一 25
	private String hqbsl1;
	// 买价位二 26
	private String hqbjw2;
	// 买数量二 27
	private String hqbsl2;
	// 买价位三 28
	private String hqbjw3;
	// 买数量三 29
	private String hqbsl3;
	// 买价位四 30
	private String hqbjw4;
	// 买数量四  31
	private String hqbsl4;
	// 买价位五  32
	private String hqbjw5;
	// 买数量五 33
	private String hqbsl5;
	//日期(yyyyMMdd)
	private String date;
	//时间(HHmmss)
	private String time;
	//类型(0:指数;1:股票)
	private int type;
	//股票组合代码(sh000001)
	private String paramCode;
	//1：正式行情，0：收市行情
	private String marketStatus;
	/**
	 * @return the hqzqdm
	 */
	public String getHqzqdm() {
		return hqzqdm;
	}
	/**
	 * @param hqzqdm the hqzqdm to set
	 */
	public void setHqzqdm(String hqzqdm) {
		this.hqzqdm = hqzqdm;
	}
	/**
	 * @return the hqzqjc
	 */
	public String getHqzqjc() {
		return hqzqjc;
	}
	/**
	 * @param hqzqjc the hqzqjc to set
	 */
	public void setHqzqjc(String hqzqjc) {
		this.hqzqjc = hqzqjc;
	}
	/**
	 * @return the hqzrsp
	 */
	public String getHqzrsp() {
		return hqzrsp;
	}
	/**
	 * @param hqzrsp the hqzrsp to set
	 */
	public void setHqzrsp(String hqzrsp) {
		this.hqzrsp = hqzrsp;
	}
	/**
	 * @return the hqjrkp
	 */
	public String getHqjrkp() {
		return hqjrkp;
	}
	/**
	 * @param hqjrkp the hqjrkp to set
	 */
	public void setHqjrkp(String hqjrkp) {
		this.hqjrkp = hqjrkp;
	}
	/**
	 * @return the hqzjcj
	 */
	public String getHqzjcj() {
		return hqzjcj;
	}
	/**
	 * @param hqzjcj the hqzjcj to set
	 */
	public void setHqzjcj(String hqzjcj) {
		this.hqzjcj = hqzjcj;
	}
	/**
	 * @return the hqcjsl
	 */
	public String getHqcjsl() {
		return hqcjsl;
	}
	/**
	 * @param hqcjsl the hqcjsl to set
	 */
	public void setHqcjsl(String hqcjsl) {
		this.hqcjsl = hqcjsl;
	}
	/**
	 * @return the hqcjje
	 */
	public String getHqcjje() {
		return hqcjje;
	}
	/**
	 * @param hqcjje the hqcjje to set
	 */
	public void setHqcjje(String hqcjje) {
		this.hqcjje = hqcjje;
	}
	/**
	 * @return the hqcjbs
	 */
	public String getHqcjbs() {
		return hqcjbs;
	}
	/**
	 * @param hqcjbs the hqcjbs to set
	 */
	public void setHqcjbs(String hqcjbs) {
		this.hqcjbs = hqcjbs;
	}
	/**
	 * @return the hqzgcj
	 */
	public String getHqzgcj() {
		return hqzgcj;
	}
	/**
	 * @param hqzgcj the hqzgcj to set
	 */
	public void setHqzgcj(String hqzgcj) {
		this.hqzgcj = hqzgcj;
	}
	/**
	 * @return the hqzdcj
	 */
	public String getHqzdcj() {
		return hqzdcj;
	}
	/**
	 * @param hqzdcj the hqzdcj to set
	 */
	public void setHqzdcj(String hqzdcj) {
		this.hqzdcj = hqzdcj;
	}
	/**
	 * @return the hqsyl1
	 */
	public String getHqsyl1() {
		return hqsyl1;
	}
	/**
	 * @param hqsyl1 the hqsyl1 to set
	 */
	public void setHqsyl1(String hqsyl1) {
		this.hqsyl1 = hqsyl1;
	}
	/**
	 * @return the hqsyl2
	 */
	public String getHqsyl2() {
		return hqsyl2;
	}
	/**
	 * @param hqsyl2 the hqsyl2 to set
	 */
	public void setHqsyl2(String hqsyl2) {
		this.hqsyl2 = hqsyl2;
	}
	/**
	 * @return the hqjsd1
	 */
	public String getHqjsd1() {
		return hqjsd1;
	}
	/**
	 * @param hqjsd1 the hqjsd1 to set
	 */
	public void setHqjsd1(String hqjsd1) {
		this.hqjsd1 = hqjsd1;
	}
	/**
	 * @return the hqjsd2
	 */
	public String getHqjsd2() {
		return hqjsd2;
	}
	/**
	 * @param hqjsd2 the hqjsd2 to set
	 */
	public void setHqjsd2(String hqjsd2) {
		this.hqjsd2 = hqjsd2;
	}
	/**
	 * @return the hqhycc
	 */
	public String getHqhycc() {
		return hqhycc;
	}
	/**
	 * @param hqhycc the hqhycc to set
	 */
	public void setHqhycc(String hqhycc) {
		this.hqhycc = hqhycc;
	}
	/**
	 * @return the hqsjw5
	 */
	public String getHqsjw5() {
		return hqsjw5;
	}
	/**
	 * @param hqsjw5 the hqsjw5 to set
	 */
	public void setHqsjw5(String hqsjw5) {
		this.hqsjw5 = hqsjw5;
	}
	/**
	 * @return the hqssl5
	 */
	public String getHqssl5() {
		return hqssl5;
	}
	/**
	 * @param hqssl5 the hqssl5 to set
	 */
	public void setHqssl5(String hqssl5) {
		this.hqssl5 = hqssl5;
	}
	/**
	 * @return the hqsjw4
	 */
	public String getHqsjw4() {
		return hqsjw4;
	}
	/**
	 * @param hqsjw4 the hqsjw4 to set
	 */
	public void setHqsjw4(String hqsjw4) {
		this.hqsjw4 = hqsjw4;
	}
	/**
	 * @return the hqssl4
	 */
	public String getHqssl4() {
		return hqssl4;
	}
	/**
	 * @param hqssl4 the hqssl4 to set
	 */
	public void setHqssl4(String hqssl4) {
		this.hqssl4 = hqssl4;
	}
	/**
	 * @return the hqsjw3
	 */
	public String getHqsjw3() {
		return hqsjw3;
	}
	/**
	 * @param hqsjw3 the hqsjw3 to set
	 */
	public void setHqsjw3(String hqsjw3) {
		this.hqsjw3 = hqsjw3;
	}
	/**
	 * @return the hqssl3
	 */
	public String getHqssl3() {
		return hqssl3;
	}
	/**
	 * @param hqssl3 the hqssl3 to set
	 */
	public void setHqssl3(String hqssl3) {
		this.hqssl3 = hqssl3;
	}
	/**
	 * @return the hqsjw2
	 */
	public String getHqsjw2() {
		return hqsjw2;
	}
	/**
	 * @param hqsjw2 the hqsjw2 to set
	 */
	public void setHqsjw2(String hqsjw2) {
		this.hqsjw2 = hqsjw2;
	}
	/**
	 * @return the hqssl2
	 */
	public String getHqssl2() {
		return hqssl2;
	}
	/**
	 * @param hqssl2 the hqssl2 to set
	 */
	public void setHqssl2(String hqssl2) {
		this.hqssl2 = hqssl2;
	}
	/**
	 * @return the hqsjw1
	 */
	public String getHqsjw1() {
		return hqsjw1;
	}
	/**
	 * @param hqsjw1 the hqsjw1 to set
	 */
	public void setHqsjw1(String hqsjw1) {
		this.hqsjw1 = hqsjw1;
	}
	/**
	 * @return the hqssl1
	 */
	public String getHqssl1() {
		return hqssl1;
	}
	/**
	 * @param hqssl1 the hqssl1 to set
	 */
	public void setHqssl1(String hqssl1) {
		this.hqssl1 = hqssl1;
	}
	/**
	 * @return the hqbjw1
	 */
	public String getHqbjw1() {
		return hqbjw1;
	}
	/**
	 * @param hqbjw1 the hqbjw1 to set
	 */
	public void setHqbjw1(String hqbjw1) {
		this.hqbjw1 = hqbjw1;
	}
	/**
	 * @return the hqbsl1
	 */
	public String getHqbsl1() {
		return hqbsl1;
	}
	/**
	 * @param hqbsl1 the hqbsl1 to set
	 */
	public void setHqbsl1(String hqbsl1) {
		this.hqbsl1 = hqbsl1;
	}
	/**
	 * @return the hqbjw2
	 */
	public String getHqbjw2() {
		return hqbjw2;
	}
	/**
	 * @param hqbjw2 the hqbjw2 to set
	 */
	public void setHqbjw2(String hqbjw2) {
		this.hqbjw2 = hqbjw2;
	}
	/**
	 * @return the hqbsl2
	 */
	public String getHqbsl2() {
		return hqbsl2;
	}
	/**
	 * @param hqbsl2 the hqbsl2 to set
	 */
	public void setHqbsl2(String hqbsl2) {
		this.hqbsl2 = hqbsl2;
	}
	/**
	 * @return the hqbjw3
	 */
	public String getHqbjw3() {
		return hqbjw3;
	}
	/**
	 * @param hqbjw3 the hqbjw3 to set
	 */
	public void setHqbjw3(String hqbjw3) {
		this.hqbjw3 = hqbjw3;
	}
	/**
	 * @return the hqbsl3
	 */
	public String getHqbsl3() {
		return hqbsl3;
	}
	/**
	 * @param hqbsl3 the hqbsl3 to set
	 */
	public void setHqbsl3(String hqbsl3) {
		this.hqbsl3 = hqbsl3;
	}
	/**
	 * @return the hqbjw4
	 */
	public String getHqbjw4() {
		return hqbjw4;
	}
	/**
	 * @param hqbjw4 the hqbjw4 to set
	 */
	public void setHqbjw4(String hqbjw4) {
		this.hqbjw4 = hqbjw4;
	}
	/**
	 * @return the hqbsl4
	 */
	public String getHqbsl4() {
		return hqbsl4;
	}
	/**
	 * @param hqbsl4 the hqbsl4 to set
	 */
	public void setHqbsl4(String hqbsl4) {
		this.hqbsl4 = hqbsl4;
	}
	/**
	 * @return the hqbjw5
	 */
	public String getHqbjw5() {
		return hqbjw5;
	}
	/**
	 * @param hqbjw5 the hqbjw5 to set
	 */
	public void setHqbjw5(String hqbjw5) {
		this.hqbjw5 = hqbjw5;
	}
	/**
	 * @return the hqbsl5
	 */
	public String getHqbsl5() {
		return hqbsl5;
	}
	/**
	 * @param hqbsl5 the hqbsl5 to set
	 */
	public void setHqbsl5(String hqbsl5) {
		this.hqbsl5 = hqbsl5;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the paramCode
	 */
	public String getParamCode() {
		return paramCode;
	}
	/**
	 * @param paramCode the paramCode to set
	 */
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getMarketStatus()
	{
		return marketStatus;
	}
	public void setMarketStatus(String marketStatus)
	{
		this.marketStatus = marketStatus;
	}
	
}
