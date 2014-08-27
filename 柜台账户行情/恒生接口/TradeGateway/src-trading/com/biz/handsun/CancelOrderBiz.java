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
 * <code>TodayOrderBiz<code> 撤单
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 11,2014)
 *
 */
public class CancelOrderBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_CANCELORDER;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{ "version", SysConstants.HS.version},			// 版本号
			{ "identity_type", "" },	// 账户身份类别
			{ "op_branch_no", SysConstants.HS.op_branch_no },	// 操作员分支代码
			{ "op_entrust_way",SysConstants.HS.op_entrust_way },	// 委托方式
			{ "op_station", null },		// 站点/电话
			{ "function_id", sFuncNo },	// 系统功能
			{ "branch_no", null },		// 分支代码
			{ "fund_account", null },	// 资金账号
			{ "password", null },		// 交易密码
			{ "batch_flag", "0" },		// 批量标志 '0'--单笔 '1'--批量
			{ "exchange_type", ""},	// 交易类别,如果账号指定市场全撤的时候才必须输入。
			{ "entrust_no", null },		// 委托编号,使用批量撤单标志batch_flag为‘1‘，则将理解为批号，其它为委托编号，如果为0，则如果批量标志为‘1’，那么批量撤单账号整个市场的。
		};
		
		asResponseField = new String[]{
			"error_no",					// 错误号	
		    "error_info",				// 错误原因
		    "entrust_no",				// 委托编号
		};
		sFunID = sFuncNo;
	}
	
}
