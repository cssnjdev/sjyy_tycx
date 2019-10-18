package com.cwks.bizcore.tycx.core.mapping.pojo;

import com.cwks.bizcore.tycx.core.utils.TycxUtils;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(CX_CXZXXX)
*/

public class Tycx002CxCxzxxxPojo {

    private static final long serialVersionUID = 1L;

    private  String cxsj; //CXSJ
    private  String cxy; //CXY
    private  Double cxzxsj; //CXZXSJ
    private  String czry_dm; //CZRY_DM
    private  Double fhjgs; //FHJGS
    private  String lrrq; //LRRQ
    private  String lrr_dm; //LRR_DM
    private  String sessionid; //SESSIONID
    private  String sjgsdq; //SJGSDQ
    private  String sjgsrq; //SJGSRQ
    private  String sqlstr; //SQLSTR
    private  String sqlxh; //SQLXH
    private  String threadid; //THREADID
    private  String tjcsstr; //TJCSSTR
    private  String uuid; //UUID
    private  String xgrq; //XGRQ
    private  String xgr_dm; //XGR_DM
    private  String ztlx_dm; //ZTLX_DM
    private  String fwip;



	/**无参构造方法**/
    public Tycx002CxCxzxxxPojo(){};

    /**带参构造方法*/
    public Tycx002CxCxzxxxPojo (String cxsj,String cxy,Double cxzxsj,String czry_dm,Double fhjgs,String lrrq,String lrr_dm,String sessionid,String sjgsdq,String sjgsrq,String sqlstr,String sqlxh,String threadid,String tjcsstr,String uuid,String xgrq,String xgr_dm,String ztlx_dm,String fwip){
        this.cxsj = cxsj;
        this.cxy = cxy;
        this.cxzxsj = cxzxsj;
        this.czry_dm = czry_dm;
        this.fhjgs = fhjgs;
        this.lrrq = lrrq;
        this.lrr_dm = lrr_dm;
        this.sessionid = sessionid;
        this.sjgsdq = sjgsdq;
        this.sjgsrq = sjgsrq;
        this.sqlstr = sqlstr;
        this.sqlxh = sqlxh;
        this.threadid = threadid;
        this.tjcsstr = tjcsstr;
        this.uuid = uuid;
        this.xgrq = xgrq;
        this.xgr_dm = xgr_dm;
        this.ztlx_dm = ztlx_dm;
        this.fwip=fwip;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public Tycx002CxCxzxxxPojo(Map data){
        this.cxsj = data.get("cxsj") == null
            ? null : (String)data.get("cxsj");
        this.cxy = data.get("cxy") == null
            ? null : (String)data.get("cxy");
        this.cxzxsj = TycxUtils.isEmpty(data.get("cxzxsj")) ? null : Double.parseDouble((String)data.get("cxzxsj"));
        this.czry_dm = data.get("czry_dm") == null
            ? null : (String)data.get("czry_dm");
        this.fhjgs = TycxUtils.isEmpty(data.get("fhjgs")) ? null : Double.parseDouble((String)data.get("fhjgs"));
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrr_dm = data.get("lrr_dm") == null
            ? null : (String)data.get("lrr_dm");
        this.sessionid = data.get("sessionid") == null
            ? null : (String)data.get("sessionid");
        this.sjgsdq = data.get("sjgsdq") == null
            ? null : (String)data.get("sjgsdq");
        this.sjgsrq = data.get("sjgsrq") == null
            ? null : (String)data.get("sjgsrq");
        this.sqlstr = data.get("sqlstr") == null
            ? null : (String)data.get("sqlstr");
        this.sqlxh = data.get("sqlxh") == null
            ? null : (String)data.get("sqlxh");
        this.threadid = data.get("threadid") == null
            ? null : (String)data.get("threadid");
        this.tjcsstr = data.get("tjcsstr") == null
            ? null : (String)data.get("tjcsstr");
        this.uuid = data.get("uuid") == null
            ? null : (String)data.get("uuid");
        this.xgrq = data.get("xgrq") == null
            ? null : (String)data.get("xgrq");
        this.xgr_dm = data.get("xgr_dm") == null
            ? null : (String)data.get("xgr_dm");
        this.ztlx_dm = data.get("ztlx_dm") == null
            ? null : (String)data.get("ztlx_dm");
        this.fwip = data.get("fwip") == null
        ? null : (String)data.get("fwip");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("cxsj",cxsj);
        map.put("cxy",cxy);
        map.put("cxzxsj",cxzxsj);
        map.put("czry_dm",czry_dm);
        map.put("fhjgs",fhjgs);
        map.put("lrrq",lrrq);
        map.put("lrr_dm",lrr_dm);
        map.put("sessionid",sessionid);
        map.put("sjgsdq",sjgsdq);
        map.put("sjgsrq",sjgsrq);
        map.put("sqlstr",sqlstr);
        map.put("sqlxh",sqlxh);
        map.put("threadid",threadid);
        map.put("tjcsstr",tjcsstr);
        map.put("uuid",uuid);
        map.put("xgrq",xgrq);
        map.put("xgr_dm",xgr_dm);
        map.put("ztlx_dm",ztlx_dm);
        map.put("fwip",fwip);
        return map;
    }

    public String getFwip() {
		return fwip;
	}

	public void setFwip(String fwip) {
		this.fwip = fwip;
	}

	public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getCxsj() {
        return this.cxsj;
    }
    public void setCxsj(String cxsj) {
        this.cxsj = cxsj;
    }
    public String getCxy() {
        return this.cxy;
    }
    public void setCxy(String cxy) {
        this.cxy = cxy;
    }
    public Double getCxzxsj() {
        return this.cxzxsj;
    }
    public void setCxzxsj(Double cxzxsj) {
        this.cxzxsj = cxzxsj;
    }
    public String getCzry_dm() {
        return this.czry_dm;
    }
    public void setCzry_dm(String czry_dm) {
        this.czry_dm = czry_dm;
    }
    public Double getFhjgs() {
        return this.fhjgs;
    }
    public void setFhjgs(Double fhjgs) {
        this.fhjgs = fhjgs;
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
    public String getSessionid() {
        return this.sessionid;
    }
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
    public String getSjgsdq() {
        return this.sjgsdq;
    }
    public void setSjgsdq(String sjgsdq) {
        this.sjgsdq = sjgsdq;
    }
    public String getSjgsrq() {
        return this.sjgsrq;
    }
    public void setSjgsrq(String sjgsrq) {
        this.sjgsrq = sjgsrq;
    }
    public String getSqlstr() {
        return this.sqlstr;
    }
    public void setSqlstr(String sqlstr) {
        this.sqlstr = sqlstr;
    }
    public String getSqlxh() {
        return this.sqlxh;
    }
    public void setSqlxh(String sqlxh) {
        this.sqlxh = sqlxh;
    }
    public String getThreadid() {
        return this.threadid;
    }
    public void setThreadid(String threadid) {
        this.threadid = threadid;
    }
    public String getTjcsstr() {
        return this.tjcsstr;
    }
    public void setTjcsstr(String tjcsstr) {
        this.tjcsstr = tjcsstr;
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
    public String getZtlx_dm() {
        return this.ztlx_dm;
    }
    public void setZtlx_dm(String ztlx_dm) {
        this.ztlx_dm = ztlx_dm;
    }

}