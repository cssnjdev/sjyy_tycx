package com.cwks.bizcore.tycx.core.mapping.pojo;

import com.cwks.bizcore.tycx.core.utils.TycxUtils;

import java.util.HashMap;
import java.util.Map;

/**
* @author
*
*对应表(CX_TXPZXX)
*/

public class Tycx003CxTxpzxxPojo {

    private static final long serialVersionUID = 1L;

    private  Double fontsize; //字体大小
    private  String hzb; //横坐标
    private  String hzbdw;
    private  String hzbmc; //横坐标名称
    private  String sjylx; //数据源类型
    private  String sql; //SQL，可以自定义SQL，如果不定义，默
    private  String sqlxh; //SQLXH
    private  String title; //标题
    private  String txlx; //图形类型,txlx=barAndLine
    private  String uuid; //UUID
    private  String zzb; //纵坐标
    private  String zzbdw; //纵坐标单位
    private  String zzbmc; //纵坐标名称
    private  String sjymc;//数据源名称
    private String x;
    private String y;
    private String x2;
    private String y2;
    private String xqxds;//倾斜度数
    private String yqxds;//倾斜度数
    private String ccgcmc;//存储过程名称
    private String visbletitle;//是否显示标题
    
    public String getHzbdw() {
		return hzbdw;
	}

	public void setHzbdw(String hzbdw) {
		this.hzbdw = hzbdw;
	}

	public String getXqxds() {
		return xqxds;
	}

	public void setXqxds(String xqxds) {
		this.xqxds = xqxds;
	}

	public String getYqxds() {
		return yqxds;
	}

	public void setYqxds(String yqxds) {
		this.yqxds = yqxds;
	}

	public String getVisbletitle() {
		return visbletitle;
	}

	public void setVisbletitle(String visbletitle) {
		this.visbletitle = visbletitle;
	}

	public String getCcgcmc() {
		return ccgcmc;
	}

	public void setCcgcmc(String ccgcmc) {
		this.ccgcmc = ccgcmc;
	}

	public void setSjymc(String sjymc) {
		this.sjymc = sjymc;
	}
   
	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getX2() {
		return x2;
	}

	public void setX2(String x2) {
		this.x2 = x2;
	}

	public String getY2() {
		return y2;
	}

