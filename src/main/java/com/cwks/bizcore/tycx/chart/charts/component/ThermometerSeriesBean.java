package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.part.ThermometerItemStyle;

/**
 * 温度计式图形数据设置
 *
 *
 * @author LIBOYI
 * @date 2015-6-4
 * @version 1.0
 *
 */
public class ThermometerSeriesBean extends ChartsSeriesBean{
	
	public ThermometerSeriesBean(){
		
	}
	
	
     public ThermometerSeriesBean(String stack,String barCategoryGap, ThermometerItemStyle itemStyle, String[] data) {
		this.stack = stack;
		this.barCategoryGap = barCategoryGap;
		this.itemStyle = itemStyle;
		this.data = data;
	}


     private String stack;
     
     /**
      *  类目间柱形距离，默认为类目间距的20%，可设固定值 
      */
     private String barCategoryGap="20%";
     
     private ThermometerItemStyle itemStyle=new ThermometerItemStyle();
     
     private String []data;



	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public String getBarCategoryGap() {
		return barCategoryGap;
	}

	public void setBarCategoryGap(String barCategoryGap) {
		this.barCategoryGap = barCategoryGap;
	}

	public ThermometerItemStyle getItemStyle() {
		return itemStyle;
	}

	public void setItemStyle(ThermometerItemStyle itemStyle) {
		this.itemStyle = itemStyle;
	}

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}
     
}
