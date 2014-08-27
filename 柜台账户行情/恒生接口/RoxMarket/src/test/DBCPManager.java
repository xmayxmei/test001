package test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.log4j.Logger;

import com.cfwx.rox.businesssync.market.dao.impl.QuoteHistoryMySqlImpl;
import com.cfwx.rox.businesssync.market.show.DayLine;

public class DBCPManager {

	private static final Logger logger = Logger.getLogger(DBCPManager.class);
	static private DBCPManager instance; // 唯一实例
	static private DataSource ds;

	private DBCPManager() {
		ds = setupDataSource();
	}

	private static DataSource setupDataSource() {
		try {
			Properties prop = new Properties();
			InputStream inputStream = DBCPManager.class.getClassLoader()
					.getResourceAsStream("dbcp.properties");
			prop.load(inputStream);
			ds = BasicDataSourceFactory.createDataSource(prop);

		} catch (IOException e) {
			logger.error("加载 dbcp.properties 错误");
			logger.info(e.toString());
		} catch (Exception e) {
			logger.info(e.toString());
		}
		if (ds != null)
			logger.info("获取数据源成功");
		return ds;
	}

	public DataSource getDs() {
		return ds;
	}
	static synchronized public DBCPManager getInstance() {
		if (instance == null) {
			instance = new DBCPManager();
		}
		return instance;
	}

	public void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			logger.info("关闭ResultSet出错");
			logger.info(e.toString());
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				logger.info("关闭Statement出错");
				logger.info(e.toString());
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					logger.info("关闭Connection出错");
					logger.info(e.toString());
				}
			}
		}
	}

	public Connection getConnection() {
		if (ds != null) {
			try {
				Connection conn = ds.getConnection();
				return conn;
			} catch (SQLException e) {
				logger.info("获取Connection出错");
				logger.info(e.toString());
			}
		}
		return null;
	}

	// 测试代码
	public static void main(String[] args) throws InterruptedException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		DBCPManager db = DBCPManager.getInstance();

		conn = db.getConnection();
		if (conn == null)
			System.out.println("conn==null");
		try {
			List<DayLine> lstDays = new QuoteHistoryMySqlImpl((BasicDataSource)db.getDs()).getDailyData("sh200", 2);
			for (DayLine d : lstDays) {
				System.err.println(d.getTime());
				Thread.sleep(100);
			}
		} finally {
			db.free(rset, stmt, conn);
		}

	}
}