import java.io.IOException;

import com.hundsun.t2sdk.common.core.context.ContextUtil;
import com.hundsun.t2sdk.common.share.dataset.DatasetService;
import com.hundsun.t2sdk.common.share.event.PackService;
import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.EventType;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import com.hundsun.t2sdk.interfaces.share.event.IPack;

public class TestSubscribe {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		T2Services server = T2Services.getInstance();
		IClient client = null;
		// TODO Auto-generated method stub
		// 启动T2SDK，只需要在服务器启动时调用一次就可以了
		try {
			server.init();
			server.start();
			client = server.getClient("mcserver");
		} catch (T2SDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 在此设置回掉类发送用的接口
		callback.setClient(client);
		// 构造请求消息 620001是订阅功能号
		//相当于IBizMessage的  SetPacketType(REQUEST_PACKET)和SetFunction(620001);
		IEvent event = ContextUtil.getServiceContext().getEventFactory()
				.getEventByAlias("620001", EventType.ET_REQUEST);
		// 设置订阅类型issuetype，这里以1为例子
		//相当于IBizMessage的SetIssueType
		event.setIntegerAttributeValue(EventTagdef.TAG_ISSUE_TYPE, 1);
		// 设置订阅关键字keyinfo
    //相当于IBizMessage的SetKeyInfo
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("pub_no");
		dataset.appendRow();
		dataset.updateString("pub_no", "*");
        //放入PACK获取二进制
		IPack outPack = PackService.getPacker(IPack.VERSION_2, "utf-8");
		outPack.addDataset(dataset);

		event.setByteArrayAttributeValue(EventTagdef.TAG_KEY_INFO, outPack
				.Pack());

		try {
			IEvent rep =  client.sendReceive(event);
			//这里是同步发送接收订阅应答
			//相当于IBizMessage的GetKeyInfo
	        byte[] info = rep.getByteArrayAttributeValue(EventTagdef.TAG_KEY_INFO);
	        IPack infoPack = PackService.getPacker(info, "utf-8");
	        IDataset ret = infoPack.getDataset(0);
	        //这个例子判断是以2.0兼容1.0的返回结果判断的，请根据实际情况判断
	        if(ret.findColumn("subscibe_result")>0 && ret.getInt("subscibe_result")==0)
	        {
	        	System.out.println("订阅成功！");
	        }
				} catch (T2SDKException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("**********************************************");
		System.out.println("输入任意字符取消订阅");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// 取消订阅的消息跟订阅的消息一样，只是把功能号设置成620002
		IEvent eventCancel = ContextUtil.getServiceContext().getEventFactory()
		.getEventByAlias("620002", EventType.ET_REQUEST);
		// 设置订阅类型issuetype，这里以1为例子
		eventCancel.setIntegerAttributeValue(EventTagdef.TAG_ISSUE_TYPE, 1);
		// 设置订阅关键字keyinfo
		eventCancel.setByteArrayAttributeValue(EventTagdef.TAG_KEY_INFO, outPack
				.Pack());
		try {
			IEvent rep =  client.sendReceive(eventCancel);
	        byte[] info = rep.getByteArrayAttributeValue(EventTagdef.TAG_KEY_INFO);
	        IPack infoPack = PackService.getPacker(info, "utf-8");
	        IDataset ret = infoPack.getDataset(0);
	        //这个例子判断是以2.0兼容1.0的返回结果判断的，请根据实际情况判断
	        if(ret.findColumn("subscibe_result")>0 && ret.getInt("subscibe_result")==0)
	        {
	        	System.out.println("取消订阅成功！");
	        }
		} catch (T2SDKException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("**********************************************");
		System.out.println("输入任意字符退出");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		server.stop();
		System.out.println("退出！");

	}

}
