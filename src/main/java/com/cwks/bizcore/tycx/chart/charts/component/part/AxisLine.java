package com.cwks.bizcore.tycx.chart.charts.component.part;



/**
 * 坐标轴线
 * 
 * 默认显示属性show控制显示与否，属性lineStyle（详见lineStyle）控制线条样式，比较特殊的是这里的lineStyle.color是一个二维数组，用于把仪表盘轴线分成若干份，
 *并且可以给每一份指定具体的颜色，格式为:[[百分比, 颜色值], [...]] 
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class AxisLine {
   private boolean show=true;
   
   private LineStyle lineStyle=new LineStyle();
   public AxisLine(){
	   
   }
   
public AxisLine(boolean show, LineStyle lineStyle) {
	this.show = show;
	this.lineStyle = lineStyle;
}

public void setShow(boolean show) {
	this.show = show;
}

public void setLineStyle(LineStyle lineStyle) {
	this.lineStyle = lineStyle;
}

public boolean isShow() {
	return show;
}

public LineStyle getLineStyle() {
	return lineStyle;
}
   
}
