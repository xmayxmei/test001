package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;
/**
 * 历史成交查询
 * @author wenwenbin
 *
 */
public class HistoryCJBiz  extends DataHandler implements IBiz{
	private final static int iFuncNo = FUNConstants.FUN_SECU_LIST_JGMXLS_KH;
	protected void buildField() {
		fieldMap=new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH,  "clientId");//客户号
		fieldMap.put(FIDConstants.FID_KSRQ, "startDate");//开始日期
		fieldMap.put(FIDConstants.FID_JSRQ, "endDate");//结束日期
		asResponseField = new int[][] {
			{FIDConstants.FID_CJRQ,   FieldType.FieldString},//成交日期
			{FIDConstants.FID_JYS,    FieldType.FieldString},//交易所
			{FIDConstants.FID_GDH,    FieldType.FieldString},//股东号
			{FIDConstants.FID_WTH,    FieldType.FieldString},//委托号
			{FIDConstants.FID_ZQDM,   FieldType.FieldString},//证券代码
			{FIDConstants.FID_ZQMC,   FieldType.FieldString},//证券名称
			{FIDConstants.FID_WTLB,   FieldType.FieldString},//交易类别
			{FIDConstants.FID_CJSL,   FieldType.FieldString},//成交数量
			{FIDConstants.FID_CJSJ,   FieldType.FieldString},//成交时间
			{FIDConstants.FID_CJJG,   FieldType.FieldString},//成交价格
			{FIDConstants.FID_BZ,     FieldType.FieldString},//币种
			{FIDConstants.FID_CJBH,   FieldType.FieldString},//成交编号
			{FIDConstants.FID_JSJ,    FieldType.FieldString},//结算价
			{FIDConstants.FID_CJJE,   FieldType.FieldString},//成交金额
			{FIDConstants.FID_BZS1,   FieldType.FieldString},//标准佣金
			{FIDConstants.FID_S1,     FieldType.FieldString},//实收佣金
			{FIDConstants.FID_S2,     FieldType.FieldString},//印花税
			{FIDConstants.FID_S3,     FieldType.FieldString},//过户费
			{FIDConstants.FID_S4,     FieldType.FieldString},//附加费
			{FIDConstants.FID_S5,     FieldType.FieldString},//结算费
			{FIDConstants.FID_S6,     FieldType.FieldString},//其它费
			{FIDConstants.FID_CJBH,   FieldType.FieldString},//成交编号
			{FIDConstants.FID_CJBS,   FieldType.FieldString},//成交笔数
			
			{FIDConstants.FID_YSJE,    FieldType.FieldString},//应收金额
			{FIDConstants.FID_BCZQSL,  FieldType.FieldString},//证券余额
			{FIDConstants.FID_BCZJYE,  FieldType.FieldString},//资金余额
			{FIDConstants.FID_JSRQ,    FieldType.FieldString},//交收日期
		};
		iFunID = iFuncNo;
	}
}
