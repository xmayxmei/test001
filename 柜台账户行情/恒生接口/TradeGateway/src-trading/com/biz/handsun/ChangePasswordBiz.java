/*
 * FileName: CheckPswBiz.java
 * Copyright: Copyright 2014-6-10 Cfwx Tech. Co. Ltd.All right reserved.
 * Description: [这里用一句话描述]
 *
 */
package com.biz.handsun;

import com.core.handsun.HSDataHandler;
import com.util.FUNConstants;
import com.util.SysConstants;
/**
 * <code>ChangePasswordBiz<code> 修改密码
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 10,2014)
 *
 */
public class ChangePasswordBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_CHANGE_PSW;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{ "version", SysConstants.HS.version },// 版本号
			{ "identity_type", "" },// 账户身份类别
			{ "op_branch_no", SysConstants.HS.op_branch_no },// 操作员分支代码
			{ "op_entrust_way", SysConstants.HS.op_entrust_way },// 委托方式
			{ "op_station", null },// 站点/电话
			{ "branch_no", null },
			{ "function_id", sFuncNo },
			{ "fund_account", null },
			{ "password", null },
			{ "password_type", null }, //密码类别 '1’：资金密码‘2’：交易密码 一次只能修改一个密码
			{ "new_password", null },
		};
		
		asResponseField = new String[]{
		    "error_no",
		    "error_info"
		};
		sFunID = sFuncNo;
	}
	
}
