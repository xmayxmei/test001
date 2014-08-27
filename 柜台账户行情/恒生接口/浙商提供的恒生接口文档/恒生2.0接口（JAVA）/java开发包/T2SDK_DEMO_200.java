import com.hundsun.t2sdk.common.core.context.ContextUtil;
import com.hundsun.t2sdk.common.share.dataset.DatasetService;
import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.event.EventType;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

/**
 * T2SDK例子
 * @author zhaozhen
 *
 */
public class T2SDK_DEMO {
	public static void main(String[] args) {
		IClient client = null;
		T2Services server = T2Services.getInstance();
		try{  
			//启动T2SDK，只需要在服务器启动时调用一次就可以了
			server.init();
			server.start();
		
			client = server.getClient("tcmp");
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory().getEventByAlias("200", EventType.ET_REQUEST);
			//组织请求参数
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("op_branch_no");
			dataset.addColumn("op_entrust_way");
			dataset.addColumn("op_station");
			dataset.addColumn("function_id");
			dataset.addColumn("input_content");
			dataset.addColumn("password");
			dataset.addColumn("content_type");
			dataset.addColumn("account_content");
			dataset.addColumn("branch_no");
			dataset.appendRow();
			dataset.updateString("op_branch_no", "100");
			dataset.updateString("op_entrust_way", "7");
			dataset.updateString("op_station", "005056C00008");
			dataset.updateString("function_id", "200");
			dataset.updateString("input_content", "6");
			dataset.updateString("password", "12345678");
			dataset.updateString("content_type", "1");
			dataset.updateString("account_content", "210000000000000");
			dataset.updateString("branch_no", "100");
			
			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//打印返回结果
			DatasetService.printDataset(rsp.getEventDatas().getDataset(0));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
}
