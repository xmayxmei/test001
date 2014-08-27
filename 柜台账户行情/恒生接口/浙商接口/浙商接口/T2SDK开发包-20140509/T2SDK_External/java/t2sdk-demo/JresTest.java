
import com.hundsun.t2sdk.common.core.context.ContextUtil;
import com.hundsun.t2sdk.common.share.dataset.DatasetService;
import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.EventReturnCode;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.EventType;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

public class JresTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IClient client = null;
		T2Services server = T2Services.getInstance();
		// 获取T2SDK配置文件中配置的连接
		try {
			server.init();
			server.start();
			// myserver是配置在配置文件中的服务
			client = server.getClient("myserver");
			// 获取event
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("88", EventType.ET_REQUEST);
			// 往event中添加dataset
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			dataset.addColumn("branch_no");
			dataset.addColumn("operator_no");
			dataset.addColumn("fund_account");
			dataset.appendRow();
			dataset.updateString("branch_no", "1");
			dataset.updateString("operator_no", "1");
			dataset.updateString("fund_account", "2");
			event.putEventData(dataset);
			// 同步发送如下
			IEvent rsp = client.sendReceive(event, 10000);
			//先判断返回值
			if(rsp.getReturnCode() !=  EventReturnCode.I_OK){ //返回错误
				System.out.println(rsp.getErrorNo() +" : " + rsp.getErrorInfo());
			}else{
				StringBuilder sb = new StringBuilder();
				//获得结果集
				IDatasets result = rsp.getEventDatas();
				//获得结果集总数
				int datasetCount = result.getDatasetCount();
				//遍历所有的结果集
				for (int i = 0; i < datasetCount; i++) {
					sb.append("\n" + "dataset - " + result.getDatasetName(i)
									+ "\n");
					// 开始读取单个结果集的信息
					IDataset ds = result.getDataset(i);
					int columnCount = ds.getColumnCount();
					// 遍历单个结果集列信息
					for (int j = 1; j <= columnCount; j++) {
						sb.append(ds.getColumnName(j));
						sb.append("|");
						sb.append(ds.getColumnType(j));
						sb.append("\n");
					}
					// 遍历单个结果集记录，遍历前首先将指针置到开始
					ds.beforeFirst();
					while (ds.hasNext()) {
						sb.append("\n");
						ds.next();
						for (int j = 1; j <= columnCount; j++) {
							sb.append(ds.getString(j));
						}
					}
				}
			    System.out.println(sb);
			}
			// 同步发送到此结束

			// 异步发送如下
			// 注意，在配置中配置的回调函数<method id="3" className="com.jres.test.CallBack"
			// />的id号必须填入event,否则回调不回应答
			event.setAttributeValue(EventTagdef.TAG_SENDERID, 3);
			client.send(event);
			// 这样异步发送出去后，应答会在com.jres.test.CallBack回调中返回
			// 异步发送到此结束
             
			// 最后
			server.stop();
		} catch (T2SDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
