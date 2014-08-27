package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;
/**
 * 用户资金查询
 * @author wenwenbin
 *
 */
public class UserZJBiz extends DataHandler implements IBiz{
	private final static int iFuncNo = FUNConstants.FUN_ACCOUNT_LIST_ZJXXBYKHH;
	@Override
	protected void buildField() {
		fieldMap=new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH,  "clientId");
		fieldMap.put(FIDConstants.FID_EXFLG, "1");
		asResponseField = new int[][] {
			{FIDConstants.FID_ZJZH,    FieldType.FieldString},//资金账户
			{FIDConstants.FID_BZ,      FieldType.FieldString},//币种
			{FIDConstants.FID_ZHYE,    FieldType.FieldString},//资金余额
			{FIDConstants.FID_DJJE,    FieldType.FieldString},//冻结资金
			
			{FIDConstants.FID_KYZJ,    FieldType.FieldString},//可用资金
			{FIDConstants.FID_KQZJ,    FieldType.FieldString},//可取资金
			{FIDConstants.FID_ZXSZ,    FieldType.FieldString},//市值
			{FIDConstants.FID_ZZC,     FieldType.FieldString},//总资产
		};
		iFunID = iFuncNo;
	}
}
