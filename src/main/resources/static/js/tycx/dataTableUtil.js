var hjh=0;
(function($) {

	$.fn.dataTable.ext.errMode = 'none';

	/**
	 * 带分页的查询
	 * 
	 */

	window.dataTableObjects = new Object(); // 设置全局变量记录dataTable相关对象信息

	$.getCssnjDataTable = function(id) {
		return dataTableObjects[id];
	}

	$.getDataTable = function(id) {  
		return dataTableObjects[id]["dataTable"];
	}

	$.fn.fyTable = function(url, datas) { // 带分页条的dataTable
		var isType = isArray(url);//判断url类型，如果是JSON==1，是url，isType==0
		var table = this;
		if(isType=="0"){
			if (url == null) {
				alert("查询地址不能为空");
				return false;
			}
		}
		
		var id = $(this).attr("id");
		
		if(typeof $.getCssnjDataTable(id)=="object"){ //如果已操作销毁以便重新加载，如果url不变的情况建议用dataTable.reload(datas)方法重新加载
			
			try{ 
				if(dataTableObjects[id].url==url){//url相同执行reload方法
 					 dataTableObjects[id].dataTable.reload(datas);
					 return true;
				}else{
					dataTableObjects[id].dataTable.destroy();
					delete dataTableObjects[id];
				}
			}catch(e){
				 $.cssnj.alert("重新加载dataTable异常！");
				 return false;
			}
			
		}
 		
		var ordering = $(this).attr("ordering");
		if (ordering == "Y") {
			ordering = true;
		} else {
			ordering = false;
		}

		var columns = new Array(); // 展现列基本信息;

		var namesTr = $(this).find("thead tr").eq(0);

		if ($(this).find("thead tr.namesTr").length > 0) {
			namesTr = $(this).find("thead tr.namesTr").eq(0);
		}

		var urlArr = new Array(); // 下钻参数数值

		var lbmAbj = new Object();

		$(namesTr).find("td,th").each(function(index, el) {

					var name = $(el).attr("name");
					var data = {
						"data" : name,
						"name" : name
					};

					if ($(el).attr("url")) {
						var xzcs = $(el).attr("xzcs");
						if (!xzcs) {
							xzcs = null;
						}
						var zqfs = 0; // 下钻方式 0 打开新窗口,1模态窗口，2 当前div iframe展现
						if ($(el).attr("zqfs")) {
							zqfs = $(el).attr("zqfs");
						}				
						data.url = $(el).attr("url");
						data.xzcs=xzcs;
						data.urlmc=$(el).attr("urlmc");
						data.zqfs=zqfs;
						data.queryParams=$(el).attr("queryParams");
					}
					if($(el).attr("yjfw")){
						data.yjfw=$(el).attr("yjfw");
						data.color=$(el).attr("color");
					}
					if ($(el).attr("lbm")) {
						lbmAbj[name] = $(el).attr("lbm");
					}
					if ($(el).attr("ycbj")) {
						data.ycbj = $(el).attr("ycbj");
					}
					if ($(el).attr("dqfs")) {
						data.dqfs = $(el).attr("dqfs");
					}
					
					if ($(el).attr("dataType")) { // 数据类型
						data.className = $(el).attr("dataType");
					} 
					
					var arr=["dt_number","dt_date","dt_string","dt_datatime","dt_checkbox"];
					
					if(contains(arr,data.className)==false){
						data.className = "dt_string";
					}

					if (data.className == "dt_checkbox") {

						$(el).html("<label class='dt_chk_lab'><input type='checkbox' onchange='dt_checked(this)  ' class='dataTable_check' name='"
												+ name + "' /><label>");

					}

					var visible = $(el).attr("visible");

					if (visible == "F") {
						data.visible = false;
					}

					var orderable = $(el).attr("orderable");

					if (orderable == "Y" && ordering == true) {

						data.orderable = true;
						var sortDir = $(el).attr("sortDir");
						if (sortDir != 'desc') {
							data.orderSequence = [ "asc", "desc" ];
						} else {
							data.orderSequence = [ "desc", "asc" ];
						}

					} else {
						data.orderable = false;
					}

					if ($(el).attr("do_onclick")) {
						data.do_onclick = $(el).attr("do_onclick");
					}
					// if ($(el).attr("do_onclick_yl")) {
					// 	data.do_onclick_yl = $(el).attr("do_onclick_yl");
					// }
					if ($(el).attr("selectData")) {
						data.selectData = $(el).attr("selectData");
					}

					if ($(el).attr("editable") == "Y") {
						data.editable = true;
					} else {
						data.editable = false;
					}

					columns.push(data);

		});
		var isfy = $(this).attr("isfy");// 是否分页

		if (isfy == "0" || isfy == "false" || isfy == "F" || isfy == "FALSE"
				|| isfy == "f") {
			isfy = false;
		} else {
			isfy = true;
		}

		var minHeight = $(this).attr("minHeight");

		var selectType = $(this).attr("selectType");// 行选中类型（单选1，多选2）
		var fixedColumns = $(this).attr("fixedColumns");
		if (fixedColumns) {
			fixednums = fixedColumns;
			fixedColumns = {
				"leftColumns" : fixednums
			}
		}

		var dataTable = new Object();
 
		var aLengthMenu = [[ 10, 20, 30, 50 ], [ 10, 20, 30, 50 ]];

		if ($(this).attr("aLengthMenu")) {
			aLengthMenu = JSON.parse($(this).attr("aLengthMenu"));
		}

		var pageLength = 20;

		if ($(this).attr("pageLength")) {
			pageLength = JSON.parse($(this).attr("pageLength"));
		}

		var oncreatedRow = $(this).attr("oncreatedRow"); // 创建行时的函数
		var onOnclikRow = $(this).attr("onOnclikRow");
		var onDrawTable = $(this).attr("onDrawTable");
		
		dataTableObjects[id] = new Object();
		
		dataTableObjects[id].url = url;
		dataTableObjects[id].searchData = datas; // 将查询条件保存到全局变量
		dataTableObjects[id].columns = columns; // 将展现列保存到全局变量
		dataTableObjects[id].minHeight = minHeight;
		dataTableObjects[id].rowTotal = null;
		dataTableObjects[id].oncreatedRow = oncreatedRow;
		dataTableObjects[id].onOnclikRow = onOnclikRow;
		dataTableObjects[id].onDrawTable = onDrawTable;
 
		dataTable = $(this).DataTable(
						    {
//								"ajax" : {
//								"type" : "POST",
//								"url"  : isType=="0"?url:null
//							},
							"fnServerParams" : function(d) { // 请求后台服务时参数的回调函数，可用于修改请求服务器时的参数

								var dat = dataTableObjects[id].searchData;
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
 
								if(dataTableObjects[id].serverData){
									if(dataTableObjects[id].serverData.summary){
									  d.summary=JSON.stringify(dataTableObjects[id].serverData.summary).replace(/\"/g,"'");	
									}
								};

							},
							"fnServerData":function(sSource,aoData,fnCallback,oSettings){
								$.ajax({
									"type" : "POST",
									"dataType":"json",
									"url" : isType=="0"?url:null,
									"data":aoData,
									"success":function (json){
 										  dataTableObjects[id].serverData = json;
	  									  fnCallback(json);
									}
								}); 
								
							},
							"fnCreatedRow" : function(rowEl, aData, iDataIndex) {
								 

							},
							"fnRowCallback" : function(rowEl, aData,
									iDataIndex, iDataIndexFull) {

								dt_initRow(rowEl, aData, iDataIndex, id);//

							},
							"fnFooterCallback" : function(nFoot, aData, iStart,
									iEnd, aiDisplay) {				
							},
							"fnInfoCallback" : function(oSetting, iStart, iEnd,
									iMax, iTotal) {
								if (iTotal > 0) {
									return "共" + iTotal + "条   第" + iStart
											+ "-" + iEnd + "条数据";
								} else {
									return "未查询到数据";
								}
							},
							"fnInitComplete" : function(oSetting, json) {
								if(isType=='0'){	
									dataTableObjects[id].rowTotal = json.count;
								}

							},
							"fnPreDrawCallback" : function(oSetting) { // 重绘前的回调函数

							},
							"fnDrawCallback" : function(oSetting) { // 重绘时的回调函数
								
								if(isfy){
									$("#" + id + "_info").after($("#" + id + "_length"));
								}
								
								 
								if(typeof window[dataTableObjects[id].onDrawTable]=="function"){
									var onDrawTable = dataTableObjects[id].onDrawTable;
									window[onDrawTable](dataTable,id,oSetting,isType,datas,dataTableObjects[id].page);
								}
								
								
								dt_resize_scrollBody(id);
								

							},
							 "fixedColumns":fixedColumns,
							"columns" : columns,
							"bProcessing" : true,
							"language" : {
								"emptyTable" : "没有数据",
								"loadingRecords" : "加载中..."
							},
							"aaData":isType=="1"?eval(url):'',
							"scrollY" : true,
							"scrollX" : true,
							"ordering" : ordering,
							"scrollCollapse" : true,
							"searching" : false,
							"paging" : isfy,
							"info" : true,
							"serverSide" : isType=="1"?false:true,
							"aLengthMenu" : aLengthMenu, // 更改显示记录数选项
							"pageLength" : pageLength, // 默认显示的记录数
							"bScrollCollapse" : true, // 是否开启DataTables的高度自适应，当数据条数不够分页数据条数的时候，插件高度是否随数据条数而改变
							"bPaginate" : true, // 是否显示（应用）分页器
							"bInfo" : isfy, // 是否显示页脚信息，DataTables插件左下角显示记录数
							"sPaginationType" : "simple_numbers", // 详细分页组，可以支持直接跳转到某页
																	// 包括
																	// number,simple,simple_number,full,full_number,first_last_numbers
						//	"renderer" : 'bootstrap',
							"oLanguage" : { // 对表格国际化
								"sLengthMenu" : "每页显示 _MENU_条",
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
			
			for(var i=0;i<columns.length;i++){
				var name=columns[i]["name"];
				data[name]="";
			}
			   
			if(rowData){
				for ( var key in data) {
					if (typeof rowData[key] != "undefined") {
						data[key] = rowData[key];
					}
				}
			}
			
			var arr = new Array();
			arr.push(data);
			
 			var tr = new $("<tr class='dt_new_tr'></tr>")
			
 			for(var key in data){
 				tr.append("<td classname='"+key+"'>"+data[key]+"</td>")
 			}
			
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
 			
 			//this.setRowData(data, tr);
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

//调整布局
function dt_resize_scrollBody(id){
	
	var wrapper = $("#" + id).closest(".dataTables_wrapper");
	
	var dataTable = dataTableObjects[id].dataTable;
 
	var minHeight = dataTableObjects[id].minHeight;

	$(wrapper).find(".dataTables_scrollBody").css("height", "auto");
	$(wrapper).parent().scrollTop(10);

	if ($(wrapper).parent().scrollTop() > 0) {// 父节点有滚动条

		var h1 = $(wrapper).parent().height()- $(wrapper).find(".dataTables_scrollHead").height() - 36;
		
		$(wrapper).find(".dataTables_scrollBody").height(h1);
		
		h2 = $(wrapper).find(".dataTables_scrollBody table.dataTable").height();
		
		if (h2 < minHeight) {
			$(wrapper).find(".dataTables_scrollBody").css("height",minHeight);
		}

	}

	$(wrapper).find(".dataTables_scrollHeadInner>.dataTable").width(
			$(wrapper).find(".dataTables_scrollBody>.dataTable").width());
	
	
}

function dt_resize_dx() {
 
	for ( var id in dataTableObjects) { 
		dt_resize_scrollBody(id);
		//dataTableObjects[id].dataTable.draw(true)
	}
	
/*
	setTimeout(function() {

		for ( var id in dataTableObjects) {

			var wrapper = $("#" + id).closest(".dataTables_wrapper");
			
			
			$(wrapper).find(".dataTables_scrollHeadInner>.dataTable").width(
					$(wrapper).find(".dataTables_scrollBody>.dataTable")
							.width());
			
			$(wrapper).find(".dataTables_scrollHeadInner").css("padding-right",
			"0");
			
		}

	}, 200);
*/

}

function dt_checked(obj) {

	var wrapper = $(obj).closest(".dataTables_wrapper");
	var name = $(obj).attr("name");
	$(wrapper).find('tbody>tr>td[dataName="' + name + '"] input').each(
			function() {
				this.checked = obj.checked;
				dt_setCheckedRow(this);
			});

}

function dt_setCheckedRow(obj) {

	if (obj.checked) {
		$(obj).closest("tr").addClass("checked_true");
	} else {
		$(obj).closest("tr").removeClass("checked_true");
	}

}

function dt_initRow(rowEl, aData, iDataIndex, id) { // dataTable 初始化行是的方法

	var dataTableBox = $.getCssnjDataTable(id);

	var dataTable = $.getDataTable(id);

	var columns = dataTableBox.columns;
	
	for ( var i = 0; i < columns.length; i++) {
		var col = columns[i];
		var name = col["name"];
		var url = col["url"];
		var value = aData[name]==undefined?'':aData[name];//判断aData[name]为空，不显示，否则页面显示null
		var td = $("td:eq(" + i + ")", rowEl)
	    $(td).attr("dataname", name);

		var className = col["className"];// className 与页面dataType 相同

		var editable = col["editable"];	
			
 		
		// if(url!=''&&url!=null){
		// $("td:eq("+i+")",rowEl).attr("dataname","<a
		// href='javascript:void(0);' onclick=\"cssnj_tycx_colClick('"+url+"')\"
		// >"+name+"</a>");
		// }else{
		// }
		

		// 数据类型
		if (className == "dt_checkbox") { // 格式checkbox

 			if (value == "1") {
				$(td).html("<label class='dt_chk_lab'><input type='checkbox'  class='dataTable_check' name='"+ name + "' checked='checked' onchange='dt_setCheckedRow(this)' /></label>");
				$(rowEl).addClass("checked_true");
			} else {
				$(td).html("<label class='dt_chk_lab'><input type='checkbox'  class='dataTable_check' name='"+ name + "' onchange='dt_setCheckedRow(this)' /></label>");
			}

		} else if(className == "dt_date"){
			
			
		} else if(className = "dt_string"){
			
			
		}
		
		
		if(className=="dt_string"||className == "dt_number"){ // 字符串或者数据
			
			if(editable==true){
				
				if(col["selectData"]){
					dt_toselect_td(col["selectData"],editable,name,value,td);
					dt_initCol_select(td, name, col, aData, rowEl, iDataIndex, dataTable);
				}else{
					dt_initCol_input(td, name, col, aData, rowEl, iDataIndex, dataTable);
				}
				
			}else{
				
				if(col["selectData"]){
					dt_toselect_td(col["selectData"],editable,name,value,td);
				}else{
					dt_initCol_span(td,value); 
				}
				
			}
			
		}
		
		 if (className == "dt_date") {
			 
			 if(editable==true){
				 
				 
				 
			 }else{
				 dt_initCol_span(td,value); 
			 }
			
			 
		 }
		
	 
		// 添加单击事件
		if (typeof window[col["do_onclick"]] == "function") {
			var _fun = col["do_onclick"];
			$(td).off("click").on("click", function() {
				var colPram =  columns[$(this).index()];
				window[_fun](aData, rowEl, this, colPram, dataTable);// 单击列事件
			}).addClass("cursor_pointer");

			if(!editable){
				var realValue="<a href='javascript:void(0);'>" + $(td).html(); + "</a>";
				$(td).html(realValue);
			}
			
		}
		// if (typeof window[col["do_onclick_yl"]] == "function") {
		// 	var _fun = col["do_onclick_yl"];
		// 	$(td).off("click").on("click", function() {
		// 		var colPram =  columns[$(this).index()];
		// 		window[_fun](aData, rowEl, this, colPram, dataTable);// 单击列事件
		// 	}).addClass("cursor_pointer");
		//
		// 	if(!editable){
		// 		var realValue="<a href='javascript:void(0);'>" + $(td).html(); + "</a>";
		// 		$(td).html(realValue);
		// 	}
		//
		// }
		 
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
			window[onOnclikRow](aData, rowEl, iDataIndex, dataTable);// 单击行事件
		}

	});

	$(rowEl).off("dblclick").on("dblclick", function() { // 绑定双击事件

	});

	var oncreatedRow = dataTableBox.oncreatedRow; // 绑定创建行事件
 	if (typeof window[oncreatedRow] == "function") {
		window[oncreatedRow](aData, rowEl, iDataIndex, dataTable,columns);// 创建行事件
	};
	var onaddSummary = dataTableBox.onaddSummary; // 绑定创建行事件
 	if (typeof window[onaddSummary] == "function") {
		window[onaddSummary](aData, rowEl, iDataIndex, dataTable,columns,id);// 创建行事件
	};
}

function dt_initCol_span(td,value){
	if(value.length>30){
 		$(td).html("<span title='"+value+"'>"+value.substring(0,28)+'...'+"</span>");//超出隐藏
 	}else{
 		$(td).html("<span >"+value+"</span>");
		return true;
	}
	
	//显示隐藏部分
	$(td).addClass("overflow_td").click(function(ev){
		 $(td).closest("table").find(".overflow_div").remove();
		
		 var w = $(td).width();
		
		  var obj = $('<div class="overflow_div" style="position:relative;width:'+w+'px;height:auto;margin-top: 3px;font-size: 13px;background: #fff;z-index:109;">            \n'+
					' <div style="position: relative;width: 100%;border: 1px solid #ccc;padding:7px;">\n'+
					'    <span style="position: absolute; right: -10px;top:-10px;cursor: pointer;color:#337AB7;" > 	   \n'+
					'       <i class="fa fa-times-circle-o fa-2x" style="" aria-hidden="true"></i>				 	   \n'+
					'    </span> \n'+
					'    <div style="width:100%;border:1px solid #ddd;word-wrap: break-word;word-break:break-all;padding:5px;white-space:normal;">    \n'+
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

function dt_toselect_td(selectaname,editable,name,value,td){

 
	if (typeof cssnjGetData == "function") {
			
			var selectData = cssnjGetData(selectaname);
		 
			if (selectData != null && selectData != "" 	&& selectData != undefined) {
				
				isSelect = true;
				
				if(editable==true){//可编辑
					
					var options = "";
					
					for ( var k = 0; k < selectData.length; k++) {
						options+="<option value='"+selectData[k]["code"]+"' >"+selectData[k]["caption"]+"</option>";
					}
		 			
					$(td).html("<select name='"+name+"' class='dt_select'  >"+options+" </select>");
					
					$("select",td).val(value);
					
				}else{
					
	 				 for ( var k = 0; k < selectData.length; k++) {
							if(selectData[k]["code"]==value){
								var caption = selectData[k]["caption"];
								dt_initCol_span(td,caption);
	 							break;
							}
						
 					  }
				 
				 }
				
			} 
			
	}else{
		dt_initCol_span(td,value);
	}
	
	 
	
}


function dt_initCol_input(el, name, param, rowData, rowEl, rowindex, dataTable) {
  
 	$(el).html("<input value='" + rowData[name] + "' dataname='" + name + "' class='dt_input' datatype='" + param["className"] + "' />");

	$("input.dt_input",el).off("blur").on("blur", function() {

		var name = $(this).parent().attr("dataname");
 
		var old_val = rowData[name];
		
		var new_val = $(this).val();

		if(old_val!=new_val){
			
			rowData[name] = new_val;
	
			var b = dataTable.setRowData(rowData, rowEl);
			
		}
		
	});

}


function dt_initCol_select(el, name, param, rowData, rowEl, rowindex, dataTable,selectData){
	
	$("select.dt_select",el).on("change",function(){
  		 
 		var name = $(this).parent().attr("dataname");

		var new_val = $(this).val();

		rowData[name] = new_val;

		var b = dataTable.setRowData(rowData, rowEl);
 		  
	}); 
	  
}

var url_qj="";
function cssnj_tycx_colClick(aData, rowEl, col, colPram, dataTable) {
	var url=colPram.url;
	var urlmc=colPram.urlmc;
	var xzcs=colPram.xzcs;
	var queryParams=colPram.queryParams;
	var cs=[];
	if(xzcs!=""&&xzcs!=null){
	var xzcsArr=xzcs.split(";");	
	for(var m=0;m<xzcsArr.length;m++){
		var csm=xzcsArr[m];
		if(colPram.lbm){
			csm=colPram.lbm;
		}
		var real_value;
		if(aData[xzcsArr[m]]==undefined){//交叉表下钻时，取表头数据，直接取数据取不到，如果有其他情况，再修改  20171227 by jinln
			real_value=colPram.data;
		}else{
			real_value=aData[xzcsArr[m]];
		}
		//cs=cs+"&"+csm+"="+real_value;
		
		cs[m]={
			name : csm,
			value : real_value
		};
	}	
	}
	if(queryParams!=null&&queryParams!=''){		
		var newcs=[];
		var m=0;
		var new_queryParams=JSON.parse(queryParams);
		for(var i=0;i<new_queryParams.length;i++){
			var name1=new_queryParams[i].name;
			if(cs.length>0){
			for(var j=0;j<cs.length;j++){
				var new_name=cs[j].name;
				if(name1==new_name){
					continue;
				}else{
					newcs[m]={
							name:name1,
							value:new_queryParams[i].value
					}
					m++;
				}
			}
			}else{
				newcs[i]={
						name:name1,
						value:new_queryParams[i].value
				}
			}
			
		}
		if(newcs!=null){
			var csLen=cs.length;
			for(var n=0;n<newcs.length;n++){
				cs[csLen+n]={
						name:newcs[n].name,
						value:newcs[n].value
				}
			}
		}
		
	}
	cs="&drillParams="+encodeURI(encodeURI(JSON.stringify(cs)))+"&execute=true";
	var urlArr=url.split(";");
	
	var host = window.location.host;
	//判断listgroup如果为空，listgroup里面增加内容；
	if(urlArr.length>1){
		var urlmcArr;
		if(urlmc!=null&&urlmc!=''){
			 urlmcArr=urlmc.split(";");
			}
	if($("#listgroup").find("li").length==0||url_qj==""){
		if(url_qj!=url){
			$("#listgroup").empty();
		}
	  for(var i=0;i<urlArr.length;i++){
		$("#listgroup").append(" <li class='list-group-item' ><a id='chaxun"+i+"' href='' target='_blank'>"+urlmcArr[i]+"</></li>");
		var new_url="http://" + host + urlArr[i]+cs;
		$("#chaxun"+i).attr("href",new_url);
	  }	
	  url_qj=url;
	}else if($("#listgroup").find("li").length>0){//如果listgroup非空，判断全局变量的url是否与url相同，如果相同，则只改变url内容，否则listgroup的内容要重新初始化
		if(url_qj!=url){
			//先清空listgroup，重新加载要显示的链接内容
			$("#listgroup").empty();
			for(var i=0;i<urlArr.length;i++){
				$("#listgroup").append(" <li class='list-group-item' ><a id='chaxun"+i+"' href='' target='_blank'>"+urlmcArr[i]+"</></li>");
				var new_url="http://" + host + urlArr[i]+cs;
				$("#chaxun"+i).attr("href",new_url);
			  }
			url_qj=url;
		}else{
			//值改变href的内容
			for(var i=0;i<urlArr.length;i++){
				var new_url="http://" + host + urlArr[i]+cs;
				$("#chaxun"+i).attr("href",new_url);
			}
		}
		
	}
	  $("#listgroup").css("left",event.clientX+10);
	 	$("#listgroup").css("top",event.clientY); 	
	    $("#listgroup").show();
	   // $("#chaxun").attr("href",url);
	    var times=setTimeout(function(){
	    	$('#listgroup').hide();
	    },1500);
	}else{
		var new_url="http://" + host + urlArr[0]+cs;
		window.open(new_url);
	}
  
}

function tycx_createRow(aData, rowEl, col, colPram, dataTable) {
	for(var m=0;m<dataTable.length;m++){
		var column=dataTable[m];
	if(column.yjfw!=''&&column.yjfw!=null &&column.yjfw!='undefined'){
		var v=$("td:eq(" + m + ")", rowEl)[0].outerText;
		if(eval('('+v+column.yjfw+')')){
		$("td:eq(" + m + ")", rowEl).css('color',column.color!=null?column.color:'red');
		}
	}
	if(column.ycbj=='0'){
		$("td:eq(" + m + ")", rowEl).css('display','none');
	}
	if(column.dqfs=='2'){
	  $("td:eq(" + m + ")", rowEl).css('text-align','right');
	}else if(column.dqfs=='1'){
	  $("td:eq(" + m + ")", rowEl).css('text-align','center');
	}else{
	  $("td:eq(" + m + ")", rowEl).css('text-align','left');
	}
}
}
 

function tycx_drawTable(dataTable,id,oSetting,isType,datas,page){
	if(!dataTable){
		return false;
	}	
	if(isType=='0'){
		    //如果有合计行,增加合计行		
		hjh=hjh+1;
		if((page==1&&hjh%2==0)||page!=1){
		    if(dataTableObjects[id].serverData){
			    var summary=dataTableObjects[id].serverData.summary;	
			    if(summary){
			    	dataTable.addRow(summary);
			    }
		    }
		} 
	}else{
		//交叉表添加合计行
          if(typeof dataTable.addRow=='function'){
			if(datas){
		       dataTable.addRow(datas);
			}
          }
	}
		
 
}

function isArray(obj){
	
	var isType ="0"
	
	try{
		var a=eval(obj);
		isType="1";
	}catch(e){
		isType="0";
	}
	
	return isType;
}
function contains(arr,obj){
	var i=arr.length;
	while(i--){
		if(arr[i]==obj){
			return true;
		}
	}
	return false;
}