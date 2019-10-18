package com.cwks.bizcore.etl001.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizcore.daoUtil.CssnjDao;
import com.cwks.bizcore.persistence.SpManager;
import com.cwks.bizcore.persistence.outtype.BaseOutParam;
import com.cwks.bizcore.sys.forder.dao.Sys001FolderDao;
import com.cwks.bizcore.tycx.core.dao.Etl001ZyglDao;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.core.cache.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("etl001ZyglService")
public class Etl001ZyglService  {

	private static Logger logger = LoggerFactory.getLogger(Etl001ZyglService.class);

	@Autowired
	private CssnjDao jdbcDao;
	
	@Autowired
    private Etl001ZyglDao etl001ZyglDao ;

	@Autowired
	private Sys001FolderDao folderDao;
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws PersistenceCheckedException
	 * @throws SQLException
	 * 初始化作业管理主界面
	 * 
	 */
	public ResponseEvent init(RequestEvent requestEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
 		
		ArrayList<HashMap<String, String>> yhdwlist = new ArrayList<HashMap<String, String>>();
 	 
    	HashMap<String, Object> resmap = new HashMap<String, Object>();

		List etltypes = CacheUtil.getCodeTable("DM_GY_ETL_TYPE", "XYBJ='1'") ;
     	
    	ArrayList etltypesJSON = new ArrayList();
    	
    	for(int i=0;i<etltypes.size();i++){
    		Map map =(Map) etltypes.get(i);
    		map.put("code",map.get("etllxDm")); 
    		map.put("caption",map.get("mc"));
    		etltypesJSON.add(map);
		}
    	
    	resmap.put("etltypesJSON", JsonUtil.toJson(etltypesJSON).replace("\"", "'"));
 
    	resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/zyglManager.jsp");
		return resEvent;
		
	}
	
	 public ResponseEvent searchMl(RequestEvent requestEvent) throws Exception {
	    	
	    	ResponseEvent resEvent = new ResponseEvent();
	    	HashMap<String, Object> resmap = new HashMap<String, Object>();
	    	
	    	List jgsList = folderDao.searchFolderByType("05");
	        List leftList = new ArrayList();
	        
	        HashMap<String,Object> map = new HashMap<String, Object>();
			for (int i=0;i<jgsList.size();i++){
				map = (HashMap<String, Object>) jgsList.get(i);
				map.put("NAMEC", map.get("NAME").toString()+"("+map.get("COUNT").toString()+")");
				if(map.get("PID")==null){
					map.put("open", "true");
				}
				
				leftList.add(map);
			}
	        
	    	HashMap<String,Object> data = new HashMap<String, Object>();
	    	data.put("nodes", jgsList);
	    	data.put("lnodes",leftList);
	    	
	    	resmap.put("JSONDATA", JsonUtil.toJson(data));
	    	resEvent.setResMap(resmap);
	    	
	    	return resEvent;
	}
  

