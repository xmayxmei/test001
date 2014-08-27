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
 * <code>TodayTransactionBiz<code> 当日成交.
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 10,2014)
 *
 */
public class QueryTodayCJBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_TODAY_TRANSACTION;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{"version", SysConstants.HS.version},
			{"identity_type", ""},
			{"op_branch_no", SysConstants.HS.op_branch_no},
			{"op_entrust_way", SysConstants.HS.op_entrust_way},
			{"op_station", null},
			{"function_id", sFuncNo},
			{"branch_no", null},
			{"fund_account", null},
			{"password", null},
			{"exchange_type", ""},
			{"stock_code", ""},
			{ "query_direction",SysConstants.HS.query_direction}, // 查询方向
			{"request_num", "1000"},
			//{"query_type", null},
			//{"position_str", "0"},
			{"etf_flag", "0"},
		};
		
		asResponseField = new String[]{
		    //"position_str",
		    "serial_no",
		    "date",
		    "exchange_type",
		    "exchange_name",
		    "stock_code",
		    "stock_name",
		    "entrust_bs",
		    "bs_name",
		    "business_price",
		    "business_amount",
		    "business_time",
		    "business_status",
		    "status_name",
		    "business_type",
		    "type_name",
		    "business_times",
		    "entrust_no",
		    "report_no",
		    "business_balance",
		    "business_no",
		};
		sFunID = sFuncNo;
	}
	
}
