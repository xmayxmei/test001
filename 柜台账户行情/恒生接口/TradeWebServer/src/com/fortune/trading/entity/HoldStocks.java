package com.fortune.trading.entity;
/**
 * <code>HoldStocks</code> 持仓实体类.
 *
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1
 */
public class HoldStocks {
	/** 股东号 */
	private String shareholder;
	/** 交易所 */
	private String exchange;
	/** 证券代码 */
	private String stockCode;
	/** 证券名称 */
	private String stockName;
	/** 数量 */
	private String qty;
	/** 成本价 */
	private String costPrice;
	/** 最新价 */
	private String lastPrice;
	/** 市值 */
	private String marketVal;
	/** 买卖盈亏 */
	private String totalPL;
	/** 可卖数量 */
	private String totalSellEnable;
	
	public String getShareholder() {
		return shareholder;
	}
	public void setShareholder(String shareholder) {
		this.shareholder = shareholder;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}
	public String getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}
	public String getMarketVal() {
		return marketVal;
	}
	public void setMarketVal(String marketVal) {
		this.marketVal = marketVal;
	}
	public String getTotalPL() {
		return totalPL;
	}
	public void setTotalPL(String totalPL) {
		this.totalPL = totalPL;
	}
	public String getTotalSellEnable() {
		return totalSellEnable;
	}
	public void setTotalSellEnable(String totalSellEnable) {
		this.totalSellEnable = totalSellEnable;
	}
	
}
