package com.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import bo.BindUserResultVO;
import bo.EcontractQueryBO;
import bo.EcontractSignBO;
import bo.FuncTrustShareBO;
import bo.FundAccountQueryBO;
import bo.FundGouBO;
import bo.FundMarketQueryBO;
import bo.FundOrderBO;
import bo.FundOrderCancleBO;
import bo.FundRedemption;
import bo.FundRiskCheckBO;
import bo.FundShareBO;
import bo.TrustAccountQueryBO;
import bo.TrustOrderBO;
import bo.TrustOrderCancleBO;
import bo.TrustProductGouBO;
import bo.TrustProductQueryBO;
import bo.TrustRiskCheckBO;

import com.ServiceInterface;
import com.hundsun.t2sdk.common.core.context.ContextUtil;
import com.hundsun.t2sdk.common.share.dataset.MapWriter;
import com.hundsun.t2sdk.interfaces.share.event.EventType;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import com.util.InterfaceServiceHelper;

public class ShoppingInterface extends ServiceInterface {
	public static Logger logger = Logger.getLogger(ShoppingInterface.class);
	
	/**
	 * 信托产品查询
	 * @return
	 */
	public static List<TrustProductQueryBO> queryTrustProduct(
			String branchNo,String opstation,String ProductCode,
			String productTaNo) {
		List<TrustProductQueryBO> trustList = new ArrayList<TrustProductQueryBO>();
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_queryTrustProduct, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			//分支编号，股民所在营业部号
			if(branchNo!=null){
				map.put("branch_no", branchNo);
			}
			//站点|电话
			if(opstation!=null){
				map.put("op_station", opstation);
			}
			//产品代码
			if( ProductCode!=null){
				map.put("prod_code", ProductCode);
			}
			//TA编码
			if(productTaNo!=null){
				map.put("prodta_no", productTaNo);
			}
			
			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,TrustProductQueryBO.class);
			if(list!=null  && list.size()>0){
				for(Object obj:list){
				trustList.add((TrustProductQueryBO)obj);
				}
				//Collections.addAll(trustList, (TrustProductQueryBO[])list.toArray());
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return trustList;
	}

	/**
	 * 信托产品预约认购
	 * @return
	 */
	public static TrustProductGouBO yuyueRengou(String productCode ,String productTaNo,
			String balance,BindUserResultVO bindUser) {
		TrustProductGouBO bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("337111", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			//站点|电话
			if(bindUser.getOpStation()!=null)
				map.put("op_station", bindUser.getOpStation());
			//分支编号，股民所在营业部号
			if(bindUser.getBranchNo()!=null)
				map.put("branch_no", bindUser.getBranchNo());
			//客户号
			map.put("client_id", bindUser.getCustomerCode());
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());

			//产品代码
			map.put("prod_code", productCode);
			//TA编码
			map.put("prodta_no",productTaNo);
			//委托金额
			map.put("entrust_balance", balance);

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,TrustProductGouBO.class);
			if(list!=null  && list.size()>0){
				bo = (TrustProductGouBO)list.get(0);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return bo;
	}


	/**
	 * 信托产品认购
	 * @return
	 */
	public static TrustProductGouBO trustRengou(String productCode ,String productTaNo,
			String balance,BindUserResultVO bindUser) {
		TrustProductGouBO bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_trustYuyuerengou, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			//站点|电话
			map.put("op_station", bindUser.getOpStation());
			//分支编号，股民所在营业部号
			map.put("branch_no", bindUser.getBranchNo());
			//客户号
			map.put("client_id", bindUser.getCustomerCode());
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());


