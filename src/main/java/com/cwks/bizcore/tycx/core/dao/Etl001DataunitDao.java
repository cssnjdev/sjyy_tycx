package com.cwks.bizcore.tycx.core.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.common.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("all")
public class Etl001DataunitDao {
	@Autowired
	JdbcDao jdbcDao;
	private static Map<String, String> xtcsInfo = null;
	
	public Map getXtcsInfo() {
		
		if (xtcsInfo == null) {
			xtcsInfo = new HashMap<String, String>();
			String sql =  " select csbm,csz from sys_xtcs ";
			SqlRowSet set = jdbcDao.queryforRowset(sql);
			while (set.next()) {
				xtcsInfo.put(set.getString("csbm"), set.getString("csz"));
			}
		}
		
		return xtcsInfo;
	}
	
	private Map queryMap(String sqlid,ArrayList param){
    	
    	String sql = jdbcDao.getSql(sqlid);
    	
    	Map map = null;
    	
    	if(param == null){
    		map = jdbcDao.queryformap(sql);
    	}else{
    		map = jdbcDao.queryformap(sql,param);
    	}
    	
		return map;
    }
    
    //查询数组
    private List queryList(String sqlid, ArrayList param){
    	
    	String sql=jdbcDao.getSql(sqlid);
    	
    	List list= null;
    	
    	if(param == null){
    	  list=jdbcDao.queryforlist(sql);
    	}else{
    	  list=jdbcDao.queryforlist(sql,param);
    	}
		
		return list;
		
	}
    
    //展现sql语句
    private void excute(String sqlid, ArrayList param){
    	
    	String sql=jdbcDao.getSql(sqlid);
    	
    	if(param == null){
     		jdbcDao.update(sql);
    	}else{
    		jdbcDao.update(sql,param);
    	}
    	
    }
    
    
    public List searchUnit(String fl,String datasource_id,String user,String en_name,String zh_name,Integer startRow,Integer endRow){
    	
    	String sql =   
			"SELECT T.*, G.DS_NAME,M.MC FOLDER_MC\n" +
			"    FROM SYS_DATAUNIT T , SYS_DATASOURCE G,SYS_FOLDER M\n" + 
			"   WHERE (T.FOLDER_ID in (select folder_id  from sys_folder  connect by prior folder_id = pfolder_id  start with folder_id = ?) or ? = '01' )\n" + 
			"     AND (T.DATASOURCE_ID = ? OR ? IS NULL)\n" + 
			"     AND (T.OWNER = ? OR ? IS NULL)\n" + 
			"	  AND (UPPER(T.EN_NAME) LIKE UPPER('%'||?||'%' ) OR ? IS NULL)\n" +
			"     AND (UPPER(T.ZH_NAME) LIKE UPPER('%'||?||'%' ) OR ? IS NULL) \n"+
			"     AND UPPER(T.IS_LAST_VERSION) = UPPER('Y')\n" +
			"     AND UPPER(T.IS_VALID) = UPPER('Y') \n" +
			"     AND T.DATASOURCE_ID = G.DATASOURCE_ID(+)\n" + 
			"     AND T.FOLDER_ID = M.FOLDER_ID(+)";

  	 
    	sql = " select * from ( select t.*,rownum rn from ("+sql+") t where rownum <=? ) g where g.rn> ? " ;
    	
    	ArrayList param = new ArrayList<String>();
    	param.add(fl);
    	param.add(fl);
    	param.add(datasource_id);
    	param.add(datasource_id);
    	param.add(user);
    	param.add(user);
    	param.add(en_name);
    	param.add(en_name);
    	param.add(zh_name);
    	param.add(zh_name);
    	param.add(endRow);
    	param.add(startRow);
    	
    	return jdbcDao.queryforlist(sql,param);
    	
    }
    
