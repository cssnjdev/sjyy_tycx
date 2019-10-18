package com.cwks.bizcore.tycx.chart.charts.component.part;
/**
 * Echarts启用功能辅助线设置
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class MarkTitle {
   private boolean mark=true; //辅助线开关，默认true
   private boolean markUndo=true;//删除辅助线，默认true
   private boolean markClear=true;//清空辅助线，默认true
public boolean isMark() {
	return mark;
}
public void setMark(boolean mark) {
	this.mark = mark;
}
public boolean isMarkUndo() {
	return markUndo;
}
public void setMarkUndo(boolean markUndo) {
	this.markUndo = markUndo;
}
public boolean isMarkClear() {
	return markClear;
}
public void setMarkClear(boolean markClear) {
	this.markClear = markClear;
}
   
   
}
