package com.cwks.bizsys.xtgl.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizsys.xtgl.dao.DmGySwryMapper;
import com.cwks.bizsys.xtgl.dao.DmQxSwrySfMapper;
import com.cwks.bizsys.xtgl.dao.QxDlzhxxMapper;
import com.cwks.bizsys.xtgl.dao.QxJggwSwrySfMapper;
import com.cwks.bizsys.xtgl.dao.local.DmGySwryMapperLocal;
import com.cwks.bizsys.xtgl.dao.local.DmQxSwrySfMapperLocal;
import com.cwks.bizsys.xtgl.dao.local.QxDlzhxxMapperLocal;
import com.cwks.bizsys.xtgl.dao.local.QxJggwSwrySfMapperLocal;
import com.cwks.bizsys.xtgl.domain.*;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.util.DateUtil;
import com.cwks.common.dao.JdbcDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

/**
*
*/
@Component
@Service("dmGySwryService")
public class DmGySwryService{

    private static Logger logger = LoggerFactory.getLogger(DmGySwryService.class);

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private DmGySwryMapper dmGySwryMapper;

    @Autowired
    private DmQxSwrySfMapper dmQxSwrySfMapper;

    @Autowired
    private QxDlzhxxMapper qxDlzhxxMapper;

    @Autowired
    private QxJggwSwrySfMapper qxJggwSwrySfMapper;

    @Autowired
    private DmGySwryMapperLocal dmGySwryMapperLocal;

    @Autowired
    private DmQxSwrySfMapperLocal dmQxSwrySfMapperLocal;

    @Autowired
    private QxDlzhxxMapperLocal qxDlzhxxMapperLocal;

    @Autowired
    private QxJggwSwrySfMapperLocal qxJggwSwrySfMapperLocal;

    //查询人员信息
    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        //参数注入
        //DmGySwryPojo dmGySwryPojo = new DmGySwryPojo(requestEvent.getRequestMap());
        //设置分页
//        PageHelper.startPage(requestEvent.getRequestMap());
//        List<DmGySwryPojo> list = dmGySwryMapper.select(dmGySwryPojo);
//        PageInfo<DmGySwryPojo> pages = new PageInfo<DmGySwryPojo>(list);
//        reqmap.put("JSONDATA", JsonUtil.toJson(pages));
        String swry_dm = (String)requestEvent.getRequestMap().get("swry_dm");
        String swryxm = (String)requestEvent.getRequestMap().get("swryxm");
        String swjg_dm = (String)requestEvent.getRequestMap().get("swjg_dm");

        if("".equals(swjg_dm)){
            swjg_dm = "00000000000";
        }
        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sql =new StringBuffer(" select a.swjgmc,a.swjg_dm, b.rysfmc,c.swry_dm,c.swryxm,c.xb_dm,d.dlzh_dm,d.dlzhkl,c.jrswjsj,d.yxbz,b.swrysf_dm ,c.zw,c.sfzjhm,b.swrysf_dm,c.lxdh,c.dzxx, d.lrrq,d.xgrq\n" +
                        " from dm_gy_swjg a, dm_qx_swrysf b, DM_GY_SWRY c,Qx_Dlzhxx d\n" +
                        " where a.swjg_dm = b.sfswjg_dm\n" +
                        "  and b.swry_dm = c.swry_dm\n" +
                        "  and d.swry_dm = c.swry_dm\n");
        if(!"".equals(swjg_dm)) {
            sql.append(" and a.swjg_dm = ? \n");
            params.add(swjg_dm);
        }
        if(!"".equals(swry_dm)) {
            sql.append(" and c.swry_dm = ? \n");
            params.add(swry_dm);
        }
        if(!"".equals(swryxm)) {
            sql.append(" and c.swryxm like ?  \n");
            params.add("%"+swryxm+"%");
        }

        sql.append(" order by c.swry_dm ");

