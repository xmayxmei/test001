/*
 * FileName: HSDataHandler.java
 * Copyright: Copyright 2014-6-10 Cfwx Tech. Co. Ltd.All right reserved.
 * Description:
 *
 */
package com.core.handsun;
import static com.util.SysConstants.HS.hErrorMapping;

import java.util.Map;

import org.apache.log4j.Logger;

import com.core.IBiz;
import com.hundsun.t2sdk.common.core.context.ContextUtil;
import com.hundsun.t2sdk.common.share.dataset.MapWriter;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.EventReturnCode;
import com.hundsun.t2sdk.interfaces.share.event.EventType;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import com.util.SysConstants;
/**
 * <code>HSDataHandler<code>
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 10,2014)
 *
 */
public abstract class HSDataHandler implements IBiz{
	protected static Logger L = Logger.getLogger(HSDataHandler.class);

	protected String[] asResponseField;

	protected String[][] asRequestField;

	protected boolean isDataSet = true;
	
	protected String sFunID;
	
	public HSDataHandler() {
		buildField();
	}
	/* (non-Javadoc)
	 * @see com.core.IBiz#requestData(java.util.Map)
	 */
	@Override
	public String requestData(Map<String, String> hReqParam) {
		hReqParam.put("function_id", sFunID);
		IClient oClient = HSApi.getInstance().getClient();
		String sMsg = "";
		if (oClient != null) {
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(sFunID, EventType.ET_REQUEST);
			MapWriter map = convertToMapWriter(hReqParam);
			event.putEventData(map.getDataset());
			try {
				IEvent oRsp = oClient.sendReceive(event, SysConstants.HS.waitTimeout);
				sMsg = parseResponse(oRsp);
			} catch (T2SDKException e) {
				L.error(e.getMessage(), e.getCause());
				sMsg = "R01|-999;" + e.getMessage();
			}
		} else {
			sMsg = "R01|-999;交易服务器连接错误.";
		}
		return sMsg;
	}
	/**
	 * 转换<code>Map</code>为<code>MapWriter</code>
	 * @param hReqParam
	 */
	private MapWriter convertToMapWriter(Map<String, String> hReqParam) {
		MapWriter hData = new MapWriter();
		int iLen = asRequestField.length;
		for (int i = 0; i < iLen; i++) {
			String sKey = asRequestField[i][0];
			String sVal = hReqParam.get(sKey);
			if (sVal == null) {
				sVal = asRequestField[i][1];
			}
			hData.put(sKey, sVal);
		}
		return hData;
	}
	/**
	 * 
	 * @param oEvtRsp
	 * @return
	 */
	private String parseResponse(IEvent oEvtRsp) {
		L.info("FoundID:"+this.sFunID);
		int iCode = oEvtRsp.getReturnCode();
		L.info("iCode:"+iCode);
		StringBuilder sRsp = new StringBuilder();
		
		if (iCode == EventReturnCode.I_OK) {
			IDatasets oResult = oEvtRsp.getEventDatas();
			int iDatasetCount = oResult.getDatasetCount();
			int iRetLen = asResponseField.length;
			sRsp.append("R00|");
			for (int i = 0; i < iDatasetCount; i++) {
				IDataset oDataSet = oResult.getDataset(i);
				oDataSet.beforeFirst();
				while (oDataSet.hasNext()){
					oDataSet.next();
					for (int iR = 0; iR < iRetLen; iR++) {
						sRsp
						.append(oDataSet.getString(asResponseField[iR]))
						.append(";");
					}
					sRsp.append("|");
				} ;
			}
		} else {
			Map<String, String> hErrMapping = hErrorMapping.get(this.sFunID);
			if (hErrMapping != null ) {
				String sErrCode = String.valueOf(iCode);
				String sErrMsg = hErrMapping.get(sErrCode);
				sRsp
				.append("R01|")
				.append(sErrCode)
				.append(";")
				.append(sErrMsg);
			} else {
				sRsp
				.append("R01|")
				.append(oEvtRsp.getErrorNo())
				.append(";")
				.append( oEvtRsp.getErrorInfo());
			}
		}
		return sRsp.toString();
	}
	
	protected abstract void buildField();
}
