var cxtjTable=null;
var cxjgTable=null;
var form=null;
var cxtjdmsql=null;
$(document).ready(function(){
	LayOutBody();
	var jgl=$("#cxlx").val();
	displayJgl(jgl);
	var sqlxh=$("#sqlxh").val();
	if(sqlxh!=null){
		search_cxjgTable(sqlxh);
		search_cxtjTable(sqlxh);
		search_cxTxpzxxTable(sqlxh);
	}
	 $("#addTxpz").attr("onclick","");
	 $("#zdyjgl").attr("onclick","");
	$("#jgTab_1 li").each(function(i){
		$(this).click(function(){
			form=i;
			if(form=='1'){
					$("#cshjgl").attr("disabled",false);
				 	$("#zdyjgl").attr("disabled",false);
				 	$("#zdyjgl").attr("onclick","addZdyCxJgl()");
				 	$("#cshtjl").attr("disabled",true);
				 	$("#baocun").attr("disabled",true);
				 	$("#baocun").attr("onclick","");
				 	$("#addTxpz").attr("disabled",true);
				 	$("#addTxpz").attr("onclick","");
					$("#deleteA").attr("disabled", true);
					$("#deleteA").attr("onclick", "");
				}else if(form=='0'){
					$("#cshjgl").attr("disabled",true);
					$("#zdyjgl").attr("disabled",true);
					$("#zdyjgl").attr("onclick","");
					$("#cshtjl").attr("disabled",true);
					$("#baocun").attr("disabled",false);
					$("#baocun").attr("onclick","saveOrUpdate()");
					$("#addTxpz").attr("disabled",true);
					$("#addTxpz").attr("onclick","");
					$("#deleteA").attr("disabled", true);
					$("#deleteA").attr("onclick", "");
				}else if(form=='2'){
					$("#cshjgl").attr("disabled",true);
					$("#zdyjgl").attr("disabled",true);
					$("#zdyjgl").attr("onclick","");
					$("#cshtjl").attr("disabled",false);
					$("#baocun").attr("disabled",true);
					$("#baocun").attr("onclick","");
					$("#addTxpz").attr("disabled",true);
					$("#addTxpz").attr("onclick","");
					$("#deleteA").attr("disabled", true);
					$("#deleteA").attr("onclick", "");
				}else if(form=='3') {
					$("#cshjgl").attr("disabled", true);
					$("#zdyjgl").attr("disabled", true);
					$("#zdyjgl").attr("onclick", "");
					$("#cshtjl").attr("disabled", true);
					$("#baocun").attr("disabled", true);
					$("#baocun").attr("onclick", "");
					$("#addTxpz").attr("disabled", false);
					$("#addTxpz").attr("onclick", "newAddTxpz()")
					$("#deleteA").attr("disabled", false);
					$("#deleteA").attr("onclick", "deleteA()");
			}
				// }else if(form=='4'){
				// 	$("#cshjgl").attr("disabled",true);
				// 	$("#zdyjgl").attr("disabled",true);
				// 	$("#zdyjgl").attr("onclick","");
				// 	$("#cshtjl").attr("disabled",true);
				// 	$("#baocun").attr("disabled",true);
				// 	$("#baocun").attr("onclick","");
				// 	$("#addTxpz").attr("disabled",true);
				// 	$("#addTxpz").attr("onclick","");
				// }
		})
	});
	$(":radio[name='jgllx']").click(function(){
		var jgllx=$(this).val();
		var cxlx=$("#cxlx").val();//取查询类型
			if(jgllx=='DATE'){
				$("#DateDiv").css("display","none");
				$("#NumberDiv").css("display","none");
				$("#jcbzdlxDiv").css("display","none");
				$("#zsfsDiv").css("display","none");	
			}else if(jgllx=='NUMBER'){
				$("#NumberDiv").css("display","none");
				$("#DateDiv").css("display","none");
				$("#jcbzdlxDiv").css("display","none");
				if(cxlx=='3'){
					$("#zsfsDiv").css("display","block");	
					}
			}else{
				$("#DateDiv").css("display","none");
				$("#NumberDiv").css("display","none");
				$("#zsfsDiv").css("display","none");	
				if(cxlx=='3'){
					$("#jcbzdlxDiv").css("display","block");
				}
			}
	});
	$(":radio[name='tjllx']").click(function(){
		var llx=$(this).val();
			if(llx=='DATE'){
				$("#cxtjRqMrzDiv").css("display","block");
				$("#cxtjVarMrzDiv").css("display","none");
			}else if(llx=='VARCHAR2'){				
				$("#cxtjVarMrzDiv").css("display","block");
				$("#cxtjRqMrzDiv").css("display","none");				
			}else{
				$("#cxtjVarMrzDiv").css("display","none");
				$("#cxtjRqMrzDiv").css("display","none");
			}
			if(llx=='DATE'||llx=='VARCHAR2'||llx=='NUMBER'){
			    $("#cxtjdmsql").val("");
				$("#cxtjdmsql").attr("disabled",true);
				$("#sjtjl").attr("disabled",true);
				$("#zdycs").attr("disabled",true);
			}else{
				if(llx=='SINGLETREE'||llx=='TREE'){
					$("#sjtjl").attr("disabled",true);
					$("#cxtjdmsql").attr("disabled",false);
					$("#cxtjdmsql").val(cxtjdmsql);
					$("#zdycs").attr("disabled",false);
				}else{
				$("#cxtjdmsql").attr("disabled",false);
				$("#zdycs").attr("disabled",false);
				$("#sjtjl").attr("disabled",false);
				$("#cxtjdmsql").val(cxtjdmsql);
				}
			}
	});
//	$(":select[id='jcbzdlx']").click(function(){
//		var jcbzdlx=$(this).val();//查询kzlx
//		if(jcbzdlx=='0'){
//			$("#kzlx").attr("disabled",true);
//		}else{
//			$("#kzlx").attr("disabled",false);
//		}
//	});
	 
	initYyfw();
});

