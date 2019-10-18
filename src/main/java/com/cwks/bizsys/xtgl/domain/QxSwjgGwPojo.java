package com.cwks.bizsys.xtgl.domain;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(QX_SWJG_GW)
*/

public class QxSwjgGwPojo {

    private static final long serialVersionUID = 1L;

    private  String gwxh; //岗位序号
    private  String gw_dm; //岗位代码
    private  String lrrq; //录入日期
    private  String lrr_dm; //录入人代码
    private  String swjg_dm; //税务机关代码
    private  String xgrq; //修改日期
    private  String xgr_dm; //修改人代码
    private  Double xsxh; //显示序号
    private  String yxbz; //有效标志

    /**无参构造方法**/
    public QxSwjgGwPojo(){};

    /**带参构造方法*/
    public QxSwjgGwPojo (String gwxh,String gw_dm,String lrrq,String lrr_dm,String swjg_dm,String xgrq,String xgr_dm,Double xsxh,String yxbz){
        this.gwxh = gwxh;
        this.gw_dm = gw_dm;
        this.lrrq = lrrq;
        this.lrr_dm = lrr_dm;
        this.swjg_dm = swjg_dm;
        this.xgrq = xgrq;
        this.xgr_dm = xgr_dm;
        this.xsxh = xsxh;
        this.yxbz = yxbz;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public QxSwjgGwPojo(Map data){
        this.gwxh = data.get("gwxh") == null
            ? null : (String)data.get("gwxh");
        this.gw_dm = data.get("gw_dm") == null
            ? null : (String)data.get("gw_dm");
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrr_dm = data.get("lrr_dm") == null
            ? null : (String)data.get("lrr_dm");
        this.swjg_dm = data.get("swjg_dm") == null
            ? null : (String)data.get("swjg_dm");
        this.xgrq = data.get("xgrq") == null
            ? null : (String)data.get("xgrq");
        this.xgr_dm = data.get("xgr_dm") == null
            ? null : (String)data.get("xgr_dm");
        this.xsxh = data.get("xsxh") == null
            || "".equals(data.get("xsxh")) ? null : Double.parseDouble((String)data.get("xsxh"));
        this.yxbz = data.get("yxbz") == null
            ? null : (String)data.get("yxbz");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("gwxh",gwxh);
        map.put("gw_dm",gw_dm);
        map.put("lrrq",lrrq);
        map.put("lrr_dm",lrr_dm);
        map.put("swjg_dm",swjg_dm);
        map.put("xgrq",xgrq);
        map.put("xgr_dm",xgr_dm);
        map.put("xsxh",xsxh);
        map.put("yxbz",yxbz);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getGwxh() {
        return this.gwxh;
    }
    public void setGwxh(String gwxh) {
        this.gwxh = gwxh;
    }
    public String getGw_dm() {
        return this.gw_dm;
    }
    public void setGw_dm(String gw_dm) {
        this.gw_dm = gw_dm;
    }
    public String getLrrq() {
        return this.lrrq;
    }
    public void setLrrq(String lrrq) {
        this.lrrq = lrrq;
    }
    public String getLrr_dm() {
        return this.lrr_dm;
    }
    public void setLrr_dm(String lrr_dm) {
        this.lrr_dm = lrr_dm;
    }
    public String getSwjg_dm() {
        return this.swjg_dm;
    }
    public void setSwjg_dm(String swjg_dm) {
        this.swjg_dm = swjg_dm;
    }
    public String getXgrq() {
        return this.xgrq;
    }
    public void setXgrq(String xgrq) {
        this.xgrq = xgrq;
    }
    public String getXgr_dm() {
        return this.xgr_dm;
    }
    public void setXgr_dm(String xgr_dm) {
        this.xgr_dm = xgr_dm;
    }
    public Double getXsxh() {
        return this.xsxh;
    }
    public void setXsxh(Double xsxh) {
        this.xsxh = xsxh;
    }
    public String getYxbz() {
        return this.yxbz;
    }
    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }

}
