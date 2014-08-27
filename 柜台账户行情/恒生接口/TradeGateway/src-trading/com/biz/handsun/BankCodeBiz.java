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
 * <code>BankCodeBiz<code> 银行代码查询
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 11,2014)
 *
 */
public class BankCodeBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_BANK_CODE;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{ "version",SysConstants.HS.version},			// 版本号
			{ "identity_type", "" },	// 账户身份类别
			{ "op_branch_no", SysConstants.HS.op_branch_no },	// 操作员分支代码
			{ "op_entrust_way", SysConstants.HS.op_entrust_way},	// 委托方式
			{ "op_station", null },		// 站点/电话
			{ "function_id", sFuncNo },	// 系统功能
			{ "branch_no", null },		// 分支代码
			{ "bank_no", null },		// 银行代码，’’表示全部
		};
		
		asResponseField = new String[]{
			"branch_no",				// 分支代码
		    "bank_no",					// 银行代码，应返回一位的银行号
		    "bank_name",				// 银行名称
		    "check_password_bk_str",	// 银行核对密码标志
		};
		sFunID = sFuncNo;
	}
	
}
