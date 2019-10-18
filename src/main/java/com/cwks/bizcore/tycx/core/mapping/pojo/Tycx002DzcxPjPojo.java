package com.cwks.bizcore.tycx.core.mapping.pojo;

import java.util.HashMap;
import java.util.Map;

public class Tycx002DzcxPjPojo {
private String fxyypjxh;
private String fxyyid;
private String pjdj_dm;
private String pjnr;
private String pjr_dm;
private String pjrxm;
public String getPjrxm() {
	return pjrxm;
}
public void setPjrxm(String pjrxm) {
	this.pjrxm = pjrxm;
}

private String pjsj;
private String xybj;
private String lr_sj;
private String xg_sj;
private String lrry_dm;
private String xgry_dm;
private String lrjg_dm;
private String xgjg_dm;
public String getFxyypjxh() {
	return fxyypjxh;
}
public void setFxyypjxh(String fxyypjxh) {
	this.fxyypjxh = fxyypjxh;
}


public String getFxyyid() {
	return fxyyid;
}
public void setFxyyid(String fxyyid) {
	this.fxyyid = fxyyid;
}
public String getPjdj_dm() {
	return pjdj_dm;
}
public void setPjdj_dm(String pjdjDm) {
	pjdj_dm = pjdjDm;
}
public String getPjnr() {
	return pjnr;
}
public void setPjnr(String pjnr) {
	this.pjnr = pjnr;
}
public String getPjr_dm() {
	return pjr_dm;
}
public void setPjr_dm(String pjrDm) {
	pjr_dm = pjrDm;
}

public String getPjsj() {
	return pjsj;
}
public void setPjsj(String pjsj) {
	this.pjsj = pjsj;
}
public String getXybj() {
	return xybj;
}
public void setXybj(String xybj) {
	this.xybj = xybj;
}

public String getLr_sj() {
	return lr_sj;
}
public void setLr_sj(String lrSj) {
	lr_sj = lrSj;
}
public String getXg_sj() {
	return xg_sj;
}
public void setXg_sj(String xgSj) {
	xg_sj = xgSj;
}
public String getLrry_dm() {
	return lrry_dm;
}
public void setLrry_dm(String lrryDm) {
	lrry_dm = lrryDm;
}
public String getXgry_dm() {
	return xgry_dm;
}
public void setXgry_dm(String xgryDm) {
	xgry_dm = xgryDm;
}
public String getLrjg_dm() {
	return lrjg_dm;
}
public void setLrjg_dm(String lrjgDm) {
	lrjg_dm = lrjgDm;
}
public String getXgjg_dm() {
	return xgjg_dm;
}
public void setXgjg_dm(String xgjgDm) {
	xgjg_dm = xgjgDm;
}
public Tycx002DzcxPjPojo(){
	
}
public Tycx002DzcxPjPojo(String fxyypjxh, String fxyyid, String pjdjDm,
		String pjnr, String pjrDm,String pjrxm, String pjsj,String xybj, String lr_sj, String xg_sj,String lrryDm,
		String xgryDm, String lrjgDm, String xgjgDm) {
	super();
	this.fxyypjxh = fxyypjxh;
	this.fxyyid = fxyyid;
	this.pjdj_dm = pjdjDm;
	this.pjnr = pjnr;
	this.pjr_dm = pjrDm;
	this.pjrxm=pjrxm;
	this.xybj = xybj;
	this.lr_sj = lr_sj;
	this.xg_sj = xg_sj;
	this.lrry_dm=lrryDm;
	this.xgry_dm = xgryDm;
	this.lrjg_dm = lrjgDm;
	this.xgjg_dm = xgjgDm;
	this.pjsj = pjsj;
}
public Tycx002DzcxPjPojo(Map data){
	this.fxyypjxh=data.get("fxyypjxh")==null?null:(String)data.get("fxyypjxh");
	this.fxyyid=data.get("fxyyid")==null?null:(String)data.get("fxyyid");
	this.pjdj_dm=data.get("pjdjDm")==null?null:(String)data.get("pjdjDm");
	this.pjnr=data.get("pjnr")==null?null:(String)data.get("pjnr");
	this.pjr_dm=data.get("pjrDm")==null?null:(String)data.get("pjrDm");
	this.pjrxm=data.get("pjrxm")==null?null:(String)data.get("pjrxm");
	this.xybj=data.get("xybj")==null?null:(String)data.get("xybj");
	this.lr_sj=data.get("lr_sj")==null?null:(String)data.get("lr_sj");
	this.xg_sj=data.get("xg_sj")==null?null:(String)data.get("xg_sj");
	this.lrry_dm=data.get("lrryDm")==null?null:(String)data.get("lrryDm");
	this.xgry_dm=data.get("xgryDm")==null?null:(String)data.get("xgryDm");
	this.lrjg_dm=data.get("lrjgDm")==null?null:(String)data.get("lrjgDm");
	this.xgjg_dm=data.get("xgjgDm")==null?null:(String)data.get("xgjgDm");
	this.pjsj=data.get("pjsj")==null?null:(String)data.get("pjsj");
}
public Map toMap(){
    Map map = new HashMap();
    map.put("fxyypjxh",fxyypjxh);
    map.put("fxyyid",fxyyid);
    map.put("pjdjDm",pjdj_dm);
    map.put("pjnr",pjnr);
    map.put("pjrDm",pjr_dm);
    map.put("pjrxm",pjrxm);
    map.put("pjsj",pjsj);
    map.put("xybj",xybj);
    map.put("lr_sj",lr_sj);
    map.put("xg_sj",xg_sj);
    map.put("lrryDm",lrry_dm);
    map.put("xgryDm",xgry_dm);
    map.put("lrjgDm",lrjg_dm);
    map.put("xgjgDM",xgjg_dm);
    return map;
}

public String toString(){
    return toMap().toString();
}


}