    public Map searchUnitCount(String fl,String datasource_id,String user,String en_name,String zh_name){
    	
    	String sql = 
				"	SELECT COUNT(1) TOTAL \n" +
				"    FROM SYS_DATAUNIT T\n" + 
				"   WHERE (T.FOLDER_ID in (select folder_id  from sys_folder  connect by prior folder_id = pfolder_id  start with folder_id = ?) or ? = '01' )\n" + 
				"     AND (T.DATASOURCE_ID = ? OR ? IS NULL)\n" + 
				"     AND (T.OWNER = ? OR ? IS NULL)\n" + 
				"	  AND (UPPER(T.EN_NAME) LIKE UPPER('%'||?||'%' ) OR ? IS NULL)\n" +
				"     AND (UPPER(T.ZH_NAME) LIKE UPPER('%'||?||'%' ) OR ? IS NULL) \n"+
				"     AND UPPER(T.IS_LAST_VERSION) = UPPER('Y') \n" +
				"     AND UPPER(T.IS_VALID) = UPPER('Y') \n";

     
    	ArrayList param = new ArrayList<String>();
    	param.add(fl);
    	param.add(fl);
    	param.add(datasource_id);
    	param.add(datasource_id);
    	param.add(user);
    	param.add(user);
    	param.add(en_name);
    	param.add(en_name);
    	param.add(zh_name);
    	param.add(zh_name);
    	
    	return jdbcDao.queryformap(sql,param);
    	
    }
    
    
    public Map queryUnit(String dataunit_id){
    	
    	ArrayList param = new ArrayList<String>();
    	param.add(dataunit_id);
    	return this.queryMap("SQL_ETL_QUERY_DATAUNIT", param);
    	
    }

    
    public void InsertUnit(Map<String,Object> map){
    	
    	ArrayList param = new ArrayList<String>();

    	String nolastSql = "update sys_dataunit set IS_LAST_VERSION=upper('N') where dataunit_id=? and upper(is_last_version) = upper('Y')  ";
    	
    	param.add(map.get("dataunit_id"));
    	jdbcDao.update(nolastSql,param);
    	
    	param = new ArrayList<String>();
     	param.add(map.get("dataunit_id"));
    	param.add(map.get("version_id"));
    	param.add(map.get("datasource_id"));
    	param.add(map.get("owner"));
    	param.add(map.get("en_name"));
    	param.add(map.get("zh_name"));
    	param.add(map.get("du_desc")!=null?map.get("du_desc"):"");
    	param.add(map.get("folder_id"));

    	String sql =  
    			"insert into sys_dataunit\n" +
    					"  (dataunit_id,\n" + 
    					"   version_id,\n" + 
    					"   datasource_id,\n" + 
    					"   owner,\n" + 
    					"   en_name,\n" + 
    					"   zh_name,\n" + 
    					"   du_desc,\n" + 
    					"   folder_id,\n" + 
    					"   Is_Valid," +
    					"   IS_LAST_VERSION)\n" + 
    					"values\n" + 
    					"  (?,?,?,?,?,?,?,?,'Y','Y')";

    	jdbcDao.update(sql,param);
    }
    
    public void UpdateUnit(Map<String,Object> map){
    	
    	ArrayList param = new ArrayList<String>();
    	
    	param.add(map.get("version_id"));
    	param.add(map.get("datasource_id"));
    	param.add(map.get("owner"));
    	param.add(map.get("en_name"));
    	param.add(map.get("zh_name"));
    	param.add(map.get("du_desc"));
    	param.add(map.get("folder_id"));
    	
    	param.add(map.get("dataunit_id"));
    	
    	String sql =
    			" update sys_dataunit      \n" +
				"   set version_id    = ?, \n" + 
				"       datasource_id = ?, \n" + 
				"       owner         = ?, \n" + 
				"       en_name       = ?, \n" + 
				"       zh_name       = ?, \n" + 
				"       du_desc       = ?, \n" + 
				"       folder_id     = ? \n" + 
				" where dataunit_id   = ? \n";

    	jdbcDao.update(sql,param);
    	
    }
    
    
    public List querySjxList(String dataunit_id,String version_id){
    	
    	ArrayList param = new ArrayList<String>();
    	
    	param.add(dataunit_id);
    	param.add(version_id);
    	
    	String sql = 
    			" select dataunit_id,	  \n" +
				"        version_id,	  \n" + 
				"        col_id,		  \n" + 
				"        col_name,		  \n" + 
				"        zh_name,		  \n" + 
				"        data_type,		  \n" + 
				"        data_length,	  \n" + 
				"        data_scale,	  \n" +
				"        biz_desc,		  \n" +
				"        tech_desc 		  \n"+
				"   from sys_dataunit_col \n" + 
				"  where dataunit_id = ?  \n" + 
				"    and version_id = ?   \n" +
				"  order by to_number(col_id)";

    	
    	return jdbcDao.queryforlist(sql,param);
    	
    }
    
