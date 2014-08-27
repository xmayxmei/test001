package com.cfwx.rox.businesssync.market.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author J.C.J
 *
 * 2013-11-22
 */
public class StockType {

//	序号	名    称	变  量	类  型	长度	单位	备  注
	private String type;//	1.		类别标识id	Type	int			主参数
	private String name;//	2.		类别名称	Name	String			
	private List<StockType> childList = new ArrayList<StockType>();
	private List<String> value= new ArrayList<String>();
	private int vlumerate;//	3.		“手”比率	Volumerate	int			每手股数
	private int pointnum;//	4.		股价小数位数	pointnum	int			0-255
	private boolean isUsed;//	5.		是否有效		boolean			是否使用,true:使用
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVlumerate() {
		return vlumerate;
	}
	public void setVlumerate(int vlumerate) {
		this.vlumerate = vlumerate;
	}
	public int getPointnum() {
		return pointnum;
	}
	public void setPointnum(int pointnum) {
		this.pointnum = pointnum;
	}
	public boolean isUsed() {
		return isUsed;
	}
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	public List<String> getValue() {
		return value;
	}
	public void setValue(List<String> value) {
		this.value = value;
	}
	public List<StockType> getChildList() {
		return childList;
	}
	public void addChild(StockType child){
		childList.add(child);
	}
}
