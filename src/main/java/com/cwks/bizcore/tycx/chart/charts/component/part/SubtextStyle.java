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
public class SubtextStyle {
	
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
    private String color;
    
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
     * 字体系列  'Arial, Verdana, sans-serif'
     */
    private String fontFamily;
    
    /**
     * 样式，可选为：'normal' | 'italic' | 'oblique' 
     * 默认normal
     */
    private String fontStyle="normal";
    
    public SubtextStyle(String color){
    	this.color=color;
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

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
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

	public String getFontFamily() {
		return fontFamily;
	}

	public String getFontStyle() {
		return fontStyle;
	}
    
}
