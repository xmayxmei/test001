/*
 * FileName: TradeFormater.java
 * Copyright: Copyright 2014-7-10 Cfwx Tech. Co. Ltd.All right reserved.
 * Description: [这里用一句话描述]
 *
 */
package com.fortune.trading.util;

import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * <code>TradeFormatUtil<code>
 *
 * @author Jimmy
 * @since TradeWebServer v0.0.2 (2014-7-10)
 *
 */
public class TradeFormatUtil {
	
	private static ThreadLocal<NumberFormat> oDf2 = new ThreadLocal<NumberFormat>(){
		protected NumberFormat initialValue() {
			NumberFormat oDf2 = NumberFormat.getInstance();
			oDf2.setMinimumFractionDigits(2);
			oDf2.setMaximumFractionDigits(2);
			oDf2.setGroupingUsed(false);
			oDf2.setRoundingMode(RoundingMode.DOWN);
			return oDf2;
		};
	};
	
	private static ThreadLocal<NumberFormat> oDf0 = new ThreadLocal<NumberFormat>(){
		protected NumberFormat initialValue() {
			NumberFormat oDf0 = NumberFormat.getInstance();
			oDf0.setMinimumFractionDigits(0);
			oDf0.setMaximumFractionDigits(0);
			oDf0.setGroupingUsed(false);
			oDf0.setRoundingMode(RoundingMode.DOWN);
			return oDf0;
		};
	};
	/**
	 * Format Qty
	 * 
	 * @param sQty
	 * @return
	 */
	public static String formatQty0(String sQty) {
		String sRst = "";
		try {
			double d = Double.parseDouble(sQty);
			sRst = oDf0.get().format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sRst;
	}
	/**
	 * Format Qty
	 * 
	 * @param sQty
	 * @return
	 */
	public static String formatQty0(double dQty) {
		String sRst = "";
		try {
			sRst = oDf0.get().format(dQty);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sRst;
	}
	/**
	 * Format price
	 * 
	 * @param sPrice
	 * @return
	 * @throws Exception 
	 */
	public static String formatPrice2(String sPrice) {
		String sRst = "";
		try {
			double d = Double.parseDouble(sPrice);
			sRst = oDf2.get().format(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sRst;
	}
	/**
	 * Format price
	 * 
	 * @param sPrice
	 * @return
	 * @throws Exception 
	 */
	public static String formatPrice2(double dPrice) {
		String sRst = "";
		try {
			sRst = oDf2.get().format(dPrice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sRst;
	}
}
