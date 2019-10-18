package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 
 * 漏斗图设置
 *
 *
 * @author LIBOYI
 * @date 2015-5-29
 * @version 1.0
 *
 */
public class Funnel {
  public Funnel(){
	  
  }
  
  public Funnel(String x, String width, String funnelAlign) {
	this.x = x;
	this.width = width;
	this.funnelAlign = funnelAlign;
}

private String x="25%";
   private String width="50%";
   private String funnelAlign="center";
public String getX() {
	return x;
}
public void setX(String x) {
	this.x = x;
}
public String getWidth() {
	return width;
}
public void setWidth(String width) {
	this.width = width;
}
public String getFunnelAlign() {
	return funnelAlign;
}
public void setFunnelAlign(String funnelAlign) {
	this.funnelAlign = funnelAlign;
}
   
}
