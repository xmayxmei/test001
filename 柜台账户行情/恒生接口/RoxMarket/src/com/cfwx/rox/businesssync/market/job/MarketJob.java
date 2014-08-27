package com.cfwx.rox.businesssync.market.job;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SimpleTriggerBean;

import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.service.IDBFStockData;
import com.cfwx.rox.businesssync.market.service.IShDBFHQData;
import com.cfwx.rox.businesssync.market.service.ISzDBFHQData;
import com.cfwx.rox.businesssync.market.util.TimeUtils;

/**
 * @author J.C.J
 *
 * 2013-8-17
 */
public class MarketJob {
	@Autowired
	private SimpleTriggerBean shMarketTrigger;
	
	private final static Logger LOG = Logger
			.getLogger(MarketJob.class);
	
	private boolean initTag = false;
	
	@Autowired
	IShDBFHQData shdbfhq;
	@Autowired
	ISzDBFHQData szdbfhq;
	@Autowired
	IDBFStockData dbfStock;
	
	public  void  updateMarketInfo(){
		
		try {
			//设置任务间隔时间
			if(!initTag)
				initInterval();
			
			//判断是否当前日期是否交易日，是否需要写入及保存数据
			if(BaseConfig.isTradeDay==1){
				//时间段内运行任务
				if(TimeUtils.inTrade()){
					shdbfhq.getHQDataToMemory();
					szdbfhq.getHQDataToMemory();
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	private void initInterval(){
		initTag =true;
		if(BaseConfig.readInterval!=0){
			shMarketTrigger.setRepeatInterval(BaseConfig.readInterval);
			LOG.info("设置间隔时间为"+BaseConfig.readInterval+"毫秒");
		}
	}
}
