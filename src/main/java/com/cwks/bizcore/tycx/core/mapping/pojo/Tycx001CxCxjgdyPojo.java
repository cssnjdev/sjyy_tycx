package com.cwks.bizcore.tycx.core.mapping.pojo;

import com.cwks.bizcore.tycx.core.utils.TycxUtils;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(CX_CXJGDY)
*/

public class Tycx001CxCxjgdyPojo {

    private static final long serialVersionUID = 1L;

    private  String dmsql; //DMSQL
    private  String dqfs; //DQFS
    private  String dyghbbj; //DYGHBBJ
    private  String glbj; //GLBJ
    private  String jcbzdlx; //JCBZDLX
    private  String kzlx; //KZLX
    private  String lbm; //LBM
    private  String lkd; //LKD
    private  String llx; //LLX
    private  String lmc; //LMC
    private  String lms; //LMS
    private  String lrrq; //LRRQ
    private  String lrr_dm; //LRR_DM
    private  String mbbz; //MBBZ
    private  String sdbj; //SDBJ
    private  String sjgsdq; //SJGSDQ
    private  String sjlmc; //SJLMC
    private  String sqlxh; //SQLXH
    private  String tjlx; //TJLX
    private  String url; //URL
    private  String uuid; //UUID
    private  String xgrq; //XGRQ
    private  String xgr_dm; //XGR_DM
    private  Double xh; //XH
    private  String xsgs; //XSGS
    private  Double xsxh; //XSXH
    private  String xzcs; //XZCS
    private  String ycbj; //YCBJ
    private  String zsfs; //ZSFS
    private  String tzfs;//跳转方式
    private  String yjfw;//预警范围
    private  String urlmc;//url名称
    private  String jskj;//技术口径
    private  String ywkj;//业务口径
    private  String color;//预警颜色
    private  String zdysql;
    private  String zdybj;
    
	public String getZdybj() {
		return zdybj;
	}

	public void setZdybj(String zdybj) {
		this.zdybj = zdybj;
	}

	public String getZdysql() {
		return zdysql;
	}

