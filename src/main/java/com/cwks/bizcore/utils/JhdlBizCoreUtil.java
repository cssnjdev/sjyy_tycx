package com.cwks.bizcore.utils;

import com.cwks.common.util.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class JhdlBizCoreUtil {
	static final String JHDL_SOURCE_TYPE_DATABASE="DB";
	static final String JHDL_SOURCE_TRANSACTIONMANAGER="_TRANSMANAGER";
	static final String JHDL_SOURCE_TRANSSTATUS="_TRANSSTATUS";
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  ConcurrentHashMap getSourcesConfigById(String dataSources,ConcurrentHashMap jhqzfwDatasourceMap,ConcurrentHashMap jhqzfwResourceMap) throws Exception {
		if(StringUtils.isEmpty(dataSources)||jhqzfwDatasourceMap==null||jhqzfwDatasourceMap.size()==0) {
			return null;
		}
		ConcurrentHashMap m=null;
		String type,conntype,dataSourceId=null;
		ConcurrentHashMap resMap=new ConcurrentHashMap();
		String[] dataSourceAry=dataSources.split(",");
		for(String s:dataSourceAry) {
			m=(ConcurrentHashMap) jhqzfwDatasourceMap.get(s);
			dataSourceId=m.get("dataSourceId").toString();
			type=(String) m.get("type");
			conntype=(String) m.get("conntype");
			if(type.equals(JHDL_SOURCE_TYPE_DATABASE)) {//数据库模式
				DataSourceUtil dataSourceUtils = new DataSourceUtil(dataSourceId);
				String isRollback = (String) jhqzfwResourceMap.get("isRollback");
				if ("true".equalsIgnoreCase(isRollback)) {
					TransactionStatus status = null;
					DataSourceTransactionManager transactionManager = null;
					DataSource dataSource = dataSourceUtils.getDataSource();
					transactionManager = new DataSourceTransactionManager(dataSource);
					DefaultTransactionDefinition def = new DefaultTransactionDefinition();//事务定义类
					def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
					def.setTimeout(500);
					status = transactionManager.getTransaction(def);// 返回事务对象
					resMap.put(dataSourceId + JHDL_SOURCE_TRANSACTIONMANAGER, transactionManager);
					resMap.put(dataSourceId + JHDL_SOURCE_TRANSSTATUS, status);
				}
				resMap.put(dataSourceId, dataSourceUtils);

			}
		}
		return resMap;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public  void closePools(ConcurrentHashMap map){
		Map.Entry<String, Object> entry=null;
		Object obj=null;
		DataSourceTransactionManager transactionManager = null;
        TransactionStatus status =null;
		for(Object o:map.entrySet()) {
			entry=(Map.Entry<String, Object>)o;
			obj=entry.getValue();
			if(obj instanceof DataSourceUtil) {
				transactionManager=(DataSourceTransactionManager) map.get(entry.getKey()+JHDL_SOURCE_TRANSACTIONMANAGER);
				status=(TransactionStatus) map.get(entry.getKey()+JHDL_SOURCE_TRANSSTATUS);
				transactionManager.commit(status);
				((DataSourceUtil)obj).closeConnection();
			}else if(obj instanceof FTPClient) {
				try {
					((FTPClient)obj).disconnect();
				} catch (Exception e) {
				}
			}
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public  void rollback(ConcurrentHashMap map,String isRollback){
		Map.Entry<String, Object> entry=null;
		Object obj=null;
		try {
	         DataSourceTransactionManager transactionManager = null;
	         TransactionStatus status =null;
		for(Object o:map.entrySet()) {
			entry=(Map.Entry<String, Object>)o;
			obj=entry.getValue();
			if(obj instanceof DataSourceUtil) {
				if("true".equals(isRollback)) {
					transactionManager=(DataSourceTransactionManager) map.get(entry.getKey()+JHDL_SOURCE_TRANSACTIONMANAGER);
					status=(TransactionStatus) map.get(entry.getKey()+JHDL_SOURCE_TRANSSTATUS);
					transactionManager.rollback(status);
				}
			}
		}
		}catch(Exception e) {
			
		}
	}
}
