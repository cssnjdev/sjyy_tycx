package com.cwks.bizcore.tycx.chart.factory;

import com.cwks.bizcore.tycx.chart.charts.*;
import com.cwks.bizcore.tycx.chart.charts.component.*;
import com.cwks.bizcore.tycx.chart.charts.component.ChartsDataBean;
import com.cwks.bizcore.tycx.chart.charts.component.GaugeOptionBean;
import com.cwks.bizcore.tycx.chart.charts.component.part.*;
import com.cwks.bizcore.tycx.chart.factory.tool.Toolkit;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 创建图形工厂
 *
 *
 * @author LIBOYI
 * @date 2015-6-17
 * @version 2.0
 *
 */
public class ChartsFactory {
	
   /**
    * 创建仪表盘
    * @return
    * @param text 仪表盘文本名称
    * @param fontSize 字体大小
    * @param value仪表盘的值
    */
   public static JSONObject createGauge(String text,int fontSize,ChartsDataBean value,Map<String,Object> resultMap){
	   int x1=Integer.parseInt((String)resultMap.get("x"));
	   int y1=Integer.parseInt((String)resultMap.get("y"));
	   int x2=Integer.parseInt((String)resultMap.get("x2"));
	   int y2=Integer.parseInt((String)resultMap.get("y2"));
	   GaugeOptionBean option=new GaugeOptionBean();
	   //设置标题
	   ChartsTitleBean title=new ChartsTitleBean();
	  // title.setText(text);
	   TextStyle textStyle=new TextStyle();
	   textStyle.setFontSize(fontSize);
	   title.setTextStyle(textStyle);
	   option.setTitle(title);
	  
	   //设置grid
	   ChartsGridBean grid=new ChartsGridBean();
	   grid.setX(x1);
	   grid.setY(y1);
	   grid.setX2(x2);
	   grid.setY2(y2);
	   option.setGrid(grid);
	   //设置工具提示
	   ChartsToolTipBean tooltip=new ChartsToolTipBean();
	   tooltip.setFormatter("{c}%");
	   option.setTooltip(tooltip);
	   //设置工具栏
	   ChartsToolBoxBean toolbox=new ChartsToolBoxBean();
	   option.setToolbox(toolbox);
	   //设值
	   List<ChartsGaugeSeriesBean> series=new ArrayList<ChartsGaugeSeriesBean>();
	   ChartsGaugeSeriesBean gaugeSeries=new ChartsGaugeSeriesBean();
	   GaugeDetail detail=new GaugeDetail();
	   detail.setFormatter("{value}%");
	   gaugeSeries.setName(text);
	   gaugeSeries.setDetail(detail);
	   //设置图的宽度
	   GaugeAxisLine axisLine=new GaugeAxisLine();
	   GaugeLineStyle lineStyle=new GaugeLineStyle();
	   //String[] color=new String[]{'1','2'};
	   lineStyle.setWidth("20");
	   //lineStyle.setColor(color);
	   axisLine.setLineStyle(lineStyle);
	   gaugeSeries.setAxisLine(axisLine);
	   List<ChartsDataBean> dataList=new ArrayList<ChartsDataBean>();
	   ChartsDataBean dataBean=value;
	   dataList.add(dataBean);
	   gaugeSeries.setData(dataList);
	   gaugeSeries.setType("gauge");
	   series.add(gaugeSeries);
	   option.setSeries(series);
	   JSONObject  jsonObject = JSONObject.fromObject(option);
	   return jsonObject;
   }
   /**
    * 创建雷达图
    * @param text
    * @param fontSize
    * @param value
    * @return
    */
   public static JSONObject createRadar(String text,int fontSize,double[] value,List<RadarName> radarList,Map<String,Object> resultMap){
	   int x1=Integer.parseInt((String)resultMap.get("x"));
	   int y1=Integer.parseInt((String)resultMap.get("y"));
	   int x2=Integer.parseInt((String)resultMap.get("x2"));
	   int y2=Integer.parseInt((String)resultMap.get("y2"));
	   RadarOptionBean option =new RadarOptionBean();
	   RadarTitle title=new RadarTitle();
	 //  title.setText(text);
	   option.setTitle(title);
	   //设置grid
	   ChartsGridBean grid=new ChartsGridBean();
	   grid.setX(x1);
	   grid.setY(y1);
	   grid.setX2(x2);
	   grid.setY2(y2);
	   option.setGrid(grid);
	   //设置工具提示
	   ChartsToolTipBean tooltip=new ChartsToolTipBean();
	   tooltip.setFormatter("{c}%");
	   option.setTooltip(tooltip);
	   //设置radar
	   Radar radar=new Radar();
	   RadarNameTextStyle RadartextStyle=new RadarNameTextStyle();
	   GuageTextStyle textStyle1=new GuageTextStyle();
	   textStyle1.setColor("#666");
	   textStyle1.setBackgroundColor("#ffffff");
	   textStyle1.setBorderRadius(3);
	   int[] a=new int[2];
	   a[0]=3;
	   a[1]=5;
	   textStyle1.setPadding(a);
	   RadartextStyle.setTextStyle(textStyle1);
	   radar.setName(RadartextStyle);
	   radar.setIndicator(radarList);	  	   
	   option.setRadar(radar);
	   //设值
	   List<ChartsRadarSeriesBean> seriesList=new ArrayList<ChartsRadarSeriesBean>();
	   ChartsRadarSeriesBean series=new ChartsRadarSeriesBean();
	   List<RadarDataBean> dataList=new ArrayList<RadarDataBean>();
	   RadarDataBean data=new RadarDataBean();
	   data.setName(text);
	   data.setValue(value);
	   series.setName(text);
	   dataList.add(data);
	   series.setData(dataList);
	   series.setType("radar");
	   seriesList.add(series);
	   option.setSeries(seriesList);	  
	   JSONObject  jsonObject = JSONObject.fromObject(option);
	   return jsonObject;
   }
   /**
    * 创建柱状图/柱状堆积
    * @return
    * @param text标题
    * @param fontSize字体大小
    * @param xAxisName x轴名称
    * @param yAxisName y轴名称
    * @param yAxisUnit 单位 如"%"、"℃"之类
    * @param xData x轴值
    * @param dataList数据
    */
   public static JSONObject createBar(String text,int fontSize,String xAxisName, String xData[],
		  String yAxisName,String yAxisUnit,List<ChartsBarDataBean> dataList,Map<String, Object> resultMap){
	   String trigger="axis";
	   String x="center";
	   String y="top";
	   int x1=Integer.parseInt((String)resultMap.get("x"));
	   int y1=Integer.parseInt((String)resultMap.get("y"));
	   int x2=Integer.parseInt((String)resultMap.get("x2"));
	   int y2=Integer.parseInt((String)resultMap.get("y2"));
	   int qxds=0;
	   if(!TycxUtils.isEmpty((String)resultMap.get("qxds"))){
		   qxds=Integer.parseInt((String)resultMap.get("qxds"));
	   }
	   boolean isShow=true;
	   String formatter="{value}";
	   List<ChartsBarSeriesBean> series=new ArrayList<ChartsBarSeriesBean>();
	   //拿到图例
	   String legend[]=new String[dataList.size()];
	   if(!Toolkit.isEmpty(dataList)&&dataList.size()>0){
		   for (int i = 0; i < dataList.size(); i++) {
			   legend[i]=dataList.get(i).getName();
			   ChartsBarSeriesBean temp=new ChartsBarSeriesBean();
			   temp.setName(dataList.get(i).getName());
			   temp.setStack(dataList.get(i).getStack());
			   temp.setData(dataList.get(i).getData());
			   temp.setType("bar");
			   series.add(temp);
		}
		ChartsBarDataBean bar=dataList.get(0);
		if(bar.getData().length==1){
			y="bottom";
			trigger="item";
		}
	   }else{
			   series=new ArrayList<ChartsBarSeriesBean>();
			   ChartsBarSeriesBean seriesBean=new ChartsBarSeriesBean();
			   seriesBean.setData(new String[]{});
			   series.add(seriesBean);
	   }
	   BarOptionBean option =new BarOptionBean();
	   //设置标题
	   ChartsTitleBean title=new ChartsTitleBean();
	  // title.setText(text);
	   TextStyle textStyle=new TextStyle();
	   textStyle.setFontSize(fontSize);
	   option.setTitle(title);
	   //设置grid
	   ChartsGridBean grid=new ChartsGridBean();
	   grid.setX(x1);
	   grid.setY(y1);
	   grid.setX2(x2);
	   grid.setY2(y2);
	   option.setGrid(grid);
	   //设置工具提示
	   BarToolTipBean tooltip=new BarToolTipBean();
	   tooltip.setTrigger(trigger);
	   option.setTooltip(tooltip);
	   //设置工具栏
	   ChartsToolBoxBean toolbox=new ChartsToolBoxBean();
	   String type[]={"bar","line"};
	   Feature feature=new Feature();
	   feature.setMark(new Mark(true));
	   //feature.setDataView(new DataView(true));
	   feature.setDataZoom(new DataZoom(false));
	   feature.setMagicType(new MagicType(true,type));
	   feature.setRestore(new Restore(true));
	   feature.setSaveAsImage(new SaveAsImage(true));
	   toolbox.setFeature(feature);
	   toolbox.setShow(isShow);
	   option.setToolbox(toolbox);
	   //设置图例
	   LegendBean legendBean=new LegendBean(x,y,legend);
	   option.setLegend(legendBean);
	   //设置x轴
	   XAxisBean xAxis=new XAxisBean();
	   xAxis.setName(xAxisName);
	   AxisLabel axisLabel=new AxisLabel();
	   axisLabel.setInterval("0");
	   axisLabel.setRotate(qxds);
	   axisLabel.setFormatter("{value}");
	   xAxis.setAxisLabel(axisLabel);
	   if(xData==null){
		   xData=new String[1];
		   xData[0]="";
	   }
	   xAxis.setData(xData);
	   option.setxAxis(xAxis);
	   //设置y轴
	   YAxisBean yAxis=new YAxisBean();
	   AxisLabel axisLabel1=new AxisLabel(formatter);
	   yAxis.setAxisLabel(axisLabel1);
	   yAxis.setName(yAxisName);
	   option.setyAxis(yAxis);
	   //数据
	   option.setSeries(series);
	   JSONObject  jsonObject = JSONObject.fromObject(option);
	   return jsonObject;
   }
   /**
    * 柱状和折线混搭图
    * @param text标题
    * @param fontSize字体大小
    * @param xAxisName x轴名称
    * @param y1AxisName y1轴名称
    * @param y2AxisName y2轴名称
    * @param xData x轴值
    * @param series数据
    * 柱状图 设置name和data两个属性，支持堆积，若堆积设置stack
    * 折线图除设置name和data外，还需设置type为line
    * @param y1Unit,y2Unit 单位 如"%"、"℃"之类
    */
   @Deprecated  
   public static JSONObject createMashupOne(String text,int fontSize,String xAxisName, String xData[],
			  String y1AxisName,String y1Unit,String y2AxisName,String y2Unit,List<ChartsBarSeriesBean> series){
	   BarLineMashupOptionBean option=new BarLineMashupOptionBean();
	   String y1Formatter="{value}"+y1Unit;
	   String y2Formatter="{value}"+y2Unit;
	   //拿到图例
	   String legend[]=new String[series.size()];
	   if(!Toolkit.isEmpty(series)&&series.size()>0){
		   for (int i = 0; i < series.size(); i++) {
			   legend[i]=series.get(i).getName();
		}
	   }else{
		   series=new ArrayList<ChartsBarSeriesBean>();
		   ChartsBarSeriesBean seriesBean=new ChartsBarSeriesBean();
		   seriesBean.setData(new String[]{});
		   series.add(seriesBean);
	   }
	   //设置标题
	   ChartsTitleBean title=new ChartsTitleBean();
	   title.setText(text);
	   TextStyle textStyle=new TextStyle();
	   textStyle.setFontSize(fontSize);
	   option.setTitle(title);
	   //设置工具提示
	   BarToolTipBean tooltip=new BarToolTipBean();
	   tooltip.setTrigger("axis");
	   option.setTooltip(tooltip);
	   //设置工具栏
	   ChartsToolBoxBean toolbox=new ChartsToolBoxBean(true);
	   String type[]={"bar","line"};
	   Feature feature=new Feature();
	   feature.setMark(new Mark(true));
	   feature.setDataView(new DataView(true));
	   feature.setDataZoom(new DataZoom(false));
	   feature.setMagicType(new MagicType(true,type));
	   feature.setRestore(new Restore(true));
	   feature.setSaveAsImage(new SaveAsImage(true));
	   toolbox.setFeature(feature);
	   option.setToolbox(toolbox);
	   //设置图例
	   LegendBean legendBean=new LegendBean("center","top",legend);
	   option.setLegend(legendBean);
	   //设置x轴
	   XAxisBean xAxis=new XAxisBean();
	   xAxis.setName(xAxisName);
	   if(xData==null){
		   xData=new String[1];
		   xData[0]="";
	   }
	   xAxis.setData(xData);
	   option.setxAxis(xAxis);
	   //设置y轴
	   List<YAxisBean> yAxis=new ArrayList<YAxisBean>();
	   YAxisBean y1=new YAxisBean();
	   y1.setName(y1AxisName);
	   AxisLabel y1AxisLabel=new AxisLabel(y1Formatter);
	   y1.setAxisLabel(y1AxisLabel);
	   yAxis.add(y1);
	   YAxisBean y2=new YAxisBean();
	   y2.setName(y2AxisName);
	   AxisLabel y2AxisLabel=new AxisLabel(y2Formatter);
	   y2.setAxisLabel(y2AxisLabel);
	   yAxis.add(y2);
	   option.setyAxis(yAxis);
	   //数据
	   option.setSeries(series);
	   JSONObject  jsonObject = JSONObject.fromObject(option);
	   return jsonObject;
   }
   /**
    * 柱状和折线混搭图
    * @param text标题
    * @param fontSize字体大小
    * @param xAxisName x轴名称
    * @param y1AxisName y1轴名称
    * @param y2AxisName y2轴名称
    * @param xData x轴值
    * @param barList 柱状图数据
    * @param lineList 折线图数据
    * 柱状图 设置name和data两个属性，支持堆积，若堆积设置stack
    * 折线图除设置name和data外，还需设置type为line
    * @param y1Unit,y2Unit 单位 如"%"、"℃"之类
    */
   public static JSONObject createMashupOne2(String text,int fontSize,String xAxisName, String xData[],
			  String y1AxisName,String y1Unit,String y2AxisName,String y2Unit,List<ChartsBarDataBean> barList,List<ChartsLineDataBean> lineList){
	   List<ChartsBarSeriesBean> series=new ArrayList<ChartsBarSeriesBean>();
	   if(!Toolkit.isEmpty(barList)&&barList.size()>0){
		   for (ChartsBarDataBean barDataBean : barList) {
			   ChartsBarSeriesBean barSeries=new ChartsBarSeriesBean();
			   barSeries.setData(barDataBean.getData());
			   barSeries.setName(barDataBean.getName());
			   barSeries.setStack(barDataBean.getStack());
			   series.add(barSeries);
		   }
	   }
	   if(!Toolkit.isEmpty(lineList)&&lineList.size()>0){
		   for (ChartsLineDataBean lineDataBean : lineList) {
			   ChartsBarSeriesBean barSeries=new ChartsBarSeriesBean();
			   barSeries.setData(lineDataBean.getData());
			   barSeries.setName(lineDataBean.getName());
			   barSeries.setType("line");
			   barSeries.setyAxisIndex(1);
			   series.add(barSeries);
		   }
	   }
	
	   BarLineMashupOptionBean option=new BarLineMashupOptionBean();
	   String y1Formatter="{value}"+y1Unit;
	   String y2Formatter="{value}"+y2Unit;
	   //拿到图例
	   String legend[]=new String[series.size()];
	   if(!Toolkit.isEmpty(series)&&series.size()>0){
		   for (int i = 0; i < series.size(); i++) {
			   legend[i]=series.get(i).getName();
		}
	   }else{
		   series=new ArrayList<ChartsBarSeriesBean>();
		   ChartsBarSeriesBean seriesBean=new ChartsBarSeriesBean();
		   seriesBean.setData(new String[]{});
		   series.add(seriesBean);
	   }
	   //设置标题
	   ChartsTitleBean title=new ChartsTitleBean();
	   title.setText(text);
	   TextStyle textStyle=new TextStyle();
	   textStyle.setFontSize(fontSize);
	   option.setTitle(title);
	   //设置工具提示
	   BarToolTipBean tooltip=new BarToolTipBean();
	   tooltip.setTrigger("axis");
	   option.setTooltip(tooltip);
	   //设置工具栏
	   ChartsToolBoxBean toolbox=new ChartsToolBoxBean(true);
	   String type[]={"bar","line"};
	   Feature feature=new Feature();
	   feature.setMark(new Mark(true));
	  // feature.setDataView(new DataView(true));
	   feature.setDataZoom(new DataZoom(false));
	   feature.setMagicType(new MagicType(true,type));
	   feature.setRestore(new Restore(true));
	   feature.setSaveAsImage(new SaveAsImage(true));
	   toolbox.setFeature(feature);
	   option.setToolbox(toolbox);
	   //设置图例
	   LegendBean legendBean=new LegendBean("center","top",legend);
	   option.setLegend(legendBean);
	   //设置x轴
	   XAxisBean xAxis=new XAxisBean();
	   xAxis.setName(xAxisName);
	   if(xData==null){
		   xData=new String[1];
		   xData[0]="";
	   }
	   xAxis.setData(xData);
	   option.setxAxis(xAxis);
	   //设置y轴
	   List<YAxisBean> yAxis=new ArrayList<YAxisBean>();
	   YAxisBean y1=new YAxisBean();
	   y1.setName(y1AxisName);
	   AxisLabel y1AxisLabel=new AxisLabel(y1Formatter);
	   y1.setAxisLabel(y1AxisLabel);
	   SplitArea y1SplitArea=new SplitArea();
	   y1SplitArea.setShow(false);
	   y1.setSplitArea(y1SplitArea);
	   yAxis.add(y1);
	   YAxisBean y2=new YAxisBean();
	   y2.setName(y2AxisName);
	   AxisLabel y2AxisLabel=new AxisLabel(y2Formatter);
	   y2.setAxisLabel(y2AxisLabel);
	   y2.setSplitArea(y1SplitArea);
	   yAxis.add(y2);
	   option.setyAxis(yAxis);
	   //数据
	   option.setSeries(series);
	   JSONObject  jsonObject = JSONObject.fromObject(option);
	   return jsonObject;
   }
   /**
    * 创建折线图
    * @return
    * @param text标题
    * @param fontSize字体大小
    * @param xAxisName x轴名称
    * @param yAxisName y轴名称
    * @param xData x轴值
    * @param dataList值
    * @param unit单位 如"%"、"℃"之类
    */
   public static JSONObject createLine(String text,int fontSize,String xAxisName,String xData[],
		  String yAxisName,String unit,List<ChartsLineDataBean> dataList,Map<String,Object> resultMap){
	   int x1=Integer.valueOf(((String)resultMap.get("x")));
	   int y1=Integer.valueOf((String)resultMap.get("y"));
	   int x2=Integer.valueOf((String)resultMap.get("x2"));
	   int y2=Integer.valueOf((String)resultMap.get("y2"));
	   int qxds=0;
	   if(!TycxUtils.isEmpty((String)resultMap.get("qxds"))){
		   qxds=Integer.parseInt((String)resultMap.get("qxds"));
	   }
	   List<ChartsLineSeriesBean> series=new ArrayList<ChartsLineSeriesBean>();
	   //拿到图例
	   String legend[]=new String[dataList.size()];
	   if(!Toolkit.isEmpty(dataList)&&dataList.size()>0){
		   for (int i = 0; i < dataList.size(); i++) {
			   legend[i]=dataList.get(i).getName();
			   ChartsLineSeriesBean temp=new ChartsLineSeriesBean();
			   temp.setName(dataList.get(i).getName());
			   temp.setData(dataList.get(i).getData());
			   temp.setType("line");
			   series.add(temp);
		}
	   }else{
		   series=new ArrayList<ChartsLineSeriesBean>();
		   ChartsLineSeriesBean seriesBean=new ChartsLineSeriesBean();
		   seriesBean.setData(new String[]{});
		   series.add(seriesBean);
	   }
	 
	   LineOptionBean option =new LineOptionBean();
	   //设置标题
	   ChartsTitleBean title=new ChartsTitleBean();
	  // title.setText(text);
	   TextStyle textStyle=new TextStyle();
	   textStyle.setFontSize(fontSize);
	   option.setTitle(title);
	   //设置grid
	   ChartsGridBean grid=new ChartsGridBean();
	   grid.setX(x1);
	   grid.setY(y1);
	   grid.setX2(x2);
	   grid.setY2(y2);
	   option.setGrid(grid);
	   //设置工具提示
	   BarToolTipBean tooltip=new BarToolTipBean("axis");
	   option.setTooltip(tooltip);
	   //设置工具栏
	   ChartsToolBoxBean toolbox=new ChartsToolBoxBean(true);
	   String type[]={"line","bar"};
	   Feature feature=new Feature();
	   feature.setMark(new Mark(true));
	   feature.setDataView(new DataView(true));
	   feature.setDataZoom(new DataZoom(false));
	   feature.setMagicType(new MagicType(true,type));
	   feature.setRestore(new Restore(true));
	   feature.setSaveAsImage(new SaveAsImage(true));
	   toolbox.setFeature(feature);
	   option.setToolbox(toolbox);
	   //设置图例
	   LegendBean legendBean=new LegendBean("center","top",legend);
	   option.setLegend(legendBean);
	   //设置x轴
	   List<XAxisBean> xAxisList=new ArrayList<XAxisBean>();
	   XAxisBean xAxis=new XAxisBean();
	   AxisLabel axisLabel=new AxisLabel();
	   axisLabel.setInterval("0");
	   axisLabel.setRotate(qxds);
	   axisLabel.setFormatter("{value}");
	   xAxis.setName(xAxisName);
	   xAxis.setAxisLabel(axisLabel);
	   if(xData==null){
		   xData=new String[1];
		   xData[0]="";
	   }
	   xAxis.setData(xData);
	   xAxisList.add(xAxis);
	   option.setxAxis(xAxisList);
	   //设置y轴
	   YAxisBean yAxis=new YAxisBean();
	   yAxis.setName(yAxisName);
	   String formatter="{value} ";
	   AxisLabel axisLabel1=new AxisLabel(formatter);
	   axisLabel1.setInterval("0");
	   yAxis.setAxisLabel(axisLabel1);
	   option.setyAxis(yAxis);
	   //数据
	   option.setSeries(series);
	   JSONObject  jsonObject = JSONObject.fromObject(option);
	   return jsonObject;
   }
   
