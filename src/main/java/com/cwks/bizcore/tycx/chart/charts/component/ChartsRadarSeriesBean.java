package com.cwks.bizcore.tycx.chart.charts.component;

import java.util.List;

public class ChartsRadarSeriesBean extends ChartsSeriesBean{
	 //private String []data; //数据  
	   private List<RadarDataBean> data;
       public List<RadarDataBean> getData() {
		return data;
	}
	public void setData(List<RadarDataBean> data) {
		this.data = data;
	}
	private String name;
       private String type;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}
