var li_index;
var form=0;
var fxyy_id=null;

$(function(){
	
	//var zNodes =[{name:'测试1',open:true,children:[{name:'test11'},{name:'test1'}]}];
	
	LayOutBody();
	
	var gy_valid = $("#gy_valid").val();
 	if(gy_valid==""){
		gy_valid='N';
	}
	$("input[name='gy_valid'][value='"+gy_valid+"']").attr("checked",true);
	if(gy_valid=="Y"){
		$("#gyTr").show();
	}else{
		$("#gyTr").hide();
	}
	 
	
	fxyy_id=$("#fxyy_id").val(); 
	
	try{
		if(fxyy_id==""){
			fxyy_id=null;
		}
	}catch (e) {
		// TODO: handle exception
	}
	
	if(fxyy_id!=null){
		reloadAll(fxyy_id);
	}
	 
	$("#jgTab_1 li").each(function(i){
		  $(this).click(function(){
				form=i;
				if(form=='1'){
					 $("#tjfj").attr("disabled",false);
					 $("#xzsjy").attr("disabled",true);
					 $("#scsjy").attr("disabled",true);
				}else if(form=='0'){
					 $("#tjfj").attr("disabled",true);
					 $("#xzsjy").attr("disabled",true);
					 $("#scsjy").attr("disabled",true);
				}else if(form=='2'){
					 $("#xzsjy").attr("disabled",false);
					 $("#scsjy").attr("disabled",false);
					 $("#tjfj").attr("disabled",true);
				}
		 });
	 });
	
	try{ 
		initZtree();
	}catch (e) {
	   null;
	}
	
	try{ 
		initBq();
	}catch (e) {
		null; 
	}
	
	
});

/**
 *刷新全部信息 
 */
function reloadAll(fxyy_id){
	 $("#cdUrl").val(ctx+"tykf/request_http?tld=YyunitService_openYy&fxyy_id="+fxyy_id) ;
 	 search_sjdyTable(fxyy_id);
	 search_kfxx(fxyy_id);
	 search_fj(fxyy_id); 
}

/**
 * 保存分析应用
 */
function save(){
     if(1==form){
 		$("#kfxxForm1").submit();
	}else{
		$("#fxyyForm").submit();
	}
     
 }

/**
 * 保存基本信息
 */
function saveFxyy(){
	
	var updata = $("#fxyyForm").cssnjGetFormData();
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=YyfbService_saveFxyy&a="+Math.random(),//路径
		data:updata,
		success : function(result) {//返回数据根据结果进行相应的处理	
			 fxyy_id=result.fxyy_id;
			 $.cssnj.alert(result.message);	
			 if(fxyy_id){
				 $("#fxyy_id").val(fxyy_id);
				 reloadAll(fxyy_id); 
				 saveYyBqAsView();
			 }
		},
		error:function(e){
			cssnj_alert("请求服务器失败！");
			reloadAll(fxyy_id);
		} 
	});
	 
}

/**
 * 保存开发信息
 */

function saveKfxx(){
	
	if(fxyy_id==null||fxyy_id==''){
		 $.cssnj.alert("请先保存基本信息");		
		 return ;
	}
	
	$("#xq_fxyy_id").val(fxyy_id);
	 
	var updata = $("#kfxxForm1").cssnjGetFormData();
	
 	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=YyfbService_saveFxyy&a="+Math.random(),//路径
		data:updata,
		success : function(result) {//返回数据根据结果进行相应的处理	
			
					 fxyy_id=result.fxyy_id;

					 $.cssnj.alert(result.message);	
					 if(fxyy_id){
						 $("#fxyy_id").val(fxyy_id);
						 $("#xq_fxyy_id").val(fxyy_id);
						 reloadAll(fxyy_id); 
					 }

		},
		error:function(e){
			cssnj_alert("请求服务器失败！");
			reloadAll(fxyy_id);
		} 
	});
	
}

/**
 * 发布分析应用
 * 
 */
function fbFxyy(){ 
	 
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		data:{
			"fxyy_id":fxyy_id
		},
		async:false,
		url:ctx+"tykf/request?tld=YyfbService_fbFxyy&a="+Math.random(),//路径
 		success : function(result) {//返回数据根据结果进行相应的处理		
			 
			    cssnj_alert(result.message);
				if(result.success==1){
					 $("#zt_bj").val("1");
					 $("#fbrmc").val(result.fbrmc);
					 $("#fbrdm").val(result.fbrdm); 
				} 
							
		},
		error:function(e){
			
		} 
	});
	
}

