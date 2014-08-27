package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;

public class HoldStockBiz extends DataHandler implements IBiz {
	private final static int iFuncNo = FUNConstants.FUN_SECU_LIST_HOLDSTOCK;
	@Override
	protected void buildField() {
		fieldMap=new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH,  "clientId");//客户号
		//fieldMap.put(FIDConstants.FID_GDH, "gdh");//股东号
		//fieldMap.put(FIDConstants.FID_JYS, "jys");//交易所
		//fieldMap.put(FIDConstants.FID_BZ,   "bz");
		fieldMap.put(FIDConstants.FID_EXFLG, "1");
		asResponseField = new int[][] {
			{FIDConstants.FID_GDH,    FieldType.FieldString},//股东号
			{FIDConstants.FID_JYS,    FieldType.FieldString},//交易所
			{FIDConstants.FID_ZQDM,   FieldType.FieldString},//证券代码
			{FIDConstants.FID_ZQMC,   FieldType.FieldString},//证券名称
			{FIDConstants.FID_BZ,     FieldType.FieldString},//币种
			{FIDConstants.FID_ZQSL,     FieldType.FieldString},//数量
			{FIDConstants.FID_DRMCWTSL, FieldType.FieldString},//当天卖出委托数量
			{FIDConstants.FID_DRMRCJSL,  FieldType.FieldString},//当天买入成交
			{FIDConstants.FID_DRMCCJSL, FieldType.FieldString},//当天卖出成交
			
			{FIDConstants.FID_KSGSL,   FieldType.FieldString},//可申购数量(对成份股)
			
			{FIDConstants.FID_KMCSL,  FieldType.FieldString},//可卖出数量
			{FIDConstants.FID_DJSL,   FieldType.FieldString},//冻结数量
			{FIDConstants.FID_WJSSL,  FieldType.FieldString},//未交收数量，当前持仓数量，包括了当天买入成交及卖出成交部分
			{FIDConstants.FID_KCRQ,   FieldType.FieldString},//开仓日期
			{FIDConstants.FID_ZXSZ,   FieldType.FieldString},//最新市值
			{FIDConstants.FID_ZXJ,    FieldType.FieldString},//最新价
			{FIDConstants.FID_MRJJ,   FieldType.FieldString},//买入均价
			{FIDConstants.FID_CCJJ,   FieldType.FieldString},//成本价
			{FIDConstants.FID_BBJ,    FieldType.FieldString},//保本价
			{FIDConstants.FID_FDYK,   FieldType.FieldString},//浮动盈亏
			
			{FIDConstants.FID_LJYK,    FieldType.FieldString},//累计盈亏
			{FIDConstants.FID_TBCBJ,   FieldType.FieldString},//摊薄成本价
			{FIDConstants.FID_TBBBJ,   FieldType.FieldString},//摊薄保本价
			{FIDConstants.FID_TBFDYK,  FieldType.FieldString},//摊薄浮动盈亏
		};
		iFunID = iFuncNo;
	}
}
