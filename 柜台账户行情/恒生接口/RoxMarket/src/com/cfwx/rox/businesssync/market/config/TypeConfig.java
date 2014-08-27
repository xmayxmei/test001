package com.cfwx.rox.businesssync.market.config;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.cfwx.rox.businesssync.market.entity.StockType;
/**
 * @author J.C.J
 * 类别配置对象
 * 2013-11-20
 */
public class TypeConfig {

	private final static Logger LOG = Logger.getLogger(TypeConfig.class);
	
	public static List<StockType> typeList = new ArrayList<StockType>();
	
	private static List<StockType> stList = null;
	
	public static void  add(StockType st){
		typeList.add(st);
	}
	
	public static List<StockType>  getAll(){
		return typeList;
	}
	
	public  static void clear(){
		typeList.clear();
	}
	
	public static int getPointNum(String type)throws Exception{
		try {
			for(StockType s :typeList){
				stList = s.getChildList();
				for(StockType st : stList){
					if(type.equals(st.getType())){
						return st.getPointnum();
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			return 2;
		}
		return 0;
	}
	
	public static int getBigTypeValue(String type)throws Exception{
		try {
			for(StockType s :typeList){
				stList = s.getChildList();
				for(StockType st : stList){
					if(type.equals(st.getType())){
						return getBigType(st.getName());
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			return 0;
		}
		return 0;
	}
	/**
	 *  1.股票，2.指数，3.债券，4.开放式基金
	 * @param bigType
	 * @return
	 */
	private static int getBigType(String bigType){
		if(bigType.equals("A股") || bigType.equals("B股") || bigType.equals("股票"))
			return 1;
		else if(bigType.equals("指数"))
			return 2;
		else if(bigType.equals("债券"))
			return 3;
		else if(bigType.equals("开放式基金"))
			return 4;
		else
			return 0;
	}
}
