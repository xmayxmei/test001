/**
 * 
 */
package com.cfwx.rox.businesssync.market.service.impl;

import static com.cfwx.rox.businesssync.market.service.impl.QuoteServiceImpl.convertTo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao;
import com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao;
import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.service.IDBFStockData;
import com.cfwx.rox.businesssync.market.service.IWorkService;
import com.cfwx.rox.businesssync.market.show.CommonLine;
import com.cfwx.rox.businesssync.market.show.DayLine;
import com.cfwx.rox.businesssync.market.show.MinLine;
import com.cfwx.rox.businesssync.market.show.TimeShare;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.structure.TimeShareCache;
import com.cfwx.rox.businesssync.market.util.GhostUtils;
import com.cfwx.rox.businesssync.market.util.MathFactory;
import com.cfwx.rox.businesssync.market.util.TimeUtils;
import com.cfwx.util.U;

/**
 * @author J.C.J
 *
 * 2013-11-28
 */
@Service
public class WorkServiceImpl implements IWorkService{

	private final static Logger LOG = Logger.getLogger(WorkServiceImpl.class);
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	@Autowired
	private IQuoteHistoryDao quoteDao;
	@Autowired
	private IQuoteCacheDao oCacheDao;
	@Autowired
	IDBFStockData dbfStock;
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.ICloseWorkService#closeWork()
	 */
	@Override
	public void closeWork() {
		LOG.info("开始收盘作业...");
//		writeMemory();
		// 分时
		writeTSFile();
		// K线数据备份 (日K、月K、周K，年K)
		genAndSaveKLine();
		// 删除K线历史缓存
		oCacheDao.clearAllKLineCache();
		
		LOG.info("收盘作业已完成...");
	}
	
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IWorkService#openWork()
	 */
	@Override
	public void openWork() {
		
		/* 交易日开盘作业内容
		 * 1.更新股票信息以及相关索引
		 * 2.添加行情昨收价
		 * 3.初始化分时数据
		 * 4.初始化K线数据(日K)
		*/
		LOG.info("开始开盘作业...");
		try {
			//更新是否是交易日标识
			TimeUtils.initTrade();
			if(BaseConfig.isTradeDay==1){
				//1.更新股票信息以及相关索引
				dbfStock.updateStockInfo(1);
				
				//2.初始化分时数据
				TimeShareCache.clear();
				
				//3.删除分时文件
				File fos = new File(TimeShareCache.ghostUrl);  
		    	File[] files = fos.listFiles();
		    	if (files != null) {
		    		for(File f :files){
			    		f.delete();
			    	}
		    	}

				//3.清理行情，只保留昨收价
				dbfStock.clearMarketShowData();
				
				LOG.info("-----------------------");
				LOG.info(sdf.format(new Date())+"开盘作业已完成...");
			}else{
				LOG.info(sdf.format(new Date())+" 读取为非交易日，无需开盘作业...");
			}
		} catch (Exception e) {
			LOG.error("开盘作业失败："+e.getMessage(),e);
		}
	}
	
	public void addDayLine()throws Exception{
		/*if(BaseConfig.isTradeDay==1){
			//3.初始化K线数据(日K)
			KLineDailyCache.initNewDayLine();
		}*/
	}
	
/*	private void writeMemory(){
		//写入K线数据
				Iterator<String> it=MarketCache.iterator();
				
				String key = "";
				ActualMarket am = null;
				Date date =  new Date();
				String dateStr = date.getTime()+"";
				
				String todayDate = sdf.format(date);
				
				while(it.hasNext()){
					key = it.next();
					am = MarketCache.get(key);
					if(am.getJk()==0){
						//今开价为0的，即表示当天没有开盘。没有开盘的就不进行收盘
						continue;
					}
					try {
						if(KLineDailyCache.get(key)!=null){
							//判断当前最后一项是否为当天，是则替换，不是则添加
							if(todayDate.equals(sdf.format(new Date(Long.valueOf(KLineDailyCache.get(key).getLast().getTime()))))){
								KLineDailyCache.replaceMarket(am.getPcode(), MathFactory.parseKLineDaily(am, dateStr));
							}else{
								KLineDailyCache.addDay(am.getPcode(), MathFactory.parseKLineDaily(am, dateStr));
							}
						}else{
							KLineDailyCache.addDay(am.getPcode(), MathFactory.parseKLineDaily(am, dateStr));
						}
					} catch (Exception e) {
						LOG.error(key+":"+e.getMessage(),e);
					}
				}
	}*/
	
