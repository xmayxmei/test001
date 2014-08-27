package com.furtune.server.session;

import java.util.Map;
/**
 * <code>RequestHolder</code>
 *
 * @author Jimmy
 * @since Trading - v.0.0.1(April 12, 2014)
 * 
 */
public class RequestHolder {
	private String m_sSessionID;
	private String m_sSeqNum;
	private Map<String, String> m_hReqParams;
	/**
	 * @param sSessionID
	 * @param hReqParams
	 */
	public RequestHolder(String sSessionID, String sSeqNum, Map<String, String> hReqParams) {
		m_hReqParams = hReqParams;
		m_sSessionID = sSessionID;
		m_sSeqNum = sSeqNum;
	}
	/**
	 * @return
	 */
	public String sessionId() {
		return m_sSessionID;
	}
	/**
	 * @return
	 */
	public Map<String, String> reqParams() {
		return m_hReqParams;
	}
	/**
	 * @param sSeqNum
	 */
	public void seqNum(String sSeqNum) {
		m_sSeqNum = sSeqNum;
	}
	/**
	 * @return
	 */
	public String seqNum() {
		return m_sSeqNum;
	}
}
