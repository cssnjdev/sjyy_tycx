package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * Echarts 区域填充样式
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class AreaStyle {
  
public AreaStyle(){
	
}


  public AreaStyle(String type) {
	this.type = type;
}


/**
   * 填充样式，目前仅支持'default'(实填充) 
   * 默认default
   */
  private String type="default";


public void setType(String type) {
	this.type = type;
}

public String getType() {
	return type;
}

  
}
