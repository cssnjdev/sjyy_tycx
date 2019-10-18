$(function(){
	
	LayOutBody();
	
	searchYy();
	
});
 
/**
 * 保存基本信息
 */
function searchYy(){
	
 	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=YyfwWdgzService_searchYy&a="+Math.random(),//路径
		success:function(result) {//返回数据根据结果进行相应的处理	
			if(result.success==1){
				initFxyy(result.list,result.toplist);
			}
		},
		error:function(e){
		
		}
	});
	 
}
	
function initFxyy(list,toplist){
	 
	
	$("#yy_container>.row").empty();
	
 	
	for(var i=0;i<toplist.length;i++){
			 
		   var item = toplist[i];
			
		   var col  = 
				' <div class="col-lg-4 col-md-12 colbox" fxyy_id="'+item["fxyy_id"]+'"  > '+ 
				'  <div class="block">			    '+		
			    '    <div class="block_title">      '+  
			    '       <span></span>			    '+
			    '        <b>'+item["fxyy_mc"]+'<i class="glyphicon glyphicon-info-sign" title="帮助信息"  onclick="openYyhelp(\''+item["fxyy_id"]+'\',\''+item["fxyy_mc"]+'\')"></i></b> \n'+
			    '        <i class="glyphicon glyphicon-fullscreen "  onclick="openQp(\''+item["fxyy_id"]+'\',\''+item["fxyy_mc"]+'\')" title="全屏展示"></i> \n'+
			    '        <a class="float_r" onclick="openYYxq(\''+item["fxyy_id"]+'\',\''+item["fxyy_mc"]+'\')">详情</a> \n'+
			    '        <a class="float_r" onclick="upTop(this,\''+item["fxyy_id"]+'\',\''+item["fxyy_mc"]+'\');">置顶&nbsp;&nbsp;</a> \n'+
			    '     </div>       										 \n'+
			    '     <div class="block_query" style="height:300px;">	 \n'+
			    '      <iframe src="/tykf/request_http?tld=YyunitService_Gyxx&fxyy_id='+item["fxyy_id"]+'" scrolling="no"> </iframe>   \n' +    	
			    '     </div>            \n'+
				'   </div>      		\n'+        
			    ' </div>				' ;
			
		   $("#yy_container >.row").append(col) ;
		
	}
	 
	
	for(var i=0;i<list.length;i++){
		
			var item = list[i];
			
			var col = 
				' <div class="col-lg-4 col-md-12 colbox" fxyy_id="'+item["fxyy_id"]+'" > '+
				'  <div class="block">			   '+		
			    '    <div class="block_title">     '+  
			    '       <span></span>			   '+
			    '        <b>'+item["fxyy_mc"]+'<i class="glyphicon glyphicon-info-sign" title="帮助信息" onclick="openYyhelp(\''+item["fxyy_id"]+'\',\''+item["fxyy_mc"]+'\')"></i></b> \n'+ 
			    '        <i class="glyphicon glyphicon-fullscreen "  onclick="openQp(\''+item["fxyy_id"]+'\',\''+item["fxyy_mc"]+'\')"  title="全屏展示"></i> \n'+
			    '        <a class="float_r" onclick="openYYxq(\''+item["fxyy_id"]+'\',\''+item["fxyy_mc"]+'\')">详情</a> \n'+
			    '        <a class="float_r" onclick="upTop(this,\''+item["fxyy_id"]+'\',\''+item["fxyy_mc"]+'\');">置顶&nbsp;&nbsp;</a> \n'+
			    '     </div>       										   \n'+
			    '     <div class="block_query" style="height:300px;">	   \n'+
			    '      <iframe src="/tykf/request_http?tld=YyunitService_Gyxx&fxyy_id='+item["fxyy_id"]+'" scrolling="no"> </iframe>   \n' +    	
			    '     </div>            \n'+
				'   </div>      		\n'+        
			    ' </div>				  ';
			
			 $("#yy_container >.row").append(col) ;
		
	}
	  
}
 

/**
 * 
 */

function upTop(obj,fxyy_id,fxyy_mc){ 
	
	var html = $(obj).closest(".colbox").html();
	
	$("#yy_container >.row").prepend(
			'<div class="col-lg-4 col-md-12 colbox" style="display:none;"  fxyy_id="'+fxyy_id+'" > '+
			'  <div class="block">			   '+		
		    '     <div class="block_title">     '+  
		    '       <span></span>			   '+
		    '        <b>'+fxyy_mc+'<i class="glyphicon glyphicon-info-sign" title="帮助信息"  onclick="openYyhelp(\''+fxyy_id+'\',\''+fxyy_mc+'\')"></i></b> \n'+ 
		    '        <i class="glyphicon glyphicon-fullscreen "  onclick="openQp(\''+fxyy_id+'\',\''+fxyy_mc+'\')" title="全屏展示"></i> \n'+
		    '        <a class="float_r" onclick="openYYxq(\''+fxyy_id+'\',\''+fxyy_mc+'\')">详情</a> \n'+
		    '        <a class="float_r" onclick="upTop(this,\''+fxyy_id+'\',\''+fxyy_mc+'\');">置顶&nbsp;&nbsp;</a> \n'+
		    '     </div>       										   \n'+
		    '     <div class="block_query" style="height:300px;">	   \n'+
		    '         <iframe scrolling="no"> </iframe>   \n' +    	
		    '     </div>            \n'+
			'   </div>      		\n'+   
			'</div>' );
	
	$(obj).closest(".colbox").hide(600,function(){
		
		$(obj).closest(".colbox").remove();
		
		var gzyy1 = $("#yy_container >.row>.colbox").eq(0).attr("fxyy_id");
		var gzyy2 = $("#yy_container >.row>.colbox").eq(1).attr("fxyy_id");
		var gzyy3 = $("#yy_container >.row>.colbox").eq(2).attr("fxyy_id");
	     
		$.ajax({  
			type : "POST",  //提交方式  
			dataType:'json',
			async:false,
			data:{
				'gzyy1':gzyy1,
				'gzyy2':gzyy2,
				'gzyy3':gzyy3
			},
			url:ctx+"tykf/request?tld=YyfwWdgzService_saveWdgz&a="+Math.random(),//路径
			success:function(result){ //返回数据根据结果进行相应的处理 	
				
				if(result.success==1){
				    parent.searchWdGz();
				}else{
					cssnj_alert(result.message);
				}
			},
			error:function(e){
				
			}
		});
		
	});
	
	$("#yy_container >.row>div:first").show(400,function(){
		$("#yy_container >.row>div:first iframe").eq(0).attr("src",ctx+"tykf/request_http?tld=YyunitService_Gyxx&fxyy_id="+fxyy_id);
	});
	
	
	
}




function openQp(fxyy_id,title){
	 
	  $("#YyMaxTitle").text(title);
	  $("#YyMaxModal").modal("show");
 	  $("#YyMaxBody").html("<iframe style='border:0;height:100%;width:100%;'></iframe>") 
	  setTimeout(function(){
		  $("#YyMaxBody>iframe").attr("src",ctx+"tykf/request_http?tld=YyunitService_Gyxx&fxyy_id="+fxyy_id)
	  },100);
 	  
 	  
}

function openYYxq(fxyy_id,title){
	
	parent.openYYxq(fxyy_id,title);
	
}

function openYyhelp(fxyy_id,title){
	
	cssnj_top_iframe(title,ctx+"tykf/request_http?tld=YyfbService_initNew&fxyy_id="+fxyy_id);
	
}

