package bo;

import java.io.Serializable;

/**
 * 成交回报查询
 */
public class MyDealBackQueryBO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1875459730450820193L;

	/**
	 * 定位串
	 */
	private String positionStr;
	
	/**
	 * 流水序号
	 */
	private String serialNo;

	/**
	 * 日期
	 */
	private String date;

	/**
	 * 交易类别
	 */
	private String exchangeType;

	/**
	 * 交易名称
	 */
	private String exchangeName;

	/**
	 * 证券账号
	 */
	private String stockAccount;
	/**
	 * 证券代码
	 */
	private String stockCode;
	
	/**
	 * 证券名称
	 */
	private String stockName;
	/**
	 * 买卖方向
	 */
	private String entrustBs;

	/**
	 * 买卖方向名称
	 */
	private String bsName;
	/**
	 * 成交价格
	 */
	private String businessPrice;
	/**
	 * 成交数量
	 */
	private String businessAmount;
	/**
	 * 成交时间
	 */
	private String businessTime;

	/**
	 * 成交状态0：‘成交’，2：‘废单’4：‘确认’
	 */
	private String businessStatus;

	/**
	 * 成交状态名称
	 */
	private String statusName;

	/***
	 * 成交类别，0：‘买卖’2：‘撤单’
	 */
	private String businessType;
	/**
	 * 成交类别名称
	 */
	private String typeName;
	/**
	 * 成交笔数
	 */
	private String businessTimes;
	/**
	 * 合同号
	 */
	private String entrustNo;
	/**
	 * 申报号
	 */
	private String reportNo;
	/**
	 * 成交金额
	 */
	private String businessBalance;
	/**
	 * 成交编号
	 */
	private String businessNo;
	public String getPositionStr() {
		return positionStr;
	}
	public void setPositionStr(String positionStr) {
		this.positionStr = positionStr;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getExchangeType() {
		return exchangeType;
	}
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}
	public String getExchangeName() {
		return exchangeName;
	}
	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}
	public String getStockAccount() {
		return stockAccount;
	}
	public void setStockAccount(String stockAccount) {
		this.stockAccount = stockAccount;
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
	public String getEntrustBs() {
		return entrustBs;
	}
	public void setEntrustBs(String entrustBs) {
		this.entrustBs = entrustBs;
	}
	public String getBsName() {
		return bsName;
	}
	public void setBsName(String bsName) {
		this.bsName = bsName;
	}
	public String getBusinessPrice() {
		return businessPrice;
	}
	public void setBusinessPrice(String businessPrice) {
		this.businessPrice = businessPrice;
	}
	public String getBusinessAmount() {
		return businessAmount;
	}
	public void setBusinessAmount(String businessAmount) {
		this.businessAmount = businessAmount;
	}
	public String getBusinessTime() {
		return businessTime;
	}
	public void setBusinessTime(String businessTime) {
		this.businessTime = businessTime;
	}
	public String getBusinessStatus() {
		return businessStatus;
	}
	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getBusinessTimes() {
		return businessTimes;
	}
	public void setBusinessTimes(String businessTimes) {
		this.businessTimes = businessTimes;
	}
	public String getEntrustNo() {
		return entrustNo;
	}
	public void setEntrustNo(String entrustNo) {
		this.entrustNo = entrustNo;
	}
	public String getReportNo() {
		return reportNo;
	}
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	public String getBusinessBalance() {
		return businessBalance;
	}
	public void setBusinessBalance(String businessBalance) {
		this.businessBalance = businessBalance;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	
	







}
