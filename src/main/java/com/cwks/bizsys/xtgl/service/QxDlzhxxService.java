package com.cwks.bizsys.xtgl.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizsys.xtgl.dao.QxDlzhxxMapper;
import com.cwks.bizsys.xtgl.dao.local.QxDlzhxxMapperLocal;
import com.cwks.bizsys.xtgl.domain.QxDlzhxxPojo;
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
@Service("qxDlzhxxService")
public class QxDlzhxxService{

    private static Logger logger = LoggerFactory.getLogger(QxDlzhxxService.class);

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private QxDlzhxxMapper qxDlzhxxMapper;

    @Autowired
    private QxDlzhxxMapperLocal qxDlzhxxMapperLocal;

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        QxDlzhxxPojo qxDlzhxxPojo = new QxDlzhxxPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<QxDlzhxxPojo> list = qxDlzhxxMapper.select(qxDlzhxxPojo);
        PageInfo<QxDlzhxxPojo> pages = new PageInfo<QxDlzhxxPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJson(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        QxDlzhxxPojo qxDlzhxxPojo = new QxDlzhxxPojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List expList = null;
        if( page!=null && !"".equals(page) ){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<QxDlzhxxPojo> pages = new PageInfo<QxDlzhxxPojo>(qxDlzhxxMapper.select(qxDlzhxxPojo));
            expList = pages.getList();
        }else{
            expList = qxDlzhxxMapper.select(qxDlzhxxPojo);
        }
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        colMap.put("dlzhkl","登录账户口令||登录账户口令");
        colMap.put("dlzh_dm","登录账户代码||登录账户代码");
        colMap.put("klyxqq","口令有效期起");
        colMap.put("klyxqz","口令有效期止");
        colMap.put("lrrq","录入日期");
        colMap.put("lrr_dm","录入人代码");
        colMap.put("swry_dm","税务人员代码");
        colMap.put("xgrq","修改日期");
        colMap.put("xgr_dm","修改人代码");
        colMap.put("yxbz","有效标志");
        //Excel导出格式
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("fileName","人员账户密码");
        dataMap.put("class",QxDlzhxxPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String[] dlzh_dm = (String[])requestEvent.getRequestMap().get("dlzh_dm");
        QxDlzhxxPojo pojo = null;
        for(int i=0;i<dlzh_dm.length;i++){
            pojo = new QxDlzhxxPojo();
            pojo.setDlzh_dm(dlzh_dm[i]);
            qxDlzhxxMapper.deleteByPKey(pojo);
            qxDlzhxxMapperLocal.deleteByPKey(pojo);
        }
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        QxDlzhxxPojo qxDlzhxxPojo = new QxDlzhxxPojo(requestEvent.getRequestMap());
        qxDlzhxxPojo.setDlzh_dm(UUIDGenerator.getUUID().toUpperCase());
        qxDlzhxxMapper.insertSelective(qxDlzhxxPojo);
        qxDlzhxxMapperLocal.insertSelective(qxDlzhxxPojo);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        QxDlzhxxPojo qxDlzhxxPojo = new QxDlzhxxPojo(requestEvent.getRequestMap());
        qxDlzhxxMapper.updateByPKeySelective(qxDlzhxxPojo);
        qxDlzhxxMapperLocal.updateByPKeySelective(qxDlzhxxPojo);
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
