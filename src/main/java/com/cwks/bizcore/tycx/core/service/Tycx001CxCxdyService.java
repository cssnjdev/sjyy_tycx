package com.cwks.bizcore.tycx.core.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizcore.tycx.core.dao.*;
import com.cwks.bizcore.tycx.core.mapping.pojo.*;
import com.cwks.bizcore.tycx.core.utils.Constant;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.bizcore.tycx.core.utils.tycxUtil;
import com.cwks.bizcore.tycx.core.vo.CXDzcxVO;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.dao.JdbcDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.*;

@Component
@Service("tycx001CxCxdyService")
public class Tycx001CxCxdyService  {

	private static Logger logger = LoggerFactory.getLogger(test.class);
	protected UserContext ctx;
	@Autowired
	private JdbcDao jdbcDao;
	com.cwks.bizcore.tycx.core.utils.tycxUtil tycxUtil = new tycxUtil();
	@Autowired
	private Tycx001CxCxdyDao tycx001CxCxdyDao;
	@Autowired
	private Tycx001CxCxjgdyDao tycx001CxCxjgdyDao;
	@Autowired
	private Tycx001CxCxtjdyDao tycx001CxCxtjdyDao;
	@Autowired
	private Tycx003CxTxpzxxDao tycx003CxTxpzxxDao;
	@Autowired
	private Tycx002DzcxDao tycx002DzcxDao;
	@Autowired
	private com.cwks.bizcore.tycx.core.dao.Tycx002CxCxzxxxDao Tycx002CxCxzxxxDao;

	public ResponseEvent init(RequestEvent requestEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> reqmap = new HashMap<String, Object>();

		resEvent.setFwordPath("biz/bizcore/sjyy/tycx/tycx001/html/index.html");
		ctx = requestEvent.getCtx();

		Map<String, Object> userInfo = new HashMap<String, Object>();

		if(ctx!=null){

			userInfo = ctx.getUserinfo();
			reqmap.put("userInfo",userInfo);

		}else{

			reqmap.put("hasLogin","0");
			resEvent.setResMap(reqmap);
			return resEvent;

		}
		List dbtypes=null;
		dbtypes = jdbcDao.queryforlist("select CSZ,CSM from cx_cs_csb where 1=1 and CSFL='CXLX' and XYBJ='Y'");;
		ArrayList cxlx = new ArrayList();
		for(int i=0;i<dbtypes.size();i++){
			Map map =(Map) dbtypes.get(i);
			Map codeMap=new HashMap();
			codeMap.put("code",map.get("CSZ"));
			codeMap.put("caption",map.get("CSM"));
			cxlx.add(codeMap);
		}
		dbtypes.clear();
		dbtypes = jdbcDao.queryforlist("select CSZ,CSM from cx_cs_csb where 1=1 and CSFL='YWFL' and XYBJ='Y'");;
		ArrayList ywfl = new ArrayList();
		for(int i=0;i<dbtypes.size();i++){
			Map map =(Map) dbtypes.get(i);
			Map codeMap=new HashMap();
			codeMap.put("code",map.get("CSZ"));
			codeMap.put("caption",map.get("CSM"));
			ywfl.add(codeMap);
		}
		reqmap.put("cxlxData", JsonUtil.toJson(cxlx).replace("\"", "'"));
		reqmap.put("ywflData", JsonUtil.toJson(ywfl).replace("\"", "'"));
		reqmap.put("ywflList", ywfl);
		reqmap.put("cxlxList", cxlx);
		resEvent.setFwordPath("biz/bizcore/sjyy/tycx/tycx001/html/tycx001Cxdy_main.html");
		resEvent.setResMap(reqmap);
		return resEvent;
	}


