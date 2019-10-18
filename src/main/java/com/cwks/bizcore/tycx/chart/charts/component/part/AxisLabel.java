package com.cwks.bizcore.tycx.chart.charts.component.part;



/**
 * Echarts 坐标轴文本标签选项
 *
 *
 *
 *
 * @author LIBOYI
 * @date 2015-5-7
 * @version 1.0
 *
 */
public class AxisLabel {
	 public AxisLabel(){
		 
	 }
	 public AxisLabel(String formatter) {
			this.formatter=formatter;
		}
     public AxisLabel( String formatter,TextStyle textStyle) {
		this.formatter=formatter;
		this.textStyle = textStyle;
	}

	private boolean show=true;
	/**
	 * 标签旋转角度，默认为0，不旋转，正值为逆时针，负值为顺时针，可选为：-90 ~ 90 
	 */
    private int rotate=0;
    /**
     *标签显示挑选间隔，默认为'auto'，可选为：
     *'auto'（自动隐藏显示不下的） | 0（全部显示） |
     *{number}（用户指定选择间隔）
     *{function}函数回调，传递参数[index，data[index]]，返回true显示，返回false隐藏 
     */
    private String interval="auto";
    
    
    /**
     * 文本样式（详见textStyle），其中当坐标轴为数值型和时间型时，color接受方法回调，实现个性化的颜色定义
     */
    private TextStyle textStyle=new TextStyle("#888");
    
    /**
     *  间隔名称格式器：{string}（Template） | {Function} 
     */
    private String formatter;
    
    /**
     *  坐标轴文本标签与坐标轴的间距，默认为8，单位px 
     */
    private int margin=8;
    
    /**
     * 坐标轴文本标签是否可点击，是否可点击
     */
    private boolean clickable=false;

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public void setTextStyle(TextStyle textStyle) {
		this.textStyle = textStyle;
	}

	public void setRotate(int rotate) {
		this.rotate = rotate;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	public boolean isShow() {
		return show;
	}

	public int getRotate() {
		return rotate;
	}

	public TextStyle getTextStyle() {
		return textStyle;
	}

	public String getFormatter() {
		return formatter;
	}

	public int getMargin() {
		return margin;
	}

	public boolean isClickable() {
		return clickable;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
	
	
    
}
