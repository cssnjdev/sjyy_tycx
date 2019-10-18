package com.cwks.bizcore.sys.core.utils.vo;

import java.io.Serializable;

/**
 * <p>File: GnSylog.java</p>
 * <p>Title: 功能使用对象</p>
 * <p>Description: 功能使用对象</p>
 * @author H.R
 * @version 1.0
 */
public class GnSylog implements Serializable{

	//日志序号
	private String xh;
	//功能代码
	private String gndm;
	//用户代码
	private String yhdm;
	//机关或部门代码
	private String jgbm_dm;
	//操作内容
	private String cznr;
	//操作时间
	private String czsj;
	//离开时间
	private String lksj;
	//登录设备ID
	private String deviceid;
	//登录设备token
	private String token;

    public GnSylog(){
        super();
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getGndm() {
		return gndm;
	}

	public void setGndm(String gndm) {
		this.gndm = gndm;
	}

	public String getYhdm() {
		return yhdm;
	}

	public void setYhdm(String yhdm) {
		this.yhdm = yhdm;
	}

	public String getJgbm_dm() {
		return jgbm_dm;
	}

	public void setJgbm_dm(String jgbm_dm) {
		this.jgbm_dm = jgbm_dm;
	}

	public String getCznr() {
		return cznr;
	}

	public void setCznr(String cznr) {
		this.cznr = cznr;
	}

	public String getCzsj() {
		return czsj;
	}

	public void setCzsj(String czsj) {
		this.czsj = czsj;
	}

	public String getLksj() {
		return lksj;
	}

	public void setLksj(String lksj) {
		this.lksj = lksj;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}


}
