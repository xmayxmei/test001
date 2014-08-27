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
 * <code>LoginAuthenBiz<code> 登录验证.
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 10,2014)
 *
 */
public class LoginAuthenBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_LOGINAUTHEN;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{"version", SysConstants.HS.version},
			{"identity_type", ""},
			{"op_branch_no", SysConstants.HS.op_branch_no},
			{"op_entrust_way", SysConstants.HS.op_entrust_way},
			{"op_station", null},
			{"function_id", sFuncNo},
			{"input_content", null},
			{"content_type", null},
			{"branch_no", SysConstants.HS.branch_no},
			{"password", null},
			{"account_content", null},
		};
		
		asResponseField = new String[]{
			"error_no",
			"error_info",
			"content_type",
			"account_content",
			"branch_no",
			"fund_account",
			"online_time",
			"client_id",
			"client_name",
			"fundaccount_count",
			"money_count",
			"money_type",
			"square_flag",
			"enable_balance",
			"current_balance",
			"client_rights",
			"bank_no",
			"exchange_type",
			"exchange_name",
			"login_date",
			"login_time",
			"last_op_ip",
		    "sys_status",
		    "sys_status_name"
		};
		sFunID = sFuncNo;
	}
	
}
