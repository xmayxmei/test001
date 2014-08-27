package bo;

/**
 * 证券理财产品信息查询
 * 功能号：337400
 * @author wangsy
 *
 */
public class FuncSecumProductQueryBO {
	private String prodtaNo;//产品TA编号
	private String prodCode;//产品代码
	private String prodName;//产品名称
	private String prodaliasName;//产品别名
	private String prodcompanyName;//产品公司
	private String prodspellCode;//拼音代码
	private String prodStatus;//产品状态  
	private String moneyType;//币种类别
	private String ipoBeginDate;//募集开始日期
	private String ipoEndDate;//募集结束日期
	private String prodBeginDate;//产品成立日期
	private String prodEndDate;//产品结束日期
	private String prodMinBala;//产品最低募集金额
	private String prodMaxBala;//产品最高募集金额
	private String promBeginDate;//推介开始日期
	private String promEndDate;//推介结束日期
	private String promfareRate;//推介费率
	private String promScale;//推介规模
	private String prodTerm;//产品期限（投资期限）
	private String investType;//投资类别
	private String prodriskLevel;//风险等级  
	private String assessLevel;//评估等级  
	private String issuePrice;//发行价格
	private String parValue;//发行面值
	private String nav;//T-1日基金单位净值
	private String prodpreRatio;//预期年收益率
	private String prodSponsor;//产品发起人
	private String prodManager;//产品管理人
	private String prodTrustee;//产品托管人
	private String trusteeBank;//托管银行
	private String prodsubRate;//认购费率
	private String maxSubscribeNum;//认购人数上限
	private String openShare;//个人首次最低
	private String maxPdshare;//客户单日申购最高金额
	private String minShare;//个人最低认购金额
	private String allotLimitshare;//个人追加认购金额
	private String orgLowlimitBalance;//机构最低认购金额
	private String orgAppendBalance;//机构追加认购金额
	private String minShare2;//个人最低申购金额
	private String allotLimitshare2;//个人追加申购金额
	private String orgLowlimitBalance2;//机构最低申购金额
	private String orgAppendBalance2;//机构追加申购金额
	private String redemptionUnit;//赎回最小单位
	private String maxAllotratio;//每次最大可申购比例
	private String minsize;//机构首次最低金额
	private String subUnit;//认购/申购的单位
	private String prodTypeAss;//产品辅助类别
	private String straddleType;//多空类型 0:看空 1:看多 
	private String matchCode;//配对代码
	private String dateCount;//证券理财赎回延迟天数
	private String positionStr;//定位串
	
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
	public String getProdaliasName() {
		return prodaliasName;
	}
	public void setProdaliasName(String prodaliasName) {
		this.prodaliasName = prodaliasName;
	}
	public String getProdcompanyName() {
		return prodcompanyName;
	}
	public void setProdcompanyName(String prodcompanyName) {
		this.prodcompanyName = prodcompanyName;
	}
	public String getProdspellCode() {
		return prodspellCode;
	}
	public void setProdspellCode(String prodspellCode) {
		this.prodspellCode = prodspellCode;
	}
	public String getProdStatus() {
		return prodStatus;
	}
	public void setProdStatus(String prodStatus) {
		this.prodStatus = prodStatus;
	}
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public String getIpoBeginDate() {
		return ipoBeginDate;
	}
	public void setIpoBeginDate(String ipoBeginDate) {
		this.ipoBeginDate = ipoBeginDate;
	}
	public String getIpoEndDate() {
		return ipoEndDate;
	}
	public void setIpoEndDate(String ipoEndDate) {
		this.ipoEndDate = ipoEndDate;
	}
	public String getProdBeginDate() {
		return prodBeginDate;
	}
	public void setProdBeginDate(String prodBeginDate) {
		this.prodBeginDate = prodBeginDate;
	}
	public String getProdEndDate() {
		return prodEndDate;
	}
	public void setProdEndDate(String prodEndDate) {
		this.prodEndDate = prodEndDate;
	}
	public String getProdMinBala() {
		return prodMinBala;
	}
	public void setProdMinBala(String prodMinBala) {
		this.prodMinBala = prodMinBala;
	}
	public String getProdMaxBala() {
		return prodMaxBala;
	}
	public void setProdMaxBala(String prodMaxBala) {
		this.prodMaxBala = prodMaxBala;
	}
	public String getPromBeginDate() {
		return promBeginDate;
	}
	public void setPromBeginDate(String promBeginDate) {
		this.promBeginDate = promBeginDate;
	}
	public String getPromEndDate() {
		return promEndDate;
	}
	public void setPromEndDate(String promEndDate) {
		this.promEndDate = promEndDate;
	}
	public String getPromfareRate() {
		return promfareRate;
	}
	public void setPromfareRate(String promfareRate) {
		this.promfareRate = promfareRate;
	}
	public String getPromScale() {
		return promScale;
	}
	public void setPromScale(String promScale) {
		this.promScale = promScale;
	}
	public String getProdTerm() {
		return prodTerm;
	}
	public void setProdTerm(String prodTerm) {
		this.prodTerm = prodTerm;
	}
	public String getInvestType() {
		return investType;
	}
	public void setInvestType(String investType) {
		this.investType = investType;
	}
	public String getProdriskLevel() {
		return prodriskLevel;
	}
	public void setProdriskLevel(String prodriskLevel) {
		this.prodriskLevel = prodriskLevel;
	}
	public String getAssessLevel() {
		return assessLevel;
	}
	public void setAssessLevel(String assessLevel) {
		this.assessLevel = assessLevel;
	}
	public String getIssuePrice() {
		return issuePrice;
	}
	public void setIssuePrice(String issuePrice) {
		this.issuePrice = issuePrice;
	}
	public String getParValue() {
		return parValue;
	}
	public void setParValue(String parValue) {
		this.parValue = parValue;
	}
	public String getNav() {
		return nav;
	}
	public void setNav(String nav) {
		this.nav = nav;
	}
	public String getProdpreRatio() {
		return prodpreRatio;
	}
	public void setProdpreRatio(String prodpreRatio) {
		this.prodpreRatio = prodpreRatio;
	}
	public String getProdSponsor() {
		return prodSponsor;
	}
	public void setProdSponsor(String prodSponsor) {
		this.prodSponsor = prodSponsor;
	}
	public String getProdManager() {
		return prodManager;
	}
	public void setProdManager(String prodManager) {
		this.prodManager = prodManager;
	}
	public String getProdTrustee() {
		return prodTrustee;
	}
	public void setProdTrustee(String prodTrustee) {
		this.prodTrustee = prodTrustee;
	}
	public String getTrusteeBank() {
		return trusteeBank;
	}
	public void setTrusteeBank(String trusteeBank) {
		this.trusteeBank = trusteeBank;
	}
	public String getProdsubRate() {
		return prodsubRate;
	}
	public void setProdsubRate(String prodsubRate) {
		this.prodsubRate = prodsubRate;
	}
	public String getMaxSubscribeNum() {
		return maxSubscribeNum;
	}
	public void setMaxSubscribeNum(String maxSubscribeNum) {
		this.maxSubscribeNum = maxSubscribeNum;
	}
	public String getOpenShare() {
		return openShare;
	}
	public void setOpenShare(String openShare) {
		this.openShare = openShare;
	}
	public String getMaxPdshare() {
		return maxPdshare;
	}
	public void setMaxPdshare(String maxPdshare) {
		this.maxPdshare = maxPdshare;
	}
	public String getMinShare() {
		return minShare;
	}
	public void setMinShare(String minShare) {
		this.minShare = minShare;
	}
	public String getAllotLimitshare() {
		return allotLimitshare;
	}
	public void setAllotLimitshare(String allotLimitshare) {
		this.allotLimitshare = allotLimitshare;
	}
	public String getOrgLowlimitBalance() {
		return orgLowlimitBalance;
	}
	public void setOrgLowlimitBalance(String orgLowlimitBalance) {
		this.orgLowlimitBalance = orgLowlimitBalance;
	}
	public String getOrgAppendBalance() {
		return orgAppendBalance;
	}
	public void setOrgAppendBalance(String orgAppendBalance) {
		this.orgAppendBalance = orgAppendBalance;
	}
	public String getMinShare2() {
		return minShare2;
	}
	public void setMinShare2(String minShare2) {
		this.minShare2 = minShare2;
	}
	public String getAllotLimitshare2() {
		return allotLimitshare2;
	}
	public void setAllotLimitshare2(String allotLimitshare2) {
		this.allotLimitshare2 = allotLimitshare2;
	}
	public String getOrgLowlimitBalance2() {
		return orgLowlimitBalance2;
	}
	public void setOrgLowlimitBalance2(String orgLowlimitBalance2) {
		this.orgLowlimitBalance2 = orgLowlimitBalance2;
	}
	public String getOrgAppendBalance2() {
		return orgAppendBalance2;
	}
	public void setOrgAppendBalance2(String orgAppendBalance2) {
		this.orgAppendBalance2 = orgAppendBalance2;
	}
	public String getRedemptionUnit() {
		return redemptionUnit;
	}
	public void setRedemptionUnit(String redemptionUnit) {
		this.redemptionUnit = redemptionUnit;
	}
	public String getMaxAllotratio() {
		return maxAllotratio;
	}
	public void setMaxAllotratio(String maxAllotratio) {
		this.maxAllotratio = maxAllotratio;
	}
	public String getMinsize() {
		return minsize;
	}
	public void setMinsize(String minsize) {
		this.minsize = minsize;
	}
	public String getSubUnit() {
		return subUnit;
	}
	public void setSubUnit(String subUnit) {
		this.subUnit = subUnit;
	}
	public String getProdTypeAss() {
		return prodTypeAss;
	}
	public void setProdTypeAss(String prodTypeAss) {
		this.prodTypeAss = prodTypeAss;
	}
	public String getStraddleType() {
		return straddleType;
	}
	public void setStraddleType(String straddleType) {
		this.straddleType = straddleType;
	}
	public String getMatchCode() {
		return matchCode;
	}
	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}
	public String getDateCount() {
		return dateCount;
	}
	public void setDateCount(String dateCount) {
		this.dateCount = dateCount;
	}
	public String getPositionStr() {
		return positionStr;
	}
	public void setPositionStr(String positionStr) {
		this.positionStr = positionStr;
	}




}
