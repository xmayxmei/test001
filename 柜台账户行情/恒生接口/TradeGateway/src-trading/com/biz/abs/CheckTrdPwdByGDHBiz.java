package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;

public class CheckTrdPwdByGDHBiz extends DataHandler implements IBiz {
	private final static int iFuncNo = FUNConstants.FUN_CUSTOM_CHKTRDPWD;

	protected void buildField() {
		fieldMap = new HashMap<Integer, String>();
		// fieldMap.put(FIDConstants.FID_KHH, "clientId");
		fieldMap.put(FIDConstants.FID_GDH, "gdh");
		fieldMap.put(FIDConstants.FID_JYS, "jys");
		// fieldMap.put(FIDConstants.FID_KHKH, "khkh");
		fieldMap.put(FIDConstants.FID_JYMM, "jymm");
		// fieldMap.put(FIDConstants.FID_TYPE, "type");
		fieldMap.put(FIDConstants.FID_JMLX, "0");// 加密方式，0 简单加密算法 1 标准加密算法
		asResponseField = new int[][] {
			{ FIDConstants.FID_CODE, 	FieldType.FieldString },//
			{ FIDConstants.FID_MESSAGE, FieldType.FieldString },//
			{ FIDConstants.FID_GDH, 	FieldType.FieldString },// 股东号，使用股东帐号进行身份认证成功的话，会返回完整的股东帐号及交易所
			{ FIDConstants.FID_JYS, 	FieldType.FieldString },// 交易所
			{ FIDConstants.FID_KHH, 	FieldType.FieldString },// 客户号
			{ FIDConstants.FID_KHQZ, 	FieldType.FieldString },// 群组
			{ FIDConstants.FID_YYB, 	FieldType.FieldString },// 营业部
			{ FIDConstants.FID_XTCSBZ,  FieldType.FieldString },// 测试状态：1测试状态，0正常交易状态
			{ FIDConstants.FID_KHXM, 	FieldType.FieldString },// 姓名
			{ FIDConstants.FID_KHZT, 	FieldType.FieldString },// 客户状态，
			{ FIDConstants.FID_TZZFL,	 FieldType.FieldString },// 客户风险承受能力，有些功能码调用时要用此字段作为输入参数
		};
		isDataSet = false;
		iFunID = iFuncNo;
	}
}