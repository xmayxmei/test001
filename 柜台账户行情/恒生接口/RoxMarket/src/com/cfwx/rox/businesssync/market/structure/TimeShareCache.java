/**
 * 
 */
package com.cfwx.rox.businesssync.market.structure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import com.cfwx.rox.businesssync.market.show.TimeShare;

/**
 * @author J.C.J
 * 日K线内存
 * 2013-11-4
 */
public class TimeShareCache{
	/**
	 * 备份文件路径
	 */
	public static  String ghostUrl ="";
	
	public static  String ghostExtension ="";
	
	//一级节点,5000个key,值是Queue
	private static HashMap<String,LinkedList<TimeShare>> tree = new HashMap<String,LinkedList<TimeShare>>();
	
	private static LinkedList<TimeShare> tempQueue;
	
	public static void addMarket(String paramCode,TimeShare ts) throws Exception{
		tempQueue =tree.get(paramCode);
		if(tempQueue != null){
			tempQueue.add(ts);
		}
		else{
			tempQueue = new LinkedList<TimeShare>();
			tempQueue.add(ts);
			tree.put(paramCode, tempQueue);
		}
//		System.out.println(paramCode+"_数量为："+tree.get(paramCode).size());
	} 
	
	public static int size(){
		return tree.size();
	}
	
	/**
	 * 早盘数据
	 * @param paramCode
	 * @param ts
	 * @throws Exception
	 */
	public static void addBeforeOpen(String paramCode,TimeShare ts) throws Exception{
		tempQueue =tree.get(paramCode);
		 
		if(tempQueue == null){
			tempQueue = new LinkedList<TimeShare>();
		}else{
			tempQueue.removeFirst();
		}
		tempQueue.add(ts);
		tree.put(paramCode, tempQueue);
//		System.out.println(paramCode+"_数量为："+tree.get(paramCode).size());
	} 
	
	public static void clear(){
		tree.clear();
	}
	
	public static LinkedList<TimeShare> get(String key){
		return tree.get(key);
	}
	
	public static TimeShare getLast(String key){
		TimeShare resTS = null;
		LinkedList<TimeShare> tempList = get(key);
		if(tempList!=null){
			resTS = tempList.getLast();
		}
		return resTS;
	}
	/**
	 * 获取倒数第二个数据
	 * @param key
	 * @return
	 */
	public static TimeShare getLastTwo(String key){
		TimeShare resTS = null;
		LinkedList<TimeShare> tempList = get(key);
		if(tempList!=null){
			if(tempList.size()>1)
				resTS = tempList.get(tempList.size()-2);
			else
				resTS = null;
		}
		return resTS;
	}

	public static HashMap<String,LinkedList<TimeShare>> getData(){
		return tree;
	}
	
	public static Iterator<String> iterator(){
		return tree.keySet().iterator();
	}
	
	public static void initData(HashMap<String,LinkedList<TimeShare>> data){
		tree = data;
	}
}
