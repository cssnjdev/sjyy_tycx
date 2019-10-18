package com.cwks.bizcore.tycx.core.service;

import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.tycx.core.dao.Tycx004RYhsJcxxDao;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx004RYhsJcxxPojo;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.service.impl.BaseService;
import com.cwks.common.dao.JdbcDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Service("tycx004RYhsJcxxService")
public class Tycx004RYhsJcxxService  {

    private static Logger logger = LoggerFactory.getLogger(Tycx004RYhsJcxxService.class);


    @Autowired
    private Tycx004RYhsJcxxDao tycx004RYhsJcxxDao;

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_select");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx004RYhsJcxxPojo tycx004RYhsJcxxPojo = new Tycx004RYhsJcxxPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<Tycx004RYhsJcxxPojo> list = tycx004RYhsJcxxDao.select(tycx004RYhsJcxxPojo);
        PageInfo<Tycx004RYhsJcxxPojo> pages = new PageInfo<Tycx004RYhsJcxxPojo>(list);
        reqmap.put("JSONDATA", JsonUtil.toJson(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent expExcel(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_expExcel");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        Tycx004RYhsJcxxPojo tycx004RYhsJcxxPojo = new Tycx004RYhsJcxxPojo(requestEvent.getRequestMap());
        String page = (String)requestEvent.getRequestMap().get("page");
        List<Tycx004RYhsJcxxPojo> expList = null;
        if(!TycxUtils.isEmpty(page) ){
            PageHelper.startPage(requestEvent.getRequestMap());
            PageInfo<Tycx004RYhsJcxxPojo> pages = new PageInfo<Tycx004RYhsJcxxPojo>(tycx004RYhsJcxxDao.select(tycx004RYhsJcxxPojo));
            expList = pages.getList();
        }else{
            expList = tycx004RYhsJcxxDao.select(tycx004RYhsJcxxPojo);
        }
        Map colMap = new LinkedHashMap();
        colMap.put("bsrdh","办税人固定电话");
        colMap.put("bsrsfzjhm","办税人身份证件号码");
        colMap.put("bsrsfzjzlmc","办税人身份证件种类");
        colMap.put("bsrxm","办税人姓名");
        colMap.put("cwfzrdh","财务负责人电话");
        colMap.put("cwfzrsfzjhm","财务负责人身份证件号码");
        colMap.put("cwfzrsfzjzlmc","财务负责人身份证件种类");
        colMap.put("cwfzrxm","财务负责人姓名");
        colMap.put("cyrs","从业人数");
        colMap.put("djjgmc","登记机关名称");
        colMap.put("djrq_1","登记日期");
        colMap.put("djxh","登记序号");
        colMap.put("djzclxmc","登记注册类型");
        colMap.put("dwlsgxmc","单位隶属关系");
        colMap.put("fddbrdh","法定代表人电话");
        colMap.put("fddbrsfzjhm","法定代表人身份证号码");
        colMap.put("fddbrsfzjlxmc","法定代表人身份证件类型");
        colMap.put("fddbrxm","法定代表人姓名");
        colMap.put("gjhdqjc","国家");
        colMap.put("gykglxmc","国有控股类型");
        colMap.put("hjszd","户籍所在地");
        colMap.put("hsfsmc","核算方式");
        colMap.put("hymc","行业");
        colMap.put("jdxzmc","街道乡镇");
        colMap.put("jmje","减免金额");
        colMap.put("jyfw","经营范围");
        colMap.put("kjzdzzmc","会计制度（准则）");
        colMap.put("kqccsztdjbz","跨区财产税主体登记标志");
        colMap.put("kyslrq_1","开业设立日期");
        colMap.put("kzztdjlxmc","课征主体登记类型");
        colMap.put("nse","纳税额");
        colMap.put("nspm_hy","行业排名");
        colMap.put("nspm_sj","纳税排名-市局");
        colMap.put("nspm_xj","纳税排名-县局");
        colMap.put("nsrmc","纳税人名称");
        colMap.put("nsrsbh","纳税人识别号");
        colMap.put("nsrzglxmc","纳税人资格类型");
        colMap.put("nsrztmc","纳税人状态");
        colMap.put("pdnd","评定年度");
        colMap.put("qsje","欠税金额");
        colMap.put("scjydz","生产经营地址");
        colMap.put("sfdqdeh","是否定期定额户");
        colMap.put("sfl","税负率");
        colMap.put("sflgfp","是否领钩发票");
        colMap.put("sfsyxxwlqy","是否属于小型微利企业");
        colMap.put("shxydm","社会信用代码");
        colMap.put("sjje","实缴金额");
        colMap.put("ssglymc","税收管理员名称");
        colMap.put("tzze","投资总额");
        colMap.put("xmts","项目登记条数");
        colMap.put("xydj_dm","信用等级");
        colMap.put("yye","营业额");
        colMap.put("zczb","注册资本");
        colMap.put("zfjglxmc","总分机构类型");
        colMap.put("zgswjmc","主管税务局名称");
        colMap.put("zgswskfjmc","主管税务所（科、分局）名称");
        colMap.put("zrrtzbl","自然人投资比例");
        colMap.put("zzhm","证照编号");
        colMap.put("zzjglxmc","组织机构类型");
        colMap.put("zzjg_dm","组织机构代码");
        colMap.put("zzlxmc","执照类型");
        //Excel导出格式
        HashMap<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("fileName","一户式基础信息");
        dataMap.put("class",Tycx004RYhsJcxxPojo.class);
        dataMap.put("colMap",colMap);
        dataMap.put("listContent",expList);
        reqmap.put("ExcelMap",dataMap);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent deleteByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_deleteByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx004RYhsJcxxPojo pojo = null;
//        for(int i=0;i<pkid.length;i++){
//            pojo = new Tycx004RYhsJcxxPojo();
//            tycx004RYhsJcxxDao.deleteByPKey(pojo);
//        }
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.DELETE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent insertSelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_insertSelective");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx004RYhsJcxxPojo tycx004RYhsJcxxPojo = new Tycx004RYhsJcxxPojo(requestEvent.getRequestMap());
        tycx004RYhsJcxxDao.insertSelective(tycx004RYhsJcxxPojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.INSERT_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }

    public ResponseEvent updateByPKeySelective(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_updateByPKeySelective");
        ResponseEvent resEvent = new ResponseEvent();
        Tycx004RYhsJcxxPojo tycx004RYhsJcxxPojo = new Tycx004RYhsJcxxPojo(requestEvent.getRequestMap());
        tycx004RYhsJcxxDao.updateByPKeySelective(tycx004RYhsJcxxPojo);
        HashMap reqmap = new HashMap();
        reqmap.put("JSONDATA", RJson.UPDATE_TRUE);
        resEvent.setResMap(reqmap);
        return resEvent;
    }


    public ResponseEvent selectByPKey(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_selectByPKey");
        ResponseEvent resEvent = new ResponseEvent();
        return resEvent;
    }



}
