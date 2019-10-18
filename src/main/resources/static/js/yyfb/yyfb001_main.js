var zNodes =null;
var searchData = new Object();
var zTree_Menu = null;
var actionNode = null;
var fxyy_id=null;
var folderid=null;
var form=0;
var mlid=null;

$(function(){
	//var zNodes =[{name:'测试1',open:true,children:[{name:'test11'},{name:'test1'}]}];
	LayOutBody();
	
	$("#tree").layout({
     	resizable:false,
        closable:false,
        applyDemoStyles: false,
        west__size:300,
        spacing_open:0
    });
	 $("#tree").bind("contextmenu",function(e){
	    return false;
	}); 
     var times=null;
     
     $("#listgroup").on("mouseenter",function(){
    	   if(times!=null){
    	     clearTimeout(times);
    	   }
     });

	$("#listgroup").on("mouseleave",function(){
	   times=setTimeout(function(){
	   	$('#listgroup').hide();
	   },2000);
	});
 	 
	initTree();
	
	search_dataTable();
	$("#tjfj").attr("disabled",true);
	$("#xzsjy").attr("disabled",true);
	$("#jgTab_1 li").each(function(i){
		$(this).click(function(){
			form=i;
			if(form=='1'){
				 $("#tjfj").attr("disabled",false);
				 $("#xzsjy").attr("disabled",true);
				}else if(form=='0'){
				$("#tjfj").attr("disabled",true);
				$("#xzsjy").attr("disabled",true);
				}else if(form=='2'){
				$("#xzsjy").attr("disabled",false);
				$("#tjfj").attr("disabled",true);
				}
		})
	});
	
	$('#myModalR').on('shown.bs.modal',function(){
		if(fxyy_id==null||fxyy_id==''){
			 document.getElementById("fxyyForm").reset();
			 $("#fxyyForm input[type='hidden']").val("");
			 document.getElementById("kfxxForm1").reset();
		}else{
			search_sjdyTable(fxyy_id);
			search_kfxx(fxyy_id);
			search_fj(fxyy_id);
		}
	});
	
});

function initTree(){
	
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=YyfbService_queyfolder",//路径
		success : function(result) {//返回数据根据结果进行相应的处理
			zNodes=result;  
		},
		error:function(e){
			
		} 
    });
 	
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
	zTree_Menu.expandNode(zTree_Menu.getNodes()[0]);
	
}


var table = null;
var setting = {
		view: {
			showLine: false,
			selectedMulti: false,
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true,
				idKey : "id",
				pIdKey: "pid",
				rootPId: null
			}
		},
		callback: {
		   onClick: cxxxxx,
		   onRightClick: OnRightClick
		}
	};
function cxxxxx(event, treeId, treeNode) {
	folderid = treeNode.id ;
	searchData.gnlj=folderid;
	table.reload(searchData);	
}
function OnRightClick(event, treeId, treeNode) {

	 if(!treeNode){
		return;
	 }
	 
    zTree_Menu.selectNode(treeNode); 
    
    $("#listgroup").css("left",event.clientX+10);
 	 $("#listgroup").css("top",event.clientY);
    $("#listgroup").show();
    
    actionNode=treeNode;
    mlid=treeNode.id;
    
    times=setTimeout(function(){
    	$('#listgroup').hide();
    },1500);
    
}
function toadddw(){
	var rowData = new Object();
	if(actionNode!=null){
	rowData.sjmlmc=actionNode.name;
	rowData.pfolder_id=actionNode.id;
	}else{
		rowData.sjmlmc="";
		rowData.pfolder_id="";
	}
	rowData.mc='';
	$("#mlForm").cssnjSetFormAsJson(rowData) ;
	$("#myModal2").modal("show");
	
}
//修改和添加目录 
function xgdw(){
    var  datas = $("#mlForm").cssnjGetFormData();
	$.ajax({
            cache: false,
            type: "POST",
            url:ctx+"tykf/request?tld=YyfbService_saveFolder&a="+Math.random(),
            data:datas,
            dataType:'json',
            async: false,
            error: function(request) {
                $('#bcsb').modal('show');
            },
            success: function(data1){    
            	 if(data1.success=="1"){
                     
                     $("#mlbox").modal("hide");
                     $.cssnj.alert("保存成功！ ");
                     
    				 initTree();
    				   
                  }else{
                  	$.cssnj.alert("保存失败 ！ ");
                  }       
            }
      });
        
}
function search_dataTable(){
	
	var  data = $("#cxtj_form").cssnjGetFormData();
    
	if(table==null){
	    table = $("#example").fyTable(ctx+"tykf/request?tld=YyfbService_select&a=" + Math.random(),data);
	}else{
		table.reload(data);
	}
	 
}

