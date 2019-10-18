package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 指定漫游组件可控地图类型
 *
 * 必须，，如：{ china: true }当同一图表内同时呈现多个地图时，可以单独指定所需控制地图类型，
 * 如：{ china: false, '北京': true} 
 *
 *
 * @author LIBOYI
 * @date 2015-6-16
 * @version 1.0
 *
 */
public class MapTypeControl {
	public MapTypeControl(){
		
	}
	
    public MapTypeControl(boolean china) {
		this.china = china;
	}
	private boolean china;

	public boolean isChina() {
		return china;
	}

	public void setChina(boolean china) {
		this.china = china;
	}
    
}
