package com.cfwx.rox.businesssync.market.service;

/**
 * @author J.C.J
 *
 * 2013-9-24
 */
public interface IDBFStockData {
	/**
	 * 更新股票信息
	 * @param mode 0:重启,1:开盘作业 
	 * @throws Exception
	 */
	public void updateStockInfo(int mode)throws Exception;
	
	public  void clearMarketShowData()throws Exception;
}
