package com.cfwx.rox.businesssync.market.service.impl;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cfwx.dbf.hq.entity.SZStockInfo;
import com.cfwx.dbf.javadbf.DBFUtils;
import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.config.StockConfig;
import com.cfwx.rox.businesssync.market.config.TypeConfig;
import com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao;
import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.entity.StockInfo;
import com.cfwx.rox.businesssync.market.service.IDBFStockData;
import com.cfwx.rox.businesssync.market.service.IShDBFHQData;
import com.cfwx.rox.businesssync.market.service.ISzDBFHQData;
import com.cfwx.rox.businesssync.market.show.ResultInfo;
import com.cfwx.rox.businesssync.market.structure.BaseStructure;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.structure.TimeShareCache;
import com.cfwx.rox.businesssync.market.util.TimeUtils;
import com.cfwx.rox.businesssync.market.wsclient.EjdbWebService;
import com.cfwx.util.U;

/**
 * @author J.C.J
 * 更新股票信息时，根据配置文件把各个分类进行组合，深沪，深市，沪市全部里面只包含A股和B股的股票信息
 * 2013-9-24
 */
@Service
public class DBFStockDataImpl implements IDBFStockData {
	private final static Logger LOG = Logger.getLogger(DBFStockDataImpl.class);

	private String tempKey = "";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private SimpleDateFormat STOCKSDF = new SimpleDateFormat("yyyy/M/d");
	private Calendar c = Calendar.getInstance();
	
	private List<ResultInfo> tempList = null;
	
	private NumberFormat	nf2 = null;
	
	private NumberFormat	nf3 = null;
	@Autowired
	private IShDBFHQData shdbfHQ;
	@Autowired
	private ISzDBFHQData szdbfHQ;
	@Autowired
	private IQuoteCacheDao oCacheDao;
	
	private static final String SHREX = "(上交所).*";
	private static final String SZREX = "(深交所).*";
	
