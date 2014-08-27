package com.cfwx.rox.businesssync.market.service;

import java.util.Map;

/**
 * @author J.C.J
 * 从DBF读取数据来源
 * 2013-9-24
 */
public interface IShDBFHQData {
	public void getHQDataToMemory()throws Exception ;
	
	public Map<String,Double> readOnceSHHQ()throws Exception ;
}
