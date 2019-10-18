package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 框选区域缩放
 *
 * Echarts启用功能属性datazoom
 * 框选区域缩放，自动与存在的dataZoom控件同步
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class DataZoom {
  private boolean show=false;
  private DataZoomTitle title=new DataZoomTitle();
  public DataZoom(){
	  
  }
  
public DataZoom(boolean show) {
	this.show = show;
}

public DataZoom(boolean show, DataZoomTitle title) {
	this.show = show;
	this.title = title;
}

public void setShow(boolean show) {
	this.show = show;
}
public void setTitle(DataZoomTitle title) {
	this.title = title;
}
public boolean isShow() {
	return show;
}
public DataZoomTitle getTitle() {
	return title;
}
  
}