	public void setY2(String y2) {
		this.y2 = y2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSjymc() {
		return sjymc;
	}

	public void setString(String sjymc) {
		this.sjymc = sjymc;
	}

	/**无参构造方法**/
    public Tycx003CxTxpzxxPojo(){};

    /**带参构造方法*/
    public Tycx003CxTxpzxxPojo(Double fontsize, String hzb, String hzbdw, String hzbmc, String sjylx, String sql, String sqlxh, String title, String txlx, String uuid, String zzb, String zzbdw, String zzbmc, String sjymc, String x, String y, String x2, String y2, String ccgcmc, String visbletitle, String xqxds, String yqxds){
        this.fontsize = fontsize;
        this.hzb = hzb;
        this.hzbdw=hzbdw;
        this.hzbmc = hzbmc;
        this.sjylx = sjylx;
        this.sql = sql;
        this.sqlxh = sqlxh;
        this.title = title;
        this.txlx = txlx;
        this.uuid = uuid;
        this.zzb = zzb;
        this.zzbdw = zzbdw;
        this.zzbmc = zzbmc;
        this.sjymc=sjymc;
        this.x=x;
        this.y=y;
        this.x2=x2;
        this.y2=y2;
        this.xqxds=xqxds;
        this.yqxds=yqxds;
        this.ccgcmc=ccgcmc;
        this.visbletitle=visbletitle;
    }

    /**带Map类型参数构造方法 将Map数据按key设置到对应的属性上,从而实例对象*/
    public Tycx003CxTxpzxxPojo(Map data){
        this.fontsize = TycxUtils.isEmpty(data.get("fontsize")) ? null : Double.parseDouble((String)data.get("fontsize"));
        this.hzb = data.get("hzb") == null
            ? null : (String)data.get("hzb");
        this.hzbdw = data.get("hzbdw") == null
        ? null : (String)data.get("hzbdw");
        this.hzbmc = data.get("hzbmc") == null
            ? null : (String)data.get("hzbmc");
        this.sjylx = data.get("sjylx") == null
            ? null : (String)data.get("sjylx");
        this.sql = data.get("sql") == null
            ? null : (String)data.get("sql");
        this.sqlxh = data.get("sqlxh") == null
            ? null : (String)data.get("sqlxh");
        this.title = data.get("title") == null
            ? null : (String)data.get("title");
        this.txlx = data.get("txlx") == null
            ? null : (String)data.get("txlx");
        this.uuid = data.get("uuid") == null
            ? null : (String)data.get("uuid");
        this.zzb = data.get("zzb") == null
            ? null : (String)data.get("zzb");
        this.zzbdw = data.get("zzbdw") == null
            ? null : (String)data.get("zzbdw");
        this.zzbmc = data.get("zzbmc") == null
            ? null : (String)data.get("zzbmc");
        this.sjymc = data.get("sjymc") == null
                ? null : (String)data.get("sjymc");
        this.x = data.get("x") == null
        ? null : (String)data.get("x");
        this.y = data.get("y") == null
        ? null : (String)data.get("y");
        this.x2 = data.get("x2") == null
        ? null : (String)data.get("x2");
        this.y2 = data.get("y2") == null
        ? null : (String)data.get("y2");
        this.ccgcmc = data.get("ccgcmc") == null
        ? null : (String)data.get("ccgcmc");
        this.visbletitle = data.get("visbletitle") == null
        ? null : (String)data.get("visbletitle");
        this.xqxds = data.get("xqxds") == null
        ? null : (String)data.get("xqxds");
        this.yqxds = data.get("yqxds") == null
        ? null : (String)data.get("yqxds");
    }

    /** 将vo对象的值放入到Map对象中*/
    public Map toMap(){
        Map map = new HashMap();
        map.put("fontsize",fontsize);
        map.put("hzb",hzb);
        map.put("hzbdw",hzbdw);
        map.put("hzbmc",hzbmc);
        map.put("sjylx",sjylx);
        map.put("sql",sql);
        map.put("sqlxh",sqlxh);
        map.put("title",title);
        map.put("txlx",txlx);
        map.put("uuid",uuid);
        map.put("zzb",zzb);
        map.put("zzbdw",zzbdw);
        map.put("zzbmc",zzbmc);
        map.put("sjymc",sjymc);
        map.put("x",x);
        map.put("y",y);
        map.put("x2",x2);
        map.put("y2",y2);
        map.put("xqxds",xqxds);
        map.put("yqxds",yqxds);
        map.put("ccgcmc",ccgcmc);
        map.put("visbletitle",visbletitle);
        return map;
    }

    public String toString(){
        return toMap().toString();
    }

    //属性get||set方法
    public Double getFontsize() {
        return this.fontsize;
    }
    public void setFontsize(Double fontsize) {
        this.fontsize = fontsize;
    }
    public String getHzb() {
        return this.hzb;
    }
    public void setHzb(String hzb) {
        this.hzb = hzb;
    }
    public String getHzbmc() {
        return this.hzbmc;
    }
    public void setHzbmc(String hzbmc) {
        this.hzbmc = hzbmc;
    }
    public String getSjylx() {
        return this.sjylx;
    }
    public void setSjylx(String sjylx) {
        this.sjylx = sjylx;
    }
    public String getSql() {
        return this.sql;
    }
    public void setSql(String sql) {
        this.sql = sql;
    }
    public String getSqlxh() {
        return this.sqlxh;
    }
    public void setSqlxh(String sqlxh) {
        this.sqlxh = sqlxh;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTxlx() {
        return this.txlx;
    }
    public void setTxlx(String txlx) {
        this.txlx = txlx;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getZzb() {
        return this.zzb;
    }
    public void setZzb(String zzb) {
        this.zzb = zzb;
    }
    public String getZzbdw() {
        return this.zzbdw;
    }
    public void setZzbdw(String zzbdw) {
        this.zzbdw = zzbdw;
    }
    public String getZzbmc() {
        return this.zzbmc;
    }
    public void setZzbmc(String zzbmc) {
        this.zzbmc = zzbmc;
    }

}