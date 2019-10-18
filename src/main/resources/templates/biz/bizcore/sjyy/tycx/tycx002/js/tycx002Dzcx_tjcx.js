function initTjTable(obj,sqlxh,queryParams,params,type){
 	
	if(sqlxh){
		
	}
	function initDataTableHead(obj,headParams){
		
		var minHeight = $(obj).height();
		var id = "dt_"+Math.floor(Math.random()*10000000);

		var tableHtml = " <table id='"+id+"' style='width:"+$(obj).width()+";'  class=\"nowrap display dataTable\"  selectType=\"1\"   cellspacing=\"0\" minHeight='"+minHeight+"'> " +
						"   <thead> \n" ;
		tableHtml=tableHtml+"<tr class='namesTr'> \n";
		for(var i=0;i<headParams.length;i++){
			var tdParam = headParams[i];
			var llx=null;
			if(tdParam.type=='group'){
				llx='string';
			}else{
				llx='number';
			}
			var td = "<td name='"+tdParam.name+"'  dataType='"+llx+"' className='dt_"+llx+"' width='100' ";
		
			td = td+">"+tdParam.text+"</td>"
			tableHtml=tableHtml+td;
		}
		
		tableHtml = tableHtml + "</tr></thead></table>";
		$(obj).html(tableHtml);
		
		return tableEl = $(obj).find("table").eq(0);
		
	}
	var tb = initDataTableHead(obj,params);
 	var datas = {
		'sqlxh':sqlxh,
		'random':Math.random(),
		'queryParams':JSON.stringify(queryParams).replace(/\"/g,"'"),
		'wrapParams':JSON.stringify(params).replace(/\"/g,"'"),
		'queryType':type
 	};
	var table = $(obj).fyTable(ctx+"tykf/request?tld=Tycx002DzcxService_executeQuery&a="+Math.random(),datas);
	return table;
	 
}