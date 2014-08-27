package com.fortune.trading.service.impl;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fortune.trading.service.IQuoteService;
import com.fortune.trading.util.Constants;
import com.fortune.trading.util.U;

/**
 * <code>QuoteServerImpl</code>
 *
 * @author Colin, Jimmy
 * @Since Tebon Trading v0.0.1 (May 8, 2014)
 *
 */
@Service("QuoteService")
@Scope("singleton")
public class QuoteServiceImpl implements IQuoteService{
	
	@Override
	public String getData(Map<String, String> hParams) {
		return U.HTTP.post(Constants.quoteURL, hParams, "UTF-8");
	}
	
}
