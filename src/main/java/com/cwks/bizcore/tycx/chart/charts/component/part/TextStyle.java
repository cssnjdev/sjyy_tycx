package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 
 * Echarts字体样式
 *
 *
 * @author LIBOYI
 * @date 2015-5-25
 * @version 1.0
 *
 */
public class TextStyle {
	
	/**
	 * 字号 ，单位px,默认12
	 */
    private int fontSize=12;
    
    /**
     * 粗细，可选为：'normal' | 'bold' | 'bolder' | 'lighter' | 100 | 200 |... | 900 
     * 默认normal
     */
    private String fontWeight="normal";
    
    /**
     * 颜色 ,默认各异
     */
    private String color="black";
    
    /**
     * 修饰，仅对tooltip.textStyle生效 ，默认none
     */
    private String decoration="none";
    
    /**
     * 水平对齐方式，可选为：'left' | 'right' | 'center' 
     * 默认各异
     */
    private String align;
    
    /**
     * 垂直对齐方式，可选为：'top' | 'bottom' | 'middle' 
     * 默认值各异
     */
    private String baseline;
    
    
    /**
     * 样式，可选为：'normal' | 'italic' | 'oblique' 
     * 默认normal
     */
    private String fontStyle="normal";
    private String backgroundColor;
    private int borderRadius;
    private int padding;
    
    public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getBorderRadius() {
		return borderRadius;
	}

	public void setBorderRadius(int borderRadius) {
		this.borderRadius = borderRadius;
	}

	

	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public TextStyle(){
    }

	public TextStyle(int fontSize, String fontWeight, String color,
			String decoration, String align, String baseline,
			String fontStyle) {
		this.fontSize = fontSize;
		this.fontWeight = fontWeight;
		this.color = color;
		this.decoration = decoration;
		this.align = align;
		this.baseline = baseline;
		this.fontStyle = fontStyle;
	}
	
	public TextStyle(int fontSize, String fontWeight, String color) {
		this.fontSize = fontSize;
		this.fontWeight = fontWeight;
		this.color = color;
	}
	
	public TextStyle(int fontSize,  String color) {
		this.fontSize = fontSize;
		this.color = color;
	}
	
	public TextStyle(int fontSize) {
		this.fontSize = fontSize;
	}
	
	public TextStyle(String color) {
		this.color = color;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public void setBaseline(String baseline) {
		this.baseline = baseline;
	}


	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	public int getFontSize() {
		return fontSize;
	}

	public String getFontWeight() {
		return fontWeight;
	}

	public String getColor() {
		return color;
	}

	public String getDecoration() {
		return decoration;
	}

	public String getAlign() {
		return align;
	}

	public String getBaseline() {
		return baseline;
	}


	public String getFontStyle() {
		return fontStyle;
	}
    
}
