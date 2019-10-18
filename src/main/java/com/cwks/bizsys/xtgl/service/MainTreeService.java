package com.cwks.bizsys.xtgl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizsys.xtgl.domain.DmGySwjgPojo;
import com.cwks.bizsys.xtgl.domain.MainTreePojo;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.dao.JdbcDao;

/**
 * Created by 13977 on 2017/4/24.
 */
//使用自动注解的方式实例化并初始化该类
@Component
@Service("mainTreeService")
public class MainTreeService {

    @Autowired
    private JdbcDao jdbcDao;

    //查询系统管理维护的功能树 xtgnswh.jsp
    public ResponseEvent getGns(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, String> reqmap = new HashMap<String, String>();

        String nodeId = (String)requestEvent.getRequestMap().get("nodeId");
        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sql = new StringBuffer(
                "select t.gn_dm, t.gn_mc, t.gn_jc, t.gn_url, t.sjgn_dm,t.xy_bj,t.gn_cj,t.icon,t.yx_bj,t.cdxh ,t.cdlx, t.opentype \n" +
                    "  from t_xt_gns t\n" +
                    " where t.xy_bj = '1'\n" +
                    "   and t.yx_bj = '1'\n" +
                    "   and t.sjgn_dm = ?\n" +
                    " order by t.cdxh");

        params.add(nodeId);
        List listSql = this.jdbcDao.queryforlist(sql.toString(),params);
        Map<?,?> map = null;
        MainTreePojo vo =null;
        List<MainTreePojo> resList = new ArrayList<MainTreePojo>();
        for(int i=0;i<listSql.size();i++){
            map = (Map<?,?>) listSql.get(i);
            vo = new MainTreePojo();
            vo.setId(map.get("gn_dm") == null ? null : map.get("gn_dm") + "");
            vo.setText(map.get("gn_jc") == null ? null : map.get("gn_jc") + "");
            vo.setGndm(map.get("gn_dm") == null ? null : map.get("gn_dm") + "");
            vo.setGnmc(map.get("gn_mc") == null ? null : map.get("gn_mc") + "");
            vo.setGnjc(map.get("gn_jc") == null ? null : map.get("gn_jc") + "");
            vo.setGnurl(map.get("gn_url") == null ? null : map.get("gn_url") + "");
            vo.setSjgndm(map.get("sjgn_dm") == null ? null : map.get("sjgn_dm") + "");
            vo.setXybj(map.get("xy_bj") == null ? null : map.get("xy_bj") + "");
            vo.setGncj(map.get("gn_cj") == null ? null : map.get("gn_cj") + "");
            vo.setIcon(map.get("icon") == null ? null : map.get("icon") + "");
            vo.setYxbj(map.get("yx_bj") == null ? null : map.get("yx_bj") + "");
            vo.setCdxh(map.get("cdxh") == null ? null : map.get("cdxh") + "");
            vo.setCdlx(map.get("cdlx") == null ? null : map.get("cdlx") + "");
            vo.setHref(map.get("gn_url") == null ? null : map.get("gn_url") + "");
            vo.setNodeId(map.get("sjgn_dm") == null ? null : map.get("sjgn_dm") + "");
            vo.setOpenType(map.get("opentype") == null ? null : map.get("opentype") + "");
            if("1".equals(map.get("cdlx") == null ? null : map.get("cdlx") + "")){
                vo.setState("closed");
            }
            resList.add(vo);
        }

        reqmap.put("JSONDATA", JsonUtil.toJson(resList));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //查询系统管理维护的功能树 xtgnswh.jsp
    public ResponseEvent getGnswh(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, String> reqmap = new HashMap<String, String>();

        String nodeId = (String)requestEvent.getRequestMap().get("nodeId");
        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sql = new StringBuffer(
                "select t.gn_dm, t.gn_mc, t.gn_jc, t.gn_url, t.sjgn_dm,t.xy_bj,t.gn_cj,t.icon,t.yx_bj,t.cdxh ,t.cdlx, t.opentype \n" +
                    "  from t_xt_gns t\n" +
                    " where \n" +
                    "   \n" +
                    " t.sjgn_dm = ?\n" +
                    " order by t.cdxh");

        params.add(nodeId);
        List<?> listSql = this.jdbcDao.queryforlist(sql.toString(),params);
        Map<?,?> map = null;
        MainTreePojo vo =null;
        List<MainTreePojo> resList = new ArrayList<MainTreePojo>();
        for(int i=0;i<listSql.size();i++){
            map = (Map<?,?>) listSql.get(i);
            vo = new MainTreePojo();
            vo.setId(map.get("gn_dm") == null ? null : map.get("gn_dm") + "");
            vo.setText(map.get("gn_jc") == null ? null : map.get("gn_jc") + "");
            vo.setGndm(map.get("gn_dm") == null ? null : map.get("gn_dm") + "");
            vo.setGnmc(map.get("gn_mc") == null ? null : map.get("gn_mc") + "");
            vo.setGnjc(map.get("gn_jc") == null ? null : map.get("gn_jc") + "");
            vo.setGnurl(map.get("gn_url") == null ? null : map.get("gn_url") + "");
            vo.setSjgndm(map.get("sjgn_dm") == null ? null : map.get("sjgn_dm") + "");
            vo.setXybj(map.get("xy_bj") == null ? null : map.get("xy_bj") + "");
            vo.setGncj(map.get("gn_cj") == null ? null : map.get("gn_cj") + "");
            vo.setIcon(map.get("icon") == null ? null : map.get("icon") + "");
            vo.setYxbj(map.get("yx_bj") == null ? null : map.get("yx_bj") + "");
            vo.setCdxh(map.get("cdxh") == null ? null : map.get("cdxh") + "");
            vo.setCdlx(map.get("cdlx") == null ? null : map.get("cdlx") + "");
            vo.setHref(map.get("gn_url") == null ? null : map.get("gn_url") + "");
            vo.setNodeId(map.get("sjgn_dm") == null ? null : map.get("sjgn_dm") + "");
            vo.setOpenType(map.get("opentype") == null ? null : map.get("opentype") + "");
            if("1".equals(map.get("cdlx") == null ? null : map.get("cdlx") + "")){
                vo.setState("closed");
            }
            resList.add(vo);
        }

        reqmap.put("JSONDATA", JsonUtil.toJson(resList));
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    
    //查询主页功能树 main.jsp
    public ResponseEvent getZyGns(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, String> reqmap = new HashMap<String, String>();

        //上级功能菜单
        String nodeId = (String)requestEvent.getRequestMap().get("nodeId");
        //获取session值
        String swrysf_dm = requestEvent.getCtx().getUserinfo().get("swrysf_dm").toString();
        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sql = null;
        String sqllx ="1";
        if("admin".equals(swrysf_dm)){

             sql = new StringBuffer("select t.gn_dm, t.gn_mc, t.gn_jc, t.gn_url, t.sjgn_dm, t.cdlx, t.opentype,t.cdxh, 'admin' userid,'admin' gwdm,'admin' gwxh,'admin' swjg_dm\n" +
                "  from t_xt_gns t\n" +
                " where t.xy_bj = '1'\n" +
                "   and t.yx_bj = '1'\n" +
                "   and t.sjgn_dm = ?\n" +
                " order by t.cdxh");
            params.add(nodeId);
            sqllx="2";

        }else {
             sqllx="1";
             sql = new StringBuffer(
            		 "select d.gn_dm,\n" +
         					"       d.gn_jc || '  [ ' || e.gwmc || ' ] ' gn_jc,\n" + 
         					"       d.gn_mc,\n" + 
         					"       d.gn_url,\n" + 
         					"       d.sjgn_dm,\n" + 
         					"       d.cdlx,\n" + 
         					"       d.opentype,\n" + 
         					"       d.cdxh,\n" + 
         					"       d.gn_cj,\n" + 
         					"       d.icon,\n" + 
         					"       (select f.swry_dm from DM_QX_SWRYSF f where f.swrysf_dm = ?) userid,\n" + 
         					"       (select b.swjg_dm\n" + 
         					"          from QX_JGGW_SWRYSF a, QX_SWJG_GW b\n" + 
         					"         where a.gwxh = b.gwxh\n" + 
         					"           and a.swrysf_dm = ?\n" + 
         					"           and b.gw_dm = e.gw_dm) swjg_dm,\n" + 
         					"       (select b.gwxh\n" + 
         					"          from QX_JGGW_SWRYSF a, QX_SWJG_GW b\n" + 
         					"         where a.gwxh = b.gwxh\n" + 
         					"           and a.swrysf_dm = ?\n" + 
         					"           and b.gw_dm = e.gw_dm) gwxh,\n" + 
         					"       (select b.gw_dm\n" + 
         					"          from QX_JGGW_SWRYSF a, QX_SWJG_GW b\n" + 
         					"         where a.gwxh = b.gwxh\n" + 
         					"           and a.swrysf_dm = ?\n" + 
         					"           and b.gw_dm = e.gw_dm) gwdm\n" + 
         					"  from QX_GW_XTGN c, T_XT_GNS d, DM_QX_GW e\n" + 
         					" where c.xtgn_dm = d.gn_dm\n" + 
         					"   and d.xy_bj = '1'\n" + 
         					"   and d.yx_bj = '1'\n" + 
         					"   and e.gw_dm = c.gw_dm\n" + 
         					"   and c.gw_dm in (select b.gw_dm\n" + 
         					"                     from QX_JGGW_SWRYSF a, QX_SWJG_GW b\n" + 
         					"                    where a.gwxh = b.gwxh\n" + 
         					"                      and a.swrysf_dm = ?)\n" + 
         					"   and d.sjgn_dm = ?\n" + 
         					" order by cdxh, swjg_dm, gn_jc"
            		 );
            ;
            params.add(swrysf_dm);
            params.add(swrysf_dm);
            params.add(swrysf_dm);
            params.add(swrysf_dm);
            params.add(swrysf_dm);
            params.add(nodeId);
        }
        List<?> listSql = this.jdbcDao.queryforlist(sql.toString(),params);
        Map<?,?> map = null;
        MainTreePojo vo =null;
        String gndm ="";
        List<MainTreePojo> resList = new ArrayList<MainTreePojo>();
        for(int i=0;i<listSql.size();i++){
            map = (Map<?,?>) listSql.get(i);
            vo = new MainTreePojo();

            //如果菜单类型是1的话就显示一个
            if ("1".equals(map.get("cdlx") == null ? null : map.get("cdlx") + "")) {
                //判断功能代码是否一致，一致则不执行下去
                if(!gndm.equals(map.get("gn_dm") == null ? null : map.get("gn_dm") + "")) {
                    //截取一样的
                    if(sqllx == "1"){
                        vo.setText((map.get("gn_jc") == null ? null : map.get("gn_jc") + "").substring(0,(map.get("gn_jc") == null ? null : map.get("gn_jc") + "").indexOf("[")));
                    }else {
                        vo.setText(map.get("gn_jc") == null ? null : map.get("gn_jc") + "");
                    }
                    gndm = map.get("gn_dm") == null ? null : map.get("gn_dm") + "";
                    vo.setId(map.get("gn_dm") == null ? null : map.get("gn_dm") + "");
                    vo.setHref(map.get("gn_url") == null ? null : map.get("gn_url") + "");
                    vo.setNodeId(map.get("sjgn_dm") == null ? null : map.get("sjgn_dm") + "");
                    vo.setOpenType(map.get("opentype") == null ? null : map.get("opentype") + "");
                    vo.setGwxh(map.get("gwxh") == null ? null : map.get("gwxh") + "");
                    vo.setSwjg_dm(map.get("swjg_dm") == null ? null : map.get("swjg_dm") + "");
                    vo.setUserid(map.get("userid") == null ? null : map.get("userid") + "");
                    vo.setGw_dm(map.get("gwdm") == null ? null : map.get("gwdm") + "");
                    if ("1".equals(map.get("cdlx") == null ? null : map.get("cdlx") + "")) {
                        vo.setState("closed");
                    }
                    resList.add(vo);
                }
            } else {
                vo.setText(map.get("gn_jc") == null ? null : map.get("gn_jc") + "");
                vo.setId(map.get("gn_dm") == null ? null : map.get("gn_dm") + "");
                vo.setHref(map.get("gn_url") == null ? null : map.get("gn_url") + "");
                vo.setNodeId(map.get("sjgn_dm") == null ? null : map.get("sjgn_dm") + "");
                vo.setOpenType(map.get("opentype") == null ? null : map.get("opentype") + "");
                vo.setGwxh(map.get("gwxh") == null ? null : map.get("gwxh") + "");
                vo.setSwjg_dm(map.get("swjg_dm") == null ? null : map.get("swjg_dm") + "");
                vo.setUserid(map.get("userid") == null ? null : map.get("userid") + "");
                vo.setGw_dm(map.get("gwdm") == null ? null : map.get("gwdm") + "");
                if ("1".equals(map.get("cdlx") == null ? null : map.get("cdlx") + "")) {
                    vo.setState("closed");
                }
                resList.add(vo);
            }

        }

        reqmap.put("JSONDATA", JsonUtil.toJson(resList));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //查询税务机构
    public ResponseEvent getSwjg(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, String> reqmap = new HashMap<String, String>();

        String nodeId = (String)requestEvent.getRequestMap().get("nodeId");
        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sql = new StringBuffer("select t.swjg_dm id, t.swjgmc text, t.swjgjc, t.sjswjg_dm,\n" +
                        "  CASE WHEN (SELECT COUNT(*) FROM DM_GY_SWJG A WHERE A.Sjswjg_Dm = t.swjg_dm) > 0 THEN '0' ELSE '1' END ISLEAF\n" +
                        "  from dm_gy_swjg t\n" +
                        " where t.xybz = 'Y'\n" +
                        "   and t.yxbz = 'Y'\n" +
                        "   and t.sjswjg_dm= ?");

        params.add(nodeId);
        List<?> listSql = this.jdbcDao.queryforlist(sql.toString(),params);
        Map<?,?> map = null;
        MainTreePojo vo =null;
        List<MainTreePojo> resList = new ArrayList<MainTreePojo>();
        for(int i=0;i<listSql.size();i++){
            map = (Map<?,?>) listSql.get(i);
            vo = new MainTreePojo();
            vo.setId(map.get("id") == null ? null : map.get("id") + "");
            vo.setText(map.get("text") == null ? null : map.get("text") + "");
            vo.setNodeId(map.get("sjswjg_dm") == null ? null : map.get("sjswjg_dm") + "");
            if("0".equals(map.get("ISLEAF") == null ? null : map.get("ISLEAF") + "")){
                vo.setState("closed");
            }
            resList.add(vo);
        }

        reqmap.put("JSONDATA", JsonUtil.toJson(resList));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //查询税务机构信息管理功能树 dmGySwjgManager.jsp
    public ResponseEvent getSwjgWh(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, String> reqmap = new HashMap<String, String>();

        String nodeId = (String)requestEvent.getRequestMap().get("nodeId");
        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sql = new StringBuffer(
                    "select t.swjg_dm,\n" +
                        "       t.sjswjg_dm,\n" +
                        "       t.swjgmc,\n" +
                        "       t.swjgjc,\n" +
                        "       t.gdslx_dm,\n" +
                        "       t.swjgywmc,\n" +
                        "       t.swjgbz,\n" +
                        "       t.ghbz,\n" +
                        "       t.jgjc_dm,\n" +
                        "       t.swjgfzr_dm,\n" +
                        "       t.swjgdz,\n" +
                        "       t.czdh,\n" +
                        "       t.dzxx,\n" +
                        "       t.swjglxdh,\n" +
                        "       t.swjgyzbm,\n" +
                        "       t.xzqhsz_dm,\n" +
                        "       t.xybz,\n" +
                        "       t.yxbz,\n" +
                        "       t.yxqsrq,\n" +
                        "       t.yxzzrq, \n" +
                        "  CASE WHEN (SELECT COUNT(*) FROM DM_GY_SWJG A WHERE A.Sjswjg_Dm = t.swjg_dm) > 0 THEN '0' ELSE '1' END ISLEAF\n" +
                        "  from dm_gy_swjg t\n" +
                        "  where t.sjswjg_dm= ?");

        params.add(nodeId);
        List<?> listSql = this.jdbcDao.queryforlist(sql.toString(),params);
        Map<?,?> map = null;
        DmGySwjgPojo vo =null;
        List<DmGySwjgPojo> resList = new ArrayList<DmGySwjgPojo>();
        for(int i=0;i<listSql.size();i++){
            map = (Map<?,?>) listSql.get(i);
            vo = new DmGySwjgPojo();
            vo.setId(map.get("swjg_dm") == null ? null : map.get("swjg_dm") + "");
            vo.setText(map.get("swjgmc") == null ? null : map.get("swjgmc") + "");
            vo.setNodeId(map.get("sjswjg_dm") == null ? null : map.get("sjswjg_dm") + "");
            if("0".equals(map.get("ISLEAF") == null ? null : map.get("ISLEAF") + "")){
                vo.setState("closed");
            }
            vo.setSwjg_dm(map.get("swjg_dm") == null ? null : map.get("swjg_dm") + "");
            vo.setSjswjg_dm(map.get("sjswjg_dm") == null ? null : map.get("sjswjg_dm") + "");
            vo.setSwjgmc(map.get("swjgmc") == null ? null : map.get("swjgmc") + "");
            vo.setSwjgjc(map.get("swjgjc") == null ? null : map.get("swjgjc") + "");
            vo.setGdslx_dm(map.get("gdslx_dm") == null ? null : map.get("gdslx_dm") + "");
            vo.setSwjgywmc(map.get("swjgywmc") == null ? null : map.get("swjgywmc") + "");
            vo.setSwjgbz(map.get("swjgbz") == null ? null : map.get("swjgbz") + "");
            vo.setGhbz(map.get("ghbz") == null ? null : map.get("ghbz") + "");
            vo.setJgjc_dm(map.get("jgjc_dm") == null ? null : map.get("jgjc_dm") + "");
            vo.setSwjgfzr_dm(map.get("swjgfzr_dm") == null ? null : map.get("swjgfzr_dm") + "");
            vo.setSwjgdz(map.get("swjgdz") == null ? null : map.get("swjgdz") + "");
            vo.setCzdh(map.get("czdh") == null ? null : map.get("czdh") + "");
            vo.setDzxx(map.get("dzxx") == null ? null : map.get("dzxx") + "");
            vo.setSwjglxdh(map.get("swjglxdh") == null ? null : map.get("swjglxdh") + "");
            vo.setSwjgyzbm(map.get("swjgyzbm") == null ? null : map.get("swjgyzbm") + "");
            vo.setXzqhsz_dm(map.get("xzqhsz_dm") == null ? null : map.get("xzqhsz_dm") + "");
            vo.setXybz(map.get("xybz") == null ? null : map.get("xybz") + "");
            vo.setYxbz(map.get("yxbz") == null ? null : map.get("yxbz") + "");
            vo.setYxqsrq(map.get("yxqsrq") == null ? null : map.get("yxqsrq") + "");
            vo.setYxzzrq(map.get("yxzzrq") == null ? null : map.get("yxzzrq") + "");
            resList.add(vo);
        }

        reqmap.put("JSONDATA", JsonUtil.toJson(resList));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //查询岗位功能树
    public ResponseEvent getGwGns(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, String> reqmap = new HashMap<String, String>();

        StringBuffer sql = new StringBuffer("select t.gw_dm id,t.gwfl_dm,t.gwmc text,t.gwms,t.swjg_dm,t.yxbz,t.lrrq from dm_qx_gw t order by t.gwfl_dm,t.gw_dm");

        List listSql = this.jdbcDao.queryforlist(sql.toString());
        Map<?,?> map = null;
        MainTreePojo vo =null;
        List<MainTreePojo> resList = new ArrayList<MainTreePojo>();
        for(int i=0;i<listSql.size();i++){
            map = (Map<?,?>) listSql.get(i);
            vo = new MainTreePojo();
            vo.setId(map.get("id") == null ? null : map.get("id") + "");
            vo.setGw_dm(map.get("id") == null ? null : map.get("id") + "");
            vo.setText(map.get("text") == null ? null : map.get("text") + "");
            vo.setGwmc(map.get("text") == null ? null : map.get("text") + "");
            vo.setGwms(map.get("gwms") == null ? null : map.get("gwms") + "");
            vo.setGwfl_dm(map.get("gwfl_dm") == null ? null : map.get("gwfl_dm") + "");
            vo.setSwjg_dm(map.get("swjg_dm") == null ? null : map.get("swjg_dm") + "");
            vo.setYxbj(map.get("yxbz") == null ? null : map.get("yxbz") + "");
            vo.setLrrq(map.get("lrrq") == null ? null : map.get("lrrq") + "");
            resList.add(vo);
        }

        reqmap.put("JSONDATA", JsonUtil.toJson(resList));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //岗位分类代码
    public ResponseEvent getGwFlGns(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, String> reqmap = new HashMap<String, String>();

        String flag = (String)requestEvent.getRequestMap().get("flag");

        //0 为自己新增一个功能树
        if("0".equals(flag)){
            Map<String, String> map2 = new HashMap<String, String>();
            List<Map<String, String>> resList = new ArrayList<Map<String, String>>();
            map2.put("id","00");
            map2.put("text","岗位分类");
            map2.put("state","closed");
            resList.add(map2);
            reqmap.put("JSONDATA", JsonUtil.toJson(resList));
        }else {
            Map<?, ?> map = null;
            MainTreePojo vo = null;
            List<MainTreePojo> resList = new ArrayList<MainTreePojo>();
            StringBuffer sql = new StringBuffer("select t.gwfl_dm id,t.gwflmc text,t.swjg_dm from dm_qx_gwfl t order by t.gwfl_dm");
            List<?> listSql = this.jdbcDao.queryforlist(sql.toString());
            for (int i = 0; i < listSql.size(); i++) {
                map = (Map<?, ?>) listSql.get(i);
                vo = new MainTreePojo();
                vo.setId(map.get("id") == null ? null : map.get("id") + "");
                vo.setText(map.get("text") == null ? null : map.get("text") + "");
                vo.setGwfl_dm(map.get("id") == null ? null : map.get("id") + "");
                vo.setGwflmc(map.get("text") == null ? null : map.get("text") + "");
                vo.setSwjg_dm(map.get("swjg_dm") == null ? null : map.get("swjg_dm") + "");

                resList.add(vo);
            }
            reqmap.put("JSONDATA", JsonUtil.toJson(resList));
        }

        resEvent.setResMap(reqmap);
        return resEvent;
    }
}
