package com.util;


import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	/**
	 * 转义mysql查询中参数,本函数不转义like语句
	 * 
	 * @param paramName
	 * @return
	 */
	public static String escapeQuryParam(String paramName) {
		String result = "";
		if (null != paramName && !"".equals(paramName.trim())) {
			paramName = paramName.trim();
			// mysql查询需要转义 % \ ' " _
			// result = paramName.replaceAll("%", "\\\\%");
			// result = result.replaceAll("\\\\", "\\\\\\\\");
			// result = result.replaceAll("'", "\\\\\'");
			// result = result.replaceAll("\"", "\\\\\"");
			// result = result.replaceAll("_", "\\\\_");
			result = paramName.replaceAll("\\\\", "\\\\\\\\")
					.replaceAll("%", "\\\\%").replaceAll("'", "\\\\\'")
					.replaceAll("\"", "\\\\\"").replaceAll("_", "\\\\_");
		}
		return result;
	}

	/**
	 * 转义like语句,并自动添加%%
	 * 
	 * @param paramName
	 * @return
	 */
	public static String escapeQuryLikeParam(String paramName) {
		StringBuffer result = new StringBuffer();
		result.append("%").append(escapeQuryParam(paramName)).append("%");
		return result.toString();
	}

	/**
	 * 获取指定位数的随机码 description: 函数的目的/功能
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomCode(int length) {

		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(r.nextInt(9));
		}

		return sb.toString();
	}

	/**
	 * 取两个字符串的交集
	 */
	public static String getIntersection(String str1, String str2) {
		String targetString = null;
		// 取出其中较短的字符串(照顾效率)
		String shorter = str1.length() > str2.length() ? str2 : str1;
		String longer = shorter.equals(str1) ? str2 : str1;

		// 在较短的字符串中抽取其‘所有长度’的子串，顺序由长到短
		out: for (int subLength = shorter.length(); subLength > 0; subLength--) {
			// 子串的起始角标由 0 开始右移，直至子串尾部与母串的尾部-重合为止
			for (int i = 0; i + subLength <= shorter.length(); i++) {
				String subString = shorter.substring(i, i + subLength); // 取子串
				if (longer.indexOf(subString) >= 0) { // 注意 ‘=’
					targetString = subString;
					break out;
					// 一旦满足条件，则最大子串即找到，停止循环，
				}
			}
		}
		return targetString;

	}
	
	
	/**
	 * 链接两个路径
	 * @param path1
	 * @param path2
	 * @return
	 */
	public static String optPathUrl(String path1,String path2)
	{
		path1 = null == path1?"":path1.trim();
		path2 = null == path2?"":path2.trim();
		StringBuffer urlPath = new StringBuffer();
		path1 = path1.replace("\\", "/");
		path2 = path2.replace("\\", "/");
		urlPath.append(path1);
		if(!"".equals(path1.trim()) && !path1.endsWith("/"))
		{
			urlPath.append("/");
		}
		if(path2.startsWith("/"))
		{
			urlPath.append(path2.substring(1));
		}
		else
		{
			urlPath.append(path2);
		}
		return urlPath.toString();
	}
	
	
	/**
	 * 获取UUID唯一标识
	 * description: 函数的目的/功能
	 * @return
	 */
	public static String getUUID() {
		
		return UUID.randomUUID().toString().replaceAll("-", "");
		
	}
	
	
	/** 字符串转化为日期 */
	public static Date StrToDate(String str)
	{

		Date returnDate = null;
		if (str != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try
			{
				returnDate = sdf.parse(str);
			}
			catch (Exception e)
			{
				System.err.println("AppTools [Date StrToDate(String str)] Exception");
				return returnDate;
			}
		}
		return returnDate;
	}
	
	
	/**
	 * 转义html代码
	 * 
	 * @param str
	 * @return
	 */
	public static String decodeHtml(String str)
	{
		if (null == str || "".equals(str.trim()))
		{
			return "";
		}

		return str.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"")
				.replaceAll("&nbsp;", " ").replaceAll("&apos;", "'").replaceAll("&amp;", "&");
	}

	/**
	 * 解码字符串
	 * 
	 * @param str
	 * @param charset
	 * @return
	 */
	public static String decode(String str)
	{
		return decode(str, "UTF-8");
	}

	/**
	 * 解码字符串
	 * 
	 * @param str
	 * @param charset
	 * @return
	 */
	public static String decode(String str, String charset)
	{
		if (null == str || "".equals(str.trim()))
		{
			return "";
		}
		try
		{
			str = URLDecoder.decode(str, charset);
		}
		catch (Exception e)
		{
			str = str.trim();
		}
		return str.trim();
	}

	/**
	 * 转义html代码
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeHtml(String str)
	{
		if (null == str || "".equals(str.trim()))
		{
			return "";
		}

		// return str.replaceAll("&", "&amp;").replaceAll("&lt;", "<").replaceAll("&gt;",
		// ">").replaceAll("&quot;", "\"").replaceAll("&nbsp;", " ").replaceAll("&apos;", "'");
		return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
				.replaceAll("\"", "&quot;").replaceAll(" ", "&nbsp;").replaceAll("'", "&apos;");
	}

	/**
	 * 转义字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String encode(String str)
	{
		return encode(str, "UTF-8");
	}

	/**
	 * 转义字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String encode(String str, String charset)
	{
		if (null == str || "".equals(str.trim()))
		{
			return "";
		}
		try
		{
			str = URLEncoder.encode(str, charset);
		}
		catch (Exception e)
		{
			str = str.trim();
		}
		return str.trim();
	}

	/**
	 * 判断字符串是否有时间
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str)
	{
		if (null == str || "".equals(str.trim()))
		{
			return false;
		}
		str = str.trim();

		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(str);
		return m.matches();
	}

}
