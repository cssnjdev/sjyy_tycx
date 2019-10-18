package com.cwks.bizcore.tycx.chart.charts;

import com.cwks.bizcore.tycx.chart.charts.component.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 漏斗图
 *
 *
 *
 * @author LIBOYI
 * @date 2015-6-4
 * @version 1.0
 *
 */
public class FunnelOptionBean {
	public FunnelOptionBean(){
		
	}
	
	public FunnelOptionBean(ChartsTitleBean title, ChartsToolTipBean tooltip,
			LegendBean legend, ChartsToolBoxBean toolbox, boolean calculable,
			List<ChartsFunnelSeriesBean> series) {
		this.title = title;
		this.tooltip = tooltip;
		this.legend = legend;
		this.toolbox = toolbox;
		this.calculable = calculable;
		this.series = series;
	}

	//标题
	private ChartsTitleBean title=new ChartsTitleBean();
	//工具提示
	private ChartsToolTipBean tooltip=new ChartsToolTipBean("item","{a} <br/>{b} : {c}%");
	//图例
	private LegendBean legend=new LegendBean();
	//工具箱
	private ChartsToolBoxBean toolbox=new ChartsToolBoxBean();
	//拖拽重计算能力
	private boolean calculable=true;
	//数据
    private List<ChartsFunnelSeriesBean> series=new ArrayList<ChartsFunnelSeriesBean>();
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
	public boolean isCalculable() {
		return calculable;
	}
	public void setCalculable(boolean calculable) {
		this.calculable = calculable;
	}

	public List<ChartsFunnelSeriesBean> getSeries() {
		return series;
	}

	public void setSeries(List<ChartsFunnelSeriesBean> series) {
		this.series = series;
	}

    
	
	
}
