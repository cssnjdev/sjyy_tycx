package com.cwks.bizcore.utils;

import com.cwks.common.core.systemConfig.SystemContext;
import com.cwks.common.util.db.BeanUtils;
import com.cwks.common.util.db.ext.SqlUtils;
import com.cwks.common.util.db.oracl.StoredProcManager;
import com.cwks.common.util.db.oracl.StoredProcParamObj;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.persistence.Table;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//连接池的工具类
public class DataSourceUtil {
    private static Logger logger = LoggerFactory.getLogger(DataSourceUtil.class);

    // 声明变量 static
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public DataSourceUtil() {
        super();
    }

    public static void main(String[] args) {
        try {
            /*LinkedHashMap resMap = new LinkedHashMap();
            DataSourceUtils aaa = new DataSourceUtils("DB_JSSW_TEST02");
            DataSource aaa_ds = aaa.getDataSource();
            DataSourceUtils bbb = new DataSourceUtils("DB_JSSW_TEST04");
            DataSource bbb_ds = aaa.getDataSource();
            resMap.put("DB_JSSW_TEST02", aaa);
            resMap.put("DB_JSSW_TEST04", bbb);
            DataSourceUtils dataSourceUtils = (DataSourceUtils) resMap.get("DB_JSSW_TEST02");
            DataSourceUtils dataSourceUtils2 = (DataSourceUtils) resMap.get("DB_JSSW_TEST04");
            Object o1 = dataSourceUtils.getDataSource();
            Object o2 = dataSourceUtils2.getDataSource();

            aaa = new DataSourceUtils("DB_JSSW_TEST02");
            aaa_ds = aaa.getDataSource();
            bbb = new DataSourceUtils("DB_JSSW_TEST04");
            bbb_ds = aaa.getDataSource();
            resMap.put("DB_JSSW_TEST02", aaa);
            resMap.put("DB_JSSW_TEST04", bbb);
            dataSourceUtils = (DataSourceUtils) resMap.get("DB_JSSW_TEST02");
            dataSourceUtils2 = (DataSourceUtils) resMap.get("DB_JSSW_TEST04");
            o1 = dataSourceUtils.getDataSource();
            o2 = dataSourceUtils2.getDataSource();
            System.out.println(aaa.toString());*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public DataSourceUtil(String dataSourceId) throws Exception {
        initializeDbConnection(dataSourceId);
    }

    static {
        // 初始化连接池（仅一次）
        /*// 加载 druid.properteis 使用类加载
        System.out.println(DataSourceUtils.class.getClassLoader());
        InputStream is = DataSourceUtils.class.getClassLoader().getResourceAsStream("druid.properties");

        // 创建连接池对象 使用工具类
        try {
            // 创建配置文件对象
            Properties properties = new Properties();
            // 加载io流 key = value
            properties.load(is);
            // 使用第三方连接池 druid
            Map pro = new HashMap();
            //dataSource = DruidDataSourceFactory.createDataSource(properties);
            dataSource = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void initializeDbConnection(String dataSourceId) throws Exception {
        // 创建连接池对象 使用工具类
        try {
            DBPoolConnection pool = new DBPoolConnection();
            dataSource = pool.getDruidDataSource(dataSourceId);
            jdbcTemplate = new JdbcTemplate(dataSource);
        } catch (Exception e) {
            logger.debug("初始化数据源异常：");
            throw new Exception("初始化数据源异常：" + e.getMessage());
        }
    }

    // 提供获取连接池的方法
    public DataSource getDataSource() {
        return dataSource;
    }

    public void closeConnection() {
        if (dataSource != null) {
            try {
                dataSource.getConnection().close();
                dataSource = null;
                jdbcTemplate = null;
                logger = null;
            } catch (SQLException e) {
                logger.debug("执行all closeConnection异常：", e);
            }
        }
    }

    // 提供获取连接的方法
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // 提供关闭资源的方法【connection是归还到连接池】
    // 提供关闭资源的方法 【方法重载】3 dql
    public void closeResource(ResultSet resultSet, Statement statement, Connection connection) {
        // 关闭结果集
        // ctrl+alt+m 将java语句抽取成方法
        closeResultSet(resultSet);
        // 关闭语句执行者
        closeStatement(statement);
        // 关闭连接
        closeConnection(connection);
    }

    // 提供关闭资源的方法 【方法重载】 2 dml
    public void closeResource(Statement statement, Connection connection) {
        // 关闭语句执行者
        closeStatement(statement);
        // 关闭连接
        closeConnection(connection);
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                jdbcTemplate = null;
            } catch (SQLException e) {
                logger.debug("执行closeConnection异常：", e);
            }
        }
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.debug("执行closeStatement异常：", e);
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.debug("执行closeResultSet异常：", e);
            }
        }
    }


    public void execute(String sql) {
        // jhlogger.debug("execute ddl sql");
        try {
            jdbcTemplate.execute(sql);
        } catch (RuntimeException re) {
            logger.debug("execute ddl sql failed", re);
            throw re;
        }
    }

    public void commit() {
        // jhlogger.debug("execute ddl sql");
        try {
            Connection conn = getConnection();
            conn.commit();
        } catch (RuntimeException re) {
            logger.debug("closeConnection failed", re);
            throw re;
        } catch (SQLException e) {
            logger.debug("closeConnection failed", e);
            try {
                throw e;
            } catch (SQLException e1) {
            }
        }
    }

    public ArrayList<?> callStoreProcess(String procName, ArrayList<StoredProcParamObj> sqlParams, boolean commit) {
        ArrayList<?> rList = null;
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(commit);
            rList = (ArrayList<?>) StoredProcManager.callStoreProcess(conn, "" + procName + "", sqlParams);
        } catch (Exception e) {
            logger.debug("callStoreProcess failed", e);
            try {
                throw e;
            } catch (Exception e1) {
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
                logger.debug("callStoreProcess failed", ex1);
                try {
                    throw ex1;
                } catch (Exception e) {
                }
            }
        }
        return rList;
    }

    @SuppressWarnings("rawtypes")
    public Map callStoreProcess(String procName, ArrayList<StoredProcParamObj> sqlParams, Map<String, Object> procedureMap) {
        Map returnMap = null;
        this.jdbcTemplate.setResultsMapCaseInsensitive(true);
        SimpleJdbcCall procReadActor = new SimpleJdbcCall(this.jdbcTemplate).withoutProcedureColumnMetaDataAccess();
        procReadActor.setProcedureName(procName);
        if (sqlParams != null && sqlParams.size() > 0) {
            SqlParameter sqlParameter = null;
            SqlOutParameter sqlOutParameter = null;
            SqlInOutParameter sqlInOutParameter = null;
            StoredProcParamObj obj = null;
            for (int i = 0; i < sqlParams.size(); i++) {
                obj = (StoredProcParamObj) sqlParams.get(i);
                if (obj.getParamType().equals(StoredProcParamObj.IN)) {
                    sqlParameter = new SqlParameter((String) obj.getValue(), obj.getDataType());
                    procReadActor.addDeclaredParameter(sqlParameter);
                } else if (obj.getParamType().equals(StoredProcParamObj.OUT)) {
                    sqlOutParameter = new SqlOutParameter((String) obj.getValue(), obj.getDataType());
                    procReadActor.addDeclaredParameter(sqlOutParameter);
                } else if (obj.getParamType().equals(StoredProcParamObj.INOUT)) {
                    sqlInOutParameter = new SqlInOutParameter((String) obj.getValue(), obj.getDataType());
                    procReadActor.addDeclaredParameter(sqlInOutParameter);
                }
            }
            SqlParameterSource in = new MapSqlParameterSource().addValues(procedureMap);
            returnMap = procReadActor.execute(in);
        } else {
            SqlParameterSource in = new MapSqlParameterSource().addValues(new HashMap<String, Object>());
            returnMap = procReadActor.execute(in);
        }
        return returnMap;
    }

    public String getSql(String sqlKey) {
        return SystemContext.getSql(sqlKey);
    }

    /**
     * 执行更新,删除等 sql语句。
     */
    public void update(String sql) {
        // jhlogger.debug("execute sql");
        try {
            jdbcTemplate.update(sql);
        } catch (RuntimeException re) {
            logger.debug("execute sql failed", re);
            throw re;
        }
    }

    /**
     * 执行带参数更新,删除等 sql语句。
     */
    public void update(String sql, ArrayList param) {
        // jhlogger.debug("execute sql with param");
        try {
            jdbcTemplate.update(sql, param.toArray());
        } catch (RuntimeException re) {
            logger.debug("execute sql with param failed", re);
            throw re;
        }
    }

    /**
     * 批量执行sql语句。
     */
    public boolean batchUpdate(ArrayList sqllist) {
        // jhlogger.debug("batchUpdate sql");
        try {
            int[] intReturn = jdbcTemplate.batchUpdate((String[]) sqllist.toArray(new String[0]));
            for (int i = 0; i < intReturn.length; i++) {
                if (intReturn[i] < 0)
                    return false;
            }
            return true;
        } catch (RuntimeException re) {
            logger.debug("batchUpdate sql failed", re);
            throw re;
        }
    }

    /**
     * 执行查询语句返回含map的list,map中的字段名为key。
     */
    public List queryforlist(String sql) {
        // jhlogger.debug("queryforlist");
        List returnlist = null;
        try {
            returnlist = jdbcTemplate.queryForList(sql);
            // jhlogger.debug("queryforlist successful");
        } catch (RuntimeException re) {
            logger.debug("queryforlist failed", re);
            throw re;
        }
        return returnlist;
    }

    /**
     * 执行带参数的查询语句返回含map的list,map中的字段名为key。
     */
    public List queryforlist(String sql, ArrayList param) {
        // jhlogger.debug("queryforlist with param");
        List returnlist = null;
        try {
            returnlist = jdbcTemplate.queryForList(sql, param.toArray());
        } catch (RuntimeException re) {
            logger.debug("queryforlist with param failed", re);
            throw re;
        }
        return returnlist;
    }

    /**
     * 执行查询语句返回含map的list,map中的字段名为key。
     */
    public List queryforlist(String sql, Map<String, Object> paramMap) {
        List<?> returnlist = null;
        try {
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
            returnlist = namedParameterJdbcTemplate.queryForList(sql, paramMap);
            // jhlogger.debug("queryforlist successful");
        } catch (RuntimeException re) {
            logger.debug("queryforlist failed", re);
            throw re;
        }
        return returnlist;
    }

    public List queryforlist(String sql, ArrayList param, Class returnClass) {
        // jhlogger.debug("queryforlist with param");
        List returnlist = null;
        param = param == null ? new ArrayList() : param;
        try {
            // RowMapper<Class> rm =
            // ParameterizedBeanPropertyRowMapper.newInstance(returnClass);
            RowMapper<Class> rowMapper = new BeanPropertyRowMapper<Class>(returnClass);
            returnlist = jdbcTemplate.query(sql, param.toArray(), rowMapper);
        } catch (RuntimeException re) {
            logger.debug("queryforlist with param failed", re);
            throw re;
        }
        return returnlist;
    }

    /**
     * 执行查询语句返回一条记录的map,map中的字段名为key。
     */
    public Map queryformap(String sql) {
        // jhlogger.debug("queryformap");
        try {
            return jdbcTemplate.queryForMap(sql);
        } catch (RuntimeException re) {
            logger.debug("queryformap failed", re);
            throw re;
        }
    }

    /**
     * 执行带参数的查询语句返回一条记录的map,map中的字段名为key。
     */
    public Map queryformap(String sql, ArrayList param) {
        // jhlogger.debug("queryformap");
        try {
            return jdbcTemplate.queryForMap(sql, param.toArray());
        } catch (RuntimeException re) {
            logger.debug("queryformap with param failed", re);
            throw re;
        }
    }

    /**
     * 执行查询语句返回SqlRowSet。
     */
    public SqlRowSet queryforRowset(String sql) {
        // jhlogger.debug("queryforRowset");
        try {
            return jdbcTemplate.queryForRowSet(sql);
        } catch (RuntimeException re) {
            logger.debug("queryforRowset failed", re);
            throw re;
        }
    }

    /**
     * 执行带参数的查询语句返回SqlRowSet。
     */
    public SqlRowSet queryforRowset(String sql, ArrayList param) {
        // jhlogger.debug("queryforRowset with param");
        try {
            return jdbcTemplate.queryForRowSet(sql, param.toArray());
        } catch (RuntimeException re) {
            logger.debug("queryforRowset with param failed", re);
            throw re;
        }
    }

    /**
     * 执行查询语句返回一个结果对象,如count等聚合函数,根据返回结果的类型对object进行转换。
     */
    public Object queryForObject(String sql, Class returnClass) {
        // jhlogger.debug("queryForObject");
        try {
            return jdbcTemplate.queryForObject(sql, returnClass);
        } catch (RuntimeException re) {
            logger.debug("queryForObject failed", re);
            throw re;
        }
    }

    /**
     * 执行带参数的查询语句返回一个结果对象,如count等聚合函数,根据返回结果的类型对object进行转换。
     */
    public Object queryForObject(String sql, ArrayList param, Class returnClass) {
        // jhlogger.debug("queryForObject with param");
        try {
            RowMapper<Class> rm = new BeanPropertyRowMapper<Class>(returnClass);
            return jdbcTemplate.queryForObject(sql, param.toArray(), rm);
        } catch (RuntimeException re) {
            logger.debug("queryForObject with param failed", re);
            throw re;
        }
    }

    /**
     * 分页查询
     *
     * @param sql      查询的sql语句
     * @param args     参数
     * @param start    开始页数
     * @param end      结束页数
     * @param pagerows 每页条数
     * @return List<Map       <       String       ,               Object>>
     */
    public List<Map<String, Object>> queryPage(String sql, Object[] args, int start, int end, int pagerows) {
        if (start <= 0 && end <= 0) {
            return (List<Map<String, Object>>) jdbcTemplate.queryForList(sql, args);
        }
        if (start <= 1 && end <= 1) {
            sql = getLimitString(sql, false);
            args = ArrayUtils.add(args, args.length, pagerows);
        } else {
            sql = getLimitString(sql, true);
            args = ArrayUtils.add(args, args.length, end * pagerows);
            args = ArrayUtils.add(args, args.length, (start - 1) * pagerows);
        }
        return (List<Map<String, Object>>) jdbcTemplate.queryForList(sql, args);
    }

    /**
     * 分页查询
     *
     * @param sql      查询的sql语句
     * @param start    开始页数
     * @param end      结束页数
     * @param pagerows 每页条数
     * @return
     */
    public List<Map<String, Object>> queryPage(String sql, int start, int end, int pagerows) {
        Object[] args = new Object[]{};
        return queryPage(sql, args, start, end, pagerows);
    }

    /**
     * 分页查询
     *
     * @param sql      查询的sql语句
     * @param start    开始页数
     * @param end      结束页数
     * @param pagerows 每页条数
     * @return
     */
    public <T> List<T> queryPage(String sql, int start, int end, int pagerows, RowMapper<T> rowMapper) throws DataAccessException {
        if (start <= 0 && end <= 0) {
            return jdbcTemplate.query(sql, rowMapper);
        }
        Object[] args = new Object[]{};
        if (start <= 1 && end <= 1) {
            sql = getLimitString(sql, false);
            args = ArrayUtils.add(args, args.length, pagerows);
        } else {
            sql = getLimitString(sql, true);
            args = ArrayUtils.add(args, args.length, end * pagerows);
            args = ArrayUtils.add(args, args.length, (start - 1) * pagerows);
        }
        Pattern pattern = Pattern.compile("\\?");
        Matcher matcher = pattern.matcher(sql);
        for (int i = 0; i < args.length; i++, matcher = pattern.matcher(sql)) {
            sql = matcher.replaceFirst(args[i].toString());
        }
        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * 分页查询
     *
     * @param sql   查询的sql语句
     * @param args  参数
     * @param start 起始行
     * @param limit 获取的行数
     * @return List<Map       <       String       ,               Object>>
     * @throws DataAccessException
     */
    public List<Map<String, Object>> queryPage(String sql, Object[] args, int start, int limit) {
        if (start <= 0 && limit <= 0) {
            return (List<Map<String, Object>>) jdbcTemplate.queryForList(sql, args);
        }
        if (start <= 1) {
            sql = getLimitString(sql, false);
            args = ArrayUtils.add(args, args.length, limit);
        } else {
            sql = getLimitString(sql, true);
            args = ArrayUtils.add(args, args.length, start + limit);
            args = ArrayUtils.add(args, args.length, start);
        }
        return (List<Map<String, Object>>) jdbcTemplate.queryForList(sql, args);
    }

    /**
     * 分页查询
     *
     * @param sql   查询的sql语句
     * @param start 起始行
     * @param limit 获取的行数
     * @return
     */
    public List<Map<String, Object>> queryPage(String sql, int start, int limit) {
        Object[] args = new Object[]{};
        return queryPage(sql, args, start, limit);
    }

    /**
     * 分页查询
     *
     * @param sql       查询的sql语句
     * @param start     起始行
     * @param limit     获取的行数
     * @param rowMapper
     * @return
     */
    public <T> List<T> queryPage(String sql, int start, int limit, RowMapper<T> rowMapper) throws DataAccessException {
        if (start <= 0 && limit <= 0) {
            return jdbcTemplate.query(sql, rowMapper);
        }
        Object[] args = new Object[]{};
        if (start <= 1) {
            sql = getLimitString(sql, false);
            args = ArrayUtils.add(args, args.length, limit);
        } else {
            sql = getLimitString(sql, true);
            args = ArrayUtils.add(args, args.length, start + limit);
            args = ArrayUtils.add(args, args.length, start);
        }
        Pattern pattern = Pattern.compile("\\?");
        Matcher matcher = pattern.matcher(sql);
        for (int i = 0; i < args.length; i++, matcher = pattern.matcher(sql)) {
            sql = matcher.replaceFirst(args[i].toString());
        }
        return jdbcTemplate.query(sql, rowMapper);
    }

    private static String getLimitString(String sql, boolean hasOffset) {
        sql = sql.trim();
        boolean isForUpdate = false;
        if (sql.toLowerCase().endsWith(" for update")) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        if (hasOffset) {
            pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
        } else {
            pagingSelect.append("select * from ( ");
        }
        pagingSelect.append(sql);
        if (hasOffset) {
            pagingSelect.append(" ) row_ where rownum <= ?) where rownum_ > ?");
        } else {
            pagingSelect.append(" ) where rownum <= ?");
        }
        if (isForUpdate) {
            pagingSelect.append(" for update");
        }
        return pagingSelect.toString();
    }

    /**
     * 根据sql语句自动设置对象 数据库中字段形式为aa_bb,类中属性要为aaBb
     *
     * @throws Exception
     */
    public Object autoSetForm(String sql, Object frm) throws Exception {
        // jhlogger.debug("AutoSetForm");
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = jdbcTemplate.getDataSource().getConnection().createStatement();
            rs = stmt.executeQuery(sql);
            String recordValue = "";
            int columnType;
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                java.text.SimpleDateFormat fat = null;
                for (int i = 1; i <= columnCount; i++) {
                    columnType = rsmd.getColumnType(i);
                    switch (columnType) {
                        case Types.CHAR:
                            recordValue = rs.getString(rsmd.getColumnName(i));
                            break;
                        case Types.VARCHAR:
                            recordValue = rs.getString(rsmd.getColumnName(i));
                            break;
                        case Types.DATE:
                            if (!rs.wasNull())
                                recordValue = rs.getString(rsmd.getColumnName(i));
                            else
                                recordValue = "";
                            break;
                        case Types.TIMESTAMP:
                            fat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                            if (null != rs.getTimestamp(rsmd.getColumnName(i)))
                                recordValue = fat
                                        .format(new java.util.Date(rs.getTimestamp(rsmd.getColumnName(i)).getTime()));
                            else
                                recordValue = "";
                            break;
                        case Types.FLOAT:
                            rs.getFloat(rsmd.getColumnName(i));
                            if (!rs.wasNull())
                                recordValue = String.valueOf(new Float(rs.getFloat(rsmd.getColumnName(i))));
                            break;
                        case Types.INTEGER:
                            rs.getInt(rsmd.getColumnName(i));
                            if (!rs.wasNull())
                                recordValue = String.valueOf(new Integer(rs.getInt(rsmd.getColumnName(i))));
                            break;
                        case Types.DECIMAL:
                            rs.getDouble(rsmd.getColumnName(i));
                            if (!rs.wasNull())
                                recordValue = String.valueOf(new Double(rs.getDouble(rsmd.getColumnName(i))));
                            break;
                        case Types.DOUBLE:
                            rs.getDouble(rsmd.getColumnName(i));
                            if (!rs.wasNull())
                                recordValue = String.valueOf(new Double(rs.getDouble(rsmd.getColumnName(i))));
                            break;
                        case Types.NUMERIC:
                            rs.getFloat(rsmd.getColumnName(i));
                            if (!rs.wasNull())
                                recordValue = String.valueOf(new Float(rs.getFloat(rsmd.getColumnName(i))));
                            break;
                        case Types.LONGVARCHAR:
                            recordValue = rs.getString(rsmd.getColumnName(i));
                            break;
                        default:
                            break;
                    }
                    Method m = frm.getClass().getMethod(getSetMethodName(rsmd.getColumnName(i)),
                            new Class[]{recordValue.getClass()});
                    m.invoke(frm, new Object[]{recordValue});
                }
            }
        } catch (SQLException ex) {
            logger.debug("AutoSetForm failed by SQLException", ex);
            throw ex;
        } catch (NoSuchMethodException ex) {
            logger.debug("AutoSetForm failed by NoSuchMethodException", ex);
            throw ex;
        } catch (InvocationTargetException ex) {
            logger.debug("AutoSetForm failed by InvocationTargetException", ex);
            throw ex;
        } catch (IllegalAccessException ex) {
            logger.debug("AutoSetForm failed by IllegalAccessException", ex);
            throw ex;
        }finally {
            if(stmt != null){
                stmt.close();
            }
            if(rs != null){
                rs.close();
            }
        }
        return frm;
    }

    /**
     * 根据sql语句自动完成ResultSet对象向ArrayList对象为集合的对象的转化 数据库中字段形式为aa_bb,类中属性要为aaBb
     *
     * @throws Exception
     */
    public ArrayList addObjToList(String sql, String className) throws Exception {
        ArrayList paraList = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = jdbcTemplate.getDataSource().getConnection().createStatement();
            rs = stmt.executeQuery(sql);
            String recordValue = "";
            int columnType;
            Object c1 = null;
            paraList = new ArrayList();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            java.text.SimpleDateFormat fat = null;
            while (rs.next()) {
                c1 = Class.forName(className).newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    columnType = rsmd.getColumnType(i);
                    switch (columnType) {
                        case Types.CHAR:
                            recordValue = rs.getString(rsmd.getColumnName(i));
                            break;
                        case Types.VARCHAR:
                            recordValue = rs.getString(rsmd.getColumnName(i));
                            break;
                        case Types.DATE:
                            rs.getString(rsmd.getColumnName(i));
                            if (!rs.wasNull())
                                recordValue = rs.getString(rsmd.getColumnName(i));
                            else
                                recordValue = "";
                            break;
                        case Types.TIMESTAMP:
                            fat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                            rs.getTimestamp(rsmd.getColumnName(i));
                            if (!rs.wasNull())
                                recordValue = fat
                                        .format(new java.util.Date(rs.getTimestamp(rsmd.getColumnName(i)).getTime()));
                            else
                                recordValue = "";
                            break;
                        case Types.FLOAT:
                            rs.getFloat(rsmd.getColumnName(i));
                            if (!rs.wasNull())
                                recordValue = String.valueOf(new Float(rs.getFloat(rsmd.getColumnName(i))));
                            break;
                        case Types.INTEGER:
                            rs.getInt(rsmd.getColumnName(i));
                            if (!rs.wasNull())
                                recordValue = String.valueOf(new Integer(rs.getInt(rsmd.getColumnName(i))));
                            break;
                        case Types.DECIMAL:
                            rs.getDouble(rsmd.getColumnName(i));
                            if (!rs.wasNull())
                                recordValue = String.valueOf(new Double(rs.getDouble(rsmd.getColumnName(i))));
                            break;
                        case Types.DOUBLE:
                            rs.getDouble(rsmd.getColumnName(i));
                            if (!rs.wasNull())
                                recordValue = String.valueOf(new Double(rs.getDouble(rsmd.getColumnName(i))));
                            break;
                        case Types.NUMERIC:
                            rs.getFloat(rsmd.getColumnName(i));
                            if (!rs.wasNull())
                                recordValue = String.valueOf(new Float(rs.getFloat(rsmd.getColumnName(i))));
                            break;
                        case Types.LONGVARCHAR:
                            recordValue = rs.getString(rsmd.getColumnName(i));
                            break;
                        default:
                            break;
                    }
                    Method m = c1.getClass().getMethod(getSetMethodName(rsmd.getColumnName(i)),
                            new Class[]{recordValue.getClass()});
                    m.invoke(c1, new Object[]{recordValue});
                }
                paraList.add(c1);
            }
            return paraList;
        } catch (SQLException ex) {
            logger.debug("AutoSetForm failed by SQLException", ex);
            throw ex;
        } catch (ClassNotFoundException ex) {
            logger.debug("AutoSetForm failed by ClassNotFoundException", ex);
            throw ex;
        } catch (NoSuchMethodException ex) {
            logger.debug("AutoSetForm failed by NoSuchMethodException", ex);
            throw ex;
        } catch (InvocationTargetException ex) {
            logger.debug("AutoSetForm failed by InvocationTargetException", ex);
            throw ex;
        } catch (IllegalAccessException ex) {
            logger.debug("AutoSetForm failed by IllegalAccessException", ex);
            throw ex;
        } catch (InstantiationException ex) {
            logger.debug("AutoSetForm failed by InstantiationException", ex);
            throw ex;
        }finally {
            if(stmt != null){
                stmt.close();
            }
            if(rs != null){
                rs.close();
            }
        }
    }

    /**
     * 获取set方法名
     */
    public String getSetMethodName(String s) {
        s = s.toLowerCase();
        StringBuffer sb = new StringBuffer("set");
        String[] asParts = s.split("_");
        int iLen = asParts.length;
        for (int i = 0; i < iLen; i++) {
            sb.append(String.valueOf(asParts[i].charAt(0)).toUpperCase());
            sb.append(asParts[i].substring(1, asParts[i].length()));
        }
        return sb.toString();
    }

    /**
     * 含lob类型的单条insert
     *
     * @param bean
     * @param <T>
     * @throws SQLException
     */
    public <T> void insert(final T bean) throws SQLException {
        execute(bean, "1", "");
    }

    /**
     * 含lob类型的单条update
     *
     * @param bean
     * @param whereSql
     * @param <T>
     * @throws SQLException
     */
    public <T> void update(final T bean, String whereSql) throws SQLException {
        execute(bean, "2", whereSql);
    }

    /**
     * 添加/修改
     *
     * @param <T>
     * @param bean
     */
    public <T> void execute(final T bean, String type, String whereSql) throws SQLException {
        Class<?> clazz = bean.getClass();
        Table annotation = (Table) clazz.getAnnotation(Table.class);
        String tableName = annotation.name();
        if (tableName == null || "".equals(tableName)) {
            throw new SQLException(String.format("参数配置有问题，@Table", null));
        }
        Map beanMap = BeanUtils.toMap(bean);

        final LobHandler lobHandler = new DefaultLobHandler();
        final Map<String, Integer> columnTypes = new HashMap<String, Integer>();
        Map<String, String> columnMaps = new HashMap<String, String>();
        Field[] fields = bean.getClass().getDeclaredFields();   //获取所有属性
        for (Field field : fields) {
            columnTypes.put(field.getName(), BeanUtils.getPmcColumnType(field));
            System.out.print(field.getName() + ":" + BeanUtils.getPmcColumnType(field) + "\n");
            columnMaps.put(field.getName(), BeanUtils.getPmcColumnName(field));
        }
        Map<String, List<String>> resultMap = null;
        if ("1".equals(type)) {
            //insert
            resultMap = SqlUtils.spInsertSql(columnMaps, beanMap, tableName);
        } else {
            if (whereSql == null || "".equals(whereSql)) {
                throw new SQLException(String.format("参数配置有问题，update缺少where条件参数", null));
            }
            //update
            resultMap = SqlUtils.spUpdateSql(columnMaps, beanMap, tableName, whereSql);
        }
        Object[] validSql = resultMap.keySet().toArray();   //改变后的Sql语句
        final List<String> fieldList = resultMap.get(validSql[0]);   //对应插入的bean字段
        jdbcTemplate.execute(validSql[0].toString(), new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
            @Override
            protected void setValues(PreparedStatement ps, LobCreator lobCreater)
                    throws SQLException, DataAccessException {
                String filedValue = null;
                Integer columnType;
                for (int i = 0, length = fieldList.size(); i < length; i++) {
                    filedValue = fieldList.get(i);
                    columnType = columnTypes.get(filedValue);
                    if (columnType == null) {
                        throw new SQLException(String.format("你提供的SQL语句有中存在不能识别的Bean属性字段，请详细检查再次尝试运行"));
                    }
                    switch (columnType) {
                        case Types.CLOB: {
                            lobCreater.setClobAsString(ps, i + 1, BeanUtils.getter(bean, filedValue) == null ? null : BeanUtils.getter(bean, filedValue).toString());
                            break;
                        }
                        case Types.BLOB: {
                            lobCreater.setBlobAsBytes(ps, i + 1, BeanUtils.getter(bean, filedValue) == null ? null : (byte[]) BeanUtils.getter(bean, filedValue));
                            break;
                        }
                        case Types.TIMESTAMP: {
                            ps.setTimestamp(i + 1, BeanUtils.getter(bean, filedValue) == null ? null : (Timestamp) BeanUtils.getter(bean, filedValue));
                            break;
                        }
                        case Types.DATE: {
                            ps.setDate(i + 1, BeanUtils.getter(bean, filedValue) == null ? null : (Date) BeanUtils.getter(bean, filedValue));
                            break;
                        }
                        case Types.INTEGER: {
                            ps.setObject(i + 1, BeanUtils.getter(bean, filedValue) == null ? null : (Integer) BeanUtils.getter(bean, filedValue), Types.INTEGER);
                            break;
                        }
                        case Types.VARCHAR: {
                            ps.setString(i + 1, BeanUtils.getter(bean, filedValue) == null ? null : BeanUtils.getter(bean, filedValue).toString());
                            break;
                        }
                        case Types.BIGINT: {
                            ps.setObject(i + 1, BeanUtils.getter(bean, filedValue) == null ? null : (Long) BeanUtils.getter(bean, filedValue), Types.BIGINT);
                            break;
                        }
                        case Types.FLOAT: {
                            ps.setObject(i + 1, BeanUtils.getter(bean, filedValue) == null ? null : (Float) BeanUtils.getter(bean, filedValue), Types.FLOAT);
                            break;
                        }
                        case Types.CHAR: {
                            ps.setObject(i + 1, BeanUtils.getter(bean, filedValue) == null ? null : BeanUtils.getter(bean, filedValue), Types.CHAR);
                            break;
                        }default:{
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     * 非Lob类型的批量insert
     *
     * @param saveList
     */
    public boolean batchInsert(final List saveList) throws SQLException {
        return batchExecute(saveList, "", "1");
    }

    /**
     * 非Lob类型的批量update
     *
     * @param saveList
     */
    public boolean batchUpdate(final List saveList, String whereSql) throws SQLException {
        return batchExecute(saveList, whereSql, "2");
    }

    /**
     * 非Lob类型的批量处理
     *
     * @param saveList
     */
    public boolean batchExecute(final List saveList, String whereSql, String type) throws SQLException {
        if (saveList == null || saveList.size() == 0) {
            return false;
        }
        Class<?> clazz = null;
        Table annotation = null;
        String tableName = null;
        Map beanMap = null;
        Map<String, String> columnMaps = new HashMap<String, String>();
        final Map<String, Integer> columnTypes = new HashMap<String, Integer>();
        Field[] fields = null;
        Map<String, List<String>> resultMap = null;
        final List<Map<String, List<String>>> lst = new LinkedList();
        Object entity = null;
        try {
            for (Iterator it = saveList.iterator(); it.hasNext(); ) {

                entity = it.next();

                clazz = entity.getClass();
                annotation = (Table) clazz.getAnnotation(Table.class);
                tableName = annotation.name();
                if (tableName == null || "".equals(tableName)) {
                    throw new SQLException(String.format("参数配置有问题，@Table", null));
                }
                beanMap = BeanUtils.toMap(entity);
                fields = entity.getClass().getDeclaredFields();   //获取所有属性
                for (Field field : fields) {
                    columnTypes.put(field.getName(), BeanUtils.getPmcColumnType(field));
                    columnMaps.put(field.getName(), BeanUtils.getPmcColumnName(field));
                }
                if ("1".equals(type)) {
                    //insert
                    resultMap = SqlUtils.spInsertSql(columnMaps, beanMap, tableName);
                } else {
                    if (whereSql == null || "".equals(whereSql)) {
                        throw new SQLException(String.format("参数配置有问题，update缺少where条件参数", null));
                    }
                    //update
                    resultMap = SqlUtils.spUpdateSql(columnMaps, beanMap, tableName, whereSql);
                }
                lst.add(resultMap);
            }
            final Object[] validSql = resultMap.keySet().toArray();

            jdbcTemplate.batchUpdate(validSql[0].toString(), new BatchPreparedStatementSetter() {
            	@Override
                public int getBatchSize() {
                    return lst.size();
                }
            	@Override
                public void setValues(PreparedStatement ps, int j)
                        throws SQLException, DataAccessException {
                    List<String> fieldList = (lst.get(j)).get(validSql[0]);   //
                    String filedValue = null;
                    Integer columnType;
                    for (int i = 0, length = fieldList.size(); i < length; i++) {
                        filedValue = fieldList.get(i);
                        columnType = columnTypes.get(filedValue);
                        if (columnType == null) {
                            throw new SQLException(String.format("你提供的SQL语句有中存在不能识别的Bean属性字段，请详细检查再次尝试运行"));
                        }
                        switch (columnType) {
                            case Types.TIMESTAMP: {
                                ps.setTimestamp(i + 1, BeanUtils.getter(saveList.get(j), filedValue) == null ? null : (Timestamp) BeanUtils.getter(saveList.get(j), filedValue));
                                break;
                            }
                            case Types.DATE: {
                                ps.setDate(i + 1, BeanUtils.getter(saveList.get(j), filedValue) == null ? null : (Date) BeanUtils.getter(saveList.get(j), filedValue));
                                break;
                            }
                            case Types.INTEGER: {
                                ps.setObject(i + 1, BeanUtils.getter(saveList.get(j), filedValue) == null ? null : (Integer) BeanUtils.getter(saveList.get(j), filedValue), Types.INTEGER);
                                break;
                            }
                            case Types.VARCHAR: {
                                ps.setString(i + 1, BeanUtils.getter(saveList.get(j), filedValue) == null ? null : BeanUtils.getter(saveList.get(j), filedValue).toString());
                                break;
                            }
                            case Types.BIGINT: {
                                ps.setObject(i + 1, BeanUtils.getter(saveList.get(j), filedValue) == null ? null : (Long) BeanUtils.getter(saveList.get(j), filedValue), Types.BIGINT);
                                break;
                            }
                            case Types.FLOAT: {
                                ps.setObject(i + 1, BeanUtils.getter(saveList.get(j), filedValue) == null ? null : (Float) BeanUtils.getter(saveList.get(j), filedValue), Types.FLOAT);
                                break;
                            }default:{
                                break;
                            }
                        }
                    }
                }
            });
        } catch (SQLException e) {
            //log.debug(e.getMessage());
            throw new SQLException(e);
            //return false;
        }
        return true;
    }

}