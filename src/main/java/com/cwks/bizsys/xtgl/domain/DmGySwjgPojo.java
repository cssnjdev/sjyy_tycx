package com.cwks.bizsys.xtgl.domain;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(DM_GY_SWJG)
*/

public class DmGySwjgPojo {

    private static final long serialVersionUID = 1L;

    private  String id;
    private  String text;
    private  String state;
    private  String nodeId;
    private  String bsfwtbz; //办税服务厅标志
    private  String czdh; //传真电话
    private  String dsswjgjg; //地税局轨
    private  String dsswjgmc; //地税税务机关名称
    private  String dzxx; //电子信箱
    private  String gdslx_dm; //国地税类型代码
    private  String ghbz; //管户标志
    private  String gjswjgmc; //国家税务机关名称
    private  String gsswjgjg; //国税局轨
    private  String jgjc_dm; //机构级次代码
    private  String sjswjg_dm; //上级税务机关代码
    private  String swjgbz; //税务机构标志 0机关 1部门（设计阶段建
    private  String swjgdz; //税务机关地址
    private  String swjgfzr_dm; //负责人
    private  String swjgjc; //税务机构简称
    private  String swjgjg; //税务机构局轨
    private  String swjglxdh; //税务机关联系电话
    private  String swjgmc; //税务机关名称
    private  String swjgywmc; //税务机构英文名称
    private  String swjgyzbm; //税务机构邮政编码
    private  String swjg_dm; //税务机关代码
    private  Double xsxh; //显示序号
    private  String xybz; //选用标志
    private  String xzqhsz_dm; //行政区划数字代码
    private  String yxbz; //有效标志
    private  String yxqsrq; //有效起始日期
    private  String yxzzrq; //有效终止日期
    private  String zn_dm; //职能代码

    /**无参构造方法**/
    public DmGySwjgPojo(){};

