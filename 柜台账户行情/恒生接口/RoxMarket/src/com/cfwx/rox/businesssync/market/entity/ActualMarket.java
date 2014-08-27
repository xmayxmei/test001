package com.cfwx.rox.businesssync.market.entity;

import java.io.Serializable;

/**
 * @author J.C.J
 *
 * 2013-11-23
 */
public class ActualMarket implements Serializable{
	
	private static final long serialVersionUID = 7184505879193249169L;
	//排行显示字段
	private String mc; //名称
	private String dm; //代码
	private long zx; //最新价
	private long zdf; //涨跌幅
	private long zde;//涨跌额
	private double ze;//交易总额
	private long zl;//交易总量
	private long zs;//昨收
	private long jk;//今开
	private long zg;//最高
	private long zd;//最低
	
	//股票实时行情字段
	private double hs;//换手 :交易量/流通股数
	private double sy;//市盈 ：动态市盈率：每股最新价/(每股收益*(4/当前季度 ))
	private long jj;//均价
	private double wb;//委比 :(总委买量-总委卖量)/(总委买量+总委卖量)
	private double lb;//量比 :总量/前五日平均成交量
	private double gb;//股本
	private double sz;//市值
	
	private String type;//详细类型 
	private int bigType; //大类  1.股票，2.指数，3.债券，4.开放式基金
	private int market;//交易市场
	private double ltgb;//流通股本
	private double mgsy;//每股收益
	private int jd;//当前季度(信息中心提供的定期报告时间提取的)
	
	private int wrjyl;//前五日平均交易量
	
	//五档委托买量
	private long b1;
	private long b2;
	private long b3;
	private long b4;
	private long b5;
	
	//五档 委买价格
	private double b1Price;
	private double b2Price;
	private double b3Price;
	private double b4Price;
	private double b5Price;
	
	//委卖5
	private long s1;
	private long s2;
	private long s3;
	private long s4;
	private long s5;
	
	private double s1Price;
	private double s2Price;
	private double s3Price;
	private double s4Price;
	private double s5Price;
	
	public double getB1Price() {
		return this.b1Price;
	}
	public void setB1Price(double b1Price) {
		this.b1Price = b1Price;
	}
	public double getB2Price() {
		return this.b2Price;
	}
	public void setB2Price(double b2Price) {
		this.b2Price = b2Price;
	}
	public double getB3Price() {
		return this.b3Price;
	}
	public void setB3Price(double b3Price) {
		this.b3Price = b3Price;
	}
	public double getB4Price() {
		return this.b4Price;
	}
	public void setB4Price(double b4Price) {
		this.b4Price = b4Price;
	}
	public double getB5Price() {
		return this.b5Price;
	}
	public void setB5Price(double b5Price) {
		this.b5Price = b5Price;
	}
	public double getS1Price() {
		return this.s1Price;
	}
	public void setS1Price(double s1Price) {
		this.s1Price = s1Price;
	}
	public double getS2Price() {
		return this.s2Price;
	}
	public void setS2Price(double s2Price) {
		this.s2Price = s2Price;
	}
	public double getS3Price() {
		return this.s3Price;
	}
	public void setS3Price(double s3Price) {
		this.s3Price = s3Price;
	}
	public double getS4Price() {
		return this.s4Price;
	}
	public void setS4Price(double s4Price) {
		this.s4Price = s4Price;
	}
	public double getS5Price() {
		return this.s5Price;
	}
	public void setS5Price(double s5Price) {
		this.s5Price = s5Price;
	}
	private long ut;//更新时间
	private String pcode;//唯一标识代码
	
	//配置
	private double enlarge=1000D;//放大倍数
	private int pointnum;//小数点后有效数字个数
	
