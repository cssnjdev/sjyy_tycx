package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.part.Feature;

/**
 * 工具箱，每个图表最多仅有一个工具箱
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class ChartsToolBoxBean {
	public ChartsToolBoxBean(){
		
	}
	
  public ChartsToolBoxBean(boolean show) {
		this.show = show;
  }
  public ChartsToolBoxBean(boolean show, Feature feature) {
		this.show = show;
		this.feature = feature;
   }


private boolean show=true;  //显示策略，可选为：true（显示） | false（隐藏） ，默认false
  
  private Feature feature=new Feature();

  private String x="right";
  private String y="top";
  
  private String orient="horizontal";
public boolean isShow() {
	return show;
}

public void setShow(boolean show) {
	this.show = show;
}

public Feature getFeature() {
	return feature;
}

public void setFeature(Feature feature) {
	this.feature = feature;
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

public String getOrient() {
	return orient;
}

public void setOrient(String orient) {
	this.orient = orient;
}
  
  
  
  
}
