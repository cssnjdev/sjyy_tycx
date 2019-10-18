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

@Service("etl001JhglService")
public class Etl001JhglService  {

	private static Logger logger = LoggerFactory.getLogger(Etl001JhglService.class);
	UserContext ctx;
	@Autowired
	private CssnjDao jdbcDao;
  
	public ResponseEvent init(RequestEvent requestEvent) throws Exception {

		ResponseEvent resEvent = new ResponseEvent();

		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/jhglManager.jsp");

		return resEvent;

	}

	public ResponseEvent searchMl(RequestEvent requestEvent) throws Exception {

		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resmap = new HashMap<String, Object>();

		String sql = " select folder_id id,pfolder_id pid,mc name,pxxh,( \n"
				+ " select sum(count1) from                           \n"
				+ " (select t.folder_id, t.mc, t.pfolder_id, nvl(t2.count, 0) count1	\n"
				+ "   from sys_folder t,                    \n"
				+ "       (select count(1) count, folder_id \n"
				+ "          from etl_task	               \n"
				+ "         where xybj = '1'                \n"
				+ "         group by folder_id) t2          \n"
				+ " where t.folderlx_dm = '03'              \n"
				+ "   and t.folder_id = t2.folder_id(+))    \n"
				+ "		connect by prior folder_id = pfolder_id start with folder_id = x.folder_id) count \n"
				+ " from sys_folder x where folderlx_dm = '03' and upper(xybj) = upper('Y') order by pxxh \n";

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
	 * 保存目录
	 * 
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent saveMl(RequestEvent requestEvent) throws Exception {

		ResponseEvent resEvent = new ResponseEvent();

		String mldm = (String) requestEvent.getRequestMap().get("mldm");
		String mlmc = (String) requestEvent.getRequestMap().get("mlmc");
		String sjml = (String) requestEvent.getRequestMap().get("sjml");
		String pxxh = (String) requestEvent.getRequestMap().get("pxxh");

		String swry_dm = "";
		if (ctx != null) {
			Map userInfo = ctx.getUserinfo();
			swry_dm = (String) userInfo.get("userId");
		}

		ArrayList<String> params = new ArrayList<String>();
		UserContext ctx = requestEvent.getCtx();

		String sql = null;

		if (TycxUtils.isEmpty(mldm)) { // 插入新分类

			mldm = UUIDGenerator.getUUID();

			sql = " INSERT INTO SYS_FOLDER                                                  \n"
					+ "  (MC, MC_J, PFOLDER_ID, FOLDERLX_DM, SSSWJG_DM, XYBJ, PXXH,FOLDER_ID)   \n"
					+ " VALUES                                                                  \n"
					+ "  (?, ?, ?, '03', ?, 'Y',?,?)                                              ";

		} else { // 更新分类

			sql = " UPDATE SYS_FOLDER                                                         \n"
					+ "  SET MC =?, MC_J=?, PFOLDER_ID=?, FOLDERLX_DM='03', SSSWJG_DM=?,  PXXH=?  \n"
					+ " WHERE FOLDER_ID=?                                                   		 ";

		}

		params.add(mlmc);
		params.add(mlmc);
		params.add(sjml);
		params.add(swry_dm);
		params.add(pxxh);
		params.add(mldm);

		HashMap<String, Object> resmap = new HashMap<String, Object>();
		HashMap<String, Object> data = new HashMap<String, Object>();

		try {
			jdbcDao.update(sql, params);
			data.put("success", "1");
		} catch (Exception e) {
			data.put("success", "0");
		}

		resmap.put("JSONDATA", JsonUtil.toJson(data));
		resEvent.setResMap(resmap);
		return resEvent;

	}

	
	public ResponseEvent moveML(RequestEvent requestEvent){
		
		ResponseEvent resEvent = new ResponseEvent();
			
		Map reqMap = requestEvent.getRequestMap();
		
		String sjdydms = (String) reqMap.get("ydUnit");
		
		sjdydms = sjdydms.replaceAll(",", "','");
			
		String sjmvml = (String) reqMap.get("sjmvml");
		
		HashMap<String, Object> jsonData = new HashMap<String, Object>();
 		
		String sql = "update etl_task set folder_id = '" + sjmvml+ "' where etl_id in ('" + sjdydms + "')";
		
		Map<String,Object> JSONDATA = new HashMap<String, Object>();
		
		try{
		
			jdbcDao.update(sql);
			JSONDATA.put("success", "1");
			
		}catch (Exception e) {
			
			JSONDATA.put("success", "0");

			// TODO: handle exception
		}
		
		HashMap<String,Object> reqmap = new HashMap<String, Object>(); 
		
		reqmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		
		resEvent.setResMap(reqmap);
    	return resEvent;
	
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws PersistenceCheckedException
	 * @throws SQLException
	 *             根据前段查询条件，返回修改作业信息列表
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
				+ "       nvl(t3.swryxm,'') cjrmc,		\n"
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

		if (TycxUtils.isEmpty(total_str)) {
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
		HashMap<String, Object> JSONDATA = new HashMap<String, Object>();

		String task_id = (String) requestEvent.getRequestMap().get("task_id");

		String folderid = (String) requestEvent.getRequestMap().get("folderid");

		String sql = " select 'MON' code,'周一' caption,1 xh from dual    	   \n"
				+ "	union select 'TUE' code,'周二' caption,2 xh from dual  \n"
				+ "	union select 'WED' code,'周三' caption,3 xh from dual  \n"
				+ "	union select 'THU' code,'周四' caption,4 xh from dual  \n"
				+ "	union select 'FRI' code,'周五' caption,5 xh from dual  \n"
				+ "	union select 'SAT' code,'周六' caption,6 xh from dual  \n"
				+ "	union select 'SUN' code,'周日' caption,7 xh from dual  \n"
				+ " order by xh	                                            ";

		List tree = jdbcDao.queryforlist(sql);
		JSONDATA.put("tree", tree);

		if (task_id == null || task_id.equals("")) {

			if ("ALL".equals(folderid) || TycxUtils.isEmpty(folderid)) {
				folderid = "03";
			}

			resmap.put("folderid", folderid);

		} else {

			ArrayList<String> param = new ArrayList<String>();
			param.add(task_id);

			sql = " select task_id dm,	    \n" +
					"		task_mc mc,		\n" +
					"       nvl(ms,'') sm,  \n" +
					"  		smpl_dm jgpl,	\n" + 
					"       jgsj weektree,	\n" +
					"  ddsj zxsj,			\n" +
					"       xybj,			\n" + 
					"       to_char(yxqq, 'yyyy-MM-dd') yxqq,	\n"+ 
					"       to_char(yxqz, 'yyyy-MM-dd') yxqz,	\n"+
					"       jgsj,			\n" +
					"		jgsjdays,		\n"+ 
					"       FOLDER_ID folderid	\n" +
					"  from etl_task			\n" + 
					" where task_id = ?	  		\n" ;

			List rs = jdbcDao.queryforlist(sql, param);

			if (rs.size() > 0) {

				Map map = (Map) rs.get(0);

				String sm = map.get("SM")==null?"":map.get("SM").toString();

				resmap.put("mc", map.get("mc"));

				if (sm != null) {
					resmap.put( "sm",sm.replaceAll("\r\n", "<br/>").replaceAll(" ","&nbsp;"));
				} else {
					resmap.put("sm", "");
				}

				resmap.put("dm", map.get("dm"));
				resmap.put("jgpl", map.get("jgpl"));
				resmap.put("weektree", map.get("weektree"));
				resmap.put("zxsj", map.get("zxsj"));
				resmap.put("xybj", map.get("xybj").toString().trim());
				resmap.put("yxqq", map.get("yxqq"));
				resmap.put("yxqz", map.get("yxqz"));
				resmap.put("jgsj", map.get("jgsj"));
				resmap.put("jgsjdays", map.get("jgsjdays"));
				resmap.put("folderid", map.get("folderid"));

			}

		}

		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/jhglEdit.jsp");
		return resEvent;

	}

	// tab 1 数据单元 保存
	public ResponseEvent rwxxSave(RequestEvent requestEvent) throws Exception {

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();
		HashMap<String, Object> JSONDATA = new HashMap<String, Object>();

		String folderid = (String) requestEvent.getRequestMap().get("folderid");
		String dm = (String) requestEvent.getRequestMap().get("dm");
		String jgweek = (String) requestEvent.getRequestMap().get("weekdays");
		String mc = (String) requestEvent.getRequestMap().get("mc");
		String jgpl = (String) requestEvent.getRequestMap().get("jgpl"); // 加工频率
		String ay = (String) requestEvent.getRequestMap().get("ay");
		String jgmonthDays = (String) requestEvent.getRequestMap().get("ycmts");

		ArrayList<Object> param = new ArrayList<Object>();
		param.add(dm);
		String testSql = " select * from user_scheduler_running_jobs where upper(job_name) = upper('ETL_CHAIN__JOB_'||?) ";

		List crs = jdbcDao.queryforlist(testSql, param);

		if (crs.size() > 0) {
			JSONDATA.put("success", "0");
			JSONDATA.put("message", "当前计划正在执行 ，不能修改");
			resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
			resEvent.setResMap(resmap);
			return resEvent;
		}

		String aj = (String) requestEvent.getRequestMap().get("aj");
		String jgseasonDays = (String) requestEvent.getRequestMap()
				.get("jcmts");
		String zxsj = (String) requestEvent.getRequestMap().get("zxsj");
		String datetimepicker = (String) requestEvent.getRequestMap().get(
				"datetimepicker");

		String datetimepicker2 = (String) requestEvent.getRequestMap().get(
				"datetimepicker2");
		String zt = (String) (String) requestEvent.getRequestMap().get("zt");
		String sm = (String) (String) requestEvent.getRequestMap().get("sm");
		String jgrlDays = (String) requestEvent.getRequestMap().get("days");

		String xybj = (String) requestEvent.getRequestMap().get("zt");

		sm = sm.replaceAll("@@", "\r");
		sm = sm.replaceAll("##", "\n");

		String jgseason = "";
		String jgmonth = "";
		if (ay != null && !ay.equals("")) {
			jgmonth = ay;
		}
		if (aj != null && !aj.equals("")) {
			jgseason = aj;
		}

		// 校验开始

		String sql1 = " select count(1) flag from dual where ?<? ";

		param = new ArrayList<Object>();
		param.add(datetimepicker + " 00:00:00");
		param.add(datetimepicker + " 23:59:59");
		List rst = jdbcDao.queryforlist(sql1, param);

		if (rst.size() > 0) {

			Map map = (Map) rst.get(0);

			String flag = map.get("flag").toString();
			if ("0".equals(flag)) {
				JSONDATA.put("success", "0");
				JSONDATA.put("message", "保存失败，有效期起不能大于有效期止");
				resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
				resEvent.setResMap(resmap);
				return resEvent;
			}

		}

		sql1 = " select count(1) flag										\n"
				+ "  from ( 													\n"
				+ "         select to_date(?|| column_value,					\n"
				+ "                       'YYYY-MM-DD hh24:mi:ss') zxsj		\n"
				+ "          from table(select PKG_PUB_FUN.FUN_STR_SPLIT(?, ',') from dual)	\n"
				+ "        ) 													\n"
				+ " where sysdate <zxsj										  ";

		// 校验提供的有效期，执行时间是否小于当前的系统时间 ，小于则提示保存失败
		try {

			if ("06".equals(jgpl)) {

				param = new ArrayList<Object>();
				param.add(datetimepicker2);
				param.add("23:59:59");
				rst = jdbcDao.queryforlist(sql1, param);

			} else {

				param = new ArrayList<Object>();
				param.add(datetimepicker2);
				param.add(zxsj);
				rst = jdbcDao.queryforlist(sql1, param);

			}

		} catch (Exception e) {

			JSONDATA.put("success", "0");
			JSONDATA.put("message", "所填写的执行时间格式有误！");
			resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
			resEvent.setResMap(resmap);

			return resEvent;

		}

		if (rst.size() > 0) {

			String flag = (String) requestEvent.getRequestMap().get("flag");

			if ("0".equals(flag)) {
				JSONDATA.put("success", "0");
				JSONDATA.put("message", "保存失败，当前系统时间已超出有效期内的执行时间！");
				resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
				resEvent.setResMap(resmap);
				return resEvent;
			}

		}

		// 校验结束
		// HashMap<String, String> map = (HashMap<String, String>)
		// req.getFormData("dataForm");

		String czrydm = "";
		String dljgdm = "";

		ctx = requestEvent.getCtx();
		if (ctx != null) {
			Map userInfo = ctx.getUserinfo();
			czrydm = (String) userInfo.get("userId");
			dljgdm = (String) userInfo.get("swjg_dm");
		}

		String hour = "";
		String minute = "";
		String second = "";

		if (zxsj.length() > 0) {
			String sj = "";
			for (int i = 0; i < zxsj.split(",").length; i++) {
				sj = zxsj.split(",")[i];
				hour += sj.split(":")[0] + ",";
				minute += sj.split(":")[1] + ",";
				second += sj.split(":")[2] + ",";
			}

			if (hour.endsWith(",")) {
				hour = hour.substring(0, hour.length() - 1);
			}

			if (minute.endsWith(",")) {
				minute = minute.substring(0, minute.length() - 1);
			}

			if (second.endsWith(",")) {
				second = second.substring(0, second.length() - 1);
			}
		}

		String gz = "";
		String jgsj = "";
		String jgsjDays = "";

		// 季
		if (jgpl.equals("01")) {
			
			if("0".equals(jgseason)){
				gz = "FREQ=YEARLY;BYMONTH=JAN,APR,JUL,OCT;BYMONTHDAY="+jgseasonDays+";BYHOUR="
					+ hour + ";BYMINUTE=" + minute + ";BYSECOND=" + second; 
			}else if("1".equals(jgseason)){//季末
				gz = "FREQ=YEARLY;BYMONTH=MAR,JUN,SEP,DEC;BYMONTHDAY=-"+jgseasonDays+";BYHOUR="
						+ hour + ";BYMINUTE=" + minute + ";BYSECOND=" + second; 
			}
			jgsj = jgseason;			
			jgsjDays = jgseasonDays;
		}
		// 月
		else if (jgpl.equals("02")) {

			String bymonthday = "";

			if ("1".equals(jgmonth)) {
				bymonthday += '-' + jgmonthDays;
			} else {
				bymonthday = jgmonthDays;
			}

			gz = "FREQ=MONTHLY;BYMONTHDAY=" + bymonthday + ";BYHOUR=" + hour
					+ ";BYMINUTE=" + minute + ";BYSECOND=" + second;

			jgsj = jgmonth;
			jgsjDays = jgmonthDays;

		}
		// 周
		else if (jgpl.equals("03")) {
			gz = "FREQ=WEEKLY;BYDAY=" + jgweek + ";BYHOUR=" + hour
					+ ";BYMINUTE=" + minute + ";BYSECOND=" + second;
			jgsj = jgweek;
		}
		// 日
		else if (jgpl.equals("04")) {
			gz = "FREQ=DAILY;BYHOUR=" + hour + ";BYMINUTE=" + minute
					+ ";BYSECOND=" + second;
		}
		// 按日历
		else if (jgpl.equals("05")) {
			gz = "FREQ=DAILY;BYMONTHDAY="+jgrlDays+";BYHOUR=" + hour + ";BYMINUTE=" + minute
					+ ";BYSECOND=" + second;
			jgsj = jgrlDays;
		}

		ArrayList<String> list = new ArrayList<String>();

		if (dm == null || dm.equals("")) {

			String sql = "select PKG_PUB_FUN.FUN_GET_ETL_COMM_SEQ() a from dual";
			List rs = jdbcDao.queryforlist(sql);

			if (rs.size() > 0) {
				Map map = (Map) rs.get(0);
				dm = map.get("a").toString();
			}

			// 新建
			sql = " insert into etl_task a \n"
					+ "  (task_id, task_mc, ms, smpl_dm, ddsj, gz,xybj,lrrq,lrry_dm,lrjg_dm,folder_id,jgsj,jgsjdays,yxqq,yxqz) \n"
					+ " values (?,?,?,?,?,?,?,sysdate,?,?,?,?,?,to_date(?,'yyyy-MM-dd hh24:mi:ss'),to_date(?,'yyyy-MM-dd hh24:mi:ss') ) ";

			list.add(dm);
			list.add(mc);
			list.add(sm);
			list.add(jgpl);
			list.add(zxsj);
			list.add(gz);
			list.add(xybj);
			list.add(czrydm);
			list.add(dljgdm);
			list.add(folderid);
			list.add(jgsj);
			list.add(jgsjDays);
			list.add(datetimepicker + " 00:00:00");
			list.add(datetimepicker2 + " 23:59:59");
			jdbcDao.update(sql, list);

		} else {

			// 修改
			String sql = " update etl_task \n"
					+ "   set task_mc = ?, \n"
					+ "       ms= ?,smpl_dm= ?,"
					+ "       ddsj=?,"
					+ "       gz=?,xybj=?, "
					+ "       xgrq=sysdate, xgry_dm = ?, "
					+ "       xgjg_dm = ?, jgsj   =?,"
					+ "       jgsjDays =?,yxqq=to_date(?,'yyyy-MM-dd hh24:mi:ss'),yxqz=to_date(?,'yyyy-MM-dd hh24:mi:ss'), \n"
					+ "       folder_id = ? \n" + " where task_id = ?    ";

			list.add(mc);
			list.add(sm);
			list.add(jgpl);
			list.add(zxsj);
			list.add(gz);
			list.add(xybj);
			list.add(czrydm);
			list.add(dljgdm);
			list.add(jgsj);
			list.add(jgsjDays);
			list.add(datetimepicker + " 00:00:00");
			list.add(datetimepicker2 + " 23:59:59");
			list.add(folderid);
			list.add(dm);

			jdbcDao.update(sql, list);

		}

		JSONDATA.put("success", "1");
		JSONDATA.put("dm", dm);
		JSONDATA.put("message", "保存成功");

		// 判断是否 需要调用 -- createXtxx

		String sql = "select 1 from etl_task_job where task_id = '" + dm + "'";
		List rs = jdbcDao.queryforlist(sql);

		if (rs.size() > 0) {

			BaseOutParam out = new BaseOutParam();// 单值
			BaseOutParam out1 = new BaseOutParam();// 单值
			// 调用存储过程的入参
			List<Object> paralist = new ArrayList<Object>();
			paralist.add(dm);

			paralist.add(out);
			paralist.add(out1);
			SpManager.callProc(jdbcDao.getConnection(),
					"PKG_ETL.P_ETL_SCHEDULER_SAVEJOB", paralist);
			// String success = out.getValue().toString();
			String success = out.getValue().toString();
			String message = out1.getValue().toString();

			JSONDATA.put("success", success);
			JSONDATA.put("message", message);

		}

		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);
		return resEvent;

	}

	public ResponseEvent zygl(RequestEvent requestEvent) throws Exception {

		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resmap = new HashMap<String, Object>();

		String task_id = (String) requestEvent.getRequestMap().get("dm");

		ArrayList<HashMap<String, Object>> nodesList = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> lineList = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> nodeParam = new ArrayList<HashMap<String, Object>>();

		String logid = "";

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
 
			ArrayList<String> list = new ArrayList<String>();
			list.add(task_id);

			sql = " select start_etl_id , end_etl_id from etl_task_line where task_id = ? ";
			List rs = jdbcDao.queryforlist(sql, list);

			for(int i=0;i<rs.size();i++){
				map = new HashMap<String, Object>();
				data = (Map) rs.get(i);
				map.put("from", data.get("START_ETL_ID"));
				map.put("to", data.get("END_ETL_ID"));
				lineList.add(map);
			}
			
			String ztzx="";
			
			sql = "select decode(count(*), 0, 'N', 'Y') ztzx\n"
					+ "  from user_scheduler_running_jobs\n"
					+ " where upper(job_name) = upper('ETL_CHAIN__JOB_' || ?)";
			
		   SqlRowSet srs= jdbcDao.queryforRowset(sql,list);
		   if(srs.next()){
			   resmap.put("ztzx", srs.getString("ztzx"));
		   }
	 
		   sql = " select sche_log_id logid,is_executing ztzx from etl_sche_log where upper(is_last) = upper('y') and sche_id = ? ";
		 
		   srs= jdbcDao.queryforRowset(sql,list);
		   if(srs.next()){
			   logid = srs.getString("logid") ;
		   }
			 
			sql = "select distinct t.etl_id,\n"
					+ "       nvl(g.mc, t.etl_id) etlmc,\n"
					+ "       nvl(top,0) top,\n"
					+ "       nvl(left,0) left,\n"
					+ "       (case upper(f.is_executing) \n"
					+ "         when upper('N') then decode(upper(f.is_success), upper('Y'), '2', '3')  \n"
					+ "         when upper('Y') then '1' else decode(upper('" + ztzx+ "'),upper('Y'),'4','0') end) zt,   \n"
					+ "       (case upper(f.is_executing)  when upper('Y') then  decode(k.step_name, null, 1, 0) else 0 end) iserror, \n"
					+ "        g.sjjgfw  \n"
					+ "  from etl_task_nodes t,					\n"
					+ "       etl_def g,						\n"
					+ "       (select *							\n"
					+ "          from etl_job_log				\n"
					+ "         where sche_id = ? 				\n"
					+ "           and sche_log_id = ? 			\n"
					+ "           and job_log_step_id = '0') f, \n"
					+ "       user_scheduler_running_chains k\n"
					+ " where task_id = ? \n"
					+ "   and upper('CHAIN_STEP_' || t.etl_id || '_' || t.task_id) =\n"
					+ "       k.step_name(+)\n"
					+ "   and t.etl_id = g.etl_id(+)\n"
					+ "   and g.etl_id = f.etl_id(+)";

			list = new ArrayList<String>(); list.add(task_id);list.add(logid);list.add(task_id);
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
 				nodesList.add(map);
			}
			 
			sql = " select  etl_id, to_char(replace(wm_concat(params_dm || '-' || decode(params_dm,'sftg','N',params_value) ),',',';')) params from etl_task_params where task_id= ? group by etl_id ";
			list = new ArrayList<String>(); list.add(task_id);
			rs = jdbcDao.queryforlist(sql, list);
			for(int i=0;i<rs.size();i++){
				data = (Map) rs.get(i);
				map = new HashMap<String, Object>();			
				map.put("etlid", data.get("ETL_ID"));
				map.put("params", data.get("PARAMS"));
				nodeParam.add(map);
			}
			
			
		}

