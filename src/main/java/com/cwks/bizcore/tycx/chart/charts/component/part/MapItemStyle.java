package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 
 *地图图形样式，可设置图表内图形的默认样式和强调样式（悬浮时样式）
 *
 *
 * @author LIBOYI
 * @date 2015-6-16
 * @version 1.0
 *
 */
public class MapItemStyle {
    private MapItemStyleNormal normal=new MapItemStyleNormal(); //图表内图形的默认样式
    private MapItemStyleEmphasis emphasis=new MapItemStyleEmphasis(); //强调样式（悬浮时样式）
	public MapItemStyleNormal getNormal() {
		return normal;
	}
	public void setNormal(MapItemStyleNormal normal) {
		this.normal = normal;
	}
	public MapItemStyleEmphasis getEmphasis() {
		return emphasis;
	}
	public void setEmphasis(MapItemStyleEmphasis emphasis) {
		this.emphasis = emphasis;
	}
        
}
