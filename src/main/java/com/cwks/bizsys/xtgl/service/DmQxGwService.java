package com.cwks.bizsys.xtgl.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizsys.xtgl.dao.DmQxGwMapper;
import com.cwks.bizsys.xtgl.dao.QxGwXtgnMapper;
import com.cwks.bizsys.xtgl.dao.local.DmQxGwMapperLocal;
import com.cwks.bizsys.xtgl.dao.local.QxGwXtgnMapperLocal;
import com.cwks.bizsys.xtgl.domain.DmQxGwPojo;
import com.cwks.bizsys.xtgl.domain.QxGwXtgnPojo;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.util.DateUtil;
import com.cwks.common.util.StringUtils;
import com.cwks.common.dao.JdbcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
*
*/
@Component
@Service("dmQxGwService")
public class DmQxGwService{
    private static Logger logger = LoggerFactory.getLogger(DmQxGwService.class);

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private DmQxGwMapper dmQxGwMapper;

    @Autowired
    private QxGwXtgnMapper qxGwXtgnMapper;

    @Autowired
    private DmQxGwMapperLocal dmQxGwMapperLocal;

    @Autowired
    private QxGwXtgnMapperLocal qxGwXtgnMapperLocal;

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
//        DmQxGwPojo dmQxGwPojo = new DmQxGwPojo(requestEvent.getRequestMap());
//        //设置分页
//        PageHelper.startPage(requestEvent.getRequestMap());
//        List<DmQxGwPojo> list = dmQxGwMapper.select(dmQxGwPojo);
//        PageInfo<DmQxGwPojo> pages = new PageInfo<DmQxGwPojo>(list);

        String gw_dm = (String)requestEvent.getRequestMap().get("gw_dm");
        String gwmc = (String)requestEvent.getRequestMap().get("gwmc");
        String gwms = (String)requestEvent.getRequestMap().get("gwms");
        String gwfl_dm_sel = (String)requestEvent.getRequestMap().get("gwfl_dm_sel");

        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sql = new StringBuffer("select t.gw_dm,t.gwfl_dm,t.gwmc,t.gwms,t.swjg_dm,t.yxbz,t.lrrq,t.xgrq,z.gwflmc from dm_qx_gw t,dm_qx_gwfl z where t.gwfl_dm=z.gwfl_dm ");
        if(!"".equals(gw_dm)) {
            sql.append(" and t.gw_dm = ? \n");
            params.add(gw_dm);
        }
        if(!"".equals(gwmc)) {
            sql.append(" and t.gwmc like ? \n");
            params.add("%"+gwmc+"%");
        }
        if(!"".equals(gwms)) {
            sql.append(" and t.gwms = ? \n");
            params.add("%"+gwms+"%");
        }
        if(!"".equals(gwfl_dm_sel)) {
            sql.append(" and t.gwfl_dm = ? \n");
            params.add(gwfl_dm_sel);
        }
        List<?> listSql = this.jdbcDao.queryforlist(sql.toString(),params);
        Map<?,?> map = null;
        DmQxGwPojo dmQxGwPojo = new DmQxGwPojo();
        List<DmQxGwPojo> resList = new ArrayList<DmQxGwPojo>();
        for(int i=0;i<listSql.size();i++){
            map = (Map<?,?>) listSql.get(i);
            dmQxGwPojo = new DmQxGwPojo();
            dmQxGwPojo.setGw_dm(map.get("gw_dm") == null ? null : map.get("gw_dm") + "");
            dmQxGwPojo.setGwfl_dm(map.get("gwfl_dm") == null ? null : map.get("gwfl_dm") + "");
            dmQxGwPojo.setGwmc(map.get("gwmc") == null ? null : map.get("gwmc") + "");
            dmQxGwPojo.setGwms(map.get("gwms") == null ? null : map.get("gwms") + "");
            dmQxGwPojo.setSwjg_dm(map.get("swjg_dm") == null ? null : map.get("swjg_dm") + "");
            dmQxGwPojo.setYxbz(map.get("yxbz") == null ? null : map.get("yxbz") + "");
            dmQxGwPojo.setLrrq(map.get("lrrq") == null ? null : map.get("lrrq") + "");
            dmQxGwPojo.setGwflmc(map.get("gwflmc") == null ? null : map.get("gwflmc") + "");
            dmQxGwPojo.setXgrq(map.get("xgrq") == null ? null : map.get("xgrq") + "");
            resList.add(dmQxGwPojo);
        }

