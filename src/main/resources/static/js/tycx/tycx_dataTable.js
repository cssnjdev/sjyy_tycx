
(function($){
 
	/**
	 * 带分页的查询
	 * 
	 */ 
	window.dataTableObjects = new Object();
	
	window.dataTableObjects = new Object(); // 设置全局变量记录dataTable相关对象信息

	$.getCssnjDataTable = function(id) {
		return dataTableObjects[id];
	}

	$.getDataTable = function(id) {  
		if(typeof dataTableObjects[id]=="object"){
			return dataTableObjects[id]["dataTable"];
		}else{
			return null;
		}
	}

	
	$.fn.tycx_dataTable = function(searchParam,columns) {  
		var dataTable = new Object();
    	var searchUrl =ctx+ "/tykf/request?tld=Tycx002DzcxService_executeQuery";
    	if(searchParam.searchUrl){
    		 searchUrl = searchParam.searchUrl;
    	}
    	
		var id = searchParam.id;
		 
		if(typeof $.getCssnjDataTable(id)=="object"){ //如果已操作销毁以便重新加载，如果url不变的情况建议用dataTable.reload(datas)方法重新加载
			 
			try{ 
				dataTableObjects[id].dataTable.destroy();
				delete dataTableObjects[id];
			}catch(e){
				 $.cssnj.alert("重新加载dataTable异常！");
				 return false;
			}
			
		}
 		
		var fixedColumns =searchParam.fixedColumns;
		
		if (fixedColumns) {
			fixednums = fixedColumns;
			fixedColumns = {
				"leftColumns" : fixednums
			}
		}

		var oncreatedRow = $(this).attr("oncreatedRow"); // 创建行时的函数
		var onOnclikRow = $(this).attr("onOnclikRow");
		var onDrawTable = $(this).attr("onDrawTable");
		var minHeight = $(this).attr("minHeight");
		
		dataTableObjects[id] = new Object();
		
		dataTableObjects[id].url = searchUrl;
		dataTableObjects[id].searchData = searchParam.searchData; // 将查询条件保存到全局变量
		dataTableObjects[id].searchParam = searchParam;
		dataTableObjects[id].columns = columns; // 将展现列保存到全局变量
		dataTableObjects[id].minHeight = minHeight;
		dataTableObjects[id].rowTotal = null;
		dataTableObjects[id].oncreatedRow = oncreatedRow;
		dataTableObjects[id].onOnclikRow = onOnclikRow;
		dataTableObjects[id].onDrawTable = onDrawTable;
		
		dataTable = $(this).DataTable({ 
			
					"fnServerParams" : function(d) { // 请求后台服务时参数的回调函数，可用于修改请求服务器时的参数
						    var dat = searchParam.searchData;
							if (dataTableObjects[id].rowTotal) {
								d.total = dataTableObjects[id].rowTotal;
								d.doTotal = false;
							} else {
								d.doTotal = true;
							}
							
							d.page=(d.start/d.length)+1;//计算得出第i页
							dataTableObjects[id].page=d.page;
							d.rows=d.length;//每页length条数据
		
							for ( var item in dat) {
								d[item] = dat[item];
							}
							d["columns"] = null; // 不将列信息传入后台
		
					},
					"fnServerData":function(sSource,aoData,fnCallback,oSettings){//预览通过分析角度所选中的值查询数据
							$.ajax({
									"type" : "POST",
									"dataType":"json",
									"url":searchUrl+"&a="+Math.random() ,
									"data":aoData,
									"success":function (json){
										  dataTableObjects[id].serverData = json;
										  dataTableObjects[id].serverData.hasDarw =0;
										  if(json.summary){
											  dataTableObjects[id].summary = json.summary;
										  }
										  if(json==undefined||json.messageCode==-1){
											  fnCallback();
										  }else{
											  fnCallback(json);
										  }

									}
							}); 
							 
					},
					"fnCreatedRow" : function(rowEl, aData, iDataIndex) {
						
					},
					"fnRowCallback" : function(rowEl, aData,iDataIndex, iDataIndexFull) {
					 
						if(searchParam.fxdata){
							var wdzd = searchParam.fxdata.WDZD;
							dt_initRow(rowEl,aData,iDataIndex,id,wdzd);//维度字段添加选择框
						}else{
							dt_initRow(rowEl, aData, iDataIndex, id);
						}
						
					},
					"fnFooterCallback" : function(nFoot, aData, iStart,iEnd, aiDisplay) {		
						  try{ //调整表头对齐	
							  $.getDataTable(id).columns.adjust(); 
						  }catch(e){
	 					    	 console.log(20)
	 					  }
					},
					"fnInfoCallback" : function(oSetting, iStart, iEnd,iMax, iTotal) {
						
							if (iTotal > 0) {
								return "共" + iTotal + "条   第" + iStart + "-" + iEnd + "条数据";
							} else {
								return "未查询到数据";
							}
						
					},
					"fnInitComplete" : function(oSetting, json) {
						 	
							dataTableObjects[id].rowTotal = json.count;
						 
					},
					"fnPreDrawCallback" : function(oSetting) { // 重绘前的回调函数
						
 					},
					"fnDrawCallback" : function(oSetting) { // 重绘时的回调函数
						
						  $("#" + id + "_info").after($("#" + id + "_length"));
						  
						  //添加合计
 						  if(searchParam.hjxsbz==1&&dataTableObjects[id].serverData.hasDarw==0){ 
 							  
							    var summary= dataTableObjects[id].summary;	
 							   
							    try{
								     var tr = dataTable.addRow(summary);
								     tr.find(">td").addClass("hj_td")
		 							 $(tr).addClass("hj_tr");
									 for(var i=0;i<columns.length;i++){ // 查找合计列
									    	var col = columns[i];
									    	if(col.xsbj==true&&col.tjlx==1){
								    			 $("td[dataname='"+col.name+"']",tr).text("合计");
								    			break;
									    	} 
									  }
										
							     }catch(e){
							    	 
							     }
							     
 	 							 dataTableObjects[id].serverData.hasDarw++;
 	 							 
						  }
 						  
 						  
  						  if(searchParam.fxdata){
  						  // if(!searchParam.fxdata.messageCode==-1){
  						  	//如果没有数据，不初始化图形
 							 dataTableEnable2Chart(searchParam.sqlxh,searchParam.fxdata)
 							 dataTable2Chart(searchParam.sqlxh);
 						  }
  						  
  						
 						  
					},
				    "fixedColumns":fixedColumns,
					"columns" :columns,
					"bProcessing" : true,
					"language" : {
						"emptyTable" : "没有数据",
						"loadingRecords" : "加载中..."
					},
					"scrollY" : true,
					"scrollX" : true,
					"ordering" : true,
					"scrollCollapse" : true,
					"searching" : false,
					"paging" : searchParam.isfy,
					"info" : true,
					"serverSide" : true,
					"aLengthMenu" : [20,30,50], // 更改显示记录数选项
					"pageLength" : searchParam.pageLength==null?20:searchParam.pageLength, // 默认显示的记录数
					"bScrollCollapse" : true, // 是否开启DataTables的高度自适应，当数据条数不够分页数据条数的时候，插件高度是否随数据条数而改变
					"bPaginate" : true, // 是否显示（应用）分页器
					"bInfo" : searchParam.isfy, // 是否显示页脚信息，DataTables插件左下角显示记录数
					"sPaginationType" : "simple_numbers", // 详细分页组，可以支持直接跳转到某页
															// 包括
															// number,simple,simple_number,full,full_number,first_last_numbers
 					"oLanguage" : { // 对表格国际化
						"sLengthMenu"  : "每页显示 _MENU_条",
						"sZeroRecords" : "没有找到符合条件的数据",
						"sInfo" : "&nbsp;&nbsp;当前第 _START_ - _END_ 条　共计 _TOTAL_ 条&nbsp;&nbsp;",
						"sInfoEmpty" : "&nbsp;没有记录&nbsp;",
						"sInfoFiltered" : "(从 _MAX_ 条记录中过滤)",
						"oPaginate" : {
							"sFirst" : "首页",
							"sPrevious" : "前一页",
							"sNext" : "后一页",
							"sLast" : "尾页"
						}
					}
		    });
		
			$("#" + id + "_info").after($("#" + id + "_length"));
	
			dataTableObjects[id].dataTable = dataTable; // 建dataTable 对象保存到全局变量
		    
			var wrapper = $(this).closest(".dataTables_wrapper");
			 
			dataTable["id"] = id;
			
			dataTable.reload = function(datas) { // 重新加载
				if (datas) {
					dataTableObjects[id].searchData = datas;
				}
				dataTableObjects[id].rowTotal = null;
				return this.ajax.reload();
			}

			dataTable.getCheckedData = function() { //获取选择列

				return this.rows(".checked_true").data();

			}
			
			dataTable.getUpdatedData = function() { //获取更新过的数据

				return this.rows(".dt_tr_Updated").data();

			}
			
			dataTable.getInsertedData = function() { //获取新插入的数据

				return this.rows(".dt_tr_Inserted").data();

			}
			
			dataTable.getChangeData = function() { //获取更新过的数据 和 新插入的数据

				return this.rows(".dt_tr_Updated,.dt_tr_Inserted").data();

			}

			dataTable.setRowData = function(data, rowEl) { //设置一行数据
				// this.row(rowEl).data()=rowData;
				var rowData = this.row(rowEl).data();

				for ( var key in data) {
					if (typeof rowData[key] != "undefined") {
						rowData[key] = data[key];
					}
				}

				var id = this["id"];
				var iDataIndex = $(rowEl).index();
				dt_initRow(rowEl, rowData, iDataIndex, id)

				$(rowEl).addClass("dt_tr_Updated");
				
				return true;

			}
			
			dataTable.getData=function(){ //获取当前数据
				var length = this.rows().data().length;
				var  data =new Array();
				for(var i=0;i<length;i++){
					data.push(this.row(i).data())
				}
				return data;
			}
			
			dataTable.addRow = function(rowData){ //添加一行，参数为空则添加空行
				
				var id = this["id"];
				
				$("#"+id+">tbody>tr>td.dataTables_empty").parent().remove();
				
				var dataTableBox = $.getCssnjDataTable(id);
				
				var columns = dataTableBox.columns;
				
				var data = new Object();
	 			var tr = new $("<tr class='dt_new_tr'></tr>")

				for(var i=0;i<columns.length;i++){
					var name=columns[i]["name"];
					data[name]="";
					tr.append("<td dataname='"+name+"' ></td>")
				}
				   
				if(rowData){
					for(var key in data) {
						if (typeof rowData[key] != "undefined") {
							data[key] = rowData[key];
						}
					}
				}
				
				var arr = new Array();
				arr.push(data);
				
	 			$("#"+id+">tbody").append(tr);
					
	 			this.rows.add(arr);
	  			var iDataIndex = $(tr).index();
	 			
	  			if(iDataIndex%2==0){
	  				$(tr).addClass("odd");
	  			}else{
	  				$(tr).addClass("even");
	  			}
	  			
	  			$(tr).addClass("dt_tr_Inserted");
	  			
	 			dt_initRow(tr, data, iDataIndex, id)
	 			
	 			return tr;
 			}

			dataTable.deleteRow = function(){  //删除一行，待开发
				
			}
			
			dataTable.enableEidtRow = function(){
				
			}
			
			dataTable.disableEidtRow = function(){
				
			}
			
			return dataTable;
		  
	 }

	
})(jQuery);


