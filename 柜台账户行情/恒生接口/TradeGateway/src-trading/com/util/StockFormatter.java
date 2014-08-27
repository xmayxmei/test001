package com.util;
/**
 * @author caspa.he
 * @Date 2009-5-26
 * @version 1.0
 */
public class StockFormatter {
	public static String conversionToStandard(String stock){
		//if(stock.length<5){
			stock = "00000" + stock;
			stock = stock.substring(stock.length() - 5, stock.length());
			return stock;
		//}
//		String stockcode="00000".concat(stock[2]);
//		String code="00000";
//		if(stock[0].equals("HK")){
//			code= stockcode.substring(stockcode.length()-5);
//			
//		}else if(stock[0].equals("CN")){
//			code= stockcode.substring(stockcode.length()-6);
//		}
//		return code;
	}
	public static String conversionToStandardHK(String stockarr){
		String [] str = stockarr.split(";");
		if(str.length==0) return null;
		String stock = str[str.length-1];
		
			stock = conversionToStandard(stock);
			
			return stock;
	
	}
	public static String conversionToAAdefined(String stockcode){
		String stockName="";
		int i=stockcode.lastIndexOf("0");
		int value=Integer.parseInt(stockcode.substring(0,i));
		if(value>0){
			for(int s=0;s<stockcode.length();s++){
				if(value>=Math.pow(10,s) && value<Math.pow(10,s+1)){
					i=i-s-2;
				}
			}
		}
		System.out.println("i:"+i);
		if(stockcode.length()==5){
			stockName="HK;H;"+stockcode.substring(i+1);
			
		}else if(stockcode.length()==6){
			stockName="CN;SH;"+stockcode.substring(i+1);
		}
		return stockName;
	}
	public static void main(String args[]){
		System.out.println("reslut:"+StockFormatter.conversionToAAdefined("000001"));
	}
}
