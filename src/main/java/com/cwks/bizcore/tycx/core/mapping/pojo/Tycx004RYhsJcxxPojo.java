package com.cwks.bizcore.tycx.core.mapping.pojo;

import com.cwks.bizcore.tycx.core.utils.TycxUtils;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(R_YHS_JCXX)
*/

public class Tycx004RYhsJcxxPojo {

    private static final long serialVersionUID = 1L;

    private  String bsrdh; //办税人固定电话
    private  String bsrsfzjhm; //办税人身份证件号码
    private  String bsrsfzjzlmc; //办税人身份证件种类
    private  String bsrxm; //办税人姓名
    private  String cwfzrdh; //财务负责人电话
    private  String cwfzrsfzjhm; //财务负责人身份证件号码
    private  String cwfzrsfzjzlmc; //财务负责人身份证件种类
    private  String cwfzrxm; //财务负责人姓名
    private  Double cyrs; //从业人数
    private  String djjgmc; //登记机关名称
    private  String djrq_1; //登记日期
    private  Double djxh; //登记序号
    private  String djzclxmc; //登记注册类型
    private  String dwlsgxmc; //单位隶属关系
    private  String fddbrdh; //法定代表人电话
    private  String fddbrsfzjhm; //法定代表人身份证号码
    private  String fddbrsfzjlxmc; //法定代表人身份证件类型
    private  String fddbrxm; //法定代表人姓名
    private  String gjhdqjc; //国家
    private  String gykglxmc; //国有控股类型
    private  String hjszd; //户籍所在地
    private  String hsfsmc; //核算方式
    private  String hymc; //行业
    private  String jdxzmc; //街道乡镇
    private  Double jmje; //减免金额
    private  String jyfw; //经营范围
    private  String kjzdzzmc; //会计制度（准则）
    private  String kqccsztdjbz; //跨区财产税主体登记标志
    private  String kyslrq_1; //开业设立日期
    private  String kzztdjlxmc; //课征主体登记类型
    private  Double nse; //纳税额
    private  String nspm_hy; //行业排名
    private  String nspm_sj; //纳税排名-市局
    private  String nspm_xj; //纳税排名-县局
    private  String nsrmc; //纳税人名称
    private  String nsrsbh; //纳税人识别号
    private  String nsrzglxmc; //纳税人资格类型
    private  String nsrztmc; //纳税人状态
    private  String pdnd; //评定年度
    private  Double qsje; //欠税金额
    private  String scjydz; //生产经营地址
    private  String sfdqdeh; //是否定期定额户
    private  String sfl; //税负率
    private  String sflgfp; //是否领钩发票
    private  String sfsyxxwlqy; //是否属于小型微利企业
    private  String shxydm; //社会信用代码
    private  Double sjje; //实缴金额
    private  String ssglymc; //税收管理员名称
    private  Double tzze; //投资总额
    private  String xmts; //项目登记条数
    private  String xydj_dm; //信用等级
    private  Double yye; //营业额
    private  Double zczb; //注册资本
    private  String zfjglxmc; //总分机构类型
    private  String zgswjmc; //主管税务局名称
    private  String zgswskfjmc; //主管税务所（科、分局）名称
    private  Double zrrtzbl; //自然人投资比例
    private  String zzhm; //证照编号
    private  String zzjglxmc; //组织机构类型
    private  String zzjg_dm; //组织机构代码
    private  String zzlxmc; //执照类型

    /**无参构造方法**/
    public Tycx004RYhsJcxxPojo(){};

