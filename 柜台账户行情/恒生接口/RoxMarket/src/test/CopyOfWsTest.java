package test;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;

import com.cfwx.rox.businesssync.market.entity.StockInfo;
import com.cfwx.rox.businesssync.market.structure.BaseStructure;
import com.cfwx.rox.businesssync.market.wsclient.EjdbWebService;

/**
 * @author J.C.J
 *         2013-11-21
 */
public class CopyOfWsTest
{

	public static void main(String[] args) throws Exception
	{

		long st = System.currentTimeMillis();
		String s = new EjdbWebService().getEjdbWebServiceSoap().getData("GetZQJCXX_WS");

		// System.out.println(s);
		//
		PropertyDescriptor[] pdArr = PropertyUtils.getPropertyDescriptors(new StockInfo());

		long e = System.currentTimeMillis();
		System.out.println("耗时：" + (e - st));
		List<StockInfo> list = jsonStringToList(s);

		System.out.println(list.size());

		for (StockInfo si : list)
			BaseStructure.stockMap.put(si.getPremaryCode(), si);

		Map<String, Integer> map = new HashMap<String, Integer>();

		// for(StockInfo si :list){
		// if(map.get(si.getJYSC()+"_"+si.getZQLB())==null)
		// map.put(si.getJYSC()+"_"+si.getZQLB(), 0);
		// else{
		// map.put(si.getJYSC()+"_"+si.getZQLB(), map.get(si.getJYSC()+"_"+si.getZQLB())+1);
		// }
		// }

		// for(StockInfo si :list){
		// if(map.get(si.getZQLB())==null)
		// map.put(si.getZQLB(), 0);
		// else{
		// map.put(si.getZQLB(), map.get(si.getZQLB())+1);
		// }
		// }
		// A股，B股，开放式基金，债券，指数
		// Iterator<String> it = map.keySet().iterator();
		// String key = "";
		// while(it.hasNext()){
		// key = it.next();
		// System.out.println(key+":"+map.get(key));
		// }
	}

	/**
	 * 处理json字符串
	 * 
	 * @param rsContent
	 * @return 对象集合
	 * @throws Exception
	 */
	private static List<StockInfo> jsonStringToList(String rsContent) throws Exception
	{
		List<StockInfo> jsonList = new ArrayList<StockInfo>();
		JSONObject jsonObject = null;
		StockInfo tempStock = null;
		try
		{
			JSONArray arry = JSONArray.fromObject(rsContent);

			for (int i = 0; i < arry.size(); i++)
			{
				jsonObject = arry.getJSONObject(i);
				tempStock = new StockInfo();
				for (Iterator<?> iter = jsonObject.keys(); iter.hasNext();)
				{
					String key = (String) iter.next();
					String value = jsonObject.get(key).toString();
					PropertyUtils.setProperty(tempStock, key, value);
				}
				tempStock.setPremaryCode(tempStock.getJYSC().equals("上交所") ? "sh" + tempStock.getZQDM() : "sz" + tempStock.getZQDM());
				jsonList.add(tempStock);
			}
		}
		catch (Exception e)
		{
			throw new Exception("处理字符串异常", e);
		}
		return jsonList;
	}
}
