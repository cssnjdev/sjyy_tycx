package com.cwks.bizcore.etl001.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.daoUtil.CssnjDao;
import com.cwks.bizcore.help.ClobHelper;
import com.cwks.bizcore.persistence.SpManager;
import com.cwks.bizcore.persistence.outtype.BaseOutParam;
import com.cwks.bizcore.persistence.outtype.OracleCursor;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.core.cache.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sun.jdbc.rowset.CachedRowSet;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * etl首页监控信息
 * @author Administrator
 *
 */
@Component
@Service("etl001SyjkService")
public class Etl001SyjkService  {

	private static Logger logger = LoggerFactory.getLogger(Etl001SyjkService.class);
	
	@Autowired
    private CssnjDao jdbcDao;
 
 	public ResponseEvent content(RequestEvent requestEvent) throws DataAccessException,Exception {
 		
 		ResponseEvent resEvent = new ResponseEvent();
		HashMap<Object, Object> resmap = new HashMap<Object, Object>();
		
		HashMap<String, String> map = new HashMap<String, String>();
		 
		//获取系统参数
 		Map<String,String> xtcsInfo = jdbcDao.getXtcsInfo();
		
 		resmap.put("xtcsInfo",xtcsInfo);
 		
 		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/syjk.jsp");	
		
 		return resEvent;
 		
	}
 	
 	/**
 	 * 计划执行和数据单元加工日志
 	 * @param requestEvent
 	 * @return
 	 * @throws Exception
 	 */
 	public ResponseEvent etlLogs(RequestEvent requestEvent)throws Exception {
 		
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		ArrayList<Object> params = new ArrayList<Object>();
		
		BaseOutParam out1 = new BaseOutParam(); //单值
  
		OracleCursor cur1 = new OracleCursor();
		OracleCursor cur2 = new OracleCursor();
		
		params.add(out1); 
		params.add(cur1);
		params.add(cur2);

		SpManager.callProc(jdbcDao.getConnection(),"PKG_ETL_INFO.P_ETL_JK_INFO",params);
		
		CachedRowSet cs = cur1.getRowset();

		ArrayList<Map<String,Object>> rwList = new ArrayList<Map<String,Object>>();

 		while (cs.next()) {
			map = new HashMap<String, Object>();
			map.put("rq", cs.getString("rq"));
			map.put("zs", cs.getString("zs"));
			map.put("yzxs", (Integer.parseInt(cs.getString("cgs")) + Integer.parseInt(cs.getString("sbs"))) + "");
			map.put("cgs", cs.getString("cgs"));
			map.put("sbs", cs.getString("sbs"));
			map.put("dzxs", cs.getString("dzxs"));
			map.put("zzzx", cs.getString("zzzx"));
			rwList.add(map);
		}

 		/**
 		 *数据单元日志
 		 */
		ArrayList<Map<String,Object>> sjdyList = new ArrayList<Map<String,Object>>();
		CachedRowSet crs = cur2.getRowset();
		HashMap<String,Map> rq = new HashMap<String, Map>();
		
		while(crs.next()){
			map = new HashMap<String, Object>();
			map.put("zdyfl1_dm", crs.getString("zdyfl1"));
			map.put("mc", crs.getString("ds_name"));
			map.put("rq", crs.getString("rq"));
			map.put("zs", crs.getString("zs"));
			map.put("wjg", crs.getString("wjg"));
			map.put("cgs", crs.getString("cgs"));
			map.put("sbs", crs.getString("sbs"));
			map.put("jgfs", crs.getString("jgfs"));
			sjdyList.add(map);
		}
		
		
		HashMap<Object, Object> resmap = new HashMap<Object, Object>();
		JSONDATA.put("rwList",rwList);		
		JSONDATA.put("sjdyLists",sjdyList);	
		JSONDATA.put("success", "1");
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);
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
		
		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/syjkTasks.jsp");
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
		
		String start_str =  (String) requestEvent.getRequestMap().get("start");
		String length = (String) requestEvent.getRequestMap().get("length");
		String total_str = (String) requestEvent.getRequestMap().get("total");
		
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
  
		String sql =  " insert into LS_GYXX					\n" +
					  "  (VC1, VC2, VC3, VC4)	\n" + 
					  " values									\n" + 
					  "  (?, ?, ?, ?)					  ";
 
	    jdbcDao.update(sql,param);
		  
