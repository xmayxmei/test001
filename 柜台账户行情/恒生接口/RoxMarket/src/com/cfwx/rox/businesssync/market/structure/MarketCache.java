/**
 * 
 */
package com.cfwx.rox.businesssync.market.structure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.cfwx.rox.businesssync.market.entity.ActualMarket;

/**
 * @author J.C.J
 * 实时行情 
 * 2013-11-4
 */
public class MarketCache{
	
	public static  String ghostUrl="";
	
	public static Map<String,ActualMarket> marketMap = new HashMap<String,ActualMarket>();
	
	public static void put(String key,ActualMarket value) throws Exception{
		marketMap.put(key, value);
	}
	
	public static int size(){
		return marketMap.size();
	}
	
	public static ActualMarket get(String key){
		return marketMap.get(key);
	}
	
	public static Iterator<String> iterator(){
		return marketMap.keySet().iterator();
	}

	public static void clear(){
		marketMap.clear();
	}
	
	public static  Map<String,ActualMarket> getData(){
		return marketMap;
	}
	
	public static void initData(Map<String,ActualMarket> data){
		marketMap = data;
	}
}
