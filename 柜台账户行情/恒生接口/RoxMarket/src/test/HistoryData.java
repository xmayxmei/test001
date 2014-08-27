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
import com.cfwx.rox.businesssync.market.show.CommonLine;
import com.cfwx.rox.businesssync.market.show.DayLine;
import com.cfwx.util.U;

/**
 * @author Jimmy
 * 
 *         2014-6-13
 */
public class HistoryData {
	static private DataSource ds;
	static final ArrayBlockingQueue<FileHolder> queue = new ArrayBlockingQueue<FileHolder>(2048);
	static QuoteHistoryMySqlImpl mDao;
	private static DecimalFormat nf = new DecimalFormat("#0.000");
	
	private static final int POINTS = 120;
	
	static final int iWorkerCount = 10;
	static {
		Properties oProp = new Properties();
		oProp.setProperty("driverClassName", "com.mysql.jdbc.Driver");
		oProp.setProperty("url", "jdbc:mysql://10.51.82.150:3306/quote");
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

	public static void persist(FileHolder oHolder) {
		File oFileFrom = oHolder.oFile;
		String sName = oFileFrom.getName();
		String sCode = oHolder.sPrefix + sName.substring(0, sName.lastIndexOf('.'));
		
		System.err.println("开始转换、保存日线数据:" + sCode);
		List<DayLine> lstDays = getDailyData(oFileFrom);
		// save daily 
		{
			int iLen = lstDays.size();
			int ii = iLen - POINTS;
			ii = ii < 0 ? 0 : ii;
			for (; ii < iLen; ii++) {
				mDao.saveDailyData(sCode, lstDays.get(ii));
			}
		}
		// save weekly
		try {
			System.err.println("开始生成、保存周线数据:" + sCode);
			List<CommonLine> lstWeeks = generateWeekly(lstDays);
			int iLen = lstWeeks.size();
			int ii = iLen - POINTS;
			ii = ii < 0 ? 0 : ii;
			for (; ii < iLen; ii++) {
				mDao.saveWeeklyData(sCode, lstWeeks.get(ii));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// save monthly
		try {
			System.err.println("开始生成、保存月线数据:" + sCode);
			List<CommonLine> lstMonths = generateMonthly(lstDays);
			int iLen = lstMonths.size();
			int ii = iLen - POINTS;
			ii = ii < 0 ? 0 : ii;
			for (; ii < iLen; ii++) {
				mDao.saveMonthlyData(sCode, lstMonths.get(ii));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// save yearly
		/*try {
			System.err.println("开始生成、保存年线数据:" + sCode);
			List<CommonLine> lstYearly = generateYearly(lstDays);
			int iLen = lstYearly.size();
			int ii = iLen - POINTS;
			ii = ii < 0 ? 0 : ii;
			for (; ii < iLen; ii++) {
				mDao.saveYearlyData(sCode, lstYearly.get(ii));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
	}
	public static List<DayLine> getDailyData(File oFileFrom) {
		FileInputStream in = null;
		DataInputStream dis = null;
		List<DayLine> lst = new ArrayList<DayLine>();
		try {
			in = new FileInputStream(oFileFrom);
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
				// 还剩下12字节，共40字节
				dis.read(itemBuf, 0, 12);

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				long lTime = 0;
				try {
					lTime = sdf.parse(String.valueOf(sDate)).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				DayLine oDay = new DayLine();
				oDay.setTime(String.valueOf(lTime));
				oDay.setOpen(nf.format(sOpen / 1000D));
				oDay.setClose(nf.format(sClose / 1000D));
				oDay.setHigh(nf.format(sHigh / 1000D));
				oDay.setLow(nf.format(sLow / 1000D));
				oDay.setHigh(nf.format(sHigh / 1000D));
				oDay.setAmount(String.valueOf(sAmount * 1000));
				oDay.setVolume(String.valueOf(sVolume));
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
	
	public static List<CommonLine> generateWeekly(List<DayLine> listDaily) throws ParseException {
		// 周K
	      List<CommonLine> lstWeekly = new ArrayList<CommonLine>();
	      int iLen = listDaily.size();
	      int iStart = 0;
	      for (int i = 0; i < iLen; i++) {
	        iStart = i;
	        String sOpen = listDaily.get(iStart).getOpen();
	        Long lAmount = Long.parseLong(listDaily.get(iStart).getAmount());
	        Long lVolume = Long.parseLong(listDaily.get(iStart).getVolume());
	        Double fHigh = Double.parseDouble(listDaily.get(iStart).getHigh());
	        Double fLow = Double.parseDouble(listDaily.get(iStart).getLow());
	        int iEnd = iStart;
	        for (; iEnd < iLen - 1; iEnd++) {
	          DayLine oDailyLine = listDaily.get(iEnd);
	          Calendar oCldPrev = Calendar.getInstance();
	          oCldPrev.setTimeInMillis(Long.parseLong(oDailyLine.getTime()));
	          int iPrevWeek = oCldPrev.get(Calendar.WEEK_OF_YEAR);

	          DayLine oDailyLineNext = listDaily.get(iEnd + 1);
	          Calendar oCldNext = Calendar.getInstance();
	          oCldNext.setTimeInMillis(Long.parseLong(oDailyLineNext.getTime()));
	          int iNextWeek = oCldNext.get(Calendar.WEEK_OF_YEAR);

	          if (iPrevWeek != iNextWeek) {
	            break;
	          }
	          lAmount += Long.parseLong(oDailyLineNext.getAmount());
	          lVolume += Long.parseLong(oDailyLineNext.getVolume());

	          Double fHighNext = Double.parseDouble(oDailyLineNext.getHigh());
	          Double fLowNext = Double.parseDouble(oDailyLineNext.getLow());

	          fHigh = Math.max(fHigh, fHighNext);
	          fLow = Math.min(fLow, fLowNext);
	        }


	        String sClose = listDaily.get(iEnd).getClose();
	        String sTime = listDaily.get(iEnd).getTime();
	        i = iEnd;
	        
	        CommonLine weekLine = new CommonLine();
	        weekLine.setFromDate(U.DT.formatTime(listDaily.get(iStart).getTime()));
	        weekLine.setTime(sTime);
	        weekLine.setToDate(U.DT.formatTime(sTime));
	        weekLine.setOpen(nf.format(Double.parseDouble(sOpen)));
	        weekLine.setClose(nf.format(Double.parseDouble(sClose)));
	        weekLine.setAmount(String.valueOf(lAmount));
	        weekLine.setVolume(String.valueOf(lVolume));
	        weekLine.setHigh(nf.format(fHigh));
	        weekLine.setLow(nf.format(fLow));
	        lstWeekly.add(weekLine);
	      }
	      
	      return lstWeekly;
	}
	
	public static List<CommonLine> generateMonthly(List<DayLine> listDaily) throws ParseException {
	      // 周K
	      List<CommonLine> lstMonthly = new ArrayList<CommonLine>();
	      int iLen = listDaily.size();
	      int iStart = 0;
	      for (int i = 0; i < iLen; i++) {
	        iStart = i;
	        String sOpen = listDaily.get(iStart).getOpen();
	        Long lAmount = Long.parseLong(listDaily.get(iStart).getAmount());
	        Long lVolume = Long.parseLong(listDaily.get(iStart).getVolume());
	        Double fHigh = Double.parseDouble(listDaily.get(iStart).getHigh());
	        Double fLow = Double.parseDouble(listDaily.get(iStart).getLow());
	        int iEnd = iStart;
	        for (; iEnd < iLen - 1; iEnd++) {
	          DayLine oDailyLine = listDaily.get(iEnd);
	          Calendar oCldPrev = Calendar.getInstance();
	          oCldPrev.setTimeInMillis(Long.parseLong(oDailyLine.getTime()));
	          int iPrevMonth = oCldPrev.get(Calendar.MONTH);

	          DayLine oDailyLineNext = listDaily.get(iEnd + 1);
	          Calendar oCldNext = Calendar.getInstance();
	          oCldNext.setTimeInMillis(Long.parseLong(oDailyLineNext.getTime()));
	          int iNextMonth = oCldNext.get(Calendar.MONTH);

	          if (iPrevMonth != iNextMonth) {
	            break;
	          }
	          lAmount += Long.parseLong(oDailyLineNext.getAmount());
	          lVolume += Long.parseLong(oDailyLineNext.getVolume());

	          Double fHighNext = Double.parseDouble(oDailyLineNext.getHigh());
	          Double fLowNext = Double.parseDouble(oDailyLineNext.getLow());

	          fHigh = Math.max(fHigh, fHighNext);
	          fLow = Math.min(fLow, fLowNext);
	        }

	        String sClose = listDaily.get(iEnd).getClose();
	        String sTime = listDaily.get(iEnd).getTime();
	        i = iEnd;
	        DecimalFormat df = new DecimalFormat("#0.000");  
	        
	        /*DayLine monthlyLine = new DayLine();
	        monthlyLine.setTime(sTime);
	        monthlyLine.setOpen(df.format(Double.parseDouble(sOpen)));
	        monthlyLine.setClose(df.format(Double.parseDouble(sClose)));
	        monthlyLine.setAmount(df.format(lAmount));
	        monthlyLine.setVolume(df.format(lVolume));
	        monthlyLine.setHigh(df.format(fHigh));
	        monthlyLine.setLow(df.format(fLow));
	        lstMonthly.add(monthlyLine);*/
	        
	        CommonLine monthLine = new CommonLine();
	        monthLine.setFromDate(U.DT.formatTime(listDaily.get(iStart).getTime()));
	        monthLine.setTime(sTime);
	        monthLine.setToDate(U.DT.formatTime(sTime));
	        monthLine.setOpen(df.format(Double.parseDouble(sOpen)));
	        monthLine.setClose(df.format(Double.parseDouble(sClose)));
	        monthLine.setAmount(String.valueOf(lAmount));
	        monthLine.setVolume(String.valueOf(lVolume));
	        monthLine.setHigh(df.format(fHigh));
	        monthLine.setLow(df.format(fLow));
	        
	        lstMonthly.add(monthLine);
	      }
	      return lstMonthly;
	}
	
	public static List<CommonLine> generateYearly(List<DayLine> listDaily) throws ParseException {
	      // 年K
	      List<CommonLine> lstYearly = new ArrayList<CommonLine>();
	      int iLen = listDaily.size();
	      int iStart = 0;
	      for (int i = 0; i < iLen; i++) {
	        iStart = i;
	        String sOpen = listDaily.get(iStart).getOpen();
	        Long lAmount = Long.parseLong(listDaily.get(iStart).getAmount());
	        Long lVolume = Long.parseLong(listDaily.get(iStart).getVolume());
	        Double fHigh = Double.parseDouble(listDaily.get(iStart).getHigh());
	        Double fLow = Double.parseDouble(listDaily.get(iStart).getLow());
	        int iEnd = iStart;
	        for (; iEnd < iLen - 1; iEnd++) {
	          DayLine oDailyLine = listDaily.get(iEnd);
	          Calendar oCldPrev = Calendar.getInstance();
	          oCldPrev.setTimeInMillis(Long.parseLong(oDailyLine.getTime()));
	          int iPrevMonth = oCldPrev.get(Calendar.YEAR);

	          DayLine oDailyLineNext = listDaily.get(iEnd + 1);
	          Calendar oCldNext = Calendar.getInstance();
	          oCldNext.setTimeInMillis(Long.parseLong(oDailyLineNext.getTime()));
	          int iNextMonth = oCldNext.get(Calendar.YEAR);

	          if (iPrevMonth != iNextMonth) {
	            break;
	          }
	          lAmount += Long.parseLong(oDailyLineNext.getAmount());
	          lVolume += Long.parseLong(oDailyLineNext.getVolume());

	          Double fHighNext = Double.parseDouble(oDailyLineNext.getHigh());
	          Double fLowNext = Double.parseDouble(oDailyLineNext.getLow());

	          fHigh = Math.max(fHigh, fHighNext);
	          fLow = Math.min(fLow, fLowNext);
	        }

	        String sClose = listDaily.get(iEnd).getClose();
	        String sTime = listDaily.get(iEnd).getTime();
	        i = iEnd;
	        DecimalFormat df = new DecimalFormat("#0.000");  
	        
	        /*DayLine monthlyLine = new DayLine();
	        monthlyLine.setTime(sTime);
	        monthlyLine.setOpen(df.format(Double.parseDouble(sOpen)));
	        monthlyLine.setClose(df.format(Double.parseDouble(sClose)));
	        monthlyLine.setAmount(df.format(lAmount));
	        monthlyLine.setVolume(df.format(lVolume));
	        monthlyLine.setHigh(df.format(fHigh));
	        monthlyLine.setLow(df.format(fLow));
	        lstMonthly.add(monthlyLine);*/
	        
	        CommonLine monthLine = new CommonLine();
	        monthLine.setFromDate(U.DT.formatTime(listDaily.get(iStart).getTime()));
	        monthLine.setTime(sTime);
	        monthLine.setToDate(U.DT.formatTime(sTime));
	        monthLine.setOpen(df.format(Double.parseDouble(sOpen)));
	        monthLine.setClose(df.format(Double.parseDouble(sClose)));
	        monthLine.setAmount(String.valueOf(lAmount));
	        monthLine.setVolume(String.valueOf(lVolume));
	        monthLine.setHigh(df.format(fHigh));
	        monthLine.setLow(df.format(fLow));
	        
	        lstYearly.add(monthLine);
	      }
	      return lstYearly;
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
		new Thread(new Runnable() {
			@Override
			public void run() {
				exportData("D:\\Program Files\\qianlong\\qijian\\QLDATA\\history\\SZNSE\\day", "sz");
			}
		}).start();
		exportData("D:\\Program Files\\qianlong\\qijian\\QLDATA\\history\\SHASE\\day", "sh");
	}
}
