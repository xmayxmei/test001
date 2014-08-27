package com.cfwx.rox.businesssync.market.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cfwx.dbf.javadbf.DBFUtils;
import com.cfwx.rox.businesssync.market.service.ISzDBFHQData;
import com.cfwx.rox.businesssync.market.util.MathFactory;

/**
 * @author J.C.J
 * 
 * 2013-9-24
 */
@Service
public class SzDBFHQDataImpl implements ISzDBFHQData {

	private final static Logger LOG = Logger.getLogger(SzDBFHQDataImpl.class);
	
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
			Map<String, List<String[]>> szMap = DBFUtils.readSJSHQDBF();
			if (szMap != null) {
				writeMomerySZ(szMap);
//				long e = System.currentTimeMillis();
//				LOG.info("深交读取：" + (e - s));
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
	
	/**
	 * 格式转换，源DBF数据转换为内存数据
	 */
	private void writeMomerySZ(Map<String,List<String[]>> szMap){
		
		//深交所数据转换，都转换成行情
//		Object[] spec =szMap.get("special").get(0);
//		Long tempTime =Long.valueOf(spec[7].toString().trim());
		
		List<String[]> marketList = szMap.get("market");
		
		for(String[] ob: marketList){
			try {
				MathFactory.parseSZMarket(ob);
			} catch (Exception e) {
				continue;
			}
		}
	}
}
