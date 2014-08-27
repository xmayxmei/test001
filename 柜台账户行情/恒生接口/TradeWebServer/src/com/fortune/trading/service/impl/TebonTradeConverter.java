package com.fortune.trading.service.impl;

import java.util.List;

import com.fortune.trading.entity.ClientResponse;
import com.fortune.trading.util.Function;
import com.fortune.trading.util.IMap;
import com.fortune.trading.util.RMap;
import com.fortune.trading.util.U;

/**
 * <code>TebonTradeConverter</code> converts the response message which is from <b>Gateway</b><br>
 * to <code>ClientResponse</code>.
 * 
 * @author Colin, Jimmy
 * @Since Tebon Trading v0.0.1 (April 29, 2014)
 *
 */
public class TebonTradeConverter extends AbstractConverter{
	/** 返回给客户的数据 是否包含有标题*/
	private static boolean IS_RESP_TITLES = false;
	/** */
	public static final char SEQ = '|';
	/** */
	public static final char SUB_SEQ = ';';
	/** 资金查询 信息标题*/
	private static final String[] QUERYFUNDS_TITLES = {"资金账号", "币种", "资金余额", "冻结资金", "可用资金", "可取资金", "市值", "总资产"};
	/** 下单返回的信息标题*/
	private static final String[] PALCEORDER_TITLES = {"返回码", "返回消息", "委托号"};
	/** 委托查询返回的信息标题*/
	private static final String[] QUERYORDERS_TITLES = {"委托日期", "交易所", "股东号", "委托号", "交易类别", "撤销标志", "证券代码", "证券名称",
		"订单类型", "委托数量", "委托价格", "委托时间", "申报时间", "申报价格", "结果说明", "撤单数量", "成交数量", "成交金额", "成交均价", "币种", "清算资金", "变动说明", "批次号"};
	/** 修改密码返回的信息标题*/
	private static final String[] MODIF_PWD_TITLES = {"返回码",  "返回消息"};
	/** 股东号查询返回的信息标题*/
	private static final String[] GDH_TITLES = {"股东号", "交易所", "币种", "标志", "交易权限"};
	/** 持股查询返回的信息标题*/
	private static final String[] HOLDSTOCK_TITLES = {"股东号", "交易所", "证券代码", "证券名称", "币种", "数量", "当天卖出委托数量", "当天买入成交", "当天卖出成交",
		"可申购数量", "可卖出数量", "冻结数量", "未交收数量", "开仓日期", "最新市值", "最新价", "买入均价", "成本价", "保本价", "浮动盈亏", "累计盈亏", "摊薄成本价",
		"摊薄保本价", "摊薄浮动盈亏"};
	/** 撤单返回的信息标题*/
	private static final String[] CANCELORDER_TITLES = {"返回码",  "返回消息"};
	/** 当日成交查询 返回的信息标题*/
	private static final String[] TODAY_TRANSACTION_TITLES = {"交易所", "股东号", "委托号", "申报标志", "撤销标志", "证券代码", "证券名称", "交易类别",
		"成交数量", "成交金额", "成交均价", "币种", "清算资金", "成交编号", "成交时间"};
	/** 历史成交查询 返回的信息标题*/
	private static final String[] HIS_TRANSACTION_TITLES = {"成交日期", "交易所", "股东号", "委托号", "证券代码", "证券名称", "交易类别",
		"成交数量", "成交时间", "成交价格", "币种", "成交编号", "结算价", "成交金额", "标准佣金", "实收佣金", "印花税", "过户费", "附加费", "结算费",
		"其它费", "成交编号", "成交笔数", "应收金额", "证券余额", "资金余额", "交收日期"};
	/** 银行到证券 返回的信息标题*/
	private static final String[] BANKTOZQ_TITLES = {"返回码",  "返回消息", "申请号"};
	/** 查询银行代码 返回的信息标题*/
	private static final String[] BANKCODE_TITLES = {"机构类别",  "机构代码", "机构简称"};
	/** 查询用户基本信息返回的信息标题*/
	private static final String[] USERINFO_TITLES = {"返回码",  "返回信息", "服务项目范围", "委托方式范围", "证件",  "营业部", "客户群组", "客户卡号", "客户姓名", "状态",
		"证券风险承受能力", "基金风险承受能力"};
	/** 查询基金明细返回的信息标题*/
	private static final String[] ZJMX_TITLES = {"发生日期", "发生时间", "资金账户", "币种", "收入金额", "付出金额", "资金余额", "变动说明"};
	/** 账号与银行账号的关系 标题*/
	private static final String[] FUNDS2BANKREQUERY_TITLES = {"银行账户", "银行代码", "资金账户", "币种"};
	/** 查询基金明细返回的信息标题*/
	private static final String[] QUERYZQTOBANK_TITLES = {"申请号", "资金账户", "银行代码", "银行账户", "币种", "业务类别", "转账金额", 
		 "余额", "申请时间", "", "处理结果", "处理说明", "原申请号", };
	
