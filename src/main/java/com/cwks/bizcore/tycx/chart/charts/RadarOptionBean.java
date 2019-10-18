package com.cwks.bizcore.tycx.chart.charts;

import com.cwks.bizcore.tycx.chart.charts.component.ChartsRadarSeriesBean;
import com.cwks.bizcore.tycx.chart.charts.component.ChartsToolTipBean;
import com.cwks.bizcore.tycx.chart.charts.component.part.ChartsGridBean;
import com.cwks.bizcore.tycx.chart.charts.component.part.Radar;
import com.cwks.bizcore.tycx.chart.charts.component.part.RadarTitle;

import java.util.List;

/**
 * 雷达图
 * @author Administrator
 *
 */
public class RadarOptionBean {
	private RadarTitle title;
	private ChartsToolTipBean tooltip;
	private List<ChartsRadarSeriesBean> series;
	private Radar radar;
	private ChartsGridBean grid=new ChartsGridBean();
	public ChartsGridBean getGrid() {
		return grid;
	}
	public void setGrid(ChartsGridBean grid) {
		this.grid = grid;
	}
	public RadarOptionBean(){
		
	}
	public List<ChartsRadarSeriesBean> getSeries() {
		return series;
	}



	public void setSeries(List<ChartsRadarSeriesBean> series) {
		this.series = series;
	}



	public Radar getRadar() {
		return radar;
	}

	public void setRadar(Radar radar) {
		this.radar = radar;
	}

	public ChartsToolTipBean getTooltip() {
		return tooltip;
	}

	public void setTooltip(ChartsToolTipBean tooltip) {
		this.tooltip = tooltip;
	}
	public RadarTitle getTitle() {
		return title;
	}
	public void setTitle(RadarTitle title) {
		this.title = title;
	}

}
