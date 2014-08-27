/**
 * 
 */
package com.cfwx.rox.businesssync.market.entity;

import java.io.Serializable;

/**
 * @author J.C.J
 * 
 *  2013-11-18
 */
public class StockMarket implements Serializable {

	private static final long serialVersionUID = 5398207208241479752L;

	// 序号 名 称 变 量 类 型 格式 单位 备 注
	private long time;// 1. 时间 time long yyyyMMddHHMMss
	private long price;// 2. 最新 price long X1000 0—2147483
	private long limit;// 3. 涨跌额 limit long 0—2147483最新价-昨收价
	private float range;// 4. 涨跌幅 range Float 百分比 最新价/昨收价
	private long amount;// 5. 总额 amount long 0—4294亿元
	private float volume;// 6. 总量 volume Float 0.00D 0—4294亿股
	private long before;// 7. 昨收 before Long X1000 0—2147483
	private long open;// 8. 今开 open Long X1000 0—2147483
	private long high;// 9. 最高 high Long X1000 0—2147483
	private long low;// 10. 最低 low Long X1000 0—2147483
	private float change;// 11. 换手 change Float 0.00D 百分比 总量/流通股
	private float earning;// 12. 市盈 earning float 0.00D 百分比
							// 现价/动态每股收益，动态每股收益由本年度最新财务数据计算，静态市盈率=现价/上一年度每股收益，目前主流是动态
	private long average;// 13. 均价 average long X1000 金额/总量
	private float weibi;// 14. 委比 weibi float 0.00F 百分比 （总委买量-总委卖量）/（总委买量+总委卖量）
	private float liangbi;// 15. 量比 liangbi float 0.00F 百分比 总量/前五日平均成交量
	private long capital;// 16. 股本 capital long 总股本
	private float value;// 17. 市值 value Float 0.00D 现价*总股本
	private String name;// 18. 证券名称 name String
	private String code;// 19. 股票唯一标识码 code String 冗余字段，为了数据交互
	private int market;// 20. 交易市场 market int 0：深圳证券交易所，1：上海证券交易所
	private int type;// 21. 证券类型 type int 对应证券类型配置信息
	private int pbuy1;// 22. 委买价1 Pbuy1 int X1000 0-16777元
	private long vbuy1;// 23. 委买量1 Vbuy1 long 手
	private int pbuy2;// 24. 委买价2 Pbuy2 int X1000 0-16777元
	private long vbuy2;// 25. 委买量2 Vbuy2 long 手 0-1677万手
	private int pbuy3;// 26. 委买价3 Pbuy3 int X1000 0-16777元
	private long vbuy3;// 27. 委买量3 Vbuy3 long 手 0-1677万手
	private int pbuy4;// 28. 委买价4 Pbuy4 int X1000 0-16777元
	private long vbuy4;// 29. 委买量4 Vbuy4 long 手 0-1677万手
	private int pbuy5;// 30. 委买价5 Pbuy5 int X1000 0-16777元
	private long vbuy5;// 31. 委买量5 Vbuy5 long 手 0-1677万手
	private int psell1;// 32. 委卖价1 Psell1 int X1000 0-16777元
	private long vsell1;// 33. 委卖量1 Vsell1 long 手
	private int psell2;// 34. 委卖价2 Psell2 int X1000 0-16777元
	private long vsell2;// 35. 委卖量2 Vsell2 long 手 0-1677万手
	private int psell3;// 36. 委卖价3 Psell3 int X1000 0-16777元
	private long vsell3;// 37. 委卖量3 Vsell3 long 手 0-1677万手
	private int psell4;// 38. 委卖价4 Psell4 int X1000 0-16777元
	private long vsell4;// 39. 委卖量4 Vsell4 long 手 0-1677万手
	private int psell5;// 40. 委卖价5 Psell5 int X1000 0-16777元
	private long vsell5;// 41. 委卖量5 Vsell5 long 手 0-1677万手
	private float jjgz;// 42. 基金模拟净值(权证溢价信息) Jjgz long 基金模拟净值：放大1000倍
						// 权证溢价：放大10000倍
	private int infotype;// 43. 信息属性 Infotype int
	private float accrual;// 44. 国债利息// 基金净值 Accrual long 国债利息 X1000 ,基金净值X10000
							// 国债利息放大1000倍,基金净值放大10000倍
							// 基金净值（3位表示，这里是低2位）
	private int rdp;// 45. RDP Rdp int
	private long records;// 46. 成交笔数 Records long 总笔数：0-16777216笔
							// 如为组合宝，此三字节数据定义见附表一
	private boolean sftp;// 47. 是否停牌 sfTp boolean false：非停牌 true：停牌
	private boolean sfsp;// 48. 是否做收盘作业 sfSp boolean false：要做 true：不做
	private boolean sfmc;// 49. 是否得到名称 sfmc boolean false：未得 true：已得
	private boolean sfcq;// 50. 是否除权 sfCq boolean false：否 true：是
	private boolean pt;// 51. 是否时额外增加的pt个股 sfpt boolean false:否，true：是

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public long getBefore() {
		return before;
	}

	public void setBefore(long before) {
		this.before = before;
	}

	public long getOpen() {
		return open;
	}

