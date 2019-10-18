package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.part.MapTypeControl;

/**
 * 缩放漫游组件，仅对地图有效
 *
 *
 * @author LIBOYI
 * @date 2015-6-16
 * @version 1.0
 *
 */
public class RoamController {
   public RoamController(){
	   
   }
   /**
    * 显示策略，可选为：true（显示） | false（隐藏）。默认true
    */
   private boolean show=true;
   
   /**
    * 水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' 
    */
   private String x="left";
   
   /**
    * 垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' 
    */
   private String y="top";
   
   /**
    * 必须，指定漫游组件可控地图类型，如：{ china: true }当同一图表内同时呈现多个地图时，可以单独指定所需控制地图类型，如：{ china: false, '北京': true}
    */
   private MapTypeControl mapTypeControl;

public boolean isShow() {
	return show;
}

public void setShow(boolean show) {
	this.show = show;
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

public MapTypeControl getMapTypeControl() {
	return mapTypeControl;
}

public void setMapTypeControl(MapTypeControl mapTypeControl) {
	this.mapTypeControl = mapTypeControl;
}
   
   
}
