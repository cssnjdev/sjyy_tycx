var bm = "";
var bzwm="";
var whjc="";
var hqcxbm = "";
var hqcxbzwm="";

var cxdy =null;

var nsrsbh=parent.nsrsbh;
var djxh=parent.djxh;
var gwssswjg=parent.gwssswjg;
var gwxh=parent.gwxh;
var zndm=parent.zndm;
 
function getQueryString(name){
	
	
	var reg = new RegExp("(^|&)"+name+"=([^&*])(&|$)","ig");
	 
    var url = window.location.href;
   
    if(url.indexOf("?")>-1){
    	
    	url = url.substring(url.indexOf("?")+1, url.length);
    	var arr = url.split("&");
    	for(var i =0;i<arr.length;i++){
    		if(name==arr[i].split("=")[0]){ 
    			return arr[i].split("=")[1] 
    		} 								 
    	}			
    				
    } 				
    				
    return null;	
      
}


$(window).resize(function() {
    $("#dataShow").height($(window).height()-$("#bodyTop").height()-25)
});

 

$(function(){


	window.sqlxh = '00010011282';//getQueryString("sqlxh");
	
	//获取查询定义
	$.ajax({
	      url:ctx+"tykf/request?tld=Tycx002DzcxService_selectCxdyAndCxtj",
	      async:false,
	      data:{
	      	"djxh":djxh,
	      	"sqlxh":sqlxh
	      },
	      dataType:'json',
	      success:function(data,textStatus,jqXHR){ 
	    	  debugger;
 	      	  cxdy = data; 
 	      	  cxdy.djxh = djxh;
 	      	  
 	      	  
	      },
		  error:function(xhr,textStatus){
		    
		  }
	 });
 
	//$("#cxTitle").html(cxdy.sqlmc); 
 		
	//初始化查询条件
	 var ointCxtj = new initCxtj(cxdy.cxtj);
	 ointCxtj.init();
	 
	 if($("#cxtjTableBody").find("td").length>0){
		 $("#cxtj_panel").show();
	 }else{
		 $("#cxtj_panel").hide();
	 }
	 var cxlx=cxdy.cxlx;
	 if(cxlx!="4"){
	 $("[dataName='sqlmc']").html(cxdy.sqlmc).attr("title",cxdy.sqlmc);
	 }
	 $("#dataShow").height($(window).height()-$("#bodyTop").height()-25);


	 if(cxdy.cxlx==4){ // 报表查询 
		 goBbcx()
		 return true;
	 }
	 
	//初始化分页表
	 var oTable = new TableInit(cxdy);
	
	 oTable.Init();
 
  
	 
}) ;

function goBbcx(){ // 报表查询
	
	  var cxtjParams =getCxtjParams();
		
	  var queryParamsStr = "" ;
	
	  for(var i=0;i<cxtjParams.length;i++){
		  
		queryParamsStr = queryParamsStr+"&"+cxtjParams[i].name+"="+cxtjParams[i].value;
		  
	  }
	 
	  $("#dataShow").html("<iframe  id='bbcxFrame' name='bbcxFrame' src='/reportJsp/" + "showReport.jsp?rpx="+  cxdy.sqlstr + "&queryParams="+queryParamsStr+ "&sjylx=" + cxdy.sjylx+"' width='100%' height='100%' frameborder='0' style='margin:0;padding:0;'></iframe>")
	  
 
}
 
 

//查询按钮
function queryMx() {


	 if(cxdy.cxlx==4){ // 报表查询 
		 goBbcx()
		 return true;
	 } 
	
	$('#tb_departments').bootstrapTable("destroy");
	 
    var oTable = new TableInit(cxdy);
 	
	oTable.Init();
	  
     
	  
}


