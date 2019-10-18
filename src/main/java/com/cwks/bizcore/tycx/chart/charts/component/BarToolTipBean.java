package com.cwks.bizcore.tycx.chart.charts.component;
/**
 * 
 * 柱状、折线用工具提示设置
 *
 *
 * @author LIBOYI
 * @date 2015-5-28
 * @version 1.0
 *
 */
public class BarToolTipBean {
  public BarToolTipBean(){
	  
  }
  
  public BarToolTipBean(String trigger) {
	this.trigger = trigger;
}

/**
   * 触发类型，默认数据触发，可选为：'item' | 'axis' 
   */
  private String trigger="item"; 

	public String getTrigger() {
		return trigger;
	}
	
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
  
   
}
