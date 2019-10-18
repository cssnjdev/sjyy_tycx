package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.part.AxisPointer;

/**
 * 
 * 图形提示框，鼠标悬浮交互时的信息提示
 *
 * 
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class ChartsToolTipBean {
	public ChartsToolTipBean(){
		
	}
	
	public ChartsToolTipBean(String trigger) {
		this.trigger = trigger;
	}
    
	public ChartsToolTipBean(String trigger, String formatter) {
		this.trigger = trigger;
		this.formatter = formatter;
	}

	/**
	 * 数据触发方式
	 */
	private String trigger="axis";
	
	/**
	 * 格式化
	 */
	private String formatter;
	
    /**
     * 坐标轴指示器，坐标轴(axis)触发有效  默认为直线，可选为：'line' | 'shadow'
     */
	 private AxisPointer axisPointer=new AxisPointer();
	 
	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public AxisPointer getAxisPointer() {
		return axisPointer;
	}

	public void setAxisPointer(AxisPointer axisPointer) {
		this.axisPointer = axisPointer;
	} 
	

}