    public void zfMl(String id){
    	
    	ArrayList param = new ArrayList<String>();
    	
    	param.add(id);
     	
    	String sql =  " update SYS_FOLDER set xybj = upper('N') where folder_id = ? ";

    	jdbcDao.update(sql,param);
    	
    }
    
    private String getColType(String data_type,String data_length,String data_scale){
    	
    	String col_type = data_type;
    	
    	if(!TycxUtils.isEmpty(data_type) ){
    		
    		if(!TycxUtils.isEmpty(data_scale) ){
        		
    			col_type = col_type +"("+data_length+","+data_scale+")" ;
    			
        	}else{
        		
        		col_type = col_type +"("+data_length+")" ;
        		
        	}
    		
    	}
    	
    	
    	
    	
		return col_type;
    	
    }
    
    public void saveSjxList(JSONArray array, String dataunit_id, String version_id){
    	
    	ArrayList param = new ArrayList<String>();
    	
    	param.add(dataunit_id);
    	param.add(version_id);
    	
    	String delSql = " delete sys_dataunit_col where dataunit_id = ? and version_id =? ";
    	
    	jdbcDao.update(delSql, param) ;
    	
    	
    	String sql = 
    			"insert into sys_dataunit_col\n" +
    					"  (dataunit_id,\n" + 
    					"   version_id,\n" + 
    					"   col_id,\n" + 
    					"   col_name,\n" + 
    					"   zh_name,\n" + 
    					"   col_type,\n" + 
    					"   data_type,\n" + 
    					"   data_length,\n" + 
    					"   data_scale)\n" + 
    					"values\n" + 
    					"  (?, ?, ?, ?, ?, ?, ?, ?, ?)";
 
    	
    	int col_id = 1;
    	
    	 
    	for(int i =0;i<array.size();i++){
    		
    		JSONObject jsonObject = array.getJSONObject(i);
    		param = new ArrayList<String>();
    		param.add(dataunit_id);
    		param.add(version_id);
    		param.add(col_id);
    		 
    		jsonObject.put("col_id",col_id+"");
    		jsonObject.put("dataunit_id",dataunit_id);
    		jsonObject.put("version_id",version_id);

    		saveSjx(jsonObject);
    		
    		col_id++;
    		
    	}
    	
    	
    }
    
