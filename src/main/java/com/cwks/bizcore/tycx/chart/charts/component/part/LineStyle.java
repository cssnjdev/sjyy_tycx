package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * Echarts 设置直线指示器
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class LineStyle {
   private String color[][];    //颜色
   private String width="8";
public LineStyle(){
	
}
public String[][] getColor() {
	return color;
}
public void setColor(String[][] color) {
	this.color = color;
}
public LineStyle(String[][] color) {
	this.color = color;
}
public String getWidth() {
	return width;
}
public void setWidth(String width) {
	this.width = width;
}

}
