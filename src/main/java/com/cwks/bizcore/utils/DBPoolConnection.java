package com.cwks.bizcore.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import com.cwks.bizcore.log.JhQzFwContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


public class DBPoolConnection {
    private static Logger logger = LoggerFactory.getLogger(DBPoolConnection.class);


    private DruidDataSource druidDataSource = null;

    private JhQzFwContext jhQzFwContext = JhQzFwContext.singleton();
    /**
     * 数据库连接池单例
     *
     * @return
     */
    public synchronized DruidDataSource getInstance(String dataSourceId) {
        ConcurrentHashMap dataSourceMap = jhQzFwContext.getDataSourceMap();
        try {
            if(dataSourceId != null && !"".equals(dataSourceId)){
                if(dataSourceMap.get(dataSourceId) != null){
                    Map dbInfo = getDataSourceMapById(dataSourceId);
                    druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(dbInfo);
                }
            }
        } catch (Exception e) {
            logger.error("加载交换数据源出现错误!",e);
        }
        return druidDataSource;
    }

    /**
     * 返回druid数据库连接
     *
     * @return
     * @throws SQLException
     */
    public DruidPooledConnection getConnection(String dataSourceId) throws SQLException {
        ConcurrentHashMap dBPoolConnectionMap = jhQzFwContext.getDBPoolConnectionMap();
        DruidDataSource dbs = null;
        if (dBPoolConnectionMap.get(dataSourceId) == null) {
            dbs = this.getInstance(dataSourceId);
            dBPoolConnectionMap.put(dataSourceId, dbs);
            jhQzFwContext.setDBPoolConnectionMap(dBPoolConnectionMap);
        }else{
        	dbs = (DruidDataSource)dBPoolConnectionMap.get(dataSourceId);
        }
        return dbs.getConnection();
    }

    public DataSource getDruidDataSource(String dataSourceId) throws NamingException {
        ConcurrentHashMap dBPoolConnectionMap = jhQzFwContext.getDBPoolConnectionMap();
        DruidDataSource dbs = null;
        if (dBPoolConnectionMap == null || dBPoolConnectionMap.size()<=0 || dBPoolConnectionMap.get(dataSourceId) == null) {
            Map dbInfo = getDataSourceMapById(dataSourceId);
            if(dbInfo != null && dbInfo.get("jndiName") != null && !"".equals((String)dbInfo.get("jndiName"))){
                Properties pro = new Properties();
                pro.setProperty(Context.PROVIDER_URL,(String)dbInfo.get("url"));
                pro.setProperty(Context.INITIAL_CONTEXT_FACTORY,(String)dbInfo.get("driverClassName"));
                Context ctx = new InitialContext(pro);
                DataSource ds = (DataSource) ctx.lookup((String)dbInfo.get("jndiName"));
                return ds;
            }

            dbs = this.getInstance(dataSourceId);

            dBPoolConnectionMap.put(dataSourceId, dbs);
            jhQzFwContext.setDBPoolConnectionMap(dBPoolConnectionMap);
        }else{
        	dbs = (DruidDataSource)dBPoolConnectionMap.get(dataSourceId);
        }
        return dbs;
    }


    private ConcurrentHashMap getDataSourceMapById(String resourceid) {
        ConcurrentHashMap ruleMap = null;
        ConcurrentHashMap dataSourceMap = jhQzFwContext.getDataSourceMap();
        if(dataSourceMap != null){
            if(dataSourceMap.get(resourceid) != null){
                ruleMap = (ConcurrentHashMap)dataSourceMap.get(resourceid);
            }else{
                String tmpkey = "";
                for(Object key : dataSourceMap.keySet()) {
                    if(((String)key).indexOf("*") != -1){
                        tmpkey = ((String)key).replaceAll("[*]","");
                        if(resourceid.indexOf(tmpkey) != -1){
                            ruleMap = (ConcurrentHashMap)dataSourceMap.get(key);
                        }
                    }
                }
            }
        }
        return ruleMap;
    }
}