    /**带参构造方法*/
    public DmGySwjgPojo (String id,String text,String state,String nodeId,String bsfwtbz,String czdh,String dsswjgjg,String dsswjgmc,String dzxx,String gdslx_dm,String ghbz,String gjswjgmc,String gsswjgjg,String jgjc_dm,String sjswjg_dm,String swjgbz,String swjgdz,String swjgfzr_dm,String swjgjc,String swjgjg,String swjglxdh,String swjgmc,String swjgywmc,String swjgyzbm,String swjg_dm,Double xsxh,String xybz,String xzqhsz_dm,String yxbz,String yxqsrq,String yxzzrq,String zn_dm){
        this.id = id;
        this.text = text;
        this.state = state;
        this.nodeId = nodeId;
        this.bsfwtbz = bsfwtbz;
        this.czdh = czdh;
        this.dsswjgjg = dsswjgjg;
        this.dsswjgmc = dsswjgmc;
        this.dzxx = dzxx;
        this.gdslx_dm = gdslx_dm;
        this.ghbz = ghbz;
        this.gjswjgmc = gjswjgmc;
        this.gsswjgjg = gsswjgjg;
        this.jgjc_dm = jgjc_dm;
        this.sjswjg_dm = sjswjg_dm;
        this.swjgbz = swjgbz;
        this.swjgdz = swjgdz;
        this.swjgfzr_dm = swjgfzr_dm;
        this.swjgjc = swjgjc;
        this.swjgjg = swjgjg;
        this.swjglxdh = swjglxdh;
        this.swjgmc = swjgmc;
        this.swjgywmc = swjgywmc;
        this.swjgyzbm = swjgyzbm;
        this.swjg_dm = swjg_dm;
        this.xsxh = xsxh;
        this.xybz = xybz;
        this.xzqhsz_dm = xzqhsz_dm;
        this.yxbz = yxbz;
        this.yxqsrq = yxqsrq;
        this.yxzzrq = yxzzrq;
        this.zn_dm = zn_dm;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public DmGySwjgPojo(Map<?, ?> data){
        this.id = data.get("id") == null
                ? null : (String)data.get("id");
        this.text = data.get("text") == null
                ? null : (String)data.get("text");
        this.state = data.get("state") == null
                ? null : (String)data.get("state");
        this.nodeId = data.get("nodeId") == null
                ? null : (String)data.get("nodeId");
        this.bsfwtbz = data.get("bsfwtbz") == null
            ? null : (String)data.get("bsfwtbz");
        this.czdh = data.get("czdh") == null
            ? null : (String)data.get("czdh");
        this.dsswjgjg = data.get("dsswjgjg") == null
            ? null : (String)data.get("dsswjgjg");
        this.dsswjgmc = data.get("dsswjgmc") == null
            ? null : (String)data.get("dsswjgmc");
        this.dzxx = data.get("dzxx") == null
            ? null : (String)data.get("dzxx");
        this.gdslx_dm = data.get("gdslx_dm") == null
            ? null : (String)data.get("gdslx_dm");
        this.ghbz = data.get("ghbz") == null
            ? null : (String)data.get("ghbz");
        this.gjswjgmc = data.get("gjswjgmc") == null
            ? null : (String)data.get("gjswjgmc");
        this.gsswjgjg = data.get("gsswjgjg") == null
            ? null : (String)data.get("gsswjgjg");
        this.jgjc_dm = data.get("jgjc_dm") == null
            ? null : (String)data.get("jgjc_dm");
        this.sjswjg_dm = data.get("sjswjg_dm") == null
            ? null : (String)data.get("sjswjg_dm");
        this.swjgbz = data.get("swjgbz") == null
            ? null : (String)data.get("swjgbz");
        this.swjgdz = data.get("swjgdz") == null
            ? null : (String)data.get("swjgdz");
        this.swjgfzr_dm = data.get("swjgfzr_dm") == null
            ? null : (String)data.get("swjgfzr_dm");
        this.swjgjc = data.get("swjgjc") == null
            ? null : (String)data.get("swjgjc");
        this.swjgjg = data.get("swjgjg") == null
            ? null : (String)data.get("swjgjg");
        this.swjglxdh = data.get("swjglxdh") == null
            ? null : (String)data.get("swjglxdh");
        this.swjgmc = data.get("swjgmc") == null
            ? null : (String)data.get("swjgmc");
        this.swjgywmc = data.get("swjgywmc") == null
            ? null : (String)data.get("swjgywmc");
        this.swjgyzbm = data.get("swjgyzbm") == null
            ? null : (String)data.get("swjgyzbm");
        this.swjg_dm = data.get("swjg_dm") == null
            ? null : (String)data.get("swjg_dm");
        this.xsxh = data.get("xsxh") == null
            || "".equals(data.get("xsxh")) ? null : Double.parseDouble((String)data.get("xsxh"));
        this.xybz = data.get("xybz") == null
            ? null : (String)data.get("xybz");
        this.xzqhsz_dm = data.get("xzqhsz_dm") == null
            ? null : (String)data.get("xzqhsz_dm");
        this.yxbz = data.get("yxbz") == null
            ? null : (String)data.get("yxbz");
        this.yxqsrq = data.get("yxqsrq") == null
            ? null : (String)data.get("yxqsrq");
        this.yxzzrq = data.get("yxzzrq") == null
            ? null : (String)data.get("yxzzrq");
        this.zn_dm = data.get("zn_dm") == null
            ? null : (String)data.get("zn_dm");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("id",id);
        map.put("text",text);
        map.put("state",state);
        map.put("nodeId",nodeId);
        map.put("bsfwtbz",bsfwtbz);
        map.put("czdh",czdh);
        map.put("dsswjgjg",dsswjgjg);
        map.put("dsswjgmc",dsswjgmc);
        map.put("dzxx",dzxx);
        map.put("gdslx_dm",gdslx_dm);
        map.put("ghbz",ghbz);
        map.put("gjswjgmc",gjswjgmc);
        map.put("gsswjgjg",gsswjgjg);
        map.put("jgjc_dm",jgjc_dm);
        map.put("sjswjg_dm",sjswjg_dm);
        map.put("swjgbz",swjgbz);
        map.put("swjgdz",swjgdz);
        map.put("swjgfzr_dm",swjgfzr_dm);
        map.put("swjgjc",swjgjc);
        map.put("swjgjg",swjgjg);
        map.put("swjglxdh",swjglxdh);
        map.put("swjgmc",swjgmc);
        map.put("swjgywmc",swjgywmc);
        map.put("swjgyzbm",swjgyzbm);
        map.put("swjg_dm",swjg_dm);
        map.put("xsxh",xsxh);
        map.put("xybz",xybz);
        map.put("xzqhsz_dm",xzqhsz_dm);
        map.put("yxbz",yxbz);
        map.put("yxqsrq",yxqsrq);
        map.put("yxzzrq",yxzzrq);
        map.put("zn_dm",zn_dm);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getNodeId() {
        return this.nodeId;
    }
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    public String getBsfwtbz() {
        return this.bsfwtbz;
    }
    public void setBsfwtbz(String bsfwtbz) {
        this.bsfwtbz = bsfwtbz;
    }
    public String getCzdh() {
        return this.czdh;
    }
    public void setCzdh(String czdh) {
        this.czdh = czdh;
    }
    public String getDsswjgjg() {
        return this.dsswjgjg;
    }
    public void setDsswjgjg(String dsswjgjg) {
        this.dsswjgjg = dsswjgjg;
    }
    public String getDsswjgmc() {
        return this.dsswjgmc;
    }
    public void setDsswjgmc(String dsswjgmc) {
        this.dsswjgmc = dsswjgmc;
    }
    public String getDzxx() {
        return this.dzxx;
    }
    public void setDzxx(String dzxx) {
        this.dzxx = dzxx;
    }
    public String getGdslx_dm() {
        return this.gdslx_dm;
    }
    public void setGdslx_dm(String gdslx_dm) {
        this.gdslx_dm = gdslx_dm;
    }
    public String getGhbz() {
        return this.ghbz;
    }
    public void setGhbz(String ghbz) {
        this.ghbz = ghbz;
    }
    public String getGjswjgmc() {
        return this.gjswjgmc;
    }
    public void setGjswjgmc(String gjswjgmc) {
        this.gjswjgmc = gjswjgmc;
    }
    public String getGsswjgjg() {
        return this.gsswjgjg;
    }
    public void setGsswjgjg(String gsswjgjg) {
        this.gsswjgjg = gsswjgjg;
    }
    public String getJgjc_dm() {
        return this.jgjc_dm;
    }
    public void setJgjc_dm(String jgjc_dm) {
        this.jgjc_dm = jgjc_dm;
    }
    public String getSjswjg_dm() {
        return this.sjswjg_dm;
    }
    public void setSjswjg_dm(String sjswjg_dm) {
        this.sjswjg_dm = sjswjg_dm;
    }
    public String getSwjgbz() {
        return this.swjgbz;
    }
    public void setSwjgbz(String swjgbz) {
        this.swjgbz = swjgbz;
    }
    public String getSwjgdz() {
        return this.swjgdz;
    }
    public void setSwjgdz(String swjgdz) {
        this.swjgdz = swjgdz;
    }
    public String getSwjgfzr_dm() {
        return this.swjgfzr_dm;
    }
    public void setSwjgfzr_dm(String swjgfzr_dm) {
        this.swjgfzr_dm = swjgfzr_dm;
    }
    public String getSwjgjc() {
        return this.swjgjc;
    }
    public void setSwjgjc(String swjgjc) {
        this.swjgjc = swjgjc;
    }
    public String getSwjgjg() {
        return this.swjgjg;
    }
    public void setSwjgjg(String swjgjg) {
        this.swjgjg = swjgjg;
    }
    public String getSwjglxdh() {
        return this.swjglxdh;
    }
    public void setSwjglxdh(String swjglxdh) {
        this.swjglxdh = swjglxdh;
    }
    public String getSwjgmc() {
        return this.swjgmc;
    }
    public void setSwjgmc(String swjgmc) {
        this.swjgmc = swjgmc;
    }
    public String getSwjgywmc() {
        return this.swjgywmc;
    }
    public void setSwjgywmc(String swjgywmc) {
        this.swjgywmc = swjgywmc;
    }
    public String getSwjgyzbm() {
        return this.swjgyzbm;
    }
    public void setSwjgyzbm(String swjgyzbm) {
        this.swjgyzbm = swjgyzbm;
    }
    public String getSwjg_dm() {
        return this.swjg_dm;
    }
    public void setSwjg_dm(String swjg_dm) {
        this.swjg_dm = swjg_dm;
    }
    public Double getXsxh() {
        return this.xsxh;
    }
    public void setXsxh(Double xsxh) {
        this.xsxh = xsxh;
    }
    public String getXybz() {
        return this.xybz;
    }
    public void setXybz(String xybz) {
        this.xybz = xybz;
    }
    public String getXzqhsz_dm() {
        return this.xzqhsz_dm;
    }
    public void setXzqhsz_dm(String xzqhsz_dm) {
        this.xzqhsz_dm = xzqhsz_dm;
    }
    public String getYxbz() {
        return this.yxbz;
    }
    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }
    public String getYxqsrq() {
        return this.yxqsrq;
    }
    public void setYxqsrq(String yxqsrq) {
        this.yxqsrq = yxqsrq;
    }
    public String getYxzzrq() {
        return this.yxzzrq;
    }
    public void setYxzzrq(String yxzzrq) {
        this.yxzzrq = yxzzrq;
    }
    public String getZn_dm() {
        return this.zn_dm;
    }
    public void setZn_dm(String zn_dm) {
        this.zn_dm = zn_dm;
    }

}
