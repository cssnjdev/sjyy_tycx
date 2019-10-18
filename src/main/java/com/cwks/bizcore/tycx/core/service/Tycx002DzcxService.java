package com.cwks.bizcore.tycx.core.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizcore.tycx.core.utils.tycxUtil;
import com.cwks.bizcore.utils.DataSourceUtil;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.core.cache.CacheUtil;
import com.cwks.common.service.impl.BaseServices;
import com.cwks.common.dao.JdbcDao;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.bizcore.tycx.chart.charts.component.*;
import com.cwks.bizcore.tycx.chart.charts.component.part.Radar;
import com.cwks.bizcore.tycx.chart.charts.component.part.RadarName;
import com.cwks.bizcore.tycx.chart.factory.ChartsFactory;
import com.cwks.bizcore.tycx.core.dao.Tycx001CxCxdyDao;
import com.cwks.bizcore.tycx.core.dao.Tycx001CxCxjgdyDao;
import com.cwks.bizcore.tycx.core.dao.Tycx001CxCxtjdyDao;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxdyPojo;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxjgdyPojo;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxtjdyPojo;
import com.cwks.bizcore.tycx.core.dao.Tycx002CxCxzxxxDao;
import com.cwks.bizcore.tycx.core.dao.Tycx002DzcxDao;
import com.cwks.bizcore.tycx.core.mapping.pojo.*;
import com.cwks.bizcore.tycx.core.vo.CXDzcxVO;
import com.cwks.bizcore.tycx.core.vo.ExtNodeVO;
import com.cwks.bizcore.tycx.core.vo.TreeVO;
import com.cwks.bizcore.tycx.core.dao.Tycx003CxTxpzxxDao;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx003CxTxpzxxPojo;
import com.cwks.bizcore.tycx.core.utils.Constant;
import com.cwks.bizcore.tycx.core.utils.FtpFileUtil;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.raqsoft.common.UUID;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.ListOrderedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

@Component
@Service("Tycx002DzcxService")
public class Tycx002DzcxService  extends BaseServices {

    private static Logger logger = LoggerFactory.getLogger(Tycx002DzcxService.class);
    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private Tycx002DzcxDao tycx002DzcxDao;
    @Autowired
    private Tycx001CxCxdyDao tycx001CxCxdyDao;
    @Autowired
    private Tycx001CxCxtjdyDao tycx001CxCxtjdyDao;
    @Autowired
    private Tycx002CxCxzxxxDao Tycx002CxCxzxxxDao;
    @Autowired
    private Tycx001CxCxjgdyDao Tycx001CxCxjgdyDao;
    @Autowired
    private Tycx003CxTxpzxxDao Tycx003CxTxpzxxDao;
    /**
     * 初始化页面
     */
    public ResponseEvent initView(RequestEvent requestEvent) throws Exception {

    	 logger.debug("debugger "+this.getClass().getName()+"_initView");
         ResponseEvent resEvent = new ResponseEvent();

         HashMap resmap = new HashMap();

        //测试登陆失效`
         resmap = testLogin(requestEvent.getCtx(),resmap);
         if(resmap.get("errorinfo")!=null){
        	 resEvent.setResMap(resmap);
        	 resEvent.setFwordPath("/public/js/common/jsp/error.html");
        	 return resEvent;
         }

         //获取sqlxh
        String sqlxh=(String) requestEvent.getRequestMap().get("sqlxh");
          //获取分析应用id
        String fxyyid=(String) requestEvent.getRequestMap().get("fxyyid");
          //获取djxh，主要用于一户式使用
        String djxh=(String) requestEvent.getRequestMap().get("djxh");
        String isExecute=(String) requestEvent.getRequestMap().get("execute");

  		Tycx001CxCxdyPojo Tycx001CxCxdyPojo=new Tycx001CxCxdyPojo();
        Tycx001CxCxdyPojo.setSqlxh(sqlxh);
        Tycx001CxCxdyPojo cxdyPojo = tycx001CxCxdyDao.selectByPKey(Tycx001CxCxdyPojo);
        String xsgnan=cxdyPojo.getXsgnan();
        if(!TycxUtils.isEmpty(xsgnan)){
        	String[] xsgnanArr = xsgnan.split(";");
        	resmap.put("xsgnanArr", xsgnanArr);
        }
        if(!TycxUtils.isEmpty(isExecute)){
        	resmap.put("execute", "true");
        }
		resmap.put("sqlxh", sqlxh);
		resmap.put("fxyyid", fxyyid);
		resmap.put("djxh", djxh);
		resmap.put("cxmc", cxdyPojo.getSqlmc());
		resEvent.setResMap(resmap);

		if(!TycxUtils.isEmpty(cxdyPojo.getShowpage())){
			resEvent.setFwordPath(cxdyPojo.getShowpage());
		}else{
			resEvent.setFwordPath("biz/bizcore/sjyy/tycx/tycx001/html/tycx_demo1.html");
		}
		return resEvent;
    }

