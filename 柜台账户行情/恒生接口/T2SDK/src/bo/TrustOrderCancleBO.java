package bo;
/**
 * 信托委托撤单
 * @author Administrator
 */
public class TrustOrderCancleBO {
	public String serialNo;//流水号
	public String initDate;//交易日期
	public String allotNo;//申购编号
	public String errorNo;//
	public String errorInfo;//
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getInitDate() {
		return initDate;
	}
	public void setInitDate(String initDate) {
		this.initDate = initDate;
	}
	public String getAllotNo() {
		return allotNo;
	}
	public void setAllotNo(String allotNo) {
		this.allotNo = allotNo;
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
