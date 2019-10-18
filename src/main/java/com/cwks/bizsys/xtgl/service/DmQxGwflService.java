package com.cwks.bizsys.xtgl.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizsys.xtgl.dao.DmQxGwflMapper;
import com.cwks.bizsys.xtgl.dao.local.DmQxGwflMapperLocal;
import com.cwks.bizsys.xtgl.domain.DmQxGwflPojo;
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
@Service("dmQxGwflService")
public class DmQxGwflService{

    private static Logger logger = LoggerFactory.getLogger(DmQxGwflService.class);

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private DmQxGwflMapper dmQxGwflMapper;

    @Autowired
    private DmQxGwflMapperLocal dmQxGwflMapperLocal;

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        DmQxGwflPojo dmQxGwflPojo = new DmQxGwflPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<DmQxGwflPojo> list = dmQxGwflMapper.select(dmQxGwflPojo);
        PageInfo<DmQxGwflPojo> pages = new PageInfo<DmQxGwflPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJson(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        DmQxGwflPojo dmQxGwflPojo = new DmQxGwflPojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List<DmQxGwflPojo> expList = null;
        if( page!=null && !"".equals(page) ){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<DmQxGwflPojo> pages = new PageInfo<DmQxGwflPojo>(dmQxGwflMapper.select(dmQxGwflPojo));
            expList = pages.getList();
        }else{
            expList = dmQxGwflMapper.select(dmQxGwflPojo);
        }
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        colMap.put("gwflmc","岗位分类名称");
        colMap.put("gwfl_dm","岗位分类代码");
        colMap.put("lrrq","录入日期");
        colMap.put("lrr_dm","录入人代码");
        colMap.put("swjg_dm","税务机关代码");
        colMap.put("uuid","UUID||uuid");
        colMap.put("xgrq","修改日期");
        colMap.put("xgr_dm","修改人代码");
        colMap.put("yxbz","有效标志");
        //Excel导出格式
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("fileName","岗位分类");
        dataMap.put("class",DmQxGwflPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String[] uuid = (String[])requestEvent.getRequestMap().get("uuid");
        DmQxGwflPojo pojo = null;
        for(int i=0;i<uuid.length;i++){
            pojo = new DmQxGwflPojo();
            pojo.setUuid(uuid[i]);
            dmQxGwflMapper.deleteByPKey(pojo);
            dmQxGwflMapperLocal.deleteByPKey(pojo);
        }
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        DmQxGwflPojo dmQxGwflPojo = new DmQxGwflPojo(requestEvent.getRequestMap());
        dmQxGwflPojo.setUuid(UUIDGenerator.getUUID().toUpperCase());
        dmQxGwflMapper.insertSelective(dmQxGwflPojo);
        dmQxGwflMapperLocal.insertSelective(dmQxGwflPojo);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        DmQxGwflPojo dmQxGwflPojo = new DmQxGwflPojo(requestEvent.getRequestMap());
        dmQxGwflMapper.updateByPKeySelective(dmQxGwflPojo);
        dmQxGwflMapperLocal.updateByPKeySelective(dmQxGwflPojo);

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
