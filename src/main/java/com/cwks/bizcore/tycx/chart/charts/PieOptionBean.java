package com.cwks.bizcore.tycx.chart.charts;

import com.cwks.bizcore.tycx.chart.charts.component.*;

import java.util.List;


/**
 * 标准环形图
 *
 *
 * @author LIBOYI
 * @date 2015-5-27
 * @version 1.0
 *
 */
public class PieOptionBean {
	/**
	 * 是否启用拖拽重计算特性，默认启用
	 */
     private boolean calculable=true;
     
     private List<ChartsPieSeriesBean> series;
     
     private ChartsToolBoxBean toolbox;
     private String[] color=new String[]{"rgb(86,143,195)","rgb(251,189,0)","rgb(152,152,152)","rgb(215,116,48)"};
     
     /**
      * 标题
      */
     private ChartsTitleBean title=new ChartsTitleBean();
     
     public String[] getColor() {
		return color;
	}

	public void setColor(String[] color) {
		this.color = color;
	}

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

   


	public List<ChartsPieSeriesBean> getSeries() {
		return series;
	}

	public ChartsToolBoxBean getToolbox() {
		return toolbox;
	}

	public void setToolbox(ChartsToolBoxBean toolbox) {
		this.toolbox = toolbox;
	}

	public void setSeries(List<ChartsPieSeriesBean> series) {
		this.series = series;
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

     

}
