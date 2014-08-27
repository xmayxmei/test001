package com.service;



import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import bo.BindUserResultVO;
import bo.ClientLoginResultBO;
import bo.CustomerInfoBO;
import bo.CustomerRightAddBO;
import bo.CustomerRightQueryBO;
import bo.MyDealBackQueryBO;
import bo.MyFundJourQueryBO;
import bo.MyFundQueryBO;
import bo.MyStockQueryBO;
import bo.WeiTuoQueryBO;

import com.ErrorCodeType;
import com.ServiceInterface;
import com.hundsun.t2sdk.common.core.context.ContextUtil;
import com.hundsun.t2sdk.common.share.dataset.MapWriter;
import com.hundsun.t2sdk.interfaces.share.event.EventType;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import com.util.InterfaceServiceHelper;

/**
 * T2业务接口管理服务
 * @author wangsy
 *
 */
public class ZheShangT2Interface extends ServiceInterface {
	private static Logger logger = Logger.getLogger(ZheShangT2Interface.class);
	/**
	 * 客户登录校验
	 */
	public static ClientLoginResultBO login(String fundAccount,
			String password,String userIdentifer) {
		ClientLoginResultBO bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("200", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//版本
			map.put("version", 4);
			//功能号
			map.put("function_id", 200);
			//操作员分支代码
			map.put("op_branch_no", 0);
			//委托方式
			map.put("op_entrust_way", "7");
			//站点|电话
			map.put("op_station", userIdentifer);
			//分支编号，股民所在营业部号
			map.put("branch_no ", 0);
			//账户类型为资金账号，‘1’，‘2’，‘3’，4’，‘5’, ‘6’, ‘A’分别表示account_content代表资金帐号，股东内码，和资金卡号，银行帐号，股东帐号
			map.put("input_content", "1");
			//输入内容，账号
			map.put("account_content", fundAccount);
			//交易密码
			map.put("password", password);
			//账号验证方式，当input_content为‘4’，‘5’时，则content_type明确银行号和股东的市场类别，其它输入为‘0’。
			map.put("content_type", "0");

			event.putEventData(map.getDataset());


			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);

			StringBuilder sb = new StringBuilder();
			//读取event的返回码,-61账号错误，-63密码错误
			sb.append("returnCode: " + rsp.getReturnCode() + "\n");
			//读取event的错误号，如果没有则返回 "0"
			sb.append("errorNo: " + rsp.getErrorNo() + "\n");
			//读取event的错误信息，如果没有设置则返回null
			sb.append("errorInfo: " + rsp.getErrorInfo() + "\n");

			if(rsp.getReturnCode()!=0){
				sb.append("错误信息："+ErrorCodeType.getErrorCode(rsp.getReturnCode()));
			}

			//输出查询值
			List<Object> list = InterfaceServiceHelper.outputAnEvent(rsp,ClientLoginResultBO.class);
			if(list!=null&&list.size()>0){
				bo =(ClientLoginResultBO)list.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bo;
	}


	/**
	 * 查询客户信息
	 */
	public static CustomerInfoBO queryCustomerInfo(String fundAccount,
			String password,String branchNo) {
		CustomerInfoBO bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("415", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//版本
			map.put("version", 4);
			//功能号
			map.put("function_id", 200);
			//操作员分支代码
			map.put("op_branch_no", 0);
			//委托方式
			map.put("op_entrust_way", "7");
			//站点|电话
			map.put("op_station", "13005017141");
			//分支编号，股民所在营业部号
			if(branchNo!=null && !branchNo.isEmpty()){
				map.put("branch_no", Long.parseLong(branchNo));
			}
			//资金账号
			map.put("fund_account", fundAccount);
			//交易密码
			map.put("password", password);

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper.outputAnEvent(rsp,CustomerInfoBO.class);
			if(list!=null&&list.size()>0){
				bo =(CustomerInfoBO)list.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bo;
	}


	/**
	 * 客户资金查询
	 */
	public static List<MyFundQueryBO> getMyFund(String fundAccount,
			String branch_no,String op_station) {
		List<MyFundQueryBO> fundList = new ArrayList<MyFundQueryBO>();
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("9879", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			map.put("version", 4);
			//操作员分支代码
			map.put("op_branch_no", 0);
			//委托方式
			map.put("op_entrust_way", "7");
			//站点|电话
			if(op_station!=null&&!op_station.equals(""))
			map.put("op_station", op_station);
			//功能号
			map.put("function_id", 9879);
			//分支编号，股民所在营业部号
			if(branch_no!=null && !branch_no.isEmpty()){
				map.put("branch_no", Long.parseLong(branch_no));
			}
			//资金账号
			map.put("fund_account", fundAccount);
			//币种
            map.put("money_type", "0");//人民币
			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper.outputAnEvent(rsp,MyFundQueryBO.class);
			if(list!=null&&list.size()>0){
				for(Object obj:list){
					fundList.add((MyFundQueryBO)obj);
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return fundList;
	}


	/**
	 * 查询股票
	 */
	public static List<MyStockQueryBO> getMyStock(String fund_account
			,String branch_no ,String op_station) {
		List<MyStockQueryBO> myStockList = new ArrayList<MyStockQueryBO>();	
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("9878", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			map.put("version", 4);
			//操作员分支代码
			map.put("op_branch_no", 0);
			//委托方式
			map.put("op_entrust_way", "7");
			
			//站点|电话
			if(op_station!=null&&!op_station.equals(""))
			map.put("op_station", op_station);
			//功能号
			map.put("function_id", 9878);
			//分支编号，股民所在营业部号
			if(branch_no!=null && !branch_no.equals("")){
				map.put("branch_no", Long.parseLong(branch_no));
			}
			//资金账号
			map.put("fund_account", fund_account);

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper.outputAnEvent(rsp,MyStockQueryBO.class);
			if(list!=null && !list.isEmpty()){
				for(Object bo:list){
					myStockList.add((MyStockQueryBO)bo);
				}	
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return myStockList;
	}

	/**
	 * 委托查询
	 */
	public static List<WeiTuoQueryBO> getMyWeiTuo(String fund_account
			,String branch_no) {
		List<WeiTuoQueryBO> myWeituoList = new ArrayList<WeiTuoQueryBO>();	
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("401", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			map.put("version", 4);
			//操作员分支代码
			map.put("op_branch_no", 0);
			//委托方式
			map.put("op_entrust_way", "7");
			//站点|电话
			map.put("op_station", "13005017141");
			//功能号
			map.put("function_id", 401);
			//分支编号，股民所在营业部号
			if(branch_no!=null && !branch_no.equals("")){
				map.put("branch_no", Long.parseLong(branch_no));
			}
			//资金账号
			map.put("fund_account", fund_account);

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper.outputAnEvent(rsp,WeiTuoQueryBO.class);
			if(list!=null && !list.isEmpty()){
				for(Object bo:list){
					myWeituoList.add((WeiTuoQueryBO)bo);
				}	
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return myWeituoList;
	}
	
	
	/**
	 * 查询成交
	 */
	public static List<MyDealBackQueryBO> getMyDealBack(String fundAccount,
			String branch_no,String op_station) {
		List<MyDealBackQueryBO> fundList = new ArrayList<MyDealBackQueryBO>();
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("9890", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			map.put("version", 4);
			//操作员分支代码
			map.put("op_branch_no", 0);
			//委托方式
			map.put("op_entrust_way", "7");
			//站点|电话
			map.put("op_station", op_station);
			//功能号
			map.put("function_id", 9890);
			//分支编号，股民所在营业部号
			if(branch_no!=null && !branch_no.isEmpty()){
				map.put("branch_no", Long.parseLong(branch_no));
			}
			//资金账号
			map.put("fund_account", fundAccount);

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper.outputAnEvent(rsp,MyDealBackQueryBO.class);
			if(list!=null&&list.size()>0){
				for(Object obj:list){
					fundList.add((MyDealBackQueryBO)obj);
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return fundList;
	}
	
	/**
	 * 查询资金流水
	 */
	public static List<MyFundJourQueryBO> getMyFundJour(String fundAccount,
			String branch_no,String op_station) {
		List<MyFundJourQueryBO> fundList = new ArrayList<MyFundJourQueryBO>();
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("9891", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			map.put("version", 4);
			//操作员分支代码
			map.put("op_branch_no", 0);
			//委托方式
			map.put("op_entrust_way", "7");
			//站点|电话
			map.put("op_station", op_station);
			//功能号
			map.put("function_id", 9891);
			//分支编号，股民所在营业部号
			if(branch_no!=null && !branch_no.isEmpty()){
				map.put("branch_no", Long.parseLong(branch_no));
			}
			//资金账号
			map.put("fund_account", fundAccount);

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper.outputAnEvent(rsp,MyFundJourQueryBO.class);
			if(list!=null&&list.size()>0){
				for(Object obj:list){
					fundList.add((MyFundJourQueryBO)obj);
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return fundList;
	}
	
	/**
	 * 客户权限查询
	 * @param bindUser
	 * @return
	 */
	public static CustomerRightQueryBO customerRightQuery(BindUserResultVO bindUser){
		CustomerRightQueryBO bo =null;	
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_customerRightQuery, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			//站点|电话
			map.put("op_station", bindUser.getOpStation());
			//分支编号，股民所在营业部号
			if(bindUser.getBranchNo()!=null && !bindUser.getBranchNo().equals("")){
				map.put("branch_no", bindUser.getBranchNo());
			}
			//客户编号
			map.put("client_id", bindUser.getCustomerCode());
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//密码
			map.put("password", bindUser.getPassword());
		
			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,CustomerRightQueryBO.class);
			if(list!=null && !list.isEmpty()){
				bo =(CustomerRightQueryBO) list.get(0);	
			}

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return bo;
			
	}
	
	/**
	 * 客户权限添加
	 * @param bindUser
	 * @return
	 */
	public static CustomerRightAddBO customerRightAdd(String right,BindUserResultVO bindUser){
		CustomerRightAddBO bo =null;	
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_customerRightAdd, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			//站点|电话
			map.put("op_station", bindUser.getOpStation());
			//分支编号，股民所在营业部号
			if(bindUser.getBranchNo()!=null && !bindUser.getBranchNo().equals("")){
				map.put("branch_no", bindUser.getBranchNo());
			}
			//客户编号
			map.put("client_id", bindUser.getCustomerCode());
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//密码
			map.put("password", bindUser.getPassword());
			
			map.put("client_rights", right);

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,CustomerRightAddBO.class);
			if(list!=null && !list.isEmpty()){
				bo =(CustomerRightAddBO) list.get(0);	
			}

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return bo;
			
	}




}
