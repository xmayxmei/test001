package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;

/**
 * 此类用于资金变动明细查询即资金流水
 * @author wenwenbin
 *
 */
public class UserZJMXBiz extends DataHandler implements IBiz{
	private final static int iFuncNo = FUNConstants.FUN_ACCOUNT_LIST_ZJMXLS_KH;
	//private final static int iFuncNo = FUNConstants.FUN_ACCOUNT_LIST_ZJMXLS_KH;
	@Override
	protected void buildField() {
		// TODO Auto-generated method stub
		fieldMap=new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH,  "clientId");
		fieldMap.put(FIDConstants.FID_KSRQ, "startDate");
		fieldMap.put(FIDConstants.FID_JSRQ, "endDate");
		fieldMap.put(FIDConstants.FID_BZ,   "bz");
		//fieldMap.put(FIDConstants.FID_EXFLG, "1");
		asResponseField = new int[][] {
			{FIDConstants.FID_RQ,      FieldType.FieldString},//发生日期
			{FIDConstants.FID_FSSJ,    FieldType.FieldString},//发生时间
			{FIDConstants.FID_ZJZH,    FieldType.FieldString},//资金账户
			{FIDConstants.FID_BZ,      FieldType.FieldString},//币种
			{FIDConstants.FID_SRJE,    FieldType.FieldString},//收入金额
			{FIDConstants.FID_FCJE,    FieldType.FieldString},//付出金额
			{FIDConstants.FID_BCZJYE,  FieldType.FieldString},//资金余额
			{FIDConstants.FID_ZY,      FieldType.FieldString},//变动说明
//			{FIDConstants.FID_LSH,    FieldType.FieldString},//流水号
//			{FIDConstants.FID_RQ,     FieldType.FieldString},//发生日期
//			{FIDConstants.FID_FSSJ,   FieldType.FieldString},//发生时间
//			{FIDConstants.FID_ZJZH,   FieldType.FieldString},//资金账户
//			{FIDConstants.FID_YWKM,   FieldType.FieldString},//科目
//			{FIDConstants.FID_BZ,     FieldType.FieldString},//币种
//			{FIDConstants.FID_SRJE,   FieldType.FieldString},//收入
//			{FIDConstants.FID_FCJE,   FieldType.FieldString},//付出
//			{FIDConstants.FID_BCZJYE, FieldType.FieldString},//发生后资金余额
//			{FIDConstants.FID_ZY,     FieldType.FieldString},//说明
//			{FIDConstants.FID_FSJE,   FieldType.FieldString},//发生额： FID_SRJE-FID_FCJE
			};
		iFunID = iFuncNo;
	}
}
