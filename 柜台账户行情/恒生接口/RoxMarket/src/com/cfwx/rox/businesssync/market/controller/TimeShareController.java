package com.cfwx.rox.businesssync.market.controller;

import java.util.HashMap;
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

import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.show.ShowAM;
import com.cfwx.rox.businesssync.market.show.TimeShare;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.structure.TimeShareCache;
import com.cfwx.rox.businesssync.market.util.MathFactory;

/**
 * @author J.C.J
 * 
 *         2013-8-17
 */
@Controller
@RequestMapping("/ts")
@Transactional(propagation = Propagation.REQUIRED)
public class TimeShareController {

	private final static String ENCODE = "utf-8";
	private final static Logger LOG = Logger.getLogger(TimeShareController.class);
	
	@RequestMapping(value = "/getData.do")
	public void getData(HttpServletRequest request,HttpServletResponse response) {
		
		//结果集合
		Map<String,Object> map = null;
		try {
		String code = request.getParameter("code");
		//获取该类型下的证券代码
		ActualMarket am = MarketCache.get(code);
		
		map = new HashMap<String,Object>();
		ShowAM sa =  MathFactory.parseShowAM(am);
		map.put("stockData", sa);
		
		List<TimeShare> list = TimeShareCache.get(code);
		map.put("items", list);
		
//		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
//		for(TimeShare ts : list){
//			System.out.println(sdf.format(new Date(Long.valueOf(ts.getTime())))+","+ts.getVolume()+","+ts.getNewPrice());
//		}
		
		response.setCharacterEncoding(ENCODE);
		response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
}
