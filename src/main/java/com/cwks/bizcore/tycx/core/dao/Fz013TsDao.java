//package com.cwks.bizcore.tycx.core.dao;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//
//import com.cwks.common.api.dto.ext.RequestEvent;
//import com.cwks.common.api.dto.ext.UserContext;
//import com.cwks.common.dao.JdbcDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.support.rowset.SqlRowSet;
//import org.springframework.stereotype.Component;
//
//import javax.xml.namespace.QName;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@SuppressWarnings("all")
//public class Fz013TsDao {
//	// 任务发起
//	@Autowired
//	JdbcDao jdbcDao;
//	public HashMap RwFq(RequestEvent requestEvent) {
//		HashMap reqmap = new HashMap();
//		String[] lcslhArr = null;
//		String tmp_workItemId = "";
//		String tmp_lclsh = "";
//		// 取操作人信息
//		UserContext ctx = requestEvent.getCtx();
//		Map userInfo = ctx.getUserinfo();
//		// 取操作人身份代码
//		String swrysf_dm = (String) userInfo.get("swrysf_dm");
//		// 获取前台参数
//		String ms = (String) requestEvent.getRequestMap().get("ms");
//		String tsurl = (String) requestEvent.getRequestMap().get("url");
//		// 获取操作人代码、机关代码
//		String sql = jdbcDao.getSql("SQL_SJFZ_FZ013_QUERYJGBYSF");
//		ArrayList params = new ArrayList<String>();
//		params.add(swrysf_dm);
//		SqlRowSet rs = jdbcDao.queryforRowset(sql, params);
//		rs.next();
//		String swrydm = rs.getString("swry_dm");
//		String swjgdm = rs.getString("sfswjg_dm");
//		String rysfmc = rs.getString("rysfmc");
//		// 拼工作流业务名称
//		String bizmc = "推送（" + rysfmc + "）";
//		// 拼装工作流报文
//		String glz_qqbw = getQqbw(swrydm, swjgdm, bizmc);
//		// 查询工作流webservice地址
//		// 调用服务
//		String result = "";
//		URL url = null;
//		// 获取工作流webservice地址
//		String gzlWebServiceDz = SjfzHelper.getGzlWebServiceDz();
//		try {
//			URL baseUrl;
//			baseUrl = CssnjWebService_Service.class.getResource(".");
//			url = new URL(baseUrl, gzlWebServiceDz);
//			CssnjWebService_Service client = new CssnjWebService_Service(url,
//					new QName("http://www.cssnj.com.cn/soa_service/",
//							"cssnjWebService"));
//			CssnjWebService helloService = client.getCssnjHttpServicePort();
//			// 发送报文
//			result = helloService.invokeTask(glz_qqbw);
//			Map returnMap = new HashMap();
//			Map mapContent = new HashMap();
//			Map resultMap = new HashMap();
//			List resultList = new ArrayList();
//			Map map = XmlParseUtil.Dom2Map(result);
//			String content = (String) map.get("body");
//			mapContent = new HashMap();
//			if (!("".equals(content) || null == content)) {
//				mapContent = XmlParseUtil.Dom2Map(content);
//				String JsonData = (String) mapContent.get("JSONDATA");
//				resultList = JsonUtil.toListMap(JsonData);
//				// JSONArray abc=JSONObject.parseObject(JsonData);
//				lcslhArr = new String[resultList.size()];
//				// 获取流程实例号
//				for (int i = 0; i < resultList.size(); i++) {
//					resultMap = (HashMap) resultList.get(i);
//					lcslhArr[i] = (String) resultMap.get("lcslh");
//					tmp_lclsh = lcslhArr[0];
//					if (i == 0) {
//						tmp_workItemId = (String) resultMap.get("workItemId");
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			reqmap.put("reCode", "0");
//			reqmap.put("reMessage", "发起工作流失败：" + e.getStackTrace());
//			return reqmap;
//		}
//		// 获取业务主键
//		String uuid = "";
//		params = new ArrayList<String>();
//		sql = this.getSql("SQL_SJFZ_COMM_GETUUID");
//		Map tmp_map = this.queryformap(sql, params);
//		uuid = (String) tmp_map.get("uuid");
//		// 开始保存业务数据
//		ArrayList sqlList = new ArrayList();
//		sql = "insert into A_FZ_TS(UUID,TSRDM,TSRJG_DM,URL,MSXX,LCZT,LCSLH,LR_SJ) values('"
//				+ uuid
//				+ "','"
//				+ swrydm
//				+ "','"
//				+ swjgdm
//				+ "','"
//				+ tsurl
//				+ "','" + ms + "','0','" + tmp_lclsh + "',sysdate)";
//		sqlList.add(sql);
//		super.batchUpdate(sqlList);
//		reqmap.put("reCode", "1");
//		reqmap.put("reMessage", "成功");
//		reqmap.put("lcslh", tmp_lclsh);
//		reqmap.put("uuid", uuid);
//		reqmap.put("workItemId", tmp_workItemId);
//		return reqmap;
//	}
//
//	public HashMap fqinit(RequestEvent requestEvent) {
//		HashMap reqmap = new HashMap();
//		String lczt = "";
//		String tzurl = "";
//		String ms = "";
//		String fk = "";
//		// 请求参数
//		String lcslh = (String) requestEvent.getRequestMap().get("lcslh");
//		// 查询具体信息
//		String sql = this.getSql("SQL_SJFZ_FZ013_QUERYTSXX");
//		ArrayList<String> params = new ArrayList<String>();
//		params.add(lcslh);
//		SqlRowSet rs = this.queryforRowset(sql, params);
//		if (rs.next()) {
//			lczt = rs.getString("lczt");
//			tzurl = rs.getString("url");
//			ms = rs.getString("msxx");
//			fk = rs.getString("fkxx");
//		}
//		reqmap.put("lcslh", lcslh);
//		reqmap.put("lczt", lczt);
//		reqmap.put("url", tzurl);
//		reqmap.put("tj_msnr", ms);
//		reqmap.put("tj_fknr", fk);
//		reqmap.put("handleCode", "fqinit");
//		return reqmap;
//	}
//
//	public HashMap fqsave(RequestEvent requestEvent) {
//		HashMap reqmap = new HashMap();
//		ArrayList sqlList = new ArrayList();
//		String lcslh = (String) requestEvent.getRequestMap().get("lcslh");
//		String ms = (String) requestEvent.getRequestMap().get("ms");
//		String sql = "update A_FZ_TS a set a.lczt=1,a.msxx='" + ms
//				+ "',a.xg_sj=sysdate where a.lcslh = '" + lcslh + "'";
//		sqlList.add(sql);
//		super.batchUpdate(sqlList);
//		reqmap.put("reCode", "1");
//		reqmap.put("reMessage", "成功!");
//		return reqmap;
//	}
//
//	// 反馈初始化
//	public HashMap fkinit(RequestEvent requestEvent) {
//		HashMap reqmap = new HashMap();
//		String lczt = "";
//		String tzurl = "";
//		String ms = "";
//		String fk = "";
//		// 请求参数
//		String lcslh = (String) requestEvent.getRequestMap().get("lcslh");
//		// 查询具体信息
//		String sql = this.getSql("SQL_SJFZ_FZ013_QUERYTSXX");
//		ArrayList<String> params = new ArrayList<String>();
//		params.add(lcslh);
//		SqlRowSet rs = this.queryforRowset(sql, params);
//		if (rs.next()) {
//			lczt = rs.getString("lczt");
//			tzurl = rs.getString("url");
//			ms = rs.getString("msxx");
//			fk = rs.getString("fkxx");
//		}
//		reqmap.put("lcslh", lcslh);
//		reqmap.put("lczt", lczt);
//		reqmap.put("url", tzurl);
//		reqmap.put("tj_msnr", ms);
//		reqmap.put("tj_fknr", fk);
//		reqmap.put("handleCode", "fkinit");
//		return reqmap;
//	}
//
//	// 反馈保存
//	public HashMap fksave(RequestEvent requestEvent) {
//		HashMap reqmap = new HashMap();
//		ArrayList sqlList = new ArrayList();
//		String lcslh = (String) requestEvent.getRequestMap().get("lcslh");
//		String fk = (String) requestEvent.getRequestMap().get("fk");
//		String sql = "update A_FZ_TS a set a.lczt=2,a.fkxx='" + fk
//				+ "',a.xg_sj=sysdate where a.lcslh = '" + lcslh + "'";
//		sqlList.add(sql);
//		super.batchUpdate(sqlList);
//		reqmap.put("reCode", "1");
//		reqmap.put("reMessage", "成功!");
//		return reqmap;
//	}
//
//	// 反馈查看初始化
//	public HashMap fkckinit(RequestEvent requestEvent) {
//		HashMap reqmap = new HashMap();
//		String lczt = "";
//		String tzurl = "";
//		String ms = "";
//		String fk = "";
//		// 请求参数
//		String lcslh = (String) requestEvent.getRequestMap().get("lcslh");
//		// 查询具体信息
//		String sql = this.getSql("SQL_SJFZ_FZ013_QUERYTSXX");
//		ArrayList<String> params = new ArrayList<String>();
//		params.add(lcslh);
//		SqlRowSet rs = this.queryforRowset(sql, params);
//		if (rs.next()) {
//			lczt = rs.getString("lczt");
//			tzurl = rs.getString("url");
//			ms = rs.getString("msxx");
//			fk = rs.getString("fkxx");
//		}
//		reqmap.put("lcslh", lcslh);
//		reqmap.put("lczt", lczt);
//		reqmap.put("url", tzurl);
//		reqmap.put("tj_msnr", ms);
//		reqmap.put("tj_fknr", fk);
//		reqmap.put("handleCode", "fkckinit");
//		return reqmap;
//	}
//
//	// 反馈查看保存
//	public HashMap fkcksave(RequestEvent requestEvent) {
//		HashMap reqmap = new HashMap();
//		ArrayList sqlList = new ArrayList();
//		String lcslh = (String) requestEvent.getRequestMap().get("lcslh");
//		String sql = "update A_FZ_TS a set a.lczt=3,a.xg_sj=sysdate where a.lcslh = '"
//				+ lcslh + "'";
//		sqlList.add(sql);
//		super.batchUpdate(sqlList);
//		reqmap.put("reCode", "1");
//		reqmap.put("reMessage", "成功!");
//		return reqmap;
//	}
//
//	public String getQqbw(String swrydm, String swjgdm, String bizmc) {
//		String tmp_swrydm = "";
//		String tmp_swjgdm = "";
//		String tmp_gwdm = "";
//		String zrwlist = "";
//		JSONArray ryArr = null;
//		JSONArray tmpArr = null;
//		JSONObject tsryLb = null;
//		JSONObject ryvo = null;
//		// 生成交易号
//		ArrayList params = new ArrayList<String>();
//		String sql = this.getSql("SQL_SJFZ_COMM_GETUUID");
//		Map tmp_map = this.queryformap(sql, params);
//		String jyh = (String) tmp_map.get("uuid");
//		// 请求报文
//		StringBuffer sb = new StringBuffer();
//		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
//		sb.append("<reqws>");
//		sb.append("<tran_id>CSSNJ.WS.WORKFLOW</tran_id>");
//		sb.append("<channel_id>WfWebUtil_startMultiProcesses</channel_id>");
//		sb.append("<tran_seq>" + jyh + "</tran_seq>");
//		sb.append("<body>");
//		sb.append("<![CDATA[");
//		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//		sb.append("<processInstanceList>");
//		// 单任务
//		sb.append("<processInstance>");
//		sb.append("<bizId></bizId>");
//		sb.append("<bizMc>" + bizmc + "</bizMc>");
//		sb.append("<lcdyid>SJFZ_TS</lcdyid>");
//		sb.append("<qdlx>2</qdlx>");
//		sb.append("<lcjgdm>" + swjgdm + "</lcjgdm>");
//		sb.append("<qdgw></qdgw>");
//		sb.append("<qdjg>" + swjgdm + "</qdjg>");
//		sb.append("<qdry>" + swrydm + "</qdry>");
//		sb.append("<tsryList></tsryList>");
//		sb.append("</processInstance>");
//		sb.append("</processInstanceList>");
//		sb.append("]]>");
//		sb.append("</body>");
//		sb.append("</reqws>");
//		return sb.toString();
//	}
//}