	/**
     * 获取查询定义和查询条件信息
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent selectCxdyAndCxtj(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_select");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();

        Map userinfo=new HashMap();
        this.ctx = requestEvent.getCtx();
        if(ctx!=null){
        	userinfo = ctx.getUserinfo();
        }

        //获取sqlxh
		String xzcs = (String) requestEvent.getRequestMap().get("xzcs");
        String sqlxh=(String) requestEvent.getRequestMap().get("sqlxh");
        //获取查询定义信息
        Tycx001CxCxdyPojo Tycx001CxCxdyPojo=new Tycx001CxCxdyPojo();
        Tycx001CxCxdyPojo.setSqlxh(sqlxh);
        Tycx001CxCxdyPojo cxdy = tycx001CxCxdyDao.selectByPKey(Tycx001CxCxdyPojo);

        //获取查询条件信息
        Tycx001CxCxtjdyPojo Tycx001CxCxtjdyPojo=new Tycx001CxCxtjdyPojo();
        Tycx001CxCxtjdyPojo.setSqlxh(sqlxh);
		Tycx001CxCxtjdyPojo.setLmc(xzcs);
        List<Tycx001CxCxtjdyPojo> cxtjdyList = tycx001CxCxtjdyDao.select(Tycx001CxCxtjdyPojo);
        // 处理查询条件
     	List<Tycx001CxCxtjdyPojo> realCxtjList = tycx001CxCxtjdyDao.handleCxtj(cxtjdyList,userinfo);
        //查询按钮
		List<Map> bottons = (List<Map>)jdbcDao.queryforlist("select CSZ,CSM from cx_cs_csb where 1=1 and CSFL='csfl'");;

//        List<Map> bottons =TycxUtils.getCSByCsfl(Constant.BUTTON + "_"+"1", false);
        String[] buttonstr=new String[bottons.size()];
        for(int i=0;i<bottons.size();i++){
        	buttonstr[i]=(String)bottons.get(i).get("csz");
        }

        //参数注入
       // PageInfo<Tycx001CxCxdyPojo> cxdyPage = new PageInfo<Tycx001CxCxdyPojo>(cxdyList);
       // PageInfo<Tycx001CxCxtjdyPojo> cxtjPage = new PageInfo<Tycx001CxCxtjdyPojo>(realCxtjList);

        HashMap resultMap = new HashMap();
        resultMap.put("cxdy", cxdy);
        resultMap.put("swryDm", "00000000999");
        resultMap.put("cxtj", realCxtjList);
        resultMap.put("buttons", buttonstr);
        reqmap.put("JSONDATA", JsonUtil.toJson(resultMap));
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    /**
     * 执行查询
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent executeQuery(RequestEvent requestEvent) throws Exception {
    	ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		HashMap resultqmap = new HashMap();
		String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh"); //sqlxh
		String queryParams = (String) requestEvent.getRequestMap().get("queryParams");// 条件
		 //queryParams="[{'name':'DJXH','value':'10123213010000000215'}]";
		String page_str = (String) requestEvent.getRequestMap().get("page");
		String limit_str = (String) requestEvent.getRequestMap().get("rows");
		String total_str = (String) requestEvent.getRequestMap().get("total");
		String sjymc = (String) requestEvent.getRequestMap().get("sjymc");

		List<Tycx001CxCxjgdyPojo> cxjgList =Tycx001CxCxjgdyDao.selectBySqlxh(sqlxh);//查询结果列

		final String summary = (String) requestEvent.getRequestMap().get("summary");
		final String summaryParams = (String) requestEvent.getRequestMap().get("summaryParams");
		final String sortParams = (String) requestEvent.getRequestMap().get("sortParams");
		final String queryType = (String) requestEvent.getRequestMap().get("queryType");
		// 统计参数
		final String wrapParams = (String) requestEvent.getRequestMap().get("wrapParams");
		final boolean doTotal = Boolean.valueOf((String) requestEvent.getRequestMap().get("doTotal"));
		final String code2name = (String) requestEvent.getRequestMap().get("code2Name");

		int page = 0;
		int limit = 0;
		int total=0;
		if (TycxUtils.isEmpty(page_str)) {
			page = Constant.PAGE;
		} else {
			page = Integer.parseInt(page_str);
		}
		if (TycxUtils.isEmpty(limit_str)) {
			limit = Constant.LIMIT;
		} else {
			limit = Integer.parseInt(limit_str);
		}
		if(!TycxUtils.isEmpty(total_str)){
			total=Integer.parseInt(total_str);
		}
		//取权限
		 ctx = requestEvent.getCtx();
		 String swry_dm="";
		 String swrysfjg="";
		 Map userInfo=null;
     	 if(ctx!=null){
	  	    	userInfo =	ctx.getUserinfo();
	  	    	swry_dm =(String)userInfo.get("userId");
	  	    	swrysfjg =(String)userInfo.get("swrysf_dm");
	   	  }
		Boolean flag = valideCxtj(queryParams);
		if (flag) {

			// 获取查询定义信息
			Tycx001CxCxdyPojo tycx001CxCxdyPojo = new Tycx001CxCxdyPojo();
			tycx001CxCxdyPojo.setSqlxh(sqlxh);

			Tycx001CxCxdyPojo Tycx001CxCxdyPojo = tycx001CxCxdyDao.selectByPKey(tycx001CxCxdyPojo);

			String sqlstr = Tycx001CxCxdyPojo.getSqlstr();
			CXDzcxVO dzcxVo = new CXDzcxVO();
			dzcxVo.setUserMap(userInfo);
			dzcxVo.setSqlxh(sqlxh);
			dzcxVo.setPage(page);
			dzcxVo.setTotal(total);
			dzcxVo.setLimit(limit);
			dzcxVo.setCode2name(code2name);
			dzcxVo.setQueryParams(queryParams);
			dzcxVo.setSummary(summary);
			dzcxVo.setSummaryParams(summaryParams);
			dzcxVo.setWrapParams(wrapParams);
			dzcxVo.setQueryType(queryType);
			dzcxVo.setSql(sqlstr);
			dzcxVo.setSjymc(Tycx001CxCxdyPojo.getSjymc());
			dzcxVo.setDoTotal(doTotal);
			dzcxVo=LoopQueryParam(dzcxVo);
			dzcxVo=LoopWrapParams(dzcxVo);
			dzcxVo = createSql(dzcxVo,cxjgList);

			// 执行前，保存日志
			Tycx002CxCxzxxxPojo Tycx002CxCxzxxxPojo = new Tycx002CxCxzxxxPojo();
			String rzuuid = UUIDGenerator.getUUID();
			long sl = new Date().getTime();
			InetAddress ia=InetAddress.getLocalHost();
			String ip=ia.getHostAddress().toString();
			String address=ia.getHostName().toString();
			Tycx002CxCxzxxxPojo.setUuid(rzuuid);
			Tycx002CxCxzxxxPojo.setSqlxh(dzcxVo.getSqlxh());
			Tycx002CxCxzxxxPojo.setSqlstr(dzcxVo.getSql());
			Tycx002CxCxzxxxPojo.setCxsj(TycxUtils.getNow());
			Tycx002CxCxzxxxPojo.setCzry_dm(swry_dm);
			Tycx002CxCxzxxxPojo.setLrr_dm(swry_dm);
			Tycx002CxCxzxxxPojo.setLrrq(TycxUtils.getNow());
			Tycx002CxCxzxxxPojo.setCxy(queryType);//查询源
			Tycx002CxCxzxxxPojo.setSjgsdq(swrysfjg);
			//Tycx002CxCxzxxxPojo.setSessionid(sessionid);
			Tycx002CxCxzxxxPojo.setFwip(ip+Constant.rowFG+address);
			Tycx002CxCxzxxxPojo.setThreadid(Thread.currentThread().getName());
			Tycx002CxCxzxxxDao.insertSelective(Tycx002CxCxzxxxPojo);
			dzcxVo.setRzbUUID(rzuuid);
			String ccgc=Tycx001CxCxdyPojo.getCcgcmc();
            if(!TycxUtils.isEmpty(ccgc)){
            	dzcxVo.setCcgcmc(ccgc);
            	tycx002DzcxDao.executeProcedure(dzcxVo);
			}
			// 执行前，日志结束
			// 取 doTotal,为true的时候才去取count
			// 合计LIST
			List<Map<String, Object>> hjList = null;
			// 查询总条数
			String totalcount = "";
			if (dzcxVo.isDoTotal()) {
				hjList = tycx002DzcxDao.executeCount(dzcxVo);
				if (!TycxUtils.isEmpty(hjList)) {
					total_str = (String) hjList.get(0).get("TOTALCOUNT");
					dzcxVo.setCount(total_str);
				}
			} else {
				totalcount = String.valueOf(dzcxVo.getTotal());
				String summary_new = dzcxVo.getSummary();
				if (!TycxUtils.isEmptyString(summary_new)) {
					summary_new = "[" + summary_new + "]";
					Map<String, Object> sumMap = new HashMap<String, Object>();
					List<Map> sumArr = JsonUtil.toListMap(summary_new);
					for (int i = 0; i < sumArr.size(); i++) {
						Map<String,Object> tjMap = sumArr.get(i);
						for (Entry<String, Object> entry : tjMap.entrySet()) {
							final String nameStr = entry.getKey();
							final Object valueStr = entry.getValue();
							sumMap.put(nameStr, valueStr);
						}
					}
					reqmap.put("summary", sumMap);
				}
			}
			if (!TycxUtils.isEmpty(hjList)) { //判断是否为空
				reqmap.put("summary", hjList.get(0));
			}

			// 设置数据源
			List list = tycx002DzcxDao.executeSql(dzcxVo);
			//代码转名称
			// 代码转名称
			if (!TycxUtils.isEmpty(code2name) && !TycxUtils.isEmpty(list)) {
				list= dmTomc(list, dzcxVo);
			}
			// 执行时间,记录日志
			long el = new Date().getTime();
			String queryTime = Long.toString(el - sl);
			Tycx002CxCxzxxxPojo Tycx002CxCxzxxxPojo1 = new Tycx002CxCxzxxxPojo();
			Tycx002CxCxzxxxPojo1.setCxzxsj(Double.valueOf(queryTime));
			Tycx002CxCxzxxxPojo1.setUuid(rzuuid);
			if(!TycxUtils.isEmpty(total_str)){
			Tycx002CxCxzxxxPojo1.setFhjgs(Double.valueOf(total_str));
			}
			Tycx002CxCxzxxxDao.updateByPKeySelective(Tycx002CxCxzxxxPojo1);
			PageInfo<Tycx001CxCxdyPojo> cxjgPage = new PageInfo<Tycx001CxCxdyPojo>(list);
			reqmap.put("data", list);
			reqmap.put("total", total_str);
			reqmap.put("count",total_str);
			reqmap.put("iTotalDisplayRecords",total_str);
			reqmap.put("iTotalRecords",total_str);
//			JsonUtil.setFormatYYYSS();
			resultqmap.put("JSONDATA", JsonUtil.toJson(reqmap));
			resEvent.setResMap(resultqmap);
		}
		return resEvent;
    }


	public static void writeMsgToExt(HttpServletResponse response,
			String infoStr) {
		response.setContentType("text/xml; charset=UTF-8");
		//ThreadLocalManager.add("download", true);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(infoStr);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}
    /**
     * 执行交叉表查询
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent executeJcbQuery(RequestEvent requestEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		//SQLXH
		String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");// sqlxh
		//查询条件
		String queryParams = (String) requestEvent.getRequestMap().get("queryParams");// 条件
		Boolean flag = valideCxtj(queryParams);
		if (flag) {
			// 获取查询定义信息
			Tycx001CxCxdyPojo Tycx001CxCxdyPojo = new Tycx001CxCxdyPojo();
			Tycx001CxCxdyPojo.setSqlxh(sqlxh);
			Tycx001CxCxdyPojo = tycx001CxCxdyDao.selectByPKey(Tycx001CxCxdyPojo);
			String sqlstr = Tycx001CxCxdyPojo.getSqlstr();
			//查询结果列
			Tycx001CxCxjgdyPojo Tycx001CxCxjgdyPojo=new Tycx001CxCxjgdyPojo();
			Tycx001CxCxjgdyPojo.setSqlxh(sqlxh);
			List<Tycx001CxCxjgdyPojo> cxjgList =Tycx001CxCxjgdyDao.select(Tycx001CxCxjgdyPojo);
			CXDzcxVO dzcxVo = new CXDzcxVO();
			dzcxVo.setSql(sqlstr);
			dzcxVo.setCxjgList(cxjgList); //查询结果列
			dzcxVo.setQueryParams(queryParams);
			dzcxVo.setSjymc(Tycx001CxCxdyPojo.getSjymc());
			cxjgList=queryCxjgdyListFilterByFzyj(dzcxVo);
			dzcxVo=this.LoopQueryParam(dzcxVo);

			List<Map<String,Object>> newcxjgList=new ArrayList<Map<String,Object>>();
			for(int i=0;i<cxjgList.size();i++){
				newcxjgList.add(TycxUtils.convert2Map(cxjgList.get(i)));
			}
			dzcxVo = createSql(dzcxVo, cxjgList);
			//查询数据
			List dataList = tycx002DzcxDao.executeSql(dzcxVo);
			// 展示的结果列
			final Map<String, Object> resultMap = this.getJcbJgl(newcxjgList, dataList,
					dzcxVo);
			final List<Map<String, Object>> resultColumn = (List<Map<String, Object>>) resultMap
			.get("resultList");
	        dzcxVo = (CXDzcxVO) resultMap.get("dzcxVo");
	       // 展示的数据集
	         final Map<String, Object> resultData = this.getJcbData(dataList, dzcxVo);
	         resultData.put("resultColumns", resultColumn);
	         reqmap.put("JSONDATA", JsonUtil.toJson(resultData));
		}
		resEvent.setResMap(reqmap);
		return resEvent;
    }
    /**
	 *
	 * @name 交叉表结果列数据
	 * @description 相关说明
	 * @time 创建时间:2015-5-20上午9:30:31
	 * @param dataList
	 *            dataList
	 * @param dzcxVo
	 *            dzcxVo
	 * @return
	 * @author jinln
	 * @throws SwordBaseCheckedException
	 * @throws JSONException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public Map<String, Object> getJcbData(List<Map<String, Object>> dataList,
			CXDzcxVO dzcxVo) throws Exception {
		final List<Map> resultDataList = new ArrayList<Map>();
		String totalcount = "";// 总条数
		final Map<String, Object> tempFootMap = new HashMap<String, Object>();
		String wd = dzcxVo.getWd();
		final String zsfs = dzcxVo.getJcbZxfs();
		wd = wd + ",";
		String colMc = "";
		String jcbhxls = "";
		if (!TycxUtils.isEmpty(wd)) {
			jcbhxls = wd.substring(0, wd.length() - 1);
			final String[] jcbArray = wd.split(",");
			for (int i = 0; i < jcbArray.length; i++) {
				colMc = colMc + "@" + jcbArray[i] + "@";
			}
		}
		List<Map<String, Object>> tjresultlist = null;
		List<Map<String, Object>> tjzxresultlist = null;
		if (!TycxUtils.isEmpty(zsfs) && zsfs.indexOf("0") >= 0) {// 横向展示
			final String tjsqlstr = "select " + jcbhxls + ", sum("
					+ dzcxVo.getJcbTjzd() + ") as hj from (" + dzcxVo.getSql()
					+ ") group by " + jcbhxls;
			tjresultlist = tycx002DzcxDao.executeSql(tjsqlstr, dzcxVo.getSjymc());
		}
		if (!TycxUtils.isEmpty(zsfs) && zsfs.indexOf("1") >= 0) {// 表示纵向\
			final String jcbhxkzl = dzcxVo.getJcbHxkzl();
			String jcbzxSql = "";
			if ("2".equals(dzcxVo.getJcbTjlx())) {// 求和
				jcbzxSql = "select " + jcbhxkzl + ", to_char(sum("
						+ dzcxVo.getJcbTjzd() + ")) as totalcount from ("
						+ dzcxVo.getSql() + ") group by " + jcbhxkzl;
			} else if ("3".equals(dzcxVo.getJcbTjlx())) {// 平均
				jcbzxSql = "select " + jcbhxkzl + ", avg("
						+ dzcxVo.getJcbTjzd() + ") as totalcount from ("
						+ dzcxVo.getSql() + ") group by " + jcbhxkzl;
			}
			if (!TycxUtils.isEmpty(jcbzxSql)) {
				tjzxresultlist =  tycx002DzcxDao.executeSql(jcbzxSql, dzcxVo.getSjymc());
			}
		}
		final String sqlTotStr = "select "
				+ " to_char(count(1))  TOTALCOUNT from (  select " + jcbhxls
				+ " from (" + dzcxVo.getSql() + ")  group by " + jcbhxls + ")";

		final List<Map<String, Object>> tempTotList =tycx002DzcxDao.executeSql(sqlTotStr, dzcxVo.getSjymc());

		for (int i = 0; i < tempTotList.size(); i++) {
			totalcount = (String) tempTotList.get(i).get("totalcount");
		}
		String colmc = "";
		String colValue = "";
		final Map<String, Object> extendMap = new HashMap<String, Object>();
		for (int j = 0; j < dataList.size(); j++) {// 第一步数据加工，封装成key：value
			final Map<String, Object> resmap = dataList.get(j);
			String jcbKey = "";
			String jcbVal = "0";
			colmc = colMc;
			for (Entry<String, Object> entry : resmap.entrySet()) {
				final String key = entry.getKey();
				String value = "";
				if (wd.indexOf(key + ",") >= 0) {
					value = (String) entry.getValue();
					colmc = colmc.replace("@" + key + "@", key + ":" + value
							+ ";");
				} else if (key.equals(dzcxVo.getJcbHxkzl())) {
					value = (String) entry.getValue();
					jcbKey = value;
				} else if (key.equals(dzcxVo.getJcbTjzd())) {
					if (entry.getValue() != null) {
						final Double values = Double.parseDouble(String
								.valueOf((entry.getValue())));
						// final int values=Integer.parseInt((String)
						// entry.getValue());
						jcbVal = String.valueOf(values);
					}
				}
			}
			colValue = jcbKey + Constant.rowFG + jcbVal + ";";
			if (extendMap.containsKey(colmc)) {
				String endColVal = (String) extendMap.get(colmc);
				endColVal = endColVal + colValue;
				extendMap.put(colmc, endColVal);
			} else {
				extendMap.put(colmc, colValue);
			}
		}
		// 如果tjresultlist不为null，则把数据封装到extendMap
		if (!TycxUtils.isEmpty(tjresultlist)) { //判断是否为空
			for (int i = 0; i < tjresultlist.size(); i++) {
				final Map<String, Object> resmap = tjresultlist.get(i);
				colmc = colMc;
				for (Entry<String, Object> entry : resmap.entrySet()) {
					final String key = entry.getKey();
					String value = "";
					if (wd.indexOf(key + ",") >= 0) {
						value = (String) entry.getValue();
						colmc = colmc.replace("@" + key + "@", key + ":"
								+ value + ";");
					} else {
						final Double values = Double.parseDouble(String
								.valueOf((entry.getValue())));
						// final int values=Integer.parseInt((String)
						// entry.getValue());
						colValue = key + Constant.rowFG
								+ String.valueOf(values) + ";";
					}
				}
				if (extendMap.containsKey(colmc)) {
					String endColVal = (String) extendMap.get(colmc);
					endColVal = endColVal + colValue;
					extendMap.put(colmc, endColVal);
				} else {
					extendMap.put(colmc, colValue);
				}
			}
		}
		// 第二步，数据最终处理成key-value格式，放入list
		for (Entry<String, Object> entry : extendMap.entrySet()) {
			final Map<String, Object> maps = new HashMap<String, Object>();
			final String key = entry.getKey();
			final String value = (String) entry.getValue();
			String[] rowLableArray = key.split(";");
			for (int i = 0; i < rowLableArray.length; i++) {
				final String colmcs = rowLableArray[i];
				final String[] sjArray = colmcs.split(Constant.rowFG);
				maps.put(sjArray[0], sjArray[1]);
			}
			final String[] sjLableArray = value.split(";");
			for (int i = 0; i < sjLableArray.length; i++) {
				final String colmcs = sjLableArray[i];
				final String[] sjArray = colmcs.split(Constant.rowFG);
				maps.put(sjArray[0], sjArray[1]);
			}
			resultDataList.add(maps);
		}

		if (!TycxUtils.isEmpty(tjzxresultlist)) { //判断是否为空
			// Double hj = 0.0;
			int hj = 0;
			for (int i = 0; i < tjzxresultlist.size(); i++) {
				tempFootMap.put((String) tjzxresultlist.get(i).get(
						dzcxVo.getJcbHxkzl()), tjzxresultlist.get(i).get(
						"TOTALCOUNT"));

				hj += Double.valueOf(String.valueOf(tjzxresultlist.get(i).get(
						"TOTALCOUNT")));
				// hj+=Integer.parseInt(String.valueOf(tjzxresultlist.get(i)
				// .get("TOTALCOUNT")));
			}
			if ("2".equals(dzcxVo.getJcbTjlx())) {
				tempFootMap.put("HJ", hj);
			}
			if ("3".equals(dzcxVo.getJcbTjlx())) {
				tempFootMap.put("HJ", hj / tjzxresultlist.size());
			}
		}

		final Map<String, Object> resultMap = new HashMap<String, Object>();
		//resultDataList.add(tempFootMap);
		resultMap.put("rows", JsonUtil.toJson(resultDataList));
		if (!TycxUtils.isEmpty(dzcxVo.getCode2name())
				&& !TycxUtils.isEmpty(resultMap)) {
			resultMap.put("rows",this.dmTomc(resultDataList, dzcxVo));
		}
		resultMap.put("summary", tempFootMap);
		resultMap.put("total", totalcount);
		return resultMap;
	}

    /**
	 *
	 * @name 得到交叉表结果列
	 * @description 相关说明
	 * @time 创建时间:2015-6-3下午3:08:46
	 * @param cxjgl
	 *            查询结果列
	 * @param dataList
	 *            数据集
	 * @param dzcxVo
	 *            dzcxVo
	 * @return
	 * @throws SwordBaseCheckedException
	 * @author 作者jinln
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
    public Map<String, Object> getJcbJgl(List<Map<String, Object>> cxjgl,
			List<Map<String, Object>> dataList, CXDzcxVO dzcxVo)
			throws Exception {
		// 结果列List
		final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		// 横向合计行
		Map<String, Object> hjMap = new HashMap<String, Object>();
		Map<String, Object> resmap = new HashMap<String, Object>();
		// 统计类型，2是求和，3是平均
		String tjlx = "";
		// 横向扩展字段的显示格式
		String xsgs = "";
		List<Map<String, Object>> code2nameList = new ArrayList<Map<String, Object>>();

		// 维度列
		String wd = dzcxVo.getWd();
		// 循环结果列，得到横向扩展列
		for (int i = 0; i < cxjgl.size(); i++) {
			final Map<String, Object> resultmap = (HashMap<String, Object>) cxjgl
					.get(i);
			final String llx = (String) resultmap.get("llx");
			if ("NUMBER".equals(llx)) {
				final String zsfs = (String) resultmap.get("zsfs");// 横向展示，则增加横向结果列展示
				tjlx = (String) resultmap.get("tjlx");// 2是求和，3是平均
				xsgs = (String) resultmap.get("xsgs");
				if (!TycxUtils.isEmpty(zsfs) && zsfs.contains("0")) {// 横向展示合计列
					hjMap.put("zsfs", (String) resultmap.get("zsfs"));
					hjMap.put("xsxh", String.valueOf(resmap.get("xsxh")));
					hjMap.put("dqfs", (String) resultmap.get("dqfs"));
					hjMap.put("lkd", (String) resultmap.get("lkd"));
					hjMap.put("xsgs", (String) resultmap.get("xsgs"));
					hjMap.put("llx", "NUMBER");
					hjMap.put("lmc", "HJ");// 替换resmap中的dataIndex
					hjMap.put("lms", "合计");// 替换resmap中的text
				}
			}
			final String jcbzdlx = (String) resultmap.get("jcbzdlx");
			if (!TycxUtils.isEmpty(jcbzdlx)) {
				if (jcbzdlx.equals(Constant.JCB_HXKZZD)) {// 1表示横向扩展列
					resmap = resultmap;
					dzcxVo.setJcbHxkzl((String) resmap.get("lmc"));

				} else if (jcbzdlx.equals(Constant.JCB_PTHXZD)) {// 0表示横向扩展列的分组列
					if (!TycxUtils.isEmpty(wd)) {
						wd = wd + ",";
					}
					if (wd.contains((String) resultmap.get("lmc") + ",")) {
						if (!TycxUtils.isEmpty(resultmap.get("dmsql"))) {
							// code2name
							Map<String, Object> code2name = new HashMap<String, Object>();
							code2name
									.put("name", (String) resultmap.get("lmc"));
							code2name.put("table", (String) resultmap
									.get("dmsql"));
							code2nameList.add(code2name);
						}
						resultList.add(resultmap);
					}
				}
			} else {
				if ("2".equals((String) resultmap.get("mbbz"))) {
					resultList.add(resultmap);
				} else {
					dzcxVo.setJcbTjzd((String) resultmap.get("lmc"));
					dzcxVo.setJcbTjlx((String) resultmap.get("tjlx"));
					dzcxVo.setJcbZxfs((String) resultmap.get("zsfs"));
				}
			}

		}
		if (TycxUtils.isEmpty(resmap)) {
			return null;
		}

		// 结果列扩展类型
		final String kzlx = (String) resmap.get("kzlx");
		final String dmsql = (String) resmap.get("dmsql");
		// 代码表
		String dmb = "";
		if (!TycxUtils.isEmpty(dmsql)) {
			final List tempDmList =  CacheUtil.getCodeTable("T_XT_HCBXX","bm_mc='"+dmsql+"'");// 得到代码列和名称列
			Map<String,Object> hcbmap=(Map<String, Object>) tempDmList.get(0);
			dmb = (String)hcbmap.get("columnId");
		}
		if (Constant.KZLX_JKZYSJ.equals(kzlx)) {
			final String zdmc = (String) resmap.get("lmc");
			String lmc_order = "";
			for (int m = 0; m < dataList.size(); m++) {
				String lmc = (String) dataList.get(m).get(zdmc);
				if (lmc_order.indexOf(lmc + ",") < 0) {
					Map<String, Object> sjMap = new HashMap<String, Object>();
					sjMap.put("zsfs", (String) resmap.get("zsfs"));
					sjMap.put("xsxh", String.valueOf(resmap.get("xsxh")));
					sjMap.put("dqfs", (String) resmap.get("dqfs"));
					sjMap.put("lkd", (String) resmap.get("lkd"));
					sjMap.put("sjlmc", (String) resmap.get("sjlmc"));
					sjMap.put("url", (String) resmap.get("url"));
					sjMap.put("xzcs", (String) resmap.get("xzcs"));
					sjMap.put("llx", "NUMBER");
					sjMap.put("tjlx", tjlx);
					sjMap.put("xsgs", xsgs);
					sjMap.put("lmc", (String) dataList.get(m).get(zdmc));// 替换resmap中的dataIndex
					if (!TycxUtils.isEmpty(dmsql)) {// 如有代码表，则代码转名称
						final String tempStr =  CacheUtil.getMcByDm(dmsql, "'"+(String) dataList.get(m).get(zdmc)+"'",dmb);
						sjMap.put("lms", tempStr);// 替换resmap中的text
					} else {
						sjMap.put("lms", (String) dataList.get(m).get(zdmc));// 替换resmap中的text
					}
					resultList.add(sjMap);
				}
				lmc_order = lmc_order + lmc + ",";
			}

		} else if (Constant.KZLX_QBKZ.equals(kzlx)) {
			if (!TycxUtils.isEmpty(dmsql)) {

				final List tempDmList =  CacheUtil.getCodeTable("T_XT_HCBXX","bm_mc='"+dmsql+"'");// 得到代码列和名称列
				final Map<String, Object> tempMap = (Map<String, Object>) tempDmList.get(0);
				String dm=(String) tempMap.get("columnValue");
				String mc=(String) tempMap.get("columnId");
				final String qbkzsql = "SELECT " + dm
						+ " AS DM," + mc
						+ " AS MC FROM " + dmsql;
				final List<Map<String, Object>> qbkzList = jdbcDao.queryforlist(qbkzsql);
				for (int i = 0; i < qbkzList.size(); i++) {
					final Map<String, Object> qbkzmap = qbkzList.get(i);
					Map<String, Object> sjMap = new HashMap<String, Object>();
					sjMap.put("zsfs", (String) resmap.get("zsfs"));
					sjMap.put("xsxh", (String) resmap.get("xsxh"));
					sjMap.put("dqfs", (String) resmap.get("dqfs"));
					sjMap.put("lkd", (String) resmap.get("lkd"));
					sjMap.put("sjlmc", (String) resmap.get("sjlmc"));
					sjMap.put("url", (String) resmap.get("url"));
					sjMap.put("xzcs", (String) resmap.get("xzcs"));
					sjMap.put("llx", "NUMBER");
					sjMap.put("tjlx", tjlx);
					sjMap.put("xsgs", xsgs);
					sjMap.put("lmc", (String) qbkzmap.get("DM"));// 替换resmap中的dataIndex
					sjMap.put("lms", (String) qbkzmap.get("MC"));// 替换resmap中的text
					resultList.add(sjMap);
				}
			}
		} else {
			List<Map<String, Object>> zdyList = new ArrayList<Map<String, Object>>();
			if (kzlx.equals(Constant.KZLX_ZDYSQL)) {
				final String zdysql = (String) resmap.get("zdysql");
				if (!TycxUtils.isEmpty(zdysql)) {
					zdyList =  tycx002DzcxDao.executeSql(zdysql, dzcxVo.getSjymc());

				}
			} else if (kzlx.equals(Constant.KZLX_ZDYKZ)) {
//				zdyList = jcbzdykzlBO.queryJcbzdykzlInfoMapListBySqlxh(dzcxVo
//						.getSqlxh());

			}
			for (int i = 0; i < zdyList.size(); i++) {
				final Map<String, Object> zdymap = zdyList.get(i);
				Map<String, Object> sjMap = new HashMap<String, Object>();
				sjMap.put("zsfs", (String) resmap.get("zsfs"));
				sjMap.put("xsxh", (String) resmap.get("xsxh"));
				sjMap.put("dqfs", (String) resmap.get("dqfs"));
				sjMap.put("lkd", (String) resmap.get("lkd"));
				sjMap.put("sjlmc", (String) resmap.get("sjlmc"));
				sjMap.put("url", (String) resmap.get("url"));
				sjMap.put("llx", "NUMBER");
				sjMap.put("tjlx", tjlx);
				sjMap.put("xsgs", xsgs);
				sjMap.put("lmc", zdymap.get("dm"));// 替换resmap中的dataIndex
				sjMap.put("lms", zdymap.get("mc"));// 替换resmap中的text

				resultList.add(sjMap);
			}
		}
		if (!TycxUtils.isEmpty(hjMap) && hjMap.size() != 0) {
			resultList.add(hjMap);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (!TycxUtils.isEmpty(code2nameList)) {
			dzcxVo.setCode2name(JsonUtil.toJson(code2nameList));
		}
		resultMap.put("resultList", resultList);
		resultMap.put("dzcxVo", dzcxVo);
		return resultMap;
	}

    /**
     * 导出文件
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent exportFile(RequestEvent requestEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		final String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");
		// 条件
		String queryParamss = (String) requestEvent.getRequestMap().get(
				"queryParams");
		final String queryParams = URLDecoder.decode(queryParamss,"UTF-8");
		final String summary = (String) requestEvent.getRequestMap().get(
				"summary");
		final String summaryParams = (String) requestEvent.getRequestMap().get(
				"summaryParams");
		final String queryType = (String) requestEvent.getRequestMap().get(
				"queryType");
		final String code2name = (String) requestEvent.getRequestMap().get(
				"code2Name");
		final String wrapParams = (String) requestEvent.getRequestMap().get("wrapParams");
		// 根据SQLXH查询查询定义信息
		// 获取查询定义信息
		Tycx001CxCxdyPojo Tycx001CxCxdyPojo = new Tycx001CxCxdyPojo();
		Tycx001CxCxdyPojo.setSqlxh(sqlxh);
		Tycx001CxCxdyPojo = tycx001CxCxdyDao.selectByPKey(Tycx001CxCxdyPojo);
		String sqlstr = Tycx001CxCxdyPojo.getSqlstr();
		CXDzcxVO dzcxVo = new CXDzcxVO();
		dzcxVo.setSqlxh(sqlxh);
		dzcxVo.setCode2name(code2name);
		dzcxVo.setQueryParams(queryParams);
		dzcxVo.setSummary(summary);
		dzcxVo.setSummaryParams(summaryParams);
		dzcxVo.setQueryType(queryType);
		dzcxVo.setWrapParams(wrapParams);
		dzcxVo.setSql(sqlstr);
		dzcxVo.setSjymc(Tycx001CxCxdyPojo.getSjymc());
		dzcxVo.setSqlmc(Tycx001CxCxdyPojo.getSqlmc());
		dzcxVo = LoopQueryParam(dzcxVo);
		dzcxVo=LoopWrapParams(dzcxVo);
		dzcxVo = createSql(dzcxVo, null);
		// 执行前，保存日志
		Tycx002CxCxzxxxPojo Tycx002CxCxzxxxPojo = new Tycx002CxCxzxxxPojo();
		String rzuuid = UUIDGenerator.getUUID();
		long sl = new Date().getTime();
		InetAddress ia=InetAddress.getLocalHost();
		String ip=ia.getHostAddress().toString();
		String address=ia.getHostName().toString();
		Tycx002CxCxzxxxPojo.setUuid(rzuuid);
		Tycx002CxCxzxxxPojo.setSqlxh(dzcxVo.getSqlxh());
		Tycx002CxCxzxxxPojo.setSqlstr(dzcxVo.getSql());
		Tycx002CxCxzxxxPojo.setCxsj(TycxUtils.getNow());
		Tycx002CxCxzxxxPojo.setCzry_dm("00000000999");
		Tycx002CxCxzxxxPojo.setLrr_dm("00000000999");
		Tycx002CxCxzxxxPojo.setLrrq(TycxUtils.getNow());
		Tycx002CxCxzxxxPojo.setSjgsdq("0000");
		Tycx002CxCxzxxxPojo.setCxy(Constant.queryType_EXPORT);//查询源
		//Tycx002CxCxzxxxPojo.setSessionid(sessionid);
		Tycx002CxCxzxxxPojo.setFwip(ip+Constant.rowFG+address);
		Tycx002CxCxzxxxPojo.setThreadid(Thread.currentThread().getName());
		Tycx002CxCxzxxxDao.insertSelective(Tycx002CxCxzxxxPojo);
		dzcxVo.setRzbUUID(rzuuid);
		// 执行前，日志结束
		// 取 doTotal,为true的时候才去取count
		// 合计LIST
		List<Map<String, Object>> hjList = tycx002DzcxDao.executeCount(dzcxVo);
		Map colMap = new LinkedHashMap();
		if(Constant.queryType_TJGN.equals(queryType)){//如果是统计的导出，结果列根据wrapParams计算即可
			List wrapParamList= JsonUtil.toListMap(wrapParams);
			for (int i = 0; i < wrapParamList.size(); i++) {
				final Map<String, Object> param =(Map<String, Object>) wrapParamList.get(i);
				final String name = (String) param.get("name");
				final String text = (String) param.get("text");
				colMap.put(name, text);
			}
		}else{//如果是非统计的导出
				Tycx001CxCxjgdyPojo Tycx001CxCxjgdyPojo = new Tycx001CxCxjgdyPojo();
				Tycx001CxCxjgdyPojo.setSqlxh(sqlxh);
				List<Tycx001CxCxjgdyPojo> cxjgList = Tycx001CxCxjgdyDao
						.select(Tycx001CxCxjgdyPojo);

				dzcxVo.setCxjgList(cxjgList);
				cxjgList = queryCxjgdyListFilterByFzyj(dzcxVo);

				for (int m = 0; m < cxjgList.size(); m++) {
					Tycx001CxCxjgdyPojo cxjgPojo = cxjgList.get(m);
					colMap.put(cxjgPojo.getLmc(), cxjgPojo.getLms());
				}

		}
		// 设置数据源
		List datalist = tycx002DzcxDao.executeSql(dzcxVo);
		// 代码转名称
		if (!TycxUtils.isEmpty(code2name) && !TycxUtils.isEmpty(datalist)) {
			datalist = dmTomc(datalist, dzcxVo);
		}
		// 执行时间,记录日志
		long el = new Date().getTime();
		String queryTime = Long.toString(el - sl);
		Tycx002CxCxzxxxPojo Tycx002CxCxzxxxPojo1 = new Tycx002CxCxzxxxPojo();
		Tycx002CxCxzxxxPojo1.setCxzxsj(Double.valueOf(queryTime));
		Tycx002CxCxzxxxPojo1.setFhjgs(Double.valueOf(String.valueOf(datalist.size())));
		Tycx002CxCxzxxxPojo1.setUuid(rzuuid);
		Tycx002CxCxzxxxDao.updateByPKeySelective(Tycx002CxCxzxxxPojo1);
		// Excel导出格式
		int pageCount = 1;
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("fileName", dzcxVo.getSqlmc());
		// dataMap.put("class",Tycx002DzcxPojo.class);
		dataMap.put("colMap", colMap);
		dataMap.put("listContent", datalist);
		reqmap.put("ExcelMap", dataMap);
		resEvent.setResMap(reqmap);
		return resEvent;
    }
    /**
     * 查询生成echarts图
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent executeQueryCharts(RequestEvent requestEvent) throws Exception {
    	ResponseEvent resEvent = new ResponseEvent();
		 String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");
		final String txuuid = (String) requestEvent.getRequestMap().get("txuuid");// sqlxh
		// 条件
		final String queryParams = (String) requestEvent.getRequestMap().get("queryParams");
		final String sjymc = (String) requestEvent.getRequestMap().get("sjymc");
//		// 根据SQLXH查询查询定义信息
//		if(TycxUtils.isEmpty(sqlxh)){
//			String sql=jdbcDao.getSql("SQL_CX_QUERYSQLXH");
//			Map<String,Object> txpzxxMap=tycx001CxCxdyDao.queryJbxx(txuuid, sql);
//			sqlxh=(String) txpzxxMap.get("sqlxh");
//
//		}
		Tycx003CxTxpzxxPojo Tycx003CxTxpzxxPojo = new Tycx003CxTxpzxxPojo();
		Tycx003CxTxpzxxPojo.setSqlxh(sqlxh);
		Tycx003CxTxpzxxPojo.setUuid(txuuid);
		List<Tycx003CxTxpzxxPojo> txpzxxList = Tycx003CxTxpzxxDao
				.select(Tycx003CxTxpzxxPojo);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		// 获取结果列信息
		for (int i = 0; i < txpzxxList.size(); i++) {
			CXDzcxVO dzcxVo = new CXDzcxVO();
			Map txpzxxMap = new HashMap();
			Tycx003CxTxpzxxPojo txpzPojo = txpzxxList.get(i);
			String hzb = txpzPojo.getHzb();
			String zzb = txpzPojo.getZzb();
			String sql = txpzPojo.getSql();
			String sjymc_new = txpzPojo.getSjymc();
			String type = txpzPojo.getTxlx();
			String hzbmc = txpzPojo.getHzbmc();
			String zzbmc = txpzPojo.getZzbmc();
			String title = txpzPojo.getTitle();
			String zzbdw=txpzPojo.getZzbdw();
			String x=txpzPojo.getX();
			String y=txpzPojo.getY();
			String x2=txpzPojo.getX2();
			String y2=txpzPojo.getY2();
			if(TycxUtils.isEmpty(x)){
				x="0";
			}
			if(TycxUtils.isEmpty(y)){
				y="0";
			}
			if(TycxUtils.isEmpty(x2)){
				x2="0";
			}
			if(TycxUtils.isEmpty(y2)){
				y2="0";
			}
			String qxds=txpzPojo.getXqxds();
			String ccgcmc=txpzPojo.getCcgcmc();
			txpzxxMap.put("HZB", hzb);
			txpzxxMap.put("UUID", txpzPojo.getUuid());
			txpzxxMap.put("ZZB", zzb);
			txpzxxMap.put("HZBMC", hzbmc);
			txpzxxMap.put("ZZBMC", zzbmc);
			txpzxxMap.put("TXLX", type);
			txpzxxMap.put("TITLE", title);
			txpzxxMap.put("ZZBDW", zzbdw);
			txpzxxMap.put("x", x);
			txpzxxMap.put("y", y);
			txpzxxMap.put("x2", x2);
			txpzxxMap.put("y2", y2);
			txpzxxMap.put("qxds", qxds);
			// 如果txpzxx表中的sql非空，则优先用配置的SQL，否则用查询定义配置的SQL。
			if (!TycxUtils.isEmpty(sql)) {
				dzcxVo.setSql(sql);
			}
			if (!TycxUtils.isEmpty(sjymc_new)) {
				dzcxVo.setSjymc(sjymc_new);
			}
			dzcxVo.setQueryParams(queryParams);
			//判断存储过程是否为空，如果非空，先执行存储过程
			 if(!TycxUtils.isEmpty(ccgcmc)){
	            	dzcxVo.setCcgcmc(ccgcmc);
	            	tycx002DzcxDao.executeProcedure(dzcxVo);
				}
			// 创建SQL
			dzcxVo = createSql(dzcxVo, null);
			if (!TycxUtils.isEmpty(hzb) || !TycxUtils.isEmpty(zzb)) {
				String sqlstr = "SELECT " + hzb + "," + zzb + " FROM ("
						+ dzcxVo.getSql() + ")";
				dzcxVo.setSql(sqlstr);
			}
			dzcxVo.setQueryType(Constant.queryType_KHD);
			List<Map<String, Object>> dataList = tycx002DzcxDao
					.executeSql(dzcxVo);
			Map<String, Object> map = getResultMap(dataList, zzb, hzb, type);
			if (Constant.TXZS_BAR.equals(type)) {
				List<ChartsBarDataBean> List = (List<ChartsBarDataBean>) map
						.get("yData");
				txpzxxMap.put("yData", List);
			} else if (Constant.TXZS_LINE.equals(type)) {
				List<ChartsLineDataBean> List = (List<ChartsLineDataBean>) map
						.get("yData");
				txpzxxMap.put("yData", List);
			} else if (Constant.TXZS_PIE.equals(type)) {
				List<PieDataBean> List = (List<PieDataBean>) map.get("yData");
				txpzxxMap.put("yData", List);
			} else if (Constant.TXZS_BAR.equals(type)) {
				String List = (String) map.get("yData");
				txpzxxMap.put("yData", List);
			} else if (Constant.TXZS_BAR_LINE.equals(type)) {
				// List<ChartsBarSeriesBean> List=(List<ChartsBarSeriesBean>)
				// map.get("yData");
				List<ChartsBarDataBean> List = (List<ChartsBarDataBean>) map
						.get("yBarData");
				List<ChartsLineDataBean> List2 = (List<ChartsLineDataBean>) map
						.get("yLineData");
				txpzxxMap.put("xBarData", map.get("xBarData"));
				txpzxxMap.put("yBarData", List);
				txpzxxMap.put("xLineData", map.get("xLineData"));
				txpzxxMap.put("yLineData", List2);
			} else if (Constant.TXZS_FUNNEL.equals(type)) {
				List<ChartsDataBean> List = (List<ChartsDataBean>) map
						.get("yData");
				txpzxxMap.put("yData", List);
			}else if(Constant.TXZS_GAUGE.equals(type)){
				ChartsDataBean data =  (ChartsDataBean) map.get("yData");
				txpzxxMap.put("yData", data);
			}else if(Constant.TXZS_RADAR.equals(type)){
				double[] data =  (double[]) map.get("yData");
				List<Radar> radarList=(List<Radar>) map.get("radar");
				txpzxxMap.put("yData", data);
				txpzxxMap.put("radar", radarList);
			}
			String[] xData = (String[]) map.get("xData");
			txpzxxMap.put("xData", xData);
			txpzxxMap.put("zzb", zzb);
			// txpzxxMap.put("title", txpzxxMap.get("TITLE"));
			resultList.add(txpzxxMap);
		}
		List list=new ArrayList();

		for (int i = 0; i < resultList.size(); i++) {
			Map<String, Object> charMap = new HashMap<String, Object>();
			Map<String, Object> resultMap = resultList.get(i);
			String[] xData = (String[]) resultMap.get("xData");
			String title = (String) resultMap.get("TITLE");
			JSONObject resultObj = null;
			String type = (String) resultMap.get("TXLX");
			String xAxisName = (String) resultMap.get("HZBMC");
			String yAxisName = (String) resultMap.get("ZZBMC");
			String yAxisUnit = (String) resultMap.get("ZZBDW");
			int fontsize = 14;// ((BigDecimal)resultMap.get("FONTSIZE")).intValue();
			if (Constant.TXZS_BAR.equals(type)) {
				List<ChartsBarDataBean> dataList = (List<ChartsBarDataBean>) resultMap
						.get("yData");
				resultObj = ChartsFactory.createBar(title, fontsize, xAxisName,
						xData, yAxisName, yAxisUnit, dataList,resultMap);
			} else if (Constant.TXZS_LINE.equals(type)) {
				List<ChartsLineDataBean> dataList = (List<ChartsLineDataBean>) resultMap
						.get("yData");
				resultObj = ChartsFactory.createLine(title, fontsize,
						xAxisName, xData, yAxisName, yAxisUnit, dataList,resultMap);
			} else if (Constant.TXZS_PIE.equals(type)) {
				List<PieDataBean> dataList = (List<PieDataBean>) resultMap
						.get("yData");
				resultObj = ChartsFactory.createPie(title, fontsize, "center",
						"top", "50%", dataList);
			} else if (Constant.TXZS_GAUGE.equals(type)) {
				ChartsDataBean data= (ChartsDataBean) resultMap.get("yData");
				resultObj = ChartsFactory
						.createGauge(title, fontsize, data,resultMap);
			} else if (Constant.TXZS_BAR_LINE.equals(type)) {
				List<ChartsBarDataBean> dataList = (List<ChartsBarDataBean>) resultMap
						.get("yBarData");
				List<ChartsLineDataBean> linedataList = (List<ChartsLineDataBean>) resultMap
						.get("yLineData");
				String yUnit[] = yAxisUnit.split(",");
				String yName[] = yAxisName.split(",");
				resultObj = ChartsFactory.createMashupOne2(title, fontsize,
						xAxisName, xData, yName[0], yUnit[0], yName[1],
						yUnit[1], dataList, linedataList);
			} else if (Constant.TXZS_FUNNEL.equals(type)) {
				List<ChartsDataBean> dataList = (List<ChartsDataBean>) resultMap
						.get("yData");
				resultObj = ChartsFactory.createFunnel(title, fontsize,
						dataList);
			}else if(Constant.TXZS_RADAR.equals(type)){
				double[] data=(double[]) resultMap.get("yData");
				List<RadarName> radarList=(List<RadarName>) resultMap.get("radar");
				resultObj = ChartsFactory.createRadar(title, fontsize, data,radarList,resultMap);
			}
			// jsonArr.add(resultObj);
			charMap.put("charData", resultObj);
			charMap.put("title", title);
			list.add(charMap);
		}
		HashMap reqmap = new HashMap();
		reqmap.put("JSONDATA", JsonUtil.toJson(list));
		resEvent.setResMap(reqmap);
		return resEvent;
    }

    /**
     * 查询结果列
     * add by jinln 2017-09-08
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent getCxjgl(RequestEvent requestEvent) throws Exception {
		logger.debug("debugger " + this.getClass().getName() + "_getCxjgl");
		ResponseEvent resEvent = new ResponseEvent();
		final String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");// sqlxh
		final String queryParams = (String) requestEvent.getRequestMap().get("queryParams");
		final String queryType = (String) requestEvent.getRequestMap().get("queryType");
		HashMap reqmap = new HashMap();
		// 获取查询定义信息
//		Tycx001CxCxdyPojo Tycx001CxCxdyPojo = new Tycx001CxCxdyPojo();
//		Tycx001CxCxdyPojo.setSqlxh(sqlxh);
//		List<Tycx001CxCxdyPojo> cxdyList = tycx001CxCxdyDao.selectCxdy(Tycx001CxCxdyPojo);
//		Tycx001CxCxjgdyPojo Tycx001CxCxjgdyPojo=new Tycx001CxCxjgdyPojo();
//		Tycx001CxCxjgdyPojo.setSqlxh(sqlxh);
		List<Tycx001CxCxjgdyPojo> cxjgList =Tycx001CxCxjgdyDao.selectBySqlxh(sqlxh);
		List cjgx = Tycx001CxCxjgdyDao.getCjgx(sqlxh);//列层级关系
		CXDzcxVO dzcxVo = new CXDzcxVO();
		dzcxVo.setCxjgList(cxjgList);
		dzcxVo.setQueryParams(queryParams);
		dzcxVo=this.LoopQueryParam(dzcxVo);
 		cxjgList=queryCxjgdyListFilterByFzyj(dzcxVo);
	//	List cxjgNewList=getTreeList(cxjgList);
	//  reqmap.put("JSONDATA", JsonUtil.toJson(cxjgNewList));

		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		JSONDATA.put("cxjgList", cxjgList);
		JSONDATA.put("cjgx", cjgx);//获取列层级关系
		reqmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));

	    resEvent.setResMap(reqmap);
		return resEvent;
    }
    public List getTreeList(List<Tycx001CxCxjgdyPojo> cxjgList){

    	List<TreeVO> treeList=new ArrayList<TreeVO>();
    	String sjlmc_str=Constant.SEPARATOR_COMMA;
    	for(int i=0;i<cxjgList.size();i++){
    		Tycx001CxCxjgdyPojo cxjgPojo=cxjgList.get(i);
    		//取所有的上级列名称
    		if(!TycxUtils.isEmpty(cxjgPojo.getSjlmc())&&sjlmc_str.indexOf(Constant.SEPARATOR_COMMA+cxjgPojo.getSjlmc()+Constant.SEPARATOR_COMMA)<0){
    			sjlmc_str=sjlmc_str+cxjgPojo.getSjlmc()+Constant.SEPARATOR_COMMA;
    		}else if(TycxUtils.isEmpty(cxjgPojo.getSjlmc())&&sjlmc_str.indexOf(Constant.SEPARATOR_COMMA+cxjgPojo.getLmc()+Constant.SEPARATOR_COMMA)<0){
    			//取没有上级列名称的并且排除sjlmc=lmc的结果列
    			TreeVO treeVo=new TreeVO();
    			treeVo.setPojo(cxjgPojo);
    			treeList.add(treeVo);
    		}
    	}
    	String[] sjlmc_Arr=sjlmc_str.split(Constant.SEPARATOR_COMMA);
    	for(int m=1;m<sjlmc_Arr.length;m++){
    		String sjlmc=sjlmc_Arr[m];
    		for(int j=0;j<cxjgList.size();j++){
    			Tycx001CxCxjgdyPojo cxjgPojo_j=cxjgList.get(j);
    			if(cxjgPojo_j.getLmc().equals(sjlmc)){
    				TreeVO treeVo_j=new TreeVO();
    				treeVo_j.setPojo(cxjgPojo_j);
    				List<TreeVO> treeList_j=new ArrayList<TreeVO>();
    				for(int n=0;n<cxjgList.size();n++){
    					Tycx001CxCxjgdyPojo cxjgPojo_n=cxjgList.get(n);
    					if(sjlmc.equals(cxjgPojo_n.getSjlmc())){
    						TreeVO treeVo_n=new TreeVO();
    						treeVo_n.setPojo(cxjgPojo_n);
    						treeList_j.add(treeVo_n);
    					}
    				}
    				treeVo_j.setChildrenList(treeList_j);
    				treeList.add(treeVo_j);
    				break;
    			}
    		}
    	}
    	return treeList;
    }
    /**
     * 获取下拉列表数据
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent getCombData(RequestEvent requestEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		Object param = requestEvent.getRequestMap().get("parentParams");
		String[] parentParams=null;
		if(param!=null) {
			parentParams = (String[]) requestEvent.getRequestMap().get("parentParams");
		}
		final String uuid = ((String) requestEvent.getRequestMap().get("uuid"));
		Tycx001CxCxtjdyPojo pojo=new Tycx001CxCxtjdyPojo();
		Tycx001CxCxdyPojo tycx001CxCxdyPojo = new Tycx001CxCxdyPojo();
		tycx001CxCxdyPojo.setSqlxh((String)requestEvent.getRequestMap().get("sqlxh"));
		Tycx001CxCxdyPojo Tycx001CxCxdyPojo = tycx001CxCxdyDao.selectByPKey(tycx001CxCxdyPojo);
		pojo.setUuid(uuid);

		pojo=tycx001CxCxtjdyDao.selectByPKey(pojo);
		String dmsql=pojo.getDmsql();
		final String zdycs=(String) requestEvent.getRequestMap().get("zdycs");
		Map<String,String> map=new HashMap<String,String>();
		//取权限
		ctx = requestEvent.getCtx();
		//Map userInfo=null;
		Map maps = ctx.getUserinfo();
		Set<Map.Entry<String, String>> s=maps.entrySet();
		for (Map.Entry<String, String> entry : s) {
			map.put(entry.getKey(),entry.getValue());
		}
//		map.put("parentParams", parentParams);
		map.put("dmsql", dmsql);
		map.put("zdycs", zdycs);
		map.put("sjy",Tycx001CxCxdyPojo.getSjymc());
		String dmSql = getRealDmSql(map);

		if (TycxUtils.isEmpty(dmSql) || dmSql.indexOf("?") > 0) {
			return resEvent;
		}
		// 防注入检查2012-04-17
		if (!TycxUtils.checkSql(dmSql)) {
			throw new Exception("SQL中包含drop、delete、update等关键字");
		}
		if(TycxUtils.isEmpty(parentParams)){//上级节点是空，则查询SQL，否则根据上级节点查询
			if (!TycxUtils.isEmpty(dmSql)) {
				dmSql = getRealDmSql(map);
			}
		}else{
			String sjlmc = "";
			String sjl = null;
			for(int i = 0;i<parentParams.length;i++){
				String map1 = parentParams[i];
				JSONObject jsonObject = JSONObject.fromObject(map1);

				for (Object str:jsonObject.keySet()) {
					sjl=str.toString();
					sjlmc+="'"+jsonObject.get(str)+"',";

				}
			}
			dmSql = dmSql.replace("@" + sjl + "@", "" + sjlmc + "''");

		}
		DataSourceUtil dataSourceUtils = new DataSourceUtil(Tycx001CxCxdyPojo.getSjymc());
		List resultList =dataSourceUtils.queryforlist(dmSql);
		HashMap reqmap = new HashMap();
		reqmap.put("JSONDATA", JsonUtil.toJson(resultList));
		//reqmap.put("JSONDATA", "[{\"dm\":\"1\",\"mc\":\"A\"}]");
		resEvent.setResMap(reqmap);
    	return resEvent;
    }
    public ResponseEvent getTreeData(RequestEvent requestEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		List<ExtNodeVO> resTree = new ArrayList<ExtNodeVO>();
		HashMap reqmap = new HashMap();
		Object param = requestEvent.getRequestMap().get("parentParams");
		String[] parentParams=null;
		if(param!=null) {
			parentParams = (String[]) requestEvent.getRequestMap().get("parentParams");
		}
		Tycx001CxCxdyPojo tycx001CxCxdyPojo = new Tycx001CxCxdyPojo();
		tycx001CxCxdyPojo.setSqlxh((String) requestEvent.getRequestMap().get("sqlxh"));
		Tycx001CxCxdyPojo Tycx001CxCxdyPojo = tycx001CxCxdyDao.selectByPKey(tycx001CxCxdyPojo);
		String tjlx = (String) requestEvent.getRequestMap().get("llx");// 得到条件类型
		String parentID = (String) requestEvent.getRequestMap().get("parentID");
		String sjymc = (String) requestEvent.getRequestMap().get("sjymc");
		String uuid = (String) requestEvent.getRequestMap().get("uuid");
		String dmSql="";
		HashMap map=new HashMap();
		map.put("sjy",Tycx001CxCxdyPojo.getSjymc());
		//取权限
		 ctx = requestEvent.getCtx();
		 //Map userInfo=null;
		 Map maps = ctx.getUserinfo();
		Set<Map.Entry<String, String>> s=maps.entrySet();
		for (Map.Entry<String, String> entry : s) {
			map.put(entry.getKey(),entry.getValue());
		}

		if(!TycxUtils.isEmpty(uuid)){
		Tycx001CxCxtjdyPojo cxtjpojo=new Tycx001CxCxtjdyPojo();
		cxtjpojo.setUuid(uuid);
		cxtjpojo=tycx001CxCxtjdyDao.selectByPKey(cxtjpojo);
		dmSql=cxtjpojo.getDmsql();
		map.put("dmsql", dmSql);
		map.put("zdycs", cxtjpojo.getZdycs());
		}
		if(TycxUtils.isEmpty(parentID)){//上级节点是空，则查询SQL，否则根据上级节点查询
			if (!TycxUtils.isEmpty(dmSql)) {
				dmSql = getRealDmSql(map);
//				List<Map<String,Object>>list=jdbcDao.queryforlist(dmSql);
//				resTree = TycxUtils.convertMapListToExtTree(list, "dm", "mc", "pdm");
			}
		}else{
			if (!TycxUtils.isEmpty(dmSql)) {
				if (dmSql.indexOf("@") > -1 || dmSql.indexOf("?") > -1) {
					dmSql = dmSql.replaceAll("\\[.+\\]", " 1=1");
					map.put("dmSql", dmSql);
					dmSql = getRealDmSql(map);
				}
				dmSql = dmSql.replaceAll("\\s+", " ");
				dmSql = dmSql.replaceAll("\\[.+\\]", " 1=1");
				dmSql = "SELECT * FROM (" + dmSql + ") WHERE PDM='" + parentID
						+ "'";

			}
		}
		if(TycxUtils.isEmpty(parentParams)){//上级节点是空，则查询SQL，否则根据上级节点查询
			if (!TycxUtils.isEmpty(dmSql)) {
				dmSql = getRealDmSql(map);
//				List<Map<String,Object>>list=jdbcDao.queryforlist(dmSql);
//				resTree = TycxUtils.convertMapListToExtTree(list, "dm", "mc", "pdm");
			}
		}else{
			String sjlmc = "";
			String sjl = null;
			for(int i = 0;i<parentParams.length;i++){
				String map1 = parentParams[i];
				int plength = map1.replace("}","").replace("{","").split(":").length;
				if(plength>1||parentParams.length>1){
				JSONObject jsonObject = JSONObject.fromObject(map1);

				for (Object str:jsonObject.keySet()) {
					sjl=str.toString();
					sjlmc+="'"+jsonObject.get(str)+"',";

				}

				}else{
					String[] a = map1.replace("}", "").replace("{", "").split(":");
					dmSql = dmSql.replace("in (@" + a[0] + "@)", "not in 'null'");
				}

			}
			dmSql = dmSql.replace("@" + sjl + "@", ""+sjlmc+"''");

		}
		DataSourceUtil dataSourceUtils = new DataSourceUtil(Tycx001CxCxdyPojo.getSjymc());
		List<Map<String,Object>>list=dataSourceUtils.queryforlist(dmSql);
		boolean isChecked=false;
		resTree = TycxUtils.convertMapListToExtTree(list, "DM", "MC", "PDM",false);
		reqmap.put("JSONDATA", JsonUtil.toJson(resTree));
		resEvent.setResMap(reqmap);
        return resEvent;
    }
    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_expExcel");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx002DzcxPojo Tycx002DzcxPojo = new Tycx002DzcxPojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List<Tycx002DzcxPojo> expList = null;
        if( !TycxUtils.isEmpty(page) ){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<Tycx002DzcxPojo> pages = new PageInfo<Tycx002DzcxPojo>(tycx002DzcxDao.select(Tycx002DzcxPojo));
            expList = pages.getList();
        }else{
            expList = tycx002DzcxDao.select(Tycx002DzcxPojo);
        }
        Map colMap = new LinkedHashMap();
        colMap.put("ccgcmc","CCGCMC");
        colMap.put("cjjg_dm","CJJG_DM");
        colMap.put("cxfl","CXFL");
        colMap.put("cxlx","CXLX");
        colMap.put("cxms","CXMS");
        colMap.put("exportmax","EXPORTMAX");
        colMap.put("fybj","FYBJ");
        colMap.put("gxfs","GXFS");
        colMap.put("headsql","HEADSQL");
        colMap.put("hjxsbz","HJXSBZ");
        colMap.put("jgsj","JGSJ");
        colMap.put("limttime","LIMTTIME");
        colMap.put("lrrq","LRRQ");
        colMap.put("lrr_dm","LRR_DM");
        colMap.put("mbxh","MBXH");
        colMap.put("myjls","MYJLS");
        colMap.put("pagelayout","PAGELAYOUT");
        colMap.put("queryplugin","QUERYPLUGIN");
        colMap.put("sjgsdq","SJGSDQ");
        colMap.put("sjylx","SJYLX");
        colMap.put("sm","SM");
        colMap.put("sqllx","SQLLX");
        colMap.put("sqlmc","SQLMC");
        colMap.put("sqlstr","SQLSTR");
        colMap.put("sqlxh","SQLXH");
        colMap.put("ssjg_dm","SSJG_DM");
        colMap.put("txzs","图形展示（可多选）");
        colMap.put("xgrq","XGRQ");
        colMap.put("xgr_dm","XGR_DM");
        colMap.put("xybz","XYBZ");
        colMap.put("yssjsj","YSSJSJ");
        colMap.put("ywy","YWY");
        //Excel导出格式
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("fileName","定制查询");
        dataMap.put("class",Tycx002DzcxPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    /**
	 *
	 * @name 得到可执行的dmSql
	 * @description 相关说明
	 * @time 创建时间:2015-5-26上午10:24:50
	 * @param map
	 *            (parentParams,zdycs,dmsql)
	 * @return
	 * @author 作者jinln
	 * @throws SwordBaseCheckedException
	 * @throws
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public String getRealDmSql(Map<String, String> map)	throws Exception {
		String parentParams = map.get("parentParams");
		final String zdycs = map.get("zdycs");
		String dmSql = map.get("dmsql");
		if (!TycxUtils.isEmpty(parentParams)) {
			String parent_value = "";
			//JSONObject tjJSONArr = new JSONObject(parentParams);
			Map<String, Object> tjMap = (Map<String, Object>) JsonUtil.toMap(parentParams);
			for (Entry<String, Object> entry : tjMap.entrySet()) {
				parent_value = (String) entry.getValue();
			}

			// parentParams = parentParams.replace(",", "','");
			dmSql = dmSql
					.replaceAll("=\\s*\\?", " in ('" + parent_value + "')");
			dmSql = dmSql.replace("?", "'" + parent_value + "'");
		}
		// 如果自定义参数非空，替换dmsql
		if (!TycxUtils.isEmptyString(zdycs) && !zdycs.equals("undefined")) {
			final String[] zdycsArr = zdycs.split(",");
			for (int i = 0; i < zdycsArr.length; i++) {

				final Map<String, String> resMap = tycx001CxCxtjdyDao.getRealValue(
						Constant.TJ_DEFAULT_VALUES_DM, zdycsArr[i], map);
				final String realMrz = resMap.get("realMrz");
				final String bzArr = resMap.get("bzArr");
				// dmSql = dmSql.replace("?", "'" + realMrz + "'");
				// dmSql = dmSql.replace("@1@", "'" + realMrz + "'");
				dmSql = dmSql.replace("@" + bzArr + "@", "'" + realMrz + "'");
			}
		}
		dmSql = dmSql.replace("[", "").replace("]", "");
		return dmSql;
	}

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_deleteByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        String[] sqlxh = (String[])requestEvent.getRequestMap().get("sqlxh");
        Tycx002DzcxPojo pojo = null;
        for(int i=0;i<sqlxh.length;i++){
            pojo = new Tycx002DzcxPojo();
            pojo.setSqlxh(sqlxh[i]);
            tycx002DzcxDao.deleteByPKey(pojo);
        }
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_insertSelective");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx002DzcxPojo Tycx002DzcxPojo = new Tycx002DzcxPojo(requestEvent.getRequestMap());
        Tycx002DzcxPojo.setSqlxh(UUIDGenerator.getUUID());
        tycx002DzcxDao.insertSelective(Tycx002DzcxPojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_updateByPKeySelective");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx002DzcxPojo Tycx002DzcxPojo = new Tycx002DzcxPojo(requestEvent.getRequestMap());
        tycx002DzcxDao.updateByPKeySelective(Tycx002DzcxPojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.UPDATE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent selectByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_selectByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        return resEvent;
    }



	/**
	 * 查询条件校验
	 *
	 * @name
	 * @description 查询条件校验:1.校验条件中是否存在SELECT等关键字 2.如果是纳税人端查询,校验条件中是否包含djxh
	 * @time 创建时间:2015-11-6下午4:26:48
	 * @param cxtjVo
	 * @return
	 * @throws SwordBaseCheckedException
	 * @author mahongtao
	 * @throws Exception
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	private Boolean valideCxtj(String queryParams) throws Exception {
		/**
		 * 常规校验:校验条件中是否存在SELECT等关键字
		 */
		Boolean commonCheckFlag = this.isHave(queryParams);
		if (!commonCheckFlag) {
			throw new Exception("1010330020100036");
		}
		return true;
	}

	public boolean isHave(String queryParams) {
		// TODO Auto-generated method stub
		if (!TycxUtils.isEmpty(queryParams)) {
			String a="[{name:'SQLMC',value:'',type:'string',displayValue:''}]";
			final String[] strs = { "SELECT", "DELETE", "DROP", "UPDATE", "CREATE" };// 定义字符串数组
			final List<Map> queryParamsArr = JsonUtil.toListMap(queryParams);
			for (int j = 0; j < strs.length; j++) {
				for (int i = 0; i < queryParamsArr.size(); i++) {
					//final Map<String, Object> queryParamsMap = ((JSONObject) queryParamsArr.get(i)).getMap();
					Map queryParamsMap=queryParamsArr.get(i);
					final String tjValue = ((String) queryParamsMap.get("value"));
					if (!TycxUtils.checkCxtj(tjValue)) {
						return false;
					}

				}
			}

		}
		return true;
	}
	/**
	 *
	 * @name 创建SQL
	 * @description 相关说明
	 * @time 创建时间:2015-5-8上午10:27:12
	 * @param dzcxVo
	 *            dzcxVo
	 * @param cxdyVo
	 *            cxdyVo
	 * @return
	 * @throws SwordBaseCheckedException
	 * @author jinln
	 * @throws Exception
	 * @throws SQLException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public CXDzcxVO createSql(CXDzcxVO dzcxVO,List<Tycx001CxCxjgdyPojo> cxjgList) throws Exception{
		// TODO Auto-generated method stub
		String sql = dzcxVO.getSql();
		// 将sql并去掉回车换行
		sql = sql.replaceAll("\\s+", " ").replaceAll("\\s+and\\s+", " AND ").replaceAll("\\s+or\\s+", " OR ");
		// SQL防注入检查
		if (!TycxUtils.checkSql(sql)) {
			throw new Exception("1010330020100136");
		}
		// 处理sql中包含[* *]、[# #]begin
		if (sql.contains("[*") || sql.contains("[#")) {
			dzcxVO = getTjcxSql(dzcxVO);
		}
		dzcxVO.setSql(sql);
		// 如果传入的查询条件非空，封装SQL
		dzcxVO = getExecuteSql(dzcxVO);
		sql=dzcxVO.getSql();

		if (Constant.queryType_TJ.equals(dzcxVO.getQueryType())) {
			sql = TycxUtils.addWdAndzbToSql(sql, dzcxVO.getWd(), dzcxVO.getZb(),cxjgList);
			dzcxVO.setSql(sql);
		}
		// 如果是统计功能（queryType_TJGN="4"），根据统计条件，封装SQL
		if (Constant.queryType_TJGN.equals(dzcxVO.getQueryType())
				|| Constant.queryType_EXPORT_TJ.equals(dzcxVO.getQueryType())) {
			sql = "SELECT " + dzcxVO.getTjSelect() + " FROM (" + sql + ")"
					+ " GROUP BY " + dzcxVO.getTjGroup() + " ORDER BY "
					+ dzcxVO.getTjOrder();
			dzcxVO.setSql(sql);
		}
		//如果是计算
		if(Constant.queryType_JS.equals(dzcxVO.getQueryType())){
			sql="SELECT "+dzcxVO.getJsbds()+" FROM ("+sql+")";
			dzcxVO.setSql(sql);
		}
		return dzcxVO;
	}
	/**
	 *
	 * @name 获得可执行的SQL
	 * @description 1、如果valueStr非空，则将@nameStr@替换成valueStr。
	 *              2、如果valueStr为空，先查找select和where直接的结果列是否包含
	 *              {....@nameStr@....}这样的字符，
	 *              如果包含，替换成""，再查找条件中[.....@nameStr@...]，将它替换成1=1。 3、最后把(AND
	 *              1=1,OR 1=1,(),{,},[,])这些符号替换成""。
	 *              4、如果排序列非空，按排序字段、排序类型把SQL包装成可执行的SQL。
	 * @time 创建时间:2015-5-8上午10:27:38
	 * @param dzcxVo
	 *            dzcxVo
	 * @param cxdyVo
	 *            cxdyVo
	 * @author jinln
	 * @throws SwordBaseCheckedException
	 * @throws SQLException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public CXDzcxVO getExecuteSql(CXDzcxVO dzcxVO)  {
		//JSONArray tjJSONArr = null;
		final String queryParams = dzcxVO.getQueryParams();
		String sqlStr = dzcxVO.getSql().toUpperCase();
		if (!TycxUtils.isEmpty(queryParams)) {
			//tjJSONArr = JSONArray.fromObject(queryParams);
			List<Map> list= JsonUtil.toListMap(queryParams);
			for (int i = 0; i < list.size(); i++) {
				//Map<String, Object> tjMap = new HashMap<String, Object>();
				Map tjMap =  list.get(i);
				final String nameStr = ((String) tjMap.get("name"))
						.toUpperCase();
				final String typeStr = (String) tjMap.get("type");
				String valueStr = (String) tjMap.get("value");
				// 如果有特殊字符[ 或]则替换为别的
				// 如果是多个参数用逗号隔开，例如in中的参数，需要每个参数加''后再替换
				if ((!TycxUtils.isEmpty(valueStr)) && valueStr.indexOf(",") > 0) {
					valueStr = "'" + valueStr.replaceAll(",", "','") + "'";
				}
				// 替换SQL中@nameStr@
				if (!TycxUtils.isEmpty(valueStr)) {
					if (sqlStr.indexOf("@" + nameStr + "@") >= 0) {
						if (("string".equals(typeStr) || "date".equals(typeStr))
								&& valueStr.indexOf("'") < 0) {
							valueStr = "'" + valueStr + "'";
						}
						sqlStr = sqlStr.replace("@" + nameStr + "@", valueStr);
					}
				} else {// 值为空，SQL中包含@nameStr@，则将[XXXX@nameStr@]部分替换为1=1
					final int index = sqlStr.indexOf("@" + nameStr + "@");// 找到@nameStr@位置
					int start;
					int end;
					if (sqlStr.lastIndexOf("{", index) > -1) {// 如果包含{}符号，表示在查询结果中存在条件变量
						start = sqlStr.lastIndexOf("{", index);// 找{的位置
						end = sqlStr.indexOf("}", index);// 找}的位置
						if (end >= 0) {
							final String str = sqlStr.substring(start, end + 1);// 截取{......@@.....}字符串
							sqlStr = sqlStr.replace(str, "''");// 替换{......@@.....}为""
						}
					} else if (sqlStr.lastIndexOf("[", index) > -1) {
						start = sqlStr.lastIndexOf("[", index);// 从index位置向前找[位置
						end = sqlStr.indexOf("]", index);// 从index位置向后找]位置
						if (end >= 0) {
							final String s = sqlStr.substring(start, end + 1);// 截取[XXX@nameStr@]
							sqlStr = sqlStr.replace(s, " 1=1 ");// 把[XXX@nameStr@]替换成1=1
						}
					}
				}

			}
		}
		String[] sqlArr = sqlStr.split("\\[");
		/**
		 * sqlArr的第一个元素不对like做特殊处理。因为第一个元素不是条件串，如果第一个元素对like做处理，有可能将原来正确内容替换错了--
		 * -modified by mahongtao 2016-1-28 如下面的例子，sql: select L11,'111'
		 * L96,'11' L95,'%' L94,'00000000000' L93 from t where [t.djxh='111111']
		 * and [t.nsrmc like ''%'%']）
		 */
		StringBuffer real_sql = new StringBuffer(sqlArr[0]);
		if (sqlArr.length > 1) {
			for (int i = 1; i < sqlArr.length; i++) {
				String[] sqlArr_mx = sqlArr[i].split("\\]");
				for (int j = 0; j < sqlArr_mx.length; j++) {
					if (sqlArr_mx[j].indexOf("@") < 0) {
						// 处理LIKE条件,20120601，林全加
						sqlArr_mx[j] = sqlArr_mx[j].replaceAll("\\s+[Ll][Ii][Kk][Ee]\\s+", " LIKE ");// 关键字LIKE改为大写
						/*
						if (sqlArr_mx[j].indexOf(" LIKE ") > -1
								&& sqlArr_mx[j].indexOf(" IN ") < 0) {
							if (sqlArr_mx[j].indexOf("''%'") > -1) {// 解决查询定义时，sql中定义like
								// '@XXX@%'的情况
								sqlArr_mx[j] = sqlArr_mx[j].replaceAll(
										"\\s+LIKE\\s+''%'", " LIKE '%")
										.replaceAll("'%'", "%'").replaceAll(
												"''", "'");
							} else if (sqlArr_mx[j].indexOf("'%'") > -1) {
								sqlArr_mx[j] = sqlArr_mx[j].replaceAll(
										"\\s+LIKE\\s+'%'", " LIKE '%")
										.replaceAll("'%'", "%'").replaceAll(
												"''", "'");
							}
						}*/
						real_sql.append(sqlArr_mx[j]);
 					} else {
						real_sql.append(" 1=1 ");
					}
				}
			}
		}

		String reall = real_sql.toString();

		reall = reall.replaceAll(
				"\\[[^\\[|\\]]*@[^\\[|\\]]*@(.*?)[^\\[|\\]]*\\]", " 1 = 1 ");
		reall = reall.replaceAll("\\[|\\]", "");
		reall = reall.replaceAll("@(.*?)@", "''");
		// 用正则表达式删除掉1=1条件,经典，林全，2012-03-31，山重水复疑无路，柳暗花明又一村
		reall = reall.replaceAll("AND\\s+1=1", " ").replaceAll(
				"OR\\s+1=1", " ").replaceAll("\\s+", " ");
		// 去掉所有的"["和"]"
		reall = reall.replaceAll("\\[", "").replaceAll("\\]", "");
		// 去掉空的(),((()))
		reall = reall.replaceAll("\\(+\\s+\\)+", "");
		// 去掉SQL中的{}，暂时不会考虑结果变量为空的情况 add by lxn 20141101
		reall = reall.replaceAll("\\{", "");
		reall = reall.replaceAll("\\}", "");
