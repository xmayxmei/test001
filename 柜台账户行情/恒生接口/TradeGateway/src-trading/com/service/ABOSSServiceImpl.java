package com.service;

import java.util.Map;

import com.biz.abs.BankDmQuery;
import com.biz.abs.BankToZJBiz;
import com.biz.abs.BankToZJReQueryBiz;
import com.biz.abs.CancleOrderBiz;
import com.biz.abs.CheckFundPwdBiz;
import com.biz.abs.CheckTrdPwdBiz;
import com.biz.abs.CheckTrdPwdByGDHBiz;
import com.biz.abs.GDHQueryBiz;
import com.biz.abs.HistoryCJBiz;
import com.biz.abs.HistoryWtBiz;
import com.biz.abs.HoldStockBiz;
import com.biz.abs.ModiFundPwdBiz;
import com.biz.abs.ModiTrdPwdBiz;
import com.biz.abs.PlaceOrderBiz;
import com.biz.abs.QuoteBiz;
import com.biz.abs.TodayCJBiz;
import com.biz.abs.TodayWtBiz;
import com.biz.abs.UserBaseInfBiz;
import com.biz.abs.UserZJBiz;
import com.biz.abs.UserZJMXBiz;
import com.biz.abs.ZJNZBiz;
import com.biz.abs.ZJToBankBiz;
import com.biz.abs.ZJToBankQueryBiz;
import com.core.IBiz;

public class ABOSSServiceImpl implements ITradeService {
	// 登录处理
	@Override
	public String loginHandler(Map<String, String> request) {
		IBiz biz;
		int loginType = 1;
		try{
			loginType = Integer.parseInt(request.get("loginType"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (loginType == 1) {
			biz = new CheckTrdPwdBiz();
		} else {
			biz = new CheckTrdPwdByGDHBiz();
		}
		return biz.requestData(request);
	}

	// 持仓查询
	@Override
	public String holdStockHandler(Map<String, String> request) {
		IBiz biz = new HoldStockBiz();
		return biz.requestData(request);
	}

	// 当日委托查询
	@Override
	public String todayWtHandler(Map<String, String> request) {
		IBiz biz = new TodayWtBiz();
		return biz.requestData(request);
	}

	// 历史委托查询
	@Override
	public String hisWtHandler(Map<String, String> request) {
		IBiz biz = new HistoryWtBiz();
		return biz.requestData(request);
	}

	// 当日成交查询
	@Override
	public String todayCJHandler(Map<String, String> request) {
		IBiz biz = new TodayCJBiz();
		return biz.requestData(request);
	}

	// 历史成交查询，新股配号(历史成交查询，成交编号就是配号), 中签查询
	@Override
	public String hisCJHandler(Map<String, String> request) {
		IBiz biz = new HistoryCJBiz();
		return biz.requestData(request);
	}

	// 资金查询
	@Override
	public String queryZJHandler(Map<String, String> request) {
		IBiz biz = new UserZJBiz();
		return biz.requestData(request);
	}

	// 资金明细查询，资金流水
	@Override
	public String queryZJMXHandler(Map<String, String> request) {
		IBiz biz = new UserZJMXBiz();
		return biz.requestData(request);
	}

	// 委托买入，委托卖出，申购功能，股票市价委托 ,新股配号
	@Override
	public String placeOrderHandler(Map<String, String> request) {
		IBiz biz = new PlaceOrderBiz();
		return biz.requestData(request);
	}

	// 撤单
	@Override
	public String cancleOrderHandler(Map<String, String> request) {
		IBiz biz = new CancleOrderBiz();
		return biz.requestData(request);
	}

	// 银证转帐 银行到证券
	@Override
	public String bankToZJHandler(Map<String, String> request) {
		IBiz biz = new BankToZJBiz();
		return biz.requestData(request);
	}

	// 银证转帐 证券到银行
	@Override
	public String zjToBank(Map<String, String> request) {
		IBiz biz = new ZJToBankBiz();
		return biz.requestData(request);
	}

	// 获取用户基本信息，风险承受能力评测
	@Override
	public String userInfHandler(Map<String, String> request) {
		IBiz biz = new UserBaseInfBiz();
		return biz.requestData(request);
	}

	// 检查资金密码的有效性
	@Override
	public String checkFPwdHandler(Map<String, String> request) {
		IBiz biz = new CheckFundPwdBiz();
		return biz.requestData(request);
	}

	// 检查交易密码的有效性
	@Override
	public String checkTPwdHandler(Map<String, String> request) {
		IBiz biz = new CheckTrdPwdBiz();
		return biz.requestData(request);
	}

	// 银证转帐查询
	@Override
	public String zjToBankQuery(Map<String, String> request) {
		IBiz biz = new ZJToBankQueryBiz();
		return biz.requestData(request);
	}

	@Override
	public String modiTPwdHandler(Map<String, String> request) {
		IBiz biz = new ModiTrdPwdBiz();
		return biz.requestData(request);
	}

	@Override
	public String modiFPwdHandler(Map<String, String> request) {
		IBiz biz = new ModiFundPwdBiz();
		return biz.requestData(request);
	}

	@Override
	public String quoteHandler(Map<String, String> request) {
		IBiz biz = new QuoteBiz();
		return biz.requestData(request);
	}

	@Override
	public String checkTPwdByGDHHandler(Map<String, String> request) {
		IBiz biz = new CheckTrdPwdByGDHBiz();
		return biz.requestData(request);
	}

	@Override
	public String zjToBankReQuery(Map<String, String> request) {
		IBiz biz = new BankToZJReQueryBiz();
		return biz.requestData(request);
	}

	@Override
	public String queryBankDmHandler(Map<String, String> request) {
		IBiz biz = new BankDmQuery();
		return biz.requestData(request);
	}

	@Override
	public String zjNZBiz(Map<String, String> request) {
		IBiz biz = new ZJNZBiz();
		return biz.requestData(request);
	}

	@Override
	public String gdhQuery(Map<String, String> request) {
		IBiz biz = new GDHQueryBiz();
		return biz.requestData(request);
	}

	@Override
	public String queryCancleOrderHandler(Map<String, String> hReqParams) {
		return null;
	}

	@Override
	public String queryPeihao(Map<String, String> hReqParams) {
		return null;
	}

	@Override
	public String queryBallot(Map<String, String> hReqParams) {
		return null;
	}

	@Override
	public String queryHisZJHandler(Map<String, String> hReqParams) {
		// TODO Auto-generated method stub
		return null;
	}
}
