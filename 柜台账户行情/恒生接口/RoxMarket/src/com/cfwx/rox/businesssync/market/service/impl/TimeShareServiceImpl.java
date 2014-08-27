package com.cfwx.rox.businesssync.market.service.impl;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.service.ITimeShareService;
import com.cfwx.rox.businesssync.market.show.TimeShare;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.structure.TimeShareCache;
import com.cfwx.rox.businesssync.market.util.GhostUtils;
import com.cfwx.rox.businesssync.market.util.MathFactory;

/**
 * @author J.C.J
 * 
 * 2013-11-27        
 */
@Service
public class TimeShareServiceImpl implements ITimeShareService{

	private final static Logger LOG = Logger.getLogger(TimeShareServiceImpl.class);
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
	
	private final static SimpleDateFormat dateSDF = new SimpleDateFormat("yyyyMMdd");
	
	private final static SimpleDateFormat timeSDF = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static final String TIME = "150000";
	/* 切记 NumberFormat不是线程安全的 */
	public NumberFormat nf0;
	
	public TimeShareServiceImpl() {
		nf0 = NumberFormat.getInstance();
		nf0.setRoundingMode(RoundingMode.HALF_UP);
		nf0.setMaximumFractionDigits(0);
		nf0.setGroupingUsed(false);  
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.ITimeShareService#writeMemory()
	 */
	@Override
	public void write() throws Exception {
		
		try {
			writeTSMemory();
			if(BaseConfig.ghost==1)
				writeFile();
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	private void writeTSMemory() throws Exception {
		
		Date date = new Date();
		long now = Long.valueOf(sdf.format(date));
		long time = date.getTime();
		if(now <931){
			//9点31分以前只写一个点，即9点30分钟以前的数据
			Iterator<String> it = MarketCache.iterator();
			String key ="";
			ActualMarket am = null;
			TimeShare tempTS = null;
			while(it.hasNext()){
					try {
						key = it.next();
						am = MarketCache.get(key);
						tempTS = MathFactory.parseTimeShare(am, String.valueOf(time));
						TimeShareCache.addBeforeOpen(am.getPcode(), tempTS);
					} catch (Exception e) {
						LOG.error(e.getMessage(),e);
					}
			}
		}else if(now == 931){
			//9点31分，9点30分的数据结束。写入9点30的数据
			//9点31分以前只写一个点，即9点30分钟的数据,总量
			Iterator<String> it = MarketCache.iterator();
			String key ="";
			ActualMarket am = null;
			TimeShare tempTS = null;
			while(it.hasNext()){
					try {
						key = it.next();
						am = MarketCache.get(key);
						tempTS = MathFactory.parseTimeShare(am, String.valueOf(time-60000));
						TimeShareCache.addBeforeOpen(am.getPcode(), tempTS);
					} catch (Exception e) {
						LOG.error(e.getMessage(),e);
					}
			}
		}else if(now >1501){
			//15点以后
			String after = dateSDF.format(date)+TIME;
			after = timeSDF.parse(after).getTime()+"";
			
			//把当前所有股票行情信息写入内存。
			Iterator<String> it = MarketCache.iterator();
			String key ="";
			ActualMarket am = null;
			List<TimeShare> list = null;
			TimeShare beforeTS = null;
			TimeShare tempTS = null;
			while(it.hasNext()){
					try {
						key = it.next();
						am = MarketCache.get(key);
						list = TimeShareCache.get(am.getPcode());
						
						tempTS = MathFactory.parseTimeShare(am, after);
						
						if(list!= null && list.size()>1){
							beforeTS = list.get(list.size()-2);
							if(beforeTS != null){
								double dVol = tempTS.getZl() - beforeTS.getZl();
								//计算分钟增量
								tempTS.setVolume(nf0.format(dVol > 0 ? dVol : 0D));
							}else{
//								if(am.getBigType()!=2)	
//									tempTS.setAverage(nf3.format(tempTS.getZe()/(tempTS.getZl()*100D)));
							}
							//更新最后一项
							list.set(list.size()-1, tempTS);
						}
						else{
							TimeShareCache.addMarket(am.getPcode(), tempTS);
						}
					} catch (Exception e) {
						LOG.error(e.getMessage(),e);
					}
			}
		}else{
			//交易时间内
			//第二个点开始(9点31分)开始，到15.00点结束，数据是在当前分钟结束后才进行写入的，写入时减去一分钟的毫秒数
			Iterator<String> it = MarketCache.iterator();
			String key ="";
			ActualMarket am = null;
			TimeShare beforeTS = null;
			TimeShare tempTS = null;
				while(it.hasNext()){
					try {
						key = it.next();
						am = MarketCache.get(key);
						
						beforeTS = TimeShareCache.getLast(am.getPcode());
						
						tempTS = MathFactory.parseTimeShare(am, String.valueOf(time-60000));
						
						if(beforeTS != null){
							double dVol = tempTS.getZl() - beforeTS.getZl();
							//计算分钟增量
							tempTS.setVolume(nf0.format(dVol > 0 ? dVol : 0D));
							tempTS.setAmount(nf0.format(tempTS.getZe()-beforeTS.getZe()>0?tempTS.getZe()-beforeTS.getZe():0D));
						}else{
//							if(am.getBigType()!=2)
//								tempTS.setAverage(nf3.format(tempTS.getZe()/(tempTS.getZl()*100D)));
						}
						TimeShareCache.addMarket(am.getPcode(),tempTS);
					} catch (Exception e) {
						LOG.error(e.getMessage(),e);
					}
				}
		}
	}
	
	private void writeFile(){
		try {
			if(TimeShareCache.getData() != null){
				long s = System.currentTimeMillis();
				GhostUtils.writeTimeShare();
				long e = System.currentTimeMillis();
				LOG.info("分时文件存储耗时：----------"+(e-s)+"--size:"+TimeShareCache.get("sh000001").size());
			}
				
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}

}
