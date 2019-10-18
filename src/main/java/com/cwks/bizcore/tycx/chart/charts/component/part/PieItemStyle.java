package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 
 *图形样式，可设置图表内图形的默认样式和强调样式（悬浮时样式）
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class PieItemStyle {
    private PieItemStyleNormal normal=new PieItemStyleNormal(); //图表内图形的默认样式
    private PieItemStyleEmphasis emphasis=new PieItemStyleEmphasis(); //强调样式（悬浮时样式）
	public PieItemStyleNormal getNormal() {
		return normal;
	}
	public void setNormal(PieItemStyleNormal normal) {
		this.normal = normal;
	}
	public PieItemStyleEmphasis getEmphasis() {
		return emphasis;
	}
	public void setEmphasis(PieItemStyleEmphasis emphasis) {
		this.emphasis = emphasis;
	}
    
}
