/** @file
* 演示T2_SDK进行异步发包、收包
* @author  T2小组
* @author  恒生电子股份有限公司
* @version 1.0
* @date    20090217
*/

#include <Include/t2sdk_interface.h>

class CCallback : public CCallbackInterface
{
public:
	unsigned long  FUNCTION_CALL_MODE QueryInterface(const char *iid, IKnown **ppv);
	unsigned long  FUNCTION_CALL_MODE AddRef();
	unsigned long  FUNCTION_CALL_MODE Release();
	void FUNCTION_CALL_MODE OnConnect(CConnectionInterface *lpConnection);
	void FUNCTION_CALL_MODE OnSafeConnect(CConnectionInterface *lpConnection);
	void FUNCTION_CALL_MODE OnRegister(CConnectionInterface *lpConnection);
	void FUNCTION_CALL_MODE OnClose(CConnectionInterface *lpConnection);
	void FUNCTION_CALL_MODE OnSent(CConnectionInterface *lpConnection, int hSend, void *reserved1, void *reserved2, int nQueuingData);
	void FUNCTION_CALL_MODE Reserved1(void *a, void *b, void *c, void *d);
	void FUNCTION_CALL_MODE Reserved2(void *a, void *b, void *c, void *d);
	int  FUNCTION_CALL_MODE Reserved3();
	void FUNCTION_CALL_MODE Reserved4();
	void FUNCTION_CALL_MODE Reserved5();
	void FUNCTION_CALL_MODE Reserved6();
	void FUNCTION_CALL_MODE Reserved7();
	void FUNCTION_CALL_MODE OnReceivedBiz(CConnectionInterface *lpConnection, int hSend, const void *lpUnPackerOrStr, int nResult);
	void FUNCTION_CALL_MODE OnReceivedBizEx(CConnectionInterface *lpConnection, int hSend, LPRET_DATA lpRetData, const void *lpUnpackerOrStr, int nResult);
	void FUNCTION_CALL_MODE OnReceivedBizMsg(CConnectionInterface *lpConnection, int hSend, IBizMessage* lpMsg);

};
//以下各回调方法的实现仅仅为演示使用
unsigned long CCallback::QueryInterface(const char *iid, IKnown **ppv)
{
	return 0;
}

unsigned long CCallback::AddRef()
{
	return 0;
}

unsigned long CCallback::Release()
{
	return 0;
}
void CCallback::OnConnect(CConnectionInterface *lpConnection)
{
	puts("CCallback::OnConnect");
}

void CCallback::OnSafeConnect(CConnectionInterface *lpConnection)
{
	puts("CCallback::OnSafeConnect");
}

void CCallback::OnRegister(CConnectionInterface *lpConnection)
{
	puts("CCallback::OnRegister");
}

void CCallback::OnClose(CConnectionInterface *lpConnection)
{
	puts("CCallback::OnClose");
}

void CCallback::OnSent(CConnectionInterface *lpConnection, int hSend, void *reserved1, void *reserved2, int nQueuingData)
{

}

void CCallback::OnReceivedBiz(CConnectionInterface *lpConnection, int hSend, const void *lpUnpackerOrStr, int nResult)
{

}
void CCallback::OnReceivedBizEx(CConnectionInterface *lpConnection, int hSend, LPRET_DATA lpRetData, const void *lpUnpackerOrStr, int nResult)
{

}
void CCallback::OnReceivedBizMsg(CConnectionInterface *lpConnection, int hSend, IBizMessage* lpMsg)
{

}
void CCallback::Reserved1(void *a, void *b, void *c, void *d)
{
}

void CCallback::Reserved2(void *a, void *b, void *c, void *d)
{
}
int  CCallback::Reserved3()
{
	return 0;
}

void CCallback::Reserved4()
{
}

void CCallback::Reserved5()
{
}

void CCallback::Reserved6()
{
}