        List<?> listSql = this.jdbcDao.queryforlist(sql.toString(),params);
        Map<?,?> map = null;
        XtglPojo xtglPojo = null;
        List<XtglPojo> resList = new ArrayList<XtglPojo>();
        for(int i=0;i<listSql.size();i++){
            map = (Map<?,?>) listSql.get(i);
            xtglPojo = new XtglPojo();
            xtglPojo.setSwjgmc(map.get("swjgmc") == null ? null : map.get("swjgmc") + "");
            xtglPojo.setSwjgdm(map.get("swjg_dm") == null ? null : map.get("swjg_dm") + "");
            xtglPojo.setRysfmc(map.get("rysfmc") == null ? null : map.get("rysfmc") + "");
            xtglPojo.setSwry_dm(map.get("swry_dm") == null ? null : map.get("swry_dm") + "");
            xtglPojo.setSwryxm(map.get("swryxm") == null ? null : map.get("swryxm") + "");
            xtglPojo.setXb_dm(map.get("xb_dm") == null ? null : map.get("xb_dm") + "");
            xtglPojo.setDlzh_dm(map.get("dlzh_dm") == null ? null : map.get("dlzh_dm") + "");
            xtglPojo.setDlzhkl(DigestUtils.md5Hex(map.get("dlzhkl") == null ? null : map.get("dlzhkl") + "").toUpperCase());
            xtglPojo.setJrswjsj(map.get("jrswjsj") == null ? null : map.get("jrswjsj") + "");
            xtglPojo.setYxbz(map.get("yxbz") == null ? null : map.get("yxbz") + "");
            xtglPojo.setSwrysfdm(map.get("swrysf_dm") == null ? null : map.get("swrysf_dm") + "");
            xtglPojo.setZw(map.get("zw") == null ? null : map.get("zw") + "");
            xtglPojo.setSfzjhm(map.get("sfzjhm") == null ? null : map.get("sfzjhm") + "");
            xtglPojo.setLxdh(map.get("lxdh") == null ? null : map.get("lxdh") + "");
            xtglPojo.setDzxx(map.get("dzxx") == null ? null : map.get("dzxx") + "");
            xtglPojo.setLrrq(map.get("lrrq") == null ? null : map.get("lrrq") + "");
            xtglPojo.setXgrq(map.get("xgrq") == null ? null : map.get("xgrq") + "");
            resList.add(xtglPojo);
        }
        reqmap.put("JSONDATA", JsonUtil.toJson(resList));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //查询税务人员身份信息
    public ResponseEvent selectSwrysf(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Object> reqmap = new HashMap<String, Object>();

        String swry_dm = (String)requestEvent.getRequestMap().get("swry_dm");

        if("".equals(swry_dm)){
            swry_dm = "0";
        }
        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sql =new StringBuffer(" select a.swjg_dm,a.swjgmc,b.swry_dm,b.SWRYSF_DM, b.rysfmc, c.swryxm, b.zsfbz,b.yxbz\n" +
                        "   from dm_gy_swjg a, dm_qx_swrysf b, DM_GY_SWRY c\n" +
                        "  where a.swjg_dm = b.sfswjg_dm\n" +
                        "    and b.swry_dm = c.swry_dm\n" +
                        "    and b.swry_dm = ?\n" +
                        "  order by b.SWRYSF_DM ");

        params.add(swry_dm);
        List<?> listSql = this.jdbcDao.queryforlist(sql.toString(),params);
        Map<?,?> map = null;
        XtglPojo xtglPojo = null;
        List<XtglPojo> resList = new ArrayList<XtglPojo>();
        for(int i=0;i<listSql.size();i++){
            map = (Map<?,?>) listSql.get(i);
            xtglPojo = new XtglPojo();
            xtglPojo.setSwjgdm(map.get("swjg_dm") == null ? null : map.get("swjg_dm") + "");
            xtglPojo.setSwry_dm(map.get("swry_dm") == null ? null : map.get("swry_dm") + "");
            xtglPojo.setSwjgmc(map.get("swjgmc") == null ? null : map.get("swjgmc") + "");
            xtglPojo.setSwrysfdm(map.get("SWRYSF_DM") == null ? null : map.get("SWRYSF_DM") + "");
            xtglPojo.setRysfmc(map.get("rysfmc") == null ? null : map.get("rysfmc") + "");
            xtglPojo.setSwryxm(map.get("swryxm") == null ? null : map.get("swryxm") + "");
            xtglPojo.setZsfbz(map.get("zsfbz") == null ? null : map.get("zsfbz") + "");
            xtglPojo.setYxbz(map.get("yxbz") == null ? null : map.get("yxbz") + "");

            resList.add(xtglPojo);
        }
        reqmap.put("JSONDATA", JsonUtil.toJson(resList));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //判断人员是否已经填入信息
    public ResponseEvent selectSfzjhm(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Object> reqmap = new HashMap<String, Object>();

        String sfzjhm = (String)requestEvent.getRequestMap().get("sfzjhm");

        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sql =new StringBuffer("select a.swry_dm, a.swryxm, c.swjg_dm, c.swjgmc\n" +
                        "            from DM_GY_SWRY a, dm_qx_swrysf b, dm_gy_swjg c\n" +
                        "           where b.sfswjg_dm = c.swjg_dm\n" +
                        "             and b.swry_dm = a.swry_dm\n" +
                        "             and a.sfzjhm = ? ");

        params.add(sfzjhm);
        List<?> listSql = this.jdbcDao.queryforlist(sql.toString(),params);
        Map<?,?> map = null;
        XtglPojo xtglPojo = null;
        List<XtglPojo> resList = new ArrayList<XtglPojo>();
        for(int i=0;i<listSql.size();i++){
            map = (Map<?,?>) listSql.get(i);
            xtglPojo = new XtglPojo();
            xtglPojo.setSwry_dm(map.get("swry_dm") == null ? null : map.get("swry_dm") + "");
            xtglPojo.setSwryxm(map.get("swryxm") == null ? null : map.get("swryxm") + "");
            xtglPojo.setSwjgdm(map.get("swry_dm") == null ? null : map.get("swry_dm") + "");
            xtglPojo.setSwjgmc(map.get("swjgmc") == null ? null : map.get("swjgmc") + "");

            resList.add(xtglPojo);
        }
        reqmap.put("JSONDATA", JsonUtil.toJson(resList));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        //参数注入
        DmGySwryPojo dmGySwryPojo = new DmGySwryPojo(requestEvent.getRequestMap());
        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        List<DmGySwryPojo> list = dmGySwryMapper.select(dmGySwryPojo);
        Map<String, String> colMap = new LinkedHashMap<String, String>();

        colMap.put("swry_dm","税务人员代码");
        colMap.put("swryxm","税务人员姓名");
        colMap.put("zw","职务");
        colMap.put("xb_dm","性别代码");
        colMap.put("lxdh","联系电话");
        colMap.put("sfzjhm","身份证件号码");
        colMap.put("dzxx","电子信箱");
        colMap.put("jrswjsj","进入税务局时间");
        colMap.put("lkswjsj","离开税务局时间");
        colMap.put("yxbz","有效标志");
        colMap.put("zc_dm","ZC_DM");

        //Excel导出格式
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("fileName","人员信息表");
        dataMap.put("class",DmGySwryPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",list);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    //作废人员信息
    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String[] swry_dm = (String[])requestEvent.getRequestMap().get("swry_dm");
        String zf_bj = requestEvent.getRequestMap().get("zf_bj").toString();

        //登录账户
        QxDlzhxxPojo qxDlzhxxPojo = null;
        //税务人员身份
        DmQxSwrySfPojo dmQxSwrySfPojo = null;
        //税务人员
        DmGySwryPojo dmGySwryPojo = null;
        for(int i=0;i<swry_dm.length;i++){
            //作废登录账户
            qxDlzhxxPojo = new QxDlzhxxPojo();
            qxDlzhxxPojo.setYxbz(zf_bj);
            qxDlzhxxPojo.setSwry_dm(swry_dm[i]);
            //原表
            qxDlzhxxMapper.updateBySwrydm(qxDlzhxxPojo);
            //附表
            qxDlzhxxMapperLocal.updateBySwrydm(qxDlzhxxPojo);

            //作废税务人员身份
            dmGySwryPojo = new DmGySwryPojo();
            dmGySwryPojo.setYxbz(zf_bj);
            dmGySwryPojo.setSwry_dm(swry_dm[i]);
            //原表
            dmGySwryMapper.updateByPKeySelective(dmGySwryPojo);
            //附表
            dmGySwryMapperLocal.updateByPKeySelective(dmGySwryPojo);

            //作废税务人员
            dmQxSwrySfPojo = new DmQxSwrySfPojo();
            dmQxSwrySfPojo.setYxbz(zf_bj);
            dmQxSwrySfPojo.setSwry_dm(swry_dm[i]);
            //原表
            dmQxSwrySfMapper.updateBySwrydm(dmQxSwrySfPojo);
            //附表
            dmQxSwrySfMapperLocal.updateBySwrydm(dmQxSwrySfPojo);

        }
        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    //作废人员身份信息
    public ResponseEvent removeSwrysf(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String[] swrysfdm = (String[])requestEvent.getRequestMap().get("swrysfdm");
        DmQxSwrySfPojo dmQxSwrySfPojo = null;
        for(int i=0;i<swrysfdm.length;i++){
            dmQxSwrySfPojo = new DmQxSwrySfPojo();
            dmQxSwrySfPojo.setSwrysf_dm(swrysfdm[i]);
            dmQxSwrySfPojo.setYxbz("N");
            //原表
            dmQxSwrySfMapper.updateByPKeySelective(dmQxSwrySfPojo);
            //附表
            dmQxSwrySfMapperLocal.updateByPKeySelective(dmQxSwrySfPojo);
        }
        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    //新增人员
    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();

        //获取session值
        String dl_swrydm = requestEvent.getCtx().getUserinfo().get("swry_dm").toString();

        //DmGySwryPojo dmGySwryPojo = new DmGySwryPojo(requestEvent.getRequestMap());
        String swry_dm = requestEvent.getRequestMap().get("swry_dm").toString();
        String swryxm = requestEvent.getRequestMap().get("swryxm").toString();
        String swrydlzh = requestEvent.getRequestMap().get("dlzh_dm").toString();
        String swrydlmm = requestEvent.getRequestMap().get("dlzhkl").toString();
        String xb_dm = requestEvent.getRequestMap().get("xb_dm").toString();
        String zw = requestEvent.getRequestMap().get("zw").toString();
        String swrysfdm = requestEvent.getRequestMap().get("swrysfdm").toString();
        String swrysfmc = requestEvent.getRequestMap().get("swrysfmc").toString();
        String swjgdm = requestEvent.getRequestMap().get("swjgdm").toString();
        String jrswjsj = requestEvent.getRequestMap().get("jrswjsj").toString();
        String lxdh = requestEvent.getRequestMap().get("lxdh").toString();
        String sfzjhm = requestEvent.getRequestMap().get("sfzjhm").toString();
        String dzxx = requestEvent.getRequestMap().get("dzxx").toString();
        String yxbz = requestEvent.getRequestMap().get("yxbz").toString();

        //新增 人员账户表Qx_Dlzhxx
        //insert into Qx_Dlzhxx (DLZH_DM,SWRY_DM,DLZHKL,LRR_DM,YXBZ) values (?,?,?,?,?);
        QxDlzhxxPojo qxDlzhxxPojo = new QxDlzhxxPojo();
        qxDlzhxxPojo.setDlzh_dm(swrydlzh);
        qxDlzhxxPojo.setSwry_dm(swry_dm);
        //qxDlzhxxPojo.setDlzhkl(swrydlmm);
        qxDlzhxxPojo.setDlzhkl(DigestUtils.md5Hex(swrydlmm).toUpperCase());
        qxDlzhxxPojo.setLrr_dm(dl_swrydm);
        qxDlzhxxPojo.setYxbz(yxbz);
        qxDlzhxxMapper.insertSelective(qxDlzhxxPojo);
        qxDlzhxxMapperLocal.insertSelective(qxDlzhxxPojo);

        //新增 人员表 DM_GY_SWRY
        //insert into DM_GY_SWRY (SWRY_DM,SWRYXM,SFZJHM,XB_DM,ZW,LXDH,DZXX,JRSWJSJ,YXBZ) values(?,?,?,?,?,?,?,?,?);
        DmGySwryPojo dmGySwryPojo = new DmGySwryPojo();
        dmGySwryPojo.setSwry_dm(swry_dm);
        dmGySwryPojo.setSwryxm(swryxm);
        dmGySwryPojo.setSfzjhm(sfzjhm);
        dmGySwryPojo.setXb_dm(xb_dm);
        dmGySwryPojo.setZw(zw);
        dmGySwryPojo.setLxdh(lxdh);
        dmGySwryPojo.setDzxx(dzxx);
        dmGySwryPojo.setJrswjsj(jrswjsj);
        dmGySwryPojo.setYxbz(yxbz);
        dmGySwryMapper.insertSelective(dmGySwryPojo);
        dmGySwryMapperLocal.insertSelective(dmGySwryPojo);

        //新增 人员身份表 dm_qx_swrysf
        //insert into dm_qx_swrysf (SWRYSF_DM,RYSFMC,SWRY_DM,SFSWJG_DM,YXBZ,LRR_DM) values (?,?,?,?,?,?);
        DmQxSwrySfPojo dmQxSwrySfPojo = new DmQxSwrySfPojo();
        dmQxSwrySfPojo.setSwrysf_dm(swrysfdm);
        dmQxSwrySfPojo.setRysfmc(swrysfmc);
        dmQxSwrySfPojo.setSwry_dm(swry_dm);
        dmQxSwrySfPojo.setSfswjg_dm(swjgdm);
        dmQxSwrySfPojo.setYxbz(yxbz);
        dmQxSwrySfPojo.setLrr_dm(dl_swrydm);
        dmQxSwrySfPojo.setZsfbz("Y");
        dmQxSwrySfMapper.insertSelective(dmQxSwrySfPojo);
        dmQxSwrySfMapperLocal.insertSelective(dmQxSwrySfPojo);

        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //修改人员信息
    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        //DmGySwryPojo dmGySwryPojo = new DmGySwryPojo(requestEvent.getRequestMap());
        //dmGySwryMapper.updateByPKeySelective(dmGySwryPojo);

        //获取session值
        String dl_swrydm = requestEvent.getCtx().getUserinfo().get("swry_dm").toString();

        String swry_dm = requestEvent.getRequestMap().get("swry_dm").toString();
        String swryxm = requestEvent.getRequestMap().get("swryxm").toString();
        String swrydlzh = requestEvent.getRequestMap().get("dlzh_dm").toString();
        String swrydlmm = requestEvent.getRequestMap().get("dlzhkl").toString();
        String xb_dm = requestEvent.getRequestMap().get("xb_dm").toString();
        String zw = requestEvent.getRequestMap().get("zw").toString();
        String swrysfdm = requestEvent.getRequestMap().get("swrysfdm").toString();
        String swrysfmc = requestEvent.getRequestMap().get("swrysfmc").toString();
        String swjgdm = requestEvent.getRequestMap().get("swjgdm").toString();
        String jrswjsj = requestEvent.getRequestMap().get("jrswjsj").toString();
        String lxdh = requestEvent.getRequestMap().get("lxdh").toString();
        String sfzjhm = requestEvent.getRequestMap().get("sfzjhm").toString();
        String dzxx = requestEvent.getRequestMap().get("dzxx").toString();
        String yxbz = requestEvent.getRequestMap().get("yxbz").toString();

        //判断是否是数字
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");

        //修改 人员账户表Qx_Dlzhxx
        QxDlzhxxPojo qxDlzhxxPojo = new QxDlzhxxPojo();
        qxDlzhxxPojo.setDlzh_dm(swrydlzh);
        qxDlzhxxPojo.setSwry_dm(swry_dm);
        //qxDlzhxxPojo.setDlzhkl(swrydlmm);
        qxDlzhxxPojo.setDlzhkl(DigestUtils.md5Hex(swrydlmm).toUpperCase());
        qxDlzhxxPojo.setXgr_dm(dl_swrydm);
        qxDlzhxxPojo.setYxbz(yxbz);
        qxDlzhxxMapper.updateByPKeySelective(qxDlzhxxPojo);
        qxDlzhxxMapperLocal.updateByPKeySelective(qxDlzhxxPojo);

        //修改 人员表 DM_GY_SWRY
        DmGySwryPojo dmGySwryPojo = new DmGySwryPojo();
        dmGySwryPojo.setSwry_dm(swry_dm);
        dmGySwryPojo.setSwryxm(swryxm);
        dmGySwryPojo.setSfzjhm(sfzjhm);
        dmGySwryPojo.setXb_dm(xb_dm);
        dmGySwryPojo.setZw(zw);
        dmGySwryPojo.setLxdh(lxdh);
        dmGySwryPojo.setDzxx(dzxx);
        dmGySwryPojo.setJrswjsj(jrswjsj);
        dmGySwryPojo.setYxbz(yxbz);
        dmGySwryMapper.updateByPKeySelective(dmGySwryPojo);
        dmGySwryMapperLocal.updateByPKeySelective(dmGySwryPojo);

        //修改 人员身份表 dm_qx_swrysf
        DmQxSwrySfPojo dmQxSwrySfPojo = new DmQxSwrySfPojo();
        dmQxSwrySfPojo.setSwrysf_dm(swrysfdm);
        dmQxSwrySfPojo.setRysfmc(swrysfmc);
        dmQxSwrySfPojo.setSwry_dm(swry_dm);
        //未修改税务机关则不修改
        if( pattern.matcher(swjgdm).matches()) {
            dmQxSwrySfPojo.setSfswjg_dm(swjgdm);
        }
        dmQxSwrySfPojo.setYxbz(yxbz);
        dmQxSwrySfPojo.setXgr_dm(dl_swrydm);
        dmQxSwrySfPojo.setZsfbz("Y");
        dmQxSwrySfMapper.updateByPKeySelective(dmQxSwrySfPojo);
        dmQxSwrySfMapperLocal.updateByPKeySelective(dmQxSwrySfPojo);

        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        reqmap.put("JSONDATA", RJson.UPDATE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //新增人员身份
    public ResponseEvent saveSwrysf(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();

        //获取session值
        String dl_swrydm = requestEvent.getCtx().getUserinfo().get("swry_dm").toString();

        String swry_dm = requestEvent.getRequestMap().get("swry_dm").toString();
        String swrysfdm = requestEvent.getRequestMap().get("swrysfdm").toString();
        String swrysfmc = requestEvent.getRequestMap().get("rysfmc").toString();
        String swjgdm = requestEvent.getRequestMap().get("swjgmc").toString();
        String yxbz = requestEvent.getRequestMap().get("yxbz").toString();
        String zsfbz = requestEvent.getRequestMap().get("zsfbz").toString();

        //新增 人员身份表 dm_qx_swrysf
        //insert into dm_qx_swrysf (SWRYSF_DM,RYSFMC,SWRY_DM,SFSWJG_DM,YXBZ,LRR_DM) values (?,?,?,?,?,?);
        DmQxSwrySfPojo dmQxSwrySfPojo = new DmQxSwrySfPojo();
        dmQxSwrySfPojo.setSwrysf_dm(swrysfdm);
        dmQxSwrySfPojo.setRysfmc(swrysfmc);
        dmQxSwrySfPojo.setSwry_dm(swry_dm);
        dmQxSwrySfPojo.setSfswjg_dm(swjgdm);
        dmQxSwrySfPojo.setYxbz(yxbz);
        dmQxSwrySfPojo.setLrr_dm(dl_swrydm);
        dmQxSwrySfPojo.setZsfbz(zsfbz);
        dmQxSwrySfPojo.setLrrq(DateUtil.getDateFormat(new Date(), "yyyy-MM-dd"));
        dmQxSwrySfPojo.setXsxh(new Double(1));
        dmQxSwrySfMapper.insertSelective(dmQxSwrySfPojo);
        dmQxSwrySfMapperLocal.insertSelective(dmQxSwrySfPojo);

        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //修改人员身份
    public ResponseEvent updateSwrysf(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();

        //获取session值
        String dl_swrydm = requestEvent.getCtx().getUserinfo().get("swry_dm").toString();

        String swry_dm = requestEvent.getRequestMap().get("swry_dm").toString();
        String swrysfdm = requestEvent.getRequestMap().get("swrysfdm").toString();
        String swrysfmc = requestEvent.getRequestMap().get("rysfmc").toString();
        String swjgdm = requestEvent.getRequestMap().get("swjgdm").toString();
        String yxbz = requestEvent.getRequestMap().get("yxbz").toString();
        String zsfbz = requestEvent.getRequestMap().get("zsfbz").toString();

        //判断是否是数字
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");

        //新增 人员身份表 dm_qx_swrysf
        //insert into dm_qx_swrysf (SWRYSF_DM,RYSFMC,SWRY_DM,SFSWJG_DM,YXBZ,LRR_DM) values (?,?,?,?,?,?);
        DmQxSwrySfPojo dmQxSwrySfPojo = new DmQxSwrySfPojo();
        dmQxSwrySfPojo.setSwrysf_dm(swrysfdm);
        dmQxSwrySfPojo.setRysfmc(swrysfmc);
        dmQxSwrySfPojo.setSwry_dm(swry_dm);
        //未修改税务机关则不修改
        if( pattern.matcher(swjgdm).matches()) {
            dmQxSwrySfPojo.setSfswjg_dm(swjgdm);
        }
        dmQxSwrySfPojo.setYxbz(yxbz);
        dmQxSwrySfPojo.setXgr_dm(dl_swrydm);
        dmQxSwrySfPojo.setZsfbz(zsfbz);
        dmQxSwrySfMapper.updateByPKeySelective(dmQxSwrySfPojo);
        dmQxSwrySfMapperLocal.updateByPKeySelective(dmQxSwrySfPojo);

        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        reqmap.put("JSONDATA", RJson.UPDATE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //查询税务人员代码
    public ResponseEvent selectSwrydm(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String swry_dm = requestEvent.getRequestMap().get("swry_dm").toString();

        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sqlStr = new StringBuffer("select * from Qx_Dlzhxx t where t.swry_dm=? ");
        params.add(swry_dm);
        List<?> list = jdbcDao.queryforlist(sqlStr.toString(),params);

        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        if(list.size() != 0){
            reqmap.put("JSONDATA",new RJson(false,"税务人员代码已存在！") );
        }else {
            reqmap.put("JSONDATA",new RJson(true,"可新增") );
        }
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //查询税务人员登录账户
    public ResponseEvent selectSwrydlzh(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String swrydlzh = requestEvent.getRequestMap().get("dlzh_dm").toString();

        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sqlStr =new StringBuffer( "select * from Qx_Dlzhxx t where t.dlzh_dm=? ");
        params.add(swrydlzh);
        List<?> list = jdbcDao.queryforlist(sqlStr.toString(),params);

        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        if(list.size() != 0){
            reqmap.put("JSONDATA",new RJson(false,"税务人员登陆账号已存在！") );
        }else {
            reqmap.put("JSONDATA",new RJson(true,"可新增") );
        }
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //查询税务人员身份是否存在
    public ResponseEvent selectSwrysfSwjg(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String swjgdm = requestEvent.getRequestMap().get("swjgdm").toString();
        String swry_dm = requestEvent.getRequestMap().get("swry_dm").toString();

        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sqlStr = new StringBuffer("select * from dm_qx_swrysf t where t.swry_dm=? and t.sfswjg_dm=? ");

        params.add(swry_dm);
        params.add(swjgdm);
        List<?> list = jdbcDao.queryforlist(sqlStr.toString(),params);

        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        if(list.size() != 0){
            reqmap.put("JSONDATA",new RJson(false,"税务人员身份已存在！") );
        }else {
            reqmap.put("JSONDATA",new RJson(true,"可新增") );
        }
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent selectComboZw(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        StringBuffer sqlStr = new StringBuffer(" select t.dm_id id,t.dm_text text from t_xt_dev_code_dm t where dm_fl='10008' ");
        List<?> list = jdbcDao.queryforlist(sqlStr.toString());
        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        reqmap.put("JSONDATA", JsonUtil.toJson(list).toLowerCase());
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //税务人员身份对应机关岗位
    public ResponseEvent selectGwGns(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();

        String swrysfdm = requestEvent.getRequestMap().get("swrysfdm").toString();
        String gwfl_dm = requestEvent.getRequestMap().get("gwfl_dm").toString();

        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sqlStr = new StringBuffer("select ss.gw_dm id,\n" +
                        "       ss.gwmc name,ss.gwxh, \n" +
                        "       (case t.gwdm when ss.gw_dm then 'true' else 'false' end) checked\n" +
                        "  from (SELECT distinct sg.gw_dm gwdm\n" +
                        "          FROM qx_jggw_swrysf js, qx_swjg_gw sg\n" +
                        "         where swrysf_dm = ?\n" +
                        "           and sg.gwxh = js.gwxh) t\n" +
                        " right join (SELECT distinct qg.gw_dm, qg.gwmc,sg.gwxh,qg.gwfl_dm \n" +
                        "               FROM qx_swjg_gw sg, dm_qx_gw qg\n" +
                        "              where sg.gw_dm = qg.gw_dm\n" +
                        "                and qg.gwfl_dm = ? " +
                        "                and sg.swjg_dm in\n" +
                        "                    (SELECT sfswjg_dm FROM dm_qx_swrysf s\n" +
                        "                     where S.swrysf_dm = ?)) ss on ss.gw_dm = t.gwdm order by ss.gwfl_dm,ss.gw_dm");

        params.add(swrysfdm);
        params.add(gwfl_dm);
        params.add(swrysfdm);
        List<?> list = jdbcDao.queryforlist(sqlStr.toString(),params);
        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        reqmap.put("JSONDATA", JsonUtil.toJson(list).toLowerCase().replaceAll("pid","pId"));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //保存税务人员身份对应机关岗位
    public ResponseEvent saveSelectNodes(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();

        //获取session值
        String dl_swrydm = requestEvent.getCtx().getUserinfo().get("swry_dm").toString();

        String swrysfdm = requestEvent.getRequestMap().get("swrysfdm").toString();
        String selectNodes = requestEvent.getRequestMap().get("selectNodes").toString();
        String gwxhAll = (String) requestEvent.getRequestMap().get("gwxhAll");
        //截取每个 , 之前的字母
        String[] gwxhs = selectNodes.split("\\,");
        String[] gwxhAlls = gwxhAll.split("\\,");

        QxJggwSwrySfPojo qxJggwSwrySfPojo = null;

        //先删除所有的对应人员身份的岗位配置功能树的信息
        for (int i = 0; i < gwxhAlls.length; i++) {
            qxJggwSwrySfPojo = new QxJggwSwrySfPojo();
            qxJggwSwrySfPojo.setGwxh(gwxhAlls[i]);
            qxJggwSwrySfMapper.deleteByGWXH(qxJggwSwrySfPojo);
            qxJggwSwrySfMapperLocal.deleteByGWXH(qxJggwSwrySfPojo);
        }

        for (int i = 0; i < gwxhs.length; i++) {
            qxJggwSwrySfPojo = new QxJggwSwrySfPojo();
            qxJggwSwrySfPojo.setUuid(UUIDGenerator.getUUID().toUpperCase());
            qxJggwSwrySfPojo.setSwrysf_dm(swrysfdm);
            qxJggwSwrySfPojo.setGwxh(gwxhs[i].toUpperCase());
            qxJggwSwrySfPojo.setLrr_dm(dl_swrydm);
            qxJggwSwrySfPojo.setYxbz("Y");
            qxJggwSwrySfPojo.setLrrq(DateUtil.getDateFormat(new Date(), "yyyy-MM-dd"));
            if(!"".equals(gwxhs[i].toUpperCase())){
            	qxJggwSwrySfMapper.insertSelective(qxJggwSwrySfPojo);
                qxJggwSwrySfMapperLocal.insertSelective(qxJggwSwrySfPojo);
            }
        }

        HashMap<String, Object> reqmap = new HashMap<String, Object>();
        reqmap.put("JSONDATA", RJson.getMsg(true,"操作成功！"));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

	  public ResponseEvent updatemm(RequestEvent requestEvent) throws Exception {
		  ResponseEvent resEvent = new ResponseEvent();

		  String ymm = requestEvent.getRequestMap().get("ymm").toString();
		  String xmm = requestEvent.getRequestMap().get("xmm").toString();
		  String usercode = requestEvent.getCtx().getUserinfo().get("usercode").toString();

		  ArrayList<String> params = new ArrayList<String>();
		  StringBuffer sql =new StringBuffer(
				  "select * from qx_dlzhxx t where t.dlzh_dm=? and t.dlzhkl=?"
				  );
		  params.add(usercode);
		  params.add(DigestUtils.md5Hex(ymm).toUpperCase());
		  List<?> list = jdbcDao.queryforlist(sql.toString(),params);

		  HashMap<String, Object> reqmap = new HashMap<String, Object>();
		  if(list.size() == 0){
			  reqmap.put("JSONDATA", RJson.getMsg(false,"原密码错误！"));
		  }else{
			  QxDlzhxxPojo qxDlzhxxPojo = new QxDlzhxxPojo();
			  qxDlzhxxPojo.setDlzh_dm(usercode);
			  qxDlzhxxPojo.setDlzhkl(DigestUtils.md5Hex(xmm).toUpperCase());
			  qxDlzhxxMapper.updateByPKeySelective(qxDlzhxxPojo);
			  qxDlzhxxMapperLocal.updateByPKeySelective(qxDlzhxxPojo);

			  reqmap.put("JSONDATA", RJson.getMsg(true,"密码修改成功！"));
		  }

		  resEvent.setResMap(reqmap);
		  return resEvent;
	}

    public ResponseEvent selectByPKey(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        return resEvent;
    }

}
