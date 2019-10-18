package com.cwks.bizcore.tycx.chart.charts.component;



/**
 * 
 * 图形提示框，鼠标悬浮交互时的信息提示
 *
 * 
 *
 *
 * @author LIBOYI
 * @date 2015-6-16
 * @version 1.0
 *
 */
public class ChartsChinaMapToolTipBean {
	public ChartsChinaMapToolTipBean(){
		
	}
	
	public ChartsChinaMapToolTipBean(String trigger) {
		this.trigger = trigger;
	}
	/**
	 * 数据触发方式
	 */
	private String trigger="axis";
	public String getTrigger() {
		return trigger;
	}
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	
	
	

}
