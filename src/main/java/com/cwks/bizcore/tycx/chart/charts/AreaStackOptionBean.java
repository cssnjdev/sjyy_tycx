package com.cwks.bizcore.tycx.chart.charts;

import com.cwks.bizcore.tycx.chart.charts.component.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 面积堆积图形
 *
 *
 *
 * @author LIBOYI
 * @date 2015-6-3
 * @version 1.0
 *
 */
public class AreaStackOptionBean {
	
	private	ChartsTitleBean title=new ChartsTitleBean();
   
	private AreaChartsToolTipBean tooltip=new AreaChartsToolTipBean();
   
	private LegendBean legend=new LegendBean();
   
	private ChartsToolBoxBean toolbox;
   
   private boolean calculable=true;
   
   private List<XAxisBean> xAxis=new ArrayList<XAxisBean>();
   
   private List<YAxisBean> yAxis=new ArrayList<YAxisBean>();
   
   private List<ChartsAreaStackSeriesBean> series=new ArrayList<ChartsAreaStackSeriesBean>();


public AreaChartsToolTipBean getTooltip() {
	return tooltip;
}

public void setTooltip(AreaChartsToolTipBean tooltip) {
	this.tooltip = tooltip;
}

public LegendBean getLegend() {
	return legend;
}

public void setLegend(LegendBean legend) {
	this.legend = legend;
}


public boolean isCalculable() {
	return calculable;
}

public void setCalculable(boolean calculable) {
	this.calculable = calculable;
}



public List<XAxisBean> getxAxis() {
	return xAxis;
}

public void setxAxis(List<XAxisBean> xAxis) {
	this.xAxis = xAxis;
}

public List<YAxisBean> getyAxis() {
	return yAxis;
}

public void setyAxis(List<YAxisBean> yAxis) {
	this.yAxis = yAxis;
}


public ChartsTitleBean getTitle() {
	return title;
}

public void setTitle(ChartsTitleBean title) {
	this.title = title;
}

public List<ChartsAreaStackSeriesBean> getSeries() {
	return series;
}

public void setSeries(List<ChartsAreaStackSeriesBean> series) {
	this.series = series;
}

public ChartsToolBoxBean getToolbox() {
	return toolbox;
}

public void setToolbox(ChartsToolBoxBean toolbox) {
	this.toolbox = toolbox;
}
   
   
   
}
