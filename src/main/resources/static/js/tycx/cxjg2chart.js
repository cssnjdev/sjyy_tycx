
function getChartsParams(sqlxh,queryParams){ // sql序号加查询条件，返回后台结果
	
	var chartsParams = null;
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=Tycx002DzcxService_executeQueryCharts",//路径
		data:{'sqlxh':sqlxh},
		success : function(result) {//返回数据根据结果进行相应的处理
			
			chartsParams = result;
			 
		}  
	});
	
	return chartsParams;
	
	
	
}

function initEchart(obj,option){//装载指定对象，与指定数据 并返回结果对象
	
	var chart=echarts.init(obj);
	chart.setOption(option);
	return chart;
	
}