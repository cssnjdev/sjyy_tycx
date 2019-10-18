package com.cwks.bizcore.tycx.chart.charts;

import com.cwks.bizcore.tycx.chart.charts.component.*;

import java.util.List;

/**
 * 中国地图
 *
 *
 * @author LIBOYI
 * @date 2015-6-16
 * @version 1.0
 *
 */
public class ChinaMapOptionBean {
	public ChinaMapOptionBean(){
		
	}
	
     public ChinaMapOptionBean(ChartsToolBoxBean toolbox, ChartsTitleBean title,
			ChartsChinaMapToolTipBean tooltip, LegendBean legend,
			DataRange dataRange, RoamController roamController,
			List<ChartsMapSeriesBean> series) {
		this.toolbox = toolbox;
		this.title = title;
		this.tooltip = tooltip;
		this.legend = legend;
		this.dataRange = dataRange;
		this.roamController = roamController;
		this.series = series;
	}


	/**
      * 工具栏
      */
     private ChartsToolBoxBean toolbox;
     
     /**
      * 标题
      */
     private ChartsTitleBean title;
     
     /**
      * 工具提示
      */
     private ChartsChinaMapToolTipBean tooltip;
     
     /**
      * 图例
      */
     private LegendBean legend;
     
     /**
      * 值域漫游
      */
     private DataRange dataRange;
     
     /**
      * 指定漫游组件可控地图类型
      */
     private RoamController roamController;

     private List<ChartsMapSeriesBean> series;
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


	public ChartsChinaMapToolTipBean getTooltip() {
		return tooltip;
	}

	public void setTooltip(ChartsChinaMapToolTipBean tooltip) {
		this.tooltip = tooltip;
	}

	public LegendBean getLegend() {
		return legend;
	}

	public void setLegend(LegendBean legend) {
		this.legend = legend;
	}

	public DataRange getDataRange() {
		return dataRange;
	}

	public void setDataRange(DataRange dataRange) {
		this.dataRange = dataRange;
	}

	public RoamController getRoamController() {
		return roamController;
	}

	public void setRoamController(RoamController roamController) {
		this.roamController = roamController;
	}

	public List<ChartsMapSeriesBean> getSeries() {
		return series;
	}

	public void setSeries(List<ChartsMapSeriesBean> series) {
		this.series = series;
	}
     

     

}
