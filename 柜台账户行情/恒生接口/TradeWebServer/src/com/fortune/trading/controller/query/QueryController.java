package com.fortune.trading.controller.query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fortune.trading.annotation.AutoInject;
import com.fortune.trading.entity.ClientResponse;
import com.fortune.trading.entity.Funds;
import com.fortune.trading.entity.HoldStocks;
import com.fortune.trading.service.ITradeConverter;
import com.fortune.trading.service.ITradeService;
import com.fortune.trading.util.TradeFormatUtil;
import com.fortune.trading.util.U;

/**
 * <code>QueryController</code> 查询模块前端控制器.
 * 
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1
 */
@Controller("queryController")
public class QueryController {
	@Autowired(required = true)
	private ITradeService tradeService;
	@Autowired(required = true)
	private ITradeConverter tradeConverter;
	
	/**
	 *  资金查询请求页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/fundsPage")
	@AutoInject(paramName="hParams",keys={"fund_account", "branch_no", "password", "op_station"})
	public String fundsRequest(HttpServletRequest oReq, @RequestParam Map<String, String> hParams) {
		String sResp = tradeService.queryZJHandler(hParams);
		ClientResponse<String[]> oClientRsp = (ClientResponse<String[]>)tradeConverter.convertResp2ClientResp(sResp, "queryFunds", null);
		List<String[]> lstDatas = oClientRsp.getData();
		
		List<Funds> lstFunds = new ArrayList<Funds>();
		// 只显示人民币
		for (String[] asVal: lstDatas) {
			String sBz = asVal[0];
			if (!"0".equals(sBz)) {
				continue;
			}
			Funds funds = new Funds();
			funds.setAcc(hParams.get("fund_account"));
			funds.setBz(asVal[0]);
			funds.setFundsBal(asVal[1]);
			String sFreez = TradeFormatUtil.formatPrice2(Double.parseDouble(asVal[7]) - Double.parseDouble(asVal[2]));
			funds.setFreezebal(String.valueOf(sFreez));
			funds.setAvailFunds(asVal[2]);
			funds.setAdviFunds(asVal[3]);
			funds.setMarketVal(asVal[8]);
			funds.setTotalAssert(asVal[5]);
			lstFunds.add(funds);
		}
		oReq.setAttribute("lstFunds", lstFunds);
		return "/query/moneyquery";
	}
	/**
	 * 资金查询
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/queryFunds.do")
	@AutoInject(paramName="hParams",keys={"fund_account", "branch_no", "password", "op_station"})
	public @ResponseBody ClientResponse<?> queryFunds(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.queryZJHandler(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "queryFunds", U.STR.fastSplit2I(sCustomFields, ','));
		return oClientRsp;
	}
	/**
	 * 资金流水请求页面
	 */
	@RequestMapping("/fundsFlowPage")
	public String fundsFlowRequest() {
		return "/query/moneyflow";
	}
	/**
	 * 持仓查询请求页面
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/holdStockPage")
	@AutoInject(paramName="hParams",keys={"fund_account", "branch_no", "password", "op_station"})
	public String holdStockPage(@RequestParam Map<String, String> hParams, HttpServletRequest oReq) {
		String sResp = tradeService.holdStockHandler(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "holdStock", null);
		String sMsg = oClientRsp.getErrMsg();
		if (U.STR.isEmpty(sMsg)) {
			List<String[]> lstData = (List<String[]>)oClientRsp.getData();
			if (lstData.size() < 1) {
				sMsg = "交易服务器返回的数据为空.";
			} else {
				List<HoldStocks> lstHoldStocks = new ArrayList<HoldStocks>();
				for(String[] as : lstData) {
					HoldStocks holdStock = new HoldStocks();
					holdStock.setExchange(as[0]);
					holdStock.setShareholder(as[1]);
					holdStock.setStockCode(as[2]);
					holdStock.setStockName(as[3]);
					holdStock.setQty(as[4]);
					holdStock.setTotalSellEnable(as[5]);
					holdStock.setLastPrice(as[6]);
					holdStock.setCostPrice(as[7]);
					holdStock.setTotalPL(as[8]);
					holdStock.setMarketVal(as[10]);
					lstHoldStocks.add(holdStock);
				}
				oReq.setAttribute("lstHoldStocks", lstHoldStocks);
			}
		}
		if (!U.STR.isEmpty(sMsg)) {
			oReq.setAttribute("msg", sMsg);
		}
		return "/query/holdstocks";
	}
	/**
	 * 持仓查询
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/holdStock")
	@AutoInject(paramName="hParams",keys={"fund_account", "branch_no", "password", "op_station"})
	public @ResponseBody ClientResponse<?> holdStock(@RequestParam Map<String, String> hParams, HttpServletRequest oReq) {
		String sCustomFields = "3,2,4,5,7,6,10,8,1,0";
		String sResp = tradeService.holdStockHandler(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "holdStock", U.STR.fastSplit2I(sCustomFields, ','));
		String str = hParams.get("position_str");
		if(!U.STR.isEmpty(str) && oClientRsp.getData().size() == 0) {
			hParams.put("position_str", "");
			sResp = tradeService.holdStockHandler(hParams);
			oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "holdStock", U.STR.fastSplit2I(sCustomFields, ','));
		}
		return oClientRsp;
	}
	/**
	 * 当日成交
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/todayTransaction")
	@AutoInject(paramName="hParams",keys={"fund_account", "branch_no", "password", "op_station"})
	public @ResponseBody ClientResponse<?> todayTransaction(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.todayTransactionHandler(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "todayTransaction", U.STR.fastSplit2I(sCustomFields, ','));
		return oClientRsp;
	}
	/**
	 * 历史成交
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/historyTransaction")
	@AutoInject(paramName="hParams",keys={"fund_account", "branch_no", "password", "op_station"})
	public @ResponseBody ClientResponse<?> historyTransaction(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.hisTransactionHandler(hParams);
		List<Integer> lstCusFields = U.STR.fastSplit2I(sCustomFields, ',');
		ClientResponse<String[]> oClientRsp = (ClientResponse<String[]>)tradeConverter.convertResp2ClientResp(sResp, "historyTransaction", lstCusFields);
		if (lstCusFields != null) {
			//排序 根据日期
			Integer iSortColumn = lstCusFields.indexOf(1);
			if (iSortColumn != null) {
				U.ARR.sort(oClientRsp.getData(), iSortColumn, false);
			}
		}
		return oClientRsp;
	}
	/**
	 * 历史委托
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/historyOrder.do")
	@AutoInject(paramName="hParams",keys={"fund_account", "branch_no", "password", "op_station"})
	public @ResponseBody ClientResponse<?> historyWt(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.hisOrderHandler(hParams);
		List<Integer> lstCusFields = U.STR.fastSplit2I(sCustomFields, ',');
		ClientResponse<String[]> oClientRsp = (ClientResponse<String[]>)tradeConverter.convertResp2ClientResp(sResp, "historyOrder", lstCusFields);
		
		if (lstCusFields != null) {
			//排序 根据日期
			Integer iSortColumn = lstCusFields.indexOf(16);
			if (iSortColumn != null) {
				U.ARR.sort(oClientRsp.getData(), iSortColumn, false);
			}
		}
		return oClientRsp;
	}
	/**
	 * 查询配号
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryDistribution")
	@AutoInject(paramName="hParams",keys={"fund_account", "password", "branch_no", "op_station"})
	public @ResponseBody ClientResponse<?> queryDistribution(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.queryPeihao(hParams);
		List<Integer> lstCusFields = U.STR.fastSplit2I(sCustomFields, ',');
		ClientResponse<String[]> oClientRsp = (ClientResponse<String[]>)tradeConverter.convertResp2ClientResp(sResp, "queryPeihao", lstCusFields);
		/*if (lstCusFields != null) {
			//排序 根据日期
			Integer iSortColumn = lstCusFields.indexOf(0);
			if (iSortColumn != null) {
				U.ARR.sort(oClientRsp.getData(), iSortColumn, false);
			}
		}*/
		return oClientRsp;
	}
	/**
	 * 查询中签
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryBallot")
	@AutoInject(paramName="hParams",keys={"fund_account", "password", "branch_no", "op_station"})
	public @ResponseBody ClientResponse<?> queryBallot(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.queryBallot(hParams);
		List<Integer> lstCusFields = U.STR.fastSplit2I(sCustomFields, ',');
		ClientResponse<String[]> oClientRsp = (ClientResponse<String[]>)tradeConverter.convertResp2ClientResp(sResp, "queryBallot", lstCusFields);
		if (lstCusFields != null) {
			//排序 根据日期
			Integer iSortColumn = lstCusFields.indexOf(1);
			if (iSortColumn != null) {
				U.ARR.sort(oClientRsp.getData(), iSortColumn, false);
			}
		}
		return oClientRsp;
	}
	/**
	 * 检查资金密码有效性
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/checkFPwd")
	public @ResponseBody String checkFundsPwd(@RequestParam Map<String, String> hParams) {
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
	 * 银行代码查询 
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/queryBankCode")
	public @ResponseBody ClientResponse<?> queryBankCode(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.queryBankDmHandler(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "queryBankDm", U.STR.fastSplit2I(sCustomFields, ','));
		return oClientRsp;
	}
	/**
	 * 查询客户号绑定的银行
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryBoundBank")
	@AutoInject(paramName="hParams",keys={"fund_account", "password", "branch_no", "op_station"})
	public @ResponseBody ClientResponse<?> boundBanks(@RequestParam Map<String, String> hParams) {
		String sMsg = null;
		hParams.put("cusFields", "3,1,4,0,2");
		ClientResponse<?> sResp = zjToBankReQuery(hParams);
		sMsg = sResp.getErrMsg();
		//获取绑定的银行账号
		List<String[]> lstRelative = null;
		if (U.STR.isEmpty(sMsg)) {
			List<String[]> lstData = (List<String[]>)sResp.getData();
			if (lstData.size() < 1) {
				sMsg = "查询失败";
			} else {
				 lstRelative = (List<String[]>)sResp.getData();
				 for (int i = 0; i < lstRelative.size(); i++) {
					 lstRelative.get(i)[2] = hParams.get("fund_account");
				 }
			}
		}
		return sResp;
	}
	/**
	 * 股东号查询
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/gdhQuery")
	@AutoInject(paramName="hParams",keys={"fund_account", "password", "branch_no", "op_station"})
	public @ResponseBody ClientResponse<?> gzhQuery(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.gdhQuery(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "gdhQuery", U.STR.fastSplit2I(sCustomFields, ','));
		return oClientRsp; 
	}
	/**
	 * 用户基本信息关系查询
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/userInfo")
	@AutoInject(paramName="hParams",keys={"fund_account"})
	public @ResponseBody ClientResponse<?> userInfo(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.userInfHandler(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "userInfo", U.STR.fastSplit2I(sCustomFields, ','));
		return oClientRsp;
	}
	/**
	 * 银证转帐关系查询
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/zjToBankReQuery")
	@AutoInject(paramName="hParams",keys={"fund_account", "branch_no", "password", "op_station"})
	public ClientResponse<?>  zjToBankReQuery(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.zjToBankReQuery(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "zjToBankReQuery", U.STR.fastSplit2I(sCustomFields, ','));
		return oClientRsp;
	}
	/**
	 * 资金明细查询，资金流水 
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryFundsDt")
	@AutoInject(paramName="hParams",keys={"fund_account", "password", "branch_no", "op_station", })
	public @ResponseBody ClientResponse<?> queryZJMX(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		// 返回的消息有 ： 当天的资金流水和历史的资金流水
		String sResp = tradeService.queryZJMXHandler(hParams);
		List<Integer> lstCusFields = U.STR.fastSplit2I(sCustomFields, ',');
		String sTodayFundsDt = null;
		String sHistoryFundsDt = null;
		if (sResp != null) {
			String[] s = U.STR.fastSplit(sResp, '@');
			sTodayFundsDt = s[0];
			sHistoryFundsDt = s[1];
		}
		String sStartDate = hParams.get("start_date");
		String sEndDate = hParams.get("end_date");
		String sCurrDate = U.formatYYYYMMDD(Calendar.getInstance().getTime());
		
		ClientResponse<String[]> oClientRsp = new ClientResponse<String[]>();
		if (sCurrDate.equals(sStartDate) || sCurrDate.equals(sEndDate)) {
			ClientResponse<String[]> oClientToday = (ClientResponse<String[]>)tradeConverter.convertResp2ClientResp(sTodayFundsDt, "queryZJMX", lstCusFields);
			ClientResponse<String[]> oClientHis = (ClientResponse<String[]>)tradeConverter.convertResp2ClientResp(sHistoryFundsDt, "queryZJMX", lstCusFields);
			if (!U.STR.isEmpty(oClientToday.getErrMsg()) && !U.STR.isEmpty(oClientHis.getErrMsg())) {
				oClientRsp.setErrMsg(oClientToday.getErrMsg());
			} else {
				oClientRsp.addData(oClientToday.getData());
				oClientRsp.addData(oClientHis.getData());
			}
		} else {
			ClientResponse<String[]> oClientHis = (ClientResponse<String[]>)tradeConverter.convertResp2ClientResp(sHistoryFundsDt, "queryZJMX", lstCusFields);
			oClientRsp.setErrMsg(oClientHis.getErrMsg());
			oClientRsp.addData(oClientHis.getData());
		}
		
		if (lstCusFields != null) {
			//排序 日期
			Integer iSortColumn = lstCusFields.indexOf(1);
			if (iSortColumn != null) {
				U.ARR.sort(oClientRsp.getData(), iSortColumn, false);
			}
		}
		return oClientRsp;
	}
	
	/**
	 * 查询历史资金证券流水
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryHisFunds")
	@AutoInject(paramName="hParams",keys={"fund_account", "password", "branch_no", "op_station"})
	public @ResponseBody ClientResponse<?> queryHistoryZJ(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.queryHistoryZJHandler(hParams);
		List<Integer> lstCusFields = U.STR.fastSplit2I(sCustomFields, ',');
		ClientResponse<String[]> oClientRsp = (ClientResponse<String[]>)tradeConverter.convertResp2ClientResp(sResp, "queryHistoryZJ", lstCusFields);
		if (lstCusFields != null) {
			//排序 日期
			Integer iSortColumn = lstCusFields.indexOf(0);
			if (iSortColumn != null) {
//				U.ARR.sort(oClientRsp.getData(), iSortColumn, false);
			}
		}
		return oClientRsp;
	}
	/**
	 * 当日委托
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/todayOrder")
	@AutoInject(paramName="hParams",keys={"fund_account", "branch_no", "password", "op_station"})
	public @ResponseBody ClientResponse<?> todayWt(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.todayOrdersHandler(hParams);
		List<Integer> lstCusFields = U.STR.fastSplit2I(sCustomFields, ',');
		ClientResponse<String[]> oClientRsp = (ClientResponse<String[]>)tradeConverter.convertResp2ClientResp(sResp, "todayOrder", lstCusFields);
		if (lstCusFields != null) {
			//排序
			Integer iSortColumn = lstCusFields.indexOf(23);
			if (iSortColumn != null) {
				U.ARR.sort(oClientRsp.getData(), iSortColumn, false);
			}
		}
		return oClientRsp;
	}
	/**
	 * 可撤单查询
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/todayOrderForCancel")
	@AutoInject(paramName="hParams",keys={"fund_account", "password", "branch_no", "op_station"})
	public @ResponseBody ClientResponse<?> queryCancelOrder(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.queryCancleOrderHandler(hParams);
		List<Integer> lstCusFields = U.STR.fastSplit2I(sCustomFields, ',');
		ClientResponse<String[]> oClientRsp = (ClientResponse<String[]>)tradeConverter.convertResp2ClientResp(sResp, "todayOrderForCancel", lstCusFields);
		/*if (lstCusFields != null) {
			//排序
			Integer iSortColumn = lstCusFields.indexOf(11);
			if (iSortColumn != null) {
//				U.ARR.sort(oClientRsp.getData(), iSortColumn, false);
			}
		}*/
		return oClientRsp;
	}
	/**
	 * 历史委托请求页面
	 */
	@RequestMapping("/hisOrderPage")
	public String historyOrderRequest() {
		return "/query/hisagency";
	}
	/**
	 * 当日委托请求页面
	 */
	@RequestMapping("/todayOrderPage")
	public String todayOrderRequest() {
		return "/query/dayagency";
	}
	/**
	 * 当日成交请求页面
	 */
	@RequestMapping("/todayTransPage")
	public String todayTransactionRequest() {
		return "/query/daytransaction";
	}
	/**
	 * 历史成交请求页面
	 */
	@RequestMapping("/hisTransaction")
	public String historyTransactionRequest() {
		return "/query/hisTransaction";
	}
	/**
	 * 首页请求页面
	 */
	@RequestMapping("/homePage.do")
	public String homePage(@RequestParam(required=false) String page, HttpServletRequest oReq) {
		if (page == null) {
			page = "1";
		}
		oReq.setAttribute("page", page);
		return "/query/home";
	}
	/**
	 * 配号请求页面
	 */
	@RequestMapping("/peihaoPage")
	public String peihaoRequest() {
		return "/query/peihaorecord";
	}
	/**
	 * 中签请求页面
	 */
	@RequestMapping("/ballotPage")
	public String ballotPage() {
		return "/query/ballotrecord";
	}
}
