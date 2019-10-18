package com.cwks.bizcore.tycx.core.dao;

//
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.common.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("all")
public class Tycx001CxYyfwDao{
	@Autowired
	JdbcDao jdbcDao;
    public Map getCxYyfw(String sqlxh){
    	
    	Map<String,Object> map = new  HashMap<String, Object>();
    	
    	String sql =  " select * from cx_cxdy_fxyy a,t_yyfw_fxyy b where sqlxh = ? and a.fxyy_id=b.fxyy_id and xybj='Y' \n";
    	
	  	ArrayList<Object> param = new ArrayList<Object>();
	  	param.add(sqlxh);
	  	
	  	List list = jdbcDao.queryforlist(sql,param);
	  	
	  	if(list.size()>0){
	  		return (Map) list.get(0);
	  	}else{
	  		return null;
	    	
	  	}
	  	
	
    }
    
    public String insertCxYy(HashMap<String,Object> cxdyMap, Map<String, Object> userInfo){
    	
    	String sqlxh = cxdyMap.get("SQLXH").toString();
    	
    	String yyurl = "/tykf/request_http?tld=Tycx002DzcxService_initView&sqlxh="+sqlxh+"&fxyyid=【CSSNJ_YYID】";
    	
    	String gyurl = "/tykf/request_http?tld=TycxInfoService_showChart&sqlxh="+sqlxh;
    	
    	String fxyy_id = null;

    	String sqlmc =  cxdyMap.get("SQLMC").toString();
    	
    	String sql = " delete  cx_cxdy_fxyy where sqlxh = ?  ";
    	ArrayList<Object> param = new ArrayList<Object>();
	  	param.add(sqlxh);

		jdbcDao.update(sql,param);
	  	
		try{
			
		  	fxyy_id = UUIDGenerator.getUUID();
	    	
		  	sql =  
				" insert into t_yyfw_fxyy \n" + 
				"  (fxyy_id,  \n" + 
				"   fxyy_mc,  \n" + 
				"   fxyylx_dm,\n" + 
				"   yxbj,     \n" + 
				"   yyurl,\n" + 
				"   fbrdm,\n" + 
				"   fb_sj,\n" + 
				"   zt_bj,\n" + 
				"   gy_valid,\n" + 
				"   gyurl,\n" + 
				"   banben,\n" + 
				"   gnlj)\n" + 
				"values\n" + 
				"  (?, ?, '01', 'Y', ?, ?, sysdate, '0', 'N', ?,1,'01')";
 
		  	param.clear();
		  	param.add(fxyy_id);
		  	param.add(sqlmc);
		  	param.add(yyurl);
		  	param.add(userInfo.get("userId"));
		  	param.add(gyurl);

			jdbcDao.update(sql,param);
			
			sql = "insert into cx_cxdy_fxyy (sqlxh, fxyy_id, xybj) values (?, ?, 'Y')";
			param.clear();
			param.add(sqlxh);
			param.add(fxyy_id);
			jdbcDao.update(sql,param);
			
			return fxyy_id;
    	
		}catch(Exception e){
			
			return null;
			
		}
		
    }
	
}
