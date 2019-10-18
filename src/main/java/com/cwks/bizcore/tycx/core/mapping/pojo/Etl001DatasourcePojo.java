package com.cwks.bizcore.tycx.core.mapping.pojo;

import com.cwks.bizcore.tycx.core.utils.TycxUtils;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(SYS_DATASOURCE)
*/

public class Etl001DatasourcePojo {

    private static final long serialVersionUID = 1L;

    private  String datasource_id; //数据源ID
    private  String ds_desc; //数据源描述
    private  String ds_ip; //数据源IP
    private  String ds_name; //数据源名称
    private  String ds_sid; //数据源SID
    private  String ds_type; //数据源数据库类型 详见代码表DM_GY_
    private  String is_valid; //有效标记 Y 有效 N 无效
    private  String lrrq; //录入日期
    private  String lrry_dm; //录入人员代码
    private  Double order_num; //排序序号
    private  String xgrq; //修改日期
    private  String xgry_dm; //修改人员代码

    /**无参构造方法**/
    public Etl001DatasourcePojo(){};

    /**带参构造方法*/
    public Etl001DatasourcePojo (String datasource_id,String ds_desc,String ds_ip,String ds_name,String ds_sid,String ds_type,String is_valid,String lrrq,String lrry_dm,Double order_num,String xgrq,String xgry_dm){
        this.datasource_id = datasource_id;
        this.ds_desc = ds_desc;
        this.ds_ip = ds_ip;
        this.ds_name = ds_name;
        this.ds_sid = ds_sid;
        this.ds_type = ds_type;
        this.is_valid = is_valid;
        this.lrrq = lrrq;
        this.lrry_dm = lrry_dm;
        this.order_num = order_num;
        this.xgrq = xgrq;
        this.xgry_dm = xgry_dm;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public Etl001DatasourcePojo(Map data){
        this.datasource_id = data.get("datasource_id") == null
            ? null : (String)data.get("datasource_id");
        this.ds_desc = data.get("ds_desc") == null
            ? null : (String)data.get("ds_desc");
        this.ds_ip = data.get("ds_ip") == null
            ? null : (String)data.get("ds_ip");
        this.ds_name = data.get("ds_name") == null
            ? null : (String)data.get("ds_name");
        this.ds_sid = data.get("ds_sid") == null
            ? null : (String)data.get("ds_sid");
        this.ds_type = data.get("ds_type") == null
            ? null : (String)data.get("ds_type");
        this.is_valid = data.get("is_valid") == null
            ? null : (String)data.get("is_valid");
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrry_dm = data.get("lrry_dm") == null
            ? null : (String)data.get("lrry_dm");
        this.order_num = TycxUtils.isEmpty(data.get("order_num"))  ? null : Double.parseDouble((String)data.get("order_num"));
        this.xgrq = data.get("xgrq") == null
            ? null : (String)data.get("xgrq");
        this.xgry_dm = data.get("xgry_dm") == null
            ? null : (String)data.get("xgry_dm");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("datasource_id",datasource_id);
        map.put("ds_desc",ds_desc);
        map.put("ds_ip",ds_ip);
        map.put("ds_name",ds_name);
        map.put("ds_sid",ds_sid);
        map.put("ds_type",ds_type);
        map.put("is_valid",is_valid);
        map.put("lrrq",lrrq);
        map.put("lrry_dm",lrry_dm);
        map.put("order_num",order_num);
        map.put("xgrq",xgrq);
        map.put("xgry_dm",xgry_dm);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getDatasource_id() {
        return this.datasource_id;
    }
    public void setDatasource_id(String datasource_id) {
        this.datasource_id = datasource_id;
    }
    public String getDs_desc() {
        return this.ds_desc;
    }
    public void setDs_desc(String ds_desc) {
        this.ds_desc = ds_desc;
    }
    public String getDs_ip() {
        return this.ds_ip;
    }
    public void setDs_ip(String ds_ip) {
        this.ds_ip = ds_ip;
    }
    public String getDs_name() {
        return this.ds_name;
    }
    public void setDs_name(String ds_name) {
        this.ds_name = ds_name;
    }
    public String getDs_sid() {
        return this.ds_sid;
    }
    public void setDs_sid(String ds_sid) {
        this.ds_sid = ds_sid;
    }
    public String getDs_type() {
        return this.ds_type;
    }
    public void setDs_type(String ds_type) {
        this.ds_type = ds_type;
    }
    public String getIs_valid() {
        return this.is_valid;
    }
    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
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
    public Double getOrder_num() {
        return this.order_num;
    }
    public void setOrder_num(Double order_num) {
        this.order_num = order_num;
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

}