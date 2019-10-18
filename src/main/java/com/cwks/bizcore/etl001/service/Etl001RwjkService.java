package com.cwks.bizcore.etl001.service;

import com.cwks.common.dao.JdbcDao;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.service.impl.BaseService;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("etl001RwjkService")
public class Etl001RwjkService  {

	private static Logger logger = LoggerFactory.getLogger(Etl001RwjkService.class);

	@Autowired
	private JdbcDao jdbcDao;
 
	public ResponseEvent init(RequestEvent requestEvent) throws Exception {

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();
		
 		String sql = "select  task_id id,task_mc mc from etl_task  where xybj = '1'";

		List crs = jdbcDao.queryforlist(sql) ;
		List jhlist =this.listLowerCase(crs);

		resmap.put("jhlist", jhlist);
		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/rwjkManager.jsp");

		return resEvent;

	}

	public ResponseEvent query(RequestEvent requestEvent) throws Exception {
		
		Map reqMap = requestEvent.getRequestMap() ;
		
		ResponseEvent resEvent = new ResponseEvent();
		
		Map<String, Object> JSONDATA = new HashMap<String, Object>();

		HashMap<String, Object> resmap = new HashMap<String, Object>();
			
		String kssj = (String) reqMap.get("kssj");
		String jssj = (String) reqMap.get("jssj");
		String zxrw = (String) reqMap.get("zxrw");
		
		String jhzt = (String) reqMap.get("jhzt");
		String zxzt = (String) reqMap.get("zxzt");
		String jhbh = (String) reqMap.get("jhbh");
		
		String start_str =  (String) requestEvent.getRequestMap().get("start");
		String length = (String) requestEvent.getRequestMap().get("length");
		String total_str = (String) requestEvent.getRequestMap().get("total");
		
		Integer endNum = Integer.parseInt(start_str) + Integer.parseInt(length);
		Integer startNum = Integer.parseInt(start_str);
		 
		String orderby = (String) reqMap.get("orderby");
		
		if(orderby==null){
			orderby="";
		}
		
		String count = (String)reqMap.get("count");
	
		String sql = " select rownum num, a.* from (select rownum num, c.* from( 					\n"
				  + " select sche_log_id logid,														\n"
				  + "       to_number(sche_id) sche_id,												\n"
				  + "       b.task_mc mc,															\n"
				  + "       decode(upper(a.is_executing), upper('Y'), '正在执行', '不在执行') zzzx,	\n"
				  + "       to_char(a.begin_time,'yyyy-mm-dd hh24:mi:ss') kssj,			\n"
				  + "       to_char(a.end_time,'yyyy-mm-dd hh24:mi:ss') jssj,  			\n"
				  + "       round(to_number(a.end_time - a.begin_time)*24*60*60 ) zxhs,  \n"
				  + "       a.sche_log_msg logmsg,										\n"
				  + "       decode(upper(a.is_last),upper('Y'),'是','否') zjzx,    		\n"
				  + "       (case when upper(a.is_executing)=upper('Y') then '正在执行' else decode(upper(a.is_success),upper('Y'),'成功','失败') end ) zxcg \n"
				  + "   from etl_sche_log a, etl_task b \n"
				  + "  where a.sche_id = b.task_id(+)   \n";
		
		if (kssj != null && !kssj.equals("")) {
			sql += " and a.begin_time >= to_date('" + kssj + "','yyyy-mm-dd hh24:mi:ss')";
		}
		
		if (jssj != null && !jssj.equals("")) {
			sql += " and ( a.end_time <= to_date('" + jssj + "','yyyy-mm-dd hh24:mi:ss') or end_time is null )";
		}
		
		if (zxrw != null && !zxrw.equals("")) {
			sql += " and upper(is_last) = upper('" + zxrw + "')";
		}
		
		if (jhzt != null && !jhzt.equals("")) {
			sql += " and upper(is_executing) = upper('" + jhzt + "')";
		}
		
		if (zxzt != null && !zxzt.equals("")) {
			sql += " and upper(is_success) = upper('" + zxzt + "')";
		}
		
		if (jhbh != null && !jhbh.equals("")) {
			sql += " and sche_id = '" + jhbh + "'";
		}
		sql += orderby + " )  c) a" + " where a.num>" + startNum + " and  a.num <= " + endNum;
	
		List rs = jdbcDao.queryforlist(sql);
		List list = this.listLowerCase(rs);
	
		sql = " select count(1) n from etl_sche_log a, etl_task b  where a.sche_id = b.task_id(+) " ;
		
		if (kssj != null && !kssj.equals("")) {
			sql += " and a.begin_time >= to_date('" + kssj
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (jssj != null && !jssj.equals("")) {
			sql += " and a.end_time <= to_date('" + jssj
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (zxrw != null && !zxrw.equals("")) {
			sql += " and upper(is_last) = upper('" + zxrw + "')";
		}
		if (jhzt != null && !jhzt.equals("")) {
			sql += " and upper(is_executing) = upper('" + jhzt + "')";
		}
		if (zxzt != null && !zxzt.equals("")) {
			sql += " and upper(is_success) = upper('" + zxzt + "')";
		}
		if (jhbh != null && !jhbh.equals("")) {
			sql += " and sche_id = '" + jhbh + "'";
		}
	
		if(TycxUtils.isEmpty(count)){
			Map crs = jdbcDao.queryformap(sql);
		    count = crs.get("N").toString();
		}
		
	    JSONDATA.put("data", list);

	    JSONDATA.put("count", count);
		JSONDATA.put("total", count);
     	JSONDATA.put("iTotalDisplayRecords",count);
    	JSONDATA.put("iTotalRecords",count);
    	
    	resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resmap);
    	
		return resEvent;
	    
	}

	public ResponseEvent zyFlow(RequestEvent requestEvent) throws Exception {
		
		Map reqMap = requestEvent.getRequestMap() ;
		
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String, Object> resmap = new HashMap<String, Object>();
		
		String logid = (String) reqMap.get("logid");
		String taskid = (String) reqMap.get("taskid");
		
		resmap.put("taskid", taskid);
		resmap.put("logid", logid);
		
		List nodesList =new ArrayList<HashMap<String, Object>>();
		List lineList = new ArrayList<HashMap<String, Object>>();
	    List nodeParam = new ArrayList<HashMap<String, Object>>();
		
		
		ArrayList<Object> params = new ArrayList<Object>();
		
		String sql = " select start_etl_id,end_etl_id from etl_task_line where task_id = ? ";
		params =new ArrayList<Object>();params.add(taskid);
		List rs = jdbcDao.queryforlist(sql,params);
		HashMap<String, Object> map = null;
		
		for (int i =0;i<rs.size();i++) {
			map = new HashMap<String, Object>();
			Map data = (Map) rs.get(i);
			map.put("from", data.get("START_ETL_ID"));
			map.put("to", data.get("END_ETL_ID"));
			lineList.add(map);
		}
		
		String ztzx = "";
		
		sql = "SELECT IS_EXECUTING ZTZX,\n" + "       B.TASK_MC TASKMC,\n"
				+ "       A.SCHE_LOG_MSG TASKLOG,\n"
				+ "       DECODE(A.IS_EXECUTING,\n" + "              'Y',\n"
				+ "              '正在执行',\n"
				+ "              DECODE(A.IS_SUCCESS,\n"
				+ "                     'Y',\n"
				+ "                     '执行成功',\n"
				+ "                     'N',\n"
				+ "                     '执行失败',\n"
				+ "                     '未执行')) zxjg\n"
				+ "  FROM etl_SCHE_LOG A, etl_TASK B\n"
				+ " WHERE SCHE_LOG_ID = ? \n" + "   AND A.SCHE_ID = B.TASK_ID";
		params =new ArrayList<Object>();params.add(logid);
		rs = jdbcDao.queryforlist(sql, params);
		
		if (rs.size()>0) {
			
			map = new HashMap<String, Object>();
			Map data = (Map) rs.get(0);
			
			ztzx = data.get("ZTZX").toString();
			resmap.put("taskmc", data.get("TASKMC").toString());
			resmap.put("tasklog",  data.get("tasklog").toString().replaceAll("\n","<br/>"));
			resmap.put("zxjg",  data.get("ZXJG").toString());
		
		}
		
		if ("Y".equals(ztzx)) { // 单前日志正在执行判断系统job是否正在执行
		
			sql = "select decode(count(*), 0, 'N', 'Y') ztzx \n"
					+ "  from user_scheduler_running_jobs\n"
					+ " where upper(job_name) = upper('ETL_CHAIN__JOB_' || ?)";
			params =new ArrayList<Object>();params.add(taskid);
			rs = jdbcDao.queryforlist(sql,params);
		
			if (rs.size()>0) {
				Map data = (Map) rs.get(0);
				ztzx =  data.get("ZTZX").toString();
			}
		
		}
		
		resmap.put("ztzx", ztzx);
		
		sql = " select t.etl_id etlid,                 \n"
				+ "       nvl(g.mc, t.etl_id) etlmc, \n"
				+ "       nvl(left,0) x,  \n"
				+ "       nvl(top,0) y, \n"
				+ "       (case upper(f.is_executing)\n"
				+ "         when upper('N') then decode(upper(f.is_success), upper('Y'), '2', '3')\n"
				+ "         when upper('Y') then  '1' "
				+ "          else  decode(upper('"
				+ ztzx
				+ "'),upper('Y'),'4','0')"
				+ "         end ) zt,\n"
				+ "       (case upper(f.is_executing)\n"
				+ "          when upper('Y') then  decode(k.step_name, null, 1, 0) else 0 end) iserror \n"
				+ "  from etl_task_nodes t,\n"
				+ "       etl_def g,\n"
				+ "       (select *\n"
				+ "          from etl_job_log\n"
				+ "         where sche_id = ?\n"
				+ "           and sche_log_id = ?\n"
				+ "           and job_log_step_id = '0') f,\n"
				+ "       user_scheduler_running_chains k\n"
				+ " where task_id = ?\n"
				+ "   and upper('CHAIN_STEP_' || t.etl_id || '_' || t.task_id) =\n"
				+ "       k.step_name(+)\n" + "   and t.etl_id = g.etl_id(+)\n"
				+ "   and g.etl_id = f.etl_id(+)";
		params =new ArrayList<Object>();params.add(taskid);params.add(logid);params.add(taskid);
		rs = jdbcDao.queryforlist(sql,params);
		nodesList = this.listLowerCase(rs);
		 
		
		if (TycxUtils.isEmpty(logid)) {
		
			sql = " select a.etl_id etlid, \n"
					+ "       to_char(replace(wm_concat(params_dm || '-' ||decode(params_dm,'sftg','N',params_value) ), ',', ';')) params \n"
					+ "  from etl_task_params a, etl_task_nodes b \n"
					+ " where a.task_id = ? \n"
					+ "   and a.task_id = b.task_id \n"
					+ "   and a.etl_id = b.etl_id \n"
					+ " group by a.etl_id    ";
			params =new ArrayList<Object>();params.add(taskid);
			rs = jdbcDao.queryforlist(sql, params);
		
		} else {
		
			sql = " select a.etl_id etlid, \n"
					+ "       to_char(replace(wm_concat(params_dm || '-' ||params_value), ',', ';')) params \n"
					+ "  from etl_task_she_params a, etl_task_nodes b \n"
					+ " where a.she_id = ? \n"
					+ "   and a.task_id = b.task_id \n"
					+ "   and a.etl_id = b.etl_id "
					+ " group by a.etl_id         ";
			params =new ArrayList<Object>();params.add(taskid);
			rs = jdbcDao.queryforlist(sql, params);
		
		}
		nodeParam = this.listLowerCase(rs);
		
		sql = "select '执行'||sum(1)||'次，成功'||sum(decode(is_success,'Y',1,0))||'次，失败'||sum(decode(is_success,'Y',0,1))||'次' zxxx\n"
				+ "  from etl_sche_log\n"
				+ " where begin_time > sysdate - 7\n"
				+ "   and end_time < sysdate\n" + "   and sche_id =? ";
		params =new ArrayList<Object>();params.add(taskid);
		rs = jdbcDao.queryforlist(sql,params);
		
		if (rs.size()>0) {
			Map data = (Map) rs.get(0);
 			resmap.put("zxxx", data.get("ZXXX").toString());
		}
		 
		String bgflag = (String) reqMap.get("bgflag");
		
		resmap.put("bgflag", bgflag);
		resmap.put("nodeParam", JsonUtil.toJson(nodeParam));
		resmap.put("nodesList", JsonUtil.toJson(nodesList));
		resmap.put("lineList", JsonUtil.toJson(lineList));
		
		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/rwjkTopo.jsp");
 		
		return resEvent;
		
	}

	public ResponseEvent zylog(RequestEvent requestEvent) throws Exception {

		Map reqMap = requestEvent.getRequestMap();

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();

		String etlid = (String) reqMap.get("etlid");
		String taskid = (String) reqMap.get("taskid");
		String sche_log_id = (String) reqMap.get("logid");

		String mcsql = " select mc,jgssbk from etl_def where etl_id = '" + etlid + "' ";

		SqlRowSet rst = jdbcDao.queryforRowset(mcsql);

		String etlmc = "";
		String jgssbk = "";
		
		if (rst.next()) {
 			etlmc = rst.getString("MC") ;
 			jgssbk = rst.getString("jgssbk");
		}

		resmap.put("sche_log_id", sche_log_id);
		resmap.put("taskid", taskid);
		resmap.put("etlid", etlid);

		resmap.put("etlmc", etlmc);
		resEvent.setResMap(resmap);
		
		if("02".equals(jgssbk)){
			resEvent.setFwordPath("/biz/core/ext/sjzl/sjzl_rwjkZyhzlog.jsp");
		}else{
			resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/rwjkZyhzlog.jsp");
		}
		
		return resEvent;

	}

	public ResponseEvent selectZyLog(RequestEvent requestEvent)
			throws Exception {

		Map reqMap = requestEvent.getRequestMap();

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();

		String etlid = (String) reqMap.get("etlid");
		String taskid = (String) reqMap.get("taskid");
		String sche_log_id = (String) reqMap.get("sche_log_id");
		String joblogid = (String) reqMap.get("joblogid");

		ArrayList<Object> params = new ArrayList<Object>();

		String sql = "select job_log_id joblogid, nvl(is_executing,'W') sfzx, is_success sfcg,job_log_msg_begin beginlog,job_log_msg_end endlog,\n"
				+ "	 to_char(begin_time, 'YYYY-MM-DD hh24:mi:ss') begin_time,       \n"
				+ "  to_char(end_time, 'YYYY-MM-DD hh24:mi:ss') end_time 			\n"
				+ "  from etl_job_log\n"
				+ " where sche_id = ?\n"
				+ "   and etl_id = ?\n"
				+ "   and sche_log_id = ?\n"
				+ "   and job_log_step_id = '0' and rownum=1";
		params = new ArrayList<Object>();
		params.add(taskid);
		params.add(etlid);
		params.add(sche_log_id);
		SqlRowSet rs = jdbcDao.queryforRowset(sql, params);

		String sfzx = null;
		String sfcg = null;
		String begin_time = "";
		String end_time = "";

		String etllog = "";

		if (rs.next()) {
 			joblogid = rs.getString("joblogid");
			sfzx =  rs.getString("sfzx");
			sfcg = rs.getString("sfcg");
			etllog = (rs.getString("beginlog")  + "<br/>" +  rs.getString("endlog")).replace("\n", "<br/>");
			begin_time = rs.getString("begin_time").toString();
			end_time =rs.getString("end_time").toString();

		} else {
			sfzx = "W";// 未执行
		}

		sql = "SELECT NVL(SUM(DECODE(IS_SUCCESS, 'Y', 1, 0)), 0) CGS,\n"
			+ "       NVL(SUM(DECODE(IS_SUCCESS, 'Y', 0, 1)), 0) SBS\n"
			+ "  FROM etl_DATAUNIT_LOG A WHERE A.JOB_LOG_ID = ? and upper(is_executing) = upper('N') \n"
			+ " group by a.etl_id ";
	
		params = new ArrayList<Object>();
		params.add(joblogid);
		rs = jdbcDao.queryforRowset(sql, params);

		String cgs = "0";
		String sbs = "0";
		String zs = "0";

		if (rs.next()) {
 			cgs = rs.getString("cgs");
			sbs = rs.getString("sbs");
 		}
 
		sql =	 "    select count(dataunit_id) zs  from etl_def_dataunit  where etl_id = ? ";
		params = new ArrayList<Object>();
		params.add(etlid);
		rs = jdbcDao.queryforRowset(sql, params);
		if (rs.next()) {
			zs = rs.getString("zs");
		}
		
		Map<String, Object> jsonData = new HashMap<String, Object>();

		jsonData.put("joblogid", joblogid);
		jsonData.put("sfzx", sfzx);
		jsonData.put("sfcg", sfcg);
		jsonData.put("cgs", cgs);
		jsonData.put("sbs", sbs);
		jsonData.put("zs", zs);
		jsonData.put("etllog", etllog);
		jsonData.put("begin_time", begin_time);
		jsonData.put("end_time", end_time);
		resmap.put("JSONDATA", JsonUtil.toJson(jsonData));

		resEvent.setResMap(resmap);
		return resEvent;

	}

	public ResponseEvent showUnitLog(RequestEvent requestEvent)
			throws Exception {

		Map reqMap = requestEvent.getRequestMap();

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();

		String etlid = (String) reqMap.get("etlid");
		String taskid = (String) reqMap.get("taskid");
		String joblogid = (String) reqMap.get("joblogid");
		String flog = (String) reqMap.get("flog");

 
		String sql = 
				" select datasource_id,ds_name \n" +
				"  from sys_datasource t\n" + 
				" where exists (select 1\n" + 
				"          from etl_def_dataunit a, sys_dataunit b\n" + 
				"         where etl_id = ? \n" + 
				"           and a.dataunit_id = b.dataunit_id\n" + 
				"           and b.is_last_version = 'Y'\n" + 
				"           and t.datasource_id = b.datasource_id)";

		ArrayList<Object> params = new ArrayList<Object>();
		params.add(etlid);

		SqlRowSet rs = jdbcDao.queryforRowset(sql, params);
		ArrayList<HashMap<String,String>> dsList = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> map = null;
		if (rs.next()) {
			map = new HashMap<String, String>();
 			map.put("datasource_id", rs.getString("datasource_id"));
			map.put("ds_name", rs.getString("ds_name"));
			dsList.add(map);
		}
		
		
		sql = "select distinct b.owner\n" +
			"  from etl_def_dataunit a, sys_dataunit b\n" + 
			" where etl_id = ? \n" + 
			"   and a.dataunit_id = b.dataunit_id\n" + 
			"   and b.is_last_version = 'Y'";
		 rs = jdbcDao.queryforRowset(sql, params);
		ArrayList<HashMap<String,String>> ownerList = new ArrayList<HashMap<String,String>>();
 		if (rs.next()) {
			map = new HashMap<String, String>();
 			map.put("owner", rs.getString("owner"));
 			ownerList.add(map);
		}
		
 		resmap.put("dsList", dsList);
 		resmap.put("etlid" , etlid);
 		resmap.put("taskid", taskid);

 		resmap.put("ownerList", ownerList);
		resmap.put("joblogid",joblogid);
		resmap.put("flog",flog);
		
		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/rwjkUintLog.jsp");
		return resEvent;
	}
	 
	public ResponseEvent selectZyLogMx(RequestEvent requestEvent)
			throws Exception {

		Map reqMap = requestEvent.getRequestMap();

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();

		String joblogid = (String) reqMap.get("joblogid");
		String flog = (String) reqMap.get("flog");

		String orderby = (String) reqMap.get("orderby");
		String index = (String) reqMap.get("index");

		String owner = (String) reqMap.get("owner");
		String en_name = (String) reqMap.get("en_name");
		String zh_name = (String) reqMap.get("zh_name");
		String datasource_id = (String) reqMap.get("datasource_id");
  		String etlid = (String) reqMap.get("etlid");
		
		
		if(orderby==null){
			orderby= "";
		}
		
		ArrayList<Object> param = new ArrayList<Object>();
		 
		
		String sql = 
			"	select t.* ,decode(flog,'W','未执行','Z','正在执行','Y','执行成功','N','执行失败') zxjg from (				\n" + 
			"    select a.dataunit_id,		\n" +
			"           a.en_name,			\n" + 
			"           a.zh_name,			\n" + 
			"           a.du_desc,			\n" + 
			"           a.datasource_id,	\n" + 
			"           a.owner,			\n" + 
			"           a.ds_name,			\n" + 
			"           decode(b.is_executing, 'N',NVL(b.is_success, 'W'), 'Y','Z', null,'W') FLOG	\n" + 
			"			from (select distinct a.dataunit_id,										\n" + 
			"                      b.en_name,		\n" + 
			"                      b.zh_name,		\n" + 
			"                      b.du_desc,		\n" + 
			"                      b.datasource_id,	\n" + 
			"                      b.owner,			\n" + 
			"                      c.ds_name 		\n" +  
			"            from etl_def_dataunit a, sys_dataunit b, sys_datasource c\n" + 
			"           where a.dataunit_id = b.dataunit_id\n" + 
			"             and upper(b.is_last_version) = upper('Y')\n" + 
			"             and b.datasource_id = c.datasource_id(+)\n" + 
			"             and etl_id = ?) a,\n" + 
			"         (select *  from etl_dataunit_log b where job_log_id = ? ) b\n" + 
			"   where a.dataunit_id = b.dataunit_id(+)\n" + 
			") t\n" + 
			"where (t.datasource_id = ? or ? is null )\n" + 
			"  and (t.owner = ? or ? is null   )\n" + 
			"  and (t.flog = ?  or ? is null \t)\n" + 
			"  and (t.en_name like '%'||?||'%' )\n" + 
			"  and (t.zh_name like '%'||?||'%' or zh_name is null )" + orderby;
		
	
		
		param = new ArrayList<Object>();
		param.add(etlid);
		param.add(joblogid);

		param.add(datasource_id);
		param.add(datasource_id);

		param.add(owner);
		param.add(owner);

		param.add(flog);
		param.add(flog);
		
		param.add(en_name);
		param.add(zh_name);
		
		List rs = jdbcDao.queryforlist(sql, param);

		List list = this.listLowerCase(rs);

		Map<String, Object> jsonData = new HashMap<String, Object>();

		jsonData.put("data", list);

		resmap.put("JSONDATA", JsonUtil.toJson(jsonData));

		resEvent.setResMap(resmap);
		return resEvent;

	}
	
	public ResponseEvent selectUnitLog(RequestEvent requestEvent)
			throws Exception {

		Map reqMap = requestEvent.getRequestMap();

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();

		ArrayList<Object> param = new ArrayList<Object>();

		String joblogid = (String) reqMap.get("joblogid");
		String schelogid = (String) reqMap.get("schelogid");
		
		String unitid = (String) reqMap.get("unitid");
		
		
		SqlRowSet rs= null;

		if(!TycxUtils.isEmpty(schelogid) ){
			
			String sql = "select log_msg				\n" +
						" from etl_dataunit_detail_log  \n" + 
						"where sche_log_id = ? 			\n" + 
						"  and dataunit_id = ?		 	\n" + 
						"order by job_log_step_id		  ";
			
			param.add(schelogid);
			param.add(unitid);
			
			rs = jdbcDao.queryforRowset(sql, param);
			
		}else{
			
			String sql = " select log_msg				\n" +
						 " from etl_dataunit_detail_log	\n" + 
						 " where job_log_id = ? 	    \n" + 
						 "  and dataunit_id = ?   		\n" + 
						 " order by job_log_step_id  	  ";
			
			param.add(joblogid);
			param.add(unitid);
			
			rs = jdbcDao.queryforRowset(sql, param);
			
		}
		
		String msg ="";
		while (rs.next()) {

			msg = msg+rs.getString("log_msg")+"<br/>";
			resmap.put("msg",msg);
			
		}
		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/rwjkUintLogmx.jsp");
		
		return resEvent;
		
	}
	
	public ResponseEvent zylog2(RequestEvent requestEvent) throws Exception {

		Map reqMap = requestEvent.getRequestMap();

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();

		String etlid = (String) reqMap.get("etlid");
		String taskid = (String) reqMap.get("taskid");
		String logid = (String) reqMap.get("logid");
		String etltype = (String) reqMap.get("etltype");

		ArrayList<Object> param = new ArrayList<Object>();

		String sql = "	select					 \n"
				+ "      job_log_id joblogid,  \n"
				+ "      nvl(is_executing,'W') sfzx, \n"
				+ "      is_success sfcg,to_char(job_log_msg_begin) beginLog, \n"
				+ "	   to_char(job_log_msg_end) endlog \n"
				+ "  from etl_job_log	\n" + "	 where sche_id = ?			\n"
				+ "   and etl_id = ?		 \n" + " and sche_log_id = ?		\n"
				+ "   and job_log_step_id = '0' \n" + "and rownum=1";

		param.add(taskid);
		param.add(etlid);
		param.add(logid);
		List rs = jdbcDao.queryforlist(sql, param);

		String joblogid = null;
		String sfzx = null;
		String sfcg = null;
		String maxStep = "0";

		if (rs.size() > 0) {
			Map data = (Map) rs.get(0);
			joblogid = data.get("JOBLOGID").toString();
			sfzx = data.get("SFZX").toString();
			sfcg = data.get("SFCG").toString();
		} else {
			sfzx = "W";// 未执行
		}

		resmap.put("joblogid", joblogid);
		resmap.put("sfzx", sfzx);
		resmap.put("sfcg", sfcg);
		resmap.put("maxStep", maxStep);
		resmap.put("etltype", etltype);

		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/rwjkZyLog.jsp");
		return resEvent;

	}

	public ResponseEvent appendZylog(RequestEvent requestEvent)
			throws Exception {

		Map reqMap = requestEvent.getRequestMap();

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();

		String joblogid = (String) reqMap.get("joblogid");
		String maxStep = (String) reqMap.get("maxStep");
		String jgzt = (String) reqMap.get("jgzt");

		String sfzx = null;
		String sfcg = null;
		String newlis = "";

		ArrayList<Object> param = new ArrayList<Object>();

		String sql = " select * from ( 																	\n" 
				   + " select decode(is_executing, 'Y', 1, decode(is_success, 'N', 0, 'Y', '2')) zt,	\n" 
				   + "       job_log_msg msg,															\n" 
				   + "       job_log_step_id step\n"
				   + "  from etl_job_log\n"
				   + " where job_log_id = ?\n"
				   + "   and job_log_step_id > =?\n"
				   + "   and job_log_step_id > 0\n"
				   + " union all \n"
				   + " select decode(is_executing, 'Y', 1, decode(is_success, 'N', 0, 'Y', '2')) zt,\n"
				   + "       job_log_msg_begin msg,\n"
				   + "       job_log_step_id step\n"
				   + "  from etl_job_log\n"
				   + " where job_log_id = ?\n"
				   + "   and job_log_step_id > =?\n"
				   + "   and job_log_step_id = 0 \n"
				   + " order by step  \n"
				   + " ) where (zt = ? or ? is null) ";
		
		param.add(joblogid);
		param.add(maxStep);
		param.add(joblogid);
		param.add(maxStep);
		param.add(jgzt);
		param.add(jgzt);
		List rs = jdbcDao.queryforlist(sql, param);

		for (int i = 0; i < rs.size(); i++) {

			Map data = (Map) rs.get(i);

			maxStep = data.get("STEP").toString();
			newlis += "<li class=\"zxtz" + data.get("ZT").toString()
					+ "\" id='step" + maxStep + "' > "
					+ data.get("MSG").toString().replaceAll("\n", "<br/>")
					+ " </li> \n";

		}

		HashMap<String, Object> jsonData = new HashMap<String, Object>();

		sql = "select is_executing sfzx, is_success sfcg  \n"
				+ "  from etl_job_log\n" + " where job_log_id = ?\n"
				+ "   and job_log_step_id = '0'\n" + "   and rownum = 1";
		param = new ArrayList<Object>();
		param.add(joblogid);
		rs = jdbcDao.queryforlist(sql, param);

		if (rs.size() > 0) {
			Map data = (Map) rs.get(0);
			sfzx = data.get("SFZX").toString();
			sfcg = data.get("SFCG").toString();
		}

		if (!"Y".equals(sfzx)) {

			sql = " select decode(is_executing, 'Y', 1, decode(is_success, 'N', 0, 'Y', '2')) zt,\n"
					+ "       job_log_msg_end msg,\n"
					+ "       job_log_step_id step\n"
					+ "  from etl_job_log\n"
					+ " where job_log_id = ?\n"
					+ "   and job_log_step_id = 0 \n";
			param = new ArrayList<Object>();
			param.add(joblogid);
			rs = jdbcDao.queryforlist(sql, param);

			for (int i = 0; i < rs.size(); i++) {
				Map data = (Map) rs.get(0);
				newlis += "<li class=\"zxtz" + data.get("ZT").toString()
						+ "\" id='step" + data.get("STEP").toString()
						+ "end' > "
						+ data.get("MSG").toString().replaceAll("\n", "<br/>")
						+ " </li> \n";
			}

		}

		System.out.println(newlis);

		jsonData.put("sfzx", sfzx);
		jsonData.put("sfcg", sfcg);
		jsonData.put("newlis", newlis);
		jsonData.put("maxStep", maxStep);

		resmap.put("JSONDATA", JsonUtil.toJson(jsonData));
		resEvent.setResMap(resmap);

		return resEvent;

	}

	@SuppressWarnings("unchecked")
	public ResponseEvent selectZylog(RequestEvent requestEvent)
			throws Exception {

		Map reqMap = requestEvent.getRequestMap();

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();

		String joblogid = (String) reqMap.get("joblogid");
		String jgzt = (String) reqMap.get("jgzt");
		String index = (String) reqMap.get("index");
		String isnofy = (String) reqMap.get("isnofy");
		String sfzx = (String) reqMap.get("sfzx");

		Integer pageSize = 30;

		String sql = "  select t.*,rownum rn from (\n"
				+ "   select decode(is_executing, 'Y', 1, decode(is_success, 'N', 0, 'Y', '2')) zt,\n"
				+ "         job_log_msg msg,\n"
				+ "         job_log_step_id step         from etl_job_log\n"
				+ "   where job_log_id = ?\n"
				+ "     and job_log_step_id != '0'\n"
				+ "    union all\n"
				+ "    select decode(is_executing, 'Y', 1, decode(is_success, 'N', 0, 'Y', '2')) zt,\n"
				+ "         job_log_msg_begin msg,\n"
				+ "         job_log_step_id step\n"
				+ "    from etl_job_log\n" + "   where job_log_id = ?\n"
				+ "     and job_log_step_id = 0\n";

		if (!"Y".equals(sfzx)) {
			sql += "   union all\n"
					+ "    select decode(is_executing, 'Y', 1, decode(is_success, 'N', 0, 'Y', '2')) zt,\n"
					+ "         job_log_msg_end msg,\n"
					+ "         (select max(job_log_step_id)+1 step from  etl_job_log where job_log_id =t.job_log_id) step\n"
					+ "    from etl_job_log t\n"
					+ "   where job_log_id = ?\n"
					+ "     and job_log_step_id = 0\n" + "     order by step\n";
		}

		sql += "  ) t where (zt = ? or ? is null)\n";

		ArrayList<Object> params = new ArrayList<Object>();

		if (!"1".equals(isnofy)) {// 不分页查询

			sql = "select * from (\n" + sql + ") where rn<=? and rn>?";

			if (!"Y".equals(sfzx)) {

				params.add(joblogid);
				params.add(joblogid);
				params.add(joblogid);

				params.add(jgzt);
				params.add(jgzt);

				params.add(Integer.parseInt(index) * pageSize);
				params.add((Integer.parseInt(index) - 1) * pageSize);

			} else {

				params.add(joblogid);
				params.add(joblogid);

				params.add(jgzt);
				params.add(jgzt);

				params.add(Integer.parseInt(index) * pageSize);
				params.add((Integer.parseInt(index) - 1) * pageSize);

			}

		} else {

			if (!"Y".equals(sfzx)) {

				params.add(joblogid);
				params.add(joblogid);
				params.add(joblogid);

				params.add(jgzt);
				params.add(jgzt);

			} else {

				params.add(joblogid);
				params.add(joblogid);

				params.add(jgzt);
				params.add(jgzt);

			}

		}

		StringBuffer lis =new StringBuffer();

		String maxStep = "0";

		List rs = jdbcDao.queryforlist(sql, params);

		for (int i = 0; i < rs.size(); i++) {
			Map data = (Map) rs.get(0);
			maxStep = data.get("step").toString();
			lis.append( "<li class=\"zxtz").append(data.get("zt") ).append( "\" id='step"
					).append( maxStep ).append( "' > "
							).append( data.get("msg").toString().replaceAll("\n", "<br/>")
									).append( " </li> \n");

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lis", lis.toString());
		map.put("maxStep", Integer.parseInt(maxStep));

		resmap.put("JSONDATA", JsonUtil.toJson(map));
		resEvent.setResMap(resmap);

		return resEvent;

	}
	
	@SuppressWarnings("unchecked")
	public ResponseEvent selectZylogs(RequestEvent requestEvent)
			throws Exception {

		Map reqMap = requestEvent.getRequestMap();

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();

		String joblogid = (String) reqMap.get("joblogid");
		String jgzt = (String) reqMap.get("jgzt");
		String index = (String) reqMap.get("index");
		String isnofy = (String) reqMap.get("isnofy");
		String sfzx = (String) reqMap.get("sfzx");

		Integer pageSize = 30;

		String sql = "  select t.*,rownum rn from (\n"
				+ "   select decode(is_executing, 'Y', 1, decode(is_success, 'N', 0, 'Y', '2')) zt,\n"
				+ "         job_log_msg msg,\n"
				+ "         job_log_step_id step         from etl_job_log\n"
				+ "   where job_log_id = ?\n"
				+ "     and job_log_step_id != '0'\n"
				+ "    union all\n"
				+ "    select decode(is_executing, 'Y', 1, decode(is_success, 'N', 0, 'Y', '2')) zt,\n"
				+ "         job_log_msg_begin msg,\n"
				+ "         job_log_step_id step\n"
				+ "    from etl_job_log\n" + "   where job_log_id = ?\n"
				+ "     and job_log_step_id = 0\n";

		if (!"Y".equals(sfzx)) {
			sql += "   union all\n"
					+ "    select decode(is_executing, 'Y', 1, decode(is_success, 'N', 0, 'Y', '2')) zt,\n"
					+ "         job_log_msg_end msg,\n"
					+ "         (select max(job_log_step_id)+1 step from  etl_job_log where job_log_id =t.job_log_id) step\n"
					+ "    from etl_job_log t\n"
					+ "   where job_log_id = ?\n"
					+ "     and job_log_step_id = 0\n" + "     order by step\n";
		}

		sql += "  ) t where (zt = ? or ? is null)\n";

		ArrayList<Object> params = new ArrayList<Object>();

		if (!"1".equals(isnofy)) {// 不分页查询

			sql = "select * from (\n" + sql + ") where rn<=? and rn>?";

			if (!"Y".equals(sfzx)) {

				params.add(joblogid);
				params.add(joblogid);
				params.add(joblogid);

				params.add(jgzt);
				params.add(jgzt);

				params.add(Integer.parseInt(index) * pageSize);
				params.add((Integer.parseInt(index) - 1) * pageSize);

			} else {

				params.add(joblogid);
				params.add(joblogid);

				params.add(jgzt);
				params.add(jgzt);

				params.add(Integer.parseInt(index) * pageSize);
				params.add((Integer.parseInt(index) - 1) * pageSize);

			}

		} else {
			if (!"Y".equals(sfzx)) {

				params.add(joblogid);
				params.add(joblogid);
				params.add(joblogid);
				
				params.add(jgzt);
				params.add(jgzt);

			} else {

				params.add(joblogid);
				params.add(joblogid);
				
				params.add(jgzt);
				params.add(jgzt);

			}
		}

		StringBuffer lis =new StringBuffer();

		String maxStep = "0";

		List rs = jdbcDao.queryforlist(sql, params);

		for (int i = 0; i < rs.size(); i++) {
			Map data = (Map) rs.get(i);

			maxStep = data.get("STEP").toString();
			String msg = (String) data.get("MSG");
			msg = (msg==null?"":msg).replaceAll("\n", "<br/>");
			
			lis.append("<li class=\"zxtz").append(data.get("ZT").toString()).append( "\" id='step").append(maxStep).append("' > "
						).append(msg).append( " </li> \n");

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lis", lis);
		map.put("maxStep", Integer.parseInt(maxStep));

		resmap.put("JSONDATA", JsonUtil.toJson(map));
		resEvent.setResMap(resmap);

		return resEvent;

	}

	public ResponseEvent getEtlztInFo(RequestEvent requestEvent)throws Exception {
	
		Map reqMap = requestEvent.getRequestMap();

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();
		
		String joblogid = (String) reqMap.get("joblogid");
		
		String etlzt = "";
		
		ArrayList<Object> param = new ArrayList<Object>();
		
		String sql = "SELECT MIN(B.BJ) || '（加工成功 ' || SUM(CASE\n"
				+ "                                      WHEN IS_SUCCESS = 'Y' THEN\n"
				+ "                                       1\n"
				+ "                                      ELSE\n"
				+ "                                       0\n"
				+ "                                    END) || ' 张表， 加工失败 ' ||\n"
				+ "       SUM(CASE\n"
				+ "             WHEN IS_SUCCESS = 'Y' THEN\n"
				+ "              0\n"
				+ "             ELSE\n"
				+ "              1\n"
				+ "           END) || ' 张表）' INFO\n"
				+ "  FROM etl_DATAUNIT_LOG A,\n"
				+ "       (SELECT DECODE(IS_SUCCESS, 'Y', '作业加工成功' ， '作业加工失败') BJ,\n"
				+ "               JOB_LOG_ID\n"
				+ "          FROM etl_JOB_LOG\n"
				+ "         WHERE JOB_LOG_ID = ?\n"
				+ "           AND JOB_LOG_STEP_ID = '0') B\n"
				+ " WHERE A.JOB_LOG_ID = B.JOB_LOG_ID";
		param.add(joblogid);
		List rs = jdbcDao.queryforlist(sql,param);
		
		if (rs.size()>0) {
			Map data = (Map) rs.get(0);
			etlzt = data.get("INFO").toString();
		}
		
		resmap.put("JSONDATA", etlzt);
		resEvent.setResMap(resmap);
		return resEvent;
	
	}
	 

	public ResponseEvent querySy(RequestEvent requestEvent)throws Exception {
		
		Map reqMap = requestEvent.getRequestMap();

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();
		
		String rq = (String) reqMap.get("rq");
		String lx = (String) reqMap.get("lx");

		String index = (String) reqMap.get("index");

		if (TycxUtils.isEmpty(index) ) {
			index = "0";
		}

		Integer page = Integer.valueOf(index);

		Integer pageSize = 20;

		String orderby = (String) reqMap.get("orderby");

		if(orderby==null){
			orderby="";
		}
		
		String sql = "select rownum num, a.* from (select rownum num, c.* from(select sche_log_id logid,\n"
				+ "       to_number(sche_id) sche_id,\n"
				+ "       b.task_mc mc,\n"
				+ "       decode(upper(a.is_executing), upper('Y'), '正在执行', '不在执行') zzzx,\n"
				+ "       to_char(a.begin_time,'yyyy-mm-dd hh24:mi:ss') kssj,\n"
				+ "       to_char(a.end_time,'yyyy-mm-dd hh24:mi:ss') jssj,  \n"
				+ "       round(to_number(a.end_time - a.begin_time)*24*60*60 ) zxhs, \n "
				+ "       a.sche_log_msg logmsg,\n"
				+ "       decode(upper(a.is_last),upper('Y'),'是','否') zjzx,\n"
				+ "       (case when upper(a.is_executing)=upper('Y') then '正在执行' else decode(upper(a.is_success),upper('Y'),'成功','失败') end ) zxcg "
				+ "   from etl_sche_log a, etl_task b\n"
				+ "  where a.sche_id = b.task_id(+)\n";

		// ===================

		if (lx.equals("3")) {
			sql += " and a.is_success=upper('Y') ";
		} else if (lx.equals("4")) {
			sql += " and a.is_success=upper('N') ";
		}

		sql += " and to_char(a.begin_time, 'yyyy-mm-dd') = '"
				+ rq
				+ "' and sche_id in "
				+ "(select sche_id from (select to_char(begin_time, 'yyyy-mm-dd') rq, sche_id\n"
				+ "\n"
				+ "  from etl_sche_log\n"
				+ " where begin_time < trunc(sysdate)\n"
				+ "\n"
				+ "union \n"
				+ "\n"
				+ "select to_char(sysdate, 'yyyy-mm-dd') dqsj, substr(job_name, 16)\n"
				+ "  from user_scheduler_jobs\n"
				+ " where job_subname is null\n"
				+ "   and (LAST_START_DATE >= trunc(sysdate) and\n"
				+ "       LAST_START_DATE < trunc(sysdate + 1))\n" + "\n"
				+ "    or (NEXT_RUN_DATE < trunc(sysdate + 1) and\n"
				+ "       NEXT_RUN_DATE >= trunc(sysdate))\n" + "\n"
				+ "union \n" + "\n"
				+ "select to_char(sysdate, 'yyyy-mm-dd') dqsj, sche_id\n"
				+ "  from etl_sche_log\n"
				+ " where begin_time > trunc(sysdate)) where rq = '" + rq
				+ "')";

		// ================================
		sql += orderby + " )  c) a";

		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		Map<String, Object> map = new HashMap<String, Object>();
		HashMap<String,String> vo = null;
		List rs = null;

		if (!lx.equals("5")) {

			rs = jdbcDao.queryforlist(sql);
			list = this.listLowerCase(rs);
			 

		}

		if (lx.equals("5") || lx.equals("1")) {

			// 待执行 任务
			sql = "select substr(job_name, 16) sche_id,\n"
					+ "       t2.task_mc mc,\n"
					+ "       '等待执行' zzzx， to_char(sysdate, 'yyyy-mm-dd') dqsj,\n"
					+ "       to_char(t.next_run_date, 'yyyy-mm-dd hh24:mi:ss') kssj\n"
					+ "  from user_scheduler_jobs t, etl_task t2\n"
					+ " where substr(job_name, 16) = t2.task_id\n"
					+ "   and job_subname is null\n"
					+ "   and NEXT_RUN_DATE <trunc(to_date('" + rq
					+ "','yyyy-mm-dd') +1)  \n"
					+ "   and NEXT_RUN_DATE >= trunc(to_date('" + rq
					+ "','yyyy-mm-dd'))\n" + " order by kssj";
			rs = jdbcDao.queryforlist(sql);
			
			list = this.listLowerCase(rs);
	 
		}

		map.put("count", "");
		map.put("data", list);
		
		resmap.put("JSONDATA", JsonUtil.toJson(map));
		resEvent.setResMap(resmap);

		return resEvent;
	}
	
	private List listLowerCase(List inList) {

		List outList = new ArrayList<HashMap<String, Object>>();

		Iterator it = inList.iterator();

		Map<String, Object> map = null;

		while (it.hasNext()) {

			map = new HashMap<String, Object>();

			Map<String, Object> data = (Map<String, Object>) it.next();

			for (String key : data.keySet()) {
				map.put(key.toLowerCase(), data.get(key));
			}

			outList.add(map);

		}

		return outList;

	}
	


}
