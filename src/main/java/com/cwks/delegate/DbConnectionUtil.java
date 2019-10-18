package com.cwks.delegate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * <p>Title: DbConnectionUtil.java</p>
 * <p>Description: 获取jndi动态数据源帮助类</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: cssnj</p>
 * @author 胡锐
 * @version 1.0
 */
public class DbConnectionUtil {
	private static Logger logger = LoggerFactory.getLogger(DbConnectionUtil.class);

	// DbConnectionUtil 的静态变量,经过类的初始化后成为全局的一个实例.
	private static DbConnectionUtil dbConnect = new DbConnectionUtil();
	
	private DbConnectionUtil() {}

	public static DbConnectionUtil singleton(){
		return dbConnect;
	}

	/**
	 * 从数据连接池中获取数据库的连接。通过数据库的定位器DSLocator，获取数据库的连接。
	 * 输入参数： dsname:String 数据源名称 返回：Connection 数据库的连接。
	 */
	public Connection getDsConnection(String dsname)throws NamingException, Exception {
		// Connection con = DSLocator.singleton().getInstance(dsname);
		// DSLocator.singleton().getLocalInstance("java:comp/env/"+dsname);
		Connection con = DSLocator.singleton().getConnectionInstance(dsname);
		return con;
	}
	
	/**
	 * 从数据连接池中获取数据库的连接。通过数据库的定位器DSLocator，获取数据库的连接。
	 * 输入参数： dsname:String 数据源名称 返回：Connection 数据库的连接。
	 */
	public DataSource getDataSource(String dsname)throws NamingException, Exception {
		// Connection con = DSLocator.singleton().getInstance(dsname);
		// DSLocator.singleton().getLocalInstance("java:comp/env/"+dsname);
		DataSource ds = DSLocator.singleton().getDataSourceInstance(dsname);
		return ds;
	}

	/**
	 * 从系统的配置参数表中获取默认的数据源，并检查默认设置的数据源与业务逻辑处理的数据是否
	 * 一致，如果一致就不用再次获取数据库的连接，直接使用已经打开的数据库连接。如果一致， 返回true，不一致就返回false.
	 * 输入参数： ds1: ds2: 返回： true:数据源一致， false:不一致
	 */
	public boolean isSameConnectDS(String ds1, String ds2)throws Exception {
		if (ds1 == null || ds2 == null) {
			throw new Exception("09997");
		}
		return ds1.trim().equals(ds2.trim());
	}

	/**
	 * 关闭数据库的连接，在业务逻辑类处理结束后，将此数据库连接关闭。判断与数据库的连接是否 已经关闭,如果没有,关闭数据库的连接.
	 * 输入参数： conn;Connection 数据库的连接。 返回：无
	 */
	public void closeConnection(Connection conn) throws SQLException {
		if (conn != null) {
			if (!conn.isClosed()) {
				conn.close();
			} else {
				logger.debug("连接对象已关闭。");
			}
			conn = null;
		} else {
			logger.debug("连接对象为空，跳过操作。");
		}
	}
}
