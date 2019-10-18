package com.cwks.bizcore.tycx.chart.charts.component.part;



/**
 *Echarts坐标轴指示器
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class AxisPointer {
	
	public AxisPointer(){
		
	}
	
   public AxisPointer(String type) {
		this.type = type;
	}

private String type="line";
public void setType(String type) {
	this.type = type;
}
public String getType() {
	return type;
}
   
}
