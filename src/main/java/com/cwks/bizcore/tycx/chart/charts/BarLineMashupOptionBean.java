package com.cwks.bizcore.tycx.chart.charts;

import com.cwks.bizcore.tycx.chart.charts.component.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 柱状图
 *
 *
 * @author LIBOYI
 * @date 2015-5-27
 * @version 1.0
 *
 */
public class BarLineMashupOptionBean {
	/**
	 * 是否启用拖拽重计算特性，默认启用
	 */
     private boolean calculable=true;
     
     private List<ChartsBarSeriesBean> series=new ArrayList<ChartsBarSeriesBean>();
     
     private ChartsToolBoxBean toolbox; 
     
     /**
      * 标题
      */
     private ChartsTitleBean title=new ChartsTitleBean();
     
     /**
      * 工具提示
      */
     private BarToolTipBean tooltip=new BarToolTipBean();
     
     /**
      * 图例
      */
     private LegendBean legend=new LegendBean();
     
     /**
      * x轴     
      */
     private XAxisBean xAxis=new XAxisBean();
     
     /**
      * y轴
      */
     private List<YAxisBean> yAxis=new ArrayList<YAxisBean>();

	public boolean isCalculable() {
		return calculable;
	}

	public void setCalculable(boolean calculable) {
		this.calculable = calculable;
	}

	public List<ChartsBarSeriesBean> getSeries() {
		return series;
	}

	public void setSeries(List<ChartsBarSeriesBean> series) {
		this.series = series;
	}



	public ChartsTitleBean getTitle() {
		return title;
	}

	public void setTitle(ChartsTitleBean title) {
		this.title = title;
	}

	public BarToolTipBean getTooltip() {
		return tooltip;
	}

	public void setTooltip(BarToolTipBean tooltip) {
		this.tooltip = tooltip;
	}

	public LegendBean getLegend() {
		return legend;
	}

	public void setLegend(LegendBean legend) {
		this.legend = legend;
	}

	public XAxisBean getxAxis() {
		return xAxis;
	}

	public void setxAxis(XAxisBean xAxis) {
		this.xAxis = xAxis;
	}


	public List<YAxisBean> getyAxis() {
		return yAxis;
	}

	public void setyAxis(List<YAxisBean> yAxis) {
		this.yAxis = yAxis;
	}

	public ChartsToolBoxBean getToolbox() {
		return toolbox;
	}

	public void setToolbox(ChartsToolBoxBean toolbox) {
		this.toolbox = toolbox;
	}



     
     

}
