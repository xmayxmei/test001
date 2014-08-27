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
 * <code>QueryCancelOrderBiz<code> 可撤单查询
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 11,2014)
 *
 */
public class QueryCancelOrderBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_QUERY_CANCELORDER;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{ "version",  SysConstants.HS.version },			// 版本号
			{ "identity_type", "" },	// 账户身份类别
			{ "op_branch_no", SysConstants.HS.op_branch_no },	// 操作员分支代码
			{ "op_entrust_way",  SysConstants.HS.op_entrust_way },	// 委托方式
			{ "op_station",  null },		// 站点/电话
			{ "function_id", sFuncNo },	// 系统功能
			{ "branch_no",  null },		// 分支代码
			{ "fund_account", null },	// 资金账号
			{ "password", null },		// 交易密码
			{ "stock_account", ""},	// 证券账号, 为空，取fund_account下的所有委托；不为空，取fund_account+stock_account的所有委托（暂不使用）
			{ "entrust_no", ""},		// 委托号，为0，取所有委托，不为0，取该笔委托，如果该笔非fund_account和stock_account的委托，该笔将取不到
			{ "request_num", "1000" },	// 请求行数，不送按50行处理，超过系统指定值（1000行）按系统指定值（1000行）处理
			{ "position_str", "" },	// 定位串
		};
		
		asResponseField = new String[]{
		    "exchange_type",			// 交易类别
		    "stock_account",			// 证券账号
		    "stock_code",				// 股票标识
		    "stock_name",				// 股票名称
		    "entrust_no",				// 委托序号
		    "entrust_bs",				// 买卖方向
		    "entrust_price",			// 委托价格
		    "entrust_amount",			// 委托数量
		    "business_amount",			// 成交数量
		    "entrust_status", 			// 委托状态	    
		};
		sFunID = sFuncNo;
	}
	
}
