// 源文件名：T2SDK.pas
// 软件版权：恒生电子股份有限公司
// 系统名称：HSESB
// 功能说明：T2SDK的Delphi版本接口定义
// 作    者：何仲君
// 备    注：Tab = 2
// 历    史：
// 20080717 初始版本

unit T2SDK;

interface

uses
  Classes;

const
  //ESB组名长度，名字为可见字符，不能包含实例分隔符、空格、分号;
  IDENTITY_NAME_LENGTH =	32;
  //实例编号最大占位长度
  ID_LENGTH     =          4;
  //节点名全长,定义时使用char sName[ID_STR_LEN+1]
  ID_STR_LEN	 =  (IDENTITY_NAME_LENGTH + ID_LENGTH + 1);
  //	插件接口名的最大长度,定义时使用char sName[PLUGINID_LENGTH+1]
  PLUGINID_LENGTH   =	256;
  //	插件实例名的最大长度,定义时使用char sName[PLUGIN_NAME_LENGTH+1]
  PLUGIN_NAME_LENGTH  =	(PLUGINID_LENGTH+ID_LENGTH+1);
  //	进程名最大长度.定义时使用char sName[SVR_NAME_LENGTH+1]
  SVR_NAME_LENGTH    =	256;
  //	进程实例名最大长度.定义时使用char sName[PLUGINID_NAME_LENGTH+1]
  SVRINSTANCE_NAME_LENGTH    =	(SVR_NAME_LENGTH+ID_LENGTH+1);

  INIT_RECVQ_LEN = 256;
  STEP_RECVQ_LEN = 512;



type
  // 配置接口
  IConfigInterface = interface(IInterface)
    function Load(const szFileName: PChar): Integer; stdcall;
    function Save(const szFileName: PChar): Integer; stdcall;
    function GetString(const szSection, szEntry, szDefault: PChar): PChar; stdcall;
    function GetInt(const szSection, szEntry: PChar; iDefault: Integer): Integer; stdcall;
    function SetString(const szSection, szEntry, szValue: PChar): Integer; stdcall;
    function SetInt(const szSection, szEntry: PChar; iValue: Integer): Integer; stdcall;
  end;

  IConnectionInterface = interface;
  //IESBMessage = interface;

  //路由信息的结构体定义
  pRoute_Info = ^Route_Info;
  Route_Info = record
    ospfName: array[0..ID_STR_LEN] of char;
    nbrName: array[0..ID_STR_LEN] of char;
    svrName: array[0..SVRINSTANCE_NAME_LENGTH] of char;
    pluginID: array[0..PLUGIN_NAME_LENGTH] of char;
    connectID: Integer;
    memberNO: Integer;
  end;


  //为了发送和返回订阅推送信息而增加的结构体的定义
  pREQ_DATA = ^TREQ_DATA;
  TREQ_DATA = record
    sequeceNo: Integer;
    issueType: Integer;
    lpKeyInfo: Pointer;
    keyInfoLen: Integer;
    lpFileHead: Pointer;
    fileHeadLen: Integer;
    packetType: Integer;//20100111 xuxp 新加的包类型
    routeInfo: Route_Info;//20110302 xuxp 请求里面增加路由信息
  end;

  LPRET_DATA = ^RET_DATA;
  RET_DATA = record
    functionID: Integer;
    returnCode: Integer;
    errorNo: Integer;
    errorInfo: PChar;
    issueType: Integer;
     lpKeyInfo: Pointer;
    keyInfoLen: Integer;
    sendInfo: Route_Info ;//20110302 xuxp 应答里面增加发送者信息
