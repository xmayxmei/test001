/*
 * FileName: QueryGDHBiz.java
 * Copyright: Copyright 2014-6-17 Cfwx Tech. Co. Ltd.All right reserved.
 * Description: 
 *
 */
package com.biz.handsun;

import com.core.handsun.HSDataHandler;
import com.util.FUNConstants;
import com.util.SysConstants;

/**
 * 
 * <code>QueryGDHBiz<code> 获取所有股东号.
 *
 * @author WWB，HJM
 * @since Handsun v0.0.1(2014-6-17)
 *
 */
public class QueryGDHBiz extends HSDataHandler{
	private static final String sFuncNo = FUNConstants.FUN_QUERY_GDH;
	
	@Override
	protected void buildField() {
		asRequestField = new String[][]{
				{ "version", SysConstants.HS.version},
				{ "identity_type", ""},
				{ "op_branch_no", SysConstants.HS.op_branch_no},
				{ "op_entrust_way", SysConstants.HS.op_entrust_way},
				{ "op_station", null},
				{ "function_id", sFuncNo},
				{ "branch_no", null},
				{ "fund_account", null},		//资金帐号
				{ "password", null},			//密码
				{ "exchange_type", "" },	//交易类别，可为空，查所有
				{ "stock_code", "" },	 	// 证券代码，可为空，若输入则输出所有持有该证券的股东
				{ "query_direction",SysConstants.HS.query_direction}, // 查询方向
				//{ "request_num", null },	// 请求行数，不送按50行处理，超过系统指定值（1000行）按系统指定值（1000行）处理
				//{ "position_str", null },	// 定位串
			};
		
			asResponseField = new String[]{
			   // "position_str",		// 定位串
			    "exchange_type",	//交易类别
			    "exchange_name",	//交易名称
			    "stock_account",	//股东账号
			    "holder_status",	//股东状态,‘0’为正常，其他为不正常
			    "holder_rights",	//股东权限 '0': 自动配股，'1':  自动配售 '2': 红利领取 'P': 代理配售申购 'D': 代理缴款 'G': 代理转配 'H': 代理转让 'I': 代理转转 'K': 代理申购 'n': ETF申购 'r': 买断回购 'g': 权证交易
			    "holder_kind",		//账户类别
			    "main_flag",		//主副标志,为‘1’为主帐号。
			    "register",			//指定标志,‘0’未指定 ‘1’指定
			    "seat_no",			//席位号（增加）
			    "enable_amount",	//可卖数量
			};
			sFunID = sFuncNo;
	}
	
}
