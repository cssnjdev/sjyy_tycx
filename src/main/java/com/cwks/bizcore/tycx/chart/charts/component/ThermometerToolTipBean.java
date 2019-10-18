package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.part.AxisPointer;

/**
 * 温度计式图表工具提示设置
 *
 *
 * @author LIBOYI
 * @date 2015-6-4
 * @version 1.0
 *
 */
public class ThermometerToolTipBean {
	/**
	 * 数据触发方式，
	 */
    private String trigger="axis";  
    
    /**
     * 坐标轴指示器，坐标轴(axis)触发有效  默认为直线，可选为：'line' | 'shadow'
     */
    private AxisPointer axisPointer=new AxisPointer("shadow");

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public AxisPointer getAxisPointer() {
		return axisPointer;
	}

	public void setAxisPointer(AxisPointer axisPointer) {
		this.axisPointer = axisPointer;
	}
    

    
    
    
    
}
