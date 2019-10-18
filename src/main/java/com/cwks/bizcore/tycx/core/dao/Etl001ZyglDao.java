package com.cwks.bizcore.tycx.core.dao;

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
public class Etl001ZyglDao {
	@Autowired
	JdbcDao jdbcDao;
	  private static final String ArrayList = null;

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
	    
	    //查询符合查询条件的作业信息
	    public HashMap<String,Object> searchEtlList(String etl_fl, String etl_mc, String cjsj_q,String cjsj_z,Integer endNum,Integer startNum,String total_str,String jgssbk){
	    	
	    	ArrayList<Object> param = new ArrayList<Object>();
	    	
	    	String sql ="select t.etl_id,\n" +
					"       t.mc etl_mc,\n" + 
					"       t.ms etl_ms,\n" + 
					"       t2.mc_j etl_lx,\n" + 
					"       t.lrry_dm lrrydm,\n" + 
					"       t3.swryxm cjry,	 \n" + 
					"       to_char(t.cjrq, 'yyyy-mm-dd') cjsj				    \n" + 
					"  from etl_def t, dm_gy_etl_type t2,Dm_Gy_Swry t3 			\n" + 
					" where (T.FOLDER_ID in (select folder_id  from sys_folder  connect by prior folder_id = pfolder_id  start with folder_id = ?) or ? = '05' )				   \n" + 
					"   and t.mc like '%' || ? || '%'						   \n" + 
					"   and (cjrq >= to_date(?, 'yyyy-mm-dd') or ? is null)    \n" + 
					"   and (cjrq  <  to_date(?, 'yyyy-mm-dd')+ 1 or ? is null) \n" +
					"   and (t.JGSSBK = ? or ? is null) \n" +
					"   and  t.lrry_dm =t3.swry_dm(+) \n"+
					"   and t.etllx_dm = t2.etllx_dm(+) " ; 
			
	    	String dataSql = " select * from ( select t.*,rownum rn from ("+sql+" order by to_number(etl_id)) t where  rownum <=? ) g where g.rn> ? ";
	    	
	    	param.add(etl_fl);param.add(etl_fl);
			param.add(etl_mc);
			param.add(cjsj_q);param.add(cjsj_q);
			param.add(cjsj_z);param.add(cjsj_z);
			param.add(jgssbk);param.add(jgssbk);
			param.add(endNum);
	    	param.add(startNum);
	    	
	    	List data = jdbcDao.queryforlist(dataSql,param);
	    	
	    	if(TycxUtils.isEmpty(total_str) ){
	    		
		        String countSql = " select count(1) total from ("+sql+") " ;
		        param = new ArrayList<Object>();
		        param.add(etl_fl);param.add(etl_fl);
				param.add(etl_mc);
				param.add(cjsj_q);param.add(cjsj_q);
				param.add(cjsj_z);param.add(cjsj_z);
				param.add(jgssbk);param.add(jgssbk); 
	        
			    Map map =jdbcDao.queryformap(countSql,param);
			     
			    total_str = map.get("TOTAL").toString();
			    
	    	}
	    	
	    	HashMap JSONDATA = new HashMap<String, Object>();
	    	
		    JSONDATA.put("data", data);
	    	
	    	JSONDATA.put("total", total_str);
	    	JSONDATA.put("count",total_str);
	    	JSONDATA.put("iTotalDisplayRecords",total_str);
	    	JSONDATA.put("iTotalRecords",total_str);
	    	
			return JSONDATA;
	    	
	    }
	    
