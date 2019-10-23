package com.cwks.bizcore.tycx.core.utils;

import com.cwks.bizcore.utils.DataSourceUtil;
import com.cwks.common.core.cache.EHCacheManager;
import com.cwks.common.core.systemConfig.SpringContextUtil;
import com.cwks.common.core.systemConfig.SystemApplicationContext;
import com.cwks.common.core.systemConfig.SystemContext;
import com.cwks.common.dao.JdbcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.util.*;

@Configuration
public class tycxUtil {

    @Autowired
    private JdbcDao jdbcDao;
    private static JdbcDao idao = (JdbcDao) SpringContextUtil.getBean("JdbcDao");
    public  boolean isEmpty(Object obj) {
        boolean isEmpty = true;
        if (obj != null) {
            isEmpty = false;
            if (obj instanceof List) {
                isEmpty = ((List) obj).size() == 0;
            } else if (obj instanceof Map) {
                isEmpty = ((Map) obj).size() == 0;
            } else if (obj instanceof String) {
                isEmpty =((String) obj).length()==0;
            }
        }
        return isEmpty;
    }
    public  ArrayList getCxCsb(String bds){
        String hcb_bds="XYBJ='Y'";
        if(!isEmpty(bds)){
            hcb_bds=hcb_bds+" AND "+bds;
        }
        List dbtypes = jdbcDao.queryforlist("select CSZ,CSM from cx_cs_csb where 1=1 and CSFL='YWFL' and XYBJ='Y'");;
        ArrayList dmbList = new ArrayList();
        for(int i=0;i<dbtypes.size();i++){
            Map map =(Map) dbtypes.get(i);
            Map codeMap=new HashMap();
            codeMap.put("code",map.get("CSZ"));
            codeMap.put("caption",map.get("CSM"));
            dmbList.add(codeMap);
        }
        return dmbList;
    }
    public  List getCodeTable(String tableName, String filterString,String sjy) throws Exception {
        DataSourceUtil dataSourceUtils = new DataSourceUtil(sjy);
        List dbtypes = dataSourceUtils.queryforlist("select * from "+tableName+" where 1=1 and "+filterString);
        return dbtypes;
    }
    public Map queryForMap(String param,String sjy) throws Exception {
        DataSourceUtil dataSourceUtils = new DataSourceUtil("dataSource_default");
        ArrayList params = new ArrayList();
        params.add(param);
        Map map = idao.queryformap("select * from  cx_cxjgdmb t where 1=1 and TABLE_NAME=?",params);
        return map;
    }
    private List getDataFromDB(String tableName, String filterString) throws Exception {
        String db_type = SystemApplicationContext.singleton().getValueAsString("ctp.db.type");
        DataSourceUtil dataSourceUtils = new DataSourceUtil("dataSource_default");
        String sql = "select * from " + tableName + " where 1=0";
        SqlRowSet rs = dataSourceUtils.queryforRowset(sql);
        SqlRowSetMetaData rsmd = rs.getMetaData();
        sql = "select ";

        for(int i = 0; i < rsmd.getColumnCount(); ++i) {
            if (!rsmd.getColumnTypeName(i + 1).equalsIgnoreCase("TIMESTAMP")) {
                if ("MYSQL".equals(db_type) && "SCHEMA".equals(rsmd.getColumnName(i + 1).toUpperCase())) {
                    sql = sql + "`" + rsmd.getColumnName(i + 1) + "`,";
                } else {
                    sql = sql + rsmd.getColumnName(i + 1) + ",";
                }
            }
        }

        sql = sql.substring(0, sql.length() - 1) + " from " + tableName;
        if (filterString != null && filterString.trim().length() > 0) {
            sql = sql + " where " + filterString;
        }

        List list = dataSourceUtils.queryforlist(sql);
        if (list != null && list.size() != 0) {
            return list;
        } else {
            return null;
        }
    }
    public  String getDmFromCache(String dmName, String key, String mc,String dmzd) {
        tycxUtil tycxUtil = new tycxUtil();
        String valueStr = "";
        if (key == null || key.equals("")) {
            return "";
        }
        if (dmName == null || dmName.equals("")) {
            return key;
        }
        if (mc == null || mc.equals("")) {
            return key;
        }
        // 解决多个代码用逗号隔开的情况转换
        final String[] keyArr = key.split(",");
        for (int i = 0; i < keyArr.length; i++) {
            try {
                mc = null;
                String real_value = getMcByDm(dmName, "'"+keyArr[i]+"'",dmzd);
                if (TycxUtils.isEmptyString(real_value)) {
                    keyArr[i] = key;
                } else {
                    keyArr[i] = real_value;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return key;
            }
        }
        valueStr = getStrFromArr(keyArr, ",");
        return valueStr;
    }
    public static String getStrFromArr(String[] inArr, String str) {
        String outStr = "";
        for (int i = 0; i < inArr.length; i++) {
            outStr = outStr + inArr[i] + str;
        }
        if (outStr != null && outStr.length() > 0) {
            outStr = outStr.substring(0, outStr.length() - 1);
        }
        return outStr;
    }
    public  String getMcByDm(String tableName, String dm, String zd) throws Exception {
        String mc = null;
        List list = getCodeTable(tableName, zd + "=" + dm);
        if (list != null && list.size() > 0) {
            Map map = (Map)list.get(0);
            mc = (String)map.get("text");
        }

        return mc;
    }
    public  List<Map> getCodeTable(String tableName, String filterString) throws Exception {
        new ArrayList();
        Map<?, ?> codeMap = null;
        List list = null;

        try {
            list = getCacheData(tableName,(String)null, filterString);
        } catch (Exception var6) {
        }
        DataSourceUtil dataSourceUtils = new DataSourceUtil("dataSource_default");
        String sql = "select * from cx_cxjgdmb where table_name='" + tableName + "'";
        List<?> clmList = dataSourceUtils.queryforlist(sql);
        return convertToCodeList(tableName, list, (List<Map<String, Object>>) clmList);
    }
    public List getCacheData(String tableName, String dealMethod, String filterString) throws Exception {
        List result = null;
        result = getDataFromDB(tableName, filterString);
            return result;
        }
        private static List<Map> convertToCodeList(String tableName, List<Map<String, Object>> list, List<Map<String, Object>> clmList) {
            String clmIdName = "";
            String clmValueName = "";
            Map clmMap = null;
            ArrayList codeList;
            Object codeMap;
            if (clmList != null && clmList.size() > 0) {
            for(Iterator var15 = clmList.iterator(); var15.hasNext(); clmValueName = (String)clmMap.get("COLUMN_VALUE")) {
                Object obj = var15.next();
                clmMap = (Map)obj;
                clmIdName = (String)clmMap.get("COLUMN_ID");
            }
        } else {
            codeList = (ArrayList) SystemContext.getcodeCatchList();
            SystemApplicationContext.singleton();
            codeMap = SystemApplicationContext.codeTablePoolMs.get(tableName);
            if (codeList != null && codeList.size() > 0 && codeMap != null) {
                HashMap<?, ?> tempMap = null;
                String sql = "";
                Object temptab = null;

                for(int i = 0; i < codeList.size(); ++i) {
                    tempMap = (HashMap)codeList.get(i);
                    String tablename = (String)tempMap.get("tableName");
                    if (tablename.equals(tableName)) {
                        clmIdName = (String)tempMap.get("clmId");
                        clmValueName = (String)tempMap.get("clmValue");
                    }
                }
            }
        }

        codeList = new ArrayList();
        codeMap = null;
        if (list != null && list.size() > 0) {
            Iterator var18 = list.iterator();
            Map<String, Object> codeMapq = new HashMap();
            while(var18.hasNext()) {
                Map<String, Object> map = (Map)var18.next();

                String key;
                for(Iterator var19 = map.keySet().iterator(); var19.hasNext(); codeMapq.put(cast2DefKey(key), map.get(key))) {
                    key = (String)var19.next();
                    if (clmIdName != null && clmValueName != null && !"".equals(clmIdName) && !"".equals(clmValueName)) {
                        if (clmIdName.equals(key)) {
                            codeMapq.put("id", map.get(key));
                        } else if (clmValueName.equals(key)) {
                            codeMapq.put("text", map.get(key));
                        }
                    }
                }

                codeList.add(codeMapq);
            }
        }

        return codeList;
    }
    public static String cast2DefKey(String oldkey) {
        String newKey;
        String befStr;
        String aftStr;
        for(newKey = oldkey.toLowerCase(); newKey.indexOf("_") >= 0; newKey = befStr.concat(aftStr)) {
            int i = newKey.indexOf("_");
            befStr = "";
            aftStr = "";
            if (i > 0) {
                befStr = newKey.substring(0, i);
            }

            if (i < newKey.length() - 1) {
                aftStr = newKey.substring(i + 1, i + 2).toUpperCase().concat(newKey.substring(i + 2));
            }
        }

        return newKey;
    }

    public String getDmMc(String tableName,String param,String mc,String dm,String sjy) throws Exception {
        DataSourceUtil dataSourceUtils = new DataSourceUtil(sjy);
        List list = dataSourceUtils.queryforlist("select " + mc + " from " + tableName + " where 1=1 and " + dm + "='" + param+"'");
        if(list!=null&&list.size()>0){
            Map map = (Map)list.get(0);
            return map.get(mc).toString();
        }else {
            return null;
        }

    }
}
