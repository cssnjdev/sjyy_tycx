package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * Echarts保存为图片设置
 *
 * 保存图片（IE8-不支持）
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class SaveAsImage {
   private boolean show=false;
   public SaveAsImage(){
	   
   }
   
public SaveAsImage(boolean show) {
	this.show = show;
}

public boolean isShow() {
	return show;
}
public void setShow(boolean show) {
	this.show = show;
}
   
   
}
