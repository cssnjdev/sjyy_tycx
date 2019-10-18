package com.cwks.bizcore.tycx.core.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.tycx.core.dao.Tycx004YhsDao;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.service.impl.BaseService;
import com.cwks.common.service.impl.BaseServices;
import com.cwks.common.dao.JdbcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service("tycx004YhsService")
public class Tycx004YhsService extends BaseServices {
	private static Logger logger = LoggerFactory.getLogger(Tycx004RYhsJcxxService.class);

	@Autowired
	private Tycx004YhsDao tycx004YhsDao;
	//初始化方法
	 public ResponseEvent init2(RequestEvent requestEvent) throws Exception {
		 ResponseEvent resEvent = new ResponseEvent();
	    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
			resEvent.setFwordPath("/biz/core/ext/tycx/tycx004/jsp/tycx004_yhsxt.html");
			resEvent.setResMap(reqmap);
			return resEvent;
	 }
	 public ResponseEvent init(RequestEvent requestEvent) throws Exception {
		 ResponseEvent resEvent = new ResponseEvent();
		 HashMap resmap = new HashMap();
		 String drillParams=(String) requestEvent.getRequestMap().get("drillParams");
		 String nsrsbh="";
			String djxh="";
			if (!TycxUtils.isEmpty(drillParams)) {
				drillParams = URLDecoder.decode(URLDecoder.decode(drillParams,
						"UTF-8"), "UTF-8");
				final List<Map> drillarray = JsonUtil.toListMap(drillParams);
				for (int i = 0; i < drillarray.size(); i++) {
					Map extraMap=drillarray.get(i);
					final String name = (String) extraMap.get("name");
					if("NSRSBH".equals(name)){
						nsrsbh=(String) extraMap.get("value");
						continue;
					}else if("DJXH".equals(name)){
						djxh=(String) extraMap.get("value");
						continue;
					}
					
				}
			}
			 String sjymc=(String) requestEvent.getRequestMap().get("sjymc");
			 if(TycxUtils.isEmpty(sjymc)){
				 sjymc="hcq105";
			 }
			
			 List resultList=tycx004YhsDao.executeProcedure(sjymc, djxh);
			 String isSuccess= resultList.get(1).toString();
			 if("0".equals(isSuccess)){
				 String sql=jdbcDao.getSql("cx_yhs_jbxx");
				 ArrayList param=new ArrayList();
				 List list=tycx004YhsDao.executeSql(sql,param,sjymc);
				 resmap.put("JSONDATA",   JsonUtil.toJson(list.get(0)));
			 }
//			 resmap.put("djxh", djxh);
//			 resmap.put("nsrsbh", nsrsbh);
			 resEvent.setResMap(resmap);
			// resEvent.setFwordPath("/biz/core/ext/tycx/tycx004/jsp/tycx004_yhsxt.jsp");
			return resEvent;
	 }
	  public ResponseEvent initView(RequestEvent requestEvent) throws Exception {
		  ResponseEvent resEvent = new ResponseEvent();
	        HashMap reqmap = new HashMap();
	        String cxrqq=(String) requestEvent.getRequestMap().get("cxrqq");
		    String cxrqz=(String) requestEvent.getRequestMap().get("cxrqz");		 
		    String djxh =(String) requestEvent.getRequestMap().get("djxh");
		    String cxlx=(String) requestEvent.getRequestMap().get("cxlx");		    
		    String nsrsbh=(String) requestEvent.getRequestMap().get("nsrsbh");
		    String lx=(String) requestEvent.getRequestMap().get("lx");//1是取文件夹，0是取子节点
		    String sjcxxmdm=(String) requestEvent.getRequestMap().get("sjjdDm");
		    String sql="";
		    ArrayList param=new ArrayList();
		    if("1".equals(lx)){//查询文件夹
		    	sql=jdbcDao.getSql("SQL_CX_YHSCXWJJ");
		    }else if("0".equals(lx)){
		    	param.add(cxlx);
		    	param.add(sjcxxmdm);
		    	sql=jdbcDao.getSql("cx_yhscx_queryYhscxZxm");
		    }else{
		    	param.add(cxlx);
		    	sql=jdbcDao.getSql("cx_yhscx_queryYhscxList");
		    }
		    List list=tycx004YhsDao.executeSql(sql, param,"");
		    reqmap.put("JSONDATA", JsonUtil.toJson(list));
	        resEvent.setResMap(reqmap);      
	        return resEvent;
	  }
	  public ResponseEvent initYhscxView(RequestEvent requestEvent) throws Exception {
		  ResponseEvent resEvent = new ResponseEvent();
	        HashMap reqmap = new HashMap();
	        resEvent.setFwordPath("/biz/core/ext/tycx/tycx004/jsp/tycx004_yhscx_main.jsp");
	        return resEvent;
	  }



}
