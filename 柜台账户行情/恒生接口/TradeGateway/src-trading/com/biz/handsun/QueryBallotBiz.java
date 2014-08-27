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
 * <code>QueryPeiHaoBiz<code> 中签查询
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 11,2014)
 *
 */
public class QueryBallotBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_BALLOT;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{ "version", SysConstants.HS.version},			// 版本号
			{ "identity_type", "" },	// 账户身份类别
			{ "op_branch_no", SysConstants.HS.op_branch_no},	// 操作员分支代码
			{ "op_entrust_way", SysConstants.HS.op_entrust_way},	// 委托方式
			{ "op_station", null},		// 站点/电话
			{ "function_id", sFuncNo},	// 系统功能
			{ "branch_no", null},		// 分支代码
			{ "fund_account", null },	// 资金账号
			{ "password", null },		// 交易密码
			{ "start_date", null },		// 起始日期
			{ "end_date", null },		// 结束日期
			{ "exchange_type", "" },	// 交易类别, 如果不为空格，查确定的，为空，查所有
			{ "stock_account", ""},	// 证券账号, 可为空, 查所有。
			{ "stock_code", ""},		// 证券代码，如果为空格，查所有，不为空，查确定的	
			//{ "query_direction", "0" }, // 查询方向
			//{ "request_num", null},		// 请求行数，不送按50行处理，超过系统指定值（1000行）按系统指定值（1000行）处理
			//{ "position_str", null},	// 定位串
		};
		
		asResponseField = new String[]{
			"position_str",				// 定位串	
		    "date",						// 发生日期	
		    "exchange_type",			//市场类别
		    "business_price", 			// 成交价格
		    "stock_code", 				// 股票标识
		    "occur_amount", 			// 发生数量
		};
		sFunID = sFuncNo;
	}
	
}
