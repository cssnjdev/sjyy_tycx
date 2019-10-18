
$(function(){
	
	LayOutBody();
	
});

function changeOwner(){
 
  var sjk = $("#sjk").val();
   
  $.ajax({ 
	        url:ctx+"tykf/request?tld=etl001DataunitService_selectUser",    //请求的url地址
	        dataType: "json",   //返回格式为json
	        data:{
	          "datasource_id":sjk
	        },
	        async:false,
	        type: "POST",   //请求方式
	        success: function(data){
	        	var html="<option value=''>全部</option>";
	        	for(var i=0;i<data.length;i++){
	        	  html +="<option value='"+data[i].CODE+"'>"+data[i].CAPTION+"</option>" ;
	        	}
 	        	$("#owner").html(html);
 	        	
	        },  error:function(require){
	        
	         	//$jq.cssnj.alert("查询异常");
	         	
	        }
      });
   
}
var table = null;
function search_dataTable(){
	var  data = $("#cxtj_form").cssnjGetFormData();
	if(table==null){
	    table = $("#example1").fyTable(ctx+"tykf/request?tld=YyfbService_addSjdyByCxtj&a=" + Math.random(),data);
	}else{
		table.reload(data);
	}
	 
}
function addSjdyByFxyyid(){
	if(table!=null){
		
		var fxyy_id = $("#fxyy_id").val();	
		var checkedRow = table.getCheckedData();
		if(checkedRow.length<1){
			return false;
		}
		 var jsonStr="[";
		 for(var i = 0;i<checkedRow.length;i++){		   
			   var row = checkedRow[i];		
			   var json_new="{";
			   
			   json_new=json_new+"'SJLY_DM':'"+row["SJLY_DM"]+"',";
			   json_new=json_new+"'VERSION':'"+row["VERSION"]+"',";
			   json_new=json_new+"'EN_NAME':'"+row["EN_NAME"]+"',";
			   json_new=json_new.substring(0, json_new.length-1);
			   
			   json_new=json_new+"}";
			   jsonStr=jsonStr+json_new+",";
		 }
		 jsonStr=jsonStr.substring(0, jsonStr.length-1);
		 jsonStr=jsonStr+"]";
	   
		 $.ajax({ 
		        url:ctx+"tykf/request?tld=YyfbService_insertSjly&a=" + Math.random(),    //请求的url地址
		        dataType: "json",   //返回格式为json
		        data:{
		          "fxyy_id":fxyy_id,
		          "sjlyData":jsonStr
		        },
		        async:false,
		        type: "POST",   //请求方式
		        success: function(data){
		        	
		        	parent.search_sjdyTable(fxyy_id);
		        	search_dataTable();
		        	      		 
		        }, error:function(require){
		        
		         	 
		        }
	    });
	}

}
