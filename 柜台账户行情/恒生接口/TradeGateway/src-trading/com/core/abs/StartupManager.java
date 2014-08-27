package com.core.abs;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.xvolks.jnative.exceptions.NativeException;

import com.util.SysConstants;

/**
 * <code>StartupManager</code> 
 * <p>
 * The following flows are the startup steps that we must follow their orders :<br>
 * 1) initJNative <br>
 * 2) initDllDepen <br>
 * 3) initDll <br>
 * 4) initGlobalProperties <br>
 * 5) initConnPool
 * 
 * @author Colin
 * @since Trading v0.0.1
 */
public class StartupManager {
	protected final Logger L = Logger.getLogger(getClass());
	
	private static StartupManager instance;
	
	private StartupManager () {
	}
	public synchronized static StartupManager getInstance() {
		if (instance == null) {
			instance = new StartupManager();
		}
		return instance;
	}
	/**
	 * @param sNativeRealPath
	 */
	public void initJNative(String sNativeRealPath) {
		System.setProperty("jnative.loadNative", "manual");
		System.load(sNativeRealPath);
	}
	/**
	 * Initialized the api dll dependent file.
	 * 
	 * @param sDllDepenPath
	 */
	public void initDllDepen(String sDllDepenPath) {
		System.load(sDllDepenPath);
	}
	/**
	 * @param sDllPath
	 * @return
	 * @throws IllegalAccessException 
	 * @throws NativeException 
	 */
	public int initDll(String sDllPath) throws NativeException, IllegalAccessException {
		L.info("init Dll path :"+sDllPath);
		System.load(sDllPath);
		L.info("Fix_Initialize.");
		int i = ABOSS2Inf.initialize();
		L.info("Fix_Initialize Finish.");
		return i;
	}
	/**
	 * @param oProp
	 */
	public void initGlobalProperties(Properties oProp) {
		SysConstants.ABS.ipAddr = oProp.getProperty("IpAddr");
		L.info("IP_Addr :" + SysConstants.ABS.ipAddr);
		SysConstants.ABS.FBDM = oProp.getProperty("FBDM");
		L.info("FBDM :" + SysConstants.ABS.FBDM);
		SysConstants.ABS.DestFBDM = oProp.getProperty("DestFBDM");
		L.info("DestFBDM :" + SysConstants.ABS.DestFBDM);
		SysConstants.ABS.Node = oProp.getProperty("Node");
		L.info("Node :" + SysConstants.ABS.Node);
		SysConstants.ABS.GYDM = oProp.getProperty("GYDM");
		L.info("GYDM :" + SysConstants.ABS.GYDM);
		SysConstants.ABS.WTFS = oProp.getProperty("WTFS");
		L.info("WTFS :" + SysConstants.ABS.WTFS);
		SysConstants.ABS.ConnTimeOut = Integer.parseInt( oProp.getProperty("ConnTimeout", "60"));
		L.info("Connection timeout :" + SysConstants.ABS.ConnTimeOut);
		SysConstants.ABS.maxPoolSize = Integer.parseInt( oProp.getProperty("MaxConnPoolSize", "10"));
		L.info("Max connection pool size :" + SysConstants.ABS.maxPoolSize);
		SysConstants.ABS.initPoolSize = Integer.parseInt( oProp.getProperty("InitConnPoolSize", "5"));
		L.info("Initial connection pool size :" + SysConstants.ABS.initPoolSize);
		SysConstants.ABS.SessionTimeOut = Integer.parseInt(oProp.getProperty("SessionTimeout", "1000"));
		L.info("Session timeout :" + SysConstants.ABS.SessionTimeOut);
		SysConstants.ABS.mode = Integer.parseInt(oProp.getProperty("Mode", "1"));;
		L.info("Mode :" + SysConstants.ABS.mode);
		SysConstants.ABS.encoding = oProp.getProperty("Encoding", "GBK");
	}
	/**
	 * Initialized Connection Pool.
	 * @param oProps
	 * @throws ServletException
	 */
	public void initConnPool() {
		ConnManager.getInstance().initPool();
	}
}
