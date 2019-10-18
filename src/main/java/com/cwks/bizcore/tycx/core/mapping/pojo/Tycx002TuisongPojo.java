package com.cwks.bizcore.tycx.core.mapping.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * 对应表 t_dzcx_wtts
 * @author dinghr
 *
 */
public class Tycx002TuisongPojo {
	private static final long serialVersionUID = 1L;
	//
	private String ts_id;
	private String sqlxh;
	private String cxtj;
	private String wtms;
	private byte[] wtimg;
	private String lrr_dm;
	private String jsry_dm;
	private String lrrq;
	private String clbj;
	private String clsj;
	private String xgrq;
	private String xgr_dm;
	private String fxyyid;
	private String lcslh;
	private String workItemId;
	
	public Tycx002TuisongPojo() {
		
	}
	
	public Tycx002TuisongPojo(String ts_id, String sqlxh, String cxtj,
			String wtms, byte[] wtimg, String lrr_dm, String jsry_dm,
			String lrrq, String clbj, String clsj, String xgrq, String xgr_dm,String fxyyid,String lcslh,String workItemId) {
		
		this.ts_id = ts_id;
		this.sqlxh = sqlxh;
		this.cxtj = cxtj;
		this.wtms = wtms;
		this.wtimg = wtimg;
		this.lrr_dm = lrr_dm;
		this.jsry_dm = jsry_dm;
		this.lrrq = lrrq;
		this.clbj = clbj;
		this.clsj = clsj;
		this.xgrq = xgrq;
		this.xgr_dm = xgr_dm;
		this.fxyyid=fxyyid;
		this.lcslh=lcslh;
		this.workItemId=workItemId;
	}

	public Tycx002TuisongPojo(Map data){
		this.ts_id = data.get("ts_id") == null ? null : (String)data.get("ts_id");
		this.sqlxh = data.get("sqlxh") == null ? null : (String)data.get("sqlxh");
		this.cxtj = data.get("cxtj") == null ? null : (String)data.get("cxtj");
		this.wtms = data.get("wtms") == null ? null : (String)data.get("wtms");
		this.wtimg = data.get("wtimg") == null ? null : (byte[])data.get("wtimg");
		this.lrr_dm = data.get("lrr_dm") == null ? null : (String)data.get("lrr_dm");
		this.jsry_dm = data.get("jsry_dm") == null ? null : (String)data.get("jsry_dm");
		this.lrrq = data.get("lrrq") == null ? null : (String)data.get("lrrq");
		this.clbj = data.get("clbj") == null ? null : (String)data.get("clbj");
		this.clsj = data.get("clsj") == null ? null : (String)data.get("clsj");
		this.xgrq = data.get("xgrq") == null ? null : (String)data.get("xgrq");
		this.xgr_dm = data.get("xgr_dm") == null ? null : (String)data.get("xgr_dm");
		this.fxyyid = data.get("fxyyid") == null ? null : (String)data.get("fxyyid");
		this.lcslh = data.get("lcslh") == null ? null : (String)data.get("lcslh");
		this.workItemId = data.get("workItemId") == null ? null : (String)data.get("workItemId");
	}
	
	public Map toMap(){
        Map map = new HashMap();
        map.put("ts_id",ts_id);
        map.put("sqlxh",sqlxh);
        map.put("cxtj",cxtj);
        map.put("wtms",wtms);
        map.put("wtimg",wtimg);
        map.put("lrr_dm",lrr_dm);
        map.put("jsry_dm",jsry_dm);
        map.put("lrrq",lrrq);
        map.put("clbj",clbj);
        map.put("clsj",clsj);
        map.put("xgrq",xgrq);
        map.put("xgr_dm",xgr_dm);
        map.put("fxyyid",fxyyid);
        map.put("lcslh",lcslh);
        map.put("workItemId",workItemId);
        return map;
    }

	public String getFxyyid() {
		return fxyyid;
	}

	public void setFxyyid(String fxyyid) {
		this.fxyyid = fxyyid;
	}

	public String getLcslh() {
		return lcslh;
	}

	public void setLcslh(String lcslh) {
		this.lcslh = lcslh;
	}

	public String getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}

	public String getTs_id() {
		return ts_id;
	}

	public void setTs_id(String ts_id) {
		this.ts_id = ts_id;
	}

	public String getSqlxh() {
		return sqlxh;
	}

	public void setSqlxh(String sqlxh) {
		this.sqlxh = sqlxh;
	}

	public String getCxtj() {
		return cxtj;
	}

	public void setCxtj(String cxtj) {
		this.cxtj = cxtj;
	}

	public String getWtms() {
		return wtms;
	}

	public void setWtms(String wtms) {
		this.wtms = wtms;
	}

	public byte[] getWtimg() {
		return wtimg;
	}

	public void setWtimg(byte[] wtimg) {
		this.wtimg = wtimg;
	}

	public String getLrr_dm() {
		return lrr_dm;
	}

	public void setLrr_dm(String lrr_dm) {
		this.lrr_dm = lrr_dm;
	}

	public String getJsry_dm() {
		return jsry_dm;
	}

	public void setJsry_dm(String jsry_dm) {
		this.jsry_dm = jsry_dm;
	}

	public String getLrrq() {
		return lrrq;
	}

	public void setLrrq(String lrrq) {
		this.lrrq = lrrq;
	}

	public String getClbj() {
		return clbj;
	}

	public void setClbj(String clbj) {
		this.clbj = clbj;
	}

	public String getClsj() {
		return clsj;
	}

	public void setClsj(String clsj) {
		this.clsj = clsj;
	}

	public String getXgrq() {
		return xgrq;
	}

	public void setXgrq(String xgrq) {
		this.xgrq = xgrq;
	}

	public String getXgr_dm() {
		return xgr_dm;
	}

	public void setXgr_dm(String xgr_dm) {
		this.xgr_dm = xgr_dm;
	}

}
