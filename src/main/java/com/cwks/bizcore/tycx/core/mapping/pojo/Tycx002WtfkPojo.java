package com.cwks.bizcore.tycx.core.mapping.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * 对应表T_WTFK
 * @author dinghr
 *
 */
public class Tycx002WtfkPojo {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String sqlxh;
	private String lrry_dm;
	private String nr;
	private String lr_sj;
	private String bt;
	private String yzcd;
	private String cxgl;
	private String zt;
	private String wt_lx;
	private String yxj;
	private String clry_dm;
	private String cl_sj;
	private String xg_sj;
	private String fxyylx_dm;
	private String zs;
	private String yxbj;
	/**
	 * 流程实例号
	 */
	private String lcslh;
	/**
	 * 工作流id
	 */
	private String workItemId;
	private String fxyyid;
	
	public Tycx002WtfkPojo() {
		
	}
	
	public Tycx002WtfkPojo(String id, String sqlxh, String lrry_dm, String nr,
			String lr_sj, String bt, String yzcd, String cxgl, String zt,
			String wt_lx, String yxj, String clry_dm, String cl_sj, String xg_sj,
			String fxyylx_dm, String zs, String yxbj,String lcslh,String workItemId,String fxyyid) {
		
		this.id = id;
		this.sqlxh = sqlxh;
		this.lrry_dm = lrry_dm;
		this.nr = nr;
		this.lr_sj = lr_sj;
		this.bt = bt;
		this.yzcd = yzcd;
		this.cxgl = cxgl;
		this.zt = zt;
		this.wt_lx = wt_lx;
		this.yxj = yxj;
		this.clry_dm = clry_dm;
		this.cl_sj = cl_sj;
		this.xg_sj = xg_sj;
		this.fxyylx_dm = fxyylx_dm;
		this.zs = zs;
		this.yxbj = yxbj;
		this.lcslh=lcslh;
		this.workItemId=workItemId;
		this.fxyyid=fxyyid;
	}
	public Tycx002WtfkPojo(Map data){
		this.id = data.get("id") == null ? null : (String)data.get("id");
		this.sqlxh = data.get("sqlxh") == null ? null : (String)data.get("sqlxh");
		this.lrry_dm = data.get("lrry_dm") == null ? null : (String)data.get("lrry_dm");
		this.nr = data.get("nr") == null ? null : (String)data.get("nr");
		this.lr_sj = data.get("lr_sj") == null ? null : (String)data.get("lr_sj");
		this.bt = data.get("bt") == null ? null : (String)data.get("bt");
		this.yzcd = data.get("yzcd") == null ? null : (String)data.get("yzcd");
		this.cxgl = data.get("cxgl") == null ? null : (String)data.get("cxgl");
		this.zt = data.get("zt") == null ? null : (String)data.get("zt");
		this.wt_lx = data.get("wt_lx") == null ? null : (String)data.get("wt_lx");
		this.yxj = data.get("yxj") == null ? null : (String)data.get("yxj");
		this.clry_dm = data.get("clry_dm") == null ? null : (String)data.get("clry_dm");
		this.cl_sj = data.get("cl_sj") == null ? null : (String)data.get("cl_sj");
		this.xg_sj = data.get("xg_sj") == null ? null : (String)data.get("xg_sj");
		this.fxyylx_dm = data.get("fxyylx_dm") == null ? null : (String)data.get("fxyylx_dm");
		this.zs = data.get("zs") == null ? null : (String)data.get("zs");
		this.yxbj = data.get("yxbj") == null ? null : (String)data.get("yxbj");
		this.lcslh = data.get("lcslh") == null ? null : (String)data.get("lcslh");
		this.workItemId = data.get("workItemId") == null ? null : (String)data.get("workItemId");
		this.fxyyid = data.get("fxyyid") == null ? null : (String)data.get("fxyyid");
	}
	
	public Map toMap(){
        Map map = new HashMap();
        map.put("id",id);
        map.put("sqlxh",sqlxh);
        map.put("lrry_dm",lrry_dm);
        map.put("nr",nr);
        map.put("lr_sj",lr_sj);
        map.put("bt",bt);
        map.put("yzcd",yzcd);
        map.put("cxgl",cxgl);
        map.put("zt",zt);
        map.put("wt_lx",wt_lx);
        map.put("yxj",yxj);
        map.put("clry_dm",clry_dm);
        map.put("cl_sj",cl_sj);
        map.put("xg_sj",xg_sj);
        map.put("fxyylx_dm",fxyylx_dm);
        map.put("zs",zs);
        map.put("yxbj",yxbj);
        map.put("lcslh", lcslh);
        map.put("workItemId", workItemId);
        map.put("fxyyid", fxyyid);
        return map;
    }


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSqlxh() {
		return sqlxh;
	}
	public void setSqlxh(String sqlxh) {
		this.sqlxh = sqlxh;
	}
	public String getLrry_dm() {
		return lrry_dm;
	}
	public void setLrry_dm(String lrry_dm) {
		this.lrry_dm = lrry_dm;
	}
	public String getNr() {
		return nr;
	}
	public void setNr(String nr) {
		this.nr = nr;
	}
	public String getLr_sj() {
		return lr_sj;
	}
	public void setLr_sj(String lr_sj) {
		this.lr_sj = lr_sj;
	}
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getYzcd() {
		return yzcd;
	}
	public void setYzcd(String yzcd) {
		this.yzcd = yzcd;
	}
	public String getCxgl() {
		return cxgl;
	}
	public void setCxgl(String cxgl) {
		this.cxgl = cxgl;
	}
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	public String getWt_lx() {
		return wt_lx;
	}
	public void setWt_lx(String wt_lx) {
		this.wt_lx = wt_lx;
	}
	public String getYxj() {
		return yxj;
	}
	public void setYxj(String yxj) {
		this.yxj = yxj;
	}
	public String getClry_dm() {
		return clry_dm;
	}
	public void setClry_dm(String clry_dm) {
		this.clry_dm = clry_dm;
	}
	public String getCl_sj() {
		return cl_sj;
	}
	public void setCl_sj(String cl_sj) {
		this.cl_sj = cl_sj;
	}
	public String getXg_sj() {
		return xg_sj;
	}
	public void setXg_sj(String xg_sj) {
		this.xg_sj = xg_sj;
	}
	public String getFxyylx_dm() {
		return fxyylx_dm;
	}
	public void setFxyylx_dm(String fxyylx_dm) {
		this.fxyylx_dm = fxyylx_dm;
	}
	public String getZs() {
		return zs;
	}
	public void setZs(String zs) {
		this.zs = zs;
	}
	public String getYxbj() {
		return yxbj;
	}
	public void setYxbj(String yxbj) {
		this.yxbj = yxbj;
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

	public String getFxyyid() {
		return fxyyid;
	}

	public void setFxyyid(String fxyyid) {
		this.fxyyid = fxyyid;
	}
	
	
}
