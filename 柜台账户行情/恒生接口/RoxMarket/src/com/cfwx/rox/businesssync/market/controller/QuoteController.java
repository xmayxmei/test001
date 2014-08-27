package com.cfwx.rox.businesssync.market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.service.IQuoteService;
import com.cfwx.rox.businesssync.market.show.CommonLine;
import com.cfwx.rox.businesssync.market.show.DayLine;
import com.cfwx.rox.businesssync.market.show.MinLine;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.util.MathFactory;

/**
 * <code>QuoteController</code> 分钟、日K,周K,月K
 * @since 2014-5-27
 */
@Controller
public class QuoteController {
  private final static String ENCODE = "utf-8";
  private final Logger LOG = Logger.getLogger(QuoteController.class);
  @Autowired
  private IQuoteService oQuoteSerivce;
  /**
   * 周K
   * @param request
   * @param response
   */
  @RequestMapping(value = "/quote/getWeeklyKline.do")
	public void getKline(HttpServletRequest request,
			HttpServletResponse response) {
		// 结果集合
		Map<String, Object> map = null;
		String code = request.getParameter("code");
		String size = request.getParameter("size");
		// 获取该类型下的证券代码
		try {
			ActualMarket am = MarketCache.get(code);
			map = new HashMap<String, Object>();
			map.put("stockData", MathFactory.parseShowAM(am));
			// 周K
			List<CommonLine> lstWeekly = oQuoteSerivce.getWeeklyKline(code,Integer.parseInt(size));
			if (lstWeekly == null || lstWeekly.isEmpty()) {
				LOG.error(code + " 无周K线数据");
				return;
			}
			map.put("items", lstWeekly);
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 月K
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/quote/getMonthlyKline.do")
	public void getMonthlyKline(HttpServletRequest request,
			HttpServletResponse response) {
		// 结果集合
		Map<String, Object> map = null;
		String code = request.getParameter("code");
		String size = request.getParameter("size");
		// 获取该类型下的证券代码
		try {
			ActualMarket am = MarketCache.get(code);
			map = new HashMap<String, Object>();
			map.put("stockData", MathFactory.parseShowAM(am));
			// 月K
			List<CommonLine> lstMonthly = oQuoteSerivce.getMonthlyKLine(code,Integer.parseInt(size));
			if (lstMonthly == null || lstMonthly.isEmpty()) {
				LOG.error(code + " 无月K线数据");
				return;
			}
			map.put("items", lstMonthly);
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 年K
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/quote/getYearlyKline.do")
	public void getYearlyKline(HttpServletRequest request,
			HttpServletResponse response) {
		// 结果集合
		Map<String, Object> map = null;
		String code = request.getParameter("code");
		String size = request.getParameter("size");
		// 获取该类型下的证券代码
		try {
			ActualMarket am = MarketCache.get(code);
			map = new HashMap<String, Object>();
			map.put("stockData", MathFactory.parseShowAM(am));
			// 月K
			List<CommonLine> lstYearly = oQuoteSerivce.getYearlyKLine(code,Integer.parseInt(size));
			if (lstYearly == null || lstYearly.isEmpty()) {
				LOG.error(code + " 无年K线数据");
				return;
			}
			map.put("items", lstYearly);
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 日K
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/daily/getKline.do")
	public void getDailyKline(HttpServletRequest request,
			HttpServletResponse response) {
		// 结果集合
		Map<String, Object> map = null;
		String sCode = request.getParameter("code");
		String sSize = request.getParameter("size");
		// 获取该类型下的证券代码
		try {
			ActualMarket am = MarketCache.get(sCode);
			map = new HashMap<String, Object>();
			map.put("stockData", MathFactory.parseShowAM(am));
			// 月K
			List<DayLine> lstDaily = oQuoteSerivce.getDailyKLine(sCode,Integer.parseInt(sSize));
			if (lstDaily == null || lstDaily.isEmpty()) {
				LOG.error(sCode + " 无日K线数据");
				return;
			}
			map.put("items", lstDaily);
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/quote/get5MinKLine.do")
	public void get5MinKline(HttpServletRequest request,
			HttpServletResponse response) {
		// 结果集合
		Map<String, Object> map = null;
		String sCode = request.getParameter("code");
		String sSize = request.getParameter("size");
		// 获取该类型下的证券代码
		try {
			ActualMarket am = MarketCache.get(sCode);
			map = new HashMap<String, Object>();
			map.put("stockData", MathFactory.parseShowAM(am));
			// 月K
			List<MinLine> lst5Min = oQuoteSerivce.get5MinKLine(sCode,Integer.parseInt(sSize));
			if (lst5Min == null || lst5Min.isEmpty()) {
				LOG.error(sCode + " 无5分K线数据");
				return;
			}
			map.put("items", lst5Min);
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/quote/get10MinKLine.do")
	public void get10MinKline(HttpServletRequest request,
			HttpServletResponse response) {
		// 结果集合
		Map<String, Object> map = null;
		String sCode = request.getParameter("code");
		String sSize = request.getParameter("size");
		// 获取该类型下的证券代码
		try {
			ActualMarket am = MarketCache.get(sCode);
			map = new HashMap<String, Object>();
			map.put("stockData", MathFactory.parseShowAM(am));
			// 月K
			List<MinLine> lst5Min = oQuoteSerivce.get10MinKLine(sCode,Integer.parseInt(sSize));
			if (lst5Min == null || lst5Min.isEmpty()) {
				LOG.error(sCode + " 无日K线数据");
				return;
			}
			map.put("items", lst5Min);
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/quote/get15MinKLine.do")
	public void get15MinKline(HttpServletRequest request,
			HttpServletResponse response) {
		// 结果集合
		Map<String, Object> map = null;
		String sCode = request.getParameter("code");
		String sSzie = request.getParameter("size");
		// 获取该类型下的证券代码
		try {
			ActualMarket am = MarketCache.get(sCode);
			map = new HashMap<String, Object>();
			map.put("stockData", MathFactory.parseShowAM(am));
			// 月K
			List<MinLine> lst15Min = oQuoteSerivce.get15MinKLine(sCode,Integer.parseInt(sSzie));
			if (lst15Min == null || lst15Min.isEmpty()) {
				LOG.error(sCode + " 无日K线数据");
				return;
			}
			map.put("items", lst15Min);
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 30分钟k线图
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/quote/get30MinKLine.do")
	public void get30MinKline(HttpServletRequest request,
			HttpServletResponse response) {
		// 结果集合
		Map<String, Object> map = null;
		String sCode = request.getParameter("code");
		String sSize = request.getParameter("size");
		// 获取该类型下的证券代码
		try {
			ActualMarket am = MarketCache.get(sCode);
			map = new HashMap<String, Object>();
			map.put("stockData", MathFactory.parseShowAM(am));
			// 月K
			List<MinLine> lst30Min = oQuoteSerivce.get30MinKLine(sCode,Integer.parseInt(sSize));
			if (lst30Min == null || lst30Min.isEmpty()) {
				LOG.error(sCode + " 无日K线数据");
				return;
			}
			map.put("items", lst30Min);
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/quote/get60MinKLine.do")
	public void get60MinKline(HttpServletRequest request,
			HttpServletResponse response) {
		// 结果集合
		Map<String, Object> map = null;
		String sCode = request.getParameter("code");
		String sSize = request.getParameter("size");
		// 获取该类型下的证券代码
		try {
			ActualMarket am = MarketCache.get(sCode);
			map = new HashMap<String, Object>();
			map.put("stockData", MathFactory.parseShowAM(am));
			// 月K
			List<MinLine> lst60Min = oQuoteSerivce.get60MinKLine(sCode,Integer.parseInt(sSize));
			if (lst60Min == null || lst60Min.isEmpty()) {
				LOG.error(sCode + " 无60分钟线数据");
				return;
			}
			map.put("items", lst60Min);
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 分时
	 * 
	 * @param request
	 * @param response
	 *//*
	@RequestMapping(value = "/getMinKline.do")
	public void getData(HttpServletRequest request, HttpServletResponse response) {
		// 结果集合
		try {
			String code = request.getParameter("code");
			String sPeriod = request.getParameter("period");
			sPeriod = sPeriod == null ? "1" : sPeriod;
			// 获取该类型下的证券代码
			ActualMarket am = MarketCache.get(code);
			if (am == null) {
				return;
			}
			Map<String, Object> hResult = new HashMap<String, Object>();
			ShowAM sa = MathFactory.parseShowAM(am);
			hResult.put("stockData", sa);

			List<TimeShare> lstTimeShare = TimeShareCache.get(code);
			int iPeriod = Integer.parseInt(sPeriod);
			int iLen = lstTimeShare.size();
			List<TimeShare> lstResult = new ArrayList<TimeShare>();
			for (int i = 0; i < iLen; i++) {
				TimeShare oData = lstTimeShare.get(i);
				oData.setNewPrice((new Random().nextFloat() * 7) + "");
				if (i == iLen - 1) {
					lstResult.add(oData);
					break;
				}
				int iCount = i % iPeriod;
				if (iCount == 0) {
					lstResult.add(oData);
				}
			}
			hResult.put("items", lstResult);
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(
					JSONObject.fromObject(hResult).toString());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}*/

}
