package com.cwks.bizcore.tycx.core.mapping.pojo;

import com.cwks.bizcore.tycx.core.utils.TycxUtils;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(CX_ZTCXXM)
*/

public class Tycx004CxZtcxxmPojo {

    private static final long serialVersionUID = 1L;

    private  String bbh; //BBH
    private  String bbid; //BBID
    private  String cxrqqtjlmc; //CXRQQTJLMC
    private  String cxrqztjlmc; //CXRQZTJLMC
    private  String cxxmmc; //CXXMMC
    private  String cxxm_dm; //CXXM_DM
    private  String dylx; //DYLX
    private  String jdlb; //JDLB
    private  String lrrq; //LRRQ
    private  String lrr_dm; //LRR_DM
    private  String mrxsbz; //MRXSBZ
    private  String sjcxxmdm; //SJCXXMDM
    private  String sqlxh; //SQLXH
    private  String tjcsstr; //TJCSSTR
    private  String url; //URL
    private  String xgrq; //XGRQ
    private  String xgr_dm; //XGR_DM
    private  Double xh; //XH
    private  String ywfl_dm; //YWFL_DM
    private  String ztlxmx_dm; //ZTLXMX_DM
    private  String ztlx_dm; //ZTLX_DM

    /**无参构造方法**/
    public Tycx004CxZtcxxmPojo(){};

