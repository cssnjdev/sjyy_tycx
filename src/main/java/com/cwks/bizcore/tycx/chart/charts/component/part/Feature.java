package com.cwks.bizcore.tycx.chart.charts.component.part;



/**
 * Echarts启用功能
 *
 *
 * @author LIBOYI
 * @date 2015-5-26
 * @version 1.0
 *
 */
public class Feature {
	 private Mark mark=new Mark(); //辅助线标志
	 private DataZoom dataZoom=new DataZoom(); //框选区域缩放
	 private DataView dataView=new DataView(); //数据视图
	 private MagicType magicType=new MagicType(); //动态类型切换
	 private Restore restore=new Restore();     //还原
	 private SaveAsImage saveAsImage=new SaveAsImage(); //保存图片（IE8-不支持）
	 
	 public Feature(){
		 
	 }
	 
	public Feature(Mark mark, DataZoom dataZoom, DataView dataView,
			MagicType magicType, Restore restore, SaveAsImage saveAsImage) {
		this.mark = mark;
		this.dataZoom = dataZoom;
		this.dataView = dataView;
		this.magicType = magicType;
		this.restore = restore;
		this.saveAsImage = saveAsImage;
	}
	
	public Feature(Mark mark,DataView dataView,
			MagicType magicType, Restore restore, SaveAsImage saveAsImage) {
		this.mark = mark;
		this.dataView = dataView;
		this.magicType = magicType;
		this.restore = restore;
		this.saveAsImage = saveAsImage;
	}

	public void setMark(Mark mark) {
		this.mark = mark;
	}
	public void setDataZoom(DataZoom dataZoom) {
		this.dataZoom = dataZoom;
	}
	public void setDataView(DataView dataView) {
		this.dataView = dataView;
	}
	public void setMagicType(MagicType magicType) {
		this.magicType = magicType;
	}
	public void setRestore(Restore restore) {
		this.restore = restore;
	}
	public void setSaveAsImage(SaveAsImage saveAsImage) {
		this.saveAsImage = saveAsImage;
	}
	public Mark getMark() {
		return mark;
	}
	public DataZoom getDataZoom() {
		return dataZoom;
	}
	public DataView getDataView() {
		return dataView;
	}
	public MagicType getMagicType() {
		return magicType;
	}
	public Restore getRestore() {
		return restore;
	}
	public SaveAsImage getSaveAsImage() {
		return saveAsImage;
	}
	 
   
}
