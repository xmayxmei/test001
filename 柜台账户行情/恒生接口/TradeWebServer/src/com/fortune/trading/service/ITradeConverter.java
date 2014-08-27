package com.fortune.trading.service;

import java.util.List;

import com.fortune.trading.entity.ClientResponse;


/**
 * <code>ITradeConverter</code>
 *
 * @author Colin, Jimmy
 * @Since Tebon Trading v0.0.1 (April 28, 2014)
 *
 */
public interface ITradeConverter {
		/**
		 * Convert the response message which from <b>Gateway</b> to <code>ClientResponse</code>. So we can response JSON format message to user  on <b>SpringMVC Front Controller</b> immediately.
		 * @param oRespFromGateway The message from Gateway.
		 * @param oMsgType  The message type.
		 * @param aCustomFields
		 * 
		 * @return
		 */
		public ClientResponse<?> convertResp2ClientResp(Object oRespFromGateway, Object oMsgType, List<Integer> lstCustomFields) ;
}
