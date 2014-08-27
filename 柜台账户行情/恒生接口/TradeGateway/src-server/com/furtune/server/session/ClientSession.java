package com.furtune.server.session;

import io.netty.channel.Channel;

/**
 * <code>ClientSession</code> 每个一个连接对用一个ClientSession。
 *
 * @author Colin, Jimmy
 * @since Trading - v.0.0.1(May 16, 2014)
 * 
 */
public class ClientSession {
	private final String m_sSessionId;
	private Channel m_ch;
	private volatile long m_lLastHeartBeat;
	/**
	 * @param sSessionID
	 * @param ch
	 */
	public ClientSession(String sSessionId, Channel ch) {
		m_ch = ch;
		m_lLastHeartBeat = System.currentTimeMillis();
		m_sSessionId = sSessionId;
	}
	/**
	 * 把消息写进Channel，并且发送给客户端
	 * @param oResp
	 */
	public void response(ResponseHolder oResp) {
		m_ch.writeAndFlush(oResp);
	}
	/**
	 * @return
	 */
	public Channel channel() {
		return m_ch;
	}
	/**
	 * 
	 */
	public void updatedHeartBeat() {
		m_lLastHeartBeat = System.currentTimeMillis();
	}
	/**
	 * @return
	 */
	public long getLastHeartBeat() {
		return m_lLastHeartBeat;
	}
	/**
	 * @return
	 */
	public String sessionId() {
		return m_sSessionId;
	}
}
