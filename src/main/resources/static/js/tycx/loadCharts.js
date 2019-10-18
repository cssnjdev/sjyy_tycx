 
function loadChart(obj,option){
 
	var myChart = echarts.init(obj,"subject_1");
	myChart.clear();
	myChart.setOption(option);
	
	return myChart; 
	
}

function loadChartAsLine(obj,wdArr,dataArr,dl){
	
	
	
}

function loadChartAsPie(obj,wdArr,dataArr,dlArr,title,showLegeng){
	var  data = new Array();
	 
	var legend = null;
	 
	for(var i=0;i<dataArr.length;i++){
 			 
		 var dt = {
			"name":wdArr[i],
			"value":dataArr[i][dlArr[0]["dldm"]]	 
		 }
		 data.push(dt);
		  
	} 	
	
	if(showLegeng){
		
	   legend ={
	        orient:'vertical',
	        left  :'10',
	        top   :'10',
	        data  :wdArr
	   }
	   
	}
	 
	option = {
		    title : {
		        text: title+"-"+dlArr[0]["dlmc"],
		        x:'center'
		    },
		    toolbox: {
		        feature: {
		            saveAsImage: {
		            	show:true
		            }
		        }
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend:legend,//左上角显示关闭
		    series : [
		        {
		            name: '访问来源',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:data, 
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
	
	return loadChart(obj,option);
	
}

function loadChartAsBar(obj,wdArr,dataArr,dlArr,title,fxdata){
 
 	
	var colors = ["#ff7f50","#87cefa","#da70d6","#32cd32","#6495ed","#ff69b4","#ba55d3","#cd5c5c","#ffa500",
	             "#40e0d0","#07a2a4","#9a7fd1","#588dd5","#f5994e","#c05050","#59678c",
	             "#c9ab00","#7eb00a","#6f5553","#c14089"];
	
	var seriesData = new Array(); 
	var mcArr = [];
	
	for(var i=0;i<dlArr.length;i++){
		
	    var arr = new Array();
	   
	    var dl = dlArr[i];
	    var dm = dl["dldm"];
	    
	    for(var k=0;k<dataArr.length;k++){
		   var data = dataArr[k];
		   arr.push(data[dm])
	    }
	 
	    var type = "bar";
	    if(dl["type"]){
	    	type = dl["type"];
	    }
		
	    var series = {
			  name: dl["dlmc"],
		      type:type,
		      smooth:false,  //true 让曲线变平滑的 
		      barMaxWidth:'40',
		      data:arr,
		      symbolSize:10,
	          symbol:'circle'
		}
 
	    if(fxdata.DLINDEX){
	    	
	    	var dlindex = fxdata.DLINDEX;
	    	
	    	var arr = dlindex.split(",");
	    	 
	    	for(var k=0;k<arr.length;k++){
 
	    		if(dl["dldm"] == arr[k].split(":")[0]){
 		    		series["yAxisIndex"] = arr[k].split(":")[1];

	    		} 
	    	}
	    	
	    }
	    
	    
	    seriesData.push(series);
		mcArr.push(dl["dlmc"]);
		
	} 	
	
 	var yAxisData = [{
	    	            type : 'value'
	    	        }]
	
	if(fxdata.DLDW){ // 循环并取出度量的单位 和 创建图形 的Y轴
		
		var dldw = fxdata.DLDW;
		var dldwArr= dldw.split(",");
		yAxisData = []; 
		
		for(var i=0;i<dldwArr.length;i++){
			
			var mc=dldwArr[i].split(":")[0];
			var dw = dldwArr[i].split(":")[1];
			var offset = 0;
			if(i>1){
				offset = 60*(i-1);
			}

			yAxisData.push({
				 	type: 'value',
		            name: mc,
		            nameTextStyle:{
		            	padding: [0,0,-5,0]
		            },
		            offset: offset,
		            axisLine: {
		                lineStyle: {
		                    color: colors[i]
		                }
		            },
		            axisLabel: {
		                formatter: '{value} '+dw
		            }
			}); 
			
		}
		
	}
	
 	var rotate =0; // 旋转
 	
 	if(wdArr.length>8){
 		rotate = 45;
 	}else if(wdArr.length>5){
 		rotate = 30;
 	}
 	
 	var wddw = fxdata.WDDW;
 	var xAxisName = "";
 	var xFormatter = "";
 	if(wddw){
 		try{
	 		var dwArr = wddw.split(",");
	 		var dw = dwArr[0]
	 		xAxisName = dw.split(":")[0];
	 		xFormatter = ' '+dw.split(":")[1];
 		}catch(e){
 			
 		}
 	}
 	 
 	option = {
		title: {
		        text: title,
		        x:'10'
		},
		toolbox: {
			"feature":{
				"saveAsImage": {
					"show": true
				}
			},
			"right":'20'
		},
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	    	'top':'-5',
	        data:mcArr
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data : wdArr,
	            name:xAxisName,
	            nameTextStyle:{
	            	fontSize:'13',
	            	color:'red',
	            	padding: [30,50,0,0],
	            },
	            axisLabel: {
	                formatter: '{value}'+xFormatter
	            },
	            axisLabel:{  
                    interval:0,  
                    rotate:rotate//倾斜度 -90 至 90 默认为0  
                }, 
	        }
	    ],
	    yAxis : yAxisData,
	    series:seriesData
	}
	
	return loadChart(obj,option);
	
}

function loadChartAsBar2(obj,wdArr,dataArr,dlArr,title){
	
	var seriesData = new Array(); 
	 
	var mcArr = [];
	
	for(var i=0;i<dlArr.length;i++){
		
	    var arr = new Array();
	   
	    var dl = dlArr[i];
	    var dm = dl["dldm"];
	    
	    for(var k=0;k<dataArr.length;k++){
		   var data = dataArr[k];
		   arr.push(data[dm])
	    }
	 
	    var series = {
			  name: dl["dlmc"],
		      type:"bar",   
		      data:arr,
		      barMaxWidth:'50'
		}
		
	    seriesData.push(series);
		mcArr.push(dl["dlmc"]);
		
	} 	
	
     
   var 	option = {
	    title: {
	        text: title,
	        x:'10'
	      //  subtext: '数据来自网络'
	    },
	    toolbox: {
			feature: {
				saveAsImage: {
					show: true
				}
			}
		},
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    legend: {
	      //  data: ['2011年', '2012年']
	    	data:mcArr
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis: {
	        type: 'value',
	        boundaryGap: [0, 0.01]
	    },
	    yAxis: {
	        type: 'category',
	       // data: ['巴西','印尼','美国','印度','中国','世界人口(万)']
	        data:wdArr
	    },
	    series:seriesData
	};
	
    return loadChart(obj,option);
  
}

//雷达
function loadChartAsRadar(obj,wdArr,dataArr,dlArr,title){
	 
	function getMax(dm){
		var max =0;
		for(var i=0;i<dataArr.length;i++){
			var data = dataArr[i];
			var val = data[dm];
			if(val>max){
				max =val;
			}
		}
		return max;
	}
	
 	 
	var indicator = [];
	
	for(var i=0;i<dlArr.length;i++){
		
	    var arr = new Array();
	   
	    var dl = dlArr[i];
	    var dm = dl["dldm"];
	    
	    indicator.push({
	    	"dm":dm,
	    	"name":dl["dlmc"],
	    	"max":getMax(dm)
	    });
	   
 	} 	
	
	var seriesData = []
   
	for(var i=0;i<dataArr.length;i++) {
		
		var data = dataArr[i];
		var valArr=[];
		for(var key in data){
			valArr.push(data[key]);
		}
		
		seriesData.push({
			name : wdArr[i],
			value :valArr
		})
			
			
	} 
 
	var option = {
		    title: {
		        text: title//
		    },
		    tooltip: {},
		    legend: {
		    	orient: 'vertical',
		        left: '10',
		        top:'25',
		        data: wdArr
		    },
			toolbox: {
				"feature":{
					"saveAsImage": {
						"show": true
					}
				}
			},
		    radar: {
		        // shape: 'circle',
		        name: {
		            textStyle: {
		                color: '#fff',
		                backgroundColor: '#999',
		                borderRadius: 3,
		                padding: [3, 5]
		           }
		        },
		        indicator: indicator//
		    },
		    series: [{
		        name: title,//
		        type: 'radar',
 		        data : seriesData //
		    }]
		};
	
      return loadChart(obj,option);
}