        reqmap.put("JSONDATA", JsonUtil.toJson(resList));
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        //参数注入
        DmQxGwPojo dmQxGwPojo = new DmQxGwPojo(requestEvent.getRequestMap());
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        List<DmQxGwPojo> list = dmQxGwMapper.select(dmQxGwPojo);
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        colMap.put("swjg_dm","税务机关代码");
        colMap.put("gw_dm","岗位代码");
        colMap.put("gwmc","岗位名称");
        colMap.put("gwms","岗位职责描述");
        colMap.put("aqkzlb_dm","安全控制类别代码");
        colMap.put("gwfl_dm","岗位分类代码");
        colMap.put("lrrq","录入日期");
        colMap.put("lrr_dm","录入人代码");
        colMap.put("xgrq","修改日期");
        colMap.put("xgr_dm","修改人代码");
        colMap.put("wqsygwjgjgjc","无权使用岗位机构机关级次");
        colMap.put("xsxh","显示序号");
        colMap.put("yxbz","有效标志");
        //Excel导出格式
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("fileName","岗位信息表");
        dataMap.put("class",DmQxGwPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",list);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String[] gw_dm = (String[])requestEvent.getRequestMap().get("gw_dm");
        DmQxGwPojo pojo = null;
        for(int i=0;i<gw_dm.length;i++){
            pojo = new DmQxGwPojo();
            pojo.setGw_dm(gw_dm[i]);
            pojo.setYxbz("N");
            dmQxGwMapper.updateByPKeySelective(pojo);
            dmQxGwMapperLocal.updateByPKeySelective(pojo);
        }
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        DmQxGwPojo dmQxGwPojo = new DmQxGwPojo(requestEvent.getRequestMap());
        //获取session值
        String dl_swrydm = requestEvent.getCtx().getUserinfo().get("swry_dm").toString();

        //dmQxGwPojo.setGw_dm(UUIDGenerator.getUUID().toUpperCase());
        dmQxGwPojo.setSwjg_dm("23200000000");
        dmQxGwPojo.setLrr_dm(dl_swrydm);
        dmQxGwPojo.setXsxh((double) 1);
        dmQxGwPojo.setLrrq(DateUtil.getDateFormat(new Date(), "yyyy-MM-dd"));
        dmQxGwMapper.insertSelective(dmQxGwPojo);
        dmQxGwMapperLocal.insertSelective(dmQxGwPojo);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //修改
    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        //获取session值
        String dl_swrydm = requestEvent.getCtx().getUserinfo().get("swry_dm").toString();

        DmQxGwPojo dmQxGwPojo = new DmQxGwPojo(requestEvent.getRequestMap());
        dmQxGwPojo.setXgr_dm(dl_swrydm);
        dmQxGwMapper.updateByPKeySelective(dmQxGwPojo);
        dmQxGwMapperLocal.updateByPKeySelective(dmQxGwPojo);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.UPDATE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent selectGwGns(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String gwdm = requestEvent.getRequestMap().get("gwdm").toString();

        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sqlStr = new StringBuffer("select t.gn_dm id,\n" +
                        "       t.sjgn_dm pId,\n" +
                        "       t.gn_mc name,\n" +
                        "       t.gn_url,\n" +
                        "       z.xtgn_dm,\n" +
                        "       case z.gw_dm\n" +
                        "         when ? then\n" +
                        "          'true'\n" +
                        "         else\n" +
                        "          'false'\n" +
                        "       end checked\n" +
                        "  from t_xt_gns t\n" +
                        "  left JOIN qx_gw_xtgn z\n" +
                        "  on t.gn_dm = z.xtgn_dm\n" +
                        "  and z.gw_dm = ?\n" +
                        " start with t.sjgn_dm = '0'\n" +
                        " connect by prior t.gn_dm = t.sjgn_dm\n" +
                        " order siblings by t.gn_dm");

        params.add(gwdm);
        params.add(gwdm);
        List<?> list = jdbcDao.queryforlist(sqlStr.toString(),params);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", JsonUtil.toJson(list).toLowerCase().replaceAll("pid","pId"));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent saveSelectNodes(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();

        //获取session值
        String dl_swrydm = requestEvent.getCtx().getUserinfo().get("swry_dm").toString();

        String selectNodes = requestEvent.getRequestMap().get("selectNodes").toString();
        String gwdm = requestEvent.getRequestMap().get("gwdm").toString();
        String[] ids =null;
        //截取每个 . 之前的字母
        if(StringUtils.isNotEmpty(selectNodes)) {
        	ids = selectNodes.split("\\,");
        }else {
        	ids=new String[] {};
        }
        QxGwXtgnPojo pojo =null;

        //先删除所有的岗位配置功能树的信息
        pojo = new QxGwXtgnPojo();
        pojo.setGw_dm(gwdm);
        qxGwXtgnMapper.deleteByPKey(pojo);
        qxGwXtgnMapperLocal.deleteByPKey(pojo);

        for (int i = 0; i < ids.length; i++) {
            pojo = new QxGwXtgnPojo();
            pojo.setUuid(UUIDGenerator.getUUID().toUpperCase());
            pojo.setGw_dm(gwdm);
            pojo.setXtgn_dm(ids[i]);
            pojo.setYx_bj("1");
            pojo.setLrr_dm(dl_swrydm);
            pojo.setLrrq(DateUtil.getDateFormat(new Date(), "yyyy-MM-dd"));
            qxGwXtgnMapper.insertSelective(pojo);
            qxGwXtgnMapperLocal.insertSelective(pojo);
        }

        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.getMsg(true,"操作成功！"));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //查询岗位分类功能树
    public ResponseEvent selectComboGwfl(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();

        StringBuffer sql = new StringBuffer("select t.gwfl_dm,t.gwflmc from dm_qx_gwfl t where t.yxbz='Y' order by t.gwfl_dm ");

        List<?> listSql = this.jdbcDao.queryforlist(sql.toString());
        Map<?,?> map = null;
        DmQxGwPojo dmQxGwPojo = new DmQxGwPojo();
        List<DmQxGwPojo> resList = new ArrayList<DmQxGwPojo>();
        for(int i=0;i<listSql.size();i++){
            map = (Map<?,?>) listSql.get(i);
            dmQxGwPojo = new DmQxGwPojo();
            dmQxGwPojo.setGwfl_dm(map.get("gwfl_dm") == null ? null : map.get("gwfl_dm") + "");
            dmQxGwPojo.setGwflmc(map.get("gwflmc") == null ? null : map.get("gwflmc") + "");
            resList.add(dmQxGwPojo);
        }

        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", JsonUtil.toJson(resList));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

}
