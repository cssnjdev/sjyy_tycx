package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.part.AxisLabel;
import com.cwks.bizcore.tycx.chart.charts.component.part.SplitArea;

/**
 * Y轴
 *
 *
 * @author LIBOYI
 * @date 2015-5-27
 * @version 1.0
 *
 */
public class YAxisBean {
    private String type="value";
    private String name;
    private AxisLabel axisLabel=new AxisLabel();
    private SplitArea splitArea=new SplitArea(true);
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SplitArea getSplitArea() {
		return splitArea;
	}
	public void setSplitArea(SplitArea splitArea) {
		this.splitArea = splitArea;
	}
	public AxisLabel getAxisLabel() {
		return axisLabel;
	}
	public void setAxisLabel(AxisLabel axisLabel) {
		this.axisLabel = axisLabel;
	}
    
}
