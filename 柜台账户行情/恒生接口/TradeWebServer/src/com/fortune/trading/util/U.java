package com.fortune.trading.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * <code>U</code> is the utilities class. It provides general convenient methods.
 *
 * @author Colin, Jimmy
 * 
 * @since Trading v0.0.1 (April 24, 2014)
 */
public class U {
	/**
	 * @return
	 */
	public static String generateUID() {
		return UUID.randomUUID().toString();
	}
	
	public static String formatYYYYMMDD(Date oDate) {
		SimpleDateFormat oSdf = new SimpleDateFormat("yyyyMMdd");
		return oSdf.format(oDate);
	}
	/**
	 * @param hLoopData
	 * @param fx
	 */
	public static void foreach(Map<Object, Object> hLoopData, Function<Void, Object> fx) {
		Set<Object> oKeys = hLoopData.keySet();
		for (Object oKey : oKeys) {
			fx.apply(hLoopData.get(oKey));
		}
	}
	/**
	 * <code>ARR</code> provides Array's convenient methods.
	 */
	public static final class ARR {
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
	 * <code>CAPTCHA</code> 
	 *
	 */
	public static final class CAPTCHA{
		private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 26);
		
	    private static final char[] CODESEQUENCE = {/* 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',     
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',     
            'X', 'Y', 'Z', */'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' }; 
		/**
		 * 生成验证码图片，返回一个保存验证码和验证码图片的Map.<br>
		 * 返回Map的Key分别为:<br>
		 * code - 验证码<br>
		 * img - 验证码图片
		 * <p>
		 * 
		 * @param hParams 下面是传递参数的Key列表：<br>
		 * 		  width  - 验证码图片的宽度<br>
		 * 		  height - 验证码图片的高度<br>
		 * 		  count  - 验证码的个数<br>
		 * 		  bg     - 验证码图片的背景颜色<br>
		 *        fg     - 字体颜色<br>
		 *        font   - 字体 <br>
		 * @return
		 */
		public static Map<String, Object> generateCaptcha(IMap<String, Object> hParams) {
			int imgWidth = hParams.getAs("width", 100);
			int imgHeight = hParams.getAs("height", 50);
			int count = hParams.getAs("count", 4);
			Color clrBg = hParams.getAs("bg", Color.gray);
			Color clrFg = hParams.getAs("fg", Color.black);
			Font font = hParams.getAs("font", DEFAULT_FONT);
			StringBuffer randomCode = new StringBuffer();
			int codeX = (imgWidth - 4) / (count + 1);
			int codeY = imgHeight - 7;
			Random random = new Random();
			BufferedImage buffImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D gd = buffImg.createGraphics();
			gd.setColor(clrBg);
			gd.fillRect(0, 0, imgWidth, imgHeight);
			gd.setFont(font);
			gd.setColor(clrFg);
			for (int i = 0; i < count; i++) {
				String strRand = String.valueOf(CODESEQUENCE[random.nextInt(10)]);
				gd.drawString(strRand, (i + 1) * codeX, codeY);
				randomCode.append(strRand);
			}
			gd.dispose();
			
			Map<String, Object> hData = new HashMap<String, Object>(2);
			hData.put("code", randomCode.toString());
			hData.put("img", buffImg);
		    return hData;
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
	}
	
  /**
   * <code>RSA<code> 
   */
  public static final class RSA {
      /** 算法名称 */
      private static final String ALGORITHOM = "RSA";
      /** 默认的安全服务提供者 */
      private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();
  
      private static KeyPairGenerator keyPairGen;
  
      static {
          try {
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
          } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
          }
      }
      /**
       *  生成并返回RSA密钥对。
       * @param iKeySize
       * @return
       */
      public static synchronized KeyPair generateKeyPair(int iKeySize) {
        KeyPair oneKeyPair = null;
        keyPairGen.initialize(iKeySize, new SecureRandom(DateFormatUtils.format(Calendar.getInstance().getTime(),"yyyyMMddhhmmssSSS").getBytes()));
        oneKeyPair = keyPairGen.generateKeyPair();
        return oneKeyPair;
      }
    /**
     * 
     * @param privateKey
     * @param data
     * @return
     * @throws Exception
     */
      public static String decrypt(PrivateKey privateKey, String sData) throws Exception{
        byte[] aData = Hex.decodeHex(sData.toCharArray());
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.DECRYPT_MODE, privateKey);
        String sRst = new String(ci.doFinal(aData));
        if (!U.STR.isEmpty(sRst)) {
        	sRst = StringUtils.reverse(sRst);
        }
        return sRst;
      }
     /**
     * 
     * @param publicKey
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] data) throws Exception {
          Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
          ci.init(Cipher.ENCRYPT_MODE, publicKey);
          return ci.doFinal(data);
      }
  }
}
