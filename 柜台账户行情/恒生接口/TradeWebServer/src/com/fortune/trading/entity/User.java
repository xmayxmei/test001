package com.fortune.trading.entity;

import com.fortune.trading.util.U;

/**
 * <code>User</code>
 *
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1
 */
public class User {
	private String branchNo;
	private String accountContent;
    /** 资金账号 */
    private String fundAccount;
	/** 交易所 */
	private String exchangeType;
	/** 客户号 */
	private String clientId;
	/** 客户名 */
	private String clientName;
	/** 登录方式 */
	private String loginType;
	/** 上次登录日期 */
	private String lastLoginDate;
	/** 上次登录时间 */
	private String lastLoginTime;
	/** 上次登录IP */
	private String lastOpIp;
	/**
	 * @return the branchNo
	 */
	public String getBranchNo() {
		return branchNo;
	}
	/**
	 * @param branchNo the branchNo to set
	 */
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getAccountContent() {
		return accountContent;
	}
	public void setAccountContent(String accountContent) {
		this.accountContent = accountContent;
	}
	public String getFundAccount() {
		return fundAccount;
	}
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	public String getExchangeType() {
		return exchangeType;
	}
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastOpIp() {
		return lastOpIp;
	}
	public void setLastOpIp(String lastOpIp) {
		this.lastOpIp = lastOpIp;
	}
	/**
	 * 
	 * @param sResp
	 */
	public void parseToUser(String sResp, String sLoginType) {
		loginType = sLoginType;
		String[] aData = U.STR.fastSplit(sResp, '|');
		String sData = aData[1];
		String[] aTokens = U.STR.fastSplit(sData, ';');
		accountContent = aTokens[3];
		branchNo = aTokens[4];
		fundAccount = aTokens[5];
		clientId = aTokens[7];
		clientName = aTokens[8];
		exchangeType = aTokens[17];
		lastLoginDate = aTokens[19];
		lastLoginTime = aTokens[20];
		lastOpIp = aTokens[21];
	}
}
