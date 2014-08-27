package bo;

/**
 * 信托产品委托查询
 * @author wangsy
 *
 */
public class TrustOrderBO {
	public String allotNo;//申请编号
	public String prodCode;//产品代码
	public String prodName;//产品名称
	public String prodtaNo;//产品TA编码
	public String businessFlag;//业务标志  41001：信托预约认购 41002：信托认购 41003：信托交易撤单 41004：信托赎回 41005：信托作废
	public String entrustDate;//发生日期
	public String entrustTime;//发生时间
	public String entrustBalance;//委托金额
	public String entrustAmount;//委托数量
	public String entrustStatus;//委托状态
	public String withdrawFlag;//撤单标志0:不允许 1:允许
	public String errorNo;//
	
	public String getWithdrawFlag() {
		return withdrawFlag;
	}
	public void setWithdrawFlag(String withdrawFlag) {
		this.withdrawFlag = withdrawFlag;
	}
	public String getErrorNo() {
		return errorNo;
	}
	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}
	public String getAllotNo() {
		return allotNo;
	}
	public void setAllotNo(String allotNo) {
		this.allotNo = allotNo;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdtaNo() {
		return prodtaNo;
	}
	public void setProdtaNo(String prodtaNo) {
		this.prodtaNo = prodtaNo;
	}
	public String getBusinessFlag() {
		return businessFlag;
	}
	public void setBusinessFlag(String businessFlag) {
		this.businessFlag = businessFlag;
	}
	public String getEntrustDate() {
		return entrustDate;
	}
	public void setEntrustDate(String entrustDate) {
		this.entrustDate = entrustDate;
	}
	public String getEntrustTime() {
		return entrustTime;
	}
	public void setEntrustTime(String entrustTime) {
		this.entrustTime = entrustTime;
	}
	public String getEntrustBalance() {
		return entrustBalance;
	}
	public void setEntrustBalance(String entrustBalance) {
		this.entrustBalance = entrustBalance;
	}
	public String getEntrustAmount() {
		return entrustAmount;
	}
	public void setEntrustAmount(String entrustAmount) {
		this.entrustAmount = entrustAmount;
	}
	public String getEntrustStatus() {
		return entrustStatus;
	}
	public void setEntrustStatus(String entrustStatus) {
		this.entrustStatus = entrustStatus;
	}
	
	
/**
curr_date == 20140526 type S, 
curr_time == 160640 type S, 
init_date == 20140526 type S, 
prodta_no == E04 type S, 
trust_account == CA1000346785 type S, 
allot_no == 20140526000450000000001 type S, 
branch_no == 45 type S, 
fund_account == 1550008140 type S, 
client_id == 1550008140 type S, 
prod_code == CA04X5 type S, 
prod_name == 宁波丁香置业A type S, 
money_type == 0 type S, 
business_flag == 41023 type S, 
entrust_date == 20140526 type S, 
entrust_time == 160640 type S, 
entrust_balance == 1000000.00 type S, 
entrust_share == 1000000.00 type S, 
entrust_price == 1.000 type S, 
agency_no == 645 type S, 
entrust_status == 0 type S, 
trust_ratio == 0 type S, 
trust_fare == 0 type S, 
conf_operator ==   type S, 
contract_id ==   type S, 
position_str == 20140526000450000000001 type S, 
trustrisk_flag == 0 type S, 
remark ==   type S, 
withdraw_flag == 1 type S, 
cancel_serialno == zscs120_lsprod#0#16:27:37.39##1 type S, 
pay_kind ==   type S, 
prod_position_str == 20140526000450000000001 type S, 
 */

	
}
