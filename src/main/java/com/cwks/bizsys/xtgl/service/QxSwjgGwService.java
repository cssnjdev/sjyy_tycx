package com.cwks.bizsys.xtgl.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizsys.xtgl.dao.QxSwjgGwMapper;
import com.cwks.bizsys.xtgl.dao.local.QxSwjgGwMapperLocal;
import com.cwks.bizsys.xtgl.domain.QxSwjgGwPojo;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.dao.JdbcDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
*
*/
@Component
@Service("qxSwjgGwService")
public class QxSwjgGwService{

    private static Logger logger = LoggerFactory.getLogger(QxSwjgGwService.class);


    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private QxSwjgGwMapper qxSwjgGwMapper;

    @Autowired
    private QxSwjgGwMapperLocal qxSwjgGwMapperLocal;

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        QxSwjgGwPojo qxSwjgGwPojo = new QxSwjgGwPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<QxSwjgGwPojo> list = qxSwjgGwMapper.select(qxSwjgGwPojo);
        PageInfo<QxSwjgGwPojo> pages = new PageInfo<QxSwjgGwPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJson(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        QxSwjgGwPojo qxSwjgGwPojo = new QxSwjgGwPojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List expList = null;
        if( page!=null && !"".equals(page) ){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<QxSwjgGwPojo> pages = new PageInfo<QxSwjgGwPojo>(qxSwjgGwMapper.select(qxSwjgGwPojo));
            expList = pages.getList();
        }else{
            expList = qxSwjgGwMapper.select(qxSwjgGwPojo);
        }
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        colMap.put("gwxh","岗位序号");
        colMap.put("gw_dm","岗位代码");
        colMap.put("lrrq","录入日期");
        colMap.put("lrr_dm","录入人代码");
        colMap.put("swjg_dm","税务机关代码");
        colMap.put("xgrq","修改日期");
        colMap.put("xgr_dm","修改人代码");
        colMap.put("xsxh","显示序号");
        colMap.put("yxbz","有效标志");
        //Excel导出格式
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("fileName","税务机关对应岗位");
        dataMap.put("class",QxSwjgGwPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String[] gwxh = (String[])requestEvent.getRequestMap().get("gwxh");
        QxSwjgGwPojo pojo = null;
        for(int i=0;i<gwxh.length;i++){
            pojo = new QxSwjgGwPojo();
            pojo.setGwxh(gwxh[i]);
            qxSwjgGwMapper.deleteByPKey(pojo);
            qxSwjgGwMapperLocal.deleteByPKey(pojo);
        }
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        QxSwjgGwPojo qxSwjgGwPojo = new QxSwjgGwPojo(requestEvent.getRequestMap());
        qxSwjgGwPojo.setGwxh(UUIDGenerator.getUUID().toUpperCase());
        qxSwjgGwMapper.insertSelective(qxSwjgGwPojo);
        qxSwjgGwMapperLocal.insertSelective(qxSwjgGwPojo);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        QxSwjgGwPojo qxSwjgGwPojo = new QxSwjgGwPojo(requestEvent.getRequestMap());
        qxSwjgGwMapper.updateByPKeySelective(qxSwjgGwPojo);
        qxSwjgGwMapperLocal.updateByPKeySelective(qxSwjgGwPojo);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.UPDATE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }


    public ResponseEvent selectByPKey(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        return resEvent;
    }

}
