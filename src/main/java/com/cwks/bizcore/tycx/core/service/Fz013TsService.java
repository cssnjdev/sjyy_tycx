//package com.cwks.bizcore.tycx.core.service;
//
//import com.cwks.bizcore.tycx.core.dao.Fz013TsDao;
//import com.cwks.common.api.dto.ext.RequestEvent;
//import com.cwks.common.api.dto.ext.ResponseEvent;
//import com.cwks.common.dao.JdbcDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.support.rowset.SqlRowSet;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.net.URLDecoder;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@Service("fz013TsService")
//public class Fz013TsService {
//
//	@Autowired
//	private JdbcDao jdbcDao;
//
//	@Autowired
//	private Fz013TsDao dao;
//
//
//
//	// 页面初始化
//	public ResponseEvent init(RequestEvent requestEvent) throws Exception {
//		LogWritter.sysDebug("debugger " + this.getClass().getName() + "_init");
//		ResponseEvent resEvent = new ResponseEvent();
//		HashMap reqmap = new HashMap();
//		String tzurl = (String) requestEvent.getRequestMap().get("tzurl");
//		if (null != tzurl) {
//			tzurl = URLDecoder.decode(tzurl, "UTF-8");
//		}
//		reqmap.put("url", tzurl);
//		reqmap.put("handleCode", "init");
//		resEvent.setResMap(reqmap);
//		resEvent.setFwordPath("/sjfz/core/fz013/jsp/fz013Ts.jsp");
//		return resEvent;
//	}
//
//	// 发起任务
//	public ResponseEvent RwFq(RequestEvent requestEvent) throws Exception {
//		LogWritter.sysDebug("debugger " + this.getClass().getName() + "_RwFq");
//		ResponseEvent resEvent = new ResponseEvent();
//		HashMap reqmap = new HashMap();
//		HashMap r_map = dao.RwFq(requestEvent);
//		reqmap.put("JSONDATA", JsonUtil.toJson(r_map));
//		resEvent.setResMap(reqmap);
//		return resEvent;
//	}
//
//	// 发起初始化
//	public ResponseEvent fqinit(RequestEvent requestEvent) throws Exception {
//		LogWritter
//				.sysDebug("debugger " + this.getClass().getName() + "_fqinit");
//		ResponseEvent resEvent = new ResponseEvent();
//		HashMap reqmap = dao.fqinit(requestEvent);
//		resEvent.setResMap(reqmap);
//		resEvent.setFwordPath("/sjfz/core/fz013/jsp/fz013Ts.jsp");
//		return resEvent;
//	}
//
//	// 发起保存
//	public ResponseEvent fqsave(RequestEvent requestEvent) throws Exception {
//		LogWritter
//				.sysDebug("debugger " + this.getClass().getName() + "_fqsave");
//		ResponseEvent resEvent = new ResponseEvent();
//		HashMap reqmap = new HashMap();
//		HashMap r_map = dao.fqsave(requestEvent);
//		reqmap.put("JSONDATA", JsonUtil.toJson(r_map));
//		resEvent.setResMap(reqmap);
//		return resEvent;
//	}
//
//	// 反馈初始化
//	public ResponseEvent fkinit(RequestEvent requestEvent) throws Exception {
//		LogWritter
//				.sysDebug("debugger " + this.getClass().getName() + "_fkinit");
//		ResponseEvent resEvent = new ResponseEvent();
//		HashMap reqmap = dao.fkinit(requestEvent);
//		resEvent.setResMap(reqmap);
//		resEvent.setFwordPath("/sjfz/core/fz013/jsp/fz013Ts.jsp");
//		return resEvent;
//	}
//
//	// 反馈保存
//	public ResponseEvent fksave(RequestEvent requestEvent) throws Exception {
//		LogWritter
//				.sysDebug("debugger " + this.getClass().getName() + "_fksave");
//		ResponseEvent resEvent = new ResponseEvent();
//		HashMap reqmap = new HashMap();
//		HashMap r_map = dao.fksave(requestEvent);
//		reqmap.put("JSONDATA", JsonUtil.toJson(r_map));
//		resEvent.setResMap(reqmap);
//		return resEvent;
//	}
//
//	// 反馈查看初始化
//	public ResponseEvent fkckinit(RequestEvent requestEvent) throws Exception {
//		LogWritter.sysDebug("debugger " + this.getClass().getName()
//				+ "_fkckinit");
//		ResponseEvent resEvent = new ResponseEvent();
//		HashMap reqmap = dao.fkckinit(requestEvent);
//		resEvent.setResMap(reqmap);
//		resEvent.setFwordPath("/sjfz/core/fz013/jsp/fz013Ts.jsp");
//		return resEvent;
//	}
//
//	// 反馈查看保存
//	public ResponseEvent fkcksave(RequestEvent requestEvent) throws Exception {
//		LogWritter.sysDebug("debugger " + this.getClass().getName()
//				+ "_fkcksave");
//		ResponseEvent resEvent = new ResponseEvent();
//		HashMap reqmap = new HashMap();
//		HashMap r_map = dao.fkcksave(requestEvent);
//		reqmap.put("JSONDATA", JsonUtil.toJson(r_map));
//		resEvent.setResMap(reqmap);
//		return resEvent;
//	}
//
//	// 获取发起人信息
//	public ResponseEvent getFqrXx(RequestEvent requestEvent) throws Exception {
//		LogWritter.sysDebug("debugger " + this.getClass().getName()
//				+ "_getFqrXx");
//		ResponseEvent resEvent = new ResponseEvent();
//		HashMap map = new HashMap();
//		String lcslh = (String) requestEvent.getRequestMap().get("lcslid");
//		// 查询发起人信息
//		String sql = this.getSql("SQL_SJFZ_FZ013_QUERYFQRXX");
//		ArrayList params = new ArrayList<String>();
//		params.add(lcslh);
//		SqlRowSet rs = jdbcDao.queryforRowset(sql, params);
//		rs.next();
//		String fqry_dm = rs.getString("tsrdm");
//		String fqjg_dm = rs.getString("tsrjg_dm");
//		// 推送人员
//		List<Map<String, Object>> tsryList = new ArrayList();
//		Map tsryMap = new HashMap<String, Object>();
//		tsryMap.put("dbry_dm", fqry_dm);
//		tsryMap.put("dbry_jgdm", fqjg_dm);
//		tsryMap.put("dbry_gwdm", "");
//		tsryList.add(tsryMap);
//		// 抄送人员
//		List<Map<String, Object>> csryList = new ArrayList();
//		// 拼装返回信息
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put("tsryList", tsryList);
//		resultMap.put("csryList", csryList);
//		resultMap.put("success", "1");
//		map.put("JSONDATA",
//				"cssnj_workflow_getNodeTsry_callBack("
//						+ JsonUtil.toJson(resultMap) + ")");
//		resEvent.setResMap(map);
//		return resEvent;
//	}
//}
