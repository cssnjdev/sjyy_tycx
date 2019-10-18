
var fxyy_id = null;

$(function(){
	 
	 $("body").layout({ 
          spacing_open:0,
          spacing_closed:0,
          closable:false, 
          resizable:true
     });
	 
	 fxyy_id = $("#fxyy_id").val();
	 
	 if(fxyy_id==null){
		  return false;
	 }
	 
	 var flag =true;
	 
	 flag =initBqFl();
	 
	 if(flag){
		 flag = initBq();
	 }
	 
	 if(flag){
		 flag = initYYbq(); 
	 }
	 
	 
});


function initBqFl(){
	
	var list =null;
	
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=YyfwBqglService_searchBqFl",//路径
		success : function(result) {//返回数据根据结果进行相应的处理
			list = result;
			
			
		},
		error:function(e){
			
		} 
    });
	 
	
	if(list==null){
		return false;
	}
	 
	for(var i=0;i<list.length;i++){
	  
		var obj = list[i];
		
		$("#center").append(
				' <div class="panel panel-default" id="'+obj["bqfl_dm"]+'">						\n'+
		    	' 	<div class="panel-heading">	\n'+
		    	'     	<span class="glyphicon glyphicon-hand-right"></span> '+obj["bqfl_mc"]+' \n'+
		    	' 	</div>						\n'+
		        ' 	<div class="panel-body">	\n'+
		        ' 	</div>						\n'+
			   ' </div> \n');
		
		if(i==0){
			$("#bqfl").append('<option value="'+obj["bqfl_dm"]+'" selected="selected" >'+obj["bqfl_mc"]+'</option>');
		}else{
			$("#bqfl").append('<option value="'+obj["bqfl_dm"]+'" >'+obj["bqfl_mc"]+'</option>');
		}
		
	}
	
	return true;
}


function initBq(){
	
	var list =null;
	
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=YyfwBqglService_searchAllBq",//路径
		success : function(result) {//返回数据根据结果进行相应的处理
			list = result;
		},
		error:function(e){
			
		} 
    });
	
	if(list==null){
		return false;
	}
	
 	for(var i=0;i<list.length;i++){
		  
		var obj = list[i];
		$(".panel#"+obj["bqfl_dm"]+">.panel-body").append( 
				'<div class="bq" id="'+obj["bq_id"]+'"><label><input type="checkbox" name="bqchk" value="'+obj["bq_id"]+'"  title="'+obj["bq_mc"]+'" />&nbsp;'+obj["bq_mc"]+'</label></div> '
		);
		 
	}
	
	return true;
	
}

function initYYbq(){
	
	var list =null;
	
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		data:{
			'fxyy_id':fxyy_id
		},
		url:ctx+"tykf/request?tld=YyfwBqglService_searchYYbq",//路径
		success : function(result) {//返回数据根据结果进行相应的处理
			list = result;
		},
		error:function(e){
			
		} 
    });
	
	if(list==null){
		return false;
	}
	
	for(var i=0;i<list.length;i++){
		  
		var obj = list[i];
		$(".bq#"+obj["bq_id"]+" input").attr("checked",true);
		 
	}
	
	return true;
	
}

function save(){
	
	var arr = $("input[name='bqchk']:checked");
	
 	var forDraw = new Array();
	
	for(var i=0;i<arr.length;i++){
        var obj = arr[i];
		forDraw.push({
			'bq_id':$(obj).val(),
			'bq_mc':$(obj).attr("title")
		});
	}
	
	parent.saveYyBq(forDraw);
	 
	cssnj_closeIframe();
	
}


function add(){
	
	var bq_mc = $("#bq_mc").val().trim();
	if(bq_mc==null||bq_mc.trim()==""){
		return false;
	}
	
	var bqfl_dm = $("#bqfl").val();
	
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		data:{
			'bq_mc':bq_mc,
			"bqfl_dm":bqfl_dm
		},
		url:ctx+"tykf/request?tld=YyfwBqglService_addBq",//路径
		success : function(result) {//返回数据根据结果进行相应的处理
		   if(result.success==1){
			   
			   $(".panel#"+bqfl_dm+">.panel-body").append( 
						'<div class="bq" id="'+result.bq_id+'"><label><input type="checkbox" name="bqchk" value="'+result["bq_id"]+'"  title="'+bq_mc+'" checked />&nbsp;'+bq_mc+'</label></div> '
			   );
			   
		   }else{
			   
		   }
		},
		error:function(e){
			
		} 
    });
	
}

