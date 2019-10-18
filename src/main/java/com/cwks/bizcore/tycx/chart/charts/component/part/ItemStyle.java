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
public class ItemStyle {
    private ItemStyleNormal normal=new ItemStyleNormal(); //图表内图形的默认样式
    private ItemStyleEmphasis emphasis=new ItemStyleEmphasis(); //强调样式（悬浮时样式）
	public ItemStyleNormal getNormal() {
		return normal;
	}
	public void setNormal(ItemStyleNormal normal) {
		this.normal = normal;
	}
	public ItemStyleEmphasis getEmphasis() {
		return emphasis;
	}
	public void setEmphasis(ItemStyleEmphasis emphasis) {
		this.emphasis = emphasis;
	}
    
}
