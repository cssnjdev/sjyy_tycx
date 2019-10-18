package com.cwks.bizsys.xtgl.domain;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(DM_QX_GWFL)
*/

public class DmQxGwflPojo {

    private static final long serialVersionUID = 1L;

    private  String gwflmc; //岗位分类名称
    private  String gwfl_dm; //岗位分类代码
    private  String lrrq; //录入日期
    private  String lrr_dm; //录入人代码
    private  String swjg_dm; //税务机关代码
    private  String uuid; //UUID||uuid
    private  String xgrq; //修改日期
    private  String xgr_dm; //修改人代码
    private  String yxbz; //有效标志

    /**无参构造方法**/
    public DmQxGwflPojo(){};

    /**带参构造方法*/
    public DmQxGwflPojo (String gwflmc,String gwfl_dm,String lrrq,String lrr_dm,String swjg_dm,String uuid,String xgrq,String xgr_dm,String yxbz){
        this.gwflmc = gwflmc;
        this.gwfl_dm = gwfl_dm;
        this.lrrq = lrrq;
        this.lrr_dm = lrr_dm;
        this.swjg_dm = swjg_dm;
        this.uuid = uuid;
        this.xgrq = xgrq;
        this.xgr_dm = xgr_dm;
        this.yxbz = yxbz;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public DmQxGwflPojo(Map data){
        this.gwflmc = data.get("gwflmc") == null
            ? null : (String)data.get("gwflmc");
        this.gwfl_dm = data.get("gwfl_dm") == null
            ? null : (String)data.get("gwfl_dm");
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrr_dm = data.get("lrr_dm") == null
            ? null : (String)data.get("lrr_dm");
        this.swjg_dm = data.get("swjg_dm") == null
            ? null : (String)data.get("swjg_dm");
        this.uuid = data.get("uuid") == null
            ? null : (String)data.get("uuid");
        this.xgrq = data.get("xgrq") == null
            ? null : (String)data.get("xgrq");
        this.xgr_dm = data.get("xgr_dm") == null
            ? null : (String)data.get("xgr_dm");
        this.yxbz = data.get("yxbz") == null
            ? null : (String)data.get("yxbz");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("gwflmc",gwflmc);
        map.put("gwfl_dm",gwfl_dm);
        map.put("lrrq",lrrq);
        map.put("lrr_dm",lrr_dm);
        map.put("swjg_dm",swjg_dm);
        map.put("uuid",uuid);
        map.put("xgrq",xgrq);
        map.put("xgr_dm",xgr_dm);
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
    public String getGwfl_dm() {
        return this.gwfl_dm;
    }
    public void setGwfl_dm(String gwfl_dm) {
        this.gwfl_dm = gwfl_dm;
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
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
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
    public String getYxbz() {
        return this.yxbz;
    }
    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }

}
