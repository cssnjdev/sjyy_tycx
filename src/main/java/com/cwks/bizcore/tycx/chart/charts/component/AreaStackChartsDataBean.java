package com.cwks.bizcore.tycx.chart.charts.component;


/**
 *面积堆积图 data 
 *
 *
 *
 * @author LIBOYI
 * @date 2015-6-4
 * @version 1.0
 *
 */
public class AreaStackChartsDataBean {
	
  public AreaStackChartsDataBean(){
	  
  }
  
  public AreaStackChartsDataBean(String stack, String name,
		String[] data) {
	this.stack = stack;
	this.name = name;
	this.data = data;
}
  /**
   * 堆积属性，可为空，此属性为堆积面积图用，使用标准面积图，请勿用此属性
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

public String getStack() {
	return stack;
}

public void setStack(String stack) {
	this.stack = stack;
}
   
 
	
	


  
}
