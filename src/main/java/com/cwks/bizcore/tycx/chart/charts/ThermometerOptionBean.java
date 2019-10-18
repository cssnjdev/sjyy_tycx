package com.cwks.bizcore.tycx.chart.charts;

import com.cwks.bizcore.tycx.chart.charts.component.*;

import java.util.List;

/**
 * 温度计式图表
 *
 *
 * @author LIBOYI
 * @date 2015-6-4
 * @version 1.0
 *
 */
public class ThermometerOptionBean {
	public ThermometerOptionBean(){
		
	}
	/**
	 * 是否启用拖拽重计算特性，默认启用
	 */
     private boolean calculable=true;
     
     /**
      * 标题
      */
     private ChartsTitleBean title;
     
     /**
      * 图例
      */
     private LegendBean legend;
     
     /**
      * x轴     
      */
     private List<XAxisBean> xAxis;
     
     /**
      * y轴
      */
     private List<YAxisBean> yAxis;
     
     /**
      * 工具栏
      */
     private ChartsToolBoxBean toolbox;
     
     
     private ThermometerToolTipBean tooltip=new ThermometerToolTipBean();
     private List<ThermometerSeriesBean> series;

	public boolean isCalculable() {
		return calculable;
	}

	public void setCalculable(boolean calculable) {
		this.calculable = calculable;
	}


	public LegendBean getLegend() {
		return legend;
	}

	public void setLegend(LegendBean legend) {
		this.legend = legend;
	}


	public ChartsToolBoxBean getToolbox() {
		return toolbox;
	}

	public void setToolbox(ChartsToolBoxBean toolbox) {
		this.toolbox = toolbox;
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

	public List<ThermometerSeriesBean> getSeries() {
		return series;
	}

	public void setSeries(List<ThermometerSeriesBean> series) {
		this.series = series;
	}



	public ChartsTitleBean getTitle() {
		return title;
	}

	public void setTitle(ChartsTitleBean title) {
		this.title = title;
	}

	public ThermometerToolTipBean getTooltip() {
		return tooltip;
	}

	public void setTooltip(ThermometerToolTipBean tooltip) {
		this.tooltip = tooltip;
	}



     
     
}