	@Override
	public void updateStockInfo(int mode)throws Exception {
		String s = "";
		try {
			 s = new EjdbWebService().getEjdbWebServiceSoap().getData("GetZQJCXX_WS");
		} catch (Exception e) {
			LOG.error("webservice第一次请求失败:"+e.getMessage());
			try {
				LOG.info("once again...");
				s = new EjdbWebService().getEjdbWebServiceSoap().getData("GetZQJCXX_WS");
			} catch (Exception e2) {
				LOG.error("webservice二次请求失败: "+e.getMessage());
			}
		}
		if (U.STR.isEmpty(s)) {
			s = oCacheDao.getStockBaseInfo();
		}
		try {
			if(!U.STR.isEmpty(s)){
				//初始化最新数据
				initData(s,mode);
				oCacheDao.addStockBaseInfo(s);
			}else{
				//清理数据
				initOldData();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new Exception(e);
		}
	}
	
	private void initData(String s,int mode)throws Exception{
		
		try {
			long st = System.currentTimeMillis();
			
			List<StockInfo> list = jsonStringToList(s);
			
			//填充内存股票信息,以及股票类型内存
			String[] filter = StockConfig.filter.split(",");
			
			String key = "";
			BaseStructure.typeMap.clear();
			BaseStructure.stockMap.clear();
			BaseStructure.pyMap.clear();
			BaseStructure.codeMap.clear();
			
			//添加大盘指数分类
			List<String> zslist = new ArrayList<String>();
			String[] zsArr=StockConfig.zs.split(",");
			
			for(int i=0;i<zsArr.length;i++){
				zslist.add(zsArr[i]);
			}
			BaseStructure.typeMap.put("-2", zslist);
			
			for(StockInfo si : list){
				
				boolean goon = true;
				for(String f :filter){
					if(si.getZQLB().equals(f)){
						goon = false;
						break;
					}
				}
				
				if(!goon){
					continue;
				}
				
				if(si.getJYSC().matches(SHREX) ){
					//白名单过滤
					boolean canRead = false;
					for(String code:StockConfig.getDcdsh()){
		        		if(si.getZQDM().matches("^("+code+").*")){
		        			//如果在配置内，则进行读取
		        			canRead =true;
		        			break;
		        		}
		        	}
					
					if(!canRead)
						continue;
					
					si.setPremaryCode("sh"+si.getZQDM());
					
					List<String> codelist = BaseStructure.typeMap.get("0");
					if(si.getZQLB().equals("A股") || si.getZQLB().equals("B股")){
						if(codelist == null)
							codelist = new ArrayList<String>();
						BaseStructure.typeMap.put("0", codelist);
						codelist.add(si.getPremaryCode());
					}
					
					key = BaseStructure.changeTypeMap.get("0_"+si.getZQLB());
					
					codelist =BaseStructure.typeMap.get(key);
					if(codelist == null)
						codelist = new ArrayList<String>();
					BaseStructure.typeMap.put(key, codelist);
					codelist.add(si.getPremaryCode());
					BaseStructure.stockMap.put(si.getPremaryCode(), si);
				}
				if(si.getJYSC().matches(SZREX)){
					//白名单过滤
					boolean canRead = false;
					for(String code:StockConfig.getDcdsz()){
		        		if(si.getZQDM().matches("^("+code+").*")){
		        			//如果在配置内，则进行读取
		        			canRead =true;
		        			break;
		        		}
		        	}
					
					if(!canRead)
						continue;
					
					si.setPremaryCode("sz"+si.getZQDM());
					
					List<String> codelist = BaseStructure.typeMap.get("1");
					if(si.getZQLB().equals("A股") || si.getZQLB().equals("B股")){
						if(codelist == null)
							codelist = new ArrayList<String>();
						BaseStructure.typeMap.put("1", codelist);
						codelist.add(si.getPremaryCode());
					}
					
					key = BaseStructure.changeTypeMap.get("1_"+si.getZQLB());
					
					codelist =BaseStructure.typeMap.get(key);
					if(codelist == null)
						codelist = new ArrayList<String>();
					BaseStructure.typeMap.put(key, codelist);
					codelist.add(si.getPremaryCode());
					BaseStructure.stockMap.put(si.getPremaryCode(), si);
				}
			}
			
			fullMarketMap();
			
			//读取行情信息
			shdbfHQ.getHQDataToMemory();
			szdbfHQ.getHQDataToMemory();
			
			if(mode!=1){
			
				if(TimeUtils.isNeedReOpen()){
					//开盘作业时间后，数据开始前
					
					//2.初始化分时数据
					TimeShareCache.clear();

					//3.清理行情，只保留昨收价
					clearMarketShowData();
				}
			}
			
			addIndex();
			
			long e = System.currentTimeMillis();
			LOG.info("股票信息及股票相关索引加载完成....耗时："+(e-st)+"=================================="+BaseStructure.stockMap.size());
			
//			Iterator<String> it = BaseStructure.typeMap.keySet().iterator();
//			System.out.println(BaseStructure.typeMap.size());
//			String ke ="";
//			while(it.hasNext()){
//				ke = it.next();
//				System.out.println(ke+":"+BaseStructure.typeMap.get(ke).size());
//			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 填充搜索的索引结构
	 */
	private void fullIndex(StockInfo si){
		
		ResultInfo ri = new ResultInfo();
		ri.setCode(si.getZQDM());
		ri.setPcode(si.getPremaryCode());
		ri.setName(si.getZQJC());
		ri.setPy(si.getPYJC());
		
		tempList = BaseStructure.pyMap.get(ri.getPy());
		if(tempList==null){
			tempList = new ArrayList<ResultInfo>();
			tempList.add(ri);
			BaseStructure.pyMap.put(ri.getPy(), tempList);
		}else{
			tempList.add(ri);
		}
		
		tempList = BaseStructure.codeMap.get(ri.getCode());
		if(tempList==null){
			tempList = new ArrayList<ResultInfo>();
			tempList.add(ri);
			BaseStructure.codeMap.put(ri.getCode(), tempList);
		}else{
			tempList.add(ri);
		}
	}
	
	/**
	 * 处理json字符串
	 * 
	 * @param rsContent
	 * @return 对象集合
	 * @throws Exception
	 */
	private List<StockInfo> jsonStringToList(String rsContent)
			throws Exception {
		List<StockInfo> jsonList = new ArrayList<StockInfo>();
		JSONObject jsonObject = null;
		StockInfo tempStock= null;
		try {
			JSONArray arry = JSONArray.fromObject(rsContent);
			for (int i = 0; i < arry.size(); i++) {
				jsonObject = arry.getJSONObject(i);
				tempStock = new StockInfo();
				for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
					String key = (String) iter.next();
					String value = jsonObject.get(key).toString();
					PropertyUtils.setProperty(tempStock, key, value);
				}
				jsonList.add(tempStock);
			}
		} catch (Exception e) {
			throw new Exception("处理字符串异常",e);
		}
		return jsonList;
	}
	/**
	 * 沪深全部数据
	 * @param si
	 */
	private void addHS(StockInfo si){
		List<String> hslist = BaseStructure.typeMap.get("-1");
		if(si.getZQLB().equals("A股") || si.getZQLB().equals("B股")){
			if(hslist == null)
				hslist = new ArrayList<String>();
			BaseStructure.typeMap.put("-1", hslist);
			hslist.add(si.getPremaryCode());
		}
		
		tempKey = BaseStructure.changeTypeMap.get("-1_"+si.getZQLB());
		
		hslist =BaseStructure.typeMap.get(tempKey);
		if(hslist == null)
			hslist = new ArrayList<String>();
		BaseStructure.typeMap.put(tempKey, hslist);
		hslist.add(si.getPremaryCode());
	}
	
	/**
	 * 初始化实时行情MAP
	 * 设置行情的部分数据
	 */
	@SuppressWarnings("static-access")
	private void fullMarketMap(){
		
		MarketCache.clear();
		//初始化NumberFormat
		initNumberFormat();
		
		Iterator<String> it = BaseStructure.stockMap.keySet().iterator();
		String key ="";
		ActualMarket tempAM = null;
		StockInfo tempStock = null;
		String time = "";
		String type = "";
		
		String nowDate = STOCKSDF.format(new Date());
		try {
			
			LOG.info("读取DBF文件...");
			//行情和证券信息中一直存在着昨收价
			//读取一份最新行情，比较依赖DBF文件。
			Map<String,SZStockInfo> sjsStock = readSJSStock();
			Map<String,Double> shStock = readSHStock();
			List<String> noUseList = new ArrayList<String>();
			
			while(it.hasNext()){
				key = it.next();
				
//				if(key.equals("sh000089")){
//					System.out.println("");
//				}
				
				tempStock = BaseStructure.stockMap.get(key);
				
				tempAM = new ActualMarket();
				
				tempAM.setPcode(key);
				tempAM.setMc(tempStock.getZQJC());
				tempAM.setDm(tempStock.getZQDM());
				
				tempAM.setGb(tempStock.getZGB().equals("")?0:Double.parseDouble(tempStock.getZGB()));
				tempAM.setLtgb(tempStock.getLTGB().equals("")?0:Double.parseDouble(tempStock.getLTGB()));
				tempAM.setMgsy(tempStock.getMGSY().equals("")?0:Double.parseDouble(tempStock.getMGSY()));
				tempAM.setEnlarge(BaseConfig.enlarge);
				tempAM.setType(tempStock.getZQLB());
				//定期报告季度
				if(!tempStock.getDQBGSJ().trim().equals("")){
					c.setTime(sdf.parse(tempStock.getDQBGSJ()));
					tempAM.setJd((c.get(c.MONTH)+1)*4/12);
				}
				
				type = BaseStructure.changeTypeMap.get(tempStock.getJYSC().matches("^sh.*")?"0_"+tempStock.getZQLB():"1_"+tempStock.getZQLB());
				if(type== null){
					//删除没有在行情中存在的数据
					noUseList.add(key);
//					LOG.info(tempStock.getZQJC()+","+tempStock.getZQDM()+","+tempStock.getZQLB()+".暂不支持该类型");
					continue;
				}
//				1.股票，2.指数，3.债券，4.开放式基金
				tempAM.setBigType(TypeConfig.getBigTypeValue(type));
				tempAM.setPointnum(TypeConfig.getPointNum(type));
				
				//设置报告季度
				time= tempStock.getDQBGSJ().trim();
				if(!time.equals("")){
					tempAM.setJd((Integer.parseInt(time.split("/")[1])*4)/12);
				}
				
				//上限与下限是需要昨收盘的。必须有之前的昨收盘数据。指数不设置上下限制。
				if(tempStock.getPremaryCode().matches("^sh.*")){
					tempAM.setMarket(0);
					if(shStock.get(tempStock.getPremaryCode())!=null){
						//添加上下限
						setSHLimit(tempStock,tempAM,shStock.get(tempStock.getPremaryCode()),nowDate);
					}
					else{
						//删除没有在行情中存在的数据
						noUseList.add(key);
						//不写入行情信息
						continue;
					}
				}else{
					tempAM.setMarket(1);
					setSZLimit(tempStock,tempAM, sjsStock,nowDate);
				}
				//添加至行情
				MarketCache.put(key, tempAM);
			}
			//循环结束后，删除多余的数据
			deleteNoUse(noUseList);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	private void initOldData()throws Exception{
		fullMarketMap();
		//读取行情信息
		shdbfHQ.getHQDataToMemory();
		szdbfHQ.getHQDataToMemory();
		LOG.info("行情旧数据加载完毕...");
	}

	private Map<String,SZStockInfo> readSJSStock()throws Exception{
		Map<String,List<String[]>> shMap = DBFUtils.readSJSDBF(BaseConfig.sjsStockDbf);
		
//		String[] specialArr = shMap.get("special").get(0);
		
		List<String[]> stockList = shMap.get("market");
		
		SZStockInfo temp = null;
		
		Map<String,SZStockInfo> sjsStock = new HashMap<String,SZStockInfo>();
		
		for(String[] s : stockList){
			temp = new SZStockInfo();
//			temp.setDate(specialArr[1]);
			//设置涨停价格，跌停价格
			temp.setXxztjg(s[28].trim());
			temp.setXxdtjg(s[29].trim());
			sjsStock.put("sz"+s[0].trim(),temp);
		}
		return sjsStock;
	}
	
	
	private Map<String,Double> readSHStock()throws Exception{
		Map<String,List<String[]>> shMap = DBFUtils.readSJSDBF(BaseConfig.shdbf);
		
//		String[] specialArr = shMap.get("special").get(0);
		
		List<String[]> stockList = shMap.get("market");
		
		Map<String,Double> shStock = new HashMap<String,Double>();
		
		for(String[] s : stockList){
//			temp.setDate(specialArr[1]);
			//设置昨收价
			if(s[2]!=null){
				shStock.put("sh"+s[0].trim(),Double.valueOf(s[2].trim()));
			}
		}
		return shStock;
	}
	
	private void setSHLimit(StockInfo tempStock,ActualMarket tempAM,double zs,String nowDate)throws Exception{
		
		//如果是新股，不设置上下限
		if(isNewStock(tempAM, nowDate))
			return ;
		
		//1.股票，2.指数，3.债券，4.开放式基金(指数，债券没有上下限，1有，另外就是小类型为封闭基金的也有)
		if(tempAM.getBigType()==1 || tempStock.getLTGB().equals("封闭基金")){
			if(tempAM.getPointnum()==3){
				tempAM.setUpperLimit(nf3.format(zs*1.1D));
				tempAM.setLowerLimit(nf3.format(zs*0.9D));
			}else{
				tempAM.setUpperLimit(nf2.format(zs*1.1D));
				tempAM.setLowerLimit(nf2.format(zs*0.9D));
			}
		}else{
			//指数设置
			tempAM.setUpperLimit("");
			tempAM.setLowerLimit("");
		}
	}
	
	private void setSZLimit(StockInfo tempStock,ActualMarket tempAM,Map<String,SZStockInfo> sjsStock,String nowDate)throws Exception{
		
		//如果是新股，不设置上下限
		if(isNewStock(tempAM, nowDate))
			return ;
		
		//非指数设置
		SZStockInfo temp = null;
		if(tempAM.getBigType()==1 || tempStock.getLTGB().equals("封闭基金")){
			temp = sjsStock.get(tempAM.getPcode());
			if(temp!= null){
				if(tempAM.getPointnum()==3){
					tempAM.setUpperLimit(nf3.format(nf3.parse(temp.getXxztjg())));
					tempAM.setLowerLimit(nf3.format(nf3.parse(temp.getXxdtjg())));
				}else{
					tempAM.setUpperLimit(nf2.format(nf2.parse(temp.getXxztjg())));
					tempAM.setLowerLimit(nf2.format(nf2.parse(temp.getXxdtjg())));
				}
			}
		}
		else{
			//指数设置
			tempAM.setUpperLimit("");
			tempAM.setLowerLimit("");
		}
	}
	
	/**
	 * 判断是否是新股上市
	 * @return
	 */
	private boolean isNewStock(ActualMarket tempAM,String nowDate){
		
		String ssDate = BaseStructure.stockMap.get(tempAM.getPcode()).getSSRQ().trim();
		if(!ssDate.equals("") && ssDate.split(" ").length==2){
			String[] arr = ssDate.split(" ");
			//今天是该股上市日期，即新股。
			if(arr[0].equals(nowDate) && tempAM.getBigType() ==1){
				LOG.info(nowDate+"新股上市"+tempAM.getPcode());
				return true;
			}
		}
		return false;
	}
	
	private void initNumberFormat(){
		nf2 = NumberFormat.getInstance();
		nf2.setRoundingMode(RoundingMode.HALF_UP);
		nf2.setMaximumFractionDigits(2);
		nf2.setMinimumFractionDigits(2);
		nf2.setGroupingUsed(false);  
		
		nf3 = NumberFormat.getInstance();
		nf3.setRoundingMode(RoundingMode.HALF_UP);
		nf3.setMaximumFractionDigits(3);
		nf3.setMinimumFractionDigits(3);
		nf3.setGroupingUsed(false);  
	}
	
	public  void clearMarketShowData()throws Exception{
		Iterator<String> it = MarketCache.iterator();
		String key ="";
		ActualMarket tempAM = null;
		while(it.hasNext()){
			key = it.next();
			tempAM = MarketCache.get(key);
			if(tempAM!= null){
				tempAM.setB1(0);
				tempAM.setB2(0);
				tempAM.setB3(0);
				tempAM.setB4(0);
				tempAM.setB5(0);
				tempAM.setS1(0);
				tempAM.setS2(0);
				tempAM.setS3(0);
				tempAM.setS4(0);
				tempAM.setS5(0);
				tempAM.setZg(0);
				tempAM.setZd(0);
				tempAM.setZe(0D);
				tempAM.setZl(0);
				tempAM.setZx(0);
				tempAM.setJk(0);
				tempAM.setZdf(0);
				tempAM.setZde(0);
			}
		}
	}
	
	private void deleteNoUse(List<String> noUseList){
		for(String key : noUseList){
			BaseStructure.stockMap.remove(key);
		}
	}
	
	/**
	 * 填充索引，添加至沪深
	 */
	private void addIndex(){
		Iterator<String> it = BaseStructure.stockMap.keySet().iterator();
		String key = "";
		StockInfo si = null;
		while(it.hasNext()){
			key = it.next();
			si = BaseStructure.stockMap.get(key);
			fullIndex(si);
			addHS(si);
		}
	}
}
