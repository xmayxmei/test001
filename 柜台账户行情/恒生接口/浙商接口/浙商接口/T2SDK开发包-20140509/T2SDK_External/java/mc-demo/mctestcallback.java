package test;

import com.hundsun.mcapi.interfaces.ISubCallback;
import com.hundsun.mcapi.subscribe.MCSubscribeParameter;
import com.hundsun.t2sdk.common.share.dataset.DatasetService;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;

public class mctestcallback  implements ISubCallback{
	public static int g_iCount = 0;

    @Override
    public void OnReceived(String topicName, IEvent event) {
        // TODO Auto-generated method stub

    	g_iCount++;
    	//if(g_iCount%100 == 0)
        System.out.println("Count:"+g_iCount);
        System.out.println(topicName);
        DatasetService.printDataset(event.getEventDatas().getDataset(0));
    }

    @Override
    public void OnRecvTickMsg(MCSubscribeParameter param,String tickMsgInfo) {
        // TODO Auto-generated method stub
        
    }

}
