package com.cwks.bizcore.yyfb.mapping.pojo;

import com.cwks.bizcore.tycx.core.utils.TycxUtils;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(SYS_DATAUNIT)
*/

public class Yyfb001SySDataunitPojo {

    private static final long serialVersionUID = 1L;

    private  String biz_desc; //业务口径
    private  String datasource_id; //数据源ID
    private  String dataunit_id; //数据单元ID
    private  String dataunit_version; //数据单元版本号
    private  String du_desc; //数据单元描述
    private  String du_type; //数据单元类型 1 表 2 视图 9 其他
    private  String en_name; //表名称
    private  String folder_id; //文件夹ID
    private  String is_last_version; //是否是最新版本 Y 是 N 否
    private  String is_valid; //是否有效 Y 有效 N 无效
    private  Double last_version_id; //上一版本ID
    private  String lrrq; //录入日期
    private  String lrry_dm; //录入人员代码
    private  String owner; //用户名
    private  String tech_desc; //技术口径
    private  Double version_id; //数据单元版本ID
    private  String xgrq; //修改日期
    private  String xgry_dm; //修改人员代码
    private  String zh_name; //表中文名称

    /**无参构造方法**/
    public Yyfb001SySDataunitPojo(){};

    /**带参构造方法*/
    public Yyfb001SySDataunitPojo (String biz_desc,String datasource_id,String dataunit_id,String dataunit_version,String du_desc,String du_type,String en_name,String folder_id,String is_last_version,String is_valid,Double last_version_id,String lrrq,String lrry_dm,String owner,String tech_desc,Double version_id,String xgrq,String xgry_dm,String zh_name){
        this.biz_desc = biz_desc;
        this.datasource_id = datasource_id;
        this.dataunit_id = dataunit_id;
        this.dataunit_version = dataunit_version;
        this.du_desc = du_desc;
        this.du_type = du_type;
        this.en_name = en_name;
        this.folder_id = folder_id;
        this.is_last_version = is_last_version;
        this.is_valid = is_valid;
        this.last_version_id = last_version_id;
        this.lrrq = lrrq;
        this.lrry_dm = lrry_dm;
        this.owner = owner;
        this.tech_desc = tech_desc;
        this.version_id = version_id;
        this.xgrq = xgrq;
        this.xgry_dm = xgry_dm;
        this.zh_name = zh_name;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public Yyfb001SySDataunitPojo(Map data){
        this.biz_desc = data.get("biz_desc") == null
            ? null : (String)data.get("biz_desc");
        this.datasource_id = data.get("datasource_id") == null
            ? null : (String)data.get("datasource_id");
        this.dataunit_id = data.get("dataunit_id") == null
            ? null : (String)data.get("dataunit_id");
        this.dataunit_version = data.get("dataunit_version") == null
            ? null : (String)data.get("dataunit_version");
        this.du_desc = data.get("du_desc") == null
            ? null : (String)data.get("du_desc");
        this.du_type = data.get("du_type") == null
            ? null : (String)data.get("du_type");
        this.en_name = data.get("en_name") == null
            ? null : (String)data.get("en_name");
        this.folder_id = data.get("folder_id") == null
            ? null : (String)data.get("folder_id");
        this.is_last_version = data.get("is_last_version") == null
            ? null : (String)data.get("is_last_version");
        this.is_valid = data.get("is_valid") == null
            ? null : (String)data.get("is_valid");
        this.last_version_id = TycxUtils.isEmpty(data.get("last_version_id")) ? null : Double.parseDouble((String)data.get("last_version_id"));
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrry_dm = data.get("lrry_dm") == null
            ? null : (String)data.get("lrry_dm");
        this.owner = data.get("owner") == null
            ? null : (String)data.get("owner");
        this.tech_desc = data.get("tech_desc") == null
            ? null : (String)data.get("tech_desc");
        this.version_id = TycxUtils.isEmpty(data.get("version_id")) ? null : Double.parseDouble((String)data.get("version_id"));
        this.xgrq = data.get("xgrq") == null
            ? null : (String)data.get("xgrq");
        this.xgry_dm = data.get("xgry_dm") == null
            ? null : (String)data.get("xgry_dm");
        this.zh_name = data.get("zh_name") == null
            ? null : (String)data.get("zh_name");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("biz_desc",biz_desc);
        map.put("datasource_id",datasource_id);
        map.put("dataunit_id",dataunit_id);
        map.put("dataunit_version",dataunit_version);
        map.put("du_desc",du_desc);
        map.put("du_type",du_type);
        map.put("en_name",en_name);
        map.put("folder_id",folder_id);
        map.put("is_last_version",is_last_version);
        map.put("is_valid",is_valid);
        map.put("last_version_id",last_version_id);
        map.put("lrrq",lrrq);
        map.put("lrry_dm",lrry_dm);
        map.put("owner",owner);
        map.put("tech_desc",tech_desc);
        map.put("version_id",version_id);
        map.put("xgrq",xgrq);
        map.put("xgry_dm",xgry_dm);
        map.put("zh_name",zh_name);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getBiz_desc() {
        return this.biz_desc;
    }
    public void setBiz_desc(String biz_desc) {
        this.biz_desc = biz_desc;
    }
    public String getDatasource_id() {
        return this.datasource_id;
    }
    public void setDatasource_id(String datasource_id) {
        this.datasource_id = datasource_id;
    }
    public String getDataunit_id() {
        return this.dataunit_id;
    }
    public void setDataunit_id(String dataunit_id) {
        this.dataunit_id = dataunit_id;
    }
    public String getDataunit_version() {
        return this.dataunit_version;
    }
    public void setDataunit_version(String dataunit_version) {
        this.dataunit_version = dataunit_version;
    }
    public String getDu_desc() {
        return this.du_desc;
    }
    public void setDu_desc(String du_desc) {
        this.du_desc = du_desc;
    }
    public String getDu_type() {
        return this.du_type;
    }
    public void setDu_type(String du_type) {
        this.du_type = du_type;
    }
    public String getEn_name() {
        return this.en_name;
    }
    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }
    public String getFolder_id() {
        return this.folder_id;
    }
    public void setFolder_id(String folder_id) {
        this.folder_id = folder_id;
    }
    public String getIs_last_version() {
        return this.is_last_version;
    }
    public void setIs_last_version(String is_last_version) {
        this.is_last_version = is_last_version;
    }
    public String getIs_valid() {
        return this.is_valid;
    }
    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }
    public Double getLast_version_id() {
        return this.last_version_id;
    }
    public void setLast_version_id(Double last_version_id) {
        this.last_version_id = last_version_id;
    }
    public String getLrrq() {
        return this.lrrq;
    }
    public void setLrrq(String lrrq) {
        this.lrrq = lrrq;
    }
    public String getLrry_dm() {
        return this.lrry_dm;
    }
    public void setLrry_dm(String lrry_dm) {
        this.lrry_dm = lrry_dm;
    }
    public String getOwner() {
        return this.owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getTech_desc() {
        return this.tech_desc;
    }
    public void setTech_desc(String tech_desc) {
        this.tech_desc = tech_desc;
    }
    public Double getVersion_id() {
        return this.version_id;
    }
    public void setVersion_id(Double version_id) {
        this.version_id = version_id;
    }
    public String getXgrq() {
        return this.xgrq;
    }
    public void setXgrq(String xgrq) {
        this.xgrq = xgrq;
    }
    public String getXgry_dm() {
        return this.xgry_dm;
    }
    public void setXgry_dm(String xgry_dm) {
        this.xgry_dm = xgry_dm;
    }
    public String getZh_name() {
        return this.zh_name;
    }
    public void setZh_name(String zh_name) {
        this.zh_name = zh_name;
    }

}