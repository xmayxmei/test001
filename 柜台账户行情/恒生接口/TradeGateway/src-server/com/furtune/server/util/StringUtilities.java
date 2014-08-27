package com.furtune.server.util;



/**
 * <code>StringUtilities</code> is a utilities class which provides convenient
 * methods to handle the string.
 * 
 * @author Jimmy(SZ)
 * 
 * @since <i> DUI v1.0.0 (Apr 14,2013)</i>
 */
public class StringUtilities {
	
	public static final String EMPTY_STRING = "";
	/**
	 * Return true if the string specified by <code>csText</code> is either null
	 * or empty string.
	 * 
	 * @param csText
	 *            The target string
	 * @return
	 * 
	 * @since <i>DUI v1.0.0 (Apr 14, 2013)</i>
	 */
	public static boolean isEmpty(CharSequence csText) {
		return csText == null || "".equals(csText);
	}

	/** @see #parseInteger(String, int) **/
	public static int parseInteger(String sVal) {
		return parseInteger(sVal, Integer.MIN_VALUE);
	}

	/**
	 * Parse String to Integer.
	 * 
	 * Note that if throwing exception , it return the default value specified
	 * by <code>iDefualt<code>.
	 * 
	 * @param sVal
	 * @param iDefault
	 * 
	 * @since <i>DUI v1.0.0 (Apr 25,2013)</i>
	 */
	public static int parseInteger(String sVal, int iDefault) {
		try {
			return Integer.parseInt(sVal);
		} catch (NumberFormatException nfe) {
			return iDefault;
		}
	}

	/** @see #parseDouble(String, double) **/
	public static double parseDouble(String sVal) {
		return parseDouble(sVal, Double.MIN_VALUE);
	}

	/**
	 * Parse String to Double.
	 * 
	 * Note that if throwing exception , it return the default value specified
	 * by <code>dDefualt<code>.
	 * 
	 * @param sVal
	 * @param dDefault
	 * 
	 * @since <i>DUI v1.0.0 (Apr 25,2013)</i>
	 */
	public static double parseDouble(String sVal, double dDefault) {
		try {
			return Double.parseDouble(sVal);
		} catch (NumberFormatException nfe) {
			return dDefault;
		}
	}

	/** @see #parseLong(String, long) **/
	public static long parseLong(String sVal) {
		return parseLong(sVal, Long.MIN_VALUE);
	}

	/**
	 * Parse String to Long.
	 * 
	 * Note that if throwing exception , it return the default value specified
	 * by <code>lDefualt<code>.
	 * 
	 * @param sVal
	 * @param lDefault
	 * 
	 * @since <i>DUI v1.0.0 (Apr 25,2013)</i>
	 */
	public static long parseLong(String sVal, long lDefault) {
		try {
			return Long.parseLong(sVal);
		} catch (NumberFormatException nfe) {
			return lDefault;
		}
	}
	
	/** @see #parseFloat(String, float) **/
	public static float parseFloat(String sVal) {
		return parseFloat(sVal, Float.MIN_VALUE);
	}

	/**
	 * Parse String to Float.
	 * 
	 * Note that if throwing exception , it return the default value specified
	 * by <code>dDefualt<code>.
	 * 
	 * @param sVal
	 * @param fDefault
	 * 
	 * @since <i>DUI v1.0.0 (Apr 25,2013)</i>
	 */
	public static float parseFloat(String sVal, float fDefault) {
		try {
			return Float.parseFloat(sVal);
		} catch (NumberFormatException nfe) {
			return fDefault;
		}
	}
}