//		如果headSQL非空
//		if (!TycxUtils.isEmpty(Tycx001CxCxdyPojo.getHeadsql())) {
//			final List<Map<String, Object>> list = dzcxDao.executeSQLBySql(
//					real_sql, Tycx001CxCxdyPojo.getSjymc(), false);
//			final Object o = list.get(0).entrySet().iterator().next()
//					.getValue();
//
//			if (o instanceof Clob) {
//
//				final Clob c = (Clob) o;
//				real_sql = c.getSubString(1, (int) c.length());
//
//			} else {
//				real_sql = String.valueOf(o);
//			}
//
//		}

		dzcxVO.setSql(reall);
		return dzcxVO;
	}

	/**
	 *
	 * @name 代码转名称
	 * @description 相关说明
	 * @time 创建时间:2015-5-9下午4:09:03
	 * @param resultMap
	 *            resultMap
	 * @param dzcxVo
	 *            dzcxVo
	 * @return List<Map<String, Object>>
	 * @author 作者jinln
	 * @throws SwordBaseCheckedException
	 * @throws
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public static List dmTomc(
			List<Map> list, CXDzcxVO dzcxVo)
			throws Exception {
		tycxUtil tycxUtil = new tycxUtil();
		Map<String, Object> tempDataMap = new HashMap<String, Object>();
		// 得到代码转名称列
		List<Map> jsonArrDmToMc = null;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				// 得到代码表转换列
				// =====begin==============
				tempDataMap = (Map<String, Object>) list.get(i);
				// 对值为null替换为"" add by lxn 20141029
				final Set set = tempDataMap.keySet();
				for (Object key : set) {
					if ("null".equals(tempDataMap.get(key))
							|| tempDataMap.get(key) == null) {
						tempDataMap.put(key.toString(), "");
					} else if (!TycxUtils.isEmpty(tempDataMap.get(key))
							&& "class java.lang.Integer".equals(tempDataMap.get(key).getClass().toString())) {
						// 修改BUG，后台对数值为0的进行转换 update by lxn 20141120
						final int value = (Integer) tempDataMap.get(key);
						if (value == 0) {
							tempDataMap.put(key.toString(),
									"0.00000000000000000001");
						}
					}
				}
				jsonArrDmToMc = JsonUtil.toListMap(dzcxVo.getCode2name());// 定义代码转名称列
				if (jsonArrDmToMc != null && jsonArrDmToMc.size() > 0) {
					for (int j = 0; j < jsonArrDmToMc.size(); j++) {
						Map jsonDmToMc= jsonArrDmToMc.get(j);
						final String dmweb = (String) jsonDmToMc.get("name");
						final String table = (String) jsonDmToMc.get("table");
						String mc = "";
						String dm = "";
						if (!TycxUtils.isEmpty(table)) {

							Map hcbList = tycxUtil.queryForMap(table,dzcxVo.getSjymc());
							Map<String,Object> hcbmap=(Map<String, Object>) hcbList.get(0);
							mc = (String) hcbList.get("default_Value_Column");
							dm = (String) hcbList.get("index_Columns");
						}

						final String dm2mc = tycxUtil.getDmMc(table,
								(String) tempDataMap.get(dmweb), mc,dm,dzcxVo.getSjymc());
						// resultList中增加dm_MC字段，即代码转名称后的结果列,如果查询类型是导出和统计导出，直接代码转名称
						if (Constant.queryType_EXPORT.equals(dzcxVo
								.getQueryType())
								|| Constant.queryType_EXPORT_TJ.equals(dzcxVo
										.getQueryType())
								|| Constant.queryType_EXPORT_JCB.equals(dzcxVo
										.getQueryType())) {
							 (list.get(i)).put(dmweb, dm2mc);
						} else {
//							(list.get(i)).put(dmweb + "_MC", dm2mc);
							(list.get(i)).remove(dm);
							(list.get(i)).put(dmweb , dm2mc);
						}
					}
				}

			}
		}
		return list;
	}

	/**
	 *
	 * @name 循环查询条件,判断是否包含分支语句、是否有维度、指标
	 * @description 相关说明
	 * @time 创建时间:2015-6-3下午3:21:05
	 * @param dzcxVo
	 *            dzcxVo
	 * @return
	 * @throws
	 *             json异常
	 * @throws SwordBaseCheckedException
	 *             SwordBaseCheckedException
	 * @author 作者jinln
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public CXDzcxVO LoopQueryParam(CXDzcxVO dzcxVo) throws Exception{
		// 调用cxtjBO,根据sqlxh查询是否有分支语句
		final String sqlxh = dzcxVo.getSqlxh();
//		final List<CXCxtjdyFzyjInfoVO> vo = fzyjBO
//				.queryCxtjdyFzyjListBySqlxh(sqlxh);
//		if (!OdsUtils.isEmpty(vo)) {
//			dzcxVo.setTjlmc(vo.get(0).getTjlmc());
//		}
		final String queryParams = dzcxVo.getQueryParams();
		List tjJSONArr = null;
		if (!TycxUtils.isEmpty(queryParams)) {
			tjJSONArr = JsonUtil.toListMap(queryParams);
			String zb = "";
			String wd = "";
			String cxjgl_checked="";
			for (int i = 0; i < tjJSONArr.size(); i++) {
				Map<String, Object> tjMap  = (Map<String, Object>) tjJSONArr.get(i);
				final String nameStr = (String) tjMap.get("name");
				final String valueStr = (String) tjMap.get("value");
//				if (nameStr.equalsIgnoreCase(dzcxVo.getTjlmc())) {
//					dzcxVo.setTjlz(valueStr);
//					dzcxVo.setTjfzyj(fzyjBO
//							.queryCxtjdyFzyjInfoBySqlxhAndTjlmcTjlz(sqlxh,
//									nameStr, valueStr).getSqlstr());
//					break;
//				}

				// 如果是交叉表查询或者是统计查询，循环出指标和维度

				if (Constant.WRAPPARAMS_ZB.equals(nameStr)) {
					zb = zb + valueStr + ",";
				}
				if (Constant.WRAPPARAMS_WD.equals(nameStr)) {
					wd = wd + valueStr + ",";
				}
				if(Constant.CXJGL_CHECKED.equals(nameStr)){
					cxjgl_checked=valueStr;
				}

			}
			if (!TycxUtils.isEmpty(zb)) {
				zb = zb.substring(0, zb.length() - 1);
			}
			if (!TycxUtils.isEmpty(wd)) {
				wd = wd.substring(0, wd.length() - 1);
			}
			if (!TycxUtils.isEmpty(cxjgl_checked)) {
				cxjgl_checked = cxjgl_checked.substring(0, cxjgl_checked.length() - 1);
			}
			dzcxVo.setZb(zb);
			dzcxVo.setWd(wd);
			dzcxVo.setCxjgl_checked(cxjgl_checked);
		}
		return dzcxVo;
	}

	/**
	 *
	 * @name
	 *       循环包装条件,包装条件主要包含统计列，指标；模糊查询条件；{statisticParams:[{name:xxx,type:GROUP}
	 *       ,{name:xxx,type:SUM},...],resultQueryParams:[.....]}
	 * @description 相关说明
	 * @time 创建时间:2015-5-11下午2:37:20
	 * @param dzcxVo
	 *            dzcxVo
	 * @author jinln
	 * @throws SwordBizCheckedException
	 * @throws JSONException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public CXDzcxVO LoopWrapParams(CXDzcxVO dzcxVo)	throws Exception {
		String wrapParams = dzcxVo.getWrapParams();

		if (!TycxUtils.isEmpty(wrapParams)) {
			List wrapParamList= JsonUtil.toListMap(wrapParams);
			//wrapParamMap = (Map<String, Object>) JsonUtil.toMap(wrapParams);
			// 如果是统计功能查询
			if (Constant.queryType_TJGN.equals(dzcxVo.getQueryType())
					|| Constant.queryType_EXPORT_TJ.equals(dzcxVo
							.getQueryType())) {
				String tjSelect = "";
				String tjGroup = "";
				String tjOrder = "";
				List<Map<String, Object>> tjlList = new ArrayList<Map<String, Object>>();

				for (int i = 0; i < wrapParamList.size(); i++) {
					final Map<String, Object> param =(Map<String, Object>) wrapParamList.get(i);
					Map<String, Object> tjlMap = new HashMap<String, Object>();
					final String name = (String) param.get("name");
					final String type = (String) param.get("type");
					final String text = (String) param.get("text");
					if (type.equals(Constant.TJ_WDL)) {// 维度列
						tjSelect = tjSelect + name + ",";
						tjGroup = tjGroup + name + ",";
						tjOrder = tjOrder + name + ",";
						tjlMap.put("lmc", name);
					} else if (type.equals(Constant.TJLX_COUNT)) {
						tjSelect = tjSelect + "COUNT(*) " + '"' + name + '"'
								+ ",";
						tjlMap.put("lmc", name);
					} else if (type.equals(Constant.TJLX_SUM)) {
						tjSelect = tjSelect + " DECODE((" + "SUM(" + name + ")"
								+ "),null,0,(" + "SUM(" + name + ")" + ")) "
								+ name + ",";// ROUND(DECODE((AVG(ZCZB)),null,0,(AVG(ZCZB))),2)
						tjlMap.put("lmc", name);
					} else if (type.equals(Constant.TJLX_AVG)) {
						tjSelect = tjSelect + " DECODE((" + "AVG(" + name + ")"
								+ "),null,0,(" + "AVG(" + name + ")" + ")) "
								+ name + ",";// +"AVG("+lmc+")"+lmc+"_AVG"+",";
						tjlMap.put("lmc", name);

					}
					tjlMap.put("lms", text);
					tjlList.add(tjlMap);
				}
				if (tjSelect.length() > 0) {
					tjSelect = tjSelect.substring(0, tjSelect.length() - 1);
				}
				if (tjGroup.length() > 0) {
					tjGroup = tjGroup.substring(0, tjGroup.length() - 1);
				}
				if (tjOrder.length() > 0) {
					tjOrder = tjOrder.substring(0, tjOrder.length() - 1);
				}
				dzcxVo.setTjSelect(tjSelect);
				dzcxVo.setTjGroup(tjGroup);
				dzcxVo.setTjOrder(tjOrder);
				dzcxVo.setTjlList(tjlList);
			} else if(Constant.queryType_JS.equals(dzcxVo.getQueryType())){
				String jsbds="";
				for (int i = 0; i < wrapParamList.size(); i++) {
				final Map<String, Object> param =(Map<String, Object>) wrapParamList.get(i);
				final String name = (String) param.get("name");
				final String text = (String) param.get("text");
				jsbds=jsbds+name+",";
				}
				if(!TycxUtils.isEmpty(jsbds)){
					jsbds=jsbds.substring(0, jsbds.length()-1);
					dzcxVo.setJsbds(jsbds);
				}
			}
		}
		return dzcxVo;
	}
	/**
	 *
	 * @name 统计查询SQL包装
	 * @description 相关说明
	 * @time 创建时间:2015-5-7上午10:20:43
	 * @param sql
	 *            sql
	 * @param wrapParamsVo
	 *            包装参数
	 * @return
	 * @author jinln
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public CXDzcxVO getTjcxSql(CXDzcxVO dzcxVo) {
		String sql = dzcxVo.getSql();
		while (sql.contains("[*")) {
			dzcxVo.setQueryType_Tj_bj(Constant.QueryType_Tj_bj);
			final String varStr = sql.substring(sql.indexOf("[*"), sql
					.indexOf("*]") + 2);
			final String zb = dzcxVo.getZb();
			sql = sql.replace(varStr, zb);
		}
		while (sql.contains("[#")) {
			final String varStr = sql.substring(sql.indexOf("[#"), sql
					.indexOf("#]") + 2);
			final String wd = dzcxVo.getWd();
			sql = sql.replace(varStr, wd);
		}
		dzcxVo.setSql(sql);
		return dzcxVo;
	}
	 public List<Tycx001CxCxjgdyPojo> queryCxjgdyListFilterByFzyj(CXDzcxVO dzcxVO) throws Exception{
		 final List<Map<String,Object>> mapList =new ArrayList<Map<String,Object>>();
		 //维度和指标,只保留维度和指标指定的列,其他移除(Constant.queryType_TJ 查询类型_统计查询的时候),格式为:LMC1,LMC2,LMC3...
		 List<Tycx001CxCxjgdyPojo> list=dzcxVO.getCxjgList();
         List<Tycx001CxCxjgdyPojo> tempCxjgdyList = new ArrayList<Tycx001CxCxjgdyPojo>();
         boolean isHasWdOrZb = false;
         final String wd = dzcxVO.getWd();
         if(!TycxUtils.isEmptyString(wd)){
             isHasWdOrZb = true;
             final String[] wdArray = wd.split(",");
             for(String wdFieldName : wdArray){
                 for(Tycx001CxCxjgdyPojo cxjgdyInfo : list){
                     if(wdFieldName.equals(cxjgdyInfo.getLmc())){
                         tempCxjgdyList.add(cxjgdyInfo);
                         break;
                     }
                 }
             }
         }

         final String zb = dzcxVO.getZb();
         if(!TycxUtils.isEmptyString(zb)){
             isHasWdOrZb = true;
             final String[] zbArray = zb.split(",");
             for(String zbFieldName : zbArray){
                 for(Tycx001CxCxjgdyPojo cxjgdyInfo : list){
                     if(zbFieldName.equals(cxjgdyInfo.getLmc())){
                         tempCxjgdyList.add(cxjgdyInfo);
                         break;
                     }
                 }
             }
         }
         final String cxjgl_checked=dzcxVO.getCxjgl_checked();
         if(!TycxUtils.isEmptyString(cxjgl_checked)){
        	 final String[] cxjgl_CheckArray = cxjgl_checked.split(",");
             for(String CheckCgl : cxjgl_CheckArray){
                 for(Tycx001CxCxjgdyPojo cxjgdyInfo : list){
                     if(CheckCgl.equals(cxjgdyInfo.getLmc())){
                         tempCxjgdyList.add(cxjgdyInfo);
                         break;
                     }
                 }
             }
         }
         if(isHasWdOrZb){
             list = tempCxjgdyList;
         }
		 return list;
	 }
	 public Map<String, Object> getResultMap(List<Map<String, Object>> dataList,
				String zzb, String hzb, String type)
				throws Exception {
			// TODO Auto-generated method stub
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String xData[] = new String[dataList.size()];
			if (Constant.TXZS_BAR.equals(type)) {
				List<ChartsBarDataBean> List = new ArrayList<ChartsBarDataBean>();
				String y[] = zzb.split(",");
				for (int n = 0; n < y.length; n++) {
					String zzb_new = y[n];
					String yData[] = new String[dataList.size()];
					ChartsBarDataBean barData = new ChartsBarDataBean();
					for (int m = 0; m < dataList.size(); m++) {
						Map<String, Object> dataMap = dataList.get(m);
						// 取X轴数据
						xData[m] = (String) dataMap.get(hzb);
						// Y轴数据
						yData[m] = String.valueOf(dataMap.get(zzb_new));
					}
					barData.setData(yData);
					barData.setName(zzb_new);
					List.add(barData);
				}
				resultMap.put("xData", xData);
				resultMap.put("yData", List);
			} else if (Constant.TXZS_LINE.equals(type)) {
				List<ChartsLineDataBean> List = new ArrayList<ChartsLineDataBean>();
				String y[] = zzb.split(",");
				for (int n = 0; n < y.length; n++) {
					String zzb_new = y[n];
					String yData[] = new String[dataList.size()];
					ChartsLineDataBean barData = new ChartsLineDataBean();
					for (int m = 0; m < dataList.size(); m++) {
						Map<String, Object> dataMap = dataList.get(m);
						// 取X轴数据
						xData[m] = (String) dataMap.get(hzb);
						// Y轴数据
						yData[m] = String.valueOf(dataMap.get(zzb_new));
					}
					barData.setData(yData);
					barData.setName(zzb_new);
					List.add(barData);
				}
				resultMap.put("xData", xData);
				resultMap.put("yData", List);
			} else if (Constant.TXZS_PIE.equals(type)) {
				List<PieDataBean> List = new ArrayList<PieDataBean>();
				String y[] = zzb.split(",");
				for (int n = 0; n < y.length; n++) {
					String zzb_new = y[n];

					for (int m = 0; m < dataList.size(); m++) {
						PieDataBean pieData = new PieDataBean();
						Map<String, Object> dataMap = dataList.get(m);
						// 取X轴数据
						// xData[m]=(String)dataMap.get(hzb);
						// Y轴数据
						pieData.setName((String) dataMap.get(hzb));
						pieData.setValue(String.valueOf(dataMap.get(zzb_new)));
						List.add(pieData);
					}

				}
				resultMap.put("xData", xData);
				resultMap.put("yData", List);
			} else if (Constant.TXZS_BAR_LINE.equals(type)) {
//				List<ChartsBarSeriesBean> List = new ArrayList<ChartsBarSeriesBean>();
//				String y[] = zzb.split(",");
//				String[] typeStr={"bar","line"};
//				for (int n = 0; n < y.length; n++) {
//					String zzb_new = y[n];
//					String yData[] = new String[dataList.size()];
//					ChartsBarSeriesBean barData = new ChartsBarSeriesBean();
//					for (int m = 0; m < dataList.size(); m++) {
//						Map<String, Object> dataMap = dataList.get(m);
//						// 取X轴数据
//						xData[m] = (String) dataMap.get(hzb);
//						// Y轴数据
//						yData[m] = (String) dataMap.get(zzb_new);
//					}
//					barData.setData(yData);
//					barData.setType(typeStr[n]);
//					barData.setName(zzb_new);
//					List.add(barData);
//				}
//				resultMap.put("xData", xData);
//				resultMap.put("yData", List);

				String y[] = zzb.split(",");
				for (int n = 0; n < y.length; n++) {
					String zzb_new = y[n];
					String yData[] = new String[dataList.size()];
					if(n==0){
						List<ChartsBarDataBean> List = new ArrayList<ChartsBarDataBean>();
					ChartsBarDataBean barData = new ChartsBarDataBean();
					for (int m = 0; m < dataList.size(); m++) {
						Map<String, Object> dataMap = dataList.get(m);
						// 取X轴数据
						xData[m] = (String) dataMap.get(hzb);
						// Y轴数据
						yData[m] = String.valueOf( dataMap.get(zzb_new));
					}
					barData.setData(yData);
					barData.setName(zzb_new);
					List.add(barData);
					resultMap.put("xBarData", xData);
					resultMap.put("yBarData", List);
					}else{
						List<ChartsLineDataBean> List = new ArrayList<ChartsLineDataBean>();
						ChartsLineDataBean lineData = new ChartsLineDataBean();
						for (int m = 0; m < dataList.size(); m++) {
							Map<String, Object> dataMap = dataList.get(m);
							// 取X轴数据
							xData[m] = (String) dataMap.get(hzb);
							// Y轴数据
							yData[m] = String.valueOf(dataMap.get(zzb_new));
						}
						lineData.setData(yData);
						lineData.setName(zzb_new);
						List.add(lineData);
						resultMap.put("xLineData", xData);
						resultMap.put("yLineData", List);
					}

				}

			}else if (Constant.TXZS_FUNNEL.equals(type)) {
				List<ChartsDataBean> List = new ArrayList<ChartsDataBean>();
				String y[] = zzb.split(",");
				for (int n = 0; n < y.length; n++) {
					String zzb_new = y[n];

					for (int m = 0; m < dataList.size(); m++) {
						ChartsDataBean pieData = new ChartsDataBean();
						Map<String, Object> dataMap = dataList.get(m);
						// 取X轴数据
						// xData[m]=(String)dataMap.get(hzb);
						// Y轴数据
						pieData.setName((String) dataMap.get(hzb));
						pieData.setValue(String.valueOf( dataMap.get(zzb_new)));
						List.add(pieData);
					}

				}
				resultMap.put("xData", xData);
				resultMap.put("yData", List);
			}else if (Constant.TXZS_GAUGE.equals(type)) {
			List<ChartsGaugeSeriesBean> List = new ArrayList<ChartsGaugeSeriesBean>();

			Map<String, Object> dataMap = dataList.get(0);
			ChartsDataBean charData=new ChartsDataBean();
			charData.setName((String)dataMap.get(hzb));
			charData.setValue(String.valueOf(dataMap.get(zzb)));
			resultMap.put("yData", charData);
			}else if(Constant.TXZS_RADAR.equals(type)){
				double[] data=new double[dataList.size()];
				List<RadarName> radarList=new ArrayList<RadarName>();
					for (int m = 0; m < dataList.size(); m++) {
						RadarName radar=new RadarName();
						Map<String, Object> dataMap = dataList.get(m);
						data[m]= Double.valueOf(String.valueOf((dataMap.get(zzb))));
						radar.setName((String) dataMap.get(hzb));
						radar.setMax(8000);
						radarList.add(radar);
					}
					resultMap.put("yData", data);
					resultMap.put("radar", radarList);
			}

			return resultMap;
		}
	  public ResponseEvent setResultColumns(RequestEvent requestEvent) throws Exception {
			logger.debug("debugger " + this.getClass().getName() + "_getCxjgl");
			ResponseEvent resEvent = new ResponseEvent();
			final String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");// sqlxh
			Tycx001CxCxjgdyPojo Tycx001CxCxjgdyPojo=new Tycx001CxCxjgdyPojo();
			Tycx001CxCxjgdyPojo.setSqlxh(sqlxh);
			List<Tycx001CxCxjgdyPojo> cxjgList =Tycx001CxCxjgdyDao.select(Tycx001CxCxjgdyPojo);
			HashMap reqmap = new HashMap();
		    reqmap.put("JSONDATA", JsonUtil.toJson(cxjgList));
//			reqmap.put("cxjglList", cxjgList);
		    resEvent.setResMap(reqmap);
			resEvent.setFwordPath("/biz/core/ext/tycx/tycx002/jsp/tycx002_setResultColumns.jsp");
			return resEvent;
	  }

	  public ResponseEvent openFK(RequestEvent reqEvent) throws Exception{
		logger.debug("debugger " + this.getClass().getName());
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		String sqlxh = (String) reqEvent.getRequestMap().get("sqlxh");
		String fxyyid = (String) reqEvent.getRequestMap().get("fxyyid");
		List wtlxList =jdbcDao.queryforlist("select * from T_WTFK_WTLX");

		List wtclztList =jdbcDao.queryforlist("select * from T_WTFK_WTCLZT");

		resMap.put("sqlxh", sqlxh);
		resMap.put("fxyyid", fxyyid);
		resMap.put("wtlxList", wtlxList);
//		resMap.put("wtlxListJSON", JsonUtil.toJson(wtlxList).replace("\"", "'"));
		resMap.put("wtclztList", wtclztList);
		resEvent.setResMap(resMap);
		resEvent.setFwordPath("/biz/core/ext/tycx/tycx002/jsp/tycx002Fankui.jsp");

	    return resEvent;
	  }

	public ResponseEvent queryFankui(RequestEvent reqEvent) throws Exception {
		logger.debug("debugger " + this.getClass().getName());
		ResponseEvent resEvent = new ResponseEvent();
		HashMap resMap = new HashMap();
		String sqlxh = (String) reqEvent.getRequestMap().get("sqlxh");
		String fxyyid=(String) reqEvent.getRequestMap().get("fxyyid");
		String wtlx = (String) reqEvent.getRequestMap().get("wtlx");
		String wtclzt = (String) reqEvent.getRequestMap().get("wtclzt");
		Tycx002WtfkPojo tycx002WtfkPojo = new Tycx002WtfkPojo();
		// 测试
		// sqlxh="58A9F3E96BFEDEDEE050108DD1331DB3";
		tycx002WtfkPojo.setSqlxh(sqlxh);
		tycx002WtfkPojo.setWt_lx(wtlx);
		tycx002WtfkPojo.setZt(wtclzt);
		tycx002WtfkPojo.setFxyyid(fxyyid);
		PageHelper.startPage(reqEvent.getRequestMap());
		List<Tycx002WtfkPojo> wtfkList = tycx002DzcxDao.searchWtfk(tycx002WtfkPojo);
		PageInfo<Tycx002WtfkPojo> pages = new PageInfo<Tycx002WtfkPojo>(wtfkList);

		resMap.put("JSONDATA", TycxUtils.toJsonForJqGrid(pages));
		resEvent.setResMap(resMap);

		  return resEvent;
	  }
	  public ResponseEvent openFKMX(RequestEvent reqEvent) throws Exception{
		logger.debug("debugger " + this.getClass().getName());
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		String id = (String) reqEvent.getRequestMap().get("id");
		String sqlxh = (String) reqEvent.getRequestMap().get("sqlxh");
		String fxyyid=(String) reqEvent.getRequestMap().get("fxyyid");
		String lcslh=(String) reqEvent.getRequestMap().get("lcslh");
		if(id != null||lcslh!=null){
			Tycx002WtfkPojo tycx002WtfkPojo = new Tycx002WtfkPojo();
			tycx002WtfkPojo.setId(id);
			tycx002WtfkPojo.setLcslh(lcslh);
			Tycx002FjPojo tycx002FjPojo = new Tycx002FjPojo();
			tycx002FjPojo.setWtfk_id(id);
			List<Tycx002WtfkPojo> wtfkList = tycx002DzcxDao.searchWtfk(tycx002WtfkPojo);
			//获取附件信息
			List<Tycx002FjPojo> fjList=tycx002DzcxDao.searchFj(tycx002FjPojo);
			if(!TycxUtils.isEmpty(wtfkList)){
				resMap.put("wtfkList", wtfkList.get(0));
				resMap.put("id", wtfkList.get(0).getId());
			}else{
				resMap.put("wtfkList", "");
				resMap.put("id", id);
			}
			resMap.put("fjList", fjList);


		}
		if(sqlxh != null){
			resMap.put("sqlxh", sqlxh);
		}
		if(!TycxUtils.isEmpty(fxyyid)){
			resMap.put("fxyyid", fxyyid);
		}

		List wtlxList = CacheUtil.getCodeTable("T_WTFK_WTLX");

		List yxjList= CacheUtil.getCodeTable("T_WTFK_YXJ");

		List cxglList= CacheUtil.getCodeTable("T_WTFK_CXGL");

		List yzcdList= CacheUtil.getCodeTable("T_WTFK_YZCD");

		List wtclztList = CacheUtil.getCodeTable("T_WTFK_WTCLZT");

		resMap.put("wtlxList", wtlxList);
		resMap.put("yxjList", yxjList);
		resMap.put("cxglList", cxglList);
		resMap.put("yzcdList", yzcdList);
		resMap.put("wtclztList", wtclztList);

		resEvent.setResMap(resMap);
		resEvent.setFwordPath("/biz/core/ext/tycx/tycx002/jsp/tycx002FankuiMx.jsp");

		return resEvent;
	  }

	  public ResponseEvent searchFj(RequestEvent reqEvent) throws Exception{
			logger.debug("debugger " + this.getClass().getName());
			ResponseEvent resEvent = new ResponseEvent();
			HashMap<String, Object> resMap = new HashMap<String, Object>();
			String id = (String) reqEvent.getRequestMap().get("id");

			Tycx002FjPojo tycx002FjPojo = new Tycx002FjPojo();
			tycx002FjPojo.setWtfk_id(id);
			//获取附件信息
			PageHelper.startPage(reqEvent.getRequestMap());
			List<Tycx002FjPojo> fjList=tycx002DzcxDao.searchFj(tycx002FjPojo);
			PageInfo pages = new PageInfo(fjList);
			resMap.put("JSONDATA", TycxUtils.toJsonForJqGrid(pages));
			resEvent.setResMap(resMap);
			return resEvent;
	  }

	  public ResponseEvent savedWtfk(RequestEvent reqEvent) throws Exception{
		  logger.debug("debugger " + this.getClass().getName());
		  ResponseEvent resEvent = new ResponseEvent();
		  HashMap<String, Object> resMap = new HashMap<String, Object>();
		  Tycx002WtfkPojo tycx002WtfkPojo = new Tycx002WtfkPojo(reqEvent.getRequestMap());
		  ctx = reqEvent.getCtx();

			 Map userInfo=null;
	    	 if(ctx!=null){
		  	    	userInfo =	ctx.getUserinfo();
		   	  }
		  String currDate=formetDate();
		  if(tycx002WtfkPojo.getId().length() > 0 ){
			  tycx002WtfkPojo.setXg_sj(currDate);
			  tycx002DzcxDao.updateWtfk(tycx002WtfkPojo);
		  }else{
			  //获取ID
			  tycx002WtfkPojo.setId(UUID.randomUUID().toString().replace("-", ""));
			  tycx002WtfkPojo.setLr_sj(currDate);
			 // tycx002WtfkPojo.setLrry_dm((String)userInfo.get("userId"));//暂时注释掉，因为取到的ctx是null
			 // tycx002WtfkPojo.setClry_dm((String)userInfo.get("userId"));
			  tycx002WtfkPojo.setLrry_dm("cssnj");
			  tycx002WtfkPojo.setClry_dm("cssnj");
			  tycx002WtfkPojo.setXg_sj(currDate);
			  tycx002WtfkPojo.setFxyylx_dm("01");
			  tycx002WtfkPojo.setYxbj("1");
			  tycx002DzcxDao.insertWtfk(tycx002WtfkPojo);
		  }

		  HashMap map = new HashMap();
		  map.put("mess", "保存成功");
		  resMap.put("JSONDATA", JsonUtil.toJson(map));
	      resEvent.setResMap(resMap);
		  return resEvent;
	  }

	//打开添加附件页面
	/*public ResponseEvent addFjJsp(RequestEvent reqEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		resEvent.setFwordPath("/biz/core/ext/tycx/tycx002/jsp/wtfk_addfj.jsp");
		return resEvent;
	}*/

	//上传操作
	public ResponseEvent uploadFj(RequestEvent reqEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		//待上传文件
		HashMap<String, Object> reqMap = reqEvent.getRequestMap();
		String filePath = reqEvent.getReqMap().get("path").toString();
		String id = (String) reqEvent.getRequestMap().get("id");

		//ip
		String ip=null;
		//port
		Integer port=null;
		//用户名,username
		String userName=null;
		//密码,password
		String password=null;
		//上传路径
		String uploadPath=null;
		//获取ftp连接参数
		List ftpParams= CacheUtil.getCodeTable("SYS_XTCS","CSBM IN('FTP_INFO','FTP_USERNAME','FTP_PASSWORD')");
		for(Object ftpParam:ftpParams){
			Map param=(Map) ftpParam;
			String csbm=(String) param.get("csbm");
			String csz=(String) param.get("csz");
			if(csbm.equals("FTP_INFO")){
				ip=csz;
				continue;
			}else if(csbm.equals("FTP_USERNAME")){
				userName=csz;
				continue;
			}else if(csbm.equals("FTP_PASSWORD")){
				password=csz;
				continue;
			}
		}
		port=21;
//		uploadPath="";

		//调用上传接口
		FtpFileUtil ftpFileUtil=new FtpFileUtil();
		String flag=ftpFileUtil.FTPUpload(ip,port,userName,password,uploadPath,filePath);
		int success;
		String fileName=null;
		//如果成功，返回为: code+fileName,失败：code
		if(flag.length()>1){
			success=Integer.parseInt(flag.substring(0, 1));
			//服务器保存的名称：UUID
			fileName=flag.substring(1);
			resMap.put("fileName", fileName);
		}else{
			success=Integer.parseInt(flag);
		}
		if(success == 0){
			resMap.put("result", true);
			resMap.put("msg", "文件上传成功！");
		}else if(success == 1){
			deleteLocalFile(filePath);
			resMap.put("result", false);
			resMap.put("msg", "文件上传失败！");
		}else if(success == 2){
			deleteLocalFile(filePath);
			resMap.put("result", false);
			resMap.put("msg", "文件服务器连接失败，请检查账户及密码……");
		}
		resMap.put("id", id);
		resMap.put("userName", userName);
		resMap.put("password", password);
		//文件原名称
		resMap.put("filePath", filePath);
		resMap.put("JSONDATA", JsonUtil.toJson(resMap));
		resEvent.setResMap(resMap);
		return resEvent;
	}

	//下载操作
	public ResponseEvent downloadFj(RequestEvent reqEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		HashMap<String, Object> reqMap = reqEvent.getRequestMap();
		String fjid = (String) reqEvent.getRequestMap().get("fjid");
		Tycx002FjPojo tycx002FjPojo = new Tycx002FjPojo();
		tycx002FjPojo.setFj_id(fjid);
		//获取附件信息
		List<Tycx002FjPojo> fjList=tycx002DzcxDao.searchFj(tycx002FjPojo);
		tycx002FjPojo=fjList.get(0);

		//ip
		String ip=null;
		// 获取ftp连接参数
		List ftpParams = CacheUtil.getCodeTable("SYS_XTCS","CSBM ='FTP_INFO'");
		ip=(String) ((Map)ftpParams.get(0)).get("csz");
		//port
		Integer port=21;
		//用户名,username
		String userName=tycx002FjPojo.getFtpusername();
		//密码,password
		String password=tycx002FjPojo.getFtppassword();
		//下载路径
		String downloadPath="";
		//文件名
		String fileName=tycx002FjPojo.getFjurl();
		String fileMc=tycx002FjPojo.getFj_mc();
		//文件保存路径
		String localPath="D:\\ftpdownload";

		//调用下载接口
		FtpFileUtil ftpFileUtil=new FtpFileUtil();
		String flag=ftpFileUtil.FTPDownload(ip,port,userName,password,downloadPath,fileName,fileMc,localPath);
		if(flag.equals("0")){
			resMap.put("result", true);
			resMap.put("msg", "文件下载成功！");
		}else{
			resMap.put("result", false);
			resMap.put("msg", "文件下载失败！");
		}
		resMap.put("JSONDATA", JsonUtil.toJson(resMap));
		resEvent.setResMap(resMap);
		return resEvent;
	}

	  private HashMap testLogin(UserContext ctx, HashMap resmap) {

		 Map userInfo=null;

    	 if(ctx!=null){
	  	    userInfo =	ctx.getUserinfo();
	  	    if(userInfo==null){
	  	    	resmap.put("errorinfo", "系统登陆失效请重新登陆！");
 	  	    }
	   	}else{
  	    	resmap.put("errorinfo", "系统登陆失效请重新登陆！");
 	   	}

    	 return resmap;

	}

	private String formetDate(){
		// 获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currDate=sdf.format(new Date());
		return currDate;
	}

	private String formetFileSize(long fileSize){
		DecimalFormat df=new DecimalFormat("#.00");
		String tempSize="";
		if(fileSize < 1024){
			tempSize=df.format(new BigDecimal(fileSize))+"B";
		}else if(fileSize < 1024*1024){
			tempSize=df.format(new BigDecimal(fileSize/1024))+"K";
		}else if(fileSize < 1024*1024*1024){
			tempSize=df.format(new BigDecimal(fileSize/1024/1024))+"M";
		}else{
			tempSize=df.format(new BigDecimal(fileSize/1024/1024/1024))+"G";
		}
		return tempSize;
	}

	/**
	 * 删除项目下保存的临时文件
	 * @param filePath
	 */
	private void deleteLocalFile(String filePath){
		File tempFile = new File(filePath);
		if (tempFile.exists()) {
			tempFile.delete();
		}
	}

	//上传成功后保存到数据库并删除项目下保存的文件
	public ResponseEvent savedWtfkFj(RequestEvent reqEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		HashMap<String, Object> reqMap = reqEvent.getRequestMap();

		String id =(String) reqEvent.getRequestMap().get("id");
		String fileName =(String) reqEvent.getRequestMap().get("fileName");
		String userName =(String) reqEvent.getRequestMap().get("userName");
		String password =(String) reqEvent.getRequestMap().get("password");
		String filePath =(String) reqEvent.getRequestMap().get("filePath");

		Tycx002FjPojo tycx002FjPojo=new Tycx002FjPojo();
		//配置附件信息
		//获取文件原名称
		String prefix=filePath.substring(filePath.lastIndexOf("\\")+1);
		tycx002FjPojo.setFj_id(fileName.substring(0,fileName.lastIndexOf(".")).replace("-", ""));
		tycx002FjPojo.setWtfk_id(id);
		tycx002FjPojo.setFj_mc(prefix);
		tycx002FjPojo.setFtpusername(userName);
		tycx002FjPojo.setFtppassword(password);
		//获取文件保存名称
		tycx002FjPojo.setFjurl(fileName);
		String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
		tycx002FjPojo.setFjgs(suffix);
		//计算文件大小
		File file=new File(filePath);
		long fileSize=file.length();

 		tycx002FjPojo.setFjdx(formetFileSize(fileSize));
		tycx002FjPojo.setXybj("1");
		String currDate=formetDate();
		tycx002FjPojo.setLr_sj(currDate);
		tycx002FjPojo.setXg_sj(currDate);
		tycx002FjPojo.setLrry_dm("23213000028");
		tycx002FjPojo.setLrjg_dm("23213000000");
		resMap.put("tycx002FjPojo", tycx002FjPojo);

		tycx002DzcxDao.insertFj(tycx002FjPojo);

		deleteLocalFile(filePath);

		HashMap map = new HashMap();
		map.put("mess", "保存成功");
		resMap.put("JSONDATA", JsonUtil.toJson(map));
		resEvent.setResMap(resMap);
		return resEvent;
	}

	public ResponseEvent openTS(RequestEvent reqEvent) throws Exception {
		logger.debug("debugger " + this.getClass().getName());
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		String sqlxh = (String) reqEvent.getRequestMap().get("sqlxh");
		String cxtjStr = (String) reqEvent.getRequestMap().get("cxtjStr");
		String fxyyid = (String) reqEvent.getRequestMap().get("fxyyid");
		String lcslh=(String) reqEvent.getRequestMap().get("lcslh");
		if(!TycxUtils.isEmpty(lcslh)){
			Tycx002TuisongPojo tycx002TuisongPojo=new Tycx002TuisongPojo();
			tycx002TuisongPojo.setLcslh(lcslh);
			List<Tycx002TuisongPojo> tsList=tycx002DzcxDao.queryTS(tycx002TuisongPojo);
			if(!TycxUtils.isEmpty(tsList)){
			resMap.put("tsList", tsList.get(0));
			}
		}
		resMap.put("fxyyid", fxyyid);
		resMap.put("sqlxh", sqlxh);
		resMap.put("cxtjStr", cxtjStr);
		resEvent.setResMap(resMap);
		resEvent.setFwordPath("/biz/core/ext/tycx/tycx002/jsp/tycx002Tuisong.jsp");
		return resEvent;
	}

	public ResponseEvent saveTS(RequestEvent reqEvent) throws Exception {
		logger.debug("debugger " + this.getClass().getName());
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();

		Tycx002TuisongPojo tycx002TuisongPojo=new Tycx002TuisongPojo(reqEvent.getRequestMap());
		tycx002TuisongPojo.setTs_id(UUID.randomUUID().toString().replace("-", ""));

		tycx002DzcxDao.insertTS(tycx002TuisongPojo);

		HashMap map = new HashMap();
		map.put("tsid", tycx002TuisongPojo.getTs_id());
		resMap.put("JSONDATA", JsonUtil.toJson(map));
		resEvent.setResMap(resMap);
		resEvent.setFwordPath("/biz/core/ext/tycx/tycx002/jsp/tycx002Tuisong.jsp");
		return resEvent;
	}

	public ResponseEvent saveTSImg(RequestEvent reqEvent) throws Exception {
		logger.debug("debugger " + this.getClass().getName());
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();

		Map map1=new HashMap();
		map1=reqEvent.getRequestMap();
		String tsid = (String) reqEvent.getRequestMap().get("tsid");
		CommonsMultipartFile file = (CommonsMultipartFile) reqEvent.getRequestMap().get("text");

//		byte[] buffer=null;
//		File file=new File(dataUrl);
//		FileInputStream fis=new FileInputStream(file);
//		ByteArrayOutputStream bos=new ByteArrayOutputStream();
//		byte[] bytes=new byte[1024];
//		int n;
//		while((n=fis.read(bytes)) != -1){
//			bos.write(bytes,0,n);
//		}
//		fis.close();
//		bos.close();
//		buffer=bos.toByteArray();
//		Tycx002TuisongPojo tycx002TuisongPojo=new Tycx002TuisongPojo();
//		tycx002TuisongPojo.setTs_id(tsid);
//		tycx002TuisongPojo.setWtimg(buffer);
//		tycx002DzcxDao.insertWtimg(tycx002TuisongPojo);

		HashMap map = new HashMap();
		map.put("mess", "保存成功");
		resMap.put("JSONDATA", JsonUtil.toJson(map));
		resEvent.setResMap(resMap);
		return resEvent;
	}

	public ResponseEvent tuisong(RequestEvent reqEvent) throws Exception {

		ResponseEvent resEvent = new ResponseEvent();

		String sqlxh = (String) reqEvent.getRequestMap().get("sqlxh");

		String queryStr = (String) reqEvent.getRequestMap().get("queryStr");

		String tzurl = "tycx/tykf/request_http?tld=Tycx002DzcxService_initView&noTj=1&sqlxh="+sqlxh+"&queryStr="+ queryStr;

//		tzurl = URLEncoder.encode(tzurl,"UTF-8");
		String url = "tycx/tykf/request_http?tld=fz013TsService_init&tzurl="+tzurl;

		resEvent.setFwordPath(url);
		resEvent.setFwordType(1);

		return resEvent;

	 }
	public ResponseEvent performTask(RequestEvent requestEvent) throws Exception {
		return runMethod(requestEvent,this);
	}

	public ResponseEvent performTaskLongTx(RequestEvent requestEvent) throws Exception {
		return runMethod(requestEvent,this);
	}

	public ResponseEvent performTaskNoTx(RequestEvent requestEvent) throws Exception {
		return runMethod(requestEvent,this);
	}

}

