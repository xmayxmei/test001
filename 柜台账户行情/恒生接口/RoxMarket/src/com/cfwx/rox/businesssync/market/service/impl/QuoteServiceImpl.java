/**
 * 
 */
package com.cfwx.rox.businesssync.market.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao;
import com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao;
import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.service.IQuoteService;
import com.cfwx.rox.businesssync.market.servlet.TimeShareServlet.TimeShareService;
import com.cfwx.rox.businesssync.market.show.CommonLine;
import com.cfwx.rox.businesssync.market.show.DayLine;
import com.cfwx.rox.businesssync.market.show.MinLine;
import com.cfwx.rox.businesssync.market.show.TimeShare;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.structure.TimeShareCache;
import com.cfwx.rox.businesssync.market.util.MathFactory;
import com.cfwx.rox.businesssync.market.util.TimeUtils;
import com.cfwx.util.U;

/**
 * <code>QuoteServiceImpl</code>
 * 
 * @author Jimmy
 * @since 2014-6-15
 */
@Service
public class QuoteServiceImpl implements IQuoteService{
	private final static Logger L = Logger.getLogger(QuoteServiceImpl.class);
	
	@Autowired
	private IQuoteHistoryDao quoteDao;
	@Autowired
	private IQuoteCacheDao oCacheDao;
	