	public TebonTradeConverter() {
		super(IS_RESP_TITLES, SUB_SEQ);
	}
	@Override
	public ClientResponse<?> convertResp2ClientResp(Object oRespFromGateway, Object oMsgType, List<Integer> lstCustomFields) {
		if (oRespFromGateway == null) {
			ClientResponse<Object> oClientRsp = new ClientResponse<Object>();
			oClientRsp.setErrMsg("连接交易服务器错误.");
			return oClientRsp;
		}
		/*if ("R00|".equals(oRespFromGateway)) {
			ClientResponse<Object> oClientRsp = new ClientResponse<Object>();
			oClientRsp.setErrMsg("返回的数据为空.");
			return oClientRsp;
		}*/
		String sResp = oRespFromGateway.toString();
		String[] aPartData = U.STR.fastSplit(sResp, SEQ);
		String sHead = aPartData[0];
		// Phase 1 : Process Error message
		if (sHead.equals("R01")) {
			ClientResponse<Object> oClientRsp = new ClientResponse<Object>();
			oClientRsp.setErrMsg(aPartData[1]);
			return oClientRsp;
		}
		// Phase 2 : Process logical
		ClientResponse<String[]> oClientRsp = new ClientResponse<String[]>();	
		if ("queryFunds".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, QUERYFUNDS_TITLES, lstCustomFields, null);
			return oClientRsp;
		} else if ("placeOrder".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, PALCEORDER_TITLES, lstCustomFields, null);
			return oClientRsp;
		} else if ("todayOrder".equals(oMsgType) || "historyOrder".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, QUERYORDERS_TITLES, lstCustomFields, fxTORespFormatter);
			return oClientRsp;
		} else if("modiFPwd".equals(oMsgType) || "modiTPwd".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, MODIF_PWD_TITLES, lstCustomFields, null);
			return oClientRsp;
		} else if ("gdhQuery".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, GDH_TITLES, lstCustomFields, null);
			return oClientRsp;
		} else if ("holdStock".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, HOLDSTOCK_TITLES, lstCustomFields, null);
			return oClientRsp;
		} else if ("cancelOrder".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, CANCELORDER_TITLES, lstCustomFields, null);
			return oClientRsp;
		} else if ("todayTransaction".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, TODAY_TRANSACTION_TITLES, lstCustomFields, fxTTRespFormatter);
			return oClientRsp;
		} else if ("historyTransaction".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, HIS_TRANSACTION_TITLES, lstCustomFields, fxHTRespFormatter);
			return oClientRsp;
		} else if ("bankToZQ".equals(oMsgType) || "zqToBank".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, BANKTOZQ_TITLES, lstCustomFields, null);
			return oClientRsp;
		} else if ("queryBankDm".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, BANKCODE_TITLES, lstCustomFields, null);
			return oClientRsp;
		} else if ("userInfo".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, USERINFO_TITLES, lstCustomFields, null);
			return oClientRsp;
		} else if ("queryZJMX".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, ZJMX_TITLES, lstCustomFields, null);
			return oClientRsp;
		} else if ("queryDistribution".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, HIS_TRANSACTION_TITLES, lstCustomFields, null, fxDistriCusRows);
			return oClientRsp;
		}  else if ("queryBallot".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, HIS_TRANSACTION_TITLES, lstCustomFields, null, fxBallotCusRows);
			return oClientRsp;
		} else if ("zjToBankReQuery".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, FUNDS2BANKREQUERY_TITLES, lstCustomFields, null);
			return oClientRsp;
		} else if ("todayOrderForCancel".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, QUERYORDERS_TITLES, lstCustomFields, fxTORespFormatter, fxTOForCancelCusRows);
			return oClientRsp;
		} else if ("zqToBankQuery".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, QUERYZQTOBANK_TITLES, lstCustomFields, null, fxBSCusRows);
			return oClientRsp;
		}
		return null;
	}
	/*
	 * 
	 * @param oClientRsp
	 * @param aPartData
	 * @param aTitles
	 
	private void  fillData2ClientResp( ClientResponse<String[]> oClientRsp, String[] aPartData, String[] aTitles) {
		fillData2ClientResp(oClientRsp, aPartData, aTitles, null, null);
	}*/
	
	/** 当日成交格式化返回信息的回调函数*/
	private Function<String, IMap<Object, Object>> fxTTRespFormatter = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			//交易类别
			if (iIndex == 7) {
				sData = hTradeType.getAs(sData);
			}
			return sData;
		}
	};
	/** 当日委托格式化返回信息的回调函数*/
	private Function<String, IMap<Object, Object>> fxTORespFormatter = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			//交易类别
			if (iIndex == 4) {
				sData = hTradeType.getAs(sData);
			} else if (iIndex == 13) {
				sData = hOrderResult.getAs(sData);
			}
			return sData;
		}
	};
	/** 历史成交格式化返回信息的回调函数*/
	private Function<String, IMap<Object, Object>> fxHTRespFormatter = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			//时间
			if (iIndex == 8) {
				sData = sData.substring(0, 5);
			}
			return sData;
		}
	};
	/** 撤单查询中的当日委托回调函数*/
	private Function<Boolean, String[]> fxTOForCancelCusRows = new Function<Boolean, String[]>() {
		@Override
		public Boolean apply(String[] oParam) {
			//判断申报结果
			String sOrderResult = oParam[13];
			if ("0".equals(sOrderResult) || "1".equals(sOrderResult) || "2".equals(sOrderResult) || "5".equals(sOrderResult)) {
				return true;
			}
			return false;
		}
	};
	/** 配号查询 回调函数*/
	private Function<Boolean, String[]> fxDistriCusRows = new Function<Boolean, String[]>() {
		@Override
		public Boolean apply(String[] oParam) {
			//判断交易类型是否是配号
			if ("8".equals(oParam[6])) {
				return true;
			}
			return false;
		}
	};
	/** 中签查询 回调函数*/
	private Function<Boolean, String[]> fxBallotCusRows = new Function<Boolean, String[]>() {
		@Override
		public Boolean apply(String[] oParam) {
			//判断交易类型是否是中签
			if ("83".equals(oParam[6])) {
				return true;
			}
			return false;
		}
	};
	/** 银证查询 回调函数*/
	private Function<Boolean, String[]> fxBSCusRows = new Function<Boolean, String[]>() {
		@Override
		public Boolean apply(String[] oParam) {
			String sResult = oParam[5];
			// 1 银转证 ， 2 证转银
			if ("1".equals(sResult) || "2".equals(sResult)) {
				return true;
			} 
			return false;
		}
	};
	/** 交易类别*/
	private IMap<String, Object> hTradeType = RMap.build()
	.put("1", "买入")
	.put("2", "卖出")
	.put("3", "配股")
	.put("8", "配号")	
	.put("9", "指定")
	.put("10", "撤指");
	/** 申报结果*/
	private IMap<String, Object> hOrderResult= RMap.build()
	.put("0", "未申报")
	.put("1", "申报中")
	.put("2", "已申报")
	.put("3", "非法委托")	
	.put("4", "撤单失败")
	.put("5", "部分成交")
	.put("6", "全部成交")
	.put("7", "部成部撤")
	.put("8", "全部撤单");
}