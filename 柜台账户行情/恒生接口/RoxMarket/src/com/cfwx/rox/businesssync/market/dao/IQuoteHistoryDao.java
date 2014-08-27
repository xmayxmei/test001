/**
 * 
 */
package com.cfwx.rox.businesssync.market.dao;

import java.util.List;

import com.cfwx.rox.businesssync.market.show.CommonLine;
import com.cfwx.rox.businesssync.market.show.DayLine;
import com.cfwx.rox.businesssync.market.show.MinLine;

/**
 * <code>IQuoteDao</code> 获取历史数据
 * 
 * @author Jimmy
 *
 * @since 2014-6-12
 */
public interface IQuoteHistoryDao {
	/**
	 *  5分 
	 * @param sCode
	 * @param iDays 最新的天数
	 * @return
	 */
	public List<MinLine> get5MinData(String sCode, int iDays);
	/**
	 *  10分 
	 * @param sCode
	 * @param iDays 最新的天数
	 * @return
	 */
	public List<MinLine> get10MinData(String sCode, int iDays);
	/**
	 *  日K数据
	 * @param sCode
	 * @param iDays 最新的天数
	 * @return
	 */
	public List<DayLine> getDailyData(String sCode, int iDays);
	/**
	 *  周K数据
	 * @param sCode
	 * @param iWeeks
	 * @return
	 */
	public List<CommonLine> getWeeklyData(String sCode, int iWeeks);
	/**
	 *  月K数据
	 * @param sCode
	 * @param iMonths
	 * @return
	 */
	public List<CommonLine> getMonthlyData(String sCode, int iMonths);
	/**
	 *  年K数据
	 * @param sCode
	 * @param iYears
	 * @return
	 */
	public List<CommonLine> getYearlyData(String sCode, int iYears);
	/**
	 * @param sCode
	 * @param oDay
	 */
	public void save5MinData(String sCode, MinLine oDay);
	/**
	 * @param sCode
	 * @param oDay
	 */
	public void save10MinData(String sCode, MinLine oDay);
	
	/**
	 * @param sCode
	 * @param oDay
	 */
	public void save15MinData(String sCode, MinLine oDay);
	
	/**
	 * @param sCode
	 * @param oDay
	 */
	public void save30MinData(String sCode, MinLine oDay);
	
	/**
	 * @param sCode
	 * @param oDay
	 */
	public void save60MinData(String sCode, MinLine oDay);
	/**
	 * @param sCode
	 * @param oDay
	 */
	public void saveDailyData(String sCode, DayLine oDay);
	/**
	 * @param sCode
	 * @param oDay
	 */
	public void saveWeeklyData(String sCode, CommonLine oWeekly);
	/**
	 * @param sCode
	 * @param oDay
	 */
	public void saveMonthlyData(String sCode, CommonLine oMonthly);
	/**
	 * @param sCode
	 * @param oDay
	 */
	public void saveYearlyData(String sCode, CommonLine oWeekly);
	/**
	 * @param sCode
	 * @param oDay
	 */
	public void updateDailyData(String sCode, DayLine oDay, DayLine oDayNew);
	/**
	 * @param sCode
	 * @param oDay
	 */
	public void updateWeeklyData(String sCode, CommonLine oWeekly, CommonLine oWeeklyNew);
	/**
	 * @param sCode
	 * @param oDay
	 */
	public void updateMonthlyData(String sCode, CommonLine oMonthly, CommonLine oMonthlyNew);
	/**
	 * @param sCode
	 * @param oDay
	 */
	public void updateYearlyData(String sCode, CommonLine oYearly, CommonLine oYearlyNew);
	/**
	 * @param sCode
	 * @param iMinDays
	 * @return
	 */
	public List<MinLine> get15MinData(String sCode, int iMinDays);
	/**
	 * @param sCode
	 * @param i
	 * @return
	 */
	public List<MinLine> get30MinData(String sCode, int i);
	/**
	 * @param sCode
	 * @param iMinDays
	 * @return
	 */
	public List<MinLine> get60MinData(String sCode, int iMinDays);
}
