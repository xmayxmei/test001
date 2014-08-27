unit Unit1;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, StdCtrls, SyncObjs, T2SDK;

type
  TForm1 = class(TForm)
    Button1: TButton;
    Button2: TButton;
    ListBox1: TListBox;
    Button3: TButton;
    Button4: TButton;
    SubscriberBtn: TButton;
    PublishBtn: TButton;
    ConnectBtn: TButton;
    CancelSubBtn: TButton;
    ClearBtn: TButton;
    btn8: TButton;
    btn9: TButton;
    btn10: TButton;
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure FormDestroy(Sender: TObject);
    procedure Button3Click(Sender: TObject);
    procedure Button4Click(Sender: TObject);
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
    procedure SubscriberBtnClick(Sender: TObject);
    procedure ConnectBtnClick(Sender: TObject);
    procedure PublishBtnClick(Sender: TObject);
    procedure CancelSubBtnClick(Sender: TObject);
    procedure ClearBtnClick(Sender: TObject);
    procedure btn8Click(Sender: TObject);
    procedure btn9Click(Sender: TObject);
    procedure btn10Click(Sender: TObject);
  private
    FLock: TCriticalSection;
    { Private declarations }
  public
    { Public declarations }
    procedure Log(const Str: string);
  end;

  // 回调接口
  TCallback = class(TInterfacedObject, ICallbackInterface)
  public
    constructor Create;
    destructor Destroy; override;
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
  private
    //FLock: TCriticalSection;
  end;

  // 订阅回调接口
  TSubCallback = class(TInterfacedObject, CSubCallbackInterface)
  public
     constructor Create;
     destructor Destroy; override;
     procedure OnReceived(lpSub: CSubscribeInterface; subscribeIndex: integer; const lpData: pointer; nLength: integer; lpRecvData: pointer); stdcall;
     procedure OnRecvTickMsg(lpSub: CSubscribeInterface; subscribeIndex: integer; const TickMsgInfo: pchar); stdcall;
  end;

  ptagSubscribeRecvData = ^tagSubscribeRecvData;
var
  Form1: TForm1;


implementation

{$R *.dfm}

uses
  tag_def;

var
  cfg: IConfigInterface;
  Connection: IConnectionInterface;
  Callback: TCallback;
  SubCallBack: TSubCallback;
  BizMessage: IBizMessage;
  SendCount, SentCount: Integer;
  FSend : integer;
  subscriber : CSubscribeInterface;
  sunIndexArray : array[0..99] of integer;
  arrayIndex:  integer;
  publisher : CPublishInterface;
constructor TCallback.Create;
begin
  inherited;
  //FLock := TCriticalSection.Create;
end;

destructor TCallback.Destroy;
begin
  //FLock.Free;
  inherited;
end;

procedure TCallback.OnConnect(Connection: IConnectionInterface);
begin
end;

procedure TCallback.OnSafeConnect(Connection: IConnectionInterface);
begin
end;

procedure TCallback.OnRegister(Connection: IConnectionInterface);
begin
end;

procedure TCallback.OnClose(Connection: IConnectionInterface);
begin
end;

procedure TCallback.OnSent(Connection: IConnectionInterface; hSend: Integer; const lpData: Pointer; nLength: Integer; nQueuingData: Integer);
begin
end;

procedure TCallback.OnReceiveNotify(Connection: IConnectionInterface; hSend: Integer; const lpData: Pointer; nLength: Integer);
begin
end;

procedure TCallback.OnReceived(Connection: IConnectionInterface; hSend: Integer; const lpData: Pointer; nLength: Integer);
begin

end;

function TCallback.OnBulkReceiveStart(Connection: IConnectionInterface; uiLength: Longword; const szComment: PChar; szFileName: PChar; var uiPieceSize: Longword; var iBulkReceiveID: Integer): Integer;
begin
  //Form1.Log('OnBulkReceiveStart');
  Result := 0;
end;

procedure TCallback.OnBulkReceivePiece(Connection: IConnectionInterface; iBulkReceiveID: Integer; uiLength, uiReceivedLength: Longword);
begin
  //Form1.Log('OnBulkReceivePiece');
end;

procedure TCallback.OnBulkReceiveEnd(Connection: IConnectionInterface; iBulkReceiveID: Integer; const lpData: Pointer; uiLength: Longword);
begin
  //Form1.Log('OnBulkReceiveEnd');
