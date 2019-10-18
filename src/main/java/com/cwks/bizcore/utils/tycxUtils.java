package com.cwks.bizcore.utils;

import com.cwks.common.core.systemConfig.SystemApplicationContext;
import com.cwks.common.util.StringUtils;
import com.cwks.common.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public  class tycxUtils {

    @Autowired
    JdbcDao jdbcDao;

    public  List getCodeTable(String tableName) {
        String catalogName = StringUtils.stringTrim(SystemApplicationContext.singleton().getValueAsString("ctp.codetable.catalog"));
        String sql = "select GX_XH, BM_MC, DS_MC,XY_BJ,READ_ORDER ,HCLX,COLUMN_ID,COLUMN_VALUE from " + catalogName + " where bm_mc='" + tableName + "'";
        List<?> rsList = jdbcDao.queryforlist(sql);
        return rsList;
    }
}
