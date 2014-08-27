package test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cfwx.rox.businesssync.market.entity.StockInfo;
import com.cfwx.rox.businesssync.market.wsclient.EjdbWebService;
import com.cfwx.util.ExcelUtils;

import java.beans.PropertyDescriptor;
import java.util.*;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author J.C.J
 *
 * 2013-11-21
 */
public class WsTest {
	public static void main(String[] args) throws Exception{
		long st = System.currentTimeMillis();
		String s = new EjdbWebService().getEjdbWebServiceSoap().getData("GetZQJCXX_WS");
		
//		System.out.println(s);
//		
//		PropertyDescriptor[] pdArr = PropertyUtils.getPropertyDescriptors(new StockInfo());
		
		long e = System.currentTimeMillis();
		System.out.println("耗时："+(e-st)+",字符串长度："+s.length());
		List<StockInfo> list = jsonStringToList(s);
		System.out.println("证券数量："+list.size());
//		StockInfo si = null;
//		for(int i=3000;i<4000;i++){
//			si = list.get(i);
//			for(PropertyDescriptor pd : pdArr){
//				if(!pd.getName().equals("class") &&  !pd.getName().equals("HYMC") && !pd.getName().equals("HYDM") && si.getJYSC().equals("") ){
//					System.out.print(pd.getName());
//					System.out.print(": ");
//					System.out.print(PropertyUtils.getProperty(si, pd.getName())+",");
//				}
//			}
//			System.out.println();
//		}
		try {
			ExcelUtils.export(getTitleList(),list,"MsgSendHistory",null);
			System.out.println("文件导出完毕...");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	/**
	 * 处理json字符串
	 * 
	 * @param rsContent
	 * @return 对象集合
	 * @throws Exception
	 */
	private static List<StockInfo> jsonStringToList(String rsContent)
			throws Exception {
		List<StockInfo> jsonList = new ArrayList<StockInfo>();
		JSONObject jsonObject = null;
		StockInfo tempStock= null;
		try {
			JSONArray arry = JSONArray.fromObject(rsContent);
		
			for (int i = 0; i < arry.size(); i++) {
				jsonObject = arry.getJSONObject(i);
				tempStock = new StockInfo();
				for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();) {
					String key = (String) iter.next();
					String value = jsonObject.get(key).toString();
					PropertyUtils.setProperty(tempStock, key, value);
				}
				jsonList.add(tempStock);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("处理字符串异常",e);
		}
		return jsonList;
	}
	
	private static List<Map<String,String>> getTitleList()throws Exception{
		
		PropertyDescriptor[] pdArr = PropertyUtils.getPropertyDescriptors(new StockInfo());
		List<Map<String,String>> titleList = new ArrayList<Map<String,String>>();
		Map<String,String> titleMap = null;
		for(PropertyDescriptor pd : pdArr){
			if(!pd.getName().equals("class")){
				titleMap = new HashMap<String,String>();
				titleMap.put("title", pd.getName());
				titleMap.put("beanAttribute", pd.getName());
				titleMap.put("columnWidth", "200");
				titleList.add(titleMap);
			}
		}
		return titleList;
	}
	
}
