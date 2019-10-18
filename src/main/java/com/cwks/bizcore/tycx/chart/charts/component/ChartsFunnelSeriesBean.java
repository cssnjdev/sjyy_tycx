package com.cwks.bizcore.tycx.chart.charts.component;

import java.util.List;


/**
 * 
 *漏斗图数据设置
 *
 *
 * @author LIBOYI
 * @date 2015-6-4
 * @version 1.0
 *
 */
public class ChartsFunnelSeriesBean extends ChartsSeriesBean{
	public ChartsFunnelSeriesBean(){
		
	}
	
    public ChartsFunnelSeriesBean(List<ChartsDataBean> data) {
		this.data = data;
	}

    private String width="40%";
    private List<ChartsDataBean> data;
	public List<ChartsDataBean> getData() {
		return data;
	}
	public void setData(List<ChartsDataBean> data) {
		this.data = data;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

}
