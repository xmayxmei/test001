package com.cfwx.rox.businesssync.market.util;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.service.IQuoteService;
import com.cfwx.rox.businesssync.market.show.DayLine;
import com.cfwx.rox.businesssync.market.show.ShowAM;
import com.cfwx.rox.businesssync.market.show.SortObj;
import com.cfwx.rox.businesssync.market.show.TimeShare;
import com.cfwx.rox.businesssync.market.show.ZhiShu;
import com.cfwx.rox.businesssync.market.structure.MarketCache;

/**
 * @author J.C.J
 * 上交所提供的市盈率，深交所要自己计算
 * 比率小数点后保留2位有效数字，金额采用系统配置
 * 2013-11-23
 */
public class MathFactory {
	
	private final static Logger LOG = Logger.getLogger(MathFactory.class);
	/**
	 * 系统配置,小数点后面3位有效数字
	 * 切记 NumberFormat不是线程安全的  多线程情况下确保线程安全
	 */
	public static NumberFormat nf3 = null;
	/**
	 * 小数点后面0位有效数字
	 * 切记 NumberFormat不是线程安全的  多线程情况下确保线程安全
	 */
	public static NumberFormat nf0 = null;
	/**
	 * 小数点后面2位有效数字
	 * 切记 NumberFormat不是线程安全的  多线程情况下确保线程安全
	 */
	public static NumberFormat nf2 = null;
	
