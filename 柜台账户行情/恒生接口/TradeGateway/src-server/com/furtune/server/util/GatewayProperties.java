/*package com.furtune.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


*//**
 * <code>GatewayProperties</code> manages gateway server properties and trading properties
 *
 * @author Jimmy
 * @since Trading - v.0.0.1(April 12, 2014)
 * 
 *//*
public class GatewayProperties {
	private Logger L = Logger.getLogger(getClass());
	*//** Gateway configuration file path **//*
	public static final String GATEWAY_CONF_FILEPATH = "config/Gateway.cfg";
	*//** **//*
	private static GatewayProperties m_propInstance;
	
	private Properties m_propGateway ;
	
	private Properties m_propTrading;
	
	private GatewayProperties() {
	}
	*//**
	 * 
	 * @return
	 *//*
	public static GatewayProperties getInstance() {
		if (m_propInstance == null) {
			m_propInstance = new GatewayProperties();
		}
		return m_propInstance;
	}
	public void init() {
		if (m_propGateway != null) {
			return ;
		}
		try {
			m_propGateway = loadProperties(GATEWAY_CONF_FILEPATH);
			String sTradingPath = m_propGateway.getProperty("trading.conf.file");
			if (U.STR.isEmpty(sTradingPath)) {
				L.info("Missed trading properties.");
			} else {
				m_propTrading = loadProperties(sTradingPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 * @param sGatewayPropPath
	 
	private Properties loadProperties(String sGatewayPropPath) throws IOException{
		File oFile = new File(sGatewayPropPath);
		if (!oFile.exists()){
			String sError = "Missing 'properties' file :" + sGatewayPropPath;
			L.error(sError);
		}
		Properties oProps = new Properties();
		FileInputStream fins = new FileInputStream(oFile);
		oProps.load(fins);
		return oProps;
	}
	*//**
	 * @param sKey
	 * @return
	 *//*
	public String getGatewayProp(String sKey) {
		return m_propGateway.getProperty(sKey);
	}
	*//**
	 * @param sKey
	 * @param sDef
	 * @return
	 *//*
	public String getGatewayProp(String sKey, String sDef) {
		return m_propGateway.getProperty(sKey, sDef);
	}
	*//**
	 * @param sKey
	 * @param sVal
	 *//*
	public void setGatewayProp(String sKey, String sVal) {
		m_propGateway.setProperty(sKey, sVal);
	}
	*//**
	 * @param sKey
	 * @return
	 *//*
	public String getTradingProp(String sKey) {
		return m_propTrading.getProperty(sKey);
	}
	*//**
	 * @param sKey
	 * @param sDef
	 * @return
	 *//*
	public String getTradingProp(String sKey, String sDef) {
		return m_propTrading.getProperty(sKey, sDef);
	}
	*//**
	 * @param sKey
	 * @param sVal
	 *//*
	public void setTradingProp(String sKey, String sVal) {
		m_propTrading.setProperty(sKey, sVal);
	}
	*//**
	 * @throws IOException 
	 * 
	 *//*
	public void save() throws IOException {
		File oFlie = new File(GATEWAY_CONF_FILEPATH);
		FileOutputStream out = new FileOutputStream(oFlie);
		m_propGateway.store(out, "");
		
		oFlie = new File(m_propGateway.getProperty("trading.conf.file"));
		out = new FileOutputStream(oFlie);
		m_propTrading.store(out, "");
	}
}
*/