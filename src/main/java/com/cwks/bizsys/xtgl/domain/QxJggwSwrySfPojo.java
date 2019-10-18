package com.cwks.bizsys.xtgl.domain;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(QX_JGGW_SWRYSF)
*/

public class QxJggwSwrySfPojo {

    private static final long serialVersionUID = 1L;

    private  String gwxh; //岗位序号
    private  String lrrq; //录入日期
    private  String lrr_dm; //录入人代码
    private  String swrysf_dm; //税务人员身份代码||税务人员身份代码
    private  String uuid; //UUID||uuid
    private  String xgrq; //修改日期
    private  String xgr_dm; //修改人代码
    private  String yxbz; //有效标志

    /**无参构造方法**/
    public QxJggwSwrySfPojo(){};

    /**带参构造方法*/
    public QxJggwSwrySfPojo (String gwxh,String lrrq,String lrr_dm,String swrysf_dm,String uuid,String xgrq,String xgr_dm,String yxbz){
        this.gwxh = gwxh;
        this.lrrq = lrrq;
        this.lrr_dm = lrr_dm;
        this.swrysf_dm = swrysf_dm;
        this.uuid = uuid;
        this.xgrq = xgrq;
        this.xgr_dm = xgr_dm;
        this.yxbz = yxbz;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public QxJggwSwrySfPojo(Map data){
        this.gwxh = data.get("gwxh") == null
            ? null : (String)data.get("gwxh");
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrr_dm = data.get("lrr_dm") == null
            ? null : (String)data.get("lrr_dm");
        this.swrysf_dm = data.get("swrysf_dm") == null
            ? null : (String)data.get("swrysf_dm");
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
        map.put("gwxh",gwxh);
        map.put("lrrq",lrrq);
        map.put("lrr_dm",lrr_dm);
        map.put("swrysf_dm",swrysf_dm);
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
    public String getGwxh() {
        return this.gwxh;
    }
    public void setGwxh(String gwxh) {
        this.gwxh = gwxh;
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
    public String getSwrysf_dm() {
        return this.swrysf_dm;
    }
    public void setSwrysf_dm(String swrysf_dm) {
        this.swrysf_dm = swrysf_dm;
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
