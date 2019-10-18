package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * 辅助线标志，分别是启用，删除上一条，删除全部，可设置更多属性 
 * 可传入lineStyle（详见lineStyle）控制线条样式
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class Mark {
  private boolean show=false;
  public Mark(){
	  
  }
  

public Mark(boolean show) {
	this.show = show;
}


public boolean isShow() {
	return show;
}
public void setShow(boolean show) {
	this.show = show;
}
  
  
}
