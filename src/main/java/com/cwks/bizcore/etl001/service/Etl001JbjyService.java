package com.cwks.bizcore.etl001.service;

import com.alibaba.fastjson.JSON;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.daoUtil.CssnjDao;
import com.cwks.bizcore.help.ClobHelper;
import com.cwks.bizcore.persistence.SpManager;
import com.cwks.bizcore.persistence.outtype.BaseOutParam;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.core.cache.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service("etl001JbjyService")
public class Etl001JbjyService  {

	private static Logger logger = LoggerFactory.getLogger(Etl001DatasourceService.class);

    @Autowired
    private CssnjDao jdbcDao;

    /**
     * 加工脚本校验
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent init(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
    	
    	List ywklist = CacheUtil.getCodeTable("SYS_DATASOURCE", "IS_VALID='Y'") ;
    	
    	reqmap.put("ywklist",ywklist) ;
    	
    	resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/jbjyManager.jsp");
    	resEvent.setResMap(reqmap);
		
		return resEvent;
		
    }
	
    /**
     * 查询需要校验的加工脚本
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent searchUnit(RequestEvent requestEvent) throws Exception {
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	Map<String,Object> reqmap =  requestEvent.getRequestMap();
    	
 		String datasource_id = (String) reqmap.get("datasource_id") ;
		String owner = (String) reqmap.get("owner")    ;
		String en_name = (String) reqmap.get("en_name");
		String zh_name = (String) reqmap.get("zh_name");

		String start_str =  (String) reqmap.get("start") ;
		String length = (String) reqmap.get("length")	 ;
		String total_str = (String) reqmap.get("total")	 ;
		String ppfs = (String)reqmap.get("ppfs");
		String jyjg = (String)reqmap.get("jyjg");
		String is_outmode = (String)reqmap.get("is_outmode");
		
		String is_jg = (String)reqmap.get("is_jg");
		
		Integer endNum = Integer.parseInt(start_str) + Integer.parseInt(length)	;
		Integer startNum = Integer.parseInt(start_str)							;
		
     	HashMap<String,Object> JSONDATA = new HashMap<String, Object>()			;
    	
    	ArrayList<Object> param = new ArrayList<Object>();
     	
    	String likeEnname="";
    	
    	if("1".equals(ppfs)){
    		likeEnname = "and (upper(b.en_name) like upper(?)||'%' or ? is null) ";
    	}else if("2".equals(ppfs)){
    		likeEnname = "and (upper(b.en_name) like '%'||upper(?) or ? is null) ";
    	}else{
    		likeEnname = "and (upper(b.en_name) like '%'||upper(?)||'%' or ? is null) ";
    	}
    	
     	String sql = 
				"select t.*, rownum rn					\n" +
				"  from (								\n" + 
				"	select a.etl_id,					\n" +
				"       a.dataunit_id,					\n" + 
				"       a.dml_type JBTYPE,				\n" + 
				"       a.test_msg YCXX,				\n" + 
				"       a.lrrq,							\n" + 
				"       b.en_name,						\n" + 
				"       b.zh_name,						\n" + 
				"       b.owner,						\n" + 
				"       b.datasource_id,				\n" + 
				"       d.ds_name,						\n" + 
				"       c.mc etlmc,						\n" + 
				"       a.pxxh,							\n" + 
				"       decode(a.dmlsql_is_valid, 'Y', 'Y', 'N') is_valid,	\n" + 
				"       decode(b.is_last_version,'Y','N','Y')  is_outmode	\n" + 
				"  from etl_def_dataunit_dmlsql a,		\n" + 
				"       etl_def_dataunit        e,		\n" + 
				"       sys_dataunit            b,		\n" + 
				"       etl_def                 c,		\n" + 
				"       sys_datasource          d 		\n" + 
				" where a.etl_id = e.etl_id				\n" + 
				"   and a.dataunit_id = e.dataunit_id	\n" + 
				"   and e.etl_id = c.etl_id				\n" + 
				"   and a.dataunit_id = b.dataunit_id   \n" + 
				"   and a.version_id = b.version_id		\n" + 
				"   and b.datasource_id = d.datasource_id(+) 			\n" + 
				"   and (nvl(a.dmlsql_is_valid,'N') = ? or ? is null)	\n" + 
				"   and (b.is_last_version =decode(upper(?),'Y','N','Y') or ? is null) \n" +
				"   and (nvl(e.is_valid,'N')= upper(?) or ? is null )	\n" +
				"   and (b.datasource_id = ? or ? is null)				\n" +
				"   and (b.owner = ? or ? is null)						\n" + 
			    likeEnname +  
				"   and (b.zh_name like '%' || ? || '%' or ? is null)	\n" + 
				" order by to_number(a.etl_id), b.owner, b.en_name		\n" +
				" ) t													\n" ;
			 
     	param.add(jyjg);param.add(jyjg);
     	param.add(is_outmode);param.add(is_outmode);
        param.add(is_jg);param.add(is_jg);

     	
     	param.add(datasource_id);param.add(datasource_id);
    	param.add(owner);param.add(owner);
    	param.add(en_name);param.add(en_name);
    	param.add(zh_name);param.add(zh_name);
     	
     	if(TycxUtils.isEmpty(total_str)){
    		
    		String sc = " select count(*) total from ( "+sql+") g ";
    		SqlRowSet scRs = jdbcDao.queryforRowset(sc,param);
    		if(scRs.next()){
    			total_str = scRs.getString("total");
    		}
    		
    	}
     	
    	String ss = " select * from ( select * from ( "+sql+") g where g.rn<=?) m where m.rn>?  " ;
    	param.add(endNum);param.add(startNum);
 		List<?> jbList = jdbcDao.queryforlist(ss,param);
		 
 		JSONDATA.put("data", jbList);
 		JSONDATA.put("total", total_str);
		JSONDATA.put("count", total_str);
		JSONDATA.put("iTotalDisplayRecords", total_str);
		JSONDATA.put("iTotalRecords", total_str);
    	
 		HashMap<String, Object> resmap = new HashMap<String, Object>();
 		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
 		
 		resEvent.setResMap(resmap);
		return resEvent;
		
    }	
    
    
    
    /**
     * 数据单元结构对比
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent duDef(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
    	
    	List ywklist = CacheUtil.getCodeTable("SYS_DATASOURCE", "IS_VALID='Y'") ;
    	
    	reqmap.put("ywklist",ywklist) ;
    	
    	resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/dudefManager.jsp")	;
    	resEvent.setResMap(reqmap);
		
		return resEvent;
		
    }
    
    /**
     * 查询列信息变更情况
     * @param requestEvent
     * @return
     * @throws Exception
     * 
     */
    public ResponseEvent searchDiffCol(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap =requestEvent.getRequestMap();
    	
    	String datasource_id = (String) reqmap.get("datasource_id") ;
		String owner   = (String) reqmap.get("owner")  ;
		String en_name = (String) reqmap.get("en_name");
		String zh_name = (String) reqmap.get("zh_name");
    	
    	String start_str = (String) reqmap.get("start")  ;
		String length	 = (String) reqmap.get("length") ;
		String total_str = (String) reqmap.get("total")	 ;
		
		Integer endNum = Integer.parseInt(start_str) + Integer.parseInt(length)	;
		Integer startNum = Integer.parseInt(start_str)							;
		
		ArrayList<Object> param = new ArrayList<Object>();
		
    	String sql =  
				"select min(job_log_id) job_log_id,\n" +
				"       t.dataunit_id,\n" + 
				"       t.version_id,\n" + 
				"       min(g.zh_name) zh_name,\n"+
				"       min(t.owner) owner,\n" + 
				"       min(t.en_name) en_name,\n" + 
				"       min(t.version_id),\n" + 
				"       min(ds_name) ds_name,\n" + 
				"       count(*) wrnum\n" + 
				"  from sys_dataunit_col_diff t, sys_datasource f,sys_dataunit g\n" + 
				" where upper(is_last) = upper('Y')\n" + 
				"   and t.datasource_id = f.datasource_id(+)\n" + 
				"   and t.dataunit_id = g.dataunit_id\n" + 
				"   and t.version_id =g.version_id\n" + 
				"   and (t.datasource_id = ? or ? is null)\n" + 
				"   and (t.owner = ? or ? is null)\n" + 
				"   and (upper(t.en_name) like '%' || upper(?) || '%' or ? is null)\n" + 
				"   and (g.zh_name like '%' || ? || '%' or ? is null)\n" + 
				" group by t.dataunit_id, t.version_id";
 

    	param.add(datasource_id); param.add(datasource_id);
    	param.add(owner);  param.add(owner);
    	param.add(en_name);param.add(en_name);
    	param.add(zh_name);param.add(zh_name);
    	
    	if(TycxUtils.isEmpty(total_str)){
    		
    		String sc = " select count(*) total from ( "+sql+") g ";
    		
    		SqlRowSet scRs = jdbcDao.queryforRowset(sc,param);
    		
    		if(scRs.next()){
    			total_str = scRs.getString("total");
    		}
    		
    	}
    	 
     	
    	String dataSal = "select * from ( select g.*,rownum rn from ( "+sql+") g where rownum<=?) m where m.rn>? ";
    	param.add(endNum);    	param.add(startNum);
    	
    	SqlRowSet srs = jdbcDao.queryforRowset(dataSal,param);
    	
    	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    	HashMap<String,String> map = new HashMap<String, String>();
    	
    	while(srs.next()){
    		map = new HashMap<String, String>();
    		
    		map.put("dataunit_id", srs.getString("dataunit_id"));
    		map.put("owner", srs.getString("owner"));
    		map.put("en_name", srs.getString("en_name"));
    		map.put("zh_name", srs.getString("zh_name"));
    		map.put("version_id", srs.getString("version_id"));
    		map.put("job_log_id", srs.getString("job_log_id"));
    		map.put("wrnum", srs.getString("wrnum"));
    		map.put("ds_name", srs.getString("ds_name"));

    		list.add(map);
    	}
    	
    	
    	Map<String,Object> JSONDATA = new HashMap<String, Object>();
    	
    	JSONDATA.put("data", list);
  		JSONDATA.put("total", total_str);
		JSONDATA.put("count", total_str);
		JSONDATA.put("iTotalDisplayRecords", total_str);
		JSONDATA.put("iTotalRecords", total_str);
    	
		HashMap<String,Object> resmap = new HashMap<String, Object>();
		resmap.put("JSONDATA", JSON.toJSON(JSONDATA));
		
		resEvent.setResMap(resmap);
		return resEvent;
		
    }
    
    
    public ResponseEvent showDiff(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap = requestEvent.getRequestMap();
    	
    	String dataunit_id = (String) reqmap.get("dataunit_id") ;
    	String job_log_id  = (String) reqmap.get("job_log_id") ;
    	
    	ArrayList<Object> param = new ArrayList<Object>();
    	param.add(job_log_id)  ;
    	param.add(job_log_id)  ;
    	param.add(dataunit_id) ;
    	
    	HashMap<String, Object> resmap =new HashMap<String, Object>();
    	
    	resmap.put("dataunit_id",dataunit_id);
    	
    	String ss = 
    			" select g.*, x.ds_name, y.ds_name	pre_ds_name		\n" +
				"  from (select f.owner,							\n" + 
				"               f.en_name,							\n" + 
				"               f.zh_name,							\n" + 
				"               f.datasource_id,					\n" + 
				"               g.owner         pre_owner,			\n" + 
				"               g.en_name       pre_en_name,		\n" + 
				"               g.zh_name       pre_zh_name,		\n" + 
				"               g.datasource_id pre_datasource_id	\n" + 
				"          from (select dataunit_id,				\n" + 
				"                       version_id,					\n" + 
				"                       pre_dataunit_id,			\n" + 
				"                       pre_version_id				\n" + 
				"                  from sys_dataunit_col_diff		\n" + 
				"                 where 1 = (case					\n" +
				"         						when job_log_id = ? then	\n" + 
				"         							 1		\n" + 
				"         						when ? is null and upper(is_last) = upper('Y')  then 	\n" + //job_log_id 空是去最新的日志
				"         							 1		\n" + 
				"         						ELSE		\n" + 
				"         							 2		\n" + 
				"       						end) 		\n"	+ 
				"                   and dataunit_id = ?				\n" + 
				"                 group by dataunit_id,				\n" + 
				"                          version_id,				\n" + 
				"                          pre_dataunit_id,			\n" + 
				"                          pre_version_id) t,		\n" + 
				"               sys_dataunit f,						\n" + 
				"               sys_dataunit g						\n" + 
				"         where t.dataunit_id = f.dataunit_id		\n" + 
				"           and t.version_id = f.version_id			\n" + 
				"           and t.pre_dataunit_id = g.dataunit_id(+)	\n" + 
				"           and t.pre_version_id = g.version_id(+)) g,	\n" + 
				"       sys_datasource x,	\n" + 
				"       sys_datasource y	\n" + 
				" where g.datasource_id = x.datasource_id(+)		\n" + 
				"   and g.pre_datasource_id = y.datasource_id(+)	  ";

    	SqlRowSet rowSet = jdbcDao.queryforRowset(ss,param);
    	
    	HashMap<String,String> info = new HashMap<String, String>();
    	if(rowSet.next()){
    		info.put("owner", rowSet.getString("owner"));
    		info.put("en_name", rowSet.getString("en_name"));
    		info.put("zh_name", rowSet.getString("zh_name"));
    		info.put("datasource_id", rowSet.getString("datasource_id"));
    		info.put("pre_owner", rowSet.getString("pre_owner"));
    		info.put("pre_en_name", rowSet.getString("pre_en_name"));
    		info.put("pre_zh_name", rowSet.getString("pre_zh_name"));
    		info.put("pre_datasource_id", rowSet.getString("pre_datasource_id"));
    		info.put("ds_name", rowSet.getString("ds_name"));
    		info.put("pre_ds_name", rowSet.getString("pre_ds_name"));
    	}
    	resmap.put("info", info);
    	
    	String sql ="select col_id,		 \n" +
					"       col_name,	 \n" + 
    			    "       col_type, 	 \n" +	
					"       data_type,	 \n" + 
					"       data_length, \n" + 
					"       data_scale,  \n" + 
					"       nullable,    \n" + 
					"       is_cons_col, \n" + 
					"       pre_col_id,           \n" + 
					"       pre_col_name,         \n" + 
    			    "       pre_col_type, 		  \n"+	
					"       pre_data_type,        \n" + 
					"       pre_data_length,      \n" + 
					"       pre_data_scale,       \n" + 
					"       pre_nullable,         \n" + 
					"       pre_is_cons_col       \n" + 
					"  from sys_dataunit_col_diff \n" + 
					"    where 1 = (case						\n" +
					"         		  when job_log_id = ? then	\n" + 
					"         				1		\n" + 
					"         			when ? is null and upper(is_last) = upper('Y')  then	\n" + 
					"         				1		\n" + 
					"         			ELSE		\n" + 
					"         				2		\n" + 
					"       		   end) 		\n" + 
					"   and dataunit_id =?  \n";
  
    	SqlRowSet srs = jdbcDao.queryforRowset(sql,param);
    	
    	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    	HashMap<String,String> map = new HashMap<String, String>();
    	while(srs.next()){
    		map = new HashMap<String, String>();		
    		map.put("col_id", srs.getString("col_id"));	
    		map.put("col_name", srs.getString("col_name"));	
    		map.put("col_type", srs.getString("col_type"));
    		map.put("data_type", srs.getString("data_type"));	
    		map.put("data_length", srs.getString("data_length"));	
    		map.put("data_scale", srs.getString("data_scale"));		
    		map.put("nullable", srs.getString("nullable"));			
    		map.put("is_cons_col", srs.getString("is_cons_col"));	
    		map.put("pre_col_id", srs.getString("pre_col_id"));		
    		map.put("pre_col_name", srs.getString("pre_col_name"));
    		map.put("pre_col_type", srs.getString("pre_col_type"));
    		map.put("pre_data_type", srs.getString("pre_data_type"));
    		map.put("pre_data_length", srs.getString("pre_data_length"));
    		map.put("pre_data_scale", srs.getString("pre_data_scale"));
    		map.put("pre_nullable", srs.getString("pre_nullable"));
    		map.put("pre_is_cons_col", srs.getString("pre_is_cons_col"));
    		list.add(map);
    	}
    	
    	resmap.put("list", list);
    	
    	resEvent.setResMap(resmap);
    	resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/dudefMx.jsp")	;
    	
    	return resEvent;
		
    }
    
