package com.cfwx.rox.businesssync.market.servlet;

import java.io.IOException;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.log4j.Logger;

import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.show.ShowAM;
import com.cfwx.rox.businesssync.market.show.TimeShare;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.structure.TimeShareCache;
import com.cfwx.rox.businesssync.market.util.MathFactory;
import com.cfwx.rox.businesssync.market.util.TimeUtils;

/**
 * @author J.C.J
 * 
 *         2013-9-10
 */
@SuppressWarnings("serial")
public class TimeShareServlet extends WebSocketServlet {

	private final static Logger LOG = Logger.getLogger(WebSocketServlet.class);

	private final Set<TimeShareMI> fiveConnections = new CopyOnWriteArraySet<TimeShareMI>();

	private ScheduledExecutorService executor = null;
	
	private final static SimpleDateFormat HSSDF = new SimpleDateFormat("HHmm");
	
	private final static SimpleDateFormat DATESDF = new SimpleDateFormat("yyyyMMdd");
	
	private final static SimpleDateFormat TIMESDF = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static final String TIME = "150000";
	/* 切记 NumberFormat不是线程安全的  多线程情况下确保线程安全*/
	public static NumberFormat nf0;
	
	static {
		nf0 = NumberFormat.getInstance();
		nf0.setRoundingMode(RoundingMode.HALF_UP);
		nf0.setMaximumFractionDigits(0);
		nf0.setGroupingUsed(false);  
	}
	
	@Override
    public void init() throws ServletException {
        super.init();
        
        executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if(TimeUtils.canSend())
					send();
			}
		}, 1, BaseConfig.sendInterval, TimeUnit.SECONDS);
    }
	
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {

		String code = request.getParameter("code");
		return new TimeShareMI(code);
	}
	
	private void send(){
		Iterator<TimeShareMI> it = fiveConnections.iterator();
		TimeShareMI ts = null;
		while(it.hasNext()){
			ts = it.next();
			ts.send();
		}
	}
	
	@Override
    public void destroy() {
        super.destroy();
        if (executor != null) {
        	executor.shutdown();
        }
    }

	private class TimeShareMI extends MessageInbound {
		
		private String code = "";
		
		private TimeShareService service = null;
		
		private TimeShare ts = null;
		
		public TimeShareMI(String code) {
			this.code =code;
			service = new TimeShareService();
		}

		@Override
		protected void onOpen(WsOutbound outbound) {
			fiveConnections.add(this);
//			LOG.info("链接已打开...");
			if(TimeUtils.canSend())
				send();
		}

		@Override
		protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
			
		}

		@Override
		protected void onTextMessage(CharBuffer arg0) throws IOException {
			
		}

		@Override
		protected void onClose(int status) {
			fiveConnections.remove(this);
//			LOG.info("链接已断开...");
		}
		
		private void send() {
			try {
					//结果集合
					Map<String,Object> map = new HashMap<String,Object>();
					
					ActualMarket am = MarketCache.get(code);
					ShowAM  sam = MathFactory.parseShowAM(am);
					map.put("stockData", sam);
					
					List<TimeShare> templist = new ArrayList<TimeShare>();
					
					ts = service.getTimeShare(code);
					if(ts != null)
						templist.add(ts);
					
					map.put("items",templist);
					
//					System.out.println(HSSDF.format(new Date(Long.valueOf(ts.getTime()))));
					
					CharBuffer buffer = CharBuffer.wrap(JSONObject.fromObject(map).toString());
					this.getWsOutbound().writeTextMessage(buffer);
//					LOG.info("推送...."+code);
			} catch (IOException ignore) {
//				LOG.error(ignore.getMessage(),ignore);
			}catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
		}
	}
	
	public static class TimeShareService{
		
		public TimeShare getTimeShare(String code) throws Exception{
			
			TimeShare tempTS = null;
			
			Date date = new Date();
			long now = Long.valueOf(HSSDF.format(date));
			String time = String.valueOf(date.getTime());
			ActualMarket am = null;
			
			try {
				if(now <931){
					//9点31分以前只写一个点，即9点30分钟以前的数据
					am = MarketCache.get(code);
					tempTS = MathFactory.parseTimeShare(am, time);
				}else if(now >1501){  
					//15点以后
					String after = DATESDF.format(date)+TIME;
					after = TIMESDF.parse(after).getTime()+"";
					
					//把当前所有股票行情信息写入内存。
					List<TimeShare> list = null;
					TimeShare beforeTS = null;
					am = MarketCache.get(code);
					list = TimeShareCache.get(code);
								
					tempTS = MathFactory.parseTimeShare(am, after);
								
					if(list!= null && list.size()>1){
						beforeTS = list.get(list.size()-2);
						if(beforeTS != null){
							//计算分钟增量
							tempTS.setVolume(nf0.format(tempTS.getZl()-beforeTS.getZl()>0?tempTS.getZl()-beforeTS.getZl():0D));
						}
					}
				}else{
					//交易时间内
					//第二个点开始(9点31分)开始，到15.00点结束，数据是在当前分钟结束后才进行写入的，写入时减去一分钟的毫秒数
					am = MarketCache.get(code);
					TimeShare beforeTS = TimeShareCache.getLast(code);
					tempTS = MathFactory.parseTimeShare(am, time);
								
					if(beforeTS != null){
						//计算分钟增量
						tempTS.setVolume(nf0.format(tempTS.getZl()-beforeTS.getZl()>0?tempTS.getZl()-beforeTS.getZl():0D));
					}
				}
			} catch (Exception e) {
				LOG.error(e.getMessage());
				throw new Exception(e);
			}
			return tempTS;
		} 
	}

}