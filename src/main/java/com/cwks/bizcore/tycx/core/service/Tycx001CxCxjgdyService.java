package com.cwks.bizcore.tycx.core.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.tycx.core.dao.Tycx001CxCxjgdyDao;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxjgdyPojo;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.service.impl.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.cj.util.DataTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Service("tycx001CxCxjgdyService")
public class Tycx001CxCxjgdyService  {

    private static Logger logger = LoggerFactory.getLogger(Tycx001CxCxjgdyService.class);


    @Autowired
    private Tycx001CxCxjgdyDao tycx001CxCxjgdyDao;
    
 
    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_select");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx001CxCxjgdyPojo tycx001CxCxjgdyPojo = new Tycx001CxCxjgdyPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<Tycx001CxCxjgdyPojo> list = tycx001CxCxjgdyDao.select(tycx001CxCxjgdyPojo);
        PageInfo<Tycx001CxCxjgdyPojo> pages = new PageInfo<Tycx001CxCxjgdyPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJsonForJqGrid(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    public ResponseEvent selectBySqlxh(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_select");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx001CxCxjgdyPojo tycx001CxCxjgdyPojo = new Tycx001CxCxjgdyPojo(requestEvent.getRequestMap());
        //设置分页
//        PageHelper.startPage(requestEvent.getRequestMap());
        //PageHelper.startPage(2, 20);
        List<Tycx001CxCxjgdyPojo> list = tycx001CxCxjgdyDao.select(tycx001CxCxjgdyPojo);
        PageInfo<Tycx001CxCxjgdyPojo> pages = new PageInfo<Tycx001CxCxjgdyPojo>(list);
        reqmap.put("JSONDATA", TycxUtils.toJsonForJqGrid(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_expExcel");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx001CxCxjgdyPojo tycx001CxCxjgdyPojo = new Tycx001CxCxjgdyPojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List<Tycx001CxCxjgdyPojo> expList = null;
        if( TycxUtils.isEmpty(page) ){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<Tycx001CxCxjgdyPojo> pages = new PageInfo<Tycx001CxCxjgdyPojo>(tycx001CxCxjgdyDao.select(tycx001CxCxjgdyPojo));
            expList = pages.getList();
        }else{
            expList = tycx001CxCxjgdyDao.select(tycx001CxCxjgdyPojo);
        }
        Map colMap = new LinkedHashMap();
        colMap.put("dmsql","DMSQL");
        colMap.put("dqfs","DQFS");
        colMap.put("dyghbbj","DYGHBBJ");
        colMap.put("glbj","GLBJ");
        colMap.put("jcbzdlx","JCBZDLX");
        colMap.put("kzlx","KZLX");
        colMap.put("lbm","LBM");
        colMap.put("lkd","LKD");
        colMap.put("llx","LLX");
        colMap.put("lmc","LMC");
        colMap.put("lms","LMS");
        colMap.put("lrrq","LRRQ");
        colMap.put("lrr_dm","LRR_DM");
        colMap.put("mbbz","MBBZ");
        colMap.put("sdbj","SDBJ");
        colMap.put("sjgsdq","SJGSDQ");
        colMap.put("sjlmc","SJLMC");
        colMap.put("sqlxh","SQLXH");
        colMap.put("tjlx","TJLX");
        colMap.put("url","URL");
        colMap.put("uuid","UUID");
        colMap.put("xgrq","XGRQ");
        colMap.put("xgr_dm","XGR_DM");
        colMap.put("xh","XH");
        colMap.put("xsgs","XSGS");
        colMap.put("xsxh","XSXH");
        colMap.put("xzcs","XZCS");
        colMap.put("ycbj","YCBJ");
        colMap.put("zsfs","ZSFS");
        //Excel导出格式
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("fileName","查询结果定义");
        dataMap.put("class",Tycx001CxCxjgdyPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_deleteByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx001CxCxjgdyPojo pojo = null;
//        for(int i=0;i<pkid.length;i++){
//            pojo = new Tycx001CxCxjgdyPojo();
//            tycx001CxCxjgdyDao.deleteByPKey(pojo);
//        }
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_insertSelective");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx001CxCxjgdyPojo tycx001CxCxjgdyPojo = new Tycx001CxCxjgdyPojo(requestEvent.getRequestMap());
        tycx001CxCxjgdyDao.insertSelective(tycx001CxCxjgdyPojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_updateByPKeySelective");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx001CxCxjgdyPojo tycx001CxCxjgdyPojo = new Tycx001CxCxjgdyPojo(requestEvent.getRequestMap());
        tycx001CxCxjgdyDao.updateByPKeySelective(tycx001CxCxjgdyPojo);
        HashMap reqmap = new HashMap();
        HashMap map = new HashMap();
        map.put("sqlxh", tycx001CxCxjgdyPojo.getSqlxh());
        map.put("mess", "保存成功");
        reqmap.put("JSONDATA", JsonUtil.toJson(map));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    /**
     * 保存自定义查询结果列
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent saveAddCxjgl(RequestEvent requestEvent) throws Exception {
    	logger.debug("debugger "+this.getClass().getName()+"_saveAddCxjgl");
    	ResponseEvent resEvent = new ResponseEvent();
    	Tycx001CxCxjgdyPojo tycx001CxCxjgdyPojo = new Tycx001CxCxjgdyPojo(requestEvent.getRequestMap());
    	String sqlxh=(String) requestEvent.getRequestMap().get("sqlxh");
//    	String xhStr=(String) requestEvent.getRequestMap().get("xh");
//    	double xh=Integer.parseInt(xhStr)+1;
    	tycx001CxCxjgdyPojo.setSqlxh(sqlxh);
    	//获取uuid
    	String uuid=UUID.randomUUID().toString();
    	uuid=uuid.replace("-", "");
    	tycx001CxCxjgdyPojo.setUuid(uuid);
    	
    	//测试数据
    	tycx001CxCxjgdyPojo.setLrr_dm("cssnj");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String lrrq= sdf.format(new Date());
    	tycx001CxCxjgdyPojo.setLrrq(lrrq);
//    	tycx001CxCxjgdyPojo.setXh(xh);
//    	tycx001CxCxjgdyPojo.setXsxh(xh);
    	
    	tycx001CxCxjgdyDao.saveAddCxjgl(tycx001CxCxjgdyPojo);
    	HashMap reqmap = new HashMap();
    	HashMap map = new HashMap();
    	map.put("sqlxh", tycx001CxCxjgdyPojo.getSqlxh());
    	map.put("mess", "保存成功");
    	reqmap.put("JSONDATA", JsonUtil.toJson(map));
    	resEvent.setResMap(reqmap);
    	return resEvent;
    }

    public ResponseEvent selectByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_selectByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx001CxCxjgdyPojo tycx001CxCxjgdyPojo = new Tycx001CxCxjgdyPojo(requestEvent.getRequestMap());
        Tycx001CxCxjgdyPojo cxjgPojo=tycx001CxCxjgdyDao.selectByPKey(tycx001CxCxjgdyPojo);
        HashMap reqmap = new HashMap();
        HashMap map = new HashMap();
        map.put("cxjgPojo", cxjgPojo);
        reqmap.put("JSONDATA", JsonUtil.toJson(map));
        resEvent.setResMap(reqmap);
        return resEvent;
    }



}
