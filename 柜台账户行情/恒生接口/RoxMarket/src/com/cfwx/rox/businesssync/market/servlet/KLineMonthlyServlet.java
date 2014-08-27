package com.cfwx.rox.businesssync.market.servlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
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
import com.cfwx.rox.businesssync.market.service.IQuoteService;
import com.cfwx.rox.businesssync.market.show.CommonLine;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.util.MathFactory;
import com.cfwx.rox.businesssync.market.util.SpringContextUtils;
import com.cfwx.rox.businesssync.market.util.TimeUtils;

/**
 * 
 * <code>KLineMonthServlet </code>
 * <p>
 * 	月k图WebSocket请求响应及定时推送实现
 * </p>
 * 
 * @author lixl
 * @since 2014-6-18
 */
@SuppressWarnings({ "serial", "deprecation" })
public class KLineMonthlyServlet extends WebSocketServlet {

	private final static Logger LOG = Logger.getLogger(TimeShareServlet.class);

	private final Set<TimeShareMI> connections = new CopyOnWriteArraySet<TimeShareMI>();

	private ScheduledExecutorService executor = null;
	
	private IQuoteService oQuoteSerivce = SpringContextUtils.getBean(IQuoteService.class);


	@Override
	public void init() throws ServletException {
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if (TimeUtils.canSend())
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

	private void send() {
		Iterator<TimeShareMI> it = connections.iterator();
		TimeShareMI ts = null;
		while (it.hasNext()) {
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

		public TimeShareMI(String code) {
			this.code = code;
		}

		@Override
		protected void onOpen(WsOutbound outbound) {
			connections.add(this);
			if (TimeUtils.canSend())
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
			connections.remove(this);
//			LOG.info("链接已断开...");
		}

		private void send() {
			try {
				// 结果集合
				Map<String, Object> map = new HashMap<String, Object>();

				// 获取该类型下的证券代码
				ActualMarket am = MarketCache.get(code);
//				List<CommonLine> templist = new ArrayList<CommonLine>();
				// 今开价为0的，即表示当天没有开盘。没有开盘的就不进行开盘作业
				if (am.getJk() != 0) {
					map.put("stockData", MathFactory.parseShowAM(am));
					List<CommonLine> yearlist = oQuoteSerivce.getMonthlyKLine(code,1);
//					templist.add(MathFactory.parseCommonKLine(am,yearlist.get(0)));
					map.put("items", yearlist);//只取最后一条数据
					CharBuffer buffer = CharBuffer.wrap(JSONObject.fromObject(
							map).toString());
					this.getWsOutbound().writeTextMessage(buffer);
//					LOG.info("推送...." + code);
				} else {
					// 不推送
					connections.remove(this);
//					LOG.info("链接已断开...");
				}
			} catch (IOException ignore) {
				LOG.error(ignore.getMessage(), ignore);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
}