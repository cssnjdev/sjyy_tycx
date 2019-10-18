package com.cwks.bizsys.xtgl.domain;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(T_XT_GNS)
*/

public class TXtGnsPojo {

    private static final long serialVersionUID = 1L;

    private  String cdlx; //菜单类型   0 目录 1 菜单
    private  String cdxh; //菜单序号
    private  String gn_cj; //功能层级
    private  String gn_dm; //功能代码
    private  String gn_jc; //功能简称
    private  String gn_mc; //功能名称
    private  String gn_url; //功能url路径
    private  String icon; //菜单图标
    private  String lr_sj; //录入时间
    private  String opentype; //打开方式       1tab   0弹
    private  String sjgn_dm; //上级功能代码
    private  String xg_sj; //修改时间
    private  String xy_bj; //选用标记 1选用 0停用
    private  String yx_bj; //有效标记 1有效 0 无效

    /**无参构造方法**/
    public TXtGnsPojo(){};

    /**带参构造方法*/
    public TXtGnsPojo (String cdlx,String cdxh,String gn_cj,String gn_dm,String gn_jc,String gn_mc,String gn_url,String icon,String lr_sj,String opentype,String sjgn_dm,String xg_sj,String xy_bj,String yx_bj){
        this.cdlx = cdlx;
        this.cdxh = cdxh;
        this.gn_cj = gn_cj;
        this.gn_dm = gn_dm;
        this.gn_jc = gn_jc;
        this.gn_mc = gn_mc;
        this.gn_url = gn_url;
        this.icon = icon;
        this.lr_sj = lr_sj;
        this.opentype = opentype;
        this.sjgn_dm = sjgn_dm;
        this.xg_sj = xg_sj;
        this.xy_bj = xy_bj;
        this.yx_bj = yx_bj;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public TXtGnsPojo(Map data){
        this.cdlx = data.get("cdlx") == null
            ? null : (String)data.get("cdlx");
        this.cdxh = data.get("cdxh") == null
            ? null : (String)data.get("cdxh");
        this.gn_cj = data.get("gn_cj") == null
            ? null : (String)data.get("gn_cj");
        this.gn_dm = data.get("gn_dm") == null
            ? null : (String)data.get("gn_dm");
        this.gn_jc = data.get("gn_jc") == null
            ? null : (String)data.get("gn_jc");
        this.gn_mc = data.get("gn_mc") == null
            ? null : (String)data.get("gn_mc");
        this.gn_url = data.get("gn_url") == null
            ? null : (String)data.get("gn_url");
        this.icon = data.get("icon") == null
            ? null : (String)data.get("icon");
        this.lr_sj = data.get("lr_sj") == null
            ? null : (String)data.get("lr_sj");
        this.opentype = data.get("opentype") == null
            ? null : (String)data.get("opentype");
        this.sjgn_dm = data.get("sjgn_dm") == null
            ? null : (String)data.get("sjgn_dm");
        this.xg_sj = data.get("xg_sj") == null
            ? null : (String)data.get("xg_sj");
        this.xy_bj = data.get("xy_bj") == null
            ? null : (String)data.get("xy_bj");
        this.yx_bj = data.get("yx_bj") == null
            ? null : (String)data.get("yx_bj");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("cdlx",cdlx);
        map.put("cdxh",cdxh);
        map.put("gn_cj",gn_cj);
        map.put("gn_dm",gn_dm);
        map.put("gn_jc",gn_jc);
        map.put("gn_mc",gn_mc);
        map.put("gn_url",gn_url);
        map.put("icon",icon);
        map.put("lr_sj",lr_sj);
        map.put("opentype",opentype);
        map.put("sjgn_dm",sjgn_dm);
        map.put("xg_sj",xg_sj);
        map.put("xy_bj",xy_bj);
        map.put("yx_bj",yx_bj);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getCdlx() {
        return this.cdlx;
    }
    public void setCdlx(String cdlx) {
        this.cdlx = cdlx;
    }
    public String getCdxh() {
        return this.cdxh;
    }
    public void setCdxh(String cdxh) {
        this.cdxh = cdxh;
    }
    public String getGn_cj() {
        return this.gn_cj;
    }
    public void setGn_cj(String gn_cj) {
        this.gn_cj = gn_cj;
    }
    public String getGn_dm() {
        return this.gn_dm;
    }
    public void setGn_dm(String gn_dm) {
        this.gn_dm = gn_dm;
    }
    public String getGn_jc() {
        return this.gn_jc;
    }
    public void setGn_jc(String gn_jc) {
        this.gn_jc = gn_jc;
    }
    public String getGn_mc() {
        return this.gn_mc;
    }
    public void setGn_mc(String gn_mc) {
        this.gn_mc = gn_mc;
    }
    public String getGn_url() {
        return this.gn_url;
    }
    public void setGn_url(String gn_url) {
        this.gn_url = gn_url;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getLr_sj() {
        return this.lr_sj;
    }
    public void setLr_sj(String lr_sj) {
        this.lr_sj = lr_sj;
    }
    public String getOpentype() {
        return this.opentype;
    }
    public void setOpentype(String opentype) {
        this.opentype = opentype;
    }
    public String getSjgn_dm() {
        return this.sjgn_dm;
    }
    public void setSjgn_dm(String sjgn_dm) {
        this.sjgn_dm = sjgn_dm;
    }
    public String getXg_sj() {
        return this.xg_sj;
    }
    public void setXg_sj(String xg_sj) {
        this.xg_sj = xg_sj;
    }
    public String getXy_bj() {
        return this.xy_bj;
    }
    public void setXy_bj(String xy_bj) {
        this.xy_bj = xy_bj;
    }
    public String getYx_bj() {
        return this.yx_bj;
    }
    public void setYx_bj(String yx_bj) {
        this.yx_bj = yx_bj;
    }

}
