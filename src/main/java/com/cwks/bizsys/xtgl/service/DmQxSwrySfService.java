package com.cwks.bizsys.xtgl.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizsys.xtgl.dao.DmQxSwrySfMapper;
import com.cwks.bizsys.xtgl.dao.local.DmQxSwrySfMapperLocal;
import com.cwks.bizsys.xtgl.domain.DmQxSwrySfPojo;
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
@Service("dmQxSwrySfService")
public class DmQxSwrySfService{

    private static Logger logger = LoggerFactory.getLogger(DmQxSwrySfService.class);

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private DmQxSwrySfMapper dmQxSwrySfMapper;

    @Autowired
    private DmQxSwrySfMapperLocal dmQxSwrySfMapperLocal;

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        DmQxSwrySfPojo dmQxSwrySfPojo = new DmQxSwrySfPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<DmQxSwrySfPojo> list = dmQxSwrySfMapper.select(dmQxSwrySfPojo);
        PageInfo<DmQxSwrySfPojo> pages = new PageInfo<DmQxSwrySfPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJson(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        DmQxSwrySfPojo dmQxSwrySfPojo = new DmQxSwrySfPojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List<DmQxSwrySfPojo> expList = null;
        if( page!=null && !"".equals(page) ){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<DmQxSwrySfPojo> pages = new PageInfo<DmQxSwrySfPojo>(dmQxSwrySfMapper.select(dmQxSwrySfPojo));
            expList = pages.getList();
        }else{
            expList = dmQxSwrySfMapper.select(dmQxSwrySfPojo);
        }
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        colMap.put("lrrq","录入日期");
        colMap.put("lrr_dm","录入人代码");
        colMap.put("rysfmc","税务人员身份名称||税务人员身份名称");
        colMap.put("sfswjg_dm","身份税务机构");
        colMap.put("sfyxqq","身份有效期起||身份有效期起");
        colMap.put("sfyxqz","身份有效期止||身份有效期止");
        colMap.put("swrysf_dm","税务人员身份代码||税务人员身份代码");
        colMap.put("swry_dm","税务人员代码");
        colMap.put("xgrq","修改日期");
        colMap.put("xgr_dm","修改人代码");
        colMap.put("xsxh","显示序号");
        colMap.put("yxbz","有效标志");
        colMap.put("zsfbz","主身份标志");
        //Excel导出格式
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("fileName","税务人员身份");
        dataMap.put("class",DmQxSwrySfPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String[] swrysf_dm = (String[])requestEvent.getRequestMap().get("swrysf_dm");
        DmQxSwrySfPojo pojo = null;
        for(int i=0;i<swrysf_dm.length;i++){
            pojo = new DmQxSwrySfPojo();
            pojo.setSwrysf_dm(swrysf_dm[i]);
            dmQxSwrySfMapper.deleteByPKey(pojo);
            dmQxSwrySfMapperLocal.deleteByPKey(pojo);
        }
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        DmQxSwrySfPojo dmQxSwrySfPojo = new DmQxSwrySfPojo(requestEvent.getRequestMap());
        dmQxSwrySfPojo.setSwrysf_dm(UUIDGenerator.getUUID().toUpperCase());
        dmQxSwrySfMapper.insertSelective(dmQxSwrySfPojo);
        dmQxSwrySfMapperLocal.insertSelective(dmQxSwrySfPojo);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        DmQxSwrySfPojo dmQxSwrySfPojo = new DmQxSwrySfPojo(requestEvent.getRequestMap());
        dmQxSwrySfMapper.updateByPKeySelective(dmQxSwrySfPojo);
        dmQxSwrySfMapperLocal.updateByPKeySelective(dmQxSwrySfPojo);
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
