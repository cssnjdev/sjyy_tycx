package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * Echarts 设置阴影指示器
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class ShadowStyle {
   private String color;
   private String width;
   private String type;
public ShadowStyle(){
	
}

public ShadowStyle(String color, String width, String type) {
	this.color = color;
	this.width = width;
	this.type = type;
}

public String getColor() {
	return color;
}
public void setColor(String color) {
	this.color = color;
}
public String getWidth() {
	return width;
}
public void setWidth(String width) {
	this.width = width;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
   
}
