package bo;

/**
 * 查询客户返回信息
 * @author wangsy
 *
 */
public class CustomerInfoBO {

	/**
	 * 客户号
	 */
	private String clientId;

	/**
	 * 客户姓名
	 */
	private String clientName;
	
	/**
	 * 客户性别
	 */
	private String clientSex;
	
	/**
	 * 出生日期
	 */
	private String birthday;

	/**
	 * 客户状态
	 */
	private String clientStatus;

	/**
	 * 资金卡号
	 */
	private String fundCard;
	
	/**
	 * 分支编号
	 */
	private String branchNo;

	/**
	 * 证件类别 0 身份证 1 护照 2 营业执照
	 */
	private String idKind;

	/**
	 * 证件号码
	 */
	private String idNo;

	/**
	 * 投资人户名
	 */
	private String lastName;

	/**
	 * 联系人
	 */
	private String mailName;

	/**
	 * 邮政编码
	 */
	private String zipcode;

	/**
	 * 联系地址
	 */
	private String  address;

	/**
	 * 身份证地址
	 */
	private String  idAddress;

	/**
	 * 联系电话
	 */
	private String  phonecode;
	/**
	 * 移动电话
	 */
	private String  mobiletelephone ;

	/**
	 * 传呼号码
	 */
	private String  beeppager ;
	/**
	 * 传真号码
	 */
	private String   fax ;

	/**
	 * 电子邮箱
	 */
	private String  eMail;
	
	/**
	 * 机构标志
	 */
	private String organProp;
	/**
	 * 机构标志名称
	 */
	private String organName;
	
	/**
	 * 证件开始日期
	 */
	private String idBegindate;
	/**
	 * 证件结束日期
	 */
	private String idTerm;
	/**
	 * 过期标示
	 */
	private String termFlag;

	/**
	 * 错误编码,正常返回0，错误信息见ErrorCodeType
	 */
	private int errorNo;

	/**
	 * 错误信息
	 */
	private String errorInfo;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientSex() {
		return clientSex;
	}

	public void setClientSex(String clientSex) {
		this.clientSex = clientSex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getClientStatus() {
		return clientStatus;
	}

	public void setClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}

	public String getFundCard() {
		return fundCard;
	}

	public void setFundCard(String fundCard) {
		this.fundCard = fundCard;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getIdKind() {
		return idKind;
	}

	public void setIdKind(String idKind) {
		this.idKind = idKind;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMailName() {
		return mailName;
	}

	public void setMailName(String mailName) {
		this.mailName = mailName;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(String idAddress) {
		this.idAddress = idAddress;
	}

	public String getPhonecode() {
		return phonecode;
	}

	public void setPhonecode(String phonecode) {
		this.phonecode = phonecode;
	}

	public String getMobiletelephone() {
		return mobiletelephone;
	}

	public void setMobiletelephone(String mobiletelephone) {
		this.mobiletelephone = mobiletelephone;
	}

	public String getBeeppager() {
		return beeppager;
	}

	public void setBeeppager(String beeppager) {
		this.beeppager = beeppager;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getOrganProp() {
		return organProp;
	}

	public void setOrganProp(String organProp) {
		this.organProp = organProp;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getIdBegindate() {
		return idBegindate;
	}

	public void setIdBegindate(String idBegindate) {
		this.idBegindate = idBegindate;
	}

	public String getIdTerm() {
		return idTerm;
	}

	public void setIdTerm(String idTerm) {
		this.idTerm = idTerm;
	}

	public String getTermFlag() {
		return termFlag;
	}

	public void setTermFlag(String termFlag) {
		this.termFlag = termFlag;
	}

	public int getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(int errorNo) {
		this.errorNo = errorNo;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	





}