   /**
    * 创建饼图
    * @return
    * @param text 标题
    * @param fontSize字体大小
    * @param x 图例水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right'
    * @param y 图例垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center'
    * @param radius半径 格式50%
    * @param data饼图数据
    * 
    */
   public static JSONObject createPie(String text,int fontSize,String x,String y,
		String radius,List<PieDataBean> data){
	   String radiusArray[]=new String[2];
	   radiusArray[0]="0%";
	   radiusArray[1]=radius;
	   JSONObject  jsonObject = createPieAndRingJson(text,fontSize,x,y,radiusArray,data);
	   return jsonObject;
   }
   /**
    * 创建环形图
    * @return
    * @param text 标题
    * @param fontSize字体大小
    * @param x 图例水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right'
    * @param y 图例垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center'
    * @param innerRadius内半径，格式20%
    * @param outerRadius外半径，格式30%
    * @param data环形图数据
    */
   public static JSONObject createRing(String text,int fontSize,String x,String y,
		String innerRadius,String outerRadius,List<PieDataBean> data){
	   String radiusArray[]=new String[2];
	   radiusArray[0]=innerRadius;
	   radiusArray[1]=outerRadius;
	   JSONObject  jsonObject = createPieAndRingJson(text,fontSize,x,y,radiusArray,data);
	   return jsonObject;
   }
   /**
    * 饼图和环形图生成json
    * @param text 标题
    * @param fontSize字体大小
    * @param x 图例水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right'
    * @param y 图例垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center'
    * @param radiusArray[]半径数组，[内半径，外半径] 格式{"50%", "70%"}
    */
   private static JSONObject createPieAndRingJson(String text,int fontSize,String x,String y,
			String radiusArray[],List<PieDataBean> data){
	   PieOptionBean option =new PieOptionBean();
	   //设置标题
	   ChartsTitleBean title=new ChartsTitleBean();
	  // title.setText(text);
	   TextStyle textStyle=new TextStyle();
	   textStyle.setFontSize(fontSize);
	   option.setTitle(title);
	   //设置工具提示
	   ChartsToolTipBean tooltip=new ChartsToolTipBean();
	   tooltip.setFormatter("{a} <br/>{b} : {c} ({d}%)");
	   tooltip.setTrigger("item");
	   option.setTooltip(tooltip);
	   //设置工具栏
	   ChartsToolBoxBean toolbox=new ChartsToolBoxBean(true);
	   String type[]={"pie", "funnel"};
	   Feature feature=new Feature(new Mark(false),new DataView(true),
				new MagicType(true,type),new  Restore(true),new  SaveAsImage(true));
	   toolbox.setFeature(feature);
	   option.setToolbox(toolbox);
	   //设置图例
	   LegendBean legendBean=new LegendBean(x,y);
	   String[] legendData=new String[data.size()];
	   if(!Toolkit.isEmpty(data)&&data.size()>0){
		   for (int i = 0; i < data.size(); i++) {
			   legendData[i]=data.get(i).getName();
		}
	   }
	   legendBean.setData(legendData);
	   option.setLegend(legendBean);
	   List<ChartsPieSeriesBean> series=new ArrayList<ChartsPieSeriesBean>();
	   ChartsPieSeriesBean ringSeries=new ChartsPieSeriesBean();
	   
	   //数据
	   ringSeries.setName(text);
	   ringSeries.setType("pie");
	   ringSeries.setRadius(radiusArray);
	   ringSeries.setData(data);
	   series.add(ringSeries);
	   option.setSeries(series);
	   return JSONObject.fromObject(option);
   }

