package com.cwks.bizsys.xtgl.domain;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(QX_DLZHXX)
*/

public class QxDlzhxxPojo {

    private static final long serialVersionUID = 1L;

    private  String dlzhkl; //登录账户口令||登录账户口令
    private  String dlzh_dm; //登录账户代码||登录账户代码
    private  String klyxqq; //口令有效期起
    private  String klyxqz; //口令有效期止
    private  String lrrq; //录入日期
    private  String lrr_dm; //录入人代码
    private  String swry_dm; //税务人员代码
    private  String xgrq; //修改日期
    private  String xgr_dm; //修改人代码
    private  String yxbz; //有效标志

    /**无参构造方法**/
    public QxDlzhxxPojo(){};

    /**带参构造方法*/
    public QxDlzhxxPojo (String dlzhkl,String dlzh_dm,String klyxqq,String klyxqz,String lrrq,String lrr_dm,String swry_dm,String xgrq,String xgr_dm,String yxbz){
        this.dlzhkl = dlzhkl;
        this.dlzh_dm = dlzh_dm;
        this.klyxqq = klyxqq;
        this.klyxqz = klyxqz;
        this.lrrq = lrrq;
        this.lrr_dm = lrr_dm;
        this.swry_dm = swry_dm;
        this.xgrq = xgrq;
        this.xgr_dm = xgr_dm;
        this.yxbz = yxbz;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public QxDlzhxxPojo(Map data){
        this.dlzhkl = data.get("dlzhkl") == null
            ? null : (String)data.get("dlzhkl");
        this.dlzh_dm = data.get("dlzh_dm") == null
            ? null : (String)data.get("dlzh_dm");
        this.klyxqq = data.get("klyxqq") == null
            ? null : (String)data.get("klyxqq");
        this.klyxqz = data.get("klyxqz") == null
            ? null : (String)data.get("klyxqz");
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrr_dm = data.get("lrr_dm") == null
            ? null : (String)data.get("lrr_dm");
        this.swry_dm = data.get("swry_dm") == null
            ? null : (String)data.get("swry_dm");
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
        map.put("dlzhkl",dlzhkl);
        map.put("dlzh_dm",dlzh_dm);
        map.put("klyxqq",klyxqq);
        map.put("klyxqz",klyxqz);
        map.put("lrrq",lrrq);
        map.put("lrr_dm",lrr_dm);
        map.put("swry_dm",swry_dm);
        map.put("xgrq",xgrq);
        map.put("xgr_dm",xgr_dm);
        map.put("yxbz",yxbz);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getDlzhkl() {
        return this.dlzhkl;
    }
    public void setDlzhkl(String dlzhkl) {
        this.dlzhkl = dlzhkl;
    }
    public String getDlzh_dm() {
        return this.dlzh_dm;
    }
    public void setDlzh_dm(String dlzh_dm) {
        this.dlzh_dm = dlzh_dm;
    }
    public String getKlyxqq() {
        return this.klyxqq;
    }
    public void setKlyxqq(String klyxqq) {
        this.klyxqq = klyxqq;
    }
    public String getKlyxqz() {
        return this.klyxqz;
    }
    public void setKlyxqz(String klyxqz) {
        this.klyxqz = klyxqz;
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
    public String getSwry_dm() {
        return this.swry_dm;
    }
    public void setSwry_dm(String swry_dm) {
        this.swry_dm = swry_dm;
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
