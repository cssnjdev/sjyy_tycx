package com.cwks.bizcore.tycx.core.mapping.pojo;

import com.cwks.bizcore.tycx.core.utils.TycxUtils;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(CX_CXDY)
*/

public class Tycx002DzcxPojo {

    private static final long serialVersionUID = 1L;

    private  String ccgcmc; //CCGCMC
    private  String cjjg_dm; //CJJG_DM
    private  String cxfl; //CXFL
    private  Double cxlx; //CXLX
    private  String cxms; //CXMS
    private  Double exportmax; //EXPORTMAX
    private  String fybj; //FYBJ
    private  String gxfs; //GXFS
    private  String headsql; //HEADSQL
    private  String hjxsbz; //HJXSBZ
    private  String jgsj; //JGSJ
    private  String limttime; //LIMTTIME
    private  String lrrq; //LRRQ
    private  String lrr_dm; //LRR_DM
    private  String mbxh; //MBXH
    private  Double myjls; //MYJLS
    private  String pagelayout; //PAGELAYOUT
    private  String queryplugin; //QUERYPLUGIN
    private  String sjgsdq; //SJGSDQ
    private  String sjylx; //SJYLX
    private  String sm; //SM
    private  String sqllx; //SQLLX
    private  String sqlmc; //SQLMC
    private  String sqlstr; //SQLSTR
    private  String sqlxh; //SQLXH
    private  String ssjg_dm; //SSJG_DM
    private  String txzs; //图形展示（可多选）
    private  String xgrq; //XGRQ
    private  String xgr_dm; //XGR_DM
    private  String xybz; //XYBZ
    private  String yssjsj; //YSSJSJ
    private  String ywy; //YWY
    private String DJZCLX_DM;
    public String getDJZCLX_DM() {
		return DJZCLX_DM;
	}

	public void setDJZCLX_DM(String dJZCLX_DM) {
		DJZCLX_DM = dJZCLX_DM;
	}

	public String getDJZCLXMC() {
		return DJZCLXMC;
	}

	public void setDJZCLXMC(String dJZCLXMC) {
		DJZCLXMC = dJZCLXMC;
	}

	public String getDLBZ() {
		return DLBZ;
	}

	public void setDLBZ(String dLBZ) {
		DLBZ = dLBZ;
	}

	public String getYXBZ() {
		return YXBZ;
	}

	public void setYXBZ(String yXBZ) {
		YXBZ = yXBZ;
	}
	private String DJZCLXMC;
    private String DLBZ;
    private String YXBZ;

    /**无参构造方法**/
    public Tycx002DzcxPojo(){};

