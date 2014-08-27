package com.cfwx.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.cfwx.rox.businesssync.market.show.MinLine;
import com.cfwx.rox.businesssync.market.show.TimeShare;

/**
 * <code>U</code> is the utilities class. It provides general convenient methods.
 *
 * @author Jimmy
 * 
 * @since Quote v0.0.1 (June 13, 2014)
 */
public class U {
	/**
	 * @return
	 */
	public static String generateUID() {
		return UUID.randomUUID().toString();
	}
	/**
	 * K line convenient methods
	 */
	public static final class K {
		
		/**
		 * 根据<code>lstTimes</code>生成5分K线数据
		 * @param lstTimes
		 * @return
		 */
		public static List<MinLine> generateMinLine(int minute, List<TimeShare> lstTimes, boolean bParseDate) {
			if (lstTimes == null || lstTimes.isEmpty()) {
				return null;
			}
			List<MinLine> lstMins = new ArrayList<MinLine>();
			Map<Integer, List<TimeShare>> hTemp = new HashMap<Integer, List<TimeShare>>();
			int iLen = lstTimes.size();
			// 570是9点半在这一天的分钟数 (60 * 9 + 30)， 900是15点在这一天的分钟数 (60 * 15)
			int iStart = 0;
			int iMiddleEnd = (690 - 570) / minute;
			int iEnd = (900 - 570) / minute;
			// 为什么要这样做，如果分数数据少缺的话，这样做确保5分钟的K线数据的时间不会出错
			for (int i = 0 ; i < iLen; i++) {
				TimeShare oTs = lstTimes.get(i);
				Calendar oCld = Calendar.getInstance();
				oCld.setTimeInMillis(Long.parseLong(oTs.getTime()));
		        int iMin = oCld.get(Calendar.MINUTE) + 60 * oCld.get(Calendar.HOUR_OF_DAY);
		        if (minute == 60) {
		        	 iMin = iMin > 780 ? iMin - 30 : iMin;
		        }
		        int iDiv = (iMin - 570) / minute;
//		        int iReminder = (iMin - 570) % minute;
		        List<TimeShare> lstTs = hTemp.get(iDiv);
		        if (lstTs == null) {
		        	lstTs = new ArrayList<TimeShare>();
		        	hTemp.put(iDiv, lstTs);
		        }
		        if (iEnd == iDiv || iMiddleEnd == iDiv) {
		        	--iDiv;
		        	lstTs = hTemp.get(iDiv);
			        if (lstTs == null) {
			        	lstTs = new ArrayList<TimeShare>();
			        	hTemp.put(iDiv, lstTs);
			        }
		        }
		        lstTs.add(oTs);
		        // 第一个点 ，9:30分的点特殊
		        /*if (iDiv == iStart) {
		        	lstTs.add(oTs);
		        } else {
		        	if (iReminder == 0){
		        		lstTs = hTemp.get(iDiv - 1);
				        if (lstTs == null) {
				        	lstTs = new ArrayList<TimeShare>();
				        	hTemp.put(iDiv - 1, lstTs);
				        }
		        	}
		        	lstTs.add(oTs);
		        }*/
			}
			for (int i = iStart; i < iEnd; i++) {
				List<TimeShare> lstTs = hTemp.get(i);
				if (lstTs == null || lstTs.isEmpty()) {
					continue;
				}
				try {
					// 生成 N 分钟K线
					TimeShare ts = lstTs.get(0);
					int iSize = lstTs.size();
					String sAmount = U.STR.isEmpty(ts.getAmount()) ? "0" : ts.getAmount();
					double dAmount = Double.parseDouble(sAmount);
					double dVolume = Double.parseDouble(ts.getVolume());
					double dOpen = Double.parseDouble(ts.getNewPrice());
					double dHigh = dOpen;
					double dLow = dOpen;
					double dClose = dOpen;
//					String sTime = ts.getTime();
					for (int ii = 1; ii < iSize; ii++) {
						ts = lstTs.get(ii);
						if (dOpen <= 0) {
							dOpen =  Double.parseDouble(ts.getNewPrice());
						}
						double dOpenNext = Double.parseDouble(ts.getNewPrice());
						dHigh = Math.max(dHigh, dOpenNext);
						dLow = Math.min(dLow, dOpenNext);
						String sAmountNext = U.STR.isEmpty(ts.getAmount()) ? "0" : ts.getAmount();
						dAmount += Double.parseDouble(sAmountNext);
						dVolume += Double.parseDouble(ts.getVolume());
						if (dOpenNext > 0) {
							dClose = dOpenNext;
//							sTime = ts.getTime();
						}
					}
					if (dClose == 0 || dHigh == 0 || dLow ==0 || dOpen == 0) {
						continue;
					}
					Calendar oCld = Calendar.getInstance();
					oCld.set(Calendar.HOUR_OF_DAY, 0);
					oCld.set(Calendar.SECOND, 0);
					if (minute == 60) {
						if (i >= 3) {
							oCld.set(Calendar.MINUTE, 570 + (i + 1) * minute + 30);
						} else {
							oCld.set(Calendar.MINUTE, 570 + (i + 1) * minute);
						}
					} else {
						oCld.set(Calendar.MINUTE, 570 + (i + 1) * minute);
					}
					
					String sTime = String.valueOf(oCld.getTimeInMillis());
					MinLine oMin = new MinLine();
					oMin.setAmount(String.valueOf(dAmount));
					oMin.setTime(sTime);
					oMin.setClose(String.valueOf(dClose));
					oMin.setHigh(String.valueOf(dHigh));
					oMin.setLow(String.valueOf(dLow));
					oMin.setVolume(String.valueOf(dVolume));
					oMin.setOpen(String.valueOf(dOpen));
					if (bParseDate) {
						oMin.setDate(U.DT.formatTime(sTime));
						oMin.setDateTime(U.DT.formatDateTime(sTime));
					}
					lstMins.add(oMin);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			return lstMins;
		}
		/*
		 * Deep copy the object specify by <code>lstTs</code>
		 * @param lstTs
		 * @return
		 */
		public static List<TimeShare> deepCopy(List<TimeShare> lstTs) {
			if (lstTs == null) {
				return null;
			}
			List<TimeShare> lstTsNew = new ArrayList<TimeShare>();
			int iSize = lstTs.size();
			for (int i = 0; i < iSize; i++) {
				TimeShare oTs = lstTs.get(i);
				TimeShare oTsNew = new TimeShare();
				oTsNew.setAmount(oTs.getAmount());
				oTsNew.setAverage(oTs.getAverage());
				oTsNew.setNewPrice(oTs.getNewPrice());
				oTsNew.setTime(oTs.getTime());
				oTsNew.setVolume(oTs.getVolume());
				oTsNew.setZe(oTs.getZe());
				oTsNew.setZl(oTs.getZl());
				
				lstTsNew.add(oTsNew);
			}
			return lstTsNew;
		}
		/**
		 * 根据<code>lstTimes</code>生成5分K线数据
		 * @param lstTimes
		 * @return
		 */
		public static List<MinLine> generate5MinLine(List<TimeShare> lstTimes, boolean bParseDate) {
			return generateMinLine(5 , lstTimes , bParseDate);
		}
		/** 
		 * 10分钟k线路处理
		 * 
		 * @param lstTimes
		 * @param b
		 * @return
		 */
		public static List<MinLine> generate10MinLine(List<TimeShare> lstTimes,
				boolean bParseDate) {
			return generateMinLine(10 , lstTimes , bParseDate);
		}
		/**
		 * 15分钟k线路处理
		 * @param lstTimes
		 * @param b
		 * @return
		 */
		public static List<MinLine> generate15MinLine(List<TimeShare> lstTimes,
				boolean bParseDate) {
			return generateMinLine(15 , lstTimes , bParseDate);
		}
		/**
		 * 30分钟k线路处理
		 * @param lstTimes
		 * @param b
		 * @return
		 */
		public static List<MinLine> generate30MinLine(List<TimeShare> lstTimes,
				boolean bParseDate) {
			return generateMinLine(30 , lstTimes , bParseDate);
		}


		/**
		 * 60分钟k线路处理，不能使用 {@link #generateMinLine(int, List, boolean)}
		 * @param lst60Times
		 * @param b
		 * @return
		 */
		public static List<MinLine> generate60MinLine(
				List<TimeShare> lst60Times, boolean bParseDate) {
			return generateMinLine(60, lst60Times , bParseDate);
		}
	}
	
	public static final class BYTE {
		/**
		 * 浮点转换为字节
		 * 
		 * @param f
		 * @return
		 */
		public static byte[] float2byte(float f) {
			
			// 把float转换为byte[]
			int fbit = Float.floatToIntBits(f);
			
			byte[] b = new byte[4];  
		    for (int i = 0; i < 4; i++) {  
		        b[i] = (byte) (fbit >> (24 - i * 8));  
		    } 
		    
		    // 翻转数组
			int len = b.length;
			// 建立一个与源数组元素类型相同的数组
			byte[] dest = new byte[len];
			// 为了防止修改源数组，将源数组拷贝一份副本
			System.arraycopy(b, 0, dest, 0, len);
			byte temp;
			// 将顺位第i个与倒数第i个交换
			for (int i = 0; i < len / 2; ++i) {
				temp = dest[i];
				dest[i] = dest[len - i - 1];
				dest[len - i - 1] = temp;
			}
		    
		    return dest;
		    
		}
		
		/**
		 * 字节转换为浮点
		 * 
		 * @param b 字节（至少4个字节）
		 * @param index 开始位置
		 * @return
		 */
		public static float byte2float(byte[] b, int index) {  
		    int l;                                           
		    l = b[index + 0];                                
		    l &= 0xff;                                       
		    l |= ((long) b[index + 1] << 8);                 
		    l &= 0xffff;                                     
		    l |= ((long) b[index + 2] << 16);                
		    l &= 0xffffff;                                   
		    l |= ((long) b[index + 3] << 24);                
		    return Float.intBitsToFloat(l);                  
		}

	}
	/**
	 * <code>ARR</code> provides Array's convenient methods.
	 */
	public static final class ARR {
		/**
		 * @param src
		 * @return
		 * @throws CloneNotSupportedException
		 */
		@SuppressWarnings("unchecked")
		public static <T extends DeepCopyable> List<T> deepCopy(List<T> src) throws CloneNotSupportedException {
			if (src == null) {
				return null;
			}
			List<T> lstDest = new ArrayList<T>(src.size());
			Iterator<T> iter = src.iterator();
			while (iter.hasNext()) {
				lstDest.add((T)iter.next().clone());
			}
			return lstDest;
		}
		/**
		 * 判断数组是否存在某个元素
		 * @param aTs
		 * @param oObjT
		 * @return
		 */
		public static <T extends Comparable<T>> boolean contains(T[] aTs, T oObjT) {
			if (oObjT == null) {
				return false;
			}
			for (T oT : aTs) {
				if (oObjT.compareTo(oT) == 0) {
					return true;
				}
			}
			return false;
		}
		/**
		 * 
		 * @param lstT
		 * @param iSort
		 * @return
		 */
		public static <T extends Comparable<T>> List<T[]> sort(List<T[]> lstT, int iSortColumn, boolean bAsc) {
			int iLen = lstT.size();
			for (int i = 1; i < iLen; i++) {
				for (int j = i; j > 0; j--) {
					T t1 = lstT.get(j)[iSortColumn];
					T t2 = lstT.get(j-1)[iSortColumn];
					if (bAsc) {
						if (t1.compareTo(t2) < 0) {
							T[] temp = lstT.get(j - 1);
							lstT.set(j - 1, lstT.get(j));
							lstT.set(j, temp);
						} else {
							break;
						}
					} else {
						if (t1.compareTo(t2) > 0) {
							T[] temp = lstT.get(j - 1);
							lstT.set(j - 1, lstT.get(j));
							lstT.set(j, temp);
						} else {
							break;
						}
					}
				}
			}
			return lstT;
		}
		
		/**
		 */
		public interface DeepCopyable {
			public Object clone() throws CloneNotSupportedException; 
		}
	}

	/**
	 * <code>Str</code> provides String's convenient methods.
	 */
	public static final class STR{
		/**
		 * 判断是否为空
		 * @return
		 */
		public static boolean isEmpty(CharSequence csVal) {
			return csVal == null || csVal.length() == 0;
		}
		/**
		 * @param str
		 * @param sCharset
		 * @return
		 */
		public static String decode(String str, String sCharset) {
			if (str == null) {
				return null;
			}
			try {
				str = new String(str.getBytes("ISO8859-1"), sCharset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return str;
		}
		/**
		 * {@link String#split(String)} 性能不佳，#fastSplit已经测试过, 比 String#split快至少4倍.
		 * @param str
		 * @param chSep
		 * @return
		 */
		public static String[] fastSplit(String str, char chSep) {
			int iLen = str.length();
			final List<String> lst = new ArrayList<String>();
			int iLastIndex = 0;
			for (int i = 0; i < iLen; i++) {
				char c = str.charAt(i);
				if (chSep == c) {
					if (i == iLastIndex ) {
						lst.add("");
					} else {
						lst.add(str.substring(iLastIndex, i));
					}
					iLastIndex = i + 1;
				} else if (i == iLen - 1) {
					lst.add(str.substring(iLastIndex, iLen));
				}
			}
			return lst.toArray(new String[lst.size()]);
		}
		/**
		 * @param str
		 * @param chSep
		 * @return
		 */
		public static List<Integer> fastSplit2I(String str, char chSep) {
			if (str == null) {
				return null;
			}
			int iLen = str.length();
			final List<Integer> lst = new ArrayList<Integer>();
			int iLastIndex = 0;
			for (int i = 0; i < iLen; i++) {
				char c = str.charAt(i);
				if (chSep == c) {
					if (i != iLastIndex ) {
						try {
							lst.add(Integer.parseInt(str.substring(iLastIndex, i)));
						}catch (Exception e) {
						}
					}
					iLastIndex = i + 1;
				} else if (i == iLen - 1) {
					try {
						lst.add(Integer.parseInt(str.substring(iLastIndex, iLen)));
					}catch (Exception e) {
					}
				}
			}
			return lst;
		}
	}
	/**
	 * <code>DT</code> provides Date convenient methods.
	 */
	public static final class DT{
		private static final SafeDateFormat yyyy_MM_dd = new SafeDateFormat("yyyy-MM-dd");
		
		public static final SafeDateFormat yyyymmddHHmmss = new SafeDateFormat("yyyy-MM-ddHH:mm:ss");
		
		public static final SafeDateFormat hhmm00 = new SafeDateFormat("HH:mm:00");
		/**
		 * Parse the format string 'yyyy-MM-ddHH:mm:ss' to actual Date.
		 * @param sDate
		 * @return
		 * @throws ParseException
		 */
		public static Date parseYYYYMMDDHHmmss(String sDate) throws ParseException {
			return yyyymmddHHmmss.parse(sDate);
		}
		/**
		 *  Return the format string as yyyy-MM-dd.
		 * @param sTime
		 * @return
		 */
		public static String formatTime(String sTimeInMillis) {
			long lTime = Long.parseLong(sTimeInMillis);
			Calendar cld = Calendar.getInstance();
			cld.setTimeInMillis(lTime);
			return formatYYYYMMDD(cld.getTime());
		}
		/**
		 *  Return the format string as HH:mm:00
		 * @param sTimeInMillis
		 * @return
		 */
		public static String formatDateTime(String sTimeInMillis) {
			long lTime = Long.parseLong(sTimeInMillis);
			Calendar cld = Calendar.getInstance();
			cld.setTimeInMillis(lTime);
			return hhmm00.format(cld.getTime());
		}
		/**
		 * Return the format string as yyyy-MM-dd.
		 * @param oDate
		 * @return
		 */
		public static String formatYYYYMMDD(Date oDate) {
			return yyyy_MM_dd.format(oDate);
		}
		/**
		 * Parse the format string 'yyyy-DD-dd' to actual Date.
		 * @param sDate
		 * @return
		 * @throws ParseException 
		 */
		public static Date parseYYYYMMDD(String sDate) throws ParseException {
			return yyyy_MM_dd.parse(sDate);
		}
		/**
		 * 
		 * Parse the string 'yyyy-DD-mm' to actual Date and returns the number of milliseconds since January 1, 1970, <br>
		 * 00:00:00 GMT represented by this Date object.
		 * 
		 * @param sDate
		 * @return
		 * @throws ParseException 
		 */
		public static long getTimeYYYYMMDD(String sDate) throws ParseException {
			Date oDate = parseYYYYMMDD(sDate);
			return oDate.getTime();
		}
		/**
		 * This class implements a Thread-Safe (re-entrant) SimpleDateFormat class. It
		 * does this by using a ThreadLocal that holds a Map, instead of the traditional
		 * approach to hold the SimpleDateFormat in a ThreadLocal.
		 */
		public static class SafeDateFormat {
			private final String sFormat;
			private static final ThreadLocal<Map<String, SimpleDateFormat>> _dateFormats = new ThreadLocal<Map<String, SimpleDateFormat>>() {
				public Map<String, SimpleDateFormat> initialValue() {
					return new HashMap<String, SimpleDateFormat>();
				}
			};

			private SimpleDateFormat getDateFormat(String format) {
				Map<String, SimpleDateFormat> formatters = _dateFormats.get();
				SimpleDateFormat formatter = formatters.get(format);
				if (formatter == null) {
					formatter = new SimpleDateFormat(format);
					formatters.put(format, formatter);
				}
				return formatter;
			}

			public SafeDateFormat(String format) {
				sFormat = format;
			}

			public String format(Date date) {
				return getDateFormat(sFormat).format(date);
			}

			public String format(Object date) {
				return getDateFormat(sFormat).format(date);
			}

			public Date parse(String day) throws ParseException {
				return getDateFormat(sFormat).parse(day);
			}

			public void setTimeZone(TimeZone tz) {
				getDateFormat(sFormat).setTimeZone(tz);
			}

			public void setCalendar(Calendar cal) {
				getDateFormat(sFormat).setCalendar(cal);
			}

			public void setNumberFormat(NumberFormat format) {
				getDateFormat(sFormat).setNumberFormat(format);
			}

			public void setLenient(boolean lenient) {
				getDateFormat(sFormat).setLenient(lenient);
			}

			public void setDateFormatSymbols(DateFormatSymbols symbols) {
				getDateFormat(sFormat).setDateFormatSymbols(symbols);
			}

			public void set2DigitYearStart(Date date) {
				getDateFormat(sFormat).set2DigitYearStart(date);
			}
		}
	}
	
	/**
	 * Static class to get/post the http request.
	 */
	public static final class HTTP {
		public static final int MAXCONNPERROUTE = 100;
		static final  RequestConfig REQUESTCONFIG = RequestConfig.custom()
		.setSocketTimeout(30000)
		.setExpectContinueEnabled(false)
		.setConnectTimeout(30000)
		.build();
		/**
		 *  
		 * @param sUrl 
		 * @param hParams Parameters that need to be post to server
		 * @return
		 */
		public static final String post(String sUrl, Map<String, String> hParams, String sCharset) {
			 /*CloseableHttpClient oClient = HttpClients.custom()
			  .setConnectionManager(CM)
			   .build();*/
			CloseableHttpClient oClient = HttpClients.custom()
			.setMaxConnPerRoute(MAXCONNPERROUTE)
			.build();
			String sResp = null;
			CloseableHttpResponse response = null;
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
				if (hParams != null) {
					Set<String> oSetNames = hParams.keySet();
					for (String sName : oSetNames) {
						nameValuePairs.add(new BasicNameValuePair(sName, hParams.get(sName)));
					}
				}
				final HttpPost  oPost = new HttpPost(sUrl);
				oPost.setHeader("Connection", "close");
				try {
					oPost.setConfig(REQUESTCONFIG);
					oPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					response = oClient.execute(oPost);
					HttpEntity responseEntity = response.getEntity();
					if (response.getStatusLine().getStatusCode() == 200) {
						if (responseEntity != null) {
							sResp = EntityUtils.toString(responseEntity, sCharset);
						}
					} 
					EntityUtils.consume(responseEntity);
				} catch(Exception exp) {
					oPost.abort();
					exp.printStackTrace();
				}finally {
					oPost.releaseConnection();
					try {
						if (response != null) {
							response.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} finally {
				try {
					oClient.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return sResp;
		}
		
		public static final String get(String sUrl, Map<String, String> hParams, String sCharset) {
			 /*CloseableHttpClient oClient = HttpClients.custom()
			  .setConnectionManager(CM)
			   .build();*/
			try {
				sUrl = java.net.URLDecoder.decode(fullUrl(sUrl, hParams),sCharset);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			CloseableHttpClient oClient = HttpClients.custom()
			.setMaxConnPerRoute(MAXCONNPERROUTE)
			.build();
			String sResp = null;
			CloseableHttpResponse response = null;
			URI uri = null;
			
			try {
				URL url = new URL(sUrl);
				uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
			try {
				final HttpGet oPost= new HttpGet(uri);
				oPost.setHeader("Connection", "close");
				try {
					oPost.setConfig(REQUESTCONFIG);
					response = oClient.execute(oPost);
					HttpEntity responseEntity = response.getEntity();
					if (response.getStatusLine().getStatusCode() == 200) {
						if (responseEntity != null) {
							sResp = EntityUtils.toString(responseEntity, sCharset);
						}
					} 
					EntityUtils.consume(responseEntity);
				} catch(Exception exp) {
					oPost.abort();
					exp.printStackTrace();
				}finally {
					oPost.releaseConnection();
					try {
						if (response != null) {
							response.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} finally {
				try {
					oClient.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return sResp;
		}
		
		private static String fullUrl(String sUrl, Map<String, String> hParams) {
			if (hParams == null) {
				return sUrl;
			}
			Set<String> oSetKey = hParams.keySet();
			Iterator<String> iter = oSetKey.iterator();
			StringBuilder oSb = new StringBuilder("?");
			while (iter.hasNext()) {
				String sKey = iter.next();
				String sVal = hParams.get(sKey);
				oSb
				.append(sKey)
				.append("=")
				.append(sVal)
				.append("&");
			}
			oSb.deleteCharAt(oSb.length() - 1);
			return sUrl + oSb.toString();
		}
	}
	
}
