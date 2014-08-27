package bo;

/**
 * 产品信息查询输入参数类
 * @author wangsy
 *
 */
public class ProductQueryVO {
	
	/**
	 * 产品信息查询参数
	 */
	public String  opStation;//站点地址
	public String  branchNo;//分支机构
	public String  productCode;//产品代码
	public String  prodtaNo;//产品TA代码
	public String  productStatus;//产品状态 1:开放期 2:认购期 3:预约认购期 4:产品成立 5:产品终止 6:停止交易 7:停止申购 8:停止赎回
	public String  moneyType="0";//币种类型,0:人民币，1：美元，2港币
	public String  assessLevel;//评估等级   评估等级  0:未评级 1:★ 2:★★ 3:★★★ 4:★★★★ 5:★★★★★
	public String  enProdriskLevel;//允许产品风险等级 0:默认等级 1:低风险等级 2:中低风险等级 3:中风险等级 4:中高风险等级 5:高风险等级 6:极高风险等级              
	public String  prodType;//产品类别，电子合同查询中。 1:服务产品 2:信托 3:保险 4:银行理财 5:证券理财 6:股权 7:基金 
	public String  requestNo;//查询记录条数,用于分页请求
	public String  positionStr;//定位串，上次最后一条记录的positionStr
	
	/**
	 * 认购、申购参数
	 */
	public long  amount;//数量
	public double  entrustBalance;//委托金额
	
	public String getOpStation() {
		return opStation;
	}
	public void setOpStation(String opStation) {
		this.opStation = opStation;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProdtaNo() {
		return prodtaNo;
	}
	public void setProdtaNo(String prodtaNo) {
		this.prodtaNo = prodtaNo;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public String getAssessLevel() {
		return assessLevel;
	}
	public void setAssessLevel(String assessLevel) {
		this.assessLevel = assessLevel;
	}
	public String getEnProdriskLevel() {
		return enProdriskLevel;
	}
	public void setEnProdriskLevel(String enProdriskLevel) {
		this.enProdriskLevel = enProdriskLevel;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getPositionStr() {
		return positionStr;
	}
	public void setPositionStr(String positionStr) {
		this.positionStr = positionStr;
	}
	public double getEntrustBalance() {
		return entrustBalance;
	}
	public void setEntrustBalance(double entrustBalance) {
		this.entrustBalance = entrustBalance;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	
	

	
	
	
	

}
