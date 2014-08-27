package bo;

import java.io.Serializable;

/**
 * @Description:
 * 我的持仓
 * 功能号: 403
 */
public class MyStockQueryBO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8857541520734182952L;

	/**
	 * 资金账号
	 */
	private String fundAccount;
	
	/**
	 * 交易类别
	 */
	private String exchangeType;
	
	/**
	 * 证券账号
	 */
	private String stockAccount;
	
	/**
	 * 证券代码
	 */
	private String stockCode;
	
	/**
	 * 证券类别
	 */
	private String stockType;
	
	/**
	 * 证券名称
	 */
	private String stockName;
	
	/**
	 * 持有数量
	 */
	private String holdAmount;
	
	/**
	 * 当前数量
	 */
	private String currentAmount;
	
	/**
	 * 可用数量
	 */
	private String enableAmount;	
	
	/**
	 * 今开仓买入持仓量
	 */
	private String  realBuyAmount;
	
	/**
	 * 今开仓卖出持仓量
	 */
	private String realSellAmount;
	
	/**
	 * 买入未交收持仓量
	 */
	private String uncomeBuyAmount;
	
	/**
	 * 卖出未交收持仓量
	 */
	private String uncomeSellAmount;
	
	/**
	 * 今开仓委托卖出量
	 */
	private String entrustSellAmount;
	
	/**
	 * 最新价
	 */
	private String lastPrice;
	/**
	 * 成本价
	 */
	private String costPrice ;
	/**
	 * 保本价
	 */
	private String keepCostPrice;
	/**
	 * 盈亏金额
	 */
	private String incomeBalance;
	/**
	 * 手标志
	 */
	private String handFlag;
	/**
	 * 证券市值
	 */
	private String marketValue;
	/**
	 * 买入均价
	 */
	private String avBuyPrice;
	
	/**
	 * 实现盈亏
	 */
	private String avIncomeBalance;
	
	/**
	 * 持仓成本（累积买+当日买-累积卖-当日卖）
	 */
	private String costBalance;
	
	/**
	 * 定位串
	 */
	private String positionStr;
	/**
	 * 退市标志
	 */
	private String delistFlag;
	
	/**
	 * 退市日期
	 */
	private String delistDate;
	
	
	/**
	 * 证券面值
	 */
	private String parValue;
	
	/**
	 * 席位编号
	 */
	private String seatNo;
	
	
	/**
	 * @return the fundAccount
	 */
	public String getFundAccount() {
		return fundAccount;
	}
	/**
	 * @param fundAccount the fundAccount to set
	 */
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	/**
	 * @return the exchangeType
	 */
	public String getExchangeType() {
		return exchangeType;
	}
	/**
	 * @param exchangeType the exchangeType to set
	 */
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}
	/**
	 * @return the stockAccount
	 */
	public String getStockAccount() {
		return stockAccount;
	}
	/**
	 * @param stockAccount the stockAccount to set
	 */
	public void setStockAccount(String stockAccount) {
		this.stockAccount = stockAccount;
	}
	/**
	 * @return the stockCode
	 */
	public String getStockCode() {
		return stockCode;
	}
	/**
	 * @param stockCode the stockCode to set
	 */
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	/**
	 * @return the stockType
	 */
	public String getStockType() {
		return stockType;
	}
	/**
	 * @param stockType the stockType to set
	 */
	public void setStockType(String stockType) {
		this.stockType = stockType;
	}
	/**
	 * @return the stockName
	 */
	public String getStockName() {
		return stockName;
	}
	/**
	 * @param stockName the stockName to set
	 */
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	/**
	 * @return the holdAmount
	 */
	public String getHoldAmount() {
		return holdAmount;
	}
	/**
	 * @param holdAmount the holdAmount to set
	 */
	public void setHoldAmount(String holdAmount) {
		this.holdAmount = holdAmount;
	}
	/**
	 * @return the currentAmount
	 */
	public String getCurrentAmount() {
		return currentAmount;
	}
	/**
	 * @param currentAmount the currentAmount to set
	 */
	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}
	/**
	 * @return the enableAmount
	 */
	public String getEnableAmount() {
		return enableAmount;
	}
	/**
	 * @param enableAmount the enableAmount to set
	 */
	public void setEnableAmount(String enableAmount) {
		this.enableAmount = enableAmount;
	}
	/**
	 * @return the realBuyAmount
	 */
	public String getRealBuyAmount() {
		return realBuyAmount;
	}
	/**
	 * @param realBuyAmount the realBuyAmount to set
	 */
	public void setRealBuyAmount(String realBuyAmount) {
		this.realBuyAmount = realBuyAmount;
	}
	/**
	 * @return the realSellAmount
	 */
	public String getRealSellAmount() {
		return realSellAmount;
	}
	/**
	 * @param realSellAmount the realSellAmount to set
	 */
	public void setRealSellAmount(String realSellAmount) {
		this.realSellAmount = realSellAmount;
	}
	/**
	 * @return the uncomeBuyAmount
	 */
	public String getUncomeBuyAmount() {
		return uncomeBuyAmount;
	}
	/**
	 * @param uncomeBuyAmount the uncomeBuyAmount to set
	 */
	public void setUncomeBuyAmount(String uncomeBuyAmount) {
		this.uncomeBuyAmount = uncomeBuyAmount;
	}
	/**
	 * @return the entrustSellAmount
	 */
	public String getEntrustSellAmount() {
		return entrustSellAmount;
	}
	/**
	 * @param entrustSellAmount the entrustSellAmount to set
	 */
	public void setEntrustSellAmount(String entrustSellAmount) {
		this.entrustSellAmount = entrustSellAmount;
	}
	/**
	 * @return the lastPrice
	 */
	public String getLastPrice() {
		return lastPrice;
	}
	/**
	 * @param lastPrice the lastPrice to set
	 */
	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}
	/**
	 * @return the costPrice
	 */
	public String getCostPrice() {
		return costPrice;
	}
	/**
	 * @param costPrice the costPrice to set
	 */
	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}
	/**
	 * @return the keepCostPrice
	 */
	public String getKeepCostPrice() {
		return keepCostPrice;
	}
	/**
	 * @param keepCostPrice the keepCostPrice to set
	 */
	public void setKeepCostPrice(String keepCostPrice) {
		this.keepCostPrice = keepCostPrice;
	}
	/**
	 * @return the incomeBalance
	 */
	public String getIncomeBalance() {
		return incomeBalance;
	}
	/**
	 * @param incomeBalance the incomeBalance to set
	 */
	public void setIncomeBalance(String incomeBalance) {
		this.incomeBalance = incomeBalance;
	}
	/**
	 * @return the handFlag
	 */
	public String getHandFlag() {
		return handFlag;
	}
	/**
	 * @param handFlag the handFlag to set
	 */
	public void setHandFlag(String handFlag) {
		this.handFlag = handFlag;
	}
	/**
	 * @return the marketValue
	 */
	public String getMarketValue() {
		return marketValue;
	}
	/**
	 * @param marketValue the marketValue to set
	 */
	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}
	/**
	 * @return the avBuyPrice
	 */
	public String getAvBuyPrice() {
		return avBuyPrice;
	}
	/**
	 * @param avBuyPrice the avBuyPrice to set
	 */
	public void setAvBuyPrice(String avBuyPrice) {
		this.avBuyPrice = avBuyPrice;
	}
	/**
	 * @return the costBalance
	 */
	public String getCostBalance() {
		return costBalance;
	}
	/**
	 * @param costBalance the costBalance to set
	 */
	public void setCostBalance(String costBalance) {
		this.costBalance = costBalance;
	}
	/**
	 * @return the avIncomeBalance
	 */
	public String getAvIncomeBalance() {
		return avIncomeBalance;
	}
	/**
	 * @param avIncomeBalance the avIncomeBalance to set
	 */
	public void setAvIncomeBalance(String avIncomeBalance) {
		this.avIncomeBalance = avIncomeBalance;
	}
	/**
	 * @return the positionStr
	 */
	public String getPositionStr() {
		return positionStr;
	}
	/**
	 * @param positionStr the positionStr to set
	 */
	public void setPositionStr(String positionStr) {
		this.positionStr = positionStr;
	}
	/**
	 * @return the delistFlag
	 */
	public String getDelistFlag() {
		return delistFlag;
	}
	/**
	 * @param delistFlag the delistFlag to set
	 */
	public void setDelistFlag(String delistFlag) {
		this.delistFlag = delistFlag;
	}
	/**
	 * @return the delistDate
	 */
	public String getDelistDate() {
		return delistDate;
	}
	/**
	 * @param delistDate the delistDate to set
	 */
	public void setDelistDate(String delistDate) {
		this.delistDate = delistDate;
	}
	/**
	 * @return the parValue
	 */
	public String getParValue() {
		return parValue;
	}
	/**
	 * @param parValue the parValue to set
	 */
	public void setParValue(String parValue) {
		this.parValue = parValue;
	}
	/**
	 * @return the seatNo
	 */
	public String getSeatNo() {
		return seatNo;
	}
	/**
	 * @param seatNo the seatNo to set
	 */
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	/**
	 * @return the uncomeSellAmount
	 */
	public String getUncomeSellAmount() {
		return uncomeSellAmount;
	}
	/**
	 * @param uncomeSellAmount the uncomeSellAmount to set
	 */
	public void setUncomeSellAmount(String uncomeSellAmount) {
		this.uncomeSellAmount = uncomeSellAmount;
	}
	
	
	
	
}
