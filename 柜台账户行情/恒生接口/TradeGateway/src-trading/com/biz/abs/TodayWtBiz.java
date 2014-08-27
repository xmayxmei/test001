package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;
/**
 * 当日委托查询
 * @author wenwenbin
 *
 */
public class TodayWtBiz extends  DataHandler implements IBiz {
	private final static int iFuncNo = FUNConstants.FUN_SECU_LIST_ENTRUST;
	@Override
	protected void buildField() {
		fieldMap=new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH,  "clientId");//客户号
		//fieldMap.put(FIDConstants.FID_BZ,   "bz");
		//fieldMap.put(FIDConstants.FID_EXFLG, "1");
		asResponseField = new int[][] {
			{FIDConstants.FID_WTRQ,   FieldType.FieldString},//委托日期
			{FIDConstants.FID_JYS,    FieldType.FieldString},//交易所
			{FIDConstants.FID_GDH,    FieldType.FieldString},//股东号
			{FIDConstants.FID_WTH,    FieldType.FieldString},//委托号
			{FIDConstants.FID_WTLB,   FieldType.FieldString},//交易类别
			{FIDConstants.FID_CXBZ,   FieldType.FieldString},//撤销标志
			{FIDConstants.FID_ZQDM,   FieldType.FieldString},//证券代码
			{FIDConstants.FID_ZQMC,   FieldType.FieldString},//证券名称
			{FIDConstants.FID_DDLX,   FieldType.FieldString},//订单类型
			{FIDConstants.FID_WTSL,   FieldType.FieldString},//委托数量
			{FIDConstants.FID_WTJG,   FieldType.FieldString},//委托价格
			{FIDConstants.FID_WTSJ,   FieldType.FieldString},//委托时间

			
			{FIDConstants.FID_SBSJ,   FieldType.FieldString},//申报时间
			{FIDConstants.FID_SBJG,   FieldType.FieldString},//申报结果
			{FIDConstants.FID_JGSM,   FieldType.FieldString},//结果说明
			{FIDConstants.FID_CDSL,   FieldType.FieldString},//撤单数量
			{FIDConstants.FID_CJSL,   FieldType.FieldString},//成交数量
			{FIDConstants.FID_CJJE,   FieldType.FieldString},//成交金额
			{FIDConstants.FID_CJJG,   FieldType.FieldString},//成交均价
			
			{FIDConstants.FID_BZ,     FieldType.FieldString},//币种
			{FIDConstants.FID_QSZJ,   FieldType.FieldString},//清算资金
			{FIDConstants.FID_NODE,   FieldType.FieldString},//变动说明
			{FIDConstants.FID_WTPCH,  FieldType.FieldString},//批次号
		};
		iFunID = iFuncNo;
	}
}