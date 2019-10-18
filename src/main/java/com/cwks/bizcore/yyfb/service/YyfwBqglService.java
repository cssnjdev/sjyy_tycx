package com.cwks.bizcore.yyfb.service;

import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.dao.JdbcDao;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.service.impl.BaseService;
import com.cwks.bizcore.comm.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service("YyfwBqglService")
public class YyfwBqglService  {

    private static Logger logger = LoggerFactory.getLogger(YyfwBqglService.class);
	UserContext ctx;
    @Autowired
    private JdbcDao jdbcDao;

    public ResponseEvent init(RequestEvent requestEvent) throws Exception {
    	
    	 ResponseEvent resEvent = new ResponseEvent();
    	 String fxyy_id = (String) requestEvent.getRequestMap().get("fxyy_id");
 		 HashMap<String, String> resMap = new HashMap<String, String>(); 
 		 
 		 resMap.put("fxyy_id", fxyy_id);
 		 
 		 resEvent.setResMap(resMap);
 		 resEvent.setFwordPath("/biz/bizcore/sjyy/yyfb/html/yywBqgl.html");
    	 return resEvent;
    	 
    } 

    /**
     * 查询标签分类
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent searchBqFl(RequestEvent requestEvent) throws Exception {
    	
    	 ResponseEvent resEvent = new ResponseEvent();
    	
    	 String sql =" select bqfl_dm,bqfl_mc from a_gy_bq_fl ";
    	 
    	 SqlRowSet rs = jdbcDao.queryforRowset(sql);
    	 
    	 ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
    	 
    	 HashMap<String,String> map = null;
    	 
    	 while(rs.next()){
    		 map = new HashMap<String, String>();
    		 map.put("bqfl_dm", rs.getString("bqfl_dm"));
    		 map.put("bqfl_mc", rs.getString("bqfl_mc"));
    		 list.add(map);
    	 }
    	 
    	 HashMap<String,Object> resMap = new HashMap<String, Object>();
    	 
    	 resMap.put("JSONDATA", JsonUtil.toJson(list));
    	 resEvent.setResMap(resMap);
    	 
    	 return resEvent;
    	 
    }
    
    /**
     * 查询所有的标签
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent searchAllBq(RequestEvent requestEvent) throws Exception {
    	
    	 ResponseEvent resEvent = new ResponseEvent();
    	
   	 	 String sql =" select bq_id,bqfl_dm,bq_mc from t_yyfw_bq ";

   	 	 SqlRowSet rs = jdbcDao.queryforRowset(sql);
    	
	   	 ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		 
		 HashMap<String,String> map = null;
		 
		 while(rs.next()){
			 map = new HashMap<String, String>();
			 map.put("bq_id", rs.getString("bq_id"));
			 map.put("bqfl_dm", rs.getString("bqfl_dm"));
			 map.put("bq_mc", rs.getString("bq_mc"));
			 list.add(map);
		 }
		 
		 HashMap<String,Object> resMap = new HashMap<String, Object>();
		 
		 resMap.put("JSONDATA", JsonUtil.toJson(list));
		 resEvent.setResMap(resMap);
   	 	
    	 return resEvent;
    	
    }
    
    /**
     * 添加标签
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent addBq(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	String bq_mc = (String) requestEvent.getRequestMap().get("bq_mc");
    	String bqfl_dm = (String) requestEvent.getRequestMap().get("bqfl_dm");
    	
    	String getBqid = "  select nvl(max(bq_id),0)+1 bq_id from t_yyfw_bq ";
    	SqlRowSet rs = jdbcDao.queryforRowset(getBqid);
    	 
    	String bq_id =null;
    	if(rs.next()){
    		bq_id = rs.getString("bq_id");
    	}
    	
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();

    	
    	if(bq_id!=null){
	    	
	    	 try{	
	    		 
		    	ctx = requestEvent.getCtx();
		    	
		    	Map<String, Object> userInfo = new HashMap<String, Object>();
				if(ctx!=null){
					userInfo = ctx.getUserinfo();
				}
		    	
		  	 	String sql =" insert into t_yyfw_bq (bq_id,bqfl_dm,bq_mc,lr_sj,lrry_dm,lrjg_dm) values(?,?,?,sysdate,?,?)  ";
		  	 	
		  	 	ArrayList<String> params = new ArrayList<String>();
		  	 	params.add(bq_id);
		  	 	params.add(bqfl_dm);
		  	 	params.add(bq_mc);
		  	 	params.add((String) userInfo.get("userId"));
		  	 	params.add((String) userInfo.get("swjg_dm"));
		
		  	 	jdbcDao.update(sql,params);
	  	 	
		  	 	JSONDATA.put("success", "1");
		  		JSONDATA.put("bq_id", bq_id);
		    	JSONDATA.put("message", "添加成功！");

	    	 }catch (Exception e) {
	    		
	    		JSONDATA.put("success", "0");
			    JSONDATA.put("message", "添加失败！");
	    		 
	 		 }
    	 
    	}
    	
		HashMap<String,Object> resMap = new HashMap<String, Object>();
    	
    	resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resMap);
  	 	
		return resEvent;
  	 	
    }
    
    /**
     * 
     * @param requestEvent
     * @return
     * @throws Exception
     * 查询单个分析应用包含的标签
     * 
     */
    public ResponseEvent searchYYbq(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	String fxyy_id = (String) requestEvent.getRequestMap().get("fxyy_id");
    	
    	String sql = "select a.fxyyid,b.bq_id,b.bqfl_dm,b.bq_mc from t_yyfw_fxyy_bq a ,t_yyfw_bq b where a.bqid = b.bq_id and a.fxyyid = ? ";
    	
    	ArrayList<String> params = new ArrayList<String>();
    	
    	params.add(fxyy_id);
    	
  	 	SqlRowSet rs = jdbcDao.queryforRowset(sql,params);
  	 	
  	 	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		 
		HashMap<String,String> map = null;
  	 	
  	 	while(rs.next()){
			 map = new HashMap<String, String>();
			 map.put("fxyyid", rs.getString("fxyyid"));
			 map.put("bq_id", rs.getString("bq_id"));
			 map.put("bqfl_dm", rs.getString("bqfl_dm"));
			 map.put("bq_mc", rs.getString("bq_mc"));
			 list.add(map);
		}
		 
		HashMap<String,Object> resMap = new HashMap<String, Object>();
		 
		resMap.put("JSONDATA", JsonUtil.toJson(list));
		resEvent.setResMap(resMap);
  	 	
		return resEvent;
   	
    }
    
