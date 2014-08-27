package com.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.util.JdbcUtil;

/**
 * 读库获取历史净值
 * @author wangsy
 */
public class TimerGetHistoryWorthService {
	public static Logger logger = Logger.getLogger(TimerGetHistoryWorthService.class);
//	public static String dburl="jdbc:oracle:thin:@10.51.20.142/TAFAPDB";
//	public static String driverClassName="oracle.jdbc.driver.OracleDriver";
//	public static String username="hsta_view_jz";
//	public static String password="hsta_view_jz";
	public static String dburl="jdbc:oracle:thin:@localhost/ORCL";
	public static String driverClassName="oracle.jdbc.driver.OracleDriver";
	public static String username="wangsy";
	public static String password="123456";
	
	


	public void getData(){
		Connection conn = getConnection(driverClassName,dburl,username,password);
		String sql = "select  *  from VTA_ZSZQ_JHJZ";
		if(conn!=null){
		   List<Map<String, Object>> list = JdbcUtil.excuteNativeQueryWithName(sql, conn);
		   for(Map<String, Object> map:list){
			     for(Map.Entry<String, Object> entry : map.entrySet()){
			         System.out.println("key "+entry.getKey()+" value "+entry.getValue());
			  }			   
		   }
		}else{
			System.out.println("数据库连接异常");
		}
	}

	/**
	 * 获取数据库的连接
	 * @return
	 */
	private Connection getConnection(String className,String url,String name,String password){ 
		Connection conn = null;
		try {   
			Class.forName(className); 
			conn= DriverManager.getConnection(url,name,password); 
		} catch (Exception e) { 
			logger.info("数据库连接获取错误"+e.getMessage(),e);       
		}        
		return conn;    
	} 

	/** 
	 * 释放资源 
	 * @param rs 
	 * @param stmt 
	 * @param conn 
	 */  
	public void close(ResultSet rs,  Connection conn) {  
		try{  
			if (rs != null) {  
				rs.close();  
			}   
			if (conn != null) {  
				conn.close();  
			}  
		}catch(Exception e){  
			logger.info("释放资源错误"+e.getMessage(),e);  
		}  
	}  
	
	
	public static void main(String args[]){
		new TimerGetHistoryWorthService().getData();
		
		
	}

}
