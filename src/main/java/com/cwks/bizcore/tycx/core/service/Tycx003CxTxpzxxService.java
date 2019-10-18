package com.cwks.bizcore.tycx.core.service;

import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.service.impl.BaseService;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizcore.tycx.core.dao.Tycx003CxTxpzxxDao;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx003CxTxpzxxPojo;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
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
@Service("tycx003CxTxpzxxService")
public class Tycx003CxTxpzxxService  {

    private static Logger logger = LoggerFactory.getLogger(Tycx003CxTxpzxxService.class);


    @Autowired
    private Tycx003CxTxpzxxDao tycx003CxTxpzxxDao;

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_select");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx003CxTxpzxxPojo tycx003CxTxpzxxPojo = new Tycx003CxTxpzxxPojo(requestEvent.getRequestMap());
        //设置分页
//        PageHelper.startPage(requestEvent.getRequestMap());
        List<Tycx003CxTxpzxxPojo> list = tycx003CxTxpzxxDao.select(tycx003CxTxpzxxPojo);
        PageInfo<Tycx003CxTxpzxxPojo> pages = new PageInfo<Tycx003CxTxpzxxPojo>(list);
        reqmap.put("JSONDATA", TycxUtils.toJsonForJqGrid(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    public ResponseEvent selectAll(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_select");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx003CxTxpzxxPojo tycx003CxTxpzxxPojo = new Tycx003CxTxpzxxPojo(requestEvent.getRequestMap());
        //设置分页
//        PageHelper.startPage(requestEvent.getRequestMap());
        List<Tycx003CxTxpzxxPojo> list = tycx003CxTxpzxxDao.selectAll(tycx003CxTxpzxxPojo);
        PageInfo<Tycx003CxTxpzxxPojo> pages = new PageInfo<Tycx003CxTxpzxxPojo>(list);
        reqmap.put("JSONDATA", TycxUtils.toJsonForJqGrid(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    public ResponseEvent selectByKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_initNew");
        ResponseEvent resEvent = new ResponseEvent();
        String uuid=(String) requestEvent.getRequestMap().get("uuid");
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx003CxTxpzxxPojo tycx003CxTxpzxxPojo = new Tycx003CxTxpzxxPojo(requestEvent.getRequestMap());
        //设置分页
//        PageHelper.startPage(requestEvent.getRequestMap());
        List<Tycx003CxTxpzxxPojo> list = tycx003CxTxpzxxDao.select(tycx003CxTxpzxxPojo);
        PageInfo<Tycx003CxTxpzxxPojo> pages = new PageInfo<Tycx003CxTxpzxxPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJson(list.get(0)));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_expExcel");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx003CxTxpzxxPojo tycx003CxTxpzxxPojo = new Tycx003CxTxpzxxPojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List<Tycx003CxTxpzxxPojo> expList = null;
        if( !TycxUtils.isEmpty(page) ){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<Tycx003CxTxpzxxPojo> pages = new PageInfo<Tycx003CxTxpzxxPojo>(tycx003CxTxpzxxDao.select(tycx003CxTxpzxxPojo));
            expList = pages.getList();
        }else{
            expList = tycx003CxTxpzxxDao.select(tycx003CxTxpzxxPojo);
        }
        Map colMap = new LinkedHashMap();
        colMap.put("fontsize","字体大小");
        colMap.put("hzb","横坐标");
        colMap.put("hzbmc","横坐标名称");
        colMap.put("sjylx","数据源类型");
        colMap.put("sql","SQL，可以自定义SQL，如果不定义，默");
        colMap.put("sqlxh","SQLXH");
        colMap.put("title","标题");
        colMap.put("txlx","图形类型,txlx=barAndLine");
        colMap.put("uuid","UUID");
        colMap.put("zzb","纵坐标");
        colMap.put("zzbdw","纵坐标单位");
        colMap.put("zzbmc","纵坐标名称");
        //Excel导出格式
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("fileName","图形配置信息");
        dataMap.put("class",Tycx003CxTxpzxxPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_deleteByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        String[] uuid = (String[])requestEvent.getRequestMap().get("uuid");
        Tycx003CxTxpzxxPojo pojo = null;
        for(int i=0;i<uuid.length;i++){
            pojo = new Tycx003CxTxpzxxPojo();
            pojo.setUuid(uuid[i]);
            tycx003CxTxpzxxDao.deleteByPKey(pojo);
        }
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_insertSelective");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap hashmap=requestEvent.getRequestMap();
        Tycx003CxTxpzxxPojo tycx003CxTxpzxxPojo = new Tycx003CxTxpzxxPojo(requestEvent.getRequestMap());
        tycx003CxTxpzxxPojo.setUuid(UUIDGenerator.getUUID());
        tycx003CxTxpzxxDao.insertSelective(tycx003CxTxpzxxPojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMsg(RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_updateByPKeySelective");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx003CxTxpzxxPojo tycx003CxTxpzxxPojo = new Tycx003CxTxpzxxPojo(requestEvent.getRequestMap());
        tycx003CxTxpzxxDao.updateByPKeySelective(tycx003CxTxpzxxPojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.UPDATE_TRUE);
        resEvent.setResMap(reqmap);
        resEvent.setResMsg(RJson.UPDATE_TRUE);
        return resEvent;
    }
    public ResponseEvent saveAddTxpzxx(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_saveAddTxpzxx");
        
        ResponseEvent resEvent = new ResponseEvent();
        return resEvent;
    }


}