function openMx(rowData,rowEl,el){
	  fxyy_id=rowData.fxyy_id;
//	  $("#fxyyForm").cssnjSetFormAsJson(rowData) ;
//	  $("#myModalR").modal({backdrop:'static'});
	  cssnj_window(ctx+"tykf/request_http?tld=YyfbService_initNew&fxyy_id="+fxyy_id);

}
function search_sjdyTable(fxyy_id){
	var data={'fxyy_id':fxyy_id};
	$("#sjyTable").dataTable().fnDestroy();
	var cxjgTable = $("#sjyTable").fyTable(ctx+"tykf/request?tld=YyfbService_querySjdy&a=" + Math.random(),data);
}




/**
 * 
 * 以下处理新建或修改数据源的代码
 * 
 */



  

function openForm(){	 
	 if(folderid==null||folderid==""){
		 $.cssnj.alert("请选择目录");
		 return;
	 }
	 fxyy_id='';
	 
	 //$("#myModalR").modal({backdrop:'static'});
	 cssnj_window(ctx+"tykf/request_http?tld=YyfbService_initNew&folderid="+folderid);
}



/**
 * 下载附件
 */
function xzfj(){
	
}
//添加附件
function addfj(){
	window.open(ctx+"tykf/request_http?tld=YyfbService_addFj", "添加附件", "height=100,width=400,top=400,left=800,location=no", false);
}
//新增数据单元
function addSjdy(){
	 var fxyyform = $("#fxyyForm").cssnjGetFormData();
	 fxyy_id=fxyyform.fxyy_id;
	 $("#fxyy_id").val(fxyy_id);
	 $("#sjdyModal").modal({backdrop:'static'});
}
var sddytable = null;
function search_sddyTable(){
	var  data = $("#sjdyCxtj_form").cssnjGetFormData();   
	if(sddytable==null){
		sddytable = $("#sjdyTable1").fyTable(ctx+"tykf/request?tld=YyfbService_addSjdyByCxtj&a=" + Math.random(),data);
	}else{
		sddytable.reload(data);
	}
	 
}

function addSjdyByFxyyid(){
	
	if(sddytable!=null){
	 var fxyy_id = $("#fxyy_id").val();	
	 var checkedRow = sddytable.getCheckedData();
	 var jsonStr="[";
	   for(var i = 0;i<checkedRow.length;i++){		
		   var row = checkedRow[i];		
		   var json_new="{";
		   for(var p in row){
			   json_new=json_new+"'"+p+"':'"+row[p]+"',";
		   } 
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
//	        	  $('#Riframe').attr("src",ctx+"tykf/request_http?tld=YyfbService_initJbxxView&fxyy_id="+fxyy_id);
//	        	 $("#myModalR").modal({backdrop:'static'});
//	        	 $("#sjdyModal",window.parent.document).modal('hide');
	        	if(data.messageCode==-1){
	        		$.cssnj.alert("添加失败，该数据单元已经添加");
	        		 search_sjdyTable(fxyy_id);
	   	       	  search_kfxx(fxyy_id);
	   	       	  search_fj(fxyy_id);
	   	       	  return;
	        	}
	       	  search_sjdyTable(fxyy_id);
	       	  search_kfxx(fxyy_id);
	       	  search_fj(fxyy_id);
	        	$.cssnj.alert("添加成功");	
	        	
	        },  error:function(require){
	        
	         	//$jq.cssnj.alert("查询异常");
	         	
	        }
    });
	}

}
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
function fbFxyy(){
	if(fxyy_id==null||fxyy_id==''){
		 $.cssnj.alert("请先保存基本信息");		
		 return ;
	}
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=YyfbService_saveFxyy&a="+Math.random(),//路径
		data:{'fxyy_id':fxyy_id,'zt_bj':'3'},
		success : function(result) {//返回数据根据结果进行相应的处理			
					 fxyy_id=result.fxyy_id;
					  search_sjdyTable(fxyy_id);
					  search_kfxx(fxyy_id);
					 search_fj(fxyy_id);
					 $.cssnj.alert("发布成功!");			
			 
		},
		error:function(e){
			
		} 
 });
}

 

