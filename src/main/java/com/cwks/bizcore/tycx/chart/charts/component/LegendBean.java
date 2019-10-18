package com.cwks.bizcore.tycx.chart.charts.component;
/**
 * Echarts 图例，每个图表最多仅有一个图例
 *
 *
 *
 *
 * @author LIBOYI
 * @date 2015-5-7
 * @version 1.0
 *
 */
public class LegendBean {
  private String x="center"; //水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px） 
  private String y="top"; //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px） 
  private String orient="horizontal"; //布局方式，默认为水平布局，可选为：'horizontal' | 'vertical' 
  private String data[];
  private boolean selectedMode=true;   //图例开关，默认true
  public LegendBean(){
	  
  }
  
  
  
	public LegendBean(String[] data,
		boolean selectedMode) {
	this.data = data;
	this.selectedMode = selectedMode;
}



	public LegendBean(String x, String y, String[] data) {
	 this.x = x;
	 this.y = y;
	 this.data = data;
    }
	
	public LegendBean(String x, String y) {
		 this.x = x;
		 this.y = y;
	    }

	public LegendBean(String x, String y, String orient, String[] data) {
		this.x = x;
		this.y = y;
		this.orient = orient;
		this.data = data;
	}
	
	public LegendBean(String x, String y, String orient) {
		this.x = x;
		this.y = y;
		this.orient = orient;
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
	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}

	public String getOrient() {
		return orient;
	}

	public void setOrient(String orient) {
		this.orient = orient;
	}

	public boolean isSelectedMode() {
		return selectedMode;
	}

	public void setSelectedMode(boolean selectedMode) {
		this.selectedMode = selectedMode;
	}
	
  
}
