
(function($){
	
	
	
})(jQuery);



function getTableHeadParams(sqlxh,queryParams){  // 查询标题配置参数
	 
	
	var headParams = null;
	
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=Tycx002DzcxService_getCxjgl",//路径
		data:{'sqlxh':sqlxh},
		success : function(result) {//返回数据根据结果进行相应的处理
			
			headParams = result;
			 
		}  
	});
	
	return headParams;
	
	
}


function initDataTableHead(obj,headParams){
	
	var  minHeight = $(obj).height();
 	
	var id = "dt_"+13255
	
	var tableHtml = "<table id='"+id+"' class=\"nowrap display dataTable\"  selectType=\"1\"   cellspacing=\"0\" width=\"100%\" minHeight='"+minHeight+"'> " +
			"<thead><tr class='namesTr'>" ;
			
	
	for(var i=0;i<headParams.length;i++){
		
		var tdParam = headParams[i];
		var td = "<td name='"+tdParam.lmc+"' dataType='"+tdParam.llx+"' className='dt_"+tdParam.llx+"' width='100'>"+tdParam.lms+"</td>"
		tableHtml=tableHtml+td;
	}
	
	
	tableHtml = tableHtml + "</tr></thead></table>";
	
	$(obj).html(tableHtml);
	
	return tableEl = $(obj).find("table").eq(0);
	
}


function QueryTableData(obj,sqlxh,queryParams,headParams){
	
 	var datas = {
		'sqlxh':sqlxh,
		'queryParams':JSON.stringify(queryParams).replace(/\"/g,"'")
	}
	
	 
	alert(datas);
   var dataTableObj =$(obj).fyTable(ctx+"tykf/request?tld=Tycx002DzcxService_executeQuery",datas);
	
   return dataTableObj;
  
}


