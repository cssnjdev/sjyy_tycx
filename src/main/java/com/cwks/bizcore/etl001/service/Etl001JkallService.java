package com.cwks.bizcore.etl001.service;

import com.cwks.bizcore.daoUtil.ChangeDao;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.service.impl.BaseService;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.persistence.SpManager;
import com.cwks.bizcore.persistence.outtype.BaseOutParam;
import com.cwks.bizcore.persistence.outtype.OracleCursor;
import com.cwks.bizcore.help.ClobHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sun.jdbc.rowset.CachedRowSet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * etl首页监控信息
 * @author Administrator
 *
 */
@Component
@Service("etl001JkallService")
public class Etl001JkallService  {

	private static Logger logger = LoggerFactory.getLogger(Etl001JkallService.class);
	 
	
	@Autowired
    private ChangeDao jdbcDao;

	/**
	 * 针对所有地市的任务监控
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent rwjkAll(RequestEvent requestEvent)throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String,Object> reqMap = requestEvent.getRequestMap();
		
		Connection conn1 = jdbcDao.getConnection();
		
		String dbname = (String) reqMap.get("dbname");
		
		String jndiname = this.getDbJNDI(dbname);
 		
		jdbcDao.setJdbcTemplateByJndiName(jndiname);
 		
		
 		HashMap<Object, Object> resmap = new HashMap<Object, Object>();
		
		HashMap<String, String> map = new HashMap<String, String>();
		 
		ArrayList<Object> params = new ArrayList<Object>();
		
		BaseOutParam out1 = new BaseOutParam(); //单值
  
		OracleCursor cur1 = new OracleCursor();
		OracleCursor cur2 = new OracleCursor();
		
		params.add(out1); 
		params.add(cur1);
		params.add(cur2);
		
		SpManager.callProc(jdbcDao.getConnection(),"PKG_ETL_INFO.P_ETL_JK_INFO",params);
		
		CachedRowSet cs = cur1.getRowset();

		ArrayList<Map<String,String>> rwList = new ArrayList<Map<String,String>>();

 		while (cs.next()) {
			map = new HashMap<String, String>();
			map.put("rq", cs.getString("rq"));
			map.put("zs", cs.getString("zs"));
			map.put("yzxs", (Integer.parseInt(cs.getString("cgs")) + Integer.parseInt(cs.getString("sbs"))) + "");
			map.put("cgs", cs.getString("cgs"));
			map.put("sbs", cs.getString("sbs"));
			map.put("dzxs", cs.getString("dzxs"));
			map.put("zzzx", cs.getString("zzzx"));
			rwList.add(map);
		}
		 

		ArrayList<Map<String,String>> sjdyList = new ArrayList<Map<String,String>>();
 		
		CachedRowSet crs = cur2.getRowset();

		while(crs.next()){
			map = new HashMap<String, String>();
			map.put("zdyfl1_dm", crs.getString("zdyfl1"));
			map.put("mc", crs.getString("ds_name"));
			map.put("rq", crs.getString("rq"));
			map.put("zs", crs.getString("zs"));
			map.put("wjg", crs.getString("wjg"));
			map.put("cgs", crs.getString("cgs"));
			map.put("sbs", crs.getString("sbs"));
			sjdyList.add(map);
		}
		
		resmap.put("rwList", rwList);		
											
		resmap.put("sjdyLists", sjdyList);	
		resmap.put("dbname", dbname);
		jdbcDao.commit();
		resEvent.setResMap(resmap); 						
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp_jk/syjk.jsp");	
		
		jdbcDao.clear();
		
 		return resEvent;
		
	}
	 
	
	/**
 	 * 按日期 执行状态 任务执行明细
 	 * @param requestEvent
 	 * @return
 	 * @throws Exception
 	 */
	public ResponseEvent taskLogs(RequestEvent requestEvent)throws Exception {
		
		Map reqMap = requestEvent.getRequestMap();

		String dbname = (String) reqMap.get("dbname");
		
		String jndiname = this.getDbJNDI(dbname);
 		
		jdbcDao.setJdbcTemplateByJndiName(jndiname);
		
		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();
		
		String rq = (String) reqMap.get("rq");
		String lx = (String) reqMap.get("lx");
 		
 		String sql = "select  task_id id,task_mc mc from etl_task  where xybj = '1'";
		
		ArrayList<HashMap<String, String>> jhlist = new ArrayList<HashMap<String, String>>();
		
		HashMap<String, String> map = null;
		
		SqlRowSet srs = jdbcDao.queryforRowset(sql);
		
 		if (srs.next()) {
 			map = new HashMap<String, String>();
			map.put("id", srs.getString("id"));
			map.put("mc", srs.getString("mc"));
			jhlist.add(map);
		}
		
		resmap.put("lx", lx);
		resmap.put("rq", rq);
 		 
		resmap.put("jhlist", jhlist);
		resmap.put("dbname", dbname);

		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp_jk/syjkTasks.jsp");
 		return resEvent;
	 }
	
