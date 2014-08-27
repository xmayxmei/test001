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

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.config.StockConfig;
import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.structure.BaseStructure;
import com.cfwx.rox.businesssync.market.structure.MarketCache;

/**
 * 
 * @author lixl
 *
 * 2014-6-19
 */
@Controller
@RequestMapping("/detail")
public class DetailController {
	
	private final static String ENCODE = "utf-8";
	
	private final static Logger LOG = Logger.getLogger(DetailController.class);
	
	/**
	 * 查询股票行情详细、并附加相关参数
	 * @param view
	 * @param request
	 * @param code 当前股票代码
	 * @param type 行情列表中的类别
	 * @param order 排序方式
	 * @param sortField 排序字段
	 * @return
	 */
	@RequestMapping("/view.do")
	public ModelAndView view(ModelAndView view,HttpServletRequest request,@RequestParam String code,String codeStr,String type,Integer order,String sortField){
		if(BaseConfig.openTrade.equals("1")){
			view.setViewName("/public/page/detail.jsp");
		}else{
			view.setViewName("/public/page/detail_noTrade.jsp");
		}
		view.addObject("tradeServerHomePath", BaseConfig.getTradeWebServerURL());
		view.addObject("version", BaseConfig.version);
		view.addObject("compressSuffix", BaseConfig.compressSuffix);
		view.addObject("code", code);
		view.addObject("codeStr", codeStr);
		view.addObject("type", type);
		view.addObject("order", order);
		view.addObject("sortField", sortField);
		return view;
	}
	
	/**
	 * 获取当前股票相邻的数据
	 * 排序规则根据行情列表或我的自选股列表一致
	 * @param code 当前股票代码
	 * @param type 行情列表中的类别
	 * @param order 排序方式
	 * @param sortField 排序字段
	 */
	@RequestMapping("/getNeighbor.do")
	public void getNeighbor(HttpServletRequest request,HttpServletResponse response,@RequestParam String code,String codeStr,String type,Integer order,String sortField){
		Map<String,Object> infoMap = new HashMap<String,Object>();
		try{
			List<ActualMarket> amList = new ArrayList<ActualMarket>();
			if (codeStr != null) {
				// 获取该关注的证券代码
				String[] codeArr = codeStr.split(",");
				for (String s : codeArr) {
					if (MarketCache.get(s.trim()) != null) {
						amList.add(MarketCache.get(s.trim()));
					}
				}
				if(StringUtils.isEmpty(sortField)){
					sortField = StockConfig.defaultSortField;
				}
			}else{
				List<String> list = BaseStructure.typeMap.get(type);
				for(String s :list){
					if(MarketCache.get(s)!= null){
						amList.add(MarketCache.get(s));
					}
				}
				if(StringUtils.isEmpty(sortField)&&!type.equals(StockConfig.notSortType)){
					sortField = StockConfig.defaultSortField;
				}
			}
			if(StringUtils.isNotEmpty(sortField)){
				sortList(amList,sortField,order);
			}
			amList = getSearchList(amList,code);
			infoMap.put("list", amList);
		}catch(Exception e){
			infoMap.put("list", new ArrayList<ActualMarket>());
			LOG.error("获取当前股票相邻的数据出错", e);
		}
		
		try {
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(JSONObject.fromObject(infoMap).toString());
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	
	
	/*
	 *  行情数据排序
	 * @param list
	 * @param filed
	 * @param psize
	 * @param currentPage
	 * @param order
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<ActualMarket> sortList(List<ActualMarket> list, String filed,int order) {

		// 创建针对某个属性的升序比较
		Comparator countCompare = new BeanComparator(filed);
		// 默认的是升序，整一个降序
		if (order == 1)
			countCompare = new ReverseComparator(countCompare);
		// 开始排序
		Collections.sort(list, countCompare);

		return list;
	}
	
	/*
	 * 行情出去取出上一个、当前、下一个数据。
	 * @param list
	 * @param code 当前股票代码
	 * @return
	 */
	private List<ActualMarket> getSearchList(List<ActualMarket> list,String code){
		List<ActualMarket> sLst = new ArrayList<ActualMarket>();
		for(int i=0;i<list.size();i++){
			ActualMarket am = list.get(i);
			if(am.getPcode().equals(code)){
				if(i == 0){
					sLst.add(list.get(list.size()-1));
				}else{
					sLst.add(list.get(i-1));
				}
				sLst.add(am);
				if(i == list.size()-1){
					sLst.add(list.get(0));
				}else{
					sLst.add(list.get(i+1));
				}
			}
		}
		return sLst;
	}
}
