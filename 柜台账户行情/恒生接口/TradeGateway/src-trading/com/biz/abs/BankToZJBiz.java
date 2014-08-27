package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;
/**
 * 银行转证券 银行到证券
 * @author wenwenbin
 *
 */
public class BankToZJBiz  extends  DataHandler implements IBiz{
	private final static int iFuncNo = FUNConstants.FUN_WBZH_TRANSFER_SAVING_ZQ;
	@Override
	protected void buildField() {
		// TODO Auto-generated method stub
		fieldMap=new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH,  "clientId");//客户号
		fieldMap.put(FIDConstants.FID_ZJZH,  "zjzh");//资金帐号
		fieldMap.put(FIDConstants.FID_BZ,    "bz");//币种
		fieldMap.put(FIDConstants.FID_YHZH,  "yhzh");//银行帐户
		fieldMap.put(FIDConstants.FID_YHDM,  "yhdm");//银行代码
		fieldMap.put(FIDConstants.FID_ZZJE,  "zzje");//转帐金额
		fieldMap.put(FIDConstants.FID_WBZHMM,"wbzhmm");//银行密码
		fieldMap.put(FIDConstants.FID_JMLX,   "0");
		asResponseField = new int[][] {
			{FIDConstants.FID_CODE,    FieldType.FieldString},//
			{FIDConstants.FID_MESSAGE, FieldType.FieldString},//
			{FIDConstants.FID_SQH,     FieldType.FieldString},//申请号
		};
		isDataSet = false;
		iFunID = iFuncNo;
	}
}
