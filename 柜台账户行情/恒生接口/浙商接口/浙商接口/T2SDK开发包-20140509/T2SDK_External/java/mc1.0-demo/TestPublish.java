
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

public class TestPublish {

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
		// 构造请求消息 620003，620020~620099是发布能号范围
		//相当于IBizMessage的  SetPacketType(REQUEST_PACKET)和SetFunction(620003);
		IEvent event = ContextUtil.getServiceContext().getEventFactory()
				.getEventByAlias("620003", EventType.ET_REQUEST);
		// 设置订阅类型issuetype，这里以1为例子
		//相当于IBizMessage的SetIssueType
		event.setIntegerAttributeValue(EventTagdef.TAG_ISSUE_TYPE, 1);
		// 设置订阅关键字keyinfo
    //相当于IBizMessage的SetKeyInfo
		IDataset dataset = DatasetService.getDefaultInstance().getDataset();
		dataset.addColumn("pub_no");
		dataset.appendRow();
		dataset.updateString("pub_no", "1");
		// 放入PACK获取二进制
		IPack outPack = PackService.getPacker(IPack.VERSION_2, "utf-8");
		outPack.addDataset(dataset);

		event.setByteArrayAttributeValue(EventTagdef.TAG_KEY_INFO, outPack
				.Pack());
		// 放入业务包
		//相当于IBizMessage的SetContent
		IDataset datasetBody = DatasetService.getDefaultInstance().getDataset();
		datasetBody.addColumn("fund_no");
		datasetBody.appendRow();
		datasetBody.updateString("fund_no", "123456");
		event.putEventData(datasetBody);

		try {
			client.send(event);
			System.out.println("发布成功！");
		} catch (T2SDKException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("发布失败！");
		}

	}
}
