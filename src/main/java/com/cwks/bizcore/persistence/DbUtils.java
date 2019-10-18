package com.cwks.bizcore.persistence;

import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

//import weblogic.jdbc.extensions.WLConnection;
//import com.css.sword.platform.comm.log.LogFactory;
//import com.css.sword.platform.comm.log.LogWritter;

/**
 * 数据库工具类，为表的增删改操作提供便捷方法，减少SQL语句的编写
 * <p>Title:DbUtils.java</p>
 * <p>Description:江苏地税----风险管理</p>
 * <p>Copyright: Copyright (c) 2010-2015</p>
 * <p>Company: CS&S</p>
 * @Function 功能描述：
 * @Author Administrator
 * @Version 1.0
 * @Date 2012-05-14
 */
public class DbUtils {
	
//	private final static LogWritter logger = LogFactory.getLogger(DbUtils.class);
	private static Logger logger = LoggerFactory.getLogger(DbUtils.class);
	
	/**
	 * 根据whereValue 删除表的记录
	 * @Method deleteRecords
	 * @Function 功能描述：
	 * @param tableName    表名
	 * @param whereValue   删除条件 （字段与对应值） 不能为空
	 * @param conn		   数据库链接 可以通过persistencdDAO.getConnection()方法获取
	 * @return
	 * @Date 2010-11-24
	 */
	public static int deleteRecords(String tableName,Map whereValue,Connection conn){
		if(TycxUtils.isEmpty(tableName) || TycxUtils.isEmpty(whereValue)){
			logger.error("表名或条件为空，不满足删除条件!!");
			return 0;
		}
		List values = new ArrayList();
		StringBuffer buffer = new StringBuffer();
        buffer.append("delete from ");
        buffer.append(tableName);
        buffer.append(" where ");
        buffer.append(buildUpdateParams(whereValue,values,"and"));
        logger.debug("删除记录语句是:" + buffer);
        int count = 0;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(buffer.toString());
            preparedParams(pstmt, values);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("删除记录失败！", e);
        } finally {
            try {
				clearRowSetAndStatement(null, pstmt);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            if(pstmt!=null){
            	try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        logger.debug(count + "条记录删除！");
        return count;
	}
	
	private static void clearRowSetAndStatement(ResultSet rowset, Statement stmt) throws SQLException {
       
            if (rowset != null) {
                rowset.close();
            }
      
       
            if (stmt != null) {
                stmt.close();
            }
        
    }
	/**
	 * 根据whereValue与tableName将表字段更新成newValue
	 * @Method update
	 * @Function 功能描述：可能存在多行被更新
	 * @param tableName    表名
	 * @param newValue     字段及更新后的值
	 * @param whereValue   更新条件：字段及对应值
	 * @param conn         数据库链接 可以通过persistencdDAO.getConnection()方法获取
	 * @return
	 * @Date 2010-11-24
	 */
	public static int updateRecords(String tableName,Map newValue,Map whereValue,Connection conn){
		//1、生成update **** set **=? and **=? where **id1= ?  and id2=?
        //2、为？号赋值
		if(TycxUtils.isEmpty(newValue)||TycxUtils.isEmpty(whereValue)){
			logger.error("表名或值或条件为空，不满足更新条件!!");
			return 0;//
		}
		List values = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append(" update ");
		sb.append(tableName);
		sb.append(" set ");
		sb.append(buildUpdateParams(newValue,values,","));
		sb.append(" where ");
		sb.append(buildUpdateParams(whereValue,values,"and"));//通过原先的值判断该条记录

		logger.debug("更新记录时生成的SQL语句是:" + sb.toString());

		//下面生成为参数赋值 执行update的sql语句
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sb.toString());
			preparedParams(pstmt, values);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("更新表记录失败!", e);
		} finally {
			try {
				clearRowSetAndStatement(null, pstmt);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		logger.debug(count + "条记录更新！");
		return count;
	}
	//link 是连接符 如，或 and
	private static String buildUpdateParams(Map newValue, List values,String link) {
		if (newValue.size() == 0) {
            return "";
        }
        List list = new ArrayList(newValue.keySet());
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<list.size();i++) {
            String key = (String) list.get(i);
            values.add(newValue.get(key));
            if (i<list.size()-1) {
				sb.append(key).append("=? ").append(link).append(" ");
			}else{
				sb.append(key).append("=? ");
			}
        }

        return sb.toString(); //去掉第一个","号
	}

	/**
	 * @Method insertRecord
	 * @Function 功能描述：插入一条记录
	 * @param tableName 表名
	 * @param record    记录的Map形式 key为字段名 value为字段值
	 * @param conn      数据库链接 可以通过persistencdDAO.getConnection()方法获取
	 * @return
	 * @Date 2010-11-24
	 */
	public static int insertRecord(String tableName,Map record,Connection conn){
		//1、生成insert into tableName (column1,column2.....) values (?,?......)
        //2、为？号赋值
		if(TycxUtils.isEmpty(record)||TycxUtils.isEmpty(tableName)){
			logger.error("表名或记录为空，不执行插入语句!!");
			return 0;//
		}
		int result = 0;
		List values = new ArrayList();
		String sql = buildInsertSql(tableName,record,values);

        logger.debug("增加记录时生成的SQL语句是:" + sql);

        //下面生成为参数赋值 执行update的sql语句
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            preparedParams(pstmt, values);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("增加表记录失败!", e);
        } finally {
        	try {
				clearRowSetAndStatement(null, pstmt);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        logger.debug(result + "条记录增加！");

		return result;
	}

	private static void preparedParams(PreparedStatement pstmt, List values) {
		for (int i = 0; i < values.size();) {
            Object keyValue = (Object) values.get(i);
            try {
				pstmt.setObject(++i, classCast(keyValue,pstmt));
			} catch (SQLException e) {
				throw new RuntimeException("对索引为["+i+"]的对象赋值失败，根据日志中的SQL语句能判断是哪个字段!",e);
			}
        }
	}
	
	/**
	 * 根据对象类型造型 增加对大字断的定义 其他维持不变
	 * 
	 * @param classType
	 *            造型结果类型
	 * @param obj
	 *            要造型的对象
	 * @param pstmt 
	 * @return Object 造型结果
	 * @throws SQLException 
	 */
	public static Object classCast(Object obj, PreparedStatement pstmt) throws SQLException {
//		if(obj instanceof BlobParam){
//			Connection conn = pstmt.getConnection();
//			BlobParam bp = (BlobParam) obj;
//			return bp.getBlob(conn);
//		}
//		if(obj instanceof ClobParam){
//			Connection conn = pstmt.getConnection();
//			ClobParam bp = (ClobParam) obj;
//			return bp.getClob(conn);
//		}
		//暂时按传入的对象直接set
		return obj;
	}

	private static String buildInsertSql(String tableName, Map record, List values) {
		StringBuffer sb = new StringBuffer();
        sb.append(" insert into ");
        sb.append(tableName);
        sb.append(" (");
        sb.append(buildInsertParams(record,values));
        sb.append(") ");
        sb.append(" values (");
        sb.append(buildInsertValues(record.size()));
        sb.append(") ");
        
        return sb.toString();
	}
	private static String buildInsertParams(Map record, List values) {
        StringBuffer buffer = new StringBuffer();
        Set keySet = record.keySet();
        for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			buffer.append(",").append(key);
			values.add(record.get(key));
		}
        
        return buffer.substring(1);//去掉第一个逗号
    }
	private static String buildInsertValues(int count) { //count 是?的数量
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < count; i++) {
            buffer.append(",").append("?");
        }
        return buffer.substring(1);
    }

	public static Connection getPrimaryConnection(Connection con) throws SQLException{
		//Connection connection;
		/*if (con instanceof WLConnection) {
            //这里将实际的连接类型转换
			connection = ((WLConnection) con).getVendorConnection();
            //logger.debug("After WLConnection Convert, The Class Type of con is :: "+con.getClass());
			connection = con.getMetaData().getConnection();
            logger.debug("After WLConnection Convert And getMetaData.getConnection, The Class Type of con is :: "+con.getClass());
        } else {
            //通过元数据取的原生connection
        	connection = con.getMetaData().getConnection();
            if(connection instanceof weblogic.jdbc.wrapper.Connection){
            	connection = ((weblogic.jdbc.wrapper.Connection)connection).getVendorConnection();
            	logger.debug("After getVendorConnection, The Class Type of con is :: "+con.getClass());
            }
        }*/
		
//		return connection;
		return con;
	}
}