	public void setZdysql(String zdysql) {
		this.zdysql = zdysql;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getJskj() {
		return jskj;
	}

	public void setJskj(String jskj) {
		this.jskj = jskj;
	}
	public String getYwkj() {
		return ywkj;
	}

	public void setYwkj(String ywkj) {
		this.ywkj = ywkj;
	}

	public String getUrlmc() {
		return urlmc;
	}

	public void setUrlmc(String urlmc) {
		this.urlmc = urlmc;
	}

	public String getTzfs() {
		return tzfs;
	}

	public void setTzfs(String tzfs) {
		this.tzfs = tzfs;
	}

	/**无参构造方法**/
    public Tycx001CxCxjgdyPojo(){};

    /**带参构造方法*/
    public Tycx001CxCxjgdyPojo (String zdybj,String dmsql,String dqfs,String dyghbbj,String glbj,String jcbzdlx,String kzlx,String lbm,String lkd,String llx,String lmc,String lms,String lrrq,String lrr_dm,String mbbz,String sdbj,String sjgsdq,String sjlmc,String sqlxh,String tjlx,String url,String uuid,String xgrq,String xgr_dm,Double xh,String xsgs,Double xsxh,String xzcs,String ycbj,String zsfs,String yjfw,String urlmc,String jskj,String ywkj,String color){
    	this.zdybj=zdybj;
        this.dmsql = dmsql;
        this.dqfs = dqfs;
        this.dyghbbj = dyghbbj;
        this.glbj = glbj;
        this.jcbzdlx = jcbzdlx;
        this.kzlx = kzlx;
        this.lbm = lbm;
        this.lkd = lkd;
        this.llx = llx;
        this.lmc = lmc;
        this.lms = lms;
        this.lrrq = lrrq;
        this.lrr_dm = lrr_dm;
        this.mbbz = mbbz;
        this.sdbj = sdbj;
        this.sjgsdq = sjgsdq;
        this.sjlmc = sjlmc;
        this.sqlxh = sqlxh;
        this.tjlx = tjlx;
        this.url = url;
        this.uuid = uuid;
        this.xgrq = xgrq;
        this.xgr_dm = xgr_dm;
        this.xh = xh;
        this.xsgs = xsgs;
        this.xsxh = xsxh;
        this.xzcs = xzcs;
        this.ycbj = ycbj;
        this.zsfs = zsfs;
        this.yjfw = yjfw;
        this.urlmc=urlmc;
        this.jskj=jskj;
        this.ywkj=ywkj;
        this.color=color;
    }

    public String getYjfw() {
		return yjfw;
	}

	public void setYjfw(String yjfw) {
		this.yjfw = yjfw;
	}

	/**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public Tycx001CxCxjgdyPojo(Map data){
    	this.zdybj = data.get("zdybj") == null
    			? null : (String)data.get("zdybj");
        this.dmsql = data.get("dmsql") == null
            ? null : (String)data.get("dmsql");
        this.dqfs = data.get("dqfs") == null
            ? null : (String)data.get("dqfs");
        this.dyghbbj = data.get("dyghbbj") == null
            ? null : (String)data.get("dyghbbj");
        this.glbj = data.get("glbj") == null
            ? null : (String)data.get("glbj");
        this.jcbzdlx = data.get("jcbzdlx") == null
            ? null : (String)data.get("jcbzdlx");
        this.kzlx = data.get("kzlx") == null
            ? null : (String)data.get("kzlx");
        this.lbm = data.get("lbm") == null
            ? null : (String)data.get("lbm");
        this.lkd = data.get("lkd") == null
            ? null : (String)data.get("lkd");
        this.llx = data.get("llx") == null
            ? null : (String)data.get("llx");
        this.lmc = data.get("lmc") == null
            ? null : (String)data.get("lmc");
        this.lms = data.get("lms") == null
            ? null : (String)data.get("lms");
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrr_dm = data.get("lrr_dm") == null
            ? null : (String)data.get("lrr_dm");
        this.mbbz = data.get("mbbz") == null
            ? null : (String)data.get("mbbz");
        this.sdbj = data.get("sdbj") == null
            ? null : (String)data.get("sdbj");
        this.sjgsdq = data.get("sjgsdq") == null
            ? null : (String)data.get("sjgsdq");
        this.sjlmc = data.get("sjlmc") == null
            ? null : (String)data.get("sjlmc");
        this.sqlxh = data.get("sqlxh") == null
            ? null : (String)data.get("sqlxh");
        this.tjlx = data.get("tjlx") == null
            ? null : (String)data.get("tjlx");
        this.url = data.get("url") == null
            ? null : (String)data.get("url");
        this.uuid = data.get("uuid") == null
            ? null : (String)data.get("uuid");
        this.xgrq = data.get("xgrq") == null
            ? null : (String)data.get("xgrq");
        this.xgr_dm = data.get("xgr_dm") == null
            ? null : (String)data.get("xgr_dm");
        this.xh =TycxUtils.isEmpty(data.get("xh")) ? null : Double.parseDouble((String)data.get("xh"));
        this.xsgs = data.get("xsgs") == null
            ? null : (String)data.get("xsgs");
        this.xsxh = TycxUtils.isEmpty(data.get("xsxh")) ? null : Double.parseDouble((String)data.get("xsxh"));
        this.xzcs = data.get("xzcs") == null
            ? null : (String)data.get("xzcs");
        this.ycbj = data.get("ycbj") == null
            ? null : (String)data.get("ycbj");
        this.zsfs = data.get("zsfs") == null
            ? null : (String)data.get("zsfs");
        this.yjfw = data.get("yjfw") == null
        ? null : (String)data.get("yjfw");
        this.urlmc = data.get("urlmc") == null
        ? null : (String)data.get("urlmc");
        this.jskj = data.get("jskj") == null
        ? null : (String)data.get("jskj");
        this.ywkj = data.get("ywkj") == null
        ? null : (String)data.get("ywkj");
        this.color = data.get("color") == null
        ? null : (String)data.get("color");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("zdybj",zdybj);
        map.put("dmsql",dmsql);
        map.put("dqfs",dqfs);
        map.put("dyghbbj",dyghbbj);
        map.put("glbj",glbj);
        map.put("jcbzdlx",jcbzdlx);
        map.put("kzlx",kzlx);
        map.put("lbm",lbm);
        map.put("lkd",lkd);
        map.put("llx",llx);
        map.put("lmc",lmc);
        map.put("lms",lms);
        map.put("lrrq",lrrq);
        map.put("lrr_dm",lrr_dm);
        map.put("mbbz",mbbz);
        map.put("sdbj",sdbj);
        map.put("sjgsdq",sjgsdq);
        map.put("sjlmc",sjlmc);
        map.put("sqlxh",sqlxh);
        map.put("tjlx",tjlx);
        map.put("url",url);
        map.put("uuid",uuid);
        map.put("xgrq",xgrq);
        map.put("xgr_dm",xgr_dm);
        map.put("xh",xh);
        map.put("xsgs",xsgs);
        map.put("xsxh",xsxh);
        map.put("xzcs",xzcs);
        map.put("ycbj",ycbj);
        map.put("zsfs",zsfs);
        map.put("yjfw",yjfw);
        map.put("urlmc", urlmc);
        map.put("jskj", jskj);
        map.put("ywkj", ywkj);
        map.put("color", color);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getDmsql() {
        return this.dmsql;
    }
    public void setDmsql(String dmsql) {
        this.dmsql = dmsql;
    }
    public String getDqfs() {
        return this.dqfs;
    }
    public void setDqfs(String dqfs) {
        this.dqfs = dqfs;
    }
    public String getDyghbbj() {
        return this.dyghbbj;
    }
    public void setDyghbbj(String dyghbbj) {
        this.dyghbbj = dyghbbj;
    }
    public String getGlbj() {
        return this.glbj;
    }
    public void setGlbj(String glbj) {
        this.glbj = glbj;
    }
    public String getJcbzdlx() {
        return this.jcbzdlx;
    }
    public void setJcbzdlx(String jcbzdlx) {
        this.jcbzdlx = jcbzdlx;
    }
    public String getKzlx() {
        return this.kzlx;
    }
    public void setKzlx(String kzlx) {
        this.kzlx = kzlx;
    }
    public String getLbm() {
        return this.lbm;
    }
    public void setLbm(String lbm) {
        this.lbm = lbm;
    }
    public String getLkd() {
        return this.lkd;
    }
    public void setLkd(String lkd) {
        this.lkd = lkd;
    }
    public String getLlx() {
        return this.llx;
    }
    public void setLlx(String llx) {
        this.llx = llx;
    }
    public String getLmc() {
        return this.lmc;
    }
    public void setLmc(String lmc) {
        this.lmc = lmc;
    }
    public String getLms() {
        return this.lms;
    }
    public void setLms(String lms) {
        this.lms = lms;
    }
    public String getLrrq() {
        return this.lrrq;
    }
    public void setLrrq(String lrrq) {
        this.lrrq = lrrq;
    }
    public String getLrr_dm() {
        return this.lrr_dm;
    }
    public void setLrr_dm(String lrr_dm) {
        this.lrr_dm = lrr_dm;
    }
    public String getMbbz() {
        return this.mbbz;
    }
    public void setMbbz(String mbbz) {
        this.mbbz = mbbz;
    }
    public String getSdbj() {
        return this.sdbj;
    }
    public void setSdbj(String sdbj) {
        this.sdbj = sdbj;
    }
    public String getSjgsdq() {
        return this.sjgsdq;
    }
    public void setSjgsdq(String sjgsdq) {
        this.sjgsdq = sjgsdq;
    }
    public String getSjlmc() {
        return this.sjlmc;
    }
    public void setSjlmc(String sjlmc) {
        this.sjlmc = sjlmc;
    }
    public String getSqlxh() {
        return this.sqlxh;
    }
    public void setSqlxh(String sqlxh) {
        this.sqlxh = sqlxh;
    }
    public String getTjlx() {
        return this.tjlx;
    }
    public void setTjlx(String tjlx) {
        this.tjlx = tjlx;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getXgrq() {
        return this.xgrq;
    }
    public void setXgrq(String xgrq) {
        this.xgrq = xgrq;
    }
    public String getXgr_dm() {
        return this.xgr_dm;
    }
    public void setXgr_dm(String xgr_dm) {
        this.xgr_dm = xgr_dm;
    }
    public Double getXh() {
        return this.xh;
    }
    public void setXh(Double xh) {
        this.xh = xh;
    }
    public String getXsgs() {
        return this.xsgs;
    }
    public void setXsgs(String xsgs) {
        this.xsgs = xsgs;
    }
    public Double getXsxh() {
        return this.xsxh;
    }
    public void setXsxh(Double xsxh) {
        this.xsxh = xsxh;
    }
    public String getXzcs() {
        return this.xzcs;
    }
    public void setXzcs(String xzcs) {
        this.xzcs = xzcs;
    }
    public String getYcbj() {
        return this.ycbj;
    }
    public void setYcbj(String ycbj) {
        this.ycbj = ycbj;
    }
    public String getZsfs() {
        return this.zsfs;
    }
    public void setZsfs(String zsfs) {
        this.zsfs = zsfs;
    }

}