function dt_initRow(rowEl, aData, iDataIndex,id,wdzd){ // dataTable 初始化行是的方法
 	
	var dataTableBox = $.getCssnjDataTable(id);

	var dataTable = $.getDataTable(id);

	var columns = dataTableBox.columns;
	
	var searchParam = dataTableObjects[id].searchParam ;
	//循环添加数据行
	for ( var i = 0; i < columns.length; i++) {
		
		var col = columns[i];
		var name = col["name"];
		var url = col["url"];
		var value = aData[name]==undefined?'':aData[name];//判断aData[name]为空，不显示，否则页面显示null
		var td = $("td:eq(" + i + ")", rowEl)
	    $(td).attr("dataname", name);

	    var className = col["className"];// className 与页面dataType 相同
	    
 	    if(col["showVal"]){
 	    	value=aData[col["showVal"]];
	    }
	    
		// 数据类型
		if (className == "dt_checkbox") { // 格式checkbox

 			if (value == "1") {
				$(td).html("<label class='dt_chk_lab'><input type='checkbox'  class='dataTable_check' name='"+ name + "' checked='checked' onchange='dt_setCheckedRow(this)' /></label>");
				$(rowEl).addClass("checked_true");
			} else {
				$(td).html("<label class='dt_chk_lab'><input type='checkbox'  class='dataTable_check' name='"+ name + "' onchange='dt_setCheckedRow(this)' /></label>");
			}

		} else if(className == "dt_date"){
			
			dt_initCol_span(td,value); 
			
		} else if(className == "dt_number"){
			
 			dt_initCol_span(td,value); 
			
		} else {
			
			dt_initCol_span(td,value); 
			
		}
		
		$(td).addClass(className);
		
		if(col["xsbj"] == false){
			$(td).addClass("td_hidden");
		}
		 
		// 添加单击事件
		if (typeof window[col["do_onclick"]] == "function") {
			var realValue="<a href='javascript:void(0);' class='do_click'>" + $(td).html(); + "</a>";

			$(td).html(realValue);
			
			var _fun = col["do_onclick"];
		 
			$(td).find(">a.do_click").off("click").on("click", function() {
				
				var colPram =  columns[$(this).parent().index()]; 
 
				window[_fun](aData, rowEl, $(this).parent(), colPram, dataTable,id);// 单击列事件 
				
			});
			
		}
		
		
		
		 
	}
	 
	//ie8以下版本不兼容，暂时注释掉 by jinln 2017-01-16
	//$(rowEl).find("[data-toggle='tooltip']").tooltip();
	
	var onOnclikRow = dataTableBox.onOnclikRow; // 绑定单击行事件

	if (typeof window[onOnclikRow] == "function") {
		$(this).addClass("cursor_pointer");
	}

	$(rowEl).off("click").on("click", function() {

		$(this).closest("tbody").find(">tr").removeClass('selected_1');
		$(this).addClass('selected_1');

		if (typeof window[onOnclikRow] == "function") {
			window[onOnclikRow](aData, rowEl, iDataIndex, dataTable,id);// 单击行事件
		}

	});

	$(rowEl).off("dblclick").on("dblclick", function() {//绑定双击事件

	});

	 
	var oncreatedRow = dataTableBox.oncreatedRow; // 绑定创建行事件
 	if (typeof window[oncreatedRow] == "function") {
		window[oncreatedRow](aData, rowEl, iDataIndex, dataTable,columns,id,wdzd);// 创建行事件
	};
	 
	
}


