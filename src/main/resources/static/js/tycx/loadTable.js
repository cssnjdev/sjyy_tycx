function initTable(obj,sqlxh,queryParams,cxdy,fxdata){
	var obj_sqlxh =$(obj).attr("sqlxh");
	
	var id = "dt_"+sqlxh;
	
	//
	if(typeof $.getCssnjDataTable(id)=="object"){ // 已经表格 存在直接查询不需要重新装载表格信息
		 
		var searchData = dataTableObjects[id].searchData;
		
		var dataTable = $.getDataTable(id);
		 
		searchData.queryParams = JSON.stringify(queryParams).replace(/\"/g,"'");
		searchData.random=Math.random(),
		dataTable.reload(searchData); 
		
		return true;
		
	}else{
		 
		if(obj_sqlxh!=sqlxh){ //删除其他sqlxh 的表格信息
 			 var other_table_id = "dt_"+obj_sqlxh;
			 if(typeof $.getCssnjDataTable(other_table_id)=="object"){
				 dataTableObjects[other_table_id].dataTable.destroy();
				 delete dataTableObjects[other_table_id];
			 }
		}
		
		$(obj).empty(); 
		
	}
	
	$(obj).attr("sqlxh",sqlxh); 
	
	var cxlx   = cxdy.cxlx    ;
	var hjxsbz = cxdy.hjxsbz  ;
	var sqlxh  = cxdy.sqlxh   ;
	
 	var code2Name = []		  ;		
  
	var sdCount=0			  ; 			
	
	var SUMMARY_TYPE_MAPPING = {
	    	"2" : "sum",
	    	"3" : "average",
	    	"0" : "合计"
	};
	
	var data = tycx_service.getCxjgl({
		'sqlxh':sqlxh,
		"queryParams":JSON.stringify(queryParams).replace(/\"/g,"'")
	}) ;
	
	if(!data){
		
		alert("未配置结果列！");
		return false;
		
	}
	var maxrowspan=1;
	
	var headParams = data.cxjgList;
	var lcjgx = data.cjgx; //多表头列层级关系
 
	var gxObj = new Object();
	
	if(lcjgx!=null && lcjgx.length>0){
		maxrowspan =2;
		for (var i=0;i<lcjgx.length;i++){
			var gx =lcjgx[i]
			gxObj[gx["SJLMC"]] = gx["LMCS"];
		}
 	}
	
	//根据列名称获取列配置参数
	function getTdParamsByLmc(lmc){
		for(var i=0;i<headParams.length;i++){
			var tdParam = headParams[i];
 			if(tdParam.lmc == lmc){
 				return tdParam
			}
		}
	}
	
	/**
	 * 处理多表头
	 */
	
	
	var summaryparams = [];

	var columns = []; // 加载的列信息
	 
	function initDataTableHead(obj,headParams,hjxsbz){
		var minHeight = $(obj).height();
 
		var tds = "" ;
		var tr_head = "";
 
		var namesTd = "";
		
		for(var i=0;i<headParams.length;i++){
			
			var tdParam = headParams[i];    
			var lmc = tdParam.lmc;
			
			if(tdParam.sdbj==1){  //锁定列
			  	 sdCount=sdCount+1;
			}
			
			if(tdParam.dmsql){ //代码转名称列
				code2Name.push({
					name : tdParam.lmc,
					table : tdParam.dmsql
				});
			}
			
			if (tdParam.tjlx == "2" || tdParam.tjlx == "3") { //统计类型
				summaryparams.push({
					name : tdParam.lmc,
					summaryType : SUMMARY_TYPE_MAPPING[tdParam.tjlx]
				});
			}
			 
			if(gxObj[lmc]){  // 处理多表态目前只考虑了两行情况
			   
				 var spanTds = gxObj[lmc] ;
				 var spanTdArr = spanTds.split(",");
				 
				 tr_head = tr_head + getTd(tdParam,1,spanTdArr.length);
 
				 for(var k=0;k<spanTdArr.length;k++){
					 
					 var lmc = spanTdArr[k];  
					 
 					 var new_param = getTdParamsByLmc(lmc);
					 tds = tds + getTd(new_param,1,1);
					 namesTd = namesTd + getTd(new_param,1,1);
					 
					 setColumns(new_param);
					 
				 }
				
				 
			}else{
				
				if(!tdParam.sjlmc){ // 不存在上级的列  
					
					tr_head = tr_head+ getTd(tdParam,2,1);
					namesTd = namesTd + getTd(tdParam,1,1);
					setColumns(tdParam);
					
				}
				
			}
			
		}
	    
		var oncreatedRow = $(obj).attr("oncreatedRow");
 		 
		if(lcjgx.length>0){
			
				tableHtml = " <table id='"+id+"' fixedColumns="+sdCount+"  style='width:100%"+";' oncreatedRow='"+oncreatedRow+"'  "+
	            "  class=\"nowrap display dataTable\"  selectType=\"1\"   cellspacing=\"0\" minHeight='"+minHeight+"'> " +
				"   <thead> " +
				"    <tr  >"+ tr_head+"</tr> "+
				"    <tr id='TBCE' > \n"+ tds +  "</tr>" +
				//"	 <tr class='namesTr hideTr' >"+namesTd+"</tr>" +
				"</thead></table>"; 
			
		}else{
				tableHtml = " <table id='"+id+"' fixedColumns="+sdCount+"  style='width:100%"+";'oncreatedRow='"+oncreatedRow+"'  class=\"nowrap display dataTable\"  selectType=\"1\"   cellspacing=\"0\" minHeight='"+minHeight+"' > " +
							"   <thead> \n" +
							"		<tr class='namesTr ' >"+namesTd+"</tr> "+
							"	</thead> \n" +
							"</table> ";
			 
		}
		
		$(obj).html(tableHtml);
 		
		return tableEl = $(obj).find("table").eq(0);
		
	}
	
	function setColumns(tdParam){
		//加载dataTable 查询列信息
		var classname = ("dt_"+tdParam.llx).toLowerCase()
		
		var arr=["dt_number","dt_date","dt_string","dt_datatime","dt_checkbox"];
		 
		if(contains(arr,classname)==false){
			classname= "dt_string";
		}
 
		var col =  {
				"data" :tdParam.lmc,
				"name" :tdParam.lmc,
				"xzcs" :tdParam.xzcs,
				"url"  :tdParam.url,
				"tzfs" :tdParam.tzfs,
				"dqfs" :tdParam.tdParam,
				"className":classname,
				"dmsql":tdParam.dmsql,
				"urlmc":tdParam.urlmc,
			    "lbm":tdParam.lbm,
			    "yjfw":tdParam.yjfw,
			    "color":tdParam.color,
			    "sdbj":tdParam.sdbj,
			    "lms":tdParam.lms,
			    "tjlx":tdParam.tjlx,
			    "showVal":tdParam.jskj,
			    "xsbj":tdParam.ycbj==1?true:false,
			    "orderable":false//排序
		};
		//如果有下钻添加事件
	    if(tdParam.url){
	    	col.do_onclick="cssnj_tycx_colClick";
		}
	    
		columns.push(col); //加载dataTable 查询列信息结束

	}
	
	function getTd(tdParam,rowspan,colspan){ 

		var td = "<td name='"+tdParam.lmc+"'  dataType='"+tdParam.llx+"' className='dt_"
			   +tdParam.llx+"' width='100' rowspan='"+rowspan + "' colspan ='"+colspan+"'";
 		
		if(tdParam.ycbj=='0'){
			td=td+" style='display:none'";
			td = td+" ycbj = '"+tdParam.ycbj+"'";
		}
		          
		td = td+">"+tdParam.lms+"</td>";
		   
 		return td;
		
	}
	
	var tb = initDataTableHead(obj,headParams,hjxsbz);
	
  	var datas = {
		'sqlxh':sqlxh,
		'random':Math.random(),
		'queryParams':JSON.stringify(queryParams).replace(/\"/g,"'"),
		'code2Name':JSON.stringify(code2Name).replace(/\"/g,"'"),
		'queryType':cxlx,
		'summaryParams':JSON.stringify(summaryparams).replace(/\"/g,"'")	
 	};
	  
 	if(maxrowspan>1){
 		$(tb).find(".namesTr>td").text("");
 	}
 	 
 	
 	var searchParam = {
 		"searchData":datas,
 		"isfy":cxdy.fybj=='Y'?true:false,
 		"hjxsbz":hjxsbz,
 		"sqlxh":sqlxh,
 		"fixedColumns":sdCount,
 		"pageLength":cxdy.myjls,
 		"id":id ,
 		"summaryparams":summaryparams
 	}
 	
 	if(fxdata){//如果参与分析获取分析信息
 		searchParam.fxdata = fxdata;
	} 
     
 	var table =$(tb).tycx_dataTable(searchParam,columns);
 	
	return table;
	 
}

 
 
