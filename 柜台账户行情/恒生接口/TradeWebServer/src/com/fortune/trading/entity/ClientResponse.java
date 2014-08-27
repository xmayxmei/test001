package com.fortune.trading.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <code>ClientResponse</code> 
 *
 * @author Colin, Jimmy
 * @Since Tebon Trading v0.0.1 (April 29, 2014)
 *
 */
public class ClientResponse<T> {
	private String errMsg = "";
	private List<T> data = new ArrayList<T>();
	
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public List<T> getData() {
		return data;
	}
	public void addData(T aDatas) {
		data.add(aDatas);
	}
	public void addData(Collection<T> aDatas) {
		data.addAll(aDatas);
	}
}
