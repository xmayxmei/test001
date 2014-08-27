package bo;

/**
 * 客户登录信息，功能号200
 * @author wangsy
 *
 */
public class ClientLoginResultBO {
	
	/**
	 * 资金账号
	 */
	private String fundAccount;
	
	/**
	 * 客户号
	 */
	private String clientId;

	/**
	 * 客户姓名
	 */
	private String clientName;
	
	/**
	 * 分支编号
	 */
	private String branchNo;
	
	/**
	 * 上次登录站点/电话
	 */
	private String lastOpStation;
	
	/**
	 * 错误编码,正常返回0，错误信息见ErrorCodeType
	 */
    private String errorNo;
    
    /**
     * 错误信息
     */
    private String errorInfo;
    
	
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getLastOpStation() {
		return lastOpStation;
	}

	public void setLastOpStation(String lastOpStation) {
		this.lastOpStation = lastOpStation;
	}

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
	

}
