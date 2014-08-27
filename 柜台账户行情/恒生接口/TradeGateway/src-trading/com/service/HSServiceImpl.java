/*
 * FileName: HSServiceImpl.java
 * Copyright: Copyright 2014-6-11 Cfwx Tech. Co. Ltd.All right reserved.
 * Description: [这里用一句话描述]
 *
 */
package com.service;

import java.util.Map;

import com.biz.handsun.BankZQTransferBiz;
import com.biz.handsun.ChangePasswordBiz;
import com.biz.handsun.PlaceOrderBiz;
import com.biz.handsun.BankCodeBiz;
import com.biz.handsun.CancelOrderBiz;
import com.biz.handsun.LoginAuthenBiz;
import com.biz.handsun.QueryBallotBiz;
import com.biz.handsun.QueryBankAccBiz;
import com.biz.handsun.QueryBankZQDtBiz;
import com.biz.handsun.QueryCancelOrderBiz;
import com.biz.handsun.QueryFundsBiz;
import com.biz.handsun.QueryFundsDetailBiz;
import com.biz.handsun.QueryGDHBiz;
import com.biz.handsun.QueryHistoryCJBiz;
import com.biz.handsun.QueryHistoryFundsBiz;
import com.biz.handsun.QueryHistoryOrderBiz;
import com.biz.handsun.QueryPeiHaoBiz;
import com.biz.handsun.QueryPortfolioBiz;
import com.biz.handsun.QueryTodayCJBiz;
import com.biz.handsun.QueryTodayOrderBiz;
import com.core.IBiz;

/**
 * <code>HSServiceImpl<code> 恒生接口实现类
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 11,2014)
 *
 */
public class HSServiceImpl implements ITradeService{

	@Override
	public String loginHandler(Map<String, String> hReqParams) {
		IBiz biz = new LoginAuthenBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String holdStockHandler(Map<String, String> hReqParams) {
		IBiz biz = new QueryPortfolioBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String todayWtHandler(Map<String, String> hReqParams) {
		IBiz biz = new QueryTodayOrderBiz();
		return biz.requestData(hReqParams);
	}
	/**
	 * 历史委托查询
	 */
	@Override
	public String hisWtHandler(Map<String, String> hReqParams) {
		IBiz biz = new QueryHistoryOrderBiz();
		return biz.requestData(hReqParams);
	}
	/**
	 * 当日成交查询
	 */
	@Override
	public String todayCJHandler(Map<String, String> hReqParams) {
		IBiz biz = new QueryTodayCJBiz();
		return biz.requestData(hReqParams);
	}
	/**
	 * 历史成交查询
	 */
	@Override
	public String hisCJHandler(Map<String, String> hReqParams) {
		IBiz biz = new QueryHistoryCJBiz();
		return biz.requestData(hReqParams);
	}

	/**
	 * 资金查询
	 */
	@Override
	public String queryZJHandler(Map<String, String> hReqParams) {
		IBiz biz = new QueryFundsBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String queryBankDmHandler(Map<String, String> hReqParams) {
		IBiz biz = new BankCodeBiz();
		return biz.requestData(hReqParams);
	}

	/**
	 * 资金明细，资金流水
	 */
	@Override
	public String queryZJMXHandler(Map<String, String> hReqParams) {
		IBiz biz = new QueryFundsDetailBiz();
		return biz.requestData(hReqParams);
	}
	
	/**
	 * 查询历史资金证券流水
	 */
	@Override
	public String queryHisZJHandler(Map<String, String> hReqParams) {
		IBiz biz = new QueryHistoryFundsBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String placeOrderHandler(Map<String, String> hReqParams) {
		IBiz biz = new PlaceOrderBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String cancleOrderHandler(Map<String, String> hReqParams) {
		IBiz biz = new CancelOrderBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String bankToZJHandler(Map<String, String> hReqParams) {
		IBiz biz = new BankZQTransferBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String zjToBank(Map<String, String> hReqParams) {
		IBiz biz = new BankZQTransferBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String zjToBankReQuery(Map<String, String> hReqParams) {
		IBiz biz = new QueryBankAccBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String zjToBankQuery(Map<String, String> hReqParams) {
		IBiz biz = new QueryBankZQDtBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String userInfHandler(Map<String, String> hReqParams) {
		return null;
	}

	@Override
	public String checkFPwdHandler(Map<String, String> hReqParams) {
		return null;
	}

	@Override
	public String checkTPwdHandler(Map<String, String> hReqParams) {
		return null;
	}

	@Override
	public String checkTPwdByGDHHandler(Map<String, String> hReqParams) {
		return null;
	}

	@Override
	public String modiTPwdHandler(Map<String, String> hReqParams) {
		IBiz biz = new ChangePasswordBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String modiFPwdHandler(Map<String, String> hReqParams) {
		IBiz biz = new ChangePasswordBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String quoteHandler(Map<String, String> hReqParams) {
		return null;
	}

	@Override
	public String zjNZBiz(Map<String, String> hReqParams) {
		return null;
	}

	@Override
	public String gdhQuery(Map<String, String> hReqParams) {
		IBiz biz = new QueryGDHBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String queryCancleOrderHandler(Map<String, String> hReqParams) {
		IBiz biz = new QueryCancelOrderBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String queryPeihao(Map<String, String> hReqParams) {
		IBiz biz = new QueryPeiHaoBiz();
		return biz.requestData(hReqParams);
	}

	@Override
	public String queryBallot(Map<String, String> hReqParams) {
		IBiz biz = new QueryBallotBiz();
		return biz.requestData(hReqParams);
	}

}
