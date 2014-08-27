package com.cfwx.rox.businesssync.market.show;

import java.io.Serializable;

/**
 * @author J.C.J
 *
 * 2013-11-23
 */
public class ShowAM implements Serializable{
	//排行显示字段
	private String mc; //名称
	private String dm; //代码
	private String zx; //最新价
	private String zdf; //涨跌幅
	private String zde;//涨跌额
	private String ze;//交易总额
	private String zl;//交易总量
	private String zs;//昨收
	private String jk;//今开
	private String zg;//最高
	private String zd;//最低
	
	private String market;
	
	//股票实时行情字段
	private String hs;//换手 :交易量/流通股数
	private String sy;//市盈 ：动态市盈率：每股最新价/(每股收益*(4/当前季度 ))
	private String jj;//均价
	private String wb;//委比 :(总委买量-总委卖量)/(总委买量+总委卖量)
	private String lb;//量比 :总量/前五日平均成交量
	private String gb;//股本
	private String sz;//市值
	
	private String wrjyl;//前五日平均交易量
	
	private String ut;//更新时间
	private String pcode;//唯一标识代码
	
	private String upperLimit; 	//上限
	private String lowerLimit;	//下限
	
	private String b1;
	private String b2;
	private String b3;
	private String b4;
	private String b5;
	
	
	
	private String b1Price;
	private String b2Price;
	private String b3Price;
	private String b4Price;
	private String b5Price;

	
	private String s1;
	private String s2;
	private String s3;
	private String s4;
	private String s5;
	
	
	private String s1Price;
	private String s2Price;
	private String s3Price;
	private String s4Price;
	private String s5Price;

	
	public String getB1Price() {
		return this.b1Price;
	}
	public void setB1Price(String b1Price) {
		this.b1Price = b1Price;
	}
	public String getB2Price() {
		return this.b2Price;
	}
	public void setB2Price(String b2Price) {
		this.b2Price = b2Price;
	}
	public String getB3Price() {
		return this.b3Price;
	}
	public void setB3Price(String b3Price) {
		this.b3Price = b3Price;
	}
	public String getB4Price() {
		return this.b4Price;
	}
	public void setB4Price(String b4Price) {
		this.b4Price = b4Price;
	}
	public String getB5Price() {
		return this.b5Price;
	}
	public void setB5Price(String b5Price) {
		this.b5Price = b5Price;
	}
	public String getS1Price() {
		return this.s1Price;
	}
	public void setS1Price(String s1Price) {
		this.s1Price = s1Price;
	}
	public String getS2Price() {
		return this.s2Price;
	}
	public void setS2Price(String s2Price) {
		this.s2Price = s2Price;
	}
	public String getS3Price() {
		return this.s3Price;
	}
	public void setS3Price(String s3Price) {
		this.s3Price = s3Price;
	}
	public String getS4Price() {
		return this.s4Price;
	}
	public void setS4Price(String s4Price) {
		this.s4Price = s4Price;
	}
	public String getS5Price() {
		return this.s5Price;
	}
	public void setS5Price(String s5Price) {
		this.s5Price = s5Price;
	}
	public String getB1() {
		return this.b1;
	}
	public void setB1(String b1) {
		this.b1 = b1;
	}
	public String getB2() {
		return this.b2;
	}
	public void setB2(String b2) {
		this.b2 = b2;
	}
	public String getB3() {
		return this.b3;
	}
	public void setB3(String b3) {
		this.b3 = b3;
	}
	public String getB4() {
		return this.b4;
	}
	public void setB4(String b4) {
		this.b4 = b4;
	}
	public String getB5() {
		return this.b5;
	}
	public void setB5(String b5) {
		this.b5 = b5;
	}
	public String getS1() {
		return this.s1;
	}
	public void setS1(String s1) {
		this.s1 = s1;
	}
	public String getS2() {
		return this.s2;
	}
	public void setS2(String s2) {
		this.s2 = s2;
	}
	public String getS3() {
		return this.s3;
	}
	public void setS3(String s3) {
		this.s3 = s3;
	}
	public String getS4() {
		return this.s4;
	}
	public void setS4(String s4) {
		this.s4 = s4;
	}
	public String getS5() {
		return this.s5;
	}
	public void setS5(String s5) {
		this.s5 = s5;
	}

	
	
	
	public String getMarket() {
		return this.market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getDm() {
		return dm;
	}
	public void setDm(String dm) {
		this.dm = dm;
	}
	public String getZx() {
		return zx;
	}
	public void setZx(String zx) {
		this.zx = zx;
	}
	public String getZdf() {
		return zdf;
	}
	public void setZdf(String zdf) {
		this.zdf = zdf;
	}
	public String getZde() {
		return zde;
	}
	public void setZde(String zde) {
		this.zde = zde;
	}
	public String getZe() {
		return ze;
	}
	public void setZe(String ze) {
		this.ze = ze;
	}
	public String getZl() {
		return zl;
	}
	public void setZl(String zl) {
		this.zl = zl;
	}
	public String getZs() {
		return zs;
	}
	public void setZs(String zs) {
		this.zs = zs;
	}
	public String getJk() {
		return jk;
	}
	public void setJk(String jk) {
		this.jk = jk;
	}
	public String getZg() {
		return zg;
	}
	public void setZg(String zg) {
		this.zg = zg;
	}
	public String getZd() {
		return zd;
	}
	public void setZd(String zd) {
		this.zd = zd;
	}
	public String getHs() {
		return hs;
	}
	public void setHs(String hs) {
		this.hs = hs;
	}
	public String getSy() {
		return sy;
	}
	public void setSy(String sy) {
		this.sy = sy;
	}
	public String getJj() {
		return jj;
	}
	public void setJj(String jj) {
		this.jj = jj;
	}
	public String getWb() {
		return wb;
	}
	public void setWb(String wb) {
		this.wb = wb;
	}
	public String getLb() {
		return lb;
	}
	public void setLb(String lb) {
		this.lb = lb;
	}
	public String getGb() {
		return gb;
	}
	public void setGb(String gb) {
		this.gb = gb;
	}
	public String getSz() {
		return sz;
	}
	public void setSz(String sz) {
		this.sz = sz;
	}
	public String getWrjyl() {
		return wrjyl;
	}
	public void setWrjyl(String wrjyl) {
		this.wrjyl = wrjyl;
	}
	public String getUt() {
		return ut;
	}
	public void setUt(String ut) {
		this.ut = ut;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getUpperLimit() {
		return upperLimit;
	}
	public void setUpperLimit(String upperLimit) {
		this.upperLimit = upperLimit;
	}
	public String getLowerLimit() {
		return lowerLimit;
	}
	public void setLowerLimit(String lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	
}