   /**
    * 标准面积图/堆积面积图
    * @param text 标题
    * @param fontSize
    * @param xAxisName x轴名称
    * @param yAxisName y轴名称
    * @param xData x轴值
    * @param unit单位 如"%"、"℃"之类
    * @param dataList数据
    * @return
    */
   public static JSONObject createAreaStack(String text,int fontSize,String xAxisName,String xData[],
			  String yAxisName,String unit,List<AreaStackChartsDataBean> dataList){
	   AreaStackOptionBean option=new AreaStackOptionBean();
	   List<ChartsAreaStackSeriesBean> series=new ArrayList<ChartsAreaStackSeriesBean>();
	   //设置标题
	   ChartsTitleBean title=new ChartsTitleBean();
	   title.setText(text);
	   TextStyle textStyle=new TextStyle();
	   textStyle.setFontSize(fontSize);
	   option.setTitle(title);
	   //设置图例
	   LegendBean legendBean=new LegendBean();
	   String[] legendData=new String[dataList.size()];
	   if(!Toolkit.isEmpty(dataList)&&dataList.size()>0){
		   for (int i = 0; i < dataList.size(); i++) {
			   legendData[i]=dataList.get(i).getName();
			   ChartsAreaStackSeriesBean temp=new ChartsAreaStackSeriesBean();
			   temp.setData(dataList.get(i).getData());
			   temp.setName(dataList.get(i).getName());
			   temp.setStack(dataList.get(i).getStack());
			   temp.setType("line");
			   series.add(temp);
			   
		}
	   }else{
		   ChartsAreaStackSeriesBean seriesBean=new ChartsAreaStackSeriesBean();
		   seriesBean.setData(new String[]{});
		   series.add(seriesBean);
	   }
	   legendBean.setData(legendData);
	   option.setLegend(legendBean);
	   //设置x轴
	   List<XAxisBean> xAxis=new ArrayList<XAxisBean>();
	   XAxisBean x1=new XAxisBean(true);
	   x1.setName(xAxisName);
	   if(xData==null){
		   xData=new String[1];
		   xData[0]="";
	   }
	   x1.setData(xData);
	   xAxis.add(x1);
	   option.setxAxis(xAxis);
	   //设置y轴
	   List<YAxisBean> yAxis=new ArrayList<YAxisBean>();
	   YAxisBean y1=new YAxisBean();
	   y1.setName(yAxisName);
	   String formatter="{value} "+unit;
	   AxisLabel axisLabel=new AxisLabel(formatter);
	   y1.setAxisLabel(axisLabel);
	   yAxis.add(y1);
	   option.setyAxis(yAxis);
	   //设置工具栏
	   ChartsToolBoxBean toolbox=new ChartsToolBoxBean();
	   Feature feature=new Feature(new Mark(false),new DataZoom(false),new DataView(true),
				new MagicType(true,new String[]{"line","bar","stack","tiled"}),new  Restore(true),new SaveAsImage(true));
	   toolbox.setFeature(feature);
	   option.setToolbox(toolbox);
	   //数据
	   option.setSeries(series);
	   JSONObject  jsonObject = JSONObject.fromObject(option);
	   return jsonObject;
   }
   