    /**
     * 为应用添加标签
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent saveYyBq(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
    	
    	String fxyy_id = (String) requestEvent.getRequestMap().get("fxyy_id");
    	String bqs = (String) requestEvent.getRequestMap().get("bqs");
    	
    	String[] bqArr = bqs.split(",");
    	
    	String sql = "delete t_yyfw_fxyy_bq where fxyyid =? \n";
    	
    	try{
    		
	    	ArrayList<String> params = new ArrayList<String>();
	    	
	    	params.add(fxyy_id);
	    	jdbcDao.update(sql,params);
	    	
	    	sql = "insert into t_yyfw_fxyy_bq (bqid,fxyyid,fwfl) values (?, ?, ?) \n";
	    	
	    	for(String bq:bqArr){
	    		params.clear();
	    		params.add(bq);
	    		params.add(fxyy_id);
	    		params.add("1");
		    	jdbcDao.update(sql,params);
	    	}
	    	
	    	JSONDATA.put("success", "1");
	    	JSONDATA.put("message", "添加成功！");

    	}catch (Exception e) {
			// TODO: handle exception
    		JSONDATA.put("success", "0");
	    	JSONDATA.put("message", "添加失败！服务器添加应用标签异常。");
		}
    	
    	HashMap<String,Object> resMap = new HashMap<String, Object>();
		 
		resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resMap);
  	 	
		return resEvent;
    	
    }
    
    public ResponseEvent removeYYbq(RequestEvent requestEvent) throws Exception {
    
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	String fxyy_id = (String) requestEvent.getRequestMap().get("fxyy_id");
    	String bqid = (String) requestEvent.getRequestMap().get("bqid");

    	HashMap<String,Object> JSONDATA = new HashMap<String, Object>();

    	String sql = "delete t_yyfw_fxyy_bq where fxyyid =? and bqid = ? \n";

    	ArrayList<String> params = new ArrayList<String>();
    	
    	params.add(fxyy_id);
    	params.add(bqid);

    	try{
    		jdbcDao.update(sql,params);
    		JSONDATA.put("success", "1");
	    	JSONDATA.put("message", "删除成功！");
    	}catch (Exception e) {
    		JSONDATA.put("success", "0");
	    	JSONDATA.put("message", "添加失败！服务器添加应用标签异常。");
		}

    	HashMap<String,Object> resMap = new HashMap<String, Object>();
		 
		resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resMap);
		
    	return resEvent;
    	
    }
    
    

    
    
}