function dt_initCol_span(td,value){
	 
	if(value.length>60){
		var showValue=value.substring(0,58)+'...';
		var showValue =value;
		try{
 			showValue = value.replace(/\s/g,"&nbsp;");
 		}catch (e) {
			 
		}
 		$(td).html("<span title='"+value+"'>"+showValue+"</span>");//超出隐藏
 	}else{
 		var showValue =value;
 		try{
 			showValue = value.replace(/\s/g,"&nbsp;");
 		}catch (e) {
			 
		}
 		$(td).html("<span >"+showValue+"</span>");
		return true;
	}
	
	//显示隐藏部分
	$(td).addClass("overflow_td").click(function(ev){
		 $(td).closest("table").find(".overflow_div").remove();
		
		 var w = $(td).width();
		
		  var obj = $('<div class="overflow_div" style="position:absolute;width:'+w+'px;height:auto;margin-top: 3px;font-size: 13px;background: #fff;z-index:109;">            \n'+
					' <div style="position: relative;width: 100%;border: 1px solid #ccc;padding:7px;">\n'+
					'    <span style="position: absolute; right: -10px;top:-10px;cursor: pointer;color:#337AB7;" > 	   \n'+
					'       <i class="fa fa-times-circle-o fa-2x" style="" aria-hidden="true"></i>				 	   \n'+
					'    </span> \n'+
					'    <div style="width:100%;border:1px solid #ddd;word-wrap: break-word;word-break:break-all;padding:5px;white-space:normal;line-height:2;">    \n'+
					value +
					'    </div>  \n'+
					' </div>     \n'+
					'</div>      \n');
		
		   obj.appendTo(this);
		 
		   obj.find("span").click(function(event){
	  			obj.remove();
	  			event.stopPropagation();
	 	   });
		 	 
	});		
			
}


