package com.biz.abs;
/*package com.biz;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;

import com.core.BizInterface;
import com.core.DataHandler;
import com.util.SysConstants;

*//**
 * 用户登录
 * @author wenwenbin
 *
 *//*
public class UserLoginBiz extends DataHandler implements BizInterface{

	public String requestData() {
		int result=this.createConnect(this.getClientId(),this.getPassWord());
		String resultString="";
		if(result>0){
			if(ConnManager.connMap.containsKey(this.getClientId()))
				ConnManager.connMap.remove(this.getClientId());
			ConnManager.connMap.put(this.getClientId(), result);
			resultString="R00|"+String.valueOf(result);
		}else{
			resultString="R01|-100";
		}
		return resultString;
	}

	public void setRequestParams(Map<String, String> request) {
	}
	
	public int createConnect(String clientId,String password){
		JNative n = null;
		try
		{
			n = new JNative("FixApi", "Fix_Connect");
			n.setParameter(0, Type.STRING,SysConstants.IP_Addr);
			n.setParameter(1, Type.STRING, clientId);
			n.setParameter(2, Type.STRING,password);
			n.setParameter(3, SysConstants.ConnTimeOut);
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
	@Override
	protected void buildField() {
		// TODO Auto-generated method stub
		
	}
}
*/