package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * Echarts动态类型切换title设置
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class MagicTypeTitle {
   private String line="折线图切换";
   private String bar="柱形图切换";
   private String stack="堆积";
   private String tiled="平铺";
   private String force="力导向布局图切换";
   private String chord="和弦图切换";
   private String pie="饼图切换";
   private String funnel="漏斗图切换";
public void setLine(String line) {
	this.line = line;
}
public void setBar(String bar) {
	this.bar = bar;
}
public void setStack(String stack) {
	this.stack = stack;
}
public void setTiled(String tiled) {
	this.tiled = tiled;
}
public void setForce(String force) {
	this.force = force;
}
public void setChord(String chord) {
	this.chord = chord;
}
public void setPie(String pie) {
	this.pie = pie;
}
public void setFunnel(String funnel) {
	this.funnel = funnel;
}
public String getLine() {
	return line;
}
public String getBar() {
	return bar;
}
public String getStack() {
	return stack;
}
public String getTiled() {
	return tiled;
}
public String getForce() {
	return force;
}
public String getChord() {
	return chord;
}
public String getPie() {
	return pie;
}
public String getFunnel() {
	return funnel;
}
   
}
