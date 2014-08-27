package bo;

/**
 * 产品适当性信息获取
 * @author Administrator
 *
 */
public class TrustRiskCheckBO {
	/**
	 * 风险匹配串
	 * 风险匹配串(串的值：0正常 1不正常)
	 * 每一位校验项： 1-需要最新评级 2-产品期限(投资年限)不符 3-产品类别(投资方向)不符 4-资产要求不符 5-风险等级不匹配
	 */
	public String riskMatchStr;
    
	public String getRiskMatchStr() {
		return riskMatchStr;
	}

	public void setRiskMatchStr(String riskMatchStr) {
		this.riskMatchStr = riskMatchStr;
	}
	
/**
corp_risk_level == 2 type S, 
prodrisk_level == 3 type S, 
risk_match_str == 00000 type S, 
prof_flag ==   type S, 
prof_begin_date == 0 type S, 
prof_end_date == 0 type S, 
*/
	
	

}
