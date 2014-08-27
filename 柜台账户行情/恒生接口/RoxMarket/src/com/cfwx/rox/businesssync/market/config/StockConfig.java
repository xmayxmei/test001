package com.cfwx.rox.businesssync.market.config;

/**
 * @author J.C.J
 *
 * 2013-11-18
 */
public class StockConfig {
	
	/**
	 * 1.上证转码个股代码特征,正则表达式匹配前置匹配，如600,匹配以600开头的股票信息。字段为集合，中间用逗号间隔。
	 */
	public static String dcdsh;
	/**
	 * 2.深证转码个股代码特征	,正则表达式匹配前置匹配，如600,匹配以600开头的股票信息。字段为集合，中间用逗号间隔。
	 */
	public static String dcdsz;
	/**
	 * 3.上证不收盘个股代码特征
	 */
	public static String notClosesh;
	/**
	 * 4.深证不收盘个股代码特征
	 */
	public static String notClosesz;
	/**
	 * 5.交易时间段读写时间,HHmmss集合  "091500,113000,130000,153000" 4个数字组合,逗号间隔
	 */
	public static String timeArea="091500,113000,130000,153000";

	/**
	 * 不排序类别
	 */
	public static String notSortType ="";
	
	/**
	 * 默认排序字段
	 */
	public static String defaultSortField = "";
	
	/**
	 * 上交所DBF超时时间
	 */
	public static long shReadTime = 0;
	
	/**
	 * 深交所DBF超时时间
	 */
	public static long szReadTime = 0;
	
	public static String[] getDcdsh(){
		return dcdsh.split(",");
	}
	
	public static String[] getDcdsz(){
		return dcdsz.split(",");
	}
	
	public static void setReadTime(String time){
		String[] arr = time.split(",");
		shReadTime= Long.valueOf(arr[0]);
		szReadTime= Long.valueOf(arr[1]);
	}
	
	//-----------------------------信息中心提供的数据过滤配置
	public static String filter ;
	
	/**
	 * 大盘指数配置
	 */
	public static String zs;
	
	/**
	 * 客户端排行显示默认指数
	 */
	public static String dzs;
	
	/**
	 * 我的关注默认证券
	 */
	public static String gzdzs;
}
