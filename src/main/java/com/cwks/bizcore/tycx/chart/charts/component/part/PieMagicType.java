package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 环形图动态类型切换
 *
 * 支持直角系下的折线图、柱状图、堆积、平铺转换，
 * 切换为堆积，切换为平铺，切换折线图，切换柱形图，切换为力导向布局图，切换为和弦图，切换为饼图，切换为漏斗图 
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class PieMagicType {
   private boolean show=false;
   private String type[]={"pie","funnel"};
   
   public PieMagicType(){
	
  }
   public PieMagicType(boolean show) {
		this.show = show;
	}
public PieMagicType(boolean show, String[] type) {
	this.show = show;
	this.type = type;
}
public boolean isShow() {
	return show;
}
public void setShow(boolean show) {
	this.show = show;
}
public String[] getType() {
	return type;
}
public void setType(String[] type) {
	this.type = type;
}
   
   
}