	private String upperLimit; 	//上限
	private String lowerLimit;	//下限
	
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
	public long getZx() {
		return zx;
	}
	public void setZx(long zx) {
		this.zx = zx;
	}
	public long getZdf() {
		return zdf;
	}
	public void setZdf(long zdf) {
		this.zdf = zdf;
	}
	public long getZde() {
		return zde;
	}
	public void setZde(long zde) {
		this.zde = zde;
	}
	public double getZe() {
		return ze;
	}
	public void setZe(double ze) {
		this.ze = ze;
	}
	public long getZs() {
		return zs;
	}
	public void setZs(long zs) {
		this.zs = zs;
	}
	public long getJk() {
		return jk;
	}
	public void setJk(long jk) {
		this.jk = jk;
	}
	public long getZg() {
		return zg;
	}
	public void setZg(long zg) {
		this.zg = zg;
	}
	public long getZd() {
		return zd;
	}
	public void setZd(long zd) {
		this.zd = zd;
	}
	public double getHs() {
		return hs;
	}
	public void setHs(double hs) {
		this.hs = hs;
	}
	public double getSy() {
		return sy;
	}
	public void setSy(double sy) {
		this.sy = sy;
	}
	public long getJj() {
		return jj;
	}
	public void setJj(long jj) {
		this.jj = jj;
	}
	public double getWb() {
		return wb;
	}
	public void setWb(double wb) {
		this.wb = wb;
	}
	public double getLb() {
		return lb;
	}
	public void setLb(double lb) {
		this.lb = lb;
	}
	public double getGb() {
		return gb;
	}
	public void setGb(double gb) {
		this.gb = gb;
	}
	public double getSz() {
		return sz;
	}
	public void setSz(double sz) {
		this.sz = sz;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getMarket() {
		return market;
	}
	public void setMarket(int market) {
		this.market = market;
	}
	public double getLtgb() {
		return ltgb;
	}
	public void setLtgb(double ltgb) {
		this.ltgb = ltgb;
	}
	public double getMgsy() {
		return mgsy;
	}
	public void setMgsy(double mgsy) {
		this.mgsy = mgsy;
	}
	public int getJd() {
		return jd;
	}
	public void setJd(int jd) {
		this.jd = jd;
	}
	public int getWrjyl() {
		return wrjyl;
	}
	public void setWrjyl(int wrjyl) {
		this.wrjyl = wrjyl;
	}
	public long getB1() {
		return b1;
	}
	public void setB1(long b1) {
		this.b1 = b1;
	}
	public long getB2() {
		return b2;
	}
	public void setB2(long b2) {
		this.b2 = b2;
	}
	public long getB3() {
		return b3;
	}
	public void setB3(long b3) {
		this.b3 = b3;
	}
	public long getB4() {
		return b4;
	}
	public void setB4(long b4) {
		this.b4 = b4;
	}
	public long getB5() {
		return b5;
	}
	public void setB5(long b5) {
		this.b5 = b5;
	}
	public long getS1() {
		return s1;
	}
	public void setS1(long s1) {
		this.s1 = s1;
	}
	public long getS2() {
		return s2;
	}
	public void setS2(long s2) {
		this.s2 = s2;
	}
	public long getS3() {
		return s3;
	}
	public void setS3(long s3) {
		this.s3 = s3;
	}
	public long getS4() {
		return s4;
	}
	public void setS4(long s4) {
		this.s4 = s4;
	}
	public long getS5() {
		return s5;
	}
	public void setS5(long s5) {
		this.s5 = s5;
	}
	public long getUt() {
		return ut;
	}
	public void setUt(long ut) {
		this.ut = ut;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public double getEnlarge() {
		return enlarge;
	}
	public void setEnlarge(double enlarge) {
		this.enlarge = enlarge;
	}
	public int getPointnum() {
		return pointnum;
	}
	public void setPointnum(int pointnum) {
		this.pointnum = pointnum;
	}
	public long getZl() {
		return zl;
	}
	public void setZl(long zl) {
		this.zl = zl;
	}
	public int getBigType() {
		return bigType;
	}
	public void setBigType(int bigType) {
		this.bigType = bigType;
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
