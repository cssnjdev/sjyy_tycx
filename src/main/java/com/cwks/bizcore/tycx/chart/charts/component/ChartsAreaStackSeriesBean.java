package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.part.AreaStackItemStyle;

/**
 *面积堆积图 series 
 *
 *
 *
 * @author LIBOYI
 * @date 2015-6-3
 * @version 1.0
 *
 */
public class ChartsAreaStackSeriesBean extends ChartsSeriesBean{
	
  public ChartsAreaStackSeriesBean(){
	  
  }
  
  public ChartsAreaStackSeriesBean(String stack,
		int yAxisIndex, String[] data, AreaStackItemStyle itemStyle) {
	this.stack = stack;
	this.yAxisIndex = yAxisIndex;
	this.data = data;
	this.itemStyle = itemStyle;
}

/**
   * 堆积维度，以哪个属性堆积
   */
   private String stack;
   
   
   private int yAxisIndex=0; //Y轴索引，从0开始，目前Echarts支持2个y轴，不写默认是0
   
   /**
    * 数据
    */
   private String[] data;
   
   private AreaStackItemStyle itemStyle=new AreaStackItemStyle();
	public String getStack() {
		return stack;
	}
	
	public void setStack(String stack) {
		this.stack = stack;
	}
	
	
	
	public String[] getData() {
		return data;
	}
	
	public void setData(String[] data) {
		this.data = data;
	}

	public int getyAxisIndex() {
		return yAxisIndex;
	}


	public void setyAxisIndex(int yAxisIndex) {
		this.yAxisIndex = yAxisIndex;
	}

	public AreaStackItemStyle getItemStyle() {
		return itemStyle;
	}

	public void setItemStyle(AreaStackItemStyle itemStyle) {
		this.itemStyle = itemStyle;
	}
	
	


  
}
