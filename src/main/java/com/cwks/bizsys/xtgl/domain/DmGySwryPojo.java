package com.cwks.bizsys.xtgl.domain;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(DM_GY_SWRY)
*/

public class DmGySwryPojo {

    private static final long serialVersionUID = 1L;

    private  String dzxx; //电子信箱
    private  String jrswjsj; //进入税务局时间
    private  String lkswjsj; //离开税务局时间
    private  String lxdh; //联系电话
    private  String sfzjhm; //身份证件号码
    private  String swryxm; //税务人员姓名
    private  String swry_dm; //税务人员代码
    private  String xb_dm; //性别代码
    private  String yxbz; //有效标志
    private  String zc_dm; //ZC_DM
    private  String zw; //职务

    /**无参构造方法**/
    public DmGySwryPojo(){};

    /**带参构造方法*/
    public DmGySwryPojo (String dzxx,String jrswjsj,String lkswjsj,String lxdh,String sfzjhm,String swryxm,String swry_dm,String xb_dm,String yxbz,String zc_dm,String zw){
        this.dzxx = dzxx;
        this.jrswjsj = jrswjsj;
        this.lkswjsj = lkswjsj;
        this.lxdh = lxdh;
        this.sfzjhm = sfzjhm;
        this.swryxm = swryxm;
        this.swry_dm = swry_dm;
        this.xb_dm = xb_dm;
        this.yxbz = yxbz;
        this.zc_dm = zc_dm;
        this.zw = zw;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public DmGySwryPojo(Map<?, ?> data){
        this.dzxx = data.get("dzxx") == null
            ? null : (String)data.get("dzxx");
        this.jrswjsj = data.get("jrswjsj") == null
            ? null : (String)data.get("jrswjsj");
        this.lkswjsj = data.get("lkswjsj") == null
            ? null : (String)data.get("lkswjsj");
        this.lxdh = data.get("lxdh") == null
            ? null : (String)data.get("lxdh");
        this.sfzjhm = data.get("sfzjhm") == null
            ? null : (String)data.get("sfzjhm");
        this.swryxm = data.get("swryxm") == null
            ? null : (String)data.get("swryxm");
        this.swry_dm = data.get("swry_dm") == null
            ? null : (String)data.get("swry_dm");
        this.xb_dm = data.get("xb_dm") == null
            ? null : (String)data.get("xb_dm");
        this.yxbz = data.get("yxbz") == null
            ? null : (String)data.get("yxbz");
        this.zc_dm = data.get("zc_dm") == null
            ? null : (String)data.get("zc_dm");
        this.zw = data.get("zw") == null
            ? null : (String)data.get("zw");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dzxx",dzxx);
        map.put("jrswjsj",jrswjsj);
        map.put("lkswjsj",lkswjsj);
        map.put("lxdh",lxdh);
        map.put("sfzjhm",sfzjhm);
        map.put("swryxm",swryxm);
        map.put("swry_dm",swry_dm);
        map.put("xb_dm",xb_dm);
        map.put("yxbz",yxbz);
        map.put("zc_dm",zc_dm);
        map.put("zw",zw);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getDzxx() {
        return this.dzxx;
    }
    public void setDzxx(String dzxx) {
        this.dzxx = dzxx;
    }
    public String getJrswjsj() {
        return this.jrswjsj;
    }
    public void setJrswjsj(String jrswjsj) {
        this.jrswjsj = jrswjsj;
    }
    public String getLkswjsj() {
        return this.lkswjsj;
    }
    public void setLkswjsj(String lkswjsj) {
        this.lkswjsj = lkswjsj;
    }
    public String getLxdh() {
        return this.lxdh;
    }
    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }
    public String getSfzjhm() {
        return this.sfzjhm;
    }
    public void setSfzjhm(String sfzjhm) {
        this.sfzjhm = sfzjhm;
    }
    public String getSwryxm() {
        return this.swryxm;
    }
    public void setSwryxm(String swryxm) {
        this.swryxm = swryxm;
    }
    public String getSwry_dm() {
        return this.swry_dm;
    }
    public void setSwry_dm(String swry_dm) {
        this.swry_dm = swry_dm;
    }
    public String getXb_dm() {
        return this.xb_dm;
    }
    public void setXb_dm(String xb_dm) {
        this.xb_dm = xb_dm;
    }
    public String getYxbz() {
        return this.yxbz;
    }
    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }
    public String getZc_dm() {
        return this.zc_dm;
    }
    public void setZc_dm(String zc_dm) {
        this.zc_dm = zc_dm;
    }
    public String getZw() {
        return this.zw;
    }
    public void setZw(String zw) {
        this.zw = zw;
    }

}