/**
 * 作废分析应用
 */
function tyFxyy(){
	
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		data:{
			"fxyy_id":fxyy_id
		},
		async:false,
		url:ctx+"tykf/request?tld=YyfbService_tyFxyy&a="+Math.random(),//路径
 		success : function(result) {//返回数据根据结果进行相应的处理		
			    cssnj_alert(result.message);
				if(result.success==1){
					 $("#zt_bj").val("2");
				} 
		},
		error:function(e){
			
		} 
	});
}

/**
 * 查询数据来源
 * @param fxyy_id
 */
var sjlyTable = null;
function search_sjdyTable(fxyy_id){
	
	var data={'fxyy_id':fxyy_id};
	
	if(sjlyTable==null){
 		sjlyTable = $("#sjyTable").fyTable(ctx+"tykf/request?tld=YyfbService_querySjdy&a=" + Math.random(),data);
	}else{
		sjlyTable.reload(data);
	}
	
}

/**
 * 查询开发信息
 * @param fxyy_id
 */
function search_kfxx(fxyy_id){
	  var datas={'fxyy_id':fxyy_id};
	  $.ajax({
	      cache: false,
	      type: "POST",
	      url:ctx+"tykf/request?tld=YyfbService_initJbxxView&a="+Math.random(),
	      data:datas,
	      dataType:'json',
	      async: false,
	      error: function(request) {
	      },
	      success: function(data1){     
	    	  var kfxxForm=data1.kfxx;
	    	  $("#kfxxForm1").cssnjSetFormAsJson(kfxxForm);  
	    	
	      }
	});
}
/**
 * 查询附件信息
 * @param fxyy_id
 */
var fjTable = null;
function search_fj(fxyy_id){
	var data={'fxyy_id':fxyy_id};
	if(fjTable==null){
		fjTable = $("#kfxx_fj").fyTable(ctx+"tykf/request?tld=YyfbService_queryFjxx&a=" + Math.random(),data);
	}else{
		fjTable.reload(data);
	}
}

function zs(fxyy_id){
	
	  var datas={'fxyy_id':fxyy_id};
	  $.ajax({
	      cache: false,
	      type: "POST",
	      url:ctx+"tykf/request?tld=YyfbService_initJbxxView&a="+Math.random(),
	      data:datas,
	      dataType:'json',
	      async: false,
	      error: function(request) {
	      },
	      success: function(data1){     
	    	  var fxyyForm=data1.fxyyForm;
	    	  var kfxxForm=data1.kfxxForm;
	    	  $("#fxyyForm").cssnjSetFormAsJson(fxyyForm);  
	    	  $("#kfxxForm").cssnjSetFormAsJson(kfxxForm);  
	    	 // $("#myModal1").modal("show");
	    	 // $("#myModalR").modal({backdrop:'static'});
	      }
	});
	  
}

 
//新增数据单元
function addSjdy(){
	 
	 cssnj_iframe("添加数据单元","/tykf/request_http?tld=YyfbService_addSjdy&fxyy_id="+fxyy_id );
	 
} 

//删除数据单元
function delSjdy(){
	
	if(sjlyTable==null){
		return false;
	}
	
	var checkedRow = sjlyTable.getCheckedData();
 
 	if(checkedRow.length<1){
		return false;
	}
	
	var duArr = new Array();
	 
	
	for(var i=0;i<checkedRow.length;i++){
  		if(checkedRow[i]){
 			duArr.push(checkedRow[i]["DATAUNIT_ID"]);
		}
	}
	
 	$.ajax({
	      cache: false,
	      type: "POST",
	      url:ctx+"tykf/request?tld=YyfbService_delSjdy&a="+Math.random(),
	      data:{
	    	"fxyy_id":fxyy_id, 
	    	"dus":duArr.join(",")
	      },
	      dataType:'json',
	      async:false,
	      success: function(data1){     
	    	  sjlyTable.reload();
	      },
	      error: function(request) {
	    	  
	      }
	});
	
	
	
}


//添加附件
function addfj(){
	 
	if(fxyy_id!=null){
		cssnj_iframe("添加附件",ctx+"tykf/request_http?tld=YyfbService_addFj&fxyy_id="+fxyy_id,{width:600});
	}else{
		cssnj_alert("请先保存基本信息！");
		
	}
	
}

/**
 * 下载附件
 * @param rowData
 * @param rowEl
 * @param el
 */
function ftpDownload(rowData,rowEl,el){
	var fjid=rowData.FJID;
	var data={'fjid':fjid};
	$.ajax({
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=YyfbService_downloadFj",
		data:data,
		success : function(data) {//返回数据根据结果进行相应的处理
			alert(data.msg);
		} 
	});
}




