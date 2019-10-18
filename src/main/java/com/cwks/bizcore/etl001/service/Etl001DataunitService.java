package com.cwks.bizcore.etl001.service;

import com.alibaba.fastjson.JSONObject;
import com.cwks.common.dao.JdbcDao;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.service.impl.BaseService;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.core.cache.CacheUtil;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizcore.tycx.core.dao.Etl001DataunitDao;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service("etl001DataunitService")
public class Etl001DataunitService   {

    private static Logger logger = LoggerFactory.getLogger(Etl001DatasourceService.class);

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private Etl001DataunitDao etl001DataunitDao;
    
    public ResponseEvent init(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
 
		
		List ywklist = CacheUtil.getCodeTable("SYS_DATASOURCE", "IS_VALID='Y'") ;
		
		reqmap.put("ywklist",ywklist);
		
    	resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/dataunitManager.jsp");
    	resEvent.setResMap(reqmap);
		
		return resEvent;
		
	}
    
    
    public ResponseEvent searchMl(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
    	
    	String sql = 
					"with temp as\n" +
					"(select count(1) cc,folder_id ff\n" + 
					"            from sys_dataunit t\n" + 
					"           where upper(t.is_last_version) = upper('Y')\n" + 
					"             and upper(t.is_valid) = upper('Y')\n" + 
					"           group by folder_id)\n" + 
					"select folder_id id,\n" + 
					"       pfolder_id pid,\n" + 
					"       mc name,\n" + 
					"       pxxh,\n" + 
					"       (select nvl(sum(cc), 0)\n" + 
					"          from temp t\n" + 
					"         where ff in (select folder_id\n" + 
					"                        from sys_folder\n" + 
					"                      connect by prior folder_id = pfolder_id\n" + 
					"                       start with folder_id = x.folder_id)\n" + 
					"            or x.folder_id = '01') count\n" + 
					"  from sys_folder x\n" + 
					" where folderlx_dm = '01'\n" + 
					"   and upper(xybj) = upper('Y')\n" + 
					" order by pxxh";
    	
    	List jgsList = jdbcDao.queryforlist(sql);	
    	 	
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
    	
    	reqmap.put("JSONDATA", JsonUtil.toJson(data));
    	resEvent.setResMap(reqmap);
    	
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
 
    	String mldm = (String) requestEvent.getRequestMap().get("mldm");
    	String mlmc = (String) requestEvent.getRequestMap().get("mlmc");
    	String sjml = (String) requestEvent.getRequestMap().get("sjml");
    	String pxxh = (String) requestEvent.getRequestMap().get("pxxh");

    	UserContext ctx = requestEvent.getCtx();
    	String swry_dm = "";
    	if(ctx!=null){
 	    	Map userInfo =	ctx.getUserinfo();
 	    	swry_dm =(String)userInfo.get("userId");
  	    }
    	
    	ArrayList<String> params = new ArrayList<String>();
    	
    	String sql = null;
    	
    	if(TycxUtils.isEmpty(mldm)){ // 插入新分类
    		
    		  mldm = UUIDGenerator.getUUID();
    		
    		  sql = " INSERT INTO SYS_FOLDER                                                  \n" +
    				"  (MC, MC_J, PFOLDER_ID, FOLDERLX_DM, SSSWJG_DM, XYBJ, PXXH,FOLDER_ID)   \n" + 
    				" VALUES                                                                  \n" + 
    				"  (?, ?, ?, '01', ?, 'Y',?,?)                                            \n" ;

    		
    	}else{ //更新分类
    		
    		sql =  " UPDATE SYS_FOLDER                                                         \n" +
    			   "  SET MC =?, MC_J=?, PFOLDER_ID=?, FOLDERLX_DM='01', SSSWJG_DM=?,  PXXH=?  \n" + 
    			   " WHERE FOLDER_ID=?                                                   	   \n" ;
    		
    	}
    	
    	params.add(mlmc);
    	params.add(mlmc);
    	params.add(sjml);
    	params.add(swry_dm);
    	params.add(pxxh);
    	params.add(mldm);

    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
    	HashMap<String,Object> data = new HashMap<String, Object>();
    	
    	try{
    	   jdbcDao.update(sql,params);
    	   data.put("success", "1");
    	}catch (Exception e) {
    		data.put("success", "0");
		}
    	
     	reqmap.put("JSONDATA", JsonUtil.toJson(data));
    	resEvent.setResMap(reqmap);
    	return resEvent;
    }
    
