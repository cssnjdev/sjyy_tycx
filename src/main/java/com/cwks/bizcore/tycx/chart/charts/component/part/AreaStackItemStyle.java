package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 面积堆积用图形样式，可设置图表内图形的默认样式和强调样式（悬浮时样式）
 *
 * @author LIBOYI
 * @date 2015-6-3
 * @version 1.0
 *
 */
public class AreaStackItemStyle {
	
	public AreaStackItemStyle(){
		
	}
	
	
    public AreaStackItemStyle(AreaStackNormalItemStyle normal) {
		this.normal = normal;
	}


	private AreaStackNormalItemStyle normal=new AreaStackNormalItemStyle();

	public AreaStackNormalItemStyle getNormal() {
		return normal;
	}

	public void setNormal(AreaStackNormalItemStyle normal) {
		this.normal = normal;
	}
    
}
