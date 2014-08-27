package bo;

/**
 * 基金份额查询
 * 功能号：7411
 * @author wangsy
 *
 */
public class FundShareBO {
	public String fundCode;//基金代码
	public String status;//基金状态
	public String fundName;//基金名称
	public String frozenAmount;//基金冻结份额
	public String enableAmount;//基金可用份额
	public String getFundCode() {
		return fundCode;
	}
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(String frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	public String getEnableAmount() {
		return enableAmount;
	}
	public void setEnableAmount(String enableAmount) {
		this.enableAmount = enableAmount;
	}
	

}
