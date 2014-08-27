/**
 * 
 */
package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author J.C.J
 *
 * 2013-12-17
 */
public class HaiKouWebServiceIP {

	/**
	 * 添加IP至信任列表
	 * @param args
	 */
	public static void main(String[] args) {
		
		String ip ="113.88.152.195";
		
		try {
			URL url = new URL("http://59.50.95.63:9999/EjdbWebService.asmx/UpdateAllowIpAddress?ipAddress="+ip+"&updateKey=cfwx68508081");
			HttpURLConnection  conn = (HttpURLConnection)url.openConnection();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));//

			String lines;
			String resMessage ="";
			while ((lines = reader.readLine()) != null) {
				 resMessage += lines;
			}
			conn.disconnect();
			System.out.println(resMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
		public static void main2(String[] args) {
			
			try {
				URL url = new URL("http://59.50.95.63:9999/EjdbWebService.asmx/GetData?procedure=GetZQJCXX_WS");
				HttpURLConnection  conn = (HttpURLConnection)url.openConnection();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "utf-8"));//
				
				StringBuilder sb = new StringBuilder();
				
				char[] buf = new char[10048];
				long s = System.currentTimeMillis();
				while ((reader.read(buf)) != -1) {
					sb.append(buf);
				}
				long e = System.currentTimeMillis();
				System.out.println("耗时："+(e-s));
				conn.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
