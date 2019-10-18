package com.cwks.bizcore.tycx.core.mapping.pojo;

import com.cwks.bizcore.tycx.core.utils.TycxUtils;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(CX_CXTJDY)
*/

public class Tycx001CxCxtjdyPojo {

    private static final long serialVersionUID = 1L;

    private  String dmsql; //DMSQL
    private  String fzzdbz; //FZZDBZ
    private  String jgcj; //JGCJ
    private  String jglx; //JGLX
    private  String jssjzd; //JSSJZD
    private  String jylx; //JYLX
    private  String jys; //JYS
    private  String jyzh; //JYZH
    private  String llx; //LLX
    private  String lmc; //LMC
    private  String lrrq; //LRRQ
    private  String lrr_dm; //LRR_DM
    private  String mbbz; //MBBZ
    private  String mrz; //MRZ
    private  String mrzxsbz; //MRZXSBZ
    private  String sjgsdq; //SJGSDQ
    private  String sjtjl; //SJTJL
    private  String sm; //SM
    private  String sqlxh; //SQLXH
    private  String swjgtreescgz; //SWJGTREESCGZ
    private  String tjmc; //TJMC
    private  String tjxylx; //TJXYLX
    private  String uuid; //UUID
    private  String xgrq; //XGRQ
    private  String xgr_dm; //XGR_DM
    private  Double xh; //XH
    private  String xsgs; //XSGS
    private  Double xsxh; //XSXH
    private  String zdycs; //ZDYCS
    private  String zdykd; //ZDYKD
    private  String znxz; //ZNXZ

    /**无参构造方法**/
    public Tycx001CxCxtjdyPojo(){};

