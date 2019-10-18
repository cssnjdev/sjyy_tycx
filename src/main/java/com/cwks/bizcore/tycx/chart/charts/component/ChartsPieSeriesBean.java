package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.part.PieItemStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准环形图 series 
 *
 *
 *
 * @author LIBOYI
 * @date 2015-5-29
 * @version 1.0
 *
 */
public class ChartsPieSeriesBean extends ChartsSeriesBean{
   
   /**
    * 半径，百分比计算比，min(width, height) / 2 * 75%， 传数组实现环形图，[内半径，外半径] 
    */
   private String radius[]={"50%", "70%"};
   
   /**
    * 数据
    */
   private List<PieDataBean> data=new ArrayList<PieDataBean>();
   
   
   private PieItemStyle itemStyle=new PieItemStyle();
   
   private String type="pie";
   
   private boolean clockWise=true;

	public String[] getRadius() {
		return radius;
	}

	public void setRadius(String[] radius) {
		this.radius = radius;
	}

	public List<PieDataBean> getData() {
		return data;
	}

	public void setData(List<PieDataBean> data) {
		this.data = data;
	}

	public PieItemStyle getItemStyle() {
		return itemStyle;
	}

	public void setItemStyle(PieItemStyle itemStyle) {
		this.itemStyle = itemStyle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isClockWise() {
		return clockWise;
	}

	public void setClockWise(boolean clockWise) {
		this.clockWise = clockWise;
	}
	
  
}
