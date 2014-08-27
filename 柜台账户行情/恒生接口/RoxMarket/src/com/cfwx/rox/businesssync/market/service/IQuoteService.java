/**
 * 
 */
package com.cfwx.rox.businesssync.market.service;

import java.util.List;

import com.cfwx.rox.businesssync.market.show.CommonLine;
import com.cfwx.rox.businesssync.market.show.DayLine;
import com.cfwx.rox.businesssync.market.show.MinLine;

/**
 * <code>IQuoteService</code>
 * @author Jimmy
 *
 * @since RoxMarket(June 15, 2014)
 */
public interface IQuoteService {
	
	public List<CommonLine> getWeeklyKline(String sCode, int size);
	/**
	 *  日K线数据
	 * @param sCode
	 * @param sSize 数据量
	 * @return
	 */
	public List<DayLine> getDailyKLine(String sCode,int sSize);
	/**
	 * 月K线数据
	 * @param code
	 * @param parseInt
	 * @return
	 */
	public List<CommonLine> getMonthlyKLine(String code, int parseInt);
	/**
	 * 年K线数据
	 * @param sCode
	 * @param size 
	 * @return
	 */
	public List<CommonLine> getYearlyKLine(String sCode, int size);
	/**
	 * 10分钟K线数据
	 * @param sCode
	 * @param size 
	 * @return
	 */
	public List<MinLine> get10MinKLine(String sCode, int size);
	/**
	 * 5分K线数据
	 * @param sCode
	 * @param size 
	 * @return
	 */
	public List<MinLine> get5MinKLine(String sCode, int size);
	/**
	 * 当天在交易时间内的5分K线数据
	 * @param sCode
	 * @return
	 */
	public List<MinLine> getToday5Min(String sCode);

	/**
	 * 当天在交易时间内的10分K线数据
	 * @param sCode
	 * @return
	 */
	public List<MinLine> getToday10MinLine(String sCode);

	/**
	 * 当天在交易时间内的15分K线数据
	 * @param sCode
	 * @return
	 */
	public List<MinLine> getToday15MinLine(String sCode);

	/**
	 * 当天在交易时间内的30分K线数据
	 * @param sCode
	 * @return
	 */
	public List<MinLine> getToday30MinLine(String sCode);
	/**
	 * 当天在交易时间内的60分K线数据
	 * @param sCode
	 * @return
	 */
	public List<MinLine> getToday60MinLine(String sCode) ;

	/**
	 * 
	 * 15分K线数据
	 * @param sCode
	 * @param size 
	 * @return
	 */
	public List<MinLine> get15MinKLine(String sCode, int size);
	
	/**
	 * 
	 * 30分K线数据
	 * @param sCode
	 * @param size 
	 * @return
	 */
	public List<MinLine> get30MinKLine(String sCode, int size);

	/**
	 * 
	 * 60分K线数据
	 * @param sCode
	 * @param size 
	 * @return
	 */
	public List<MinLine> get60MinKLine(String sCode, int size);
	



}