	/**
     * 保存目录
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent saveMl(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
 
    	HashMap<String,Object> reqmap = requestEvent.getRequestMap();
    	
    	String mldm = (String)reqmap.get("mldm");
    	String mlmc = (String)reqmap.get("mlmc");
    	String sjml = (String)reqmap.get("sjml");
    	String pxxh = (String)reqmap.get("pxxh");

    	String swry_dm = "";
    	UserContext ctx = requestEvent.getCtx();
    	if(ctx!=null){
 	    	Map userInfo =	ctx.getUserinfo();
 	    	swry_dm =(String)userInfo.get("userId");
  	    }

    	HashMap<String, Object> resmap = new HashMap<String, Object>();
    	HashMap<String,Object> data = new HashMap<String, Object>();
    	
    	try{
    		if(TycxUtils.isEmpty(mldm) ){ // 插入新分类
        		mldm = UUIDGenerator.getUUID();
        		folderDao.createFolder(mldm, mlmc, sjml, swry_dm, pxxh, "05");
        	}else{ //更新分类
        		folderDao.updateFolder(mldm, mlmc, sjml, swry_dm, pxxh, "05");
        	}
    	   data.put("success", "1");
    	}catch (Exception e) {
    		data.put("success", "0");
		}
    	
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
	 	public ResponseEvent searchEtlList(RequestEvent requestEvent)throws Exception {
	 		
			ResponseEvent resEvent = new ResponseEvent();
			
			HashMap<String, Object> resmap = new HashMap<String, Object>();
	    	 
			HashMap<String, Object> reqmap = requestEvent.getRequestMap();
			
			String etl_fl = (String) reqmap.get("etl_fl");
			String etl_mc = (String) reqmap.get("etl_mc");
			String cjsj_q = (String) reqmap.get("cjsj_q");
			String cjsj_z = (String) reqmap.get("cjsj_z");
	 		
			String start_str = (String) reqmap.get("start");
			String length    = (String) reqmap.get("length");
			String total_str = (String) reqmap.get("total");
			

	    	if("ALL".equals(etl_fl)){
	    		etl_fl="";
	    	}
			
	    	Integer endNum = Integer.parseInt(start_str) + Integer.parseInt(length);
			Integer startNum = Integer.parseInt(start_str);
	    	
			HashMap<String,Object> JSONDATA = etl001ZyglDao.searchEtlList(etl_fl, etl_mc, cjsj_q, cjsj_z, endNum, startNum, total_str,"01");
	    	
	    	resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
	    	
	    	resEvent.setResMap(resmap);
	    	
			return resEvent;

	 }
		 
	
	public ResponseEvent openEtl(RequestEvent requestEvent)throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String, Object> resmap = new HashMap<String, Object>();
		
		String folderid = (String) requestEvent.getRequestMap().get("folderid");
		String etl_id = (String) requestEvent.getRequestMap().get("etl_id");
		String flag = (String) requestEvent.getRequestMap().get("flag");
		
		if(!"1".equals(flag)){
			flag="0";
		}
		resmap.put("flag",flag);
		
		if (etl_id == null || etl_id.equals("")) {
			
			String cjrmc = "";
			
			UserContext ctx = requestEvent.getCtx();
	    	if(ctx!=null){
	 	    	Map userInfo =	ctx.getUserinfo();
	 	    	cjrmc =(String)userInfo.get("swry_mc");
	  	    }
	    	
			Date d = new Date();

	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    	
			String cjrq = formatter.format(d);
			
			resmap.put("cjrmc", cjrmc);
			resmap.put("cjrq", cjrq);
			
			if("ALL".equals(folderid)|| TycxUtils.isEmpty(folderid)){
				folderid = "05";
			}
			 
			
			resmap.put("folderid", folderid);
		
		    Map map = etl001ZyglDao.getDefZzjgfw(null);
		
		    resmap.put("zzjgfwdm", map.get("zzjgfwdm"));
			
			resmap.put("zzjgfwmc", map.get("zzjgfwmc"));
		    
		}else {
			
			resmap.put("etlid", etl_id);
			 
			Map map = etl001ZyglDao.searchEtlById(etl_id);
			
			if(map!=null){
				
				resmap.put("etldm", map.get("etldm"));
				resmap.put("etlmc", map.get("etlmc"));
				resmap.put("etllx", map.get("etllx"));
				
				resmap.put("cjrmc", map.get("cjrmc"));
				resmap.put("cjrq", map.get("cjrq"));
				
				resmap.put("ms", map.get("ms"));
				resmap.put("xybj",map.get("xybj"));
 				
				resmap.put("proc_dm", map.get("proc_dm"));
				resmap.put("proc_db", map.get("proc_db"));
				
				resmap.put("sjjgfw", map.get("sjjgfw"));
 				
				resmap.put("folderid", map.get("folder_id"));
				
				resmap.put("zzjgfwdm", map.get("zzjgfwdm"));
				
				resmap.put("zzjgfwmc", map.get("zzjgfwmc"));
				
			}
			
		} 
		
		
		List etltypes = CacheUtil.getCodeTable("DM_GY_ETL_TYPE", "XYBJ='1'") ; //etl加工类型
		
		resmap.put("etltypes", etltypes);

		
		List dsList = CacheUtil.getCodeTable("SYS_DATASOURCE", "IS_VALID='Y'") ; //数据库
		
		resmap.put("dsList",dsList);
		 
		List sjfklist =  etl001ZyglDao.sjjgfw() ; //时间加工范围
		resmap.put("sjfklist",sjfklist);
 
		
		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/zyglEdit.jsp");
		
		return resEvent;
		
	}

	
    /**
     * ETL管理保存基本信息
     * @param req
     * @return
     * @throws PersistenceCheckedException
     * @throws SQLException
     */ 
	
