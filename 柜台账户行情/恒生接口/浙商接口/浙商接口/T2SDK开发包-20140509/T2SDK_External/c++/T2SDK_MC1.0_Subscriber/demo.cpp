/** @file
* 演示T2_SDK进行异步发包、收包
* @author  T2小组
* @author  恒生电子股份有限公司
* @version 1.0
* @date    20090217
*/

#include <Include/t2sdk_interface.h>

// 全局连接对象
CConnectionInterface *g_Connection = NULL;

void PrintUnPack(IF2UnPacker* lpUnPack)
{
	while (!lpUnPack->IsEOF())
	{
		for (int i=0;i<lpUnPack->GetColCount();i++)
		{
			char* colName = (char*)lpUnPack->GetColName(i);
			char colType = lpUnPack->GetColType(i);
			if (colType!='R')
			{
				char* colValue = (char*)lpUnPack->GetStrByIndex(i);
				printf("%s:			[%s]\n",colName,colValue);
			}
			else
			{
				int colLength = 0;
				char* colValue = (char*)lpUnPack->GetRawByIndex(i,&colLength);
				printf("%s:			[%s](%d)\n",colName,colValue,colLength);
			}
		}
		lpUnPack->Next();
	}
	
}
// 自定义类CCallback，通过继承（实现）CCallbackInterface，来自定义各种事件（包括连接成功、
// 连接断开、发送完成、收到数据等）发生时的回调方法
class CCallback : public CCallbackInterface
{
public:
    // 因为CCallbackInterface的最终纯虚基类是IKnown，所以需要实现一下这3个方法
    unsigned long  FUNCTION_CALL_MODE QueryInterface(const char *iid, IKnown **ppv);
    unsigned long  FUNCTION_CALL_MODE AddRef();
    unsigned long  FUNCTION_CALL_MODE Release();

    // 各种事件发生时的回调方法，实际使用时可以根据需要来选择实现，对于不需要的事件回调方法，可直接return
    // Reserved?为保留方法，为以后扩展做准备，实现时可直接return或return 0。
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

// 以下各回调方法的实现仅仅为演示使用
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

void CCallback::Reserved1(void *a, void *b, void *c, void *d)
{
}

void CCallback::Reserved2(void *a, void *b, void *c, void *d)
{
}

void CCallback::OnReceivedBizEx(CConnectionInterface *lpConnection, int hSend, LPRET_DATA lpRetData, const void *lpUnpackerOrStr, int nResult)
{
	
}

void CCallback::OnReceivedBiz(CConnectionInterface *lpConnection, int hSend, const void *lpUnPackerOrStr, int nResult)
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
void CCallback::OnReceivedBizMsg(CConnectionInterface *lpConnection, int hSend, IBizMessage* lpMsg)
{
	 int iFunctionID = lpMsg->GetFunction();
    
    if (iFunctionID == 620000) //消息中心心跳
    {
    	//printf("收到消息中心心跳请求\n");
    	lpMsg->ChangeReq2AnsMessage();
		  lpConnection->SendBizMsg(lpMsg,1);	
    }
    //收到发布过来的行情
    else if (iFunctionID == 620003 || iFunctionID == 620025)
    {  
		char * lpKeyRevData = NULL;
		int iKeyRevLen = 0;
		lpKeyRevData = (char *)lpMsg->GetKeyInfo(iKeyRevLen);
		IF2UnPacker* lpUnPack = NewUnPacker(lpKeyRevData,iKeyRevLen);
		if (lpUnPack)
		{
			
			lpUnPack->AddRef();
			PrintUnPack(lpUnPack);
			lpUnPack->Release();
		}
		  //这里如果发布时用的是PACK格式，请用PACK格式解包
		  int iRevLen = 0;
		 char* lpRevData = (char *)lpMsg->GetContent(iRevLen);
		  if(lpRevData)
			{
					printf("收到发布包TAG_MESSAGE_BODY:%s\n",lpRevData);
			}
		
    } 
    else if (iFunctionID == 620001)
    {
    	
		     printf("收到订阅应答\n");
    }
	  else if (iFunctionID == 620002)
    {
    	   printf("收到取消订阅应答\n");
    }
}
int main(int argc,char *argv[])
{

    CConfigInterface * lpConfig = NewConfig();
    if(!lpConfig)
    {
       	puts("lpConfig初始化失败！");	
       	return -1;
    }
     lpConfig->AddRef();
	   lpConfig->Load("subscribe.ini");

    //通过T2SDK的引出函数，来获取一个新的CConnection对象指针
    g_Connection = NewConnection(lpConfig);
    g_Connection->AddRef();

    //创建自定义类CCallback的对象（在创建连接时需传递此对象，请看下面代码）
    CCallback callback;

    int ret = 0;

    //初始化连接对象，返回0表示初始化成功，注意此时并没开始连接服务器
    if (0 == (ret = g_Connection->Create2BizMsg(&callback)))
    {
        //正式开始连接，参数1000为超时参数，单位是ms
        if (ret = g_Connection->Connect(5000))
        {
            puts(g_Connection->GetErrorMsg(ret));
        }
        else
        {
        	  //连接成功后，就进行订阅 
        	  
        	   //通过T2SDK的引出函数，来获取一个新的IBizMessage对象指针
            IBizMessage * lpMessage = NewBizMessage();
            lpMessage->AddRef();
            
            //初始化ESBMessage，REQUEST_PACKET为包类型（请求包）,620001:消息订阅
            int funid = atoi((char*)lpConfig->GetString("subcribe","function_id",""));
            lpMessage->SetPacketType(REQUEST_PACKET);
            lpMessage->SetFunction(funid);
            
            //类型 11:行情
            int issueid = atoi((char*)lpConfig->GetString("subcribe","issue_no",""));
            lpMessage->SetIssueType(issueid);
            
            //添加过滤字段
	          int nCount = lpConfig->GetInt("subcribe","filter_count",0);
	          //获取打包器并打入内容
            IF2Packer* lpPack = NewPacker(2);
            lpPack->AddRef(); 
            lpPack->BeginPack();
            int i =0;
	          for ( i=1;i<=nCount;i++)
						{
							char lName[128]={0};
							sprintf(lName,"filter_name%d",i);
							char* filterName = (char*)lpConfig->GetString("subcribe",lName,"");
							 lpPack->AddField(filterName, 'S');	
						}
	          for ( i=1;i<=nCount;i++)
						{
							char lValue[128]={0};
							sprintf(lValue,"filter_value%d",i);
							char* filterValue = (char*)lpConfig->GetString("subcribe",lValue,"");
							lpPack->AddStr(filterValue);
							
						}						
            lpPack->EndPack();
            
            //订阅主键
           lpMessage->SetKeyInfo(lpPack->GetPackBuf(),lpPack->GetPackLen());
            
            lpPack->FreeMem(lpPack->GetPackBuf());
            lpPack->Release();

            
            //发送包             

     		    if ((ret = g_Connection->SendBizMsg(lpMessage, 1)) < 0)
            {
               puts(g_Connection->GetErrorMsg(ret));
            }
            lpMessage->Release();

        }
    }
    else
    {
        puts(g_Connection->GetErrorMsg(ret));
    }

    //通过getchar阻塞线程，等待服务端应答包到达
    getchar();
    g_Connection->Close();
    g_Connection->Release();
    lpConfig->Release();
   
    return 0;
}
