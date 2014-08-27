package com.furtune.server.monitor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.furtune.server.session.ClientSession;
import com.furtune.server.session.ClientSessionManager;

/**
 * <code>HeartBeatMonitor</code> 监听每个连接的心跳间隔时间。 每隔<code>m_iFrequency</code>毫秒检查一遍.
 *
 * @author Colin, Jimmy
 * @Since Tebon Trading v0.0.1 (May 16, 2014)
 *
 */
public class HeartBeatMonitor implements Runnable{
	private final Logger L = Logger.getLogger(getClass());
	
	private ClientSessionManager m_oSessionMgr;
	private boolean m_running = false;
	private int m_iFrequency = 600000;
	
	public HeartBeatMonitor(ClientSessionManager oSessionMgr) {
		m_oSessionMgr = oSessionMgr;
	}
	
	@Override
	public void run() {
		while (m_running) {
			try {
				Thread.sleep(m_iFrequency);
				L.debug("Start check heartbeat.");
				long lCurrentTime = System.currentTimeMillis();
				Map<String, ClientSession>  hClients = m_oSessionMgr.getAllClientSessions();
				Set<String> oSet = hClients.keySet();
				if (oSet.isEmpty()) {
					continue;
				}
				// Fixed concurrency exception
				Set<String> oSetDuplicate = new HashSet<String>();
				oSetDuplicate.addAll(oSet);
				for (String sKey : oSetDuplicate) {
					ClientSession oSession = hClients.get(sKey);
					if (lCurrentTime - oSession.getLastHeartBeat() > m_iFrequency) {
						oSession.channel().close();
						hClients.remove(sKey);
						L.debug("Closed invalid session: " + sKey);
					}
				}
				L.debug("End check heartbeat.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 *  Start monitor the heartBeat information.
	 */
	public void start() {
		if (m_running) {
			return ;
		}
		m_running = true;
		Thread oTh = new Thread(this);
		oTh.setDaemon(true);
		oTh.setName("HeartBeatMonitor - Thread");
		oTh.start();
	}
}
