package bo;

/**
 * 信托产品认购、申购
 * 功能号：337111、337113
 * @author Administrator
 *
 */
public class TrustProductGouBO {
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 交易日期
	 */
	private String initDate;
	/**
	 * 认购费
	 */
	private String trustFare;
	/**
	 * 申请编号
	 */
	private String allotNo;
	
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
	public String getTrustFare() {
		return trustFare;
	}
	public void setTrustFare(String trustFare) {
		this.trustFare = trustFare;
	}
	public String getAllotNo() {
		return allotNo;
	}
	public void setAllotNo(String allotNo) {
		this.allotNo = allotNo;
	}


}
