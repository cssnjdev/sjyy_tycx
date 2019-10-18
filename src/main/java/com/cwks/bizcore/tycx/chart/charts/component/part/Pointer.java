package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * Echarts指针样式
 *
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class Pointer {
  /**
   * 控制线长，百分比相对的是仪表盘的外半径 
   */
  private String length ="80%";
  
  /**
   * 控制指针最宽处，默认8
   */
  private int width =8;
  
  /**
   * 控制指针颜色 
   */
  private String color ="auto";

public void setLength(String length) {
	this.length = length;
}

public void setWidth(int width) {
	this.width = width;
}

public void setColor(String color) {
	this.color = color;
}

public String getLength() {
	return length;
}

public int getWidth() {
	return width;
}

public String getColor() {
	return color;
}
  
}
