package bo;

/**
 * 基金产品撤单
 * @author Administrator
 *
 */
public class FundOrderCancleBO {
	public String serialNo;//合同号
	public String allotnoOut;//委托编号
	public String errorNo;
	public String errorInfo;
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getAllotnoOut() {
		return allotnoOut;
	}
	public void setAllotnoOut(String allotnoOut) {
		this.allotnoOut = allotnoOut;
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
