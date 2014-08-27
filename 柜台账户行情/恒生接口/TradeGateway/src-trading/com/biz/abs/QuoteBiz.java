package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;

public class QuoteBiz extends DataHandler implements IBiz{
	private final static int iFuncNo = FUNConstants.FUN_QUOTE_GETHQ;
	@Override
	protected void buildField() {
		fieldMap=new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_ZQDM,  "zqdm");//客户号
		//fieldMap.put(FIDConstants.FID_SQH,  "sqh");//申请号
		
		asResponseField = new int[][] {
			{FIDConstants.FID_JYS,      FieldType.FieldString},//交易所
			{FIDConstants.FID_ZQMC,     FieldType.FieldString},//证券名称
			{FIDConstants.FID_BZ,     	FieldType.FieldString},//币种
			{FIDConstants.FID_JYDW,     FieldType.FieldString},//数量单位
			{FIDConstants.FID_TPBZ,     FieldType.FieldString},//1:停牌  0:正常
			{FIDConstants.FID_ZXJ,     	FieldType.FieldString},//最新价
			{FIDConstants.FID_ZSP,     	FieldType.FieldString},//昨收盘 
			{FIDConstants.FID_JKP,   	FieldType.FieldString},//今开盘
			{FIDConstants.FID_JYJW,     FieldType.FieldString},//交易价位
			{FIDConstants.FID_JJJYBZ,   FieldType.FieldString},//是否净价交易，一般债券为净价交易，1表示净价，0表示否失败
			{FIDConstants.FID_LXBJ,     FieldType.FieldString},//债券利息报价
			{FIDConstants.FID_ZDBJ,    	FieldType.FieldString},//最低报价
			
			{FIDConstants.FID_ZGBJ,    	FieldType.FieldString},//最高报价
			{FIDConstants.FID_CJSL,    	FieldType.FieldString},//总成交数量
			{FIDConstants.FID_CJJE,    	FieldType.FieldString},//总成交金额
			{FIDConstants.FID_ZXZS,    	FieldType.FieldString},//本市场综合指数
			{FIDConstants.FID_MRJG1,    FieldType.FieldString},//买一
			{FIDConstants.FID_MRJG2,    FieldType.FieldString},//买二
			{FIDConstants.FID_MRJG3,    FieldType.FieldString},//买三
			{FIDConstants.FID_MRJG4,    FieldType.FieldString},//买四
			{FIDConstants.FID_MRJG5,    FieldType.FieldString},//买五
			{FIDConstants.FID_MCJG1,    FieldType.FieldString},//卖一
			{FIDConstants.FID_MCJG2,    FieldType.FieldString},//卖二
			{FIDConstants.FID_MCJG3,    FieldType.FieldString},//卖三
			{FIDConstants.FID_MCJG4,    FieldType.FieldString},//卖四
			{FIDConstants.FID_MCJG5,    FieldType.FieldString},//卖五
			
			{FIDConstants.FID_MRSL1,    FieldType.FieldString},//买量一
			{FIDConstants.FID_MRSL2,    FieldType.FieldString},//买量二
			{FIDConstants.FID_MRSL3,    FieldType.FieldString},//买量三
			{FIDConstants.FID_MRSL4,    FieldType.FieldString},//买量四
			{FIDConstants.FID_MRSL5,    FieldType.FieldString},//买量五
			{FIDConstants.FID_MCSL1,    FieldType.FieldString},//卖量一
			{FIDConstants.FID_MCSL2,    FieldType.FieldString},//卖量二
			{FIDConstants.FID_MCSL3,    FieldType.FieldString},//卖量三
			{FIDConstants.FID_MCSL4,    FieldType.FieldString},//卖量四
			{FIDConstants.FID_MCSL5,    FieldType.FieldString},//卖量五
			{FIDConstants.FID_ZQLB,     FieldType.FieldString},//证券类别
		};
		iFunID = iFuncNo;
	}
}