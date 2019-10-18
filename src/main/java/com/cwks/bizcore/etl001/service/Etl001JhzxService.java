package com.cwks.bizcore.etl001.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizcore.daoUtil.CssnjDao;
import com.cwks.bizcore.persistence.SpManager;
import com.cwks.bizcore.persistence.outtype.BaseOutParam;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("etl001JhzxService")
public class Etl001JhzxService  {

	private static Logger logger = LoggerFactory.getLogger(Etl001JhzxService.class);

	@Autowired
	private CssnjDao jdbcDao;
 
	public ResponseEvent init(RequestEvent requestEvent) throws Exception {

		ResponseEvent resEvent = new ResponseEvent();

		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/jhzxManager.jsp");

		return resEvent;

	}
 
	public ResponseEvent searchMl(RequestEvent requestEvent) throws Exception {

		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resmap = new HashMap<String, Object>();

		String sql = " select folder_id id,pfolder_id pid,mc name,pxxh,( \n"
				+ " select sum(count1) from                           	 \n"
				+ " (select t.folder_id, t.mc, t.pfolder_id, nvl(t2.count, 0) count1 \n"
				+ "   from sys_folder t,                    \n"
				+ "       (select count(1) count, folder_id \n"
				+ "          from etl_task	                \n"
				+ "         where xybj = '1'                \n"
				+ "         group by folder_id) t2          \n"
				+ " where t.folderlx_dm = '03'              \n"
				+ "   and t.folder_id = t2.folder_id(+))    \n"
				+ "		connect by prior folder_id = pfolder_id start with folder_id = x.folder_id) count   \n"
				+ " from sys_folder x where folderlx_dm = '03'  and upper(xybj) = upper('Y')  order by pxxh \n";

		List jgsList = jdbcDao.queryforlist(sql);

		List leftList = new ArrayList();

		HashMap<String, Object> map = new HashMap<String, Object>();
 
		for (int i = 0; i < jgsList.size(); i++) {
			map = (HashMap<String, Object>) jgsList.get(i);
			map.put("NAMEC", map.get("NAME").toString() + "("
					+ map.get("COUNT").toString() + ")");
			if (map.get("PID") == null) {
				map.put("open", "true");
			}

			leftList.add(map);
		}

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("nodes", jgsList);
		data.put("lnodes", leftList);

		resmap.put("JSONDATA", JsonUtil.toJson(data));
		resEvent.setResMap(resmap);

		return resEvent;
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws PersistenceCheckedException
	 * @throws SQLException
	 * 根据前段查询条件，返回修改作业信息列表
	 * 
	 */
	public ResponseEvent searchTaskList(RequestEvent requestEvent)
			throws Exception {

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();
		HashMap<String, Object> JSONDATA = new HashMap<String, Object>();

		String task_fl = (String) requestEvent.getRequestMap().get("task_fl");

		String start_str = (String) requestEvent.getRequestMap().get("start");
		String length = (String) requestEvent.getRequestMap().get("length");
		String total_str = (String) requestEvent.getRequestMap().get("total");

		if ("ALL".equals(task_fl)) {
			task_fl = "";
		}

		String sql = " select substr(to_char(t2.LAST_START_DATE, 'yyyy-MM-dd hh24:mi:ss'), 0, 19) lastdate,			\n"
				+ "       decode(t.smpl_dm,'06','',to_char(t2.NEXT_RUN_DATE,'yyyy-MM-dd hh24:mi:ss')) nextdate,	\n"
				+ "       t.task_mc mc,									\n"
				+ "       t.lrry_dm lrry,									\n"
				+ "       to_char(nvl(t.xgrq,t.lrrq), 'yyyy-mm-dd') lrsj,	\n"
				+ "       decode(t.xybj, 1, '启用', '0', '停用') xybj,		\n"
				+ "       t.xybj xbj,						\n"
				+ "       to_number(t.task_id) taskid,	\n"
				+ "      	nvl(t3.swryxm,'') cjrmc,		\n"
				+ "       decode(t.smpl_dm,'01','按季','02','按月','03','按周','04','按日','05','按日历','06','不定期') jgpl,	\n"
				+ "       ddsj,															 \n"
				+ "       decode((select count(1)											 \n"
				+ "                from user_scheduler_running_jobs						 \n"
				+ "               where job_name = upper('ETL_CHAIN__JOB_' || t.task_id)), \n"
				+ "               0,											\n"
				+ "              '不在执行',									\n"
				+ "              '正在执行') zxzt								\n"
				+ "  from etl_task t, user_scheduler_jobs t2, dm_gy_swry t3   \n"
				+ " where 'ETL_CHAIN__JOB_' || t.task_id = t2.JOB_NAME(+)     \n"
				+ "   and t2.job_subname is null                      		\n"
				+ "   and  nvl(t.xgry_dm,t.lrry_dm)  = t3.swry_dm(+)   		\n"
				+ "   and (T.FOLDER_ID in (select folder_id  from sys_folder  connect by prior folder_id = pfolder_id  start with folder_id = ?) or ? = '03' )              ";

		Integer endNum = Integer.parseInt(start_str) + Integer.parseInt(length);
		Integer startNum = Integer.parseInt(start_str);

		ArrayList<Object> param = new ArrayList<Object>();

		param.add(task_fl);
		param.add(task_fl);
		param.add(endNum);
		param.add(startNum);

		String dataSql = " select * from ( select t.*,rownum rn from (" + sql
				+ ") t where  rownum <=? ) g where g.rn> ? ";

		List data = jdbcDao.queryforlist(dataSql, param);

		if (!TycxUtils.isEmpty(total_str)) {
			String countSql = " select count(1) total from (" + sql + ") ";

			param = new ArrayList<Object>();
			param.add(task_fl);
			param.add(task_fl);

			Map map = jdbcDao.queryformap(countSql, param);

			total_str = map.get("TOTAL").toString();
		}

		JSONDATA.put("data", data);

		JSONDATA.put("total", total_str);
		JSONDATA.put("count", total_str);
		JSONDATA.put("iTotalDisplayRecords", total_str);
		JSONDATA.put("iTotalRecords", total_str);

		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));

