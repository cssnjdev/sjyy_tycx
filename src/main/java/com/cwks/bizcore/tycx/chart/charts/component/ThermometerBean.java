package com.cwks.bizcore.tycx.chart.charts.component;
/**
 * 温度计式图形数据
 *
 *
 * @author LIBOYI
 * @date 2015-6-4
 * @version 1.0
 *
 */
public class ThermometerBean {
    public ThermometerBean(){
    	
    }
    
    
	public ThermometerBean(String name, String[] data) {
		this.name = name;
		this.data = data;
	}


	private String name;
    private String []data;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String[] getData() {
		return data;
	}
	public void setData(String[] data) {
		this.data = data;
	}
}