function search_cxtjTable(sqlxh){

	var data={'sqlxh':sqlxh};
	 cxtjTable = $("#cxtjl").fyTable(ctx+"tykf/request?tld=tycx001CxCxtjdyService_selectBySqlxh&a=" + Math.random(),data);
	//cxtjTable.reload(data);
}

function search_cxTxpzxxTable(sqlxh){
	var data={'sqlxh':sqlxh};
	cxtxpzTable = $("#txpzxx").fyTable(ctx+"tykf/request?tld=tycx003CxTxpzxxService_select&a=" + Math.random(),data);
}
function saveOrUpdate(){
	var updata = $("#cxdyForm").cssnjGetFormData();
	var sqlxh = updata.sqlxh;
	var ywy = updata.ywy;
	if(ywy==""||ywy==undefined||ywy==null){
		$.cssnj.alert("业务分类不能为空!");
		return;
	}

 	$.ajax({  
		type 	: "POST",  //提交方式  
		dataType: 'json',
		async	: false,
		url  	: ctx+"tykf/request?tld=tycx001CxCxdyService_insertSelective",//路径
		data 	: updata,
		success : function(result)   {//返回数据根据结果进行相应的处理		
			if(result.success="1"){
				if(!sqlxh){
				   window.location.href=ctx+"tykf/request_http?tld=tycx001CxCxdyService_initNew&sqlxh="+result.sqlxh;
				}else{
					$("#sqlxh").val(result.sqlxh); 							
					window.parent.search_dataTable();					
					$.cssnj.alert("保存成功!");	
				}
			}else{
				$.cssnj.alert("保存失败!");	
			}
		},								
		error:function(e){								
													
		} 											
 	});
}

