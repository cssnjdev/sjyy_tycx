package com.cwks.bizcore.tycx.core.mapping.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * 对应表T_WTFK
 * @author dinghr
 *
 */
public class Tycx002FjPojo {
	private static final long serialVersionUID = 1L;
	
	private String fj_id;
	private String wtfk_id;
	private String fj_mc;
	private String fjlx_dm;
	private String cclxbj;
	private String fjnr;
	private String ftpusername;
	private String ftppassword;
	private String fjurl;
	private String xybj;
	private String lr_sj;
	private String xg_sj;
	private String lrry_dm;
	private String xgry_dm;
	private String lrjg_dm;
	private String xgjg_dm;
	private String fjgs;
	private String fjdx;
	private String option="下载";
	
	public Tycx002FjPojo() {
		
	}
	
	public Tycx002FjPojo(String fj_id, String wtfk_id, String fj_mc,
			String fjlx_dm, String cclxbj, String fjnr, String ftpusername,
			String ftppassword, String fjurl, String xybj, String lr_sj,
			String xg_sj, String lrry_dm, String xgry_dm, String lrjg_dm,
			String xgjg_dm, String fjgs, String fjdx) {
		
		this.fj_id = fj_id;
		this.wtfk_id = wtfk_id;
		this.fj_mc = fj_mc;
		this.fjlx_dm = fjlx_dm;
		this.cclxbj = cclxbj;
		this.fjnr = fjnr;
		this.ftpusername = ftpusername;
		this.ftppassword = ftppassword;
		this.fjurl = fjurl;
		this.xybj = xybj;
		this.lr_sj = lr_sj;
		this.xg_sj = xg_sj;
		this.lrry_dm = lrry_dm;
		this.xgry_dm = xgry_dm;
		this.lrjg_dm = lrjg_dm;
		this.xgjg_dm = xgjg_dm;
		this.fjgs = fjgs;
		this.fjdx = fjdx;
	}

	public Tycx002FjPojo(Map data){
		this.fj_id = data.get("fj_id") == null ? null : (String)data.get("fj_id");
		this.wtfk_id = data.get("wtfk_id") == null ? null : (String)data.get("wtfk_id");
		this.fj_mc = data.get("fj_mc") == null ? null : (String)data.get("fj_mc");
		this.fjlx_dm = data.get("fjlx_dm") == null ? null : (String)data.get("fjlx_dm");
		this.cclxbj = data.get("cclxbj") == null ? null : (String)data.get("cclxbj");
		this.fjnr = data.get("fjnr") == null ? null : (String)data.get("fjnr");
		this.ftpusername = data.get("ftpusername") == null ? null : (String)data.get("ftpusername");
		this.ftppassword = data.get("ftppassword") == null ? null : (String)data.get("ftppassword");
		this.fjurl = data.get("fjurl") == null ? null : (String)data.get("fjurl");
		this.xybj = data.get("xybj") == null ? null : (String)data.get("xybj");
		this.lr_sj = data.get("lr_sj") == null ? null : (String)data.get("lr_sj");
		this.xg_sj = data.get("xg_sj") == null ? null : (String)data.get("xg_sj");
		this.lrry_dm = data.get("lrry_dm") == null ? null : (String)data.get("lrry_dm");
		this.xgry_dm = data.get("xgry_dm") == null ? null : (String)data.get("xgry_dm");
		this.lrjg_dm = data.get("lrjg_dm") == null ? null : (String)data.get("lrjg_dm");
		this.xgjg_dm = data.get("xgjg_dm") == null ? null : (String)data.get("xgjg_dm");
		this.fjgs = data.get("fjgs") == null ? null : (String)data.get("fjgs");
		this.fjdx = data.get("fjdx") == null ? null : (String)data.get("fjdx");
	}
	
	public Map toMap(){
        Map map = new HashMap();
        map.put("fj_id",fj_id);
        map.put("wtfk_id",wtfk_id);
        map.put("fj_mc",fj_mc);
        map.put("fjlx_dm",fjlx_dm);
        map.put("cclxbj",cclxbj);
        map.put("fjnr",fjnr);
        map.put("ftpusername",ftpusername);
        map.put("ftppassword",ftppassword);
        map.put("fjurl",fjurl);
        map.put("xybj",xybj);
        map.put("lr_sj",lr_sj);
        map.put("xg_sj",xg_sj);
        map.put("lrry_dm",lrry_dm);
        map.put("xgry_dm",xgry_dm);
        map.put("lrjg_dm",lrjg_dm);
        map.put("xgjg_dm",xgjg_dm);
        map.put("fjgs",fjgs);
        map.put("fjdx",fjdx);
        return map;
    }

	public String getFj_id() {
		return fj_id;
	}

	public void setFj_id(String fj_id) {
		this.fj_id = fj_id;
	}

	public String getWtfk_id() {
		return wtfk_id;
	}

	public void setWtfk_id(String wtfk_id) {
		this.wtfk_id = wtfk_id;
	}

	public String getFj_mc() {
		return fj_mc;
	}

	public void setFj_mc(String fj_mc) {
		this.fj_mc = fj_mc;
	}

	public String getFjlx_dm() {
		return fjlx_dm;
	}

	public void setFjlx_dm(String fjlx_dm) {
		this.fjlx_dm = fjlx_dm;
	}

	public String getCclxbj() {
		return cclxbj;
	}

	public void setCclxbj(String cclxbj) {
		this.cclxbj = cclxbj;
	}

	public String getFjnr() {
		return fjnr;
	}

	public void setFjnr(String fjnr) {
		this.fjnr = fjnr;
	}

	public String getFtpusername() {
		return ftpusername;
	}

	public void setFtpusername(String ftpusername) {
		this.ftpusername = ftpusername;
	}

	public String getFtppassword() {
		return ftppassword;
	}

	public void setFtppassword(String ftppassword) {
		this.ftppassword = ftppassword;
	}

	public String getFjurl() {
		return fjurl;
	}

	public void setFjurl(String fjurl) {
		this.fjurl = fjurl;
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

	public void setLr_sj(String lr_sj) {
		this.lr_sj = lr_sj;
	}

	public String getXg_sj() {
		return xg_sj;
	}

	public void setXg_sj(String xg_sj) {
		this.xg_sj = xg_sj;
	}

	public String getLrry_dm() {
		return lrry_dm;
	}

	public void setLrry_dm(String lrry_dm) {
		this.lrry_dm = lrry_dm;
	}

	public String getXgry_dm() {
		return xgry_dm;
	}

	public void setXgry_dm(String xgry_dm) {
		this.xgry_dm = xgry_dm;
	}

	public String getLrjg_dm() {
		return lrjg_dm;
	}

	public void setLrjg_dm(String lrjg_dm) {
		this.lrjg_dm = lrjg_dm;
	}

	public String getXgjg_dm() {
		return xgjg_dm;
	}

	public void setXgjg_dm(String xgjg_dm) {
		this.xgjg_dm = xgjg_dm;
	}

	public String getFjgs() {
		return fjgs;
	}

	public void setFjgs(String fjgs) {
		this.fjgs = fjgs;
	}

	public String getFjdx() {
		return fjdx;
	}

	public void setFjdx(String fjdx) {
		this.fjdx = fjdx;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}



	
	
}
