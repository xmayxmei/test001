/**
 * 
 */
package com.cfwx.rox.businesssync.market.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.Range;
import org.jfree.data.time.Minute;
import org.jstockchart.JStockChartFactory;
import org.jstockchart.area.PriceArea;
import org.jstockchart.area.TimeseriesArea;
import org.jstockchart.area.VolumeArea;
import org.jstockchart.axis.TickAlignment;
import org.jstockchart.axis.logic.CentralValueAxis;
import org.jstockchart.axis.logic.LogicDateAxis;
import org.jstockchart.axis.logic.LogicNumberAxis;
import org.jstockchart.dataset.TimeseriesDataset;
import org.jstockchart.model.TimeseriesItem;
import org.jstockchart.util.DateUtils;
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
import com.cfwx.rox.businesssync.market.util.StockDataUtils;
import com.objectplanet.image.PngEncoder;

/**
 * @author W.W.B
 *
 * 2014-4-30
 */
@Controller
@RequestMapping("/chart")
@Transactional(propagation = Propagation.REQUIRED)
public class ChartController {
	
	private final static String ENCODE = "utf-8";
	private final static Logger LOG = Logger.getLogger(ChartController.class);
	
	@RequestMapping(value = "/getTimeChart.do")
	public void getChart(HttpServletRequest request,HttpServletResponse response) {
		//结果集合
		//Map<String,Object> map = null;
		try {
		String code = request.getParameter("code");
		double width=360;
		double height=160;
		if(null!=request.getParameter("w")){
			width=Double.parseDouble(request.getParameter("w"));
		}
		if(null!=request.getParameter("h")){
			height=Double.parseDouble(request.getParameter("h"));
		}
		//获取该类型下的证券代码
		ActualMarket am=StockDataUtils.getAM(code);
		if(null!=am){
			ShowAM sa =  MathFactory.parseShowAM(am);
			JFreeChart chart=null;
			long start=	System.currentTimeMillis();
			List<TimeShare> list = TimeShareCache.get(sa.getPcode());
			if(list.size()>1){
				response.setCharacterEncoding(ENCODE);
				response.setContentType("text/html");
				response.setHeader("Pragrma", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setContentType("image/png");
				OutputStream outputStream = response.getOutputStream();
				List<TimeseriesItem> data = new ArrayList<TimeseriesItem>();
				for(TimeShare timeShare:list){
					TimeseriesItem dateItem=new TimeseriesItem(new Date(Long.parseLong(timeShare.getTime())),Double.parseDouble(timeShare.getNewPrice()),
							Double.parseDouble(timeShare.getVolume()));
					data.add(dateItem);
				}
				TimeseriesItem startItem=data.get(0);
				Calendar cal=Calendar.getInstance();//使用日历类
				cal.setTime(startItem.getTime());
				Date startTime = DateUtils.createDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH), 9, 30, 0);
				Date endTime = DateUtils.createDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH), 15, 00, 0);
			    SegmentedTimeline timeline = new SegmentedTimeline(SegmentedTimeline.MINUTE_SEGMENT_SIZE, 1351, 89);
				timeline.setStartTime(SegmentedTimeline.firstMondayAfter1900() + 780* SegmentedTimeline.MINUTE_SEGMENT_SIZE);
		
				// Creates timeseries data set.
				TimeseriesDataset dataset = new TimeseriesDataset(Minute.class, 1,timeline, true);
				dataset.addDataItems(data);
		
				// Creates logic price axis.
				CentralValueAxis logicPriceAxis = new CentralValueAxis(new Double(sa.getJk()), new Range(dataset.getMinPrice().doubleValue(),dataset.getMaxPrice().doubleValue()), 9,new DecimalFormat(".00"));
				PriceArea priceArea = new PriceArea(logicPriceAxis);
				priceArea.setMarkCentralValue(false);
				// Creates logic volume axis.
				LogicNumberAxis logicVolumeAxis = new LogicNumberAxis(new Range(dataset.getMinVolume().doubleValue(), dataset.getMaxVolume().doubleValue()), 3, new DecimalFormat("0"));
				VolumeArea volumeArea = new VolumeArea(logicVolumeAxis);
				TimeseriesArea timeseriesArea = new TimeseriesArea(priceArea,volumeArea, createlogicDateAxis(startItem.getTime()));
				timeseriesArea.setStartDate(startTime);
				timeseriesArea.setEndDate(endTime);
				chart = JStockChartFactory.createTimeseriesChart("", dataset, timeline, timeseriesArea,false);
				chart.getTitle().setVisible(false);
				writeBufferedImageAsPNG(outputStream,chart,width,height);
				outputStream.flush();
				outputStream.close();
			}
		
