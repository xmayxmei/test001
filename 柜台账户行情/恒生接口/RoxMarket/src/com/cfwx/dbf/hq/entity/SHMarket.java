package com.cfwx.dbf.hq.entity;

import java.io.Serializable;
/**
 * @author J.C.J
 * 上海交易所行情信息
 * 2013-9-18
 */
public class SHMarket implements Serializable{
	
	private static final long serialVersionUID = -7255713227945236156L;
	//证券代码
	private String s1;
	//证券名称
	private String s2;
	//前收盘价格
	private String s3;
	//今开盘价格
	private String s4;
	//今成交金额
	private String s5;
	//最高价格
	private String s6;
	//最低价格
	private String s7;
	//最新价格
	private String s8;
	//当前买入价格
	private String s9;
	//当前卖出价格
	private String s10;
	//成交数量
	private String s11;
	//市盈率
	private String s13;
	//申买量一
	private String s15;
	//申买价二
	private String s16;
	//申买量二
	private String s17;
	//申买价三
	private String s18;
	//申买量三
	private String s19;
	//申卖量一
	private String s21;
	//申卖价二
	private String s22;
	//申卖量二
	private String s23;
	//申卖价三
	private String s24;
	//申卖量三
	private String s25;
	//申买价四
	private String s26;
	//申买量四
	private String s27;
	//申买价五
	private String s28;
	//申买量五
	private String s29;
	//申卖价四
	private String s30;
	//申卖量四
	private String s31;
	//申卖价五
	private String s32;
	//申卖量五
	private String s33;
	//股票组合代码(sh000001)
	private String paramCode;
	//日期(yyyyMMdd)
	private String date;
	//更新时间(HHmmss)
	private String time;
	//类型(0:指数;1:股票)
	private int type;
	//是否删除(1:是,已删除，0：否，未删除)
	private String isDeleted;
	//1：正式行情，0：收市行情
	private String marketStatus;
	/**
	 * @return the s1
	 */
	public String getS1() {
		return s1;
	}
	/**
	 * @param s1 the s1 to set
	 */
	public void setS1(String s1) {
		this.s1 = s1;
	}
	/**
	 * @return the s2
	 */
	public String getS2() {
		return s2;
	}
	/**
	 * @param s2 the s2 to set
	 */
	public void setS2(String s2) {
		this.s2 = s2;
	}
	/**
	 * @return the s3
	 */
	public String getS3() {
		return s3;
	}
	/**
	 * @param s3 the s3 to set
	 */
	public void setS3(String s3) {
		this.s3 = s3;
	}
	/**
	 * @return the s4
	 */
	public String getS4() {
		return s4;
	}
	/**
	 * @param s4 the s4 to set
	 */
	public void setS4(String s4) {
		this.s4 = s4;
	}
	/**
	 * @return the s5
	 */
	public String getS5() {
		return s5;
	}
	/**
	 * @param s5 the s5 to set
	 */
	public void setS5(String s5) {
		this.s5 = s5;
	}
	/**
	 * @return the s6
	 */
	public String getS6() {
		return s6;
	}
	/**
	 * @param s6 the s6 to set
	 */
	public void setS6(String s6) {
		this.s6 = s6;
	}
	/**
	 * @return the s7
	 */
	public String getS7() {
		return s7;
	}
	/**
	 * @param s7 the s7 to set
	 */
	public void setS7(String s7) {
		this.s7 = s7;
	}
	/**
	 * @return the s8
	 */
	public String getS8() {
		return s8;
	}
	/**
	 * @param s8 the s8 to set
	 */
	public void setS8(String s8) {
		this.s8 = s8;
	}
	/**
	 * @return the s9
	 */
	public String getS9() {
		return s9;
	}
	/**
	 * @param s9 the s9 to set
	 */
	public void setS9(String s9) {
		this.s9 = s9;
	}
	/**
	 * @return the s10
	 */
	public String getS10() {
		return s10;
	}
	/**
	 * @param s10 the s10 to set
	 */
	public void setS10(String s10) {
		this.s10 = s10;
	}
	/**
	 * @return the s11
	 */
	public String getS11() {
		return s11;
	}
	/**
	 * @param s11 the s11 to set
	 */
	public void setS11(String s11) {
		this.s11 = s11;
	}
	/**
	 * @return the s13
	 */
	public String getS13() {
		return s13;
	}
	/**
	 * @param s13 the s13 to set
	 */
	public void setS13(String s13) {
		this.s13 = s13;
	}
	/**
	 * @return the s15
	 */
	public String getS15() {
		return s15;
	}
	/**
	 * @param s15 the s15 to set
	 */
	public void setS15(String s15) {
		this.s15 = s15;
	}
	/**
	 * @return the s16
	 */
	public String getS16() {
		return s16;
	}
	/**
	 * @param s16 the s16 to set
	 */
	public void setS16(String s16) {
		this.s16 = s16;
	}
	/**
	 * @return the s17
	 */
	public String getS17() {
		return s17;
	}
	/**
	 * @param s17 the s17 to set
	 */
	public void setS17(String s17) {
		this.s17 = s17;
	}
	/**
	 * @return the s18
	 */
	public String getS18() {
		return s18;
	}
	/**
	 * @param s18 the s18 to set
	 */
	public void setS18(String s18) {
		this.s18 = s18;
	}
	/**
	 * @return the s19
	 */
	public String getS19() {
		return s19;
	}
	/**
	 * @param s19 the s19 to set
	 */
	public void setS19(String s19) {
		this.s19 = s19;
	}
	/**
	 * @return the s21
	 */
	public String getS21() {
		return s21;
	}
	/**
	 * @param s21 the s21 to set
	 */
	public void setS21(String s21) {
		this.s21 = s21;
	}
	/**
	 * @return the s22
	 */
	public String getS22() {
		return s22;
	}
	/**
	 * @param s22 the s22 to set
	 */
	public void setS22(String s22) {
		this.s22 = s22;
	}
	/**
	 * @return the s23
	 */
	public String getS23() {
		return s23;
	}
	/**
	 * @param s23 the s23 to set
	 */
	public void setS23(String s23) {
		this.s23 = s23;
	}
	/**
	 * @return the s24
	 */
	public String getS24() {
		return s24;
	}
	/**
	 * @param s24 the s24 to set
	 */
	public void setS24(String s24) {
		this.s24 = s24;
	}
	/**
	 * @return the s25
	 */
	public String getS25() {
		return s25;
	}
	/**
	 * @param s25 the s25 to set
	 */
	public void setS25(String s25) {
		this.s25 = s25;
	}
	/**
	 * @return the s26
	 */
	public String getS26() {
		return s26;
	}
	/**
	 * @param s26 the s26 to set
	 */
	public void setS26(String s26) {
		this.s26 = s26;
	}
	/**
	 * @return the s27
	 */
	public String getS27() {
		return s27;
	}
	/**
	 * @param s27 the s27 to set
	 */
	public void setS27(String s27) {
		this.s27 = s27;
	}
	/**
	 * @return the s28
	 */
	public String getS28() {
		return s28;
	}
	/**
	 * @param s28 the s28 to set
	 */
	public void setS28(String s28) {
		this.s28 = s28;
	}
	/**
	 * @return the s29
	 */
	public String getS29() {
		return s29;
	}
	/**
	 * @param s29 the s29 to set
	 */
	public void setS29(String s29) {
		this.s29 = s29;
	}
	/**
	 * @return the s30
	 */
	public String getS30() {
		return s30;
	}
	/**
	 * @param s30 the s30 to set
	 */
	public void setS30(String s30) {
		this.s30 = s30;
	}
	/**
	 * @return the s31
	 */
	public String getS31() {
		return s31;
	}
	/**
	 * @param s31 the s31 to set
	 */
	public void setS31(String s31) {
		this.s31 = s31;
	}
	/**
	 * @return the s32
	 */
	public String getS32() {
		return s32;
	}
	/**
	 * @param s32 the s32 to set
	 */
	public void setS32(String s32) {
		this.s32 = s32;
	}
	/**
	 * @return the s33
	 */
	public String getS33() {
		return s33;
	}
	/**
	 * @param s33 the s33 to set
	 */
	public void setS33(String s33) {
		this.s33 = s33;
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
	 * @return the isDeleted
	 */
	public String getIsDeleted() {
		return isDeleted;
	}
	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
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
	public String getMarketStatus()
	{
		return marketStatus;
	}
	public void setMarketStatus(String marketStatus)
	{
		this.marketStatus = marketStatus;
	}
	
}
