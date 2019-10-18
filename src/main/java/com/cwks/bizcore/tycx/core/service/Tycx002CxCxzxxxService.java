package com.cwks.bizcore.tycx.core.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.tycx.core.dao.Tycx002CxCxzxxxDao;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx002CxCxzxxxPojo;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.service.impl.BaseService;
import com.cwks.common.dao.JdbcDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Service("tycx002CxCxzxxxService")
public class Tycx002CxCxzxxxService {

    private static Logger logger = LoggerFactory.getLogger(Tycx002CxCxzxxxService.class);
    @Autowired
    private Tycx002CxCxzxxxDao tycx002CxCxzxxxDao;

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_select");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx002CxCxzxxxPojo tycx002CxCxzxxxPojo = new Tycx002CxCxzxxxPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<Tycx002CxCxzxxxPojo> list = tycx002CxCxzxxxDao.select(tycx002CxCxzxxxPojo);
        PageInfo<Tycx002CxCxzxxxPojo> pages = new PageInfo<Tycx002CxCxzxxxPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJson(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_expExcel");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx002CxCxzxxxPojo tycx002CxCxzxxxPojo = new Tycx002CxCxzxxxPojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List<Tycx002CxCxzxxxPojo> expList = null;
        if( TycxUtils.isEmpty(page) ){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<Tycx002CxCxzxxxPojo> pages = new PageInfo<Tycx002CxCxzxxxPojo>(tycx002CxCxzxxxDao.select(tycx002CxCxzxxxPojo));
            expList = pages.getList();
        }else{
            expList = tycx002CxCxzxxxDao.select(tycx002CxCxzxxxPojo);
        }
        Map colMap = new LinkedHashMap();
        colMap.put("cxsj","CXSJ");
        colMap.put("cxy","CXY");
        colMap.put("cxzxsj","CXZXSJ");
        colMap.put("czry_dm","CZRY_DM");
        colMap.put("fhjgs","FHJGS");
        colMap.put("lrrq","LRRQ");
        colMap.put("lrr_dm","LRR_DM");
        colMap.put("sessionid","SESSIONID");
        colMap.put("sjgsdq","SJGSDQ");
        colMap.put("sjgsrq","SJGSRQ");
        colMap.put("sqlstr","SQLSTR");
        colMap.put("sqlxh","SQLXH");
        colMap.put("threadid","THREADID");
        colMap.put("tjcsstr","TJCSSTR");
        colMap.put("uuid","UUID");
        colMap.put("xgrq","XGRQ");
        colMap.put("xgr_dm","XGR_DM");
        colMap.put("ztlx_dm","ZTLX_DM");
        //Excel导出格式
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("fileName","查询日志信息");
        dataMap.put("class",Tycx002CxCxzxxxPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_deleteByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx002CxCxzxxxPojo pojo = null;
//        for(int i=0;i<pkid.length;i++){
//            pojo = new Tycx002CxCxzxxxPojo();
//            tycx002CxCxzxxxDao.deleteByPKey(pojo);
//        }
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_insertSelective");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx002CxCxzxxxPojo tycx002CxCxzxxxPojo = new Tycx002CxCxzxxxPojo(requestEvent.getRequestMap());
        tycx002CxCxzxxxDao.insertSelective(tycx002CxCxzxxxPojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_updateByPKeySelective");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx002CxCxzxxxPojo tycx002CxCxzxxxPojo = new Tycx002CxCxzxxxPojo(requestEvent.getRequestMap());
        tycx002CxCxzxxxDao.updateByPKeySelective(tycx002CxCxzxxxPojo);
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
