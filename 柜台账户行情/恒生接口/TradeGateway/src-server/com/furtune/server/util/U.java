package com.furtune.server.util;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * <code>U</code> provides utilities methods.
 *
 * @author Jimmy
 * @since Trading - v.0.0.1(April 12, 2014)
 * 
 */
public class U {
	public static final class STR {
		/**
		 * 
		 * @param csContent
		 * @return
		 */
		public static boolean isEmpty(CharSequence csContent) {
			if (csContent == null || "".equals(csContent)) {
				return true;
			}
			return false;
		}
		
		/**
		 * @param sReq
		 * @return
		 */
		public static Map<String, String> convert2Map(String sReq, String sSep, String sSubSep) {
			Map<String, String> hReq = new HashMap<String, String>();
			IStringTokenizer st = TokenizerFactory.createTokenizer(sReq, sSep);
			while (st.hasMoreTokens()) {
				String sPartData = st.nextToken();
				IStringTokenizer stSub = TokenizerFactory.createTokenizer(sPartData, sSubSep);
				if (stSub.countTokens() < 2) {
					continue;
				}
				try{
					hReq.put(stSub.nextToken(), stSub.nextToken());
				}catch (NoSuchElementException e) {
					e.printStackTrace();
				}
			}
			return hReq;
		}
	}
}
