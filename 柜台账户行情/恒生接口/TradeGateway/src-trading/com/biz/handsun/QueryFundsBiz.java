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
 * <code>QueryFundsBiz<code> 资金查询.
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 10,2014)
 *
 */
public class QueryFundsBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_QUERY_FUNDS;
	

	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{"version",SysConstants.HS.version},
			{"identity_type", ""},
			{"op_branch_no", SysConstants.HS.op_branch_no},
			{"op_entrust_way", SysConstants.HS.op_entrust_way},
			{"op_station", null},
			{"function_id", sFuncNo},
			{"branch_no", null },
			{"fund_account", null},
			{"password", null},
			//{"query_direction", SysConstants.HS.query_direction }, // 查询方向
			{"money_type", ""},
		};
		
		asResponseField = new String[]{
			"money_type",//币种类别
		    "current_balance",//当前余额
		    "enable_balance",//可用余额
		    "fetch_balance",//可取余额
		    "interest",//待入帐利息
		    "asset_balance",//资产总值（不含基金市值）
		    "fetch_cash",//可取现金
		    "fund_balance",//资金（= 资产总值 - 证券市值）
		    "market_value",//证券市值
		    "opfund_market_value",//基金市值
		    "pre_interest",//预计利息
		};
		sFunID = sFuncNo;
	}
	
}