	public ResponseEvent initNew(RequestEvent requestEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		String sqlxh=(String) requestEvent.getRequestMap().get("sqlxh");
		HashMap<String, Object> reqmap = new HashMap<String, Object>();
		if(!tycxUtil.isEmpty(sqlxh)){
			Map map = tycx001CxCxdyDao.queryCxdy(sqlxh);
			Set<Map.Entry<String, String>> s=map.entrySet();
			for (Map.Entry<String, String> entry : s) {
				reqmap.put(entry.getKey(),entry.getValue());
			}
//			reqmap=(HashMap<String, Object>) tycx001CxCxdyDao.queryCxdy(sqlxh);
			Tycx001CxCxtjdyPojo cxtjPojo=new Tycx001CxCxtjdyPojo();
			cxtjPojo.setSqlxh(sqlxh);
			List cxtjlist=tycx001CxCxtjdyDao.select(cxtjPojo);
			reqmap.put("cxtjlist", cxtjlist);
		}

		String sjyStr=jdbcDao.getSql("SQL_CX_QUERYSJY");
		List sjyList=jdbcDao.queryforlist(sjyStr);

		List dbtypes=null;
		dbtypes = jdbcDao.queryforlist("select CSZ,CSM from cx_cs_csb where 1=1 and CSFL='CXLX' and XYBJ='Y'");;
		ArrayList cxlx = new ArrayList();
		for(int i=0;i<dbtypes.size();i++){
			Map map =(Map) dbtypes.get(i);
			Map codeMap=new HashMap();
			codeMap.put("code",map.get("CSZ"));
			codeMap.put("caption",map.get("CSM"));
			cxlx.add(codeMap);
		}
		dbtypes.clear();
		dbtypes = jdbcDao.queryforlist("select CSZ,CSM from cx_cs_csb where 1=1 and CSFL='YWFL' and XYBJ='Y'");;
		ArrayList ywfl = new ArrayList();
		for(int i=0;i<dbtypes.size();i++){
			Map map =(Map) dbtypes.get(i);
			Map codeMap=new HashMap();
			codeMap.put("code",map.get("CSZ"));
			codeMap.put("caption",map.get("CSM"));
			ywfl.add(codeMap);
		}
		dbtypes.clear();
		dbtypes = jdbcDao.queryforlist("select CSZ,CSM from cx_cs_csb where 1=1 and CSFL='XSGS' and XYBJ='Y'");;
		ArrayList xsgsNumList = new ArrayList();
		for(int i=0;i<dbtypes.size();i++){
			Map map =(Map) dbtypes.get(i);
			Map codeMap=new HashMap();
			codeMap.put("code",map.get("CSZ"));
			codeMap.put("caption",map.get("CSM"));
			xsgsNumList.add(codeMap);
		}
		dbtypes.clear();
		dbtypes = jdbcDao.queryforlist("select CSZ,CSM from cx_cs_csb where 1=1 and CSFL='XSGS_DATE' and XYBJ='Y'");;
		ArrayList xsgsDateList = new ArrayList();
		for(int i=0;i<dbtypes.size();i++){
			Map map =(Map) dbtypes.get(i);
			Map codeMap=new HashMap();
			codeMap.put("code",map.get("CSZ"));
			codeMap.put("caption",map.get("CSM"));
			xsgsDateList.add(codeMap);
		}

		dbtypes.clear();
		dbtypes = jdbcDao.queryforlist("select CSZ,CSM from cx_cs_csb where 1=1 and CSFL='TJ_DEFAULT_VALUES_DATE' and XYBJ='Y'");;
		ArrayList mrzList = new ArrayList();
		for(int i=0;i<dbtypes.size();i++){
			Map map =(Map) dbtypes.get(i);
			Map codeMap=new HashMap();
			codeMap.put("code",map.get("CSZ"));
			codeMap.put("caption",map.get("CSM"));
			mrzList.add(codeMap);
		}

		dbtypes.clear();
		dbtypes = jdbcDao.queryforlist("select CSZ,CSM from cx_cs_csb where 1=1 and CSFL='TJ_DEFAULT_VALUES_DM' and XYBJ='Y'");;
		ArrayList zdyList = new ArrayList();
		for(int i=0;i<dbtypes.size();i++){
			Map map =(Map) dbtypes.get(i);
			Map codeMap=new HashMap();
			codeMap.put("code",map.get("CSZ"));
			codeMap.put("caption",map.get("CSM"));
			zdyList.add(codeMap);
		}
		//获取 下钻参数 列表
		List<String> xzcsList=new ArrayList<String>();
		Map<String,String> xzcsMap=new HashMap<String,String>();
		Map<String,String> sjlmcMap=new HashMap<String,String>();
		Tycx001CxCxjgdyPojo tycx001CxCxjgdyPojo=new Tycx001CxCxjgdyPojo();
		tycx001CxCxjgdyPojo.setSqlxh(sqlxh);
		List<Tycx001CxCxjgdyPojo> cxjgList = tycx001CxCxjgdyDao.select(tycx001CxCxjgdyPojo);
		//首先获取列名称和上级列名称
		for(Tycx001CxCxjgdyPojo pojo:cxjgList){
			String lmc=pojo.getLmc();
			String sjlmc=pojo.getSjlmc();
			xzcsMap.put(lmc, lmc);
			if(tycxUtil.isEmpty(sjlmc)){
				sjlmcMap.put(sjlmc, sjlmc);
			}
		}
		//根据上级列名称删除列名称
		for(String value:sjlmcMap.values()){
			xzcsMap.remove(value);
		}
		//将最终列名称存入集合传递
		for(String value:xzcsMap.values()){
			xzcsList.add(value);
		}


		//TXZS
		dbtypes.clear();
		dbtypes = jdbcDao.queryforlist("select CSZ,CSM from cx_cs_csb where 1=1 and CSFL='TXZS' and XYBJ='Y'");;
		ArrayList txlx = new ArrayList();
		for(int i=0;i<dbtypes.size();i++){
			Map map =(Map) dbtypes.get(i);
			Map codeMap=new HashMap();
			codeMap.put("code",map.get("CSZ"));
			codeMap.put("caption",map.get("CSM"));
			txlx.add(codeMap);
		}
		String tjlxDatas = "[{'code':'0','caption':'不统计'},{'code':'1','caption':'分组列'},{'code':'2','caption':'统计列-求和'},{'code':'3','caption':'统计列-平均'},{'code':'4','caption':'统计列-最大值'},{'code':'5','caption':'统计列-最小值'}]";
		String llxData = "[{'code':'VARCHAR2','caption':'字符型'},{'code':'DATE','caption':'日期型'},{'code':'NUMBER','caption':'数值型'},{'code':'SELECT','caption':'单选'},{'code':'MULTI','caption':'多选'},{'code':'SINGLETREE','caption':'单选树'},{'code':'TREE','caption':'多选树'}]";
		String dqfsData = "[{'code':'0','caption':'居左'},{'code':'1','caption':'居中'},{'code':'2','caption':'居右'}]";
		reqmap.put("tjlxData",JsonUtil.toJson(tjlxDatas).replace("\"[", "[").replace("]\"", "]"));
		reqmap.put("llxData",JsonUtil.toJson(llxData).replace("\"[", "[").replace("]\"", "]"));
		reqmap.put("dqfsData",JsonUtil.toJson(dqfsData).replace("\"[", "[").replace("]\"", "]"));
		reqmap.put("cxlxData", JsonUtil.toJson(cxlx).replace("\"", "'"));
		reqmap.put("ywflData", JsonUtil.toJson(ywfl).replace("\"", "'"));
		reqmap.put("txlxData", JsonUtil.toJson(txlx).replace("\"", "'"));
		reqmap.put("ywflList", ywfl);
		reqmap.put("cxlxList2", cxlx);
		reqmap.put("sjyList", sjyList);
		reqmap.put("txlxList", txlx);
		reqmap.put("xsgsNumList", xsgsNumList);
		reqmap.put("xsgsDateList", xsgsDateList);
		reqmap.put("xzcsList", xzcsList);
		reqmap.put("mrzList", mrzList);
		reqmap.put("zdyList", zdyList);
		resEvent.setResMap(reqmap);

		resEvent.setFwordPath("biz/bizcore/sjyy/tycx/tycx001/html/tycx001CxCxdyManager.html");

		return resEvent;
	}
	/**
	 * 初始化结果列
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent initResult(RequestEvent requestEvent) throws Exception {
		logger.debug("debugger "+this.getClass().getName()+"_initResult");
		ResponseEvent resEvent = new ResponseEvent();
		String sqlxh=(String) requestEvent.getRequestMap().get("sqlxh");
		//参数注入
		Tycx001CxCxdyPojo tycx001CxCxdyPojo = new Tycx001CxCxdyPojo();
		tycx001CxCxdyPojo.setSqlxh(sqlxh);
		Tycx001CxCxdyPojo cxdyPojo=tycx001CxCxdyDao.selectByPKey(tycx001CxCxdyPojo);
		String sqlstr=cxdyPojo.getSqlstr();
		String sjymc=cxdyPojo.getSjymc();
		CXDzcxVO dzcxVO =new CXDzcxVO();
		dzcxVO.setSql(sqlstr);
		dzcxVO.setSjymc(sjymc);
		dzcxVO=this.createSql(dzcxVO);
		String sql=dzcxVO.getSql();
		// String sjymc=dzcxVO.getSjymc();
		List resultList=tycx001CxCxdyDao.initResultColumns(sqlxh,sql, sjymc);
		HashMap reqmap = new HashMap();
		reqmap.put("JSONDATA", JsonUtil.toJson(resultList));
		resEvent.setResMap(reqmap);
		return resEvent;
	}
	/**
	 * 初始化查询条件
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent initCxtj(RequestEvent requestEvent) throws Exception {
		logger.debug("debugger "+this.getClass().getName()+"_initResult");
		ResponseEvent resEvent = new ResponseEvent();
		String sqlxh=(String) requestEvent.getRequestMap().get("sqlxh");
		//参数注入
		Tycx001CxCxdyPojo tycx001CxCxdyPojo = new Tycx001CxCxdyPojo();
		tycx001CxCxdyPojo.setSqlxh(sqlxh);
		Tycx001CxCxdyPojo cxdyPojo=tycx001CxCxdyDao.selectByPKey(tycx001CxCxdyPojo);

		String sqlstr=cxdyPojo.getSqlstr();
		String sjymc=cxdyPojo.getSjymc();
		String procname=cxdyPojo.getCcgcmc();
		String cxlx=String.valueOf(cxdyPojo.getCxlx());
		Boolean isBB=false;
		if("4".equals(cxlx)){
			isBB=true;
		}
		//查询已有查询条件
		List resultList=tycx001CxCxdyDao.initCxtj(sqlxh, sqlstr, sjymc, procname, isBB);
//        HashMap reqmap = new HashMap();
//        reqmap.put("JSONDATA", JsonUtil.toJson(resultList));
//        resEvent.setResMap(reqmap);
		return resEvent;
	}
	public ResponseEvent selectCxdy(RequestEvent requestEvent) throws Exception {
		logger.debug("debugger "+this.getClass().getName()+"_select");
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		//参数注入
		Tycx001CxCxdyPojo tycx001CxCxdyPojo = new Tycx001CxCxdyPojo(requestEvent.getRequestMap());
		//设置分页
		String page = requestEvent.getRequestMap().get("page").toString();
		requestEvent.getRequestMap().put("pageNum",page);
		requestEvent.getRequestMap().put("pageSize",requestEvent.getRequestMap().get("rows").toString());

        PageHelper.startPage(requestEvent.getRequestMap());
		List<Tycx001CxCxdyPojo> list = tycx001CxCxdyDao.selectCxdy(tycx001CxCxdyPojo);
		PageInfo<Tycx001CxCxdyPojo> pages = new PageInfo<Tycx001CxCxdyPojo>(list);
		reqmap.put("JSONDATA", TycxUtils.toJsonForJqGrid(pages));
		resEvent.setResMap(reqmap);
		return resEvent;
	}

	public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
		logger.debug("debugger "+this.getClass().getName()+"_expExcel");
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		//参数注入
		Tycx001CxCxdyPojo tycx001CxCxdyPojo = new Tycx001CxCxdyPojo(requestEvent.getRequestMap());
		String page = (String)requestEvent.getRequestMap().get("page");
		List<Tycx001CxCxdyPojo> expList = null;
		if(!tycxUtil.isEmpty(page) ){
			PageHelper.startPage(requestEvent.getRequestMap());
			PageInfo<Tycx001CxCxdyPojo> pages = new PageInfo<Tycx001CxCxdyPojo>(tycx001CxCxdyDao.selectCxdy(tycx001CxCxdyPojo));
			expList = pages.getList();
		}else{
			expList = tycx001CxCxdyDao.selectCxdy(tycx001CxCxdyPojo);
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
		dataMap.put("fileName","通用查询");
		dataMap.put("class",Tycx001CxCxdyPojo.class);
		dataMap.put("colMap",colMap);
		dataMap.put("listContent",expList);
		reqmap.put("ExcelMap",dataMap);
		resEvent.setResMap(reqmap);
		return resEvent;
	}

	public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
		logger.debug("debugger "+this.getClass().getName()+"_deleteByPKey");
		ResponseEvent resEvent = new ResponseEvent();
		Tycx001CxCxdyPojo pojo = null;
//        for(int i=0;i<pkid.length;i++){
//            pojo = new Tycx001CxCxdyPojo();
//            tycx001CxCxdyDao.deleteByPKey(pojo);
//        }
		HashMap reqmap = new HashMap();
		reqmap.put("JSONDATA", RJson.DELETE_TRUE);
		resEvent.setResMap(reqmap);
		return resEvent;
	}

	public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
		logger.debug("debugger "+this.getClass().getName()+"_insertSelective");
		ResponseEvent resEvent = new ResponseEvent();
		ctx = requestEvent.getCtx();
		Map<String, Object> userInfo = new HashMap<String, Object>();

		if(ctx!=null){
			userInfo = ctx.getUserinfo();
		}
		String lrrq=TycxUtils.getNow();
		Tycx001CxCxdyPojo tycx001CxCxdyPojo = new Tycx001CxCxdyPojo(requestEvent.getRequestMap());
		HashMap hasmap=requestEvent.getRequestMap();
		String gnan="";
		ArrayList list=new ArrayList();
		String jglsz=(String)hasmap.get("jglsz");
		list.add(jglsz);
		String xq=(String)hasmap.get("xq");
		list.add(xq);
		String sc=(String)hasmap.get("sc");
		list.add(sc);
		String pj=(String)hasmap.get("pj");
		list.add(pj);
		String fk=(String)hasmap.get("fk");
		list.add(fk);
		String ts=(String)hasmap.get("ts");
		list.add(ts);
		String fx=(String)hasmap.get("fx");
		list.add(fx);
		String gb=(String)hasmap.get("gb");
		list.add(gb);
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null){
				gnan=gnan+list.get(i)+";";
			}
		}

		String sqlxh=tycx001CxCxdyPojo.getSqlxh();
		if (tycxUtil.isEmpty(sqlxh)) {
			String ywy = tycx001CxCxdyPojo.getYwy();
			String sqlstr = jdbcDao.getSql("SQL_CX_QUERYSEQUERY");
			Map<String, Object> map = tycx001CxCxdyDao.querySQL(sqlstr);
			String tempXh = String.valueOf(map.get("NEXTVAL"));
			sqlxh = TycxUtils.getNewSqlxh(tempXh, ywy);
			tycx001CxCxdyPojo.setSqlxh(sqlxh);
			tycx001CxCxdyPojo.setLrr_dm((String)userInfo.get("userId"));
			tycx001CxCxdyPojo.setLrrq(lrrq);
			tycx001CxCxdyPojo.setSjgsdq((String)userInfo.get("swrysfjg"));
			tycx001CxCxdyPojo.setXybz("Y");
			tycx001CxCxdyPojo.setXsgnan(gnan);
			tycx001CxCxdyPojo.setFybj("Y");
			tycx001CxCxdyPojo.setFwlj(ctx+"tycx/tykf/request_http?tld=Tycx002DzcxService_initView&sqlxh="+sqlxh);

			tycx001CxCxdyDao.insertSelective(tycx001CxCxdyPojo);
		}else{
			tycx001CxCxdyPojo.setXsgnan(gnan);
			tycx001CxCxdyPojo.setFybj("Y");
			tycx001CxCxdyDao.updateByPKeySelective(tycx001CxCxdyPojo);
		}

		HashMap<String,String> JSONDATA = new HashMap<String, String>();
		JSONDATA.put("sqlxh", sqlxh);
		JSONDATA.put("success", "1");

		HashMap reqmap = new HashMap();
		reqmap.put("JSONDATA", JSONUtils.toJSONString(JSONDATA));
		// resEvent.setResMsg(RJson.INSERT_TRUE);
		resEvent.setResMap(reqmap);
		return resEvent;
	}
	public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		String sqlxhs=(String) requestEvent.getRequestMap().get("sqlxh");
		if(!tycxUtil.isEmpty(sqlxhs)){
			String[] sqlxhArr=sqlxhs.split(",");
			Tycx001CxCxdyPojo tycx001CxCxdyPojo = new Tycx001CxCxdyPojo();
			for(int i=0;i<sqlxhArr.length;i++){
				tycx001CxCxdyPojo.setSqlxh(sqlxhArr[i]);
				tycx001CxCxdyPojo.setXybz("N");
				tycx001CxCxdyDao.updateByPKeySelective(tycx001CxCxdyPojo);
			}
		}
		HashMap reqmap = new HashMap();
		reqmap.put("JSONDATA", RJson.UPDATE_TRUE);
		resEvent.setResMap(reqmap);
		return resEvent;
	}
	//删除
	public ResponseEvent deleteByPKeySelective(RequestEvent requestEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		String sqlxhs=(String) requestEvent.getRequestMap().get("sqlxh");
		String uuids=(String) requestEvent.getRequestMap().get("uuid");
		if(!tycxUtil.isEmpty(sqlxhs)){
		String[] sqlxh= sqlxhs.split(",");
		String[] uuid= uuids.split(",");
		Tycx003CxTxpzxxPojo tycx003CxTxpzxxPojo = new Tycx003CxTxpzxxPojo();
			for(int i =0;i<sqlxh.length;i++){
				tycx003CxTxpzxxPojo.setSqlxh(sqlxh[i]);
				tycx003CxTxpzxxPojo.setUuid(uuid[i]);
				tycx003CxTxpzxxDao.deleteByPKeySelective(tycx003CxTxpzxxPojo);
			}
		}
		HashMap reqmap = new HashMap();
		reqmap.put("JSONDATA", RJson.UPDATE_TRUE);
		resEvent.setResMap(reqmap);
		return resEvent;
	}
	public ResponseEvent checkSqlAndBbmc(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		HashMap map=requestEvent.getRequestMap();
		ctx = requestEvent.getCtx();
		Map userInfo=null;
		if(ctx!=null){
			userInfo =	ctx.getUserinfo();
		}
		String sqlstr =(String)map.get("sqlstr");
		String sjylx=(String)map.get("sjylx");
		String sqlxh=(String) map.get("sqlxh");
		String sjymc=tycx001CxCxdyDao.getSjymc(sjylx);
		HashMap reqmap = new HashMap();
		CXDzcxVO dzcxVo = new CXDzcxVO();
		dzcxVo.setSql(sqlstr);
		dzcxVo.setSjymc(sjymc);
		dzcxVo = createSql(dzcxVo);
		String sql="select * from ("+dzcxVo.getSql()+") where rownum=1";
		dzcxVo.setSql(sql);
		Tycx002CxCxzxxxPojo Tycx002CxCxzxxxPojo = new Tycx002CxCxzxxxPojo();
		String rzuuid = UUIDGenerator.getUUID();
		long sl = new Date().getTime();
		InetAddress ia=InetAddress.getLocalHost();
		String ip = "";
		if(ia.getHostAddress()!=null){
			ip = ia.getHostAddress().toString();
		}
		String address=ia.getHostName().toString();
		Tycx002CxCxzxxxPojo.setUuid(rzuuid);
		Tycx002CxCxzxxxPojo.setSqlxh(sqlxh);
		Tycx002CxCxzxxxPojo.setSqlstr(dzcxVo.getSql());
		Tycx002CxCxzxxxPojo.setCxsj(TycxUtils.getNow());
		Tycx002CxCxzxxxPojo.setCzry_dm((String)userInfo.get("userId"));
		Tycx002CxCxzxxxPojo.setLrr_dm((String)userInfo.get("userId"));
		Tycx002CxCxzxxxPojo.setLrrq(TycxUtils.getNow());
		Tycx002CxCxzxxxPojo.setCxy("9");//查询源
		Tycx002CxCxzxxxPojo.setSjgsdq((String)userInfo.get("swrysfjg"));
		//Tycx002CxCxzxxxPojo.setSessionid(sessionid);
		Tycx002CxCxzxxxPojo.setFwip(ip+ Constant.rowFG+address);
		Tycx002CxCxzxxxPojo.setThreadid(Thread.currentThread().getName());


		try{
			List list = tycx002DzcxDao.executeSql(dzcxVo);
			if(list.size()>0){//没数据会验证失败
				reqmap.put("JSONDATA", JsonUtil.toJson("验证成功"));
			}else{
				reqmap.put("JSONDATA", JsonUtil.toJson("验证失败:未查询到数据!:"));
			}
		}catch(Exception e){
			logger.debug("========"+e);
			Tycx002CxCxzxxxPojo.setSqlstr(e.toString());
			reqmap.put("JSONDATA", JsonUtil.toJson("验证失败:"+e));
		}

		Tycx002CxCxzxxxDao.insertSelective(Tycx002CxCxzxxxPojo);
		resEvent.setResMap(reqmap);
		return resEvent;
	}
	public ResponseEvent selectByPKey(RequestEvent requestEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		return resEvent;
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
	public CXDzcxVO createSql(CXDzcxVO dzcxVO) throws Exception{
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
		if (Constant.queryType_TJ.equals(dzcxVO.getQueryType())
				&& tycxUtil.isEmpty(dzcxVO.getQueryType_Tj_bj())) {
			sql = TycxUtils.addWdAndzbToSql(sql, dzcxVO.getWd(), dzcxVO.getZb(), null);
			dzcxVO.setSql(sql);
		}
		return dzcxVO;
	}
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
	public CXDzcxVO getExecuteSql(CXDzcxVO dzcxVO)  {
		//JSONArray tjJSONArr = null;
		final String queryParams = dzcxVO.getQueryParams();
		String sqlStr = dzcxVO.getSql().toUpperCase();
		if (!tycxUtil.isEmpty(queryParams)) {
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
				if ((!tycxUtil.isEmpty(valueStr)) && valueStr.indexOf(",") > 0) {
					valueStr = "'" + valueStr.replaceAll(",", "','") + "'";
				}
				// 替换SQL中@nameStr@
				if (!tycxUtil.isEmpty(valueStr)) {
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
							sqlStr = sqlStr.replace(str, "");// 替换{......@@.....}为""
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
		String real_sql = sqlArr[0];
		if (sqlArr.length > 1) {
			for (int i = 1; i < sqlArr.length; i++) {
				String[] sqlArr_mx = sqlArr[i].split("\\]");
				for (int j = 0; j < sqlArr_mx.length; j++) {
					if (sqlArr_mx[j].indexOf("@") < 0) {
						// 处理LIKE条件,20120601，林全加
						sqlArr_mx[j] = sqlArr_mx[j].replaceAll(
								"\\s+[Ll][Ii][Kk][Ee]\\s+", " LIKE ");// 关键字LIKE改为大写
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
						}
						real_sql = real_sql + sqlArr_mx[j];
					} else {
						real_sql = real_sql + " 1=1 ";
					}
				}
			}
		}

		real_sql = real_sql.replaceAll(
				"\\[[^\\[|\\]]*@[^\\[|\\]]*@(.*?)[^\\[|\\]]*\\]", " 1 = 1 ");
		real_sql = real_sql.replaceAll("\\[|\\]", "");
		real_sql = real_sql.replaceAll("@(.*?)@", "''");
		// 用正则表达式删除掉1=1条件,经典，林全，2012-03-31，山重水复疑无路，柳暗花明又一村
		real_sql = real_sql.replaceAll("AND\\s+1=1", " ").replaceAll(
				"OR\\s+1=1", " ").replaceAll("\\s+", " ");
		// 去掉所有的"["和"]"
		real_sql = real_sql.replaceAll("\\[", "").replaceAll("\\]", "");
		// 去掉空的(),((()))
		real_sql = real_sql.replaceAll("\\(+\\s+\\)+", "");
		// 去掉SQL中的{}，暂时不会考虑结果变量为空的情况 add by lxn 20141101
		real_sql = real_sql.replaceAll("\\{", "");
		real_sql = real_sql.replaceAll("\\}", "");
//		// 如果headSQL非空
//		if (!tycxUtil.isEmpty(Tycx001CxCxdyPojo.getHeadsql())) {
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

		dzcxVO.setSql(real_sql);
		return dzcxVO;
	}

}