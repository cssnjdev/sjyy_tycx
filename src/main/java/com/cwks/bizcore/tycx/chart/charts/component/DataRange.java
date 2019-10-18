package com.cwks.bizcore.tycx.chart.charts.component;
/**
 * 值域选择，每个图表最多仅有一个值域控件
 *
 *
 * @author LIBOYI
 * @date 2015-6-16
 * @version 1.0
 *
 */
public class DataRange {
	
	public DataRange(){
		
	}
	
  /**
   * 显示策略，可选为：true（显示） | false（隐藏）, 默认true
   */
  private boolean show=true;
  
  /**
   * 指定的最小值，eg: 0，默认无，必须参数，唯有指定了splitList时可缺省min
   */
  private int min;
  
  /**
   * 指定的最大值，eg: 100，默认无，必须参数，唯有指定了splitList时可缺省max
   */
  private int max;
  
  /**
   * 水平安放位置，默认为全图左对齐，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px） 
   */
  private String x="left";
  
  /**
   * 垂直安放位置，默认为全图底部，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px） 
   */
  private String y="bottom";
  
  /**
   * 值域文字显示，splitNumber生效时默认以计算所得数值作为值域文字显示，可指定长度为2的文本数组显示简介的值域文本，如['高', '低']，'\n'指定换行
   */
  private String text[]={"高","低"};
  
  /**
   * 是否启用值域漫游，启用后无视splitNumber和splitList，值域显示为线性渐变 
   */
  private boolean calculable=true;
  

public boolean isShow() {
	return show;
}

public void setShow(boolean show) {
	this.show = show;
}

public int getMin() {
	return min;
}

public void setMin(int min) {
	this.min = min;
}

public int getMax() {
	return max;
}

public void setMax(int max) {
	this.max = max;
}

public String getX() {
	return x;
}

public void setX(String x) {
	this.x = x;
}

public String getY() {
	return y;
}

public void setY(String y) {
	this.y = y;
}

public String[] getText() {
	return text;
}

public void setText(String[] text) {
	this.text = text;
}

public boolean isCalculable() {
	return calculable;
}

public void setCalculable(boolean calculable) {
	this.calculable = calculable;
}

  
  
}
