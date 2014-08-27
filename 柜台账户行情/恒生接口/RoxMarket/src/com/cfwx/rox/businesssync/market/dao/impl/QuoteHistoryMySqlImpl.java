/**
 * 
 */
package com.cfwx.rox.businesssync.market.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao;
import com.cfwx.rox.businesssync.market.show.CommonLine;
import com.cfwx.rox.businesssync.market.show.DayLine;
import com.cfwx.rox.businesssync.market.show.MinLine;
import com.cfwx.util.U;

/**
 * <code>QuoteHistoryMySqlImpl</code> 
 * 
 * @author Jimmy
 *
 * @since Quote (June 13, 2013)
 */
@Repository("quoteHistoryDaoImpl")
public class QuoteHistoryMySqlImpl implements IQuoteHistoryDao{
	private final static Logger L = Logger.getLogger(QuoteHistoryMySqlImpl.class);
	
	public static final String QUERY_SH_5MIN = "select * from t_sh_5min where code=? and date " +
			"in (select date from (select * from t_sh_5min where code=? group by date order by date desc limit ?) as t) " +
			"order by date desc,time desc";
	public static final String QUERY_SZ_5MIN = "select * from t_sz_5min where code=? and date " +
			"in (select date from (select * from t_sz_5min where code=? group by date order by date desc limit ?) as t) " +
			"order by date desc,time desc";
	public static final String QUERY_SH_10MIN = "select * from t_sh_10min where code=? and date " +
			"in (select date from (select * from t_sh_10min where code=? group by date order by date desc limit ?) as t) " +
			"order by date desc,time desc";
	public static final String QUERY_SZ_10MIN = "select * from t_sz_10min where code=? and date " +
			"in (select date from (select * from t_sz_10min where code=? group by date order by date desc limit ?) as t) " +
			"order by date desc,time desc";
	public static final String QUERY_SH_15MIN = "select * from t_sh_15min where code=? and date " +
			"in (select date from (select * from t_sh_15min where code=? group by date order by date desc limit ?) as t) " +
			"order by date desc,time desc";
	public static final String QUERY_SZ_15MIN = "select * from t_sz_15min where code=? and date " +
			"in (select date from (select * from t_sz_15min where code=? group by date order by date desc limit ?) as t) " +
			"order by date desc,time desc";
	public static final String QUERY_SH_30MIN = "select * from t_sh_30min where code=? and date " +
			"in (select date from (select * from t_sh_30min where code=? group by date order by date desc limit ?) as t) " +
			"order by date desc,time desc";
	public static final String QUERY_SZ_30MIN = "select * from t_sz_30min where code=? and date " +
			"in (select date from (select * from t_sz_30min where code=? group by date order by date desc limit ?) as t) " +
			"order by date desc,time desc";
	public static final String QUERY_SH_60MIN = "select * from t_sh_60min where code=? and date " +
			"in (select date from (select * from t_sh_60min where code=? group by date order by date desc limit ?) as t) " +
			"order by date desc,time desc";
	public static final String QUERY_SZ_60MIN = "select * from t_sz_60min where code=? and date " +
			"in (select date from (select * from t_sz_60min where code=? group by date order by date desc limit ?) as t) " +
			"order by date desc,time desc";
	public static final String QUERY_SH_DAILY = "SELECT * FROM t_sh_daily WHERE code=? ORDER BY date DESC LIMIT ?";
	public static final String QUERY_SZ_DAILY = "SELECT * FROM t_sz_daily WHERE code=? ORDER BY date DESC LIMIT ?";
	public static final String QUERY_SH_WEEKLY = "SELECT * FROM t_sh_weekly WHERE code=? ORDER BY toDate DESC LIMIT ?";
	public static final String QUERY_SZ_WEEKLY = "SELECT * FROM t_sz_weekly WHERE code=? ORDER BY toDate DESC LIMIT ?";
	public static final String QUERY_SH_MONTHLY = "SELECT * FROM t_sh_monthly WHERE code=? ORDER BY toDate DESC LIMIT ?";
	public static final String QUERY_SZ_MONTHLY = "SELECT * FROM t_sz_monthly WHERE code=? ORDER BY toDate DESC LIMIT ?";
	public static final String QUERY_SH_YEARLY = "SELECT * FROM t_sh_yearly WHERE code=? ORDER BY toDate DESC LIMIT ?";
	public static final String QUERY_SZ_YEARLY = "SELECT * FROM t_sz_yearly WHERE code=? ORDER BY toDate DESC LIMIT ?";
	public static final String SAVE_SH_5MIN = "INSERT INTO t_sh_5min(date,time,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SZ_5MIN = "INSERT INTO t_sz_5min(date,time,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SH_10MIN = "INSERT INTO t_sh_10min(date,time,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SZ_10MIN = "INSERT INTO t_sz_10min(date,time,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SH_15MIN = "INSERT INTO t_sh_15min(date,time,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SZ_15MIN = "INSERT INTO t_sz_15min(date,time,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SH_30MIN = "INSERT INTO t_sh_30min(date,time,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SZ_30MIN = "INSERT INTO t_sz_30min(date,time,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SH_60MIN = "INSERT INTO t_sh_60min(date,time,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SZ_60MIN = "INSERT INTO t_sz_60min(date,time,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SH_DAILY = "INSERT INTO t_sh_daily(date,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?)";
	public static final String SAVE_SZ_DAILY = "INSERT INTO t_sz_daily(date,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?)";
	public static final String SAVE_SH_WEEKLY = "INSERT INTO t_sh_weekly(fromDate,toDate,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SZ_WEEKLY = "INSERT INTO t_sz_weekly(fromDate,toDate,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SH_MONTHLY = "INSERT INTO t_sh_monthly(fromDate,toDate,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SZ_MONTHLY = "INSERT INTO t_sz_monthly(fromDate,toDate,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SH_YEARLY = "INSERT INTO t_sh_yearly(fromDate,toDate,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String SAVE_SZ_YEARLY = "INSERT INTO t_sz_yearly(fromDate,toDate,code,open,close,high,low,amount,volume) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String UPDATE_SH_DAILY = "UPDATE t_sh_daily SET date=?,close=?,high=?,low=?,amount=?,volume=? WHERE date=? AND code=?";
	public static final String UPDATE_SZ_DAILY = "UPDATE t_sz_daily SET date=?,close=?,high=?,low=?,amount=?,volume=? WHERE date=? AND code=?";
	public static final String UPDATE_SH_WEEKLY = "UPDATE t_sh_weekly SET fromDate=?,toDate=?,open=?,close=?,high=?,low=?,amount=?,volume=? WHERE fromDate=? and toDate=? and code=?";
	public static final String UPDATE_SZ_WEEKLY = "UPDATE t_sz_weekly SET fromDate=?,toDate=?,open=?,close=?,high=?,low=?,amount=?,volume=? WHERE fromDate=? and toDate=? and code=?";
	public static final String UPDATE_SH_MONTHLY = "UPDATE t_sh_monthly SET fromDate=?,toDate=?,open=?,close=?,high=?,low=?,amount=?,volume=? WHERE fromDate=? and toDate=? and code=?";
	public static final String UPDATE_SZ_MONTHLY = "UPDATE t_sz_monthly SET fromDate=?,toDate=?,open=?,close=?,high=?,low=?,amount=?,volume=? WHERE fromDate=? and toDate=? and code=?";
	public static final String UPDATE_SH_YEARLY = "UPDATE t_sh_yearly SET fromDate=?,toDate=?,open=?,close=?,high=?,low=?,amount=?,volume=? WHERE fromDate=? and toDate=? and code=?";
	public static final String UPDATE_SZ_YEARLY =  "UPDATE t_sz_yearly SET fromDate=?,toDate=?,open=?,close=?,high=?,low=?,amount=?,volume=? WHERE fromDate=? and toDate=? and code=?";
	
	@Autowired
	@Qualifier("quoteDataSource")
	private BasicDataSource quoteDataSource;
	
	public QuoteHistoryMySqlImpl() {
	}
	
	public QuoteHistoryMySqlImpl(BasicDataSource quoteDataSource) {
		this.quoteDataSource = quoteDataSource;
	}
	
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#getDailyData(java.lang.String, int)
	 */
	@Override
	public List<DayLine> getDailyData(String sCode, int iDays) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = QUERY_SH_DAILY;
		} else if (sCode.startsWith("sz")) {
			sSql = QUERY_SZ_DAILY;
		} else {
			return null;
		}
		String sStockCode = sCode.substring(2);
		List<DayLine> lstDays = new ArrayList<DayLine>();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, sStockCode);
			stmt.setInt(2, iDays);
			rs = stmt.executeQuery();
			while (rs.next()) {
				DayLine oDay = new DayLine();
				oDay.setTime(String.valueOf(rs.getDate("date").getTime()));
				oDay.setOpen(rs.getString("open"));
				oDay.setClose(rs.getString("close"));
				oDay.setHigh(rs.getString("high"));
				oDay.setLow(rs.getString("low"));
				oDay.setAmount(rs.getString("amount"));
				oDay.setVolume(rs.getString("volume"));
				lstDays.add(oDay);
			}
			Collections.reverse(lstDays);
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lstDays;
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#getWeeklyData(java.lang.String, int)
	 */
	@Override
	public List<CommonLine> getWeeklyData(String sCode, int iWeeks) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = QUERY_SH_WEEKLY;
		} else if (sCode.startsWith("sz")) {
			sSql = QUERY_SZ_WEEKLY;
		} else {
			return null;
		}
		String sStockCode = sCode.substring(2);
		List<CommonLine> lstWeeks = new ArrayList<CommonLine>();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, sStockCode);
			stmt.setInt(2, iWeeks);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CommonLine oDay = new CommonLine();
				oDay.setFromDate(rs.getString("fromDate"));
				oDay.setToDate(rs.getString("toDate"));
				oDay.setOpen(rs.getString("open"));
				oDay.setTime(String.valueOf(rs.getDate("toDate").getTime()));
				oDay.setOpen(rs.getString("open"));
				oDay.setClose(rs.getString("close"));
				oDay.setHigh(rs.getString("high"));
				oDay.setLow(rs.getString("low"));
				oDay.setAmount(rs.getString("amount"));
				oDay.setVolume(rs.getString("volume"));
				lstWeeks.add(oDay);
			}
			Collections.reverse(lstWeeks);
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lstWeeks;
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#getMonthlyData(java.lang.String, int)
	 */
	@Override
	public List<CommonLine> getMonthlyData(String sCode, int iMonths) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = QUERY_SH_MONTHLY;
		} else if (sCode.startsWith("sz")) {
			sSql = QUERY_SZ_MONTHLY;
		} else {
			return null;
		}
		String sStockCode = sCode.substring(2);
		List<CommonLine> lstMonths = new ArrayList<CommonLine>();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, sStockCode);
			stmt.setInt(2, iMonths);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CommonLine oDay = new CommonLine();
				oDay.setFromDate(rs.getString("fromDate"));
				oDay.setToDate(rs.getString("toDate"));
				oDay.setTime(String.valueOf(rs.getDate("toDate").getTime()));
				oDay.setOpen(rs.getString("open"));
				oDay.setClose(rs.getString("close"));
				oDay.setHigh(rs.getString("high"));
				oDay.setLow(rs.getString("low"));
				oDay.setAmount(rs.getString("amount"));
				oDay.setVolume(rs.getString("volume"));
				lstMonths.add(oDay);
			}
			Collections.reverse(lstMonths);
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lstMonths;
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#getYearlyData(java.lang.String, int)
	 */
	@Override
	public List<CommonLine> getYearlyData(String sCode, int iYears) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = QUERY_SH_YEARLY;
		} else if (sCode.startsWith("sz")) {
			sSql = QUERY_SZ_YEARLY;
		} else {
			return null;
		}
		String sStockCode = sCode.substring(2);
		List<CommonLine> lstYears = new ArrayList<CommonLine>();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, sStockCode);
			stmt.setInt(2, iYears);
			rs = stmt.executeQuery();
			while (rs.next()) {
				CommonLine oDay = new CommonLine();
				oDay.setFromDate(rs.getString("fromDate"));
				oDay.setToDate(rs.getString("toDate"));
				oDay.setTime(String.valueOf(rs.getDate("toDate").getTime()));
				oDay.setOpen(rs.getString("open"));
				oDay.setClose(rs.getString("close"));
				oDay.setHigh(rs.getString("high"));
				oDay.setLow(rs.getString("low"));
				oDay.setAmount(rs.getString("amount"));
				oDay.setVolume(rs.getString("volume"));
				lstYears.add(oDay);
			}
			Collections.reverse(lstYears);
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lstYears;
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#saveDailyData(java.lang.String, com.cfwx.rox.businesssync.market.show.DayLine)
	 */
	@Override
	public void saveDailyData(String sCode, DayLine oDay) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = SAVE_SH_DAILY;
		} else if (sCode.startsWith("sz")) {
			sSql = SAVE_SZ_DAILY;
		} else {
			return;
		}
		String sStockCode = sCode.substring(2);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, U.DT.formatTime(oDay.getTime()));
			stmt.setString(2, sStockCode);
			stmt.setString(3, oDay.getOpen());
			stmt.setString(4, oDay.getClose());
			stmt.setString(5, oDay.getHigh());
			stmt.setString(6, oDay.getLow());
			stmt.setString(7, oDay.getAmount());
			stmt.setString(8, oDay.getVolume());
			stmt.execute();
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#saveWeeklyData(java.lang.String, com.cfwx.rox.businesssync.market.show.WeekLine)
	 */
	@Override
	public void saveWeeklyData(String sCode, CommonLine oDay) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = SAVE_SH_WEEKLY;
		} else if (sCode.startsWith("sz")) {
			sSql = SAVE_SZ_WEEKLY;
		} else {
			return;
		}
		String sStockCode = sCode.substring(2);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, oDay.getFromDate());
			stmt.setString(2, oDay.getToDate());
			stmt.setString(3, sStockCode);
			stmt.setString(4, oDay.getOpen());
			stmt.setString(5, oDay.getClose());
			stmt.setString(6, oDay.getHigh());
			stmt.setString(7, oDay.getLow());
			stmt.setString(8, oDay.getAmount());
			stmt.setString(9, oDay.getVolume());
			stmt.execute();
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#saveMonthlyData(java.lang.String, com.cfwx.rox.businesssync.market.show.WeekLine)
	 */
	@Override
	public void saveMonthlyData(String sCode, CommonLine oDay) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = SAVE_SH_MONTHLY;
		} else if (sCode.startsWith("sz")) {
			sSql = SAVE_SZ_MONTHLY;
		} else {
			return;
		}
		String sStockCode = sCode.substring(2);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, oDay.getFromDate());
			stmt.setString(2, oDay.getToDate());
			stmt.setString(3, sStockCode);
			stmt.setString(4, oDay.getOpen());
			stmt.setString(5, oDay.getClose());
			stmt.setString(6, oDay.getHigh());
			stmt.setString(7, oDay.getLow());
			stmt.setString(8, oDay.getAmount());
			stmt.setString(9, oDay.getVolume());
			stmt.execute();
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#saveYearlyData(java.lang.String, com.cfwx.rox.businesssync.market.show.WeekLine)
	 */
	@Override
	public void saveYearlyData(String sCode, CommonLine oDay) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = SAVE_SH_YEARLY;
		} else if (sCode.startsWith("sz")) {
			sSql = SAVE_SZ_YEARLY;
		} else {
			return;
		}
		String sStockCode = sCode.substring(2);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, oDay.getFromDate());
			stmt.setString(2, oDay.getToDate());
			stmt.setString(3, sStockCode);
			stmt.setString(4, oDay.getOpen());
			stmt.setString(5, oDay.getClose());
			stmt.setString(6, oDay.getHigh());
			stmt.setString(7, oDay.getLow());
			stmt.setString(8, oDay.getAmount());
			stmt.setString(9, oDay.getVolume());
			stmt.execute();
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#updateDailyData(java.lang.String, com.cfwx.rox.businesssync.market.show.DayLine)
	 */
	@Override
	public void updateDailyData(String sCode, DayLine oDay, DayLine oDayNew) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = UPDATE_SH_DAILY;
		} else if (sCode.startsWith("sz")) {
			sSql = UPDATE_SZ_DAILY;
		} else {
			return;
		}
		String sStockCode = sCode.substring(2);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, oDayNew.getOpen());
			stmt.setString(2, oDayNew.getClose());
			stmt.setString(3, oDayNew.getHigh());
			stmt.setString(4, oDayNew.getLow());
			stmt.setString(5, oDayNew.getAmount());
			stmt.setString(6, oDayNew.getVolume());
			stmt.setString(7, U.DT.formatTime(oDay.getTime()));
			stmt.setString(8, sStockCode);
			stmt.execute();
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#updateWeeklyData(java.lang.String, com.cfwx.rox.businesssync.market.show.WeekLine, com.cfwx.rox.businesssync.market.show.WeekLine)
	 */
	@Override
	public void updateWeeklyData(String sCode, CommonLine oWeekly, CommonLine oWeeklyNew) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = UPDATE_SH_WEEKLY;
		} else if (sCode.startsWith("sz")) {
			sSql = UPDATE_SZ_WEEKLY;
		} else {
			return;
		}
		String sStockCode = sCode.substring(2);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, oWeeklyNew.getFromDate());
			stmt.setString(2, oWeeklyNew.getToDate());
			stmt.setString(3, oWeeklyNew.getOpen());
			stmt.setString(4, oWeeklyNew.getClose());
			stmt.setString(5, oWeeklyNew.getHigh());
			stmt.setString(6, oWeeklyNew.getLow());
			stmt.setString(7, oWeeklyNew.getAmount());
			stmt.setString(8, oWeeklyNew.getVolume());
			stmt.setString(9, oWeekly.getFromDate());
			stmt.setString(10, oWeekly.getToDate());
			stmt.setString(11, sStockCode);
			stmt.execute();
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#updateMonthlyData(java.lang.String, com.cfwx.rox.businesssync.market.show.WeekLine, com.cfwx.rox.businesssync.market.show.WeekLine)
	 */
	@Override
	public void updateMonthlyData(String sCode, CommonLine oMonthly, CommonLine oMonthlyNew) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = UPDATE_SH_MONTHLY;
		} else if (sCode.startsWith("sz")) {
			sSql = UPDATE_SZ_MONTHLY;
		} else {
			return;
		}
		String sStockCode = sCode.substring(2);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, oMonthlyNew.getFromDate());
			stmt.setString(2, oMonthlyNew.getToDate());
			stmt.setString(3, oMonthlyNew.getOpen());
			stmt.setString(4, oMonthlyNew.getClose());
			stmt.setString(5, oMonthlyNew.getHigh());
			stmt.setString(6, oMonthlyNew.getLow());
			stmt.setString(7, oMonthlyNew.getAmount());
			stmt.setString(8, oMonthlyNew.getVolume());
			stmt.setString(9, oMonthly.getFromDate());
			stmt.setString(10, oMonthly.getToDate());
			stmt.setString(11, sStockCode);
			stmt.execute();
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#updateYearlyData(java.lang.String, com.cfwx.rox.businesssync.market.show.WeekLine, com.cfwx.rox.businesssync.market.show.WeekLine)
	 */
	@Override
	public void updateYearlyData(String sCode, CommonLine oYearly,
			CommonLine oYearlyNew) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = UPDATE_SH_YEARLY;
		} else if (sCode.startsWith("sz")) {
			sSql = UPDATE_SZ_YEARLY;
		} else {
			return;
		}
		String sStockCode = sCode.substring(2);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, oYearlyNew.getFromDate());
			stmt.setString(2, oYearlyNew.getToDate());
			stmt.setString(3, oYearlyNew.getOpen());
			stmt.setString(4, oYearlyNew.getClose());
			stmt.setString(5, oYearlyNew.getHigh());
			stmt.setString(6, oYearlyNew.getLow());
			stmt.setString(7, oYearlyNew.getAmount());
			stmt.setString(8, oYearlyNew.getVolume());
			stmt.setString(9, oYearly.getFromDate());
			stmt.setString(10, oYearly.getToDate());
			stmt.setString(11, sStockCode);
			stmt.execute();
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#get5MinData(java.lang.String, int)
	 */
	@Override
	public List<MinLine> get5MinData(String sCode, int iDays) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = QUERY_SH_5MIN;
		} else if (sCode.startsWith("sz")) {
			sSql = QUERY_SZ_5MIN;
		} else {
			return null;
		}
		String sStockCode = sCode.substring(2);
		List<MinLine> lstMins = new ArrayList<MinLine>();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, sStockCode);
			stmt.setString(2, sStockCode);
			stmt.setInt(3, iDays);
			rs = stmt.executeQuery();
			while (rs.next()) {
				MinLine oMins = new MinLine();
				String sDate = rs.getString("date");
				String sDatetime = rs.getString("time");
				oMins.setTime(String.valueOf(U.DT.parseYYYYMMDDHHmmss(sDate + sDatetime).getTime()));
				oMins.setDate(sDate);
				oMins.setDateTime(sDatetime);
				oMins.setOpen(rs.getString("open"));
				oMins.setClose(rs.getString("close"));
				oMins.setHigh(rs.getString("high"));
				oMins.setLow(rs.getString("low"));
				oMins.setAmount(rs.getString("amount"));
				oMins.setVolume(rs.getString("volume"));
				lstMins.add(oMins);
			}
			Collections.reverse(lstMins);
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lstMins;
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#save5MinData(java.lang.String, com.cfwx.rox.businesssync.market.show.DayLine)
	 */
	@Override
	public void save5MinData(String sCode, MinLine oMin5) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = SAVE_SH_5MIN;
		} else if (sCode.startsWith("sz")) {
			sSql = SAVE_SZ_5MIN;
		} else {
			return;
		}
		String sStockCode = sCode.substring(2);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, oMin5.getDate());
			stmt.setString(2, oMin5.getDateTime());
			stmt.setString(3, sStockCode);
			stmt.setString(4, oMin5.getOpen());
			stmt.setString(5, oMin5.getClose());
			stmt.setString(6, oMin5.getHigh());
			stmt.setString(7, oMin5.getLow());
			stmt.setString(8, oMin5.getAmount());
			stmt.setString(9, oMin5.getVolume());
			stmt.execute();
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#get10MinData(java.lang.String, int)
	 */
	@Override
	public List<MinLine> get10MinData(String sCode, int iDays) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = QUERY_SH_10MIN;
		} else if (sCode.startsWith("sz")) {
			sSql = QUERY_SZ_10MIN;
		} else {
			return null;
		}
		String sStockCode = sCode.substring(2);
		List<MinLine> lstMins = new ArrayList<MinLine>();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, sStockCode);
			stmt.setString(2, sStockCode);
			stmt.setInt(3, iDays);
			rs = stmt.executeQuery();
			while (rs.next()) {
				MinLine oMins = new MinLine();
				String sDate = rs.getString("date");
				String sDatetime = rs.getString("time");
				oMins.setTime(String.valueOf(U.DT.parseYYYYMMDDHHmmss(sDate + sDatetime).getTime()));
				oMins.setDate(sDate);
				oMins.setDateTime(sDatetime);
				oMins.setOpen(rs.getString("open"));
				oMins.setClose(rs.getString("close"));
				oMins.setHigh(rs.getString("high"));
				oMins.setLow(rs.getString("low"));
				oMins.setAmount(rs.getString("amount"));
				oMins.setVolume(rs.getString("volume"));
				lstMins.add(oMins);
			}
			Collections.reverse(lstMins);
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lstMins;
	}
	
	@Override
	public void save10MinData(String sCode, MinLine oMin10) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = SAVE_SH_10MIN;
		} else if (sCode.startsWith("sz")) {
			sSql = SAVE_SZ_10MIN;
		} else {
			return;
		}
		String sStockCode = sCode.substring(2);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, oMin10.getDate());
			stmt.setString(2, oMin10.getDateTime());
			stmt.setString(3, sStockCode);
			stmt.setString(4, oMin10.getOpen());
			stmt.setString(5, oMin10.getClose());
			stmt.setString(6, oMin10.getHigh());
			stmt.setString(7, oMin10.getLow());
			stmt.setString(8, oMin10.getAmount());
			stmt.setString(9, oMin10.getVolume());
			stmt.execute();
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#get15MinData(java.lang.String, int)
	 */
	@Override
	public List<MinLine> get15MinData(String sCode, int iMinDays) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = QUERY_SH_15MIN;
		} else if (sCode.startsWith("sz")) {
			sSql = QUERY_SZ_15MIN;
		} else {
			return null;
		}
		String sStockCode = sCode.substring(2);
		List<MinLine> lstMins = new ArrayList<MinLine>();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, sStockCode);
			stmt.setString(2, sStockCode);
			stmt.setInt(3, iMinDays);
			rs = stmt.executeQuery();
			while (rs.next()) {
				MinLine oMins = new MinLine();
				String sDate = rs.getString("date");
				String sDatetime = rs.getString("time");
				oMins.setTime(String.valueOf(U.DT.parseYYYYMMDDHHmmss(sDate + sDatetime).getTime()));
				oMins.setDate(sDate);
				oMins.setDateTime(sDatetime);
				oMins.setOpen(rs.getString("open"));
				oMins.setClose(rs.getString("close"));
				oMins.setHigh(rs.getString("high"));
				oMins.setLow(rs.getString("low"));
				oMins.setAmount(rs.getString("amount"));
				oMins.setVolume(rs.getString("volume"));
				lstMins.add(oMins);
			}
			Collections.reverse(lstMins);
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lstMins;
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#get30MinData(java.lang.String, int)
	 */
	@Override
	public List<MinLine> get30MinData(String sCode, int iMinDays) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = QUERY_SH_30MIN;
		} else if (sCode.startsWith("sz")) {
			sSql = QUERY_SZ_30MIN;
		} else {
			return null;
		}
		String sStockCode = sCode.substring(2);
		List<MinLine> lstMins = new ArrayList<MinLine>();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, sStockCode);
			stmt.setString(2, sStockCode);
			stmt.setInt(3, iMinDays);
			rs = stmt.executeQuery();
			while (rs.next()) {
				MinLine oMins = new MinLine();
				String sDate = rs.getString("date");
				String sDatetime = rs.getString("time");
				oMins.setTime(String.valueOf(U.DT.parseYYYYMMDDHHmmss(sDate + sDatetime).getTime()));
				oMins.setDate(sDate);
				oMins.setDateTime(sDatetime);
				oMins.setOpen(rs.getString("open"));
				oMins.setClose(rs.getString("close"));
				oMins.setHigh(rs.getString("high"));
				oMins.setLow(rs.getString("low"));
				oMins.setAmount(rs.getString("amount"));
				oMins.setVolume(rs.getString("volume"));
				lstMins.add(oMins);
			}
			Collections.reverse(lstMins);
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lstMins;
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#save15MinData(java.lang.String, com.cfwx.rox.businesssync.market.show.MinLine)
	 */
	@Override
	public void save15MinData(String sCode, MinLine oMin15) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = SAVE_SH_15MIN;
		} else if (sCode.startsWith("sz")) {
			sSql = SAVE_SZ_15MIN;
		} else {
			return;
		}
		String sStockCode = sCode.substring(2);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, oMin15.getDate());
			stmt.setString(2, oMin15.getDateTime());
			stmt.setString(3, sStockCode);
			stmt.setString(4, oMin15.getOpen());
			stmt.setString(5, oMin15.getClose());
			stmt.setString(6, oMin15.getHigh());
			stmt.setString(7, oMin15.getLow());
			stmt.setString(8, oMin15.getAmount());
			stmt.setString(9, oMin15.getVolume());
			stmt.execute();
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#save30MinData(java.lang.String, com.cfwx.rox.businesssync.market.show.MinLine)
	 */
	@Override
	public void save30MinData(String sCode, MinLine oMin30) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = SAVE_SH_30MIN;
		} else if (sCode.startsWith("sz")) {
			sSql = SAVE_SZ_30MIN;
		} else {
			return;
		}
		String sStockCode = sCode.substring(2);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, oMin30.getDate());
			stmt.setString(2, oMin30.getDateTime());
			stmt.setString(3, sStockCode);
			stmt.setString(4, oMin30.getOpen());
			stmt.setString(5, oMin30.getClose());
			stmt.setString(6, oMin30.getHigh());
			stmt.setString(7, oMin30.getLow());
			stmt.setString(8, oMin30.getAmount());
			stmt.setString(9, oMin30.getVolume());
			stmt.execute();
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#get60MinData(java.lang.String, int)
	 */
	@Override
	public List<MinLine> get60MinData(String sCode, int iMinDays) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = QUERY_SH_60MIN;
		} else if (sCode.startsWith("sz")) {
			sSql = QUERY_SZ_60MIN;
		} else {
			return null;
		}
		String sStockCode = sCode.substring(2);
		List<MinLine> lstMins = new ArrayList<MinLine>();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, sStockCode);
			stmt.setString(2, sStockCode);
			stmt.setInt(3, iMinDays);
			rs = stmt.executeQuery();
			while (rs.next()) {
				MinLine oMins = new MinLine();
				String sDate = rs.getString("date");
				String sDatetime = rs.getString("time");
				oMins.setTime(String.valueOf(U.DT.parseYYYYMMDDHHmmss(sDate + sDatetime).getTime()));
				oMins.setDate(sDate);
				oMins.setDateTime(sDatetime);
				oMins.setOpen(rs.getString("open"));
				oMins.setClose(rs.getString("close"));
				oMins.setHigh(rs.getString("high"));
				oMins.setLow(rs.getString("low"));
				oMins.setAmount(rs.getString("amount"));
				oMins.setVolume(rs.getString("volume"));
				lstMins.add(oMins);
			}
			Collections.reverse(lstMins);
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lstMins;
	}

	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteHistoryDao#save60MinData(java.lang.String, com.cfwx.rox.businesssync.market.show.MinLine)
	 */
	@Override
	public void save60MinData(String sCode, MinLine oMin60) {
		String sSql = "";
		if (sCode.startsWith("sh")) {
			sSql = SAVE_SH_60MIN;
		} else if (sCode.startsWith("sz")) {
			sSql = SAVE_SZ_60MIN;
		} else {
			return;
		}
		String sStockCode = sCode.substring(2);
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = quoteDataSource.getConnection();
			stmt = con.prepareStatement(sSql);
			stmt.setString(1, oMin60.getDate());
			stmt.setString(2, oMin60.getDateTime());
			stmt.setString(3, sStockCode);
			stmt.setString(4, oMin60.getOpen());
			stmt.setString(5, oMin60.getClose());
			stmt.setString(6, oMin60.getHigh());
			stmt.setString(7, oMin60.getLow());
			stmt.setString(8, oMin60.getAmount());
			stmt.setString(9, oMin60.getVolume());
			stmt.execute();
		} catch (Exception e) {
			L.error(sCode + ":" + e.getMessage() , e.getCause());
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
