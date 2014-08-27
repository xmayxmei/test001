/**
 * 
 */
package test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 把钱龙的历史数据保存到本地
 */
public class DataInit {

  public static void exportData(String sReadBasePath, String sWriteBasePath, String sPrefix) {
    // File file = new File("D://history/SHASE/day/000001.day");
    File oFileRead = new File(sReadBasePath);
    if (!oFileRead.exists() || !oFileRead.isDirectory()) {
      return;
    }
    File oFileWrite = new File(sWriteBasePath);
    if (!oFileWrite.exists()) {
      oFileWrite.mkdirs();
    }
    
    File[] aFiles = oFileRead.listFiles();
    int iLen = aFiles.length;
    for (int i = 0; i < iLen; i++) {
      File oFileFrom = aFiles[i];
      String sName = oFileFrom.getName();
      File oFileTo = new File(sWriteBasePath + File.separator + sPrefix + sName);
      if (!oFileTo.exists()) {
        try {
          oFileTo.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      
      export(oFileFrom, oFileTo);
    }
  }
  
  public static void export(File oFileFrom, File oFileTo) {
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMaximumFractionDigits(3);
    nf.setMinimumFractionDigits(3);
    nf.setGroupingUsed(false);
    
    FileInputStream in = null;
    DataInputStream dis = null;
    FileWriter fw = null;
    try {
      in = new FileInputStream(oFileFrom);
      dis = new DataInputStream(in);
      fw = new FileWriter(oFileTo,true);
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
        // 还剩下12字节，共40字节
        dis.read(itemBuf, 0, 12);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        long lTime = 0;
        try {
          lTime = sdf.parse(String.valueOf(sDate)).getTime();
        } catch (ParseException e) {
          e.printStackTrace();
        }
        
        StringBuffer content = new StringBuffer();
        content.append(lTime).append(",");
        content.append(sOpen / 1000f).append(",");
        content.append(sClose / 1000f).append(",");
        content.append(sVolume ).append(",");
        content.append(sHigh / 1000f).append(",");
        content.append(sLow / 1000f).append(",");
        content.append(sAmount);
        content.append("\r\n");
        
        fw.write(content.toString());
        
        System.out.print("日期:" + sDate + ",");
        System.out.print("开盘:" + nf.format(sOpen / 1000F) + ",");
        System.out.print("最高:" + (sHigh / 1000.0f )+ ",");
        System.out.print("最低:" + Float.toString(sLow / 1000f) + ",");
        System.out.print("收盘:" + Float.toString(sClose / 1000f) + ",");
        System.out.print("金额:" + Float.toString(sAmount / 1000f) + ",");
        System.out.print("成交量:" + Float.toString(sVolume / 1000f) + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        // close
        dis.close();
        in.close();
        fw.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
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

  public static void main(String[] args) {
    exportData("C:\\qianlong\\qijian\\QLDATA\\history\\SHASE\\day", "D:\\dbfserver\\marketcache\\day", "sh");
    exportData("C:\\qianlong\\qijian\\QLDATA\\history\\SZNSE\\day", "D:\\dbfserver\\marketcache\\day", "sz");
  }
}