var url_qj="";


function dt_checked(obj) {
	
	var wrapper = $(obj).closest(".dataTables_wrapper");
	var name = $(obj).attr("name");
  	$(wrapper).find('tbody>tr>td input[name="' + name + '"]').each(function() {
		this.checked = obj.checked;
 		if (this.checked) {
			$(this).closest("tr").addClass("checked_true");
		} else {
			$(this).closest("tr").removeClass("checked_true");
		}
	});
    
}

function dt_setCheckedRow(obj) {
 
 	if (obj.checked) {
		$(obj).closest("tr").addClass("checked_true");
	} else {
		$(obj).closest("tr").removeClass("checked_true");
	}

 	//控制表头的全选框 是否勾选
 	var tbody = $(obj).closest("tbody");
 	var name= $(obj).attr("name");
 	
 	var l=$(tbody).find("input[name='"+name+"']:not(:checked):first").length;
 
 	if(l>0){
 		$(obj).closest(".dataTables_wrapper").find(".dataTables_scrollHeadInner .namesTr input[name='"+name+"']").each(function(){
 			this.checked = false;
 		});
 	}else{
 		$(obj).closest(".dataTables_wrapper").find(".dataTables_scrollHeadInner .namesTr input[name='"+name+"']").each(function(){
 			this.checked = true;
 		});
 	}
 	
}

