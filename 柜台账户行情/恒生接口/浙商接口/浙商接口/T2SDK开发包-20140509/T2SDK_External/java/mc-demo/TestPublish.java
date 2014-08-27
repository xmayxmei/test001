package test;

import java.io.IOException;

import com.hundsun.mcapi.MCServers;
import com.hundsun.mcapi.exception.MCException;
import com.hundsun.mcapi.exception.MCPublishException;
import com.hundsun.mcapi.interfaces.IPublisher;
import com.hundsun.t2sdk.common.share.dataset.DatasetService;
import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;

public class TestPublish {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		T2Services server = T2Services.getInstance();
		// TODO Auto-generated method stub
		try {
			// 启动T2SDK，只需要在服务器启动时调用一次就可以了
			server.init();
			server.start();
			// 初始化MC服务
			MCServers.MCInit();
			// 获取数据集
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
			// 填充数据集字段
			dataset.addColumn("branch_no");
			// dataset.addColumn("operator_no");
			dataset.addColumn("fund_account");
			dataset.appendRow();
			dataset.updateString("branch_no", "B");
			// dataset.updateString("operator_no", "1");
			dataset.updateString("fund_account", "600625");
			// 获取MC服务中发布接口
			IPublisher publisher = MCServers.GetPublisher();
			// 发布消息
			while (true) {
				int iRet = publisher.PubMsg("secu.rzrq_contract_flow", dataset,
						5000);
				System.out.println("发布返回结果：" + iRet);
				System.out.println("是否退出，[y]退出，[其他字符]继续");
				char ch = '0';
				try {
					ch = (char) System.in.read();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				if (ch == 'y') {
					break;
				}
			}
			// 释放资源
			MCServers.Destroy();
			server.stop();

		} catch (T2SDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MCPublishException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
