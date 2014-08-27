package com.fortune.trading.service;

import java.util.Map;


/**
 * <code>ITradeService</code>
 * 
 * @author Colin
 */
public interface ITradeService {
	/**
	 * 登录
	 * @param hReqParams
	 * @return
	 */
	public String loginHandler(Map<String, String> hReqParams);
	/**
	 * 当日持仓
	 * @param hReqParams
	 * @return
	 */
	public String holdStockHandler(Map<String, String> hReqParams);
	/**
	 * 当日委托查询
	 * @param hReqParams
	 * @return
	 */
	public String todayOrdersHandler(Map<String, String> hReqParams);
	/**
	 * 历史委托查询
	 * @param hReqParams
	 * @return
	 */
	public String hisOrderHandler(Map<String, String> hReqParams);
	/**
	 * 当日成交查询
	 * @param hReqParams
	 * @return
	 */
	public String todayTransactionHandler(Map<String, String> hReqParams);
	/**
	 * 历史成交查询，新股配号(历史成交查询，成交编号就是配号), 中签查询
	 * @param hReqParams
	 * @return
	 */
	public String hisTransactionHandler(Map<String, String> hReqParams);
	/**
	 * 配号查询
	 * @param hReqParams
	 * @return
	 */
	public String queryPeihao(Map<String, String> hReqParams);
	/**
	 * 中签查询
	 * @param hReqParams
	 * @return
	 */
	public String queryBallot(Map<String, String> hReqParams);
	/**
	 * 资金查询
	 * @param hReqParams
	 * @return
	 */
	public String queryZJHandler(Map<String, String> hReqParams);
	/**
	 * 资金明细查询，资金流水 
	 * @param hReqParams
	 * @return
	 */
	public String queryBankDmHandler(Map<String, String> hReqParams);
	/**
	 * 资金明细查询，资金流水 
	 * @param hReqParams
	 * @return
	 */
	public String queryZJMXHandler(Map<String, String> hReqParams);
	
	/**
	 * 资金明细查询，资金流水 
	 * @param hReqParams
	 * @return
	 */
	public String queryHistoryZJHandler(Map<String, String> hReqParams);
	/**
	 * 委托买入，委托卖出，申购功能，股票市价委托 ,新股配号
	 * @param hReqParams
	 * @return
	 */
	public String placeOrderHandler(Map<String, String> hReqParams);
	/**
	 * 撤单
	 * @param hReqParams
	 * @return
	 */
	public String cancleOrderHandler(Map<String, String> hReqParams);
	/**
	 * 可撤单查询
	 * @param hReqParams
	 * @return
	 */
	public String queryCancleOrderHandler(Map<String, String> hReqParams);
	/**
	 * 银证转帐 银行到证券
	 * @param hReqParams
	 * @return
	 */
	public String bankToZQHandler(Map<String, String> hReqParams);
	/**
	 * 银证转帐 证券到银行
	 * @param hReqParams
	 * @return
	 */
	public String zjToBank(Map<String, String> hReqParams);
	/**
	 * 银证转帐关系查询
	 * @param hReqParams
	 * @return
	 */
	public String zjToBankReQuery(Map<String, String> hReqParams);
	/**
	 * @param hReqParams
	 * @return
	 */
	public String zjToBankQuery(Map<String, String> hReqParams);
	/**
	 * 获取用户基本信息，风险承受能力评测 
	 * @param hReqParams
	 * @return
	 */
	public String userInfHandler(Map<String, String> hReqParams);
	/**
	 * 检查资金密码有效性
	 * @param hReqParams
	 * @return
	 */
	public String checkFPwdHandler(Map<String, String> hReqParams);
	/**
	 * 检查交易密码有效性
	 * @param hReqParams
	 * @return
	 */
	public String checkTPwdHandler(Map<String, String> hReqParams);
	/**
	 * 检查交易密码有效性用股东号
	 * @param hReqParams
	 * @return
	 */
	public String checkTPwdByGDHHandler(Map<String, String> hReqParams);
	/**
	 * 修改交易密码
	 * @param hReqParams
	 * @return
	 */
	public String modiTPwdHandler(Map<String, String> hReqParams);
	/**
	 * 修改资金密码
	 * @param hReqParams
	 * @return
	 */
	public String modiFPwdHandler(Map<String, String> hReqParams);
	/**
	 * 行情
	 * @param hReqParams
	 * @return
	 */
	public String quoteHandler(Map<String, String> hReqParams);
	/**
	 * 资金归转
	 * @param hReqParams
	 * @return
	 */
	public String zjNZBiz(Map<String, String> hReqParams);
	/**
	 * 查询股东号
	 * @param hReqParams
	 * @return
	 */
	public String gdhQuery(Map<String, String> hReqParams);
}
