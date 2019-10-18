package com.cwks.bizcore.yyfb.mapping.pojo;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(A_GY_FXYY)
*/

public class FxyyPojo {

    private static final long serialVersionUID = 1L;

    private  String banben; //版本号
    private  String fbrdm; //发布人代码
    private  String fb_sj; //发布时间
    private  String fxyylx_dm; //分析应用类型代码
    private  String fxyy_id; //分析应用ID
    private  String fxyy_mc; //分析应用名称
    private  String gjz; //关键字
    private  String gnlj; //功能路径
    private  String gnms; //功能描述
    private  String jskj; //技术口径
    private  String kfdw_dm; //开发单位代码
    private  String kfrlxdh; //开发人员联系电话
    private  String kfrlxfs; //开发人员联系方式
    private  String kfry_dm; //开发人员代码
    private  String lrjg_dm; //录入机关代码
    private  String lrry_dm; //录入人员代码
    private  String lr_sj; //录入时间
    private  String procedure_name; //PROCEDURE_NAME
    private  String xgjg_dm; //修改机关代码
    private  String xgry_dm; //修改人员代码
    private  String xg_sj; //修改时间
    private  String xqdw_dm; //需求单位代码
    private  String xqry_dm; //需求人员代码
    private  String xtgndm; //系统功能代码
    private  String xtsjcd; //系统上级菜单
    private  String ywkj; //业务口径
    private  String yxbj; //有效标记：N 无效 用户不能使用，Y 有
    private  String yyurl; //应用url
    private  String zt_bj; //状态标记：0新建，1发布,2停用。

    private  String gy_valid; //是否支持概要展现
	private  String gyurl; 	  //概要展现url

	/**无参构造方法**/
    public FxyyPojo(){};