function initResult(){
 	var updata = $("#cxdyForm").cssnjGetFormData();
 	//var sqlxh=updata.sqlxh;
	var sqlxh=$("#sqlxh").val();
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx001CxCxdyService_initResult",//路径
		data:{'sqlxh':sqlxh},
		success : function(result) {//返回数据根据结果进行相应的处理		
			search_cxjgTable(sqlxh);
		},
		error:function(e){
			
		} 
 });
	
	
}
function search_cxjgTable(sqlxh){
	var data={'sqlxh':sqlxh};
	//$("#cxjgl").dataTable().fnDestroy();
	var cxjgTable = $("#cxjgl").fyTable(ctx+"tykf/request?tld=tycx001CxCxjgdyService_selectBySqlxh&a=" + Math.random(),data);

}
function openCxjglMx(rowData,rowEl,el,colparam){
	//重置表单
	document.getElementById("cxjgform1").reset();
	var sqlxh=$('#sqlxh').val();
	$('#lmc').removeAttr("readonly");
	$('#dmsql').attr("readonly");
	$('#zsfs').removeAttr("readonly");
	$('#jcbzdlx').removeAttr("readonly");
	$('#kzlx').removeAttr("readonly");
	$('#url').removeAttr("readonly");
	$('#xzcs').removeAttr("readonly");
	$('#yjfw').removeAttr("readonly");
	$('#color').removeAttr("readonly");
	$('#jskj').removeAttr("readonly");
	$('#ywkj').removeAttr("readonly");
	$('#xsxh').removeAttr("readonly");
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx001CxCxjgdyService_selectByPKey",//路径
		data:{'uuid':rowData.uuid,'sqlxh':sqlxh},
		success : function(result) {//返回数据根据结果进行相应的处理
			var llx=result.cxjgPojo.llx;
			var dqfs=result.cxjgPojo.dqfs;
			var tjlx=result.cxjgPojo.tjlx;
			
			var ycbj=result.cxjgPojo.ycbj;
			var sdbj=result.cxjgPojo.sdbj;
			var cxlx=$("#cxlx").val();//取查询类型
//			var zdm=result.zdmList;
			if(llx=='DATE'){
				$("#DateDiv").css("display","none");
				$("#NumberDiv").css("display","none");
				$("#jcbzdlxDiv").css("display","none");
				$("#zsfsDiv").css("display","none");	
			}else if(llx=='NUMBER'){
				$("#NumberDiv").css("display","none");
				$("#DateDiv").css("display","none");
				$("#jcbzdlxDiv").css("display","none");
				if(cxlx=='3'){
				$("#zsfsDiv").css("display","block");	
				}
			}else{
				$("#DateDiv").css("display","none");
				$("#NumberDiv").css("display","none");
				$("#zsfsDiv").css("display","none");
				if(cxlx=='3'){
					$("#jcbzdlxDiv").css("display","block");	
					var jcbzdlx=$("#jcbzdlx").val();//查询kzlx
					if(jcbzdlx=='0'){
						$("#kzlx").attr("disabled","disabled");
					}else{
						$("#kzlx").removeAttr("disabled");
					}
				}
			}
			
			$("input[type='radio'][name='jgllx'][value='"+llx+"']").attr('checked','checked');
			$("input[type='radio'][name='dqfs2'][value='"+dqfs+"']").attr('checked','checked');
			$("input[type='radio'][name='tjlx2'][value='"+tjlx+"']").attr('checked','checked');
			$("input[type='radio'][name='ycbj2'][value='"+ycbj+"']").attr('checked','checked');			//$("input:radio[name=sdbj7]")
			if(sdbj=="0"){
				$("input[type='radio'][name='sdbj7'][value='1']").removeAttr("checked");
				$("input[type='radio'][name='sdbj7'][value='"+sdbj+"']").attr("checked",'checked');			 
			}else{
				$("input[type='radio'][name='sdbj7'][value='0']").removeAttr("checked");
				$("input[type='radio'][name='sdbj7'][value='"+sdbj+"']").attr("checked",'checked');		
			}
			$("#cxjgform1").cssnjSetFormAsJson(result.cxjgPojo);
			$('#xzcs').multiselect('refresh');
			$('#xzcs').multiselect({
				nonSelectedText:'--请选择--',
				buttonWidth:'200px',
				maxHeight:200,
				numberDisplayed:30,
				selectedList:30,
				includeSelectAllOption:true
			});

			$('#lmc').attr("readonly","readonly");
			$('#saveBtn').attr("onclick","saveCxjgl();");
			$("#cxjgForm").modal("show");
		},
		error:function(e){
			
		} 
 });
	
}
function openCxtjlMx(rowData,rowEl,el,colparam){
	//重置表单
	document.getElementById("cxtjform1").reset();
		$.ajax({
			type : "POST",  //提交方式  
			dataType:'json',
			async:false,
			url : ctx+"tykf/request?tld=tycx001CxCxtjdyService_selectByPKey",//路径
			data:{'uuid':rowData.uuid},
			success : function(result) {//返回数据根据结果进行相应的处理	
				var llx=result.llx;
				var tjxylx=result.tjxylx;
				var ycbj=result.ycbj;
				cxtjdmsql=result.dmsql;
				if(llx=='DATE'){
					$("#cxtjRqMrzDiv").css("display","block");
					$("#cxtjVarMrzDiv").css("display","none");
				}else if(llx=='VARCHAR2'){
					$("#cxtjVarMrzDiv").css("display","block");
					$("#cxtjRqMrzDiv").css("display","none");
				}else{
					$("#cxtjVarMrzDiv").css("display","none");
					$("#cxtjRqMrzDiv").css("display","none");
				}
				if(llx=='DATE'||llx=='VARCHAR2'||llx=='NUMBER'){
					$("#sjtjl").attr("disabled",true);
					$("#cxtjdmsql").val("");
					$("#cxtjdmsql").attr("disabled",true);
					$("#zdycs").attr("disabled",true);
				}else{
					if(llx=='SINGLETREE'||llx=='TREE'||llx=='SELECT'||llx=='MULTI'){
						$("#sjtjl").attr("disabled",true);
						$("#cxtjdmsql").attr("disabled",false);
						$("#zdycs").attr("disabled",false);
						$("#cxtjdmsql").val(cxtjdmsql);
					}else{
					$("#sjtjl").attr("disabled",false);
					$("#cxtjdmsql").attr("disabled",false);
					$("#zdycs").attr("disabled",false);
					}
				}
				$("input[type='radio'][name='tjllx'][value='"+llx+"']").attr('checked','checked');
				$("input[type='radio'][name='tjxylx2'][value='"+tjxylx+"']").attr('checked','checked');
				$("input[type='radio'][name='ycbj'][value='"+ycbj+"']").attr('checked','checked');
			  $("#cxtjform1").cssnjSetFormAsJson(result) ;
			  $("#cxtjForm").modal("show");
				// saveCxtjl();
			},
			error:function(e){
				
			} 
	 });
	  
}
function saveCxjgl(){
	var updata = $("#cxjgform1").cssnjGetFormData();
	updata.dqfs=updata.dqfs2;
	updata.llx=updata.jgllx;
	updata.tjlx=updata.tjlx2;
	updata.ycbj=updata.ycbj2;
	updata.sdbj=updata.sdbj7;
 	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx001CxCxjgdyService_updateByPKeySelective",//路径
		data:updata,
		success : function(result) {//返回数据根据结果进行相应的处理
 		        search_cxjgTable(result.sqlxh);
				 $.cssnj.alert(result.mess);			
			 
		},
		error:function(e){
			
		} 
 });
}
function saveAddCxjgl(){
	var updata = $("#cxjgform1").cssnjGetFormData();
	var sqlxh=$('#sqlxh').val();
//	var xh=$('#xsxh>input:last').val();
	updata.dqfs=updata.dqfs2;
	updata.llx=updata.jgllx;
	updata.tjlx=updata.tjlx2;
 	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx001CxCxjgdyService_saveAddCxjgl&sqlxh="+sqlxh,//路径
		data:updata,
		success : function(result) {//返回数据根据结果进行相应的处理
			search_cxjgTable(result.sqlxh);
			$.cssnj.alert(result.mess);			
			$('#cxjgForm').modal("hide");
			 
		},
		error:function(e){
			
		} 
 });
}
//打开新增图形配置信息
function newAddTxpz(){
	$("#title").val("");
	$("#title").attr("readonly",false);
	$("#xztxpzxx").text("新增图形配置信息");
	$("#uuid").val("");
	$("#txlx").val("");
	$("#hzb").val("");
	$("#hzbdw").val("");
	$("#hzbmc").val("");
	$("#zzb").val("");
	$("#zzbdw").val("");
	$("#zzbmc").val("");
	$("#x").val("");
	$("#y").val("");
	$("#x2").val("");
	$("#y2").val("");
	$("#xqxds").val("");
	$("#yqxds").val("");
	$("#fontsize").val("");
	$("#ccgcmc").val("");
	$("#sql").val("");
	$("#txpzxxForm").modal("show");
}
function selectModifyOrSave(){
	var uuid=$("#uuid").val();
	if(uuid!=''){
		modifyTxpzxx();
	}else{
		saveAddTxpzxx();
	}
}
//修改图形配置信息
function modifyTxpzxx(){
	var updata = $("#txpzxxxx").cssnjGetFormData();
	var sqlxh=$('#sqlxh').val();
 	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx003CxTxpzxxService_updateByPKeySelective",//路径
		data:updata,
		success : function(result) {//返回数据根据结果进行相应的处理
			$.cssnj.alert(result.message);	
			search_cxTxpzxxTable(sqlxh);
		},
		error:function(e){
			
		} 
 });
}
//删除
function deleteA(){

	if(cxtxpzTable!=null){
		var checkedRow = cxtxpzTable.getCheckedData();

		var sqlxhList = new Array();
		var uuid = new Array();
		for(var i = 0;i<checkedRow.length;i++){

			var row = checkedRow[i];
			uuid.push(row["uuid"])
			sqlxhList.push(row["sqlxh"]);

		}

	}
	if(sqlxhList.length==0){
		$.cssnj.alert("请选择一行!");
		return;
	}
	$.cssnj.messager("确定删除","确定删除？",{
		"onOk":function(){

			$.ajax({
				url: ctx+"tykf/request?tld=tycx001CxCxdyService_deleteByPKeySelective&uuid="+uuid,    //请求的url地址
				data: { "sqlxh":sqlxhList.join(",")},    //参数值
				dataType: "json",   //返回格式为json
				async:false,
				type: "POST",   //请求方式
				success: function(data){

					if(data.state == "1"){

						$.cssnj.alert("删除成功！");
						cxtxpzTable.reload();
						//查询所有图形配置
					}

					if(data.state == "0"){

						$.cssnj.alert("删除失败！");

					}

				}
			});


		}


	});
}
//新增图形配置信息
function saveAddTxpzxx(){
	var updata = $("#txpzxxxx").cssnjGetFormData();
	$("#title").attr("readonly",false);
	var sqlxh=$('#sqlxh').val();
 	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx003CxTxpzxxService_insertSelective",//路径
		data:updata,
		success : function(result) {//返回数据根据结果进行相应的处理
			$.cssnj.alert(result.message);	
			search_cxTxpzxxTable(sqlxh);
		},
		error:function(e){
			
		} 
 });
}
function saveCxtjl(){
	var updata = $("#cxtjform1").cssnjGetFormData();
	updata.llx=updata.tjllx;
	updata.dmsql=updata.cxtjdmsql;
	updata.tjxylx=updata.tjxylx2;	
	if(updata.tjllx=='DATE'){
		updata.mrz=updata.mrz1;
	}else if(updata.tjllx=='VARCHAR2'){
		updata.mrz=updata.mrz2;
	}
 	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx001CxCxtjdyService_updateByPKeySelective",//路径
		data:updata,
		success : function(result) {//返回数据根据结果进行相应的处理	
 		         var sqlxh=updata.sqlxh;
 		        search_cxtjTable(sqlxh);
 		        // window.parent.search_cxtjTable(sqlxh);
				 $.cssnj.alert(result.message);
			 
		},
		error:function(e){
			
		} 
 });
}
function initCxtjl(){
	var updata = $("#cxdyForm").cssnjGetFormData();
	var sqlxh=$("#sqlxh").val();
 	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx001CxCxdyService_initCxtj",//路径
		data:{'sqlxh':sqlxh},
		success : function(result) {//返回数据根据结果进行相应的处理		
			search_cxtjTable(sqlxh);
		},
		error:function(e){
			
		} 
 });
}
function view(){
	var sqlxh=$("#sqlxh").val();
	if(sqlxh==''||sqlxh==null){
		$.cssnj.alert("请先保存查询定义信息！");
		return;
	}
	 var cxjgl = document.getElementById("cxjgl").rows.length
	if(cxjgl<2){
		$.cssnj.alert("请先初始化或定义查询结果!");
		return;
	}
	var host = window.location.host;
	var url="http://" + host + ctx+"tykf/request_http?tld=Tycx002DzcxService_initView&sqlxh="+sqlxh;
	window.open(url);
}
function addTxpz(rowData,rowEl,el,colparam){
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx003CxTxpzxxService_selectByKey",//路径
		data:{'uuid':rowData.uuid},
		success : function(result) {//返回数据根据结果进行相应的处理	
			$("#txpzxxForm").cssnjSetFormAsJson(result) ;
			$("#xztxpzxx").text("图形配置信息");
			$("#uuid").val(result.uuid);
			$("#txpzxxForm").modal("show");
		},
		error:function(e){
			
		} 
 });
	
}



