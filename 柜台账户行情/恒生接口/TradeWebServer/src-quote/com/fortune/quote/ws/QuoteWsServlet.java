package com.fortune.quote.ws;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.log4j.Logger;

import com.fortune.quote.session.SessionManager;
import com.fortune.trading.util.U;

/**
 * <code>QuoteWsServlet</code> 是基于Tomcat7的websocket服务Servlet。
 * <p>
 * 下面是相关的逻辑:<br>
 * 1) 每个新加入的客户端，根据注册的股票代码，分组保存在内存<br>
 *   也就是 注册相同股票代码的Session保存在一起<br>
 * 2) 每个6秒钟向行情服务器请求数据，然后分发给对应的客户端<br>
 * 3) 当用户关闭连接时，从内存中清除掉改Session.<br>
 * 
 * @author Colin, Jimmy
 * @since Trading v0.0.1(May 19,2014)
 */
@SuppressWarnings("deprecation")
public class QuoteWsServlet extends WebSocketServlet {
	private static final long serialVersionUID = 1L;
	
	private final Logger L = Logger.getLogger(getClass());
	
	private SessionManager oSessionMgr;

	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
		String code = request.getParameter("code");
		return new Session(code);
	}
	
	@Override
	public void init() throws ServletException {
		oSessionMgr = new SessionManager();
		super.init();
	}
	
	public class Session extends MessageInbound{
		/** 主要是处理URL连接带过来的code */
		private String[] asInitRegisterCode;
		
		private volatile boolean isClose;
		
		public Session(String... asCode) {
			asInitRegisterCode = asCode;
		}
		
		public void sendMessage(String sMsg) {
			if (isClose || U.STR.isEmpty(sMsg)) {
				return ;
			}
			WsOutbound out = this.getWsOutbound();
			try {
				L.debug("Outgoing message:" + sMsg);
				out.writeTextMessage(CharBuffer.wrap(sMsg));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		protected void onBinaryMessage(ByteBuffer buf) throws IOException {
			
		}
		@Override
		protected void onTextMessage(CharBuffer buf) throws IOException {
			String sParams = buf.toString();
			if (sParams.startsWith("register=")) {
				String sCodes = sParams.substring("register=".length());
				processRegister(sCodes);
			} else if (sParams.startsWith("unregister=")) {
				String sCodes = sParams.substring("unregister=".length());
				processUnregister(sCodes);
			}
		}
		@Override
		protected void onOpen(WsOutbound outbound) {
			L.debug("onOpen");
			if (asInitRegisterCode != null) {
				for (String sCode : asInitRegisterCode) {
					oSessionMgr.registerCode(sCode, this);
				}
			}
			isClose = false;
		}
		/* (non-Javadoc)
		 * @see org.apache.catalina.websocket.StreamInbound#onClose(int)
		 */
		@Override
		protected void onClose(int status) {
			L.debug("onClose");
			isClose = true;
			super.onClose(status);
			oSessionMgr.removeSession(this);
		}
		/**
		 * @param sRegisterCodes
		 */
		private void processRegister(String sRegisterCodes) {
			String[] asCode = U.STR.fastSplit(sRegisterCodes, ',');
			for (String sCode : asCode) {
				oSessionMgr.registerCode(sCode, this);
			}
		}
		/**
		 * 处理
		 * @param sUnregisterCodes
		 */
		private void processUnregister(String sUnregisterCodes) {
			String[] asCode = U.STR.fastSplit(sUnregisterCodes, ',');
			for (String sCode : asCode) {
				oSessionMgr.unregisterCode(sCode, this);
			}
		}
	}
}