/**
 * 以下针对 dblink 的js 
 */
 


 $(function(){

	$("#myModal1").on('hide.bs.modal',function(){ //关闭窗口时清空form
	 	 document.getElementById("fxyyForm").reset();
		 tr=null; 
		 cssnjInitData(); 
		 table.reload();
			 
	 });
	
	
	$("#myModal1").on('show.bs.modal',function(){ //打开窗口时初始化form

	});
	
	  
});
function toaddMl(){
	actionNode = getSelectedTreeNode();	
	  if(actionNode!=null){
	 
		      $("#mlForm").cssnjForm({
		    	  "mldm":"",
		    	  "mc":"",
		          "pfolder_id":actionNode["id"],
		      	  "sjmlmc":actionNode["name"],
		      	  "pxxh":actionNode["pxxh"],
	 	      }); 
		      
		      var node = zTree_Menu.getNodesByParam("id",actionNode["id"], null)[0];
		      
		      if(node!=null){
		    	  zTree_Menu.selectNode(node);
		      }
	    
	  }else{
	    
		      $("#mlForm").cssnjForm({
		    	  "mldm":"",
		    	  "mc":"",
		          "pfolder_id":"",
		      	  "sjmlmc":"",
		      	  "pxxh":""
		      }); 
	          
		      
	   }
	  
	   
	   $("#mlbox").modal("show");
}
function getSelectedTreeNode(){ 
	
	var nodes = zTree_Menu.getSelectedNodes();
	
	if(nodes.length>0){
		
		var node = nodes[0];
		
 		if(node["ID"]=="ALL"){
			return null;
		}else{
			return node;
		}
 		
	}else{
		return null;
	}
	
	return zTree_Menu.getSelectedNodes();
	
}
 
function toxgMl(){

	   if(actionNode==null){
	     return false;
	   }
	   
	   var pid=actionNode["pid"];

	   if(pid==null){
		   $.cssnj.alert("管理目录不可更改！");
		   return false;
	   }
	   $("#mlForm").cssnjForm({
		   "folder_id":actionNode["id"],
		   "pxxh":actionNode["pxxh"],
		   "mc":actionNode["name"],
		   "pfolder_id":pid
	   });
	   
	   var pnode = zTree_Menu.getNodesByParam("id",pid, null)[0];

	   if(pnode!=null){
		     $("#mlForm").cssnjForm().setValue("sjmlmc",pnode.name);
		     zTree_Menu.selectNode(pnode);
	   }
	    
	   $("#mlbox").modal("show");

	}
//作废单位
function toscMl(){
  mlid=actionNode["id"];
  $.cssnj.messager("提示信息","删除选中目录 "+actionNode["name"]+" 吗? ",{
     showCancel:true,
     showDel:true,
     showOk:false,
     showImage:'/public/js/image/sc.png',
     onDel:qdzf
   });
   
}
function zfCheck(){
	if(table!=null){
	   var checkedRow = table.getCheckedData();	  
	   var dblist = new Array();
	   for(var i = 0;i<checkedRow.length;i++){		   
		   var row = checkedRow[i];		   
		   dblist.push(row["fxyy_id"]);		   
	   }
	   zfdb(dblist.join(","));		 
	}	

}

function zfdb(dbs){
	$.cssnj.messager("确定删除","确定删除？",{
        "onOk":function(){
       
	          $.ajax({
				    url:ctx+"tykf/request?tld=YyfbService_deleteFxyy",    //请求的url地址
					    data: { "fxyy_id":dbs},    //参数值
					dataType: "json",   //返回格式为json
    		 		async:false,
    				type: "POST",   //请求方式
				    success: function(data){				    
						if(data.state == "1"){			      	    
				      	    $.cssnj.alert("删除成功！");				      	    
				      	    table.reload();								
				     	}
						
						if(data.state == "0"){
						
			          		$.cssnj.alert("删除失败！");

			     		}
			     		
				    }	
			  });
			  
         
        }
    
    
    });	
//	$.post(ctx+"tykf/request?tld=YyfbService_deleteFxyy",{"fxyy_id":dbs},function(data){
//		
//		 $.cssnj.alert("删除成功");		
//		 table.reload();
//		 
//		 
//	 },"JSON");
	
}
function qdzf(){
	   
	 $.ajax({
	    type : 'POST',
	    url : '/tykf/request?tld=YyfbService_delFolder',
	    data : {
		 folder_id : mlid
	    },
	    dataType:'json',
	    success : function (data) {
	    	
	    	if(data.success=="1"){
                
                $("#mlbox").modal("hide");
                $.cssnj.alert(data.message);
                
				 initTree();
				   
             }	          
//			  setTimeout(function(){
//			     $.cssnj.alert(data.messager);
//			  },200);
			  
	    }
	  });
	}

 






