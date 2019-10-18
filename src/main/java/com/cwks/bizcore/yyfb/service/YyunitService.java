package com.cwks.bizcore.yyfb.service;

import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.dao.JdbcDao;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.service.impl.BaseService;
import com.cwks.bizcore.yyfb.dao.YyfbDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service("YyunitService")
public class YyunitService  {
	UserContext ctx;
	@Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private YyfbDao FxyyDao;
    
	public ResponseEvent openYy(RequestEvent requestEvent) throws Exception {
   	    ResponseEvent resEvent = new ResponseEvent();
   	    
   	    String fxyy_id = (String) requestEvent.getRequestMap().get("fxyy_id");
   	    
   	    String sql=jdbcDao.getSql("SQL_YYFB_queryJbxx");
   	    ArrayList<Object> param=new ArrayList<Object>();
		param.add(fxyy_id);
   	 	HashMap<String,String> data = (HashMap<String, String>) FxyyDao.executeSql2(sql, param); 
       
   	 	String yyurl = data.get("YYURL");
   	 	String zt =data.get("ZT_BJ"); 
   	 	
   	    HashMap<String,String> resmap = new HashMap<String, String>();
   	 	
   	 	if(!"1".equals(zt)){
	   	 	resmap.put("errorinfo","当前应用尚未发布，无法使用！");
			resEvent.setResMap(resmap); 
			resEvent.setFwordPath("/public/js/common/jsp/error.jsp");
	 		return resEvent;
   	 	}
   	 	
   	 	ctx = requestEvent.getCtx();
   	    
		Map<String, Object> userInfo = new HashMap<String, Object>();
		 
		if(ctx!=null){
			
			userInfo = ctx.getUserinfo();
			
		}else{
			
			resmap.put("errorinfo","当前系统登陆失效请重新登陆！");
			resEvent.setResMap(resmap); 
			resEvent.setFwordPath("/public/js/common/jsp/error.jsp");
	 		return resEvent;
	 		
		}
		
		String swrysf_dm = (String) userInfo.get("swrysf_dm");
		String swry_dm = (String)userInfo.get("userId");
		
		if(yyurl!=null){
			yyurl = yyurl.replace("【CSSNJ_YYID】", fxyy_id).replace("【CSSNJ_SWRYSF】", swrysf_dm).replace("【CSSNJ_SWRY】", swry_dm);
		}
		resmap.put("yyurl", yyurl);
   	    
 		resEvent.setResMap(resmap);
 		if(yyurl.indexOf("http.action?tld=YyunitService_openYy")<0){ // 保护当前应用链接
 			resEvent.setFwordPath("/biz/bizcore/sjyy/yyfb/html/yyunit001.html");
 		}
 		return resEvent;
    }
	
	public ResponseEvent Gyxx(RequestEvent requestEvent) throws Exception {
		
   	    ResponseEvent resEvent = new ResponseEvent();
   	    
   	    String fxyy_id = (String) requestEvent.getRequestMap().get("fxyy_id");
   	    
   	    String sql=jdbcDao.getSql("SQL_YYFB_queryJbxx");
   	    ArrayList<String> param=new ArrayList<String>();
		param.add(fxyy_id);
   	 	HashMap<String,String> data = (HashMap<String, String>) FxyyDao.executeSql2(sql, param); 
       
   	 	String yyurl = data.get("GYURL");
   	 	String zt = data.get("ZT_BJ"); 
   	 	
   	    HashMap<String,String> resmap = new HashMap<String, String>();
   	 	
   	 	if(!"1".equals(zt)){
	   	 	resmap.put("errorinfo","当前应用尚未发布，无法使用！");
			resEvent.setResMap(resmap); 
			resEvent.setFwordPath("/public/js/common/jsp/error.jsp");
	 		return resEvent;
   	 	}
   	 	
   	 	ctx = requestEvent.getCtx();
   	    
		Map<String, Object> userInfo = new HashMap<String, Object>();
		 
		if(ctx!=null){
			
			userInfo = ctx.getUserinfo();
			
		}else{
			
			resmap.put("errorinfo","当前系统登陆失效请重新登陆！");
			resEvent.setResMap(resmap); 
			resEvent.setFwordPath("/public/js/common/jsp/error.jsp");
	 		return resEvent;
	 		
		}
		
		String swrysf_dm = (String) userInfo.get("swrysf_dm");
		String swry_dm = (String) userInfo.get("userId");
		
		yyurl = yyurl.replace("【CSSNJ_YYID】", fxyy_id).replace("【CSSNJ_SWRYSF】", swrysf_dm).replace("【CSSNJ_SWRY】", swry_dm);
		resmap.put("yyurl", yyurl);
   	    
 		resEvent.setResMap(resmap);
 		if(yyurl.indexOf("http.action?tld=YyunitService_openYy")<0){ // 保护当前应用链接
 			resEvent.setFwordPath("/biz/bizcore/sjyy/yyfb/html/yyunit001.html");
 		}
 		return resEvent;
    }
	 
	

	
}