	    //通过etl_id获取 加工作业信息
	    public Map  searchEtlById(String etl_id){
	    	
	    	String sql =
					" select etl_id,\n" +
					"       dm etldm,\n" + 
					"       mc etlmc,\n" + 
					"       etllx_dm etllx,\n" + 
					"       t3.swryxm  cjrmc,\n" + 
					"       to_char(cjrq, 'yyyy-MM-dd') cjrq,\n" + 
					"       a.ms,\n" + 
					"       etlcs,\n" + 
					"       sjld_sj,\n" + 
					"       sjld_zz,\n" + 
					"       sjld_sjmx,\n" + 
					"       sjld_zzmx,\n" + 
					"       fpjg_sj,\n" + 
					"       fpjg_zz,\n" + 
					"       fpjgsj_xz,\n" + 
					"       fpjgzz_xz,\n" + 
					"       folder_id,\n" + 
					"       xybj,\n" + 
					"       proc_dm,\n" + 
					"       proc_db,					\n" + 
 					"       nvl(sjjgfw, '') sjjgfw,		\n" + 
					"       zzjgfw zzjgfwdm             \n" + 
 					"  from etl_def a,Dm_Gy_Swry t3 	\n" + 
					" where etl_id = ? 					\n" +
					"   and a.lrry_dm =t3.swry_dm(+) 	\n";
	    	
	    	ArrayList<Object> param = new ArrayList<Object>();
			param.add(etl_id);
	    	
			HashMap map = (HashMap) jdbcDao.queryformap(sql, param);
	    	
			
			String zzjgfw =(String) map.get("zzjgfwdm");
			Map zzjgMap = this.getDefZzjgfw(zzjgfw);

	    	map.put("zzjgfwmc", zzjgMap.get("zzjgfwmc"));
	    	 
			return map;
			 
		     
	    }
	    
	    //查询默认加工组织范围
	    public Map getDefZzjgfw(String zzjgfw){
	    	
	    	Map<String,String> map= new HashMap<String, String>();
	    	
	    	String sql = " SELECT B.SWJG_DM zzjgfwdm, B.SWJGMC zzjgfwmc	\n" +
					  "  from dm_gy_swjg B	\n" + 
					  " WHERE B.SWJG_DM =	\n" + 
					  "       nvl(?,		\n" + 
					  "           (select A.CSZ from sys_XTCS A WHERE UPPER(CSBM) = UPPER('ZZJGFW'))	\n" + 
					  "       ) 			\n";
	    	
	    	
	    	 ArrayList<Object>  param = new ArrayList<Object>();
		     param.add(zzjgfw);
			
		     SqlRowSet rs = jdbcDao.queryforRowset(sql,param);
		     
	    	 if(rs.next()){
	    		String zzjgfwmc = (String) rs.getString("zzjgfwmc");
	    		map.put("zzjgfwmc",  rs.getString("zzjgfwmc"));
	    		map.put("zzjgfwdm", rs.getString("zzjgfwdm"));
	    	 }
	    	 
			 return map; 
			 
	    }
	     
	    //获取系统所有的时间加工范围
	    public List sjjgfw(){
	    	
	    	return jdbcDao.queryforlist("select dm,mc from  DM_DATAUNIT_SJLD_SJ_CS where xybj='1' order by pxxh");
	    	
	    }
	     
	    //新建加工作业
	    public void createEtl(String etlid,String etlmc,String ms,String etllx,String cjrmc,
	    		 String cjrq,String folderid,String czrydm,String xybj,
	    		 String proc_dm,String proc_db,String proc_type,String sjjgfw,
	    		 String jgssbk
	    		){
	    	
	    	ArrayList<Object> param = new ArrayList<Object>();

			String sql = " insert into etl_def \n" +
					  "  (etl_id, mc, ms, etllx_dm, cjrmc, cjrq, folder_id, LRRQ, lrry_dm,xybj,proc_dm,proc_db,proc_type,sjjgfw,JGSSBK,proc_user) \n" + 
					  " values \n" + 
					  "  (?, ?, ?, ?, ?,  to_date(?, 'yyyy-mm_dd'), ?,sysdate,?,?,?,?,?,?,?,(nvl((select username  from dba_db_links where db_link  =  " +
					  "      (select dblink from sys_datasource_dblink where from_datasource_id  = (select  csz from sys_xtcs where csbm = 'PT_DATASOURCE') and to_datasource_id = ?)),'dw_app')))";
			
			param.add(etlid);
			param.add(etlmc);
			param.add(ms);
			param.add(etllx);
			
			param.add(cjrmc);
			param.add(cjrq);
			param.add(folderid);
			param.add(czrydm);
			param.add(xybj);
			
			param.add(proc_dm);
			param.add(proc_db);
			param.add(proc_type);
			param.add(sjjgfw);
			param.add(jgssbk);
 
			param.add(proc_db);

			jdbcDao.update(sql, param);
			 
	    	
	    }
	    
