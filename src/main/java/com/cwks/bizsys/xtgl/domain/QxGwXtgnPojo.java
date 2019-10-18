package com.cwks.bizsys.xtgl.domain;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(QX_GW_XTGN)
*/

public class QxGwXtgnPojo {

    private static final long serialVersionUID = 1L;

    private  String gw_dm; //岗位代码
    private  String lrrq; //录入日期
    private  String lrr_dm; //录入人代码
    private  String uuid; //UUID
    private  String xgrq; //修改日期
    private  String xgr_dm; //修改人代码
    private  String xtgn_dm; //系统功能代码
    private  String yx_bj; //有效标记

    /**无参构造方法**/
    public QxGwXtgnPojo(){};

    /**带参构造方法*/
    public QxGwXtgnPojo (String gw_dm,String lrrq,String lrr_dm,String uuid,String xgrq,String xgr_dm,String xtgn_dm,String yx_bj){
        this.gw_dm = gw_dm;
        this.lrrq = lrrq;
        this.lrr_dm = lrr_dm;
        this.uuid = uuid;
        this.xgrq = xgrq;
        this.xgr_dm = xgr_dm;
        this.xtgn_dm = xtgn_dm;
        this.yx_bj = yx_bj;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public QxGwXtgnPojo(Map data){
        this.gw_dm = data.get("gw_dm") == null
            ? null : (String)data.get("gw_dm");
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrr_dm = data.get("lrr_dm") == null
            ? null : (String)data.get("lrr_dm");
        this.uuid = data.get("uuid") == null
            ? null : (String)data.get("uuid");
        this.xgrq = data.get("xgrq") == null
            ? null : (String)data.get("xgrq");
        this.xgr_dm = data.get("xgr_dm") == null
            ? null : (String)data.get("xgr_dm");
        this.xtgn_dm = data.get("xtgn_dm") == null
            ? null : (String)data.get("xtgn_dm");
        this.yx_bj = data.get("yx_bj") == null
            ? null : (String)data.get("yx_bj");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("gw_dm",gw_dm);
        map.put("lrrq",lrrq);
        map.put("lrr_dm",lrr_dm);
        map.put("uuid",uuid);
        map.put("xgrq",xgrq);
        map.put("xgr_dm",xgr_dm);
        map.put("xtgn_dm",xtgn_dm);
        map.put("yx_bj",yx_bj);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
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
    public String getXtgn_dm() {
        return this.xtgn_dm;
    }
    public void setXtgn_dm(String xtgn_dm) {
        this.xtgn_dm = xtgn_dm;
    }
    public String getYx_bj() {
        return this.yx_bj;
    }
    public void setYx_bj(String yx_bj) {
        this.yx_bj = yx_bj;
    }

}