    /**带参构造方法*/
    public Tycx004CxZtcxxmPojo (String bbh,String bbid,String cxrqqtjlmc,String cxrqztjlmc,String cxxmmc,String cxxm_dm,String dylx,String jdlb,String lrrq,String lrr_dm,String mrxsbz,String sjcxxmdm,String sqlxh,String tjcsstr,String url,String xgrq,String xgr_dm,Double xh,String ywfl_dm,String ztlxmx_dm,String ztlx_dm){
        this.bbh = bbh;
        this.bbid = bbid;
        this.cxrqqtjlmc = cxrqqtjlmc;
        this.cxrqztjlmc = cxrqztjlmc;
        this.cxxmmc = cxxmmc;
        this.cxxm_dm = cxxm_dm;
        this.dylx = dylx;
        this.jdlb = jdlb;
        this.lrrq = lrrq;
        this.lrr_dm = lrr_dm;
        this.mrxsbz = mrxsbz;
        this.sjcxxmdm = sjcxxmdm;
        this.sqlxh = sqlxh;
        this.tjcsstr = tjcsstr;
        this.url = url;
        this.xgrq = xgrq;
        this.xgr_dm = xgr_dm;
        this.xh = xh;
        this.ywfl_dm = ywfl_dm;
        this.ztlxmx_dm = ztlxmx_dm;
        this.ztlx_dm = ztlx_dm;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public Tycx004CxZtcxxmPojo(Map data){
        this.bbh = data.get("bbh") == null
            ? null : (String)data.get("bbh");
        this.bbid = data.get("bbid") == null
            ? null : (String)data.get("bbid");
        this.cxrqqtjlmc = data.get("cxrqqtjlmc") == null
            ? null : (String)data.get("cxrqqtjlmc");
        this.cxrqztjlmc = data.get("cxrqztjlmc") == null
            ? null : (String)data.get("cxrqztjlmc");
        this.cxxmmc = data.get("cxxmmc") == null
            ? null : (String)data.get("cxxmmc");
        this.cxxm_dm = data.get("cxxm_dm") == null
            ? null : (String)data.get("cxxm_dm");
        this.dylx = data.get("dylx") == null
            ? null : (String)data.get("dylx");
        this.jdlb = data.get("jdlb") == null
            ? null : (String)data.get("jdlb");
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrr_dm = data.get("lrr_dm") == null
            ? null : (String)data.get("lrr_dm");
        this.mrxsbz = data.get("mrxsbz") == null
            ? null : (String)data.get("mrxsbz");
        this.sjcxxmdm = data.get("sjcxxmdm") == null
            ? null : (String)data.get("sjcxxmdm");
        this.sqlxh = data.get("sqlxh") == null
            ? null : (String)data.get("sqlxh");
        this.tjcsstr = data.get("tjcsstr") == null
            ? null : (String)data.get("tjcsstr");
        this.url = data.get("url") == null
            ? null : (String)data.get("url");
        this.xgrq = data.get("xgrq") == null
            ? null : (String)data.get("xgrq");
        this.xgr_dm = data.get("xgr_dm") == null
            ? null : (String)data.get("xgr_dm");
        this.xh = TycxUtils.isEmpty(data.get("xh")) ? null : Double.parseDouble((String)data.get("xh"));
        this.ywfl_dm = data.get("ywfl_dm") == null
            ? null : (String)data.get("ywfl_dm");
        this.ztlxmx_dm = data.get("ztlxmx_dm") == null
            ? null : (String)data.get("ztlxmx_dm");
        this.ztlx_dm = data.get("ztlx_dm") == null
            ? null : (String)data.get("ztlx_dm");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("bbh",bbh);
        map.put("bbid",bbid);
        map.put("cxrqqtjlmc",cxrqqtjlmc);
        map.put("cxrqztjlmc",cxrqztjlmc);
        map.put("cxxmmc",cxxmmc);
        map.put("cxxm_dm",cxxm_dm);
        map.put("dylx",dylx);
        map.put("jdlb",jdlb);
        map.put("lrrq",lrrq);
        map.put("lrr_dm",lrr_dm);
        map.put("mrxsbz",mrxsbz);
        map.put("sjcxxmdm",sjcxxmdm);
        map.put("sqlxh",sqlxh);
        map.put("tjcsstr",tjcsstr);
        map.put("url",url);
        map.put("xgrq",xgrq);
        map.put("xgr_dm",xgr_dm);
        map.put("xh",xh);
        map.put("ywfl_dm",ywfl_dm);
        map.put("ztlxmx_dm",ztlxmx_dm);
        map.put("ztlx_dm",ztlx_dm);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getBbh() {
        return this.bbh;
    }
    public void setBbh(String bbh) {
        this.bbh = bbh;
    }
    public String getBbid() {
        return this.bbid;
    }
    public void setBbid(String bbid) {
        this.bbid = bbid;
    }
    public String getCxrqqtjlmc() {
        return this.cxrqqtjlmc;
    }
    public void setCxrqqtjlmc(String cxrqqtjlmc) {
        this.cxrqqtjlmc = cxrqqtjlmc;
    }
    public String getCxrqztjlmc() {
        return this.cxrqztjlmc;
    }
    public void setCxrqztjlmc(String cxrqztjlmc) {
        this.cxrqztjlmc = cxrqztjlmc;
    }
    public String getCxxmmc() {
        return this.cxxmmc;
    }
    public void setCxxmmc(String cxxmmc) {
        this.cxxmmc = cxxmmc;
    }
    public String getCxxm_dm() {
        return this.cxxm_dm;
    }
    public void setCxxm_dm(String cxxm_dm) {
        this.cxxm_dm = cxxm_dm;
    }
    public String getDylx() {
        return this.dylx;
    }
    public void setDylx(String dylx) {
        this.dylx = dylx;
    }
    public String getJdlb() {
        return this.jdlb;
    }
    public void setJdlb(String jdlb) {
        this.jdlb = jdlb;
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
    public String getMrxsbz() {
        return this.mrxsbz;
    }
    public void setMrxsbz(String mrxsbz) {
        this.mrxsbz = mrxsbz;
    }
    public String getSjcxxmdm() {
        return this.sjcxxmdm;
    }
    public void setSjcxxmdm(String sjcxxmdm) {
        this.sjcxxmdm = sjcxxmdm;
    }
    public String getSqlxh() {
        return this.sqlxh;
    }
    public void setSqlxh(String sqlxh) {
        this.sqlxh = sqlxh;
    }
    public String getTjcsstr() {
        return this.tjcsstr;
    }
    public void setTjcsstr(String tjcsstr) {
        this.tjcsstr = tjcsstr;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
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
    public String getYwfl_dm() {
        return this.ywfl_dm;
    }
    public void setYwfl_dm(String ywfl_dm) {
        this.ywfl_dm = ywfl_dm;
    }
    public String getZtlxmx_dm() {
        return this.ztlxmx_dm;
    }
    public void setZtlxmx_dm(String ztlxmx_dm) {
        this.ztlxmx_dm = ztlxmx_dm;
    }
    public String getZtlx_dm() {
        return this.ztlx_dm;
    }
    public void setZtlx_dm(String ztlx_dm) {
        this.ztlx_dm = ztlx_dm;
    }

}