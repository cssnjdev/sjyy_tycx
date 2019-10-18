package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * Echarts数据视图设置
 *
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class DataView {
  private boolean show=false;
  private boolean readOnly=false; // 默认数据视图为只读，可指定readOnly为false打开编辑功能
  public DataView(){
	  
  }
  
public DataView(boolean show,boolean readOnly) {
	this.show = show;
	this.readOnly = readOnly;
}

public DataView(boolean show) {
	this.show = show;
}

public void setShow(boolean show) {
	this.show = show;
}
public void setReadOnly(boolean readOnly) {
	this.readOnly = readOnly;
}
public boolean isShow() {
	return show;
}
public boolean isReadOnly() {
	return readOnly;
}
  
}
