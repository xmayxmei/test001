package bo;

/**
 * 信托产品份额查询
 * 功能号：337151
 * @author wangsy
 */
public class FuncTrustShareBO {
	public String prodCode;//产品代码
	public String prodtaNo;//产品TA编号
	public String currentShare;//当前份额
	public String enableShare;//可用份额
	public String frozenShare;//冻结份额
	public String businessFrozenShare;//交易冻结数量
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
	public String getCurrentShare() {
		return currentShare;
	}
	public void setCurrentShare(String currentShare) {
		this.currentShare = currentShare;
	}
	public String getEnableShare() {
		return enableShare;
	}
	public void setEnableShare(String enableShare) {
		this.enableShare = enableShare;
	}
	public String getFrozenShare() {
		return frozenShare;
	}
	public void setFrozenShare(String frozenShare) {
		this.frozenShare = frozenShare;
	}
	public String getBusinessFrozenShare() {
		return businessFrozenShare;
	}
	public void setBusinessFrozenShare(String businessFrozenShare) {
		this.businessFrozenShare = businessFrozenShare;
	}

/**
branch_no == 2 type S, 
prodta_no == E04 type S, 
prod_code == CA04X5 type S, 
fund_account == 267025323 type S, 
trust_account == CA1000339912 type S, 
client_id == 267025323 type S, 
money_type == 0 type S, 
buy_date == 20140512 type S, 
allot_no == 20140425000020000000001 type S, 
current_share == 1300000.00 type S, 
frozen_share == 0 type S, 
business_frozen_share == 0 type S, 
enable_share == 1300000.00 type S, 
position_str == 20140512000020000000001 type S, 
prod_name == 宁波丁香置业A type S, 
 */
	
}
