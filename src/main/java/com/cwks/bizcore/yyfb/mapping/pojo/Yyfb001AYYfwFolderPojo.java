package com.cwks.bizcore.yyfb.mapping.pojo;

import com.cwks.bizcore.tycx.core.utils.TycxUtils;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(A_YYFW_FOLDER)
*/

public class Yyfb001AYYfwFolderPojo {

    private static final long serialVersionUID = 1L;

    private  String folderlx_dm; //节点类型
    private  String folder_id; //用户自定义节点ID
    private  String lrry_dm; //录入人员代码
    private  String lr_sj; //录入时间
    private  String mc; //用户自定义节点名称
    private  String mc_j; //用户自定义节点简称
    private  String pfolder_id; //用户自定义节点ID上级节点ID
    private  Double pxxh; //排序序号
    private  String ssswjg_dm; //所属税务机关代码
    private  String xgry_dm; //修改人员代码
    private  String xg_sj; //修改时间
    private  String xybj; //选用标记 1 选用 0 不选用

    /**无参构造方法**/
    public Yyfb001AYYfwFolderPojo(){};

    /**带参构造方法*/
    public Yyfb001AYYfwFolderPojo (String folderlx_dm,String folder_id,String lrry_dm,String lr_sj,String mc,String mc_j,String pfolder_id,Double pxxh,String ssswjg_dm,String xgry_dm,String xg_sj,String xybj){
        this.folderlx_dm = folderlx_dm;
        this.folder_id = folder_id;
        this.lrry_dm = lrry_dm;
        this.lr_sj = lr_sj;
        this.mc = mc;
        this.mc_j = mc_j;
        this.pfolder_id = pfolder_id;
        this.pxxh = pxxh;
        this.ssswjg_dm = ssswjg_dm;
        this.xgry_dm = xgry_dm;
        this.xg_sj = xg_sj;
        this.xybj = xybj;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public Yyfb001AYYfwFolderPojo(Map data){
        this.folderlx_dm = data.get("folderlx_dm") == null
            ? null : (String)data.get("folderlx_dm");
        this.folder_id = data.get("folder_id") == null
            ? null : (String)data.get("folder_id");
        this.lrry_dm = data.get("lrry_dm") == null
            ? null : (String)data.get("lrry_dm");
        this.lr_sj = data.get("lr_sj") == null
            ? null : (String)data.get("lr_sj");
        this.mc = data.get("mc") == null
            ? null : (String)data.get("mc");
        this.mc_j = data.get("mc_j") == null
            ? null : (String)data.get("mc_j");
        this.pfolder_id = data.get("pfolder_id") == null
            ? null : (String)data.get("pfolder_id");
        this.pxxh = TycxUtils.isEmpty(data.get("pxxh")) ? null : Double.parseDouble((String)data.get("pxxh"));
        this.ssswjg_dm = data.get("ssswjg_dm") == null
            ? null : (String)data.get("ssswjg_dm");
        this.xgry_dm = data.get("xgry_dm") == null
            ? null : (String)data.get("xgry_dm");
        this.xg_sj = data.get("xg_sj") == null
            ? null : (String)data.get("xg_sj");
        this.xybj = data.get("xybj") == null
            ? null : (String)data.get("xybj");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("folderlx_dm",folderlx_dm);
        map.put("folder_id",folder_id);
        map.put("lrry_dm",lrry_dm);
        map.put("lr_sj",lr_sj);
        map.put("mc",mc);
        map.put("mc_j",mc_j);
        map.put("pfolder_id",pfolder_id);
        map.put("pxxh",pxxh);
        map.put("ssswjg_dm",ssswjg_dm);
        map.put("xgry_dm",xgry_dm);
        map.put("xg_sj",xg_sj);
        map.put("xybj",xybj);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getFolderlx_dm() {
        return this.folderlx_dm;
    }
    public void setFolderlx_dm(String folderlx_dm) {
        this.folderlx_dm = folderlx_dm;
    }
    public String getFolder_id() {
        return this.folder_id;
    }
    public void setFolder_id(String folder_id) {
        this.folder_id = folder_id;
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
    public String getMc() {
        return this.mc;
    }
    public void setMc(String mc) {
        this.mc = mc;
    }
    public String getMc_j() {
        return this.mc_j;
    }
    public void setMc_j(String mc_j) {
        this.mc_j = mc_j;
    }
    public String getPfolder_id() {
        return this.pfolder_id;
    }
    public void setPfolder_id(String pfolder_id) {
        this.pfolder_id = pfolder_id;
    }
    public Double getPxxh() {
        return this.pxxh;
    }
    public void setPxxh(Double pxxh) {
        this.pxxh = pxxh;
    }
    public String getSsswjg_dm() {
        return this.ssswjg_dm;
    }
    public void setSsswjg_dm(String ssswjg_dm) {
        this.ssswjg_dm = ssswjg_dm;
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
    public String getXybj() {
        return this.xybj;
    }
    public void setXybj(String xybj) {
        this.xybj = xybj;
    }

}