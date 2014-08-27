package com.fortune.trading.util;

/**
 * <code>Function</code> is used for callback function.
 *
 * @author Colin, Jimmy
 * @Since Tebon Trading v0.0.1 (Map 6, 2014)
 *
 */
public interface Function<T, P> {
	/**
	 * @param oParam
	 * @return
	 */
	public T apply(P oParam);
	
}
