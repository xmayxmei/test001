package com.cfwx.rox.businesssync.market.init;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.config.StockConfig;
import com.cfwx.rox.businesssync.market.config.TypeConfig;
import com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao;
import com.cfwx.rox.businesssync.market.entity.StockType;
import com.cfwx.rox.businesssync.market.service.IDBFStockData;
import com.cfwx.rox.businesssync.market.show.DayLine;
import com.cfwx.rox.businesssync.market.structure.BaseStructure;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.structure.TimeShareCache;
import com.cfwx.rox.businesssync.market.util.GhostUtils;
import com.cfwx.rox.businesssync.market.util.IOUtils;
import com.cfwx.rox.businesssync.market.util.MathFactory;
import com.cfwx.rox.businesssync.market.util.TimeUtils;


/**
 * @author J.C.J
 *
 * 2013-11-18
 */
public class InitConfig {
	
	private final static Logger LOG= Logger
			.getLogger(InitConfig.class);
	
	private  String baseConfig = "baseConfig.properties";
	private  String stockConfig = "stockCode.properties";
	private  String typeConfig = "stockType.properties";
	private String stockFilter = "stockFilter.properties";
	private String fileNameInfo = "fileNameConfig.properties";
	
	@Autowired
	private IDBFStockData dbfStock;
	@Autowired
	private IQuoteCacheDao oCacheDao;
	
	public  void init(){
		try {
			long s = System.currentTimeMillis();
			//文件位置
			initLocation();
			//基础配置
			readBaseConfig();
			//证券配置
			readStockConfig();
			//证券类型配置
			readTypeConfig();
			//排行字段
			setFieldmap();
			//缓存备份文件路径配置
			setCacheURL();
			//初始数据格式
			initNumberFormat();
			
			//交易日标识初始化
			initTrad();
			//自动恢复TS
			if(!TimeUtils.inTrade() || BaseConfig.isTradeDay != 1){
				recover();
			}
			//清空历史缓存
			oCacheDao.clearAllKLineCache();
			//更新股票信息
			dbfStock.updateStockInfo(0);
			//历史数据写入，K线数据
//			writeHistoryData();
			long e = System.currentTimeMillis();
			LOG.info("系统配置加载完毕，启动成功========================================耗时："+(e-s));
		} catch (Exception e) {
			LOG.error("系统启动配置加载失败:"+e.getMessage(),e);
		}
	}
	
	private void initLocation(){
		String catalog = this.getClass().getResource("").getPath();
		catalog = catalog.split("classes")[0]+="classes/";
		baseConfig = catalog+baseConfig;
		stockConfig = catalog+stockConfig;
		typeConfig = catalog+typeConfig;
		stockFilter = catalog+stockFilter;
		fileNameInfo= catalog+fileNameInfo;
		LOG.info("系统目录为:"+catalog);
	}
	