		resmap.put("nodeParam", JsonUtil.toJson(nodeParam));
		
		resmap.put("logid", logid);
		resmap.put("nodesList", JsonUtil.toJson(nodesList));
		resmap.put("lineList", JsonUtil.toJson(lineList));
		resmap.put("task_id", task_id);
		
		resEvent.setResMap(resmap) ;
		
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/jhglZygl.jsp");
		return resEvent;

	}
 

	public ResponseEvent searchZyList(RequestEvent requestEvent)throws Exception {

		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resmap = new HashMap<String, Object>();
		HashMap<String, Object> JSONDATA = new HashMap<String, Object>();

		String task_id = (String) requestEvent.getRequestMap().get("task_id");

		String sql = " select folder_id id, pfolder_id pid, mc name, 'folder' iconSkin \n"
				   + "  from sys_folder              	\n"
				   + " where folderlx_dm in('05','07')  \n"
				   + "   and xybj = 'Y'			     \n"
				   + " union all					 \n"
				   + " select t.etl_id id,		     \n"
				   + "       t.folder_id pid,	     \n"
				   + "       mc name,			     \n"
				   + "       decode(g.etl_id, null, 'etl', 'disabled') iconSkin					\n"
				   + "  from etl_def t,															\n"
				   + "       (select distinct etl_id from etl_task_nodes where task_id = ? ) g	\n"
				   + " where xybj = '1'															\n"
				   + "   and t.etl_id = g.etl_id(+)												\n";

		ArrayList<Object> param = new ArrayList<Object>();
		param.add(task_id);

		List list = jdbcDao.queryforlist(sql, param);
		Iterator crs = list.iterator();

		List<HashMap<String, Object>> jgsList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;
		while (crs.hasNext()) {
			Map data = (Map) crs.next();
			map = new HashMap<String, Object>();
			map.put("id", data.get("ID"));
			map.put("pid", data.get("PID"));
			map.put("name", data.get("NAME"));
			map.put("iconSkin", data.get("ICONSKIN"));
			jgsList.add(map);
		}

		JSONDATA.put("nodes", jgsList);

		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);

		return resEvent;

	}
	
	public ResponseEvent saveFlow(RequestEvent requestEvent)throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();

		Map requestMap =  requestEvent.getRequestMap();
		
		HashMap<String, Object> resmap = new HashMap<String, Object>();
		HashMap<String, Object> JSONDATA = new HashMap<String, Object>();

		String lines = (String) requestMap.get("lines");
		String nodes = (String) requestMap.get("nodes");
		String nodestr = (String)requestMap.get("nodestr");
		
		String taskId = (String) requestMap.get("taskid");
		
		ArrayList<String> param = new ArrayList();
		param.add(taskId);
		String testSql = " select * from user_scheduler_running_jobs where upper(job_name) = upper('ETL_CHAIN__JOB_'||?) ";

		List crs = jdbcDao.queryforlist (testSql,param);
        
		if(crs.size()>0){
			
			JSONDATA.put("success","0");
			JSONDATA.put("message", "当前计划正在执行 ，不能修改！");
			resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
			resEvent.setResMap(resmap);
			return resEvent;
			
        }
		
		String sql = "delete etl_task_nodes where task_id = ?";
		param = new ArrayList();
		param.add(taskId);

		jdbcDao.update(sql, param);

		sql = "insert into etl_task_nodes (task_id,etl_id,left,top) values (?,?,?,?)";

		for (int i = 0; i < nodes.split(";").length; i++) {

			param = new ArrayList();

			param.add(taskId);
			param.add(nodes.split(";")[i].split("-")[0]);
			param.add(nodes.split(";")[i].split("-")[1]);
			param.add(nodes.split(";")[i].split("-")[2]);
			
			jdbcDao.update(sql, param);
			
		}

		sql = "delete etl_task_line where task_id = ?";
		param = new ArrayList();
		param.add(taskId);

		jdbcDao.update(sql, param);

		sql = "insert into etl_task_line (task_id,start_etl_id,end_etl_id) values (?,?,?)";

		for (int i = 0; i < lines.split(";").length; i++) {

			param = new ArrayList();

			param.add(taskId);
			param.add(lines.split(";")[i].split("-")[0]);
			param.add(lines.split(";")[i].split("-")[1]);

			jdbcDao.update(sql, param);

		}
		
		
		if(nodestr!=null){
		
			JSONArray array = JSONArray.parseArray(nodestr);
			
			String etlid = "";
			String params = "" ;

			sql =" delete etl_task_params  where task_id = ? ";
			param = new ArrayList();
			param.add(taskId);
			jdbcDao.update(sql, param);
			
			sql = 
				"insert into etl_task_params\n" +
				"  (task_id, etl_id, params_dm, params_value)\n" + 
				"  select ? task_id,\n" + 
				"         ? etl_id,\n" + 
				"         substr(column_value, 1, instr(column_value, '-') - 1) params_dm, \n" + 
				"         substr(column_value, instr(column_value, '-') + 1) params_value  \n" + 
				"    from table(PKG_PUB_FUN.FUN_STR_SPLIT(?, ';')) ";
			
			
			
			for (int i = 0; i < array.size(); i++) {
				
				JSONObject jsonObject = array.getJSONObject(i);
				etlid = jsonObject.getString("etlid");
				params = jsonObject.getString("params");
				param = new ArrayList(); param.add(taskId);param.add(etlid);param.add(params);
				jdbcDao.update(sql, param);
				 
			}
			
			
		}
 		
		List<Object> paralist = new ArrayList<Object>();

		BaseOutParam out = new BaseOutParam();// 单值
		BaseOutParam out1 = new BaseOutParam();// 单值
		
		paralist.add(taskId);
		paralist.add(out);
		paralist.add(out1);
		
		HashMap<String, Object> map= new HashMap<String, Object>();
		try{
		    SpManager.callProc(jdbcDao.getConnection(),"PKG_ETL.P_ETL_SCHEDULER_SAVEJOB", paralist);
		}catch (Exception e) {
			
			JSONDATA.put("success","0");
			JSONDATA.put("message", "生成加工任务失败！");
			resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
			resEvent.setResMap(resmap);
			return resEvent;
			
		}
		
		JSONDATA.put("success",(String)out.getValue());
		JSONDATA.put("message",(String)out1.getValue());
		
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);

		return resEvent;
		
	}
	
	public ResponseEvent deleteTask(RequestEvent requestEvent)throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		
		Map reqMap = requestEvent.getRequestMap();
		
		String taskid = (String) reqMap.get("taskid");
		BaseOutParam out  = new BaseOutParam();		// 单值
		BaseOutParam out1 = new BaseOutParam();		// 单值
		
		ArrayList<Object> paralist = new ArrayList<Object>();
		paralist.add(taskid);
		paralist.add(out)  ;	
		paralist.add(out1) ;
		
		String success = "0" ; 
		String message = "删除失败，后台程序运行异常" ;
		
		try{
			
			SpManager.callProc(jdbcDao.getConnection(),"PKG_ETL.P_ETL_SCHEDULER_DORPTASK",paralist);
			success = (String) out.getValue();
			message = (String) out1.getValue();

		}catch (Exception e) {
			
 			 
		}
	   
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		JSONDATA.put("success", success);
		JSONDATA.put("message", message);									
		
		HashMap<String,Object> reqmap = new HashMap<String, Object>();		
		reqmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		
		resEvent.setResMap(reqmap);
		
		return resEvent;
		
	}



}
