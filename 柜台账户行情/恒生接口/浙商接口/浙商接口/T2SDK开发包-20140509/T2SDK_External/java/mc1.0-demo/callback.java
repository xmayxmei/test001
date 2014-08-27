
import com.hundsun.t2sdk.common.share.dataset.DatasetService;
import com.hundsun.t2sdk.common.share.event.PackService;
import com.hundsun.t2sdk.impl.client.ClientSocket;
import com.hundsun.t2sdk.interfaces.ICallBackMethod;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import com.hundsun.t2sdk.interfaces.share.event.IPack;

public class callback implements ICallBackMethod {

	private static IClient client = null;

	public static void setClient(IClient client) {
			callback.client = client;
	}

	@Override
	public void execute(IEvent arg0, ClientSocket arg1) {
		// TODO Auto-generated method stub
		// 获取消息功能号
		//相当于IBizMessage的GetFunction()
		long iFunctionID = arg0
				.getIntegerAttributeValue(EventTagdef.TAG_FUNCTION_ID);
 
		if (iFunctionID == 620000) { // 消息中心心跳
			arg0.changeToresponse();
			try {
				callback.client.send(arg0);
			} catch (T2SDKException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// 收到发布过来的行情
		else if (iFunctionID == 620003 || iFunctionID == 620025) {
			// 获取过滤信息
			//相当于IBizMessage的GetKeyInfo
			byte[] keyInfo = arg0
					.getByteArrayAttributeValue(EventTagdef.TAG_KEY_INFO);
			IPack outPack = PackService.getPacker(keyInfo, "utf-8");
			StringBuilder sb = new StringBuilder();
			// 获得结果集
			// 开始读取单个结果集的信息
			IDataset ds = outPack.getDataset(0);
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

			System.out.println(sb);
			// 获取业务包
			////相当于IBizMessage的GetContent
			if (arg0.getEventDatas().getDatasetCount() > 0) {
				DatasetService.printDataset(arg0.getEventDatas().getDataset(0));
			}
		}
	}
}
