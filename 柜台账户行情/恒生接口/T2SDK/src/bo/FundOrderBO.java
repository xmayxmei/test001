package bo;

/**
 * 基金交易信息（订单查询）
 * @author Administrator
 */
public class FundOrderBO {
	public String allotno;//申请标号
	public String fundCode;//基金代码
	public String fundName;//基金名称
	public String date;//发生日期
	public String time;//发生时间
	public String businessFlag;//业务标志
	public String businessName;//业务名称
	public String balance;//金额
	public String shares;//份额
	public String entrustStatus;//委托状态
	public String entrustName;//委托状态名称	
	public String positionStr;//
	public String errorNo;//错误号
	public String errorInfo;//错误原因
	
	public String getErrorNo() {
		return errorNo;
	}
	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public String getAllotno() {
		return allotno;
	}
	public void setAllotno(String allotno) {
		this.allotno = allotno;
	}
	public String getFundCode() {
		return fundCode;
	}
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBusinessFlag() {
		return businessFlag;
	}
	public void setBusinessFlag(String businessFlag) {
		this.businessFlag = businessFlag;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getShares() {
		return shares;
	}
	public void setShares(String shares) {
		this.shares = shares;
	}
	public String getEntrustStatus() {
		return entrustStatus;
	}
	public void setEntrustStatus(String entrustStatus) {
		this.entrustStatus = entrustStatus;
	}
	public String getEntrustName() {
		return entrustName;
	}
	public void setEntrustName(String entrustName) {
		this.entrustName = entrustName;
	}
	public String getPositionStr() {
		return positionStr;
	}
	public void setPositionStr(String positionStr) {
		this.positionStr = positionStr;
	}
	
	
/**
当前订单查询结果
branch_no == 1 type S, 
date == 20140526 type S, 
time == 165603 type S, 
entrust_no == 1 type S, 
stock_account == 99F604450334 type S, 
other_stkacco ==   type S, 
allot_date == 20140526 type S, 
allotdate == 20140526 type S, 
business_flag == 22 type S, 
business_name == 申购申请 type S, 
bflag_name == 申购申请 type S, 
money_type == 0 type S, 
balance == 1000.0000 type S, 
shares == 0 type S, 
trans_code ==   type S, 
hope_date == 0 type S, 
hopedate == 0 type S, 
fund_account == 110012788 type S, 
fund_company == 99 type S, 
fare_sx == 0 type S, 
fund_code == 519678 type S, 
fund_name == 银河消费驱动股票型基金 type S, 
allotno == 645000120140526990000001 type S, 
deal_share == 0 type S, 
deal_shares == 0 type S, 
deal_balance == 0 type S, 
position_str == 201405261000099000000000001 type S, 
entrust_status == 0 type S, 
status_name == 未报 type S, 
entrust_name == 未报 type S, 
exchange_name == 沪市TA type S, 
bonus_type == 1 type S, 
bonus_name == 现金分红 type S, 
original_tano ==   type S, 
bank_account == 622468001012104117 type S, 
ofrisk_flag == 2 type S, 
ofrisk_name == 风险等级不匹配 type S, 
batch_no == 1 type S, 
remark ==   type S, 
redeem_date == 20140526 type S, 
*/
	

}