	public void setOpen(long open) {
		this.open = open;
	}

	public long getHigh() {
		return high;
	}

	public void setHigh(long high) {
		this.high = high;
	}

	public long getLow() {
		return low;
	}

	public void setLow(long low) {
		this.low = low;
	}

	public float getChange() {
		return change;
	}

	public void setChange(float change) {
		this.change = change;
	}

	public float getEarning() {
		return earning;
	}

	public void setEarning(float earning) {
		this.earning = earning;
	}

	public long getAverage() {
		return average;
	}

	public void setAverage(long average) {
		this.average = average;
	}

	public float getWeibi() {
		return weibi;
	}

	public void setWeibi(float weibi) {
		this.weibi = weibi;
	}

	public float getLiangbi() {
		return liangbi;
	}

	public void setLiangbi(float liangbi) {
		this.liangbi = liangbi;
	}

	public long getCapital() {
		return capital;
	}

	public void setCapital(long capital) {
		this.capital = capital;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getMarket() {
		return market;
	}

	public void setMarket(int market) {
		this.market = market;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPbuy1() {
		return pbuy1;
	}

	public void setPbuy1(int pbuy1) {
		this.pbuy1 = pbuy1;
	}

	public long getVbuy1() {
		return vbuy1;
	}

	public void setVbuy1(long vbuy1) {
		this.vbuy1 = vbuy1;
	}

	public int getPbuy2() {
		return pbuy2;
	}

	public void setPbuy2(int pbuy2) {
		this.pbuy2 = pbuy2;
	}

	public long getVbuy2() {
		return vbuy2;
	}

	public void setVbuy2(long vbuy2) {
		this.vbuy2 = vbuy2;
	}

	public int getPbuy3() {
		return pbuy3;
	}

	public void setPbuy3(int pbuy3) {
		this.pbuy3 = pbuy3;
	}

	public long getVbuy3() {
		return vbuy3;
	}

	public void setVbuy3(long vbuy3) {
		this.vbuy3 = vbuy3;
	}

	public int getPbuy4() {
		return pbuy4;
	}

	public void setPbuy4(int pbuy4) {
		this.pbuy4 = pbuy4;
	}

	public long getVbuy4() {
		return vbuy4;
	}

	public void setVbuy4(long vbuy4) {
		this.vbuy4 = vbuy4;
	}

	public int getPbuy5() {
		return pbuy5;
	}

	public void setPbuy5(int pbuy5) {
		this.pbuy5 = pbuy5;
	}

	public long getVbuy5() {
		return vbuy5;
	}

	public void setVbuy5(long vbuy5) {
		this.vbuy5 = vbuy5;
	}

	public int getPsell1() {
		return psell1;
	}

	public void setPsell1(int psell1) {
		this.psell1 = psell1;
	}

	public long getVsell1() {
		return vsell1;
	}

	public void setVsell1(long vsell1) {
		this.vsell1 = vsell1;
	}

	public int getPsell2() {
		return psell2;
	}

	public void setPsell2(int psell2) {
		this.psell2 = psell2;
	}

	public long getVsell2() {
		return vsell2;
	}

	public void setVsell2(long vsell2) {
		this.vsell2 = vsell2;
	}

	public int getPsell3() {
		return psell3;
	}

	public void setPsell3(int psell3) {
		this.psell3 = psell3;
	}

	public long getVsell3() {
		return vsell3;
	}

	public void setVsell3(long vsell3) {
		this.vsell3 = vsell3;
	}

	public int getPsell4() {
		return psell4;
	}

	public void setPsell4(int psell4) {
		this.psell4 = psell4;
	}

	public long getVsell4() {
		return vsell4;
	}

	public void setVsell4(long vsell4) {
		this.vsell4 = vsell4;
	}

	public int getPsell5() {
		return psell5;
	}

	public void setPsell5(int psell5) {
		this.psell5 = psell5;
	}

	public long getVsell5() {
		return vsell5;
	}

	public void setVsell5(long vsell5) {
		this.vsell5 = vsell5;
	}

	public float getJjgz() {
		return jjgz;
	}

	public void setJjgz(float jjgz) {
		this.jjgz = jjgz;
	}

	public int getInfotype() {
		return infotype;
	}

	public void setInfotype(int infotype) {
		this.infotype = infotype;
	}

	public float getAccrual() {
		return accrual;
	}

	public void setAccrual(float accrual) {
		this.accrual = accrual;
	}

	public int getRdp() {
		return rdp;
	}

	public void setRdp(int rdp) {
		this.rdp = rdp;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public boolean isSftp() {
		return sftp;
	}

	public void setSftp(boolean sftp) {
		this.sftp = sftp;
	}

	public boolean isSfsp() {
		return sfsp;
	}

	public void setSfsp(boolean sfsp) {
		this.sfsp = sfsp;
	}

	public boolean isSfmc() {
		return sfmc;
	}

	public void setSfmc(boolean sfmc) {
		this.sfmc = sfmc;
	}

	public boolean isSfcq() {
		return sfcq;
	}

	public void setSfcq(boolean sfcq) {
		this.sfcq = sfcq;
	}

	public boolean isPt() {
		return pt;
	}

	public void setPt(boolean pt) {
		this.pt = pt;
	}

}