	private  void readBaseConfig() {
	     Properties props = new Properties();
	        try {
	         InputStream in = new BufferedInputStream (new FileInputStream(baseConfig));
	         props.load(in);
	            BaseConfig.closewktime= props.getProperty ("closewktime");
	            BaseConfig.daycount=props.getProperty ("daycount").equals("")?0: Integer.parseInt(props.getProperty ("daycount"));
	            BaseConfig.enlarge= props.getProperty ("enlarge").equals("")?0:Double.parseDouble(props.getProperty ("enlarge"));
	            BaseConfig.ghost= props.getProperty ("ghost").equals("")?0:Integer.parseInt(props.getProperty ("ghost"));
	            BaseConfig.ghostLocation= props.getProperty ("ghostLocation");
	            BaseConfig.initTime= props.getProperty ("initTime").equals("")?"090000":props.getProperty ("initTime");
	            BaseConfig.kday=props.getProperty ("kday").equals("")?0: Integer.parseInt(props.getProperty ("kday"));
	            BaseConfig.location= props.getProperty ("location");
	            BaseConfig.min1day= props.getProperty ("min1day").equals("")?0:Integer.parseInt(props.getProperty ("min1day"));
	            BaseConfig.mincount=props.getProperty ("mincount").equals("")?0: Integer.parseInt(props.getProperty ("mincount")); 
	            BaseConfig.minday=props.getProperty ("minday").equals("")?0: Integer.parseInt(props.getProperty ("minday"));
	            BaseConfig.ptday= props.getProperty ("ptday").equals("")?0:Integer.parseInt(props.getProperty ("ptday"));
	            BaseConfig.readInterval= props.getProperty ("readInterval").equals("")?0:Long.parseLong(props.getProperty ("readInterval"));
	            BaseConfig.scanidxsh= props.getProperty ("scanidxsh").equals("")?0:Integer.parseInt(props.getProperty ("scanidxsh"));
	            BaseConfig.scanidxsz=props.getProperty ("scanidxsz").equals("")?0: Integer.parseInt(props.getProperty ("scanidxsz"));
	            BaseConfig.scanstksh= props.getProperty ("scanstksh").equals("")?0:Integer.parseInt(props.getProperty ("scanstksh"));
	            BaseConfig.scanstksz= props.getProperty ("scanstksz").equals("")?0:Integer.parseInt(props.getProperty ("scanstksz"));
	            BaseConfig.sendInterval= props.getProperty ("sendInterval").equals("")?0:Integer.parseInt(props.getProperty ("sendInterval"));
	            BaseConfig.shdbf= props.getProperty ("shdbf");
	            BaseConfig.szdbf= props.getProperty ("szdbf");
	            BaseConfig.test= props.getProperty ("test").equals("")?0:Integer.parseInt(props.getProperty ("test"));
	            BaseConfig.shDirectory= props.getProperty ("shDirectory");
	            BaseConfig.szDirectory= props.getProperty ("szDirectory");
	            BaseConfig.isLoadHistory= props.getProperty ("isLoadHistory");
	            BaseConfig.sjsStockDbf= props.getProperty ("sjsStockDbf");
	            BaseConfig.tradeServerIp = props.getProperty ("tradeServerIp","127.0.0.1");
	            BaseConfig.tradeServerPort = props.getProperty ("tradeServerPort",BaseConfig.httpDefaultPort);
	            BaseConfig.version = props.getProperty ("version", "1.0.1");
	            BaseConfig.compressSuffix = props.getProperty ("compressSuffix", "min");
	            BaseConfig.openTrade= props.getProperty ("openTrade");
	            BaseConfig.traderServerContentPath = props.getProperty ("traderServerContentPath","TradeWebServer");
	            BaseConfig.zsServerUrl = props.getProperty ("zsServerUrl");
	            BaseConfig.zsServerUrlTest = props.getProperty ("zsServerUrlTest");
	            in.close();
//	            System.out.println(BaseConfig.closewktime);
//	            System.out.println(BaseConfig.daycount);
//	            System.out.println(BaseConfig.enlarge);
//	            System.out.println(BaseConfig.ghost);
//	            System.out.println(BaseConfig.ghostLocation);
//	            System.out.println(BaseConfig.initTime);
//	            System.out.println(BaseConfig.kday);
//	            System.out.println(BaseConfig.location);
//	            System.out.println(BaseConfig.min1day);
//	            System.out.println(BaseConfig.mincount); 
//	            System.out.println(BaseConfig.minday);
//	            System.out.println(BaseConfig.ptday);
//	            System.out.println(BaseConfig.readIntervalint);
//	            System.out.println(BaseConfig.scanidxsh);
//	            System.out.println(BaseConfig.scanidxsz);
//	            System.out.println(BaseConfig.scanstksh);
//	            System.out.println(BaseConfig.scanstksz);
//	            System.out.println(BaseConfig.sendInterval);
	            
	            //文件名称配置
	            in = new BufferedInputStream (new FileInputStream(fileNameInfo));
		         props.load(in);
		         BaseConfig.dayLineCatalogName= props.getProperty("dayLineCatalogName");
		         BaseConfig.dayExtension= props.getProperty("dayExtension");
		         BaseConfig.marketFileName= props.getProperty("marketFileName");
		         BaseConfig.tsCatalogName= props.getProperty("tsCatalogName");
		         BaseConfig.tsExtension= props.getProperty("tsExtension");
		         in.close();
	            
		         //股票过滤
	             in = new BufferedInputStream (new FileInputStream(stockFilter));
		         props.load(in);
		         StockConfig.filter= props.getProperty ("filter");
		         StockConfig.zs= props.getProperty("zs");
		         StockConfig.dzs= props.getProperty("dzs");
		         StockConfig.gzdzs= props.getProperty("gzdzs");
		         StockConfig.notSortType= props.getProperty("notSortType");
		         StockConfig.defaultSortField= props.getProperty("defaultSortField");
		         in.close();
	            LOG.info("基础配置加载完成...==================================");
	        } catch (Exception e) {
	        	LOG.error(e.getMessage(),e);
	        }
	    }
	
