package com.cfwx.rox.businesssync.market.entity;

import java.io.Serializable;

/**
 * @author J.C.J
 *
 * 2013-11-21
 */
public class StockInfo  implements Serializable{
	
	private static final long serialVersionUID = 5034800260048523154L;
	private String ZQID;
	private String ZQDM; //	证券代码
	private String ZQJC; //	证券简称
	private String PYJC;//	拼音简称
	private String ZQQC;//	证券全称
	private String ISIN;//	ISIN
	private String ZQLB;//	证券类别
	private String JYSC;//	交易市场
	private String JYDW;//	交易单位
	private String HBZL;//	货币种类
	private String MGMJ; //	每股面值
	private String SSRQ; //	上市日期
	private String ZZSSRQ;//终止上市日期
	private String MGSY;//	每股收益
	private String MGJZC;//	每股净资产
	private String DQBGSJ;//定期报告时间
	private String ZGB;//	总股本
	private String FXZGB;//	发行总股本
	private String LTGB;//	流通股本
	private String DWJZ;//	单位净值
	private String HYDM;//	行业代码
	private String HYMC;//	行业名称
	private String premaryCode;
	public String getZQID() {
		return ZQID;
	}
	public void setZQID(String zQID) {
		ZQID = zQID;
	}
	public String getZQDM() {
		return ZQDM;
	}
	public void setZQDM(String zQDM) {
		ZQDM = zQDM;
	}
	public String getZQJC() {
		return ZQJC;
	}
	public void setZQJC(String zQJC) {
		ZQJC = zQJC;
	}
	public String getPYJC() {
		return PYJC;
	}
	public void setPYJC(String pYJC) {
		PYJC = pYJC;
	}
	public String getZQQC() {
		return ZQQC;
	}
	public void setZQQC(String zQQC) {
		ZQQC = zQQC;
	}
	public String getISIN() {
		return ISIN;
	}
	public void setISIN(String iSIN) {
		ISIN = iSIN;
	}
	public String getZQLB() {
		return ZQLB;
	}
	public void setZQLB(String zQLB) {
		ZQLB = zQLB;
	}
	public String getJYSC() {
		return JYSC;
	}
	public void setJYSC(String jYSC) {
		JYSC = jYSC;
	}
	public String getJYDW() {
		return JYDW;
	}
	public void setJYDW(String jYDW) {
		JYDW = jYDW;
	}
	public String getHBZL() {
		return HBZL;
	}
	public void setHBZL(String hBZL) {
		HBZL = hBZL;
	}
	public String getMGMJ() {
		return MGMJ;
	}
	public void setMGMJ(String mGMJ) {
		MGMJ = mGMJ;
	}
	public String getSSRQ() {
		return SSRQ;
	}
	public void setSSRQ(String sSRQ) {
		SSRQ = sSRQ;
	}
	public String getZZSSRQ() {
		return ZZSSRQ;
	}
	public void setZZSSRQ(String zZSSRQ) {
		ZZSSRQ = zZSSRQ;
	}
	public String getMGSY() {
		return MGSY;
	}
	public void setMGSY(String mGSY) {
		MGSY = mGSY;
	}
	public String getMGJZC() {
		return MGJZC;
	}
	public void setMGJZC(String mGJZC) {
		MGJZC = mGJZC;
	}
	public String getDQBGSJ() {
		return DQBGSJ;
	}
	public void setDQBGSJ(String dQBGSJ) {
		DQBGSJ = dQBGSJ;
	}
	public String getZGB() {
		return ZGB;
	}
	public void setZGB(String zGB) {
		ZGB = zGB;
	}
	public String getFXZGB() {
		return FXZGB;
	}
	public void setFXZGB(String fXZGB) {
		FXZGB = fXZGB;
	}
	public String getLTGB() {
		return LTGB;
	}
	public void setLTGB(String lTGB) {
		LTGB = lTGB;
	}
	public String getDWJZ() {
		return DWJZ;
	}
	public void setDWJZ(String dWJZ) {
		DWJZ = dWJZ;
	}
	public String getHYDM() {
		return HYDM;
	}
	public void setHYDM(String hYDM) {
		HYDM = hYDM;
	}
	public String getHYMC() {
		return HYMC;
	}
	public void setHYMC(String hYMC) {
		HYMC = hYMC;
	}
	public String getPremaryCode() {
		return premaryCode;
	}
	public void setPremaryCode(String premaryCode) {
		this.premaryCode = premaryCode;
	}
	
}