	/**
	 * 查询任务执行情况
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent searchTaskLogs(RequestEvent requestEvent)throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String,Object> reqmap = requestEvent.getRequestMap();
		
		String dbname = (String) reqmap.get("dbname");
		
		String jndiname = this.getDbJNDI(dbname);
 		
		jdbcDao.setJdbcTemplateByJndiName(jndiname);
		
		Connection conn = jdbcDao.getConnection();
		
		String start_str =  (String)reqmap.get("start");
		String length = (String) reqmap.get("length");
		String total_str = (String) reqmap.get("total");
		
		String rq = (String) reqmap.get("rq");
		String lx = (String) reqmap.get("lx");
		
		reqmap.put("lx", lx);
		reqmap.put("rq", rq);
		
 		Map<String,Object> JSONDATA = new HashMap<String, Object>();
 		
 		
 		Integer endNum = Integer.parseInt(start_str) + Integer.parseInt(length);
		Integer startNum = Integer.parseInt(start_str);
		
		ArrayList<Object> param= new ArrayList<Object>();
		
		param.add(lx)        ;
		param.add(rq)        ;
		param.add(endNum)    ;
		param.add(startNum)  ;
  
		String sql = " insert into A_LS_GYXX		   \n" +
					 "  (VC1,VC2,VC3,VC4)			   \n" + 
					 " values						   \n" + 
					 "  (?,?,?,?)					  	 " ;
		
		conn.setAutoCommit(false);
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);
		pst.setString(1, lx);pst.setString(2, rq);pst.setString(3, endNum.toString());pst.setString(4, startNum.toString());
		int i = pst.executeUpdate();
		  
	    BaseOutParam out1 = new BaseOutParam();// 单值
	    BaseOutParam out2 = new BaseOutParam();// 单值
	    BaseOutParam out3 = new BaseOutParam();// 单值
		OracleCursor cur1 = new OracleCursor();
 		
		ArrayList<Object> params= new ArrayList<Object>();

		params.add(out1); 
		params.add(out2);
		params.add(out3);
		params.add(cur1);

		SpManager.callProc(conn, "PKG_ETL_INFO.P_ETL_JK_JH_INFO", params);
		
		String success = (String) out1.getValue();
		String message = (String) out2.getValue();
		total_str = (String) out3.getValue();
		
		HashMap<String,String> map = new HashMap<String, String>();
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		
		if("1".equals(success)){
			
		   CachedRowSet cs = cur1.getRowset();
		   
		   while(cs.next()){
			    map = new HashMap<String, String>();
				map.put("stats_date",cs.getString("stats_date"));
				map.put("sche_id",cs.getString("sche_id"));
				map.put("sche_name",cs.getString("sche_name"));
				map.put("sche_log_id",cs.getString("sche_log_id"));
 				map.put("plan_begin_time",cs.getString("plan_begin_time"));
 				map.put("begin_time",cs.getString("begin_time")); 
				map.put("zxzt",cs.getString("zxzt")); 
				map.put("end_time",cs.getString("end_time")); 
				list.add(map); 
		   }
		   
		}
		jdbcDao.commit();
 		
 		JSONDATA.put("total", total_str);
 		JSONDATA.put("count", total_str);
 		JSONDATA.put("iTotalDisplayRecords", total_str);
 		JSONDATA.put("iTotalRecords", total_str);
 		JSONDATA.put("data", list);
 		JSONDATA.put("dbname", dbname);

  		
		HashMap<String, Object> resmap = new HashMap<String, Object>();

		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap); 
  
		return resEvent;
	}
 	
	/**
 	 * 按日期 执行状态 数据单元加工明细
 	 * @param requestEvent
 	 * @return
 	 * @throws Exception
 	 */
	public ResponseEvent unitLogs(RequestEvent requestEvent)
			throws Exception {

		Map reqMap = requestEvent.getRequestMap();

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();

		String rq = (String) reqMap.get("rq");
		String lx = (String) reqMap.get("lx");
		String zdyfl1_dm = (String) reqMap.get("zdyfl1_dm");
		
		String dbname = (String) reqMap.get("dbname");
		
		String jndiname = this.getDbJNDI(dbname);
 		
		jdbcDao.setJdbcTemplateByJndiName(jndiname);
		 
 		
		resmap.put("lx", lx);
		resmap.put("rq", rq);
		resmap.put("zdyfl1_dm", zdyfl1_dm);
		jdbcDao.getConnection();
		 
		String sqls =  "select to_char((case\n" + 
						"                 when sysdate >=\n" + 
						"                      to_date(to_char(sysdate, 'yyyy-mm-dd') || ' 17:00:00',\n" + 
						"                              'yyyy-mm-dd hh24:mi:ss') then\n" + 
						"                  trunc(sysdate)\n" + 
						"                 else\n" + 
						"                  trunc(sysdate) - 1\n" + 
						"               end),\n" + 
						"               'YYYY-MM-DD') state_date\n" + 
						"  from dual";
		SqlRowSet cs = jdbcDao.queryforRowset(sqls);
		
		String dd = rq;
		if(cs.next()){
			dd = cs.getString("state_date");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(dd+" 01:01:01");
		
 		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		 
		ArrayList<String> datelist = new ArrayList<String>();
		calendar.add(calendar.DATE,1);  
		
		for(int i=0;i<7;i++){
			calendar.add(calendar.DATE,-1);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
			datelist.add(format.format(calendar.getTime()));
		}
		
		String dssql = " select datasource_id,ds_name dsname from SYS_DATASOURCE where is_valid = 'Y' ";
	 
		List ywklist= jdbcDao.queryforlist(dssql);
		
 		resmap.put("ywklist",ywklist); 

		resmap.put("datelist",datelist);
		
		String sql = " select zdyfl1_dm code, min(t.zdyfl1_mc) caption \n" +
					 "  from SYS_DATAUNIT_fl t \n" + 
					 " group by zdyfl1_dm      \n";
			  
		SqlRowSet crs = jdbcDao.queryforRowset(sql);
			
		ArrayList<HashMap<String,String>> sjlyList = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> map = new HashMap<String, String>();
		while (crs.next()) {
			map = new HashMap<String, String>();
			map.put("code", crs.getString("code"));
			map.put("caption", crs.getString("caption"));
			sjlyList.add(map);
		}
		
		resmap.put("sjlyList",sjlyList); 
		resmap.put("dbname", dbname);
		
		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp_jk/syjkUints.jsp");

		return resEvent;
	}
	
	/**
	 * 查询数据单元加工明细
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent searchUnitLogs(RequestEvent requestEvent)throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String,Object> reqMap = requestEvent.getRequestMap();
		 
		String dbname = (String) reqMap.get("dbname");
		
		String jndiname = this.getDbJNDI(dbname);
 		
		jdbcDao.setJdbcTemplateByJndiName(jndiname);
		
		Connection conn = jdbcDao.getConnection();
		
		String state_date 	 =  (String) reqMap.get("state_date");
		String datasource_id =  (String) reqMap.get("datasource_id");
		String owner   = (String) reqMap.get("owner");
		String en_name = (String) reqMap.get("en_name");
		String jgqy    = (String) reqMap.get("jgqy");
		String jgzt    = (String) reqMap.get("jgzt");
		String sjly    = (String) reqMap.get("sjly");
		
		String start_str =  (String) requestEvent.getRequestMap().get("start");
		String length = (String) requestEvent.getRequestMap().get("length");
		String total_str = (String) requestEvent.getRequestMap().get("total");
		
		Integer endNum = Integer.parseInt(start_str) + Integer.parseInt(length);
		Integer startNum = Integer.parseInt(start_str);
		
		ArrayList<Object> param= new ArrayList<Object>();
		
		param.add(state_date)    ;
		param.add(datasource_id) ;
		param.add(owner)		 ;
		param.add(en_name) 	 	 ;
		param.add(jgqy) 		 ;
		param.add(jgzt)			 ;
		param.add(sjly)          ;
		
		param.add(startNum)      ;
		param.add(endNum)        ;
 
		
		String sql =  " insert into A_LS_GYXX					\n" +
					  "  (VC1, VC2, VC3, VC4, VC5, VC6, VC7,VC8,VC9)	\n" + 
					  " values									\n" + 
					  "  (?, ?, ?, ?, ?, ?, ?,?,?)					  ";
 
		conn.setAutoCommit(false);
		PreparedStatement pst = (PreparedStatement) conn.prepareStatement(sql);
		pst.setString(1, state_date);pst.setString(2, datasource_id);pst.setString(3, owner);pst.setString(4, en_name);
		pst.setString(5, jgqy);pst.setString(6, jgzt);pst.setString(7, sjly);pst.setString(8, startNum.toString());pst.setString(9, endNum.toString());
		int i = pst.executeUpdate();
		
	    //jdbcDao.update(sql,param);
		  
	    BaseOutParam out1 = new BaseOutParam();// 单值
	    BaseOutParam out2 = new BaseOutParam();// 单值
	    BaseOutParam out3 = new BaseOutParam();// 单值

		OracleCursor cur1 = new OracleCursor();
 		
		ArrayList<Object> params= new ArrayList<Object>();

		params.add(out1); 
		params.add(out2);
		params.add(out3);
		params.add(cur1);

		SpManager.callProc(conn, "PKG_ETL_INFO.P_ETL_JK_DU_INFO", params);
		jdbcDao.commit();
		
		String success = (String) out1.getValue();
		String message = (String) out2.getValue();
		total_str = (String) out3.getValue();
		
		HashMap<String,String> map = new HashMap<String, String>();
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		
		if("1".equals(success)){
			
		   CachedRowSet cs = cur1.getRowset();
		   
		   while(cs.next()){
			    map = new HashMap<String, String>();
				map.put("dataunit_id",cs.getString("dataunit_id"));
				map.put("en_name",cs.getString("en_name"));
				map.put("zh_name",cs.getString("zh_name"));
				map.put("sche_name",cs.getString("sche_name"));
				map.put("owner",cs.getString("owner"));
				map.put("last_sche_log_id",cs.getString("last_sche_log_id"));
				map.put("plan_begin_time",cs.getString("plan_begin_time"));
				map.put("last_begin_time",cs.getString("last_begin_time"));
				map.put("last_sche_id",cs.getString("last_sche_id"));
				map.put("sche_name",cs.getString("sche_name"));
				map.put("jgzt",cs.getString("jgzt"));
				map.put("runnums", "runnums");
				
				list.add(map);
				
		   }
		   
		}
		
 		HashMap<Object, Object> resmap = new HashMap<Object, Object>();

 		Map<String,Object> JSONDATA = new HashMap<String, Object>();
 		
 		JSONDATA.put("total", total_str);
 		JSONDATA.put("count", total_str);
 		JSONDATA.put("iTotalDisplayRecords", total_str);
 		JSONDATA.put("iTotalRecords", total_str);
 		JSONDATA.put("data", list);
		
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap); 
		
		return resEvent;
		
	}
	
	/**
	 * 监控信息 文本描述
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent textMs(RequestEvent requestEvent)throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String,Object> reqMap = requestEvent.getRequestMap();
		
		BaseOutParam out1 = new BaseOutParam();// 单值success
	    BaseOutParam out2 = new BaseOutParam();// 单值message
 
		OracleCursor cur1 = new OracleCursor();
 		
		ArrayList<Object> params= new ArrayList<Object>();

		params.add(out1); 
		params.add(out2);
		params.add(cur1);

		SpManager.callProc(jdbcDao.getConnection(), "PKG_ETL_INFO.P_ETL_JK_TEXT_INFO", params);
		 
		String success = (String) out1.getValue();
		String message = (String) out2.getValue();
		String text= "";
		
		if("1".equals(success)){
		   CachedRowSet cs = cur1.getRowset();
		   if(cs.next()){
			   text = ClobHelper.ClobToString(cs.getClob("text"));
		   }
		}
		
 		HashMap<Object, Object> resmap = new HashMap<Object, Object>();
		
		Map<String,Object> JSONDATA = new HashMap<String, Object>();
		
		JSONDATA.put("success", success);
		JSONDATA.put("message", message);
		JSONDATA.put("text", text);
		
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap); 
		
		return resEvent;
		
	}

    public ResponseEvent selectUser(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	String datasource_id = (String) requestEvent.getRequestMap().get("datasource_id");
    	
    	HashMap<String,Object> reqMap = requestEvent.getRequestMap();
    	
    	String dbname = (String) reqMap.get("dbname");
 		
		String jndiname = this.getDbJNDI(dbname);
 		
		jdbcDao.setJdbcTemplateByJndiName(jndiname);
		 
    	String sql = " select ds_user code,ds_user caption from sys_datasource_user t where (t.datasource_id = ? OR ? is NULL) ";
 	  
    	ArrayList params= new ArrayList<String>();
    	params.add(datasource_id);
    	params.add(datasource_id);

    	List list = jdbcDao.queryforlist(sql,params);
    	
    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
    	reqmap.put("JSONDATA", JsonUtil.toJson(list));
    	resEvent.setResMap(reqmap);
    	
    	return resEvent;
    	
    }

	private  String getDbJNDI(String dbname) throws IOException{
		
		Properties prop = new Properties();
		InputStream in = null;
		
		String classPath = this.getClass().getResource("/").getPath();
		
		try{
			in = new BufferedInputStream(new FileInputStream(classPath+"dbsource.properties"));
			prop.load(in);
		}catch (Exception e) {
			if(in!=null){
				in.close();
			}
		}finally{
			if(in!=null){
				in.close();
			}
		}
		
		String jndiname = prop.getProperty(dbname);
		
		return jndiname;
		
	}
	

	
}