    /**带参构造方法*/
    public Tycx001CxCxtjdyPojo (String dmsql,String fzzdbz,String jgcj,String jglx,String jssjzd,String jylx,String jys,String jyzh,String llx,String lmc,String lrrq,String lrr_dm,String mbbz,String mrz,String mrzxsbz,String sjgsdq,String sjtjl,String sm,String sqlxh,String swjgtreescgz,String tjmc,String tjxylx,String uuid,String xgrq,String xgr_dm,Double xh,String xsgs,Double xsxh,String zdycs,String zdykd,String znxz){
        this.dmsql = dmsql;
        this.fzzdbz = fzzdbz;
        this.jgcj = jgcj;
        this.jglx = jglx;
        this.jssjzd = jssjzd;
        this.jylx = jylx;
        this.jys = jys;
        this.jyzh = jyzh;
        this.llx = llx;
        this.lmc = lmc;
        this.lrrq = lrrq;
        this.lrr_dm = lrr_dm;
        this.mbbz = mbbz;
        this.mrz = mrz;
        this.mrzxsbz = mrzxsbz;
        this.sjgsdq = sjgsdq;
        this.sjtjl = sjtjl;
        this.sm = sm;
        this.sqlxh = sqlxh;
        this.swjgtreescgz = swjgtreescgz;
        this.tjmc = tjmc;
        this.tjxylx = tjxylx;
        this.uuid = uuid;
        this.xgrq = xgrq;
        this.xgr_dm = xgr_dm;
        this.xh = xh;
        this.xsgs = xsgs;
        this.xsxh = xsxh;
        this.zdycs = zdycs;
        this.zdykd = zdykd;
        this.znxz = znxz;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public Tycx001CxCxtjdyPojo(Map data){
        this.dmsql = data.get("dmsql") == null
            ? null : (String)data.get("dmsql");
        this.fzzdbz = data.get("fzzdbz") == null
            ? null : (String)data.get("fzzdbz");
        this.jgcj = data.get("jgcj") == null
            ? null : (String)data.get("jgcj");
        this.jglx = data.get("jglx") == null
            ? null : (String)data.get("jglx");
        this.jssjzd = data.get("jssjzd") == null
            ? null : (String)data.get("jssjzd");
        this.jylx = data.get("jylx") == null
            ? null : (String)data.get("jylx");
        this.jys = data.get("jys") == null
            ? null : (String)data.get("jys");
        this.jyzh = data.get("jyzh") == null
            ? null : (String)data.get("jyzh");
        this.llx = data.get("llx") == null
            ? null : (String)data.get("llx");
        this.lmc = data.get("lmc") == null
            ? null : (String)data.get("lmc");
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrr_dm = data.get("lrr_dm") == null
            ? null : (String)data.get("lrr_dm");
        this.mbbz = data.get("mbbz") == null
            ? null : (String)data.get("mbbz");
        this.mrz = data.get("mrz") == null
            ? null : (String)data.get("mrz");
        this.mrzxsbz = data.get("mrzxsbz") == null
            ? null : (String)data.get("mrzxsbz");
        this.sjgsdq = data.get("sjgsdq") == null
            ? null : (String)data.get("sjgsdq");
        this.sjtjl = data.get("sjtjl") == null
            ? null : (String)data.get("sjtjl");
        this.sm = data.get("sm") == null
            ? null : (String)data.get("sm");
        this.sqlxh = data.get("sqlxh") == null
            ? null : (String)data.get("sqlxh");
        this.swjgtreescgz = data.get("swjgtreescgz") == null
            ? null : (String)data.get("swjgtreescgz");
        this.tjmc = data.get("tjmc") == null
            ? null : (String)data.get("tjmc");
        this.tjxylx = data.get("tjxylx") == null
            ? null : (String)data.get("tjxylx");
        this.uuid = data.get("uuid") == null
            ? null : (String)data.get("uuid");
        this.xgrq = data.get("xgrq") == null
            ? null : (String)data.get("xgrq");
        this.xgr_dm = data.get("xgr_dm") == null
            ? null : (String)data.get("xgr_dm");
        this.xh = TycxUtils.isEmpty(data.get("xh")) ? null : Double.parseDouble((String)data.get("xh"));
        this.xsgs = data.get("xsgs") == null
            ? null : (String)data.get("xsgs");
        this.xsxh =TycxUtils.isEmpty(data.get("xsxh")) ? null : Double.parseDouble((String)data.get("xsxh"));
        this.zdycs = data.get("zdycs") == null
            ? null : (String)data.get("zdycs");
        this.zdykd = data.get("zdykd") == null
            ? null : (String)data.get("zdykd");
        this.znxz = data.get("znxz") == null
            ? null : (String)data.get("znxz");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("dmsql",dmsql);
        map.put("fzzdbz",fzzdbz);
        map.put("jgcj",jgcj);
        map.put("jglx",jglx);
        map.put("jssjzd",jssjzd);
        map.put("jylx",jylx);
        map.put("jys",jys);
        map.put("jyzh",jyzh);
        map.put("llx",llx);
        map.put("lmc",lmc);
        map.put("lrrq",lrrq);
        map.put("lrr_dm",lrr_dm);
        map.put("mbbz",mbbz);
        map.put("mrz",mrz);
        map.put("mrzxsbz",mrzxsbz);
        map.put("sjgsdq",sjgsdq);
        map.put("sjtjl",sjtjl);
        map.put("sm",sm);
        map.put("sqlxh",sqlxh);
        map.put("swjgtreescgz",swjgtreescgz);
        map.put("tjmc",tjmc);
        map.put("tjxylx",tjxylx);
        map.put("uuid",uuid);
        map.put("xgrq",xgrq);
        map.put("xgr_dm",xgr_dm);
        map.put("xh",xh);
        map.put("xsgs",xsgs);
        map.put("xsxh",xsxh);
        map.put("zdycs",zdycs);
        map.put("zdykd",zdykd);
        map.put("znxz",znxz);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getDmsql() {
        return this.dmsql;
    }
    public void setDmsql(String dmsql) {
        this.dmsql = dmsql;
    }
    public String getFzzdbz() {
        return this.fzzdbz;
    }
    public void setFzzdbz(String fzzdbz) {
        this.fzzdbz = fzzdbz;
    }
    public String getJgcj() {
        return this.jgcj;
    }
    public void setJgcj(String jgcj) {
        this.jgcj = jgcj;
    }
    public String getJglx() {
        return this.jglx;
    }
    public void setJglx(String jglx) {
        this.jglx = jglx;
    }
    public String getJssjzd() {
        return this.jssjzd;
    }
    public void setJssjzd(String jssjzd) {
        this.jssjzd = jssjzd;
    }
    public String getJylx() {
        return this.jylx;
    }
    public void setJylx(String jylx) {
        this.jylx = jylx;
    }
    public String getJys() {
        return this.jys;
    }
    public void setJys(String jys) {
        this.jys = jys;
    }
    public String getJyzh() {
        return this.jyzh;
    }
    public void setJyzh(String jyzh) {
        this.jyzh = jyzh;
    }
    public String getLlx() {
        return this.llx;
    }
    public void setLlx(String llx) {
        this.llx = llx;
    }
    public String getLmc() {
        return this.lmc;
    }
    public void setLmc(String lmc) {
        this.lmc = lmc;
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
    public String getMbbz() {
        return this.mbbz;
    }
    public void setMbbz(String mbbz) {
        this.mbbz = mbbz;
    }
    public String getMrz() {
        return this.mrz;
    }
    public void setMrz(String mrz) {
        this.mrz = mrz;
    }
    public String getMrzxsbz() {
        return this.mrzxsbz;
    }
    public void setMrzxsbz(String mrzxsbz) {
        this.mrzxsbz = mrzxsbz;
    }
    public String getSjgsdq() {
        return this.sjgsdq;
    }
    public void setSjgsdq(String sjgsdq) {
        this.sjgsdq = sjgsdq;
    }
    public String getSjtjl() {
        return this.sjtjl;
    }
    public void setSjtjl(String sjtjl) {
        this.sjtjl = sjtjl;
    }
    public String getSm() {
        return this.sm;
    }
    public void setSm(String sm) {
        this.sm = sm;
    }
    public String getSqlxh() {
        return this.sqlxh;
    }
    public void setSqlxh(String sqlxh) {
        this.sqlxh = sqlxh;
    }
    public String getSwjgtreescgz() {
        return this.swjgtreescgz;
    }
    public void setSwjgtreescgz(String swjgtreescgz) {
        this.swjgtreescgz = swjgtreescgz;
    }
    public String getTjmc() {
        return this.tjmc;
    }
    public void setTjmc(String tjmc) {
        this.tjmc = tjmc;
    }
    public String getTjxylx() {
        return this.tjxylx;
    }
    public void setTjxylx(String tjxylx) {
        this.tjxylx = tjxylx;
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
    public Double getXh() {
        return this.xh;
    }
    public void setXh(Double xh) {
        this.xh = xh;
    }
    public String getXsgs() {
        return this.xsgs;
    }
    public void setXsgs(String xsgs) {
        this.xsgs = xsgs;
    }
    public Double getXsxh() {
        return this.xsxh;
    }
    public void setXsxh(Double xsxh) {
        this.xsxh = xsxh;
    }
    public String getZdycs() {
        return this.zdycs;
    }
    public void setZdycs(String zdycs) {
        this.zdycs = zdycs;
    }
    public String getZdykd() {
        return this.zdykd;
    }
    public void setZdykd(String zdykd) {
        this.zdykd = zdykd;
    }
    public String getZnxz() {
        return this.znxz;
    }
    public void setZnxz(String znxz) {
        this.znxz = znxz;
    }

}