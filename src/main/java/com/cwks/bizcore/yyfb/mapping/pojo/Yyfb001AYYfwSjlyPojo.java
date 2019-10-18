package com.cwks.bizcore.yyfb.mapping.pojo;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(A_YYFW_SJLY)
*/

public class Yyfb001AYYfwSjlyPojo {

    private static final long serialVersionUID = 1L;

    private  String fxyy_id; //FXYY_ID
    private  String lrrq; //录入时间
    private  String lrry_dm; //录入人员代码
    private  String sjly_dm; //SJLY_DM
    private  String sjly_mc; //SJLY_MC
    private  String xgrq; //修改时间
    private  String xgry_dm; //修改人员代码
    private  String xybj; //选用标记 1 选用 0 不选用
    private  String version; //选用标记 1 选用 0 不选用

    public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**无参构造方法**/
    public Yyfb001AYYfwSjlyPojo(){};

    /**带参构造方法*/
    public Yyfb001AYYfwSjlyPojo (String fxyy_id,String lrrq,String lrry_dm,String sjly_dm,String sjly_mc,String xgrq,String xgry_dm,String xybj){
        this.fxyy_id = fxyy_id;
        this.lrrq = lrrq;
        this.lrry_dm = lrry_dm;
        this.sjly_dm = sjly_dm;
        this.sjly_mc = sjly_mc;
        this.xgrq = xgrq;
        this.xgry_dm = xgry_dm;
        this.xybj = xybj;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public Yyfb001AYYfwSjlyPojo(Map data){
        this.fxyy_id = data.get("fxyy_id") == null
            ? null : (String)data.get("fxyy_id");
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrry_dm = data.get("lrry_dm") == null
            ? null : (String)data.get("lrry_dm");
        this.sjly_dm = data.get("sjly_dm") == null
            ? null : (String)data.get("sjly_dm");
        this.sjly_mc = data.get("sjly_mc") == null
            ? null : (String)data.get("sjly_mc");
        this.xgrq = data.get("xgrq") == null
            ? null : (String)data.get("xgrq");
        this.xgry_dm = data.get("xgry_dm") == null
            ? null : (String)data.get("xgry_dm");
        this.xybj = data.get("xybj") == null
            ? null : (String)data.get("xybj");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("fxyy_id",fxyy_id);
        map.put("lrrq",lrrq);
        map.put("lrry_dm",lrry_dm);
        map.put("sjly_dm",sjly_dm);
        map.put("sjly_mc",sjly_mc);
        map.put("xgrq",xgrq);
        map.put("xgry_dm",xgry_dm);
        map.put("xybj",xybj);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getFxyy_id() {
        return this.fxyy_id;
    }
    public void setFxyy_id(String fxyy_id) {
        this.fxyy_id = fxyy_id;
    }
    public String getLrrq() {
        return this.lrrq;
    }
    public void setLrrq(String lrrq) {
        this.lrrq = lrrq;
    }
    public String getLrry_dm() {
        return this.lrry_dm;
    }
    public void setLrry_dm(String lrry_dm) {
        this.lrry_dm = lrry_dm;
    }
    public String getSjly_dm() {
        return this.sjly_dm;
    }
    public void setSjly_dm(String sjly_dm) {
        this.sjly_dm = sjly_dm;
    }
    public String getSjly_mc() {
        return this.sjly_mc;
    }
    public void setSjly_mc(String sjly_mc) {
        this.sjly_mc = sjly_mc;
    }
    public String getXgrq() {
        return this.xgrq;
    }
    public void setXgrq(String xgrq) {
        this.xgrq = xgrq;
    }
    public String getXgry_dm() {
        return this.xgry_dm;
    }
    public void setXgry_dm(String xgry_dm) {
        this.xgry_dm = xgry_dm;
    }
    public String getXybj() {
        return this.xybj;
    }
    public void setXybj(String xybj) {
        this.xybj = xybj;
    }

}