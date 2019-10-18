package com.cwks.bizcore.utils;


import com.cwks.common.core.systemConfig.BizContext;

/**
 * 静态常量类
 * @author hur
 * 
 */  
public class ComProConf {

    public static String JHDL_RESTAPI_NBFW_URL_ISLOCAL = BizContext.singleton().getValueAsString("biz.sjjhpt.qzfw.core.nbfw.restful.islocal");

    public static String JHDL_RESTAPI_NBFW_URL = BizContext.singleton().getValueAsString("biz.sjjhpt.qzfw.core.nbfw.restful.url");

    public static String JHDL_RESTAPI_WBFW_URL = BizContext.singleton().getValueAsString("biz.sjjhpt.qzfw.core.wbfw.restful.url");
    
    public static String JHDL_RESTAPI_NBFW_URL_HZPATH ="/jhfw-api/jhdl-nbfw.action";


      
}