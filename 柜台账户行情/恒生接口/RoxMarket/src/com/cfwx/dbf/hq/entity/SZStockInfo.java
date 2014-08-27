package com.cfwx.dbf.hq.entity;

import java.io.Serializable;
/**
 * 深圳证券交易所股票信息
 * 本库的第一条记录为特殊记录，XXZQDM（字段1：证券代码）为
	“000000”，XXZQJC（字段2：证券简称）存放当前日期CCYYMMDD，XXBLDW（字
	段18：买数量单位）存放库更新时间，XXSLDW（字段19：卖数量单位）存放当
	日挂牌证券的数量。
	（2） XXZQDM（字段1：证券代码）为关键字，最左两位作为证券种类标识
	码（见表1-1），其中首位标识证券大类，第二位标识该大类下的衍生证券，证券
	代码的后四位为顺序码。
 *	@author J.C.J
 * 2013-9-18
 */
public class SZStockInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4480324380608420718L;
	//证券代码
	private String xxzqdm;
	//证券简称
	private String xxzqjc;
	//证券简称前缀
	private String xxjcqz;
	//英文简称
	private String xxywjc;
	//基础证券
	private String xxjczq;
	//isin 编码
	private String xxisin;
	//行业种类
	private String xxhyzl;
	//货币种类
	private String xxhbzl;
	//每股面值
	private String xxmgmz;
	//总发行量
	private String xxzfxl;
	//流通股数
	private String xxltgs;
	//上年每股利润
	private String xxsnlr;
	//本年每股利润
	private String xxbnlr;
	//基金份额累计净值
	private String xxljjz;
	//经手费率
	private String xxjsfl;
	//印花税率
	private String xxyhsl;
	//过户费率
	private String xxghfl;
	//上市日期
	private String xxssrq;
	//债券起息日
	private String xxqxrq;
	//到期/交割日
	private String xxdjrq;
	//交易单位
	private String xxjydw;
	//买数量单位
	private String xxbldw;
	//卖数量单位
	private String xxsldw;
	//每笔委托限量
	private String xxmbxl;
	//价格档位
	private String xxjgdw;
	//集合竞价限价参数
	private String xxjhcs;
	//连续竞价限价参数
	private String xxlxcs;
	//限价参数性质
	private String xxxjxz;
	//涨停价格
	private String xxztjg;
	//跌停价格
	private String xxdtjg;
	//大宗交易价格上限2
	private String xxjgsx;
	//大宗交易价格下限2
	private String xxjgxx;
	//债券折合比例
	private String xxzhbl;
	//担保物折算率
	private String xxdbzsl;
	//融资标的标志
	private String xxrzbd;
	//融券标的标志
	private String xxrqbd;
	//成份股标志
	private String xxcfbz;
	//做市商标志
	private String xxzsbz;
	//所属市场代码
	private String xxscdm;
	//证券类别
	private String xxzqlb;
	//证券级别
	private String xxzqjb;
	//证券状态 
	private String xxzqzt;
	//交易类型
	private String xxjylx;
	//产品交易阶段
	private String xxjyjd;
	//暂停交易标志
	private String xxtpbz;
	//融资交易状态
	private String xxrzzt;
	//融券交易状态
	private String xxrqzt;
	//融券卖出价格限制
	private String xxrqjx;
	//网络投票标志
	private String xxwltp;
	//其他业务状态
	private String xxywzt;
	//记录更新时间
	private String xxgxsj;
	//备用字段
	private String xxmark;
	//备用标志
	private String xxbybz;
	//更新日期
	private String date;
	//类型(0:指数;1:股票)
	private int type;
	//交易场所(0:上交所，1：深交所)
	private int tradingPlace;
	/**
	 * @return the xxzqdm
	 */
	public String getXxzqdm() {
		return xxzqdm;
	}
	/**
	 * @param xxzqdm the xxzqdm to set
	 */
	public void setXxzqdm(String xxzqdm) {
		this.xxzqdm = xxzqdm;
	}
	/**
	 * @return the xxzqjc
	 */
	public String getXxzqjc() {
		return xxzqjc;
	}
	/**
	 * @param xxzqjc the xxzqjc to set
	 */
	public void setXxzqjc(String xxzqjc) {
		this.xxzqjc = xxzqjc;
	}
	/**
	 * @return the xxjcqz
	 */
	public String getXxjcqz() {
		return xxjcqz;
	}
	/**
	 * @param xxjcqz the xxjcqz to set
	 */
	public void setXxjcqz(String xxjcqz) {
		this.xxjcqz = xxjcqz;
	}
	/**
	 * @return the xxywjc
	 */
	public String getXxywjc() {
		return xxywjc;
	}
	/**
	 * @param xxywjc the xxywjc to set
	 */
	public void setXxywjc(String xxywjc) {
		this.xxywjc = xxywjc;
	}
	/**
	 * @return the xxjczq
	 */
	public String getXxjczq() {
		return xxjczq;
	}
	/**
	 * @param xxjczq the xxjczq to set
	 */
	public void setXxjczq(String xxjczq) {
		this.xxjczq = xxjczq;
	}
	/**
	 * @return the xxisin
	 */
	public String getXxisin() {
		return xxisin;
	}
	/**
	 * @param xxisin the xxisin to set
	 */
	public void setXxisin(String xxisin) {
		this.xxisin = xxisin;
	}
	/**
	 * @return the xxhyzl
	 */
	public String getXxhyzl() {
		return xxhyzl;
	}
	/**
	 * @param xxhyzl the xxhyzl to set
	 */
	public void setXxhyzl(String xxhyzl) {
		this.xxhyzl = xxhyzl;
	}
	/**
	 * @return the xxhbzl
	 */
	public String getXxhbzl() {
		return xxhbzl;
	}
	/**
	 * @param xxhbzl the xxhbzl to set
	 */
	public void setXxhbzl(String xxhbzl) {
		this.xxhbzl = xxhbzl;
	}
	/**
	 * @return the xxmgmz
	 */
	public String getXxmgmz() {
		return xxmgmz;
	}
	/**
	 * @param xxmgmz the xxmgmz to set
	 */
	public void setXxmgmz(String xxmgmz) {
		this.xxmgmz = xxmgmz;
	}
	/**
	 * @return the xxzfxl
	 */
	public String getXxzfxl() {
		return xxzfxl;
	}
	/**
	 * @param xxzfxl the xxzfxl to set
	 */
	public void setXxzfxl(String xxzfxl) {
		this.xxzfxl = xxzfxl;
	}
	/**
	 * @return the xxltgs
	 */
	public String getXxltgs() {
		return xxltgs;
	}
	/**
	 * @param xxltgs the xxltgs to set
	 */
	public void setXxltgs(String xxltgs) {
		this.xxltgs = xxltgs;
	}
	/**
	 * @return the xxsnlr
	 */
	public String getXxsnlr() {
		return xxsnlr;
	}
	/**
	 * @param xxsnlr the xxsnlr to set
	 */
	public void setXxsnlr(String xxsnlr) {
		this.xxsnlr = xxsnlr;
	}
	/**
	 * @return the xxbnlr
	 */
	public String getXxbnlr() {
		return xxbnlr;
	}
	/**
	 * @param xxbnlr the xxbnlr to set
	 */
	public void setXxbnlr(String xxbnlr) {
		this.xxbnlr = xxbnlr;
	}
	/**
	 * @return the xxljjz
	 */
	public String getXxljjz() {
		return xxljjz;
	}
	/**
	 * @param xxljjz the xxljjz to set
	 */
	public void setXxljjz(String xxljjz) {
		this.xxljjz = xxljjz;
	}
	/**
	 * @return the xxjsfl
	 */
	public String getXxjsfl() {
		return xxjsfl;
	}
	/**
	 * @param xxjsfl the xxjsfl to set
	 */
	public void setXxjsfl(String xxjsfl) {
		this.xxjsfl = xxjsfl;
	}
	/**
	 * @return the xxyhsl
	 */
	public String getXxyhsl() {
		return xxyhsl;
	}
	/**
	 * @param xxyhsl the xxyhsl to set
	 */
	public void setXxyhsl(String xxyhsl) {
		this.xxyhsl = xxyhsl;
	}
	/**
	 * @return the xxghfl
	 */
	public String getXxghfl() {
		return xxghfl;
	}
	/**
	 * @param xxghfl the xxghfl to set
	 */
	public void setXxghfl(String xxghfl) {
		this.xxghfl = xxghfl;
	}
	/**
	 * @return the xxssrq
	 */
	public String getXxssrq() {
		return xxssrq;
	}
	/**
	 * @param xxssrq the xxssrq to set
	 */
	public void setXxssrq(String xxssrq) {
		this.xxssrq = xxssrq;
	}
	/**
	 * @return the xxqxrq
	 */
	public String getXxqxrq() {
		return xxqxrq;
	}
	/**
	 * @param xxqxrq the xxqxrq to set
	 */
	public void setXxqxrq(String xxqxrq) {
		this.xxqxrq = xxqxrq;
	}
	/**
	 * @return the xxdjrq
	 */
	public String getXxdjrq() {
		return xxdjrq;
	}
	/**
	 * @param xxdjrq the xxdjrq to set
	 */
	public void setXxdjrq(String xxdjrq) {
		this.xxdjrq = xxdjrq;
	}
	/**
	 * @return the xxjydw
	 */
	public String getXxjydw() {
		return xxjydw;
	}
	/**
	 * @param xxjydw the xxjydw to set
	 */
	public void setXxjydw(String xxjydw) {
		this.xxjydw = xxjydw;
	}
	/**
	 * @return the xxbldw
	 */
	public String getXxbldw() {
		return xxbldw;
	}
	/**
	 * @param xxbldw the xxbldw to set
	 */
	public void setXxbldw(String xxbldw) {
		this.xxbldw = xxbldw;
	}
	/**
	 * @return the xxsldw
	 */
	public String getXxsldw() {
		return xxsldw;
	}
	/**
	 * @param xxsldw the xxsldw to set
	 */
	public void setXxsldw(String xxsldw) {
		this.xxsldw = xxsldw;
	}
	/**
	 * @return the xxmbxl
	 */
	public String getXxmbxl() {
		return xxmbxl;
	}
	/**
	 * @param xxmbxl the xxmbxl to set
	 */
	public void setXxmbxl(String xxmbxl) {
		this.xxmbxl = xxmbxl;
	}
	/**
	 * @return the xxjgdw
	 */
	public String getXxjgdw() {
		return xxjgdw;
	}
	/**
	 * @param xxjgdw the xxjgdw to set
	 */
	public void setXxjgdw(String xxjgdw) {
		this.xxjgdw = xxjgdw;
	}
	/**
	 * @return the xxjhcs
	 */
	public String getXxjhcs() {
		return xxjhcs;
	}
	/**
	 * @param xxjhcs the xxjhcs to set
	 */
	public void setXxjhcs(String xxjhcs) {
		this.xxjhcs = xxjhcs;
	}
	/**
	 * @return the xxlxcs
	 */
	public String getXxlxcs() {
		return xxlxcs;
	}
	/**
	 * @param xxlxcs the xxlxcs to set
	 */
	public void setXxlxcs(String xxlxcs) {
		this.xxlxcs = xxlxcs;
	}
	/**
	 * @return the xxxjxz
	 */
	public String getXxxjxz() {
		return xxxjxz;
	}
	/**
	 * @param xxxjxz the xxxjxz to set
	 */
	public void setXxxjxz(String xxxjxz) {
		this.xxxjxz = xxxjxz;
	}
	/**
	 * @return the xxztjg
	 */
	public String getXxztjg() {
		return xxztjg;
	}
	/**
	 * @param xxztjg the xxztjg to set
	 */
	public void setXxztjg(String xxztjg) {
		this.xxztjg = xxztjg;
	}
	/**
	 * @return the xxdtjg
	 */
	public String getXxdtjg() {
		return xxdtjg;
	}
	/**
	 * @param xxdtjg the xxdtjg to set
	 */
	public void setXxdtjg(String xxdtjg) {
		this.xxdtjg = xxdtjg;
	}
	/**
	 * @return the xxjgsx
	 */
	public String getXxjgsx() {
		return xxjgsx;
	}
	/**
	 * @param xxjgsx the xxjgsx to set
	 */
	public void setXxjgsx(String xxjgsx) {
		this.xxjgsx = xxjgsx;
	}
	/**
	 * @return the xxjgxx
	 */
	public String getXxjgxx() {
		return xxjgxx;
	}
	/**
	 * @param xxjgxx the xxjgxx to set
	 */
	public void setXxjgxx(String xxjgxx) {
		this.xxjgxx = xxjgxx;
	}
	/**
	 * @return the xxzhbl
	 */
	public String getXxzhbl() {
		return xxzhbl;
	}
	/**
	 * @param xxzhbl the xxzhbl to set
	 */
	public void setXxzhbl(String xxzhbl) {
		this.xxzhbl = xxzhbl;
	}
	/**
	 * @return the xxdbzsl
	 */
	public String getXxdbzsl() {
		return xxdbzsl;
	}
	/**
	 * @param xxdbzsl the xxdbzsl to set
	 */
	public void setXxdbzsl(String xxdbzsl) {
		this.xxdbzsl = xxdbzsl;
	}
	/**
	 * @return the xxrzbd
	 */
	public String getXxrzbd() {
		return xxrzbd;
	}
	/**
	 * @param xxrzbd the xxrzbd to set
	 */
	public void setXxrzbd(String xxrzbd) {
		this.xxrzbd = xxrzbd;
	}
	/**
	 * @return the xxrqbd
	 */
	public String getXxrqbd() {
		return xxrqbd;
	}
	/**
	 * @param xxrqbd the xxrqbd to set
	 */
	public void setXxrqbd(String xxrqbd) {
		this.xxrqbd = xxrqbd;
	}
	/**
	 * @return the xxcfbz
	 */
	public String getXxcfbz() {
		return xxcfbz;
	}
	/**
	 * @param xxcfbz the xxcfbz to set
	 */
	public void setXxcfbz(String xxcfbz) {
		this.xxcfbz = xxcfbz;
	}
	/**
	 * @return the xxzsbz
	 */
	public String getXxzsbz() {
		return xxzsbz;
	}
	/**
	 * @param xxzsbz the xxzsbz to set
	 */
	public void setXxzsbz(String xxzsbz) {
		this.xxzsbz = xxzsbz;
	}
	/**
	 * @return the xxscdm
	 */
	public String getXxscdm() {
		return xxscdm;
	}
	/**
	 * @param xxscdm the xxscdm to set
	 */
	public void setXxscdm(String xxscdm) {
		this.xxscdm = xxscdm;
	}
	/**
	 * @return the xxzqlb
	 */
	public String getXxzqlb() {
		return xxzqlb;
	}
	/**
	 * @param xxzqlb the xxzqlb to set
	 */
	public void setXxzqlb(String xxzqlb) {
		this.xxzqlb = xxzqlb;
	}
	/**
	 * @return the xxzqjb
	 */
	public String getXxzqjb() {
		return xxzqjb;
	}
	/**
	 * @param xxzqjb the xxzqjb to set
	 */
	public void setXxzqjb(String xxzqjb) {
		this.xxzqjb = xxzqjb;
	}
	/**
	 * @return the xxzqzt
	 */
	public String getXxzqzt() {
		return xxzqzt;
	}
	/**
	 * @param xxzqzt the xxzqzt to set
	 */
	public void setXxzqzt(String xxzqzt) {
		this.xxzqzt = xxzqzt;
	}
	/**
	 * @return the xxjylx
	 */
	public String getXxjylx() {
		return xxjylx;
	}
	/**
	 * @param xxjylx the xxjylx to set
	 */
	public void setXxjylx(String xxjylx) {
		this.xxjylx = xxjylx;
	}
	/**
	 * @return the xxjyjd
	 */
	public String getXxjyjd() {
		return xxjyjd;
	}
	/**
	 * @param xxjyjd the xxjyjd to set
	 */
	public void setXxjyjd(String xxjyjd) {
		this.xxjyjd = xxjyjd;
	}
	/**
	 * @return the xxtpbz
	 */
	public String getXxtpbz() {
		return xxtpbz;
	}
	/**
	 * @param xxtpbz the xxtpbz to set
	 */
	public void setXxtpbz(String xxtpbz) {
		this.xxtpbz = xxtpbz;
	}
	/**
	 * @return the xxrzzt
	 */
	public String getXxrzzt() {
		return xxrzzt;
	}
	/**
	 * @param xxrzzt the xxrzzt to set
	 */
	public void setXxrzzt(String xxrzzt) {
		this.xxrzzt = xxrzzt;
	}
	/**
	 * @return the xxrqzt
	 */
	public String getXxrqzt() {
		return xxrqzt;
	}
	/**
	 * @param xxrqzt the xxrqzt to set
	 */
	public void setXxrqzt(String xxrqzt) {
		this.xxrqzt = xxrqzt;
	}
	/**
	 * @return the xxrqjx
	 */
	public String getXxrqjx() {
		return xxrqjx;
	}
	/**
	 * @param xxrqjx the xxrqjx to set
	 */
	public void setXxrqjx(String xxrqjx) {
		this.xxrqjx = xxrqjx;
	}
	/**
	 * @return the xxwltp
	 */
	public String getXxwltp() {
		return xxwltp;
	}
	/**
	 * @param xxwltp the xxwltp to set
	 */
	public void setXxwltp(String xxwltp) {
		this.xxwltp = xxwltp;
	}
	/**
	 * @return the xxywzt
	 */
	public String getXxywzt() {
		return xxywzt;
	}
	/**
	 * @param xxywzt the xxywzt to set
	 */
	public void setXxywzt(String xxywzt) {
		this.xxywzt = xxywzt;
	}
	/**
	 * @return the xxgxsj
	 */
	public String getXxgxsj() {
		return xxgxsj;
	}
	/**
	 * @param xxgxsj the xxgxsj to set
	 */
	public void setXxgxsj(String xxgxsj) {
		this.xxgxsj = xxgxsj;
	}
	/**
	 * @return the xxmark
	 */
	public String getXxmark() {
		return xxmark;
	}
	/**
	 * @param xxmark the xxmark to set
	 */
	public void setXxmark(String xxmark) {
		this.xxmark = xxmark;
	}
	/**
	 * @return the xxbybz
	 */
	public String getXxbybz() {
		return xxbybz;
	}
	/**
	 * @param xxbybz the xxbybz to set
	 */
	public void setXxbybz(String xxbybz) {
		this.xxbybz = xxbybz;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the tradingPlace
	 */
	public int getTradingPlace() {
		return tradingPlace;
	}
	/**
	 * @param tradingPlace the tradingPlace to set
	 */
	public void setTradingPlace(int tradingPlace) {
		this.tradingPlace = tradingPlace;
	}
	
}