function addZdyCxJgl(){
	$('#xzcs').multiselect({
		buttonWidth:'200px',
		maxHeight:200,
		numberDisplayed:30,
		selectedList:30
	});
	$('#cxjgform1').get(0).reset();
	$('#lmc').attr("readonly",false);
	$('#dmsql').attr("readonly","readonly");
	$('#zsfs').attr("readonly","readonly");
	$('#jcbzdlx').attr("readonly","readonly");
	$('#kzlx').attr("readonly","readonly");
	$('#url').attr("readonly","readonly");
	$('#xzcs').attr("readonly","readonly");
	$('#yjfw').attr("readonly","readonly");
	$('#color').attr("readonly","readonly");
	$('#jskj').attr("readonly","readonly");
	$('#ywkj').attr("readonly","readonly");
	$('#xsxh').attr("readonly","readonly");
	$('#saveBtn').attr("onclick","saveAddCxjgl();");
	$('#cxjgForm').modal("show");
}

//交叉表字段类型--->扩展方式
function changeKzlx(){
	var val=$('#jcbzdlx').val();
	if(val == '1'){
		$('#kzlx').attr("readonly",false);
	}else{
		$('#kzlx').val("");
		$('#kzlx').attr("readonly","readonly");
	}
}
function displayJgl(value){
	if(value==3||value==4){
    $("#jglsz").hide();
    $("#jglsz1").hide();
    $("#jglsz").prop("checked",false);
	}else{
    $("#jglsz").show();
    $("#jglsz1").show();
    $("#jglsz").prop("checked",true);
	}
}
function checkSqlAndBbmc(){
	var sqlxh=$("#sqlxh").val(); 
	var sqlstr=$("#sqlstr").val();
	var sjylx=$("#sjylx").val();
	var sqlxh=$("#sqlxh").val();
	var cxlx=$("#cxlx").val();
	if(cxlx==4){
		var str=$("#sqlstr").val();
		var substr=str.substr(str.lastIndexOf("."));
		if(substr==".rpx"){
			$.cssnj.alert("验证成功");
		}else{
			$.cssnj.alert("验证失败");
		}
	}else{
		$.ajax({  
			type : "POST",  //提交方式  
			dataType:'json',
			async:false,
			url : ctx+"tykf/request?tld=tycx001CxCxdyService_checkSqlAndBbmc",//路径
			data:{'sqlstr':sqlstr,'sjylx':sjylx,'sqlxh':sqlxh},
			success : function(data) {//返回数据根据结果进行相应的处理
				$.cssnj.alert(data);
			},
			error:function(e){
				$.cssnj.alert("验证失败");
			} 
	      });
		}
}
function checkNumber(val){
	if(isNaN(val)){
		$.cssnj.alert("请输入数字!");
	}
}



