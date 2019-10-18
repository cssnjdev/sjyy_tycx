package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.ChartsDataBean;
import com.cwks.bizcore.tycx.chart.charts.component.part.GaugeAxisLine;
import com.cwks.bizcore.tycx.chart.charts.component.part.GaugeDetail;

import java.util.List;

/**
 *  series（仪表盘） 
 *
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class ChartsGaugeSeriesBean extends ChartsSeriesBean{
   /**
    * 仪表盘详情 
    */
   private GaugeDetail detail=new GaugeDetail();
   
   private List<ChartsDataBean> data; //数据
   String color[][]={{"0.2","#ff9999"},{"0.8","#484574"},{"1","#228b22"}};
   private GaugeAxisLine axisLine;


public GaugeDetail getDetail() {
	return detail;
}

public void setDetail(GaugeDetail detail) {
	this.detail = detail;
}



public List<ChartsDataBean> getData() {
	return data;
}

public void setData(List<ChartsDataBean> data) {
	this.data = data;
}

public String[][] getColor() {
	return color;
}

public void setColor(String[][] color) {
	this.color = color;
}

public GaugeAxisLine getAxisLine() {
	return axisLine;
}

public void setAxisLine(GaugeAxisLine axisLine) {
	this.axisLine = axisLine;
}


   
}
