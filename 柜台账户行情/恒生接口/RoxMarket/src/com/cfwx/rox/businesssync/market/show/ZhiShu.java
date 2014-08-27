/**
 * 
 */
package com.cfwx.rox.businesssync.market.show;

import java.io.Serializable;

/**
 * @author J.C.J
 *
 * 2013-11-29
 */
public class ZhiShu implements Serializable{

	private static final long serialVersionUID = -421838843594745179L;

	private String name ;
	
	private String zx ;
	
	private String zde;
	
	private String zdf;
	
	private String pcode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZx() {
		return zx;
	}

	public void setZx(String zx) {
		this.zx = zx;
	}

	public String getZde() {
		return zde;
	}

	public void setZde(String zde) {
		this.zde = zde;
	}

	public String getZdf() {
		return zdf;
	}

	public void setZdf(String zdf) {
		this.zdf = zdf;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	
}
