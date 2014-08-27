package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;
/**
 * 转帐查询 
 * @author wenwenbin
 *
 */
public class ZJToBankQueryBiz extends DataHandler implements IBiz {
	private final static int iFuncNo = FUNConstants.FUN_WBZH_LIST_ZJJYSQ_ZQ;
	@Override
	protected void buildField() {
		// TODO Auto-generated method stub
		fieldMap=new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH,  "clientId");//客户号
		//fieldMap.put(FIDConstants.FID_SQH,  "sqh");//资金帐号
		asResponseField = new int[][] {
			//{FIDConstants.FID_CODE,    FieldType.FieldString},//
			//{FIDConstants.FID_MESSAGE,      FieldType.FieldString},//
			{FIDConstants.FID_SQH,      FieldType.FieldString},//申请号
			{FIDConstants.FID_ZJZH,     FieldType.FieldString},//资金账户
			{FIDConstants.FID_JGDM,     FieldType.FieldString},//银行代码
			{FIDConstants.FID_WBZH,     FieldType.FieldString},//银行账户
			{FIDConstants.FID_BZ,       FieldType.FieldString},//币种
			{FIDConstants.FID_YWLB,     FieldType.FieldString},//业务类别，1银转证,2证转银,4查余额,8登记,16撤销,32冲银转证,64冲证转银 128交易核实 剩下为其它业务
			{FIDConstants.FID_FSJE,     FieldType.FieldString},//转账金额
			{FIDConstants.FID_BCZJYE,   FieldType.FieldString},//本次资金余额或银行余额
			{FIDConstants.FID_SQSJ,     FieldType.FieldString},//申请时间
			{FIDConstants.FID_CLJG,     FieldType.FieldString},//处理结果：111表示成功，其它表示失败
			{FIDConstants.FID_JGSM,     FieldType.FieldString},//处理说明
			{FIDConstants.FID_CXSQH,    FieldType.FieldString},//原申请号，对冲正记录有效
		};
		iFunID = iFuncNo;
	}
}