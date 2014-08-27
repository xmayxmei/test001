/**
 * 
 */
package com.cfwx.rox.businesssync.market.show;

import java.io.Serializable;

/**
 * @author J.C.J
 * 搜索结果
 * 2013-11-28
 */
public class ResultInfo implements Serializable{
	
	private static final long serialVersionUID = -8131711835920970316L;

	//代码000001
	private String code;
	
	//标识代码 :sh000001
	private String pcode;
	
	//名称
	private String name;
	
	//拼音
	private String py;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}
}