	/**
	 * 上交所数据转换为行情
	 * @param o
	 * @return
	 */
	public static ActualMarket parseSHMarket(String[] o)throws Exception{
		ActualMarket tempMarket = null;
		
		tempMarket = MarketCache.get("sh"+o[0]);
		if(tempMarket == null) {
			return null;
		}
		
		try {
			setSHMarketValue(tempMarket,o);
			//当前买入价格 s9;
			//当前卖出价格 s10;
			tempMarket.setZe(Double.parseDouble(nf3.format(nf3.parse(o[4].trim()))));//今成交金额 s5;
			//设置成交量
			setSHZL(tempMarket,o[10].trim());
				
			tempMarket.setSy(o[11].trim().equals("-.---")?0D:Double.parseDouble(nf3.format(nf3.parse(o[11].trim()))));//市盈率 s13;
			tempMarket.setB1(o[12].trim().equals("-")?0:Long.parseLong(o[12].trim()));//申买量一 s15;
			tempMarket.setB2(o[14].trim().equals("-")?0:Long.parseLong(o[14].trim()));//申买量二 s17;
			tempMarket.setB3(o[16].trim().equals("-")?0:Long.parseLong(o[16].trim()));//申买量三 s19;
			tempMarket.setB4(o[23].trim().equals("-")?0:Long.parseLong(o[23].trim()));//申买量四 s27;
			tempMarket.setB5(o[25].trim().equals("-")?0:Long.parseLong(o[25].trim()));//申买量五 s29;
			tempMarket.setS1(o[17].trim().equals("-")?0:Long.parseLong(o[17].trim()));//申卖量一 s21;
			tempMarket.setS2(o[19].trim().equals("-")?0:Long.parseLong(o[19].trim()));//申卖量二 s23;
			tempMarket.setS3(o[21].trim().equals("-")?0:Long.parseLong(o[21].trim()));//申卖量三 s25; 
			tempMarket.setS4(o[27].trim().equals("-")?0:Long.parseLong(o[27].trim()));//申卖量四 s31;
			tempMarket.setS5(o[29].trim().equals("-")?0:Long.parseLong(o[29].trim()));//申卖量五 s33;
			
			
			tempMarket.setB1Price(o[8].trim().equals("-.---")?0D:Double.parseDouble(o[8].trim()));//买一价  当前买入价格 s9;
			tempMarket.setB2Price(o[13].trim().equals("-.---")?0D:Double.parseDouble(o[13].trim()));//当前买二价格 s14;
			tempMarket.setB3Price(o[15].trim().equals("-.---")?0D:Double.parseDouble(o[15].trim()));//当前买三价格 s16;
			tempMarket.setB4Price(o[22].trim().equals("-.---")?0D:Double.parseDouble(o[22].trim()));//当前买四价格 s13;
			tempMarket.setB5Price(o[24].trim().equals("-.---")?0D:Double.parseDouble(o[24].trim()));//当前买五价格 s25;
			
			
			tempMarket.setS1Price(o[9].trim().equals("-.---")?0D:Double.parseDouble(o[9].trim()));//卖一价  当前卖出价格 s10;
			tempMarket.setS2Price(o[18].trim().equals("-.---")?0D:Double.parseDouble(o[18].trim()));//卖二价 s22;
			tempMarket.setS3Price(o[20].trim().equals("-.---")?0D:Double.parseDouble(o[20].trim()));//卖三价 s24;
			tempMarket.setS4Price(o[26].trim().equals("-.---")?0D:Double.parseDouble(o[26].trim()));//卖四价 s30;
			tempMarket.setS5Price(o[28].trim().equals("-.---")?0D:Double.parseDouble(o[28].trim()));//卖五价 s32;
			
			
			tempMarket.setMarket(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			throw new Exception(e);
		}
		return tempMarket;
	}
	
	/**
	 * 读取上交所昨收价
	 * @param o
	 * @return
	 */
	public static Map<String,Double> readSHMarket(List<String[]> shList){
		String key = "";
		Map<String,Double> map = new HashMap<String,Double>();
		for(String[] ob: shList){
				key = "sh"+ob[0];
				map.put(key, Double.parseDouble(ob[2]));//前收盘价格 s3;
		}
		return map;
	}
	
	/**
	 * 深交所数据转换为行情数据
	 * @param o
	 * @return
	 */
	public static synchronized ActualMarket  parseSZMarket(String[] o)throws Exception{
		ActualMarket  tempMarket = MarketCache.get("sz"+o[0]);
//		if(tempMarket.getPcode().equals("sz112148")){
//			System.out.println(tempMarket.getPcode());
//		}
		
		if(tempMarket == null)
		return null;
		try {
			setSZMarketValue(tempMarket,o);
			tempMarket.setZe(Double.valueOf(nf3.format(nf3.parse(o[6].trim()))));//今成交金额 s5;
			//设置成交量
			setSZZL(tempMarket,o[5].trim());
//			tempMarket.setZl(Long.valueOf(nf0.format(nf0.parse(o[5].trim()))));//成交数量 s11;
//			tempMarket.setSy(Double.parseDouble(o[12]));//市盈率 s13;需计算
			
			
			tempMarket.setB1Price(Double.valueOf(nf2.format(nf2.parse(o[25].trim()))));//买一 26;
			tempMarket.setB2Price(Double.valueOf(nf2.format(nf2.parse(o[27].trim()))));//买二 28;
			tempMarket.setB3Price(Double.valueOf(nf2.format(nf2.parse(o[29].trim()))));//买三 30;
			tempMarket.setB4Price(Double.valueOf(nf2.format(nf2.parse(o[31].trim()))));//买四 32;
			tempMarket.setB5Price(Double.valueOf(nf2.format(nf2.parse(o[33].trim()))));//买五 34;
			
			tempMarket.setB1(Long.valueOf(nf0.format(nf0.parse(o[24].trim()))));//申买量一 25;
			tempMarket.setB2(Long.valueOf(nf0.format(nf0.parse(o[26].trim()))));//申买量二 27;
			tempMarket.setB3(Long.valueOf(nf0.format(nf0.parse(o[28].trim()))));//申买量三 29;
			tempMarket.setB4(Long.valueOf(nf0.format(nf0.parse(o[30].trim()))));//申买量四 31;
			tempMarket.setB5(Long.valueOf(nf0.format(nf0.parse(o[32].trim()))));//申买量五 33;
			
			tempMarket.setS1Price(Double.valueOf(nf2.format(nf2.parse(o[23].trim()))));//卖一 24;
			tempMarket.setS2Price(Double.valueOf(nf2.format(nf2.parse(o[21].trim()))));//卖二 22;
			tempMarket.setS3Price(Double.valueOf(nf2.format(nf2.parse(o[19].trim()))));//卖三 20;
			tempMarket.setS4Price(Double.valueOf(nf2.format(nf2.parse(o[17].trim()))));//卖四 18;
			tempMarket.setS5Price(Double.valueOf(nf2.format(nf2.parse(o[15].trim()))));//卖五 16;
			
			
			tempMarket.setS1(Long.valueOf(nf0.format(nf0.parse(o[24].trim()))));//申卖量一 25;
			tempMarket.setS2(Long.valueOf(nf0.format(nf0.parse(o[22].trim()))));//申卖量二 23;
			tempMarket.setS3(Long.valueOf(nf0.format(nf0.parse(o[20].trim()))));//申卖量三 21; 
			tempMarket.setS4(Long.valueOf(nf0.format(nf0.parse(o[18].trim()))));//申卖量四 19;
			tempMarket.setS5(Long.valueOf(nf0.format(nf0.parse(o[16].trim()))));//申卖量五 17;
			tempMarket.setMarket(1);
		} catch (NumberFormatException e) {
			LOG.error(tempMarket.getPcode()+":"+e.getCause().getLocalizedMessage()+e.getMessage(),e);
			throw new Exception(e);
		}
		catch (Exception e) {
			LOG.error(tempMarket.getPcode()+":"+e.getMessage(),e);
			throw new Exception(e);
		}
		return tempMarket;
	}
	
	private static void  setSHMarketValue(ActualMarket temp,String[] values)throws Exception{
			try {
				temp.setZs(Long.valueOf(nf0.format(Double.parseDouble(values[2].trim())*temp.getEnlarge())));//前收盘价格 s3;
				temp.setJk(Long.valueOf(nf0.format(Double.parseDouble(values[3].trim())*temp.getEnlarge())));//今开盘价格 s4;
				temp.setZg(Long.valueOf(nf0.format(Double.parseDouble(values[5].trim())*temp.getEnlarge())));//最高价格 s6;
				temp.setZd(Long.valueOf(nf0.format(Double.parseDouble(values[6].trim())*temp.getEnlarge())));//最低价格 s7;
				temp.setZx(Long.valueOf(nf0.format(Double.parseDouble(values[7].trim())*temp.getEnlarge())));//最新价格 s8;
				if(temp.getZx() != 0){
					temp.setZde((temp.getZx()-temp.getZs())); //涨跌额
					temp.setZdf(Long.valueOf(nf0.format((temp.getZde()/Double.parseDouble(temp.getZs()+""))*100000))); //涨跌幅
				}else{
					temp.setZde(0); //涨跌额
					temp.setZdf(0); //涨跌幅
				}
			} catch (Exception e) {
//				StringBuffer sb = new StringBuffer();
//				for(int i =0;i<values.length;i++){
//					sb.append(" ").append(i).append(" : ").append(values[i].trim()).append(";");
//				}
				LOG.error(temp.getPcode()+e.getMessage());
				throw new Exception(temp.getPcode()+e.getMessage(),e);
			}
	}
	
	private static void  setSZMarketValue(ActualMarket temp,String[] values)throws Exception{
		try {
			temp.setZs(Long.valueOf(nf0.format(Double.parseDouble(values[2].trim())*temp.getEnlarge())));//前收盘价格 s3;
			temp.setJk(Long.valueOf(nf0.format(Double.parseDouble(values[3].trim())*temp.getEnlarge())));//今开盘价格 s4;
			temp.setZg(Long.valueOf(nf0.format(Double.parseDouble(values[8].trim())*temp.getEnlarge())));//最高价格 s6;
			temp.setZd(Long.valueOf(nf0.format(Double.parseDouble(values[9].trim())*temp.getEnlarge())));//最低价格 s7;
			temp.setZx(Long.valueOf(nf0.format(Double.parseDouble(values[4].trim())*temp.getEnlarge())));//最新价格 s8;
			
			if(temp.getZx() != 0){
				temp.setZde(temp.getZx()-temp.getZs()); //涨跌额
				temp.setZdf(Long.valueOf(nf0.format((temp.getZde()/Double.parseDouble(temp.getZs()+""))*100000))); //涨跌幅
			}else{
				temp.setZde(0); //涨跌额
				temp.setZdf(0); //涨跌幅
			}
		} catch (Exception e) {
			LOG.error(temp.getPcode()+e.getMessage());
			throw new Exception(temp.getPcode()+e.getMessage(),e);
		}
	}
	/**
	 * 上交所：S11成交数量的单位对股票为股，基金为份，债券与回购为手，权证为100份。如果累计总量超过9999999999，显示为9999999999。
	 * @param am
	 * @param zl
	 */
	private static synchronized void setSHZL(ActualMarket  am,final String zl){
		try {
			if(!"".equals(zl)){
				//1.股票，2.指数，3.债券，4.开放式基金
				if(am.getBigType()==1 || am.getBigType()==4){
					am.setZl(Long.valueOf(nf0.format(Double.valueOf(zl)/100)));//成交数量
				}
				else{
					Number num = nf0.parse(zl);
					String sNum = nf0.format(num);
					am.setZl(Long.valueOf(sNum));//成交数量
				}
			}else{
				am.setZl(Long.valueOf(nf0.format(0)));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("zl="+zl+",---"+e.getMessage(), e.getCause());
		}
	}
	/**
	 * 深交所:原值。基础单位,转换为百单位。手=100股 ,债券10张为1手
	 * @param am
	 * @param zl
	 */
	private synchronized static void setSZZL(ActualMarket  am,String zl){
		try {
			//1.股票，2.指数，3.债券，4.开放式基金
			if(am.getBigType() != 3)
				am.setZl(Long.valueOf(nf0.format(Double.valueOf(zl)/100)));//成交数量
			else
				am.setZl(Long.valueOf(nf0.format(Double.valueOf(zl)/10)));//成交数量
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 排行信息
	 * @param am
	 * @return
	 */
	public static SortObj parseSortObj(ActualMarket am){
		NumberFormat tempnf = NumberFormat.getInstance();
		tempnf.setMaximumFractionDigits(am.getPointnum());
		tempnf.setMinimumFractionDigits(am.getPointnum());
		tempnf.setGroupingUsed(false);  
		
		NumberFormat nf2 = NumberFormat.getInstance();
		nf2.setRoundingMode(RoundingMode.HALF_UP);
		nf2.setMaximumFractionDigits(2);
		nf2.setMinimumFractionDigits(2);
		nf2.setGroupingUsed(false); 
		
		SortObj so = new SortObj();
		
		try {
			so.setDm(am.getDm());
			so.setMc(am.getMc());
			so.setZd(tempnf.format(am.getZd()/am.getEnlarge()));
			so.setZde(tempnf.format((am.getZde()/am.getEnlarge())));
			if(am.getZdf()==BaseConfig.DEFAULTLONGVALUE)
				so.setZdf(nf2.format(0));
			else
				so.setZdf(am.getZdf()!=0?nf2.format((am.getZdf()/1000D)):"");
			
			if(am.getJk()!=0){
				so.setJk(tempnf.format(am.getJk()/am.getEnlarge()));
				so.setZx(tempnf.format(am.getZx()/am.getEnlarge()));
			}else{
				//如果没有开盘，设置最高，最低为0
				so.setJk("");
				so.setZx("");
			}
			so.setZe(parseMeneyUnit(tempnf.format(am.getZe()),tempnf));
			so.setZg(tempnf.format(am.getZg()/am.getEnlarge()));
			so.setZl(parseUnit(tempnf.format(am.getZl()),tempnf));
			so.setZs(tempnf.format(am.getZs()/am.getEnlarge()));
			so.setPcode(am.getPcode());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return so;
	}
	
	public static ShowAM parseShowAM(ActualMarket am){
		
		NumberFormat nf2 = NumberFormat.getInstance();
		nf2.setRoundingMode(RoundingMode.HALF_UP);
		nf2.setMaximumFractionDigits(2);
		nf2.setMinimumFractionDigits(2);
		nf2.setGroupingUsed(false); 
		
		NumberFormat nf0 = NumberFormat.getInstance();
		nf0.setRoundingMode(RoundingMode.HALF_UP);
		nf0.setMaximumFractionDigits(0);
		nf0.setGroupingUsed(false);  
		
		NumberFormat tempnf = NumberFormat.getInstance();
		tempnf.setMaximumFractionDigits(am.getPointnum());
		tempnf.setMinimumFractionDigits(am.getPointnum());
		tempnf.setGroupingUsed(false);
		
		ShowAM showAM = new ShowAM();
		
		//排行显示字段
		showAM.setDm(am.getDm());
		showAM.setMc(am.getMc());
		showAM.setZde(tempnf.format(am.getZde()/am.getEnlarge()));
		showAM.setZdf(nf2.format(am.getZdf()/1000D));
		showAM.setZe(parseMeneyUnit(tempnf.format(am.getZe()),tempnf));
		showAM.setZl(parseUnit(tempnf.format(am.getZl()),tempnf));
		showAM.setZs(tempnf.format(am.getZs()/am.getEnlarge()));
		
		if(am.getJk()!=0){
			showAM.setJk(tempnf.format(am.getJk()/am.getEnlarge()));
			showAM.setZg(tempnf.format(am.getZg()/am.getEnlarge()));
			showAM.setZd(tempnf.format(am.getZd()/am.getEnlarge()));
			showAM.setZx(tempnf.format(am.getZx()/am.getEnlarge()));
		}else{
			//如果没有开盘，设置最高，最低为0
			showAM.setJk("");
			showAM.setZg("");
			showAM.setZd("");
			showAM.setZx("");
		}
		
		showAM.setB1(nf0.format(am.getB1()/100));
		showAM.setB2(nf0.format(am.getB2()/100));
		showAM.setB3(nf0.format(am.getB3()/100));
		showAM.setB4(nf0.format(am.getB4()/100));
		showAM.setB5(nf0.format(am.getB5()/100));
		
		showAM.setB1Price(String.valueOf(am.getB1Price()));
		showAM.setB2Price(String.valueOf(am.getB2Price()));
		showAM.setB3Price(String.valueOf(am.getB3Price()));
		showAM.setB4Price(String.valueOf(am.getB4Price()));
		showAM.setB5Price(String.valueOf(am.getB5Price()));
		
		
		showAM.setS1(nf0.format(am.getS1()/100));
		showAM.setS2(nf0.format(am.getS2()/100));
		showAM.setS3(nf0.format(am.getS3()/100));
		showAM.setS4(nf0.format(am.getS4()/100));
		showAM.setS5(nf0.format(am.getS5()/100));
		
		showAM.setS1Price(String.valueOf(am.getS1Price()));
		showAM.setS2Price(String.valueOf(am.getS2Price()));
		showAM.setS3Price(String.valueOf(am.getS3Price()));
		showAM.setS4Price(String.valueOf(am.getS4Price()));
		showAM.setS5Price(String.valueOf(am.getS5Price()));
		
		showAM.setPcode(am.getPcode());
		showAM.setUpperLimit(am.getUpperLimit());
		showAM.setLowerLimit(am.getLowerLimit());
		if(am.getMarket()>0){
			showAM.setMarket("sz");
		}else{
			showAM.setMarket("sh");
		}
		
		//股票实时行情字段
		if(am.getLtgb()!=0D)
			showAM.setHs(nf2.format((am.getZl()*100/am.getLtgb())*100));//换手 :交易量/流通股数
		
		//市盈率
		if(am.getMarket() == 0){
			//上交所 有自己计算的市盈,如果没有也要计算
			if(am.getSy()!=0D){
				showAM.setSy(tempnf.format(am.getSy()));
			}else{
				if(am.getJd()!=0 && am.getMgsy()!= 0D){
					showAM.setSy(tempnf.format((am.getZx()/1000D)/(am.getMgsy()*4/am.getJd())));//市盈 ：动态市盈率：每股最新价/(每股收益*(4/当前季度 ))
				}
				else
					showAM.setSy(tempnf.format(0));
			}
		}else{
			if(am.getJd()!=0 && am.getMgsy()!= 0D){
				showAM.setSy(tempnf.format((am.getZx()/1000D)/(am.getMgsy()*4/am.getJd())));//市盈 ：动态市盈率：每股最新价/(每股收益*(4/当前季度 ))
			}
			else
				showAM.setSy(tempnf.format(0));
		}
		
		/*if(am.getZl()!=0D){
			showAM.setJj(tempnf.format(am.getZe()/(am.getZl()*100)));//均价
		}
		else
			showAM.setJj(tempnf.format(0));*///均价
			
//		showAM.setWb(getWeiBi(am,nf2));//委比 :(总委买量-总委卖量)/(总委买量+总委卖量)
//		showAM.setLb(getLiangBi(am,nf2));//量比 :总量/前五日平均成交量,取K线记录
		showAM.setGb(parseMeneyUnit(tempnf.format(am.getGb()),tempnf));//股本
		showAM.setSz(parseMeneyUnit(tempnf.format(am.getGb() * am.getZx() / 1000),tempnf)); //市值
		
//		private String wb;//委比 :(总委买量-总委卖量)/(总委买量+总委卖量)
//		private String lb;//量比 :总量/前五日平均成交量
		showAM.setJj("");
		showAM.setWb("");
		showAM.setLb("");
		//1.股票，2.指数，3.债券，4.开放式基金
		if(am.getBigType() != 2){
			Double dAmount = am.getZe();
			Long lVolume = am.getZl();
			boolean bFlag = lVolume != null && lVolume != 0 && dAmount != null && dAmount != 0;
			// 计算均价
			if (bFlag) {
				showAM.setJj(tempnf.format((dAmount / (lVolume * 100))));
			}
			// 计算委比
			showAM.setWb(getWeiBi(am, nf2));
			// 计算量比
			showAM.setLb(getLiangBi(am, nf2));
		}
		
		return showAM;
	}
	
	/**
	 * 转换为分时
	 * @param am
	 * @param time HHmmss
	 * @param timeArea 时间区间：0=9:30以前(包含)，1=9:30-15:30之间，2=15:30以后(包含)
	 * @return
	 */
	public static TimeShare parseTimeShare(ActualMarket am,String time){
		NumberFormat tempnf = NumberFormat.getInstance();
		tempnf.setMaximumFractionDigits(am.getPointnum());
		tempnf.setMinimumFractionDigits(am.getPointnum());
		tempnf.setGroupingUsed(false);

		TimeShare ts = new TimeShare();
		ts.setZe(am.getZe());
		ts.setNewPrice(tempnf.format(am.getZx()/am.getEnlarge()));
		ts.setTime(time);
		ts.setZl(Double.valueOf(am.getZl()));
		ts.setVolume(tempnf.format(am.getZl()));
		ts.setAmount(tempnf.format(am.getZe()));
		
		if(am.getBigType()!=2 && am.getZl()!=0)
			ts.setAverage(tempnf.format(am.getZe()/(am.getZl()*100D)));
		return ts;
	}
	
	public static DayLine parseKLineDaily(ActualMarket am,String date){
		NumberFormat tempnf = NumberFormat.getInstance();
		tempnf.setMaximumFractionDigits(am.getPointnum());
		tempnf.setMinimumFractionDigits(am.getPointnum());
		tempnf.setGroupingUsed(false);
		
		DayLine kd = new DayLine();
		kd.setClose(tempnf.format(am.getZx()/am.getEnlarge()));
		kd.setTime(date);
		
		//针对债券，做如下处理
		if(am.getZg()!=0){
			kd.setHigh(tempnf.format(am.getZg()/am.getEnlarge()));
		}else{
			kd.setHigh(tempnf.format(am.getZx()/am.getEnlarge()));
		}
		
		if(am.getZd()!=0){
			kd.setLow(tempnf.format(am.getZd()/am.getEnlarge()));
		}else{
			kd.setLow(tempnf.format(am.getZx()/am.getEnlarge()));
		}
		
		kd.setOpen(tempnf.format(am.getJk()/am.getEnlarge()));
		kd.setVolume(tempnf.format(am.getZl()));
		kd.setAmount(tempnf.format(am.getZe()));
		
		return kd;
	}
	
/*	*//**
	 * @param am
	 * @param i
	 * @return
	 *//*
	public static CommonLine parseCommonKLine(ActualMarket am, CommonLine lstDayMarket) {
		NumberFormat tempnf = NumberFormat.getInstance();
		tempnf.setMaximumFractionDigits(am.getPointnum());
		tempnf.setMinimumFractionDigits(am.getPointnum());
		tempnf.setGroupingUsed(false);
		
		lstDayMarket.setClose(tempnf.format(am.getZx()/am.getEnlarge()));
		
		//针对债券，做如下处理
		if(am.getZg()!=0){
			lstDayMarket.setHigh(tempnf.format(am.getZg()/am.getEnlarge()));
		}else{
			lstDayMarket.setHigh(tempnf.format(am.getZx()/am.getEnlarge()));
		}
		if(am.getZd()!=0){
			lstDayMarket.setLow(tempnf.format(am.getZd()/am.getEnlarge()));
		}else{
			lstDayMarket.setLow(tempnf.format(am.getZx()/am.getEnlarge()));
		}
		lstDayMarket.setOpen(tempnf.format(am.getJk()/am.getEnlarge()));
		lstDayMarket.setVolume(tempnf.format(am.getZl()));
		lstDayMarket.setAmount(tempnf.format(am.getZe()));
		return lstDayMarket;
	}*/

	
	/**
	 * 计算总页数
	 * @param listSize
	 * @param psize
	 * @return
	 */
	public static String getAllSize(int listSize,int psize){
		String value = "";
		if(psize!=0){
			long num =listSize/psize;
			if(listSize%psize>0){
				num+=1;
			}
			value = Long.toString(num);
		}
		return value;
	}
	
	public static ZhiShu parseZhiShu(ActualMarket am){
		NumberFormat nf2 = NumberFormat.getInstance();
		nf2.setRoundingMode(RoundingMode.HALF_UP);
		nf2.setMaximumFractionDigits(2);
		nf2.setMinimumFractionDigits(2);
		nf2.setGroupingUsed(false); 
		
		ZhiShu zs  =new ZhiShu();
		NumberFormat tempnf = NumberFormat.getInstance();
		tempnf.setMaximumFractionDigits(am.getPointnum());
		tempnf.setMinimumFractionDigits(am.getPointnum());
		tempnf.setGroupingUsed(false);
		zs.setName(am.getMc());
		zs.setPcode(am.getPcode());
		zs.setZde(tempnf.format(am.getZde()/am.getEnlarge()));
		zs.setZdf(am.getZdf()==0?"0.00":nf2.format(am.getZdf()/1000D));
		zs.setZx(tempnf.format(am.getZx()/am.getEnlarge()));
		return zs;
	}
	
	public static void initNumberFormat(){
		nf3 = NumberFormat.getInstance();
		nf3.setRoundingMode(RoundingMode.HALF_UP);
		nf3.setMaximumFractionDigits(3);
		nf3.setMinimumFractionDigits(3);
		nf3.setGroupingUsed(false);  
		
		nf0 = NumberFormat.getInstance();
		nf0.setRoundingMode(RoundingMode.HALF_UP);
		nf0.setMaximumFractionDigits(0);
		nf0.setGroupingUsed(false);  
		
		nf2 = NumberFormat.getInstance();
		nf2.setRoundingMode(RoundingMode.HALF_UP);
		nf2.setMaximumFractionDigits(2);
		nf2.setMinimumFractionDigits(2);
		nf2.setGroupingUsed(false);  
	}
	
//	/**
//	 * 量比 :总量/前五日平均成交量,取K线记录
//	 * @param am
//	 * @param tempnf
//	 * @return
//	 */
	private static String getLiangBi(ActualMarket am, NumberFormat tempnf){
		IQuoteService oQuoteSerivce = SpringContextUtils.getBean(IQuoteService.class);
		List<DayLine> list = oQuoteSerivce.getDailyKLine(am.getPcode(), 6);
		if (list == null || list.size() < 6) {
			return "";
		}
		DayLine oLast = list.get(5);
		String sVolPct = "";
		try {
			double zl = 0.000D;
			for (int i = 0; i < 5; i++) {
				DayLine kd = list.get(i);
				zl += Double.valueOf(kd.getVolume());
			}
			if (zl != 0D) {
				sVolPct = tempnf.format(Double.valueOf(oLast.getVolume())/ (zl / 5));
			} else {
				sVolPct = tempnf.format(0);
			}
		} catch (Exception e) {
			LOG.error("量比计算异常...", e);
		}
		return sVolPct;
	}
	/**
	 * @param am
	 * @param tempnf
	 * @return
	 */
	private static String getWeiBi(ActualMarket am, NumberFormat tempnf) {
		long buy = am.getB1() + am.getB2() + am.getB3() + am.getB4()
				+ am.getB5();
		long sell = am.getS1() + am.getS2() + am.getS3() + am.getS4()
				+ am.getS5();

		if ((buy + sell) != 0D) {
			return tempnf.format((Double.valueOf((buy - sell)) / (buy + sell)) * 100);
		} else {
			return tempnf.format(0D);
		}
	}
	
	private static String parseUnit(String num,NumberFormat tempnf){
		String resStr = "";
		if(!num.trim().equals("")){
			int ind = num.indexOf(".");
			if(ind !=-1)
				num =num.substring(0, num.indexOf("."));
			if(num.length()>4 && num.length()<9){
				//万
				resStr = tempnf.format(Double.valueOf(num)/10000)+"万";
			}else if(num.length()>8){
				 //亿 
				resStr = tempnf.format(Double.valueOf(num)/100000000)+"亿";
			}else{
				if(Double.valueOf(num)%1==0) {
					NumberFormat nf0 = NumberFormat.getInstance();
					nf0.setRoundingMode(RoundingMode.HALF_UP);
					nf0.setMaximumFractionDigits(0);
					nf0.setGroupingUsed(false); 
					resStr = nf0.format(Double.valueOf(num));
				} else {
					resStr = tempnf.format(Double.valueOf(num));
				}
			}
		}
		return resStr;
	}
	
	private static String parseMeneyUnit(String num,NumberFormat tempnf){
		
		String resStr = "";
		String temp = "";
		if(!num.trim().equals("")){
			
			int ind = num.indexOf(".");
			if(ind !=-1)
				temp =num.substring(0, num.indexOf("."));
			if(temp.length()>4 && temp.length()<9){
				//万
				resStr = tempnf.format(Double.valueOf(num)/10000)+"万";
			}else if(temp.length()>8){
				 //亿 
				resStr = tempnf.format(Double.valueOf(num)/100000000)+"亿";
			}else{
				resStr = tempnf.format(Double.valueOf(num));
			}
		}
		return resStr;
	}
}
