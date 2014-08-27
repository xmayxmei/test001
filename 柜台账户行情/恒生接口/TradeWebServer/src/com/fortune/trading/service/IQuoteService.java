package com.fortune.trading.service;

import java.util.Map;

/**
 * <code>IQuoteService</code> 
 *
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1 (May 8, 2014)
 */
public interface IQuoteService {
	/**
	 * 获取行情
	 * @param hParams
	 * @return
	 */
	public String getData(Map<String, String> hParams);
}
