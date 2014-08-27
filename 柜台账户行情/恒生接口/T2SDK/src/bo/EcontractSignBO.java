package bo;

/**
 * 电子合同签署
 * @author Administrator
 *
 */
public class EcontractSignBO {
	private String  serialNo;
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

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
}
