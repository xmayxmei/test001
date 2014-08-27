package com.cfwx.rox.businesssync.market.structure;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.cfwx.rox.businesssync.market.entity.StockInfo;
import com.cfwx.rox.businesssync.market.show.ResultInfo;
/**
 * @author J.C.J
 *
 * 2013-11-20
 */
public class BaseStructure {

	/**
	 * 行情结构
	 */
//	public static Map<String,ActualMarket> marketMap = new HashMap<String,ActualMarket>();
	
	/**
	 * 股票结构,每日初始化数据时更新
	 */
	public static Map<String,StockInfo> stockMap = new HashMap<String,StockInfo>();
	
	/**
	 * 拼音索引结构,每日初始化数据时更新
	 */
	public static Map<String,List<ResultInfo>> pyMap = new HashMap<String,List<ResultInfo>>();
	
	/**
	 * 代码检索结构,每日初始化数据时更新
	 */
	public static Map<String,List<ResultInfo>> codeMap = new HashMap<String,List<ResultInfo>>();
	
	/**
	 * 类别集合,key:类别代码,value:股票代码集合,用于前端展示与传值
	 */
	public static Map<String,List<String>> typeMap  = new HashMap<String,List<String>>();
	
	/**
	 * 类别集合,key:信息中心提供的类型,value:需要转换的类型值
	 */
	public static Map<String,String> changeTypeMap  = new HashMap<String,String>();
	
	/**
	 * 类别排序索引,key:类别代码,value:排序列表集合(key:排序字段,value:已排序的股票代码集合)
	 */
	public static Map<String,Map<String,List<String>>> sortMap  = new HashMap<String,Map<String,List<String>>>();
	
	
	public static boolean hasStock = false;
	
	/**
	 * 排行字段信息
	 */
	public static Map<String,String> fieldMap;
	
}