end;

procedure TCallback.OnBulkSendPiece(Connection: IConnectionInterface; iBulkSendID: Integer; uiLength, uiSentLength: Longword);
begin
  //Form1.Log('OnBulkSendPiece');
end;

procedure TCallback.OnBulkSendEnd(Connection: IConnectionInterface; iBulkSendID: Integer; iError: Integer);
begin
  //Form1.Log('OnBulkSendEnd');
end;

procedure TCallback.OnReceivedBiz(Connection: IConnectionInterface; hSend: Integer; const lpUnPackerOrStr: Pointer; iResult: Integer);
begin
  //Form1.Log('OnReceivedBiz');
end;

procedure TCallback.OnReceivedBizEx(Connection: IConnectionInterface; hSend: Integer; lpRetData: LPRET_DATA; const lpUnPackerOrStr: Pointer; iResult: Integer);
begin
  //Form1.Log('OnReceivedBizEx');
end;

procedure TCallback.OnReceivedBizMsg(Connection: IConnectionInterface; hSend: Integer; lpMsg:Pointer);
var
  bizmsg:IBizMessage;
  iRetCode,len,iFunction : Integer;
  lpUnPacker: IF2UnPacker;
  Ptr,stemp:Pchar;
begin
  Form1.Log('OnReceivedBizMsg');

  bizmsg := IBizMessage(lpMsg);
  iFunction := bizmsg.GetFunction;
   if iFunction = 620000 then
  begin
      Form1.Log('收到心跳');
      bizmsg.ChangeReq2AnsMessage();
      Connection.SendBizMsg(Pointer(bizmsg),1);
      exit;
  end
  else if (iFunction = 620003) or (iFunction = 620025) then
  begin
         Form1.Log('收到主推消息');
  end
  else if iFunction = 620001 then
  begin
      Form1.Log('收到订阅应答!');
       exit;
  end
  else if iFunction = 620002 then
  begin
      Form1.Log('收到取消订阅应答!');
       exit;
  end;

    iRetCode:=bizmsg.GetReturnCode();
    if iRetCode <> 0 then  //服务器端返回异常信息
    begin
      stemp:=bizmsg.GetErrorInfo();
      Form1.Log('错误信息：'+ stemp);
    end
    else
    begin
      Ptr:=bizmsg.GetContent(len);


      //版本1的解包器
      if GetPackVersion(Ptr) = 1 then
      begin
       lpUnPacker := IF2UnPacker(NewUnPackerV1(Ptr, len));
      end
      else
      begin
       //版本2的解包器
       lpUnPacker := IF2UnPacker(NewUnPacker(Ptr, len));
      end;

      if lpUnPacker = nil then
      begin
        Form1.Log('数据接解包失败！可能是服务器端有异常！');
      end
      else
      begin
        Form1.Log(lpUnPacker.GetStr('Title'));  //注意后台报文，大小写敏感的
      end;
      lpUnPacker := nil;
    end;
end;

constructor TSubCallback.Create;
begin
  inherited;
  //FLock := TCriticalSection.Create;
end;

destructor TSubCallback.Destroy;
begin
  //FLock.Free;
  inherited;
end;

procedure TSubCallback.OnReceived(lpSub: CSubscribeInterface; subscribeIndex: integer; const lpData: pointer; nLength: integer; lpRecvData: pointer);
var
  sTemp :string;
  lpUnPacker,lpFilterUnPacker: IF2UnPacker;
  recvData:ptagSubscribeRecvData;
begin

  //解析主题名字和过滤字段
  recvData := ptagSubscribeRecvData(lpRecvData);
  Form1.Log('主题名字：'+recvData.szTopicName);
  //解析过滤条件
  if GetPackVersion(recvData.lpFilterData) = 1 then
  begin
        lpFilterUnPacker := IF2UnPacker(NewUnPackerV1(recvData.lpFilterData, recvData.iFilterDataLen));
  end
  else
  begin
       //版本2的解包器
       lpFilterUnPacker := IF2UnPacker(NewUnPacker(recvData.lpFilterData, recvData.iFilterDataLen));
  end;

  if lpFilterUnPacker = nil then
  begin
        Form1.Log('数据接解包失败！可能是服务器端有异常！');
  end
  else
  begin
        Form1.Log('过滤条件：'+lpFilterUnPacker.GetStr('branchno'));
        lpFilterUnPacker := nil;
  end;

  //解析具体的发布内容
  if GetPackVersion(lpData) = 1 then
  begin
        lpUnPacker := IF2UnPacker(NewUnPackerV1(lpData, nLength));
  end
  else
  begin
       //版本2的解包器
       lpUnPacker := IF2UnPacker(NewUnPacker(lpData, nLength));
  end;

  if lpUnPacker = nil then
  begin
        Form1.Log('数据接解包失败！可能是服务器端有异常！');
  end
  else
  begin
        Form1.Log('接收数据：'+lpUnPacker.GetStr('Title'));
        lpUnPacker := nil;
  end;

