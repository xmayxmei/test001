package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;
/**
 * 客户基本信息查询	
 * 风险承受能力评测(FID_KHJF)	
 * @author wenwenbin
 *
 */
public class UserBaseInfBiz  extends DataHandler  implements IBiz{
	private final static int iFuncNo = FUNConstants.FUN_CUSTOM_GET_CUSTINFO;
	@Override
	protected void buildField() {
		fieldMap=new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH,  "clientId");//客户号
		fieldMap.put(FIDConstants.FID_EXFLG, "1");
		asResponseField = new int[][] {
			{FIDConstants.FID_CODE,   FieldType.FieldString},//>0 成功 <=0 失败
			{FIDConstants.FID_MESSAGE,FieldType.FieldString},//返回信息
			{FIDConstants.FID_FWXM,   FieldType.FieldString},//服务项目范围,FLDM=FWXM
			{FIDConstants.FID_WTFS,   FieldType.FieldString},//委托方式范围,FLDM=WTFS
			{FIDConstants.FID_ZJBH,   FieldType.FieldString},//证件
			{FIDConstants.FID_YYB,    FieldType.FieldString},//营业部
			{FIDConstants.FID_KHQZ,   FieldType.FieldString},//客户群组
			{FIDConstants.FID_KHKH,   FieldType.FieldString},//客户卡号
			{FIDConstants.FID_KHXM,   FieldType.FieldString},//客户姓名
			{FIDConstants.FID_KHZT,   FieldType.FieldString},//状态
			{FIDConstants.FID_KHJF,   FieldType.FieldString},//证券风险承受能力，FLDM=KHJF
			{FIDConstants.FID_TZZFL,  FieldType.FieldString},//基金风险承受能力， FLDM=TZZFL
		};
		isDataSet = false;
		iFunID = iFuncNo;
	}
}