	public ResponseEvent etlSave(RequestEvent requestEvent)throws Exception {
		
			ResponseEvent resEvent = new ResponseEvent();
			
			HashMap<String, Object> resmap = new HashMap<String, Object>();
	    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
			
			String etlmc = (String) requestEvent.getRequestMap().get("etlmc");
			
			String etlbk = (String) requestEvent.getRequestMap().get("etlbk");
			
			String etllx = (String) requestEvent.getRequestMap().get("etllx");
			String folderid = (String) requestEvent.getRequestMap().get("folderid");
			String cjrq = (String) requestEvent.getRequestMap().get("cjrq");
			String cjrmc = (String) requestEvent.getRequestMap().get("cjrmc");
			String etlid    = (String) requestEvent.getRequestMap().get("etlid");
			String ms = (String) requestEvent.getRequestMap().get("ms");
			String xybj = (String) requestEvent.getRequestMap().get("xybj");
 			
			String proc_dm = (String) requestEvent.getRequestMap().get("proc_dm");
			String proc_db = (String) requestEvent.getRequestMap().get("proc_db");
			
			String sjjgfw = (String) requestEvent.getRequestMap().get("sjjgfw");
 			
			ms = ms.replaceAll("@@", "\r").replaceAll("##", "\n").replaceAll("&&", "\t");
 
			String czrydm = "";
			
			UserContext ctx = requestEvent.getCtx();
	    	if(ctx!=null){
	 	    	Map userInfo =	ctx.getUserinfo();
	 	    	czrydm =(String)userInfo.get("userId");
	 	    	cjrmc=(String)userInfo.get("swry_mc");
	  	    }
	    	
	    	String proc_type = this.getProType(proc_dm); // 一般测试过程
	    	
	    	int i=0;
			
	    	ArrayList<Object> param = new ArrayList<Object>();
	    	
			if (etlid == null || etlid.equals("")) { // 新建etl
				
				String getSeq = "  SELECT PKG_PUB_FUN.FUN_GET_ETL_COMM_SEQ ETLID FROM DUAL ";
				Map mapE = jdbcDao.queryformap(getSeq);
				etlid = mapE.get("ETLID").toString();
				etl001ZyglDao.createEtl(etlid, etlmc, ms, etllx, cjrmc, cjrq, folderid, czrydm, xybj, proc_dm, proc_db, proc_type, sjjgfw, "01");
			
			}else{ // 更新etl
				
				// 修改
				etl001ZyglDao.updateEtl(etlid, etlmc, ms, etllx, cjrmc, folderid, czrydm, xybj, proc_dm, proc_db, proc_type, sjjgfw, "01");
			
			}
	    	
			JSONDATA.put("success", "1");
			JSONDATA.put("etlid", etlid);
 			resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
        	resEvent.setResMap(resmap);
        	
			return resEvent;
			 
	}

	/**
	 * etl 数据单元页面
	 */
	public ResponseEvent etlSjdy(RequestEvent requestEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		 
		HashMap<String, Object> resMap = new HashMap<String, Object>();

		String etlid = (String) requestEvent.getRequestMap().get("etlid");
		
		if(TycxUtils.isEmpty(etlid)){
			resMap.put("errorinfo", "请先保存数据单元基本信息！");
			resEvent.setFwordPath("/public/js/common/jsp/error.jsp");
			return resEvent;
		}
		 
		String flag = (String) requestEvent.getRequestMap().get("flag");
		
		if(!"1".equals(flag)){
			flag="0";
		}
		resMap.put("flag",flag);
		
		String procxtbj = this.getProTypeByEtl(etlid) ;
		
 		resMap.put("etlid", etlid);
		resMap.put("procxtbj", procxtbj);
		
		resEvent.setResMap(resMap);
		
		if("1".equals(procxtbj)){ //存储过程属于回流的 跳转到回流的数据单元管理页面
		
			resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/zygl_Unit_U.jsp");
		
		}else{
			
			resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/zygl_Unit_P.jsp");
			
		}
		
		return resEvent;

	}
	 
	public ResponseEvent searchEtlDuSql(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String, Object> resmap = new HashMap<String, Object>();
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
    	
    	String etl_id = (String) requestEvent.getRequestMap().get("etl_id");
		String dataunit_id = (String) requestEvent.getRequestMap().get("dataunit_id");
    	
    	String sql = "  select pxxh,dmlsql sql,dml_type sql_type,nvl(dmlsql_is_valid,'N') dmlsql_is_valid,test_msg from etl_def_dataunit_dmlsql t where etl_id = ? and dataunit_id = ? ";
    	
    	ArrayList<Object> param = new ArrayList<Object>();
    	
    	param.add(etl_id);
    	param.add(dataunit_id);
    	
    	List list = jdbcDao.queryforlist(sql,param);
    	
    	JSONDATA.put("data", list);
    	JSONDATA.put("success", "1");
    	
    	resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resmap);
		
