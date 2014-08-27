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
 * <code>QueryPeiHaoBiz<code> 银证转账流水查询
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 11,2014)
 *
 */
public class QueryBankZQDtBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_BANKZQDETAIL;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{ "version",  SysConstants.HS.version },			// 版本号
			{ "identity_type", "" },	// 账户身份类别
			{ "op_branch_no",  SysConstants.HS.op_branch_no },	// 操作员分支代码
			{ "op_entrust_way",  SysConstants.HS.op_entrust_way },	// 委托方式
			{ "op_station",  null },		// 站点/电话
			{ "function_id", sFuncNo },	// 系统功能
			{ "branch_no", null },		// 分支代码
			{ "fund_account", null },	// 资金账号
			{ "password", null },		// 交易密码
//			{ "bank_password", null },	// 银行密码（暂时不用）
//			{ "start_date", null },		// 起始日期（暂时不用）
//			{ "end_date", null },		// 结束日期（暂时不用）
//			{ "bank_no", null },		// 银行代码（暂时不用）
//			{ "entrust_no", null },		// 委托号（暂时不用）
//			{ "query_direction", null },// 查询方向（暂时不用）
			{ "request_num", "1000"},		// 请求行数，不送按50行处理，超过系统指定值（1000行）按系统指定值（1000行）处理
//			{ "position_str", null},	// 定位串
		};
		
		asResponseField = new String[]{
			"branch_no",				// 营业部号	
		    "fund_accoun",				// 资金帐号
		    "bank_no",					// 银行号码
		    "bank_name",				// 银行名称
		    "entrust_no",				// 合同号	
		    "business_type",			// 成交类别
		    "trans_name",				// 成交类别名称
		    "source_flag",				// 发起方	
		    "money_type",				// 币种类别	
		    "money_name",				// 币种名称	
		    "occur_balance",			// 资金发生额	
		    "entrust_time",				// 委托时间
		    "entrust_status",			// 委托状态
		    "entrust_name",				// 委托状态名称	
		    "error_no",					// 错误号	
		    "cancel_info",				// 废单原因(不论银行发起，证券发起，错误原因均放在cancel_info里，以前是证券发起，银行错误原因在bank_error_info中，银行发起，证券错误原因写在cancel_info里，现在输出时，不论发起方，错误信息将记录在cancel_info里。Banktransfer里的处理不变。)		
		    "bank_error_info",			// 银行错误详细信息
		    "position_str",				// 定位串
		};
		sFunID = sFuncNo;
	}
	
}