end;

procedure TSubCallback.OnRecvTickMsg(lpSub: CSubscribeInterface; subscribeIndex: integer; const TickMsgInfo: pchar);
begin
  Form1.Log('踢人信息：'+IntToStr(subscribeIndex) + TickMsgInfo);
end;

procedure TForm1.Button1Click(Sender: TObject);
var
  I, Len,iRetCode: Integer;
  hSend: Integer;
  Ptr: Pointer;
  pack : IF2Packer;
  lpUnPacker: IF2UnPacker;
  sTemp : string;
  bizmsg:IBizMessage;
begin
  bizmsg:=nil;
  if (connection.GetStatus and Ord(Registered)) <> 0 then
  begin
    //初始化消息包头，类型：请求，功能号：76
    BizMessage.SetFunction(76);
    BizMessage.SetPacketType(REQUEST_PACKET);

    //获取一个版本2的打包器
    pack := IF2Packer(NewPacker(2));
    pack.BeginPack;
    pack.AddField('Title');
    pack.AddStr('test');
    pack.EndPack;

    //打入业务包体
    BizMessage.SetContent(pack.GetPackBuf(),pack.GetPackLen());
    //同步发送请求
    hSend := connection.sendBizMsg(Pointer(BizMessage));
    if hSend <> 0 then
    begin
      Len := 0;
      //同步收应答
      if connection.RecvBizMsg(hSend,@bizmsg,5000) = 0 then
      begin
        iRetCode:=bizmsg.GetReturnCode();
        if iRetCode <> 0 then  //服务器端返回异常信息
         begin
             Form1.Log('错误信息：'+bizmsg.GetErrorInfo);
         end
        else
        begin
          Ptr:=bizmsg.GetContent(len);

          //版本1的解包器
          if GetPackVersion(Ptr) = 1 then
          begin
           lpUnPacker := IF2UnPacker(NewUnPackerV1(Ptr, len));
          end
          else
          begin
           //版本2的解包器
           lpUnPacker := IF2UnPacker(NewUnPacker(Ptr, len));
          end;
          if lpUnPacker = nil then
          begin
            Form1.Log('数据接解包失败！可能是服务器端有异常！');
          end
          else
          begin
            Form1.Log(lpUnPacker.GetStr('Title'));//注意后台报文，大小写敏感的
          end;
          lpUnPacker := nil;
        end;
      end;
    end;
    BizMessage.ReSet();
    //注意，必须释放打包器的缓存空间，否则会内存泄露
    pack.FreeMem(pack.GetPackBuf);
    pack := nil;
  end
  else
  begin
    //Application.ProcessMessages ;
        Form1.Log('连接还未注册!');
  end  ;
end;

procedure TForm1.Button2Click(Sender: TObject);
begin
  // 显式释放
  cfg := nil;
  connection := nil;
  BizMessage := nil;
  subscriber:=nil;
  arrayIndex:=0;
  publisher:=nil;

  Button1.Enabled := False;
   Button2.Enabled := False;
   Button3.Enabled := False;
   Button4.Enabled := False;
   ConnectBtn.Enabled := True;
   SubscriberBtn.Enabled := False;
   PublishBtn.Enabled := False;
   CancelSubBtn.Enabled := False;
   //Application.ProcessMessages ;
   Form1.Log('连接释放!');
end;

