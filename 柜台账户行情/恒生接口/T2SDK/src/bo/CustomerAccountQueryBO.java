package bo;

/**
 * 客户账户信息查询
 * @author Administrator
 */
public class CustomerAccountQueryBO {
	public String branchNo;//
	public String fundAccount;//
	public String clientName;//
	public String moneyCount;//
	public String enableBalance;//
	public String exchangeType;//
	public String stockAccount;//
	public String  clientRights;//客户权限
    
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getMoneyCount() {
		return moneyCount;
	}

	public void setMoneyCount(String moneyCount) {
		this.moneyCount = moneyCount;
	}

	public String getEnableBalance() {
		return enableBalance;
	}

	public void setEnableBalance(String enableBalance) {
		this.enableBalance = enableBalance;
	}

	public String getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}

	public String getStockAccount() {
		return stockAccount;
	}

	public void setStockAccount(String stockAccount) {
		this.stockAccount = stockAccount;
	}

	public String getClientRights() {
		return clientRights;
	}

	public void setClientRights(String clientRights) {
		this.clientRights = clientRights;
	}

}
