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
 * <code>QueryPortfolioBiz<code> 持仓查询.
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 10,2014)
 *
 */
public class QueryPortfolioBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_POSITIONS;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{"version", SysConstants.HS.version},
			{"identity_type", ""},
			{"op_branch_no", SysConstants.HS.op_branch_no},
			{"op_entrust_way", SysConstants.HS.op_entrust_way},
			{"op_station", null },
			{"function_id", sFuncNo},
			{"branch_no", null},
			{"fund_account", null},
			{"password", null},
			{"exchange_type", ""},
			{"stock_account", ""},
			{"stock_code", ""},
			{ "query_direction",SysConstants.HS.query_direction}, // 查询方向
			{"query_mode", "0"}, 
			{"request_num", "1000"}, 
			{"position_str", ""},
		
		};
		
		asResponseField = new String[]{
		    "exchange_type",//交易类型
		    "stock_account",//证券账号
		    "stock_code",//证券代码
		    "stock_name",//证券名称
		    "current_amount",//当前股票
		    "enable_amount",//可卖股票
		    "last_price",//最新价
		    "cost_price",//成本价
		    "income_balance",//买卖盈亏
		    "hand_flag",//股手标志，'0'--股   '1' -- 手。
		    "market_value",
		    "sum_buy_amount",
		    "sum_buy_balance",
		    "real_buy_amount",
		    "real_buy_balance",
		    "sum_sell_amount",
		    "sum_sell_balance",
		    "real_sell_amount",
		    "real_sell_balance",
		    "correct_amount",
		    "income_balance_nofare",
		    "uncome_buy_amount",
		    "uncome_sell_amount",
		    "begin_amount",
		    "stock_type",
		    "delist_flag",
		    "delist_date",
		    "par_value",
		    "position_str"// 定位符
		};
		sFunID = sFuncNo;
	}
	
}
