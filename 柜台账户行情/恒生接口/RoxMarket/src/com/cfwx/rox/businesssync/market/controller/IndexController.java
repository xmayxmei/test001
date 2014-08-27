/**
 * 
 */
package com.cfwx.rox.businesssync.market.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cfwx.rox.businesssync.market.config.BaseConfig;

/**
 * @author lixl
 *
 * 2014-6-18
 */
@Controller
public class IndexController {
	
	
	/**
	 * 行情首页跳转,并附加相关参数
	 * @param view
	 * @param request
	 * @return
	 */
	@RequestMapping("index.do")
	public ModelAndView index(ModelAndView view, HttpServletRequest request, String openId){
		view.addObject("tradeServerHomePath", BaseConfig.getTradeWebServerURL());
		view.addObject("version", BaseConfig.version);
		view.addObject("openId", openId);
		view.addObject("compressSuffix", BaseConfig.compressSuffix);
		if(BaseConfig.openTrade.equals("1")){
			view.setViewName("/index.jsp");
		}else{
			view.setViewName("/index_noTrade.jsp");
		}
		return view;
	}
}
