package com.cwks.bizcore.utils;

import com.cwks.common.util.db.oracl.StoredProcManager;
import com.cwks.common.util.db.oracl.StoredProcParamObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
    private static Logger logger = LoggerFactory.getLogger(DBUtil.class);

    /**
     * 执行sql 语句 动态参数
     * @param conn 数据库连接
     * @param sql  sql语句
     * @param objects 多个参数
     * @return  int
     */
    public static int excuteUpdate(Connection conn,String sql, Object... objects) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getStateMent(conn, sql, objects);
            return preparedStatement.executeUpdate(); //执行sql并返回结果
        } catch (SQLException e) {
            logger.error("DBUtil.excuteUpdate error: ",e);
        } finally {
            close(preparedStatement, null);
        }
        return 0;
    }

    /**
     * 执行批量sql 语句
     * @param conn 数据库连接
     * @param sqlList  多条sql语句
     * @return  ResultSet
     */
    public static boolean excuteBatch(Connection conn,ArrayList sqlList) {
        Statement stmt = null;
        try {
            conn.setAutoCommit(false);
            stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            for(int i = 0; i < sqlList.size();i++){
                stmt.addBatch((String)sqlList.get(i));
            }
            stmt.executeBatch();
            conn.commit();
            return true; //执行sql并返回结果
        } catch (SQLException e) {
            logger.error("DBUtil.excuteBatch error: ",e);
        } finally {
            close(stmt, null);
        }
        return false;
    }

    /**
     * 执行单条sql 语句
     * @param conn 数据库连接
     * @param sql  查询语句
     * @return  ResultSet
     */
    public static int update(Connection conn,String sql) {
        PreparedStatement ps = null;
        try {
            //创建执行SQL的prepareStatement对象
            ps = conn.prepareStatement(sql);
            //用于增删改操作
            int result = ps.executeUpdate();
            return result;
        } catch (SQLException e) {
            logger.error("DBUtil.update error: ",e);
        } finally {
            close(ps, null);
        }
        return 0;
    }

    /**
     * 查询单条记录
     * @param conn 数据库连接
     * @param sql  查询语句
     * @return  ResultSet
     */
    public static ResultSet queryForResultSet(Connection conn,String sql) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //创建执行SQL的prepareStatement对象
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            logger.error("DBUtil.excuteQuery error: ",e);
        } finally {
            close(ps, rs);
        }
        return null;
    }


    /**
     * 查询单条记录
     *
     * @param sql  查询语句
     * @param clazz 返回对象的class
     * @param objects 需要的参数，必须跟sql占位符的位置一一对应
     * @param <T>   泛型返回
     * @return      返回单个对象
     */
    public static <T> T queryForObject(Connection connection,String sql, Class<T> clazz, Object... objects) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        T object = null;
        try {
            preparedStatement = getStateMent(connection, sql, objects);
            resultSet = getResultSet(preparedStatement);
            if (resultSet.next()) {
                object = invokeObject(resultSet, clazz);
            }

        } catch (SQLException | IllegalAccessException | InstantiationException
                | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            logger.error("DBUtil.queryForObject error: ",e);
        } finally {
            close(preparedStatement, resultSet); //记得关闭
        }
        return object;
    }

    /**
     *查询多条记录
     *
     * @param sql  查询语句
     * @param clazz 返回对象的class
     * @param objects 需要的参数，必须跟sql占位符的位置一一对应
     * @param <T>   泛型返回
     *
     * @return list
     */
    public static <T> List<T> queryForList(Connection connection,String sql, Class<T> clazz, Object... objects) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> list = new ArrayList<>();
        try {
            preparedStatement = getStateMent(connection, sql, objects);
            resultSet = getResultSet(preparedStatement);
            while (resultSet.next()) {
                //调用 invokeObject方法，把一条记录封装成一个对象，添加到list中
                list.add(invokeObject(resultSet, clazz));
            }
        } catch (SQLException | IllegalAccessException | InstantiationException
                | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            logger.error("DBUtil.queryForList error: ",e);
        }  finally {
            close(preparedStatement, resultSet);
        }

        return list.size() > 0 ? list : null;

    }

    /**
     *调用存储过程
     *
     * @param procName  存储过程名
     * @param sqlParams 调用过程参数
     * @param commit 是否自动提交
     *
     * @return list
     */
    public ArrayList<?> callStoreProcess(Connection conn, String procName, ArrayList<StoredProcParamObj> sqlParams, boolean commit) {
        ArrayList<?> rList = null;
        try {
            conn.setAutoCommit(commit);
            rList = (ArrayList<?>) StoredProcManager.callStoreProcess(conn, "" + procName + "", sqlParams);
        } catch (Exception e) {
            logger.error("DBUtil.callStoreProcess error: ",e);
            try {
                throw e;
            } catch (Exception e1) {
                logger.error("DBUtil.callStoreProcess error2: ",e1);
            }
        } finally {
            try {
                if (conn != null) {
//					if(commit){
//						conn.commit();
//					}
                    //conn.close();
                }
            } catch (Exception ex1) {
                logger.error("DBUtil.callStoreProcess error3: ",ex1);
                try {
                    throw ex1;
                } catch (Exception e) {
                }
            }
        }
        return rList;
    }


    private static void close(PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if(resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            logger.error("DBUtil.close error: ",e);
        }
    }

    private static void close(Statement preparedStatement, ResultSet resultSet) {
        try {
            if(resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();

            }
        } catch (SQLException e) {
            logger.error("DBUtil.close error: ",e);
        }
    }

    /**
     * 把数据库中的一条记录通过反射包装成相应的Bean
     * @param resultSet
     * @param clazz
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private static <T> T invokeObject(ResultSet resultSet, Class<T> clazz) throws IllegalAccessException, InstantiationException,
            SQLException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        T object = clazz.getDeclaredConstructor().newInstance();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 0, count = metaData.getColumnCount(); i < count; i++) {
            String columnName = metaData.getColumnName(i + 1);     //数据库返回结果的列名
            String fieldName = camelName(columnName); //去掉列名中的下划线“_”并转为驼峰命名
            Field field = clazz.getDeclaredField(fieldName);            //根据字段名获取field
            String methName = setMethodName(fieldName);         //拼set方法名
            Class type = field.getType();                       //获取字段类型
            Method setMethod = clazz.getDeclaredMethod(methName, field.getType());
            Object value = resultSet.getObject(i + 1);            //获取字段值
            setMethod.invoke(object, type.cast(value));       //强转并且赋值
        }
        return object;
    }

    private static PreparedStatement getStateMent(Connection connection, String sql, Object... objects) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0, len = objects.length; i < len; i++) {
                preparedStatement.setObject(i + 1, objects[i]);  //给sql每个？占位符填上数据
            }
        }catch (Exception e){

        }finally {
            if(preparedStatement != null){
                preparedStatement.close();
            }
        }
        return preparedStatement;
    }

    private static ResultSet getResultSet(PreparedStatement statement) throws SQLException {
        if (statement == null) {
            return null;
        } else {
            return statement.executeQuery();
        }
    }

    private static String setMethodName(String str) {
        return "set" + firstUpperCase(str);
    }


    /**
     * 转为驼峰命名
     * @param str
     * @return string
     */
    public static String camelName(String str) {
        if (!isEmpty(str)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0, len = str.length(); i < len; i++) {
                if (str.charAt(i) == '_') {
                    while (str.charAt(i + 1) == '_') {
                        i++;
                    }
                    stringBuilder.append(("" + str.charAt(++i)).toUpperCase());
                } else {
                    stringBuilder.append(str.charAt(i));
                }
            }
            return stringBuilder.toString();
        }
        return str;
    }

    /**
     * 判断是否为空串
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (str != null && str.length() > 0) {
            for (int i = 0, len = str.length(); i < len; i++) {
                if (!Character.isSpaceChar(str.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否为空串 ？！！！ 我怎么又写了个一样的方法？！！！
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


    /**
     * 将第一个字母替换为大写
     * @param str
     * @return
     */
    public static String firstUpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }
}