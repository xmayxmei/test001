/**
 * 
 */
package test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.cfwx.rox.businesssync.market.dao.impl.QuoteHistoryMySqlImpl;
import com.cfwx.rox.businesssync.market.show.MinLine;
import com.cfwx.util.U;

/**
 * @author Jimmy
 * 
 *         2014-6-13
 */
public class Min5HistoryData {
	static private DataSource ds;
	static final ArrayBlockingQueue<FileHolder> queue = new ArrayBlockingQueue<FileHolder>(2048);
	static QuoteHistoryMySqlImpl mDao;
	private static DecimalFormat nf = new DecimalFormat("#0.000");
	
	static final int iWorkerCount = 10;
	static {
		Properties oProp = new Properties();
		oProp.setProperty("driverClassName", "com.mysql.jdbc.Driver");
		oProp.setProperty("url", "jdbc:mysql://192.168.0.222:3306/quote");
		oProp.setProperty("username", "root");
		oProp.setProperty("password", "cfwx2014");
		try {
			ds = BasicDataSourceFactory.createDataSource(oProp);
			mDao = new QuoteHistoryMySqlImpl((BasicDataSource)ds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < iWorkerCount; i++) {
			new Thread(new Worker()).start();
		}
	}
	
	static class Worker implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			while (true) {
				try {
					FileHolder oHolder = queue.take();
					persist(oHolder);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void exportData(String sReadBasePath, String sPrefix) {
		File oFileRead = new File(sReadBasePath);
		if (!oFileRead.exists() || !oFileRead.isDirectory()) {
			return;
		}
		File[] aFiles = oFileRead.listFiles();
		int iLen = aFiles.length;
		for (int i = 0; i < iLen; i++) {
			FileHolder oHolder = new FileHolder();
			oHolder.oFile = aFiles[i];
			oHolder.sPrefix = sPrefix;
			try {
				queue.put(oHolder);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class FileHolder {
		String sPrefix;
		File oFile;
	}

	public static void persist(FileHolder oHolder) throws ParseException {
		File oFileFrom = oHolder.oFile;
		String sName = oFileFrom.getName();
		String sCode = /*oHolder.sPrefix +*/ sName.substring(0, sName.lastIndexOf('.'));
		
		System.err.println("开始转换、保存5分线数据:" + sCode);
		List<MinLine> lstDays = getMinData(oFileFrom);
		int iLen = lstDays.size();
		for (int ii = 0; ii < iLen; ii++) {
			mDao.save5MinData(sCode, lstDays.get(ii));
//			mDao.save10MinData(sCode, lstDays.get(ii));
//			mDao.save15MinData(sCode, lstDays.get(ii));
//			mDao.save30MinData(sCode, lstDays.get(ii));
//			mDao.save60MinData(sCode, lstDays.get(ii));
		}
		{
			System.err.println("开始转换、保存15分线数据:" + sCode);
			List<MinLine> min15 = generateNBy5Min(lstDays, 15);
			for (int i = 0; i < min15.size(); i++) {
				mDao.save15MinData(sCode, min15.get(i));
			}
		}
		
		{
			System.err.println("开始转换、保存30分线数据:" + sCode);
			List<MinLine> min30 = generateNBy5Min(lstDays, 30);
			for (int i = 0; i < min30.size(); i++) {
				mDao.save30MinData(sCode, min30.get(i));
			}
		}
		
		{
			System.err.println("开始转换、保存60分线数据:" + sCode);
			List<MinLine> min60 = generateNBy5Min(lstDays, 60);
			for (int i = 0; i < min60.size(); i++) {
				mDao.save60MinData(sCode, min60.get(i));
			}
		}
	}
	public static List<MinLine> getMinData(File oFileFrom) throws ParseException {
		FileInputStream in = null;
		DataInputStream dis = null;
		List<MinLine> lst = new ArrayList<MinLine>();
		try {
			in = new FileInputStream(oFileFrom);
			dis = new DataInputStream(in);
			byte[] itemBuf = new byte[32];
			while (dis.read(itemBuf, 0, 2) != -1) {
				// 日期
				int num = byte2int(itemBuf);
				int year=(int)Math.floor(num/2048)+2004;
				int month=(int)Math.floor((num % 2048)/100);
				int day=((num % 2048) % 100);
				dis.read(itemBuf, 0, 2);
				int min = byte2int(itemBuf);
				
				Calendar cld = Calendar.getInstance();
				cld.set(Calendar.YEAR, year);
				cld.set(Calendar.MONDAY, month);
				cld.set(Calendar.DAY_OF_MONTH, day);
				cld.set(Calendar.MINUTE, min);
				cld.set(Calendar.HOUR_OF_DAY, 0);
				cld.set(Calendar.SECOND, 0);
				// 开盘指数x1000
				itemBuf = new byte[4];
				dis.read(itemBuf, 0, 4);
				float sOpen = U.BYTE.byte2float(itemBuf, 0);
				itemBuf = new byte[4];
				// 最高指数x1000
				dis.read(itemBuf, 0, 4);
				float sHigh = U.BYTE.byte2float(itemBuf, 0);
				// 最低指数x1000
				dis.read(itemBuf, 0, 4);
				float sLow = U.BYTE.byte2float(itemBuf, 0);
				// 收盘指数x1000
				dis.read(itemBuf, 0, 4);
				float sClose = U.BYTE.byte2float(itemBuf, 0);
				// 成交金额（千元）
				dis.read(itemBuf, 0, 4);
				float sAmount = U.BYTE.byte2float(itemBuf, 0);
				// 成交量（百股）
				dis.read(itemBuf, 0, 4);
				int sVolume = byte2int(itemBuf);
				// 还剩下12字节，共40字节
				dis.read(itemBuf, 0, 4);
				MinLine oDay = new MinLine();
				oDay.setTime(String.valueOf(cld.getTime().getTime()));
				oDay.setDate(String.format("%02d", year)+ "-" + String.format("%02d", month) + "-" +String.format("%02d", day));
				oDay.setDateTime(new SimpleDateFormat("HH:mm:ss").format(cld.getTime()));
				oDay.setOpen(nf.format(sOpen));
				oDay.setClose(nf.format(sClose ));
				oDay.setHigh(nf.format(sHigh ));
				oDay.setLow(nf.format(sLow ));
				oDay.setHigh(nf.format(sHigh ));
				oDay.setAmount(nf.format(sAmount));
				oDay.setVolume(nf.format(sVolume / 100));
				lst.add(oDay);
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
		return lst;
	}
	
	
	public static List<MinLine> generateNBy5Min(List<MinLine> list5Mins, int iMins) throws ParseException {
	      List<MinLine> lstMinsN = new ArrayList<MinLine>();
	      int iLen = list5Mins.size();
	      int iStart = 0;
	      int iCount = iMins / 5;
	      for (int i = 0; i < iLen; i++) {
	        iStart = i;
	        int iEnd = iStart;
	        for (; iEnd < iLen - 1; iEnd++) {
	          if (!list5Mins.get(iEnd).getDate().equals(list5Mins.get(iEnd + 1).getDate())) {
	            break;
	          }
	        }
	        i = iEnd;
	        
	        for (int ii = iStart; ii < iEnd; ii++) {
	        	int iiStart = ii;
	        	MinLine mins = deepCopy(list5Mins.get(iiStart));
	        	for (iiStart++;iiStart < ii + iCount && iiStart < iEnd; iiStart++) {
	        		MinLine oMinNew = list5Mins.get(iiStart);
	        		double dAmount = Double.parseDouble(mins.getAmount()) + Double.parseDouble(oMinNew.getAmount());
	        		double dVolume = Double.parseDouble(mins.getVolume()) + Double.parseDouble(oMinNew.getVolume());
	        		double dHigh =  Math.max(Double.parseDouble(mins.getHigh()), Double.parseDouble(oMinNew.getHigh()));
	        		double dLow =  Math.min(Double.parseDouble(mins.getLow()), Double.parseDouble(oMinNew.getLow()));
	        		mins.setLow(String.valueOf(dLow));
	        		mins.setHigh(String.valueOf(dHigh));
	        		mins.setAmount(String.valueOf(dAmount));
	        		mins.setClose(oMinNew.getClose());
	        		mins.setDate(oMinNew.getDate());
	        		mins.setDateTime(oMinNew.getDateTime());
	        		mins.setTime(oMinNew.getTime());
	        		mins.setVolume(String.valueOf(dVolume));
	        	}
	        	ii = iiStart - 1;
	        	lstMinsN.add(mins);
	        }
	      }
	      return lstMinsN;
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
	
	public static MinLine deepCopy(MinLine mins){
		MinLine minNew = new MinLine();
		minNew.setAmount(mins.getAmount());
		minNew.setClose(mins.getClose());
		minNew.setDate(mins.getDate());
		minNew.setDateTime(mins.getDateTime());
		minNew.setHigh(mins.getHigh());
		minNew.setLow(mins.getLow());
		minNew.setOpen(mins.getOpen());
		minNew.setTime(mins.getTime());
		minNew.setVolume(mins.getVolume());
		return minNew;
	}

	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				exportData("D:\\new_tdx\\vipdoc\\sz\\fzline", "sh");
			}
		}).start();
		exportData("D:\\new_tdx\\vipdoc\\sh\\fzline", "sh");
	}
}