var initCxtj = function(cxtj){
 
	var ointCxtj  = new Object();
	
	ointCxtj.init = function(){ 
		
		var tr = "" ;
		var j = 0 ;
		
		for(var i =0;i<cxtj.length;i++){
			var tj = cxtj[i];

			if(tj.tjxylx=='3'){
			   
				var mrz = "";
				if(tj.mrz){
					mrz = tj.mrz;
				}
                 
				$("#cxtjYctj").append('<input style="width:250px;" type="text"  data-type="string" name="'+tj.lmc+'" id="tj_'+tj.lmc+'" value="'+mrz+'" uuid="'+tj.uuid+'" />');
			  
			}else{
				
				if(j%3==0){
					tr = $("<tr></tr>");
				} 
				
				$(tr).append('<td  align="right"><span>'+tj.tjmc+'：</span></td>');
				
				var inptd = get(tj);
				  
				$(tr).append(inptd);
				
				//if(j%3==2||j==cxtj.length-1){
					$("#cxtjTableBody").append(tr);
				//}
			    
				j++;
				
			}
			
		}
		//如果没有查询条件，隐藏查询按钮
		if(j==0){
			$("#element").hide();
		}
		initDate(); //绑定事件
		
		initZtree(cxtj); //初始化树
		
	}
	
	
	function get(tj){ 
		
		var mrz = "";
		if(tj.mrz){
			mrz = tj.mrz;
		}

		var $td = $('<td  align="left" ></td>');
		
		var $inp = $('<input style="width:250px;" type="text"  data-type="string" name="'+tj.lmc+'" id="tj_'+tj.lmc+'" value="'+mrz+'" uuid="'+tj.uuid+'" />');
		switch(tj.llx){
		 case "VARCHAR2"://字符

			$inp = $('<input style="width:250px;" type="text" data-type="string" name="'+tj.lmc+'" id="tj_'+tj.lmc+'" value="'+mrz+'" uuid="'+tj.uuid+'" />');
			break;
		 case "DATE"://日期
	 
			$inp = $('<input class="datainp"  type="text" data-type="string" placeholder="'+tj.xsgs+'"  date-format="'+tj.xsgs+'"   name="'+tj.lmc+'" id="tj_'+tj.lmc+'" value="'+mrz+'" uuid="'+tj.uuid+'"  >')
			 
			break;
		 case "NUMBER"://数值
			 
			break;
		 case "SELECT"://单选
			
			$inp = getCombData(tj);
			 	
			
			break ;
		 case "MULTI"://下拉多选
 			 
 			 $inp = $(' <div class="input-group combo_box" id="combo_'+tj.lmc+'" style="display:static;" style="" mouseIn="0" > ' +
				 	  ' 	<input id="tj_'+tj.lmc+'"  data-type="string" name="'+tj.lmc+'" value="'+mrz+'" class="combo_real " type="hidden" /> ' +
				 	  '     <input class="combo_inp " value="" id="combo_'+tj.lmc+'" readonly="readonly" />'+
				 	  '     <div class="dropdown"></div>'+
			          '     <div class="combo_content ztree " id="combo_'+tj.lmc+'_content"  >123</div> '+
				      ' </div> ') ;
			 
			break ;
		 case "SINGLETREE"://单选树
			 
			break ;
		 case "TREE"://多选树
			 $inp = $(' <div class="input-group combo_box" id="combo_'+tj.lmc+'" style="display:static;" style="" mouseIn="0" > ' +
				 	  ' 	<input id="tj_'+tj.lmc+'"  data-type="string" name="'+tj.lmc+'" value="'+mrz+'" class="combo_real " type="hidden" /> ' +
				 	  '     <input class="combo_inp " value="" id="combo_'+tj.lmc+'" readonly="readonly" />'+
				 	  '     <div class="dropdown"></div>'+
			          '     <div class="combo_content ztree " id="combo_'+tj.lmc+'_content"  >123</div> '+
				      ' </div> ') ;
			
			break ;
		 case "ORGTREE"://机关-行政树
 		 
			 $inp = $(' <div class="input-group combo_box" id="combo_'+tj.lmc+'" style="display:static;" style="" mouseIn="0" > ' +
				 	  ' 	<input id="tj_'+tj.lmc+'"  data-type="string" name="'+tj.lmc+'" value="'+mrz+'" class="combo_real " type="hidden" /> ' +
				 	  '     <input class="combo_inp " value="" id="combo_'+tj.lmc+'" readonly="readonly" />'+
				 	  '     <div class="dropdown"></div>'+
			          '     <div class="combo_content ztree " id="combo_'+tj.lmc+'_content"  >123</div> '+
				      ' </div> ') ;
	 
			break ;
		 case "ZNJGTREE"://机关-职能树
			 
			
			break ;
		 case "SWSSELECT"://税务所-单选
			 

			break ;
		 case "SWSDXSELECT"://税务所-多选
			 
			break ;
		 case "SWJGTREE"://机关树
			 
			break ;
		 case "HSJGTREE"://核算机关树
			 
			
			break ;
		 case "SERCHSINGTREE"://可过滤-查询树
			 
			
			break ;
		 case "SERCHTREE"://可过滤-查询树
			 
			
			break ;
		}
		
		$td.append($inp);
		
		return $td;
		
	}
	
	function initDate(){
		 
		
		$("input.datainp").each(function(){
			
			var format = $(this).attr("date-format");
			
			//转换控件支持的日期格式
			format = format.replace("Y","YYYY").replace("y","YYYY").replace("m",'MM').replace("d","DD").replace("H","hh").replace('i','mm').replace("s",'ss');
			 
 			  $(this).jeDate({ 
				    zIndex:3000, 
				 	isTime:false,
					format:format,
					isinitVal:true
			 });
			
			
		});
		
		 
		
		$(".combo_box").bind({
 			mouseenter:function(){
			   $(this).attr("mouseIn","1");
		    },
		    mouseleave:function(){
		    	
			   $(this).attr("mouseIn","0"); 
			   var obj = this;
			   
			   setTimeout(function(){
 				   if($(obj).attr("mouseIn")=="0"){
					   $(obj).find(".combo_content").slideUp();
					   $(obj).removeClass("isup");
				   }
			   },1000);
			   
			   
		    }
		
		});
		
	 
		
		$(".combo_box .dropdown").bind({
			
			click:function(){ 
		 
			   $(this).parent().toggleClass("isup");
			   $(this).parent().find(".combo_content").slideToggle();
			   
		    }
		
		
		})
	 
		
		$(document).mousedown(function(){
			 
			$(".combo_box").each(function(){  
				if($(this).attr("mouseIn")=="0"){
			    	$(this).find(".combo_content").eq(0).slideUp();
			    	$(this).removeClass("isup");
				} 
			});
		
		})
		
		
		$(".combo_inp").bind({
			
			click:function(){ 
			   $(this).parent().find(".combo_content").eq(0).slideDown(); 
			   $(this).parent().addClass("isup");
			},
			 
		})
		
		 
	}
	
	function getCombData(tj){
		var mrz;
		if(tj.mrz!=null&&tj.mrz!=""){
		 mrz = tj.mrz.split('||')[0]
		}
		var $inp = $('<select  name="'+tj.lmc+'" id="tj_'+tj.lmc+'" value="'+mrz+'"  data-type="string" ></select>');
  
		$.ajax({
		      url:"/download.sword?ctrl=CX301DzcxCtrl_getCombData&a="+Math.random(),
		      async:false,
		      data:{
		      	"dmsql":encodeURI(encodeURI(tj.dmsql)),
		      	"zdycs":tj.zdycs,
		      	"sjymc":tj.sjymc,
		      	"llx":tj.llx,
		      	"jsonType":"01",
		      	"gwssswjg":gwssswjg,
		      	"gwxh":gwxh,
		      	"zndm":zndm
		      },
		      dataType:'json',
		      success:function(data,textStatus,jqXHR){  
		    	 // alert(JSON.stringify(data))
	 	      	  if(data){ 
		 	      		for(var i=0;i<data.length;i++){ 
		 	      			var dat = data[i]; 
		 	      			if(dat.dm == mrz){
		 	      				$inp.append('<option value="'+dat.dm+'" selected="selected" >'+dat.mc+'</option>');
		 	      			}else{
		 	      				$inp.append('<option value="'+dat.dm+'"  >'+dat.mc+'</option>');
		 	      			} 
		 	      		} 
	 	      	  } 
		      },
			  error:function(xhr,textStatus){
			    
			  }
		 });
		
		return $inp
		
	}
	
	
	  
	function initZtree(cxtj){ //初始树
		
		  function resetZtree(treeId){
			  
			  var treeObj = $.fn.zTree.getZTreeObj(treeId);
			  
			  var nodes = treeObj.getCheckedNodes(true);
            
			  var combo_box = $("#"+treeId).closest(".combo_box");
			  
			  var nameArr = new Array();
			  var idArr= new Array();
			  
			  $(nodes).each(function(){
				  
				 nameArr.push(this.name);
				 idArr.push(this.iconCls);
			  
			  });
 
			  $(combo_box).find(".combo_real").val(idArr.join(","));
			  $(combo_box).find(".combo_inp").val(nameArr.join(",")).attr("title",nameArr.join(","));

		   }
		
		
		   var setTing = function(tj){
			 
			   
		   function radioCheck(event, treeId, treeNode) {
 
				resetZtree(treeId);
				 
	  	   }
			  
			  
		   function zTreeOnClick(event, treeId, treeNode) {
			 
			  var treeObj = $.fn.zTree.getZTreeObj(treeId);
			  treeObj.checkNode(treeNode);
			  resetZtree(treeId);
			  
		   };

		   function filter(treeId, parentNode, childNodes) {
 		      
 		   return childNodes;
		   }
	 
		   
		   var setting = {
				   check:{
			   chkStyle:'checkbox'
		   },
				view: {
					showLine: false,
					selectedMulti: false,
					dblClickExpand: false
				},
				key:{
					name: 'text'
				},
				data: {
					simpleData: {
						enable: true,
						idKey : "id",
						pIdKey: "pid",
						rootPId: null
					}
				},
				callback:{
		    			onCheck: radioCheck,
		    			onClick: zTreeOnClick 
		    	 }
				
		     };
					
		     if(tj.llx == "ORGTREE"){
		    	 
		    	 setting.check={
		    			enable: true,
						chkStyle: "radio" ,
						radioType : "all"
		    	 }
		  
		    	 setting.async={
		    		enable: true,
					url:"/download.sword?ctrl=CX301DzcxCtrl_getTreeData",
					autoParam:["id","id=parentID"],
					otherParam:tj,
					dataFilter: filter 
		    			 
		    	 }
		    	 
		     }else if(tj.llx == "MULTI"){
		    	 
		    	 setting.check={
		    			enable: true,
						chkStyle: "checkbox" ,
 		    	 }
		    	 
		     }else if(tj.llx == "TREE"){
		    	 
		    	 setting.check={
			    			enable: true,
							chkStyle: "radio" ,
							radioType : "all"
			    	 }
			  
			    	 setting.async={
			    		enable: true,
						url:"/download.sword?ctrl=CX301DzcxCtrl_getTreeData",
						autoParam:["id","id=parentID"],
						otherParam:tj,
						dataFilter: filter 
			    			 
			    	 }		    	 
		     }
		     
			 
			 return setting;
			 
			 
		 }
		  
		 function getTreeData(tj){ 
				tj.gwssswjg = gwssswjg ; 
				tj.gwxh = gwxh;
				tj.zndm = '01' ;
				tj.tjlx = tj.llx;
				tj.jsonType="01";				
				var treedata =null;
				$.ajax({
				      url:"/download.sword?ctrl=CX301DzcxCtrl_getTreeData&a="+Math.random(),
				      async:false,
				      type:'post',
				      data:tj,
				      dataType:'json',
				      success:function(data,textStatus,jqXHR){ 
			 	      	  if(data){ 
			 	      		 treedata =data; 
			 	      	  } 
				      },
					  error:function(xhr,textStatus){
				    	  	return null;
					  }
				 }); 
				
				return treedata
				
		 } 
		
		 function initTree(id,setting,data){
  			 $.fn.zTree.init($("#"+id), setting, data);
			 
 			 resetZtree(id);
 			 
		 }
		   
		 $(cxtj).each(function(index,tj){  
			 
			 switch(tj.llx){ 
			 
			  case "ORGTREE": 
				   
				   var data = getTreeData(tj); 
				    
				   var setting = new setTing(tj); 
		
				   var id = 'combo_'+tj.lmc+'_content'; 
				   
				   initTree(id,setting,data);
				    
				   break; 
				 
			  case "MULTI": 
				   
				   var data = getTreeData(tj); 
				   var setting = new setTing(tj); 
				   var id = 'combo_'+tj.lmc+'_content'; 
				   initTree(id,setting,data); 
				   break;   
			  case "TREE": 
				   
				   var data = getTreeData(tj); 
				   var setting = new setTing(tj); 
				   var id = 'combo_'+tj.lmc+'_content'; 
				   initTree(id,setting,data); 
				   break;   
			 }
			 
		 }) ;
		 
		 
	}
	
	return ointCxtj;
	
}




