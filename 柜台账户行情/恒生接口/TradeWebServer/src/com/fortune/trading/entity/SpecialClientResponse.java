/*
 * FileName: SpecialClientResponse.java
 * Copyright: Copyright 2014-8-26 Cfwx Tech. Co. Ltd.All right reserved.
 * Description: [这里用一句话描述]
 *
 */
package com.fortune.trading.entity;

/**
 * <code>SpecialClientResponse<code>
 *
 * @author Jimmy
 *
 */
public class SpecialClientResponse<T> extends ClientResponse<T>{
	private String position_str = "";

	/**
	 * @return the position_str
	 */
	public String getPositionStr() {
		return position_str;
	}
	/**
	 * @param position_str the position_str to set
	 */
	public void setPositionStr(String positionStr) {
		this.position_str = positionStr;
	}
	
}