			//产品代码
			map.put("prod_code", productCode);
			//TA编码
			map.put("prodta_no",productTaNo);
			//委托金额
			map.put("entrust_balance", balance);

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,TrustProductGouBO.class);
			if(list!=null  && list.size()>0){
				bo = (TrustProductGouBO)list.get(0);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return bo;
	}

	/**
	 * 信托产品份额查询
	 * @param vo
	 * @return
	 */
	public static FuncTrustShareBO trustShareQuery(String ProductCode,
			String productTaNo,BindUserResultVO bindUser){		
		FuncTrustShareBO bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("337151", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			if(bindUser.getOpStation()!=null){
				//站点|电话
				map.put("op_station", bindUser.getOpStation());
			}
			if(bindUser.getBranchNo()!=null){
				//分支编号，股民所在营业部号
				map.put("branch_no", bindUser.getBranchNo());
			}
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());
			
			//产品代码
			map.put("prod_code", ProductCode);
			//TA编码
			map.put("prodta_no", productTaNo);

			event.putEventData(map.getDataset());
			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,FuncTrustShareBO.class);
			if(list!=null  && list.size()>0){
				bo = (FuncTrustShareBO)list.get(0);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return bo;

	}
	
	/**
	 * 信托风险检查
	 * @param productCode
	 * @return
	 */
	public static TrustRiskCheckBO trustRiskCheck(String productCode,BindUserResultVO bindUser){
		TrustRiskCheckBO bo = new TrustRiskCheckBO();
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_trustRiskCheck, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			if(bindUser.getOpStation()!=null){
				//站点|电话
				map.put("op_station", bindUser.getOpStation());
			}
			if(bindUser.getBranchNo()!=null){
				//分支编号，股民所在营业部号
				map.put("branch_no", bindUser.getBranchNo());
			}
			//客户号
			map.put("client_id", bindUser.getCustomerCode());
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());
			
			//产品代码
			map.put("prod_code", productCode);
					
			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,TrustRiskCheckBO.class);
			if(list!=null  && list.size()>0){
				bo=(TrustRiskCheckBO) list.get(0);
			}

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return bo;		
	}

	
	/**
	 * 信托账户查询
	 * @param bindUser
	 * @return
	 */
	public static TrustAccountQueryBO trustAccountQuery(BindUserResultVO bindUser){
		TrustAccountQueryBO bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_trustAccountQuery, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			if(bindUser.getOpStation()!=null){
				//站点|电话
				map.put("op_station", bindUser.getOpStation());
			}
			if(bindUser.getBranchNo()!=null){
				//分支编号，股民所在营业部号
				map.put("branch_no", bindUser.getBranchNo());
			}
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());
							
			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,TrustAccountQueryBO.class);
			if(list!=null  && list.size()>0){
				bo=(TrustAccountQueryBO) list.get(0);
			}

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return bo;	
		
	}



	/**
	 * 基金行情信息查询
	 * @param opStation C12 站点/电话 
	 * @param branchNo 分支代码
	 * @param requestNum 请求行数，不送按50行处理，超过系统指定值（1000行）按系统指定值（1000行）处理
	 * @param positionStr 定位串
	 * @return
	 */
	public static List<FundMarketQueryBO> fundMarketQuery(String branchNo,
			String opstation,String fundCode) {
		List<FundMarketQueryBO> fundList = new ArrayList<FundMarketQueryBO>();
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("7413", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//版本号
			map.put("version", 4);
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			//功能号
			map.put("function_id", 7413);
			//站点|电话
			if(opstation!=null){
				map.put("op_station", opstation);
			}
			//分支编号，股民所在营业部号
			if(branchNo!=null){
				map.put("branch_no", branchNo);
			}
			//基金代码
			if(fundCode!=null){
				map.put("fund_code", fundCode);	
			}
		//	map.put("fund_company", "98");
			event.putEventData(map.getDataset());
			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,FundMarketQueryBO.class);
			System.out.println(list.size());
			if(list!=null  && list.size()>0){
				for(Object obj:list){
					fundList.add((FundMarketQueryBO)obj);
				}
			//	Collections.addAll(fundList, (FundMarketQueryBO[])list.toArray());
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return fundList;
	}

	/**
	 * 基金产品认购
	 * @param product
	 * @param bindUser
	 * @return
	 */
	public static FundGouBO fundRengou(String fundCode,
			String productTaNo,String amount,String balance
			,BindUserResultVO bindUser) {
		FundGouBO bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("7401", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			//功能号
			map.put("function_id", 7401);
			//站点|电话
			if(bindUser.getOpStation()!=null)
			map.put("op_station", bindUser.getOpStation());
			//分支编号，股民所在营业部号
			if(bindUser.getBranchNo()!=null)
			map.put("branch_no ", bindUser.getBranchNo());
			//资金账号
			if(bindUser.getFundAccount()!=null)
			map.put("fund_account", bindUser.getFundAccount());
			//密码
			if(bindUser.getPassword()!=null)
			map.put("password",bindUser.getPassword());
			if(productTaNo!=null){
				map.put("fund_company", productTaNo);
			}
			//基金代码
			if(fundCode!=null)
			map.put("fund_code", fundCode);
			//认购数量
			map.put("amount", amount);
			//委托金额
			map.put("balance", balance);


			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,FundGouBO.class);
			if(list!=null  && list.size()>0){
				bo = (FundGouBO)list.get(0);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return bo;
	}

	/**
	 * 基金产品申购
	 * @param product
	 * @param bindUser
	 * @return
	 */
	public static FundGouBO fundShengou(String fundCode,String productTaNo,
			String balance,BindUserResultVO bindUser) {
		FundGouBO bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_fundShengou, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			//功能号
			map.put("function_id", 7402);
			//站点|电话
			if(bindUser.getOpStation()!=null)
			map.put("op_station", bindUser.getOpStation());
			//分支编号，股民所在营业部号
			if(bindUser.getBranchNo()!=null)
			map.put("branch_no ", bindUser.getBranchNo());
			//资金账号
			if(bindUser.getFundAccount()!=null)
			map.put("fund_account", bindUser.getFundAccount());
			//密码
			if(bindUser.getPassword()!=null)
			map.put("password",bindUser.getPassword());
			if(productTaNo!=null){
				map.put("fund_company", productTaNo);
			}
			//基金代码
			if(fundCode!=null)
			map.put("fund_code",fundCode);
			//委托金额
			if(balance!=null){
		     	map.put("balance", balance);
			}

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,FundGouBO.class);
			if(list!=null  && list.size()>0){
				bo = (FundGouBO)list.get(0);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return bo;
	}

	/**
	 * 基金份额查询
	 * @return
	 */
	public static FundShareBO fundShare(String TaNo,String fundCode,BindUserResultVO bindUser){
		FundShareBO share = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("7411", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//版本号
			map.put("version", 4);
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			//功能号
			map.put("function_id", 7411);
			//站点|电话
			if(bindUser.getOpStation()!=null){
				map.put("op_station", bindUser.getOpStation());
			}
			//分支编号，股民所在营业部号
			if(bindUser.getBranchNo()!=null){
				map.put("branch_no", bindUser.getBranchNo());
			}
			//资金账号
			if(bindUser.getFundAccount()!=null){
				map.put("fund_account", bindUser.getFundAccount());
			}
			//密码
			if(bindUser.getPassword()!=null){
				map.put("password",bindUser.getPassword());
			}
			//基金代码
			if(fundCode!=null){
				map.put("fund_code", fundCode);	
			}
			event.putEventData(map.getDataset());
			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,FundShareBO.class);
			if(list!=null  && list.size()>0){
				share = (FundShareBO)list.get(0);
				//Collections.addAll(fundList, (FundShareBO[])list.toArray());
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return share;

	}
	
	/**
	 * 基金s风险检查
	 * @param productCode
	 * @return
	 */
	public static FundRiskCheckBO fundRiskCheck(String productCode,String productTaNo,BindUserResultVO bindUser){
		FundRiskCheckBO bo = new FundRiskCheckBO();
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_fundRiskCheck, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//版本号
			map.put("version", 4);
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			if(bindUser.getOpStation()!=null){
				//站点|电话
				map.put("op_station", bindUser.getOpStation());
			}
			if(bindUser.getBranchNo()!=null){
				//分支编号，股民所在营业部号
				map.put("branch_no", bindUser.getBranchNo());
			}
			map.put("function_id", Integer.parseInt(functionNo.function_fundRiskCheck));
	
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());
			
			//基金代码
			map.put("fund_code", productCode);
			//基金公司
		    map.put("fund_company", productTaNo);
			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,FundRiskCheckBO.class);
			if(list!=null  && list.size()>0){
				bo=(FundRiskCheckBO) list.get(0);
			}

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return bo;		
	}

	
	/**
	 * 电子合同查询
	 * @param productCode
	 * @param productTaNo
	 * @param productType 1:信托产品 2:理财产品
	 * @return
	 */
	public static EcontractQueryBO econtractQuery(String productCode,String productTaNo,
			int productType,BindUserResultVO bindUser){
		EcontractQueryBO bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_econtractQuery, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			//站点|电话
			map.put("op_station", bindUser.getOpStation());
			//分支编号，股民所在营业部号
			map.put("branch_no", bindUser.getBranchNo());
			//客户编号
			map.put("client_id", bindUser.getCustomerCode());
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//密码
			map.put("password",bindUser.getPassword());
			if(productType==1){
				//基金代码
				map.put("prod_type", "7");
				//基金公司
				if(productTaNo!=null){
					map.put("fund_company", productTaNo);
				}
				map.put("fund_code", productCode);
				
			}else{
				//信托
				map.put("prod_type", "2");		
				map.put("prod_code", productCode);
				map.put("prodta_no",productTaNo);
			}

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,EcontractQueryBO.class);
			if(list!=null  && list.size()>0){
				bo =(EcontractQueryBO) list.get(0);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return bo;

	}

	/**
	 * 电子合同签署
	 * @param riskFlag 是否签署风险揭示书（0-未签署风险揭示书，a-已签署电子风险揭示书，b-已签署纸质风险揭示书）
	 * @param bindUser
	 * @return
	 */
	public static EcontractSignBO econtractSign(String productCode,String productTaNo,
			int productType,String riskFlag,BindUserResultVO bindUser){
		EcontractSignBO bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_econtractSign, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			//站点|电话
			map.put("op_station", bindUser.getOpStation());
			//分支编号，股民所在营业部号
			map.put("branch_no", bindUser.getBranchNo());
			//客户编号
			map.put("client_id", bindUser.getCustomerCode());
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//密码
			map.put("password",bindUser.getPassword());
			//是否签署风险揭示书
			map.put("sub_risk_flag", riskFlag);			
			if(productCode!=null&&productType==1){
				//基金产品签署
				if(productTaNo!=null){
					map.put("fund_company", productTaNo);//基金公司
				}
				//基金代码
				map.put("fund_code", productCode);
			}else{
				//信托产品代码
				map.put("prod_code", productCode);
				map.put("prodta_no",productTaNo);
			}
			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			int returnCode = rsp.getReturnCode();
			System.out.println(("returnCode:"+returnCode));
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,EcontractSignBO.class);
			if(list!=null  && list.size()>0){
				bo =(EcontractSignBO) list.get(0);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return bo;

	}
	/**
	 * 信托产品委托查询
	 * @param vo
	 * @return
	 */
	public static List<TrustOrderBO> trustOrderQuery(
			Long requestNum,String positionStr,BindUserResultVO bindUser){		
		List<TrustOrderBO> orderList  = new ArrayList<TrustOrderBO>();
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("337152", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			if(bindUser.getOpStation()!=null){
				//站点|电话
				map.put("op_station", bindUser.getOpStation());
			}
			if(bindUser.getBranchNo()!=null){
				//分支编号，股民所在营业部号
				map.put("branch_no", bindUser.getBranchNo());
			}
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());
			//请求行数
			if(requestNum!=null)
			map.put("request_num", requestNum);
			//定位串
			if(positionStr!=null)
			map.put("position_str", positionStr);

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,TrustOrderBO.class);
			if(list!=null  && list.size()>0){
				for(Object obj:list){
					orderList.add((TrustOrderBO)obj);
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;
	}
	
	/**
	 * 信托产品历史委托查询
	 * @param vo
	 * @return
	 */
	public static List<TrustOrderBO> trustOrderHistoryQuery(String startDate,String endDate,
			Long requestNum,String positionStr,BindUserResultVO bindUser){		
		List<TrustOrderBO> orderList  = new ArrayList<TrustOrderBO>();
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("339700", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			if(bindUser.getOpStation()!=null){
				//站点|电话
				map.put("op_station", bindUser.getOpStation());
			}
			if(bindUser.getBranchNo()!=null){
				//分支编号，股民所在营业部号
				map.put("branch_no", bindUser.getBranchNo());
			}
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());
			map.put("start_date", startDate);
			map.put("end_date", endDate);
			//请求行数
			if(requestNum!=null)
			map.put("request_num", requestNum);
			//定位串
			if(positionStr!=null)
			map.put("position_str", positionStr);
			
			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,TrustOrderBO.class);
			if(list!=null  && list.size()>0){
				for(Object obj: list){
					orderList.add((TrustOrderBO)obj);
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;

	}
	
	
	/**
	 * 基金产品委托查询
	 * @param vo
	 * @return
	 */
	public static List<FundOrderBO> fundOrderQuery(
			Long requestNum,String positionStr,BindUserResultVO bindUser){		
		List<FundOrderBO> orderList  = new ArrayList<FundOrderBO>();
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("7410", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			if(bindUser.getOpStation()!=null){
				//站点|电话
				map.put("op_station", bindUser.getOpStation());
			}
			if(bindUser.getBranchNo()!=null){
				//分支编号，股民所在营业部号
				map.put("branch_no", bindUser.getBranchNo());
			}
			map.put("function_id", 7410);
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());
			//请求行数
			if(requestNum!=null)
			map.put("request_num", requestNum);
			//定位串
			if(positionStr!=null)
			map.put("position_str", positionStr);

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,FundOrderBO.class);
			if(list!=null  && list.size()>0){
				for(Object obj:list){
					orderList.add((FundOrderBO)obj);
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;
	}
	
	/**
	 * 基金产品历史委托查询
	 * @param vo
	 * @return
	 */
	public static List<FundOrderBO> fundOrderHistoryQuery(
			String startDate,String endDate,Long requestNum,
			String positionStr,BindUserResultVO bindUser){		
		List<FundOrderBO> orderList  = new ArrayList<FundOrderBO>();
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias("7450", EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			if(bindUser.getOpStation()!=null){
				//站点|电话
				map.put("op_station", bindUser.getOpStation());
			}
			if(bindUser.getBranchNo()!=null){
				//分支编号，股民所在营业部号
				map.put("branch_no", bindUser.getBranchNo());
			}
			map.put("function_id", 7450);
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());
			map.put("start_date", startDate);
			map.put("end_date", endDate);
			//请求行数
			if(requestNum!=null)
			map.put("request_num", requestNum);
			//定位串
			if(positionStr!=null)
			map.put("position_str", positionStr);
			
			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,FundOrderBO.class);
			if(list!=null  && list.size()>0){
				for(Object obj: list){
					orderList.add((FundOrderBO)obj);
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;

	}
	
	/**
	 * 基金账户查询
	 * @param bindUser
	 * @return
	 */
	public static FundAccountQueryBO fundAccountQuery(BindUserResultVO bindUser){
		FundAccountQueryBO bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_fundAccountQuery, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();
			map.put("version", 4);
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			if(bindUser.getOpStation()!=null){
				//站点|电话
				map.put("op_station", bindUser.getOpStation());
			}
			if(bindUser.getBranchNo()!=null){
				//分支编号，股民所在营业部号
				map.put("branch_no", bindUser.getBranchNo());
			}
			map.put("function_id", Integer.parseInt(functionNo.function_fundAccountQuery));
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());
							
			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,FundAccountQueryBO.class);
			if(list!=null  && list.size()>0){
				bo=(FundAccountQueryBO) list.get(0);
			}

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return bo;	
		
	}
	
	/**
	 * 信托产品撤单
	 * @param productCode 产品代码
	 * @param productTaNo TA代码
	 * @param allotNo 申请编号
	 * @param bindUser 登录用户
	 * @return
	 */
	public static TrustOrderCancleBO trustOrderCancle(String productCode,
			String productTaNo,String allotNo,BindUserResultVO bindUser){
		TrustOrderCancleBO bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_trustOrderCancle, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();		
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			if(bindUser.getOpStation()!=null){
				//站点|电话
				map.put("op_station", bindUser.getOpStation());
			}
			if(bindUser.getBranchNo()!=null){
				//分支编号，股民所在营业部号
				map.put("branch_no", bindUser.getBranchNo());
			}
			//客户号
			map.put("client_id", bindUser.getCustomerCode());
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());
			//产品代码
			map.put("prod_code", productCode);
			//TA编码
			map.put("prodta_no", productTaNo);
			//申请编号
			map.put("allot_no", allotNo);

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,TrustOrderCancleBO.class);
			if(list!=null  && list.size()>0){
				bo=(TrustOrderCancleBO) list.get(0);
			}

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return bo;		
	}
	
	/**
	 * 基金赎回
	 * @return
	 */
	public static FundRedemption fundRedemption(String fund_code,String fund_company,
			Double amount,BindUserResultVO bindUser){
		FundRedemption bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_fundRedemption, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();
			map.put("version", 4);
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			if(bindUser.getOpStation()!=null){
				//站点|电话
				map.put("op_station", bindUser.getOpStation());
			}
			if(bindUser.getBranchNo()!=null){
				//分支编号，股民所在营业部号
				map.put("branch_no", bindUser.getBranchNo());
			}
			map.put("function_id", Integer.parseInt(functionNo.function_fundRedemption));
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());
			//基金产品代码
			map.put("fund_code", fund_code);
			//基金公司对应编码
			map.put("fund_company", fund_company);
			//赎回数量,N16.2
			map.put("amount", amount);

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,FundRedemption.class);
			if(list!=null  && list.size()>0){
				bo=(FundRedemption) list.get(0);
			}

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return bo;			
	}
	
	/**
	 * 基金委托撤单
	 * @return
	 */
	public static FundOrderCancleBO fundOrderCancle(String fund_code,String fund_company,
			String allotNo,BindUserResultVO bindUser){
		FundOrderCancleBO bo = null;
		try
		{  
			//定义功能号
			IEvent event = ContextUtil.getServiceContext().getEventFactory()
					.getEventByAlias(functionNo.function_fundOrderCancle, EventType.ET_REQUEST);
			MapWriter map = new MapWriter();
			map.put("version", 4);
			//操作员分支代码
			map.put("op_branch_no", op_branch_no);
			//委托方式
			map.put("op_entrust_way", op_entrust_way);
			if(bindUser.getOpStation()!=null){
				//站点|电话
				map.put("op_station", bindUser.getOpStation());
			}
			if(bindUser.getBranchNo()!=null){
				//分支编号，股民所在营业部号
				map.put("branch_no", bindUser.getBranchNo());
			}
			map.put("function_id", Integer.parseInt(functionNo.function_fundOrderCancle));
			//资金账号
			map.put("fund_account", bindUser.getFundAccount());
			//交易密码
			map.put("password",bindUser.getPassword());
			//基金产品代码
			map.put("fund_code", fund_code);
			//基金公司对应编码
			map.put("fund_company", fund_company);
			//赎回数量,N16.2
			map.put("allotno", allotNo);

			event.putEventData(map.getDataset());

			//发送并接收返回
			IEvent rsp = client.sendReceive(event,10000);
			//输出查询值
			List<Object> list = InterfaceServiceHelper
					.outputAnEvent(rsp,FundOrderCancleBO.class);
			if(list!=null  && list.size()>0){
				bo=(FundOrderCancleBO) list.get(0);
			}

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return bo;			
	}
	
	




}
