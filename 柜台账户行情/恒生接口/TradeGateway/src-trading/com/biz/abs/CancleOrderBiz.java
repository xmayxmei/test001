package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;

public class CancleOrderBiz extends DataHandler implements IBiz {
	private final static int iFuncNo = FUNConstants.FUN_SECU_ENTRUST_WITHDRAW;

	protected void buildField() {
		// TODO Auto-generated method stub
		fieldMap = new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH, "clientId");
		fieldMap.put(FIDConstants.FID_GDH, "gdh");
		fieldMap.put(FIDConstants.FID_WTH, "wth");
		fieldMap.put(FIDConstants.FID_JYS, "jys");
		// fieldMap.put(FIDConstants.FID_EXFLG, "1");
		asResponseField = new int[][] {
			{ FIDConstants.FID_CODE, 	FieldType.FieldString },//
			{ FIDConstants.FID_MESSAGE, FieldType.FieldString },//
		// {FIDConstants.FID_COUNT, FieldType.FieldString},//资金账户
		// {FIDConstants.FID_WTPCH, FieldType.FieldString},//币种
		// {FIDConstants.FID_EN_WTH, FieldType.FieldString},//收入金额
		};
		iFunID = iFuncNo;
		isDataSet = false;
	}
}
