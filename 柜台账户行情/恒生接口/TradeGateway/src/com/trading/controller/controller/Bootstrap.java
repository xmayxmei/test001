package com.trading.controller.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xvolks.jnative.exceptions.NativeException;

import com.core.abs.StartupManager;
import com.core.handsun.HSStartup;
import com.furtune.server.main.GatewayServer;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.util.SysConstants;

/**
 * <code>Bootstrap</code> 启动前加载所有的资源 .
 * <p>
 * 包括:<br>
 * 1) 启动配置好的接口<br>
 * 2) 启动Socket监听
 * 
 * @author Colin, Jimmy
 * @since Trading v0.0.1
 */
@Component("bootstrap")
@Scope("singleton")
public class Bootstrap {	
	protected Logger L = Logger.getLogger(getClass());
	
	private static final String SYSTEM_CONFIGPATH = "config/system_config.properties";
	
	@Autowired(required = true)
	private ServletContext context;
	
	@PostConstruct
	public void startup() {
		try {
			String sFilePath = context.getRealPath(SYSTEM_CONFIGPATH);
			L.info("Trade configuration filePath : " + sFilePath);
			Properties oPropsConfig = new Properties();
			oPropsConfig.load(new FileInputStream(sFilePath));
			
			String sBrokerId = oPropsConfig.getProperty("brokerId", "abs");
			String sBrokerConfigPath = context.getRealPath(oPropsConfig.getProperty("brokerConfigPath"));
			if ("abs".equals(sBrokerId)) {
				startAbs(sBrokerConfigPath);
			} else if ("handsun".equals(sBrokerId)) {
				startHandsun(sBrokerConfigPath);
			} else {
				throw new Exception("brokerId错误.");
			}
			SysConstants.brokerId = sBrokerId;
			
			boolean isSupportSocket = Boolean.parseBoolean(oPropsConfig.getProperty("supportSocketComm", "true"));
			//建立Socket监听
			if (isSupportSocket) {
				GatewayServer oGateway = new GatewayServer();
				oGateway.bootstrap(oPropsConfig);
				oGateway.ayncStart();
			}
		} catch (IOException e) {
			L.error("Failure to load system configuration file.");
		}catch (Exception e) {
			e.printStackTrace();
			L.error(e.getMessage(), e.getCause());
		}
	}
	/**
	 * 1) 初始化JNative动态库<br>
	 * 2) 初始化Fix Dll 文件<br>
	 * 3) 设置全局属性<br>
	 * 4) 建立连接到定点柜台的连接池
	 * 
	 * @param sAbsConfigPath
	 * @throws IOException
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	private void startAbs(String sAbsConfigPath){
		Properties oPropsConfig = new Properties();
		try {
			oPropsConfig.load(new FileInputStream(sAbsConfigPath));
			
			StartupManager startup = StartupManager.getInstance();
			// JNative 动态库
			String sNativeRealPath = "";
			String sOSName = System.getProperty("os.name").toLowerCase();
			if (sOSName.indexOf("windows") != -1) {
				System.err.println(oPropsConfig.getProperty("NativedllWindows"));
				sNativeRealPath = context.getRealPath(oPropsConfig.getProperty("NativedllWindows"));
			} else if (sOSName.indexOf("linux") != -1) {
				sNativeRealPath = context.getRealPath(oPropsConfig.getProperty("NativedllLinux"));
			}
			L.info("JNative File Path: " + sNativeRealPath);
			startup.initJNative(sNativeRealPath);
			// API DLL动态库的依赖库
			String sPath = oPropsConfig.getProperty("DllFileDepenPath");
			String[] asPath = sPath.split(",");
			for (String str : asPath) {
				String sDllDepenPath = context.getRealPath(str);
				startup.initDllDepen(sDllDepenPath);
			}
			// API Dll动态库
			String sDllPath = context.getRealPath(oPropsConfig.getProperty("DllFilePath"));
			L.info("Dll File Path:" + sDllPath);
			startup.initDll(sDllPath);
			//设置全局属性
			startup.initGlobalProperties(oPropsConfig);
			//建立与定点柜台的连接池
			startup.initConnPool();
		} catch (FileNotFoundException e) {
			L.error(e.getCause());
		} catch (IOException e) {
			L.error(e.getCause());
		} catch (NativeException e) {
			L.error(e.getCause());
		} catch (IllegalAccessException e) {
			L.error(e.getCause());
		}
	}
	/**
	 * 1) 启动恒生接口服务.
	 * 
	 * @param sAbsConfigPath
	 * @throws T2SDKException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private void startHandsun(String configPath) {
		Properties oPropsConfig = new Properties();
		try {
			oPropsConfig.load(new FileInputStream(configPath));
			String sT2sdkConfig = context.getRealPath(oPropsConfig.getProperty("t2sdkConfigPath"));
			String sErrMapping =  context.getRealPath(oPropsConfig.getProperty("errorCodeMappingFile"));
			
			HSStartup oStartup = HSStartup.getInstance();
			oStartup.initGlobalProperties(oPropsConfig);
			oStartup.initErrorCodeMapping(sErrMapping);
			oStartup.initT2Service(sT2sdkConfig);
		} catch (FileNotFoundException e) {
			L.error(e.getCause());
		} catch (IOException e) {
			L.error(e.getCause());
		} catch (T2SDKException e) {
			L.error(e.getMessage(), e.getCause());
		}
	}
}
