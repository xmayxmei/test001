package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;

/**
 * 检查资金密码
 * @author wenwenbin
 *
 */
public class CheckFundPwdBiz  extends DataHandler implements IBiz{
	private final static int iFuncNo = FUNConstants.FUN_CUSTOM_CHKFUNDWD;
	@Override
	protected void buildField() {
		fieldMap=new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH,  "clientId");
		fieldMap.put(FIDConstants.FID_GDH, "gdh");
		fieldMap.put(FIDConstants.FID_JYMM, "jymm");
		fieldMap.put(FIDConstants.FID_JMLX, "0");//加密方式，0  简单加密算法  1  标准加密算法 
		asResponseField = new int[][] {
			{FIDConstants.FID_CODE,    FieldType.FieldString},//
			{FIDConstants.FID_MESSAGE, FieldType.FieldString},//
		};
		iFunID = iFuncNo;
	}

}
