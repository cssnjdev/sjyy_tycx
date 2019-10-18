package com.cwks.bizsys.xtgl.service;

import com.cwks.bizcore.comm.utils.EasyUiComboTreeUtil;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizcore.comm.vo.ComboTreePojo;
import com.cwks.bizsys.xtgl.dao.DmGySwjgMapper;
import com.cwks.bizsys.xtgl.dao.QxSwjgGwMapper;
import com.cwks.bizsys.xtgl.dao.local.DmGySwjgMapperLocal;
import com.cwks.bizsys.xtgl.dao.local.QxSwjgGwMapperLocal;
import com.cwks.bizsys.xtgl.domain.DmGySwjgPojo;
import com.cwks.bizsys.xtgl.domain.MainTreePojo;
import com.cwks.bizsys.xtgl.domain.QxSwjgGwPojo;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.util.DateUtil;
import com.cwks.common.dao.JdbcDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
@Service("dmGySwjgService")
public class DmGySwjgService{

    private static Logger logger = LoggerFactory.getLogger(DmGySwjgService.class);

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private DmGySwjgMapper dmGySwjgMapper;

    @Autowired
    private QxSwjgGwMapper qxSwjgGwMapper;

    @Autowired
    private DmGySwjgMapperLocal dmGySwjgMapperLocal;