    public boolean saveSjx(JSONObject jsonObject){
    	
    	ArrayList param = new ArrayList<String>();
    	
    	String delSql = " select count(1) counts  from sys_dataunit_col t  where dataunit_id = ?  and version_id = ? and col_id = ?";

    	String col_id = (String)jsonObject.get("col_id") ;
    	String dataunit_id = (String) jsonObject.get("dataunit_id");
    	String version_id = (String) jsonObject.get("version_id");
    	
    	param.add(dataunit_id);
    	param.add(version_id);
    	param.add(col_id);
  
    	String sql = "";
    	 
		SqlRowSet rs= jdbcDao.queryforRowset(delSql, param) ;
		
		param = new ArrayList<String>();
		
		
		String counts = "0";
		if(rs.next()){
			counts=rs.getString("counts");
		}
		
		if("0".equals(counts)){
			  
			  sql = " insert into sys_dataunit_col \n" +
					"  (col_name,      \n" + 
					"   zh_name,       \n" + 
					"   col_type,      \n" + 
					"   data_type,     \n" + 
					"   data_length,   \n" + 
					"   data_scale,    \n" +
					"   biz_desc,      \n" +
					"   tech_desc,     \n" +
					"	dataunit_id,   \n" + 
					"   version_id,    \n" + 
					"   col_id)        \n" + 
					" values  (?,?,?,?,?,?,?,?,?,?,(select nvl(max(col_id),0)+1 from sys_dataunit_col  where dataunit_id = ?  and version_id = ?)  ) ";
			  
			  
			   
				param.add(jsonObject.get("col_name"));
				param.add(jsonObject.get("zh_name"));
				
				String data_type = (String) jsonObject.get("data_type");
				String data_length = (String) jsonObject.get("data_length");
				String data_scale = (String) jsonObject.get("data_scale");

				param.add(getColType(data_type,data_length,data_scale));
				
		 		param.add(data_type);
				param.add(data_length);
				param.add(data_scale);
		    	
				param.add((String)jsonObject.get("biz_desc"));
				param.add((String)jsonObject.get("tech_desc"));
			  
				param.add(dataunit_id);
				param.add(version_id);
	 
				param.add(dataunit_id);
				param.add(version_id);
				
				
		}else{
			  
			  sql = "update  sys_dataunit_col	\n" +
					" set col_name=?,			\n" + 
					"     zh_name=?,			\n" + 
					"     col_type=?,			\n" + 
					"     data_type=?,		    \n" + 
					"     data_length=?,		\n" + 
					"     data_scale=?,		    \n" +
					"     biz_desc=?,			\n" +
					"     tech_desc=? 		    \n" + 
					" where dataunit_id=? 		\n" +
					"   and version_id=?   		\n" +
					"   and col_id=?       		  ";
			  
			param.add(jsonObject.get("col_name"));
			param.add(jsonObject.get("zh_name"));
				
			String data_type = (String) jsonObject.get("data_type");
			String data_length = (String) jsonObject.get("data_length");
			String data_scale = (String) jsonObject.get("data_scale");

			param.add(getColType(data_type,data_length,data_scale));
			
			param.add(data_type);
			param.add(data_length);
			param.add(data_scale);
	    	
			param.add((String)jsonObject.get("biz_desc"));
			param.add((String)jsonObject.get("tech_desc"));
		  
			param.add(dataunit_id);
			param.add(version_id);
			param.add(col_id);
			  
			  
		}
      
		boolean success =true;
		
		try{
			
  		  jdbcDao.update(sql, param) ;
  		  
		}catch (Exception e) {
			// TODO: handle exception
			success=false;
		}
		
		return success;
		
    }
    