		return resEvent;
		
	}
	
	/**
	 * 重新创建脚本
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent cxcjjb(RequestEvent requestEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		
 		HashMap<String,Object> resmap   = new HashMap<String,Object>();
    	HashMap<String,Object> JSONDATA = new HashMap<String,Object>();
		
		String datastr = (String) requestEvent.getRequestMap().get("datastr");
  
		String czrydm = "";
		String swjgdm = "";
		UserContext ctx = requestEvent.getCtx();
    	if(ctx!=null){
 	    	Map userInfo =	ctx.getUserinfo();
 	    	czrydm =(String)userInfo.get("userId");
 	    	swjgdm =(String)userInfo.get("swjg_dm");
   	    }
    	
    	String success = "0";
		String message = "保存异常！";
    	
    	try{
    	
    		ArrayList<Object> list = new ArrayList<Object>();
    		BaseOutParam outCode = new BaseOutParam(); // 成功返回 1 ,失败返回0
    		BaseOutParam outMessage = new BaseOutParam(); // 返回操作 信息
    		
    		String[] dataArr = datastr.split(","); 
			for(int i=0;i<dataArr.length;i++){
				
				list = new ArrayList<Object>();
				
				String data =dataArr[i];
				String[] p = data.split("-");
		 
				list.add(p[0]);
				list.add(p[1]);
				list.add("");
				list.add(czrydm);
				list.add(swjgdm);
				list.add(outCode);
				list.add(outMessage);

				SpManager.callProc(etl001ZyglDao.getConnection(), "PKG_DATAUNIT_TRANSACITON.P_ETL_SQL_BY_DATAUNIT_ID", list);
				
			}
		
			success = "1";
			message = "保存成功！";
			
    	}catch (Exception e) {

    		success = "0";
    		message = e.getMessage();
     		
		}
		
		
		JSONDATA.put("success", "1");
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);
		return resEvent;
		
 	}
	/**
	 * 校验sql脚本 
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 * 
	 */
	public ResponseEvent jySql(RequestEvent requestEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		Map reqMap = requestEvent.getRequestMap();
		String etl_id = (String)reqMap.get("etl_id");
		String dataunit_id = (String)reqMap.get("dataunit_id");
		 
		HashMap<String, Object> resmap = new HashMap<String, Object>();

		//pxxh++;
 			
		//etl001ZyglDao.insertZyUnit(etl_id, dataunit_id, czrydm, pxxh);
		
		String czrydm = "";
		String swjgdm = "";
		UserContext ctx = requestEvent.getCtx();
    	if(ctx!=null){
 	    	Map userInfo =	ctx.getUserinfo();
 	    	czrydm =(String)userInfo.get("userId");
 	    	swjgdm =(String)userInfo.get("swjg_dm");
   	    }
    	
    	String success = "0";
		String message = "校验异常！";
		ArrayList<Object> list = new ArrayList<Object>();

		try{
			
			BaseOutParam outCode = new BaseOutParam(); // 成功返回 1 ,失败返回0
			BaseOutParam outMessage = new BaseOutParam(); // 返回操作 信息
			
			list.add(etl_id);
			list.add(dataunit_id);
			list.add("");
			list.add(czrydm);
			list.add(swjgdm);
			list.add(outCode);
			list.add(outMessage);
	
			SpManager.callProc(etl001ZyglDao.getConnection(), "PKG_DATAUNIT_TRANSACITON.P_ETL_SQL_TEST_BY_DATAUNIT_ID", list);
			
			success =(String) outCode.getValue();
			message =(String) outMessage.getValue();

		}catch (Exception e) {
			  
			
		}
		
		resmap.put("success", success);
		resmap.put("message", message);
		
		if("1".equals(success)){
			
			String sql = 
					" select sum(decode(DMLSQL_IS_VALID, 'Y', 0, 1)) jysbcs --校验失败次数	\n" + 
					"        from etl_def_dataunit_dmlsql\n" + 
					"       where ETL_ID = ? and dataunit_id=?  ";
			
			String is_jysb = "1"; //是否校验失败 1、是
			list = new ArrayList<Object>();
			list.add(etl_id);
			list.add(dataunit_id);

			Map map = jdbcDao.queryformap(sql,list);
			 
			String jysbcs = map.get("JYSBCS").toString();
			
			if("0".equals(jysbcs)){
				is_jysb = "0";
			}
			
			resmap.put("is_jysb", is_jysb);

		}
		
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
    	
    	JSONDATA.put("data", list);
    	JSONDATA.put("success", "1");
    	
    	resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resmap);
 		
		return resEvent;
		
	}
	
	
	public ResponseEvent saveEtlDuSql(RequestEvent requestEvent) throws Exception{
		
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String, Object> resmap = new HashMap<String, Object>();
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		String czrydm = "";
		UserContext ctx = requestEvent.getCtx();
    	if(ctx!=null){
 	    	Map userInfo =	ctx.getUserinfo();
 	    	czrydm =(String)userInfo.get("userId");
   	    }
		
		String jsonData =  (String) requestEvent.getRequestMap().get("dataStr");
		String etl_id = (String) requestEvent.getRequestMap().get("etl_id");
		String dataunit_id = (String) requestEvent.getRequestMap().get("dataunit_id");

		jsonData = jsonData.replace("\\", "/").replace("^", "%").replace("+", "^$^");
		jsonData = URLDecoder.decode(URLDecoder.decode(jsonData,"UTF-8"),"UTF-8");
		jsonData = jsonData.replace("^$^", "+");
		
		JSONArray array = JSONArray.parseArray(jsonData);
		
		String delSql = "delete  etl_def_dataunit_dmlsql where etl_id =? and dataunit_id =? ";
		
		String insertSql = " insert into etl_def_dataunit_dmlsql(etl_id,dataunit_id,pxxh,dmlsql,dml_type,lrry_dm,lrrq,dmlsql1,dmlsql2,dmlsql3) values(?,?,?,?,?,?,sysdate,substr(?,1,4000),substr(?,4001,4000),substr(?,8001,4000)) ";

		ArrayList<Object> param = new ArrayList<Object>();
		
		param.add(etl_id);
		param.add(dataunit_id);

		jdbcDao.update(delSql,param); 
	  
		for(int i=0;i<array.size();i++){
			
			JSONObject data =array.getJSONObject(i);
	 
			String pxxh = data.getString("pxxh");
			String sql  = data.getString("sql");
			String type = data.getString("type");
		
			param = new ArrayList<Object>();
			
			param.add(etl_id);
			param.add(dataunit_id);
			param.add(pxxh);
			param.add(sql);
			param.add(type);
			param.add(czrydm);
			param.add(sql);
			param.add(sql);
			param.add(sql);
			
			jdbcDao.update(insertSql,param);
			
		}
		
		
		JSONDATA.put("success", "1");
 		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resmap);
		
		return resEvent;
		
	}
	
	
	
	/**
	 * etl回流库数据单元编辑页面
	 * @param req
	 * @return
	 * @throws PersistenceCheckedException
	 * @throws SQLException
	 */
	/*
	public ResponseEvent etlglmxU(RequestEvent requestEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		
		String etlid = (String)req.getAttr("etlid");
		String procxtbj = (String)req.getAttr("procxtbj");
		reqmap.put("etlid", etlid);
		reqmap.put("procxtbj", procxtbj);
		resEvent.setFwordPath("/pages/etl/etl001/etl_zygl_mx_sjdy_u.jsp");
		
		return resEvent;
	}
    */
	
	/**
	 * 
	 * etl存储过程数据单元编辑页面
	 * @param req
	 * @return
	 * @throws PersistenceCheckedException
	 * @throws SQLException
	 * 
	 */
	/*
	public ResponseEvent etlglmxP(RequestEvent requestEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
	 
		HashMap<String, Object> resMap = new HashMap<String, Object>();

		String etlid = (String) requestEvent.getRequestMap().get("etlid");
		
		String procxtbj = (String) requestEvent.getRequestMap().get("procxtbj");
		 
		resMap.put("etlid", etlid);
		resMap.put("procxtbj", procxtbj);
		
		resEvent.setResMap(resMap);
		
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/zygl_Unit_P.jsp");
		
		return resEvent;
	}
    */
  
	
	/*
	public ArrayList<HashMap<String, String>> getZb(CachedRowSet rowSet)throws Exception {
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = null;
		while (rowSet.next()) {
			map = new HashMap<String, String>();
			map
					.put(
							"checkbox",
							"<input value=\""
									+ rowSet.getString("zbid")
									+ "\"style='margin-left:10px' type=\"checkbox\" name='check'/>");
			map.put("zbid", rowSet.getString("zbid"));
			map.put("zbmc", rowSet.getString("zbmc"));
			map.put("zbflmc", rowSet.getString("zbflmc"));
			map.put("ztmc", rowSet.getString("ztmc"));
			dataList.add(map);
		}
		return dataList;
	}

	public String getCount(CachedRowSet rowSet)
			throws PersistenceCheckedException, SQLException {
		String count = "";
		while (rowSet.next()) {
			count = rowSet.getString("count");
		}
		return count;
	}
	*/
	/**
	 * 方法 解析指标分类
	 * @author jiangjw
	 * @日期 20170801
	 * @throws PersistenceCheckedException 
	 * @throws SQLException 
	 * @描述 初始化指标的分类
	 */
	/*
	public ArrayList<HashMap<String, String>> initZbfl(CachedRowSet rowSet) throws Exception {
		ArrayList<HashMap<String, String>> dataList=new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map=null;
		while(rowSet.next()){
			map=new HashMap<String, String>();
			map.put("id", rowSet.getString("id"));
			map.put("name", rowSet.getString("name"));
			dataList.add(map);
		}
		
		return dataList;
	}
	*/
	 
	  
	
 	/**
 	 * 
	 * 查询etl作业选用的数据单元
	 * @param req
	 * @return
	 * @throws PersistenceCheckedException
	 * @throws SQLException
	 * 
	 */
	public ResponseEvent selectSjdy(RequestEvent requestEvent)throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String, Object> resmap = new HashMap<String, Object>();
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
    	HashMap<String,String> reqmap = requestEvent.getRequestMap();
    	
		String en_name = (String) reqmap.get("en_name");
		String zh_name = (String) reqmap.get("zh_name");
		
		String etl_id=(String) reqmap.get("etlid");
		
		String jyjg = (String) reqmap.get("jyjg");
		
		String is_valid = (String) reqmap.get("is_valid");
		
		String is_outmode = (String) reqmap.get("is_outmode");
		
		String procxtbj = (String) reqmap.get("procxtbj");
		
		List list = null;
		
		if("1".equals(procxtbj)){
		   list = etl001ZyglDao.selectZyUnit(etl_id,en_name,zh_name,jyjg,is_valid,is_outmode);
		}else{
		   list = etl001ZyglDao.selectZyUnit(etl_id,en_name,zh_name);
		}
		
		JSONDATA.put("data", list);
	    resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
	    resEvent.setResMap(resmap);
		return resEvent;
		
	}

	
	public ResponseEvent saveUtilpxxh (RequestEvent requestEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resmap = new HashMap<String, Object>();
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		String datastr = (String) requestEvent.getRequestMap().get("datastr");
 
		String[] dataArr = datastr.split(",");
		
		String sql =" update etl_def_dataunit set pxxh = ?,is_valid=? where etl_id = ? and dataunit_id = ? ";
		
		ArrayList<Object> param = new ArrayList<Object>();
		
		for(int i=0;i<dataArr.length;i++){
			
			String data = dataArr[i];
			param = new ArrayList<Object>();
			
			String[] p = data.split("-");
			param.add(p[0]);
			param.add(p[3]);
			param.add(p[1]);
			param.add(p[2]);
			jdbcDao.update(sql,param);

		}
		
		
		JSONDATA.put("success", "1");
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);
		return resEvent;
	}
	
	
	 
 
	/**
 	 * 
	 * 删除作业的数据单元
	 * @param req
	 * @return
	 * @throws PersistenceCheckedException
	 * @throws SQLException
	 * 
	 */
	public ResponseEvent delEtlUnit(RequestEvent requestEvent) throws Exception {
 		
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String,Object> resmap   = new HashMap<String,Object>();
    	HashMap<String,Object> JSONDATA = new HashMap<String,Object>();
		
		String datastr = (String) requestEvent.getRequestMap().get("datastr");
 
		String[] dataArr = datastr.split(",");
		
        String sql =" delete etl_def_dataunit  where etl_id = ? and dataunit_id = ? ";
		
        String delSql = " delete  etl_def_dataunit_dmlsql where etl_id =? and dataunit_id =? ";
        
		ArrayList<Object> param = new ArrayList<Object>();
		
		for(int i=0;i<dataArr.length;i++){
			
			String data = dataArr[i];
			param = new ArrayList<Object>();
			
			String[] p = data.split("-");
			param.add(p[0]);
			param.add(p[1]);
			jdbcDao.update(sql,param);
			jdbcDao.update(delSql,param);
			
		}
		
		JSONDATA.put("success", "1");
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);
		return resEvent;
		 
	}
	
	
	/**
 	 * 
	 * 返回给作业添加数据单元页面
	 * @param req
	 * @return
	 * @throws PersistenceCheckedException
	 * @throws SQLException
	 * 
	 */
	public ResponseEvent toAddUnit(RequestEvent requestEvent)throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		
		String etlid = (String) requestEvent.getRequestMap().get("etlid");
		
		String procxtbj = this.getProTypeByEtl(etlid) ;
		
 		List ywklist = CacheUtil.getCodeTable("SYS_DATASOURCE", "IS_VALID='Y'") ;
		
 		resMap.put("ywklist",ywklist);
 		resMap.put("etlid",etlid);
 		resMap.put("procxtbj",procxtbj);

		resEvent.setResMap(resMap);
		
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/zygl_Unit_addUnit.jsp");
		return resEvent;
	}	

	
	/**
	 * 查询etl可以使用的数据单元
	 * @param req
	 * @return
	 * @throws PersistenceCheckedException
	 * @throws SQLException
	 */  
	public ResponseEvent searchUnit(RequestEvent requestEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String,String> reqMap =  requestEvent.getRequestMap();
		
 		String datasource_id = (String) reqMap.get("datasource_id");
		String owner = (String) reqMap.get("owner");
		String en_name = (String) reqMap.get("en_name");
		String zh_name = (String) reqMap.get("zh_name");
		String etl_id = (String) reqMap.get("etlid");
		String procxtbj = (String) reqMap.get("procxtbj");

		String start_str =  (String) reqMap.get("start");
		String length = (String) reqMap.get("length");
		String total_str = (String) reqMap.get("total");
		
		String ppfs = (String) reqMap.get("ppfs"); // 英文名匹配方式 1、开头匹配，2、结尾匹配
		
		Integer endNum = Integer.parseInt(start_str) + Integer.parseInt(length);
		Integer startNum = Integer.parseInt(start_str);
		
    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
    	List data    = etl001ZyglDao.searchUnit(datasource_id,owner,en_name,zh_name,startNum,endNum,etl_id,ppfs);

    	if(TycxUtils.isEmpty(total_str)){
        	
    		Map countMap = etl001ZyglDao.searchUnitCount(datasource_id,owner,en_name,zh_name,etl_id,ppfs);
    	    total_str = countMap.get("TOTAL").toString();
    	
    	}
    	
    	JSONDATA.put("data", data);
    	JSONDATA.put("total", total_str);
    	JSONDATA.put("count",total_str);
    	JSONDATA.put("iTotalDisplayRecords",total_str);
    	JSONDATA.put("iTotalRecords",total_str);
    	
    	reqmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	
    	resEvent.setResMap(reqmap);
        
		return resEvent;

	}
	
	
    /**
     * 添加etl需加工的数据单元
     * @param req
     * @return
     * @throws PersistenceCheckedException
     * @throws SQLException
     */
	public ResponseEvent addEtlUnit(RequestEvent requestEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resmap = new HashMap<String, Object>();
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		String etl_id = (String) requestEvent.getRequestMap().get("etlid");
		String dus = (String) requestEvent.getRequestMap().get("dus"); 
		
		String procxtbj = this.getProTypeByEtl(etl_id) ;
		
		String[] sjdyList = dus.split(",");
		
		/*
		String sql = " select nvl(max(pxxh),0) pxxh from etl_def_dataunit where etl_id=? ";
		
		Integer pxxh = 0;
		
		try{
			ArrayList<Object> param = new ArrayList<Object>();
			param.add(etl_id);
			Map map = jdbcDao.queryformap(sql,param);
			pxxh = Integer.parseInt(map.get("PXXH").toString());
		}catch (Exception e) {
			// TODO: handle exception
		}
		*/
		
		String czrydm = "";
		String swjgdm = "";
		UserContext ctx = requestEvent.getCtx();
    	if(ctx!=null){
 	    	Map userInfo =	ctx.getUserinfo();
 	    	czrydm =(String)userInfo.get("userId");
 	    	swjgdm =(String)userInfo.get("swjg_dm");
   	    }
    	
    	String success = "0";
		String message = "保存异常！";
    	
    	try{
    	
    		ArrayList<Object> list = new ArrayList<Object>();
    		BaseOutParam outCode = new BaseOutParam(); // 成功返回 1 ,失败返回0
    		BaseOutParam outMessage = new BaseOutParam(); // 返回操作 信息
    		
    		
			for(int i=0;i<sjdyList.length;i++){
				
				list = new ArrayList<Object>();
				
				//pxxh++;
				String dataunit_id = sjdyList[i];
 				
				//etl001ZyglDao.insertZyUnit(etl_id, dataunit_id, czrydm, pxxh);
				
				list.add(etl_id);
				list.add(dataunit_id);
				list.add("");
				list.add(czrydm);
				list.add(swjgdm);
				list.add(outCode);
				list.add(outMessage);

				SpManager.callProc(etl001ZyglDao.getConnection(), "PKG_DATAUNIT_TRANSACITON.P_ETL_SQL_BY_DATAUNIT_ID", list);
				
			}
		
			success = "1";
			message = "保存成功！";
			
    	}catch (Exception e) {

    		success = "0";
    		message = e.getMessage();
     		
		}
		
		/*
		BaseOutParam out = new BaseOutParam();// 单值
		BaseOutParam out1 = new BaseOutParam();// 单值
		
		for(int i = 0; i < sjdyList.size(); i++){
			
			map = sjdyList.get(i);
			version = (String) map.get("version");
			uinitid = (String) map.get("uinitid");
			paralist = new ArrayList<Object>();
			
			paralist.add(etlid);
			
			paralist.add(uinitid);
			
			paralist.add(version);
			
			paralist.add(this.getCZRYDM());
			
			paralist.add(this.getDLJGDM());

			paralist.add(out);
			
			paralist.add(out1);
			
		    SpManager.callProc(jdbcDao.getConnection(),"PKG_APP_DATAUNIT_TRANSACITON.P_ETL_SQL_BY_DATAUNIT_ID",paralist);
		}
		*/
		
    	JSONDATA.put("success", success);
    	JSONDATA.put("message", message);
    	
 		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resmap);
		
		return resEvent;
        
	}
	


	public ResponseEvent zfML(RequestEvent requestEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		/*
		HashMap<String,Object> jsonData=new HashMap<String, Object>();

		String code = (String) requestEvent.getRequestMap().get("code");
		
		ArrayList<String> list = new ArrayList<String>();
		list.add(code);
		
		// 判断是否可以删除,有子节点不能删除---------
		String sql = "select folder_id code,pfolder_id pcode, mc caption from a_xt_folder where pfolder_id = ? and folderlx_dm = '05'";
		CachedRowSet rs = jdbcDao.query(sql, list);
		if (rs.size() > 0) {
			jsonData.put("messager","不是子目录，不能删除");
			jsonData.put("success", "0");
			requestEvent.addAttr("jsonData", JSON.mapToJson(jsonData));
			return resEvent;
		}
		sql = " select * from etl_def where folder_id = ? ";
		
		rs = jdbcDao.query(sql, list);
		
		if (rs.size() > 0) {
			jsonData.put("messager", "该目录包含etl，不能删除");
			jsonData.put("success", "0");
			requestEvent.addAttr("jsonData", JSON.mapToJson(jsonData));
			return resEvent;
		}
		
		// 删除 -------
		sql = "delete a_xt_folder where folder_id = ?";
		jdbcDao.execute(sql, list);
		jsonData.put("messager", "删除成功");
		jsonData.put("success", "1");
		List<HashMap<String, String>> jgsList = this.getMl(jdbcDao);
		jsonData.put("jgsList", jgsList);
		requestEvent.addAttr("jsonData", JSON.mapToJson(jsonData));
		*/
		return resEvent;
	}
	 
	
    public ResponseEvent moveML(RequestEvent requestEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		
		Map reqMap = requestEvent.getRequestMap();
		
		String sjdydms = (String) reqMap.get("ydUnit");
		
		sjdydms = sjdydms.replaceAll(",", "','");
			
		String sjmvml = (String) reqMap.get("sjmvml");
		
		HashMap<String, Object> jsonData = new HashMap<String, Object>();
 		
		String sql = " update etl_def set folder_id = '" + sjmvml+ "' where etl_id in ('" + sjdydms + "') ";
		
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
    
    public ResponseEvent deleteEtl(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
		
		Map reqMap = requestEvent.getRequestMap();
		
		String etlid = (String) reqMap.get("etlid");
		BaseOutParam out  = new BaseOutParam();		// 单值
		BaseOutParam out1 = new BaseOutParam();		// 单值
		
		ArrayList<Object> paralist = new ArrayList<Object>();
		paralist.add(etlid);
		paralist.add(out)  ;	
		paralist.add(out1) ;
		
		String success = "0" ; 
		String message = "删除失败，后台程序运行异常" ;
		
		try{
			
			SpManager.callProc(jdbcDao.getConnection(),"PKG_ETL.P_ETL_SCHEDULER_DELETL",paralist);
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
    
	/*
	private List<HashMap<String,String>> getMl(IjdbcDao jdbcDao) throws Exception {
		
		String sql = "select folder_id code,pfolder_id pcode, mc caption from a_xt_folder where folderlx_dm = '05'  order by pxxh ";

		CachedRowSet crs = jdbcDao.query(sql, null);
		List<HashMap<String, String>> jgsList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = null;
		while (crs.next()) {
			map = new HashMap<String, String>();
			map.put("id", cmap.get("code"));
			map.put("pid", cmap.get("pcode"));
			map.put("name", cmap.get("caption"));
			jgsList.add(map);
		}
		return jgsList;
		
	}
    */
    
    private String getProTypeByEtl(String etlid){
    	
    	String proc_dm = this.getProcDm(etlid);
    	
    	return this.getProType(proc_dm);
    	
    }
    
    private String getProcDm(String etlid){
    	
    	String proc_dm = "";
    	
    	try {
    		
    		ArrayList<Object> param = new ArrayList<Object>();
    		param.add(etlid);
        	String sql = "select proc_dm from etl_def where etl_id=?";
    		Map map = jdbcDao.queryformap(sql,param);
    		proc_dm = map.get("PROC_DM").toString();
    		
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	
		return proc_dm;
    	
    }
    
	/**
	 * 查询作业使用的存储过程 的作用方式
	 * @param proc_dm
	 * @return
	 */
    private String getProType(String proc_dm){
    	
    	String proc_type = "0";
    	
    	Map map = null ;
    	
    	String sql =
    			"select decode(csbm,\n" +
				"              'HLK_SJQYPROC',\n" + 
				"              1,\n" + 
				"              'ORACLE2IQ_PROC',\n" + 
				"              2,\n" + 
				"              'SYBASEIQ_PROC',\n" + 
				"              3) PROC_TYPE\n" + 
				"  from sys_xtcs\n" + 
				" where INSTR(UPPER(CSZ), UPPER(?)) > 0";
    	
    	ArrayList<Object> param = new ArrayList<Object>();
    	
    	param.add(proc_dm);
    	
    	try {
    		
    		List list = jdbcDao.queryforlist(sql,param);
    		
	    	if(list.size()>0){
	    		map = (Map) list.get(0);
	    	}
	    	
	    	if(map!=null){
	    		proc_type = map.get("PROC_TYPE").toString();
	    	}
	    	
		} catch (Exception e) {
			// TODO: handle exception
		}
 
    	 
		return proc_type;

    	
    }
    


}