procedure TForm1.FormCreate(Sender: TObject);
begin
  Callback := TCallback.Create;
  SubCallBack := TSubCallback.Create;
  FLock := TCriticalSection.Create;
  FSend := 0;
  //Button2Click(sender);

   Button1.Enabled := False;
   Button2.Enabled := False;
   Button3.Enabled := False;
   Button4.Enabled := False;
   ConnectBtn.Enabled := True;
   SubscriberBtn.Enabled := False;
   PublishBtn.Enabled := False;
   CancelSubBtn.Enabled := False;

   subscriber:=nil;
   arrayIndex:=0;
   publisher:=nil;
end;

procedure TForm1.FormDestroy(Sender: TObject);
begin
  Callback.Free;
  FLock.Free;
end;

procedure TForm1.Log(const Str: string);
begin
  FLock.Acquire;
  try
    OutputDebugString(PChar(IntToStr(GetCurrentThreadId) + ' lock: ' + Str));
    ListBox1.Items.Add(IntToStr(GetCurrentThreadId) + ': ' + Str);
    ListBox1.Update;
  finally
    FLock.Release;
    OutputDebugString(PChar(IntToStr(GetCurrentThreadId) + ' unlock: ' + Str));
  end;
end;

procedure TForm1.Button3Click(Sender: TObject);
var
  config: IConfigInterface;
begin
  config := IConfigInterface(NewConfig);
  config.SetString('hzj', 'entry', '600570');
  config.SetString('hzj', 'entry', '601328');
  ShowMessage(config.GetString('hzj', 'entry', '没找到'));
end;

procedure TForm1.Button4Click(Sender: TObject);
var
  I, Len: Integer;
  hSend: Integer;
  Ptr: Pointer;
  pack : IF2Packer;
begin
  if (connection.GetStatus and Ord(Registered)) <> 0 then
  begin
    //初始化消息包头，类型：请求，功能号：76
    BizMessage.SetFunction(76);
    BizMessage.SetPacketType(REQUEST_PACKET);

    //获取一个版本2的打包器
    pack := IF2Packer(NewPacker(2));
    pack.BeginPack;
    pack.AddField('Title');
    pack.AddStr('test');
    pack.EndPack;

    //打入业务包体
    BizMessage.SetContent(pack.GetPackBuf(),pack.GetPackLen());
    //同步发送请求
    hSend := connection.sendBizMsg(Pointer(BizMessage),1);

    //保存收发句柄，以便匹配
    FSend := hSend;
    
    //注意，必须释放打包器的缓存空间，否则会内存泄露
    pack.FreeMem(pack.GetPackBuf);
    pack := nil;
    BizMessage.ReSet();
  end
  else
  begin
    //Application.ProcessMessages ;
        Form1.Log('连接还未注册!');
  end  ;
end;

procedure TForm1.FormClose(Sender: TObject; var Action: TCloseAction);
begin
  Button2Click(Sender);
end;

procedure TForm1.SubscriberBtnClick(Sender: TObject);
var
  iRet,subIndex: Integer;
  SubParam: CSubscribeParamInterface;
  pack : IF2Packer;
  lpUnPacker: IF2UnPacker;
  temp :PChar;
begin
  lpUnPacker:=nil;
  if (connection.GetStatus and Ord(Registered)) <> 0 then
  begin
    //先初始化
    if subscriber = nil then
    begin
       subscriber := CSubscribeInterface(connection.NewSubscriber(SubCallBack,'xuxp',5000));
    end;

    //再判断，还是nil,就说明订阅建立有问题
    if subscriber = nil then
    begin
       Form1.Log(connection.GetMCLastError());
    end
    else
    begin
        //开始订阅 ,首先组装订阅参数
        SubParam:=CSubscribeParamInterface(NewSubscribeParam());
        SubParam.SetTopicName('topic0');
        SubParam.SetFilter('branchno','100');

        //获取一个版本2的打包器
        pack := IF2Packer(NewPacker(2));
        pack.BeginPack;
        pack.AddField('Title');
        pack.AddStr('test');
        pack.EndPack;

        //发起订阅
        subIndex := subscriber.SubscribeTopic(SubParam,5000,@lpUnPacker,Pointer(pack));
        //Application.ProcessMessages ;
        if subIndex >0 then
        begin
           sunIndexArray[arrayIndex] :=  subIndex;
           Inc(arrayIndex);
           Form1.Log('订阅成功:'+IntToStr(subIndex));
        end
        else
        begin
           Form1.Log('订阅失败：'+connection.GetErrorMsg(subIndex));
           if  lpUnPacker<>nil then
           begin
              //temp:=lpUnPacker.GetColName(0) ;
              temp:=lpUnPacker.GetStr('error_info') ;
              Form1.Log('错误信息：'+temp);
              lpUnPacker:=nil;  //必须得释放
           end;
        end;

        //注意，必须释放打包器的缓存空间，否则会内存泄露
        pack.FreeMem(pack.GetPackBuf);
        pack := nil;
        SubParam  := nil;
    end ;
  end
  else
  begin
    //Application.ProcessMessages ;
        Form1.Log('连接还未注册!');
  end  ;
