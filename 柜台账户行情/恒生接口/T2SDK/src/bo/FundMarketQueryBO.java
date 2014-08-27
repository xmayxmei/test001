package bo;

/**
 * 基金行情信息查询
 * 功能号：7413
 * @author wangsy
 */
public class FundMarketQueryBO {
	private String fundCode;//基金代码
	private String fundName;//基金名称
	private String fundCompany;//基金公司 TA编码
 /**
  ‘0’正常期，可以做申购、赎回、转换、分红方式设置；
  ‘1’认购期，只能做认购；
  ‘2’申购期，只能做申购、分红方式设置；
  ‘3’赎回期，只能做赎回、分红方式设置；
  ‘4’暂时关闭，不允许做认购、申购、赎回、分红方式设置；
  ‘5’非开放日，不允许做认购、申购、赎回、分红方式设置；
  ‘6’基金终止，不允许做认购、申购、赎回、分红方式设置；
  ‘7’权益登记，只能做申购、赎回、转换；
  ‘8’红利发放，只能做申购、赎回、转换；
  ‘9’发行失败，不允许做任何业务
  **/
	private String status;//基金状态
	private String statusName;//基金状态名称
	private String holdMinshare;//最低持有份额
	private String navTotal;//累计净值

	
	public String getFundCode() {
		return fundCode;
	}
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getHoldMinshare() {
		return holdMinshare;
	}
	public void setHoldMinshare(String holdMinshare) {
		this.holdMinshare = holdMinshare;
	}
	public String getNavTotal() {
		return navTotal;
	}
	public void setNavTotal(String navTotal) {
		this.navTotal = navTotal;
	}
	public String getFundCompany() {
		return fundCompany;
	}
	public void setFundCompany(String fundCompany) {
		this.fundCompany = fundCompany;
	}
	
/**
fund_company == 98 type S, 
exchange_name == 深市TA type S, 
fund_code == 166801 type S, 
fund_name == 浙商聚潮新思维混合型基金 type S, 
nav == 1.1540 type S, 
status == 0 type S, 
status_name == 正常开放 type S, 
person_invest == 1000.0000 type S, 
person_pace == 0 type S, 
mach_invest == 1000.0000 type S, 
mach_pace == 1000.0000 type S, 
charge_type == 0 type S, 
type_name == 前端收费 type S, 
par_value == 1.0000 type S, 
redeem_limitshare == 5.0000 type S, 
sub_unit == 0 type S, 
total_share == 0 type S, 
delay_date == 2 type S, 
maxsize == 52861321.9600 type S, 
modify_date == 20140527 type S, 
position_str == 00000098|166801 type S, 
ofund_type == 1 type S, 
ofund_type_name == 股票型 type S, 
fund_risklevel == 3 type S, 
fund_risklevel_name == 中风险等级 type S, 
en_allow_busin == 22,23,24,25,26,27,28,29,31,32,33,34,35,37,38,39,49,59,60,61 type S, 
en_branch_no ==   type S, 
en_other_flag ==   type S, 
min_timer_balance == 100.0000 type S, 
open_share == 1000.0000 type S, 
minsize == 1000.0000 type S, 
en_allow_change_code ==   type S, 
income == 0 type S, 
income_unit == 0 type S, 
income_ratio == 0 type S, 
en_allow_flag == 1,2,20,22,3,4,5,6,7,8,9,24,26,27,28,29,31,32,33,34,35,37,38,39,49,52,53,59,60,61,98 type S, 
person_invest2 == 1000.0000 type S, 
person_pace2 == 0 type S, 
mach_invest2 == 1000.0000 type S, 
mach_pace2 == 1000.0000 type S, 
hold_minshare == 5.0000 type S, 
nav_total == 1.1540 type S, 
issue_date == 20120206 type S, 
sub_day == 20120305 type S, 
minsize2 == 1000.0000 type S, 
open_share2 == 1000.0000 type S, 
mfund_year_rate == 0 type S, 
rationtime_unit == 0 type S, 
money_type == 0 type S, 
trans_limitshare == 0 type S, 
nav_date == 20140515 type S, 
ofcash_balance == 0 type S, 
close_day == 20121231 type S, 

基金产品编码:166801基金公司：98
*/

	
}