	private TimeShareService oService = new TimeShareService();
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IQuoteService#getWeeklyKline()
	 */
	@Override
	public List<CommonLine> getWeeklyKline(String sCode,int size) {
		List<CommonLine> lstWeekly = null;
		try {
			// 获取该类型下的证券代码
			ActualMarket am = MarketCache.get(sCode);
			// 缓存周K历史数据
			lstWeekly = oCacheDao.getWeekly(sCode);
			if (lstWeekly == null || lstWeekly.isEmpty()) {
				lstWeekly = quoteDao.getWeeklyData(sCode, BaseConfig.kday);
				if (lstWeekly != null) {
					oCacheDao.addWeekly(sCode, lstWeekly);
				}
			}
			if (TimeUtils.inNotClose() && BaseConfig.isTradeDay == 1 && am.getJk() != 0) {
				String date = String.valueOf(Calendar.getInstance().getTime()
						.getTime());
				DayLine oDay = MathFactory.parseKLineDaily(am, date);
				if (lstWeekly == null || lstWeekly.isEmpty()) {
					lstWeekly = new ArrayList<CommonLine>();
					lstWeekly.add(convertTo(oDay));
				} else {
					int iLen = lstWeekly.size();
					CommonLine oLastWeekly = lstWeekly.get(iLen - 1);
					Calendar oCldLast = Calendar.getInstance();
					oCldLast.setTime(U.DT.parseYYYYMMDD(oLastWeekly.getToDate()));
					int iLastWeekly = oCldLast.get(Calendar.WEEK_OF_YEAR);

					Calendar oCldNext = Calendar.getInstance();
					oCldNext.setTimeInMillis(Long.parseLong(oDay.getTime()));
					int iNextWeekly = oCldNext.get(Calendar.WEEK_OF_YEAR);

					if (iLastWeekly != iNextWeekly) {
						lstWeekly.add(convertTo(oDay));
					} else {
						Double fHigh = Double.parseDouble(oLastWeekly.getHigh());
						Double fLow = Double.parseDouble(oLastWeekly.getLow());
						Double fHighNew = Double.parseDouble(oDay.getHigh());
						Double fLowNew = Double.parseDouble(oDay.getLow());

						Double lAmount = Double.parseDouble(oLastWeekly.getAmount());
						Double lVolume = Double.parseDouble(oLastWeekly.getVolume());
						Double lAmountNew = Double.parseDouble(oDay.getAmount());
						Double lVolumeNew = Double.parseDouble(oDay.getVolume());

						oLastWeekly.setTime(oDay.getTime());
						oLastWeekly.setHigh(String.valueOf(Math.max(fHigh, fHighNew)));
						oLastWeekly.setLow(String.valueOf(Math.min(fLow, fLowNew)));
						oLastWeekly.setClose(oDay.getClose());
						oLastWeekly.setToDate(U.DT.formatTime(oDay.getTime()));
						oLastWeekly.setAmount(String.valueOf(lAmount + lAmountNew));
						oLastWeekly.setVolume(String.valueOf(lVolumeNew + lVolume));
					}
					if (lstWeekly.size() > BaseConfig.kday) {
						lstWeekly.remove(0);
					}
				}
			}
			if(lstWeekly.size()>size){
				lstWeekly = lstWeekly.subList(lstWeekly.size()-size, lstWeekly.size());
			}
		} catch (Exception e) {
			L.error(e, e.getCause());
			e.printStackTrace();
		}
		return lstWeekly;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IQuoteService#getMonthlyKLine()
	 */
	@Override
	public List<CommonLine> getMonthlyKLine(String sCode,int sSize) {
		List<CommonLine> lstMonthly = null;
		try {
			// 获取该类型下的证券代码
			ActualMarket am = MarketCache.get(sCode);
			// 缓存月K历史数据
			lstMonthly = oCacheDao.getMonthly(sCode);
			if (lstMonthly == null || lstMonthly.isEmpty()) {
				lstMonthly = quoteDao.getMonthlyData(sCode, BaseConfig.kday);
				if (lstMonthly != null) {
					oCacheDao.addMonthly(sCode, lstMonthly);
				}
			}
			/*
			 * if (listDaily == null || listDaily.isEmpty()) { return null; }
			 */
			if (TimeUtils.inNotClose() && BaseConfig.isTradeDay == 1
					&& am.getJk() != 0) {
				String sTime = String.valueOf(Calendar.getInstance().getTime()
						.getTime());
				DayLine oDay = MathFactory.parseKLineDaily(am, sTime);
				if (lstMonthly == null || lstMonthly.isEmpty()) {
					lstMonthly = new ArrayList<CommonLine>();
					lstMonthly.add(convertTo(oDay));
				} else {
					int iLen = lstMonthly.size();
					CommonLine oLastMonthly = lstMonthly.get(iLen - 1);
					Calendar oCldLast = Calendar.getInstance();
					oCldLast.setTime(U.DT.parseYYYYMMDD(oLastMonthly.getToDate()));
					int iLastMonth = oCldLast.get(Calendar.MONTH);

					Calendar oCldNext = Calendar.getInstance();
					oCldNext.setTimeInMillis(Long.parseLong(oDay.getTime()));
					int iNextMonthly = oCldNext.get(Calendar.MONTH);

					if (iLastMonth != iNextMonthly) {
						lstMonthly.add(convertTo(oDay));
					} else {
						Double fHigh = Double.parseDouble(oLastMonthly.getHigh());
						Double fLow = Double.parseDouble(oLastMonthly.getLow());
						Double fHighNew = Double.parseDouble(oDay.getHigh());
						Double fLowNew = Double.parseDouble(oDay.getLow());

						Double lAmount = Double.parseDouble(oLastMonthly.getAmount());
						Double lVolume = Double.parseDouble(oLastMonthly.getVolume());
						Double lAmountNew = Double.parseDouble(oDay.getAmount());
						Double lVolumeNew = Double.parseDouble(oDay.getVolume());

						oLastMonthly.setTime(oDay.getTime());
						oLastMonthly.setHigh(String.valueOf(Math.max(fHigh, fHighNew)));
						oLastMonthly.setLow(String.valueOf(Math.min(fLow, fLowNew)));
						oLastMonthly.setClose(oDay.getClose());
						oLastMonthly.setToDate(U.DT.formatTime(oDay.getTime()));
						oLastMonthly.setAmount(String.valueOf(lAmount + lAmountNew));
						oLastMonthly.setVolume(String.valueOf(lVolumeNew + lVolume));
						oLastMonthly.setOpen(oLastMonthly.getOpen());
					}
					if (lstMonthly.size() > BaseConfig.kday) {
						lstMonthly.remove(0);
					}
				}
			}
			
			if(lstMonthly.size() > sSize){
				lstMonthly = lstMonthly.subList(lstMonthly.size() - sSize, lstMonthly.size());
			}
		} catch (Exception e) {
			L.error(e, e.getCause());
			e.printStackTrace();
		}
		return lstMonthly;
	}
	
	
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IQuoteService#getYearlyKLine(java.lang.String)
	 */
	@Override
	public List<CommonLine> getYearlyKLine(String sCode,int sSize) {
		List<CommonLine> lstYearly = null;
		try {
			// 获取该类型下的证券代码
			ActualMarket am = MarketCache.get(sCode);
			
			lstYearly = oCacheDao.getYearly(sCode);
			if (lstYearly == null) {
				lstYearly = quoteDao.getYearlyData(sCode, BaseConfig.kday);
				if (lstYearly != null) {
					// 缓存年K历史数据
					oCacheDao.addYearly(sCode, lstYearly);
				}
			}
			/*
			 * if (listDaily == null || listDaily.isEmpty()) { return null; }
			 */
			if (TimeUtils.inNotClose() && BaseConfig.isTradeDay == 1
					&& am.getJk() != 0) {
				String date = String.valueOf(Calendar.getInstance().getTime()
						.getTime());
				DayLine oDay = MathFactory.parseKLineDaily(am, date);
				if (lstYearly == null || lstYearly.isEmpty()) {
					lstYearly = new ArrayList<CommonLine>();
					lstYearly.add(convertTo(oDay));
				} else {
					int iLen = lstYearly.size();
					CommonLine oLastYearly = lstYearly.get(iLen - 1);
					Calendar oCldLast = Calendar.getInstance();
					oCldLast.setTime(U.DT.parseYYYYMMDD(oLastYearly.getToDate()));
					int iLastYear = oCldLast.get(Calendar.YEAR);

					Calendar oCldNext = Calendar.getInstance();
					oCldNext.setTimeInMillis(Long.parseLong(oDay.getTime()));
					int iNextYearly = oCldNext.get(Calendar.YEAR);

					if (iLastYear != iNextYearly) {
						lstYearly.add(convertTo(oDay));
					} else {
						Double fHigh = Double.parseDouble(oLastYearly.getHigh());
						Double fLow = Double.parseDouble(oLastYearly.getLow());
						Double fHighNew = Double.parseDouble(oDay.getHigh());
						Double fLowNew = Double.parseDouble(oDay.getLow());

						Double lAmount = Double.parseDouble(oLastYearly.getAmount());
						Double lVolume = Double.parseDouble(oLastYearly.getVolume());
						Double lAmountNew = Double.parseDouble(oDay.getAmount());
						Double lVolumeNew = Double.parseDouble(oDay.getVolume());

						oLastYearly.setTime(oDay.getTime());
						oLastYearly.setFromDate(oLastYearly.getFromDate());
						oLastYearly.setHigh(String.valueOf(Math.max(fHigh, fHighNew)));
						oLastYearly.setLow(String.valueOf(Math.min(fLow, fLowNew)));
						oLastYearly.setClose(oDay.getClose());
						oLastYearly.setToDate(U.DT.formatTime(oDay.getTime()));
						oLastYearly.setAmount(String.valueOf(lAmount + lAmountNew));
						oLastYearly.setVolume(String.valueOf(lVolumeNew + lVolume));
						oLastYearly.setOpen(oLastYearly.getOpen());
					}
					if (lstYearly.size() > BaseConfig.kday) {
						lstYearly.remove(0);
					}
				}
			}
			if(lstYearly.size() > sSize){
				lstYearly = lstYearly.subList(lstYearly.size() - sSize, lstYearly.size());
			}
		} catch (Exception e) {
			L.error(e, e.getCause());
			e.printStackTrace();
		}
		return lstYearly;
	}
	
	/*
	 * @param oDaily
	 * @return
	 */
	public static CommonLine convertTo(DayLine oDaily) {
		String sDate = U.DT.formatTime(oDaily.getTime());
		CommonLine oLine = new CommonLine();
		oLine.setAmount(oDaily.getAmount());
		oLine.setClose(oDaily.getClose());
		oLine.setToDate(sDate);
		oLine.setTime(oDaily.getTime());
		oLine.setFromDate(sDate);
		oLine.setHigh(oDaily.getHigh());
		oLine.setLow(oDaily.getLow());
		oLine.setOpen(oDaily.getOpen());
		oLine.setVolume(oDaily.getVolume());
		return oLine;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IQuoteService#getDailyKLine(java.lang.String)
	 */
	@Override
	public List<DayLine> getDailyKLine(String sCode,int sSize) {
		// 结果集合
		List<DayLine> lstDaily = null;
		try {
			// 获取该类型下的证券代码
			ActualMarket am = MarketCache.get(sCode);

			lstDaily = oCacheDao.getDaily(sCode);
			if (lstDaily == null || lstDaily.isEmpty()) {
				lstDaily = quoteDao.getDailyData(sCode, BaseConfig.kday);
				if (lstDaily != null) {
					// 缓存年K历史数据
					oCacheDao.addDaily(sCode, lstDaily);
				}
			}
			if (TimeUtils.inNotClose() && BaseConfig.isTradeDay == 1 && am.getJk() != 0) {
				String sTime = String.valueOf(Calendar.getInstance().getTime().getTime());
				DayLine oDaily = MathFactory.parseKLineDaily(am, sTime);
				if (lstDaily == null || lstDaily.isEmpty()) {
					lstDaily = new ArrayList<DayLine>();
					lstDaily.add(oDaily);
				} else {
					int iLen = lstDaily.size();
					//如果是交易日
//					String lastDate = new SimpleDateFormat("yyyyMMdd").format(new Date(Long.valueOf(list.getLast().getTime())));
					String sLastDate = U.DT.formatTime(lstDaily.get(iLen - 1).getTime());
					String nowDate = U.DT.formatYYYYMMDD(new Date());
					if(sLastDate.equals(nowDate)){
						//同一天
						//如果最后一项是当天数据，并且在交易时间内，则进行删除最后一项。
						lstDaily.remove(iLen - 1);
					}
					lstDaily.add(oDaily);
				}
			}
			if (lstDaily.size() > BaseConfig.kday) {
				lstDaily.remove(0);
			}
			
			if(lstDaily.size()>sSize){
				lstDaily = lstDaily.subList(lstDaily.size()-sSize, lstDaily.size());
			}
		} catch (Exception e) {
			L.error(e, e.getCause());
			e.printStackTrace();
		}
		return lstDaily;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IQuoteService#get5MinLine(java.lang.String)
	 */
	@Override
	public List<MinLine> get5MinKLine(String sCode , int iSize) {
		// 结果集合
		List<MinLine> lstMins = null;
		try {
			// 获取该类型下的证券代码
			lstMins = oCacheDao.get5Min(sCode);
			// 5分钟K线 一天只有48个点
			float fPoints = 48;
			int iMinDays = Math.round(BaseConfig.kday / fPoints);
			if (lstMins == null || lstMins.isEmpty() ) {
				// 
				lstMins = quoteDao.get5MinData(sCode, iMinDays);
				if (lstMins != null) {
					// 缓存5分K历史数据
					oCacheDao.add5Min(sCode, lstMins);
				}
			}
			List<MinLine> lstTodayMin = getToday5Min(sCode);
			if (lstMins == null) {
				lstMins = new ArrayList<MinLine>();
			}
			
			if (lstTodayMin != null && !lstTodayMin.isEmpty()){
				lstMins.addAll(lstTodayMin);
			}
			if(lstMins.size() > iSize){
				lstMins = lstMins.subList(lstMins.size() - iSize, lstMins.size());
			}
			
		} catch (Exception e) {
			L.error(e, e.getCause());
			e.printStackTrace();
		}
		return lstMins;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IQuoteService#getToday5Min(java.lang.String)
	 */
	@Override
	public List<MinLine> getToday5Min(String sCode) {
		ActualMarket am = MarketCache.get(sCode);
		List<MinLine> lstMins = null;
		// 下面是生成当天的K线数据
		if (TimeUtils.inNotClose() && BaseConfig.isTradeDay == 1 && am.getJk() != 0) {
			lstMins = new ArrayList<MinLine>();
			List<TimeShare> lstTimes = TimeShareCache.get(sCode);
			lstTimes = U.K.deepCopy(lstTimes);
			try {
				if (lstTimes == null) {
					lstTimes = new ArrayList<TimeShare>();
				}
				TimeShare ts = oService.getTimeShare(sCode);
				if (ts != null) {
					lstTimes.add(ts);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			lstMins = U.K.generate5MinLine(lstTimes, true);
		}
		return lstMins;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IQuoteService#get10MinLine(java.lang.String)
	 */
	@Override
	public List<MinLine> getToday10MinLine(String sCode) {
		ActualMarket am = MarketCache.get(sCode);
		List<MinLine> lstMins = null;
		// 下面是生成当天的K线数据
		if (TimeUtils.inNotClose() && BaseConfig.isTradeDay == 1 && am.getJk() != 0) {
			lstMins = new ArrayList<MinLine>();
			List<TimeShare> lstTimes = TimeShareCache.get(sCode);
			lstTimes = U.K.deepCopy(lstTimes);
			try {
				if (lstTimes == null) {
					lstTimes = new ArrayList<TimeShare>();
				}
				TimeShare ts = oService.getTimeShare(sCode);
				if (ts != null && lstTimes != null) {
					lstTimes.add(ts);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			lstMins = U.K.generate10MinLine(lstTimes, false);
		}
		return lstMins;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IQuoteService#get10MinKLine(java.lang.String)
	 */
	@Override
	public List<MinLine> get10MinKLine(String sCode , int iSize) {
		// 结果集合
		List<MinLine> lstMins = null;
		try {
			// 获取该类型下的证券代码
			lstMins = oCacheDao.get10Min(sCode);
			// 10分钟K线 一天只有24个点
			float fPoints = 24;
			int iMinDays = Math.round(BaseConfig.kday / fPoints);
			if (lstMins == null || lstMins.isEmpty() ) {
				// 
				lstMins = quoteDao.get10MinData(sCode, iMinDays);
				if (lstMins != null) {
					// 缓存10MK历史数据
					oCacheDao.add10Min(sCode, lstMins);
				}
			}
			List<MinLine> lstTodayMin = getToday10MinLine(sCode);
			if (lstMins == null) {
				lstMins = new ArrayList<MinLine>();
			}
			
			if (lstTodayMin != null && !lstTodayMin.isEmpty()){
				lstMins.addAll(lstTodayMin);
			}
			if(lstMins.size() > iSize){
				lstMins = lstMins.subList(lstMins.size() - iSize, lstMins.size());
			}
		} catch (Exception e) {
			L.error(e, e.getCause());
			e.printStackTrace();
		}
		return lstMins;
	}
	
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IQuoteService#getToday15MinLine(java.lang.String)
	 */
	@Override
	public List<MinLine> getToday15MinLine(String sCode) {
		ActualMarket am = MarketCache.get(sCode);
		List<MinLine> lstMins = null;
		// 下面是生成当天的K线数据
		if (TimeUtils.inNotClose() && BaseConfig.isTradeDay == 1 && am.getJk() != 0) {
			lstMins = new ArrayList<MinLine>();
			List<TimeShare> lstTimes = TimeShareCache.get(sCode);
			lstTimes = U.K.deepCopy(lstTimes);
			try {
				if (lstTimes == null) {
					lstTimes = new ArrayList<TimeShare>();
				}
				TimeShare ts = oService.getTimeShare(sCode);
				if (ts != null && lstTimes != null) {
					lstTimes.add(ts);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			lstMins = U.K.generate15MinLine(lstTimes, false);
		}
		return lstMins;
	}
	
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IQuoteService#getToday30MinLine(java.lang.String)
	 */
	@Override
	public List<MinLine> getToday30MinLine(String sCode) {
		ActualMarket am = MarketCache.get(sCode);
		List<MinLine> lstMins = null;
		// 下面是生成当天的K线数据
		if (TimeUtils.inNotClose() && BaseConfig.isTradeDay == 1 && am.getJk() != 0) {
			lstMins = new ArrayList<MinLine>();
			List<TimeShare> lstTimes = TimeShareCache.get(sCode);
			lstTimes = U.K.deepCopy(lstTimes);
			try {
				if (lstTimes == null) {
					lstTimes = new ArrayList<TimeShare>();
				}
				TimeShare ts = oService.getTimeShare(sCode);
				if (ts != null && lstTimes != null) {
					lstTimes.add(ts);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			lstMins = U.K.generate30MinLine(lstTimes, false);
		}
		return lstMins;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IQuoteService#get15MinKLine(java.lang.String)
	 */
	@Override
	public List<MinLine> get15MinKLine(String sCode , int iSize) {
		// 结果集合
		List<MinLine> lstMins = null;
		try {
			// 获取该类型下的证券代码
			lstMins = oCacheDao.get15Min(sCode);
			// 15分钟K线 一天只有16个点
			float fPoints = 16;
			int iMinDays = Math.round(BaseConfig.kday / fPoints);
			if (lstMins == null || lstMins.isEmpty() ) {
				// 
				lstMins = quoteDao.get15MinData(sCode, iMinDays);
				if (lstMins != null) {
					// 缓存年K历史数据
					oCacheDao.add15Min(sCode, lstMins);
				}
			}
			List<MinLine> lstTodayMin = getToday15MinLine(sCode);
			if (lstMins == null) {
				lstMins = new ArrayList<MinLine>();
			}
			if (lstTodayMin != null && !lstTodayMin.isEmpty()){
				lstMins.addAll(lstTodayMin);
			}
			if(lstMins.size() > iSize){
				lstMins = lstMins.subList(lstMins.size() - iSize, lstMins.size());
			}
		} catch (Exception e) {
			L.error(e, e.getCause());
			e.printStackTrace();
		}
		return lstMins;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IQuoteService#get30MinKLine(java.lang.String)
	 */
	@Override
	public List<MinLine> get30MinKLine(String sCode, int iSize) {
		// 结果集合
		List<MinLine> lstMins = null;
		try {
			// 获取该类型下的证券代码
			lstMins = oCacheDao.get30Min(sCode);
			// 10分钟K线 一天只有24个点
			float fPoints = 8;
			int iMinDays = Math.round(BaseConfig.kday / fPoints);
			if (lstMins == null || lstMins.isEmpty() ) {
				// 
				lstMins = quoteDao.get30MinData(sCode, iMinDays);
				if (lstMins != null) {
					// 缓存年K历史数据
					oCacheDao.add30Min(sCode, lstMins);
				}
			}
			List<MinLine> lstTodayMin = getToday30MinLine(sCode);
			if (lstMins == null) {
				lstMins = new ArrayList<MinLine>();
			} 
			if(lstTodayMin != null && !lstTodayMin.isEmpty()){
				lstMins.addAll(lstTodayMin);
			}
			if(lstMins.size() > iSize){
				lstMins = lstMins.subList(lstMins.size() - iSize, lstMins.size());
			}
		} catch (Exception e) {
			L.error(e, e.getCause());
			e.printStackTrace();
		}
		return lstMins;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IQuoteService#get60MinKLine(java.lang.String)
	 */
	@Override
	public List<MinLine> get60MinKLine(String sCode, int iSize) {
		List<MinLine> lstMins = null;
		try {
			// 获取该类型下的证券代码
			lstMins = oCacheDao.get60Min(sCode);
			// 10分钟K线 一天只有24个点
			float fPoints = 4;
			int iMinDays = Math.round(BaseConfig.kday / fPoints);
			if (lstMins == null || lstMins.isEmpty() ) {
				// 
				lstMins = quoteDao.get60MinData(sCode, iMinDays);
				if (lstMins != null) {
					// 缓存年K历史数据
					oCacheDao.add60Min(sCode, lstMins);
				}
			}
			List<MinLine> lstTodayMin = getToday60MinLine(sCode);
			if (lstMins == null) {
				lstMins = new ArrayList<MinLine>();
			} 
			if (lstTodayMin != null && !lstTodayMin.isEmpty()){
				lstMins.addAll(lstTodayMin);
			}
			if(lstMins.size() > iSize){
				lstMins = lstMins.subList(lstMins.size() - iSize, lstMins.size());
			}
		} catch (Exception e) {
			L.error(e, e.getCause());
			e.printStackTrace();
		}
		return lstMins;
	}

	public List<MinLine> getToday60MinLine(String sCode) {
		ActualMarket am = MarketCache.get(sCode);
		List<MinLine> lstMins = null;
		// 下面是生成当天的K线数据
		if (TimeUtils.inNotClose() && BaseConfig.isTradeDay == 1 && am.getJk() != 0) {
			lstMins = new ArrayList<MinLine>();
			List<TimeShare> lstTimes = TimeShareCache.get(sCode);
			lstTimes = U.K.deepCopy(lstTimes);
			try {
				if (lstTimes == null) {
					lstTimes = new ArrayList<TimeShare>();
				}
				TimeShare ts = oService.getTimeShare(sCode);
				if (ts != null && lstTimes != null) {
					lstTimes.add(ts);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			lstMins = U.K.generate60MinLine(lstTimes, false);
		}
		return lstMins;
	}
}