function dataTableEnable2Chart(sqlxh,fxdata){ //
	if(fxdata.messageCode==-1){
		return;
	}
	var id =  "dt_"+sqlxh		;
	 
	var dlzd = fxdata.DLZD		;
	var dlArr = dlzd.split(",")	;
	
	var headTable = $("#"+id+"_wrapper").find(".dataTables_scrollHead >.dataTables_scrollHeadInner>table").eq(0)	;
	var headTr = $(headTable).find(".namesTr")	;
	
	for(var i=0;i<dlArr.length;i++){
		var zd = dlArr[i]								;
		var text = $("td[name='"+zd+"']",headTr).text()	;
		$("td[name='"+zd+"']",headTr).html("<label><input type='checkbox' name='"+sqlxh+"_DL' class='dlcheckAll' value='"+zd+"' onchange='dt_checkedOneCol(\""+sqlxh+"\",this);dataTable2Chart(\""+sqlxh+"\");' title="+text+" />"+text+"</label>") ;
	}
	
	if(chartType=="pie"){
		dt_checkedFirstCol(sqlxh,fxdata)	;
	}else{
		dt_checkedAllCol(sqlxh)		;
	}
	
	var checked = "checked = true ";
	if(dataTableObjects[id].serverData.data.length>20){
		checked = "";
	}
	
	var wdzd = fxdata.WDZD;
	
	if(wdzd){
		var wdArr = wdzd.split(",")	;
		for(var i=0;i<wdArr.length;i++){
			var zd = wdArr[i]	;
			var td = $("td[name='"+zd+"']",headTr).eq(0);
			if(!$(td).is(':hidden')&&$(td).html()!=undefined){
				var text = $(td).text()	;
				$(td).html("<label><input type='checkbox' name='"+zd+"' class='wdcheckAll ' "+checked+"  onchange='dt_checked(this);dataTable2Chart(\""+sqlxh+"\") ' title="+text+" />"+text+"</label>") ;
			    break;
			}
		}
	}
	
	 
}

function dt_checkedOneCol(sqlxh,obj){ // 只选择一列数据
	
	if(chartType=="pie"){ //全局参数 图像类型为饼图只选一列
		
	    if(obj.checked){	 
			 var id = "dt_"+sqlxh;
			 var dataTable = $.getDataTable(id);
			 if(typeof dataTable=="object"){
				 var headTable = $("#"+id+"_wrapper").find(".dataTables_scrollHead >.dataTables_scrollHeadInner>table").eq(0);
		 		 $(headTable).find("tr.namesTr input").each(function(){
		 				 $(this).attr("checked", false);
			 	 });
			 }
			 obj.checked=true;
	    }	
 	 
	}
	
}

function dt_checkedFirstCol(sqlxh,fxdata){//选择第一列
	
	 var id = "dt_"+sqlxh;
	 var dataTable = $.getDataTable(id);
	 if(typeof dataTable=="object"){
		 var headTable = $("#"+id+"_wrapper").find(".dataTables_scrollHead >.dataTables_scrollHeadInner>table").eq(0);
		 
		 var dlzd = fxdata.DLZD			;
		 var dlArr = dlzd.split(",")	;
		 
		 for(var i=0;i<dlArr.length;i++){
			 var val = dlArr[i];
			 var td = $(headTable).find("tr.namesTr input[value='"+val+"']")
			 if(i==0){
				td.attr("checked",true);
			 }else{
				td.attr("checked",false);
 			 }
		 }
		  
		 
 	 }
	
}

