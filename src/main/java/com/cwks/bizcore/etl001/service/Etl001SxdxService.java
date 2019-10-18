package com.cwks.bizcore.etl001.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.daoUtil.CssnjDao;
import com.cwks.bizcore.persistence.SpManager;
import com.cwks.bizcore.persistence.outtype.BaseOutParam;
import com.cwks.bizcore.persistence.outtype.OracleCursor;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.jdbc.rowset.CachedRowSet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 失效对象查询
 * @author Administrator
 *
 */
@Service("etl001SxdxService")
public class Etl001SxdxService  {
	
	@Autowired
	private CssnjDao jdbcDao;
	
	private static Logger logger = LoggerFactory.getLogger(Etl001SxdxService.class);

	public ResponseEvent init(RequestEvent requestEvent) throws Exception {

		ResponseEvent resEvent = new ResponseEvent();
		
		resEvent.setFwordPath("/biz/core/ext/etl/etl001/jsp/sxdx.jsp");
		return resEvent;
	}
	
	public ResponseEvent query(RequestEvent requestEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String,Object> reqmap = requestEvent.getRequestMap();
 		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();

		String type = (String) reqmap.get("type");
		String yxx = (String)reqmap.get("yxx");
		
		BaseOutParam out1 = new BaseOutParam();// 单值success
	    BaseOutParam out2 = new BaseOutParam();// 单值message
 
		OracleCursor cur1 = new OracleCursor();
		OracleCursor cur2 = new OracleCursor();

		ArrayList<Object> params= new ArrayList<Object>();

		params.add(type); 
		params.add(yxx);
		params.add(out1); 
		params.add(out2);
		params.add(cur1);
		params.add(cur2); 
		
		String success ="0";
		String message ="";
		
		try{
			SpManager.callProc(jdbcDao.getConnection(), "PKG_ETL_INFO.P_ETL_JK_OBJ", params);
			
			CachedRowSet headSet = cur1.getRowset();
			ArrayList<HashMap<String,String>> headList = new ArrayList<HashMap<String,String>>();
			HashMap<String,String> map = new HashMap<String, String>();
			
			ArrayList<String> codes = new ArrayList<String>();
			
			while(headSet.next()){
				
				map = new HashMap<String, String>();
				map.put("code", headSet.getString("code"));
				map.put("caption", headSet.getString("caption"));
				
				codes.add(headSet.getString("code"));
				
				headList.add(map);
				
			}
			
			CachedRowSet dataSet = cur2.getRowset();
			ArrayList<HashMap<String,String>> dataList = new ArrayList<HashMap<String,String>>();
			
	 		while(dataSet.next()){
				
				map = new HashMap<String, String>();
				
				for(String code:codes){
					map.put(code, dataSet.getString(code));
				}
				 
				dataList.add(map);
				
			}
	 		
	 		JSONDATA.put("headList", headList);
	 		JSONDATA.put("dataList", dataList);
	 		
	 		success = (String) out1.getValue();
	 		message = (String) out2.getValue();
	 		
		}catch (Exception e) {
			 
			success="0";
			message=e.getMessage();
		}
	 
		JSONDATA.put("success", success);
 		JSONDATA.put("message", message);
 
 		HashMap<String,Object> resmap = new HashMap<String, Object>();
		
 		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
 		
 		resEvent.setResMap(resmap);
 		
		return resEvent;
		
	}



}
