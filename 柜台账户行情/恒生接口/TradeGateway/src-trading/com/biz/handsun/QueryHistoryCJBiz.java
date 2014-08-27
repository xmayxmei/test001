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
 * <code>HistoryTransactionBiz<code> 历史成交
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 10,2014)
 *
 */
public class QueryHistoryCJBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_HIS_TRANSACTION;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{"version", SysConstants.HS.version},			// 版本号
			{"identity_type", ""},		// 账户身份类别
			{"op_branch_no", SysConstants.HS.op_branch_no},		// 操作员分支代码
			{"op_entrust_way", SysConstants.HS.op_entrust_way},	// 委托方式
			{"op_station", null },		// 站点/电话
			{"function_id", sFuncNo},	// 系统功能
			{"branch_no", null },		// 分支代码
			{"fund_account", null},		// 资金账号
			{"password", null},			// 交易密码
			{"start_date", null},	    // 起始时间
			{"end_date", null},			// 结束时间
			{"exchange_type", ""},	// 交易类别
			{"stock_code", ""},		// 证券代码
			{ "query_direction",SysConstants.HS.query_direction}, // 查询方向
			{"request_num", "1000"},
			//{"position_str", null},		// 定位串
		};
		
		asResponseField = new String[]{
		    //"position_str",				// 定位串
		    "serial_no",				// 流水号
		    "date",						// 发生日期
		    "exchange_type",			// 交易类别
		    "exchange_name",			// 交易名称
		    "stock_code",				// 股票标识
		    "stock_name",				// 股票名称
		    "entrust_bs",				// 买卖方向
		    "bs_name",				 	// 买卖方向名称
		    "business_price",			// 成交价格
		    "business_time",			// 成交时间
		    "business_status",			// 成交状态
		    "status_name",				// 成交状态名称
		    "business_times",			// 成交笔数
		    "entrust_no",				// 合同号
		    "report_no",				// 申报号
		    "occur_amount",				// 成交数量
		    "business_balance",			// 成交金额
		    "occur_balance", 			// 清算金额
		    "post_balance", 			// 后资金额
		    "post_amount", 				// 股票余额
		    "fare0", 					// 佣金
		    "fare1", 					// 印花税
		    "fare2", 					// 过户费
		    "fare3", 					// 费用3
		    "farex", 					// 费用x
		    "clear_fare0", 				// 净佣金	
		    "profit", 					// 利息
		    
		};
		sFunID = sFuncNo;
	}
	
}
