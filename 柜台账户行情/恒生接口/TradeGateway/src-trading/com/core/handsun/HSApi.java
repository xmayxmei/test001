/*
 * FileName: HandsunAPI.java
 * Copyright: Copyright 2014-6-10 Cfwx Tech. Co. Ltd.All right reserved.
 * Description: 
 *
 */
package com.core.handsun;

import org.apache.log4j.Logger;

import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;

/**
 * <code>HsApi<code>
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 10,2014)
 *
 */
public class HSApi {
	protected Logger L = Logger.getLogger(getClass());
	private static volatile HSApi instance;
	/** T2服务 */
	protected static T2Services oServer;
	/** 客户接口服务 */
	protected static IClient oClient;
	
	private HSApi() {
	}
	
	public static HSApi getInstance() {
		if (instance == null) {
			instance = new HSApi();
		}
		return instance;
	}
	/**
	 * Start Service
	 * @param sClientName
	 * @throws T2SDKException
	 */
	public void start(String sT2sdkConfigPath, String sClientName) throws T2SDKException {
		if(oServer == null) {
			oServer = T2Services.getInstance();
			L.info("setConfig");
			oServer.setT2sdkConfigString(sT2sdkConfigPath);
			L.info("servet Init");
			oServer.init();
			L.info("servet start");
			oServer.start();
			oClient = oServer.getClient(sClientName);
		}
	}
	/**
	 * Stop Service
	 */
	public synchronized void stop() {
		if(oServer != null) {
			oServer.stop();
		}
	}
	/**
	 * @return
	 */
	public IClient getClient() {
		return oClient;
	}
}
