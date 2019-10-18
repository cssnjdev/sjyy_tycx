package com.cwks.bizcore.tycx.core.service;

import com.cwks.bizcore.daoUtil.CssnjDao;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.service.impl.BaseService;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.common.service.impl.BaseServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("TycxDemoService")
public class TycxDemoService   extends BaseServices {
	
    @Autowired
    private CssnjDao cssnjDao;
    private static Logger logger = LoggerFactory.getLogger(TycxDemoService.class);
    
    public ResponseEvent dqsr(RequestEvent requestEvent)throws Exception {
    	
    	  ResponseEvent resEvent = new ResponseEvent();
          HashMap reqmap = new HashMap();
          
          Map userinfo=new HashMap();
          
          this.ctx = requestEvent.getCtx();
          
          String znfw = null;
          
          if(ctx!=null){
          	userinfo = ctx.getUserinfo();
          	znfw = (String) userinfo.get("znfw");
          }
          
          HashMap<String,Object> JSONDATA = new HashMap<String,Object>();
          
          if(znfw!=null){
        	  
        	  ArrayList<String> params =new ArrayList<String>();
          	  params.add(znfw);
        	  
          	  try{
          		  
	        	  String sql = getSql("11");
	        	  List list1 = cssnjDao.queryforlist(sql,params);
	        	  
	        	  JSONDATA.put("state","0");
	        	  JSONDATA.put("list1", list1);
                  if("2320292".equals(znfw)){
                	  JSONDATA.put("type", "2");
                  }else{
                	  JSONDATA.put("type", "1");
                  }
                  
                  JSONDATA.put("state","1");
                  
          	  }catch(Exception e){
          		  
          		  JSONDATA.put("state","0");
 
          	  }
        	  
          	  
          }else{
        	  JSONDATA.put("state","0");
          }
           
           
          reqmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
          resEvent.setResMap(reqmap);       					
          
          return resEvent;
    }
    
    /**
     * 累积征收收入
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent ljzs(RequestEvent requestEvent)throws Exception {
    	
    	 ResponseEvent resEvent = new ResponseEvent();
    	 
         HashMap<String, Object> reqmap = new HashMap<String, Object>();
         HashMap<String, Object> userinfo=new HashMap<String, Object>();
         
         this.ctx = requestEvent.getCtx();
         
         String znfw = null;
         
         if(ctx!=null){
         	userinfo = (HashMap<String, Object>) ctx.getUserinfo();
         	znfw = (String) userinfo.get("znfw");
         }
         
         HashMap<String,Object> JSONDATA = new HashMap<String,Object>();

         if(znfw!=null){
       	  
        	 ArrayList<String> params =new ArrayList<String>();
         	 params.add(znfw);
         	  
         	 try{
         		 
	        	  String sql = getSql("13");						
	        	  Map sr = cssnjDao.queryformap(sql,params); 	
	        	  												
	        	  sql = getSql("14");		
	        	  params.add(znfw);
	        	  Map sr2 = cssnjDao.queryformap(sql,params);		
	        	  
	        	  JSONDATA.put("sr",sr);	
	        	  JSONDATA.put("sr2",sr2);	
	        	  
	        	  JSONDATA.put("state","1");
	        	  
         	 }catch(Exception e){
         		  JSONDATA.put("state","0");
         	 }
         	 
         }else{
        	 JSONDATA.put("state","0");
         }
         reqmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
         resEvent.setResMap(reqmap);       
         return resEvent;
         
    }
    
    /**
     * 累积征收收入
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent srgc(RequestEvent requestEvent)throws Exception {
    	
    	 ResponseEvent resEvent = new ResponseEvent();
    	 
         HashMap<String, Object> reqmap = new HashMap<String, Object>();
         HashMap<String, Object> userinfo=new HashMap<String, Object>();
         
         this.ctx = requestEvent.getCtx();
         
         String znfw = null;
         
         if(ctx!=null){
         	userinfo = (HashMap<String, Object>) ctx.getUserinfo();
         	znfw = (String) userinfo.get("znfw");
         }
         
         HashMap<String,Object> JSONDATA = new HashMap<String,Object>();

         if(znfw!=null){
       	  
        	 ArrayList<String> params =new ArrayList<String>();
         	 params.add(znfw);
         	  
         	 try{
         		 
	        	  String sql = getSql("15");							
	        	  List sr = cssnjDao.queryforlist(sql,params); 		
	        	  JSONDATA.put("sr",sr);						
	        	  JSONDATA.put("state","1"); 					
	        	  
         	 }catch(Exception e){									
         		  JSONDATA.put("state","0");	
         	 }
         	 
         }else{
        	 JSONDATA.put("state","0");
         }
         
         reqmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
         
         resEvent.setResMap(reqmap);       
         return resEvent;
         
    }
    public ResponseEvent updateZcptfxzc(RequestEvent requestEvent)throws Exception {
    	
	   	 ResponseEvent resEvent = new ResponseEvent();
	   	 
	   	 String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");
	   	 
	   	 String YYURL = (String) requestEvent.getRequestMap().get("YYURL");
	   	 
	   	 String URL = (String) requestEvent.getRequestMap().get("URL");
	   	 
	   
	   	
	     HashMap<String,Object> JSONDATA = new HashMap<String, Object>();

    	 try{
    		
		 	String sql = "update t_Yyfw_Fxyy set yyurl=? where  fxyy_id ='6A3082D3A20A1C05E050188D16D53925'" ;
	   	 
    	   	ArrayList<Object> params = new ArrayList<Object>();
    	   	params.add(YYURL);
		 
			cssnjDao.update(sql,params);
    		JSONDATA.put("state", 1);
 
		   		sql = "update t_Yyfw_Fxyy set yyurl=? where  fxyy_id ='7a5668087d904b9982612269b61a9a29'";

	   		params.clear();
	   		params.add(URL);
   	 
   	     	cssnjDao.update(sql,params);
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
    
    public String getSql(String dm){
    	
    	String sql = null;
    	String sqlStr = "select xsxx_sql from db_yshj.r_yshj_syxx where zxxdl_dm = ? and syid ='1' ";
    	
    	ArrayList<String> params =new ArrayList<String>();
    	params.add(dm);
    	Map map =cssnjDao.queryformap(sqlStr,params);
    	sql = (String) map.get("xsxx_sql");
		return sql;
    	
    	
    }
    
    /**
     * 使用查询
     */
    public ResponseEvent getDataBySqlxh(RequestEvent requestEvent)throws Exception {

	   	 ResponseEvent resEvent = new ResponseEvent();
	   	 
	   	 String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");
	   	 
	   	 String sql = " select sqlstr from cx_cxdy where sqlxh = ? ";
	  
	   	 ArrayList<String> params =new ArrayList<String>();
	   	 params.add(sqlxh);
	   	 
	     Map map = cssnjDao.queryformap(sql,params);

	     String sqlstr = map.get("SQLSTR").toString();
	     
	     List list = cssnjDao.queryforlist(sqlstr);
	     
	     HashMap<String,Object> JSONDATA = new HashMap<String, Object>();

	     JSONDATA.put("data", list);
	     
	     JSONDATA.put("state", "1");

	     HashMap<String, Object> resMap = new HashMap<String, Object>();
	     
    	 resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	 
     	 resEvent.setResMap(resMap);
     	 
		 return resEvent;
		 
    }
     
    

	
}