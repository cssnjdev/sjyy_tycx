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
public class ThermometerItemStyle {
	
	public ThermometerItemStyle(){
		
	}
	
	
    public ThermometerItemStyle(ThermometerBaseItemStyle normal) {
		this.normal = normal;
	}


	private ThermometerBaseItemStyle normal=new ThermometerBaseItemStyle(); //图表内图形的默认样式

	public ThermometerBaseItemStyle getNormal() {
		return normal;
	}

	public void setNormal(ThermometerBaseItemStyle normal) {
		this.normal = normal;
	}
    
    
    
}
