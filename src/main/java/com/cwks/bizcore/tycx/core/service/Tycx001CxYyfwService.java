package com.cwks.bizcore.tycx.core.service;

import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.service.impl.BaseService;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.tycx.core.dao.Tycx001CxCxdyDao;
import com.cwks.bizcore.tycx.core.dao.Tycx001CxYyfwDao;
import com.cwks.common.service.impl.BaseServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Service("tycx001CxYyfwService")
public class Tycx001CxYyfwService   extends BaseServices {
    private static Logger logger = LoggerFactory.getLogger(Tycx001CxYyfwService.class);

 
    @Autowired 
    private Tycx001CxYyfwDao  tycx001CxYyfwDao ;
    
    @Autowired
    private Tycx001CxCxdyDao tycx001CxCxdyDao;
    
    public ResponseEvent getCxYyfw(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap = new HashMap<String, Object>(); 
    	
    	String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");
    	
    	Map map= tycx001CxYyfwDao.getCxYyfw(sqlxh);
     
    	HashMap<String,Object> resMap = new HashMap<String, Object>();
    	resMap.put("JSONDATA", JsonUtil.toJson(map));
    	resEvent.setResMap(resMap);
    	
    	return resEvent;
	} 
    
    public ResponseEvent insertCxYy(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap = new HashMap<String, Object>(); 
    	
    	ctx = requestEvent.getCtx();
 		Map<String, Object> userInfo = new HashMap<String, Object>();
 		 
 		if(ctx!=null){		
 			userInfo = ctx.getUserinfo();			 
 		}
    	
    	String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");
    	
//    	HashMap<String, Object> cxdyMap=(HashMap<String, Object>)  tycx001CxCxdyDao.queryCxdy(sqlxh);
		HashMap<String, Object> cxdyMap = new HashMap<String, Object>();
		Map map1 = tycx001CxCxdyDao.queryCxdy(sqlxh);
		Set<Map.Entry<String, String>> s=map1.entrySet();
		for (Map.Entry<String, String> entry : s) {
			cxdyMap.put(entry.getKey(),entry.getValue());
		}




    	String fxyy_id= tycx001CxYyfwDao.insertCxYy(cxdyMap,userInfo);
    	
    	HashMap<String,Object> map = new HashMap<String, Object>();
    	
    	map.put("fxyy_id", fxyy_id);
    	map.put("success", 1);
     
    	HashMap<String,Object> resMap = new HashMap<String, Object>();
    	resMap.put("JSONDATA", JsonUtil.toJson(map));
    	resEvent.setResMap(resMap);
    	
    	return resEvent;
    	
    }
     


    
}