function getCxtjParams(){
	
	var cxtjParams = new Array();
	var cxtj = cxdy.cxtj;
	var obj_djxh={
			"name":'DJXH',
			"type":'string',
			"value":djxh
	};
	var obj_nsrsbh={
			"name":'NSRSBH',
			"type":'string',
			"value":nsrsbh
	};
	cxtjParams.push(obj_djxh);
	cxtjParams.push(obj_nsrsbh);
	
	for(var i=0;i<cxtj.length;i++){
		 
		var tj = cxtj[i];
		
		var obj = {
			"name":tj.lmc,
			"type":$("#tj_"+tj.lmc).attr("data-type"),
			"value":$("#tj_"+tj.lmc).val() 
		}
		;
		cxtjParams.push(obj);
		
	} 	
	
	 	
	return cxtjParams;
	
}


var Cols = null;

var columns = null;

var code2Name = new Array();

function getColumns(){
	
	var cxtjParams =getCxtjParams();
	
	var queryParams = encodeURI(encodeURI(JSON.stringify(cxtjParams)));
	
	$.ajax({
	      url:"/download.sword?ctrl=CX301DzcxCtrl_getResultColumns",
	      async:false,
	      data:{
		    'sqlxh':sqlxh,
		    'queryType':'1',
		    'queryParams':'',
		    'jsonType':'01'
	      },
	      dataType:'json',
	      success:function(data,textStatus,jqXHR){  
 	      	  
 	      	  Cols = data;
 	      	  
 	      },
		  error:function(xhr,textStatus){
		    
		  }
	 });
	
	var  columnss = new Array();
	
	code2Name = new Array();
	
	if(Cols){
		
		$(Cols).each(function(){
			
			var col = this;
			
			
			
			var column = {
					field:col.lmc,
					title:col.lms,
					formatter:function(value,row,index){
				  
						return " <div class='tableCell' onmouseenter='showCellValue(this)'  style='min-width:100%;max-width:200px;text-overflow:ellipsis;white-space:nowrap;overflow:hidden;'> "+value+"</div>";
			       
					}
			}	
			
			if (col.dmsql) {
				
				code2Name.push({
					name : col.lmc,
					table : col.dmsql
				});
				
				column.field = col.lmc+"_MC";
				 
			}
			
			columnss.push(column);
			
			
		});
		
	 }
	
	columns = columnss; 	
	
}

