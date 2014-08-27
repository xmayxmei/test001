package com.furtune.server.session;


/**
 * <code>ResponseHolder</code>
 *
 * @author Jimmy
 * @since Trading - v.0.0.1(April 12, 2014)
 * 
 */
public class ResponseHolder {
	private String m_sSessionId;
	private String m_sResp;
	private String m_sSeqNum;
	
	/**
	 * @param sSessionID
	 */
	public ResponseHolder(String sSessionId) {
		this(sSessionId, null);
	}
	/**
	 * @param sSessionID
	 * @param sSeqNum
	 */
	public ResponseHolder(String sSessionId, String sSeqNum) {
		this(sSessionId, sSeqNum, null);
	}
	/**
	 * @param sSessionID
	 * @param hReqParams
	 */
	public ResponseHolder(String sSessionId, String sSeqNum, String sResp) {
		m_sResp = sResp;
		m_sSessionId = sSessionId;
		m_sSeqNum = sSeqNum;
	}
	/**
	 * @return
	 */
	public String sessionId() {
		return m_sSessionId;
	}
	/**
	 * @return
	 */
	public String resp() {
		return m_sResp;
	}
	/**
	 * @return
	 */
	public void sessionID(String sSessionId) {
		m_sSessionId = sSessionId;
	}
	/**
	 * @return
	 */
	public void resp(String sResp) {
		m_sResp = sResp;
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