			long end=System.currentTimeMillis()-start;
			System.out.println("userTime:"+end);
		}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	@RequestMapping(value = "/getData.do")
	public void getData(HttpServletRequest request,HttpServletResponse response) {
		//结果集合
		Map<String,Object> map = null;
		try {
		String code = request.getParameter("code");
		ActualMarket am=StockDataUtils.getAM(code);
		if(null!=am){
			map = new HashMap<String,Object>();
			ShowAM sa =  MathFactory.parseShowAM(am);
			map.put("name", sa.getMc());
			map.put("code", sa.getDm());
			map.put("lastPrice", sa.getZx());
			map.put("open", sa.getJk());
			map.put("preClose", sa.getZs());
			map.put("high", sa.getZg());
			map.put("zd", sa.getZde());
			map.put("zdf", sa.getZdf());
			map.put("low", sa.getZd());
			map.put("volume", sa.getZl());
			map.put("pcode", sa.getPcode());
			
		}
		response.setCharacterEncoding(ENCODE);
		response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	@RequestMapping(value = "/getFData.do")
	public void getFData(HttpServletRequest request,HttpServletResponse response) {
		//结果集合
		Map<String,Object> map = null;
		try {
		String code = request.getParameter("code");
		ActualMarket am=StockDataUtils.getAM(code);
		map = new HashMap<String,Object>();
		if(am!=null){
			ShowAM sa =  MathFactory.parseShowAM(am);
			map.put("status", "1");
			map.put("mc", sa.getMc());
			map.put("code", sa.getDm());
			map.put("pcode", sa.getPcode());
			map.put("zs", sa.getZs());
			map.put("lastPrice", sa.getZx());
			map.put("open", sa.getJk());
			map.put("market", sa.getMarket());
			map.put("b1", sa.getB1());
			map.put("b2", sa.getB2());
			map.put("b3", sa.getB3());
			map.put("b4", sa.getB4());
			map.put("b5", sa.getB5());
			
			map.put("b1Price", sa.getB1Price());
			map.put("b2Price", sa.getB2Price());
			map.put("b3Price", sa.getB3Price());
			map.put("b4Price", sa.getB4Price());
			map.put("b5Price", sa.getB5Price());
			
			
			map.put("s1", sa.getS1());
			map.put("s2", sa.getS2());
			map.put("s3", sa.getS3());
			map.put("s4", sa.getS4());
			map.put("s5", sa.getS5());
			
			
			map.put("s1Price", sa.getS1Price());
			map.put("s2Price", sa.getS2Price());
			map.put("s3Price", sa.getS3Price());
			map.put("s4Price", sa.getS4Price());
			map.put("s5Price", sa.getS5Price());
			
		}
		response.setCharacterEncoding(ENCODE);
		response.getWriter().println(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	@RequestMapping(value = "/getWFData.do")
	public void getWFData(HttpServletRequest request,HttpServletResponse response) {
		try {
			//结果集合
			Map<String,Object> map = null;
			String code = request.getParameter("code");
			ActualMarket am=StockDataUtils.getAM(code);
			map = new HashMap<String,Object>();
			if(null!=am){
				map.put("status", "1");
				ShowAM  sam = MathFactory.parseShowAM(am);
				map.put("stockData", sam);
				response.setCharacterEncoding(ENCODE);
				response.getWriter().println(JSONObject.fromObject(map).toString());
			}
		}catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
		
	@SuppressWarnings("unused")
	public  void writeBufferedImageAsPNG(OutputStream outputStream, JFreeChart chart,double chartWidth,double chartHeight) {
		long start = System.currentTimeMillis();
		BufferedImage bufferedImage = null;
	    ChartRenderingInfo chartInfo = new ChartRenderingInfo(new StandardEntityCollection());
		if (null != chart)
			bufferedImage = chart.createBufferedImage((int)chartWidth, (int)chartHeight,chartWidth,chartHeight,chartInfo);
		// encode the BufferedImage
		if (null != bufferedImage) {
			start = System.currentTimeMillis();
			PngEncoder encoder = new PngEncoder();
			try {
				encoder.encode(bufferedImage, outputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
		}
	}
	/**
	 * 获取所有股票当前价
	 * Create By J.C.J
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getAllData.do")
	public void getAllData(HttpServletRequest request,HttpServletResponse response) {
		//结果集合
		Map<String,String> resultMap = new HashMap<String,String>();
		try {
			
		Map<String,ActualMarket> allMap = MarketCache.getData();
		
		Iterator<String> iter = allMap.keySet().iterator();
		String key = "";
		ActualMarket	am = null;
		ShowAM sa = null;
		
		NumberFormat tempnf = null;
		
		while(iter.hasNext()){
			key = iter.next();
			am = MarketCache.get(key);
			
			if(am != null && am.getBigType() == 1){
				
				tempnf = NumberFormat.getInstance();
				tempnf.setMaximumFractionDigits(am.getPointnum());
				tempnf.setMinimumFractionDigits(am.getPointnum());
				tempnf.setGroupingUsed(false);
				
				sa = new ShowAM();
				if(am.getJk()!=0){
					sa.setZx(tempnf.format(am.getZx()/am.getEnlarge()));
				}else{
					//如果没有开盘，设置最高，最低为0
					sa.setZx("");
				}
				resultMap.put(am.getDm(), sa.getZx()+"");
			}
		}
//		System.out.println("--------" + resultMap.size());
		response.setCharacterEncoding(ENCODE);
		response.getWriter().println(JSONObject.fromObject(resultMap).toString());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}

	// Specifies date axis ticks.
	private static LogicDateAxis createlogicDateAxis(Date baseDate) {
		LogicDateAxis logicDateAxis = new LogicDateAxis(baseDate,new SimpleDateFormat("HH:mm"));
		logicDateAxis.addDateTick("09:30", TickAlignment.MID);
		logicDateAxis.addDateTick("10:00");
		logicDateAxis.addDateTick("10:30");
		logicDateAxis.addDateTick("11:00");
		logicDateAxis.addDateTick("11:30", TickAlignment.END);
		logicDateAxis.addDateTick("13:00", TickAlignment.START);
		logicDateAxis.addDateTick("13:30");
		logicDateAxis.addDateTick("14:00");
		logicDateAxis.addDateTick("14:30", TickAlignment.END);
		logicDateAxis.addDateTick("15:00", TickAlignment.MID);
		return logicDateAxis;
	}

}
