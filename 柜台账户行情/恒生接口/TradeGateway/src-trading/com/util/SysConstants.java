package com.util;

import java.util.Map;

public class SysConstants {
	// public static final String CONFIGFILE_PATH =
	// "conf/ABS_config.properties";//linux路径
	
	public static String brokerId;
	
	/************** Start Aboss config *******************************/ 
	public static final class ABS {
		public static final String ERROR_MESSAGE_FILE_PATH = "\\errorMessageFile";
		public static final String WEBCONTEXT = "webapps";
		/** 主分割标 */
		public static final String TAB = "\t";// 
		/** 项目发布路径 */
		public static String CONTEXT_PATH;
		/** 项目名称 */
		public static String APP_NAME;
		/** 柜台连接地址 */
		public static String ipAddr;
		public static String FBDM = "9999";
		public static String DestFBDM = "9999";
		/** 设置地址信息是IP地址或Mac */
		public static String Node = "172.16.100.9";
		/** 柜员号 */
		public static String GYDM = "02130694";
		/** 委托方式 */
		public static String WTFS = "8";
		/** 日志文件的过滤格式 */
		public static final String LOG_FILTER_FORMAT = "password";
		/** 代替密码 */
		public static final String LOG_PASSWORD_REPLACE_TXT = "***";
		/** 连接超时 单位为秒 **/
		public static int ConnTimeOut;
		/** 创建会话超时 单位为毫秒 **/
		public static int SessionTimeOut;
		/** 最大连接数 **/
		public static int maxPoolSize;
		/** 初始连接数 **/
		public static int initPoolSize;
		/** 模式： 0 为测试， 1为正常 **/
		public static int mode ;
		public static String encoding ;
	}
	/************** End Aboss config *******************************/
	
	/************** Start Handsun config *******************************/ 
	public static final class HS {
		/** 发送消息到恒生接口后等待返回消息的时间 **/
		public static long waitTimeout;
		
		public static String clientName;
		
		/**版本号*/
		public static String version = "4";
		
		/**操作员分支代码*/
		public static String op_branch_no = "100";
		
		/** 委托方式 */
		public static String op_entrust_way = "7";
		
		/**站点|电话 可以是用户的IP地址*/
		public static String op_station = "005056C00008";
		
		/**分支编号，股民所在营业部号*/
		public static String branch_no="100";
		/**
		 * 账户类型为资金账号，‘1’，‘2’，‘3’，4’，‘5’, ‘6’, ‘A’
		 * 分别表示account_content
		 * 代表资金帐号，股东内码，资金卡号，银行帐号，股东帐号,客户编号，期货帐号
		 */
		public static String input_content="1";
		/**
		 * 账号验证方式，当input_content为‘4’，‘5’时，
		 * 则content_type明确银行号和股东的市场类别，其它输入为‘0’。
		 */
		public static String content_type="0";
		/**
		 * 查询方向 '0'是逆序，'1'顺序
		 */
		public static String query_direction="1";
		/**
		 * 错误码映射 
		 */
		public static Map<String, Map<String, String>> hErrorMapping;
	}
	/************** End Handsun config *******************************/ 
}