    /**带参构造方法*/
    public FxyyPojo (String banben,String fbrdm,String fb_sj,String fxyylx_dm,String fxyy_id,String fxyy_mc,
    		String gjz,String gnlj,String gnms,String jskj,String kfdw_dm,String kfrlxdh,String kfrlxfs,
    		String kfry_dm,String lrjg_dm,String lrry_dm,String lr_sj,String procedure_name,String xgjg_dm,
    		String xgry_dm,String xg_sj,String xqdw_dm,String xqry_dm,String xtgndm,String xtsjcd,String ywkj,
    		String yxbj,String yyurl,String zt_bj,String gy_valid,String gyurl
    	){
    	
        this.banben = banben;
        this.fbrdm = fbrdm;
        this.fb_sj = fb_sj;
        this.fxyylx_dm = fxyylx_dm;
        this.fxyy_id = fxyy_id;
        this.fxyy_mc = fxyy_mc;
        this.gjz = gjz;
        this.gnlj = gnlj;
        this.gnms = gnms;
        this.jskj = jskj;
        this.kfdw_dm = kfdw_dm;
        this.kfrlxdh = kfrlxdh;
        this.kfrlxfs = kfrlxfs;
        this.kfry_dm = kfry_dm;
        this.lrjg_dm = lrjg_dm;
        this.lrry_dm = lrry_dm;
        this.lr_sj = lr_sj;
        this.procedure_name = procedure_name;
        this.xgjg_dm = xgjg_dm;
        this.xgry_dm = xgry_dm;
        this.xg_sj = xg_sj;
        this.xqdw_dm = xqdw_dm;
        this.xqry_dm = xqry_dm;
        this.xtgndm = xtgndm;
        this.xtsjcd = xtsjcd;
        this.ywkj = ywkj;
        this.yxbj = yxbj;
        this.yyurl = yyurl;
        this.zt_bj = zt_bj;
        
        this.gy_valid = gy_valid;
        this.gyurl = gyurl;
        
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public FxyyPojo(Map data){
        this.banben = data.get("banben") == null
            ? null : (String)data.get("banben");
        this.fbrdm = data.get("fbrdm") == null
            ? null : (String)data.get("fbrdm");
        this.fb_sj = data.get("fb_sj") == null
            ? null : (String)data.get("fb_sj");
        this.fxyylx_dm = data.get("fxyylx_dm") == null
            ? null : (String)data.get("fxyylx_dm");
        this.fxyy_id = data.get("fxyy_id") == null
            ? null : (String)data.get("fxyy_id");
        this.fxyy_mc = data.get("fxyy_mc") == null
            ? null : (String)data.get("fxyy_mc");
        this.gjz = data.get("gjz") == null
            ? null : (String)data.get("gjz");
        this.gnlj = data.get("gnlj") == null
            ? null : (String)data.get("gnlj");
        this.gnms = data.get("gnms") == null
            ? null : (String)data.get("gnms");
        this.jskj = data.get("jskj") == null
            ? null : (String)data.get("jskj");
        this.kfdw_dm = data.get("kfdw_dm") == null
            ? null : (String)data.get("kfdw_dm");
        this.kfrlxdh = data.get("kfrlxdh") == null
            ? null : (String)data.get("kfrlxdh");
        this.kfrlxfs = data.get("kfrlxfs") == null
            ? null : (String)data.get("kfrlxfs");
        this.kfry_dm = data.get("kfry_dm") == null
            ? null : (String)data.get("kfry_dm");
        this.lrjg_dm = data.get("lrjg_dm") == null
            ? null : (String)data.get("lrjg_dm");
        this.lrry_dm = data.get("lrry_dm") == null
            ? null : (String)data.get("lrry_dm");
        this.lr_sj = data.get("lr_sj") == null
            ? null : (String)data.get("lr_sj");
        this.procedure_name = data.get("procedure_name") == null
            ? null : (String)data.get("procedure_name");
        this.xgjg_dm = data.get("xgjg_dm") == null
            ? null : (String)data.get("xgjg_dm");
        this.xgry_dm = data.get("xgry_dm") == null
            ? null : (String)data.get("xgry_dm");
        this.xg_sj = data.get("xg_sj") == null
            ? null : (String)data.get("xg_sj");
        this.xqdw_dm = data.get("xqdw_dm") == null
            ? null : (String)data.get("xqdw_dm");
        this.xqry_dm = data.get("xqry_dm") == null
            ? null : (String)data.get("xqry_dm");
        this.xtgndm = data.get("xtgndm") == null
            ? null : (String)data.get("xtgndm");
        this.xtsjcd = data.get("xtsjcd") == null
            ? null : (String)data.get("xtsjcd");
        this.ywkj = data.get("ywkj") == null
            ? null : (String)data.get("ywkj");
        this.yxbj = data.get("yxbj") == null
            ? null : (String)data.get("yxbj");
        this.yyurl = data.get("yyurl") == null
            ? null : (String)data.get("yyurl");
        this.zt_bj = data.get("zt_bj") == null
            ? null : (String)data.get("zt_bj");
        
        this.gy_valid = data.get("gy_valid") == null
                ? null : (String)data.get("gy_valid");
        
        this.gyurl = data.get("gyurl") == null
                ? null : (String)data.get("gyurl");
        
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("banben",banben);
        map.put("fbrdm",fbrdm);
        map.put("fb_sj",fb_sj);
        map.put("fxyylx_dm",fxyylx_dm);
        map.put("fxyy_id",fxyy_id);
        map.put("fxyy_mc",fxyy_mc);
        map.put("gjz",gjz);
        map.put("gnlj",gnlj);
        map.put("gnms",gnms);
        map.put("jskj",jskj);
        map.put("kfdw_dm",kfdw_dm);
        map.put("kfrlxdh",kfrlxdh);
        map.put("kfrlxfs",kfrlxfs);
        map.put("kfry_dm",kfry_dm);
        map.put("lrjg_dm",lrjg_dm);
        map.put("lrry_dm",lrry_dm);
        map.put("lr_sj",lr_sj);
        map.put("procedure_name",procedure_name);
        map.put("xgjg_dm",xgjg_dm);
        map.put("xgry_dm",xgry_dm);
        map.put("xg_sj",xg_sj);
        map.put("xqdw_dm",xqdw_dm);
        map.put("xqry_dm",xqry_dm);
        map.put("xtgndm",xtgndm);
        map.put("xtsjcd",xtsjcd);
        map.put("ywkj",ywkj);
        map.put("yxbj",yxbj);
        map.put("yyurl",yyurl);
        map.put("zt_bj",zt_bj);
        
        map.put("gy_valid",gy_valid);
        map.put("gyurl",gyurl);
        
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getBanben() {
        return this.banben;
    }
    public void setBanben(String banben) {
        this.banben = banben;
    }
    public String getFbrdm() {
        return this.fbrdm;
    }
    public void setFbrdm(String fbrdm) {
        this.fbrdm = fbrdm;
    }
    public String getFb_sj() {
        return this.fb_sj;
    }
    public void setFb_sj(String fb_sj) {
        this.fb_sj = fb_sj;
    }
    public String getFxyylx_dm() {
        return this.fxyylx_dm;
    }
    public void setFxyylx_dm(String fxyylx_dm) {
        this.fxyylx_dm = fxyylx_dm;
    }
    public String getFxyy_id() {
        return this.fxyy_id;
    }
    public void setFxyy_id(String fxyy_id) {
        this.fxyy_id = fxyy_id;
    }
    public String getFxyy_mc() {
        return this.fxyy_mc;
    }
    public void setFxyy_mc(String fxyy_mc) {
        this.fxyy_mc = fxyy_mc;
    }
    public String getGjz() {
        return this.gjz;
    }
    public void setGjz(String gjz) {
        this.gjz = gjz;
    }
    public String getGnlj() {
        return this.gnlj;
    }
    public void setGnlj(String gnlj) {
        this.gnlj = gnlj;
    }
    public String getGnms() {
        return this.gnms;
    }
    public void setGnms(String gnms) {
        this.gnms = gnms;
    }
    public String getJskj() {
        return this.jskj;
    }
    public void setJskj(String jskj) {
        this.jskj = jskj;
    }
    public String getKfdw_dm() {
        return this.kfdw_dm;
    }
    public void setKfdw_dm(String kfdw_dm) {
        this.kfdw_dm = kfdw_dm;
    }
    public String getKfrlxdh() {
        return this.kfrlxdh;
    }
    public void setKfrlxdh(String kfrlxdh) {
        this.kfrlxdh = kfrlxdh;
    }
    public String getKfrlxfs() {
        return this.kfrlxfs;
    }
    public void setKfrlxfs(String kfrlxfs) {
        this.kfrlxfs = kfrlxfs;
    }
    public String getKfry_dm() {
        return this.kfry_dm;
    }
    public void setKfry_dm(String kfry_dm) {
        this.kfry_dm = kfry_dm;
    }
    public String getLrjg_dm() {
        return this.lrjg_dm;
    }
    public void setLrjg_dm(String lrjg_dm) {
        this.lrjg_dm = lrjg_dm;
    }
    public String getLrry_dm() {
        return this.lrry_dm;
    }
    public void setLrry_dm(String lrry_dm) {
        this.lrry_dm = lrry_dm;
    }
    public String getLr_sj() {
        return this.lr_sj;
    }
    public void setLr_sj(String lr_sj) {
        this.lr_sj = lr_sj;
    }
    public String getProcedure_name() {
        return this.procedure_name;
    }
    public void setProcedure_name(String procedure_name) {
        this.procedure_name = procedure_name;
    }
    public String getXgjg_dm() {
        return this.xgjg_dm;
    }
    public void setXgjg_dm(String xgjg_dm) {
        this.xgjg_dm = xgjg_dm;
    }
    public String getXgry_dm() {
        return this.xgry_dm;
    }
    public void setXgry_dm(String xgry_dm) {
        this.xgry_dm = xgry_dm;
    }
    public String getXg_sj() {
        return this.xg_sj;
    }
    public void setXg_sj(String xg_sj) {
        this.xg_sj = xg_sj;
    }
    public String getXqdw_dm() {
        return this.xqdw_dm;
    }
    public void setXqdw_dm(String xqdw_dm) {
        this.xqdw_dm = xqdw_dm;
    }
    public String getXqry_dm() {
        return this.xqry_dm;
    }
    public void setXqry_dm(String xqry_dm) {
        this.xqry_dm = xqry_dm;
    }
    public String getXtgndm() {
        return this.xtgndm;
    }
    public void setXtgndm(String xtgndm) {
        this.xtgndm = xtgndm;
    }
    public String getXtsjcd() {
        return this.xtsjcd;
    }
    public void setXtsjcd(String xtsjcd) {
        this.xtsjcd = xtsjcd;
    }
    public String getYwkj() {
        return this.ywkj;
    }
    public void setYwkj(String ywkj) {
        this.ywkj = ywkj;
    }
    public String getYxbj() {
        return this.yxbj;
    }
    public void setYxbj(String yxbj) {
        this.yxbj = yxbj;
    }
    public String getYyurl() {
        return this.yyurl;
    }
    public void setYyurl(String yyurl) {
        this.yyurl = yyurl;
    }
    public String getZt_bj() {
        return this.zt_bj;
    }
    public void setZt_bj(String zt_bj) {
        this.zt_bj = zt_bj;
    }

    public String getGy_valid() {
		return gy_valid;
	}

	public void setGy_valid(String gy_valid) {
		this.gy_valid = gy_valid;
	}

	public String getGyurl() {
		return gyurl;
	}

	public void setGyurl(String gyurl) {
		this.gyurl = gyurl;
	}
    
}