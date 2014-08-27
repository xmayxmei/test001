package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;
/**
 * 当日成交查询
 * @author 温文彬
 */
public class TodayCJBiz  extends DataHandler implements IBiz{
	private final static int iFuncNo = FUNConstants.FUN_SECU_LIST_FBCJ;
	@Override
	protected void buildField() {
		fieldMap=new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH,  "clientId");//客户号
		//fieldMap.put(FIDConstants.FID_EN_WTH, "enwth");//委托号范围，多个委托号可以用逗号分隔
		//fieldMap.put(FIDConstants.FID_BZ,   "bz");
		//fieldMap.put(FIDConstants.FID_EXFLG, "1");
		asResponseField = new int[][] {
			{FIDConstants.FID_JYS,    FieldType.FieldString},//交易所
			{FIDConstants.FID_GDH,    FieldType.FieldString},//股东号
			{FIDConstants.FID_WTH,    FieldType.FieldString},//委托号
			{FIDConstants.FID_SBWTH,  FieldType.FieldString},//申报委托号
			{FIDConstants.FID_CXBZ,   FieldType.FieldString},//撤销标志
			{FIDConstants.FID_ZQDM,   FieldType.FieldString},//证券代码
			{FIDConstants.FID_ZQMC,   FieldType.FieldString},//证券名称
			{FIDConstants.FID_WTLB,   FieldType.FieldString},//交易类别
			{FIDConstants.FID_CJSL,   FieldType.FieldString},//成交数量
			{FIDConstants.FID_CJJE,   FieldType.FieldString},//成交金额
			{FIDConstants.FID_CJJG,   FieldType.FieldString},//成交均价
			
			{FIDConstants.FID_BZ,     FieldType.FieldString},//币种
			{FIDConstants.FID_QSZJ,   FieldType.FieldString},//清算资金
			{FIDConstants.FID_CJBH,   FieldType.FieldString},//成交编号
			{FIDConstants.FID_CJSJ,   FieldType.FieldString},//成交时间
		};
		iFunID = iFuncNo;
	}
}
