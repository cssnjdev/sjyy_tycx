package com.cwks.bizcore.tycx.chart.charts.component.part;



/**
 * 
 * Echarts坐标轴小标记
 * 默认显示属性show控制显示与否，属性lineStyle（详见lineStyle）控制线条样式，属性
 *属性length控制线长 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class AxisTick {
   /**
    * 显示策略，默认true
    */
   private boolean show=true;
   
   /**
    * splitNumber控制每份split细分多少段,默认5
    */
   private int splitNumber=5;
   
   /**
    * 控制线长，默认8
    */
   private int length=8;
   

public void setShow(boolean show) {
	this.show = show;
}

public void setSplitNumber(int splitNumber) {
	this.splitNumber = splitNumber;
}

public void setLength(int length) {
	this.length = length;
}


public boolean isShow() {
	return show;
}

public int getSplitNumber() {
	return splitNumber;
}

public int getLength() {
	return length;
}

   
   
}
