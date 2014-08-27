/**
 * 
 */
package com.cfwx.rox.businesssync.market.service;

/**
 * @author J.C.J
 *
 * 2013-11-28
 */
public interface IWorkService {

	/**
	 * 收盘作业
	 */
	public void closeWork();
	
	/**
	 * 开盘作业
	 */
	public void openWork();
	
	/**
	 * 手动开盘作业
	 */
	public void openWorkByMan()throws Exception;
	
	/**
	 * 日线数据写入文件
	 * @throws Exception
	 */
	public void writeDayLine()throws Exception;
	
	/**
	 * 开盘时间，添加日线数据
	 * @throws Exception
	 */
	public void addDayLine()throws Exception;
}
