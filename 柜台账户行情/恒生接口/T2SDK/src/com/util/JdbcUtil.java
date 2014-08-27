/**
 * 
 */
package com.util;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * JDBC工具类。通过JDBC的方式获取连接或者执行查询。
 * 
 * @author XianBin
 * 
 */
public class JdbcUtil {
	private final static Logger logutil = Logger.getLogger(
			JdbcUtil.class);

	private static DBType dbType = null; // 数据库类型
	/**
	 * 执行本地SQL语句，并返回数据集列表。
	 * 
	 * @param sqlString
	 * @param conn
	 * @return
	 */
	public static List<Object[]> excuteNativeQuery(String sqlString,
			Connection conn) {
		return excuteNativeQuery(sqlString, new Object[] {}, conn);
	}

	/**
	 * 执行本地SQL语句，并返回数据集列表。使用Play中配置的默认数据库。
	 * 
	 * @param sqlString
	 * @param params
	 * @param conn
	 * @return
	 */
	public static List<Object[]> excuteNativeQuery(String sqlString,
			Object[] params, Connection conn) {
		PreparedStatement preStatement = null;
		ResultSet resultSet = null;

		try {
			preStatement = conn.prepareStatement(sqlString);
			setQueryStatementParams(preStatement, params);
			resultSet = preStatement.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			List<Object[]> rows = new ArrayList<Object[]>();
			while (resultSet.next()) {
				Object[] row = new Object[columnCount];
				for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
					row[columnIndex] = resultSet.getObject(columnIndex + 1);
				}

				rows.add(row);
			}

			return rows;
		} catch (Exception e) {
			logutil.error(e.getMessage(),e);

			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn, preStatement, resultSet);
		}
	}

	/**
	 * 查询语句中必须带有 id 字段 执行本地SQL语句，并返回给定实体类的数据集List<T>。使用Play中配置的默认数据库。
	 * 
	 * @param <T>
	 * @param sqlString
	 * @param params
	 * @param c
	 * @param conn
	 * @return
	 */
	public static <T> List<T> excuteNativeQuery(String sqlString,
			Object[] params, Class c, Connection conn) {
		PreparedStatement preStatement = null;
		ResultSet resultSet = null;

		try {
			preStatement = conn.prepareStatement(sqlString);
			setQueryStatementParams(preStatement, params);
			resultSet = preStatement.executeQuery();
			List<T> rows = new ArrayList<T>();
			while (resultSet.next()) {
				T o = null;
				Long id = resultSet.getLong("id");
				Method m = c.getMethod("findById", Object.class);
				o = (T) m.invoke(c, id);
				rows.add(o);
			}
			return rows;
		} catch (Exception e) {
			logutil.error(e.getMessage(),e);

			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn, preStatement, resultSet);
		}
	}

	/**
	 * 执行本地SQL语句。
	 * 
	 * @param sqlString
	 * @param conn
	 * @return
	 */
	public static boolean excuteNative(String sqlString, Connection conn) {
		return excuteNative(sqlString, new Object[] {}, conn);
	}

	/**
	 * 执行本地SQL语句。使用Play中配置的默认数据库。
	 * 
	 * @param sqlString
	 * @param params
	 * @param conn
	 * @return
	 */
	public static boolean excuteNative(String sqlString, Object[] params,
			Connection conn) {
		PreparedStatement preStatement = null;
		ResultSet resultSet = null;

		try {
			preStatement = conn.prepareStatement(sqlString);
			setQueryStatementParams(preStatement, params);
			return preStatement.execute();
		} catch (Exception e) {
			logutil.error(e.getMessage(),e);

			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn, preStatement, resultSet);
		}
	}

	/**
	 * 批量执行本地SQL语句，并返回数据集列表。使用Play中配置的默认数据库。批量处理仅支持批量插入操作，其他均不支持。
	 * 
	 * @param sqlList
	 * @param paramList
	 * @param conn
	 */
	public static void excuteNativeQuery(List<String> sqlList,
			List<List<Object[]>> paramList, Connection conn) {
		PreparedStatement preStatement = null;
		ResultSet resultSet = null;

		final int batchSize = 100;
		int count = 0;

		try {
			for (int sqlIndex = 0; sqlIndex < sqlList.size(); sqlIndex++) {
				String sqlString = sqlList.get(sqlIndex);
				preStatement = conn.prepareStatement(sqlString);

				int paramSize = paramList.size() > 0 ? paramList.get(sqlIndex)
						.size() : 0;
				if (paramSize > 0) {
					for (int paramIndex = 0; paramIndex < paramSize; paramIndex++) {
						// 当有sql语句对应的参数时，返回参数，否则返回空对象数组。
						Object[] params = paramSize > 0
								&& paramSize > paramIndex ? paramList.get(
								sqlIndex).get(paramIndex) : new Object[] {};

						setQueryStatementParams(preStatement, params);

						preStatement.addBatch();

						if (++count % batchSize == 0) {
							int[] results = preStatement.executeBatch();
							logutil.info("Excute SQL Batch result: " + results);
							preStatement.clearBatch();
						}
					}
				} else {
					preStatement.addBatch();

					if (++count % batchSize == 0) {
						int[] results = preStatement.executeBatch();
						logutil.info("Excute SQL Batch result: " + results);
						preStatement.clearBatch();
					}
				}
			}

			int[] results = preStatement.executeBatch();
			logutil.info("Excute SQL Batch result: " + results);
		} catch (Exception e) {
			logutil.error(e.getMessage(),e);

			try {
				conn.rollback();
			} catch (Exception rollBackException) {
				logutil.error("Rollback Exception", rollBackException);
			}

			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn, preStatement, resultSet);
		}
	}

	/**
	 * 执行本地SQL语句，并返回数据集列表。
	 * 
	 * @param sqlString
	 * @param conn
	 * @return
	 */
	public static List<Map<String, Object>> excuteNativeQueryWithName(
			String sqlString, Connection conn) {
		return excuteNativeQueryWithName(sqlString, new Object[] {}, conn);
	}

	/**
	 * 执行本地SQL语句，并返回数据集列表。
	 * 
	 * @param sqlString
	 * @param conn
	 * @return
	 */
	public static List<Map<String, Object>> excuteNativeQueryWithName(
			String sqlString, Object[] params, Connection conn) {
		PreparedStatement preStatement = null;
		ResultSet resultSet = null;

		try {
			preStatement = conn.prepareStatement(sqlString);
			setQueryStatementParams(preStatement, params);
			resultSet = preStatement.executeQuery();

			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			String[] columnsName = new String[columnCount];
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				columnsName[columnIndex] = metaData
						.getColumnName(columnIndex + 1);
			}

			List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
			while (resultSet.next()) {
				Map<String, Object> row = new HashMap<String, Object>();
				for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
					String columnName = columnsName[columnIndex];
					Object value = resultSet.getObject(columnName);

					row.put(columnName, value);
				}

				rows.add(row);
			}

			return rows;
		} catch (Exception e) {
			logutil.error(e.getMessage(),e);

			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn, preStatement, resultSet);
		}
	}

	/**
	 * 将查询参数设置到查询语句中。
	 * 
	 * @param preStatement
	 * @param params
	 * @throws SQLException
	 */
	private static void setQueryStatementParams(PreparedStatement preStatement,
			Object[] params) throws SQLException {
		for (int i = 1; i <= params.length; i++) {
			preStatement.setObject(i, params[i - 1]);
		}
	}

	/**
	 * 关闭Connection连接对象。
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				logutil.error(e.getMessage(),e);
			}
		}
	}

	/**
	 * 关闭Statement对象。
	 * 
	 * @param statement
	 */
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
				logutil.error(e.getMessage(),e);
			}
		}
	}

	/**
	 * 关闭ResultSet对象。
	 * 
	 * @param set
	 */
	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (Exception e) {
				logutil.error(e.getMessage(),e);
			}
		}
	}

	/**
	 * 同时关闭Connection, Statement对象。
	 * 
	 * @param conn
	 * @param statement
	 */
	public static void close(Connection conn, Statement statement) {
		close(statement);
		close(conn);
	}

	/**
	 * 同时关闭Connection, Statement, ResultSet对象。
	 * 
	 * @param conn
	 * @param statement
	 * @param set
	 */
	public static void close(Connection conn, Statement statement,
			ResultSet resultSet) {
		close(resultSet);
		close(conn, statement);
	}

	public static DBType getDBType() {
		if (dbType != null) {
			return dbType;
		} else {
			/*
			 * 初始化数据库类型。
			 */
			String key = "db.driverClassName";
			Properties p = SpringProperties.getProperties();
			boolean isDefined = p.containsKey(key);
			if (!isDefined)
				throw new RuntimeException(String.format(
						"The [%s] property is not defined!", key));

			String keyValue = p.getProperty(key);
			if (keyValue.toLowerCase().contains("mysql")) {
				dbType = DBType.MySQL;
			} else if (keyValue.toLowerCase().contains("oracle")) {
				dbType = DBType.Oracle;
			} else {
				throw new RuntimeException(
						String.format(
								"Unkown DBType! Please check the [%s] property in application.conf file!",
								key));
			}

			return dbType;
		}
	}
}
