package com.trading.controller.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.ITradeService;
/**
 * <code>TradingController</code> 交易前端控制层.
 *
 * @author Colin, Jimmy
 * @since Trading v0.0.1 (April 24, 2014)
 */
@Controller("tradingController")
@RequestMapping()
public class TradingController {
	public static final String LOGIN_USER = "login.user";
	
	@Autowired(required = true)
	private ITradeService tradeService;
	/**
	 * 登录验证
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/loginAuthen")
	public @ResponseBody String loginAuthen(@RequestParam Map<String, String> hParams, HttpSession oSession) {
		preProcessLoginAuthen(hParams);
		String sResp = tradeService.loginHandler(hParams);
		return sResp;
	}
	/**
	 * 资金查询
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/queryZJ")
	public @ResponseBody String queryFunds(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.queryZJHandler(hParams);
		return sResp;
	}
	/**
	 * 持仓查询
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/holdStock")
	public @ResponseBody String holdStock(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.holdStockHandler(hParams);
		return sResp;
	}
	/**
	 * 下单
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/placeOrder")
	public @ResponseBody String placeOrder(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.placeOrderHandler(hParams);
		return sResp;
	}
	/**
	 * 撤单
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/cancelOrder")
	public @ResponseBody String cancelOrder(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.cancleOrderHandler(hParams);
		return sResp;
	}
	/**
	 * 可撤单查询
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/queryCancelOrder")
	public @ResponseBody String queryCancelOrder(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.queryCancleOrderHandler(hParams);
		return sResp;
	}
	/**
	 * 当日委托
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/todayWt")
	public @ResponseBody String todayWt(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.todayWtHandler(hParams);
		return sResp;
	}
	/**
	 * 当日成交
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/todayCJ")
	public @ResponseBody String todayTransaction(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.todayCJHandler(hParams);
		return sResp;
	}
	/**
	 * 历史成交查询(历史成交查询，成交编号就是配号)
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/historyCJ")
	public @ResponseBody String historyTransaction(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.hisCJHandler(hParams);
		return sResp;
	}
	/**
	 * 配号查询
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/queryPeihao")
	public @ResponseBody String queryPeihao(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.queryPeihao(hParams);
		return sResp;
	}
	/**
	 * 中签查询
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/queryBallot")
	public @ResponseBody String queryBallot(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.queryBallot(hParams);
		return sResp;
	}
	/**
	 * 历史委托
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/historyWt")
	public @ResponseBody String historyWt(@RequestParam Map<String, String> hParams, HttpServletRequest oReq) {
		processClientId(hParams);
		String sResp = tradeService.hisWtHandler(hParams);
		return sResp;
	}
	/**
	 * 行情
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/quote")
	public @ResponseBody String quote(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.quoteHandler(hParams);
		return sResp;
	}
	/**
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/bankToZJ")
	public @ResponseBody String bankToBond(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.bankToZJHandler(hParams);
		return sResp;
	}
	/**
	 * 银证转帐 证券到银行
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/zjToBank")
	public @ResponseBody String bondToBank(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.zjToBank(hParams);
		return sResp;
	}
	/**
	 * 检查资金密码有效性
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/checkFPwd")
	public @ResponseBody String checkFundsPwd(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.checkFPwdHandler(hParams);
		return sResp;
	}
	/**
	 * 检查交易密码有效性用股东号
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/checkTPwdByGDH")
	public @ResponseBody String checkTPwdByGDH(@RequestParam Map<String, String> hParams) {
		String sResp = tradeService.checkTPwdByGDHHandler(hParams);
		return sResp;
	}
	/**
	 * 检查交易密码有效性
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/checkTPwd")
	public @ResponseBody String checkTPwd(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.checkTPwdHandler(hParams);
		return sResp;
	}
	/**
	 * 修改资金密码
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/modiFPwd")
	public @ResponseBody String modiFPwd(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.modiFPwdHandler(hParams);
		return sResp;
	}
	/**
	 * 修改交易密码
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/modiTPwd")
	public @ResponseBody String modiTPwd(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.modiTPwdHandler(hParams);
		return sResp;
	}
	/**
	 * 查询银行代码
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/queryBankDm")
	public @ResponseBody String queryBankDm(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.queryBankDmHandler(hParams);
		return sResp;
	}
	
	/**
	 * 资金明细查询，资金流水 
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/queryZJMX")
	public @ResponseBody String queryZJMX(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.queryZJMXHandler(hParams);
		String sRespHis = tradeService.queryHisZJHandler(hParams);
		return sResp + "@" + sRespHis;
	}
	/**
	 * 查询历史资金证券流水
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/queryHistoryZJ")
	public @ResponseBody String queryHistoryZJ(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.queryHisZJHandler(hParams);
		return sResp;
	}
	/**
	 * 资金归转
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/zjNZ")
	public @ResponseBody String zjNZ(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.zjNZBiz(hParams);
		return sResp;
	}
	/**
	 * 
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/zjToBankQuery")
	public @ResponseBody String zjToBankQuery(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.zjToBankQuery(hParams);
		return sResp;
	}
	/**
	 * 银证转帐关系查询
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/zjToBankReQuery")
	public @ResponseBody String zjToBankReQuery(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.zjToBankReQuery(hParams);
		return sResp;
	}
	/**
	 * 获取用户基本信息，风险承受能力评测 
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/userInfo")
	public @ResponseBody String userInfo(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.userInfHandler(hParams);
		return sResp;
	}
	/**
	 * 查询股东号
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/gdhQuery")
	public @ResponseBody String gdhQuery(@RequestParam Map<String, String> hParams) {
		processClientId(hParams);
		String sResp = tradeService.gdhQuery(hParams);
		return sResp;
	}
	/*
	 * 
	 * @param hParams
	 * @return
	 */
	private void processClientId(Map<String, String> hParams)  {
		String sClientID = hParams.get("clientId");
		if (sClientID == null || sClientID.equals("") || sClientID.length() > 11) {
			return ;
		}
		sClientID = "000000000000" + sClientID;
		int iLen = sClientID.length();
		hParams.put("clientId", sClientID.substring(iLen - 12));
	}
	/*
	 * @param hParams
	 */
	private void preProcessLoginAuthen(Map<String, String> hParams) {
		String sLoginType = hParams.get("loginType");
		if (!"1".equals(sLoginType)) {
			return;
		}
		processClientId(hParams);
	}
}
