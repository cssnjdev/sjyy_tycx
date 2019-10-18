package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 
 *设置温度计式图形样式，可设置图表内图形的默认样式和强调样式（悬浮时样式）
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class FunnelItemStyle {
	
	public FunnelItemStyle(){
		
	}
    
	public FunnelItemStyle(FunnelBaseItemStyle normal) {
		this.normal = normal;
	}

	private FunnelBaseItemStyle normal=new FunnelBaseItemStyle(); //图表内图形的默认样式

	public FunnelBaseItemStyle getNormal() {
		return normal;
	}

	public void setNormal(FunnelBaseItemStyle normal) {
		this.normal = normal;
	}
    
}