    public ResponseEvent zfML(RequestEvent requestEvent){
    	ResponseEvent resEvent = new ResponseEvent();
    	String id = (String) requestEvent.getRequestMap().get("code");
    	
    	String success ;
    	
    	try{
    		etl001DataunitDao.zfMl(id);
    		success = "1";
    	}catch (Exception e) {
    		success="0";
		}
    	
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
    	JSONDATA.put("success", success);
    	
    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
    	reqmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	
    	resEvent.setResMap(reqmap);
    	return resEvent;
    	
    }
    
	public ResponseEvent moveML(RequestEvent requestEvent){
			
			ResponseEvent resEvent = new ResponseEvent();
 			
			Map reqMap = requestEvent.getRequestMap();
			
			String sjdydms = (String) reqMap.get("ydUnit");
			
  			sjdydms = sjdydms.replaceAll(",", "','");
 			
			String sjmvml = (String) reqMap.get("sjmvml");
			
			HashMap<String, Object> jsonData = new HashMap<String, Object>();
	 		
			String sql = "update sys_dataunit set folder_id = '" + sjmvml+ "' where dataunit_id in ('" + sjdydms + "')";
			
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
    
    public ResponseEvent searchUnit(RequestEvent requestEvent) throws Exception {
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	String unit_fl = (String) requestEvent.getRequestMap().get("unit_fl");
		String datasource_id = (String) requestEvent.getRequestMap().get("datasource_id");
		String owner = (String) requestEvent.getRequestMap().get("owner");
		String en_name = (String) requestEvent.getRequestMap().get("en_name");
		String zh_name = (String) requestEvent.getRequestMap().get("zh_name");

		String start_str =  (String) requestEvent.getRequestMap().get("start");
		String length = (String) requestEvent.getRequestMap().get("length");
		String total_str = (String) requestEvent.getRequestMap().get("total");
		
		Integer endNum = Integer.parseInt(start_str) + Integer.parseInt(length);
		Integer startNum = Integer.parseInt(start_str);
		
    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
    	
    	if(TycxUtils.isEmpty(unit_fl)){
    		unit_fl="01";
    	}
    	
    	List data    = etl001DataunitDao.searchUnit(unit_fl,datasource_id,owner,en_name,zh_name,startNum,endNum);
    	
    	if(TycxUtils.isEmpty(total_str)){
    	
    		Map countMap = etl001DataunitDao.searchUnitCount(unit_fl,datasource_id,owner,en_name,zh_name);
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
    
    public ResponseEvent initNew(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resmap = new HashMap<String, Object>();
		
		String folderid = (String) requestEvent.getRequestMap().get("folderid");
		String dataunitid = (String) requestEvent.getRequestMap().get("dataunitid");
 		
		if(dataunitid == null || dataunitid.equals("")) {
			
			resmap.put("VERSION_ID", "1");
			resmap.put("VERSION_MC", "version 1");
			
		}else{
			
			ArrayList proParam = new ArrayList();
			
			resmap = (HashMap<String, Object>) etl001DataunitDao.queryUnit(dataunitid);
			resmap.put("VERSION_MC", "version "+resmap.get("VERSION_ID"));
			folderid = (String) resmap.get("FOLDER_ID");
			
			String version_id =  resmap.get("VERSION_ID").toString();
			
			
		}
		
		String foldermc = "" ;
		List<String> folderList = new ArrayList<String>();
		String sql = "  select folder_id code,    \n" +
					 "         mc caption,        \n" +
					 "         pfolder_id pcode,  \n" +
					 "         level lv           \n" +
					 "    from sys_folder         \n" +
					 "   where folderlx_dm = ?    \n" +
					 " connect by prior pfolder_id = folder_id \n" + 
					 "   start with folder_id = ? \n"+ 
					 "   order by lv desc         \n";
		
		ArrayList<String> params = new ArrayList<String>();
		params.add("01");
		params.add(folderid);

		List list = jdbcDao.queryforlist(sql,params) ;
		
		for(int i=0;i<list.size();i++){
			
			HashMap<String,Object> map= (HashMap<String,Object>) list.get(i) ;
 			
			folderList.add(map.get("caption").toString());
		}
 
		foldermc = folderList.toString();
		foldermc = foldermc.substring(1,foldermc.length()-1).replace(",", "&nbsp;◢  &nbsp;");
		 
		resmap.put("FOLDER_MC", foldermc);
		resmap.put("FOLDER_ID", folderid);
		resmap.put("datasourcelist", CacheUtil.getCodeTable("SYS_DATASOURCE", "IS_VALID='Y'"));
		
		resEvent.setResMap(resmap); 
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/dataunitEdit.jsp");
		return resEvent;
	}
    
    /**
     * 
     * @param requestEvent
     * @return
     * @throws Exception
     * 查询当前版本数据单元的数据项信息
     * 
     */
	public ResponseEvent searchSjxList(RequestEvent requestEvent) throws Exception {
	    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> resmap = new HashMap<String, Object>();
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
    	
    	String dataunitid = (String) requestEvent.getRequestMap().get("dataunit_id");
    	String version_id = (String) requestEvent.getRequestMap().get("version_id");
    	
    	List sjxList = etl001DataunitDao.querySjxList(dataunitid,version_id);
    	
    	JSONDATA.put("data",sjxList);
    	
    	resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resmap);
    	return resEvent;
	    	
	}
    
    public ResponseEvent saveUnit(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> resmap = new HashMap<String, Object>();
    	
    	Map<String,Object> map = requestEvent.getRequestMap();
    	
    	/*
    	String en_name = (String) requestEvent.getRequestMap().get("en_name");
    	String zh_name = (String) requestEvent.getRequestMap().get("zh_name");
    	String datasource_id = (String) requestEvent.getRequestMap().get("datasource_id");
    	String owner = (String) requestEvent.getRequestMap().get("owner");
    	String version_id = (String) requestEvent.getRequestMap().get("version_id");
    	String folder_id = (String)requestEvent.getRequestMap().get("folder_id");
    	String du_desc= (String)requestEvent.getRequestMap().get("du_desc");
    	*/
    	
    	String dataunit_id = (String) requestEvent.getRequestMap().get("dataunit_id");
    	String version_id = (String) requestEvent.getRequestMap().get("version_id");
 
		if(TycxUtils.isEmpty(version_id)){
			version_id = "1";
		}
		 
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
    	
    	if(TycxUtils.isEmpty(dataunit_id) ){ // 新增数单元

    		dataunit_id = UUIDGenerator.getUUID();
    		map.put("dataunit_id", dataunit_id);
    		etl001DataunitDao.InsertUnit(map);
    		
    	}else{ //更新数据单元
    		
    		etl001DataunitDao.UpdateUnit(map);
    		
    	}
    	
  
    	JSONDATA.put("success", "1");
    	JSONDATA.put("dataunit_id", dataunit_id);
    	JSONDATA.put("version_id", version_id);
    	
    	resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resmap); 
    	
    	return resEvent;
    	
    }
    
    public ResponseEvent saveDuSjx(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> resmap = new HashMap<String, Object>();
    	
    	Map<String,Object> map = requestEvent.getRequestMap();
    	
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put("dataunit_id", map.get("dataunit_id"));
    	jsonObject.put("version_id", map.get("version_id"));
    	jsonObject.put("col_id", map.get("COL_ID"));
    	jsonObject.put("col_name", map.get("COL_NAME"));
    	jsonObject.put("zh_name", map.get("ZH_NAME"));
    	jsonObject.put("data_type", map.get("DATA_TYPE"));
    	jsonObject.put("data_length", map.get("DATA_LENGTH"));
    	jsonObject.put("data_scale", map.get("DATA_SCALE"));
    	jsonObject.put("biz_desc" ,((String)map.get("BIZ_DESC")).replace("&&", "\t").replace("##", "\n").replace("@@", "\r"));
    	jsonObject.put("tech_desc",((String)map.get("TECH_DESC")).replace("&&", "\t").replace("##", "\n").replace("@@", "\r"));
    	
    	boolean success = etl001DataunitDao.saveSjx(jsonObject);
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
    	
    	if(success){
    		JSONDATA.put("success", "1");
    		JSONDATA.put("message", "保存成功");
    	}else{
    		JSONDATA.put("success", "0");
    		JSONDATA.put("message", "保存异常！");
    	}
    	
    	resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resmap); 
    	
    	return resEvent;
    	
    }
    