void CCallback::Reserved7()
{
}
int main(int argc,char *argv[])
{

    //通过T2SDK的引出函数，来获取一个新的CConfig对象指针
    //此对象在创建连接对象时被传递，用于配置所创建的连接对象的各种属性（比如服务器IP地址、安全模式等）
    //值得注意的是，在向配置对象设置配置信息时，配置信息既可以从ini文件中载入，
    //也可以在程序代码中设定，或者是2者的混合，如果对同一个配置项设不同的值，则以最近一次设置为准
    CConfigInterface * lpConfig = NewConfig();

    //通过T2SDK的引出函数NewXXXX返回的对象，需要调用对象的Release方法释放，而不能直接用delete
    //因为t2sdk.dll和调用程序可能是由不同的编译器、编译模式生成，delete可能会导致异常
    //为了适合Delphi等使用（Delphi对接口自动调用AddRef方法），用C开发的代码，需要在NewXXXX之后调用一下AddRef
    //以保证引用计数正确
    lpConfig->AddRef();

    //[t2sdk] servers指定需要连接的IP地址及端口
     lpConfig->Load("publish.ini");



    //通过T2SDK的引出函数，来获取一个新的CConnection对象指针
    CConnectionInterface *lpConnection = NewConnection(lpConfig);
    lpConnection->AddRef();

    //创建自定义类CCallback的对象（在创建连接时需传递此对象，请看下面代码）
    CCallback callback;
    
    char pHqData[128] = {0};

    int ret = 0;

    //初始化连接对象，返回0表示初始化成功，注意此时并没开始连接服务器
    if (0 == (ret = lpConnection->Create2BizMsg(&callback)))
    {
        //正式开始连接，参数1000为超时参数，单位是ms
        if (ret = lpConnection->Connect(1000))
        {
            puts(lpConnection->GetErrorMsg(ret));
        }
        else
        {
        	   //连接成功后，构造发布消息并发送
        	   
            //通过T2SDK的引出函数，来获取一个新的IBizMessage对象指针
            IBizMessage * lpMessage = NewBizMessage();
            lpMessage->AddRef();
            
        	  //初始化BMessage，REQUEST_PACKET为包类型（请求包）,620003，620020~620099:消息发布
            int funid = atoi((char*)lpConfig->GetString("publish","function_id",""));
            lpMessage->SetPacketType(REQUEST_PACKET);
            lpMessage->SetFunction(funid);
            
            //类型 11:行情
            int issueid = atoi((char*)lpConfig->GetString("publish","issue_no",""));
            lpMessage->SetIssueType(issueid);

            //添加过滤字段
	          int nCount = lpConfig->GetInt("publish","filter_count",0);
	          //获取打包器并打入内容
            IF2Packer* lpPack = NewPacker(2);
            lpPack->AddRef(); 
            lpPack->BeginPack();
            int i =0;
	          for ( i=1;i<=nCount;i++)
						{
							char lName[128]={0};
							sprintf(lName,"filter_name%d",i);
							char* filterName = (char*)lpConfig->GetString("publish",lName,"");
							 lpPack->AddField(filterName, 'S');	
						}
	          for ( i=1;i<=nCount;i++)
						{
							char lValue[128]={0};
							sprintf(lValue,"filter_value%d",i);
							char* filterValue = (char*)lpConfig->GetString("publish",lValue,"");
							lpPack->AddStr(filterValue);
							
						}						
            lpPack->EndPack();
            
            //发布主键
            lpMessage->SetKeyInfo(lpPack->GetPackBuf(),lpPack->GetPackLen());
            
            lpPack->FreeMem(lpPack->GetPackBuf());
            lpPack->Release();

            
            memcpy(pHqData, "0   251,600570,13.850,13.710,1,", 31);
            lpMessage->SetContent(pHqData, 31);
           
            //发送
					  if ((ret = lpConnection->SendBizMsg(lpMessage, 1)) < 0)
					  {
					     puts(lpConnection->GetErrorMsg(ret));
					  }
					  else
					  {
					     puts("发布成功！\n");	
					  }

            lpMessage->Release();
        }
    }
    else
    {
        puts(lpConnection->GetErrorMsg(ret));
    }

    //通过getchar阻塞线程，等待服务端应答包到达
    getchar();
    lpConnection->Close();
    lpConnection->Release();
    lpConfig->Release();


    return 0;
}
