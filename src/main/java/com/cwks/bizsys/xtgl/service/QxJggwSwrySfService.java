package com.cwks.bizsys.xtgl.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizsys.xtgl.dao.QxJggwSwrySfMapper;
import com.cwks.bizsys.xtgl.dao.local.QxJggwSwrySfMapperLocal;
import com.cwks.bizsys.xtgl.domain.QxJggwSwrySfPojo;
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
@Service("qxJggwSwrySfService")
public class QxJggwSwrySfService {

    private static Logger logger = LoggerFactory.getLogger(QxJggwSwrySfService.class);

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private QxJggwSwrySfMapper qxJggwSwrySfMapper;

    @Autowired
    private QxJggwSwrySfMapperLocal qxJggwSwrySfMapperLocal;

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        QxJggwSwrySfPojo qxJggwSwrySfPojo = new QxJggwSwrySfPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<QxJggwSwrySfPojo> list = qxJggwSwrySfMapper.select(qxJggwSwrySfPojo);
        PageInfo<QxJggwSwrySfPojo> pages = new PageInfo<QxJggwSwrySfPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJson(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        QxJggwSwrySfPojo qxJggwSwrySfPojo = new QxJggwSwrySfPojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List<QxJggwSwrySfPojo> expList = null;
        if( page!=null && !"".equals(page) ){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<QxJggwSwrySfPojo> pages = new PageInfo<QxJggwSwrySfPojo>(qxJggwSwrySfMapper.select(qxJggwSwrySfPojo));
            expList = pages.getList();
        }else{
            expList = qxJggwSwrySfMapper.select(qxJggwSwrySfPojo);
        }
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        colMap.put("gwxh","岗位序号");
        colMap.put("lrrq","录入日期");
        colMap.put("lrr_dm","录入人代码");
        colMap.put("swrysf_dm","税务人员身份代码||税务人员身份代码");
        colMap.put("uuid","UUID||uuid");
        colMap.put("xgrq","修改日期");
        colMap.put("xgr_dm","修改人代码");
        colMap.put("yxbz","有效标志");
        //Excel导出格式
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("fileName","机关岗位人员身份对应");
        dataMap.put("class",QxJggwSwrySfPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String[] uuid = (String[])requestEvent.getRequestMap().get("uuid");
        QxJggwSwrySfPojo pojo = null;
        for(int i=0;i<uuid.length;i++){
            pojo = new QxJggwSwrySfPojo();
            pojo.setUuid(uuid[i]);
            qxJggwSwrySfMapper.deleteByPKey(pojo);
            qxJggwSwrySfMapperLocal.deleteByPKey(pojo);
        }
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        QxJggwSwrySfPojo qxJggwSwrySfPojo = new QxJggwSwrySfPojo(requestEvent.getRequestMap());
        qxJggwSwrySfPojo.setUuid(UUIDGenerator.getUUID().toUpperCase());
        qxJggwSwrySfMapper.insertSelective(qxJggwSwrySfPojo);
        qxJggwSwrySfMapperLocal.insertSelective(qxJggwSwrySfPojo);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        QxJggwSwrySfPojo qxJggwSwrySfPojo = new QxJggwSwrySfPojo(requestEvent.getRequestMap());
        qxJggwSwrySfMapper.updateByPKeySelective(qxJggwSwrySfPojo);
        qxJggwSwrySfMapperLocal.updateByPKeySelective(qxJggwSwrySfPojo);
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
