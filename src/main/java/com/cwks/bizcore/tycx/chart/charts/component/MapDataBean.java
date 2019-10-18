package com.cwks.bizcore.tycx.chart.charts.component;

import java.util.List;

/**
 *
 *
 *
 *
 *
 * @author LIBOYI
 * @date 2015-6-23
 * @version 1.0
 *
 */
public class MapDataBean {
  private String name;  //名称
  private List<ChartsDataBean> data;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public List<ChartsDataBean> getData() {
	return data;
}
public void setData(List<ChartsDataBean> data) {
	this.data = data;
}
  
  
}