    /**带参构造方法*/
    public Tycx004RYhsJcxxPojo (String bsrdh,String bsrsfzjhm,String bsrsfzjzlmc,String bsrxm,String cwfzrdh,String cwfzrsfzjhm,String cwfzrsfzjzlmc,String cwfzrxm,Double cyrs,String djjgmc,String djrq_1,Double djxh,String djzclxmc,String dwlsgxmc,String fddbrdh,String fddbrsfzjhm,String fddbrsfzjlxmc,String fddbrxm,String gjhdqjc,String gykglxmc,String hjszd,String hsfsmc,String hymc,String jdxzmc,Double jmje,String jyfw,String kjzdzzmc,String kqccsztdjbz,String kyslrq_1,String kzztdjlxmc,Double nse,String nspm_hy,String nspm_sj,String nspm_xj,String nsrmc,String nsrsbh,String nsrzglxmc,String nsrztmc,String pdnd,Double qsje,String scjydz,String sfdqdeh,String sfl,String sflgfp,String sfsyxxwlqy,String shxydm,Double sjje,String ssglymc,Double tzze,String xmts,String xydj_dm,Double yye,Double zczb,String zfjglxmc,String zgswjmc,String zgswskfjmc,Double zrrtzbl,String zzhm,String zzjglxmc,String zzjg_dm,String zzlxmc){
        this.bsrdh = bsrdh;
        this.bsrsfzjhm = bsrsfzjhm;
        this.bsrsfzjzlmc = bsrsfzjzlmc;
        this.bsrxm = bsrxm;
        this.cwfzrdh = cwfzrdh;
        this.cwfzrsfzjhm = cwfzrsfzjhm;
        this.cwfzrsfzjzlmc = cwfzrsfzjzlmc;
        this.cwfzrxm = cwfzrxm;
        this.cyrs = cyrs;
        this.djjgmc = djjgmc;
        this.djrq_1 = djrq_1;
        this.djxh = djxh;
        this.djzclxmc = djzclxmc;
        this.dwlsgxmc = dwlsgxmc;
        this.fddbrdh = fddbrdh;
        this.fddbrsfzjhm = fddbrsfzjhm;
        this.fddbrsfzjlxmc = fddbrsfzjlxmc;
        this.fddbrxm = fddbrxm;
        this.gjhdqjc = gjhdqjc;
        this.gykglxmc = gykglxmc;
        this.hjszd = hjszd;
        this.hsfsmc = hsfsmc;
        this.hymc = hymc;
        this.jdxzmc = jdxzmc;
        this.jmje = jmje;
        this.jyfw = jyfw;
        this.kjzdzzmc = kjzdzzmc;
        this.kqccsztdjbz = kqccsztdjbz;
        this.kyslrq_1 = kyslrq_1;
        this.kzztdjlxmc = kzztdjlxmc;
        this.nse = nse;
        this.nspm_hy = nspm_hy;
        this.nspm_sj = nspm_sj;
        this.nspm_xj = nspm_xj;
        this.nsrmc = nsrmc;
        this.nsrsbh = nsrsbh;
        this.nsrzglxmc = nsrzglxmc;
        this.nsrztmc = nsrztmc;
        this.pdnd = pdnd;
        this.qsje = qsje;
        this.scjydz = scjydz;
        this.sfdqdeh = sfdqdeh;
        this.sfl = sfl;
        this.sflgfp = sflgfp;
        this.sfsyxxwlqy = sfsyxxwlqy;
        this.shxydm = shxydm;
        this.sjje = sjje;
        this.ssglymc = ssglymc;
        this.tzze = tzze;
        this.xmts = xmts;
        this.xydj_dm = xydj_dm;
        this.yye = yye;
        this.zczb = zczb;
        this.zfjglxmc = zfjglxmc;
        this.zgswjmc = zgswjmc;
        this.zgswskfjmc = zgswskfjmc;
        this.zrrtzbl = zrrtzbl;
        this.zzhm = zzhm;
        this.zzjglxmc = zzjglxmc;
        this.zzjg_dm = zzjg_dm;
        this.zzlxmc = zzlxmc;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public Tycx004RYhsJcxxPojo(Map data){
        this.bsrdh = data.get("bsrdh") == null
            ? null : (String)data.get("bsrdh");
        this.bsrsfzjhm = data.get("bsrsfzjhm") == null
            ? null : (String)data.get("bsrsfzjhm");
        this.bsrsfzjzlmc = data.get("bsrsfzjzlmc") == null
            ? null : (String)data.get("bsrsfzjzlmc");
        this.bsrxm = data.get("bsrxm") == null
            ? null : (String)data.get("bsrxm");
        this.cwfzrdh = data.get("cwfzrdh") == null
            ? null : (String)data.get("cwfzrdh");
        this.cwfzrsfzjhm = data.get("cwfzrsfzjhm") == null
            ? null : (String)data.get("cwfzrsfzjhm");
        this.cwfzrsfzjzlmc = data.get("cwfzrsfzjzlmc") == null
            ? null : (String)data.get("cwfzrsfzjzlmc");
        this.cwfzrxm = data.get("cwfzrxm") == null
            ? null : (String)data.get("cwfzrxm");
        this.cyrs =TycxUtils.isEmpty(data.get("cyrs")) ? null : Double.parseDouble((String)data.get("cyrs"));
        this.djjgmc = data.get("djjgmc") == null
            ? null : (String)data.get("djjgmc");
        this.djrq_1 = data.get("djrq_1") == null
            ? null : (String)data.get("djrq_1");
        this.djxh = TycxUtils.isEmpty(data.get("djxh")) ? null : Double.parseDouble((String)data.get("djxh"));
        this.djzclxmc = data.get("djzclxmc") == null
            ? null : (String)data.get("djzclxmc");
        this.dwlsgxmc = data.get("dwlsgxmc") == null
            ? null : (String)data.get("dwlsgxmc");
        this.fddbrdh = data.get("fddbrdh") == null
            ? null : (String)data.get("fddbrdh");
        this.fddbrsfzjhm = data.get("fddbrsfzjhm") == null
            ? null : (String)data.get("fddbrsfzjhm");
        this.fddbrsfzjlxmc = data.get("fddbrsfzjlxmc") == null
            ? null : (String)data.get("fddbrsfzjlxmc");
        this.fddbrxm = data.get("fddbrxm") == null
            ? null : (String)data.get("fddbrxm");
        this.gjhdqjc = data.get("gjhdqjc") == null
            ? null : (String)data.get("gjhdqjc");
        this.gykglxmc = data.get("gykglxmc") == null
            ? null : (String)data.get("gykglxmc");
        this.hjszd = data.get("hjszd") == null
            ? null : (String)data.get("hjszd");
        this.hsfsmc = data.get("hsfsmc") == null
            ? null : (String)data.get("hsfsmc");
        this.hymc = data.get("hymc") == null
            ? null : (String)data.get("hymc");
        this.jdxzmc = data.get("jdxzmc") == null
            ? null : (String)data.get("jdxzmc");
        this.jmje = TycxUtils.isEmpty(data.get("jmje")) ? null : Double.parseDouble((String)data.get("jmje"));
        this.jyfw = data.get("jyfw") == null
            ? null : (String)data.get("jyfw");
        this.kjzdzzmc = data.get("kjzdzzmc") == null
            ? null : (String)data.get("kjzdzzmc");
        this.kqccsztdjbz = data.get("kqccsztdjbz") == null
            ? null : (String)data.get("kqccsztdjbz");
        this.kyslrq_1 = data.get("kyslrq_1") == null
            ? null : (String)data.get("kyslrq_1");
        this.kzztdjlxmc = data.get("kzztdjlxmc") == null
            ? null : (String)data.get("kzztdjlxmc");
        this.nse = TycxUtils.isEmpty(data.get("nse")) ? null : Double.parseDouble((String)data.get("nse"));
        this.nspm_hy = data.get("nspm_hy") == null
            ? null : (String)data.get("nspm_hy");
        this.nspm_sj = data.get("nspm_sj") == null
            ? null : (String)data.get("nspm_sj");
        this.nspm_xj = data.get("nspm_xj") == null
            ? null : (String)data.get("nspm_xj");
        this.nsrmc = data.get("nsrmc") == null
            ? null : (String)data.get("nsrmc");
        this.nsrsbh = data.get("nsrsbh") == null
            ? null : (String)data.get("nsrsbh");
        this.nsrzglxmc = data.get("nsrzglxmc") == null
            ? null : (String)data.get("nsrzglxmc");
        this.nsrztmc = data.get("nsrztmc") == null
            ? null : (String)data.get("nsrztmc");
        this.pdnd = data.get("pdnd") == null
            ? null : (String)data.get("pdnd");
        this.qsje = TycxUtils.isEmpty(data.get("qsje")) ? null : Double.parseDouble((String)data.get("qsje"));
        this.scjydz = data.get("scjydz") == null
            ? null : (String)data.get("scjydz");
        this.sfdqdeh = data.get("sfdqdeh") == null
            ? null : (String)data.get("sfdqdeh");
        this.sfl = data.get("sfl") == null
            ? null : (String)data.get("sfl");
        this.sflgfp = data.get("sflgfp") == null
            ? null : (String)data.get("sflgfp");
        this.sfsyxxwlqy = data.get("sfsyxxwlqy") == null
            ? null : (String)data.get("sfsyxxwlqy");
        this.shxydm = data.get("shxydm") == null
            ? null : (String)data.get("shxydm");
        this.sjje = TycxUtils.isEmpty(data.get("sjje")) ? null : Double.parseDouble((String)data.get("sjje"));
        this.ssglymc = data.get("ssglymc") == null
            ? null : (String)data.get("ssglymc");
        this.tzze = TycxUtils.isEmpty(data.get("tzze")) ? null : Double.parseDouble((String)data.get("tzze"));
        this.xmts = data.get("xmts") == null
            ? null : (String)data.get("xmts");
        this.xydj_dm = data.get("xydj_dm") == null
            ? null : (String)data.get("xydj_dm");
        this.yye = TycxUtils.isEmpty(data.get("yye")) ? null : Double.parseDouble((String)data.get("yye"));
        this.zczb = TycxUtils.isEmpty(data.get("zczb")) ? null : Double.parseDouble((String)data.get("zczb"));
        this.zfjglxmc = data.get("zfjglxmc") == null
            ? null : (String)data.get("zfjglxmc");
        this.zgswjmc = data.get("zgswjmc") == null
            ? null : (String)data.get("zgswjmc");
        this.zgswskfjmc = data.get("zgswskfjmc") == null
            ? null : (String)data.get("zgswskfjmc");
        this.zrrtzbl = TycxUtils.isEmpty(data.get("zrrtzbl")) ? null : Double.parseDouble((String)data.get("zrrtzbl"));
        this.zzhm = data.get("zzhm") == null
            ? null : (String)data.get("zzhm");
        this.zzjglxmc = data.get("zzjglxmc") == null
            ? null : (String)data.get("zzjglxmc");
        this.zzjg_dm = data.get("zzjg_dm") == null
            ? null : (String)data.get("zzjg_dm");
        this.zzlxmc = data.get("zzlxmc") == null
            ? null : (String)data.get("zzlxmc");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("bsrdh",bsrdh);
        map.put("bsrsfzjhm",bsrsfzjhm);
        map.put("bsrsfzjzlmc",bsrsfzjzlmc);
        map.put("bsrxm",bsrxm);
        map.put("cwfzrdh",cwfzrdh);
        map.put("cwfzrsfzjhm",cwfzrsfzjhm);
        map.put("cwfzrsfzjzlmc",cwfzrsfzjzlmc);
        map.put("cwfzrxm",cwfzrxm);
        map.put("cyrs",cyrs);
        map.put("djjgmc",djjgmc);
        map.put("djrq_1",djrq_1);
        map.put("djxh",djxh);
        map.put("djzclxmc",djzclxmc);
        map.put("dwlsgxmc",dwlsgxmc);
        map.put("fddbrdh",fddbrdh);
        map.put("fddbrsfzjhm",fddbrsfzjhm);
        map.put("fddbrsfzjlxmc",fddbrsfzjlxmc);
        map.put("fddbrxm",fddbrxm);
        map.put("gjhdqjc",gjhdqjc);
        map.put("gykglxmc",gykglxmc);
        map.put("hjszd",hjszd);
        map.put("hsfsmc",hsfsmc);
        map.put("hymc",hymc);
        map.put("jdxzmc",jdxzmc);
        map.put("jmje",jmje);
        map.put("jyfw",jyfw);
        map.put("kjzdzzmc",kjzdzzmc);
        map.put("kqccsztdjbz",kqccsztdjbz);
        map.put("kyslrq_1",kyslrq_1);
        map.put("kzztdjlxmc",kzztdjlxmc);
        map.put("nse",nse);
        map.put("nspm_hy",nspm_hy);
        map.put("nspm_sj",nspm_sj);
        map.put("nspm_xj",nspm_xj);
        map.put("nsrmc",nsrmc);
        map.put("nsrsbh",nsrsbh);
        map.put("nsrzglxmc",nsrzglxmc);
        map.put("nsrztmc",nsrztmc);
        map.put("pdnd",pdnd);
        map.put("qsje",qsje);
        map.put("scjydz",scjydz);
        map.put("sfdqdeh",sfdqdeh);
        map.put("sfl",sfl);
        map.put("sflgfp",sflgfp);
        map.put("sfsyxxwlqy",sfsyxxwlqy);
        map.put("shxydm",shxydm);
        map.put("sjje",sjje);
        map.put("ssglymc",ssglymc);
        map.put("tzze",tzze);
        map.put("xmts",xmts);
        map.put("xydj_dm",xydj_dm);
        map.put("yye",yye);
        map.put("zczb",zczb);
        map.put("zfjglxmc",zfjglxmc);
        map.put("zgswjmc",zgswjmc);
        map.put("zgswskfjmc",zgswskfjmc);
        map.put("zrrtzbl",zrrtzbl);
        map.put("zzhm",zzhm);
        map.put("zzjglxmc",zzjglxmc);
        map.put("zzjg_dm",zzjg_dm);
        map.put("zzlxmc",zzlxmc);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public String getBsrdh() {
        return this.bsrdh;
    }
    public void setBsrdh(String bsrdh) {
        this.bsrdh = bsrdh;
    }
    public String getBsrsfzjhm() {
        return this.bsrsfzjhm;
    }
    public void setBsrsfzjhm(String bsrsfzjhm) {
        this.bsrsfzjhm = bsrsfzjhm;
    }
    public String getBsrsfzjzlmc() {
        return this.bsrsfzjzlmc;
    }
    public void setBsrsfzjzlmc(String bsrsfzjzlmc) {
        this.bsrsfzjzlmc = bsrsfzjzlmc;
    }
    public String getBsrxm() {
        return this.bsrxm;
    }
    public void setBsrxm(String bsrxm) {
        this.bsrxm = bsrxm;
    }
    public String getCwfzrdh() {
        return this.cwfzrdh;
    }
    public void setCwfzrdh(String cwfzrdh) {
        this.cwfzrdh = cwfzrdh;
    }
    public String getCwfzrsfzjhm() {
        return this.cwfzrsfzjhm;
    }
    public void setCwfzrsfzjhm(String cwfzrsfzjhm) {
        this.cwfzrsfzjhm = cwfzrsfzjhm;
    }
    public String getCwfzrsfzjzlmc() {
        return this.cwfzrsfzjzlmc;
    }
    public void setCwfzrsfzjzlmc(String cwfzrsfzjzlmc) {
        this.cwfzrsfzjzlmc = cwfzrsfzjzlmc;
    }
    public String getCwfzrxm() {
        return this.cwfzrxm;
    }
    public void setCwfzrxm(String cwfzrxm) {
        this.cwfzrxm = cwfzrxm;
    }
    public Double getCyrs() {
        return this.cyrs;
    }
    public void setCyrs(Double cyrs) {
        this.cyrs = cyrs;
    }
    public String getDjjgmc() {
        return this.djjgmc;
    }
    public void setDjjgmc(String djjgmc) {
        this.djjgmc = djjgmc;
    }
    public String getDjrq_1() {
        return this.djrq_1;
    }
    public void setDjrq_1(String djrq_1) {
        this.djrq_1 = djrq_1;
    }
    public Double getDjxh() {
        return this.djxh;
    }
    public void setDjxh(Double djxh) {
        this.djxh = djxh;
    }
    public String getDjzclxmc() {
        return this.djzclxmc;
    }
    public void setDjzclxmc(String djzclxmc) {
        this.djzclxmc = djzclxmc;
    }
    public String getDwlsgxmc() {
        return this.dwlsgxmc;
    }
    public void setDwlsgxmc(String dwlsgxmc) {
        this.dwlsgxmc = dwlsgxmc;
    }
    public String getFddbrdh() {
        return this.fddbrdh;
    }
    public void setFddbrdh(String fddbrdh) {
        this.fddbrdh = fddbrdh;
    }
    public String getFddbrsfzjhm() {
        return this.fddbrsfzjhm;
    }
    public void setFddbrsfzjhm(String fddbrsfzjhm) {
        this.fddbrsfzjhm = fddbrsfzjhm;
    }
    public String getFddbrsfzjlxmc() {
        return this.fddbrsfzjlxmc;
    }
    public void setFddbrsfzjlxmc(String fddbrsfzjlxmc) {
        this.fddbrsfzjlxmc = fddbrsfzjlxmc;
    }
    public String getFddbrxm() {
        return this.fddbrxm;
    }
    public void setFddbrxm(String fddbrxm) {
        this.fddbrxm = fddbrxm;
    }
    public String getGjhdqjc() {
        return this.gjhdqjc;
    }
    public void setGjhdqjc(String gjhdqjc) {
        this.gjhdqjc = gjhdqjc;
    }
    public String getGykglxmc() {
        return this.gykglxmc;
    }
    public void setGykglxmc(String gykglxmc) {
        this.gykglxmc = gykglxmc;
    }
    public String getHjszd() {
        return this.hjszd;
    }
    public void setHjszd(String hjszd) {
        this.hjszd = hjszd;
    }
    public String getHsfsmc() {
        return this.hsfsmc;
    }
    public void setHsfsmc(String hsfsmc) {
        this.hsfsmc = hsfsmc;
    }
    public String getHymc() {
        return this.hymc;
    }
    public void setHymc(String hymc) {
        this.hymc = hymc;
    }
    public String getJdxzmc() {
        return this.jdxzmc;
    }
    public void setJdxzmc(String jdxzmc) {
        this.jdxzmc = jdxzmc;
    }
    public Double getJmje() {
        return this.jmje;
    }
    public void setJmje(Double jmje) {
        this.jmje = jmje;
    }
    public String getJyfw() {
        return this.jyfw;
    }
    public void setJyfw(String jyfw) {
        this.jyfw = jyfw;
    }
    public String getKjzdzzmc() {
        return this.kjzdzzmc;
    }
    public void setKjzdzzmc(String kjzdzzmc) {
        this.kjzdzzmc = kjzdzzmc;
    }
    public String getKqccsztdjbz() {
        return this.kqccsztdjbz;
    }
    public void setKqccsztdjbz(String kqccsztdjbz) {
        this.kqccsztdjbz = kqccsztdjbz;
    }
    public String getKyslrq_1() {
        return this.kyslrq_1;
    }
    public void setKyslrq_1(String kyslrq_1) {
        this.kyslrq_1 = kyslrq_1;
    }
    public String getKzztdjlxmc() {
        return this.kzztdjlxmc;
    }
    public void setKzztdjlxmc(String kzztdjlxmc) {
        this.kzztdjlxmc = kzztdjlxmc;
    }
    public Double getNse() {
        return this.nse;
    }
    public void setNse(Double nse) {
        this.nse = nse;
    }
    public String getNspm_hy() {
        return this.nspm_hy;
    }
    public void setNspm_hy(String nspm_hy) {
        this.nspm_hy = nspm_hy;
    }
    public String getNspm_sj() {
        return this.nspm_sj;
    }
    public void setNspm_sj(String nspm_sj) {
        this.nspm_sj = nspm_sj;
    }
    public String getNspm_xj() {
        return this.nspm_xj;
    }
    public void setNspm_xj(String nspm_xj) {
        this.nspm_xj = nspm_xj;
    }
    public String getNsrmc() {
        return this.nsrmc;
    }
    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }
    public String getNsrsbh() {
        return this.nsrsbh;
    }
    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }
    public String getNsrzglxmc() {
        return this.nsrzglxmc;
    }
    public void setNsrzglxmc(String nsrzglxmc) {
        this.nsrzglxmc = nsrzglxmc;
    }
    public String getNsrztmc() {
        return this.nsrztmc;
    }
    public void setNsrztmc(String nsrztmc) {
        this.nsrztmc = nsrztmc;
    }
    public String getPdnd() {
        return this.pdnd;
    }
    public void setPdnd(String pdnd) {
        this.pdnd = pdnd;
    }
    public Double getQsje() {
        return this.qsje;
    }
    public void setQsje(Double qsje) {
        this.qsje = qsje;
    }
    public String getScjydz() {
        return this.scjydz;
    }
    public void setScjydz(String scjydz) {
        this.scjydz = scjydz;
    }
    public String getSfdqdeh() {
        return this.sfdqdeh;
    }
    public void setSfdqdeh(String sfdqdeh) {
        this.sfdqdeh = sfdqdeh;
    }
    public String getSfl() {
        return this.sfl;
    }
    public void setSfl(String sfl) {
        this.sfl = sfl;
    }
    public String getSflgfp() {
        return this.sflgfp;
    }
    public void setSflgfp(String sflgfp) {
        this.sflgfp = sflgfp;
    }
    public String getSfsyxxwlqy() {
        return this.sfsyxxwlqy;
    }
    public void setSfsyxxwlqy(String sfsyxxwlqy) {
        this.sfsyxxwlqy = sfsyxxwlqy;
    }
    public String getShxydm() {
        return this.shxydm;
    }
    public void setShxydm(String shxydm) {
        this.shxydm = shxydm;
    }
    public Double getSjje() {
        return this.sjje;
    }
    public void setSjje(Double sjje) {
        this.sjje = sjje;
    }
    public String getSsglymc() {
        return this.ssglymc;
    }
    public void setSsglymc(String ssglymc) {
        this.ssglymc = ssglymc;
    }
    public Double getTzze() {
        return this.tzze;
    }
    public void setTzze(Double tzze) {
        this.tzze = tzze;
    }
    public String getXmts() {
        return this.xmts;
    }
    public void setXmts(String xmts) {
        this.xmts = xmts;
    }
    public String getXydj_dm() {
        return this.xydj_dm;
    }
    public void setXydj_dm(String xydj_dm) {
        this.xydj_dm = xydj_dm;
    }
    public Double getYye() {
        return this.yye;
    }
    public void setYye(Double yye) {
        this.yye = yye;
    }
    public Double getZczb() {
        return this.zczb;
    }
    public void setZczb(Double zczb) {
        this.zczb = zczb;
    }
    public String getZfjglxmc() {
        return this.zfjglxmc;
    }
    public void setZfjglxmc(String zfjglxmc) {
        this.zfjglxmc = zfjglxmc;
    }
    public String getZgswjmc() {
        return this.zgswjmc;
    }
    public void setZgswjmc(String zgswjmc) {
        this.zgswjmc = zgswjmc;
    }
    public String getZgswskfjmc() {
        return this.zgswskfjmc;
    }
    public void setZgswskfjmc(String zgswskfjmc) {
        this.zgswskfjmc = zgswskfjmc;
    }
    public Double getZrrtzbl() {
        return this.zrrtzbl;
    }
    public void setZrrtzbl(Double zrrtzbl) {
        this.zrrtzbl = zrrtzbl;
    }
    public String getZzhm() {
        return this.zzhm;
    }
    public void setZzhm(String zzhm) {
        this.zzhm = zzhm;
    }
    public String getZzjglxmc() {
        return this.zzjglxmc;
    }
    public void setZzjglxmc(String zzjglxmc) {
        this.zzjglxmc = zzjglxmc;
    }
    public String getZzjg_dm() {
        return this.zzjg_dm;
    }
    public void setZzjg_dm(String zzjg_dm) {
        this.zzjg_dm = zzjg_dm;
    }
    public String getZzlxmc() {
        return this.zzlxmc;
    }
    public void setZzlxmc(String zzlxmc) {
        this.zzlxmc = zzlxmc;
    }

}