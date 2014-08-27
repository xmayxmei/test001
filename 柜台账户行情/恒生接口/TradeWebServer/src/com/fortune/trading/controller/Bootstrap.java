package com.fortune.trading.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fortune.client.main.GatewayClient;
import com.fortune.trading.util.AccountMonitor;
import com.fortune.trading.util.Constants;

/**
 * <code>Bootstrap</code> 启动前加载所有的资源 .
 * <p>
 * 1) 加载属性文件
 * 
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1
 */
@Component("bootstrap")
@Scope("singleton")
public class Bootstrap {
	protected Logger L = Logger.getLogger(getClass());
	
	private static final String TRADE_CONFIGPATH = "config/zheshang-trading.properties";
	
	@Autowired(required = true)
	private ServletContext context;
	
	@PostConstruct
	public void startup() {
		try {
			String sFilePath = context.getRealPath(TRADE_CONFIGPATH);
			L.info("Tebon trading configuration filePath : " + sFilePath);
			Properties oPropsConfig = new Properties();
			oPropsConfig.load(new FileInputStream(sFilePath));
			
			Constants.mode = Integer.parseInt(oPropsConfig.getProperty("mode", "1"));
			Constants.tradingHost = oPropsConfig.getProperty("tradingHost");
			Constants.tradingPort = oPropsConfig.getProperty("tradingPort");
			Constants.quoteURL = oPropsConfig.getProperty("quoteURL");
			Constants.quoteWSURL = oPropsConfig.getProperty("quoteWSURL");
			Constants.quoteURLForWsRequest = oPropsConfig.getProperty("quoteURLForWsRequest");
			Constants.limitedTimes = Integer.parseInt(oPropsConfig.getProperty("limitedTimes", "-1"));
			Constants.accLockTimes = Long.parseLong(oPropsConfig.getProperty("lockTime", "-1"));
			Constants.isSupportEncry = Boolean.parseBoolean((oPropsConfig.getProperty("isSupportEncryption", "true")));
			Constants.isSupportLogJDBC = Boolean.parseBoolean((oPropsConfig.getProperty("supportLogJDBC", "false")));
			Constants.isSupportLockAcc = Constants.limitedTimes > -1 ? true : false;
			Constants.isSupportSocket = Boolean.parseBoolean((oPropsConfig.getProperty("isSupportSocket", "false")));
			Constants.opStationPrefix = oPropsConfig.getProperty("opStationPrefix", "");
			
			if (Constants.isSupportLockAcc) {
				AccountMonitor.getInstance().startMonitor();
			}
			if (Constants.isSupportSocket) {
				GatewayClient oClient = GatewayClient.getInstance();
				oClient.bootstrap(oPropsConfig);
				oClient.asyncStart();
			}
			L.info("Trading host :" + Constants.tradingHost);
			L.info("Trading port :" + Constants.tradingPort);
			L.info("quoteURL :" + Constants.quoteURL);
			L.info("quoteWSURL :" + Constants.quoteWSURL);
			L.info("quoteURLForWsRequest :" + Constants.quoteURLForWsRequest);
			L.info("limitedTimes :" + Constants.limitedTimes);
			L.info("isSupportEncry :" + Constants.isSupportEncry);
			L.info("isSupportLogJDBC :" + Constants.isSupportLogJDBC);
			L.info("isSupportSocket:" + Constants.isSupportSocket);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
