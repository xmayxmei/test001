/**
 * 
 */
package com.cfwx.rox.businesssync.market.dao;

import java.util.List;

import com.cfwx.rox.businesssync.market.show.CommonLine;
import com.cfwx.rox.businesssync.market.show.DayLine;
import com.cfwx.rox.businesssync.market.show.MinLine;

/**
 * <code>IQuoteCacheDao</code>
 * 
 * @author Jimmy
 * @since RoxMarket (June 18, 2014)
 */
public interface IQuoteCacheDao {
	/**
	 * 增加5分钟数据集到内存
	 * 
	 * @param sCode
	 * @param lstDaily
	 */
	public void add5Min(String sCode, List<MinLine> lst5Min);
	/**
	 * 增加10分钟数据集到内存
	 * 
	 * @param sCode
	 * @param lstDaily
	 */
	public void add10Min(String sCode, List<MinLine> lst10Min);
	/**
	 * 获取10分K线数据
	 * @return
	 */
	public List<MinLine> get10Min(String sCode);
	/**
	 * 获取5分K线数据
	 * @return
	 */
	public List<MinLine> get5Min(String sCode);
	/**
	 * 增加日K集到内存
	 * 
	 * @param sCode
	 * @param lstDaily
	 */
	public void addDaily(String sCode, List<DayLine> lstDaily);
	/**
	 * 获取日K
	 * @return
	 */
	public List<DayLine> getDaily(String sCode);
	/**
	 * 获取最后一条日K
	 * @return
	 */
	public DayLine getLastDaily(String sCode);
	/**
	 * 增加周K集到内存
	 * @param sCode
	 * @param lstNewWeekly
	 */
	public void addWeekly(String sCode, List<CommonLine> lstNewWeekly);
	/**
	 * @param sCode
	 * @return
	 */
	public List<CommonLine> getWeekly(String sCode);
	/**
	 * @param sCode
	 * @return
	 */
	public CommonLine getLastWeekly(String sCode);
	/**
	 * 增加月K集到内存
	 * @param sCode
	 * @param lstNewMonthly
	 */
	public void addMonthly(String sCode, List<CommonLine> lstNewMonthly);
	/**
	 * @param sCode
	 * @return
	 */
	public List<CommonLine> getMonthly(String sCode);
	/**
	 * @param sCode
	 * @return
	 */
	public CommonLine getLastMonthly(String sCode);
	/**
	 * 增加月K集到内存
	 * @param sCode
	 * @param lstNewYearly
	 */
	public void addYearly(String sCode, List<CommonLine> lstNewYearly);
	/**
	 * @param sCode
	 * @return
	 */
	public List<CommonLine> getYearly(String sCode);
	/**
	 * @param sCode
	 * @return
	 */
	public CommonLine getLastYearly(String sCode);
	/**
	 * 
	 */
	public void clearAllKLineCache();
	/**
	 * 
	 */
	public void clear5MinCache();
	/**
	 * 
	 */
	public void clear10MinCache();
	/**
	 * 
	 */
	public void clear15MinCache();
	/**
	 * @param sCode
	 * @param lstMins
	 */
	public void add15Min(String sCode, List<MinLine> lstMins);
	/**
	 * @param sCode
	 * @return
	 */
	public List<MinLine> get15Min(String sCode);
	/**
	 * 
	 */
	public void clear30MinCache();
	/**
	 * @param sCode
	 * @param lstMins
	 */
	public void add30Min(String sCode, List<MinLine> lstMins);
	/**
	 * @param sCode
	 * @return
	 */
	public List<MinLine> get30Min(String sCode);
	/**
	 * @param sCode
	 * @return
	 */
	public List<MinLine> get60Min(String sCode);
	/**
	 * @param sCode
	 * @param lstMins
	 */
	public void add60Min(String sCode, List<MinLine> lstMins);
	/**
	 * 
	 */
	public void clear60MinCache();
	
	public void addStockBaseInfo(String sData);
	
	public String getStockBaseInfo();
	
	public void addOptionStock(String sOpenId, String sStockCode);
	
	public void delOptionStock(String sOpenId, String sStockCode);
	
	public String getOptionStocks(String sOpenId);
	
	public boolean isContainOpenIdKey(String sOpenId);
}
