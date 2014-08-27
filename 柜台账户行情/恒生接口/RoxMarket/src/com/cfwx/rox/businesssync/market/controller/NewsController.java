/**
 * 
 */
package com.cfwx.rox.businesssync.market.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.util.U;

/**
 * 资讯控制器
 * @author W.W.B
 *
 * 2014-4-30
 */

@RequestMapping("/news")
@Transactional(propagation = Propagation.REQUIRED)
@Controller
public class NewsController {
	private final static Logger LOG = Logger.getLogger(MarketController.class);
	private final static String ENCODE = "utf-8";
	
	/**
	 * 资讯首页跳转、并附加相关参数
	 * @param view
	 * @param request
	 * @return
	 */
	@RequestMapping("newslist.do")
	public ModelAndView newslist(ModelAndView view,HttpServletRequest request){
		view.addObject("tradeServerHomePath" , BaseConfig.getTradeWebServerURL());
		view.addObject("version" , BaseConfig.version);
		view.addObject("compressSuffix" , BaseConfig.compressSuffix);
//		view.setViewName("/public/page/newslist_noTrade.jsp");
		if(BaseConfig.openTrade.equals("1")){
			view.setViewName("/public/page/newslist.jsp");
		}else{
			view.setViewName("/public/page/newslist_noTrade.jsp");
		}
		return view;
	}
	/**
	 * 资讯首页跳转、并附加相关参数
	 * @param view
	 * @param request
	 * @return
	 */
	@RequestMapping("fs.do")
	public ModelAndView fs(ModelAndView view,HttpServletRequest request){
		String code=request.getParameter("code");
		view.addObject("code", code);
		view.addObject("version" , BaseConfig.version);
		view.addObject("compressSuffix" , BaseConfig.compressSuffix);
		view.setViewName("/public/page/f10.jsp");
		return view;
	}
	/**
	 * 资讯首页跳转、并附加相关参数
	 * @param view
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping("getFSData.do")
	public void getFsData(@RequestParam Map<String, String> hParams,HttpServletResponse response){
		Map<String,String> map = new HashMap<String,String>();
		String data=U.HTTP.get(BaseConfig.zsServerUrl, hParams, ENCODE);//(BaseConfig.zsServerUrl, hParams, ENCODE);
		if (data == null) {
			return ;
		}
		data = data.substring(1).replaceAll("\r\n", "<br>").trim();
		map.put("fsData", data);
//		LOG.info("Data Info:"+data);
		try {
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(JSONObject.fromObject(map).toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 资讯首页跳转、并附加相关参数
	 * @param view
	 * @param request
	 * @return 
	 * @return
	 */
	@RequestMapping("getZXData.do")
	public void getZXData(@RequestParam Map<String, String> hParams,HttpServletResponse response){
		String data = U.HTTP.get(BaseConfig.zsServerUrl, hParams, ENCODE);//(BaseConfig.zsServerUrl, hParams, ENCODE);
		if (data != null) {
			String urlTemp=BaseConfig.zsServerUrl+"\\?";
//			LOG.info("data Info:"+data);
			data = data.replaceAll(urlTemp,"");
		}
		//map.put("fsData", data);
//		LOG.info("Data Info:"+data);
		try {
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(data);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage(),e);
		}
	}
	@RequestMapping("getNewsData.do")
	public void getNewsData(@RequestParam Map<String, String> hParams,HttpServletResponse response){
		String data = U.HTTP.post(BaseConfig.zsServerUrlTest, hParams, ENCODE);//(BaseConfig.zsServerUrl, hParams, ENCODE);
		if (data != null) {
			String urlTemp=BaseConfig.zsServerUrlTest+"\\?";
//			LOG.info("data Info:"+data);
//			LOG.info("urlTemp Info:"+urlTemp);
			data = data.replaceAll(urlTemp,"");
		}
		//map.put("fsData", data);
//		LOG.info("Data Info:"+data);
		try {
			response.setCharacterEncoding(ENCODE);
			response.getWriter().println(data);
		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
		}
	}
}
