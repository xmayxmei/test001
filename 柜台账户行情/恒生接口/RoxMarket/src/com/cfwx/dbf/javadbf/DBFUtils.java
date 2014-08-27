package com.cfwx.dbf.javadbf;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.config.StockConfig;
import com.cfwx.rox.businesssync.market.service.impl.ShDBFHQDataImpl;
import com.cfwx.rox.businesssync.market.structure.MarketCache;

/**
 * @author J.C.J
 *
 * 2013-9-17
 */
public class DBFUtils {
	
	private static final String CHARACTORTYPE = "GBK"; 
	private final static Logger LOG = Logger.getLogger(ShDBFHQDataImpl.class);
	
	/**
	 * 获取深交所行情信息
	 * @param dbfFileUrl
	 * @return 包含特殊记录信息，过滤后的指数信息，个股信息。key:special,stock
	 */
	public static Map<String,List<String[]>>  readSJSHQDBF()throws Exception {   
	    return readDBF(BaseConfig.szdbf,"sz",StockConfig.szReadTime);
	}
	
	/**
	 * 获取深交所行情信息
	 * @param dbfFileUrl
	 * @return 包含特殊记录信息，过滤后的指数信息，个股信息。key:special,stock
	 */
	public static Map<String,List<String[]>>  readSHHQDBF()throws Exception {   
		 return readDBF(BaseConfig.shdbf, "sh",StockConfig.shReadTime);
	}
	
	
	/*
	 * 获取DBF信息
	 * @param dbfFileUrl
	 * @param marketTag ：交易所标识：sh,sz
	 * @return 包含特殊记录信息，过滤后的指数信息，个股信息。key:special,market
	 */
	private static Map<String,List<String[]>>  readDBF(String dbfFileUrl,String marketTag,long useTime)throws Exception {   
		
		ConcurrentHashMap<String,List<String[]>>	map= new ConcurrentHashMap<String,List<String[]>>();
		List<String[]> stockList = new ArrayList<String[]>();
		List<String[]> specialList = null;
	    InputStream in = null;
	    DBFReader reader = null;
	    try {   
//	    		System.err.println("开始读:" + marketTag);
	    	  	in = new BufferedInputStream(new FileInputStream(dbfFileUrl)); 
//	    	  	System.err.println("初始化:" + marketTag);
	    	  	reader=new DBFReader(in);//将文件从文件流中读入。  
	    	  	reader.setCharactersetName(CHARACTORTYPE);
		        String[] rowObjects = null;
	    		long s = System.currentTimeMillis();
//	    		System.err.println("准备读:" + marketTag);
	    		String[] asTargetIndex = null;
	    		String[] asSrcIndex = null;
		        while((rowObjects =  reader.nextRecord())!=null){ 
		        	//填充特殊记录,第一条为特殊记录
		        	if(specialList!=null){
		        		String sCode = rowObjects[0].toString().trim();
		        		String tempStr = marketTag + sCode;
		        		//读取过滤,从第二条开始,判断行情中是否存在该证券信息
		        		if(MarketCache.get(tempStr) != null){
		        			stockList.add(rowObjects);
		        		}
		        		if ("sz".equals(marketTag)) {
		        			if ("395099".equals(sCode)) {
			        			asTargetIndex = rowObjects;
			        		} else if ("399001".equals(sCode)) {
			        			asSrcIndex = rowObjects;
			        		}
		        		}
		        	}else{
		        		specialList = new ArrayList<String[]>();
		        		specialList.add(rowObjects);
		        	}
		        	long e = System.currentTimeMillis();
		        	if((e-s)>useTime){
		        		LOG.error(marketTag+"文件读取超时:"+(e-s)+"毫秒,配置时间为"+useTime);
		        		return null;
		        	}
		        }
		        if (asSrcIndex != null && asTargetIndex != null) {
		        	asSrcIndex[5] = asTargetIndex[5];
		        	asSrcIndex[6] = asTargetIndex[6];
		        }
//		        long e = System.currentTimeMillis();
//		        System.err.println(marketTag + "文件读取耗时-->" + (e-s));
		        map.put("special", specialList);
		        map.put("market", stockList);
	    }   
	     catch (FileNotFoundException e) {   
	    	 e.printStackTrace();
	    	throw new Exception("解析dbf数据过程中，没有找到dbf文件",e);
	    } catch (DBFException e) {   
	    	 e.printStackTrace();
	    	 throw new Exception("解析dbf数据过程中，dbf文件读取异常",e);
	    }
	    finally{
	    	if(in != null)
	    		in.close();
	    }
	    return map;
	}
	