	    public void updateEtl(String etlid,String etlmc,String ms,String etllx,String cjrmc,
	    		String folderid,String czrydm,String xybj,String proc_dm,String proc_db,
	    		String proc_type,String sjjgfw,String jgssbk){
	    	
	    	
	    	String sql = 
					" update etl_def       \n" +
					"   set mc        = ?, \n" + 
					"       ms        = ?, \n" + 
					"       etllx_dm  = ?, \n" + 
					"       cjrmc     = ?, \n" + 
					"       folder_id = ?, \n" + 
					"       XGRQ      = sysdate,\n" + 
					"       xgry_dm   = ?, \n" + 
					"       xybj      = ?, \n" +
					"       proc_dm   = ?, \n" +
					"       proc_db   = ?, \n" +
					"       proc_type = ?, \n" +
					"       sjjgfw    = ?, \n" + 
					"       jgssbk =?,     \n"+
					"       proc_user =  (select username from all_db_links where db_link = (select dblink from sys_datasource_dblink where from_datasource_id  = (select  csz from sys_xtcs where csbm = 'PT_DATASOURCE') and to_datasource_id = ?))  " + 
					" where etl_id = ?  ";
				
	    	ArrayList<Object> param = new ArrayList<Object>();

	    	param.add(etlmc);
			param.add(ms);
			param.add(etllx);
			param.add(cjrmc);
				
            param.add(folderid);
            param.add(czrydm);
            param.add(xybj);
           
            param.add(proc_dm);
			param.add(proc_db);
			param.add(proc_type);
			
			param.add(sjjgfw);
			param.add(jgssbk);

			param.add(proc_db);
			param.add(etlid);

			jdbcDao.update(sql, param);
	    }
	    
	    //查询作业的要加工的数据单元
	    public List selectZyUnit(String etl_id, String en_name, String zh_name) {

	    	String sql = 
					"select G.DATAUNIT_ID,\n" +
					"       G.ETL_ID,\n" + 
					"       MIN(G.IS_VALID) IS_VALID,\n" + 
					"       MAX(H.DS_NAME) DS_NAME,\n" + 
					"       MAX(T.OWNER) OWNER,\n" + 
					"       MAX(T.DATASOURCE_ID) DATASOURCE_ID,\n" + 
					"       MAX(T.EN_NAME) EN_NAME,\n" + 
					"       MAX(T.ZH_NAME) ZH_NAME,\n" + 
					"       MAX(T.DU_DESC) DU_DESC,\n" + 
					"       MAX(G.PXXH) PXXH\n" + 
					"  from ETL_DEF_DATAUNIT g, SYS_DATAUNIT t, SYS_DATASOURCE h\n" + 
					" where G.ETL_ID = ?\n" + 
					"   AND upper(T.EN_NAME) LIKE '%' || upper(?) || '%'\n" + 
					"   AND (T.ZH_NAME LIKE '%' || ? || '%' or ? is null)\n" + 
					"   AND g.dataunit_id = t.dataunit_id\n" + 
					"   AND T.IS_LAST_VERSION = 'Y' \n" + 
					"   and t.datasource_id = h.datasource_id\n" + 
					" GROUP BY G.ETL_ID, G.DATAUNIT_ID";

			ArrayList param = new ArrayList<String>();
	    	
 	    	param.add(etl_id);
	    	param.add(en_name);
	    	param.add(zh_name);
	    	param.add(zh_name);
					
			return jdbcDao.queryforlist(sql,param);
			
		}
	    