		resEvent.setResMap(resmap);

		return resEvent;

	}

	public ResponseEvent openTask(RequestEvent requestEvent) throws Exception {

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();
 
		String taskid = (String) requestEvent.getRequestMap().get("task_id");

		
		if (taskid == null || taskid.equals("")) {
			
		} else {
	
			ArrayList<String> list = new ArrayList<String>();
			list.add(taskid);
			String sql = "select task_id dm,\n" + "       task_mc mc,\n"
					+ "       ms sm,\n" + "       smpl_dm jgpl,\n"
					+ "       ddrq weektree,\n" + "       ddsj zxsj,\n"
					+ "       xybj,\n"
					+ "       to_char(yxqq, 'yyyy-MM-dd') yxqq,\n"
					+ "       to_char(yxqz, 'yyyy-MM-dd') yxqz,\n"
					+ "       jgsj,\n" + "       jgsjdays,\n"
					+ "       FOLDER_ID folderid\n" + "  from etl_task\n"
					+ " where task_id = ?";
			List rs = jdbcDao.queryforlist(sql, list);
	
			if (rs.size()>0) {
				
				Map data = (Map) rs.get(0);
				
				String sm = data.get("sm")==null?"":data.get("sm").toString();
		
				resmap.put("mc", data.get("mc"));
				
				resmap.put("sm", sm.replaceAll("\r\n", "<br/>").replaceAll(" ", "&nbsp;"));
				resmap.put("dm", data.get("dm"));
				resmap.put("jgpl", data.get("jgpl"));
				resmap.put("weektree", data.get("weektree"));
				resmap.put("zxsj", data.get("zxsj"));
				resmap.put("xybj", data.get("xybj"));
				resmap.put("yxqq", data.get("yxqq"));
				resmap.put("yxqz", data.get("yxqz"));
				resmap.put("jgsj", data.get("jgsj"));
				resmap.put("jgsjdays", data.get("jgsjdays"));
				resmap.put("folderid", data.get("folderid"));
				
			}
	
	    }
		
		resmap.put("taskid", taskid);
 		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/jhzxMx.jsp");
		return resEvent;

	}

	
	public ResponseEvent queryFlow(RequestEvent requestEvent) throws Exception {
 
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resmap = new HashMap<String, Object>();

		String task_id = (String) requestEvent.getRequestMap().get("dm");

		List nodesList = new ArrayList<HashMap<String, Object>>();
		List lineList = new ArrayList<HashMap<String, Object>>();
		List nodeParam = new ArrayList<HashMap<String, Object>>();

		String logid = "";
		
		ArrayList<String> list = new ArrayList<String>();

		 
		String sql = "select dm,cszlx,csz from dm_dataunit_sjld_sj_cs where xybj = 1";
		List<HashMap<String, String>> sjfklist = new ArrayList<HashMap<String, String>>();

		List crs = jdbcDao.queryforlist(sql);
		Map<String, Object> sjjgfwMap = new HashMap<String, Object>();

		Map<String,Object> data = new HashMap<String, Object>();
		
		for (int i = 0; i < crs.size(); i++) {
			data = (Map) crs.get(i);
			String cszlx = (String) data.get("CSZLX");
			if("0".equals(cszlx)){
				sjjgfwMap.put(data.get("DM").toString(), data.get("CSZ").toString());
			}else{
				String cszSql =" select "+ data.get("CSZ").toString() +" csz from dual";
			    SqlRowSet rowSet= jdbcDao.queryforRowset(cszSql);
				if(rowSet.next()){
					sjjgfwMap.put(data.get("DM").toString(),rowSet.getString("csz"));

				}
			}
		}
 
		resmap.put("sjjgfwMap", JsonUtil.toJson(sjjgfwMap));

		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if (!TycxUtils.isEmpty(task_id)) {
 
			list = new ArrayList<String>();
			list.add(task_id);
			
			String ztzx="";
			
			sql = " select decode(count(*), 0, 'N', 'Y') ztzx				\n"
				+ "  from user_scheduler_running_jobs						\n"
				+ " where upper(job_name) = upper('ETL_CHAIN__JOB_' || ?)	";
			
			List rs = jdbcDao.queryforlist(sql,list);
			for(int i=0;i<rs.size();i++){
 				data = (Map) rs.get(i);
 				ztzx = data.get("ZTZX").toString();
 				resmap.put("ztzx", ztzx);
			}

			sql = " select start_etl_id  ,end_etl_id   from etl_task_line where task_id = ? ";
			rs = jdbcDao.queryforlist(sql, list);
			for(int i=0;i<rs.size();i++){
				map = new HashMap<String, Object>();
				data = (Map) rs.get(i);
				map.put("from", data.get("START_ETL_ID"));
				map.put("to", data.get("END_ETL_ID"));
				lineList.add(map);
			}

			sql = " select sche_log_id logid,is_executing ztzx from etl_sche_log where upper(is_last) = upper('y') and sche_id = ? ";
			rs = jdbcDao.queryforlist(sql, list);

			for(int i=0;i<rs.size();i++){
 				data = (Map) rs.get(i);
				logid = data.get("LOGID").toString();
			}

			sql = "select distinct t.etl_id,\n"
					+ "       nvl(g.mc, t.etl_id) etlmc,\n"
					+ "       nvl(top,0) top,\n"
					+ "       nvl(left,0) left,\n"
					+ "       (case upper(f.is_executing) \n"
					+ "         when upper('N') then decode(upper(f.is_success), upper('Y'), '2', '3')  \n"
					+ "         when upper('Y') then '1' else decode(upper('" + ztzx+ "'),upper('Y'),'4','0') end) zt,   \n"
					+ "       (case upper(f.is_executing)  when upper('Y') then  decode(k.step_name, null, 1, 0) else 0 end) iserror, \n"
					+ "        nvl(g.sjjgfw,'01') sjjgfw  \n"
					+ "  from etl_task_nodes t,\n"
					+ "       etl_def g,\n"
					+ "       (select *\n"
					+ "          from etl_job_log\n"
					+ "         where sche_id = ? \n"
					+ "           and sche_log_id = ? \n"
					+ "           and job_log_step_id = '0') f, \n"
					+ "       user_scheduler_running_chains k\n"
					+ " where task_id = ? \n"
					+ "   and upper('CHAIN_STEP_' || t.etl_id || '_' || t.task_id) =\n"
					+ "       k.step_name(+)\n"
					+ "   and t.etl_id = g.etl_id(+)\n"
					+ "   and g.etl_id = f.etl_id(+)";

			list = new ArrayList<String>();
			list.add(task_id);
			list.add(logid);
			list.add(task_id);
			
			rs = jdbcDao.queryforlist(sql, list);

			for(int i=0;i<rs.size();i++){
 				data = (Map) rs.get(i);
 				map = new HashMap<String, Object>();
 				map.put("etlid", data.get("ETL_ID"));
				map.put("etlmc", data.get("ETLMC"));
				map.put("x", data.get("LEFT"));
				map.put("y", data.get("TOP"));
				map.put("sjjgfw", data.get("SJJGFW"));
				if (data.get("ETL_ID").equals("start") || data.get("ETL_ID").equals("end")) {
					nodesList.add(map);
					continue;
				}
				map.put("zt", data.get("ZT"));
				
				String sjjgfw = data.get("SJJGFW").toString();
				
				if ("01".equals(sjjgfw)) {
					
					sql = "select 1 from dual where 1=2 ";
					
 				}else if ("02".equals(sjjgfw)) { //年
						
					sql = "select distinct cwqj_id id,sjcwqj_id pid,mc name,decode(qjlx_id,'1','1','0') bj from DM_GY_CWQJ where yx_bj = '1' and qjlx_id  = '1'";
				 
				}else if ("03".equals(sjjgfw)) {  //半年
					
					sql = "select distinct cwqj_id id,sjcwqj_id pid,mc name,decode(qjlx_id,'2','1','0') bj from DM_GY_CWQJ connect  by prior sjcwqj_id = cwqj_id start with cwqj_id in ( select cwqj_id from DM_GY_CWQJ where yx_bj = '1' and qjlx_id  = '2') ";
					 
				}else if ("04".equals(sjjgfw)) {  //季度
		
					sql = "select distinct cwqj_id id,sjcwqj_id pid,mc name,decode(qjlx_id,'3','1','0') bj from DM_GY_CWQJ connect  by prior sjcwqj_id = cwqj_id start with cwqj_id in ( select cwqj_id from DM_GY_CWQJ where  yx_bj = '1' and qjlx_id  = '3')";
				
				}else if ("05".equals(sjjgfw)) {  //月
					
					sql = "select distinct cwqj_id id,sjcwqj_id pid,mc name,decode(qjlx_id,'4','1','0') bj from DM_GY_CWQJ connect  by prior sjcwqj_id = cwqj_id start with cwqj_id in ( select cwqj_id from DM_GY_CWQJ where  yx_bj = '1' and qjlx_id  = '4')";
				 
				} 
				
				List list2 = jdbcDao.queryforlist(sql);
				map.put("sjtree", this.listLowerCase(list2));
				
 				nodesList.add(map);
 				
			}
			 
 			
			
			if(!"Y".equals(ztzx)){
				
				sql =   " select a.etl_id etlid, \n" +
				    	"       replace(wm_concat(params_dm || '-' ||decode(params_dm,'sftg','N',params_value) ), ',', ';') params \n" + 
				    	"  from etl_task_params a, etl_task_nodes b \n" + 
				    	" where a.task_id = ? \n" + 
				    	"   and a.task_id = b.task_id \n" + 
				    	"   and a.etl_id = b.etl_id \n" + 
				    	" group by a.etl_id    ";
		
				
			}else{
				
				 sql =  " select a.etl_id etlid, \n" +
						"       replace(wm_concat(params_dm || '-' ||params_value), ',', ';') params \n" + 
						"  from etl_task_she_params a, etl_task_nodes b \n" + 
						" where a.she_id = ? \n" + 
						"   and a.task_id = b.task_id \n" + 
						"   and a.etl_id = b.etl_id \n"+
				        " group by a.etl_id    ";
			}
			
			
			list = new ArrayList<String>(); 
			list.add(task_id);
			rs = jdbcDao.queryforlist(sql, list);
			nodeParam = this.listLowerCase(rs);
			
		}

		resmap.put("nodeParam", JsonUtil.toJson(nodeParam));
		
		resmap.put("logid", logid);
		resmap.put("nodesList", JsonUtil.toJson(nodesList));
		resmap.put("lineList", JsonUtil.toJson(lineList));
		resmap.put("taskid", task_id);
		
		resEvent.setResMap(resmap) ;
		
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/jhzxTopo.jsp");
		return resEvent; 

	}
	
	// 查询节点执行状态
	public ResponseEvent queryZxzt(RequestEvent requestEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
	
		HashMap<String, Object> resmap = new HashMap<String, Object>();
		HashMap<String, Object> JSONDATA = new HashMap<String, Object>();
		
		String taskid = (String) requestEvent.getRequestMap().get("taskid");
		String logid = (String) requestEvent.getRequestMap().get("logid");
		String ztzx = null;
		
		
		ArrayList<String> list = new ArrayList<String>();
		list.add(taskid);
	
		String zxjg = "";
		String tasklog = "";	
		String sqls = 
				"SELECT SCHE_LOG_ID LOGID,\n" +
				"		IS_EXECUTING ZTZX ,\n" +
				"       SCHE_LOG_MSG TASKLOG,\n" + 
				"       DECODE(IS_EXECUTING,\n" + 
				"              'Y',\n" + 
				"              '正在执行',\n" + 
				"              DECODE(IS_SUCCESS,\n" + 
				"                     'Y',\n" + 
				"                     '执行成功',\n" + 
				"                     'N',\n" + 
				"                     '执行失败',\n" + 
				"                     '未执行')) ZXJG\n" +
				"FROM etl_SCHE_LOG WHERE UPPER(IS_LAST) = UPPER('Y') AND SCHE_LOG_ID = ? ";
		list = new ArrayList<String>();list.add(logid);
		List rs = jdbcDao.queryforlist(sqls,list);
		for(int i=0;i<rs.size();i++){
			Map data = (Map) rs.get(0);
			ztzx = data.get("ztzx").toString();
			zxjg = data.get("zxjg").toString();
			tasklog = data.get("zxjg").toString().replaceAll("\n", "<br/>");
		}
	 
		if ("Y".equals(ztzx)) { // 单前日志正在执行判断系统job是否正在执行
			
			sqls = "select decode(count(*), 0, 'N', 'Y') ztzx \n"
					+ "  from user_scheduler_running_jobs\n"
					+ " where upper(job_name) = upper('ETL_CHAIN__JOB_' || ?)";
			list = new ArrayList<String>();list.add(taskid);
			rs = jdbcDao.queryforlist(sqls,list);
	
			if (rs.size()>0) {
				Map data = (Map) rs.get(0);
				ztzx = data.get("ztzx").toString();
				 
			}
	
		}
		
		
		String sql = "select t.etl_id etlid,\n"
				+ "       (case upper(f.is_executing)\n"
				+ "         when upper('N') then decode(upper(f.is_success), upper('Y'), '2', '3')\n"
				+ "         when upper('Y') then '1'\n"
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
				+ "         where sche_id = ? \n"
				+ "           and sche_log_id = ? \n"
				+ "           and job_log_step_id = '0') f, \n"
				+ "       user_scheduler_running_chains k\n"
				+ " where task_id = ? \n"
				+ "   and upper('CHAIN_STEP_' || t.etl_id || '_' || t.task_id) =\n"
				+ "       k.step_name(+)\n" + "   and t.etl_id = g.etl_id(+)\n"
				+ "   and g.etl_id = f.etl_id(+)";
		list = new ArrayList<String>();list.add(taskid);list.add(logid);list.add(taskid);
		rs = jdbcDao.queryforlist(sql,list);
 		List nodesList = this.listLowerCase(rs);
		
 		
 		JSONDATA.put("ztzx", ztzx);
		JSONDATA.put("nodesList", nodesList);
		JSONDATA.put("zxjg", zxjg);
		JSONDATA.put("tasklog", tasklog);
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);
		return resEvent; 
	}
	
	public ResponseEvent lijizx(RequestEvent requestEvent) throws Exception {
	
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String, Object> resmap = new HashMap<String, Object>();
		HashMap<String, Object> JSONDATA = new HashMap<String, Object>();
		
		String taskid =  (String) requestEvent.getRequestMap().get("taskid");
		
		String nodeparamstr = (String) requestEvent.getRequestMap().get("nodeparamstr");
	
	
		ArrayList<Object> param = new ArrayList<Object>();
		
		String testSql = " select * from user_scheduler_running_jobs where upper(job_name) = upper('ETL_CHAIN__JOB_'||?) and rownum=1 ";
		param = new ArrayList<Object>();param.add(taskid);
		List crs = jdbcDao.queryforlist(testSql,param);
		if(crs.size()>0){
			JSONDATA.put("message", "当前计划正在执行 ，不能执行");
			resmap.put("JSONDATA", JsonUtil.toJson (JSONDATA));
			resEvent.setResMap(resmap);
			return resEvent; 
		}
		
		String uuid = UUIDGenerator.getUUID();
		
		if (nodeparamstr != null) {
			
			JSONArray array = JSONArray.parseArray(nodeparamstr);
			
			/*
			if (array.size() == 0) {
				JSONDATA.put("message", "请先 右键 节点,保存  立即执行参数");
				resmap.put("JSONDATA", JsonUtil.toJson (JSONDATA));
				resEvent.setResMap(resmap);
				return resEvent; 
			}
			*/
			
			UserContext ctx = requestEvent.getCtx();
	    	String swry_dm = "";
	    	String swjg_dm = "";
	    	if(ctx!=null){
	 	    	Map userInfo =	ctx.getUserinfo();
	 	    	swry_dm =(String)userInfo.get("userId");
	 	    	swjg_dm =(String)userInfo.get("swjg_dm");
	  	    } 
			
			String etlid = "";
			String params = "";
			String sftg = "";
			String sjcs = "";
			
			String sql = "insert into LS_GYXX (vc1,vc2,vc3,vc4,vc5,vc6,vc7) values (?,?,?,?,?,?,?)";
			
			HashMap<String, String> paramMap = null;
			for (int i=0; i<array.size(); i++){
				
				JSONObject jsonObject = array.getJSONObject(i);
				etlid = jsonObject.getString("etlid");
				params = jsonObject.getString("params");
				
				paramMap = new HashMap<String, String>();
				String[] li = params.split(";");
				for(int k=0;k<li.length;k++){
					String[] val = li[k].split("-");
					if(val.length>1){
						paramMap.put(val[0], val[1]);
					}else{
						paramMap.put(val[0], null);
					}
				}
			 
				param = new ArrayList<Object>();
				param.add(etlid);
				param.add(paramMap.get("tree")==null?"ALL":paramMap.get("tree"));
				param.add("");
				param.add(swry_dm);
				param.add(swjg_dm);
				param.add(paramMap.get("sftg")==null?"N":paramMap.get("sftg"));
				param.add(paramMap.get("sbjxzx")==null?"Y":paramMap.get("sbjxzx"));
				jdbcDao.update(sql, param);
				
			}
			
			
		}
		
		
		List<Object> paralist = new ArrayList<Object>();
		
		BaseOutParam out1 = new BaseOutParam();// 单值
		BaseOutParam out2 = new BaseOutParam();// 单值

		paralist.add(uuid);
		paralist.add(taskid);
		paralist.add("0");
		paralist.add(out1);
		paralist.add(out2);
		
		try {
			
			SpManager.callProc(jdbcDao.getConnection(), "PKG_ETL.P_ETL_PARAMS", paralist);

			String succ = (String) out1.getValue();
			String message = (String) out2.getValue();
 			
			if ("N".equals(succ)) {
				
 				JSONDATA.put("message",message);
				resmap.put("JSONDATA", JsonUtil.toJson (JSONDATA));
				resEvent.setResMap(resmap);
				return resEvent; 
				
			}
			
		} catch (Exception e) {
			
			JSONDATA.put("message", "生成加工任务失败！");
			resmap.put("JSONDATA", JsonUtil.toJson (JSONDATA));
			resEvent.setResMap(resmap);
			return resEvent; 
		 
		}
		
		

	    paralist = new ArrayList<Object>();

		out1 = new BaseOutParam();// 单值
		out2 = new BaseOutParam();// 单值

		paralist.add(taskid);
		paralist.add(uuid);
		paralist.add(out1);
		paralist.add(out2);
		

		try {
			
			SpManager.callProc(jdbcDao.getConnection(),"PKG_ETL.P_ETL_SCHEDULER_MANUAL", paralist);
			
			JSONDATA.put("success", (String) out1.getValue());
			JSONDATA.put("message", (String) out2.getValue());
			JSONDATA.put("logid", uuid.toString());
			

		} catch (Exception e) {
			
			JSONDATA.put("message", "生成加工任务失败！");
			
		}
		
		resmap.put("JSONDATA", JsonUtil.toJson (JSONDATA));
		resEvent.setResMap(resmap);
		return resEvent; 
		
	}	
	
	public ResponseEvent stopTask(RequestEvent requestEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String, Object> resmap = new HashMap<String, Object>();
		HashMap<String, Object> JSONDATA = new HashMap<String, Object>();
		
		String taskid = (String) requestEvent.getRequestMap().get("taskid");
		
		String ztzx = "";
		
		ArrayList<Object> param = new ArrayList<Object>();
		
		String sqls = "select decode(count(*), 0, 'N', 'Y') ztzx\n"
				+ "  from user_scheduler_running_jobs\n"
				+ " where upper(job_name) = upper('ETL_CHAIN__JOB_' || ?)";
		param = new ArrayList<Object>();param.add(taskid);
		List rs = jdbcDao.queryforlist(sqls,param);
		
		if (rs.size()>0) {
			Map data = (Map) rs.get(0);
			ztzx = data.get("ztzx").toString();
			resmap.put("ztzx", ztzx);
		}
		
		if ("Y".equals(ztzx)) {
			
			List<Object> paralist = new ArrayList<Object>();
			
			BaseOutParam out = new BaseOutParam(); // 单值
			BaseOutParam out1 = new BaseOutParam(); // 单值
	
			paralist.add(taskid);
			paralist.add("stop");
	
			paralist.add(out);
			paralist.add(out1);
			
			try {
				
				SpManager.callProc(jdbcDao.getConnection(),"PKG_ETL.P_ETL_SCHEDULER_PLATFORM", paralist);
				JSONDATA.put("success", (String) out.getValue());
				JSONDATA.put("message", (String) out1.getValue());
	
			} catch (Exception e) {
				JSONDATA.put("success", "0");
				JSONDATA.put("message", "执行停用任务异常");
			}
			
			
		}else {
	
			JSONDATA.put("success", "0");
			JSONDATA.put("message", "当前任务已不在执行");
		}
	
		resmap.put("JSONDATA", JsonUtil.toJson (JSONDATA));
		resEvent.setResMap(resmap);
		
		return resEvent;
		
	}  
 

	private List listLowerCase(List inList){
		
		List outList = new ArrayList<HashMap<String,Object>>();
		
		Iterator it =	inList.iterator();
		
		Map<String,Object> map = null;
		
		while(it.hasNext()){
			
			map = new HashMap<String, Object>();
			
			Map<String,Object> data = (Map<String, Object>) it.next();
			
			for(String key:data.keySet()){
				map.put(key.toLowerCase(), data.get(key));
			}
			
			outList.add(map);
			
		}
		
		return outList;
		
	}
	


}
