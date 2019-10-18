function initTable(obj,sqlxh,queryParams,cxlx,hjxsbz){

 	var code2Name = [];
	if(sqlxh){
		
	}
	function getTableHeadParams(sqlxh,queryParams){ 
		$.ajax({  
			type : "POST",  //提交方式  
			dataType:'json',
			async:false,
			url:ctx+"tykf/request?tld=Tycx002DzcxService_getCxjgl",//路径
			data:{
				'sqlxh':sqlxh,
				"queryParams":JSON.stringify(queryParams).replace(/\"/g,"'")
			},
			success : function(result) {//返回数据根据结果进行相应的处理
				headParams = result;				
			}  
		});		
		return headParams;
	}
	 var sdCount=0;
	 var SUMMARY_TYPE_MAPPING = {
	    		"2" : "sum",
	    		"3" : "average",
	    		"0" :"合计"
	    	};
	function initDataTableHead(obj,headParams,queryParams,hjxsbz){
		
		var minHeight = $(obj).height();
	 	
		var id = "dt_"+Math.floor(Math.random()*10000000);

		var tds ="" ;
		//var djbt=" <tr ><td colspan='19'>TEST</td></tr> \n";
		//tableHtml=tableHtml+djbt; 
		for(var i=0;i<headParams.length;i++){
			var tdParam = headParams[i].pojo;    
			if(i==0){
				summaryparams.push({
					name : tdParam.lmc,
					summaryType : SUMMARY_TYPE_MAPPING[0]
				});
			}
			if(headParams[i].childrenList.length>0){
				for(var m=0;m<headParams[i].childrenList.length;m++){
					tdParam=headParams[i].childrenList[m].pojo;
					tds=getTd(tdParam,tds,1,queryParams);					
				}
			}else{
				tr_head=getTd(tdParam,tr_head,maxrowspan,queryParams);
				
			}
			
		}

		if(maxrowspan>1){
			if(hjxsbz=='0'){//不显示合计
				tableHtml = " <table id='"+id+"' fixedColumns="+sdCount+"  style='width:100%"+";' oncreatedRow='tycx_createRow'  "+
	            "  class=\"nowrap display dataTable\"  selectType=\"1\"   cellspacing=\"0\" minHeight='"+minHeight+"'> " +
				"   <thead> <tr  >"+
				tr_head+"</tr> "+
				" <tr id='TBCE' > \n"+
				tds + 
				"</tr><tr class='namesTr' >"+tds+
				
				head_hidden+"</tr></thead></table>";
			}else{
			tableHtml = " <table id='"+id+"' fixedColumns="+sdCount+"  style='width:100%"+";' oncreatedRow='tycx_createRow' onDrawTable='tycx_drawTable' "+
            "  class=\"nowrap display dataTable\"  selectType=\"1\"   cellspacing=\"0\" minHeight='"+minHeight+"'> " +
			"   <thead> <tr  >"+
			tr_head+"</tr> "+
			" <tr id='TBCE' > \n"+
			tds + 
			"</tr><tr class='namesTr' >"+tds+
			
			head_hidden+"</tr></thead></table>";	
			}
		}else{
			if(hjxsbz=='0'){//不显示合计
				 tableHtml = " <table id='"+id+"' fixedColumns="+sdCount+"  style='width:100%"+";' oncreatedRow='tycx_createRow' "+
	               "  class=\"nowrap display dataTable\"  selectType=\"1\"   cellspacing=\"0\" minHeight='"+minHeight+"'> " +
				"   <thead> <tr class='namesTr' >"+
				tr_head+"</tr> "+
				"</thead></table>";
			}else{
		    tableHtml = " <table id='"+id+"' fixedColumns="+sdCount+"  style='width:100%"+";' oncreatedRow='tycx_createRow' onDrawTable='tycx_drawTable'"+
		               "  class=\"nowrap display dataTable\"  selectType=\"1\"   cellspacing=\"0\" minHeight='"+minHeight+"'> " +
					"   <thead> <tr class='namesTr' >"+
					tr_head+"</tr> "+
					"</thead></table>";
			}
		}
		$(obj).html(tableHtml);
 		
		return tableEl = $(obj).find("table").eq(0);
		
	}
	function getTd(tdParam,str,maxrowspan,queryParams){
		var td_dmtoMc="";
		if(tdParam.dmsql){
			
			code2Name.push({
				name : tdParam.lmc,
				table : tdParam.dmsql
			})
			//需要代码转名称
			td_dmtoMc="1";
		}
		if (tdParam.tjlx == "2" || tdParam.tjlx == "3") {
			summaryparams.push({
				name : tdParam.lmc,
				summaryType : SUMMARY_TYPE_MAPPING[tdParam.tjlx]
			});
		}
		var td = "<td name='"+tdParam.lmc+"'  dataType='"+tdParam.llx+"' className='dt_"+tdParam.llx+"' width='50' rowspan="+maxrowspan;
		var td_hidden = "<td name='"+tdParam.lmc+"'  dataType='"+tdParam.llx+"' className='dt_"+tdParam.llx+"' width='50' align='center' rowspan="+1;
		if("1"==td_dmtoMc){
			td_dmtoMc="<td name='"+tdParam.lmc+"_MC"+"'  dataType='"+tdParam.llx+"' className='dt_"+tdParam.llx+"' width='50' align='center' rowspan="+maxrowspan;
			td=td+" style='display:none'";
			td = td+" ycbj = '0'";
		}
		if(tdParam.ycbj=='0'){
			td=td+" style='display:none'";
			td = td+" ycbj = '"+tdParam.ycbj+"'";
			td_hidden=td_hidden+" style='display:none'";
			td_hidden = td_hidden+" ycbj = '"+tdParam.ycbj+"'";
			if(td_dmtoMc!=''&&td_dmtoMc!=null){
			  td_dmtoMc=td_dmtoMc+" style='display:none'";
			  td_dmtoMc = td_dmtoMc+" ycbj = '"+tdParam.ycbj+"'";
			}
		}
		if(tdParam.url){
			td=td+" do_onclick='cssnj_tycx_colClick'";
			td_hidden=td_hidden+" do_onclick='cssnj_tycx_colClick'";
			if(td_dmtoMc!=''&&td_dmtoMc!=null){
			 td_dmtoMc=td_dmtoMc+" do_onclick='cssnj_tycx_colClick'";
			}
		}
		if(tdParam.url){
			td = td+" url = '"+tdParam.url+"'"+" queryParams= '"+JSON.stringify(queryParams)+"'";
			td_hidden = td_hidden+" url = '"+tdParam.url+"'"+" queryParams= '"+JSON.stringify(queryParams)+"'";
			td_dmtoMc = td_dmtoMc+" url = '"+tdParam.url+"'"+" queryParams= '"+JSON.stringify(queryParams)+"'";
		}
		if(tdParam.dqfs){
			td = td+" dqfs = '"+tdParam.dqfs+"'";
			td_hidden = td_hidden+" dqfs = '"+tdParam.dqfs+"'";
			td_dmtoMc = td_dmtoMc+" dqfs = '"+tdParam.dqfs+"'";
		}
		if(tdParam.urlmc){
			td = td+" urlmc = '"+tdParam.urlmc+"'";
			td_hidden = td_hidden+" urlmc = '"+tdParam.urlmc+"'";
			if(td_dmtoMc!=''&&td_dmtoMc!=null){
			 td_dmtoMc = td_dmtoMc+" urlmc = '"+tdParam.urlmc+"'";
			}
		}
		if(tdParam.xzcs){
			td = td+" xzcs = '"+tdParam.xzcs+"'";
			td_hidden = td_hidden+" xzcs = '"+tdParam.xzcs+"'";
			if(td_dmtoMc!=''&&td_dmtoMc!=null){
			  td_dmtoMc = td_dmtoMc+" xzcs = '"+tdParam.xzcs+"'";
			}
		}
		
		if(tdParam.lbm){
			td = td+" lbm = '"+tdParam.lbm+"'";
			td_hidden = td_hidden+" lbm = '"+tdParam.lbm+"'";
			if(td_dmtoMc!=''&&td_dmtoMc!=null){
			  td_dmtoMc = td_dmtoMc+" lbm = '"+tdParam.lbm+"'";
			}
		}
		if(tdParam.yjfw){
			td = td+" yjfw = '"+tdParam.yjfw+"'";
			td_hidden = td_hidden+" yjfw = '"+tdParam.yjfw+"'";
			if(td_dmtoMc!=''&&td_dmtoMc!=null){
			  td_dmtoMc = td_dmtoMc+" yjfw = '"+tdParam.yjfw+"'";
			}
		}
		if(tdParam.color){
			td = td+" color = '"+tdParam.color+"'";
			td_hidden = td_hidden+" color = '"+tdParam.color+"'";
			if(td_dmtoMc!=''&&td_dmtoMc!=null){
			  td_dmtoMc = td_dmtoMc+" color = '"+tdParam.color+"'";
			}
		}
		if(tdParam.sdbj){
			td = td+" sdbj = '"+tdParam.sdbj+"'";
			if(tdParam.sdbj==1){
		  	 sdCount=sdCount+1;
			}
		}
		td = td+">"+tdParam.lms+"</td>"
		td_hidden = td_hidden+">"+"</td>"
		if(td_dmtoMc!=''&&td_dmtoMc!=null){
		  td_dmtoMc = td_dmtoMc+">"+tdParam.lms+"</td>"
		}
//		if(tdParam.dqfs){
//			td = td+" dqfs = '"+tdParam.dqfs+"'";			
//		}
		if(maxrowspan>1){
		head_hidden=head_hidden+td_hidden;
		}
		if(tdParam.dmsql){
			if(td_dmtoMc!=''&&td_dmtoMc!=null){
			  str=str+td_dmtoMc;
			}
		}
		str=str+td;
		return str;
	}
	
	var headParams = getTableHeadParams(sqlxh,queryParams);
	/**
	 * 处理多表头
	 */
	var maxrowspan=1;
	var tr_head="";
	var head_hidden="";
	function checkHead(headParams){
		var td_s ="" ;
		for(var i=0;i<headParams.length;i++){
		 var trParams=headParams[i];
		 if(trParams.childrenList.length>0){
			 maxrowspan=2;
			 var tdParam=trParams.pojo;			 
			 var td = "<td name='"+tdParam.lmc+"'  dataType='"+tdParam.llx+"' className='dt_"+tdParam.llx+"' align='center' width='50' colspan= "+trParams.childrenList.length;
			 td = td+">"+tdParam.lms+"</td>"  
			 td_s=td_s+td;
		 }
		}
		return tr_head=tr_head+td_s;	
	}
	 var summaryparams = [];
	 tr_head=checkHead(headParams);
	var tb = initDataTableHead(obj,headParams,queryParams,hjxsbz);
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
 	alert(1);
	var table = $(tb).fyTable(ctx+"tykf/request?tld=Tycx002DzcxService_executeQuery&a="+Math.random(),datas);

	return table;
	 
}
function initJcbTable(obj,sqlxh,queryParams,cxlx){
	var columns;
	var rows;
	var total;
	var summary;
	var resultColumns=new Array();
	var res = null;
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=Tycx002DzcxService_executeJcbQuery",//路径
		data:{
			'sqlxh':sqlxh,
			"queryParams":JSON.stringify(queryParams).replace(/\"/g,"'")
		},
		success : function(result) {//返回数据根据结果进行相应的处理
			 columns=result.resultColumns;
			 rows=result.rows;
			 total=result.total;
			 summary=result.summary;
			 res={
				"data":eval(rows),
				"count":total,
				"total":total,
				"iTotalDisplayRecords":total,
				"iTotalRecords":total
			 }
			 
		}  
	});
	//处理结果列
	var tb=initJcbTableHead(obj,columns);
	var table = $(tb).fyTable(rows,summary);
	table.addRow(summary);
	setTimeout(function(){dt_resize_scrollBody($(tb).attr("id"))},500);
	return table;
	//初始化表格
//	table1= $(tb).DataTable({  
//        "paging": true,  
//        "lengthChange": true,  
//        "searching": true,  
//        "ordering": true,  
//        "aaSorting" : [[0, "asc"]], //默认的排序方式，第1列，升序排列  
//        "info": true,  
//        "autoWidth": false,  
//        "destroy":true,  
//        "processing":true,  
//        "scrollX": true,   //水平新增滚动轴  
//        "serverSide":false,    //true代表后台处理分页，false代表前台处理分页  
//        "aLengthMenu":[10,15,20],  
//        "PaginationType" : "full_numbers", //详细分页组，可以支持直接跳转到某页  
//        //当处理大数据时，延迟渲染数据，有效提高Datatables处理能力  
//        "deferRender": true,  
//        "aaData":[
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'},
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'},
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'},
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'},
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'},
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'},
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'},
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'},
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'},
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'},
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'},
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'},
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'},
//                  {"ZGSWJG_DM":'A',"ZGSWSKFJ_DM":'B'}
//         ],
//        "columns":[
//                     {"data":"ZGSWJG_DM"},
//                     {"data":"ZGSWSKFJ_DM"}
//                     
//                     ],
//
//        /*是否开启主题*/  
//        "bJQueryUI": true,  
//        "oLanguage": {    // 语言设置  
//          "sLengthMenu": "每页显示 _MENU_ 条记录",  
//          "sZeroRecords": "抱歉， 没有找到",  
//          "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",  
//          "sInfoEmpty": "没有数据",  
//          "sInfoFiltered": "(从 _MAX_ 条数据中检索)",  
//          "sZeroRecords": "没有检索到数据",  
//          "sSearch": "检索:",  
//          "oPaginate": {  
//            "sFirst": "首页",  
//            "sPrevious": "前一页",  
//            "sNext": "后一页",  
//            "sLast": "尾页"  
//          }  
//        },  
//      });  
	
	
	
function initJcbTableHead(obj,headParams){
		
		var minHeight = $(obj).height();
	 	
		var id = "dt_"+Math.floor(Math.random()*10000000);

		var tds ="" ;
		//var djbt=" <tr ><td colspan='19'>TEST</td></tr> \n";
		//tableHtml=tableHtml+djbt;
	    var sdCount=0;
		for(var i=0;i<headParams.length;i++){			
			var tdParam = headParams[i];
			resultColumns.push( {
				"data" : tdParam.lmc
			});
			if(tdParam.dmsql){
				
				code2Name.push({
					name : tdParam.lmc,
					table : tdParam.dmsql
				})
			}
			
			var td = "<td name='"+tdParam.lmc+"'  dataType='"+tdParam.llx+"' className='dt_"+tdParam.llx+"' width='100' align='center' ";
			if(tdParam.ycbj=='0'){
				td=td+" style='display:none'";
				td = td+"ycbj = '"+tdParam.ycbj+"'";
			}
			if(tdParam.url){
				
				td=td+" do_onclick='cssnj_tycx_colClick'";
			}
			if(tdParam.url){
				td = td+"url = '"+tdParam.url+"'";
			}
			if(tdParam.urlmc){
				td = td+"urlmc = '"+tdParam.urlmc+"'";
			}
			if(tdParam.xzcs){
				td = td+"xzcs = '"+tdParam.xzcs+"'";
			}
			
			if(tdParam.lbm){
				td = td+"lbm = '"+tdParam.lbm+"'";
			}
			if(tdParam.yjfw){
				td = td+"yjfw = '"+tdParam.yjfw+"'";
			}
			if(tdParam.color){
				td = td+"color = '"+tdParam.color+"'";
			}
			if(tdParam.sdbj){
				td = td+"sdbj = '"+tdParam.sdbj+"'";
				if(tdParam.sdbj==1){
				sdCount=sdCount+1;
				}
			}
			td = td+">"+tdParam.lms+"</td>"
			
			tds=tds+td;
		}
		
		tableHtml = " <table id='"+id+"' fixedColumns="+sdCount+"  style='width:"+$(obj).width()+";' oncreatedRow='tycx_createRow' onDrawTable='tycx_drawTable' "+
		               "  class=\"nowrap display dataTable\"  selectType=\"1\"   cellspacing=\"0\" minHeight='"+minHeight+"'> " +
					"   <thead> \n"+
					" <tr id='TBCE' class='namesTr'> \n"+
					tds + 
					"</tr></thead></table>";
		
		$(obj).html(tableHtml);
 		
		return tableEl = $(obj).find("table").eq(0);
		
	}
}