    public ResponseEvent jysql(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap = requestEvent.getRequestMap();
    	
    	String etl_id = (String) reqmap.get("etl_id");
    	String dataunit_id = (String) reqmap.get("dataunit_id");
    	String pxxh = (String) reqmap.get("pxxh");
    	
    	String sql=
    			" select dmlsql,	\n" +
    			"  dml_sql_test, 	\n" +
    			"  dml_type,		\n" +
    			"  dmlsql_is_valid, \n" +
    			"  test_msg 		\n" +
				"  from etl_def_dataunit_dmlsql \n" + 
				" where dataunit_id = ? \n" + 
				"   and etl_id = ?		\n" + 
				"   and pxxh = ?		\n";
    	
    	ArrayList<Object> param = new ArrayList<Object>();
    	param.add(dataunit_id);
    	param.add(etl_id);
    	param.add(pxxh);

    	SqlRowSet srs = jdbcDao.queryforRowset(sql,param);
    	 
    	HashMap<String,Object> resmap = new HashMap<String, Object>();
    	
    	if(srs.next()){
    		resmap.put("dmlsql", ClobHelper.ClobToString((Clob)srs.getObject("dmlsql")));
    		resmap.put("dml_sql_test", ClobHelper.ClobToString((Clob)srs.getObject("dml_sql_test")));
    		resmap.put("dml_type", srs.getString("dml_type"));
    		resmap.put("dmlsql_is_valid", srs.getString("dmlsql_is_valid"));
    		resmap.put("test_msg", srs.getString("test_msg"));
    	}
    	
    	resmap.put("etl_id", etl_id);
    	resmap.put("dataunit_id", dataunit_id);
    	resmap.put("pxxh", pxxh);
    	resEvent.setResMap(resmap);
    	resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/jbjySql.jsp")	;
    	
    	return resEvent;
    	
    }
    
