package com.cfwx.rox.businesssync.market.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfwx.rox.businesssync.market.config.StockConfig;
import com.cfwx.rox.businesssync.market.config.TypeConfig;
import com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao;
import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.entity.StockType;
import com.cfwx.rox.businesssync.market.show.SortObj;
import com.cfwx.rox.businesssync.market.show.ZhiShu;
import com.cfwx.rox.businesssync.market.structure.BaseStructure;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.util.MathFactory;
import com.cfwx.util.U;

/**
 * @author J.C.J
 * 
 *         2013-8-17
 */
@Controller
@RequestMapping("/follow")
@Transactional(propagation = Propagation.REQUIRED)
public class MyFollowController {
	private final static String ENCODE = "utf-8";
	private final static Logger LOG = Logger.getLogger(MyFollowController.class);
	
	@Autowired(required = true)
	private IQuoteCacheDao oCacheDao;
	
	@RequestMapping(value = "/myFollow.do")
	public void myFollow(HttpServletRequest request,
			HttpServletResponse response) {

		List<ActualMarket> amList = new ArrayList<ActualMarket>();
		try {
			int size = request.getParameter("size") == null ? 10 : Integer
					.parseInt(request.getParameter("size"));
			int cp = request.getParameter("cp") == null ? 1 : Integer
					.parseInt(request.getParameter("cp"));
			int order = request.getParameter("order") == null ? 1 : Integer
					.parseInt(request.getParameter("order"));
			String sortField = request.getParameter("sortField") == null||request.getParameter("sortField").equals("") ? StockConfig.defaultSortField
					: request.getParameter("sortField").trim();
			String codeStr = request.getParameter("codeStr");
			int allSize = 0;
			if (codeStr != null) {
				// 获取该关注的证券代码
				String[] codeArr = codeStr.split(",");
				allSize += codeArr.length;
				for (String s : codeArr) {
					if (MarketCache.get(s.trim()) != null) {
						amList.add(MarketCache.get(s.trim()));
					}
				}
			}

			// 结果集合
			Map<String, Object> map = new HashMap<String, Object>();

			Map<String, Object> infoMap = new HashMap<String, Object>();
			infoMap.put("order", order);
			infoMap.put("sortField", sortField);
			infoMap.put("codeStr", codeStr);
			infoMap.put("psize", size);
			infoMap.put("cp", cp);
			map.put("info", infoMap);
			
			allSize = amList.size()/size;
			if(amList.size()%size>0){
				allSize+=1;
			}
			infoMap.put("allSize", allSize);

			if (allSize != 0) {
				List<SortObj> sList = null;
				if ((sortField.equals(""))) {
					// 东方自定义需求指数,如果是不排序的类别，则不进行排序
					sList = getSortList(amList, size, cp);
				} else {
					sList = sortList(amList, sortField, size, cp, order);
				}
				map.put("list", sList);
			} else {
				map.put("list", new ArrayList<SortObj>());
			}

			// 加载字段信息
			map.put("field", BaseStructure.fieldMap);

			// 加载默认指数行情信息
			List<ZhiShu> szList = new ArrayList<ZhiShu>();
			String[] sArr = StockConfig.dzs.split(",");

			ActualMarket am = null;
			for (String s : sArr) {
				am = MarketCache.get(s);
				if (am != null) {
					szList.add(MathFactory.parseZhiShu(am));
				}
			}

			map.put("szList", szList);

			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取所有类别
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getType.do")
	public void getType(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<StockType> list = TypeConfig.getAll();

			List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = null;
			for (StockType st : list) {
				map = new HashMap<String, Object>();
				map.put("name", st.getName());
				map.put("type", st.getType());
				map.put("child", getChild(st.getChildList()));
				resList.add(map);
			}
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(
					JSONArray.fromObject(resList).toString());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * zx：最新价格排序
	 * 
	 * @param list
	 * @param psize
	 * @param currentPage
	 * @param order
	 *            1：降序，0：升序;
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<SortObj> sortList(List<ActualMarket> list, String filed,
			int psize, int currentPage, int order) {

		// 创建针对某个属性的升序比较
		Comparator countCompare = new BeanComparator(filed);
		// 默认的是升序，整一个降序
		if (order == 1)
			countCompare = new ReverseComparator(countCompare);
		// 开始排序
		Collections.sort(list, countCompare);

		return getSortList(list, psize, currentPage);
	}

	/**
	 * 降序列表
	 * 
	 * @param list
	 * @param psize
	 * @param cp
	 * @return
	 */
	private List<SortObj> getSortList(List<ActualMarket> list, int psize, int cp) {

		List<SortObj> resultList = new ArrayList<SortObj>();
		// 处理条数
		for (int i = psize * (cp - 1); i < psize * (cp); i++) {
			if (list.size() > i)
				resultList.add(MathFactory.parseSortObj(list.get(i)));
		}
		return resultList;
	}
	/**
	 * @param list
	 * @return
	 */
	private List<Map<String, String>> getChild(List<StockType> list) {
		List<Map<String, String>> resList = new ArrayList<Map<String, String>>();

		Map<String, String> map = null;

		for (StockType st : list) {
			map = new HashMap<String, String>();
			map.put("name", st.getName());
			map.put("type", st.getType());
			resList.add(map);
		}
		return resList;
	}
	/**
	 * @param code
	 */
	@RequestMapping(value = "/addFollow.do")
	public void addOptionStock(HttpSession oSession, String openId, String code, HttpServletResponse response) {
		FollowRspHolder oHolder = new FollowRspHolder();
		oHolder.setInfo("0");
		try {
			if (U.STR.isEmpty(openId)) {
				LOG.error("OpenId为空, 增加自选股失败 ");
			} else {
				String[] asCode = U.STR.fastSplit(code, ',');
				for (int i = 0; i < asCode.length; i++) {
					String sCode = asCode[i].trim();
					if (U.STR.isEmpty(sCode)) {
						continue;
					}
					oCacheDao.addOptionStock(openId, sCode);
				}
				oHolder.setInfo("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.setCharacterEncoding(ENCODE);
				response.getWriter().println(JSONObject.fromObject(oHolder.getHData()).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param code
	 */
	@RequestMapping(value = "/delFollow.do")
	public void delOptionStock(HttpSession oSession, String openId, String code , HttpServletResponse response) {
		FollowRspHolder oHolder = new FollowRspHolder();
		oHolder.setInfo("0");
		try {
			if (U.STR.isEmpty(openId)) {
				LOG.error("OpenId为空, 删除自选股失败 ");
			} else {
				oCacheDao.delOptionStock(openId, code);
				if (!oCacheDao.isContainOpenIdKey(openId)) {
					oCacheDao.addOptionStock(openId, "");
				}
				oHolder.setInfo("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.setCharacterEncoding(ENCODE);
				response.getWriter().println(JSONObject.fromObject(oHolder.getHData()).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param code
	 */
	@RequestMapping(value = "/getFollow.do")
	public void getOptionStocks(HttpSession oSession, String openId, HttpServletResponse response) {
		FollowRspHolder oHolder = new FollowRspHolder();
		try {
			if (U.STR.isEmpty(openId)) {
				LOG.error("OpenId为空, 获取自选股失败 ");
				oHolder.setInfo("0");
			} else {
				if (oCacheDao.isContainOpenIdKey(openId)) {
					oHolder.setData(oCacheDao.getOptionStocks(openId));
				}
				oHolder.setInfo("1");
			}
		} catch (Exception e) {
			oHolder.setInfo("0");
			e.printStackTrace();
		} finally {
			try {
				response.setCharacterEncoding(ENCODE);
				response.getWriter().println(JSONObject.fromObject(oHolder.getHData()).toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class  FollowRspHolder {
		private String info;
		private String data;
		/**
		 * @return the info
		 */
		public String getInfo() {
			return info;
		}
		/**
		 * @param info the info to set
		 */
		public void setInfo(String info) {
			this.info = info;
		}
		/**
		 * @return the data
		 */
		public String getData() {
			return data;
		}
		/**
		 * @param data the data to set
		 */
		public void setData(String data) {
			this.data = data;
		}
		
		public Map<String, Object> getHData() {
			Map<String, Object> hData = new HashMap<String, Object>();
			hData.put("info", info);
			hData.put("data", data);
			return hData;
		}
	}
}
