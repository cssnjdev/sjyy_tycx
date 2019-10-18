package com.cwks.bizcore.yyfb.mapping.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * 对应表A_GY_FXYY
 * @author dinghr
 *
 */

public class YyfwXiangqingPojo {
	
	private static final long serialVersionUID = 1L;
	
	//版本号;
    private String banben;

    //发布人代码;
    private String fbrdm;

    //发布时间;
    private String fbsj;

    //分析应用类型代码;
    private String fxyylxdm;

    //分析应用ID;
    private String fxyyid;

    //分析应用名称;
    private String fxyymc;

    //关键字;
    private String gjz;

    //功能路径;
    private String gnlj;

    //功能描述;
    private String gnms;

    //技术口径;
    private String jskj;

    //开发单位代码;
    private String kfdwdm;

    //开发人员联系电话;
    private String kfrlxdh;

    //开发人员联系方式;
    private String kfrlxfs;

    //开发人员代码;
    private String kfrydm;

    //录入机关代码;
    private String lrjgdm;

    //录入人员代码;
    private String lrrydm;

    //;
    private String procedurename;

    //修改机关代码;
    private String xgjgdm;

    //修改人员代码;
    private String xgrydm;

    //需求单位代码;
    private String xqdwdm;

    //需求人员代码;
    private String xqrydm;

    //系统功能代码;
    private String xtgndm;

    //系统上级菜单;
    private String xtsjcd;

    //业务口径;
    private String ywkj;

    //有效标记：N 无效 用户不能使用，Y 有效 用户可以使用。;
    private String yxbj;

    //应用url;
    private String yyurl;

    //状态标记：0新建，1停用，2发布。;
    private String ztbj;

    /**无参构造函数**/
    public YyfwXiangqingPojo() {
    	
    }

    /**有参构造函数**/
	public YyfwXiangqingPojo(String banben, String fbrdm, String fbsj,
                             String fxyylxdm, String fxyyid, String fxyymc, String gjz,
                             String gnlj, String gnms, String jskj, String kfdwdm,
                             String kfrlxdh, String kfrlxfs, String kfrydm, String lrjgdm,
                             String lrrydm, String procedurename, String xgjgdm, String xgrydm,
                             String xqdwdm, String xqrydm, String xtgndm, String xtsjcd,
                             String ywkj, String yxbj, String yyurl, String ztbj) {
		this.banben = banben;
		this.fbrdm = fbrdm;
		this.fbsj = fbsj;
		this.fxyylxdm = fxyylxdm;
		this.fxyyid = fxyyid;
		this.fxyymc = fxyymc;
		this.gjz = gjz;
		this.gnlj = gnlj;
		this.gnms = gnms;
		this.jskj = jskj;
		this.kfdwdm = kfdwdm;
		this.kfrlxdh = kfrlxdh;
		this.kfrlxfs = kfrlxfs;
		this.kfrydm = kfrydm;
		this.lrjgdm = lrjgdm;
		this.lrrydm = lrrydm;
		this.procedurename = procedurename;
		this.xgjgdm = xgjgdm;
		this.xgrydm = xgrydm;
		this.xqdwdm = xqdwdm;
		this.xqrydm = xqrydm;
		this.xtgndm = xtgndm;
		this.xtsjcd = xtsjcd;
		this.ywkj = ywkj;
		this.yxbj = yxbj;
		this.yyurl = yyurl;
		this.ztbj = ztbj;
	}
	
