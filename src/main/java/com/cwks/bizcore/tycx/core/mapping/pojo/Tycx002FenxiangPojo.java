package com.cwks.bizcore.tycx.core.mapping.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * 对应表 t_gnfx
 * @author dinghr
 *
 */
public class Tycx002FenxiangPojo {
	private static final long serialVersionUID = 1L;
	//
	private String fx_id;
	private String fxly;
	private String fxurl;
	private String jsr_dm;
	private String lrr_dm;
	private String lrrq;
	private String xgrq;
	private String xgr_dm;
	private String lcslh;
	private String workItemId;
	
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


	public Tycx002FenxiangPojo() {
		
	}
	

	public Tycx002FenxiangPojo(String fx_id, String fxly, String fxurl,
			String jsr_dm, String lrr_dm, String lrrq, String xgrq,
			String xgr_dm,String lcslh,String workItemId) {
		
		this.fx_id = fx_id;
		this.fxly = fxly;
		this.fxurl = fxurl;
		this.jsr_dm = jsr_dm;
		this.lrr_dm = lrr_dm;
		this.lrrq = lrrq;
		this.xgrq = xgrq;
		this.xgr_dm = xgr_dm;
		this.lcslh=lcslh;
		this.workItemId=workItemId;
	}


	public Tycx002FenxiangPojo(Map data){
		this.fx_id = data.get("fx_id") == null ? null : (String)data.get("fx_id");
		this.fxly = data.get("fxly") == null ? null : (String)data.get("fxly");
		this.fxurl = data.get("fxurl") == null ? null : (String)data.get("fxurl");
		this.jsr_dm = data.get("jsr_dm") == null ? null : (String)data.get("jsr_dm");
		this.lrr_dm = data.get("lrr_dm") == null ? null : (String)data.get("lrr_dm");
		this.lrrq = data.get("lrrq") == null ? null : (String)data.get("lrrq");
		this.xgrq = data.get("xgrq") == null ? null : (String)data.get("xgrq");
		this.xgr_dm = data.get("xgr_dm") == null ? null : (String)data.get("xgr_dm");
		this.lcslh = data.get("lcslh") == null ? null : (String)data.get("lcslh");
		this.workItemId = data.get("workItemId") == null ? null : (String)data.get("workItemId");
	}
	
	public Map toMap(){
        Map map = new HashMap();
        map.put("fx_id",fx_id);
        map.put("fxly",fxly);
        map.put("fxurl",fxurl);
        map.put("jsr_dm",jsr_dm);
        map.put("lrr_dm",lrr_dm);
        map.put("lrrq",lrrq);
        map.put("xgrq",xgrq);
        map.put("xgr_dm",xgr_dm);
        map.put("lcslh",lcslh);
        map.put("workItemId",workItemId);
        return map;
    }


	public String getFx_id() {
		return fx_id;
	}


	public void setFx_id(String fx_id) {
		this.fx_id = fx_id;
	}


	public String getFxly() {
		return fxly;
	}


	public void setFxly(String fxly) {
		this.fxly = fxly;
	}


	public String getFxurl() {
		return fxurl;
	}


	public void setFxurl(String fxurl) {
		this.fxurl = fxurl;
	}


	public String getJsr_dm() {
		return jsr_dm;
	}


	public void setJsr_dm(String jsr_dm) {
		this.jsr_dm = jsr_dm;
	}


	public String getLrr_dm() {
		return lrr_dm;
	}


	public void setLrr_dm(String lrr_dm) {
		this.lrr_dm = lrr_dm;
	}


	public String getLrrq() {
		return lrrq;
	}


	public void setLrrq(String lrrq) {
		this.lrrq = lrrq;
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
