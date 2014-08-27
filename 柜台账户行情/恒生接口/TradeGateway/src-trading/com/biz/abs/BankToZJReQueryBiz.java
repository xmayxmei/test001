package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;

/**
 * <code>BankToZJReQueryBiz</code> 
 * 
 */
public class BankToZJReQueryBiz extends DataHandler implements IBiz {
	private final static int iFuncNo = FUNConstants.FUN_WBZH_LIST_YZZZDY;

	@Override
	protected void buildField() {
		// TODO Auto-generated method stub
		fieldMap = new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH, "clientId");// 客户号
		asResponseField = new int[][] {
				{ FIDConstants.FID_YHZH, FieldType.FieldString },// 银行账户
				{ FIDConstants.FID_YHDM, FieldType.FieldString },// 银行代码
				{ FIDConstants.FID_ZJZH, FieldType.FieldString },// 资金账户
				{ FIDConstants.FID_BZ,   FieldType.FieldString },// 币种
		};
		iFunID = iFuncNo;
	}
}