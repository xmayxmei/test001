package bo;

import java.io.Serializable;

/**
 * 绑定用户查询类
 */
public class BindUserResultVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6221344155006361803L;

	/**
	 * 资金账号
	 */
	private String fundAccount;
	
	/**
	 * 交易密码
	 */
	private String password;

	/**
	 * 机构编码
	 */
	private String branchNo;
	
	/**
	 * 站点标示，传入IP/手机
	 */
	private String opStation;
	
	/**
	 * 客户号
	 */
	private String customerCode;

	/**
	 * 客户绑定信息表ID
	 */
	private Long accountBindId ;
	
	
	/**
	 * 客户姓名
	 */
	private String customerName;
	
	
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
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return the accountBindId
	 */
	public Long getAccountBindId() {
		return accountBindId;
	}

	/**
	 * @param accountBindId the accountBindId to set
	 */
	public void setAccountBindId(Long accountBindId) {
		this.accountBindId = accountBindId;
	}


	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getOpStation() {
		return opStation;
	}

	public void setOpStation(String opStation) {
		this.opStation = opStation;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	} 
	
	
	
	
	
	
	
	
}
