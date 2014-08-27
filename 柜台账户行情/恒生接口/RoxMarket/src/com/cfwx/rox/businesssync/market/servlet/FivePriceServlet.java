/**
 * 
 */
package com.cfwx.rox.businesssync.market.servlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Iterator;
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
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.util.MathFactory;
import com.cfwx.rox.businesssync.market.util.StockDataUtils;
import com.cfwx.rox.businesssync.market.util.TimeUtils;

/**
 * @author W.W.B
 *
 * 2014-5-6
 */
public class FivePriceServlet extends WebSocketServlet {

	private final static Logger LOG = Logger.getLogger(FivePriceServlet.class);

	private final Set<TimeShareMI> connections = new CopyOnWriteArraySet<TimeShareMI>();

	private ScheduledExecutorService executor = null;
	
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
		Iterator<TimeShareMI> it = connections.iterator();
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
		
		public TimeShareMI(String code) {
			this.code =code;
		}

		@Override
		protected void onOpen(WsOutbound outbound) {
			connections.add(this);
//			LOG.info("链接已打开...");
			//if(TimeUtils.canSend())
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
//				LOG.info("链接已断开...");
		}
		
		private void send() {
			try {
				//结果集合
				Map<String,Object> map = new HashMap<String,Object>();
				ActualMarket am=StockDataUtils.getAM(code);
				if(null!=am){
					map.put("status", "1");
					ShowAM  sam = MathFactory.parseShowAM(am);
					map.put("stockData", sam);
					CharBuffer buffer = CharBuffer.wrap(JSONObject.fromObject(map).toString());
					this.getWsOutbound().writeTextMessage(buffer);
				}
			} catch (IOException ignore) {
			LOG.error(ignore.getMessage(),ignore);
			}catch (Exception e) {
				LOG.error(e.getMessage(),e);
			}
		}
	}
}
