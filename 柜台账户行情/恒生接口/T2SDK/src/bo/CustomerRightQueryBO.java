package bo;

/**
 * 客户权限信息查询
 * @author Administrator
 *
 */
public class CustomerRightQueryBO {	
	public String branchNo;//分支机构
	public String clientRights;//客户权限:tec
	public String errorNo;
	
	
	public String getErrorNo() {
		return errorNo;
	}
	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getClientRights() {
		return clientRights;
	}
	public void setClientRights(String clientRights) {
		this.clientRights = clientRights;
	}
	

}
