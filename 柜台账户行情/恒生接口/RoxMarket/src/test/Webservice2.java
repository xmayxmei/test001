//package test;
//
//import org.apache.cxf.interceptor.LoggingInInterceptor;
//import org.apache.cxf.interceptor.LoggingOutInterceptor;
//import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
//
//import com.cfwx.rox.businesssync.market.wsclient.EjdbWebServiceSoap;
//
//
//public class Webservice2
//{
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args)
//	{
//		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
//		factoryBean.getInInterceptors().add(new LoggingInInterceptor());
//		factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
//		factoryBean.setServiceClass(EjdbWebServiceSoap.class);
//		//设置webservice wsdl地址
//		factoryBean.setAddress("http://59.50.95.63:9999/EjdbWebService.asmx?wsdl");
//		EjdbWebServiceSoap impl = (EjdbWebServiceSoap) factoryBean.create();
//		
//		//方法调用	
//		System.out.println(impl.getData("GETJYRXX_WS|RQ=2013-11-24"));
//	}
//
//}
