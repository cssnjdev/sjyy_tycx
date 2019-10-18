package com.cwks.bizcore.sys.core.utils.vo;

import java.io.Serializable;

/**
 * <p>File: UserInfo.java</p>
 * <p>Title: 移动端用户对象</p>
 * <p>Description: 移动端用户对象</p>
 * @author H.R
 * @version 1.0
 */
public class UserInfo implements Serializable{

	//用户登录账号
	private String dl_account;
	//用户登录电话
	private String dl_tel;
	//用户登录邮箱
	private String dl_email;
	//用户登录身份证号
	private String dl_idcard;
	//用户登录支付宝认证ID
	private String dl_alipay;
	//用户登录微信ID
	private String dl_weixin;
	//用户登录QQ号
	private String dl_qq;
	//用户代码
	private String yhdm;
	//用户注册时间
	private String zcsj;
	//机关或部门代码
	private String jgbm_dm;
	//token
	private String token;
	//自动登录标记
	private String nologinflag;
	//用户状态
	private String yhzt;
	//人员身份代码
	private String rysfdm;
	//人员身份名称
	private String rysfmc;
	//岗位代码
	private String gw_dm;
	//岗位名称
	private String gw_mc;
	//所属机构代码
	private String jg_dm;
	//所属机构名称
	private String jg_mc;
	//姓名
	private String xm;
	//昵称
	private String nc;
	//性别代码
	private String xb;
	//住址
	private String dz;
	//联系电话
	private String lxdh;
	//联系邮件
	private String email;
	//用户备注
	private String bz;

	//用户当前设备id
	private String deviceid;
	//用户当前设备信息-设备型号
	private String d_model;
	//用户当前设备信息-设备厂商
	private String d_vendor;
	//用户当前设备信息-设备UUID
	private String d_uuid;
	//用户当前设备信息-设备IMSI
	private String d_imsi;
	//用户当前设备信息-设备IMEI
	private String d_imei;
	//用户当前设备信息-屏幕分辨率
	private String d_resolution;
	//用户当前设备信息-屏幕DPI
	private String d_dpi;
	//用户当前设备信息-系统名称
	private String os_name;
	//用户当前设备信息-系统版本
	private String os_version;
	//用户当前设备信息-系统语言
	private String os_language;
	//用户当前设备信息-系统厂商
	private String os_vendor;


	public String getJg_dm() {
		return jg_dm;
	}

	public void setJg_dm(String jg_dm) {
		this.jg_dm = jg_dm;
	}

	public String getJg_mc() {
		return jg_mc;
	}

	public void setJg_mc(String jg_mc) {
		this.jg_mc = jg_mc;
	}

	public String getDl_alipay() {
		return dl_alipay;
	}

	public void setDl_alipay(String dl_alipay) {
		this.dl_alipay = dl_alipay;
	}

	public String getDl_weixin() {
		return dl_weixin;
	}

	public void setDl_weixin(String dl_weixin) {
		this.dl_weixin = dl_weixin;
	}

	public String getDl_qq() {
		return dl_qq;
	}

	public void setDl_qq(String dl_qq) {
		this.dl_qq = dl_qq;
	}

	public String getDl_idcard() {
		return dl_idcard;
	}

	public void setDl_idcard(String dl_idcard) {
		this.dl_idcard = dl_idcard;
	}

	public String getD_model() {
		return d_model;
	}

	public void setD_model(String d_model) {
		this.d_model = d_model;
	}

	public String getD_vendor() {
		return d_vendor;
	}

	public void setD_vendor(String d_vendor) {
		this.d_vendor = d_vendor;
	}

	public String getD_uuid() {
		return d_uuid;
	}

	public void setD_uuid(String d_uuid) {
		this.d_uuid = d_uuid;
	}

	public String getD_imsi() {
		return d_imsi;
	}

	public void setD_imsi(String d_imsi) {
		this.d_imsi = d_imsi;
	}

	public String getD_imei() {
		return d_imei;
	}

	public void setD_imei(String d_imei) {
		this.d_imei = d_imei;
	}

	public String getD_resolution() {
		return d_resolution;
	}

	public void setD_resolution(String d_resolution) {
		this.d_resolution = d_resolution;
	}

	public String getD_dpi() {
		return d_dpi;
	}

	public void setD_dpi(String d_dpi) {
		this.d_dpi = d_dpi;
	}

	public String getOs_name() {
		return os_name;
	}

	public void setOs_name(String os_name) {
		this.os_name = os_name;
	}

	public String getOs_version() {
		return os_version;
	}

	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}

	public String getOs_language() {
		return os_language;
	}

	public void setOs_language(String os_language) {
		this.os_language = os_language;
	}

	public String getOs_vendor() {
		return os_vendor;
	}

	public void setOs_vendor(String os_vendor) {
		this.os_vendor = os_vendor;
	}

	public String getZcsj() {
		return zcsj;
	}

	public void setZcsj(String zcsj) {
		this.zcsj = zcsj;
	}
    public String getDl_account() {
		return dl_account;
	}

	public void setDl_account(String dl_account) {
		this.dl_account = dl_account;
	}

	public String getDl_tel() {
		return dl_tel;
	}

	public void setDl_tel(String dl_tel) {
		this.dl_tel = dl_tel;
	}

	public String getDl_email() {
		return dl_email;
	}

	public void setDl_email(String dl_email) {
		this.dl_email = dl_email;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNologinflag() {
		return nologinflag;
	}

	public void setNologinflag(String nologinflag) {
		this.nologinflag = nologinflag;
	}

	public String getYhzt() {
		return yhzt;
	}

	public void setYhzt(String yhzt) {
		this.yhzt = yhzt;
	}

	public String getRysfdm() {
		return rysfdm;
	}

	public void setRysfdm(String rysfdm) {
		this.rysfdm = rysfdm;
	}

	public String getRysfmc() {
		return rysfmc;
	}

	public void setRysfmc(String rysfmc) {
		this.rysfmc = rysfmc;
	}

	public String getGw_dm() {
		return gw_dm;
	}

	public void setGw_dm(String gw_dm) {
		this.gw_dm = gw_dm;
	}

	public String getGw_mc() {
		return gw_mc;
	}

	public void setGw_mc(String gw_mc) {
		this.gw_mc = gw_mc;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getNc() {
		return nc;
	}

	public void setNc(String nc) {
		this.nc = nc;
	}

	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public String getDz() {
		return dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public UserInfo(){
        super();
    }

}
