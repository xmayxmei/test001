package com.util;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ReflectionUtils;

import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.EventReturnCode;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

/**
 * 
 * @author wangsy
 *
 */
public class InterfaceServiceHelper {

	/**
	 * 解析返回值
	 * @param event
	 * @throws UnsupportedEncodingException 
	 */
	public static List<Object> outputAnEvent(IEvent event,Class<?> cls) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		//读取event的返回码,-61账号错误，-63密码错误
		sb.append("returnCode: " + event.getReturnCode() + "\n");
		//读取event的错误号，如果没有则返回 "0"
		sb.append("errorNo: " + event.getErrorNo() + "\n");
		//读取event的错误信息，如果没有设置则返回null
		sb.append("errorInfo: " + event.getErrorInfo() + "\n");
		List<Object> list = new ArrayList<Object>();
		Object resultObj = null;
		//如正确返回，则解析返回结果集
		boolean ifTrue=false;
		if (event.getReturnCode() == EventReturnCode.I_OK) {
			ifTrue = true;
		}
		//获得结果集
		IDatasets result = event.getEventDatas();
		//获得结果集总数
		int datasetCount = result.getDatasetCount();
		if(datasetCount>0){
			//遍历所有的结果集
			for (int i = 0; i < datasetCount; i++) {

				sb.append("dataset - " + result.getDatasetName(i)+ "\n");
				// 开始读取单个结果集的信息
				IDataset ds = result.getDataset(i);
				int columnCount = ds.getColumnCount();
				// 遍历单个结果集列信息
				// 遍历单个结果集记录，遍历前首先将指针置到开始
				ds.beforeFirst();
				int rowSize = 0;
				System.out.println("ds.getRowCount()"+ds.getRowCount());
				while(ds.hasNext()&&ds.getRowCount()>0) {
					sb.append("\n"+"第"+rowSize+"个结果集——————————————————————————>"+"\n");
					ds.next();
					// 打印日志
					for(int j = 1; j < columnCount; j++) 
					{
						sb.append(ds.getColumnName(j)).append(" == ").append(ds.getString(j))
						.append(" type "+ds.getColumnType(j)).append(", \n");
					}
					resultObj = setResults(ds, cls,ifTrue);
					list.add(resultObj);
					rowSize++;
				}
			}
		}
		//	}
		System.out.println("result ==>"+new String(sb.toString()));
		return list;

	}





	/**
	 * 映射方式设置对象属性值
	 * @param dataSet
	 */
	protected static Object setResults(IDataset dataSet, Class<?> classzz,boolean ifTrue){
		// 反射获取所有方法
		Method[] methods = ReflectionUtils.getAllDeclaredMethods(classzz);

		try{
			if (null != methods && dataSet!=null && dataSet.getRowCount()>0)
			{
				Object obj = classzz.newInstance();
				for (Method m : methods)
				{


					String methodName = m.getName();
					String fstLetter = "";
					//正确返回结果集设置errorNo=0
					//	System.out.println(methodName);
					if(ifTrue&&methodName.equals("setErrorNo")){
						m.invoke(obj, new Object[]{"0"});
					}
					else if (methodName.startsWith("set"))
					{

						methodName = methodName.substring(3);
						// 第一个字母转小写
						fstLetter = methodName.substring(0, 1).toLowerCase();
						// 获取属性名称(大写转为小写, 按格式增加"_"号)
						methodName =  fstLetter +   methodName.substring(1).replaceAll("([A-Z])", "_$1").toLowerCase();
						try
						{							
							m.invoke(obj, new Object[] { dataSet.getString(methodName) });

						}
						catch (Exception e1)
						{
							if (m.getParameterTypes()[0].getName().equals("java.lang.Long")||m.getParameterTypes()[0].getName().equals("long"))
							{
								m.invoke(obj, new Object[] { dataSet.getLong(methodName) });
							}
							else if (m.getParameterTypes()[0].getName().equals("java.util.Date"))
							{
								m.invoke(
										obj,
										new Object[] { dataSet.getString(methodName) != null ? StringUtils.StrToDate(dataSet
												.getString(methodName)) : null });
							}
							else if (m.getParameterTypes()[0].getName().equals("java.lang.Integer")||m.getParameterTypes()[0].getName().equals("int"))
							{
								m.invoke(obj, new Object[] { dataSet.getInt(methodName) });
							}else {
								//	System.out.println("methodName:"+methodName+" setResults doesn't match type...."+m.getParameterTypes()[0].getName());
							}

						}
					}

				}


				return obj;

			}

		}catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * 双精度转换字符格式, 保留两位小数
	 * 
	 * @param dbValue
	 * @return
	 */
	public  String getDoubleStr(Double dbValue) {
		if(null == dbValue){
			return "";
		}
		BigDecimal percentD = new BigDecimal(dbValue);

		return percentD.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
	}

}
