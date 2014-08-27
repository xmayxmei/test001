package com;

import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.IClient;

public class ServiceInterface {
	/**
	 * T2服务
	 */
	protected static T2Services server = null;

	/**
	 * 客户接口服务
	 */
	protected static IClient client = null;


	/**
	 * 服务名
	 */
	protected static String clientServerName = "zszh_cs";
	
	/**
	 * 操作员分支代码
	 */
	protected static int op_branch_no = 0;

	/**
	 * 委托方式
	 */
	public  static String op_entrust_way = "7";
	
	public static FunctionNo functionNo = new FunctionNo();

	/**
	 * 启动服务
	 */
	public static synchronized void start(){

		if(null == server) {
			try {
				server = T2Services.getInstance();
				server.init();
				server.start();
				client = server.getClient(clientServerName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 初始化相关配置
	 */
	public static void initConfigs() {		
		//		wxBsAccountBindingDao = (WxBsAccountBindingDao) SpringBeanFactory.getBusinessOjbect("wxBsAccountBindingDao");
		//		commonService  =  (CommonService)SpringBeanFactory.getBusinessOjbect("commonService");		
		//     clientServerName = ConfigProperties.getConfig("clientName");
	}


	/**
	 * 关闭服务clientName
	 */
	public static synchronized void stop() {
		if(null != server) {
			server.stop();
		}
	}
	
	

}
