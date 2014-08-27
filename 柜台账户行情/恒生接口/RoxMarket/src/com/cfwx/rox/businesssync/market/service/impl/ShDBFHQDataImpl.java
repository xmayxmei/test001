package com.cfwx.rox.businesssync.market.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cfwx.dbf.javadbf.DBFUtils;
import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.service.IShDBFHQData;
import com.cfwx.rox.businesssync.market.util.MathFactory;

/**
 * @author J.C.J
 * 
 * 2013-9-24
 */
@Service
public class ShDBFHQDataImpl implements IShDBFHQData  {

	private final static Logger LOG = Logger.getLogger(ShDBFHQDataImpl.class);
	
//	private String tempTime ="";
	
	@Override
	public synchronized void getHQDataToMemory()throws Exception {
		try {
			readDBF();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new Exception(e.getMessage(),e);
		}
	}

	private void readDBF() throws Exception {
		try {
//			long s = System.currentTimeMillis();
			Map<String, List<String[]>> shMap = DBFUtils.readSHHQDBF();
			if(shMap!= null){
				writeMomerySH(shMap);
//				long e = System.currentTimeMillis();
//				LOG.info("上交读取：" + (e - s));
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new Exception(e.getMessage(), e);
		}
	}
	
	/**
	 * 格式转换，源DBF数据转换为内存数据
	 */
	private void writeMomerySH(Map<String,List<String[]>> shMap){
		
		//上交所数据转换，都转换成行情
		//深交所数据转换，都转换成行情
//				Object[] spec =shMap.get("special").get(0);
//				Long tempTime =Long.valueOf(spec[1].toString().trim());
				
				List<String[]> marketList = shMap.get("market");
				
				for(String[] ob: marketList){
					try {
						MathFactory.parseSHMarket(ob);
					} catch (Exception e) {
						continue;
					}
				}
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.service.IDBFHQData#writeOnceHQ()
	 */
	@Override
	public Map<String,Double> readOnceSHHQ()throws Exception {
		Map<String,Double> resultMap = null;
		try {
			Map<String,List<String[]>> shMap = DBFUtils.readDBF(BaseConfig.shdbf);
			List<String[]> marketList = shMap.get("market");
			resultMap =  MathFactory.readSHMarket(marketList);
		} catch (Exception e) {
			LOG.error("读取单次行情信息失败",e);
		}
		return resultMap;
	}
}