   /**
    * 温度计式图表
    * @param text 标题
    * @param fontSize
    * @param xAxisName x轴名称
    * @param yAxisName y轴名称
    * @param xData x轴值
    * @param unit单位 如"%"、"℃"之类
    * @param thermometerList数据
    * @return
    */
   public static JSONObject createTermometer(String text,int fontSize,String xAxisName,String xData[],
			  String yAxisName,String unit,List<ThermometerBean> thermometerList){
	   ThermometerOptionBean option=new ThermometerOptionBean();
	   //设置标题
	   ChartsTitleBean title=new ChartsTitleBean();
	   title.setText(text);
	   TextStyle textStyle=new TextStyle();
	   textStyle.setFontSize(fontSize);
	   option.setTitle(title);
	   //设置工具栏
	   ChartsToolBoxBean toolbox=new ChartsToolBoxBean();
	   Feature feature=new Feature(new Mark(true),new DataZoom(false),new DataView(true),
				new MagicType(false),new Restore(true),new SaveAsImage(true));
	   toolbox.setFeature(feature);
	   option.setToolbox(toolbox);
	   //设置图例
	   LegendBean legendBean=new LegendBean();
	   String[] legendData=new String[thermometerList.size()];
	   if(!Toolkit.isEmpty(thermometerList)&&thermometerList.size()>0){
		   for (int i = 0; i < thermometerList.size(); i++) {
			   legendData[i]=thermometerList.get(i).getName();
		}
	   }
	   legendBean.setSelectedMode(true);
	   legendBean.setData(legendData);
	   option.setLegend(legendBean);
	   //设置x轴
	   List<XAxisBean> xAxis=new ArrayList<XAxisBean>();
	   XAxisBean x1=new XAxisBean(true);
	   x1.setName(xAxisName);
	   if(xData==null){
		   xData=new String[1];
		   xData[0]="";
	   }
	   x1.setData(xData);
	   xAxis.add(x1);
	   option.setxAxis(xAxis);
	   //设置y轴
	   List<YAxisBean> yAxis=new ArrayList<YAxisBean>();
	   YAxisBean y1=new YAxisBean();
	   y1.setName(yAxisName);
	   String formatter="{value} "+unit;
	   AxisLabel axisLabel=new AxisLabel(formatter);
	   y1.setAxisLabel(axisLabel);
	   yAxis.add(y1);
	   option.setyAxis(yAxis);
	   List<ThermometerSeriesBean> series=new ArrayList<ThermometerSeriesBean>();
	   if(!Toolkit.isEmpty(thermometerList)&&thermometerList.size()>1){
		   ThermometerBean thermometer= thermometerList.get(0);
		   ThermometerSeriesBean temp=new ThermometerSeriesBean();
		   temp.setName(thermometer.getName());
		   temp.setData(thermometer.getData());
		   temp.setStack("stack");
		   ThermometerItemStyle itemStyle=new ThermometerItemStyle(new ThermometerBaseItemStyle("tomato", new Label("insideTop",new TextStyle("#fff"))));
		   temp.setItemStyle(itemStyle);
		   temp.setType("bar");
		   series.add(temp);
		   
		   ThermometerBean thermometerBean= thermometerList.get(1);
		   ThermometerSeriesBean tempBean=new ThermometerSeriesBean();
		   tempBean.setName(thermometerBean.getName());
		   tempBean.setData(thermometerBean.getData());
		   tempBean.setStack("stack");
		   tempBean.setType("bar");
		   ThermometerItemStyle itemStyleBean=new ThermometerItemStyle(new ThermometerBaseItemStyle("#fff",
				   new Label("top",new TextStyle("tomato"))));
		   tempBean.setItemStyle(itemStyleBean);
		   series.add(tempBean);
	   }
	   //数据
	   option.setSeries(series);
	   JSONObject  jsonObject = JSONObject.fromObject(option);
	   return jsonObject;
   }
   /**
    * 创建漏斗图
    * @return
    * @param text 标题
    * @param fontSize 字体大小
    * @param dataList漏斗图的值
    */
   public static JSONObject createFunnel(String text,int fontSize,List<ChartsDataBean> dataList){
	   FunnelOptionBean option=new FunnelOptionBean();
	   //设置标题
//	   ChartsTitleBean title=new ChartsTitleBean();
//	   title.setText(text);
//	   TextStyle textStyle=new TextStyle();
//	   textStyle.setFontSize(fontSize);
//	   title.setTextStyle(textStyle);
//	   option.setTitle(title);
	   //设置图例
	   LegendBean legendBean=new LegendBean();
	   String[] legendData=new String[dataList.size()];
	   if(!Toolkit.isEmpty(dataList)&&dataList.size()>0){
		   for (int i = 0; i < dataList.size(); i++) {
			   legendData[i]=dataList.get(i).getName();
		}
	   }
	 
	   legendBean.setX("center");
	   legendBean.setY("bottom"); 
	   legendBean.setSelectedMode(true);
	   legendBean.setData(legendData);
	   option.setLegend(legendBean);
	   //设置工具提示
	   ChartsToolTipBean tooltip=new ChartsToolTipBean("item","{a} <br/>{b} : {c}");
	   option.setTooltip(tooltip);
	   //设置工具栏
	   ChartsToolBoxBean toolbox=new ChartsToolBoxBean(true,new Feature(new Mark(false),
			   new DataView(true),new MagicType(false),new Restore(true),new SaveAsImage(true)));
	   toolbox.setShow(true);
	   option.setToolbox(toolbox);
	   //数据
	   List<ChartsFunnelSeriesBean> series=new ArrayList<ChartsFunnelSeriesBean>();
	   ChartsFunnelSeriesBean funnelSeries=new ChartsFunnelSeriesBean();
	   funnelSeries.setName(text);
	   funnelSeries.setData(dataList);
	   funnelSeries.setType("funnel");
	   series.add(funnelSeries);
	   option.setSeries(series);
	   JSONObject  jsonObject =JSONObject.fromObject(option);
	   return jsonObject;
   }
   /**
    * 创建玫瑰图
    * @return
    * @param text标题
    * @param fontSize字体大小
    * 
    */
   public static JSONObject createRose(String text,int fontSize,
		List<PieDataBean> data){
	   RoseDiagramOptionBean option =new RoseDiagramOptionBean();
	   //设置标题
	   ChartsTitleBean title=new ChartsTitleBean();
	   title.setText(text);
	   TextStyle textStyle=new TextStyle();
	   textStyle.setFontSize(fontSize);
	   option.setTitle(title);
	   //设置工具提示
	   ChartsToolTipBean tooltip=new ChartsToolTipBean();
	   tooltip.setFormatter("{a} <br/>{b} : {c} ({d}%)");
	   tooltip.setTrigger("item");
	   option.setTooltip(tooltip);
	   //设置工具栏
	   ChartsToolBoxBean toolbox=new ChartsToolBoxBean(true);
	   String type[]={"pie", "funnel"};
	   Feature feature=new Feature(new Mark(false),new DataView(true),
				new MagicType(true,type),new  Restore(true),new  SaveAsImage(true));
	   toolbox.setFeature(feature);
	   option.setToolbox(toolbox);
	   //设置图例
	   LegendBean legendBean=new LegendBean("center","bottom");
	   String[] legendData=new String[data.size()];
	   if(!Toolkit.isEmpty(data)&&data.size()>0){
		   for (int i = 0; i < data.size(); i++) {
			   legendData[i]=data.get(i).getName();
		}
	   }
	   legendBean.setData(legendData);
	   option.setLegend(legendBean);
	   List<ChartsRoseSeriesBean> series=new ArrayList<ChartsRoseSeriesBean>();
	   ChartsRoseSeriesBean roseSeries=new ChartsRoseSeriesBean();
	   //数据
	   String radius[]={"20","110"};
	   roseSeries.setName(text);
	   roseSeries.setType("pie");
	   roseSeries.setRadius(radius);
	   roseSeries.setRoseType("area");
	   roseSeries.setData(data);
	   series.add(roseSeries);
	   option.setSeries(series);
	   JSONObject  jsonObject = JSONObject.fromObject(option);
	   return jsonObject;
   }
   /**
    * 创建中国地图
    * @return
    * @param text柱状图标题
    * @param fontSize字体大小
    * 
    */
   public static JSONObject createChinaMap(String text,int fontSize,List<MapDataBean> data){
	   ChinaMapOptionBean option =new ChinaMapOptionBean();
	   List<ChartsMapSeriesBean> series=new ArrayList<ChartsMapSeriesBean>();
	   String[] legendData=new String[data.size()];
	   List<ChartsDataBean> valueList=new ArrayList<ChartsDataBean>();
	   int dataRangeMax=0;
	   if(!Toolkit.isEmpty(data)&&data.size()>0){
		   for (int i = 0; i < data.size(); i++) {
			   legendData[i]=data.get(i).getName();
			   ChartsMapSeriesBean seriesBean=new ChartsMapSeriesBean();
			   seriesBean.setMapType("china");
			   seriesBean.setType("map");
			   seriesBean.setName(data.get(i).getName());
			   seriesBean.setData(data.get(i).getData());
			   valueList.addAll(data.get(i).getData());
			   MapItemStyle itemStyle=new MapItemStyle();
			   MapItemStyleNormal normal=new MapItemStyleNormal();
			   MapItemStyleEmphasis emphasis=new MapItemStyleEmphasis();
			   itemStyle.setNormal(normal);
			   itemStyle.setEmphasis(emphasis);
			   seriesBean.setItemStyle(itemStyle);
			   series.add(seriesBean);
		   }
		   int valueArray[]=new int[valueList.size()];
		   for (int i = 0; i < valueList.size(); i++) {
			   valueArray[i]=Integer.parseInt(valueList.get(i).getValue());
		   }
		   Arrays.sort(valueArray);
		   dataRangeMax=valueArray[valueArray.length-1];
	   }
	   //设置标题
	   ChartsTitleBean title=new ChartsTitleBean();
	   title.setText(text);
	   title.setX("center");
	   TextStyle textStyle=new TextStyle();
	   textStyle.setFontSize(fontSize);
	   option.setTitle(title);
	   //设置工具提示
	   ChartsChinaMapToolTipBean tooltip=new ChartsChinaMapToolTipBean();
	   tooltip.setTrigger("item");
	   option.setTooltip(tooltip);
	   //设置工具栏
	   ChartsToolBoxBean toolbox=new ChartsToolBoxBean(true);
	   String type[]={""};
	   Feature feature=new Feature(new Mark(true),new DataView(true),
				new MagicType(false,type),new  Restore(true),new  SaveAsImage(true));
	   toolbox.setFeature(feature);
	   toolbox.setX("right");
	   toolbox.setY("center");
	   toolbox.setOrient("vertical");
	   option.setToolbox(toolbox);
	   //值域漫游
	   DataRange dataRange=new DataRange();
	   dataRange.setX("left");
	   dataRange.setY("bottom");
	   dataRange.setCalculable(true);
	   dataRange.setMin(0);
	   dataRange.setMax(dataRangeMax);
	   option.setDataRange(dataRange);
	   //指定漫游组件可控地图类型
	   RoamController roamController=new RoamController();
	   roamController.setShow(true);
	   roamController.setX("right");
	   MapTypeControl mapTypeControl=new MapTypeControl();
	   mapTypeControl.setChina(true);
	   roamController.setMapTypeControl(mapTypeControl);
	   option.setRoamController(roamController);
	   //设置图例
	   LegendBean legend=new LegendBean();
	   legend.setOrient("vertical");
	   legend.setX("left");
	   legend.setData(legendData);
	   option.setLegend(legend);
	   //设置数据
	   option.setSeries(series);
	   JSONObject  jsonObject = JSONObject.fromObject(option);
	   return jsonObject;
   }
}