end;

  //为了发送和返回订阅推送信息而增加的结构体的定义
  LPREQ_DATA = ^REQ_DATA;
  REQ_DATA = record
    sequeceNo: Integer;
    issueType: Integer;
    lpKeyInfo: Pointer;
    keyInfoLen: Integer;
    lpFileHead: Pointer;
    fileHeadLen: Integer;
    packetType: Integer;//20100111 xuxp 新加的包类型
    routeInfo: Route_Info ;//20110302 xuxp 请求里面增加路由信息
  end;



  // 回调接口
  ICallbackInterface = interface(IInterface)
    procedure OnConnect(Connection: IConnectionInterface); stdcall;
    procedure OnSafeConnect(Connection: IConnectionInterface); stdcall;
    procedure OnRegister(Connection: IConnectionInterface); stdcall;
    procedure OnClose(Connection: IConnectionInterface); stdcall;
    procedure OnSent(Connection: IConnectionInterface; hSend: Integer; const lpData: Pointer; nLength: Integer; nQueuingData: Integer); stdcall;
    procedure OnReceiveNotify(Connection: IConnectionInterface; hSend: Integer; const lpData: Pointer; nLength: Integer); stdcall;
    procedure OnReceived(Connection: IConnectionInterface; hSend: Integer; const lpData: Pointer; nLength: Integer); stdcall;
    function OnBulkReceiveStart(Connection: IConnectionInterface; uiLength: Longword; const szComment: PChar; szFileName: PChar; var uiPieceSize: Longword; var iBulkReceiveID: Integer): Integer; stdcall;
    procedure OnBulkReceivePiece(Connection: IConnectionInterface; iBulkReceiveID: Integer; uiLength, uiReceivedLength: Longword); stdcall;
    procedure OnBulkReceiveEnd(Connection: IConnectionInterface; iBulkReceiveID: Integer; const lpData: Pointer; uiLength: Longword); stdcall;
    procedure OnBulkSendPiece(Connection: IConnectionInterface; iBulkSendID: Integer; uiLength, uiSentLength: Longword); stdcall;
    procedure OnBulkSendEnd(Connection: IConnectionInterface; iBulkSendID: Integer; iError: Integer); stdcall;
    procedure OnReceivedBiz(Connection: IConnectionInterface; hSend: Integer; const lpUnPackerOrStr: Pointer; iResult: Integer); stdcall;
    procedure OnReceivedBizEx(Connection: IConnectionInterface; hSend: Integer; lpRetData: LPRET_DATA; const lpUnPackerOrStr: Pointer; iResult: Integer); stdcall;
    procedure OnReceivedBizMsg(Connection: IConnectionInterface; hSend: Integer; lpMsg:Pointer); stdcall;
  end;

  // 连接状态
  TConnectionStatus = (
		Disconnected    = $0000,  // 未连接
		Connecting      = $0001,  // socket正在连接
		Connected       = $0002,  // socket已连接
		SafeConnecting  = $0004,  // 正在建立安全连接
		SafeConnected   = $0008,  // 已建立安全连接
		Registering     = $0010,  // 已注册
		Registered      = $0020,  // 已注册
		Rejected        = $0040   // 被拒绝，将被关闭
  );

  // 发送标志
  TSendFlag = (
    ReplyCallback = $0001,  // 通过回调取应答
    IsESBMessage  = $0100,  // 发送一个ESBMessage，此时Send方法的第一个参数为ESBMessage的地址，第二个参数忽略
    ThroughLine   = $0200,  // 不另行添加PacketID，以buffer自带的PacketID为此次发送的PacketID
    ThirdParty    = $0400   // 通过回调取应答（第三方专用）
  );
  
  // 接收选项（可组合，0表示接收超时时，不删除包ID，仍可再次调用Receive方法来尝试接收）
  TRecvFlags = (
    JustRemoveHandle = $0001  // 当接收超时时，把packet_id删除（以后再收到，则会以异步的方式收到），注意，此参数是在Receive方法中传递（如果需要的话）
  );

  
  IBizMessage = interface(IInterface)
	//设置功能号
	procedure SetFunction(const nFUnctionNo : Integer ); stdcall;
	//获取功能号
	function GetFunction():Integer; stdcall;

	//设置包类型
	procedure SetPacketType(const nPacketType : Integer); stdcall;
	//获取包类型
	function GetPacketType():Integer; stdcall;

	//设置营业部号
	procedure SetBranchNo(const nBranchNo : Integer); stdcall;
	//获取营业部号
	function GetBranchNo():Integer; stdcall;

	//设置系统号
	procedure SetSystemNo(const nSystemNo : Integer); stdcall;
	//获取系统号
	function GetSystemNo():Integer; stdcall;

	//设置子系统号
	procedure SetSubSystemNo(const nSubSystemNo : Integer); stdcall;
	//获取子系统号
	function GetSubSystemNo():Integer; stdcall;

	//设置发送者编号
	procedure SetSenderId(const nSenderId : Integer); stdcall;
	//获取发送者编号
	function GetSenderId() :Integer; stdcall;

	//设置包序号
	procedure SetPacketId(const nPacketId : Integer); stdcall;
	//获取包序号
	function GetPacketId():Integer; stdcall;

	//设置目的地路由
	procedure SetTargetInfo(const targetInfo : Route_Info); stdcall;
	//获取目的地路由
	procedure GetTargetInfo(var targetInfo : Route_Info) ; stdcall;
	
	//设置发送者路由
	procedure SetSendInfo(const sendInfo : Route_Info); stdcall;
	//获取发送者路由
	procedure GetSendInfo(var sendInfo : Route_Info); stdcall;

	//设置错误号
	procedure SetErrorNo(const nErrorNo : Integer); stdcall;
	//获取错误号
	function GetErrorNo():Integer; stdcall;
	
	//设置错误信息
	procedure SetErrorInfo(const strErrorInfo : Pchar) ; stdcall;
	//获取错误信息
	function GetErrorInfo(): Pchar; stdcall;
	
	//设置返回码
	procedure SetReturnCode(const nReturnCode : Integer); stdcall;
	//获取返回码
	function GetReturnCode():Integer; stdcall;

	//设置业务内容
	procedure SetContent(lpContent : pointer; iLen : Integer); stdcall;
	//获取业务内容
	function GetContent(var iLen : Integer): Pchar; stdcall;

	//以下接口用于消息中心1.0的订阅
	//设置订阅类型
	procedure SetIssueType(const nIssueType : Integer); stdcall;
	//获取订阅类型
	function GetIssueType():Integer; stdcall;

	//设置序号
	procedure SetSequeceNo(const nSequeceNo : Integer); stdcall;
	//获取序号
	function GetSequeceNo():Integer; stdcall;

	//设置关键字段信息
	procedure SetKeyInfo(lpKeyData: pointer; iLen : Integer); stdcall;
	//获取关键字段信息
	function GetKeyInfo(var iLen : Integer): pointer; stdcall;

	//设置附加数据，订阅推送时原样返回
	procedure SetAppData(const lpAppdata: pointer; nAppLen : Integer); stdcall;
	//获取附加数据，订阅推送时原样返回
	function GetAppData(var nAppLen : Integer): pointer; stdcall;

	//请求转应答
	function ChangeReq2AnsMessage():Integer; stdcall;

	//获取二进制
	function GetBuff(var nBuffLen : Integer): pointer; stdcall;
	//解析二进制
	function SetBuff(const lpBuff : pointer; nBuffLen : Integer):Integer; stdcall;

	//清除消息内的字段，可以下次复用。
	procedure ReSet(); stdcall;
	
  end;
  
  //主题级别
  ReliableLevel = (
    LEVEL_DOBEST = 0, //尽力而为
    LEVEL_DOBEST_BYSEQ = 1, //尽力有序
    LEVEL_MEM = 2, //内存
    LEVEL_FILE = 3, //文件
    LEVEL_SYSTEM = 4 //系统
    );

 // 过滤条件接口
  CFilterInterface = interface(IInterface)
  {根据下标获取过滤条件的名字
  /**
  * @param index 对应的过滤条件下标
  * @return 返回对应的下标过滤条件的名字，否则返回NULL。
  **/}
    function GetFilterNameByIndex(index: Integer): PChar; stdcall;

  {根据下标获取过滤条件的值
  /**
  * @param index 对应的过滤条件下标
  * @return 返回对应的下标过滤条件的值，否则返回NULL。
  **/}
    function GetFilterValueByIndex(index: Integer): PChar; stdcall;

  {根据过滤条件的名字获取过滤条件的值
  /**
  * @param fileName 对应的过滤条件名字
  * @return 返回对应的过滤条件名字的条件值，否则返回NULL。
  **/}
    function GetFilterValue(fileName: PChar): PChar; stdcall;

  {获取过滤条件的个数
  /**
  * @return 返回对应过滤条件的个数，没有返回0
  **/}
    function GetCount(): Integer; stdcall;

  {设置过滤条件，根据过滤条件名字和值
  /**
  * @param filterName 对应的过滤条件名字
  * @param filterValue 对应的过滤条件名字的值
  **/}
    procedure SetFilter(filterName: PChar; filterValue: PChar); stdcall;
  end;

 //订阅主题属性
  CSubscribeParamInterface = interface(IInterface)
  {设置主题名字
  /**
  * @param szName 对应的主题名字
  **/}
    procedure SetTopicName(szName: PChar); stdcall;

  {设置附加数据
  /**
  * @param lpData 附加数据的首地址
  * @param iLen 附加数据的长度
  **/}
    procedure SetAppData(lpData: pointer; iLen: integer); stdcall;

  {添加过滤条件
  /**
  * @param filterName 过滤条件的名字
  * @param filterValue 过滤条件的值
  **/}
    procedure SetFilter(filterName: pchar; filterValue: pchar); stdcall;

  {添加返回字段
  /**
  * @param filedName 需要添加的返回字段
  **/}
    procedure SetReturnFiled(filedName: pchar); stdcall;

  {设置是否补缺标志
  /**
  * @param bFromNow true表示需要之前的数据，也就是补缺，false表示不需要补缺
  **/}
    procedure SetFromNow(bFromNow: LongBool); stdcall;

  {设置覆盖订阅标志
  /**
  * @param bReplace true表示覆盖订阅，取消之前的所有订阅，只保留当前的订阅，false表示追加订阅
  **/}
    procedure SetReplace(bReplace: LongBool); stdcall;

  {设置发送间隔
  /**
  * @param nSendInterval 单位是秒
  **/}
    procedure SetSendInterval(nSendInterval: integer); stdcall;

  {获取主题名字
  /**
  * @return 返回主题名字信息
  **/}
    function GetTopicName(): pchar; stdcall;

  {获取附加数据
  /**
  * @param iLen 出参，表示附加数据的长度
  * @return 返回附加数据首地址，没有返回NULL
  **/}
    function GetAppData(iLen: PInteger): pointer; stdcall;

  {获取对应的过滤字段的名字
  /**
  * @param index 对应的过滤条件下标
  * @return 返回对应的下标过滤条件的名字，否则返回NULL。
  **/}
    function GetFilterNameByIndex(index: integer): pchar; stdcall;

  {根据下标获取过滤条件的值
  /**
  * @param index 对应的过滤条件下标
  * @return 返回对应的下标过滤条件的值，否则返回NULL。
  **/}
    function GetFilterValueByIndex(index: integer): pchar; stdcall;

  {根据过滤条件的名字获取过滤条件的值
  /**
  * @param fileName 对应的过滤条件名字
  * @return 返回对应的过滤条件名字的条件值，否则返回NULL。
  **/}
    function GetFilterValue(fileName: pchar): pchar; stdcall;

  {获取过滤条件的个数
  /**
  * @return 返回对应过滤条件的个数，没有返回0
  **/}
    function GetFilterCount(): integer; stdcall;

  {获取返回字段
  /**
  * @return 返回对应的返回字段信息
  **/}
    function GetReturnFiled(): pchar; stdcall;

  {获取是否补缺的标志
  /**
  * @return 返回对应的补缺标志
  **/}
    function GetFromNow(): LongBool; stdcall;

  {获取是否覆盖订阅的标志
  /**
  * @return 返回对应的覆盖订阅标志
  **/}
    function GetReplace(): LongBool; stdcall;

  {获取对应的发送频率
  /**
  * @return 返回对应的发送间隔
  **/}
    function GetSendInterval(): integer; stdcall;
  end;

 //订阅接口的定义
  CSubscribeInterface = interface(IInterface)
  {/**订阅主题
	* @param lpSubscribeParam 上面定义的订阅参数结构
	* @param uiTimeout 超时时间
	* @param lppBizUnPack 业务校验时，失败返回的业务错误信息，如果订阅成功没有返回，输出参数，需要外面调用Release释放
						  如果接受业务校验的错误信息，写法如下：
						  IF2UnPacker* lpBizUnPack =NULL;
						  SubscribeTopic(...,&lpBizUnPack);
						  最后根据返回值，如果是失败的就判断 lpBizUnPack 是不是NULL。
						  最后错误信息获取完之后,释放
						  lpBizUnPack->Release();
	* @param lpBizPack 业务校验需要增加的业务字段以及值，没有就根据过滤属性作为业务校验字段
	@return 返回值大于0，表示当前订阅成功的订阅标识，外面要记住这个标识和订阅项之间的映射关系，这个标识需要用于取消订阅和接收消息的回调里面.
	*		  返回其他值，根据错误号获取错误信息.
	**/}
    function SubscribeTopic(lpSubscribeParamInter: CSubscribeParamInterface; uiTimeout: Longword; lppBizUnPack : pointer = nil; lpBizPack: pointer = nil): integer; stdcall;

	{/**
    * 取消订阅主题
    * @param subscribeIndex 消息对应的订阅标识，这个标识来自于SubscribeTopic函数的返回
    * @return 返回0表示取消订阅成功，返回其他值，根据错误号获取错误信息.
    */}
    function CancelSubscribeTopic(subscribeIndex : integer): integer; stdcall;
	
  {/**取消订阅主题
  * @param topicName 主题名字
  * @param lpFilter 对应的过滤条件
  * @return 返回0表示取消订阅成功，返回其他值，根据错误号获取错误信息。
  **/}
    function CancelSubscribeTopicEx(topicName: pchar; lpFilterInterface: CFilterInterface): integer; stdcall;

  {/**获取当前订阅接口已经订阅的所有主题以及过滤条件信息
  * @param lpPack 外面传入的打包器
  *报文字段“SubcribeIndex IsBornTopic TopicName TopicNo FilterRaw Appdata SendInterval ReturnFileds isReplace isFromNow Stutas QueueCount
  **/}
    procedure GetSubcribeTopic(lpPack: pointer); stdcall;

  {/**
  * 取服务器地址
  * @param lpPort 输出的服务器端口，可以为NULL
  * @return 返回服务器地址
  */}
    function GetServerAddress(lpPort: pinteger): pchar; stdcall;
  end;

 //发布接口的定义
  CPublishInterface = interface(IInterface)
  {/**业务打包格式的内容发布接口
	* @param topicName 主题名字，不知道名字就传NULL
	* @param lpPacker 具体的内容
	* @param iTimeOut 超时时间
	* @param lppBizUnPack 业务校验时，失败返回的业务错误信息，如果发布成功没有返回，输出参数，需要外面调用Release释放
							如果接受业务校验的错误信息，写法如下：
							IF2UnPacker* lpBizUnPack =NULL;
							PubMsgByPacker(...,&lpBizUnPack);
							最后根据返回值，如果是失败的就判断 lpBizUnPack 是不是NULL。
							最后错误信息获取完之后,释放
							lpBizUnPack->Release();
	* @param bAddTimeStamp 是否添加时间戳，配合单笔性能查找
	* @return 返回0表示成功，返回其他值，根据错误号获取错误信息。
	**/}
    function PubMsgByPacker(topicName: pchar; lpUnPacker: pointer; iTimeOut: integer = -1; lppBizUnPack : pointer = nil; bAddTimeStamp: LongBool = false): integer; stdcall;

  {/**非业务打包格式的内容发布接口，一般二进制格式报文发布
  * @param topicName 主题名字，不知道名字就传NULL
  * @param lpFilterInterface 过滤条件，需要上层自己指定，否则默认没有过滤条件
  * @param lpData 具体的内容
  * @param nLength 内容长度
  * @param iTimeOut 超时时间
  * @return 返回0表示成功，返回其他值，根据错误号获取错误信息。
  **/}
    function PubMsg(topicName: pchar; lpFilterInterface: CFilterInterface; const lpData: pointer; nLength: integer; iTimeOut: integer = -1; lppBizUnPack : pointer = nil; bAddTimeStamp: LongBool = false): integer; stdcall;


  {/**返回当前主题的发布序号
  * @param topicName 主题名字
  * @return 返回0表示没有对应的主题，返回其他值表示成功
  **/}
    function GetMsgNoByTopicName(topicName: pchar): Longword; stdcall;

  {/**
  * 取服务器地址
  * @param lpPort 输出的服务器端口，可以为NULL
  * @return 返回服务器地址
  */}
    function GetServerAddress(lpPort: pinteger): pchar; stdcall;
  end;
  
  //订阅回调接口返回的数据定义，除了订阅需要的业务体之外，还需要返回的数据
  tagSubscribeRecvData = record
    lpFilterData : pchar;//过滤字段的数据头指针，用解包器解包
    iFilterDataLen : integer;//过滤字段的数据长度
    lpAppData : pchar;//附加数据的数据头指针
    iAppDataLen : integer;//附加数据的长度
    szTopicName: Array[ 0..259 ] Of char;//主题名字
  end;
  
  CSubCallbackInterface = interface(IInterface)
  {收到发布消息的回调
  /**
  * @param lpSub 回调的订阅指针
  * @param subscribeIndex 消息对应的订阅标识，这个标识来自于SubscribeTopic函数的返回
  * @param lpData 返回消息的二进制指针，一般是消息的业务打包内容
  * @param nLength 二进制数据的长度
  * @param lpRecvData 主推消息的其他字段返回，主要包含了附加数据，过滤信息，主题名字，详细参看tagSubscribeRecvData结构体定义
  * @return
  **/}
    procedure OnReceived(lpSub: CSubscribeInterface; subscribeIndex: integer; const lpData: pointer; nLength: integer; lpRecvData: pointer); stdcall;


  {收到剔除订阅项的消息回调，一般在拥有踢人策略的主题下会回调这个接口
  /**
  * @param lpSub 回调的订阅指针
  * @param subscribeIndex 消息对应的订阅标识，这个标识来自于SubscribeTopic函数的返回
  * @param TickMsgInfo 踢人的错误信息，主要是包含具体重复的订阅项位置信息
  * @return
  **/}
    procedure OnRecvTickMsg(lpSub: CSubscribeInterface; subscribeIndex: integer; const TickMsgInfo: pchar); stdcall;
  end;

  // 连接接口
  IConnectionInterface = interface(IInterface)
    function Reserved0(): Integer; stdcall;
    function Connect(uiTimeout: Longword): Integer; stdcall;
    function Close: Integer; stdcall;
    function Reserved1(): Integer; stdcall;
    function Reserved2(): Integer; stdcall;
    function Reserved3(): Integer; stdcall;
    function Reserved4(): Integer; stdcall;
    function Reserved5(): Integer; stdcall;
    function Reserved6(): Integer; stdcall;
    function GetServerAddress(lpPort: PInteger): PChar; stdcall;
    function GetStatus: Integer; stdcall;
    function GetServerLoad: Integer; stdcall;
    function GetErrorMsg(iErrorCode: Integer): PChar; stdcall;
    function GetConnectError: Integer; stdcall;
    function SendBiz(iFunID: Integer; lpPacker: Pointer; nAsy: Integer = 0; iSystemNo: Integer = 0; nCompressID: Integer = 1): Integer; stdcall;
    function RecvBiz(hSend: Integer; lppUnPackerOrStr: PPointer; uiTimeout: Longword = 1000; uiFlag: Longword = 0): Integer; stdcall;
    function SendBizEx(iFunID: Integer; lpPacker: Pointer; svrName : PChar; nAsy: Integer = 0; iSystemNo: Integer = 0; nCompressID: Integer = 1; branchNo: Integer = 0; lpRequest: pREQ_DATA = nil): Integer; stdcall;
    function RecvBizEx(hSend: Integer; lppUnPackerOrStr: PPointer; lpRetData: LPRET_DATA; uiTimeout: Longword = 1000; uiFlag: Longword = 0): Integer; stdcall;
    function CreateEx(Callback: ICallbackInterface): Integer; stdcall;
    function GetRealAddress(): PChar; stdcall;
    function CreateByMemCert(lpCallback: ICallbackInterface; lpCertData:pointer; nLength : Integer; const szPWD : PChar): Integer; stdcall;
    function Reserved7(): Integer; stdcall;
    function GetSelfAddress(): PChar; stdcall;
    function GetSelfMac(): PChar; stdcall;
    function NewSubscriber(lpCallback: CSubCallbackInterface; SubScribeName: PChar; iTimeOut: Integer; iInitRecvQLen : Integer=INIT_RECVQ_LEN; iStepRecvQLen : Integer=STEP_RECVQ_LEN) :pointer; stdcall;
    function NewPublisher(PublishName : pchar; msgCount:Integer; iTimeOut:Integer; bResetNo: LongBool= false): pointer; stdcall;
    function GetTopic(byForce : LongBool; iTimeOut : Integer): pointer; stdcall;	
    function GetMCLastError(): PChar; stdcall;
    function Create2BizMsg(Callback: ICallbackInterface): Integer; stdcall;
    function SendBizMsg(lpMsg :Pointer;nAsy : Integer = 0) : Integer; stdcall;
    function RecvBizMsg(hSend: Integer ;lpMsg:Pointer;uiTimeout: Integer = 1000;uiFlag : Integer = 0) : Integer; stdcall;
   end;



  IF2ResultSet = interface(IInterface)
    ///取字段数
    function GetColCount : integer; stdcall;
	
    ///取字段名
    function GetColName(column : integer) : PChar; stdcall;
	
    //取字段数据类型
    function GetColType(column : integer) : Char; stdcall;
	
    ///取数字型字段小数位数
    function GetColScale(column : integer) : integer; stdcall;
	
    //取字段允许存放数据的最大宽度.
    function GetColWidth(column : integer) : integer; stdcall;
	
    ///取字段名对应的字段序号
    function FindColIndex(const columnName : PChar) : integer; stdcall;

    //按字段序号(以0为基数)，取字段值(字符串)
    function GetStrByIndex(column : integer) : PChar; stdcall;
	
    //按字段名，取字段值(字符串)
    function GetStr(const columnName : PChar) : PChar; stdcall;
	
    //按字段序号(以0为基数)，取字段值
    function GetCharByIndex(column : integer) : Char; stdcall;
	
    //按字段名，取字段值
    function GetChar(const columnName : PChar) : Char; stdcall;
	
    //按字段序号，取字段值
    function GetDoubleByIndex(column : integer) : Double; stdcall;
	
    ///按字段名，取字段值
    function GetDouble(const columnName : PChar) : Double; stdcall;
	
    ///按字段序号，取字段值
    function GetIntByIndex(column : integer) : integer; stdcall;
	
    ///按字段名，取字段值
    function GetInt(const columnName : PChar) : integer; stdcall;
	
    ///按字段序号获得字段值,二进制数据
    function GetRawByIndex(column : integer; lpRawLen : Pinteger) : Pointer; stdcall;
	
    ///按字段名，取字段值
    function GetRaw(const columnName : PChar ; lpRawLen : Pinteger) : Pointer; stdcall;
	
    ///最后一次取的字段值是否为NULL
    function WasNull : integer; stdcall;

    ///取下一条记录
    procedure Next ; stdcall;
	
    ///判断是否为结尾
    function IsEOF : integer; stdcall;
	
    ///判断是否为空
    function IsEmpty : integer; stdcall;
	
    function Destroy : Pointer; stdcall;
  end;
  
  IF2UnPacker = interface(IF2ResultSet)
    //取打包格式版本
    function GetVersion : integer; stdcall;
	
    //取解包数据长度
    function Open(lpBuffer : Pointer; iLen : Longword) : integer; stdcall;
	
    ///取结果集个数(0x20以上版本支持)
    function GetDatasetCount : integer; stdcall;
	
    ///设置当前结果集(0x20以上版本支持)
    function SetCurrentDatasetByIndex(nIndex : integer) : integer; stdcall;

    ///设置当前结果集 (0x20以上版本支持)
    function SetCurrentDataset(const szDatasetName : PChar) : integer; stdcall;

    //取解包数据区指针
    function GetPackBuf : Pointer; stdcall;

    //取解包数据长度
    function GetPackLen : Longword; stdcall;

    //取解包数据记录条数,20051207以后版本支持
    function GetRowCount : Longword; stdcall;
	
	///结果集行记录游标接口：取结果集的首条记录
    procedure First(); stdcall;

    ///结果集行记录游标接口：取结果集的最后一条记录
    procedure Last(); stdcall;

    ///结果集行记录游标接口：取结果集的第n条记录，取值范围[1, GetRowCount()]
    procedure Go(nRow : integer) ; stdcall;

	///增加获取当前结果集名字的接口,没有名字返回""
	function GetDatasetName(): PChar; stdcall;
	
	function OpenAndCopy(lpBuffer:Pointer;iLen : integer): integer; stdcall;
   end;

  IF2Packer = interface(IInterface)

    ///打包器初始化(使用调用者的缓存区
    procedure SetBuffer(pBuf : Pointer; iBufSize : integer; iDataLen : integer = 0 ) ; stdcall;

    ///复位，重新开始打另一个包(字段数与记录数置为0行0例)
    procedure BeginPack; stdcall;

    ///开始打一个结果集
    function  NewDataset(const szDatasetName : PChar; iReturnCode : integer = 0) : integer; stdcall;

     /// 功能：向包添加字段
    function AddField(const szFieldName : PChar ; cFieldType : Char = 'S'; iFieldWidth : integer =255; iFieldScale : integer =4 ) : integer; stdcall;

     //功能：向包添加字符串数据
    function AddStr( const szValue : PChar) : integer; stdcall;

     //功能：向包添加整数数据
    function AddInt(iValue : integer) : integer; stdcall;

     //功能：向包添加浮点数据
    function AddDouble(fValue : Double) : integer; stdcall;

    //功能：向包添加一个字符
    function AddChar(cValue : Char) : integer; stdcall;

     //功能：向包添加一个大对象
    function AddRaw(lpBuff : Pointer; iLen : integer) : integer; stdcall;
	
    ///结束打包
    procedure EndPack; stdcall;

    //功能：取打包结果指针
    function GetPackBuf : Pointer; stdcall;

    //功能：取打包结果长度
    function GetPackLen : integer; stdcall;

    //功能：取打包结果缓冲区大小
    function GetPackBufSize : integer; stdcall;
        
    //功能：取打包格式版本
    function GetVersion : integer; stdcall;
	
    ///设置结果集的返回码(0x20版以上要求)，错误结果集需要设置
    procedure SetReturnCode(dwRetCode : Longword); stdcall;
	
    //直接返回当前打包结果的解包接口,必须在EndPack()之后才能调用,在打包器释放时相应的解包器实例也释放
    function UnPack : pointer; stdcall;
	
    procedure FreeMem(lpBuf : Pointer); stdcall;

    procedure ClearValue; stdcall;

    ///复位，重新开始打另一个包(字段数与记录数置为0行0例)
    procedure BeginPackEx(szName : pchar = nil); stdcall;

    ///复位当前结果集(字段数与记录数置为0行0例)，不影响其他结果集
    procedure ClearDataSet(); stdcall;
  end;
  
  function GetVersionInfo: Integer; stdcall; external 'T2SDK.dll'; // 0x01000002 = 1.0.0.2
  function NewConfig: Pointer; stdcall; external 'T2SDK.dll';
  function NewConnection(const Config: IConfigInterface): Pointer; stdcall; external 'T2SDK.dll';
  function NewPacker(iVersion: Integer): Pointer; stdcall; external 'T2SDK.dll';
  function NewUnPacker(lpBuffer: Pointer; iLen: Longword): Pointer; stdcall; external 'T2SDK.dll';
  function NewUnPackerV1(lpBuffer: Pointer; iLen: Longword): Pointer; stdcall; external 'T2SDK.dll';
  // EncodePass为char[16]，函数返回时里面为散列值（可读的字符串） Password为需要散列的密码串，Key建议填8
  function Encode(EncodePass: PChar; const Password: PChar; Key: Integer): PChar; stdcall; external 'T2SDK.dll';   
  function GetPackVersion(const lpBuffer: Pointer): Integer; stdcall; external 'T2SDK.dll';  
  function NewFilter(): Pointer; stdcall; external 'T2SDK.dll';
  function NewSubscribeParam(): Pointer; stdcall; external 'T2SDK.dll';
  function NewBizMessage(): Pointer; stdcall; external 'T2SDK.dll';
implementation
  
end.