function initYyfw(){
 
	var sqlxh=$("#sqlxh").val();
 	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx001CxYyfwService_getCxYyfw",//路径
		data:{'sqlxh':sqlxh},
		success:function(data) { 
 
			if(data){
			  openYyfw(data.FXYY_ID); 
		   }else{
			   $("#recent4").html(
				  ' <span class="p_10p" > \n'+
				  '  <a href="javascript:void(0);" class="btn btn-sm btn-primary no-radius " onclick="fbyy(\''+sqlxh+'\')"> \n'+
	              '	  <span class="glyphicon glyphicon-back"></span>发布新应用  \n'+
	              '  </a> \n'+
	              ' </span> \n'
               );
		    }
		  
		 },error:function(e){
			$.cssnj.alert("访问后台异常");
		 } 
    });

}

function openYyfw(fxyy_id){
	
 	$("#recent4").html(
	  "<iframe src='/tykf/request_http?tld=YyfbService_initNew&fxyy_id="+fxyy_id+"' width='100%' height='100%' style='border:0;'/>"
	)
}


function fbyy(sqlxh){
 
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url : ctx+"tykf/request?tld=tycx001CxYyfwService_insertCxYy",//路径
		data:{'sqlxh':sqlxh},
		success : function(data) {//返回数据根据结果进行相应的处理
			if(data.success==1){
				if(data.fxyy_id){
					openYyfw(data.fxyy_id);
				}else{
					$.cssnj.alert("访问后台异常");
				}
			}else{
				
				
			}
		},
		error:function(e){
			$.cssnj.alert("访问后台异常");
		} 
	});
	
}





