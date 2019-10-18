package com.cwks.bizsys.xtgl.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizsys.xtgl.dao.QxGwXtgnMapper;
import com.cwks.bizsys.xtgl.dao.local.QxGwXtgnMapperLocal;
import com.cwks.bizsys.xtgl.domain.QxGwXtgnPojo;
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
@Service("qxGwXtgnService")
public class QxGwXtgnService{

    private static Logger logger = LoggerFactory.getLogger(QxGwXtgnService.class);

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private QxGwXtgnMapper qxGwXtgnMapper;

    @Autowired
    private QxGwXtgnMapperLocal qxGwXtgnMapperLocal;

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        QxGwXtgnPojo qxGwXtgnPojo = new QxGwXtgnPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<QxGwXtgnPojo> list = qxGwXtgnMapper.select(qxGwXtgnPojo);
        PageInfo<QxGwXtgnPojo> pages = new PageInfo<QxGwXtgnPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJson(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        //参数注入
        QxGwXtgnPojo qxGwXtgnPojo = new QxGwXtgnPojo(requestEvent.getRequestMap());
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        List<QxGwXtgnPojo> list = qxGwXtgnMapper.select(qxGwXtgnPojo);
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        colMap.put("gw_dm","岗位代码");
        colMap.put("lrrq","录入日期");
        colMap.put("lrr_dm","录入人代码");
        colMap.put("uuid","UUID");
        colMap.put("xgrq","修改日期");
        colMap.put("xgr_dm","修改人代码");
        colMap.put("xtgn_dm","系统功能代码");
        colMap.put("yx_bj","有效标记");
        //Excel导出格式
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("fileName","岗位功能树关系");
        dataMap.put("class",QxGwXtgnPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",list);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String[] uuid = (String[])requestEvent.getRequestMap().get("uuid");
        QxGwXtgnPojo pojo = null;
        for(int i=0;i<uuid.length;i++){
            pojo = new QxGwXtgnPojo();
            pojo.setUuid(uuid[i]);
            qxGwXtgnMapper.deleteByPKey(pojo);
            qxGwXtgnMapperLocal.deleteByPKey(pojo);
        }
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        QxGwXtgnPojo qxGwXtgnPojo = new QxGwXtgnPojo(requestEvent.getRequestMap());
        qxGwXtgnPojo.setUuid(UUIDGenerator.getUUID().toUpperCase());
        qxGwXtgnMapper.insertSelective(qxGwXtgnPojo);
        qxGwXtgnMapperLocal.insertSelective(qxGwXtgnPojo);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        QxGwXtgnPojo qxGwXtgnPojo = new QxGwXtgnPojo(requestEvent.getRequestMap());
        qxGwXtgnMapper.updateByPKeySelective(qxGwXtgnPojo);
        qxGwXtgnMapperLocal.updateByPKeySelective(qxGwXtgnPojo);
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
