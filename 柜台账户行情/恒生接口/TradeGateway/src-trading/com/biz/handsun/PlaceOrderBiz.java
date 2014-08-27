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
 * <code>PlaceOrderBiz<code> 下单
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 11,2014)
 *
 */
public class PlaceOrderBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_PLACEORDER;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
			{ "version",SysConstants.HS.version},			// 版本号
			{ "identity_type", "" },	// 账户身份类别
			{ "op_branch_no", SysConstants.HS.op_branch_no },	// 操作员分支代码
			{ "op_entrust_way", SysConstants.HS.op_entrust_way },	// 委托方式
			{ "op_station", null },		// 站点/电话
			{ "function_id", sFuncNo },	// 系统功能
			{ "branch_no", null },		// 分支代码
			{ "fund_account", null },	// 资金账号
			{ "password", null },		// 交易密码
			{ "exchange_type", null},	// 交易类别, 必须输入确定的市场，不支持‘0‘或空格。
			{ "stock_account",""  },	// 证券账号
			{ "stock_code", null },		// 证券代码，必须输入确定的股票代码，不支持内码
			{ "entrust_amount", null },	// 委托数量
			{ "entrust_price", null },	// 委托价格
			{ "entrust_prop", null},	// 委托属性,默认送'0'-买卖
			{ "entrust_bs", null }, 	// 买卖方向 1 买入 2 卖出	
			{ "entrust_type", null },	// 委托类别
		};
		
		asResponseField = new String[]{
			"error_no",					// 错误号	
		    "error_info",				// 错误原因
		    "entrust_no",				// 委托编号
		};
		sFunID = sFuncNo;
	}
	
}