    @Autowired
    private QxSwjgGwMapperLocal qxSwjgGwMapperLocal;

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        //参数注入
        DmGySwjgPojo dmGySwjgPojo = new DmGySwjgPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<DmGySwjgPojo> list = dmGySwjgMapper.select(dmGySwjgPojo);
        PageInfo<DmGySwjgPojo> pages = new PageInfo<DmGySwjgPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJson(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        //参数注入
        DmGySwjgPojo dmGySwjgPojo = new DmGySwjgPojo(requestEvent.getRequestMap());
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        List<DmGySwjgPojo> list = dmGySwjgMapper.select(dmGySwjgPojo);
        Map<String, String> colMap = new LinkedHashMap<String, String>();
        colMap.put("bsfwtbz","办税服务厅标志");
        colMap.put("czdh","传真电话");
        colMap.put("dsswjgjg","地税局轨");
        colMap.put("dsswjgmc","地税税务机关名称");
        colMap.put("dzxx","电子信箱");
        colMap.put("gdslx_dm","国地税类型代码 ");
        colMap.put("ghbz","管户标志");
        colMap.put("gjswjgmc","国家税务机关名称");
        colMap.put("gsswjgjg","国税局轨");
        colMap.put("jgjc_dm","机构级次代码");
        colMap.put("sjswjg_dm","上级税务机关代码");
        colMap.put("swjgbz","税务机构标志 0机关 1部门（设计阶段建");
        colMap.put("swjgdz","税务机关地址");
        colMap.put("swjgfzr_dm","负责人");
        colMap.put("swjgjc","税务机构简称");
        colMap.put("swjgjg","税务机构局轨");
        colMap.put("swjglxdh","税务机关联系电话");
        colMap.put("swjgmc","税务机关名称");
        colMap.put("swjgywmc","税务机构英文名称");
        colMap.put("swjgyzbm","税务机构邮政编码");
        colMap.put("swjg_dm","税务机关代码");
        colMap.put("xsxh","显示序号");
        colMap.put("xybz","选用标志");
        colMap.put("xzqhsz_dm","行政区划数字代码");
        colMap.put("yxbz","有效标志");
        colMap.put("yxqsrq","有效起始日期");
        colMap.put("yxzzrq","有效终止日期");
        colMap.put("zn_dm","职能代码");
        //Excel导出格式
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("fileName","机关信息表");
        dataMap.put("class",DmGySwjgPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",list);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String[] swjg_dm = (String[])requestEvent.getRequestMap().get("swjg_dm");
        DmGySwjgPojo pojo = null;
        for(int i=0;i<swjg_dm.length;i++){
            pojo = new DmGySwjgPojo();
            pojo.setSwjg_dm(swjg_dm[i]);
            //原表
            dmGySwjgMapper.deleteByPKey(pojo);
            //附表
            dmGySwjgMapperLocal.deleteByPKey(pojo);
        }
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        DmGySwjgPojo dmGySwjgPojo = new DmGySwjgPojo(requestEvent.getRequestMap());
        //原表
        dmGySwjgMapper.insertSelective(dmGySwjgPojo);
        //附表
        dmGySwjgMapperLocal.insertSelective(dmGySwjgPojo);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        DmGySwjgPojo dmGySwjgPojo = new DmGySwjgPojo(requestEvent.getRequestMap());
        dmGySwjgPojo.setXsxh(Double.valueOf("1"));
        //原表
        dmGySwjgMapper.updateByPKeySelective(dmGySwjgPojo);
        //附表
        dmGySwjgMapperLocal.updateByPKeySelective(dmGySwjgPojo);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();

        reqmap.put("JSONDATA", RJson.UPDATE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    public ResponseEvent selectComboSjSwjgDm(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        String sqlStr = " select t.swjg_dm id,t.swjgmc text,t.sjswjg_dm parentId from dm_gy_swjg t start with t.sjswjg_dm='00000000000' connect by prior t.swjg_dm = sjswjg_dm order siblings by t.swjg_dm ";
        List<ComboTreePojo> list = dmGySwjgMapper.selectComboSjSwjgDm(null);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();

        reqmap.put("JSONDATA", JsonUtil.toJson(new EasyUiComboTreeUtil().createComboTreeTree(list,"00000000000")).toLowerCase());
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //机关岗位功能书
    public ResponseEvent selectGwGns(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();

        String swjg_dm = requestEvent.getRequestMap().get("swjg_dm").toString();
        String gwfl_dm = requestEvent.getRequestMap().get("gwfl_dm").toString();

        ArrayList<String> params = new ArrayList<String>();
        StringBuffer sqlStr = new StringBuffer("select t.gw_dm id, t.gwmc name,t.gwfl_dm,z.gwxh,\n" +
                        "       case z.swjg_dm when ? then 'true' else 'false' end checked\n" +
                        "  from dm_qx_gw t\n" +
                        "  left JOIN qx_swjg_gw z on t.gw_dm=z.gw_dm and z.swjg_dm=?\n" +
                        "  where t.yxbz = 'Y' and t.gwfl_dm = ? \n" +
                        "  order by t.gwfl_dm, t.gw_dm");

        params.add(swjg_dm);
        params.add(swjg_dm);
        params.add(gwfl_dm);
        List<?> list = jdbcDao.queryforlist(sqlStr.toString(),params);
        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", JsonUtil.toJson(list).toLowerCase().replaceAll("pid","pId"));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    //保存机关和岗位关系
    public ResponseEvent saveSelectNodes(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();

        //获取session值
        String dl_swrydm = requestEvent.getCtx().getUserinfo().get("swry_dm").toString();

        String swjg_dm = requestEvent.getRequestMap().get("swjg_dm").toString();
        //原本选中岗位
        String ylNodes = (String) requestEvent.getRequestMap().get("ylNodes");
        //现选中的岗位
        String selectNodes = (String)requestEvent.getRequestMap().get("selectNodes");

        List<MainTreePojo> selectNodesAttrList = JsonUtil.toListJavaBean(MainTreePojo.class,selectNodes);
        List<MainTreePojo> ylNodesAttrList = JsonUtil.toListJavaBean(MainTreePojo.class,ylNodes);
        QxSwjgGwPojo pojo =null;

        //如果原来选中的岗位为空 则直接insert
        if(ylNodesAttrList.size() == 0){
            for (int i = 0; i < selectNodesAttrList.size(); i++) {
                pojo = new QxSwjgGwPojo();
                pojo.setGwxh(UUIDGenerator.getUUID().toUpperCase());
                pojo.setGw_dm(selectNodesAttrList.get(i).getId());
                pojo.setSwjg_dm(swjg_dm);
                pojo.setLrr_dm(dl_swrydm);
                pojo.setYxbz("Y");
                pojo.setXsxh((double) 1);
                pojo.setLrrq(DateUtil.getDateFormat(new Date(), "yyyy-MM-dd"));
                //原表
                qxSwjgGwMapper.insertSelective(pojo);
                //附表
                qxSwjgGwMapperLocal.insertSelective(pojo);
            }
        }else {
            //删除岗位
            //原来选中岗位
            if(selectNodesAttrList.size() <=0){
                for(int i = 0; i < ylNodesAttrList.size();i++){
                    pojo = new QxSwjgGwPojo();
                    pojo.setGwxh(ylNodesAttrList.get(i).getGwxh());
                    //原表
                    qxSwjgGwMapper.deleteByGWXH(pojo);
                    //附表
                    qxSwjgGwMapperLocal.deleteByGWXH(pojo);
                }
            }else{
                List<String> tmp = new ArrayList<String>();
                //获取选中的
                for(int i = 0; i < selectNodesAttrList.size();i++){
                    tmp.add(selectNodesAttrList.get(i).getId());
                }
                List<String> dellist = new ArrayList<String>();
                //获取原选中的id
                for(int i = 0; i < ylNodesAttrList.size();i++){
                    //获取要删除的
                    if(!tmp.contains(ylNodesAttrList.get(i).getId()) && ylNodesAttrList.get(i).getGwxh() != null ){
                        dellist.add(ylNodesAttrList.get(i).getGwxh());
                    }
                }
                //删除岗位
                for(int i=0;i<dellist.size();i++){
                    pojo = new QxSwjgGwPojo();
                    pojo.setGwxh(dellist.get(i).toString());
                    //原表
                    qxSwjgGwMapper.deleteByGWXH(pojo);
                    //附表
                    qxSwjgGwMapperLocal.deleteByGWXH(pojo);
                }

            }

            //新增之前未选中的岗位
            for(int j = 0; j < selectNodesAttrList.size(); j++){
                if(selectNodesAttrList.get(j).getGwxh() == null){
                    pojo = new QxSwjgGwPojo();
                    pojo.setGwxh(UUIDGenerator.getUUID().toUpperCase());
                    pojo.setGw_dm(selectNodesAttrList.get(j).getId().toUpperCase());
                    pojo.setSwjg_dm(swjg_dm);
                    pojo.setLrr_dm(dl_swrydm);
                    //原表
                    qxSwjgGwMapper.insertSelective(pojo);
                    //附表
                    qxSwjgGwMapperLocal.insertSelective(pojo);
                }
            }

        }

        HashMap<String, Serializable> reqmap = new HashMap<String, Serializable>();
        reqmap.put("JSONDATA", RJson.getMsg(true,"操作成功！"));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent selectByPKey(RequestEvent requestEvent) throws Exception {
        ResponseEvent resEvent = new ResponseEvent();
        return resEvent;
    }

}