end;

procedure TForm1.ConnectBtnClick(Sender: TObject);
var
  iRet: Integer;
begin
  if connection = nil then
  begin
    cfg := IConfigInterface(NewConfig);
    cfg.Load('t2sdk.ini');
    connection := IConnectionInterface(NewConnection((cfg)));
    BizMessage := IBizMessage(NewBizMessage());
    iRet:=connection.Create2BizMsg(Callback);
    if ( iRet = 0) then
    begin
      iRet := connection.Connect(5000);
      //Application.ProcessMessages ;
      if (iRet<> 0) then
      begin
          Form1.Log(connection.GetErrorMsg(iRet));
      end
      else
      begin
        Form1.Log('连接成功!');
        Button1.Enabled := True;
        Button2.Enabled := True;
        Button3.Enabled := True;
        Button4.Enabled := True;
        ConnectBtn.Enabled := False;
        SubscriberBtn.Enabled := True;
        PublishBtn.Enabled := True;
        CancelSubBtn.Enabled := True;
        btn8.Enabled := True;
        btn9.Enabled := True;
        btn10.Enabled := True;
      end;

    end
    else
    begin
       Form1.Log(connection.GetErrorMsg(iRet));
    end;
  end;
end;

procedure TForm1.PublishBtnClick(Sender: TObject);
var
  iRet : Integer;
  pack : IF2Packer;
begin
  if (connection.GetStatus and Ord(Registered)) <> 0 then
  begin
    //先初始化
    if publisher = nil then
    begin
       publisher := CPublishInterface(connection.NewPublisher('xuxp',200,5000));
    end;

    //再判断，还是nil,就说明订阅建立有问题
    if publisher = nil then
    begin
       Form1.Log(connection.GetMCLastError());
    end
    else
    begin
        //开始发布 ,首先组装发布包
        pack := IF2Packer(NewPacker(2));
        pack.BeginPack;
        pack.AddField('branchno');
        pack.AddField('Title');
        pack.AddStr('100');
        pack.AddStr('test');
        pack.EndPack;

        //正式发布
        iRet:=publisher.PubMsgByPacker('topic0',pack.UnPack(),5000);
        //Application.ProcessMessages ;
        if iRet <>0 then
        begin
           Form1.Log('发布失败：'+connection.GetErrorMsg(iRet));
        end
        else
        begin
           Form1.Log('发布成功!');
        end;  


        //注意，必须释放打包器的缓存空间，否则会内存泄露
        pack.FreeMem(pack.GetPackBuf);
        pack := nil;
    end ;
  end
  else
  begin
    //Application.ProcessMessages ;
        Form1.Log('连接还未注册!');
  end  ;
end;

procedure TForm1.CancelSubBtnClick(Sender: TObject);
var
  I,iRet : Integer;
begin
   if (connection.GetStatus and Ord(Registered)) <> 0 then
  begin
    //先初始化
    if subscriber = nil then
    begin
      //Application.ProcessMessages ;
       Form1.Log('没有订阅者');
    end
    else
    begin
      //Application.ProcessMessages ;
      if arrayIndex > 0 then
      begin
         for I := 0 to arrayIndex do
         begin
             iRet:=subscriber.CancelSubscribeTopic(sunIndexArray[I]);
             if iRet <> 0 then
             begin
                Form1.Log('取消订阅失败：'+connection.GetErrorMsg(iRet));
             end
             else
             begin
                Form1.Log('取消订阅成功：'+IntToStr(sunIndexArray[I]) );
             end;
         end;

         arrayIndex :=0;
      end
      else
      begin
         Form1.Log('没有订阅项');
      end;
    end;

  end
  else
  begin
    //Application.ProcessMessages ;
        Form1.Log('连接还未注册!');
  end  ;
end;

