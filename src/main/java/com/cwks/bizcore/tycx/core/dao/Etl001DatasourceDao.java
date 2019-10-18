package com.cwks.bizcore.tycx.core.dao;


import com.cwks.bizcore.tycx.core.mapping.mapper.Etl001DatasourceMapper;
import com.cwks.bizcore.tycx.core.mapping.pojo.Etl001DatasourcePojo;
import com.cwks.common.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("all")
public class Etl001DatasourceDao {
	@Autowired
	JdbcDao jdbcDao;
    @Autowired
    private Etl001DatasourceMapper etl001DatasourceMapper;

    public List select(Etl001DatasourcePojo pojo){
        return etl001DatasourceMapper.select(pojo);
    };

    public Etl001DatasourcePojo selectByPKey(Etl001DatasourcePojo pojo){
        return etl001DatasourceMapper.selectByPKey(pojo);
    };

    public void updateByPKey(Etl001DatasourcePojo pojo){
        etl001DatasourceMapper.updateByPKey(pojo);
    };

    public void updateByPKeySelective(Etl001DatasourcePojo pojo){
        etl001DatasourceMapper.updateByPKeySelective(pojo);
    };

    public void insert(Etl001DatasourcePojo pojo){
        etl001DatasourceMapper.insert(pojo);
    };

    public void insertSelective(Etl001DatasourcePojo pojo){
        etl001DatasourceMapper.insertSelective(pojo);
    };

    public void deleteByPKey(Etl001DatasourcePojo pojo){
        etl001DatasourceMapper.deleteByPKey(pojo);
    };
    
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
    
    /**
     * 查询启用数据库信息
     * @return
     */
    public List selectAllDbList(){
    	
    	return queryList("SQL_ETL_QUERYSQLXH",null);
    	
    }
    
    /**
     * 通过 from_datasource_id 删除 dblink
     * @param id
     */
    public void deletDblinkByKey(String id){
    	
		ArrayList params = new ArrayList<String>();
		params.add(id);
		this.excute("SQL_ETL_DELETE_DBLINK_BYKEY", params);
	
    }
    
    /**
     * 插入dblink数据
     * @param map
     */
    public void InsertDblink(Map map){
    	 
    	ArrayList params = new ArrayList<String>();
    	
    	params.add(map.get("from_datasource_id"));
    	params.add(map.get("to_datasource_id"));
    	params.add(map.get("dblink"));
    	params.add(map.get("lrry_dm"));
    	params.add(map.get("from_datasource_id"));
    	params.add(map.get("to_datasource_id"));
    	
    	this.excute("SQL_ETL_INSERT_DBLINK", params);
    	 
    }

    
    /**
     * 通过 from_datasource_id 查询dblink 信息
     * @param form_datasuorce_id
     * @return
     */
    public List selectAllDbList(String form_datasuorce_id){ 
    	ArrayList params = new ArrayList<String>();
		params.add(form_datasuorce_id);
    	return queryList("SQL_ETL_SELECT_DBLINK_BYKEY",params);
    }
    
    
    public void zfDatasuorce(String datasource_id){
    	ArrayList params = new ArrayList<String>();
		params.add(datasource_id);
    	this.excute("SQL_ETL_ZF_DATASOURCE", params);
    }
    
    public void qyDatasuorce(String datasource_id){
    	ArrayList params = new ArrayList<String>();
		params.add(datasource_id);
    	this.excute("SQL_ETL_QY_DATASOURCE", params);
    }
    
