package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.part.*;

/**
 * 环形图用工具箱，每个图表最多仅有一个工具箱
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class PieToolBoxBean {
public PieToolBoxBean(){
	
}
public PieToolBoxBean(boolean show) {
	this.show = show;
}
  public PieToolBoxBean(boolean show, String x, String y, String[] type, Feature feature) {
	this.show = show;
	this.type = type;
	this.feature = feature;
}

private boolean show=false;  //显示策略，可选为：true（显示） | false（隐藏） ，默认false
  
  String type[]={"pie", "funnel"};
  private Feature feature=new Feature(new Mark(false),new DataView(true),
			new MagicType(true,type),new  Restore(true),new  SaveAsImage(false));
public void setShow(boolean show) {
	this.show = show;
}
public void setFeature(Feature feature) {
	this.feature = feature;
}
public boolean isShow() {
	return show;
}
public Feature getFeature() {
	return feature;
}
  
  
}
