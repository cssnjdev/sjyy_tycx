package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 主分隔线
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class SplitLine {
  private boolean show= true; //控制显示与否
  private int length=30;  //控制线长 
  private LineStyle lineStyle=new LineStyle(); //控制线条样式
public boolean isShow() {
	return show;
}
public void setShow(boolean show) {
	this.show = show;
}
public int getLength() {
	return length;
}
public void setLength(int length) {
	this.length = length;
}
public LineStyle getLineStyle() {
	return lineStyle;
}
public void setLineStyle(LineStyle lineStyle) {
	this.lineStyle = lineStyle;
}
  
  
}
