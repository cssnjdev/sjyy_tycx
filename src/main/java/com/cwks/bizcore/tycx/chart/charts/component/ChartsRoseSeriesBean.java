package com.cwks.bizcore.tycx.chart.charts.component;
import java.util.ArrayList;
import java.util.List;

/**
 * 玫瑰图 series 
 *
 *
 *
 * @author LIBOYI
 * @date 2015-5-29
 * @version 1.0
 *
 */
public class ChartsRoseSeriesBean extends ChartsSeriesBean{
   
   /**
    * 半径，百分比计算比，min(width, height) / 2 * 75%， 传数组实现环形图，[内半径，外半径] 
    */
   private String radius[];
   
  /**
   * 玫瑰图模式，'radius'（半径） | 'area'（面积）
   */
   private String roseType;
   
   /**
    * 数据
    */
   private List<PieDataBean> data=new ArrayList<PieDataBean>();
   
   
   private String sort="ascending";

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

	public String getRoseType() {
		return roseType;
	}

	public void setRoseType(String roseType) {
		this.roseType = roseType;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	
  
}