	/*
	 * 获取深交所行情信息
	 * @param dbfFileUrl
	 * @return 包含特殊记录信息，过滤后的指数信息，个股信息。key:special,market
	 */
	public static   Map<String,List<String[]>>  readSJSDBF(String dbfFileUrl)throws Exception {   
		
		Map<String,List<String[]>> sjsMap= new HashMap<String,List<String[]>>();
		List<String[]> stockList = new ArrayList<String[]>();
		List<String[]> specialList = null;
		DBFReader sjsReader = null;	//从dbf中获取内容
	    InputStream in = null;
	    try {   
	    	  	in=new BufferedInputStream(new FileInputStream(dbfFileUrl));   
	    	  	sjsReader=new DBFReader(in);//将文件从文件流中读入。   
		        sjsReader.setCharactersetName(CHARACTORTYPE);
		        String[] rowObjects = null;
		        while((rowObjects =  sjsReader.nextRecord())!=null){ 
		        	//填充特殊记录,第一条为特殊记录
		        	if(specialList==null){
		        		specialList = new ArrayList<String[]>();
		        		specialList.add(rowObjects);
		        	}else{
		        		//读取过滤,从第二条开始
			        	stockList.add(rowObjects);
		        	}
		        }
		        sjsMap.put("special", specialList);
		        sjsMap.put("market", stockList);
	    }   
	     catch (FileNotFoundException e) {   
	    	e.printStackTrace();   
	    	throw new Exception("解析深交所证券dbf数据过程中，没有找到dbf文件",e);
	    } catch (DBFException e) {   
	    	 e.printStackTrace();  
	    	 throw new Exception("解析深交所证券dbf数据过程中，dbf文件读取异常",e);
	    }
	    finally{
	    	if(in != null)
	    		in.close();
	    }
	    return sjsMap;
	}
	
	/*
	 * 获取DBF文件内容
	 * @param dbfFileUrl
	 * @return 包含特殊记录信息，过滤后的指数信息，个股信息。key:special,market
	 */
	public static Map<String,List<String[]>>  readDBF(String dbfFileUrl)throws Exception {   
		
		Map<String,List<String[]>> map= new HashMap<String,List<String[]>>();
		List<String[]> stockList = new ArrayList<String[]>();
		List<String[]> specialList = null;
		DBFReader reader = null;
	    InputStream in = null;
	    try {   
	    	  	in=new FileInputStream(dbfFileUrl);
	    		in = new BufferedInputStream(in);
		        reader=new DBFReader(in);//将文件从文件流中读入。   
		        reader.setCharactersetName(CHARACTORTYPE);
		        String[] rowObjects = null;
		        while((rowObjects =  reader.nextRecord())!=null){ 
		        	//填充特殊记录,第一条为特殊记录
		        	if(specialList!=null){
		        		//读取过滤,从第二条开始,判断行情中是否存在该证券信息
		        		stockList.add(rowObjects);
		        	}else{
		        		specialList = new ArrayList<String[]>();
		        		specialList.add(rowObjects);
		        	}
		        }
		        map.put("special", specialList);
		        map.put("market", stockList);
	    }   
	     catch (FileNotFoundException e) {   
	    	e.printStackTrace();   
	    	throw new Exception("解析dbf数据过程中，没有找到dbf文件",e);
	    } catch (DBFException e) {   
	    	 e.printStackTrace();  
	    	 throw new Exception("解析dbf数据过程中，dbf文件读取异常",e);
	    }
	    finally{
	    	if(in != null)
	    		in.close();
	    }
	    return map;
	}
	
}
