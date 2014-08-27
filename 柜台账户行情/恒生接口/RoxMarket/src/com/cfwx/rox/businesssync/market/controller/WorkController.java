package com.cfwx.rox.businesssync.market.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cfwx.rox.businesssync.market.service.IWorkService;
import com.cfwx.rox.businesssync.market.show.TimeShare;
import com.cfwx.rox.businesssync.market.structure.TimeShareCache;

/**
 * @author J.C.J
 * 
 * 2013-8-17
 */
@Controller
@RequestMapping("/work")
@Transactional(propagation = Propagation.REQUIRED)
public class WorkController {

	private final static Logger LOG = Logger.getLogger(WorkController.class);
	
//	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	@Autowired
	IWorkService workservice;
	
	/**
	 * 手动开盘作业
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/workOpen.do")
	public void workOpen(HttpServletRequest request,HttpServletResponse response) {
		try {
			workservice.openWorkByMan();
			response.setCharacterEncoding("utf-8");
			LOG.info("手动开盘作业完成....");
			response.getWriter().println("手动开盘作业完成....");
		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
		}catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	@RequestMapping(value = "/workClose.do")
	public void workClose(HttpServletRequest request,HttpServletResponse response) {
		try {
			workservice.closeWork();
			response.setCharacterEncoding("utf-8");
			LOG.info("手动收盘作业完成....");
			response.getWriter().println("手动收盘作业完成....");
		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
		}catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	
	@RequestMapping(value = "/closeAndWrite.do")
	public void workCloseAndWriteDay(HttpServletRequest request,HttpServletResponse response) {
		try {
			workservice.closeWork();
//			GhostUtils.writeAllDayLine();
			response.setCharacterEncoding("utf-8");
			LOG.info("手动收盘作业完成....");
			response.getWriter().println("手动收盘作业完成....");
		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
		}catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
	}
	/**
	 * 保存日线文件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/writeKLine.do")
	public void writeKLine(HttpServletRequest request,HttpServletResponse response) {
		try {
			workservice.writeDayLine();
			response.setCharacterEncoding("utf-8");
			LOG.info("日线数据写入文件成功....");
			response.getWriter().println("日线数据写入文件成功....");
		} catch (IOException e) {
			LOG.error("日线数据写入文件失败",e);
		}catch (Exception e) {
			LOG.error("日线数据写入文件失败",e);
		}
	}
	
	/**
	 * 更新分时数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateTimeShare.do")
	public void updateTimeShare(HttpServletRequest request,HttpServletResponse response) {
		try {
			Iterator<String> it = TimeShareCache.iterator();
			String key = "";
			while(it.hasNext()){
				key = it.next();
				LinkedList<TimeShare> list= TimeShareCache.get(key);
				if(list.size()>240){
					for(int i=239;i>=0;i--){
						list.remove(i);
					}
				}
			}
//			GhostUtils.write(TimeShareCache.ghostUrl, TimeShareCache.getData());
			response.setCharacterEncoding("utf-8");
			LOG.info("分时数据重写成功....");
			response.getWriter().println("分时数据重写成功....");
		} catch (IOException e) {
			LOG.error("分时数据重写失败",e);
		}catch (Exception e) {
			LOG.error("分时数据重写失败",e);
		}
	}
	
	/**
	 * 加载分时数据
	 * @param request
	 * @param response
	 * @param code 代码:sh000001
	 * @param exeClose :是否添加当天的K线数据
	 */
	@RequestMapping(value = "/loadOne.do")
	public void loadOne(HttpServletRequest request,HttpServletResponse response) {
		/*try {
//			String code1 = "sz399001";
//			String code2 = "sz399005";
			String code3 = "sz399006";
			
//			String c1 = "sz399659";
//			String c2 = "sz399634";
			String c3 = "sz399635";
			
			LinkedList<DayLine> list1 = KLineDailyCache.get(code3);
			LinkedList<DayLine> list2 = KLineDailyCache.get(c3);
			
			DayLine dl1 = null;
			DayLine dl2 = null;
			for(int i=0;i<list1.size();i++){
				dl1 = list1.get(i);
				dl2 = list2.get(i);
				dl1.setVolume(dl2.getVolume());
			}
			
//			GhostUtils.writeOneDayLine(code3);
			
			response.setCharacterEncoding("utf-8");
			LOG.info("分时数据重写成功....");
			response.getWriter().println("分时数据重写成功....");
		} catch (IOException e) {
			LOG.error("分时数据重写失败",e);
		}catch (Exception e) {
			LOG.error("分时数据重写失败",e);
		}*/
	}
	
	
//	private void writeMemory(String code){
//		//写入K线数据
//				Iterator<String> it=MarketCache.iterator();
//				
//				ActualMarket am = null;
//				Date date =  new Date();
//				String dateStr = date.getTime()+"";
//				
//				String todayDate = sdf.format(date);
//				
//					am = MarketCache.get(code);
//					if(am.getJk()==0){
//						//今开价为0的，即表示当天没有开盘。没有开盘的就不进行收盘
//						return ;
//					}
//					try {
//						if(KLineDailyCache.get(code)!=null){
//							//判断当前最后一项是否为当天，是则替换，不是则添加
//							if(todayDate.equals(sdf.format(new Date(Long.valueOf(KLineDailyCache.get(code).getLast().getTime()))))){
//								KLineDailyCache.replaceMarket(am.getPcode(), MathFactory.parseKLineDaily(am, dateStr));
//							}else{
//								KLineDailyCache.addDay(am.getPcode(), MathFactory.parseKLineDaily(am, dateStr));
//							}
//						}else{
//							KLineDailyCache.addDay(am.getPcode(), MathFactory.parseKLineDaily(am, dateStr));
//						}
//					} catch (Exception e) {
//						LOG.error(code+":"+e.getMessage(),e);
//					}
//	}
}