	private  void readStockConfig() {
	     Properties props = new Properties();
	     InputStream in = null;
	        try {
	         in = new BufferedInputStream (new FileInputStream(stockConfig));
	         props.load(in);
	            StockConfig.dcdsh= props.getProperty ("dcdsh");
	            StockConfig.dcdsz=props.getProperty ("dcdsz");
	            StockConfig.notClosesh= props.getProperty ("notclosesh");
	            StockConfig.notClosesz= props.getProperty ("notclosesz");
	            StockConfig.timeArea= props.getProperty ("timeArea");
	            StockConfig.setReadTime(props.getProperty ("readTime"));
	            in.close();
//	            System.out.println(props.getProperty ("dcdsh"));
//	            System.out.println(props.getProperty ("dcdsz"));
//	            System.out.println(props.getProperty ("notclosesh"));
//	            System.out.println(props.getProperty ("notclosesz"));
//	            System.out.println(props.getProperty ("timeArea"));
	            LOG.info("股票过滤配置加载完成...==================================");
	        } catch (Exception e) {
	        	LOG.error(e.getMessage(),e);
	        }finally{
	        	try {
					in.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(),e);
				}
	        }
	    }
	
	@SuppressWarnings("rawtypes")
	private  void readTypeConfig() {
	     Properties props = new Properties();
	     InputStream in = null;
	        try {
	         in = new BufferedInputStream (new FileInputStream(typeConfig));
	         props.load(in);
	         
	         //添加默认值,上交所和深交所
	         StockType zs = new StockType();
	         zs.setName("大盘指数");
	         zs.setType("-2");
	         zs.setUsed(true);
	         
	         StockType hs = new StockType();
	         hs.setName("沪深");
	         hs.setType("-1");
	         hs.setUsed(true);
	         
	         StockType sh = new StockType();
	         sh.setName("沪市");
	         sh.setType("0");
	         sh.setUsed(true);
	         
	         StockType sz = new StockType();
	         sz.setName("深市");
	         sz.setType("1");
	         sz.setUsed(true);
	         
	         StockType temp = null;
	         
	         Iterator it =props.keySet().iterator();
	         int i=1;
	         while(it.hasNext()){
	        	 String key = it.next().toString();
	        	 
	        	 temp = new StockType();
	        	 temp.setName(key);
	        	 temp.setType("-1_"+i);
	        	 setType(temp,props.getProperty(key));
	        	 hs.addChild(temp);
	        	 
	        	 temp = new StockType();
	        	 temp.setName(key);
	        	 temp.setType("0_"+i);
	        	 setType(temp,props.getProperty(key));
	        	 sh.addChild(temp);
	        	 
	        	 temp = new StockType();
	        	 temp.setName(key);
	        	 temp.setType("1_"+i);
	        	 setType(temp,props.getProperty(key));
	        	 sz.addChild(temp);
	        	 i++;
	         }
	         	in.close();
	            TypeConfig.add(zs);
	            TypeConfig.add(sh);
		        TypeConfig.add(sz);
		        TypeConfig.add(hs);
		        
//		       List<StockType> list =  TypeConfig.getAll();
//		       
//		       for(StockType s : list){
//		    	   System.out.println(s.getName()+":"+s.getType());
//		    	   
//		    	   List<StockType> clist = s.getChildList();
//		    	   
//		    	   for(StockType c :clist){
//		    		   System.out.print(c.getName()+":"+c.getType()+"("+c.getValue().toString()+")");
//		    		   System.out.print("   ");
//		    	   }
//		    	   System.out.println();
//		       }
		       
		       fullChangeType();
		       
//		      Iterator<String> ite = BaseStructure.changeTypeMap.keySet().iterator();
//		      
//		      while(ite.hasNext()){
//		    	  String s = ite.next();
//		    	  System.out.println(s+":"+BaseStructure.changeTypeMap.get(s));
//		      }
	           LOG.info("股票类型加载完成...==================================");
	        } catch (Exception e) {
	        	LOG.error(e.getMessage(),e);
	        }
	    }
	/**
	 * 配置排行显示字段
	 */
	public void setFieldmap(){
		BaseStructure.fieldMap = new HashMap<String,String>();
		BaseStructure.fieldMap.put("mc", "名称");
		BaseStructure.fieldMap.put("dm", "代码");
		BaseStructure.fieldMap.put("zx", "最新价");
		BaseStructure.fieldMap.put("zdf", "涨跌幅");
		BaseStructure.fieldMap.put("zde", "涨跌额");
		BaseStructure.fieldMap.put("ze", "交易额");
		BaseStructure.fieldMap.put("zl", "交易量");
		BaseStructure.fieldMap.put("zs", "昨收");
		BaseStructure.fieldMap.put("jk", "今开");
		BaseStructure.fieldMap.put("zg", "最高");
		BaseStructure.fieldMap.put("zd", "最低");
		LOG.info("排行字段加载完成...==================================");
	}
	
	private void setCacheURL(){
		String catalog = BaseConfig.ghostLocation;
		File file = new File(catalog);
		if(!file.exists())
			file.mkdirs();
		TimeShareCache.ghostUrl=BaseConfig.ghostLocation+BaseConfig.tsCatalogName;
		TimeShareCache.ghostExtension=BaseConfig.tsExtension;
		
//		KLineDailyCache.ghostUrl=BaseConfig.ghostLocation+"/"+BaseConfig.dayLineCatalogName;
//		KLineDailyCache.ghostExtension=BaseConfig.dayExtension;
		
		MarketCache.ghostUrl=BaseConfig.ghostLocation+"/"+BaseConfig.marketFileName;
		
		LOG.info("缓存备份文件路径配置完成...==================================");
	}
	
	private void setType(StockType st ,String value){
		String[] arr= value.split(";");
		if(arr.length==2){
			//设置子类型
			String[] child = arr[0].split(",");
			st.setValue(Arrays.asList(child));
			st.setPointnum(Integer.parseInt(arr[1]));
		}
	}
	
	/**
	 * 填充类型转换map
	 */
	private void fullChangeType(){
		BaseStructure.changeTypeMap.clear();
		 List<StockType> list =  TypeConfig.getAll();
	       
	       for(StockType s : list){
	    	   for(StockType c :s.getChildList()){
	    		  for(String value:c.getValue())
	    			  BaseStructure.changeTypeMap.put(s.getType()+"_"+value, c.getType());
	    	   }
	       }
	}
	
	/**
	 * 备份文件恢复
	 */
	private void recover(){
			try {
				long s = System.currentTimeMillis();
//				readKLineFromDB();
				GhostUtils.readTimeShare();
				long e = System.currentTimeMillis();
				LOG.info("分时加载耗时：" + (e-s));
				LOG.info("文件自动恢复已完成...==================================");
			}catch (Exception e) {
				LOG.error(e +"文件自动恢复失败...==================================");
			}
	}

	private void initTrad(){
		//初始化交易日标识
		try {
			TimeUtils.initTrade();
			//检测是否是测试环境
			if(BaseConfig.test==1)
			BaseConfig.isTradeDay= 1;
			LOG.info("交易日标识已初始化...=================================="+BaseConfig.isTradeDay);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("交易日标识失败...=================================="+BaseConfig.isTradeDay);
		}
	}
	
	private void initNumberFormat(){
		MathFactory.initNumberFormat();
		LOG.info("数据格式初始化完毕...==================================");
	}
	
	/*private void writeHistoryData(){
		if(BaseConfig.isLoadHistory.equals("1")){
			HashMap<String,LinkedList<DayLine>> shmap = IOUtils.readSHDayLineFile(BaseConfig.shDirectory, BaseConfig.kday);
			HashMap<String,LinkedList<DayLine>> szmap = IOUtils.readSZDayLineFile(BaseConfig.szDirectory, BaseConfig.kday);
			if(shmap != null && szmap!=null){
				shmap.putAll(szmap);
			}
//			KLineDailyCache.initData(shmap);
//			GhostUtils.writeAllDayLine();
			LOG.info("K线历史数据加载完毕...==================================");
		}else{
			LOG.info("无需加载K线历史数据...==================================");
		}
	}*/
	/* private void readKLineFromDB() {
		 Iterator<String> iter = BaseStructure.stockMap.keySet().iterator();
		 while (iter.hasNext()) {
			 String sCode = iter.next();
			 List<DayLine> lstDaily = quoteDao.getDailyData(sCode, BaseConfig.kday);
			 KLineDailyCache.addAllDayLine(sCode, lstDaily);
		 }
	 }*/
}
