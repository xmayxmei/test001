package com.fortune.trading.controller.trade;

import static com.fortune.trading.util.Constants.SK.CANCELORDER_PAGE_RANID;
import static com.fortune.trading.util.Constants.SK.PLACEORDER_PAGE_RANID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fortune.trading.annotation.AutoInject;
import com.fortune.trading.entity.ClientResponse;
import com.fortune.trading.service.ITradeConverter;
import com.fortune.trading.service.ITradeService;
import com.fortune.trading.service.impl.HandsunConverter;
import com.fortune.trading.util.Constants;
import com.fortune.trading.util.U;
/**
 * <code>TradingController</code> 交易前端控制层.
 *
 * @author Colin, Jimmy
 * @since Trading v0.0.1 (April 24, 2014)
 */
@Controller("tradingController")
public class TradingController {
	@Autowired(required = true)
	private ITradeService tradeService;
	@Autowired(required = true)
	private ITradeConverter tradeConverter;
	/**
	 *  限价买入页面
	 */
	@RequestMapping("/limitBuyPage")
	public String limitBuyPage(HttpServletRequest oReq, HttpSession oSession) {
		String sRanID = U.generateUID();
		oSession.setAttribute(PLACEORDER_PAGE_RANID, sRanID);
		oReq.setAttribute("ranID", sRanID);
		oReq.setAttribute("wsURL", Constants.quoteWSURL);
		oReq.setAttribute("entrust_prop", "0");
		oReq.setAttribute("entrust_bs", "1");
		oReq.setAttribute("entrust_type", "0");
		oReq.setAttribute("defaultCode", oReq.getParameter("code")==null? "": oReq.getParameter("code"));
		return "/trade/buy";
	}
	/**
	 *  新股申购页面
	 */
	@RequestMapping("/ipo")
	public String IPO(HttpServletRequest oReq, HttpSession oSession) {
		String sRanID = U.generateUID();
		oSession.setAttribute(PLACEORDER_PAGE_RANID, sRanID);
		oReq.setAttribute("ranID", sRanID);
		oReq.setAttribute("wsURL", Constants.quoteWSURL);
		oReq.setAttribute("entrust_prop", "3");
		oReq.setAttribute("entrust_bs", "1");
		oReq.setAttribute("entrust_type", "0");
		return "/trade/ipo";
	}
	/**
	 *  限价卖出页面
	 */
	@RequestMapping("/limitSellPage")
	public String limitSellPage(HttpServletRequest oReq, HttpSession oSession) {
		String sRanID = U.generateUID();
		oSession.setAttribute(PLACEORDER_PAGE_RANID, sRanID);
		oReq.setAttribute("ranID", sRanID);
		oReq.setAttribute("wsURL", Constants.quoteWSURL);
		oReq.setAttribute("entrust_prop", "0");
		oReq.setAttribute("entrust_bs", "2");
		oReq.setAttribute("entrust_type", "0");
		
		oReq.setAttribute("defaultCode", oReq.getParameter("code")==null?"":oReq.getParameter("code"));
		return "/trade/sell";
	}
	/**
	 *  市价买入页面
	 */
	@RequestMapping("/marketBuyPage")
	public String marketBuyPage(HttpServletRequest oReq, HttpSession oSession) {
		String sRanID = U.generateUID();
		oSession.setAttribute(PLACEORDER_PAGE_RANID, sRanID);
		oReq.setAttribute("ranID", sRanID);
		oReq.setAttribute("wsURL", Constants.quoteWSURL);
		oReq.setAttribute("entrust_prop", "0");
		oReq.setAttribute("entrust_bs", "1");
		oReq.setAttribute("entrust_type", "0");
		return "/trade/marketbuy";
	}
	/**
	 *  市价卖出页面
	 */
	@RequestMapping("/marketSellPage")
	public String marketSellPage(HttpServletRequest oReq, HttpSession oSession) {
		String sRanID = U.generateUID();
		oSession.setAttribute(PLACEORDER_PAGE_RANID, sRanID);
		oReq.setAttribute("ranID", sRanID);
		oReq.setAttribute("wsURL", Constants.quoteWSURL);
		oReq.setAttribute("entrust_prop", "0");
		oReq.setAttribute("entrust_bs", "2");
		oReq.setAttribute("entrust_type", "0");
		return "/trade/marketsell";
	}
	/**
	 * 撤单页面
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/cancelOrderPage")
	public String cancelOrderPage(HttpServletRequest oReq, HttpSession oSession) {
		String sRanID = U.generateUID();
		oSession.setAttribute(CANCELORDER_PAGE_RANID, sRanID);
		oReq.setAttribute("ranID", sRanID);
		return "/trade/ordercancel";
	}
	/**
	 * 下单
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/placeOrder")
	@AutoInject(paramName="hParams",keys={"fund_account", "password", "branch_no", "op_station"})
	public @ResponseBody ClientResponse<?> placeOrder(@RequestParam Map<String, String> hParams) {
		String sExchange = hParams.remove("jys");
		if (sExchange != null && !hParams.containsKey("exchange_type")) {
			hParams.put("exchange_type", (String)HandsunConverter.hExMapping.getAs(sExchange));
		}
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.placeOrderHandler(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "placeOrder", U.STR.fastSplit2I(sCustomFields, ','));
		return oClientRsp;
	}
	/**
	 * 撤单
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/cancelOrder")
	@AutoInject(paramName="hParams",keys={"fund_account", "password", "branch_no", "op_station"})
	public @ResponseBody ClientResponse<?> cancelOrder(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.cancleOrderHandler(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "cancelOrder", U.STR.fastSplit2I(sCustomFields, ','));
		return oClientRsp;
	}
	/**
	 * 检查交易密码有效性
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/checkTPwd")
	public @ResponseBody String checkTPwd(@RequestParam Map<String, String> hParams) {
		String sResp = tradeService.checkTPwdHandler(hParams);
		return sResp;
	}
	/**
	 * 指定交易 （只限于上交所）
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/specifiedTradePage")
	@AutoInject(paramName="hParams",keys={"clientId"})
	public String specifiedTradePage(@RequestParam Map<String, String> hParams, HttpServletRequest oReq, HttpSession oSession) {
		String sMsg = null;
		//获取股东号
		String sResp = tradeService.gdhQuery(hParams);
		ClientResponse<String[]> oStockHolderRsp = (ClientResponse<String[]>)tradeConverter.convertResp2ClientResp(sResp, "gdhQuery", null);
		sMsg = oStockHolderRsp.getErrMsg();
		List<String> lstStockHolder = new ArrayList<String>();
		List<String> lstExchange = new ArrayList<String>();
		if (U.STR.isEmpty(sMsg)) {
			List<String[]> lstData = oStockHolderRsp.getData();
			if (lstData.size() > 0) {
				for (String[] as : lstData) {
					String sExchange = as[1];
					if ("sh".equalsIgnoreCase(sExchange) || "hb".equalsIgnoreCase(sExchange)) {
						lstStockHolder.add(as[0]);
						lstExchange.add(sExchange);
					}
				}
				if (lstStockHolder.size() < 1) {
					sMsg = "指定交易只限定于上交所.";
				}
			} else {
				sMsg = "获取股东号失败.";
			}
		} else {
			sMsg = sMsg + "(获取股东号失败)";
		}
		if (U.STR.isEmpty(sMsg)) {
			String sRanID = U.generateUID();
			oSession.setAttribute(PLACEORDER_PAGE_RANID, sRanID);
			oReq.setAttribute("ranID", sRanID);
			oReq.setAttribute("ddlx", "0");
			oReq.setAttribute("jylb", "9");
			oReq.setAttribute("gdh", lstStockHolder.get(0));
			oReq.setAttribute("jys", lstExchange.get(0));
			return "/trade/specifiedtrade";
		} else {
			oReq.setAttribute("msg", sMsg);
			return "/result/info";
		}
	}
	/**
	 * 检查交易密码有效性
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/specifiedTrade")
	@AutoInject(paramName="hParams",keys={"clientId", "jymm"})
	public String specifiedTrade(@RequestParam Map<String, String> hParams, RedirectAttributes attr) {
		String sMsg = null;
		ClientResponse<?>  sResp  = placeOrder(hParams);
		sMsg = sResp.getErrMsg();
		if (U.STR.isEmpty(sMsg)) {
			List<String[]> lstData = (List<String[]>)sResp.getData();
			if (lstData.size() < 1) {
				sMsg = "交易服务器返回的数据为空.";
			} else {
				String[] asData = lstData.get(0);
				try {
					sMsg = asData[1];
					if (Float.parseFloat(asData[0]) > 0) {
						attr.addAttribute("isSuccess", "true");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		attr.addAttribute("msg", sMsg);
		return "redirect:info.do";
	}
}
