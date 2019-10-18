package com.cwks.bizcore.tycx.chart.charts.component;


/**
 * 折线图 data 
 *
 *
 *
 * @author LIBOYI
 * @date 2015-6-4
 * @version 1.0
 *
 */
public class ChartsLineDataBean{
   
   /**
    * 名称
    */
   private String name;
   
   
   /**
    * 数据
    */
   private String[] data;

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
