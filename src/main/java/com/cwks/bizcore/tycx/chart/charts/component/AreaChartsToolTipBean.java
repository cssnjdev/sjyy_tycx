package com.cwks.bizcore.tycx.chart.charts.component;


/**
 * 
 * 面积图提示框，鼠标悬浮交互时的信息提示
 *
 * 
 *
 *
 * @author LIBOYI
 * @date 2015-6-8
 * @version 1.0
 *
 */
public class AreaChartsToolTipBean {
	public AreaChartsToolTipBean(){
		
	}
	
    
	public AreaChartsToolTipBean(String trigger) {
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
