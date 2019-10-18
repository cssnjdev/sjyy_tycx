package com.cwks.bizsys.xtgl.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizsys.xtgl.dao.TXtGnsMapper;
import com.cwks.bizsys.xtgl.domain.TXtGnsPojo;
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
@Service("tXtGnsService")
public class TXtGnsService{

    private static Logger logger = LoggerFactory.getLogger(TXtGnsService.class);

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private TXtGnsMapper tXtGnsMapper;

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        TXtGnsPojo tXtGnsPojo = new TXtGnsPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<TXtGnsPojo> list = tXtGnsMapper.select(tXtGnsPojo);
        PageInfo<TXtGnsPojo> pages = new PageInfo<TXtGnsPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJson(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        TXtGnsPojo tXtGnsPojo = new TXtGnsPojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List<TXtGnsPojo> expList = null;
        if( page!=null && !"".equals(page) ){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<TXtGnsPojo> pages = new PageInfo<TXtGnsPojo>(tXtGnsMapper.select(tXtGnsPojo));
            expList = pages.getList();
        }else{
            expList = tXtGnsMapper.select(tXtGnsPojo);
        }
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        colMap.put("cdlx","菜单类型   0 目录 1 菜单");
        colMap.put("cdxh","菜单序号");
        colMap.put("gn_cj","功能层级");
        colMap.put("gn_dm","功能代码");
        colMap.put("gn_jc","功能简称");
        colMap.put("gn_mc","功能名称");
        colMap.put("gn_url","功能url路径");
        colMap.put("icon","菜单图标");
        colMap.put("lr_sj","录入时间");
        colMap.put("opentype","打开方式       1tab   0弹");
        colMap.put("sjgn_dm","上级功能代码");
        colMap.put("xg_sj","修改时间");
        colMap.put("xy_bj","选用标记 1选用 0停用");
        colMap.put("yx_bj","有效标记 1有效 0 无效");
        //Excel导出格式
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("fileName","菜单功能树");
        dataMap.put("class",TXtGnsPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String[] gn_dm = (String[])requestEvent.getRequestMap().get("gn_dm");
        TXtGnsPojo pojo = null;
        for(int i=0;i<gn_dm.length;i++){
            pojo = new TXtGnsPojo();
            pojo.setGn_dm(gn_dm[i]);
            tXtGnsMapper.deleteByPKey(pojo);
        }
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }


    //新增功能菜单树
    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();

        String gndm = requestEvent.getRequestMap().get("gndm").toString();
        String sjgndm = requestEvent.getRequestMap().get("sjgndm").toString();
        String gnmc = requestEvent.getRequestMap().get("gnmc").toString();
        String gnjc = requestEvent.getRequestMap().get("gnjc").toString();
        String gnurl = requestEvent.getRequestMap().get("gnurl").toString();
        gnurl = gnurl.replace("??", "/");
        String gncj = requestEvent.getRequestMap().get("gncj").toString();
        String icon = requestEvent.getRequestMap().get("icon").toString();
        String cdxh = requestEvent.getRequestMap().get("cdxh").toString();
        String cdlx = requestEvent.getRequestMap().get("cdlx").toString();
        String openType = requestEvent.getRequestMap().get("openType").toString();
        String xybj = requestEvent.getRequestMap().get("xybj").toString();
        String yxbj = requestEvent.getRequestMap().get("yxbj").toString();

        TXtGnsPojo tXtGnsPojo = new TXtGnsPojo();
        tXtGnsPojo.setGn_dm(gndm);
        tXtGnsPojo.setSjgn_dm(sjgndm);
        tXtGnsPojo.setGn_mc(gnmc);
        tXtGnsPojo.setGn_jc(gnjc);
        tXtGnsPojo.setGn_url(gnurl);
        tXtGnsPojo.setGn_cj(gncj);
        tXtGnsPojo.setIcon(icon);
        tXtGnsPojo.setCdxh(cdxh);
        tXtGnsPojo.setCdlx(cdlx);
        tXtGnsPojo.setOpentype(openType);
        tXtGnsPojo.setXy_bj(xybj);
        tXtGnsPojo.setYx_bj(yxbj);
        tXtGnsMapper.insertSelective(tXtGnsPojo);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //修改功能菜单树
    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();

        String gndm = requestEvent.getRequestMap().get("gndm").toString();
        String sjgndm = requestEvent.getRequestMap().get("sjgndm").toString();
        String gnmc = requestEvent.getRequestMap().get("gnmc").toString();
        String gnjc = requestEvent.getRequestMap().get("gnjc").toString();
        String gnurl = requestEvent.getRequestMap().get("gnurl").toString();
        gnurl = gnurl.replace("??", "/");
        String gncj = requestEvent.getRequestMap().get("gncj").toString();
        String icon = requestEvent.getRequestMap().get("icon").toString();
        String cdxh = requestEvent.getRequestMap().get("cdxh").toString();
        String cdlx = requestEvent.getRequestMap().get("cdlx").toString();
        String openType = requestEvent.getRequestMap().get("openType").toString();
        String xybj = requestEvent.getRequestMap().get("xybj").toString();
        String yxbj = requestEvent.getRequestMap().get("yxbj").toString();

        TXtGnsPojo tXtGnsPojo = new TXtGnsPojo();
        tXtGnsPojo.setGn_dm(gndm);
        tXtGnsPojo.setSjgn_dm(sjgndm);
        tXtGnsPojo.setGn_mc(gnmc);
        tXtGnsPojo.setGn_jc(gnjc);
        tXtGnsPojo.setGn_url(gnurl);
        tXtGnsPojo.setGn_cj(gncj);
        tXtGnsPojo.setIcon(icon);
        tXtGnsPojo.setCdxh(cdxh);
        tXtGnsPojo.setCdlx(cdlx);
        tXtGnsPojo.setOpentype(openType);
        tXtGnsPojo.setXy_bj(xybj);
        tXtGnsPojo.setYx_bj(yxbj);
        tXtGnsMapper.updateByPKeySelective(tXtGnsPojo);

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
