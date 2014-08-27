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
 * <code>QueryPeiHaoBiz<code> 银证转账
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 11,2014)
 *
 */
public class BankZQTransferBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_BANKZQ_TRANSFER;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{ "version", SysConstants.HS.version },			// 版本号
			{ "identity_type", "" },	// 账户身份类别
			{ "op_branch_no", SysConstants.HS.op_branch_no },	// 操作员分支代码
			{ "op_entrust_way",SysConstants.HS.op_entrust_way },	// 委托方式
			{ "op_station", null },		// 站点/电话
			{ "function_id", sFuncNo },	// 系统功能
			{ "branch_no", null },		// 分支代码
			{ "fund_account", null },	// 资金账号
			{ "password", null },		// 交易密码
			{ "fund_password", null },	// 资金密码
			{ "bank_password", null },	// 银行密码
			{ "money_type", null },		// 币种类别
			{ "bank_no", null },		// 银行代码
			{ "transfer_direction", null },	// 交易方向 '1' – 银行转证券  '2' -- 证券转银行	
			{ "occur_balance", null },	// 发生金额
		};
		
		asResponseField = new String[]{
			"error_no",					// 错误号	
		    "error_info",				// 错误原因
		    "entrust_no",				// 委托编号
		};
		sFunID = sFuncNo;
	}
	
}
