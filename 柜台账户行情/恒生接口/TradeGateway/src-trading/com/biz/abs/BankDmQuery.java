package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FieldType;

public class BankDmQuery extends DataHandler implements IBiz {
	private final static int iFuncNo = 103010;

	@Override
	protected void buildField() {
		fieldMap = new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_JGLB, "jglb");// 机构类别：1为B股转账， 3为存管银行代码
				
		asResponseField = new int[][] {
				{FIDConstants.FID_JGLB,  FieldType.FieldString },//机构类别
				{ FIDConstants.FID_JGDM, FieldType.FieldString },//机构代码
				{ FIDConstants.FID_JGJC, FieldType.FieldString },//机构简称
		};
		
		iFunID = iFuncNo;
	}
}