package com.cwks.bizcore.tycx.core.service;

import com.alibaba.fastjson.JSONArray;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.daoUtil.CssnjDao;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.bizcore.tycx.core.dao.TycxEditDao;
import com.cwks.common.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Service("TycxEditService")
public class TycxEditService  {
	
	@Autowired
	private TycxEditDao tycxEditDao;
	
	@Autowired
	private CssnjDao cssnjDao;
	 
	
    public ResponseEvent init(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();

        HashMap<String, Object>  resMap = new HashMap<String, Object>();

    	//测试登陆失效
        resMap = testLogin(requestEvent.getCtx(),resMap);
        if(resMap.get("errorinfo")!=null){
       	 resEvent.setResMap(resMap);
       	 resEvent.setFwordPath("/public/js/common/jsp/error.jsp");
       	 return resEvent;
        }
    	
    	String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");
 
    	try{
    		
    		Map map = tycxEditDao.getInfoBySqlxh(sqlxh);
    		resMap.put("tabpkcol", map.get("TAB_PKCOL"));
    		
    	}catch(Exception e){
    		
    	}
    	
    	resMap.put("sqlxh", sqlxh);
    	
    	resEvent.setResMap(resMap);
    	
		resEvent.setFwordPath("/biz/core/ext/tycx_jy/edit/tycx_edit.jsp");

		return resEvent;
    	
    }
    
    public ResponseEvent getEditInfo(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh"); 

    	Map JSONDATA = tycxEditDao.getInfoBySqlxh(sqlxh); 
    	
    	HashMap<String, Object> resMap = new HashMap<String, Object>(); 
    	
    	resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	
    	resEvent.setResMap(resMap); 
    	
    	return resEvent; 
    
    }
 
    public ResponseEvent updateOneRow(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();

    	String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");
    	
    	HashMap<String,Object> map = requestEvent.getRequestMap();
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();

    	try{
        	tycxEditDao.updateOneRow(sqlxh,map);
        	JSONDATA.put("state", 1);
    	}catch(Exception e){
        	JSONDATA.put("state", 0);
        	JSONDATA.put("message",e.getMessage());
    	}
    	 
    	HashMap<String, Object> resMap = new HashMap<String, Object>();
    	
    	resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resMap);
		return resEvent;
    	
    }    
    
    public ResponseEvent insertOneRow(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	HashMap<String,Object> map = requestEvent.getRequestMap();
    	
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();

    	String sqlxh = (String) map.get("sqlxh");
    	
    	try{
        	tycxEditDao.insertOneRow(sqlxh,map);
        	JSONDATA.put("state", 1);
    	}catch(Exception e){
        	JSONDATA.put("state", 0);
        	JSONDATA.put("message",e.getMessage());
    	}

    	HashMap<String, Object> resMap = new HashMap<String, Object>();
    	
    	resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resMap);
		return resEvent;
		
    }
    
    public ResponseEvent deleRow(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	HashMap<String,Object> map = requestEvent.getRequestMap();
    	
    	String sqlxh = (String) map.get("sqlxh");
    	
    	String jsonData = (String) map.get("jsonData");
    	
    	jsonData = jsonData.replace("^", "%").replace("+", "^$^");
		jsonData = URLDecoder.decode(URLDecoder.decode(jsonData,"UTF-8"),"UTF-8");
		jsonData = jsonData.replace("^$^", "+");
		
		JSONArray array = JSONArray.parseArray(jsonData);

    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();

    	try{
        	tycxEditDao.deleteRowList(sqlxh,array);
        	JSONDATA.put("state", 1);
    	}catch(Exception e){
        	JSONDATA.put("state", 0);
        	JSONDATA.put("message",e.getMessage());
    	}


    	HashMap<String, Object> resMap = new HashMap<String, Object>();
    	
    	resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resMap);
		return resEvent;
    	
    }
    
    public ResponseEvent updatelist(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	
    	
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
    

    
    
}