	    BaseOutParam out1 = new BaseOutParam();// 单值
	    BaseOutParam out2 = new BaseOutParam();// 单值
	    BaseOutParam out3 = new BaseOutParam();// 单值

		OracleCursor cur1 = new OracleCursor();
 		
		ArrayList<Object> params= new ArrayList<Object>();

		params.add(out1); 
		params.add(out2);
		params.add(out3);

		params.add(cur1);

		SpManager.callProc(jdbcDao.getConnection(), "PKG_ETL_INFO.P_ETL_JK_JH_INFO", params);
		 
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
 		
 		
 		
 		JSONDATA.put("total", total_str);
 		JSONDATA.put("count", total_str);
 		JSONDATA.put("iTotalDisplayRecords", total_str);
 		JSONDATA.put("iTotalRecords", total_str);
 		JSONDATA.put("data", list);
		
 		
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
		
		
		List ywklist = CacheUtil.getCodeTable("SYS_DATASOURCE", "IS_VALID='Y'") ;

		resmap.put("datelist",datelist);
		resmap.put("ywklist",ywklist); 
		
		String sql = " select zdyfl1_dm code, min(t.zdyfl1_mc) caption\n" +
				"  from SYS_DATAUNIT_fl t\n" + 
				" group by zdyfl1_dm";
			  
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
		
		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/syjkUints.jsp");

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
		 
		String state_date 	 =  (String) reqMap.get("state_date");
		String datasource_id =  (String) reqMap.get("datasource_id");
		String owner   = (String) reqMap.get("owner");
		String en_name = (String) reqMap.get("en_name");
		String jgqy    = (String) reqMap.get("jgqy");
		String jgzt    = (String) reqMap.get("jgzt");
		String sjly    = (String) reqMap.get("sjly");
		String xffs    = (String) reqMap.get("xffs");
		
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
		param.add(xffs)          ;
		
		String sql =  " insert into LS_GYXX					     \n" +
					  "  (VC1,VC2,VC3,VC4,VC5,VC6,VC7,VC8,VC9,VC10) \n" + 
					  " values									     \n" + 
					  "  (?,?,?,?,?,?,?,?,?,?)					       ";
 
	    jdbcDao.update(sql,param);
		  
	    BaseOutParam out1 = new BaseOutParam();// 单值
	    BaseOutParam out2 = new BaseOutParam();// 单值
	    BaseOutParam out3 = new BaseOutParam();// 单值

		OracleCursor cur1 = new OracleCursor();
 		
		ArrayList<Object> params= new ArrayList<Object>();

		params.add(out1); 
		params.add(out2);
		params.add(out3);

		params.add(cur1);

		SpManager.callProc(jdbcDao.getConnection(), "PKG_ETL_INFO.P_ETL_JK_DU_INFO", params);
		 
		String success = (String) out1.getValue();
		String message = (String) out2.getValue();
		total_str = (String) out3.getValue()     ;
		
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
				map.put("jgys",cs.getString("jgys"));
				map.put("last_sche_id",cs.getString("last_sche_id"));
				map.put("sche_name",cs.getString("sche_name"));
				map.put("jgzt",cs.getString("jgzt"));
				map.put("runnums", cs.getString("runnums"));
				
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
	
	public ResponseEvent sxdxCount(RequestEvent requestEvent)throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String,Object> reqMap = requestEvent.getRequestMap();
		
		BaseOutParam out1 = new BaseOutParam(); // 单值success
	    BaseOutParam out2 = new BaseOutParam(); // 单值message
	    BaseOutParam out3 = new BaseOutParam(); //
 		
		ArrayList<Object> params= new ArrayList<Object>();

		params.add(out1); 
		params.add(out2);
		params.add(out3);
	
		String success = "0";
		String message = "后台程序执行异常！";
		String sxdxms = "";
		
		try{
		
		  SpManager.callProc(jdbcDao.getConnection(), "PKG_ETL_INFO.P_ETL_JK_SXDX_INFO", params);
			
		  success = (String) out1.getValue();
		  message = (String) out2.getValue();
		  sxdxms = (String) out3.getValue();

		}catch (Exception e) {
			 
		}
 		
		
		HashMap<Object, Object> resmap = new HashMap<Object, Object>();
		
		Map<String,Object> JSONDATA = new HashMap<String, Object>();
		
		JSONDATA.put("success", success);
		JSONDATA.put("message", message);
		JSONDATA.put("text", sxdxms);
		
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap); 
		
		return resEvent;
 		
	}
	

}
