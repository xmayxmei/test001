package com.biz.abs;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;

import com.util.SysConstants;
/**
 * 系统初始化参数配置
 */
public class SystemInitBiz {
	public static int initDll(String dllPath) {
		JNative n;
		try {
			System.load("D:\\FixApi.dll");
			 n = new JNative("FixApi", "Fix_Initialize");
			n.setRetVal(Type.INT);
			n.invoke();
			return n.getRetValAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public int createConnect(String clientId,String password){
		JNative n = null;
		try
		{
			n = new JNative("FixApi", "Fix_Connect");
			n.setParameter(0, Type.STRING,SysConstants.ABS.ipAddr);
			n.setParameter(1, Type.STRING, clientId);
			n.setParameter(2, Type.STRING,password);
			n.setParameter(3, SysConstants.ABS.ConnTimeOut);
			n.setRetVal(Type.INT);
			n.invoke();
			return n.getRetValAsInt();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 卸载dll
	 * @return
	 */
	public static int dispostDll() {
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_Uninitialize");
			n.setRetVal(Type.INT);
			n.invoke();
			return n.getRetValAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
