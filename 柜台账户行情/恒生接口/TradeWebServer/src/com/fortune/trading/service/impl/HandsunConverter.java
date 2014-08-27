package com.fortune.trading.service.impl;

import java.util.List;

import com.fortune.trading.entity.ClientResponse;
import com.fortune.trading.entity.SpecialClientResponse;
import com.fortune.trading.util.Function;
import com.fortune.trading.util.IMap;
import com.fortune.trading.util.RMap;
import com.fortune.trading.util.TradeFormatUtil;
import com.fortune.trading.util.U;
/**
 * <code>HandsunConverter</code> converts the response message which is from <b>Gateway</b><br>
 * to <code>ClientResponse</code>.
 * 
 * @author Colin, Jimmy
 * @Since Handsun Trading v0.0.1 (June 17, 2014)
 *
 */
public class HandsunConverter extends AbstractConverter{
	/** 返回给客户的数据 是否包含有标题*/
	private static boolean IS_RESP_TITLES = false;
	/** */
	public static final char SEQ = '|';
	/** */
	public static final char SUB_SEQ = ';';
	
	public HandsunConverter() {
		super(IS_RESP_TITLES, SUB_SEQ);
	}
	
	@Override
	public ClientResponse<?> convertResp2ClientResp(Object oRespFromGateway,
			Object oMsgType, List<Integer> lstCustomFields) {
		if (oRespFromGateway == null) {
			ClientResponse<Object> oClientRsp = new ClientResponse<Object>();
			oClientRsp.setErrMsg("连接交易服务器错误.");
			return oClientRsp;
		}
		String sResp = oRespFromGateway.toString();
		String[] aPartData = U.STR.fastSplit(sResp, SEQ);
		String sHead = aPartData[0];
		// Phase 1 : Process Error message
		if (sHead.equals("R01")) {
			ClientResponse<Object> oClientRsp = new ClientResponse<Object>();
			oClientRsp.setErrMsg(aPartData[1]);
			return oClientRsp;
		}
		ClientResponse<String[]> oClientRsp = new ClientResponse<String[]>();
		if ("queryFunds".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, fxFundsRespFormatter);
			return oClientRsp;
		} else if ("placeOrder".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, null);
			return oClientRsp;
		} else if ("todayOrder".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, fxTORespFormatter);
			return oClientRsp;
		} else if ("historyOrder".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, fxHORespFormatter);
			return oClientRsp;
		} else if("modiFPwd".equals(oMsgType) || "modiTPwd".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, null);
			return oClientRsp;
		} else if ("gdhQuery".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, fxGdhRespFormatter);
			return oClientRsp;
		} else if ("holdStock".equals(oMsgType)) {
			SpecialClientResponse<String[]> oClient = new SpecialClientResponse<String[]>();
			fillData2ClientResp(oClient, aPartData, null, lstCustomFields, fxHSRespFormatter);
			if (aPartData.length > 1) {
				String[] as = U.STR.fastSplit(aPartData[aPartData.length - 1], SUB_SEQ);
				if (as.length > 28) {
					oClient.setPositionStr(as[28]);
				}
			}
			return oClient;
		} else if ("cancelOrder".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, null);
			return oClientRsp;
		} else if ("todayTransaction".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, fxTTTransRespFmt);
			return oClientRsp;
		} else if ("historyTransaction".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, fxHisTransRespFormatter);
			return oClientRsp;
		} else if ("bankToZQ".equals(oMsgType) || "zqToBank".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, null);
			return oClientRsp;
		} else if ("queryBankDm".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, null);
			return oClientRsp;
		} else if ("userInfo".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, null);
			return oClientRsp;
		} else if ("queryZJMX".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, fxFundsDTRespFmt);
			return oClientRsp;
		} else if ("queryHistoryZJ".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, fxFundsDTRespFmt);
			return oClientRsp;
		} else if ("queryPeihao".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, fxPHRespFormatter);
			return oClientRsp;
		}  else if ("queryBallot".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, fxBallotRespFmt);
			return oClientRsp;
		} else if ("zjToBankReQuery".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, fxBTZRespFormatter);
			return oClientRsp;
		} else if ("todayOrderForCancel".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, fxTCFORespFormatter);
			return oClientRsp;
		} else if ("zqToBankQuery".equals(oMsgType)) {
			fillData2ClientResp(oClientRsp, aPartData, null, lstCustomFields, fxBankQueryRespFormatter);
			return oClientRsp;
		}
		return null;
	}
	/*
	 * Format datetime
	 * @param sSrc
	 * @return
	 */
	private static String formatDatetime(String sSrc) {
		sSrc = "000000" + sSrc;
		sSrc = sSrc.substring(sSrc.length() - 6);
		String sTraget = sSrc.substring(0, 2) + ":" + sSrc.substring(2, 4);
		return sTraget;
	}
	/**
	 * Today order response formatter 
	 */
	private Function<String, IMap<Object, Object>> fxTORespFormatter = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			
			switch(iIndex) {
				case 7:
					sData = TradeFormatUtil.formatPrice2(sData);
					break;
				case 8:
					sData = TradeFormatUtil.formatQty0(sData);
					break;
				case 23:
					sData = formatDatetime(sData);
					break;
				default :
					break;
			}
			return sData;
		}
	};
	/**
	 * History order response formatter 
	 */
	private Function<String, IMap<Object, Object>> fxHORespFormatter = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			
			switch(iIndex) {
				case 8:
				case 11:
					sData = TradeFormatUtil.formatPrice2(sData);
					break;
				case 9:
				case 10:
					sData = TradeFormatUtil.formatQty0(sData);
					break;
				default :
					break;
			}
			return sData;
		}
	};
	/**
	 * Today order response formatter 
	 */
	private static Function<String, IMap<Object, Object>> fxGdhRespFormatter = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			switch(iIndex) {
				case 0:
					sData = hExchangeMapping.getAs(sData);
					break;
				default :
					break;
			}
			return sData;
		}
	};
	private static Function<String, IMap<Object, Object>> fxTCFORespFormatter = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			switch(iIndex) {
				case 5:
					sData = hBsMapping.getAs(sData);
					break;
				case 6:
					sData = TradeFormatUtil.formatPrice2(sData);
					break;
				case 7:
				case 8:
					sData = TradeFormatUtil.formatQty0(sData);
					break;
				default :
					break;
			}
			return sData;
		}
	};
	
	private static Function<String, IMap<Object, Object>> fxBankQueryRespFormatter = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			switch(iIndex) {
				case 11:
					sData = formatDatetime(sData);
					break;
				case 10:
					// 资金发生额
					sData = TradeFormatUtil.formatPrice2(sData);
					break;
				default :
					break;
			}
			return sData;
		}
	};
	/**
	 * HoldStock response formatter 
	 */
	private static Function<String, IMap<Object, Object>> fxHSRespFormatter = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			switch(iIndex) {
				case 0:
					sData = hExNameMapping.getAs(sData);
					break;
				case 4:
				case 5:
					sData = TradeFormatUtil.formatQty0(sData);
					break;
				case 6:
				case 7:
				case 8:
				case 10:
					sData = TradeFormatUtil.formatPrice2(sData);
					break;
				default :
					break;
			}
			return sData;
		}
	};
	private static Function<String, IMap<Object, Object>> fxPHRespFormatter = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			switch(iIndex) {
				case 2:
					sData = hExNameMapping.getAs(sData);
					break;
				default :
					break;
			}
			return sData;
		}
	};
	private static Function<String, IMap<Object, Object>> fxBallotRespFmt = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			switch(iIndex) {
				case 2:
					sData = hExNameMapping.getAs(sData);
				break;
				case 3:
					sData = TradeFormatUtil.formatPrice2(sData);
					break;
				case 5:
					sData = TradeFormatUtil.formatQty0(sData);
					break;
				default :
					break;
			}
			return sData;
		}
	};
	private static Function<String, IMap<Object, Object>> fxHisTransRespFormatter = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			switch(iIndex) {
				case 8:
					sData = TradeFormatUtil.formatPrice2(sData);
				break;
				case 9:
					sData = formatDatetime(sData);
					break;
				case 15:
					sData = TradeFormatUtil.formatQty0(sData);
					break;
				default :
					break;
			}
			return sData;
		}
	};
	private static Function<String, IMap<Object, Object>> fxFundsDTRespFmt = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			switch(iIndex) {
				case 5:
				case 6:
					sData = TradeFormatUtil.formatPrice2(sData);
				break;
				default :
					break;
			}
			return sData;
		}
	};
	
	private static Function<String, IMap<Object, Object>> fxTTTransRespFmt = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			switch(iIndex) {
				case 8:
				case 18:
					sData = TradeFormatUtil.formatPrice2(sData);
					break;
				case 9:
					sData = TradeFormatUtil.formatQty0(sData);
					break;
				default :
					break;
			}
			return sData;
		}
	};
	
	private static Function<String, IMap<Object, Object>> fxBTZRespFormatter = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			switch(iIndex) {
				case 0:
					sData = hMoneyType.getAs(sData);
					break;
				case 3:
					sData = "*" + sData.substring(sData.length() - 6);
					break;
				default :
					break;
			}
			return sData;
		}
	};
	private static Function<String, IMap<Object, Object>> fxFundsRespFormatter = new Function<String, IMap<Object,Object>>() {
		@Override
		public String apply(IMap<Object, Object> oParam) {
			Integer iIndex = oParam.getAs("index");
			String sData = oParam.getAs("data");
			switch(iIndex) {
				case 1:
				case 2:
				case 3:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
					sData = TradeFormatUtil.formatPrice2(sData);
					break;
				default :
					break;
			}
			return sData;
		}
	};
	
	
	private static IMap<String, Object> hExchangeMapping = RMap.build()
	.put("1", "sh")
	.put("2", "sz")
	.put("D", "hb")
	.put("H", "sb");
	
	private static IMap<String, Object> hExNameMapping = RMap.build()
	.put("1", "上海A")
	.put("2", "深圳A")
	.put("D", "上海B")
	.put("H", "深圳B");
	
	public static IMap<String, Object> hExMapping = RMap.build()
	.put("sh", "1")
	.put("sz", "2")
	.put("hb", "D")
	.put("sb", "H");
	
	public static IMap<String, Object> hBsMapping = RMap.build()
	.put("1", "买入")
	.put("2", "卖出");
	
	public static IMap<String, Object> hMoneyType = RMap.build()
	.put("0", "人民币")
	.put("1", "美圆")
	.put("2", "港币");
	
	public static IMap<String, Object> hReMoneyType = RMap.build()
	.put("人民币", "0")
	.put("美圆", "1")
	.put("港币", "2");
}
