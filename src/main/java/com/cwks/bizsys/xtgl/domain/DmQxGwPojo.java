package com.cwks.bizsys.xtgl.domain;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(DM_QX_GW)
*/

public class DmQxGwPojo {

    private static final long serialVersionUID = 1L;

    private  String aqkzlb_dm; //安全控制类别代码
    private  String gwfl_dm; //岗位分类代码
    private  String gwmc; //岗位名称
    private  String gwms; //岗位职责描述
    private  String gw_dm; //岗位代码
    private  String lrrq; //录入日期
    private  String lrr_dm; //录入人代码
    private  String swjg_dm; //税务机关代码
    private  String wqsygwjgjgjc; //无权使用岗位机构机关级次
    private  String xgrq; //修改日期
    private  String xgr_dm; //修改人代码
    private  Double xsxh; //显示序号
    private  String yxbz; //有效标志
    private  String gwflmc; //岗位分类名称

    /**无参构造方法**/
    public DmQxGwPojo(){};

    /**带参构造方法*/
    public DmQxGwPojo (String gwflmc,String aqkzlb_dm,String gwfl_dm,String gwmc,String gwms,String gw_dm,String lrrq,String lrr_dm,String swjg_dm,String wqsygwjgjgjc,String xgrq,String xgr_dm,Double xsxh,String yxbz){
        this.gwflmc = gwflmc;
        this.aqkzlb_dm = aqkzlb_dm;
        this.gwfl_dm = gwfl_dm;
        this.gwmc = gwmc;
        this.gwms = gwms;
        this.gw_dm = gw_dm;
        this.lrrq = lrrq;
        this.lrr_dm = lrr_dm;
        this.swjg_dm = swjg_dm;
        this.wqsygwjgjgjc = wqsygwjgjgjc;
        this.xgrq = xgrq;
        this.xgr_dm = xgr_dm;
        this.xsxh = xsxh;
        this.yxbz = yxbz;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public DmQxGwPojo(Map data){
        this.gwflmc = data.get("gwflmc") == null
                ? null : (String)data.get("gwflmc");
        this.aqkzlb_dm = data.get("aqkzlb_dm") == null
            ? null : (String)data.get("aqkzlb_dm");
        this.gwfl_dm = data.get("gwfl_dm") == null
            ? null : (String)data.get("gwfl_dm");
        this.gwmc = data.get("gwmc") == null
            ? null : (String)data.get("gwmc");
        this.gwms = data.get("gwms") == null
            ? null : (String)data.get("gwms");
        this.gw_dm = data.get("gw_dm") == null
            ? null : (String)data.get("gw_dm");
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrr_dm = data.get("lrr_dm") == null
            ? null : (String)data.get("lrr_dm");
        this.swjg_dm = data.get("swjg_dm") == null
            ? null : (String)data.get("swjg_dm");
        this.wqsygwjgjgjc = data.get("wqsygwjgjgjc") == null
            ? null : (String)data.get("wqsygwjgjgjc");
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
        map.put("gwflmc",gwflmc);
        map.put("aqkzlb_dm",aqkzlb_dm);
        map.put("gwfl_dm",gwfl_dm);
        map.put("gwmc",gwmc);
        map.put("gwms",gwms);
        map.put("gw_dm",gw_dm);
        map.put("lrrq",lrrq);
        map.put("lrr_dm",lrr_dm);
        map.put("swjg_dm",swjg_dm);
        map.put("wqsygwjgjgjc",wqsygwjgjgjc);
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
    public String getGwflmc() {
        return this.gwflmc;
    }
    public void setGwflmc(String gwflmc) {
        this.gwflmc = gwflmc;
    }
    public String getAqkzlb_dm() {
        return this.aqkzlb_dm;
    }
    public void setAqkzlb_dm(String aqkzlb_dm) {
        this.aqkzlb_dm = aqkzlb_dm;
    }
    public String getGwfl_dm() {
        return this.gwfl_dm;
    }
    public void setGwfl_dm(String gwfl_dm) {
        this.gwfl_dm = gwfl_dm;
    }
    public String getGwmc() {
        return this.gwmc;
    }
    public void setGwmc(String gwmc) {
        this.gwmc = gwmc;
    }
    public String getGwms() {
        return this.gwms;
    }
    public void setGwms(String gwms) {
        this.gwms = gwms;
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
    public String getWqsygwjgjgjc() {
        return this.wqsygwjgjgjc;
    }
    public void setWqsygwjgjgjc(String wqsygwjgjgjc) {
        this.wqsygwjgjgjc = wqsygwjgjgjc;
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
