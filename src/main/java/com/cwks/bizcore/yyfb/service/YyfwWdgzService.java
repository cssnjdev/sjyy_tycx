package com.cwks.bizcore.yyfb.service;

import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.service.impl.BaseService;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.common.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("YyfwWdgzService")
public class YyfwWdgzService  {
	UserContext ctx;
	@Autowired
    private JdbcDao jdbcDao;
 
	public ResponseEvent init(RequestEvent requestEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String,Object> resmap = new HashMap<String,Object>();
		 
		this.ctx = requestEvent.getCtx();
 		
		Map<String, Object> userInfo = new HashMap<String, Object>();
		 
		if(ctx!=null){
			
			userInfo = ctx.getUserinfo();
			resmap.put("userInfo",userInfo);
			 
		}else{
			
			resmap.put("errorinfo", "系统登陆失效！请重新登陆");
			resEvent.setResMap(resmap);
			resEvent.setFwordPath("/public/js/common/jsp/error.jsp");
	 		return resEvent;
			
		}
		 
		resEvent.setFwordPath("/biz/bizcore/sjyy/yyfb/html/yyfwWdgz.html");
 		return resEvent;
 		
    }
  
	
	public ResponseEvent searchYy(RequestEvent requestEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String,Object> resmap = new HashMap<String,Object>();

		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		  
		ctx = requestEvent.getCtx();
		
		Map userInfo = new HashMap<String, Object>();
		
		String swry_dm = null;
		
		if(ctx!=null){
			userInfo = ctx.getUserinfo();
			swry_dm = (String) userInfo.get("userId");
		}
		ArrayList<String> param = new ArrayList<String>();
		param.add(swry_dm);
		
		String sql = " select fxyy_id, \n" +
					 "		fxyy_mc    \n" +
					 "		from  t_yyfw_fxyy a  \n"+
				     "  where gy_valid='Y'		 \n"+
				     "	  and not exists ( 		 \n"+
				     " 		select 1 from t_yyfw_fxyy_yhgz b where a.fxyy_id=b.fxyy_id and b.swry_dm = ? and b.pxxh <= 3  \n"+
				     "  ) " ;
		
		SqlRowSet srs = jdbcDao.queryforRowset(sql,param);
		HashMap<String,String> map = null;
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		
		while(srs.next()){
			map =new HashMap<String, String>();
			map.put("fxyy_id", srs.getString("fxyy_id"));
			map.put("fxyy_mc", srs.getString("fxyy_mc"));
			list.add(map); 
		}
 		
		sql = " select a.fxyy_id, a.fxyy_mc  	\n" + 
			  "   from t_yyfw_fxyy a, 			\n" + 
			  "		   t_yyfw_fxyy_yhgz b		\n" + 
			  "  where gy_valid='Y'             \n" +
			  "    and a.fxyy_id = b.fxyy_id	\n" + 
			  "    and b.swry_dm = ? \n" + 
			  "    and b.pxxh <= 3   \n" + 
			  "  order by b.pxxh       " ;
 
		
		srs = jdbcDao.queryforRowset(sql,param);
		
		List<HashMap<String,String>> toplist = new ArrayList<HashMap<String,String>>();

		while(srs.next()){
			
			map =new HashMap<String, String>();
			map.put("fxyy_id", srs.getString("fxyy_id"));
			map.put("fxyy_mc", srs.getString("fxyy_mc"));
			toplist.add(map); 
			
		}
		
		JSONDATA.put("list", list);
		JSONDATA.put("toplist", toplist);
		JSONDATA.put("success", "1"); 
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);
		return resEvent;
		
	}
	

	public ResponseEvent saveWdgz(RequestEvent requestEvent) throws Exception {
		
	   ResponseEvent resEvent = new ResponseEvent();
   	    
	   HashMap<String,Object> reqmap= requestEvent.getRequestMap();
	   
	   String gzyy1 = (String) reqmap.get("gzyy1");
	   String gzyy2 = (String) reqmap.get("gzyy2");
	   String gzyy3 = (String) reqmap.get("gzyy3");
	   
   	   HashMap<String,Object> resMap = new HashMap<String, Object>();
	   HashMap<String,String> JSONDATA = new HashMap<String, String>();
 
	   ctx = requestEvent.getCtx();
  	    
	   Map<String, Object> userInfo = new HashMap<String, Object>();
		 
	   if(ctx!=null){
			userInfo = ctx.getUserinfo();
	   }else{
		     
		   JSONDATA.put("success","0");
		   JSONDATA.put("message","当前系统登陆失效请重新登陆！");
		   resEvent.setResMap(resMap); 
 	 	   return resEvent;
 	 	   
	    }
	   
	   String swry_dm = (String)userInfo.get("userId");
  	   
	   HashMap<String,String> map = new HashMap<String, String>();
	    
	   map = new HashMap<String, String>();
	   
	   
	   try{
	   
		   String sql = "delete t_yyfw_fxyy_yhgz where swry_dm = ? "	;
		   ArrayList<Object> param = new ArrayList<Object>()			;
	  	   param.add(swry_dm)	     									;
	  	   jdbcDao.update(sql,param)									;
  	   
	  	   sql = " insert into t_yyfw_fxyy_yhgz (swry_dm,fxyy_id,pxxh) values(?,?,?) " ;
	  	   param.clear()											;
	  	   param.add(swry_dm);param.add(gzyy1);param.add("1")		;
	  	   jdbcDao.update(sql,param)								;
 
	  	   param.clear()											;
	  	   param.add(swry_dm);param.add(gzyy2);param.add("2")		;
	  	   jdbcDao.update(sql,param)								;
	  	   
	  	   param.clear()											;
	  	   param.add(swry_dm);param.add(gzyy3);param.add("3")		;
	  	   jdbcDao.update(sql,param)								;
	  	   JSONDATA.put("success","1");
	  	    
	   }catch (Exception e) {
		   
		   JSONDATA.put("success","0");
		   JSONDATA.put("message",e.getMessage());
		   
	   }
	    
	    
   	   resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
       resEvent.setResMap(resMap); 
 	   
       return resEvent;
	   	   
	}
	
	

	
}