/**
 * 功能分类树
 */
var setting = {
	view: {
		showLine: false,
		selectedMulti: false,
		dblClickExpand: false,
		
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
	   onCheck: zTreeOnCheck
	},
	check: {
		enable: true,
		chkStyle:"radio",
		radioType: "all"
	}

};

function cxxxxx(event, treeId, treeNode) {
	
	zTree_Menu.checkNode(treeNode, true, true);
	zTreeOnCheck(event, treeId, treeNode);
	
}

function zTreeOnCheck(event, treeId, treeNode) {
     
	var id = treeNode["id"];
	var pid = treeNode["pid"];
 	var node =  treeNode;
 	
 	var name ="";
 	
	while(typeof node =="object"){
		name = ">"+node["name"] +name;
		node = zTree_Menu.getNodesByParam("id", node["pid"], null)[0];
	}
 	
	$("#gnlj").val(id);
	$("#gnljmc").val(name);

 	$("#treebox").modal("hide");
 	 
};



function initZtree(){
	
	var  zNodes = null;
    
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
	zTree_Menu.expandAll(true);
	
	var gnlj = $("#gnlj").val();
	
	var nodes = zTree_Menu.getNodesByParam("id", gnlj, null);
    if(nodes){
    	zTree_Menu.checkNode(nodes[0], true, true);
    }
	
 	
}


/**
 * 添加标签
 */

function addBq(){
	
	cssnj_iframe("添加标签",ctx+"tykf/request_http?tld=YyfwBqglService_init&fxyy_id="+fxyy_id,{width:600});
 
}

function initBq(){
	
	var list =null;
	
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		data:{
			'fxyy_id':fxyy_id
		},
		url:ctx+"tykf/request?tld=YyfwBqglService_searchYYbq",//路径
		success : function(result) {//返回数据根据结果进行相应的处理
			list = result;
		},
		error:function(e){
			
		} 
    });
	
	if(list==null){
		return false;
	}
	
	drawBq(list);
	
	return true;
	
}

function drawBq(list){
	
	$("#bqbox").empty();
 	for(var i=0;i<list.length;i++){
		var obj = list[i];
		$("#bqbox").append('<div class="bq" id="'+obj["bq_id"]+'" > '+obj["bq_mc"]+' <span class="glyphicon glyphicon-remove" title="删除" onclick=\'removeBq("'+obj["bq_id"]+'")\'></span></div>')
	}
	 
}

function saveYyBq(list){
	  
	if(fxyy_id!=null){
		
		var bqArr = new Array();
		
		$(list).each(function(){
			bqArr.push(this["bq_id"]);
		});
		
		$.ajax({  
			type : "POST",  //提交方式  
			dataType:'json',
			async:false,
			data:{
				'fxyy_id':fxyy_id,
				"bqs":bqArr.join(",")
			},
			url:ctx+"tykf/request?tld=YyfwBqglService_saveYyBq",//路径
			success : function(result) {//返回数据根据结果进行相应的处理
				initBq();
			},
			error:function(e){
				
			} 
	    });
	
	}else{
		
		drawBq(list);
		
	}
	
}

function saveYyBqAsView(){
	
	if(fxyy_id!=null){
		
		var bqArr = new Array();
		
		$("#bqbox>.bq").each(function(){
			bqArr.push($(this).attr("id"));
		})
		
		$.ajax({  
			type : "POST",  //提交方式  
			dataType:'json',
			async:false,
			data:{
				'fxyy_id':fxyy_id,
				"bqs":bqArr.join(",")
			},
			url:ctx+"tykf/request?tld=YyfwBqglService_saveYyBq",//路径
			success : function(result) {//返回数据根据结果进行相应的处理
				initBq();
			},
			error:function(e){
				
			} 
	    });
	
	}
	
}


function removeBq(bqid){
	
	if(fxyy_id!=null){
		
		$.ajax({  
			type : "POST",  //提交方式  
			dataType:'json',
			async:false,
			data:{
				'fxyy_id':fxyy_id,
				'bqid':bqid
			},
			url:ctx+"tykf/request?tld=YyfwBqglService_removeYYbq",//路径
			success : function(result) {//返回数据根据结果进行相应的处理
				
 				if(result.success==1){
					$(".bq#"+bqid).remove();
				}
				
			},
			error:function(e){
				
			} 
	    });
		
	}else{
		
		$(".bq#"+bqid).remove();
		
	}
	
	
}





