package com.fortune.trading.service.impl;

import java.util.List;

import com.fortune.trading.entity.ClientResponse;
import com.fortune.trading.service.ITradeConverter;
import com.fortune.trading.util.Function;
import com.fortune.trading.util.IMap;
import com.fortune.trading.util.RMap;
import com.fortune.trading.util.U;
/**
 * <code>AbstractConverter</code> 
 * 
 * @author Colin, Jimmy
 * @Since Handsun Trading v0.0.1 (June 17, 2014)
 *
 */
public abstract class AbstractConverter implements ITradeConverter{
	/** 返回给客户的数据 是否包含有标题*/
	private boolean isRspTitles;
	/** */
	private char SUB_SEQ;
	
	public AbstractConverter(boolean isRspWithTitles, char chSubSeq) {
		isRspTitles = isRspWithTitles;
		SUB_SEQ = chSubSeq;
	}
	
	/** @see #fillData2ClientResp(ClientResponse, String[], String[], List, Function, Function)*/
	protected void  fillData2ClientResp( ClientResponse<String[]> oClientRsp, String[] aPartData, String[] aTitles, List<Integer> lstCusFields, 
			Function<String, IMap<Object, Object>> fxRespFormatter) {
		this.fillData2ClientResp(oClientRsp, aPartData, aTitles, lstCusFields, fxRespFormatter, null);
	}
	/**
	 * @param oClientRsp 需要填充数据的ClientResponse
	 * @param aPartData 解析后的数据
	 * @param aTitles 返回给客户的标题
	 * @param lstCusFields 定制的字段
	 * @param fxCusResp 定制返回字段的内容
	 * @param fxCusRows 定制返回的行
	 */
	protected void  fillData2ClientResp( ClientResponse<String[]> oClientRsp, String[] aPartData, String[] aTitles, List<Integer> lstCusFields, 
			Function<String, IMap<Object, Object>> fxRespFormatter,  Function<Boolean, String[]> fxCusRows) {
		int iRespLen = 0;
		if (lstCusFields != null) {
			iRespLen = lstCusFields.size();
		}
		int iPartDataLen = aPartData.length;
		final int iStart = 1;
		// 定制返回数据的标题
		if (aTitles != null && isRspTitles) {
			if (lstCusFields == null) {
				oClientRsp.addData(aTitles);
			} else {
				String[] asCustomTitle = new String[iRespLen];
				int iLen = aTitles.length;
				int iCount = 0;
				for (int i = 0; i < iLen; i++) {
					if (!lstCusFields.contains(i)) {
						continue;
					}
					asCustomTitle[iCount] = aTitles[i];
					iCount++;
				}
				oClientRsp.addData(asCustomTitle);
			}
		}
		// 定制返回数据的内容
		for (int i = iStart; i < iPartDataLen; ++i ) {
			String sData = aPartData[i];
			if (U.STR.isEmpty(sData)) {
				continue;
			}
			String[] aTokens = U.STR.fastSplit(sData, SUB_SEQ);
			if (iRespLen <= 0) {
				iRespLen = aTokens.length;
			}
			String[] asRespMsg = new String[iRespLen];
			int iTokenLen = aTokens.length;
			// 判断fxCusRows是否为true
			if (fxCusRows != null && !fxCusRows.apply(aTokens)) {
				continue;
			}
			for(int iC = 0; iC < iRespLen; iC++) {
				int iIndex = 0;
				String sMsg = "";
				if (lstCusFields == null) {
					iIndex = iC;
				} else {
					iIndex = lstCusFields.get(iC);
				}
				if (iIndex < iTokenLen) {
					sMsg = aTokens[iIndex];
				}
				if (fxRespFormatter != null) {
					IMap<Object, Object> hParams = RMap.create()
					.put("index", iIndex)
					.put("data", sMsg);
					sMsg = fxRespFormatter.apply(hParams);
				}
				asRespMsg[iC] = sMsg;
			}
			oClientRsp.addData(asRespMsg);
		}
	}
}
