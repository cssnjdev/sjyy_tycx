package com.cwks.delegate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * <p>
 * Title: DSLocator.java
 * </p>
 * <p>
 * Description: 用singleton模式实现的服务定位器类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * </p>
 * <p>
 * Company: cssnj
 * </p>
 * 
 * @author 胡锐
 * @version 1.0
 */
public class DSLocator {
	private static Logger logger = LoggerFactory.getLogger(DSLocator.class);

	// 缓存，用来存放查找到的DataSource对象。Map中的key是DataSource的jndi名，value 是DataSource对象。
	private static Map dsMap = Collections.synchronizedMap(new HashMap());

	// DSLocator的静态变量,经过类的初始化后成为全局的一个实例.
	private static DSLocator dsLocator = new DSLocator();

	private DSLocator() {
	}

	/**
	 * 返回dsLocator属性。
	 * 
	 * @return DSLocator - 返回的属性。
	 */
	public static DSLocator singleton() {
		return dsLocator;
	}
	
	/**
	 * 用来根据实例的jndi名得到DataSource实例。方法会先在dsMap属性<br>
	 * 中查找是否有给实例的缓存。如果有，则用实例生成Connection实例<br>
	 * 并返回。如果生成出错，则调用clear方法，然后调用lookup方法找到<br>
	 * 该DataSource实例，放入dsMap中，调用LogWritter的sysInfo方<br>
	 * 法记录存放的实例。如果缓存中没有该实例，调用lookup方法找到<br>
	 * 该DataSource实例，放入dsMap中，调用LogWritter的sysInfo方<br>
	 * 法记录存放的实例。
	 * 
	 * @param key:String
	 *            实例名
	 * @return Connection 得到的Connection
	 * @throws NamingException
	 * @throws Exception
	 */
	public DataSource getDataSourceInstance(String key) throws Exception {
		logger.debug("getDataSourceInstance获得连接对象..........." + key);
		if (dsMap.containsKey(key)) { // 如果存在于缓存中，返回缓存
			try {
				DataSource ds = (DataSource) dsMap.get(key);
				return ds;
			} // 缓存的内容过期，重新查找
			catch (Exception ex) {
				clear(key);
				try {
					DataSource ds = (DataSource) lookupDs(key);
					dsMap.put(key, ds);
					return ds;
				} catch (NamingException ex1) {
					logger.error(ex1.getMessage(), ex1);
					throw new Exception("00002");
				} catch (Exception exOther1) {
					logger.error(exOther1.getMessage(), exOther1);
					throw new Exception("00003");
				}
			}
		} else { // 如果不存在于缓存中，查找实例
			try {
				DataSource ds = (DataSource) lookupDs(key);
				dsMap.put(key, ds);
				return ds;
			} catch (NamingException ex2) {
				logger.error(ex2.getMessage(), ex2);
				throw new Exception("00005");
			} catch (Exception exOther2) {
				logger.error(exOther2.getMessage(), exOther2);
				throw new Exception("00006");
			}
		}
	}

	/**
	 * 用来根据实例的jndi名得到DataSource实例。方法会先在dsMap属性<br>
	 * 中查找是否有给实例的缓存。如果有，则用实例生成Connection实例<br>
	 * 并返回。如果生成出错，则调用clear方法，然后调用lookup方法找到<br>
	 * 该DataSource实例，放入dsMap中，调用LogWritter的sysInfo方<br>
	 * 法记录存放的实例。如果缓存中没有该实例，调用lookup方法找到<br>
	 * 该DataSource实例，放入dsMap中，调用LogWritter的sysInfo方<br>
	 * 法记录存放的实例。
	 * 
	 * @param key:String
	 *            实例名
	 * @return Connection 得到的Connection
	 * @throws NamingException
	 * @throws Exception
	 */
	public Connection getConnectionInstance(String key) throws Exception {
		logger.debug("DSLocator获得连接对象..........." + key);
		if (dsMap.containsKey(key)) { // 如果存在于缓存中，返回缓存
			try {
				DataSource ds = getDataSourceInstance(key);
				Connection con = ds.getConnection();
				return con;
			} // 缓存的内容过期，重新查找
			catch (SQLException ex) {
				clear(key);
				Connection con = null;
				try {
					DataSource ds = getDataSourceInstance(key);
					dsMap.put(key, ds);
					con = ds.getConnection();
				} catch (SQLException ex1) {
					logger.error(ex1.getMessage(), ex1);
					throw new Exception("00001"); //
				} catch (NamingException ex1) {
					logger.error(ex1.getMessage(), ex1);
					throw new Exception("00002");
				} catch (Exception exOther1) {
					logger.error(exOther1.getMessage(), exOther1);
					throw new Exception("00003");
				}
				return con;
			}
		} else { // 如果不存在于缓存中，查找实例
			Connection con = null;
			try {
				DataSource ds = getDataSourceInstance(key);
				dsMap.put(key, ds);
				con = ds.getConnection();
			} catch (SQLException ex2) {
				logger.error(ex2.getMessage(), ex2);
				throw new Exception("00004");
			} catch (NamingException ex2) {
				logger.error(ex2.getMessage(), ex2);
				throw new Exception("00005");
			} catch (Exception exOther2) {
				logger.error(exOther2.getMessage(), exOther2);
				throw new Exception("00006");
			}
			return con;
		}
	}

	/**
	 * getInstance方法的实现。
	 * 
	 * @param name:String
	 *            被查找对象的JNDI名
	 * @return Object - JNDI得到的对象
	 * @throws NamingException
	 */
	protected Object lookupDs(String name) throws NamingException {
		Context ctx = getDsContext();
		Object obj = ctx.lookup(name);
		return obj;
	}

	/**
	 * 清空DSMap属性中的所用缓存实例。然后调用LogWritter的<br>
	 * 静态方法sysInfo,在日志中记录缓存已经清空的信息。
	 */
	private void clear(String key) {
		if (dsMap.containsKey(key)) {
			dsMap.remove(key);
		}
	}

	/**
	 * 实现方法，用来得到Context实例。这个方法首先会调用getPorperties方法<br>
	 * 得到一个java.util.Porperties的实例，然后再根据Porperties的实例得<br>
	 * 到一个java.util.Context类的实例，并返回。
	 * 
	 * @return Context - 生成的Context类的实例。
	 * @throws NamingException
	 */
	private Context getDsContext() throws NamingException {
		Context ctx = new InitialContext();
		return ctx;
	}

}