    public Map getPTk(){
    	
    	return this.queryMap("SQL_ETL_PTID",null);
    	
    }
    
    
    public List selectAllUser(String datasuorce_id,String dblink){
     
		String sql =
				"SELECT username id, username name, '' pid\n" +
						"  FROM All_Users"+dblink+" a\n" + 
						" where username not in (upper('SCOTT'),\n" + 
						"                        upper('ORACLE_OCM'),\n" + 
						"                        upper('XS$NULL'),\n" + 
						"                        upper('MDDATA'),\n" + 
						"                        upper('DIP'),\n" + 
						"                        upper('APEX_PUBLIC_USER'),\n" + 
						"                        upper('SPATIAL_CSW_ADMIN_USR'),\n" + 
						"                        upper('SPATIAL_WFS_ADMIN_USR'),\n" + 
						"                        upper('DBSNMP'),\n" + 
						"                        upper('SYSMAN'),\n" + 
						"                        upper('ANONYMOUS'),\n" + 
						"                        upper('FLOWS_FILES'),\n" + 
						"                        upper('MDSYS'),\n" + 
						"                        upper('ORDSYS'),\n" + 
						"                        upper('EXFSYS'),\n" + 
						"                        upper('WMSYS'),\n" + 
						"                        upper('APPQOSSYS'),\n" + 
						"                        upper('APEX_030200'),\n" + 
						"                        upper('OWBSYS_AUDIT'),\n" + 
						"                        upper('ORDDATA'),\n" + 
						"                        upper('CTXSYS'),\n" + 
						"                        upper('XDB'),\n" + 
						"                        upper('ORDPLUGINS'),\n" + 
						"                        upper('OWBSYS'),\n" + 
						"                        upper('SI_INFORMTN_SCHEMA'),\n" + 
						"                        upper('OLAPSYS'),\n" + 
						"                        upper('MGMT_VIEW'),\n" + 
						"                        upper('SYS'),\n" + 
						"                        upper('SYSTEM'),\n" + 
						"                        upper('OUTLN'))\n" + 
						"   and NOT EXISTS (SELECT 1\n" + 
						"          FROM sys_datasource_user t2\n" + 
						"         WHERE a.username = t2.ds_user\n" + 
						"           and t2.datasource_id = '"+datasuorce_id+"' )\n" + 
						" order by a.username";

		
		return jdbcDao.queryforlist(sql);
    	
    }
    
    
    
    public List selectDatasourceUser(String datasuorce_id){
    	
    	String sql = " SELECT ds_user ID, ds_user NAME, '' PID       \n" +
					 "  FROM sys_datasource_user A                \n" + 
					 " WHERE DATASOURCE_ID = '"+datasuorce_id+"' \n" + 
					 " ORDER BY ds_user   ";
    	
    	return jdbcDao.queryforlist(sql);
    	
    }
    
    /**
     * 
     * @param ds_users
     * @param datasource_id
     * @param swry_dm
     * 
     * ds_users 多个用户名用逗号隔开 
     * 
     */
    
    public void reAddUser(String ds_users,String datasource_id,String swry_dm){
    	
    	 String sql = "INSERT INTO sys_datasource_USER (DATASOURCE_ID,ds_user,IS_VALID,LRRQ,LRRY_DM) "
    			    + " SELECT ?,COLUMN_VALUE,?,SYSDATE,? FROM TABLE(PKG_PUB_FUN.FUN_STR_SPLIT(?,','))";
    	
    	 
    	 ArrayList<String> param = null;
			
    	 param = new ArrayList<String>();
    	 param.add(datasource_id);
    	 param.add("Y");
    	 param.add(swry_dm);
    	 param.add(ds_users);
			
		 jdbcDao.update(sql,param);
			
    }
    
    public void deleteUser(String ds_users,String datasource_id){
    	
		String sql = "DELETE FROM sys_datasource_USER T WHERE DATASOURCE_ID=? AND EXISTS (SELECT 1 FROM  TABLE(PKG_PUB_FUN.FUN_STR_SPLIT(?,',')) G WHERE G.COLUMN_VALUE=T.ds_user)";

		ArrayList<String> param = new ArrayList<String>();
		param.add(datasource_id);
		param.add(ds_users);
		
		jdbcDao.update(sql,param);
    	
    }
    
    
}
