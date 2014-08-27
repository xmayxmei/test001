package bo;

import java.io.Serializable;

/**
 * @Description:
 * 资金查询
 * @Create Date: 2013-12-9
 * @author: hek
 * 
 * 接口号: 332254
 * 返回信息:
 * money_type == 0 current_balance == 100000000.00 enable_balance == 16104082.33  fetch_balance == 16104082.33     frozen_balance == 0.00  
 * unfrozen_balance == 0  post_balance == 0.00     occur_balance == 0.00   fund_balance == 16104082.33
 *
 * @Modified By:
 * @Modified Date:
 * @Version: 1.0
 */
public class MyFundQueryBO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6764345124081807224L;

	/**
	 * 币种类别
	 */
	private String moneyType;
	
	/**
	 * 资金账号
	 */
	private String fundAccount;
	
	/**
	 * 当前余额
	 */
	private String currentBalance;
	
	/**
	 * 可用资金
	 */
	private String enableBalance;

	/**
	 * 可取金额
	 */
	private String fetchBalance;
	
	/**
	 * 待入账利息
	 */
	private String interest;
	
	/**
	 * 资产总值（不含基金市值）
	 */
	private String assetBalance;
	
	/**
	 * 可用现金
	 */
	private String fetchCash;
	
	/**
	 * 资金（= 资产总值 - 证券市值）
	 */
	private String fundBalance;
	
	/**
	 * 证券市值
	 */
	private String marketValue;
	
	/**
	 * 基金市值
	 */
	private String opfundMarketValue;
	/**
	 * 预计利息
	 */
	private String preInterest;
	
	
	
	
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public String getFundAccount() {
		return fundAccount;
	}
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	public String getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}
	public String getEnableBalance() {
		return enableBalance;
	}
	public void setEnableBalance(String enableBalance) {
		this.enableBalance = enableBalance;
	}
	public String getFetchBalance() {
		return fetchBalance;
	}
	public void setFetchBalance(String fetchBalance) {
		this.fetchBalance = fetchBalance;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getAssetBalance() {
		return assetBalance;
	}
	public void setAssetBalance(String assetBalance) {
		this.assetBalance = assetBalance;
	}
    
	public String getFetchCash() {
		return fetchCash;
	}
	public void setFetchCash(String fetchCash) {
		this.fetchCash = fetchCash;
	}
	public String getFundBalance() {
		return fundBalance;
	}
	public void setFundBalance(String fundBalance) {
		this.fundBalance = fundBalance;
	}
	public String getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}
	public String getOpfundMarketValue() {
		return opfundMarketValue;
	}
	public void setOpfundMarketValue(String opfundMarketValue) {
		this.opfundMarketValue = opfundMarketValue;
	}
	public String getPreInterest() {
		return preInterest;
	}
	public void setPreInterest(String preInterest) {
		this.preInterest = preInterest;
	}
	
    





	
	
	
	
}


