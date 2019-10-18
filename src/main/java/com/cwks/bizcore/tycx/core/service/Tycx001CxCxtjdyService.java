package com.cwks.bizcore.tycx.core.service;

import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.service.impl.BaseService;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.bizcore.tycx.core.dao.Tycx001CxCxtjdyDao;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxtjdyPojo;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
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
@Service("tycx001CxCxtjdyService")
public class Tycx001CxCxtjdyService  {

    private static Logger logger = LoggerFactory.getLogger(Tycx001CxCxtjdyService.class);


    @Autowired
    private Tycx001CxCxtjdyDao tycx001CxCxtjdyDao;

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_select");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx001CxCxtjdyPojo tycx001CxCxtjdyPojo = new Tycx001CxCxtjdyPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<Tycx001CxCxtjdyPojo> list = tycx001CxCxtjdyDao.select(tycx001CxCxtjdyPojo);
        PageInfo<Tycx001CxCxtjdyPojo> pages = new PageInfo<Tycx001CxCxtjdyPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJsonForJqGrid(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    public ResponseEvent selectBySqlxh(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_select");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx001CxCxtjdyPojo tycx001CxCxtjdyPojo = new Tycx001CxCxtjdyPojo(requestEvent.getRequestMap());
        //设置分页
//        PageHelper.startPage(requestEvent.getRequestMap());
        List<Tycx001CxCxtjdyPojo> list = tycx001CxCxtjdyDao.select(tycx001CxCxtjdyPojo);
        PageInfo<Tycx001CxCxtjdyPojo> pages = new PageInfo<Tycx001CxCxtjdyPojo>(list);
        reqmap.put("JSONDATA", TycxUtils.toJsonForJqGrid(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_expExcel");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx001CxCxtjdyPojo tycx001CxCxtjdyPojo = new Tycx001CxCxtjdyPojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List<Tycx001CxCxtjdyPojo> expList = null;
        if( !TycxUtils.isEmpty(page) ){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<Tycx001CxCxtjdyPojo> pages = new PageInfo<Tycx001CxCxtjdyPojo>(tycx001CxCxtjdyDao.select(tycx001CxCxtjdyPojo));
            expList = pages.getList();
        }else{
            expList = tycx001CxCxtjdyDao.select(tycx001CxCxtjdyPojo);
        }
        Map colMap = new LinkedHashMap();
        colMap.put("dmsql","DMSQL");
        colMap.put("fzzdbz","FZZDBZ");
        colMap.put("jgcj","JGCJ");
        colMap.put("jglx","JGLX");
        colMap.put("jssjzd","JSSJZD");
        colMap.put("jylx","JYLX");
        colMap.put("jys","JYS");
        colMap.put("jyzh","JYZH");
        colMap.put("llx","LLX");
        colMap.put("lmc","LMC");
        colMap.put("lrrq","LRRQ");
        colMap.put("lrr_dm","LRR_DM");
        colMap.put("mbbz","MBBZ");
        colMap.put("mrz","MRZ");
        colMap.put("mrzxsbz","MRZXSBZ");
        colMap.put("sjgsdq","SJGSDQ");
        colMap.put("sjtjl","SJTJL");
        colMap.put("sm","SM");
        colMap.put("sqlxh","SQLXH");
        colMap.put("swjgtreescgz","SWJGTREESCGZ");
        colMap.put("tjmc","TJMC");
        colMap.put("tjxylx","TJXYLX");
        colMap.put("uuid","UUID");
        colMap.put("xgrq","XGRQ");
        colMap.put("xgr_dm","XGR_DM");
        colMap.put("xh","XH");
        colMap.put("xsgs","XSGS");
        colMap.put("xsxh","XSXH");
        colMap.put("zdycs","ZDYCS");
        colMap.put("zdykd","ZDYKD");
        colMap.put("znxz","ZNXZ");
        //Excel导出格式
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("fileName","查询条件定义");
        dataMap.put("class",Tycx001CxCxtjdyPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_deleteByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx001CxCxtjdyPojo pojo = null;
//        for(int i=0;i<pkid.length;i++){
//            pojo = new Tycx001CxCxtjdyPojo();
//            tycx001CxCxtjdyDao.deleteByPKey(pojo);
//        }
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_insertSelective");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx001CxCxtjdyPojo tycx001CxCxtjdyPojo = new Tycx001CxCxtjdyPojo(requestEvent.getRequestMap());
        tycx001CxCxtjdyDao.insertSelective(tycx001CxCxtjdyPojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_updateByPKeySelective");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx001CxCxtjdyPojo tycx001CxCxtjdyPojo = new Tycx001CxCxtjdyPojo(requestEvent.getRequestMap());
        HashMap hashmap=requestEvent.getRequestMap();
        tycx001CxCxtjdyDao.updateByPKeySelective(tycx001CxCxtjdyPojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.UPDATE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }


    public ResponseEvent selectByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_selectByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx001CxCxtjdyPojo tycx001CxCxtjdyPojo = new Tycx001CxCxtjdyPojo(requestEvent.getRequestMap());
        Tycx001CxCxtjdyPojo cxtjPojo=tycx001CxCxtjdyDao.selectByPKey(tycx001CxCxtjdyPojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", JsonUtil.toJson(cxtjPojo));
        resEvent.setResMap(reqmap);
        return resEvent;
    }



}
