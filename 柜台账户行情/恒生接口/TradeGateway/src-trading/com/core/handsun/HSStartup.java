/*
 * FileName: Startup.java
 * Copyright: Copyright 2014-6-11 Cfwx Tech. Co. Ltd.All right reserved.
 * Description: [这里用一句话描述]
 *
 */
package com.core.handsun;

import static com.util.SysConstants.HS.hErrorMapping;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.entity.HandsunErrorMapping;
import com.entity.HandsunErrorMapping.Part;
import com.entity.HandsunErrorMapping.Error;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.util.SysConstants;
/**
 * <code>Startup<code>
 * The following flows are the startup steps that we must follow their orders :<br>
 * 1) initGlobalProperties <br>
 * 2) start T2Service
 * 
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 10,2014)
 *
 */
public class HSStartup {
	protected final Logger L = Logger.getLogger(getClass());

	private static HSStartup instance;
	
	private HSStartup(){}
	
	public static HSStartup getInstance() {
		if (instance == null) {
			instance = new HSStartup();
		}
		return instance;
	}
	/**
	 * Initialize the Handsun properties.
	 * @param oProp
	 */
	public void initGlobalProperties(Properties oProp) {
		SysConstants.HS.clientName = oProp.getProperty("clientName");
		SysConstants.HS.waitTimeout = Long.parseLong(oProp.getProperty("waitTimeout", "10000"));
		SysConstants.HS.op_station= oProp.getProperty("op_station");
		SysConstants.HS.branch_no= oProp.getProperty("branch_no");
		SysConstants.HS.op_entrust_way= oProp.getProperty("op_entrust_way");
		SysConstants.HS.op_branch_no= oProp.getProperty("op_branch_no");
		SysConstants.HS.version= oProp.getProperty("version");
		SysConstants.HS.input_content= oProp.getProperty("input_content");
		SysConstants.HS.content_type= oProp.getProperty("content_type");
		SysConstants.HS.query_direction=oProp.getProperty("query_direction");
		L.info("clientName :" + SysConstants.HS.clientName);
		L.info("waitTimeout :" + SysConstants.HS.waitTimeout);
		L.info("op_station :" + SysConstants.HS.op_station);
		L.info("branch_no :" + SysConstants.HS.op_entrust_way);
		L.info("op_entrust_way :" + SysConstants.HS.op_entrust_way);
		L.info("op_branch_no :" + SysConstants.HS.op_branch_no);
		L.info("version :" + SysConstants.HS.version);
		L.info("input_content :" + SysConstants.HS.input_content);
		L.info("content_type :" + SysConstants.HS.content_type);
		L.info("query_direction :" + SysConstants.HS.query_direction);
	}
	/**
	 * @param sMappingFilePath
	 */
	public void initErrorCodeMapping(String sMappingFilePath) {
		if (hErrorMapping == null) {
			hErrorMapping = new HashMap<String, Map<String,String>>();
		}
		XStream xStream = new XStream(new DomDriver());  
	    xStream.autodetectAnnotations(true);
	    xStream.processAnnotations(HandsunErrorMapping.class);
	    try {
	    	HandsunErrorMapping oMapping = (HandsunErrorMapping)xStream.fromXML(new FileInputStream(new File(sMappingFilePath)));
	    	List<HandsunErrorMapping.Error> lstError = oMapping.getLstError();
	    	if (lstError != null) {
	    		int iLen = lstError.size();
	    		for (int i = 0; i < iLen; i++) {
	    			Error oErr = lstError.get(i);
	    			String sFunId = oErr.getFunctionId();
	    			Map<String, String> hData = hErrorMapping.get(sFunId);
	    			if (hData == null) {
	    				hData = new HashMap<String, String>();
	    				hErrorMapping.put(sFunId, hData);
	    			}
	    			List<Part> lstPart = oErr.getLstPart();
	    			if (lstPart == null) {
	    				continue;
	    			}
	    			for (int ii = 0; ii < lstPart.size(); ii++) {
	    				Part oPart = lstPart.get(ii);
	    				hData.put(oPart.getErrCode(), oPart.getErrMsg());
	    			}
	    		}
	    		
	    	}
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Initialize the T2Service.
	 * @throws T2SDKException 
	 */
	public void initT2Service(String sT2sdkConfig) throws T2SDKException {
		HSApi.getInstance().start(sT2sdkConfig, SysConstants.HS.clientName);
		L.info("启动T2Service成功!");
	}
}
