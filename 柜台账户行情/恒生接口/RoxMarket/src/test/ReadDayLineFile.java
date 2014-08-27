package test;

import java.io.*;
import java.text.NumberFormat;

public class ReadDayLineFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);
		nf.setMinimumFractionDigits(3);
		nf.setGroupingUsed(false);
//		File file = new File("D://history/SHASE/day/000001.day");
		File file = new File("D://Program Files/qianlong/qijian/QLDATA/history/SZNSE/day/399634.day");
		if (file.exists()) {

			FileInputStream in = null;
			DataInputStream dis =null;
			
			try {
				 in = new FileInputStream(file);
				 dis = new DataInputStream(in);

				byte[] itemBuf = new byte[20];
					while (dis.read(itemBuf, 0, 4) != -1) {
						// 日期
						int sDate = byte2int(itemBuf);

						// 开盘指数x1000
						dis.read(itemBuf, 0, 4);
						int sOpen = byte2int(itemBuf);

						// 最高指数x1000
						dis.read(itemBuf, 0, 4);
						int sHigh = byte2int(itemBuf);

						// 最低指数x1000
						dis.read(itemBuf, 0, 4);
						int sLow = byte2int(itemBuf);

						// 收盘指数x1000
						dis.read(itemBuf, 0, 4);
						int sClose = byte2int(itemBuf);

						// 成交金额（千元）
						dis.read(itemBuf, 0, 4);
						int sAmount = byte2int(itemBuf);

						// 成交量（百股）
						dis.read(itemBuf, 0, 4);
						int sVolume = byte2int(itemBuf);
						
						//还剩下12字节，共40字节
						dis.read(itemBuf, 0, 12);

						System.out.print("日期:" + sDate + ",");
						System.out.print("开盘:" + nf.format(sOpen) + ",");
						System.out.print("最高:" + Integer.toString(sHigh) + ",");
						System.out.print("最低:" + Integer.toString(sLow) + ",");
						System.out.print("收盘:" + Integer.toString(sClose) + ",");
						System.out.print("金额:" + Integer.toString(sAmount)+ ",");
						System.out.print("成交量:" + Integer.toString(sVolume)+ "\n");
					}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					// close
					dis.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			System.out.println("文件不存在...");
		}
	}

	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];

		targets[0] = (byte) (res & 0xff);// 最低位 
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位 
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位 
		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。 
		return targets; 
	} 

	public static int byte2int(byte[] res) {
		// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
				| ((res[2] << 24) >>> 8) | (res[3] << 24);
		return targets;
	}
}
