package com.cwks.bizcore.daoUtil ;
import com.cwks.bizcore.persistence.SpManager;
import com.cwks.bizcore.utils.DataSourceUtil;
import com.cwks.common.dao.JdbcDao;
import com.cwks.common.util.db.oracl.StoredProcParamObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("all")
public class ChangeDao {
	@Autowired
	JdbcDao jdbcDao;
	public void setJdbcTemplateByJndiName(String jndiname) throws Exception {
		DataSourceUtil dataSourceUtils = new DataSourceUtil(jndiname);
//		jdbcDao.setJdbcTemplateByJndiName(jndiname);
	}

	public List<Map<String, Object>> queryPage(String sql,int start,int pagerows) {
		return jdbcDao.queryPage(sql,start,start,pagerows);
	}

	public List<Map<String, Object>> queryPage(String sql, int start, int end,
			int pagerows) {
		return jdbcDao.queryPage(sql, start, end, pagerows);
	}

	public <T> List<T> queryPage(String sql, int start, int pagerows,
			RowMapper<T> rowMapper) {
		return jdbcDao.queryPage(sql, start, start, pagerows, rowMapper);
	}

	public <T> List<T> queryPage(String sql, int start, int end, int pagerows,
			RowMapper<T> rowMapper) {
		return jdbcDao.queryPage(sql, start, end, pagerows, rowMapper);
	}

	public ArrayList<?> callStoreProcess(String procName,
										 ArrayList<StoredProcParamObj> sqlParams, boolean commit) {
		return jdbcDao.callStoreProcess(procName, sqlParams, commit);
	}

	public void commit() {
		jdbcDao.commit();
	}
	
	public void clear() {
		jdbcDao.clear();
	}

	public void execute(String sql) {
		jdbcDao.execute(sql);
	}

	public void update(String sql) {
		jdbcDao.update(sql);
	}

	public void update(String sql, ArrayList param) {
		jdbcDao.update(sql, param);
	}

	public boolean batchUpdate(ArrayList sqllist) {
		return jdbcDao.batchUpdate(sqllist);
	}

	public List queryforlist(String sql) {
		return jdbcDao.queryforlist(sql);
	}

	public List queryforlist(String sql, ArrayList param) {
		return jdbcDao.queryforlist(sql, param);
	}

	public List queryforlist(String sql, ArrayList param, Class returnClass) {
		return jdbcDao.queryforlist(sql, param, returnClass);
	}

	public Map queryformap(String sql) {
		return jdbcDao.queryformap(sql);
	}

	public Map queryformap(String sql, ArrayList param) {
		return jdbcDao.queryformap(sql, param);
	}

	public SqlRowSet queryforRowset(String sql) {
		return jdbcDao.queryforRowset(sql);
	}

	public SqlRowSet queryforRowset(String sql, ArrayList param) {
		return jdbcDao.queryforRowset(sql, param);
	}

	public Object queryForObject(String sql, Class returnClass) {
		return jdbcDao.queryForObject(sql, returnClass);
	}

	public Object queryForObject(String sql, ArrayList param, Class returnClass) {
		return jdbcDao.queryForObject(sql, param, returnClass);
	}

	public Object AutoSetForm(String sql, Object frm) throws Exception {
		return jdbcDao.AutoSetForm(sql, frm);
	}

	public ArrayList AddObjToList(String sql, String className)
			throws Exception {
		return jdbcDao.AddObjToList(sql, className);
	}

	public String getSetMethodName(String s) {
		return jdbcDao.getSetMethodName(s);
	}

	public String getSql(String sqlKey) {
		return jdbcDao.getSql(sqlKey);
	}

	public Connection getConnection() throws SQLException { // 获取jdbc链接
		return DataSourceUtils.getConnection(jdbcDao.jdbcTemplate.getDataSource());
	}

	public ArrayList callProc(String proc_dam, ArrayList<Object> params)
			throws SQLException, Exception {
		SpManager.callProc(this.getConnection(), proc_dam, params);
		return params;
	}
	 
}