	    //查询作业的要加工的数据单元
	    public List selectZyUnit(String etl_id,String en_name,String zh_name, String jyjg,String is_valid,String is_outmode){
	    	
	    	String sql =
	    			"SELECT * FROM (\n" +
					" SELECT G.DATAUNIT_ID,\n" + 
					"        G.ETL_ID,\n" + 
					"        MIN(G.IS_VALID) IS_VALID,\n" + 
					"        MAX(H.DS_NAME) DS_NAME,\n" + 
					"        MAX(T.OWNER) OWNER,\n" + 
					"        MAX(T.DATASOURCE_ID) DATASOURCE_ID,\n" + 
					"        MAX(T.EN_NAME) EN_NAME,\n" + 
					"        MAX(T.ZH_NAME) ZH_NAME,\n" + 
					"        MAX(T.DU_DESC) DU_DESC,\n" + 
					"        MAX(G.PXXH) PXXH,\n" + 
					"        (CASE WHEN SUM(DECODE(DMLSQL_IS_VALID,'Y',0,1))>0  THEN   1 ELSE  0   END) IS_JYSB ,\n" + 
					"        (CASE WHEN MAX(F.VERSION_ID) = MAX(T.VERSION_ID) THEN 'N' ELSE 'Y' END) IS_OUTMODE\n" + 
					"   FROM ETL_DEF_DATAUNIT G, SYS_DATAUNIT T, SYS_DATASOURCE H ,ETL_DEF_DATAUNIT_DMLSQL F\n" + 
					"  WHERE G.ETL_ID = ?\n" + 
					"    AND UPPER(T.EN_NAME) LIKE '%' || UPPER(?) || '%'\n" + 
					"    AND (T.ZH_NAME LIKE '%' || ? || '%' OR ? IS NULL)\n" + 
					"    AND T.DATAUNIT_ID = G.DATAUNIT_ID\n" + 
					"    AND UPPER(T.IS_LAST_VERSION) = UPPER('Y')\n" + 
					"    AND (UPPER(G.IS_VALID) = UPPER(?) OR ? IS NULL)\n" + 
					"    AND T.DATASOURCE_ID = H.DATASOURCE_ID(+)\n" + 
					"    AND G.ETL_ID  =F.ETL_ID(+)\n" + 
					"    AND G.DATAUNIT_ID = F.DATAUNIT_ID(+)\n" + 
					"  GROUP BY G.ETL_ID, G.DATAUNIT_ID\n" + 
					") WHERE (IS_JYSB=? OR ? IS NULL)\n" + 
					"    AND (IS_OUTMODE = ? OR ? IS NULL)\n" + 
					"  ORDER BY PXXH";
 
	    	
	    	ArrayList param = new ArrayList<String>();
	    	
 	    	param.add(etl_id);
 	    	
	    	param.add(en_name);
	    	param.add(zh_name);
	    	param.add(zh_name);
	    	
	    	param.add(is_valid);
	    	param.add(is_valid);
	    	
	    	param.add(jyjg);
	    	param.add(jyjg);
	    	
	    	param.add(is_outmode);
	    	param.add(is_outmode);
	    	
	    	return jdbcDao.queryforlist(sql,param);
	    	 
	    }
	  
	    
	    //查询可被添加到作业的数据单元
	    public List searchUnit(String datasource_id,String user,String en_name,String zh_name,Integer startRow,Integer endRow,String etl_id, String ppfs) {
	    	
	    	 
	    	String likeEnname = "";
	    			
	    	if("1".equals(ppfs)){
	    		likeEnname = "AND upper(T.EN_NAME) LIKE upper(?)||'%' 	   \n";
	    	}else if("2".equals(ppfs)){
	    		likeEnname = "AND upper(T.EN_NAME) LIKE '%'||upper(?) 	   \n";
	    	}else{
	    		likeEnname = "AND upper(T.EN_NAME) LIKE '%'||upper(?)||'%' \n";
	    	}
	    	
	    	String	sql = " SELECT T.*												\n" +
				  "  FROM SYS_DATAUNIT T									\n" + 
				  " WHERE (T.DATASOURCE_ID = ? OR ? IS NULL)				\n" + 
				  "   AND (T.OWNER = ? OR ? IS NULL)						\n" + 
				  	likeEnname + 
				  "   AND (T.ZH_NAME LIKE '%' || ? || '%' or ? is null)		\n" + 
				  "   AND UPPER(T.IS_LAST_VERSION) = UPPER('Y')				\n" + 
				  "   AND UPPER(T.IS_VALID) = UPPER('Y') 					\n" + 
				  "   AND NOT EXISTS (SELECT 1			 		 			\n" + 
				  "          FROM ETL_DEF_DATAUNIT H	 		 			\n" + 
				  "         WHERE H.ETL_ID = ?			 		 			\n" + 
				  "           AND H.DATAUNIT_ID = T.DATAUNIT_ID) 			\n" ;

	    	
	    	
	    	
	    	sql = " select * from ( select t.*,rownum rn from ("+sql+") t where  rownum <=? ) g where g.rn> ? " ;
	    	
	    	ArrayList param = new ArrayList<String>();
	    	param.add(datasource_id);
	    	param.add(datasource_id);
	    	param.add(user);
	    	param.add(user);
	    	param.add(en_name);
	    	param.add(zh_name);
	    	param.add(zh_name); 
	    	param.add(etl_id);
	    	param.add(endRow);
	    	param.add(startRow);
	    	
	    	return jdbcDao.queryforlist(sql,param);
	    	
	    }
	    

	    
	    public Map searchUnitCount(String datasource_id,String user,String en_name,String zh_name,String etl_id, String ppfs){
	    	
  
	    	String likeEnname = "";
			
	    	if("1".equals(ppfs)){
	    		likeEnname = "AND T.EN_NAME LIKE ?||'%'";
	    	}else if("2".equals(ppfs)){
	    		likeEnname = "AND T.EN_NAME LIKE '%'||? ";
	    	}else{
	    		likeEnname =  "AND T.EN_NAME LIKE '%' || ? || '%'\n";
	    	}
	    	
	    	String sql = " SELECT T.*						\n" +
				  "  FROM SYS_DATAUNIT T			\n" + 
				  " WHERE (T.DATASOURCE_ID = ? OR ? IS NULL)				\n" + 
				  "   AND (T.OWNER = ? OR ? IS NULL)						\n" + 
					likeEnname + 
				  "   AND (T.ZH_NAME LIKE '%' || ? || '%' or ? is null)		\n" + 
				  "   AND UPPER(T.IS_LAST_VERSION) = UPPER('Y')				\n" + 
				  "   AND UPPER(T.IS_VALID) = UPPER('Y')\n" + 
				  "   AND NOT EXISTS (SELECT 1			\n" + 
				  "          FROM ETL_DEF_DATAUNIT H	\n" + 
				  "         WHERE H.ETL_ID = ?			\n" + 
				  "           AND H.DATAUNIT_ID = T.DATAUNIT_ID) \n";

	    	
	    	sql = " select count(1) total from ("+sql+") " ;
	    	
	    	ArrayList param = new ArrayList<String>();
	    	param.add(datasource_id);
	    	param.add(datasource_id);
	    	param.add(user);
	    	param.add(user);
	    	param.add(en_name);
	    	param.add(zh_name);
	    	param.add(zh_name);
	    	param.add(etl_id);

	    	return jdbcDao.queryformap(sql,param);
	    	
	    }
	    
	    
	    
