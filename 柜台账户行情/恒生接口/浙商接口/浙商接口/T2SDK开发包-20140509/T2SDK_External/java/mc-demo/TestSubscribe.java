package test;

import java.io.IOException;

import com.hundsun.mcapi.MCServers;
import com.hundsun.mcapi.exception.MCException;
import com.hundsun.mcapi.exception.MCSubscribeException;
import com.hundsun.mcapi.interfaces.ISubscriber;
import com.hundsun.mcapi.subscribe.MCSubscribeParameter;
import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.T2SDKException;

public class TestSubscribe {

    /**
     * @param args
     */
    public static void main(String[] args) throws MCSubscribeException {
         
        T2Services server = T2Services.getInstance();
        // TODO Auto-generated method stub
          //启动T2SDK，只需要在服务器启动时调用一次就可以了
            try {
				server.init();
			} catch (T2SDKException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            server.start();
            //初始化MC服务
            try {
				MCServers.MCInit();
			} catch (T2SDKException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		//获取MC服务中订阅接口
        ISubscriber subscriber = MCServers.GetSubscriber();
        MCSubscribeParameter subParam = new MCSubscribeParameter();
        subParam.SetTopicName("secu.rzrq_contract_flow");
        byte[] data = {1,3,5,8,9,10,2,3,0};
        subParam.SetAppData(data);
        subParam.SetFromNow(true);
        subParam.SetReplace(true);
        subParam.SetFilter("branch_no", "B");
        subParam.SetFilter("fund_account", "600625");
        int iRet = subscriber.SubscribeTopic(subParam, 3000);
        //返回iRet>0表示成功
        System.out.println("RESULT:"+iRet);  
        System.out.println("**********************************************");
        System.out.println("输入任意字符取消订阅");
        try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		iRet = subscriber.CancelSubscribeTopic(iRet);
		System.out.println("CancelSubscribeTopic:"+subParam.GetTopicName()+" 返回码："+iRet);
		System.out.println("输入任意字符退出");
        try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		//释放资源
		MCServers.Destroy();
		server.stop();
		System.out.println("退出！");
		
		
        
        
    }

}
