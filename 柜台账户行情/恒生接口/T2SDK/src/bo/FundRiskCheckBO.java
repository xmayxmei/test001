package bo;

/**
 * 基金新规交易风险检查
 * @author Administrator
 *
 */
public class FundRiskCheckBO {
	String enableFlag;//可操作标志(0－否，1－是)

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	
	/**
error_no ==  type S, 
error_info ==  type S, 
fund_risklevel == 3 type S, 
fund_risklevel_name == 中风险等级 type S, 
client_risklevel == 2 type S, 
client_risklevel_name == 稳健型 type S, 
	 */
	
	
}
