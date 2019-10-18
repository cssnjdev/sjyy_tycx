package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 温度计式图表基本样式设置
 *
 *
 * @author LIBOYI
 * @date 2015-6-4
 * @version 1.0
 *
 */
public class ThermometerBaseItemStyle {
	
	public ThermometerBaseItemStyle(){
		
	}
	
	 
	public ThermometerBaseItemStyle( Label label) {
		this.label = label;
	}


	public ThermometerBaseItemStyle(String color, Label label) {
		this.color = color;
		this.label = label;
	}


	private String color="tomato";  //颜色
	
	private String barBorderColor="tomato";
	
	private int barBorderWidth=6;
	
	private int barBorderRadius=0;
	
	private Label label=new Label();

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBarBorderColor() {
		return barBorderColor;
	}

	public void setBarBorderColor(String barBorderColor) {
		this.barBorderColor = barBorderColor;
	}

	public int getBarBorderWidth() {
		return barBorderWidth;
	}

	public void setBarBorderWidth(int barBorderWidth) {
		this.barBorderWidth = barBorderWidth;
	}

	public int getBarBorderRadius() {
		return barBorderRadius;
	}

	public void setBarBorderRadius(int barBorderRadius) {
		this.barBorderRadius = barBorderRadius;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}
	
	
}