function showCellValue(obj){

	var value = $(obj).text();
    $("#tootip span").html(value);
	$('#tootip').css("top",event.clientY);
	$('#tootip').css("left",event.clientX);
    $("#tootip").show();
     
}



var TableInit = function(cxdy) {
 
   var oTableInit = new Object();
  
   if(!columns){ 
	   getColumns(); 
   } 
   //初始化Table
   oTableInit.Init = function () {
	   $('#tb_departments').bootstrapTable("destroy");
	  $('#tb_departments').bootstrapTable({
		// url: '/download.sword?ctrl=CX904YhscxwhCtrl_initView',   //请求后台的URL（*）
		   url:'/download.sword?ctrl=CX301DzcxCtrl_executeQuery&a='+Math.random(),
		   method: 'get',         //请求方式（*）
		// toolbar: '#toolbar',   //工具按钮用哪个容器
		   striped: false,        //是否显示行间隔色
		   cache: false,          //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		   pagination: true,      //是否显示分页（*）
		   sortable: false,       //是否启用排序
		   sortOrder: "asc",      //排序方式
		   queryParamsType:'',
		   queryParams: oTableInit.queryParams, //传递参数（*）  
		   search: false,          //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
		   strictSearch: false,    //
		   showColumns: false,     //是否显示所有的列
		   showRefresh: false,     //是否显示刷新按钮
		   minimumCountColumns: 2, //最少允许的列数
		   clickToSelect: true,    //是否启用点击选中行
		// height: 600,      	   //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		// uniqueId: "ID",         //每一行的唯一标识，一般为主键列
		   showToggle:false,       //是否显示详细视图和列表视图的切换按钮
		   cardView: false,        //是否显示详细视图
		   detailView: false,         //是否显示父子表
		   sidePagination: "server",  //分页方式：client客户端分页，server服务端分页（*） 
		   pageNumber: 1,             //初始化加载第一页，默认第一页
		   pageSize: 5,               //每页的记录行数（*）
		   pageList:[10,25,50,100],   //可供选择的每页的行数（*）
		   columns:columns
		   
		   
	  });
	  
 	  
  };
 
  
 //得到查询的参数
  oTableInit.queryParams = function (params) {
 
	 var cxtjParams =getCxtjParams();
	 var queryParamsStr = encodeURI(encodeURI(JSON.stringify(cxtjParams)));
	     
	 var queryParams = {	
		     "queryParams":queryParamsStr,
		     "jsonType":'01',
		     "code2Name":JSON.stringify(code2Name),
		     "sqlxh":cxdy.sqlxh,
		     "queryType":'',
		     "doTotal":true,
		     "page":params.pageNumber,
		     "limit":params.pageSize
	  }
	  
	  return queryParams;
  };
 
  return oTableInit;
 
};
 



