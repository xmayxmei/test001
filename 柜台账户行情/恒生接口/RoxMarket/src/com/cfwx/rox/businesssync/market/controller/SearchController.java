package com.cfwx.rox.businesssync.market.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.show.ResultInfo;
import com.cfwx.rox.businesssync.market.structure.BaseStructure;
import com.cfwx.rox.businesssync.market.util.MathFactory;

/**
 * @author J.C.J
 * 
 *         2013-8-17
 */
@Controller
@RequestMapping("/search")
@Transactional(propagation = Propagation.REQUIRED)
public class SearchController {

	private final static String ENCODE = "utf-8";
	
	//数字代码
	private final static String CODEREGEX = "^[0-9]{6}$";
	//数字
	private final static String NUMEREGEX = "^[0-9]*$";
	//拼音
	private final static String PYREGEX= "^[a-z|A-Z]*$";
	//汉字
//	private final static String HZREGEX = "^[\u4e00-\u9fa5]*$";
	
	private final static Logger LOG = Logger.getLogger(SearchController.class);
	
	@RequestMapping("/searchPage.do")
	public ModelAndView searchPage(ModelAndView view,HttpServletRequest request){
		view.addObject("version" , BaseConfig.version);
		view.addObject("compressSuffix" , BaseConfig.compressSuffix);
		view.setViewName("/public/page/search.jsp");
		return view;
	}
	@RequestMapping(value = "/search.do")
	public void getData(HttpServletRequest request,HttpServletResponse response) {
		//结果集合
		Map<String,Object> map = null;
		String key = request.getParameter("key");
		int size = request.getParameter("size")==null?10:Integer.valueOf(request.getParameter("size"));
		int cp = request.getParameter("cp")==null?1:Integer.valueOf(request.getParameter("cp"));
		try {
		map = new HashMap<String,Object>();
		
		Map<String,String> infoMap = new HashMap<String,String>();
		infoMap.put("key", key);
		infoMap.put("size", size+"");
		infoMap.put("cp", cp+"");
		
		map.put("info", infoMap);
		
		if(key.matches(CODEREGEX)){
			//6位数字代码
			List<ResultInfo> list = BaseStructure.codeMap.get(key);
			if(list!=null){
				infoMap.put("allSize", MathFactory.getAllSize(list.size(), size));
				map.put("items", getPageSize(list,size,cp));
			}
			else
				map.put("items", null);
		}
		else if(key.matches(NUMEREGEX) && key.length()<6){
			//数字
			Iterator<String> it = BaseStructure.codeMap.keySet().iterator();
			
			String codeKey = "";
			List<ResultInfo> list = new ArrayList<ResultInfo>();
			while(it.hasNext()){
				codeKey = it.next();
				
		    	if(codeKey.matches("^"+key+".*")){
		    		list.addAll(BaseStructure.codeMap.get(codeKey));
		    	}
			}
			if(list.size()!=0){
				infoMap.put("allSize", MathFactory.getAllSize(list.size(), size));
				map.put("items", getPageSize(list,size,cp));
			}
			else
				map.put("items", null);	
		}
		else if(key.matches(PYREGEX)){
			key = key.toLowerCase();
			//拼音
			Iterator<String> it = BaseStructure.pyMap.keySet().iterator();
			
			String codeKey = "";
			List<ResultInfo> list = new ArrayList<ResultInfo>();
			while(it.hasNext()){
				codeKey = it.next();
				
		    	if(codeKey.matches("^"+key+".*")){
		    		list.addAll(BaseStructure.pyMap.get(codeKey));
		    	}
			}
			if(list.size()!=0){
				infoMap.put("allSize", MathFactory.getAllSize(list.size(), size));
				map.put("items",getPageSize(list,size,cp));
			}
			else
				map.put("items", null);	
		}else{
			//其他
			map.put("items", null);
		}
		response.setCharacterEncoding(ENCODE);
		response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	private List<ResultInfo> getPageSize(List<ResultInfo> list,int size,int cp){
		
		List<ResultInfo> resultList = new ArrayList<ResultInfo>(); 
		
		for(int i = (cp-1)*size;i<cp*size;i++){
			if(i>=list.size())
				break;
			else
			resultList.add(list.get(i));
		}
		return resultList;
	} 
}
