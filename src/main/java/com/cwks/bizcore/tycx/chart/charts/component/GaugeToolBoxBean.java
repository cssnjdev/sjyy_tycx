package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.part.Feature;
import com.cwks.bizcore.tycx.chart.charts.component.part.TextStyle;

/**
 * 仪表盘用工具箱，每个图表最多仅有一个工具箱
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class GaugeToolBoxBean {
  private boolean show=false;  //显示策略，可选为：true（显示） | false（隐藏） ，默认false
  
  private String x="right"; //水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px） ,默认right
  private String y="top";   //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px） ，默认top
  private boolean showTitle=true;        //是否显示工具箱文字提示，默认启用
  private TextStyle textStyle=new TextStyle();
  private Feature feature=new Feature();
public void setShow(boolean show) {
	this.show = show;
}

public void setX(String x) {
	this.x = x;
}
public void setY(String y) {
	this.y = y;
}

public void setShowTitle(boolean showTitle) {
	this.showTitle = showTitle;
}
public void setTextStyle(TextStyle textStyle) {
	this.textStyle = textStyle;
}
public void setFeature(Feature feature) {
	this.feature = feature;
}
public boolean isShow() {
	return show;
}

public String getX() {
	return x;
}
public String getY() {
	return y;
}


public boolean isShowTitle() {
	return showTitle;
}
public TextStyle getTextStyle() {
	return textStyle;
}
public Feature getFeature() {
	return feature;
}
  
  
}
