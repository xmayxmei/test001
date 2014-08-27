package com.biz.handsun;

import com.core.handsun.HSDataHandler;
import com.util.FUNConstants;
import com.util.SysConstants;

/**
 * <code>QueryBankDMBiz<code> 查询客户银行信息
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 11,2014)
 *
 */
public class QueryBankAccBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_BANkACCOUNT;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{ "version", SysConstants.HS.version },			// 版本号
			{ "identity_type", "" },	// 账户身份类别
			{ "op_branch_no", SysConstants.HS.op_branch_no },	// 操作员分支代码
			{ "op_entrust_way",SysConstants.HS.op_entrust_way },	// 委托方式
			{ "op_station", null },		// 站点/电话
			{ "function_id", sFuncNo },	// 系统功能
			{ "branch_no", null},		// 分支代码
			{ "fund_account", null },	// 资金账号
			{ "password", null },		// 交易密码
			{ "money_type", "" },		// 币种类别
			{ "bank_no", ""},		// 银行代码
			
		};
		
		asResponseField = new String[]{
			"money_type",	// 币种
		    "bank_no",		// 银行代码
		    "bank_name",	// 银行名称
		    "bank_account",	// 银行帐号
		    "bkaccount_status",	// 银行帐号状态
		    "bkaccount_rights",	// 银行帐号权限
		    "bkaccount_regflag",// 银行帐号存管指定标记
		};
		sFunID = sFuncNo;
	}
}
