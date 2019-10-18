$(function(){
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		url:ctx+"tykf/request?tld=Tycx002DzcxService_executeQueryCharts",//路径
		data:{'txuuid':'62A327A48D5F7CD4EAEB84AB8D1D1974'},
		success : function(result) {//返回数据根据结果进行相应的处理
			alert(result[0]);
			$('#table').bootstrapTable('load', result);
			var chartOption=echarts.init(document.getElementById('barAndLine'));
			chartOption.setOption(result[0]);
//			var chartOption2=echarts.init(document.getElementById('bar'));
// 			chartOption2.setOption(result[1]);
			// Highcharts.chart('barAndLine', result[1]
 			// );
 			
		}  
	});
	
 
	
})

function EchartsShow(xData,yData){
	var myChart = echarts.init(document.getElementById('bar'));
	// 指定图表的配置项和数据
	var option = {
	   
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },   
	    /* legend: {
	        data:['文件源']
	    }, */
	    color:['#81CDE4'],
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    
	    xAxis:  {
	        type: 'value'
	    },
	    yAxis: {
	        type: 'category',
	        data: yData
	    },
	    series: [
	        {
	            name: '数据总量MB',
	            type: 'bar',
	            stack: '总量',
	            label: {
	                normal: {
	                    show: true,
	                    position: 'insideRight'
	                }
	            },
	            data: xData
	        }
	    ]
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	var data=encodeURIComponent(myChart.getDataURL("png"));
		alert(data);
}