    public void promoteVersion(String dataunit_id){  //提示版本
    	
    	ArrayList param = new ArrayList<String>();
    	param.add(dataunit_id);
    	
        String newV_sql = " select max(to_number(version_id))+1 new_version from sys_dataunit where dataunit_id = ?";
    	
    	Map map = jdbcDao.queryformap(newV_sql,param);
    	
    	String new_version = map.get("new_version").toString();
    	
    	String lastV_sql = " select  max(to_number(version_id)) last_version from sys_dataunit where dataunit_id = ? and upper(is_last_version) = upper('Y') " ;
    	map = jdbcDao.queryformap(lastV_sql,param);
    	String last_version = map.get("last_version").toString();
    	
    	
    	String noLastSql = 
    			"update sys_dataunit\n" +
    					"   set is_last_version = upper('N') \n" + 
    					" where dataunit_id = ?\n" + 
    					"   and upper(is_last_version) = upper('Y') ";

    	param = new ArrayList<String>();
     	param.add(dataunit_id);
     	jdbcDao.update(noLastSql,param);
    	
    	String insertSql = 
    			"insert into sys_dataunit\n" +
    					"  (dataunit_id,\n" + 
    					"   version_id,\n" + 
    					"   datasource_id,\n" + 
    					"   owner,\n" + 
    					"   en_name,\n" + 
    					"   zh_name,\n" + 
    					"   du_desc,\n" + 
    					"   biz_desc,\n" + 
    					"   tech_desc,\n" + 
    					"   is_valid,\n" + 
    					"   last_version_id,\n" + 
    					"   dataunit_version,\n" + 
    					"   is_last_version,\n" + 
    					"   folder_id,\n" + 
    					"   du_type,\n" + 
    					"   lrry_dm,\n" + 
    					"   xgry_dm,\n" + 
    					"   lrrq,\n" + 
    					"   xgrq)\n" + 
    					"  select dataunit_id,\n" + 
    					"         ?,\n" + 
    					"         datasource_id,\n" + 
    					"         owner,\n" + 
    					"         en_name,\n" + 
    					"         zh_name,\n" + 
    					"         du_desc,\n" + 
    					"         biz_desc,\n" + 
    					"         tech_desc,\n" + 
    					"         is_valid,\n" + 
    					"         last_version_id,\n" + 
    					"         dataunit_version,\n" + 
    					"         'Y',\n" + 
    					"         folder_id,\n" + 
    					"         du_type,\n" + 
    					"         lrry_dm,\n" + 
    					"         xgry_dm,\n" + 
    					"         lrrq,\n" + 
    					"         xgrq\n" + 
    					"    from sys_dataunit t\n" + 
    					"    where dataunit_id = ?\n" + 
    					"     and version_id = ?";

    	
    	param = new ArrayList<String>();
    	param.add(new_version);
    	param.add(dataunit_id);
    	param.add(last_version);
    	jdbcDao.update(insertSql,param);
    	
    	  insertSql =   "insert into sys_dataunit_col(\n" + 
    					"    dataunit_id,\n" + 
    					"    version_id,\n" + 
    					"    col_id,\n" + 
    					"    col_name,\n" + 
    					"    zh_name,\n" + 
    					"    col_type,\n" + 
    					"    data_type,\n" + 
    					"    data_length,\n" + 
    					"    data_scale,\n" + 
    					"    nullable,\n" + 
    					"    is_cons_col,\n" + 
    					"    biz_desc,\n" + 
    					"    tech_desc,\n" + 
    					"    lrry_dm,\n" + 
    					"    xgry_dm,\n" + 
    					"    lrrq,\n" + 
    					"    xgrq)\n" + 
    					"  select  dataunit_id,\n" + 
    					"          ?,\n" + 
    					"          col_id,\n" + 
    					"          col_name,\n" + 
    					"          zh_name,\n" + 
    					"          col_type,\n" + 
    					"          data_type,\n" + 
    					"          data_length,\n" + 
    					"          data_scale,\n" + 
    					"          nullable,\n" + 
    					"          is_cons_col,\n" + 
    					"          biz_desc,\n" + 
    					"          tech_desc,\n" + 
    					"          lrry_dm,\n" + 
    					"          xgry_dm,\n" + 
    					"          sysdate,\n" + 
    					"          xgrq\n" + 
    					"    from sys_dataunit_col t\n" + 
    					"   where dataunit_id = ?\n" + 
    					"     and version_id = ? ";
    	
     
    	jdbcDao.update(insertSql,param);
    	
    	
    }
    
    public List queryforlist(String sql) {
		return jdbcDao.queryforlist(sql);
	}

	public List queryforlist(String sql, ArrayList param) {
		return jdbcDao.queryforlist(sql, param);
	}
	
	public Map queryformap(String sql) {
		return jdbcDao.queryformap(sql);
	}

	public Map queryformap(String sql, ArrayList param) {
		return jdbcDao.queryformap(sql, param);
	}
	
	public void update(String sql) {
		jdbcDao.update(sql);
	}

	public void update(String sql, ArrayList param) {
		jdbcDao.update(sql, param);
	}
	
	public SqlRowSet queryforRowset(String sql) {
		return jdbcDao.queryforRowset(sql);
	}

	public SqlRowSet queryforRowset(String sql, ArrayList param) {
		return jdbcDao.queryforRowset(sql, param);
	}
   
    public Connection getConnection() throws SQLException{
     	return DataSourceUtils.getConnection(jdbcDao.jdbcTemplate.getDataSource());
    }
    
    public void commit() {
    	jdbcDao.commit();
    }
    
     
}