	/**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
	public YyfwXiangqingPojo(Map data){
		this.banben = data.get("banben") == null ? null : (String)data.get("banben");
		this.fbrdm = data.get("fbrdm") == null ? null : (String)data.get("fbrdm");
		this.fbsj = null;
		this.fxyylxdm = data.get("fxyylxdm") == null ? null : (String)data.get("fxyylxdm");
		this.fxyyid = data.get("fxyyid") == null ? null : (String)data.get("fxyyid");
		this.fxyymc = data.get("fxyymc") == null ? null : (String)data.get("fxyymc");
		this.gjz = data.get("gjz") == null ? null : (String)data.get("gjz");
		this.gnlj = data.get("gnlj") == null ? null : (String)data.get("gnlj");
		this.gnms = data.get("gnms") == null ? null : (String)data.get("gnms");
		this.jskj = data.get("jskj") == null ? null : (String)data.get("jskj");
		this.kfdwdm = data.get("kfdwdm") == null ? null : (String)data.get("kfdwdm");
		this.kfrlxdh = data.get("kfrlxdh") == null ? null : (String)data.get("kfrlxdh");
		this.kfrlxfs = data.get("kfrlxfs") == null ? null : (String)data.get("kfrlxfs");
		this.kfrydm = data.get("kfrydm") == null ? null : (String)data.get("kfrydm");
		this.lrjgdm = data.get("lrjgdm") == null ? null : (String)data.get("lrjgdm");
		this.lrrydm = data.get("lrrydm") == null ? null : (String)data.get("lrrydm");
		this.procedurename = data.get("procedurename") == null ? null : (String)data.get("procedurename");
		this.xgjgdm = data.get("xgjgdm") == null ? null : (String)data.get("xgjgdm");
		this.xgrydm = data.get("xgrydm") == null ? null : (String)data.get("xgrydm");
		this.xqdwdm = data.get("xqdwdm") == null ? null : (String)data.get("xqdwdm");
		this.xqrydm = data.get("xqrydm") == null ? null : (String)data.get("xqrydm");
		this.xtgndm = data.get("xtgndm") == null ? null : (String)data.get("xtgndm");
		this.xtsjcd = data.get("xtsjcd") == null ? null : (String)data.get("xtsjcd");
		this.ywkj = data.get("ywkj") == null ? null : (String)data.get("ywkj");
		this.yxbj = data.get("yxbj") == null ? null : (String)data.get("yxbj");
		this.yyurl = data.get("yyurl") == null ? null : (String)data.get("yyurl");
		this.ztbj = data.get("ztbj") == null ? null : (String)data.get("ztbj");
	}
	
	/** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("banben",banben);
        map.put("fbrdm",fbrdm);
        map.put("fbsj",fbsj);
        map.put("fxyylxdm",fxyylxdm);
        map.put("fxyyid",fxyyid);
        map.put("fxyymc",fxyymc);
        map.put("gjz",gjz);
        map.put("gnlj",gnlj);
        map.put("gnms",gnms);
        map.put("jskj",jskj);
        map.put("kfdwdm",kfdwdm);
        map.put("kfrlxdh",kfrlxdh);
        map.put("kfrlxfs",kfrlxfs);
        map.put("kfrydm",kfrydm);
        map.put("lrjgdm",lrjgdm);
        map.put("lrrydm",lrrydm);
        map.put("procedurename",procedurename);
        map.put("xgjgdm",xgjgdm);
        map.put("xgrydm",xgrydm);
        map.put("xqdwdm",xqdwdm);
        map.put("xqrydm",xqrydm);
        map.put("xtgndm",xtgndm);
        map.put("xtsjcd",xtsjcd);
        map.put("ywkj",ywkj);
        map.put("yxbj",yxbj);
        map.put("yyurl",yyurl);
        map.put("ztbj",ztbj);
        return map;
    }

	public String getBanben() {
		return banben;
	}

	public void setBanben(String banben) {
		this.banben = banben;
	}

	public String getFbrdm() {
		return fbrdm;
	}

	public void setFbrdm(String fbrdm) {
		this.fbrdm = fbrdm;
	}

	public String getFbsj() {
		return fbsj;
	}

	public void setFbsj(String fbsj) {
		this.fbsj = fbsj;
	}

	public String getFxyylxdm() {
		return fxyylxdm;
	}

	public void setFxyylxdm(String fxyylxdm) {
		this.fxyylxdm = fxyylxdm;
	}

	public String getFxyyid() {
		return fxyyid;
	}

	public void setFxyyid(String fxyyid) {
		this.fxyyid = fxyyid;
	}

	public String getFxyymc() {
		return fxyymc;
	}

	public void setFxyymc(String fxyymc) {
		this.fxyymc = fxyymc;
	}

	public String getGjz() {
		return gjz;
	}

	public void setGjz(String gjz) {
		this.gjz = gjz;
	}

	public String getGnlj() {
		return gnlj;
	}

	public void setGnlj(String gnlj) {
		this.gnlj = gnlj;
	}

	public String getGnms() {
		return gnms;
	}

	public void setGnms(String gnms) {
		this.gnms = gnms;
	}

	public String getJskj() {
		return jskj;
	}

	public void setJskj(String jskj) {
		this.jskj = jskj;
	}

	public String getKfdwdm() {
		return kfdwdm;
	}

	public void setKfdwdm(String kfdwdm) {
		this.kfdwdm = kfdwdm;
	}

	public String getKfrlxdh() {
		return kfrlxdh;
	}

	public void setKfrlxdh(String kfrlxdh) {
		this.kfrlxdh = kfrlxdh;
	}

	public String getKfrlxfs() {
		return kfrlxfs;
	}

	public void setKfrlxfs(String kfrlxfs) {
		this.kfrlxfs = kfrlxfs;
	}

	public String getKfrydm() {
		return kfrydm;
	}

	public void setKfrydm(String kfrydm) {
		this.kfrydm = kfrydm;
	}

	public String getLrjgdm() {
		return lrjgdm;
	}

	public void setLrjgdm(String lrjgdm) {
		this.lrjgdm = lrjgdm;
	}

	public String getLrrydm() {
		return lrrydm;
	}

	public void setLrrydm(String lrrydm) {
		this.lrrydm = lrrydm;
	}

	public String getProcedurename() {
		return procedurename;
	}

	public void setProcedurename(String procedurename) {
		this.procedurename = procedurename;
	}

	public String getXgjgdm() {
		return xgjgdm;
	}

	public void setXgjgdm(String xgjgdm) {
		this.xgjgdm = xgjgdm;
	}

	public String getXgrydm() {
		return xgrydm;
	}

	public void setXgrydm(String xgrydm) {
		this.xgrydm = xgrydm;
	}

	public String getXqdwdm() {
		return xqdwdm;
	}

	public void setXqdwdm(String xqdwdm) {
		this.xqdwdm = xqdwdm;
	}

	public String getXqrydm() {
		return xqrydm;
	}

	public void setXqrydm(String xqrydm) {
		this.xqrydm = xqrydm;
	}

	public String getXtgndm() {
		return xtgndm;
	}

	public void setXtgndm(String xtgndm) {
		this.xtgndm = xtgndm;
	}

	public String getXtsjcd() {
		return xtsjcd;
	}

	public void setXtsjcd(String xtsjcd) {
		this.xtsjcd = xtsjcd;
	}

	public String getYwkj() {
		return ywkj;
	}

	public void setYwkj(String ywkj) {
		this.ywkj = ywkj;
	}

	public String getYxbj() {
		return yxbj;
	}

	public void setYxbj(String yxbj) {
		this.yxbj = yxbj;
	}

	public String getYyurl() {
		return yyurl;
	}

	public void setYyurl(String yyurl) {
		this.yyurl = yyurl;
	}

	public String getZtbj() {
		return ztbj;
	}

	public void setZtbj(String ztbj) {
		this.ztbj = ztbj;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    
}
















