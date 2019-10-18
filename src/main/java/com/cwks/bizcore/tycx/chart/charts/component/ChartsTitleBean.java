package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.part.TextStyle;

/**
 * 
 * 图形标题
 *
 * 标题，每个图表最多仅有一个标题控件，每个标题控件可设主副标题。
 *
 *
 * @author LIBOYI
 * @date 2015-5-25
 * @version 1.0
 *
 */
public class ChartsTitleBean {
	public ChartsTitleBean(){
		
	}
	public ChartsTitleBean(String text){
		this.text=text;
	}
	
	public ChartsTitleBean(String x, String y, String text, TextStyle textStyle) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.textStyle = textStyle;
	}

	public ChartsTitleBean(String text, TextStyle textStyle) {
		this.text = text;
		this.textStyle = textStyle;
	}
    private String x="left";
    private String y="top";
	private String text;
	private TextStyle textStyle=new TextStyle();
    
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public TextStyle getTextStyle() {
		return textStyle;
	}
	public void setTextStyle(TextStyle textStyle) {
		this.textStyle = textStyle;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}
	
}