function dt_checkedAllCol(sqlxh){ // 选择全部列

	 var id = "dt_"+sqlxh;
	 var dataTable = $.getDataTable(id);
	 if(typeof dataTable=="object"){
		 var headTable = $("#"+id+"_wrapper").find(".dataTables_scrollHead >.dataTables_scrollHeadInner>table").eq(0);
		 $(headTable).find("tr.namesTr input").each(function(index){
			 this.checked=true;
	 	 });
	 }
} 

function dataTable2Chart(sqlxh){
	if(typeof do_loadChart == "function"){
		
		var id = "dt_"+sqlxh;
		
		var dataTableBox =  $.getCssnjDataTable(id);
		
		var dataTable = dataTableBox.dataTable;
		
	 	var searchParam = dataTableBox.searchParam;
		
	 	var name = sqlxh+"_DL"
	 	
	 	function getShowVal(name,rowData){ //获取维度显示名
	 		 var columns = dataTableObjects[id].columns;
	 		 for(var i=0;i<columns.length;i++){
	 			 var col = columns[i];
	 			 if(col.name==name){
	 				 if(col.showVal){
	 					 return rowData[col.showVal]
	 				 }else{
	 					 return rowData[name]
	 				 }
	 				 break;
	 			 }
	 		 }
	 		 
	 	}
	 	
	 	if(searchParam.fxdata){
	 		 var fxdata = searchParam.fxdata;
			if(fxdata.messageCode==-1){
				return;
			}
	 		 var wdzd = fxdata.WDZD;
	 		 var dlzd = fxdata.DLZD;
	 		 var linetype = fxdata.LINETYPE;
	 		 var dlArr = new Array();
	 		 var dlTitle = new Array();
		 	 var dlParams= new Array(); //图形样式

	 		 
	 		 var headTable = $("#"+id+"_wrapper").find(".dataTables_scrollHead >.dataTables_scrollHeadInner>table").eq(0);
	 		 
	 		 var dls = dlzd.split(",");
	 		 
	 		 for(var i=0;i<dls.length;i++){
	 			 var inp =  $(headTable).find("tr.namesTr input[name='"+name+"'][value='"+dls[i]+"']:checked").eq(0)
	 			 if(inp.length>0){
		 			dlArr.push(dls[i]) ;
		 			var param = {
	 				   'dldm':dls[i],
	 				   'dlmc':$(inp).attr("title")
	 				}
	 				if(linetype){
		 				var type = linetype.split(",")[i];
		 				if(type){
		 					param["type"] = type;
		 				}
		 			}
			 		dlParams.push(param);
	 			 }
	 		 }
 
	 		 var datarow = dataTable.getCheckedData();
	 		 
	 		 var dataArr = new Array(); // 数据
	 		 var wdArr = new Array();//维度

	 		 var wdlist = wdzd.split(","); 
	 		 
	 		 for(var i=0;i<datarow.length;i++){
	 			 
	 			 var row = datarow[i];
	 			 
	 			 var data = new Object;
	 			  
	 			 for(var j=0;j<dlArr.length;j++){
	 				 var v= row[dlArr[j]];
 	 				 if(typeof v=="string"&&v.indexOf("%")>0){
	 					 v = parseFloat(v.replace("%",""));
	 				 }
 	 				 data[dlArr[j]]=v;
	 			 }
	 			 
 	 			 dataArr.push(data);
	 			 
	 			 var sr = new Array();
	 			 for(var k=0;k<wdlist.length;k++){
	 				 var name = wdlist[k];
	 				 var vv = getShowVal(name,row)
	 				 if(vv){
	 					 sr.push(vv)
	 				 }
	 			 }
	 			 
	 			 wdArr.push(sr.join("-"));
	 			
	 		 }
	 		 
	 		 
	 		 do_loadChart(wdArr,dataArr,dlParams,wdzd,sqlxh);
	 		 
	 	}
	 	
	}	
	

}


  