package com.cwks.bizcore.tycx.chart.charts;

import com.cwks.bizcore.tycx.chart.charts.component.*;

import java.util.List;

/**
 * 玫瑰图
 *
 *
 * @author LIBOYI
 * @date 2015-6-12
 * @version 1.0
 *
 */
public class RoseDiagramOptionBean {
	/**
	 * 是否启用拖拽重计算特性，默认启用
	 */
     private boolean calculable=true;
     
     private List<ChartsRoseSeriesBean> series;
     
     private ChartsToolBoxBean toolbox;
     
     /**
      * 标题
      */
     private ChartsTitleBean title=new ChartsTitleBean();
     
     /**
      * 工具提示
      */
     private ChartsToolTipBean tooltip=new ChartsToolTipBean();
     
     /**
      * 图例
      */
     private LegendBean legend=new LegendBean("center", "bottom", "horizontal");
     
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


	public ChartsTitleBean getTitle() {
		return title;
	}

	public void setTitle(ChartsTitleBean title) {
		this.title = title;
	}

	public ChartsToolTipBean getTooltip() {
		return tooltip;
	}

	public void setTooltip(ChartsToolTipBean tooltip) {
		this.tooltip = tooltip;
	}

	public List<ChartsRoseSeriesBean> getSeries() {
		return series;
	}

	public void setSeries(List<ChartsRoseSeriesBean> series) {
		this.series = series;
	}

     

}
