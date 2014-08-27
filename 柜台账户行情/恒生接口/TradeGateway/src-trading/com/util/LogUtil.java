package com.util;
public class LogUtil {
	/**
	 * logRecordFilter(过滤掉不显示的内容)
	 * @param logRecord
	 * @param filter    需要过滤的内容格式        
	 * @return  String
	 * @exception 
	 * @since  1.0.0
	 */
	public static String logRecordFilter(String logRecord, String filter,
			String replaceTxt) {
		String[] aStrVal = filter.split("\\|");
		for (String str : aStrVal) {
			String strLocal = "\\|" + str + "\\|";
			String[] aStrLocal = logRecord.split(strLocal);
			if (aStrLocal.length > 1) {
				logRecord = logRecord.substring(0,
						logRecord.indexOf("|" + str + "|") + str.length() + 1)
						+ "|"
						+ replaceTxt
						+ aStrLocal[1].substring(aStrLocal[1].indexOf("|"));
			}
		}
		return logRecord;
	}
}