procedure TForm1.ClearBtnClick(Sender: TObject);
begin
    ListBox1.Items.Clear();
    ListBox1.Update;
end;

procedure TForm1.btn8Click(Sender: TObject);
var
  I, Len: Integer;
  hSend: Integer;
  Ptr: Pointer;
  pack : IF2Packer;
begin
  if (connection.GetStatus and Ord(Registered)) <> 0 then
  begin
    //初始化消息包头，类型：请求，功能号：620001
    BizMessage.SetFunction(620001);
    BizMessage.SetPacketType(REQUEST_PACKET);
    //订阅类型 这里以1为例子
    BizMessage.SetIssueType(1);

    //获取一个版本2的打包器
    pack := IF2Packer(NewPacker(2));
    pack.BeginPack;
    pack.AddField('pub_no');
    pack.AddStr('*');
    pack.EndPack;

    //打入过滤字段
    BizMessage.SetKeyInfo(pack.GetPackBuf,pack.GetPackLen);

    //异步发送请求
    hSend := connection.SendBizMsg(Pointer(BizMessage), 1);

    //保存收发句柄，以便匹配
    FSend := hSend;

    //注意，必须释放打包器的缓存空间，否则会内存泄露
    pack.FreeMem(pack.GetPackBuf);
    pack := nil;
    BizMessage.ReSet();
  end
  else
  begin
    //Application.ProcessMessages ;
        Form1.Log('连接还未注册!');
  end  ;
end;

procedure TForm1.btn9Click(Sender: TObject);
var
    I, Len: Integer;
  hSend: Integer;
  Ptr: Pointer;
  pack : IF2Packer;
begin
    if (connection.GetStatus and Ord(Registered)) <> 0 then
  begin
    //初始化消息包头，类型：请求，功能号：620002
    BizMessage.SetFunction(620002);
    BizMessage.SetPacketType(REQUEST_PACKET);
    //订阅类型 这里以1为例子
    BizMessage.SetIssueType(1);

    //获取一个版本2的打包器
    pack := IF2Packer(NewPacker(2));
    pack.BeginPack;
    pack.AddField('pub_no');
    pack.AddStr('*');
    pack.EndPack;

    //打入过滤字段
    BizMessage.SetKeyInfo(pack.GetPackBuf,pack.GetPackLen);

    //异步发送请求
    hSend := connection.SendBizMsg(Pointer(BizMessage), 1);

    //保存收发句柄，以便匹配
    FSend := hSend;

    //注意，必须释放打包器的缓存空间，否则会内存泄露
    pack.FreeMem(pack.GetPackBuf);
    pack := nil;
    BizMessage.ReSet();
  end
  else
  begin
    //Application.ProcessMessages ;
        Form1.Log('连接还未注册!');
  end  ;
end;

procedure TForm1.btn10Click(Sender: TObject);
var
    I, Len: Integer;
  hSend: Integer;
  Ptr: Pointer;
  pack : IF2Packer;
begin
  if (connection.GetStatus and Ord(Registered)) <> 0 then
  begin
    //初始化消息包头，类型：请求，功能号：620003,620020~620099为发布功能号范围
    BizMessage.SetFunction(620003);
    BizMessage.SetPacketType(REQUEST_PACKET);
    //发布类型 这里以1为例子
   BizMessage.SetIssueType(1);

    //获取一个版本2的打包器
    pack := IF2Packer(NewPacker(2));
    pack.BeginPack;
    pack.AddField('pub_no');
    pack.AddStr('1');
    pack.EndPack;

    //打入过滤字段
    BizMessage.SetKeyInfo(pack.GetPackBuf,pack.GetPackLen);
   //打入业务体
    pack.BeginPack;
    pack.AddField('fund_no');
    pack.AddStr('123456');
    pack.EndPack;
    BizMessage.SetContent(pack.GetPackBuf,pack.GetPackLen);
    //异步发送请求
    hSend := connection.SendBizMsg(Pointer(BizMessage), 1);

    //保存收发句柄，以便匹配
    FSend := hSend;

    //注意，必须释放打包器的缓存空间，否则会内存泄露
    pack.FreeMem(pack.GetPackBuf);
    pack := nil;
    BizMessage.ReSet();
  end
  else
  begin
    //Application.ProcessMessages ;
        Form1.Log('连接还未注册!');
  end  ;
end;

end.
