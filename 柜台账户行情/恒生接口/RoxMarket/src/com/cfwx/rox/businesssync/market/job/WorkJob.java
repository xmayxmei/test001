package com.cfwx.rox.businesssync.market.job;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.service.IWorkService;
import com.cfwx.rox.businesssync.market.service.ITimeShareService;
import com.cfwx.rox.businesssync.market.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author J.C.J
 * 开盘作业，收盘作业，分时数据写入,K线数据写入。
 * 2013-8-17
 */
public class WorkJob {
	
	private final static Logger LOG = Logger
			.getLogger(WorkJob.class);
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("HHmmss");
	
	private String dateTime = "";
	
	@Autowired
	ITimeShareService tsServcie;
	@Autowired
	IWorkService workservice;
	
	public  void  write(){
		
		try {
			dateTime = SDF.format(new Date());
			//判断是否当前日期是否交易日，是否需要写入及保存数据
//			if(dateTime.equals(BaseConfig.initTime)){
//				//开盘作业
//				workservice.openWork();
//			}
			//2秒内
			if(Long.valueOf(dateTime)>= Long.valueOf(BaseConfig.initTime) && Long.valueOf(dateTime)<= Long.valueOf(BaseConfig.initTime)+2){
				//开盘作业
				workservice.openWork();
			}
			
			if(TimeUtils.isAddDayLineTime(Long.valueOf(dateTime))){
				//开盘作业
				workservice.addDayLine();
			}
			
			if (BaseConfig.isTradeDay == 1) {

				// 时间段内运行任务
				if (TimeUtils.inTimeArea(dateTime)) {
					tsServcie.write();
				}

				if (dateTime.equals(BaseConfig.closewktime)) {
					// 收盘作业
					workservice.closeWork();
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
}
