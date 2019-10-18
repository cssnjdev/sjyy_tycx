package com.cwks.bizcore.tycx.chart.charts.component;



/**
 * 柱状图 series 
 *
 *
 *
 * @author LIBOYI
 * @date 2015-5-27
 * @version 1.0
 *
 */
public class ChartsBarSeriesBean {
  /**
   * 堆积维度，以哪个属性堆积
   */
   private String stack;
   
   /**
    * 名称
    */
   private String name;
   
   /**
    * 图形类型
    */
   private String type="bar";
   
   private int yAxisIndex=0; //Y轴索引，从0开始，目前Echarts支持2个y轴，不写默认是0
   
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
		if("line".equals(type)){
			this.yAxisIndex=1;
		}
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


  
}
