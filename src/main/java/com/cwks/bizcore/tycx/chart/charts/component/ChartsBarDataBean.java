package com.cwks.bizcore.tycx.chart.charts.component;



/**
 * 柱状图 series 
 *
 *
 *
 * @author LIBOYI
 * @date 2015-6-4
 * @version 1.0
 *
 */
public class ChartsBarDataBean {
  /**
   * 堆积维度，以哪个属性堆积，堆积图用，标准柱状图（普通柱状图）不需要设值
   */
   private String stack;
   
   /**
    * 名称
    */
   private String name;
   
   /**
    * 数据
    */
   private String[] data;
	public String getStack() {
		return stack;
	}
	
	public void setStack(String stack) {
		this.stack = stack;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public String[] getData() {
		return data;
	}
	
	public void setData(String[] data) {
		this.data = data;
	}


  
}
