package com.fortune.trading.service;

import java.util.Map;

/**
 * <code>ISystemService</code> 
 *
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1 (April 24, 2014)
 */
public interface ISystemService {
	/**
	 * Generate the captcha image.
	 * 
	 * @param hParams
	 * @return
	 */
	public Map<String, Object> captcha();
	
}
