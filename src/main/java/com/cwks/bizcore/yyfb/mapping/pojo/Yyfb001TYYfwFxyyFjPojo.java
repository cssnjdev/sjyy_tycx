package com.cwks.bizcore.yyfb.mapping.pojo;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(T_YYFW_FXYY_FJ)
*/

public class Yyfb001TYYfwFxyyFjPojo {

    private static final long serialVersionUID = 1L;

    private  String cclxbj; //存储类型标记 1 数据库存储 2 ftp
    private  String fjdx; //附件大小
    private  String fjgs; //附件格式
    private  String fjlx_dm; //附件类型代码 01 业务需求 02 操作
    private  byte[] fjnr; //附件内容 当存储类型标记等于1时，存储相
    private  String fjurl; //附件URL
    private  String fj_id; //附件ID
    private  String fj_mc; //附件名称
    private  String ftppassword; //ftp文件服务器密码
    private  String ftpusername; //ftp文件服务器用户名
    private  String fxyy_id; //分析应用ID
    private  String lrjg_dm; //录入机关代码
    private  String lrrq; //录入时间
    private  String lrry_dm; //录入人员代码
    private  String xgjg_dm; //修改机关代码
    private  String xgrq; //修改时间
    private  String xgry_dm; //修改人员代码
    private  String xybj; //选用标记 1 选用 0 不选用

    /**无参构造方法**/
    public Yyfb001TYYfwFxyyFjPojo(){};