	    public void insertZyUnit(String etl_id,String dataunit_id,String czrydm,Integer pxxh){
	    	
	    	String sql = 
	    			" INSERT INTO ETL_DEF_DATAUNIT \n" +
					"  (ETL_ID, DATAUNIT_ID, IS_VALID, LRRQ, LRRY_DM, PXXH)\n" + 
					" SELECT ?, ?, UPPER('Y'), SYSDATE, ?, ? FROM DUAL WHERE NOT EXISTS (\n" + 
					"  SELECT 1 FROM ETL_DEF_DATAUNIT WHERE ETL_ID = ? AND DATAUNIT_ID=?\n" + 
					" ) ";

	    	ArrayList<Object> param = new ArrayList<Object>();
	    	
	    	param.add(etl_id);
	    	param.add(dataunit_id);
	    	param.add(czrydm);
	    	param.add(pxxh);
	    	param.add(etl_id);
	    	param.add(dataunit_id);
	    	
	    	jdbcDao.update(sql, param);
	    	
	    }
	    
	   
	    public String getEtlID(){
	    	
	    	String sql = "SELECT PKG_PUB_FUN.FUN_GET_ETL_COMM_SEQ SEQ FROM DUAL ";
	    	
	    	Map map = jdbcDao.queryformap(sql);
	    	
	    	String seq = map.get("SEQ").toString();
	    	
			return seq;
	    	
	    }
	    
	    public Connection getConnection() throws SQLException{
	    	return  DataSourceUtils.getConnection(jdbcDao.jdbcTemplate.getDataSource());
	    }

		
	    
	   

}
