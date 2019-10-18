package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 漏斗图基本样式设置
 *
 *
 * @author LIBOYI
 * @date 2015-6-4
 * @version 1.0
 *
 */
public class FunnelBaseItemStyle {
	
	public FunnelBaseItemStyle(){
		
	}
	

	private Label label=new Label();
	public Label getLabel() {
		return label;
	}
	public void setLabel(Label label) {
		this.label = label;
	}
	public FunnelBaseItemStyle(Label label) {
		this.label = label;
	}
    
    
	 
}
