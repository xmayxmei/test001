package bo;

/**
 * 信托产品查询
 * 功能号：337100
 * @author wangsy
 *
 */
public class TrustProductQueryBO {
	private String prodtaNo;//TA代码
	private String prodCode;//产品代码
	private String prodName;//产品名称	
	private String prodStatus;//产品状态 ,//1:开放期 2:认购期 3:预约认购期 4:产品成立 5:产品终止 6:停止交易 7:停止申购 8:停止赎回
	private String nav;//T-1日基金单位净值	
	
	public String getProdtaNo() {
		return prodtaNo;
	}
	public void setProdtaNo(String prodtaNo) {
		this.prodtaNo = prodtaNo;
	}
	public String getProdCode() {
		return prodCode;
	}
	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdStatus() {
		return prodStatus;
	}
	public void setProdStatus(String prodStatus) {
		this.prodStatus = prodStatus;
	}
	public String getNav() {
		return nav;
	}
	public void setNav(String nav) {
		this.nav = nav;
	}

/**
prodta_no == E04 type S, 
prod_code == CA04X5 type S, 
prod_type == 2 type S, 
prod_type_ass ==   type S, 
prod_name == 宁波丁香置业A type S, 
prodalias_name == 丁香置业A type S, 
prodcompany_name == 长安信托 type S, 
prodspell_code == DXZYA type S, 
prodrelative_code ==   type S, 
prod_status == 3 type S, 
money_type == 0 type S, 
ipo_begin_date == 20140425 type S, 
ipo_end_date == 20140509 type S, 
ipo_begin_time == 93000 type S, 
ipo_end_time == 150000 type S, 
pre_end_date == 20140509 type S, 
pre_end_time == 150000 type S, 
prod_begin_date == 20140507 type S, 
prod_end_date == 20151112 type S, 
prod_min_bala == 1000000.00 type S, 
prod_max_bala == 65700000.00 type S, 
prom_begin_date == 20140425 type S, 
prom_end_date == 20140509 type S, 
subconf_enddate == 20140509 type S, 
subconf_endtime == 150000 type S, 
prod_real_bala == 12900000.00 type S, 
promfare_rate == 0 type S, 
prom_scale == 65700000.00 type S, 
prod_term == 547 type S, 
prodrisk_level == 3 type S, 
assess_level == 0 type S, 
issue_price == 1.000 type S, 
par_value == 1.000 type S, 
nav == 1.0000 type S, 
prodpre_ratio == 0.09000000 type S, 
prod_sponsor == 长安信托 type S, 
prod_manager == 长安信托 type S, 
prod_trustee == 长安信托 type S, 
trustee_bank == 广发银行 type S, 
prodsub_rate == 0 type S, 
invest_type == 5 type S, 
income_type == 3 type S, 
interest_freq == 半年付息一次 type S, 
max_subscribe_num == 28 type S, 
min_subscribe_balance == 0 type S, 
max_subscribe_balance == 0 type S, 
sub_unit == 100000 type S, 
issue_date == 0 type S, 
open_share == 1000000.00 type S, 
max_pdshare == 0 type S, 
sum_sub_balance == 0 type S, 
precisions == 0 type S, 
min_share == 0 type S, 
allot_limitshare == 100000.00 type S, 
org_lowlimit_balance == 0 type S, 
org_append_balance == 0 type S, 
min_share2 == 0 type S, 
allot_limitshare2 == 0 type S, 
org_lowlimit_balance2 == 0 type S, 
org_append_balance2 == 0 type S, 
min_mergeamount == 0 type S, 
min_splitamount == 0 type S, 
redemption_unit == 0 type S, 
max_allotratio == 0 type S, 
minsize == 0 type S, 
redeem_use_flag ==   type S, 
en_change_code ==   type S, 
trans_limitshare == 0 type S, 
prod_back_n == 1 type S, 
en_entrust_way == 0,4,7 type S, 
prodcode_ctrlstr == 01000010100001000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000 type S, 
per_myriad_income == 0 type S, 
min_asset_need == 1000000.00 type S, 
switch_unit == 0 type S, 
current_amount_low == 0 type S, 
ctrlstr_useflags == 011111111100110111000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000 type S, 
dividend_way ==   type S, 
charge_type ==   type S, 
redeem_limitshare == 0 type S, 
year_days == 0 type S, 
interest_end_date == 0 type S, 
min_timer_balance == 0 type S, 
max_timer_balance == 0 type S, 
prod_internal_code == CA04X5 type S, 
*/

}
