/**
 * 
 */
package com.cfwx.rox.businesssync.market.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.show.TimeShare;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.structure.TimeShareCache;


/**
 * @author J.C.J
 *
 * 2013-11-28
 */
public class GhostUtils {
	
	private final static Logger LOG = Logger.getLogger(GhostUtils.class);
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
	
/*	public static void write(String file,Object obj)throws Exception{
		FileOutputStream fos = new FileOutputStream(file);  
        ObjectOutputStream oos = new ObjectOutputStream(fos);  
        oos.writeObject(obj);  
        oos.flush();  
        oos.close(); 
        fos.close();
	}
	*/
	public static Object  getObject(String file)throws Exception{
		Object obj = null;
		FileInputStream fis = null;
		ObjectInputStream oin = null;
		try {
			 fis = new FileInputStream(file);  
	         oin = new ObjectInputStream(fis);  
	         obj = oin.readObject();
		}catch(FileNotFoundException e){
			LOG.error(e.getMessage());
			throw new Exception(file+":文件没有找到",e);
		}
		catch (Exception e) {
			throw new Exception(file+":读取失败",e);
		}finally{
			oin.close();
	        fis.close();
		}
        return  obj;  
	}
/*	
	public static void initData()throws Exception{
		try {
			long s = System.currentTimeMillis();
			readDayLine();
			readTimeShare();
			long e = System.currentTimeMillis();
			LOG.info("分时,K线数据加载耗时："+(e-s));
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	*/
	public static synchronized void writeTimeShare()throws Exception{
        
		long now = Long.valueOf(sdf.format(new Date()));
		//由于分时写入是延迟一分钟，最后一次写入是15点01分，02分以后就不再写入数据。
		if(now <93000 || now >=150200){
			return;
		}
		
		File  catalog  = new File(TimeShareCache.ghostUrl);
		
		if(!catalog.exists()){
			catalog.mkdirs();
		}
		
        Iterator<String> it = TimeShareCache.iterator();
        String key = "";
        FileWriter fw = null;
        String fileName ="";
        while(it.hasNext()){
        	key = it.next();
        	fileName=catalog+"/"+key+TimeShareCache.ghostExtension;
        	try {
        		fw = new FileWriter(fileName,true);
            	fw.write(getTimeShareContent(TimeShareCache.getLast(key)));
			} catch (Exception e) {
				throw new Exception(e);
			}finally{
				fw.close();
			}
        }
	}
	
	public static synchronized void writeTimeShareAll()throws Exception{
		File  catalog  = new File(TimeShareCache.ghostUrl);
		if(!catalog.exists()){
			catalog.mkdirs();
		}
        Iterator<String> it = TimeShareCache.iterator();
        String key = "";
        FileWriter fw = null;
        String fileName ="";
        while(it.hasNext()){
        	key = it.next();
        	fileName=catalog+"/"+key+TimeShareCache.ghostExtension;
        	try {
        		fw = new FileWriter(new File(fileName),true);
        		
        		LinkedList<TimeShare> tsList = TimeShareCache.get(key);
        		
        		for(TimeShare ts :tsList){
        			fw.write(getTimeShareContent(ts));
        		}
			} catch (Exception e) {
				throw new Exception(e);
			}finally{
				fw.close();
			}
        }
	}
	
	
	public static void readTimeShare()throws Exception{
        
		File  catalog  = new File(TimeShareCache.ghostUrl);
		
		File[] files = catalog.listFiles();
        String key = "";
        BufferedReader bs = null;
        FileReader fr =null;
        String temp =null;
        if (files == null) {
        	return ;
        }
        for(File file : files){
        	key = file.getName().split("\\.")[0];
        	try {
        		fr = new FileReader(file.getPath());
        		bs = new BufferedReader(fr);
        		temp =null;
        		while((temp =bs.readLine())!= null){
        			TimeShareCache.addMarket(key, getTimeShare(temp));
        		}
			} catch (Exception e) {
				LOG.error(file.getName()+":"+e.getMessage());
			}finally{
				if(bs != null)
					bs.close();
				if(fr != null)
					fr.close();
			}
        }
	}
	
	/*public static void writeDayLine()throws Exception{
		File  catalog  = new File(KLineDailyCache.ghostUrl);
		
		if(!catalog.exists()){
			catalog.mkdirs();
		}
		//写入K线数据
		Iterator<String> it=MarketCache.iterator();
		String key = "";
		ActualMarket am = null;
		String fileName ="";
		while(it.hasNext()){
			key = it.next();
			am = MarketCache.get(key);
			 FileWriter fw = null;
			 fileName=catalog+"/"+key+KLineDailyCache.ghostExtension;
			if(am.getJk()==0){
				//今开价为0的，即表示当天没有开盘。没有开盘的就不进行写入日线
				continue;
			}
			try {
				if(KLineDailyCache.get(key)!=null){
					fw = new FileWriter(fileName,true);
	            	fw.write(getDayLineContent(KLineDailyCache.getLast(key)));
				}
			} catch (Exception e) {
				LOG.error(key+":"+e.getMessage(),e);
			}
			finally{
				if(fw != null)
				fw.close();
			}
		}
	}*/
	
	/**
	 * 写入单个数据的日K线,全部数据
	 * @throws Exception
	 *//*
	public static void writeOneDayLine(String code)throws Exception{
		File  catalog  = new File(KLineDailyCache.ghostUrl);
		
		if(!catalog.exists()){
			catalog.mkdirs();
		}
		//写入K线数据
		ActualMarket am = null;
		String fileName ="";
			am = MarketCache.get(code);
			 FileWriter fw = null;
			 fileName=catalog+"/"+code+KLineDailyCache.ghostExtension;
			if(am.getJk()==0){
				//今开价为0的，即表示当天没有开盘。没有开盘的就不进行写入日线
				return ;
			}
			try {
				LinkedList<DayLine> list = KLineDailyCache.get(code);
				if(list!=null){
					fw = new FileWriter(fileName,true);
					for(DayLine dl : list){
						fw.write(getDayLineContent(dl));
					}
				}
				LOG.error(code+":添加K线数据完成,数量："+list.size());
			} catch (Exception e) {
				LOG.error(code+":"+e.getMessage(),e);
			}
			finally{
				if(fw != null)
				fw.close();
			}
	}*/
	