	private void writeTSFile(){
		try {
			/*if(KLineDailyCache.getData() != null) {
//				GhostUtils.writeDayLine();
			}*/
			
			//如果备份文件方式是2，即每日备份一次分时数据，则如下
			if( BaseConfig.ghost==2 && TimeShareCache.getData() != null ){
				long s = System.currentTimeMillis();
				GhostUtils.writeTimeShareAll();
				long e = System.currentTimeMillis();
				List<TimeShare> lst = TimeShareCache.get("sh000001");
				LOG.info("分时文件存储耗时：----------" + (e-s) + "--size:" + (lst != null ? lst.size() : 0));
			}
				
			
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	public void writeDayLine()throws Exception{
		/*try {
			if(TimeShareCache.getData() != null)
			GhostUtils.write(KLineDailyCache.ghostUrl, KLineDailyCache.getData());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new Exception(e);
		}*/
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IWorkService#openWork()
	 */
	@Override
	public void openWorkByMan() {
		
		/* 交易日开盘作业内容
		 * 1.更新股票信息以及相关索引
		 * 2.添加行情昨收价
		 * 3.初始化分时数据
		 * 4.初始化K线数据(日K)
		*/
		try {
			//更新是否是交易日标识
			TimeUtils.initTrade();
			if(BaseConfig.isTradeDay==1){
				//1.更新股票信息以及相关索引
				dbfStock.updateStockInfo(1);
				//2.清理行情，只保留昨收价
				dbfStock.clearMarketShowData();
				//3.初始化分时数据
				TimeShareCache.clear();
				
				LOG.info("-----------------------");
				LOG.info(sdf.format(new Date())+"开盘作业已完成...");
			}else{
				LOG.info(sdf.format(new Date())+" 读取为非交易日，无需开盘作业...");
			}
		} catch (Exception e) {
			LOG.error("开盘作业失败："+e.getMessage(),e);
		}
	}
	/**
	 * 生成并保存K线数据
	 */
	public void genAndSaveKLine() {
		Iterator<String> it = MarketCache.iterator();
		String key = "";
		ActualMarket am = null;
		String sTime = Calendar.getInstance().getTime().getTime() + "";
		while (it.hasNext()) {
			key = it.next();
			am = MarketCache.get(key);
			if (am.getJk() == 0) {
				// 今开价为0的，即表示当天没有开盘。没有开盘的就不进行写入日线
				continue;
			}
			try {
				//保存5分钟
				List<TimeShare> lstTimes = TimeShareCache.get(key);
				List<MinLine> lstMins = U.K.generate5MinLine(lstTimes, true);
				if (lstMins != null) {
					int iLen = lstMins.size();
					for (int i = 0 ; i < iLen; i++) {
						quoteDao.save5MinData(key, lstMins.get(i));
					}
				}
				//保存10分钟
				/*List<TimeShare> lst10Times = TimeShareCache.get(key);
				List<MinLine> lst10Mins = U.K.generate10MinLine(lst10Times, false);
				if (lst10Mins != null) {
					int iLen = lst10Mins.size();
					for (int i = 0 ; i < iLen; i++) {
						quoteDao.save10MinData(key, lst10Mins.get(i));
					}
				}*/
				//保存15分钟
				List<MinLine> lst15Mins = U.K.generate15MinLine(lstTimes, true);
				if (lst15Mins != null) {
					int iLen = lst15Mins.size();
					for (int i = 0 ; i < iLen; i++) {
						quoteDao.save15MinData(key, lst15Mins.get(i));
					}
				}
				//保存30分钟
				List<MinLine> lst30Mins = U.K.generate30MinLine(lstTimes, true);
				if (lst30Mins != null) {
					int iLen = lst30Mins.size();
					for (int i = 0 ; i < iLen; i++) {
						quoteDao.save30MinData(key, lst30Mins.get(i));
					}
				}
				//保存60分钟
				List<MinLine> lst60Mins = U.K.generate60MinLine(lstTimes, true);
				if (lst60Mins != null) {
					int iLen = lst60Mins.size();
					for (int i = 0 ; i < iLen; i++) {
						quoteDao.save60MinData(key, lst60Mins.get(i));
					}
				}
				// 保存日线
				DayLine oDaily = MathFactory.parseKLineDaily(am, sTime);
				quoteDao.saveDailyData(key, oDaily);
				// 生成周K 并保存
				CommonLine[] aWeeklys = generateWeekly(key, oDaily);
				CommonLine oOldWeekly = aWeeklys[0];
				CommonLine oNewWeekly = aWeeklys[1];
				if (oOldWeekly == null) {
					quoteDao.saveWeeklyData(key, oNewWeekly);
				} else {
					quoteDao.updateWeeklyData(key, oOldWeekly, oNewWeekly);
				}
				// 生成月K 并保存
				CommonLine[] aMonthlys = generateMonthly(key, oDaily);
				CommonLine oOldMonthly = aMonthlys[0];
				CommonLine oNewMonthly = aMonthlys[1];
				if (oOldMonthly == null) {
					quoteDao.saveMonthlyData(key, oNewMonthly);
				} else {
					quoteDao.updateMonthlyData(key, oOldMonthly, oNewMonthly);
				}
				// 生成月年K 并保存
				/*CommonLine[] ayearlys = generateMonthly(key, oDaily);
				CommonLine oOldYearly = ayearlys[0];
				CommonLine oNewYearly = ayearlys[1];
				if (oOldMonthly == null) {
					quoteDao.saveYearlyData(key, oNewYearly);
				} else {
					quoteDao.updateYearlyData(key, oOldYearly, oNewYearly);
				}*/
			} catch (Exception e) {
				LOG.error(key + ":" + e.getMessage(), e);
			}
		}
	}
	/*
	 * @param sCode
	 * @param oDaily
	 * @return
	 */
	private CommonLine[] generateWeekly(String sCode, DayLine oNewDaily) {
		//获取最新的那条数据
		List<CommonLine> oWeek = quoteDao.getWeeklyData(sCode, 1);
		if (oWeek == null || oWeek.isEmpty()) {
			return new CommonLine[]{null, convertTo(oNewDaily)};
		}
		CommonLine oLastWeek = oWeek.get(0);
		String sTime = oLastWeek.getTime();
		Calendar oCld = Calendar.getInstance();
		oCld.setTimeInMillis(Long.parseLong(sTime));
		int iWeekOfYear = oCld.get(Calendar.WEEK_OF_YEAR);
		
		String sTimeNew = oNewDaily.getTime();
		Calendar oCldNew = Calendar.getInstance();
		oCldNew.setTimeInMillis(Long.parseLong(sTimeNew));
		int iWeekOfYearNew = oCldNew.get(Calendar.WEEK_OF_YEAR);
		// 不同周就直接返回
		if (iWeekOfYear != iWeekOfYearNew) {
			return new CommonLine[]{null, convertTo(oNewDaily)};
		}
        Double fHigh = Double.parseDouble(oLastWeek.getHigh());
        Double fLow = Double.parseDouble(oLastWeek.getLow());
        Double fHighNew = Double.parseDouble(oNewDaily.getHigh());
        Double fLowNew = Double.parseDouble(oNewDaily.getLow());
        
        Double lAmount = Double.parseDouble(oLastWeek.getAmount());
        Double lVolume = Double.parseDouble(oLastWeek.getVolume());
        Double lAmountNew = Double.parseDouble(oNewDaily.getAmount());
        Double lVolumeNew = Double.parseDouble(oNewDaily.getVolume());
        
        CommonLine oWeekNew = new CommonLine();
        oWeekNew.setFromDate(oLastWeek.getFromDate());
        oWeekNew.setHigh(String.valueOf(Math.max(fHigh, fHighNew)));
        oWeekNew.setLow(String.valueOf(Math.min(fLow, fLowNew)));
        oWeekNew.setClose(oNewDaily.getClose());
        oWeekNew.setToDate(U.DT.formatTime(sTimeNew));
        oWeekNew.setAmount(String.valueOf(lAmount + lAmountNew));
        oWeekNew.setVolume(String.valueOf(lVolumeNew + lVolume));
        oWeekNew.setOpen(oLastWeek.getOpen());
        return new CommonLine[]{oLastWeek, oWeekNew};
	}
	/*
	 * @param sCode
	 * @param oDaily
	 * @return
	 */
	private CommonLine[] generateMonthly(String sCode, DayLine oNewDaily) {
		//获取最新的那条数据
		List<CommonLine> oMonthly = quoteDao.getMonthlyData(sCode, 1);
		if (oMonthly == null || oMonthly.isEmpty()) {
			return new CommonLine[]{null, convertTo(oNewDaily)};
		}
		CommonLine oLastWeek = oMonthly.get(0);
		String sTime = oLastWeek.getTime();
		Calendar oCld = Calendar.getInstance();
		oCld.setTimeInMillis(Long.parseLong(sTime));
		int iMonthOfYear = oCld.get(Calendar.MONTH);
		
		String sTimeNew = oNewDaily.getTime();
		Calendar oCldNew = Calendar.getInstance();
		oCldNew.setTimeInMillis(Long.parseLong(sTimeNew));
		int iMonthOfYearNew = oCldNew.get(Calendar.MONTH);
		// 不同周就直接返回
		if (iMonthOfYear != iMonthOfYearNew) {
			return new CommonLine[]{null, convertTo(oNewDaily)};
		}
        Double fHigh = Double.parseDouble(oLastWeek.getHigh());
        Double fLow = Double.parseDouble(oLastWeek.getLow());
        Double fHighNew = Double.parseDouble(oNewDaily.getHigh());
        Double fLowNew = Double.parseDouble(oNewDaily.getLow());
        
        Double lAmount = Double.parseDouble(oLastWeek.getAmount());
        Double lVolume = Double.parseDouble(oLastWeek.getVolume());
        Double lAmountNew = Double.parseDouble(oNewDaily.getAmount());
        Double lVolumeNew = Double.parseDouble(oNewDaily.getVolume());
        
        CommonLine oMonthlyNew = new CommonLine();
        oMonthlyNew.setFromDate(oLastWeek.getFromDate());
        oMonthlyNew.setHigh(String.valueOf(Math.max(fHigh, fHighNew)));
        oMonthlyNew.setLow(String.valueOf(Math.min(fLow, fLowNew)));
        oMonthlyNew.setClose(oNewDaily.getClose());
        oMonthlyNew.setToDate(U.DT.formatTime(sTimeNew));
        oMonthlyNew.setAmount(String.valueOf(lAmount + lAmountNew));
        oMonthlyNew.setVolume(String.valueOf(lVolumeNew + lVolume));
        oMonthlyNew.setOpen(oLastWeek.getOpen());
        return new CommonLine[]{oLastWeek, oMonthlyNew};
	}
//	@SuppressWarnings("unchecked")
//	private void initMarketData()throws Exception{
//		//获取前一个交易日最后保存的行情信息,即最后行情信息
//		Map<String,ActualMarket> ghostData = (Map<String,ActualMarket>)GhostUtils.getObject(MarketCache.ghostUrl);
//		
//		if(ghostData != null){
//			Iterator<String> it = MarketCache.iterator();
//			
//			String key ="";
//			ActualMarket am  = null;
//			ActualMarket temp  = null;
//			while(it.hasNext()){
//				key = it.next();
//				am =MarketCache.get(key);
//				
//				temp = ghostData.get(key);
//				if(temp!= null){
//					am.setZs(temp.getZx());
//				}
//			}
//		}
//	}

}