    public ResponseEvent deleteSjx(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> resmap = new HashMap<String, Object>();
    	
    	Map<String,Object> map = requestEvent.getRequestMap();
    	
    	ArrayList<String> param = new ArrayList<String>();
    	param.add((String)map.get("dataunit_id"));
    	param.add((String)map.get("version_id"));
    	
    	String col_ids = (String) map.get("col_ids");
    	
    	col_ids = col_ids.replace(",", "','");
    	
    	String sql = "delete sys_dataunit_col where dataunit_id = ? and version_id =? and col_id in ( '"+col_ids+"') ";
    	
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();

    	try{
    		jdbcDao.update(sql,param);
    		JSONDATA.put("success", "1");
    	}catch (Exception e) {
    		JSONDATA.put("success", "0");
		}
     
    	resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resmap); 
    	
    	return resEvent;
    	
    }
    
    
    public ResponseEvent selectUser(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	String datasource_id = (String) requestEvent.getRequestMap().get("datasource_id");
    	
    	String sql = " select ds_user code,ds_user caption from sys_datasource_user t where (t.datasource_id = ? OR ? is NULL)";
    	
    	ArrayList params= new ArrayList<String>();
    	params.add(datasource_id);
    	params.add(datasource_id);

    	List list = jdbcDao.queryforlist(sql,params);
    	
    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
    	reqmap.put("JSONDATA", JsonUtil.toJson(list));
    	resEvent.setResMap(reqmap);
    	
    	return resEvent;
    	
    }
    
    //提示版本
    public ResponseEvent promoteVersion(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	HashMap<String, Object> resmap = new HashMap<String, Object>();
    	
    	String dataunit_id = (String) requestEvent.getRequestMap().get("dataunit_id");
    	
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
    	
    	
    	try{
    		
    		etl001DataunitDao.promoteVersion(dataunit_id);
    		JSONDATA.put("success", "1");
        	JSONDATA.put("dataunit_id", dataunit_id);
    		
    	}catch (Exception e) {
    		JSONDATA.put("success", "0");
        	JSONDATA.put("dataunit_id", dataunit_id);
		}
    	
    	resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resmap);
    	
    	return resEvent;
    	
    }
    
     
    
    /**
     * 数据项
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent initSjx(RequestEvent requestEvent) throws Exception {
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> resmap = new HashMap<String, Object>();
    	String dataunit_id = (String) requestEvent.getRequestMap().get("dataunit_id");
    	resmap.put("dataunit_id", dataunit_id);
		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/dataunitSjx.jsp");
		return resEvent;
    }
    
    /**
     * 
     * 前置数据单元
     * @param requestEvent
     * @return
     * @throws Exception
     * 
     */
    public ResponseEvent initQzsj(RequestEvent requestEvent) throws Exception {
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> resmap = new HashMap<String, Object>();
    	String dataunit_id = (String) requestEvent.getRequestMap().get("dataunit_id");
    	resmap.put("dataunit_id", dataunit_id);
		resEvent.setResMap(resmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/dataunitQzdy.jsp");
		return resEvent;
    }
    
    public ResponseEvent queryQzData(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	HashMap<String, Object> resmap = new HashMap<String, Object>();
    	
    	String dataunit_id = (String) requestEvent.getRequestMap().get("dataunit_id");
    	
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
    	
    	String sql = " select dataunit_id predataunit_id,owner,en_name,zh_name,c.ds_name,b.mc folder_mc 	\n" +
					 "  from sys_dataunit A, SYS_FOLDER B, sys_datasource C									\n" + 
					 " where dataunit_id in																	\n" + 
					 "       (select predataunit_id from sys_dataunit_pre where dataunit_id = ?)			\n" + 
					 "   and A.Is_Last_Version = upper('Y')													\n" + 
					 "   and A.Is_Valid = upper('Y')														\n" + 
					 "   and A.Folder_Id = b.folder_id(+)													\n" + 
					 "   and A.Datasource_Id = c.datasource_id(+)								 			  ";

    	
    	ArrayList<Object> param = new ArrayList<Object>();
    	
    	param.add(dataunit_id);
    	
    	List list = jdbcDao.queryforlist(sql,param);
    	JSONDATA.put("data", list);
     	JSONDATA.put("total",1);
    	JSONDATA.put("count",1);
    	JSONDATA.put("iTotalDisplayRecords",1);
    	JSONDATA.put("iTotalRecords",1);
     	resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));

    	resEvent.setResMap(resmap);
    	
    	return resEvent;
    	
    }
    
    public ResponseEvent deletePredataunit(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	HashMap<String, Object> resmap = new HashMap<String, Object>();
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();

    	
    	String dataunit_id = (String) requestEvent.getRequestMap().get("dataunit_id");
    	String predataunit_id = (String) requestEvent.getRequestMap().get("predataunit_id");

    	ArrayList<Object> param = new ArrayList<Object>();
    	param.add(dataunit_id);
    	param.add(predataunit_id);
    	
    	String sql = " delete sys_dataunit_pre where dataunit_id = ? and predataunit_id = ? ";
    	
    	try{
	    	jdbcDao.update(sql,param);  
	    	JSONDATA.put("state", "1"); 
    	}catch (Exception e) {
			// TODO: handle exception
    		JSONDATA.put("state", "0"); 
		}
    	
     	resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
     	resEvent.setResMap(resmap);
    	return resEvent;
    	
    }
    
    /**
     * 
     * @param requestEvent
     * @return
     * @throws Exception
     * 
     */
    public ResponseEvent initAddPredataunit(RequestEvent requestEvent)throws  Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	HashMap<String, Object> resmap = new HashMap<String, Object>();
    	
     	String dataunit_id = (String) requestEvent.getRequestMap().get("dataunit_id");
     	
		List ywklist = CacheUtil.getCodeTable("SYS_DATASOURCE", "IS_VALID='Y'") ;

    	resmap.put("dataunit_id",dataunit_id);
    	resmap.put("ywklist",ywklist);
    	
     	resEvent.setResMap(resmap);
     	
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/dataunitAddpre.jsp");

    	return resEvent;
    	
    }
    
    public ResponseEvent addPredataunit(RequestEvent requestEvent)throws  Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	HashMap<String, Object> resmap = new HashMap<String, Object>();
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
    	
    	String predataunits =  (String) requestEvent.getRequestMap().get("dataunits");
    	String dataunit_id =  (String) requestEvent.getRequestMap().get("dataunit_id");
     	
    	String indataunits =predataunits.replace(",", "','");
    	
    	try{
    	
	    	String sql = "delete sys_dataunit_pre where dataunit_id=? and predataunit_id in ('"+indataunits+"') ";
	    	
	    	ArrayList<Object> param = new ArrayList<Object>();
	    	param.add(dataunit_id);
	    	jdbcDao.update(sql,param);  
	    	
	    	sql = " insert into sys_dataunit_pre        	 \n" +
				  "  (dataunit_id, predataunit_id, lrrq)	 \n" + 
				  "  select ?, column_value, sysdate		 \n" + 
				  "    from table(pkg_pub_fun.FUN_STR_SPLIT(?,','))	";
	
	    	param = new ArrayList<Object>();
	    	param.add(dataunit_id);
	    	param.add(predataunits);
	    	
	    	jdbcDao.update(sql,param);  
    	
	    	JSONDATA.put("state", "1"); 
	    	
    	}catch (Exception e) {

    		JSONDATA.put("state", "0"); 
    		
    	} 
    	
    	resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
     	resEvent.setResMap(resmap);
    	
    	return resEvent;
    	
    }
    
    public ResponseEvent saveQzsj(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	String sql = "" ;
    	
    	
    	return resEvent ;
    	
    }
    
    
    /**
     * 血缘关系
     * @param requestEvent
     * @return
     * @throws Exception
     * 
     */
    public ResponseEvent initXygx(RequestEvent requestEvent) throws Exception {
    	ResponseEvent resEvent = new ResponseEvent();
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/dataunitXygx.jsp");
		return resEvent;
    }
    
    public ResponseEvent selectByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_selectByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        return resEvent;
    }



    
}