	/*private static void readDayLine()throws Exception{
		File  catalog  = new File(KLineDailyCache.ghostUrl);
		File[] files = catalog.listFiles();
        String key = "";
        BufferedReader bs = null;
        FileReader fr = null;
        String temp =null;
        if (files == null) {
        	return ;
        }
        for(File file : files){
        	key = file.getName().split("\\.")[0];
        	try {
        		fr = new FileReader(file.getPath());
        		bs = new BufferedReader(new FileReader(file.getPath()));
        		temp =null;
        		while((temp =bs.readLine())!= null){
        			KLineDailyCache.addDay(key, getDayLine(temp));
        		}
			} catch (Exception e) {
				LOG.error(file.getName()+":"+e.getMessage());
			}finally{
				if(bs != null)
					bs.close();
				if(fr != null)
					fr.close();
			}
        }
	}*/
	
	@SuppressWarnings("unchecked")
	public static void initMarket()throws Exception{
		LOG.info("MarketCache:"+MarketCache.ghostUrl);
		Map<String,ActualMarket> data = (Map<String,ActualMarket>)getObject(MarketCache.ghostUrl);
		if(data != null)
			MarketCache.initData(data);
	}
	
	private static String getTimeShareContent(TimeShare ts){
		StringBuffer content = new StringBuffer();
		
		content.append(ts.getNewPrice()==null?"":ts.getNewPrice()).append(",");
		content.append(ts.getVolume()==null?"":ts.getVolume()).append(",");
		content.append(ts.getAverage()==null?"":ts.getAverage()).append(",");
		content.append(ts.getZe()==0?"":ts.getZe()).append(",");
		content.append(ts.getZl()).append(",");
		content.append(ts.getTime()).append(",");
		content.append(ts.getAmount()==null?"0":ts.getAmount());
		content.append("\r\n");
		
		return content.toString();
	}
	
	private static TimeShare getTimeShare(String ts){
		TimeShare temp = new TimeShare();
		try {
			String[] tsArr = ts.split(",");
			
			temp.setNewPrice(tsArr[0]);
			temp.setVolume(tsArr[1]);
			temp.setAverage(tsArr[2]);
			temp.setZe(tsArr[3].equals("")?0:Double.valueOf(tsArr[3]));
			temp.setZl(tsArr[4].equals("")?0:Double.valueOf(tsArr[4]));
			temp.setTime(tsArr[5]);
//			temp.setAmount(tsArr[6]);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return temp;
	}
	
	/*private static String getDayLineContent(DayLine dl){
//		private String time;// 1. 日期 date int yyyyMMdd
//		private String open;// 2. 开盘 open long X1000
//		private String close;// 3. 收盘 close long X1000
//		private String volume;// 7. 交易量 百股
//		private String high;// 8. 最高价 high long X1000
//		private String low;// 9. 最低价 low long X1000
//		private String amount;// 9. 成交金额 千元
		StringBuffer content = new StringBuffer();
		content.append(dl.getTime()).append(",");
		content.append(dl.getOpen()).append(",");
		content.append(dl.getClose()).append(",");
		content.append(dl.getVolume()).append(",");
		content.append(dl.getHigh()).append(",");
		content.append(dl.getLow()).append(",");
		content.append(dl.getAmount());
		content.append("\r\n");
		return content.toString();
	}*/
	
	/*private static DayLine getDayLine(String dl){
//		private String time;// 1. 日期 date int yyyyMMdd
//		private String open;// 2. 开盘 open long X1000
//		private String close;// 3. 收盘 close long X1000
//		private String volume;// 7. 交易量 百股
//		private String high;// 8. 最高价 high long X1000
//		private String low;// 9. 最低价 low long X1000
//		private String amount;// 9. 成交金额 千元
		DayLine temp = new DayLine();
		try {
			String[] tsArr = dl.split(",");
			temp.setTime(tsArr[0]);
			temp.setOpen(tsArr[1]);
			temp.setClose(tsArr[2]);
			temp.setVolume(tsArr[3]);
			temp.setHigh(tsArr[4]);
			temp.setLow(tsArr[5]);
			temp.setAmount(tsArr[6]);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return temp;
	}*/
	
/*	public static void writeAllDayLine(){
		File  catalog  = new File(KLineDailyCache.ghostUrl);
		
		if(!catalog.exists()){
			catalog.mkdirs();
		}
		//写入K线数据
		Iterator<String> it=MarketCache.iterator();
		String key = "";
		String fileName ="";
		StringBuffer content = null;
		List<DayLine> list = null;
		while(it.hasNext()){
			key = it.next();
			 FileWriter fw = null;
			 content = new StringBuffer();
			 fileName=catalog+"/"+key+KLineDailyCache.ghostExtension;
			try {
				if(KLineDailyCache.get(key)!=null){
					fw = new FileWriter(fileName,true);
					list =   KLineDailyCache.get(key);
					if(list != null){
						for(DayLine dl : list){
							content.append(getDayLineContent(dl));
						}
					}
	            	fw.write(content.toString());
				}
			} catch (Exception e) {
				LOG.error(key+":"+e.getMessage(),e);
			}
			finally{
				try {
					if(fw!= null)
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}*/
	
}
