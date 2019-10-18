package com.cwks.bizcore.tycx.core.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizcore.tycx.core.dao.Tycx004CxZtcxxmDao;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx004CxZtcxxmPojo;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.core.cache.CacheUtil;
import com.cwks.common.service.impl.BaseService;
import com.cwks.common.dao.JdbcDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.*;

//import com.cwks.common.api.dto.ext.RequestEvent;
//import com.cwks.common.core.cache.CacheUtil;

@Component
@Service("tycx004CxZtcxxmService")
public class Tycx004CxZtcxxmService  {

    private static Logger logger = LoggerFactory.getLogger(Tycx004CxZtcxxmService.class);


    @Autowired
    private Tycx004CxZtcxxmDao tycx004CxZtcxxmDao;
 
    /**
     * 一户式初始化功能树
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent initYhs(RequestEvent requestEvent) throws Exception{
		logger.debug("debugger " + this.getClass().getName() + "_select");
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		String djxh = (String) requestEvent.getRequestMap().get("djxh");
		String cxlx = (String) requestEvent.getRequestMap().get("cxlx");
		String lx = (String) requestEvent.getRequestMap().get("lx");// 1是取文件夹，0是取子节点
		String sjcxxmdm = (String) requestEvent.getRequestMap().get("sjjdDm");
		List hcbList = CacheUtil.getCodeTable("sys_xtcs","csbm=DTHX");
		// 参数注入
		Tycx004CxZtcxxmPojo tycx004CxZtcxxmPojo = new Tycx004CxZtcxxmPojo();
		List<Tycx004CxZtcxxmPojo> list = new ArrayList<Tycx004CxZtcxxmPojo>();
		if ("1".equals(lx)) {// 查询文件夹
			list = tycx004CxZtcxxmDao.select(tycx004CxZtcxxmPojo);
		} else {
			tycx004CxZtcxxmPojo.setZtlx_dm(cxlx);
			tycx004CxZtcxxmPojo.setSjcxxmdm(sjcxxmdm);
			list = tycx004CxZtcxxmDao.selectCxxmBySjdm(tycx004CxZtcxxmPojo);
		}
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Tycx004CxZtcxxmPojo ztcxxmPojo = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", ztcxxmPojo.getCxxmmc());
			map.put("isParent", true);
			map.put("cxxmDm", ztcxxmPojo.getCxxm_dm());
			map.put("cxxmmc", ztcxxmPojo.getCxxmmc());
			map.put("jdlb", ztcxxmPojo.getJdlb());
			if (!"1".equals(lx)) {
				map.put("sqlxh", ztcxxmPojo.getSqlxh());
			}
			resultList.add(map);
		}
		reqmap.put("JSONDATA", JsonUtil.toJson(resultList));
		resEvent.setResMap(reqmap);
		
		return resEvent;
    }
    public ResponseEvent initView(RequestEvent requestEvent) throws Exception{
		logger.debug("debugger " + this.getClass().getName() + "_select");
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		String drillParams = (String) requestEvent.getRequestMap().get("drillParams");
		drillParams="[{'name':'DJXH','value':'10123213010000000215'}]";
		String nsrsbh = "";
		String djxh = "";
		if (!TycxUtils.isEmpty(drillParams)) {
			drillParams = URLDecoder.decode(URLDecoder.decode(drillParams, "UTF-8"), "UTF-8");
			final List drillarray = JsonUtil.toListMap(drillParams);
			for (int i = 0; i < drillarray.size(); i++) {
				final Map<String, Object> extraMap = (Map<String, Object>) drillarray.get(i);
				final String name = (String) extraMap.get("name");
				if ("NSRSBH".equals(name)) {
					nsrsbh = (String) extraMap.get("value");
					continue;
				} else if ("DJXH".equals(name)) {
					djxh = (String) extraMap.get("value");
					continue;
				}
			}
		}
		reqmap.put("nsrsbh", nsrsbh);
		reqmap.put("djxh", djxh);
		List resultList=tycx004CxZtcxxmDao.queryJbxx(reqmap);
		reqmap.put("JSONDATA", JsonUtil.toJson(resultList));
		resEvent.setFwordPath("/biz/core/ext/tycx/tycx004/jsp/tycx004HxDhhx.html");
		resEvent.setResMap(reqmap);
		
		return resEvent;	
    }
    public ResponseEvent initYhsView(RequestEvent requestEvent) throws Exception{
    	ResponseEvent resEvent = new ResponseEvent();
    	resEvent.setFwordPath("/biz/core/ext/tycx/tycx004/jsp/tycx005_yhscx_main.html");
		HashMap reqmap = new HashMap();
    	return resEvent;
    }
    public ResponseEvent selectHx(RequestEvent requestEvent) throws Exception{
    	logger.debug("debugger " + this.getClass().getName() + "_initHx");
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		String djxh = (String) requestEvent.getRequestMap().get("djxh");
		Map<String,Object> resultMap=tycx004CxZtcxxmDao.queryBqhx(djxh);
		reqmap.put("JSONDATA", JsonUtil.toJson(resultMap));
		resEvent.setResMap(reqmap);
    	return resEvent;	
    }
    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_select");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx004CxZtcxxmPojo tycx004CxZtcxxmPojo = new Tycx004CxZtcxxmPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<Tycx004CxZtcxxmPojo> list = tycx004CxZtcxxmDao.select(tycx004CxZtcxxmPojo);
        PageInfo<Tycx004CxZtcxxmPojo> pages = new PageInfo<Tycx004CxZtcxxmPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJson(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_expExcel");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx004CxZtcxxmPojo tycx004CxZtcxxmPojo = new Tycx004CxZtcxxmPojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List<Tycx004CxZtcxxmPojo> expList = null;
        if(!TycxUtils.isEmpty(page) ){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<Tycx004CxZtcxxmPojo> pages = new PageInfo<Tycx004CxZtcxxmPojo>(tycx004CxZtcxxmDao.select(tycx004CxZtcxxmPojo));
            expList = pages.getList();
        }else{
            expList = tycx004CxZtcxxmDao.select(tycx004CxZtcxxmPojo);
        }
        Map colMap = new LinkedHashMap();
        colMap.put("bbh","BBH");
        colMap.put("bbid","BBID");
        colMap.put("cxrqqtjlmc","CXRQQTJLMC");
        colMap.put("cxrqztjlmc","CXRQZTJLMC");
        colMap.put("cxxmmc","CXXMMC");
        colMap.put("cxxm_dm","CXXM_DM");
        colMap.put("dylx","DYLX");
        colMap.put("jdlb","JDLB");
        colMap.put("lrrq","LRRQ");
        colMap.put("lrr_dm","LRR_DM");
        colMap.put("mrxsbz","MRXSBZ");
        colMap.put("sjcxxmdm","SJCXXMDM");
        colMap.put("sqlxh","SQLXH");
        colMap.put("tjcsstr","TJCSSTR");
        colMap.put("url","URL");
        colMap.put("xgrq","XGRQ");
        colMap.put("xgr_dm","XGR_DM");
        colMap.put("xh","XH");
        colMap.put("ywfl_dm","YWFL_DM");
        colMap.put("ztlxmx_dm","ZTLXMX_DM");
        colMap.put("ztlx_dm","ZTLX_DM");
        //Excel导出格式
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("fileName","主题查询项目");
        dataMap.put("class",Tycx004CxZtcxxmPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_deleteByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        String[] cxxm_dm = (String[])requestEvent.getRequestMap().get("cxxm_dm");
        Tycx004CxZtcxxmPojo pojo = null;
        for(int i=0;i<cxxm_dm.length;i++){
            pojo = new Tycx004CxZtcxxmPojo();
            pojo.setCxxm_dm(cxxm_dm[i]);
            tycx004CxZtcxxmDao.deleteByPKey(pojo);
        }
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_insertSelective");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx004CxZtcxxmPojo tycx004CxZtcxxmPojo = new Tycx004CxZtcxxmPojo(requestEvent.getRequestMap());
        tycx004CxZtcxxmPojo.setCxxm_dm(UUIDGenerator.getUUID());
        tycx004CxZtcxxmDao.insertSelective(tycx004CxZtcxxmPojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_updateByPKeySelective");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx004CxZtcxxmPojo tycx004CxZtcxxmPojo = new Tycx004CxZtcxxmPojo(requestEvent.getRequestMap());
        tycx004CxZtcxxmDao.updateByPKeySelective(tycx004CxZtcxxmPojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.UPDATE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }


    public ResponseEvent selectByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_selectByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        return resEvent;
    }



}
