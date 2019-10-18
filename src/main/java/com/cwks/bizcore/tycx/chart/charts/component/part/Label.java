package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 标签
 *
 *饼图默认显示在外部，离饼图距离由labelLine.length决定，
 *漏斗图默认显示在右侧，离图形距离由labelLine.length决定
 *地图标签不可指定位置，折线图，柱形图，K线图，散点图可指定position 
 *适用类型折线图，柱形图，K线图，散点图，饼图 ，地图，力导向，漏斗图，markPoint，markLine
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class Label {
  /**
   * 标签显示策略，可选为：true（显示） | false（隐藏） ,默认true
   */
  private boolean show=true;
  
  /**
   * 标签显示位置，地图标签不可指定位置
   *饼图可选为：'outer'（外部） | 'inner'（内部），
   *漏斗图可选为：'inner'（内部）| 'left' | 'right'（默认），
   *折线图，柱形图，K线图，散点图默认根据布局自适应为'top'或者'right'，可选的还有：'inside' | 'left' | 'bottom'
   *柱形图可选的还有，'insideLeft' | 'insideRight' | 'insideTop' | 'insideBottom' 
   *默认 	'outer' | null
   */
  private String position="outer";
  
  
  /**
   * 标签的文本样式
   */
  private TextStyle textStyle=new TextStyle();

public Label(boolean show, String position, TextStyle textStyle) {
	this.show = show;
	this.position = position;
	this.textStyle = textStyle;
}
public Label(String position) {
	this.position = position;
}
public Label(String position, TextStyle textStyle) {
	this.position = position;
	this.textStyle = textStyle;
}

public Label() {
}

public Label(boolean show) {
	this.show = show;
}

public boolean isShow() {
	return show;
}

public void setShow(boolean show) {
	this.show = show;
}

public String getPosition() {
	return position;
}

public void setPosition(String position) {
	this.position = position;
}


public TextStyle getTextStyle() {
	return textStyle;
}

public void setTextStyle(TextStyle textStyle) {
	this.textStyle = textStyle;
}
  
  
}
