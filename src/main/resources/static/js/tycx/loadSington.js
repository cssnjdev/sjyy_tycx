function loadSington(obj,pageHtml,sqlxh,queryParams,cxdy,coldata){
 
	var datas = {
			'sqlxh':sqlxh,
			'random':Math.random(),
			'queryParams':JSON.stringify(queryParams).replace(/\"/g,"'"),
 			'queryType':cxdy.cxlx
 	};
	
	var url = "/tykf/request?tld=Tycx002DzcxService_executeQuery";
	
	var qouryData = null;
	 
	$.ajax({
		url : url,
		async : false,
		data : datas,
		dataType : 'json',
		success : function(data, textStatus, jqXHR) {
			qouryData = data.data;
		},
		error : function(xhr, textStatus) {

		}
	});
	
	if(qouryData){
 		 rowData = qouryData[0];
		 for(var key in rowData){
			 var dt = rowData[key];
			 var html = initSingleCell(key,dt,coldata);
			 pageHtml = pageHtml.replace("@"+key+"@",html);
		 }
		 $(obj).html(pageHtml);
		 
	}else{
		return false;
	}
	
	
}


function initSingleCell(key,dt,coldata){
	
	var html ="<span>"+dt+"</span>";
	 
	for(var i=0;i<coldata.length;i++){
 		var col = coldata[i];
		if(col.LMC ==key){
			if(col.URL){
				html = "<span><a href='javaScript:' sqlxh='"+col.URL+"' onclick='zqcx(this);'>"+dt+"</a><span>" ;
			}
		}
	}
	
	return html;
	
}
