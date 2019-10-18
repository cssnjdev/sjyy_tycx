package com.cwks.bizsys.xtgl.domain;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(DM_QX_SWRYSF)
*/

public class DmQxSwrySfPojo {

    private static final long serialVersionUID = 1L;

    private  String lrrq; //录入日期
    private  String lrr_dm; //录入人代码
    private  String rysfmc; //税务人员身份名称||税务人员身份名称
    private  String sfswjg_dm; //身份税务机构
    private  String sfyxqq; //身份有效期起||身份有效期起
    private  String sfyxqz; //身份有效期止||身份有效期止
    private  String swrysf_dm; //税务人员身份代码||税务人员身份代码
    private  String swry_dm; //税务人员代码
    private  String xgrq; //修改日期
    private  String xgr_dm; //修改人代码
    private  Double xsxh; //显示序号
    private  String yxbz; //有效标志
    private  String zsfbz; //主身份标志

    /**无参构造方法**/
    public DmQxSwrySfPojo(){};

    /**带参构造方法*/
    public DmQxSwrySfPojo (String lrrq,String lrr_dm,String rysfmc,String sfswjg_dm,String sfyxqq,String sfyxqz,String swrysf_dm,String swry_dm,String xgrq,String xgr_dm,Double xsxh,String yxbz,String zsfbz){
        this.lrrq = lrrq;
        this.lrr_dm = lrr_dm;
        this.rysfmc = rysfmc;
        this.sfswjg_dm = sfswjg_dm;
        this.sfyxqq = sfyxqq;
        this.sfyxqz = sfyxqz;
        this.swrysf_dm = swrysf_dm;
        this.swry_dm = swry_dm;
        this.xgrq = xgrq;
        this.xgr_dm = xgr_dm;
        this.xsxh = xsxh;
        this.yxbz = yxbz;
        this.zsfbz = zsfbz;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public DmQxSwrySfPojo(Map data){
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrr_dm = data.get("lrr_dm") == null
            ? null : (String)data.get("lrr_dm");
        this.rysfmc = data.get("rysfmc") == null
            ? null : (String)data.get("rysfmc");
        this.sfswjg_dm = data.get("sfswjg_dm") == null
            ? null : (String)data.get("sfswjg_dm");
        this.sfyxqq = data.get("sfyxqq") == null
            ? null : (String)data.get("sfyxqq");
        this.sfyxqz = data.get("sfyxqz") == null
            ? null : (String)data.get("sfyxqz");
        this.swrysf_dm = data.get("swrysf_dm") == null
            ? null : (String)data.get("swrysf_dm");
        this.swry_dm = data.get("swry_dm") == null
            ? null : (String)data.get("swry_dm");
        this.xgrq = data.get("xgrq") == null
            ? null : (String)data.get("xgrq");
        this.xgr_dm = data.get("xgr_dm") == null
            ? null : (String)data.get("xgr_dm");
        this.xsxh = data.get("xsxh") == null
            || "".equals(data.get("xsxh")) ? null : Double.parseDouble((String)data.get("xsxh"));
        this.yxbz = data.get("yxbz") == null
            ? null : (String)data.get("yxbz");
        this.zsfbz = data.get("zsfbz") == null
            ? null : (String)data.get("zsfbz");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("lrrq",lrrq);
        map.put("lrr_dm",lrr_dm);
        map.put("rysfmc",rysfmc);
        map.put("sfswjg_dm",sfswjg_dm);
        map.put("sfyxqq",sfyxqq);
        map.put("sfyxqz",sfyxqz);
        map.put("swrysf_dm",swrysf_dm);
        map.put("swry_dm",swry_dm);
        map.put("xgrq",xgrq);
        map.put("xgr_dm",xgr_dm);
        map.put("xsxh",xsxh);
        map.put("yxbz",yxbz);
        map.put("zsfbz",zsfbz);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
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
    public String getRysfmc() {
        return this.rysfmc;
    }
    public void setRysfmc(String rysfmc) {
        this.rysfmc = rysfmc;
    }
    public String getSfswjg_dm() {
        return this.sfswjg_dm;
    }
    public void setSfswjg_dm(String sfswjg_dm) {
        this.sfswjg_dm = sfswjg_dm;
    }
    public String getSfyxqq() {
        return this.sfyxqq;
    }
    public void setSfyxqq(String sfyxqq) {
        this.sfyxqq = sfyxqq;
    }
    public String getSfyxqz() {
        return this.sfyxqz;
    }
    public void setSfyxqz(String sfyxqz) {
        this.sfyxqz = sfyxqz;
    }
    public String getSwrysf_dm() {
        return this.swrysf_dm;
    }
    public void setSwrysf_dm(String swrysf_dm) {
        this.swrysf_dm = swrysf_dm;
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
    public String getZsfbz() {
        return this.zsfbz;
    }
    public void setZsfbz(String zsfbz) {
        this.zsfbz = zsfbz;
    }

}
