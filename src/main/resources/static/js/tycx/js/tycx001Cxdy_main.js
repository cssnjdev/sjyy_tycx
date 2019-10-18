$(function(){
	search_dataTable();
});


var table = null;

function search_dataTable(){
	
	var data = $("#cxtj_form").cssnjGetFormData();
 	
 	if(table==null){
 		
	    table = $("#example").fyTable(ctx+"tykf/request?tld=tycx001CxCxdyService_selectCxdy&a=" + Math.random(),data);
	    
	}else{
		
		table.reload(data);	
		
	}	
	 
}

function openForm(){
	//$("#myModal1").modal("show");initNew
	cssnj_window(ctx+"tykf/request_http?tld=tycx001CxCxdyService_initNew");
	
}
function openMx(rowData,rowEl,el,colparam){
	  
//	  $("#cxdyForm").cssnjSetFormAsJson(rowData) ;
//	  $("#myModal1").modal("show");
//	  //查询 查询结果列和查询条件列
//	  var sqlxh=rowData.sqlxh;
//	  search_cxjgTable(sqlxh);
//	  search_cxtjTable(sqlxh);
	var sqlxh=rowData.sqlxh;
	if(colparam.name=="sqlxh"){
		cssnj_window(ctx+"tykf/request_http?tld=tycx001CxCxdyService_initNew&sqlxh="+sqlxh);
	}else{
		cssnj_window(ctx+"tykf/request_http?tld=Tycx002DzcxService_initView&sqlxh="+sqlxh);
	}
}

function openCxjglMx(rowData,rowEl,el,colparam){
	 $("#cxjgform1").cssnjSetFormAsJson(rowData) ;
	  $("#cxjgForm").modal("show");
}
function openCxtjlMx(rowData,rowEl,el,colparam){

	 $("#cxtjform1").cssnjSetFormAsJson(rowData) ;
	  $("#cxtjForm").modal("show");
}
function search_cxjgTable(sqlxh){
	var data={'sqlxh':sqlxh};
	$("#cxjgl").dataTable().fnDestroy();
	var cxjgTable = $("#cxjgl").fyTable(ctx+"tykf/request?tld=tycx001CxCxjgdyService_selectBySqlxh&a=" + Math.random(),data);
}
function search_cxtjTable(sqlxh){
	var data={'sqlxh':sqlxh};
	$("#cxtjl").dataTable().fnDestroy();
	var cxtjTable = $("#cxtjl").fyTable(ctx+"tykf/request?tld=tycx001CxCxtjdyService_selectBySqlxh&a=" + Math.random(),data);
}
function initResult(){
	var updata = $("#cxdyForm").cssnjGetFormData();
	var sqlxh=updata.sqlxh;
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx001CxCxdyService_initResult",//路径
		data:{'sqlxh':sqlxh},
		success : function(result) {//返回数据根据结果进行相应的处理		
//			for(var i=0;i<result.length;i++){
//				var res=result[i];
//			$('#cxjgl>tbody').append( " <tr> 											\n" +
//					"    <td width='30px' classname='leftAlign' style='text-align:left;'>   \n" + 
//					"        <input type='checkbox' name='col_id' value='' />				\n" + 
//					"    </td>																\n" + 
//					"    <td  width='5%' name='xh' className='leftAlign' >						\n" + 
//					       res.lmc+ 
//					"     </td>																												\n" + 
//					"    <td width='25%'  classname='leftAlign'>																			\n" + 
//					       res.lms + 
//					"    </td>\n" + 
//					"    <td width='15%'  classname='leftAlign'>\n" + 
//					       res.sjlmc+
//					"    </td>\n" + 
//					"    <td width='15%'  classname='leftAlign'>\n" + 
//					     res.llx+ 
//					"    </td>\n" + 
//					"    <td width='15%'  classname='leftAlign'>\n" + 
//					     res.dqfs + 
//					"    </td>\n" + 
//					"    <td width='15%'  classname='leftAlign'>\n" + 
//					     res.tjlx + 
//					"    </td>\n" + 
//					"    <td width='15%'  classname='leftAlign'>\n" + 
//					     res.xsxh + 
//					"    </td>\n" + 
//					" </tr>"
//					)
//			}
			search_cxjgTable(sqlxh);
		},
		error:function(e){
			
		} 
 });
	
	
}
function initCxtjl(){

	var updata = $("#cxdyForm").cssnjGetFormData();
	var sqlxh=updata.sqlxh;
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx001CxCxdyService_initCxtj",//路径
		data:{'sqlxh':sqlxh},
		success : function(result) {//返回数据根据结果进行相应的处理
			search_cxtjTable(sqlxh);
		},
		error:function(e){
			
		} 
 });
}
function saveCxjgl(){
	var updata = $("#cxjgform1").cssnjGetFormData();
 	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx001CxCxjgdyService_updateByPKeySelective",//路径
		data:updata,
		success : function(result) {//返回数据根据结果进行相应的处理	
 		         var sqlxh=updata.sqlxh;
 		         search_cxjgTable(sqlxh);
				 $.cssnj.alert(result.message);			
			 
		},
		error:function(e){
			
		} 
 });
}
function saveCxtjl(){
	var updata = $("#cxtjform1").cssnjGetFormData();
 	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx001CxCxtjdyService_updateByPKeySelective",//路径
		data:updata,
		success : function(result) {//返回数据根据结果进行相应的处理	
 		         var sqlxh=updata.sqlxh;
 		         search_cxtjTable(sqlxh);
				 $.cssnj.alert(result.message);			
			 
		},
		error:function(e){
			
		} 
 });
}
function zfCheck(){
	if(table!=null){
		   var checkedRow = table.getCheckedData();
		  if(checkedRow.length==0){
		  	$.cssnj.alert("请选择一行!")
		  }
		   var sqlxhList = new Array();
	 	   for(var i = 0;i<checkedRow.length;i++){
			   
			   var row = checkedRow[i];
			   
			   sqlxhList.push(row["sqlxh"]);
			   
		   }		   
		
		}
	$.cssnj.messager("确定作废","确定作废？",{
        "onOk":function(){
       
	          $.ajax({
				    url: ctx+"tykf/request?tld=tycx001CxCxdyService_updateByPKeySelective",    //请求的url地址
					    data: { "sqlxh":sqlxhList.join(",")},    //参数值
					dataType: "json",   //返回格式为json
    		 		async:false,
    				type: "POST",   //请求方式
				    success: function(data){
				    
						    if(data.state == "1"){
				      	    
				      	    $.cssnj.alert("作废成功！");
				      	    
								table.reload();
								
				     	}
						
						if(data.state == "0"){
						
			          		$.cssnj.alert("删除失败！");

			     		}
			     		
				    }	
			  });
			  
         
        }
    
    
    });
}
