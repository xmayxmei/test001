package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;
/**
 * 股票买入，卖出，新股申购，新股配号 
 * @author wenwenbin
 * 交易类别：
 * 1 ：买入
 * 2 ：卖出
 * 8 : 配号
 */
public class PlaceOrderBiz extends DataHandler implements IBiz {

	private final static int iFuncNo = FUNConstants.FUN_SECU_ENTRUST_TRADE;
	@Override
	protected void buildField() {
		// TODO Auto-generated method stub
		fieldMap=new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH,  "clientId");//客户号
		fieldMap.put(FIDConstants.FID_JYMM,  "jymm");//客户号
		fieldMap.put(FIDConstants.FID_JMLX, "0");//加密类型
		fieldMap.put(FIDConstants.FID_GDH, 	"gdh");//股东号
		fieldMap.put(FIDConstants.FID_JYS,  "jys");//交易所
		fieldMap.put(FIDConstants.FID_ZQDM, "zqdm");//证券代码即股票号码
		fieldMap.put(FIDConstants.FID_JYLB, "jylb");//交易类别
		fieldMap.put(FIDConstants.FID_WTSL, "wtsl");//委托数量
		fieldMap.put(FIDConstants.FID_WTJG, "wtjg");//委托价格
		fieldMap.put(FIDConstants.FID_DDLX, "ddlx");//订单类型
		asResponseField = new int[][] {
			{FIDConstants.FID_CODE, FieldType.FieldString},//消息CODE
			{FIDConstants.FID_MESSAGE, FieldType.FieldString},//消息
			{FIDConstants.FID_WTH,     FieldType.FieldString},//委托号
		};
		isDataSet = false;
		iFunID = iFuncNo;
	}
}
