package com;


/**
 * 200功能号返回错误码信息
 * @author wangsy
 *
 */
public enum ErrorCodeType {
	
	error101(-41,"资金密码到期未修改"),
	error102(-42,"交易密码到期未修改"),
	error103(-51,"无此账户"),
	error104(-61,"账户登录失败"),
	error105(-63,"密码错误"),
	error106(-64,"您没有此类委托的权限"),
	error107(-66,"您的帐户状态不正常"),
	error108(-71,"客户登录受限"),
	//以上为T2服务200功能号返回错误信息
	
	error200(-200,"系统错误"),
	error300(-300,"账号或密码验证失败"),
	error301(-301,"验证码输入错误");
	
	private int errorNo;
	private String errorCode;
	
	private ErrorCodeType(int errorNo,String errorCode){
		this.errorNo=errorNo;
		this.errorCode=errorCode;
	}

	public static String getErrorCode(int errorNo) {
		ErrorCodeType[] types = ErrorCodeType.values();
		String errorCode ="";
		for(int i =0;i<types.length;i++){
			if(errorNo==types[i].errorNo){
				errorCode = types[i].errorCode;
			}
		}
		return errorCode;
	}


	
}
