package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * Echarts启用功能框选区域缩放title设置
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class DataZoomTitle {
   private boolean dataZoom =true; //区域缩放
   private boolean dataZoomReset=true; //区域缩放后退
public void setDataZoom(boolean dataZoom) {
	this.dataZoom = dataZoom;
}
public void setDataZoomReset(boolean dataZoomReset) {
	this.dataZoomReset = dataZoomReset;
}
public boolean isDataZoom() {
	return dataZoom;
}
public boolean isDataZoomReset() {
	return dataZoomReset;
}
   
}
