package com.cwks.bizcore.etl001.service;

import com.cwks.bizcore.tycx.core.mapping.pojo.Etl001DatasourcePojo;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.core.cache.CacheUtil;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizcore.tycx.core.dao.Etl001DatasourceDao;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.common.dao.JdbcDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Component
@Service("etl001DatasourceService")
public class Etl001DatasourceService  {

    private static Logger logger = LoggerFactory.getLogger(Etl001DatasourceService.class);

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private Etl001DatasourceDao etl001DatasourceDao;

    public ResponseEvent init(RequestEvent requestEvent) throws Exception {
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
    	List dbtypes = CacheUtil.getCodeTable("DM_GY_DS_TYPE", "XYBJ='Y'") ;
    	reqmap.put("dbtypes",dbtypes);
    	
    	ArrayList dbtypesJSON = new ArrayList();
    	for(int i=0;i<dbtypes.size();i++){
    		Map map =(Map) dbtypes.get(i);
    		map.put("code",map.get("dm"));
    		map.put("caption",map.get("mc"));
    		dbtypesJSON.add(map);
		}
    	
    	reqmap.put("dbtypesJSON", JsonUtil.toJson(dbtypesJSON).replace("\"", "'"));
    	
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/datasourceManager.jsp");
		resEvent.setResMap(reqmap);
		return resEvent;
	}
    
    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_select");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Etl001DatasourcePojo etl001DatasourcePojo = new Etl001DatasourcePojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<Etl001DatasourcePojo> list = etl001DatasourceDao.select(etl001DatasourcePojo); 
        PageInfo<Etl001DatasourcePojo> pages = new PageInfo<Etl001DatasourcePojo>(list);
        reqmap.put("JSONDATA", toJsonForJqGrid(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    
    public ResponseEvent selectDbList(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
          
        List list = etl001DatasourceDao.selectAllDbList();
        
        reqmap.put("JSONDATA", JsonUtil.toJson(list));
        
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_expExcel");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Etl001DatasourcePojo etl001DatasourcePojo = new Etl001DatasourcePojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List<Etl001DatasourcePojo> expList = null;
        if(!TycxUtils.isEmpty(page)){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<Etl001DatasourcePojo> pages = new PageInfo<Etl001DatasourcePojo>(etl001DatasourceDao.select(etl001DatasourcePojo));
            expList = pages.getList();
        }else{
            expList = etl001DatasourceDao.select(etl001DatasourcePojo);
        }
        Map colMap = new LinkedHashMap();
        colMap.put("datasource_id","数据源ID");
        colMap.put("db_ip","数据库IP");
        colMap.put("db_lx","数据库类型：1、ORACLE,2、MYS");
        colMap.put("db_param","数据库链接参数");
        colMap.put("db_port","数据库端口号");
        colMap.put("db_sid","数据库SID");
        colMap.put("host","端口号");
        colMap.put("ip","数据库地址");
        colMap.put("is_jk","监控表空间 、用户资源、数据表情况：1、");
        colMap.put("is_valid","选用标记(YN)");
        colMap.put("lrry_dm","录入人员代码");
        colMap.put("lr_sj","录入时间");
        colMap.put("mc","数据源名称");
        colMap.put("ms","数据源描述");
        colMap.put("password","数据库登录口令");
        colMap.put("pxxh","排序序号");
        colMap.put("sid","数据库服务名");
        colMap.put("username","数据库登录账号");
        colMap.put("xgry_dm","修改人员代码");
        colMap.put("xg_sj","修改时间");
        colMap.put("xtbj","系统标记，1 业务库,2 回流库,3 服");
        //Excel导出格式
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("fileName","数据源");
        dataMap.put("class",Etl001DatasourcePojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_deleteByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        Etl001DatasourcePojo pojo = null;
       /*
        for(int i=0;i<pkid.length;i++){
            pojo = new Etl001DatasourcePojo();
            etl001DatasourceDao.deleteByPKey(pojo);
        }
        */
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //保存一条数据源信息 
    public ResponseEvent saveOneRow(RequestEvent requestEvent) throws Exception {
    	
        ResponseEvent resEvent = new ResponseEvent();

        Etl001DatasourcePojo etl001DatasourcePojo = new Etl001DatasourcePojo(requestEvent.getRequestMap());
		
        String datasource_id = etl001DatasourcePojo.getDatasource_id();
        
        if(!TycxUtils.isEmpty(datasource_id)){//主键不为空插入一天数据
        	resEvent = updateByPKeySelective(requestEvent);
        }else{
    	    
        	resEvent = insertSelective(requestEvent);
        }
        
        String dblinks = (String) requestEvent.getRequestMap().get("dblinks");
        
        boolean flag = this.saveDblinks(datasource_id, dblinks, requestEvent);
        
        return resEvent;
 
    }
    
    
    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_insertSelective");
        ResponseEvent resEvent = new ResponseEvent();
        Etl001DatasourcePojo etl001DatasourcePojo = new Etl001DatasourcePojo(requestEvent.getRequestMap());
	    
        HashMap<String,String> reqmap = new HashMap<String,String>();
        
        HashMap<String,String> result = new HashMap<String, String>(); 	
        
        try{
        
        	UserContext ctx = requestEvent.getCtx();
    	    if(ctx!=null){
    	    	Map userInfo =	ctx.getUserinfo();
    	    	etl001DatasourcePojo.setLrry_dm((String)userInfo.get("userId"));
    	    }
    	  
    	    String datasource_id = UUIDGenerator.getUUID();
            etl001DatasourcePojo.setDatasource_id(datasource_id);
    	    
        	etl001DatasourceDao.insertSelective(etl001DatasourcePojo);
        	
        	result.put("datasource_id", etl001DatasourcePojo.getDatasource_id());
        	
        	
        }catch (Exception e) {
        	
        	 result.put("state", "0");
	         result.put("message", e.getMessage());
	         reqmap.put("JSONDATA", JsonUtil.toJson(result));
	         
	         resEvent.setResMap(reqmap);
	         return resEvent;
	         
 		}finally{
			
	         result.put("state", "1");
	         result.put("message", "插入数据成功");
	       
         
		}
         
        reqmap.put("JSONDATA", JsonUtil.toJson(result));
        
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_updateByPKeySelective");
        ResponseEvent resEvent = new ResponseEvent();
        Etl001DatasourcePojo etl001DatasourcePojo = new Etl001DatasourcePojo(requestEvent.getRequestMap());
        
        UserContext ctx = requestEvent.getCtx();
	    if(ctx!=null){
	    	Map userInfo =	ctx.getUserinfo();
	    	etl001DatasourcePojo.setXgry_dm((String)userInfo.get("userId"));
	    }
         
        etl001DatasourceDao.updateByPKeySelective(etl001DatasourcePojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.UPDATE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    
    public ResponseEvent selectDblinks(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	HashMap<String, String> reqmap = new HashMap<String, String>();
        
    	String datasource_id = (String) requestEvent.getRequestMap().get("datasource_id");
    	
	    List list = etl001DatasourceDao.selectAllDbList(datasource_id);
       
        reqmap.put("JSONDATA", JsonUtil.toJson(list));
       
        resEvent.setResMap(reqmap);
        
        return resEvent;
    	
    }
    
    /**
     * 作废数据源
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent zfDatasource(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();

    	HashMap<String, String> reqmap = new HashMap<String, String>();

    	String dbs = (String) requestEvent.getRequestMap().get("dbs");
    	
    	String[] dbarr = dbs.split(",");
    	
    	Map<String,String> map =new HashMap<String, String>();
    	
    	try{
    	
	    	for(int i=0;i<dbarr.length;i++){
	    		etl001DatasourceDao.zfDatasuorce(dbarr[i]);
	    	}
    	
	    	map.put("state", "1");
	    	map.put("message", "作废成功");

    	}catch (Exception e) {
    		
	    	map.put("state", "0");
	    	map.put("message", "作废失败");

		}

    	reqmap.put("JSONDATA", JsonUtil.toJson(map));
    	resEvent.setResMap(reqmap);
        
        return resEvent;

    }
    
    /**
     * 启用数据源
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent qyDatasource(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();

    	HashMap<String, String> reqmap = new HashMap<String, String>();

    	String dbs = (String) requestEvent.getRequestMap().get("dbs");
    	
    	String[] dbarr = dbs.split(",");
    	
    	Map<String,String> map =new HashMap<String, String>();
    	
    	try{
    	
	    	for(int i=0;i<dbarr.length;i++){
	    		etl001DatasourceDao.qyDatasuorce(dbarr[i]);
	    	}
    	
	    	map.put("state", "1");
	    	map.put("message", "启用成功");

    	}catch (Exception e) {
    		
	    	map.put("state", "0");
	    	map.put("message", "启用失败");

		}

    	reqmap.put("JSONDATA", JsonUtil.toJson(map));
    	resEvent.setResMap(reqmap);
        
        return resEvent;

    }
    
    private boolean saveDblinks(String datasource_id, String dblinks, RequestEvent requestEvent) throws Exception {
    	
    	 
    	 boolean flag = false;
    	 
    	 try{
    		   etl001DatasourceDao.deletDblinkByKey(datasource_id);
    		   flag = true;
    	 }catch (Exception e) {
    		   flag = false;
		 }
    	 
    	 
    	 if(flag){
    		 
    		 	try {
    		 		
		    		UserContext ctx = requestEvent.getCtx();
		    		String swry_dm = null;
		    		
		     	    if(ctx!=null){
		     	    	Map userInfo =	ctx.getUserinfo();
		     	    	swry_dm = (String)userInfo.get("userId");
		     	    }
		    		 
		    		String[] dblinkArr= dblinks.split(",");
		    		
		    		for(int i =0;i<dblinkArr.length;i++){
		    			
		    			String dblink = dblinkArr[i];
		    			Map<String,String> db = new HashMap<String, String>();
		    			
		    			db.put("from_datasource_id", datasource_id);
		    			db.put("to_datasource_id", dblink.split("-")[0]);
		    			db.put("dblink", dblink.split("-")[1]);
		    			db.put("lrry_dm", swry_dm);
		    			
		    			etl001DatasourceDao.InsertDblink(db);
		    			
		    		}
	    		
		    		flag =true;
		    		
	    		}catch (Exception e) {
	    			flag = false;
				}
    		
    	 }
    	 
		  return flag;
		 
    }
    
    private String toJsonForJqGrid(PageInfo<?> page)
    {
    	
      Map map = new HashMap();
      
      map.put("page", Integer.valueOf(page.getPageNum()));
      map.put("total", page.getTotal());
      map.put("count",page.getTotal());
      map.put("iTotalDisplayRecords",page.getTotal());
      map.put("iTotalRecords",page.getTotal());
      
      map.put("records", Long.valueOf(page.getTotal()));
      
      map.put("data", page.getList());
      
      return JsonUtil.toJson(map);
      
    }
 
    private String toCssnjJson(Object obj){
    	 return JsonUtil.toJson(obj).replace("\"", "\\\"");
    }
    
     
    
    /**
     * 
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent yhtb(RequestEvent requestEvent) throws Exception {
    	
        ResponseEvent resEvent = new ResponseEvent();
		HashMap<Object, Object> reqmap = new HashMap<Object, Object>();

        String id = (String) (String) requestEvent.getRequestMap().get("id"); // datasouse_id
		String mc = (String) (String) requestEvent.getRequestMap().get("mc"); // ywkmc
        
		Map map = etl001DatasourceDao.getPTk();
		
		String ptid = (String) map.get("ptid");
		
		String dblink = "";
		String cs = null;
	
		if(id.equals(ptid)){
			
			
		}else{
			
			String getCs = " select b.dblink cs from sys_datasource_DBLINK B where b.FROM_DATASOURCE_ID = ? and b.to_datasource_id=? "; 

			ArrayList<String> param = new ArrayList<String>();
			
			param.add(ptid);
			param.add(id);
			
			List onerow=jdbcDao.queryforlist(getCs,param) ;
			
			if(onerow.size()<1){
				
				reqmap.put("errorinfo", "连接数据库失败: <h3>未配置平台库到该库的dblink</h3>");
				resEvent.setResMap(reqmap);
				resEvent.setFwordPath("/public/js/common/jsp/error.jsp");
				return resEvent;
				
			}else{
				
				Map row = (Map) onerow.get(0);
				cs = (String) row.get("cs");
				
			}
			
		}
		
		
		if(!TycxUtils.isEmpty(cs)){
			dblink = "@" + cs;
		} 
		 
  
		List leftList = null;
		
		try {
			leftList = etl001DatasourceDao.selectAllUser(id,dblink);
		} catch (Exception e) {
			reqmap.put("errorinfo", "连接数据库失败: <h3>平台库到该数据库的链接参数无效</h3>");
			resEvent.setResMap(reqmap);
			resEvent.setFwordPath("/public/js/common/jsp/error.jsp");
			return resEvent;
		}
		
		List rightList = etl001DatasourceDao.selectDatasourceUser(id);
		
		
		reqmap.put("leftList", JsonUtil.toJson(leftList));
		reqmap.put("rightList", JsonUtil.toJson(rightList));

		reqmap.put("id", id);
		reqmap.put("cs", cs);
		reqmap.put("mc", mc);
		 
		resEvent.setResMap(reqmap);
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/datasourceUserTb.jsp");
		
		return resEvent;

    }
    
    /**
     *  重新添加用户
     * 
     */
    public ResponseEvent reAddUser(RequestEvent requestEvent) throws Exception {
    	
        ResponseEvent resEvent = new ResponseEvent();
        String id = (String) (String) requestEvent.getRequestMap().get("id"); 		  // datasouse_id
		String owners = (String) (String) requestEvent.getRequestMap().get("owners"); // ywkmc
		String swry_dm = "";
    	 
   	  	UserContext ctx = requestEvent.getCtx();
   	  	
 	    if(ctx!=null){
 	    	
 	    	Map userInfo =	ctx.getUserinfo();
 	    	swry_dm = (String) userInfo.get("sery_dm");
 	    	
 	    }
		 
		HashMap<String,String> reqmap = new HashMap<String,String>();
        
        HashMap<String,String> result = new HashMap<String, String>(); 	
		
		try{
			etl001DatasourceDao.reAddUser(owners, id, swry_dm);
			result.put("state", "1");
		}catch (Exception e) {
			result.put("state", "0");
		} 
          
		reqmap.put("JSONDATA", JsonUtil.toJson(result));
        resEvent.setResMap(reqmap);
        
        return resEvent;
        
    }
    
    public ResponseEvent deleteUser(RequestEvent requestEvent) throws Exception {
    	
    	 ResponseEvent resEvent = new ResponseEvent();
    	 String id = (String) (String) requestEvent.getRequestMap().get("id"); 		  // datasouse_id
 		 String owners = (String) (String) requestEvent.getRequestMap().get("owners"); // ywkmc
 		 
 		HashMap<String,String> reqmap = new HashMap<String,String>();
         
        HashMap<String,String> result = new HashMap<String, String>(); 	
 		
 		 try{
 			etl001DatasourceDao.deleteUser(owners,id);
 			result.put("state", "1");
		 }catch (Exception e) {
			result.put("state", "0");
		 } 
          
		 reqmap.put("JSONDATA", JsonUtil.toJson(result));
         resEvent.setResMap(reqmap);
        
 		 return resEvent;
    
    }
    
    
    public ResponseEvent selectByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_selectByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        return resEvent;
    }



}
