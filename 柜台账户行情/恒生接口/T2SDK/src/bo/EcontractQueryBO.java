package bo;

/**
 * 电子合同查询
 * @author wangsy
 *
 */
public class EcontractQueryBO {

	private String econtractId;//电子合同编号
	private String econtractStatus;//电子合同状态
	private String initDate;//发生日期
	private String econtractDate;//电子合同签订日期
	private String clientId;//电子合同签订时间
	private String fundAccount;//资金账号
	private String prodCode;//产品代码
	private String prodtaNo;//产品TA代码
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
	public String getEcontractId() {
		return econtractId;
	}
	public void setEcontractId(String econtractId) {
		this.econtractId = econtractId;
	}
	public String getEcontractStatus() {
		return econtractStatus;
	}
	public void setEcontractStatus(String econtractStatus) {
		this.econtractStatus = econtractStatus;
	}
	public String getInitDate() {
		return initDate;
	}
	public void setInitDate(String initDate) {
		this.initDate = initDate;
	}
	public String getEcontractDate() {
		return econtractDate;
	}
	public void setEcontractDate(String econtractDate) {
		this.econtractDate = econtractDate;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getFundAccount() {
		return fundAccount;
	}
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getProdtaNo() {
		return prodtaNo;
	}
	public void setProdtaNo(String prodtaNo) {
		this.prodtaNo = prodtaNo;
	}


	/**
信托电子合同查询
init_date == 20140528 type S, 
econtract_date == 20140528 type S, 
econtract_time == 110255 type S, 
branch_no == 1 type S, 
client_id == 110011023 type S, 
fund_account == 110011023 type S, 
prod_account == 00E0400100004 type S, 
trans_account ==   type S, 
id_kind == 0 type S, 
id_no == 320202197003221029 type S, 
client_name == 彭波 type S, 
client_type == 1 type S, 
fund_company == E04 type S, 
fund_code == CA04X5 type S, 
prodta_no == E04 type S, 
prod_code == CA04X5 type S, 
prod_type == 2 type S, 
sub_risk_flag == a type S, 
op_entrust_way == 7 type S, 
charge_type ==   type S, 
econtract_id == 0000CA04X51645201405280002 type S, 
econtract_status == 0 type S, 
remark ==   type S, 
position_str == 201405280000000002 type S, 
ofrisk_flag ==   type S, 
trustee_check_status == 0 type S, 
	 */


}
