/**
 * 
 */
package com.cfwx.rox.businesssync.market.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.config.StockConfig;
import com.cfwx.rox.businesssync.market.wsclient.EjdbWebService;

/**
 * @author J.C.J
 *
 * 2013-11-28
 */
public class TimeUtils {

	private static final String DATEFORMAT = "HHmmss";
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat(DATEFORMAT);
	
	private static final SimpleDateFormat TRADESDF = new SimpleDateFormat("yyyy-MM-dd");
	
	private static long t1 ;
	private static long t2 ;
	private static long t3 ;
	private static long t4 ;
	
	/**
	 * 判断是否在配置的时间范围内
	 * @return
	 */
	public static  boolean inTimeArea(){
		long temp =Long.parseLong(SDF.format(new Date()));
		if((temp>=t1 && temp<=t2+100) || (temp>=t3-100 && temp<=t4))
			return true;
		else
			return false;
	} 
	
	/**
	 * 判断是否在配置的时间范围内
	 * @return
	 */
	public static  boolean inTimeArea(String date){
		long temp =Long.parseLong(date);
		
		if((temp>=t1 && temp<=t2+100) || (temp>=t3+100 && temp<=t4) )
			return true;
		else
			return false;
	}
	
	/**
	 * 判断是否在配置的时间范围内
	 * @return
	 */
	public static  boolean inTrade(){
		long temp =Long.parseLong(SDF.format(new Date()));
		
		if((temp>=t1 && temp<=t4) )
			return true;
		else
			return false;
	}
	
	public static boolean inNotClose() {
		long temp = Long.parseLong(SDF.format(new Date()));
		if ((temp >= t1 && temp <= Long.parseLong(BaseConfig.closewktime))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void initTrade()throws Exception{
		try {
			//判断当前日期是否是交易日
			String s =  new EjdbWebService().getEjdbWebServiceSoap().getData("GetJYRXX_WS|RQ="+TRADESDF.format(new Date()));
			if(s.matches(".*1.*"))
				BaseConfig.isTradeDay=1;
			else
				BaseConfig.isTradeDay=0;
			init4Time();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获取交易日webservice失败...");
		}
	}
	
	private static void init4Time(){
		String[] arr = StockConfig.timeArea.split(",");
		 t1 = Long.parseLong(arr[0]);
		 t2 = Long.parseLong(arr[1]);
		 t3 = Long.parseLong(arr[2]);
		 t4 = Long.parseLong(arr[3]);
	}
	
	/**
	 * 是否在推送时间段内
	 * @return
	 */
	public static boolean canSend(){
		if(BaseConfig.isTradeDay==1){
			long temp =Long.parseLong(SDF.format(new Date()));
			
			if((temp>=t1 && temp<=t2+100) || (temp>=t3 && temp<=t4) )
				return true;
			else
				return false;
		}else{
			return false;
		}
	}
	/**
	 * K线数据是否要替换，显示K线数据时使用
	 * @return
	 */
	public static boolean needChange(){
		if(BaseConfig.isTradeDay==1){
			long temp =Long.parseLong(SDF.format(new Date()));
			if((temp>Long.valueOf(BaseConfig.initTime) && temp<t4))
				return true;
			else
				return false;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 判断是否在配置的时间范围内
	 * @return
	 */
	public static  boolean isNeedReOpen(){
		
		long temp =Long.parseLong(SDF.format(new Date()));
		if( BaseConfig.isTradeDay==1 && (temp>=t1 && temp<Long.valueOf(BaseConfig.initTime.trim())  ) )
			return true;
		else
			return false;
	}
	
	/**
	 * 判断是否在配置的时间范围内
	 * @return
	 */
	public static  boolean isAddDayLineTime(long time){
		if( BaseConfig.isTradeDay==1 && (time==t1) )
			return true;
		else
			return false;
	}
	
}