    public ResponseEvent jyaction(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap = requestEvent.getRequestMap();
     	
    	String jsonstr = (String) reqmap.get("jsonstr");
    	
    	String[] dataArr = jsonstr.split(";");
    	
    	ArrayList<Object> list = new ArrayList<Object>();
    	
     
		for(int i=0;i<dataArr.length;i++){
		
			try{
				
				list = new ArrayList<Object>();
			
		    	BaseOutParam outCode = new BaseOutParam(); // 成功返回 1 ,失败返回0
				BaseOutParam outMessage = new BaseOutParam(); // 返回操作 信息
		    	
				String d = dataArr[i];
				
				list.add(d.split("-")[0]);
				list.add(d.split("-")[1]);
				list.add("");
				list.add("");
				list.add("");
				list.add(outCode);
				list.add(outMessage);
				
				SpManager.callProc(jdbcDao.getConnection(), "PKG_DATAUNIT_TRANSACITON.P_ETL_SQL_BY_DATAUNIT_ID", list);
			
			}catch(Exception e){
				
				
			}
			
		}
		 
		
		HashMap<String,Object> resmap   = new HashMap<String, Object>();
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		JSONDATA.put("success",1);
		//JSONDATA.put("message",message);
		 
		
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);
		
    	return resEvent;
    	
    }
    
    public ResponseEvent jiaoyan(RequestEvent requestEvent) throws Exception {
	 
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap = requestEvent.getRequestMap();
    	
    	String dataunit_id = (String) reqmap.get("dataunit_id");
    	
    	ArrayList<Object> list = new ArrayList<Object>();
    	
    	BaseOutParam outCode = new BaseOutParam(); // 成功返回 1 ,失败返回0
		BaseOutParam outMessage = new BaseOutParam();
		
		list.add(dataunit_id);
    	list.add(outCode);
		list.add(outMessage);
    	
    	
		SpManager.callProc(jdbcDao.getConnection(), "PKG_ETL_INFO.P_ETL_RECOMP_DU", list);
		
		HashMap<String,Object> resmap   = new HashMap<String, Object>();
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		JSONDATA.put("success",outCode.getValue());
		JSONDATA.put("message",outMessage.getValue());

		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);
		 
    	return resEvent;
    	
    }
    


	
}
