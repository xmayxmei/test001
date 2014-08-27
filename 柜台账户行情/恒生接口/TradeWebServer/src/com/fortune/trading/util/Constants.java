package com.fortune.trading.util;

/**
 * <code>Constants</code>
 * 
 * @author Colin, Jimmy
 *
 */
public class Constants {
	/** 模式： 0 为测试， 1为正常 **/
	public static int mode ;
	/** 交易端Host*/
	public static String tradingHost;
	/** 交易端端口*/
	public static String tradingPort;
	/** 行情获取URL*/
	public static String quoteURL;
	/** 行情获取WebSocket 地址 */
	public static String quoteWSURL;
	/** 是否锁定账户 */
	public static boolean isSupportLockAcc;
	/** 允许密码输入错误的次数 */
	public static int limitedTimes ;
	/** 账户锁定的时间 */
	public static long accLockTimes;
	
	public static String quoteURLForWsRequest;
	/** 微信返回登陆链接的有效时间 单位为毫秒 */
	public static final int URL_KEEP_ALIVE = 15000;
	/** 登录时，是否支持RSA加密 */
	public static boolean isSupportEncry;
	/** 是否保存用户的操作记录到数据库 */
	public static boolean isSupportLogJDBC;
	/** 是否支持socket */
	public static boolean isSupportSocket;
	/**op_station prefix */
	public static String opStationPrefix;
	
	public static final class SK{
		/** 登录界面的随机数 索引*/
		public static final String LOGIN_PAGE_RANID = "login.page.ranid";
		/** 下单页面的随机数 索引*/
		public static final String PLACEORDER_PAGE_RANID = "placeorder.page.ranid";
		/** 修改密码页面的随机数 索引*/
		public static final String CHANGEPSW_PAGE_RANID = "changepsw.page.ranid";
		/** 撤单页面的随机数 索引*/
		public static final String CANCELORDER_PAGE_RANID = "cancelorder.page.ranid";
		/** */
		public static final String CAPTCHA_CODE = "captcha.code";
		/** */
		public static final String LOGIN_REQUEST_TIMEMILLIS= "currentTime";
		
		public static final String LOGIN_USER = "login.user";
		
		public static final String RSA_PRIVATE_KEY = "rsa.private.key";
	}
	
}
