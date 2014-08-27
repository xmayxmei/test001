package bo;

/**
 * 信拖账户查询
 * @author Administrator
 *
 */
public class TrustAccountQueryBO {
	public String prodtaNo;
	public String trustAccount;
	public String branchNo;
	public String fundAccount;
	public String clientId;
	public String idKind;
	public String idNo;
	public String prodholderStatus;
	public String openDate;
	public String getProdtaNo() {
		return prodtaNo;
	}
	public void setProdtaNo(String prodtaNo) {
		this.prodtaNo = prodtaNo;
	}
	public String getTrustAccount() {
		return trustAccount;
	}
	public void setTrustAccount(String trustAccount) {
		this.trustAccount = trustAccount;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getFundAccount() {
		return fundAccount;
	}
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getIdKind() {
		return idKind;
	}
	public void setIdKind(String idKind) {
		this.idKind = idKind;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getProdholderStatus() {
		return prodholderStatus;
	}
	public void setProdholderStatus(String prodholderStatus) {
		this.prodholderStatus = prodholderStatus;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	/**
prodta_no == E04 type S, 
trust_account == CA1000339912 type S, 
branch_no == 2 type S, 
fund_account == 267025323 type S, 
client_id == 267025323 type S, 
id_kind == 0 type S, 
id_no == 330702194912120415 type S, 
trustholder_status == 0 type S, 
	 */



}
