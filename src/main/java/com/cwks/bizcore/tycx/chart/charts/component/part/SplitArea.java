package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 
 * 分隔区域，默认不显示
 *
 *
 * @author LIBOYI
 * @date 2015-5-27
 * @version 1.0
 *
 */
public class SplitArea {
   private boolean show=false;
   
   public SplitArea(){
	   
   }
   
public SplitArea(boolean show) {
	this.show = show;
}

public boolean isShow() {
	return show;
}

public void setShow(boolean show) {
	this.show = show;
}
   
}
