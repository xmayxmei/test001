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
			IEvent event = ContextUtil.getServiceContext().getEventFactory().getEventByAlias("302", EventType.ET_REQUEST);
			//组织请求参数
			IDataset dataset = DatasetService.getDefaultInstance().getDataset();
                        dataset.AddColumn("op_branch_no");
                        dataset.AddColumn("op_entrust_way");
                        dataset.AddColumn("op_station");
                        dataset.AddColumn("function_id");
                        dataset.AddColumn("password");
                        dataset.AddColumn("branch_no");
                        dataset.AddColumn("fund_account");
                        dataset.AddColumn("exchange_type");
                        dataset.AddColumn("stock_account");
                        dataset.AddColumn("stock_code");
                        dataset.AddColumn("entrust_amount");
                        dataset.AddColumn("entrust_price");
                        dataset.AddColumn("entrust_bs");
                        dataset.AddColumn("entrust_type");
                        dataset.AddColumn("entrust_prop");
                        dataset.AddColumn("batch_no");
			dataset.appendRow();
            dataset.updateString("op_branch_no","100");
			dataset.updateString("op_entrust_way","7");
			dataset.updateString("op_station","005056C00008");
			dataset.updateString("function_id","302");
			dataset.updateString("password","12345678");
			dataset.updateString("branch_no","100");
			dataset.updateString("fund_account","10000083");
			dataset.updateString("exchange_type","K");
			dataset.updateString("stock_account","10000083");
			dataset.updateString("stock_code","90008");
			dataset.updateString("entrust_amount","1000");
			dataset.updateString("entrust_price","20");
			dataset.updateString("entrust_bs","1");
			dataset.updateString("entrust_type","0");
			dataset.updateString("entrust_prop","e");
			dataset.updateString("batch_no","0");
			
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
