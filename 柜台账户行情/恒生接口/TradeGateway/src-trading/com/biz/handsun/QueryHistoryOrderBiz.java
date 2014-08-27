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
 * <code>TodayOrderBiz<code> 历史委托查询
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 11,2014)
 *
 */
public class QueryHistoryOrderBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_HIS_ORDER;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{ "version", SysConstants.HS.version},			// 版本号
			{ "identity_type", "" },	// 账户身份类别
			{ "op_branch_no", SysConstants.HS.op_branch_no },	// 操作员分支代码
			{ "op_entrust_way", SysConstants.HS.op_entrust_way },	// 委托方式
			{ "op_station", null },		// 站点/电话
			{ "function_id", sFuncNo },	// 系统功能
			{ "branch_no", null },		// 分支代码
			{ "fund_account", null },	// 资金账号
			{ "password", null },		// 交易密码
			{ "exchange_type", "" },	// 交易类别, 如果不为空格，查确定的，为空，查所有
			{ "stock_account", "" },	// 证券账号
			{ "stock_code", "" },		// 证券代码，如果为空格，查所有，不为空，查确定的，此时exchange_type必须不为空	
			{ "start_date", null },		// 起始日期
			{ "end_date", null },		// 结束日期
			{ "query_type", "0" },		// 查询类别，是否查委托类型为撤单的委托，0，全部；1，不查委托类型为撤单的委托(默认)
			{ "query_direction",SysConstants.HS.query_direction}, // 查询方向
			{ "request_num", "1000" },	// 请求行数，不送按50行处理，超过系统指定值（1000行）按系统指定值（1000行）处理
			//{ "position_str", null },	// 定位串
		};
		
		asResponseField = new String[]{
			"entrust_no",				// 委托序号
		    "exchange_type",			// 交易类别
		    "exchange_name",			// 交易类别名称
		    "stock_account",			// 证券账号
		    "stock_code",				// 股票标识
		    "stock_name",				// 股票名称
		    "entrust_bs",				// 买卖方向
		    "bs_name",				 	// 买卖方向名称
		    "entrust_price",			// 委托价格
		    "entrust_amount",			// 委托数量
		    "business_amount",			// 成交数量
		    "business_price",			// 成交价格
		    "report_no",				// 申报号
		    "report_time",				// 申报时间
		    "curr_date", 				// 发生日期
		    "entrust_time", 			// 委托时间
		    "entrust_date", 			// 初始化日期
		    "entrust_type",				// 委托类别
		    "type_name",				// 委托类别名称
		    "entrust_status", 			// 委托状态
		    "status_name",				// 状态名称
		    "entrust_way", 				// 委托方式
		    "entrust_way_name", 		// 委托方式名称
		    "cancel_info",				// 废单原因
		    "position_str"				// 定位串
		};
		sFunID = sFuncNo;
	}
	
}
