package com.fortune.quote.session;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.fortune.quote.ws.QuoteWsServlet.Session;
import com.fortune.trading.util.Constants;
import com.fortune.trading.util.U;

/**
 * <code>SessionManager</code> 
 * 
 * @author Colin, Jimmy
 * 
 */
public class SessionManager {
	
	private final Logger L = Logger.getLogger(getClass());
	
	private static final long PERIOD = 6000L;
	
    private final Lock lock = new ReentrantLock();
	 
	private Map<String, Set<Session>> hRegisterTable = new ConcurrentHashMap<String, Set<Session>>();
	
	private Timer oTimer;
	
	public SessionManager() {
		oTimer = new Timer();
		oTimer.scheduleAtFixedRate(new UpdateQuoteTask(), PERIOD, PERIOD);
	}
	/**
	 * @param sCode
	 * @param oSession
	 */
	public void registerCode(String sCode, Session oSession) {
		boolean isNeedToSend = false;
		try {
			lock.lock();
			Set<Session> vSessions = hRegisterTable.get(sCode);
			if (vSessions == null) {
				vSessions = new CopyOnWriteArraySet<Session>();
				hRegisterTable.put(sCode, vSessions);
			}
			if (!vSessions.contains(oSession)) {
				vSessions.add(oSession);
				isNeedToSend = true;
			}
		} finally {
			lock.unlock();
		}
		if (isNeedToSend) {
			sendToOne(sCode, oSession);
		}
	}
	/**
	 * @param sCode
	 * @param oSession
	 */
	public void unregisterCode(String sCode, Session oSession) {
		try {
			lock.lock();
			Set<Session> vSessions = hRegisterTable.get(sCode);
			if (vSessions != null) {
				if (vSessions.contains(oSession)) {
					vSessions.remove(oSession);
				}
			}
		} finally {
			lock.unlock();
		}
	}
	/**
	 * @param oSession
	 */
	public void removeSession(Session oSession) {
		L.debug("removeSession");
		try {
			lock.lock();
			Set<String> oSetCodes = hRegisterTable.keySet();
			Set<String> oCopyCodes = new HashSet<String>();
			if (oSetCodes == null) {
				return ;
			}
			oCopyCodes.addAll(oSetCodes);
			for (String sCode : oCopyCodes) {
				Set<Session> vSessions = hRegisterTable.get(sCode);
				if (vSessions == null) {
					hRegisterTable.remove(sCode);
					continue;
				}
				vSessions.remove(oSession);
				if (vSessions.size() == 0) {
					hRegisterTable.remove(sCode);
				}
			}
		} finally {
			lock.unlock();
		}
	}
	
	public void stopUpdateQUote()  {
		oTimer.cancel();
	}
	/**
	 * 更新行情数据
	 */
	class UpdateQuoteTask extends TimerTask{
		@Override
		public void run() {
			try {
				sendToAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 */
	public void sendToAll() throws Exception{
		Set<String> oSetCodes = hRegisterTable.keySet();
		Set<String> oCopyCodes = new HashSet<String>();
		if (oSetCodes == null) {
			return ;
		}
		oCopyCodes.addAll(oSetCodes);
		Map<String, String> hParams = new HashMap<String, String>();
		for (String sCode : oCopyCodes) {
			hParams.put("code", sCode);
			String sRsp = U.HTTP.post(Constants.quoteURLForWsRequest, hParams, "UTF-8");
			L.debug("requestCode:" + sCode + ", responseContent:" + sRsp);
			hParams.clear();
			if (sRsp == null) {
				continue;
			}
			Set<Session> vSessions = hRegisterTable.get(sCode);
			if (vSessions == null) {
				continue;
			}
			for(Session oSession : vSessions) {
				oSession.sendMessage(sRsp);
			}
		}
	}
	/**
	 * @param sCode
	 * @param oSession
	 */
	public void sendToOne(String sCode, Session oSession) {
		Map<String, String> hParams = new HashMap<String, String>();
		hParams.put("code", sCode);
		String sRsp = U.HTTP.post(Constants.quoteURLForWsRequest, hParams, "UTF-8");
		oSession.sendMessage(sRsp);
	}
	
}
