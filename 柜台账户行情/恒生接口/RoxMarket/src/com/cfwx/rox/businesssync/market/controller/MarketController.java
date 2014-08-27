package com.cfwx.rox.businesssync.market.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfwx.rox.businesssync.market.config.StockConfig;
import com.cfwx.rox.businesssync.market.config.TypeConfig;
import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.entity.StockType;
import com.cfwx.rox.businesssync.market.show.SortObj;
import com.cfwx.rox.businesssync.market.show.ZhiShu;
import com.cfwx.rox.businesssync.market.structure.BaseStructure;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.util.MathFactory;

/**
 * @author J.C.J
 * 
 * 2013-8-17
 */
@Controller
@RequestMapping("/sync")
@Transactional(propagation = Propagation.REQUIRED)
public class MarketController {

	private final static String ENCODE = "utf-8";
	private final static Logger LOG = Logger.getLogger(MarketController.class);
	
	@RequestMapping(value = "/market.do")
	public void market(HttpServletRequest request,HttpServletResponse response) {
		
//		long st = System.currentTimeMillis();
		//获取某类别下的所有行情信息
		List<ActualMarket> amList= new ArrayList<ActualMarket>();
		try {
		
		int size = request.getParameter("size")==null?10:Integer.parseInt(request.getParameter("size"));
		int cp = request.getParameter("cp")==null?1:Integer.parseInt(request.getParameter("cp"));
		String type =request.getParameter("type")==null?"-1":request.getParameter("type");
		int order = request.getParameter("order")==null?1:Integer.parseInt(request.getParameter("order"));
		String sortField = request.getParameter("sortField")==null?"":request.getParameter("sortField").trim();
		
		
		//获取该类型下的证券代码
		List<String> list = BaseStructure.typeMap.get(type);
		for(String s :list){
			if(MarketCache.get(s)!= null){
				amList.add(MarketCache.get(s));
			}
		}
		
		//结果集合
		Map<String,Object> map = new HashMap<String,Object>();
		
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("psize", size);
		infoMap.put("cp", cp);
		infoMap.put("order", order);
		infoMap.put("sortField", sortField);
		infoMap.put("type", type);
		int allSize = amList.size()/size;
		if(amList.size()%size>0){
			allSize+=1;
		}
		infoMap.put("allSize", allSize);
		map.put("info", infoMap);
		
		List<SortObj> sList = null;
		
		if((sortField.equals("")) && type.equals(StockConfig.notSortType)){
			//东方自定义需求指数,如果是不排序的类别，则不进行排序
			 sList = getSortList(amList,size,cp);
		}else{
			 if(sortField.equals(""))
				 sortField = StockConfig.defaultSortField;
			 
			 sList = sortList(amList,sortField,size,cp,order);
		}
		map.put("list", sList);
		
		//加载字段信息
		map.put("field", BaseStructure.fieldMap);
		
		//加载默认指数行情信息
		List<ZhiShu> szList = new ArrayList<ZhiShu>();
		String[] sArr= StockConfig.dzs.split(",");
		
		ActualMarket am = null;
		for(String s :sArr){
			am = MarketCache.get(s);
			if(am!= null){
				szList.add(MathFactory.parseZhiShu(am));
			}
		}
		
		map.put("szList", szList);
		
		response.setCharacterEncoding(ENCODE);
		response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
//		long et = System.currentTimeMillis();
//		LOG.info("排序："+amList.size()+"条,排序耗时为"+(et-st));
	}
	
	/**
	 * 获取所有类别
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getType.do")
	public void getType(HttpServletRequest request,HttpServletResponse response) {
		try {
			List<StockType> list = TypeConfig.getAll();
			
			List<Map<String,Object>> resList = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = null;
			for(StockType st : list){
				map = new  HashMap<String,Object>();
				map.put("name", st.getName());
				map.put("type", st.getType());
				map.put("child", getChild(st.getChildList()));
				resList.add(map);
			}
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(JSONArray.fromObject(resList).toString());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	/**
	 * zx：最新价格排序
	 * @param list
	 * @param psize
	 * @param currentPage
	 * @param order 1：降序，0：升序;
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<SortObj> sortList(List<ActualMarket> list,String filed,int psize ,int currentPage,int order){
		
		//创建针对某个属性的升序比较
        Comparator countCompare = new BeanComparator(filed);
       
        //默认的是升序，整一个降序
        if(order==1)
            countCompare=new ReverseComparator(countCompare);
        //开始排序
        Collections.sort(list,countCompare);
        
        return getSortList(list,psize,currentPage);
	}
	
	/**
	 * 获取分页
	 * @param list
	 * @param psize
	 * @param cp
	 * @return
	 */
	private List<SortObj> getSortList(List<ActualMarket> list,int psize,int cp){
		
		List<SortObj> resultList = new ArrayList<SortObj>();
		//处理条数
		for(int i=psize*(cp-1);i<psize*(cp);i++){
			if(list.size()>i)
			resultList.add(MathFactory.parseSortObj(list.get(i)));
		}
		return resultList;
	}
	
	private List<Map<String,String>> getChild(List<StockType> list){
		List<Map<String,String>> resList = new ArrayList<Map<String,String>>();
		
		Map<String,String> map = null;
		
		for(StockType st : list){
			map = new HashMap<String,String>();
			map.put("name", st.getName());
			map.put("type", st.getType());
			resList.add(map);
		}
		return resList;
	}
}
