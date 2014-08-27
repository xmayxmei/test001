package com.cfwx.rox.businesssync.market.config;



/**
 * @author J.C.J
 *
 * 2013-11-18
 */
public class BaseConfig {
	

	public  static final long serialVersionUID = -1391107041372833874L;
	
	/**
	 * 1.		上证指数库扫描间隔	Scanidxsh	int	1	秒	0-255秒(0:自动)
	 */
	public static  int scanidxsh;
	/**
	 * 2.		上证行情库扫描间隔	Scanstksh	int	1	秒	0-255秒(0:自动)
	 */
	public static  int scanstksh;
	/**
	 * 3.		深证指数库扫描间隔	Scanidxsz	int	1	秒	0-255秒(0:自动)
	 */
	public static  int scanidxsz;
	/**
	 * 4.		深证行情库扫描间隔	Scanstksz	int	1	秒	0-255秒(0:自动)
	 */
	public static  int scanstksz;
	/**
	 * 5.		收盘作业时间	HHmmss
	 */
	public static  String closewktime;
	/**
	 * 6.		Pt股票交易日	Ptday	int			0-6
	 */
	public static  int ptday;
	/**
	 * 7.		分时资料保留天数	Minday	int			0无效
	 */
	public static  int minday;
	/**
	 * 8.		1分钟资料保留天数	Min1day	int			0无效
	 */
	public static  int min1day;
	/**
	 * 9.		日K线资料保留天数	Kday	int
	 */
	public static  int kday;
	/**
	 * 10.		分时显示点数	mincount	int			
	 */
	public static  int mincount;		
	/**
	 * 11.		日K线显示点数	daykcount	int
	 */
	public static  int daycount;	
	/**
	 * 12.		分时文件是否进行文件备份(0:备份，1：一分钟备份一次，2：一天备份一次)	
	 */
	public static  int ghost;			
	/**
	 * 13.		文件备份目录	ghostlocation	String	
	 */
	public static  String ghostLocation;		
	/**
	 * 14.		DBF文件目录,	格式准确
	 */
	public static  String location;
	/**
	 * 15.		主动推送间隔时间	sendinterval  	int		秒	
	 */
	public static  int sendInterval;	
	/**
	 * 16.		开盘作业时间HHmmss	各种内存数据更新时间
	 */
	public static  String initTime;
	/**
	 * 17.		转码机读取数据间隔时间	readinterval  int		秒	
	 */
	public static  long readInterval;
	/**
	 * 18.		基本金额字段放大倍数	enlarge	int			1000
	 */
	public static  double enlarge=0D;
	/**
	 * 19.上交所DBF文件位置
	 */
	public static String shdbf;
	/**
	 * 20.深交所DBF文件位置
	 */
	public static String szdbf;
	//备份文件名称配置
	
	/**
	 * 行情备份文件名称
	 */
	public static String marketFileName;
	
	/**
	 * 日K线备份文件名称
	 */
	public static String dayLineCatalogName;
	
	/**
	 * 是否是交易日：默认-1,1：是，0：否
	 */
	public static int isTradeDay = -1;
	
	/**
	 * 测试环境1，生产环境0
	 */
	public static int test = -1;
	
	/**
	 * 默认long值
	 */
	public static long DEFAULTLONGVALUE= -1000000;
	
	/**
	 * K线对接数据库，上交所备份数据目录,目录
	 */
	public static String shDirectory ="";
	
	/**
	 * K线对接数据库，深交所备份数据目录,目录
	 */
	public static String szDirectory ="";
	
	/**
	 * 是否要进行历史数据加载，历史数据加载需加载最新历史信息，将会覆盖本地历史信息 1:加载(需指定22,24路径)，0：不加载
	 */
	public static String isLoadHistory ="";
	
	/**
	 * 深交所股票信息DBF位置
	 */
	public static String sjsStockDbf = "";
	
	/**
	 * 分时图目录
	 */
	public static String tsCatalogName = "";
	
	/**
	 * 分时扩展名
	 */
	public static String tsExtension = "";
	
	/**
	 * 日K扩展名
	 */
	public static String dayExtension = "";
	
	/**
	 * 交易系统IP,默认值是127.0.0.1
	 */
	public static String tradeServerIp = "127.0.0.1";
	/**
	 * http默认端口
	 */
	public static String httpDefaultPort = "80";
	/**
	 * 交易系统端口，默认值是80
	 */
	public static String tradeServerPort = httpDefaultPort;
	/**
	 * 版本号
	 */
	public static String version;
	
	/**
	 *是否显示交易的功能
	 */
	public static String openTrade;
	/**
	 * 后缀名
	 */
	public static String compressSuffix;
	/**
	 * 交易系统ContextPath,默认值为
	 */
	public static String traderServerContentPath = "TradeWebServer";
	
	public static String getTradeWebServerURL(){
		return "http://"+tradeServerIp+(tradeServerPort.equals(httpDefaultPort)?"":":"+tradeServerPort)+"/"+traderServerContentPath;
	}
	
	public static String zsServerUrl="";
	
	public static String zsServerUrlTest;
}
