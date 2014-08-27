package com.cfwx.rox.businesssync.market.show;

import java.io.Serializable;

/**
 * @author J.C.J
 *
 * 2013-11-25
 */
public class SortObj implements Serializable{

	private static final long serialVersionUID = -7151930545823460161L;
	
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
	private String pcode;//证券唯一标识码
	
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
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
}
