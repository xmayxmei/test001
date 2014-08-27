package com;

import java.util.List;

import bo.BindUserResultVO;
import bo.ClientLoginResultBO;
import bo.CustomerInfoBO;
import bo.MyDealBackQueryBO;
import bo.MyFundJourQueryBO;
import bo.MyFundQueryBO;
import bo.MyStockQueryBO;
import bo.TrustProductGouBO;
import bo.TrustProductQueryBO;

import com.service.ShoppingInterface;
import com.service.ZheShangT2Interface;

public class Main {

	public static void main(String args[]){

		ServiceInterface.start();
		if(args.length<=0){
			System.out.println("调用示例：\n查询客户信息： java -jar T2.jar 0 110000550 123456 " 
		+"\n查询资金 java -jar T2.jar 1 110000550 1 13005017141"
		+ "\n查询股票 java -jar T2.jar 2 110000550 1 13005017141"
		+ "\n查询成交 java -jar T2.jar 3 110000550 1 13005017141"
		+ "\n查询资金流水 java -jar T2.jar 4 110000550 1 13005017141"
		+ "\n查询信托产品  java -jar T2.jar 5 可加产品代码"
		);
	    ServiceInterface.stop();
			return;
		}
		//执行示例：java -jar T2.jar 0 110000550 123456	
		//         java -jar T2.jar 1 110000550 123456	1		
		String opStation = "1234567890";
		String functionId = args[0];
		String branch_no =null;
		String op_station =null;

		try{
			if(functionId.equals("0")){
				ClientLoginResultBO bo = ZheShangT2Interface.login(args[1],args[2],opStation);
				if(bo!=null){
					System.out.println(bo.getBranchNo());
				}
				System.out.println("客户信息查询开始+++++++++++++++++++++++++++++++++++++++");
				CustomerInfoBO cbo = ZheShangT2Interface.queryCustomerInfo(args[1],args[2],bo.getBranchNo());
				System.out.println("客户信息++++++++++++++++++++++++++++\n"+cbo.getFundCard()+"\n"+cbo.getBirthday());
			}

			if(functionId.equals("1")){
				System.out.println("\n\n资金信息查询开始++++++++++++++++++++++++++++++++++++++++");
				if(args[2]!="#"){
					branch_no=args[2];
				}
				if(args[3]!="#"){
					op_station=args[3];
				}
				List<MyFundQueryBO> mboList = ZheShangT2Interface.getMyFund(args[1],branch_no,op_station);
				for(MyFundQueryBO mbo:mboList){
					System.out.println("资金信息++++++++++++++++++++++++++++\n"+mbo.getEnableBalance()+"\n"+mbo.getCurrentBalance());
				}
			}

			if(functionId.equals("2")){
				System.out.println("\n\n股票信息查询开始++++++++++++++++++++++++++++++++++++++++");
				if(args[2]!="#"){
					branch_no=args[2];
				}
				if(args[3]!="#"){
					op_station=args[3];
				}
				List<MyStockQueryBO> stockList = ZheShangT2Interface.getMyStock(args[1],branch_no,op_station);
				for(MyStockQueryBO mbo:stockList){
					System.out.println("股票信息++++++++++++++++++++++++++++\n"+mbo.getAvBuyPrice()+"\n"+mbo.getAvIncomeBalance());
				}
			}


			if(functionId.equals("3")){
				System.out.println("\n\n成交查询开始++++++++++++++++++++++++++++++++++++++++");
				if(args[2]!="#"){
					branch_no=args[2];
				}
				if(args[3]!="#"){
					op_station=args[3];
				}
				List<MyDealBackQueryBO> dealList = ZheShangT2Interface.getMyDealBack(args[1],branch_no,op_station);
				for(MyDealBackQueryBO wbo:dealList){
					System.out.println("成交信息++++++++++++++++++++++++++++\n"+wbo.getBusinessAmount());
				}
			}

			if(functionId.equals("4")){
				System.out.println("\n\n资金流水查询开始++++++++++++++++++++++++++++++++++++++++");
				if(args[2]!="#"){
					branch_no=args[2];
				}
				if(args[3]!="#"){
					op_station=args[3];
				}
				List<MyFundJourQueryBO> weituoList = ZheShangT2Interface.getMyFundJour(args[1],branch_no,op_station);
				for(MyFundJourQueryBO wbo:weituoList){
					System.out.println("资金流水信息++++++++++++++++++++++++++++\n"+wbo.getBusinessName());
				}
			}

			if(functionId.equals("5")){
					
				System.out.println("\n\n信托产品查询开始++++++++++++++++++++++++++++++++++++++++");
				String productCode = null;
				String productTaNo = null;
				if(args.length>1){
					productCode = args[1];

				}if(args.length>2){		
					productTaNo = args[2];
				}			
				List<TrustProductQueryBO> list = ShoppingInterface
						.queryTrustProduct("0",opStation,productCode,productTaNo);
				if(list!=null){
					TrustProductQueryBO trustbo = list.get(0);
					System.out.println("产品TA编码:"+trustbo.getProdtaNo());					
				}
			}

			if(functionId.equals("6")){
				System.out.println("\n\n信托产品预约认购开始++++++++++++++++++++++++++++++++++++++++");
				BindUserResultVO vo = new BindUserResultVO();
				vo.setBranchNo("0");
				vo.setOpStation(opStation);
				vo.setCustomerCode(args[1]);
				vo.setFundAccount(args[2]);
				vo.setPassword(args[3]);
				String productCode = args[4];
				String productTaNo = args[5];
				String balance = args[6];
				TrustProductGouBO trustProduct = ShoppingInterface.yuyueRengou(productCode, productTaNo, balance, vo);
				System.out.println(trustProduct!=null?trustProduct.getSerialNo():"无结果返回");
			}

			if(functionId.equals("7")){
				System.out.println("\n\n信托产品认购开始++++++++++++++++++++++++++++++++++++++++");
				BindUserResultVO vo = new BindUserResultVO();
				vo.setBranchNo("0");
				vo.setOpStation(opStation);
				vo.setCustomerCode(args[1]);
				vo.setFundAccount(args[2]);
				vo.setPassword(args[3]);
				String productCode = args[4];
				String productTaNo = args[5];
				String balance = args[6];
				TrustProductGouBO trustProduct = ShoppingInterface.trustRengou(productCode, productTaNo, balance, vo);
				System.out.println(trustProduct!=null?trustProduct.getSerialNo():"无结果返回");
			}		

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ServiceInterface.stop();
		}

	}

}