    /**带参构造方法*/
    public Yyfb001TYYfwFxyyFjPojo (String cclxbj,String fjdx,String fjgs,String fjlx_dm,byte[] fjnr,String fjurl,String fj_id,String fj_mc,String ftppassword,String ftpusername,String fxyy_id,String lrjg_dm,String lrrq,String lrry_dm,String xgjg_dm,String xgrq,String xgry_dm,String xybj){
        this.cclxbj = cclxbj;
        this.fjdx = fjdx;
        this.fjgs = fjgs;
        this.fjlx_dm = fjlx_dm;
        this.fjnr = fjnr;
        this.fjurl = fjurl;
        this.fj_id = fj_id;
        this.fj_mc = fj_mc;
        this.ftppassword = ftppassword;
        this.ftpusername = ftpusername;
        this.fxyy_id = fxyy_id;
        this.lrjg_dm = lrjg_dm;
        this.lrrq = lrrq;
        this.lrry_dm = lrry_dm;
        this.xgjg_dm = xgjg_dm;
        this.xgrq = xgrq;
        this.xgry_dm = xgry_dm;
        this.xybj = xybj;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public Yyfb001TYYfwFxyyFjPojo(Map data){
        this.cclxbj = data.get("cclxbj") == null
            ? null : (String)data.get("cclxbj");
        this.fjdx = data.get("fjdx") == null
            ? null : (String)data.get("fjdx");
        this.fjgs = data.get("fjgs") == null
            ? null : (String)data.get("fjgs");
        this.fjlx_dm = data.get("fjlx_dm") == null
            ? null : (String)data.get("fjlx_dm");
        this.fjnr = data.get("fjnr") == null
            ? null : data.get("fjnr").toString().getBytes();
        this.fjurl = data.get("fjurl") == null
            ? null : (String)data.get("fjurl");
        this.fj_id = data.get("fj_id") == null
            ? null : (String)data.get("fj_id");
        this.fj_mc = data.get("fj_mc") == null
            ? null : (String)data.get("fj_mc");
        this.ftppassword = data.get("ftppassword") == null
            ? null : (String)data.get("ftppassword");
        this.ftpusername = data.get("ftpusername") == null
            ? null : (String)data.get("ftpusername");
        this.fxyy_id = data.get("fxyy_id") == null
            ? null : (String)data.get("fxyy_id");
        this.lrjg_dm = data.get("lrjg_dm") == null
            ? null : (String)data.get("lrjg_dm");
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrry_dm = data.get("lrry_dm") == null
            ? null : (String)data.get("lrry_dm");
        this.xgjg_dm = data.get("xgjg_dm") == null
            ? null : (String)data.get("xgjg_dm");
        this.xgrq = data.get("xgrq") == null
            ? null : (String)data.get("xgrq");
        this.xgry_dm = data.get("xgry_dm") == null
            ? null : (String)data.get("xgry_dm");
        this.xybj = data.get("xybj") == null
            ? null : (String)data.get("xybj");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("cclxbj",cclxbj);
        map.put("fjdx",fjdx);
        map.put("fjgs",fjgs);
        map.put("fjlx_dm",fjlx_dm);
        map.put("fjnr",fjnr);
        map.put("fjurl",fjurl);
        map.put("fj_id",fj_id);
        map.put("fj_mc",fj_mc);
        map.put("ftppassword",ftppassword);
        map.put("ftpusername",ftpusername);
        map.put("fxyy_id",fxyy_id);
        map.put("lrjg_dm",lrjg_dm);
        map.put("lrrq",lrrq);
        map.put("lrry_dm",lrry_dm);
        map.put("xgjg_dm",xgjg_dm);
        map.put("xgrq",xgrq);
        map.put("xgry_dm",xgry_dm);
        map.put("xybj",xybj);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getCclxbj() {
        return this.cclxbj;
    }
    public void setCclxbj(String cclxbj) {
        this.cclxbj = cclxbj;
    }
    public String getFjdx() {
        return this.fjdx;
    }
    public void setFjdx(String fjdx) {
        this.fjdx = fjdx;
    }
    public String getFjgs() {
        return this.fjgs;
    }
    public void setFjgs(String fjgs) {
        this.fjgs = fjgs;
    }
    public String getFjlx_dm() {
        return this.fjlx_dm;
    }
    public void setFjlx_dm(String fjlx_dm) {
        this.fjlx_dm = fjlx_dm;
    }
    public byte[] getFjnr() {
        return this.fjnr;
    }
    public void setFjnr(byte[] fjnr) {
        this.fjnr = fjnr;
    }
    public String getFjurl() {
        return this.fjurl;
    }
    public void setFjurl(String fjurl) {
        this.fjurl = fjurl;
    }
    public String getFj_id() {
        return this.fj_id;
    }
    public void setFj_id(String fj_id) {
        this.fj_id = fj_id;
    }
    public String getFj_mc() {
        return this.fj_mc;
    }
    public void setFj_mc(String fj_mc) {
        this.fj_mc = fj_mc;
    }
    public String getFtppassword() {
        return this.ftppassword;
    }
    public void setFtppassword(String ftppassword) {
        this.ftppassword = ftppassword;
    }
    public String getFtpusername() {
        return this.ftpusername;
    }
    public void setFtpusername(String ftpusername) {
        this.ftpusername = ftpusername;
    }
    public String getFxyy_id() {
        return this.fxyy_id;
    }
    public void setFxyy_id(String fxyy_id) {
        this.fxyy_id = fxyy_id;
    }
    public String getLrjg_dm() {
        return this.lrjg_dm;
    }
    public void setLrjg_dm(String lrjg_dm) {
        this.lrjg_dm = lrjg_dm;
    }
    public String getLrrq() {
        return this.lrrq;
    }
    public void setLrrq(String lrrq) {
        this.lrrq = lrrq;
    }
    public String getLrry_dm() {
        return this.lrry_dm;
    }
    public void setLrry_dm(String lrry_dm) {
        this.lrry_dm = lrry_dm;
    }
    public String getXgjg_dm() {
        return this.xgjg_dm;
    }
    public void setXgjg_dm(String xgjg_dm) {
        this.xgjg_dm = xgjg_dm;
    }
    public String getXgrq() {
        return this.xgrq;
    }
    public void setXgrq(String xgrq) {
        this.xgrq = xgrq;
    }
    public String getXgry_dm() {
        return this.xgry_dm;
    }
    public void setXgry_dm(String xgry_dm) {
        this.xgry_dm = xgry_dm;
    }
    public String getXybj() {
        return this.xybj;
    }
    public void setXybj(String xybj) {
        this.xybj = xybj;
    }

}