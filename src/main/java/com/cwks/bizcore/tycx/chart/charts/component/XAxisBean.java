package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.part.AxisLabel;

/**
 * 
 *Echarts 图表X轴用
 *
 *
 *
 *
 * @author LIBOYI
 * @date 2015-5-7
 * @version 1.0
 *
 */
public class XAxisBean {
	public XAxisBean(){
		
	}
	
	 public XAxisBean(String type, String[] data, AxisLabel axisLabel,
			String name, boolean boundaryGap) {
		this.type = type;
		this.data = data;
		this.axisLabel = axisLabel;
		this.name = name;
		this.boundaryGap = boundaryGap;
	}
	 
	 public XAxisBean(boolean boundaryGap) {
			this.boundaryGap = boundaryGap;
     }
	 
	 

	/**
	  * 坐标轴有三种类型，类目型、数值型和时间型 类目型：需要指定类目列表，坐标轴内有且仅有这些指定类目坐标
	  * 数值型：需要指定数值区间，不指定时则自定计算数值范围，坐标轴内包含数值区间内容全部坐标
	  * 时间型：时间型坐标轴用法同数值型，只是目标处理和格式化显示时会自动转变为时间，并且随着时间跨度的不同自动切换需要显示的时间粒度
	  * 默认为类目型'category'
	  */
	  private String type="category";
	  private String[] data={""};        //[]值 类目列表，同时也是label内容
	  private AxisLabel axisLabel=new AxisLabel();  //坐标轴文本标签
	  private String name;
	  
	  /**
	   * 类目起始和结束两端空白策略，默认为true留空，false则顶头 
	   */
	  private boolean boundaryGap=true;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}
	public AxisLabel getAxisLabel() {
		return axisLabel;
	}
	public void setAxisLabel(AxisLabel axisLabel) {
		this.axisLabel = axisLabel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isBoundaryGap() {
		return boundaryGap;
	}
	public void setBoundaryGap(boolean boundaryGap) {
		this.boundaryGap = boundaryGap;
	}
  
}
