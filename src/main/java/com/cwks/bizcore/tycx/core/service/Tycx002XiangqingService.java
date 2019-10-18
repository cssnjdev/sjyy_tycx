package com.cwks.bizcore.tycx.core.service;

import com.cwks.common.service.impl.BaseServices;
import com.cwks.common.dao.JdbcDao;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.service.impl.BaseService;
import com.cwks.common.core.cache.CacheUtil;
import com.cwks.bizcore.tycx.core.dao.Tycx002XiangqingDao;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx002XiangqingPojo;
import com.cwks.bizcore.tycx.core.utils.YyfbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service("Tycx002XiangqingService")
public class Tycx002XiangqingService  extends BaseServices {

	private static Logger logger = LoggerFactory.getLogger(Tycx002XiangqingService.class);
	
    @Autowired
    private Tycx002XiangqingDao xiangqingDao;
    
    public ResponseEvent getXiangqing(RequestEvent reqEvent) throws Exception{
    	logger.debug("debugger "+this.getClass().getName());
    	
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        
        String fxyyId=(String) reqEvent.getRequestMap().get("fxyyid");
        
     // 应用类型
     		List fxyylxList = CacheUtil.getCodeTable("DM_YYFW_FXYYLX");
     		// 单位
     		List yhdwList = CacheUtil.getCodeTable("T_YYFW_YHDW");
     		// 发布状态
     		List fbztList = CacheUtil.getCodeTable("DM_YYFW_FBZT");
        
		List<Tycx002XiangqingPojo> xiangqingList=new ArrayList<Tycx002XiangqingPojo>();
		String gnlj=null;
		//如果fxyyId非空，查询分析应用基本信息
		if(!YyfbUtils.isEmpty(fxyyId)){
			xiangqingList=xiangqingDao.getXiangqing(fxyyId);
			//功能路径
			String folderId=xiangqingList.get(0).getGnlj();
			gnlj=getGnljMc(folderId);
			
			resMap.put("xiangqing", (xiangqingList.get(0)));
		}
        
        resMap.put("fxyylxList", fxyylxList);
        resMap.put("yhdwList", yhdwList);
        resMap.put("fbztList", fbztList);
        resMap.put("gnlj", gnlj);
        resMap.put("fxyyId", fxyyId);
        
        resEvent.setResMap(resMap);
        resEvent.setFwordPath("/biz/bizcore/sjyy/tycx/tycx002/html/tycx002Xiangqing.html");
        
    	return resEvent;
    }
    
    //获取功能路径名称
    public String getGnljMc(String folderid){
		ArrayList param=new ArrayList();
		param.add(folderid);
		String sqlstr=jdbcDao.getSql("SQL_YYFB_querySjWjjByFolder");
 		List gnljlist=jdbcDao.queryforlist(sqlstr,param);
 		String lj="";
 		for(int m=0;m<gnljlist.size();m++){
 			Map<String,String> gnljMap=(Map<String, String>) gnljlist.get(m);
 			lj=">"+gnljMap.get("mc")+lj;
 		}
 		return lj;
	}


}
