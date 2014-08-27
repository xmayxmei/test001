/**
 * 
 */
package com.cfwx.rox.businesssync.market.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.cfwx.rox.businesssync.market.show.DayLine;

/**
 * @author J.C.J
 *
 * 2013-12-4
 */
public class IOUtils {
	
	private final static Logger LOG = Logger.getLogger(IOUtils.class);
	
	private static NumberFormat nf = NumberFormat.getInstance();
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	public  static HashMap<String,LinkedList<DayLine>> readSHDayLineFile(String DirectoryUrl,int size){
		
		HashMap<String,LinkedList<DayLine>> map = null;
		
		try {
			File file = new File(DirectoryUrl);
			
			if(file.exists() && file.isDirectory()){
				map = new HashMap<String,LinkedList<DayLine>>();
				File[] files = file.listFiles();
				String tempFile = "";
				LinkedList<DayLine> dayLineList = null;
				String tempCode = "";
				for(File f : files){
					tempFile = DirectoryUrl+"/"+f.getName();
					dayLineList = readOne(tempFile,size);
					if(dayLineList!= null){
						tempCode = "sh"+getCode(f.getName());
						map.put(tempCode, dayLineList);
					}
				}
			}else{
				//文件不存在,或者不是目录
				LOG.error(DirectoryUrl+"：文件不存在或者不是目录");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return map;
	}
	
	public static HashMap<String,LinkedList<DayLine>> readSZDayLineFile(String DirectoryUrl,int size){
		HashMap<String,LinkedList<DayLine>> map = null;
		
		try {
			File file = new File(DirectoryUrl);
			
			if(file.exists() && file.isDirectory()){
				
				map = new HashMap<String,LinkedList<DayLine>>();
				File[] files = file.listFiles();
				
				String tempFile = "";
				LinkedList<DayLine> dayLineList = null;
				String tempCode = "";
				
				for(File f : files){
					
					tempFile = DirectoryUrl+"/"+f.getName();
					dayLineList = readOne(tempFile,size);
					
					if(dayLineList!= null){
						tempCode = "sz"+getCode(f.getName());
						map.put(tempCode, dayLineList);
					}
				}
			}else{
				//文件不存在,或者不是目录
				LOG.error(DirectoryUrl+"：文件不存在或者不是目录");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
		}
		return map;
	}
	
	/**
	 * @param args
	 */
	public static LinkedList<DayLine> readOne(String fileUrl,int size) {
		
		setNumberFormat();
		
		LinkedList<DayLine> dayLineList = null;
		
		File file = new File(fileUrl);
		if (file.exists()) {
			dayLineList = new LinkedList<DayLine>();
			FileInputStream in = null;
			DataInputStream dis = null;
			
			try {
				 in = new FileInputStream(file);
				 dis = new DataInputStream(in);

				byte[] itemBuf = new byte[20];
					DayLine dl = null;
					while (dis.read(itemBuf, 0, 4) != -1) {
						dl = new DayLine();
						// 日期
						int sDate = byte2int(itemBuf);
						
						dl.setTime(Long.toString(sdf.parse(Integer.toString(sDate)).getTime()));

						// 开盘x1000
						dis.read(itemBuf, 0, 4);
						int sOpen = byte2int(itemBuf);
						dl.setOpen(nf.format(sOpen/1000D));

						// 最高x1000
						dis.read(itemBuf, 0, 4);
						int sHigh = byte2int(itemBuf);
						dl.setHigh(nf.format(sHigh/1000D));

						// 最低x1000
						dis.read(itemBuf, 0, 4);
						int sLow = byte2int(itemBuf);
						dl.setLow(nf.format(sLow/1000D));

						// 收盘x1000
						dis.read(itemBuf, 0, 4);
						int sClose = byte2int(itemBuf);
						dl.setClose(nf.format(sClose/1000D));

						// 成交金额（千元）
						dis.read(itemBuf, 0, 4);
						int sAmount = byte2int(itemBuf);
						dl.setAmount(nf.format(sAmount*1000D));

						// 成交量（百股）
						dis.read(itemBuf, 0, 4);
						int sVolume = byte2int(itemBuf);
						dl.setVolume(nf.format(sVolume));
						
						//还剩下12字节，共40字节
						dis.read(itemBuf, 0, 12);
						
						if(dayLineList.size()==size){
							dayLineList.removeFirst();
						}
						dayLineList.add(dl);
					}
			} catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			} 
			finally {
				try {
					dis.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return  dayLineList;
	}
	
	private static String getCode(String fileName)throws Exception{
		String resStr = "";
		try {
			if(!fileName.trim().equals("")){
				resStr= fileName.split("\\.")[0];
			}
		} catch (Exception e) {
			System.out.println(resStr);
			e.printStackTrace();
		}
		return resStr;
	}

//	private static byte[] int2byte(int res) {
//		byte[] targets = new byte[4];
//
//		targets[0] = (byte) (res & 0xff);// 最低位 
//		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位 
//		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位 
//		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。 
//		return targets; 
//	} 

	private static int byte2int(byte[] res) {
		// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
				| ((res[2] << 24) >>> 8) | (res[3] << 24);
		return targets;
	}
	
	private static void setNumberFormat(){
		nf.setMaximumFractionDigits(3);
		nf.setMinimumFractionDigits(3);
		nf.setGroupingUsed(false);
	} 
}
