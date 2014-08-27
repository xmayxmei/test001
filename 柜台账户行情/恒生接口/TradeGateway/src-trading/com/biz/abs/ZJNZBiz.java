package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FieldType;

public class ZJNZBiz extends DataHandler implements IBiz{
	private final static int iFuncNo = 203526;
	@Override
	protected void buildField() {
		// TODO Auto-generated method stub
		fieldMap=new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH,    "clientId");//客户号
		fieldMap.put(FIDConstants.FID_ZJZH,   "zjzh");//转出方资金账户
		fieldMap.put(FIDConstants.FID_BZ,     "bz");//币种
		fieldMap.put(FIDConstants.FID_ZJZH_ZR,"zrzh");//转入方资金账户
		fieldMap.put(FIDConstants.FID_FSJE,   "fsje");//转帐金额
		fieldMap.put(FIDConstants.FID_ZY,     "zy");//转帐说明
		fieldMap.put(FIDConstants.FID_ZZXY,   "zzxy");//T+1自动转账标志，1表示自动转账，默认为0，表示T+1要手工再发起证转银
		fieldMap.put(FIDConstants.FID_ZJMM,   "zjmm");//资金密码
		fieldMap.put(FIDConstants.FID_JMLX,   "0");//加密类型
		asResponseField = new int[][] {
			{FIDConstants.FID_CODE,    FieldType.FieldString},//
			{FIDConstants.FID_MESSAGE, FieldType.FieldString},//
		};
		isDataSet = false;
		iFunID = iFuncNo;
	}
}