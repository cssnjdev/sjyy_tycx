package com.cwks.bizcore.tycx.chart.charts.component;

import com.cwks.bizcore.tycx.chart.charts.component.part.MapItemStyle;

import java.util.List;


/**
 *  series（地图） 
 *
 *
 *
 * @author LIBOYI
 * @date 2015-6-16
 * @version 1.0
 *
 */
public class ChartsMapSeriesBean extends ChartsSeriesBean{
	public ChartsMapSeriesBean(){
		
	}
	/**
	 *地图类型，支持world，china及全国34个省市自治区。省市自治区的mapType直接使用简体中文：
     *新疆， 西藏， 内蒙古， 青海， 四川， 黑龙江， 甘肃， 云南， 广西， 湖南， 陕西，
     *广东，吉林， 河北， 湖北， 贵州， 山东， 江西， 河南， 辽宁， 山西， 安徽， 福建，
     *浙江， 江苏，重庆， 宁夏， 海南， 台湾， 北京， 天津， 上海， 香港， 澳门' 
	 */
     private String mapType;
     
     /**
      * 是否开启滚轮缩放和拖拽漫游，默认为false（关闭），其他有效输入为true（开启），'scale'（仅开启滚轮缩放），'move'（仅开启拖拽漫游）
      */
     private boolean roam=false;
     
     /**
      * 图形样式，可设置图表内图形的默认样式和强调样式（悬浮时样式）
      */
     private MapItemStyle itemStyle;
     
     private List<ChartsDataBean> data;

	public String getMapType() {
		return mapType;
	}

	public void setMapType(String mapType) {
		this.mapType = mapType;
	}

	public boolean isRoam() {
		return roam;
	}

	public void setRoam(boolean roam) {
		this.roam = roam;
	}

	public MapItemStyle getItemStyle() {
		return itemStyle;
	}

	public void setItemStyle(MapItemStyle itemStyle) {
		this.itemStyle = itemStyle;
	}

	public List<ChartsDataBean> getData() {
		return data;
	}

	public void setData(List<ChartsDataBean> data) {
		this.data = data;
	}
     
   
}
