package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 和弦图中的弦样式
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class ChordStyle {
 /**
  * 贝塞尔曲线的线宽, ribbonType是false时有效
  * 默认1
  */
  private int width=1;
  
  /**
   * 贝塞尔曲线的颜色, ribbonType是false时有效
   * 默认1
   */
  private String color="1";
  
  /**
   * ribbon的描边线宽, ribbonType是true时有效
   * 默认1
   */
  private String borderWidth="1";
  
  /**
   * ribbon的描边颜色, ribbonType是true时有效
   * 默认1
   */
  private String borderColor="1";

public void setWidth(int width) {
	this.width = width;
}

public void setColor(String color) {
	this.color = color;
}

public void setBorderWidth(String borderWidth) {
	this.borderWidth = borderWidth;
}

public void setBorderColor(String borderColor) {
	this.borderColor = borderColor;
}

public int getWidth() {
	return width;
}

public String getColor() {
	return color;
}

public String getBorderWidth() {
	return borderWidth;
}

public String getBorderColor() {
	return borderColor;
}
  
  
  
}