var ButtonInit = function () {
	 var oInit = new Object();
	 var postdata = {}; 
	 return oInit;
};


/**
 * 获取表数据
 */
function hqBmsj(bm,bzwm){
	$('button[name=baocun]').hide();
	openIframe('查看表数据','/dsj/dsj011/dmbsj.html?bm='+bm+'&bzwm='+encodeURI(encodeURI(bzwm)));		
}

/**
 * 获取表明细
 */
function hqBmsm(bm,bzwm){
	$('button[name=baocun]').hide();
	openIframe(bzwm+"表说明",'/dsj/dsj011/dmbsm.html?bm='+bm);
}

function openIframe(title,url){
	$('.modal-title').html(title);
	document.getElementById("iframeTab").src=url; 
	$("#mymodal").modal("toggle");
}

function closeModal(){
	document.getElementById("iframeTab").src=""; 
}
/**
 * 导出数据方法
**/
function exportExcel(){
	alert(2);
	if(cxdy.cxlx=='4'){
		//document.domain="127.0.0.1";
		document.getElementById("bbcxFrame").contentWindow.report1_saveAsExcel();
	}else{
	$.ajax({
	      url:"/download.sword?ctrl=CX301DzcxCtrl_generateExportFile&r="+Math.random(),
	      async:false,
	      data:{
		    'sqlxh':sqlxh,
		    'fileType':'excel',
		    'queryParams': getParams(), //传递参数（*）  
		    'jsonType':'01',
		    'queryType':'6',
		    'doTotal':true,
		    'max':'20000'
	      },
	      dataType:'json',
	      success:function(data){  
	    	  var path=encodeURI(encodeURI(res.responseText));
	    	  var url="/download.sword?ctrl=CX301DzcxCtrl_exportFile&filePath="+path+"&r="+Math.random();
			var eleIf=document.createElement("iframe");
			eleIf.src=url;
			eleIf.style.display="none";
			document.body.appendChild(eleIf);
	      },
		  error:function(res,textStatus){
	    	 /** var path=encodeURI(encodeURI(res.responseText));
			   var url="/download.sword?ctrl=CX301DzcxCtrl_exportFile"+"&r="+Math.random();
			   var form=$("<form>");
			   form.attr('style','display:none');
			   form.attr('target','');
			   form.attr('method','post');
			   form.attr('action',url);
			   var input1=$("<input>");
			   input1.attr("type","hidden");
			   input1.attr("name","filePath");
			   input1.attr("value",path);
			   $("body").append(form);
			   form.append(input1);
			   form.submit();
			   form.remove();**/
	    	  var path=encodeURI(encodeURI(res.responseText));
	    	  var url="/download.sword?ctrl=CX301DzcxCtrl_exportFile&filePath="+path+"&r="+Math.random();
			var eleIf=document.createElement("iframe");
			eleIf.src=url;
			eleIf.style.display="none";
			document.body.appendChild(eleIf);
		  }
	 });
	}
}
function getParams(){
	 //得到查询的参数	 
		 var cxtjParams =getCxtjParams();
		 var queryParamsStr = encodeURI(encodeURI(JSON.stringify(cxtjParams)));		     
		 var queryParams = {	
			     "queryParams":queryParamsStr,
			     "jsonType":'01',
			     "code2Name":JSON.stringify(code2Name),
			     "sqlxh":cxdy.sqlxh,
			     "queryType":'',
			     "doTotal":true
		  }		  
		  return queryParamsStr;
}