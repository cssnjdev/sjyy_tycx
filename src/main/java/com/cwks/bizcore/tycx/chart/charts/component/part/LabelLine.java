package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 标签视觉引导线
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class LabelLine {
	public LabelLine(){
		
	}
	
	
	public LabelLine(boolean show) {
		this.show = show;
	}


	/**
	 * 标签视觉引导线，默认显示 ,适用对象 饼图，漏斗图
	 */
    private boolean show=true;

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
    
    
}