    /**带参构造方法*/
    public Tycx002DzcxPojo (String ccgcmc,String cjjg_dm,String cxfl,Double cxlx,String cxms,Double exportmax,String fybj,String gxfs,String headsql,String hjxsbz,String jgsj,String limttime,String lrrq,String lrr_dm,String mbxh,Double myjls,String pagelayout,String queryplugin,String sjgsdq,String sjylx,String sm,String sqllx,String sqlmc,String sqlstr,String sqlxh,String ssjg_dm,String txzs,String xgrq,String xgr_dm,String xybz,String yssjsj,String ywy,String DJZCLX_DM,String DJZCLXMC,String DLBZ,String YXBZ){
        this.ccgcmc = ccgcmc;
        this.cjjg_dm = cjjg_dm;
        this.cxfl = cxfl;
        this.cxlx = cxlx;
        this.cxms = cxms;
        this.exportmax = exportmax;
        this.fybj = fybj;
        this.gxfs = gxfs;
        this.headsql = headsql;
        this.hjxsbz = hjxsbz;
        this.jgsj = jgsj;
        this.limttime = limttime;
        this.lrrq = lrrq;
        this.lrr_dm = lrr_dm;
        this.mbxh = mbxh;
        this.myjls = myjls;
        this.pagelayout = pagelayout;
        this.queryplugin = queryplugin;
        this.sjgsdq = sjgsdq;
        this.sjylx = sjylx;
        this.sm = sm;
        this.sqllx = sqllx;
        this.sqlmc = sqlmc;
        this.sqlstr = sqlstr;
        this.sqlxh = sqlxh;
        this.ssjg_dm = ssjg_dm;
        this.txzs = txzs;
        this.xgrq = xgrq;
        this.xgr_dm = xgr_dm;
        this.xybz = xybz;
        this.yssjsj = yssjsj;
        this.ywy = ywy;
        this.DJZCLX_DM=DJZCLX_DM;
        this.DJZCLXMC=DJZCLXMC;
        this.DLBZ=DLBZ;
        this.YXBZ=YXBZ;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public Tycx002DzcxPojo(Map data){
        this.ccgcmc = data.get("ccgcmc") == null
            ? null : (String)data.get("ccgcmc");
        this.cjjg_dm = data.get("cjjg_dm") == null
            ? null : (String)data.get("cjjg_dm");
        this.cxfl = data.get("cxfl") == null
            ? null : (String)data.get("cxfl");
        this.cxlx = TycxUtils.isEmpty(data.get("cxlx")) ? null : Double.parseDouble((String)data.get("cxlx"));
        this.cxms = data.get("cxms") == null
            ? null : (String)data.get("cxms");
        this.exportmax = TycxUtils.isEmpty(data.get("exportmax")) ? null : Double.parseDouble((String)data.get("exportmax"));
        this.fybj = data.get("fybj") == null
            ? null : (String)data.get("fybj");
        this.gxfs = data.get("gxfs") == null
            ? null : (String)data.get("gxfs");
        this.headsql = data.get("headsql") == null
            ? null : (String)data.get("headsql");
        this.hjxsbz = data.get("hjxsbz") == null
            ? null : (String)data.get("hjxsbz");
        this.jgsj = data.get("jgsj") == null
            ? null : (String)data.get("jgsj");
        this.limttime = data.get("limttime") == null
            ? null : (String)data.get("limttime");
        this.lrrq = data.get("lrrq") == null
            ? null : (String)data.get("lrrq");
        this.lrr_dm = data.get("lrr_dm") == null
            ? null : (String)data.get("lrr_dm");
        this.mbxh = data.get("mbxh") == null
            ? null : (String)data.get("mbxh");
        this.myjls = TycxUtils.isEmpty(data.get("myjls")) ? null : Double.parseDouble((String)data.get("myjls"));
        this.pagelayout = data.get("pagelayout") == null
            ? null : (String)data.get("pagelayout");
        this.queryplugin = data.get("queryplugin") == null
            ? null : (String)data.get("queryplugin");
        this.sjgsdq = data.get("sjgsdq") == null
            ? null : (String)data.get("sjgsdq");
        this.sjylx = data.get("sjylx") == null
            ? null : (String)data.get("sjylx");
        this.sm = data.get("sm") == null
            ? null : (String)data.get("sm");
        this.sqllx = data.get("sqllx") == null
            ? null : (String)data.get("sqllx");
        this.sqlmc = data.get("sqlmc") == null
            ? null : (String)data.get("sqlmc");
        this.sqlstr = data.get("sqlstr") == null
            ? null : (String)data.get("sqlstr");
        this.sqlxh = data.get("sqlxh") == null
            ? null : (String)data.get("sqlxh");
        this.ssjg_dm = data.get("ssjg_dm") == null
            ? null : (String)data.get("ssjg_dm");
        this.txzs = data.get("txzs") == null
            ? null : (String)data.get("txzs");
        this.xgrq = data.get("xgrq") == null
            ? null : (String)data.get("xgrq");
        this.xgr_dm = data.get("xgr_dm") == null
            ? null : (String)data.get("xgr_dm");
        this.xybz = data.get("xybz") == null
            ? null : (String)data.get("xybz");
        this.yssjsj = data.get("yssjsj") == null
            ? null : (String)data.get("yssjsj");
        this.ywy = data.get("ywy") == null
            ? null : (String)data.get("ywy");
        this.DJZCLX_DM = data.get("DJZCLX_DM") == null
                ? null : (String)data.get("DJZCLX_DM");
            this.DJZCLXMC = data.get("DJZCLXMC") == null
                ? null : (String)data.get("DJZCLXMC");
            this.DLBZ = data.get("DLBZ") == null
                ? null : (String)data.get("DLBZ");
            this.YXBZ = data.get("YXBZ") == null
                ? null : (String)data.get("YXBZ");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("ccgcmc",ccgcmc);
        map.put("cjjg_dm",cjjg_dm);
        map.put("cxfl",cxfl);
        map.put("cxlx",cxlx);
        map.put("cxms",cxms);
        map.put("exportmax",exportmax);
        map.put("fybj",fybj);
        map.put("gxfs",gxfs);
        map.put("headsql",headsql);
        map.put("hjxsbz",hjxsbz);
        map.put("jgsj",jgsj);
        map.put("limttime",limttime);
        map.put("lrrq",lrrq);
        map.put("lrr_dm",lrr_dm);
        map.put("mbxh",mbxh);
        map.put("myjls",myjls);
        map.put("pagelayout",pagelayout);
        map.put("queryplugin",queryplugin);
        map.put("sjgsdq",sjgsdq);
        map.put("sjylx",sjylx);
        map.put("sm",sm);
        map.put("sqllx",sqllx);
        map.put("sqlmc",sqlmc);
        map.put("sqlstr",sqlstr);
        map.put("sqlxh",sqlxh);
        map.put("ssjg_dm",ssjg_dm);
        map.put("txzs",txzs);
        map.put("xgrq",xgrq);
        map.put("xgr_dm",xgr_dm);
        map.put("xybz",xybz);
        map.put("yssjsj",yssjsj);
        map.put("ywy",ywy);
        map.put("DJZCLXMC",DJZCLXMC);
        map.put("DLBZ",DLBZ);
        map.put("DJZCLX_DM",DJZCLX_DM);
        map.put("YXBZ",YXBZ);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getCcgcmc() {
        return this.ccgcmc;
    }
    public void setCcgcmc(String ccgcmc) {
        this.ccgcmc = ccgcmc;
    }
    public String getCjjg_dm() {
        return this.cjjg_dm;
    }
    public void setCjjg_dm(String cjjg_dm) {
        this.cjjg_dm = cjjg_dm;
    }
    public String getCxfl() {
        return this.cxfl;
    }
    public void setCxfl(String cxfl) {
        this.cxfl = cxfl;
    }
    public Double getCxlx() {
        return this.cxlx;
    }
    public void setCxlx(Double cxlx) {
        this.cxlx = cxlx;
    }
    public String getCxms() {
        return this.cxms;
    }
    public void setCxms(String cxms) {
        this.cxms = cxms;
    }
    public Double getExportmax() {
        return this.exportmax;
    }
    public void setExportmax(Double exportmax) {
        this.exportmax = exportmax;
    }
    public String getFybj() {
        return this.fybj;
    }
    public void setFybj(String fybj) {
        this.fybj = fybj;
    }
    public String getGxfs() {
        return this.gxfs;
    }
    public void setGxfs(String gxfs) {
        this.gxfs = gxfs;
    }
    public String getHeadsql() {
        return this.headsql;
    }
    public void setHeadsql(String headsql) {
        this.headsql = headsql;
    }
    public String getHjxsbz() {
        return this.hjxsbz;
    }
    public void setHjxsbz(String hjxsbz) {
        this.hjxsbz = hjxsbz;
    }
    public String getJgsj() {
        return this.jgsj;
    }
    public void setJgsj(String jgsj) {
        this.jgsj = jgsj;
    }
    public String getLimttime() {
        return this.limttime;
    }
    public void setLimttime(String limttime) {
        this.limttime = limttime;
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
    public String getMbxh() {
        return this.mbxh;
    }
    public void setMbxh(String mbxh) {
        this.mbxh = mbxh;
    }
    public Double getMyjls() {
        return this.myjls;
    }
    public void setMyjls(Double myjls) {
        this.myjls = myjls;
    }
    public String getPagelayout() {
        return this.pagelayout;
    }
    public void setPagelayout(String pagelayout) {
        this.pagelayout = pagelayout;
    }
    public String getQueryplugin() {
        return this.queryplugin;
    }
    public void setQueryplugin(String queryplugin) {
        this.queryplugin = queryplugin;
    }
    public String getSjgsdq() {
        return this.sjgsdq;
    }
    public void setSjgsdq(String sjgsdq) {
        this.sjgsdq = sjgsdq;
    }
    public String getSjylx() {
        return this.sjylx;
    }
    public void setSjylx(String sjylx) {
        this.sjylx = sjylx;
    }
    public String getSm() {
        return this.sm;
    }
    public void setSm(String sm) {
        this.sm = sm;
    }
    public String getSqllx() {
        return this.sqllx;
    }
    public void setSqllx(String sqllx) {
        this.sqllx = sqllx;
    }
    public String getSqlmc() {
        return this.sqlmc;
    }
    public void setSqlmc(String sqlmc) {
        this.sqlmc = sqlmc;
    }
    public String getSqlstr() {
        return this.sqlstr;
    }
    public void setSqlstr(String sqlstr) {
        this.sqlstr = sqlstr;
    }
    public String getSqlxh() {
        return this.sqlxh;
    }
    public void setSqlxh(String sqlxh) {
        this.sqlxh = sqlxh;
    }
    public String getSsjg_dm() {
        return this.ssjg_dm;
    }
    public void setSsjg_dm(String ssjg_dm) {
        this.ssjg_dm = ssjg_dm;
    }
    public String getTxzs() {
        return this.txzs;
    }
    public void setTxzs(String txzs) {
        this.txzs = txzs;
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
    public String getXybz() {
        return this.xybz;
    }
    public void setXybz(String xybz) {
        this.xybz = xybz;
    }
    public String getYssjsj() {
        return this.yssjsj;
    }
    public void setYssjsj(String yssjsj) {
        this.yssjsj = yssjsj;
    }
    public String getYwy() {
        return this.ywy;
    }
    public void setYwy(String ywy) {
        this.ywy = ywy;
    }

}