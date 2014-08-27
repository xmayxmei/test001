package com.furtune.server.session;

import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.furtune.server.util.U;
import com.service.ITradeService;

/**
 * <code>ClientSessionManager</code> 处理所有来自客户端的请求。
 * <p>
 * 这里采用了生产者与消费者的设计模式，这个模式非常合适用于NIO应用中。<br>
 * 在这里采用了10个“消费者”来“消费”所有的请求。
 * 
 * @author Colin, Jimmy
 * @since Trading - v.0.0.1(April 12, 2014)
 * 
 */
public class ClientSessionManager {
	private final Logger L = Logger.getLogger(getClass());
	/** 每个与服务器端连接成功的客户端都保存在这里 */
	private final Map<String, ClientSession> m_hSessions;
	/** 所有的消息保存在这里，然后给“消费者”消费，相当于一个仓库 */
	private LinkedBlockingQueue<RequestHolder> m_oReqQueue = new LinkedBlockingQueue<RequestHolder>(1024 * 10);
	/** 回话标识键 */
	public static final AttributeKey<String> SESSIONID_KEY = AttributeKey.valueOf("_sessionID_");
	/** 请求监听器 */
	private RequestMonitor m_monitor;
	/** **/
	private volatile boolean m_bRunning = true;
	/** 消息的“消费者”的个数**/
	private final int m_iWorkerCount = 10;
	/** 消费的线程数, 在这里一个消费线程代表一个消费者 */
	private Thread[] m_oWorks;
	/** */
	private final ITradeService oService;
	/**
	 */
	public ClientSessionManager(ITradeService tradeService) {
		m_hSessions = new ConcurrentHashMap<String, ClientSession>();
		m_monitor = new RequestMonitor();
		m_oWorks = new Thread[m_iWorkerCount];
		oService = tradeService;
	}
	/**
	 * @param sSessionID
	 * @param oClientSession
	 */
	public void registerSession(String sSessionID, ClientSession oClientSession) {
		m_hSessions.put(sSessionID, oClientSession);
	}
	/**
	 * @param sSessionID
	 */
	public void unregisterSession(String sSessionID) {
		m_hSessions.remove(sSessionID);
	}
	/**
	 * @param sSessionID
	 */
	public ClientSession getClientSession(String sSessionID) {
		return m_hSessions.get(sSessionID);
	}
	/**
	 * 
	 */
	public Map<String, ClientSession> getAllClientSessions() {
		return m_hSessions;
	}
	/**
	 * @param oReq
	 */
	public void processTradeRequest(RequestHolder oReq) {
		ClientSession oSession = m_hSessions.get(oReq.sessionId());
		if (oSession == null) {
			return ;
		}
		// Update heartBeat
		if ("heartBeat".equals(oReq.reqParams().get("actionName"))) {
			oSession.updatedHeartBeat();
			return ;
		}
		try {
			m_oReqQueue.put(oReq);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param oResp
	 */
	public void processTradeResponse(ResponseHolder oResp) {
		String sSessionID = oResp.sessionId();
		ClientSession oClientSession = m_hSessions.get(sSessionID);
		if (oClientSession != null) {
			oClientSession.response(oResp);
		}
	}
	/**
	 * Start request monitor.And all the workers are going to work.
	 */
	public void startRequestMonitor() {
		L.info("Starting Request Monitor.");
		for (int i = 0; i < m_iWorkerCount; i++) {
			m_oWorks[i] = new Thread(m_monitor);
			m_oWorks[i].setName("Request Monitor Thread[" + i + "]");
			m_oWorks[i].start();
		}
	}
	/**
	 * Stop request monitor. And all the workers are going to stop working.
	 */
	public void stopRequestMonitor() {
		L.info("Stopping Request Monitor.");
		m_bRunning = false;
		for (int i = 0; i < m_iWorkerCount; i++) {
			try {
				m_oWorks[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 */
	private class RequestMonitor implements Runnable{
		@Override
		public void run() {
			while (m_bRunning) {
				RequestHolder oReqHolder;
				try {
					oReqHolder = m_oReqQueue.take();
					ResponseHolder oRespHolder = new ResponseHolder(oReqHolder.sessionId(), oReqHolder.seqNum());
					Map<String, String> hReqParams = oReqHolder.reqParams();
					String sAction = hReqParams.get("actionName");
					String sRespContent = "";
					if(U.STR.isEmpty(sAction)) {
						sRespContent = "R01|缺少actionName字段";
					} else {
						if ("loginAuthen".equals(sAction)) {
							sRespContent = oService.loginHandler(hReqParams);
						} else if ("holdStock".equals(sAction)) {
							sRespContent = oService.holdStockHandler(hReqParams);
						} else if ("queryFunds".equals(sAction)) {
							sRespContent = oService.queryZJHandler(hReqParams);
						} else if ("bankToZQ".equals(sAction)) {
							sRespContent = oService.bankToZJHandler(hReqParams);
						} else if ("cancelOrder".equals(sAction)) {
							sRespContent = oService.cancleOrderHandler(hReqParams);
						} else if ("queryCancelOrder".equals(sAction)) {
							sRespContent = oService.queryCancleOrderHandler(hReqParams);
						} else if ("checkFPwd".equals(sAction)) {
							sRespContent = oService.checkFPwdHandler(hReqParams);
						} else if ("checkTPwdByGDH".equals(sAction)) {
							sRespContent = oService.checkTPwdByGDHHandler(hReqParams);
						} else if ("checkTPwd".equals(sAction)) {
							sRespContent = oService.checkTPwdHandler(hReqParams);
						} else if ("queryGDH".equals(sAction)) {
							sRespContent = oService.gdhQuery(hReqParams);
						} else if ("historyTransaction".equals(sAction)) {
							sRespContent = oService.hisCJHandler(hReqParams);
						} else if ("historyOrder".equals(sAction)) {
							sRespContent = oService.hisWtHandler(hReqParams);
						} else if ("modiFPwd".equals(sAction)) {
							sRespContent = oService.modiFPwdHandler(hReqParams);
						} else if ("modiTPwd".equals(sAction)) {
							sRespContent = oService.modiTPwdHandler(hReqParams);
						} else if ("placeOrder".equals(sAction)) {
							sRespContent = oService.placeOrderHandler(hReqParams);
						} else if ("queryBankDm".equals(sAction)) {
							sRespContent = oService.queryBankDmHandler(hReqParams);
						} else if ("queryFunds".equals(sAction)) {
							sRespContent = oService.queryZJHandler(hReqParams);
						} else if ("queryFundsMX".equals(sAction)) {
							sRespContent = oService.queryZJMXHandler(hReqParams);
						} else if ("todayTransaction".equals(sAction)) {
							sRespContent = oService.todayCJHandler(hReqParams);
						} else if ("todayOrder".equals(sAction)) {
							sRespContent = oService.todayWtHandler(hReqParams);
						} else if ("userInfo".equals(sAction)) {
							sRespContent = oService.userInfHandler(hReqParams);
						} else if ("fundsNZ".equals(sAction)) {
							sRespContent = oService.zjNZBiz(hReqParams);
						} else if ("ZqToBank".equals(sAction)) {
							sRespContent = oService.zjToBank(hReqParams);
						} else if ("bankZqQuery".equals(sAction)) {
							sRespContent = oService.zjToBankQuery(hReqParams);
						} else if ("bankZqReQuery".equals(sAction)) {
							sRespContent = oService.zjToBankReQuery(hReqParams);
						} else if ("queryPeihao".equals(sAction)) {
							sRespContent = oService.queryPeihao(hReqParams);	
						} else if ("queryBallot".equals(sAction)) {
							sRespContent = oService.queryBallot(hReqParams);	
						}
					}
					oRespHolder.resp(sRespContent);
					processTradeResponse(oRespHolder);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * @return
	 */
	public static final String generateSessionID() {
		return System.currentTimeMillis() +  UUID.randomUUID().toString();
	}
}
