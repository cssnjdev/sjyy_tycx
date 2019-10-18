function loadCxtj(cxtj,swryDm,cxdy,extraParams){
	 
	$("#jb_tj").empty();
	 
	if(cxtj.length<1){
		
		$("#cxtj_panel").hide();
		
		return false;
	}
	
	for (var i=0;i<cxtj.length;i++) {	
		
		var tj = cxtj[i]	;
 		var inptd = get(tj) ;
 		//处理级联列				
		if(tj.sjtjl){
			var parentArray = tj.sjtjl.split("&");
			for(var j=0;j<parentArray.length;j++){
				var parentName = parentArray[j];
				var tjObj=document.getElementById("tj_"+parentName);
				tjObj.setAttribute("onchange", "javascript:setJlValue('"+parentName+"','"+tj.lmc+"','"+tj.uuid+"')");
			}					
		}
 		var widthLen='120';
		if(i%5==0){
			widthLen='100';
		} 
		//隐藏条件
		var isyc="";
		if(tj.tjxylx=='2'){
			isyc=" display:none;";
		}
		var table = 
		 ' <table  style="float:left; height:auto;'+isyc+'" cellpadding="0" cellspacing="0"  ><tbody> '+
	     ' <tr>  					 ' +
	     '   <td width='+widthLen+' align="center" > '+tj.tjmc+'</td>  ' +
	     '   <td  width="200"   >	 ' +
 	     		inptd  				   +
 	     '   </td>					 ' +
	     '  </tr>			         ' +
	     ' </tbody> 				 ' +
	     '</table>		 	 		 ' ;

		 $("#jb_tj").append(table) ;		
  	}										
	initDate();
	initZtree(cxtj); 	
	loadQueryParams(extraParams);//加载保存的查询条件
	function loadQueryParams(extraParams){
		var values = {};
		var cookieId = document.location.host + swryDm + sqlxh,
		cookieData = getCookieData(cookieId) || [];
		cookieData=eval(cookieData);
		
		for(var i=0;i<cookieData.length;i++){
			
			$("#tj_"+cookieData[i].name).val(cookieData[i].value);
		}
		
	 if(extraParams!=null&&extraParams!=''){
		 
	   var extra_value=eval(extraParams);
	   for(var i=0;i<extra_value.length;i++){
		 var tj_class=$("#tj_"+extra_value[i].name).attr("class");
		 if(tj_class=="combo_real "){
		   var treeObj=$.fn.zTree.getZTreeObj('combo_' + extra_value[i].name + '_content')
		   treeObj.checkAllNodes(false);
		   var valueArr=extra_value[i].value.split(",")
		   var nameArr=new Array();
		   for(var j=0;j<valueArr.length;j++){
		   var node=treeObj.getNodeByParam("id",valueArr[j]);
		   node.checked=true;
		   treeObj.updateNode(node);
		   nameArr.push(node.name);		 
		   }
		   $("#tj_"+extra_value[i].name).val(valueArr.join(",")); 	
		   var combo_box = $("#" + 'combo_' + extra_value[i].name + '_content').closest(".combo_box");
		   $(combo_box).find(".combo_inp").val(nameArr.join(",")).attr("title", nameArr.join(","));	
		 }else{
			   $("#tj_"+extra_value[i].name).val(extra_value[i].value);
		 }
	   }
	 }
	}	 
	// 初始化树								
	function get(tj) { 							
        												
  		
		var mrz = "";						
													
		if (tj.mrz) {								
			mrz = tj.mrz;								
		}										
																								
		var $inp = '<input style="width:200px;" type="text" class="form-control" data-type="string" name="'	
					+ tj.lmc															
					+ '" id="tj_'	
					+ tj.lmc
					+ '" value="'
					+ mrz
					+ '" uuid="'
					+ tj.uuid +
				   '" />' ;
 
		switch (tj.llx) {
		
			case "VARCHAR2"://字符
				
				$inp =  '<input style="width:200px;" type="text" class="form-control" data-type="string" name="'
						+ tj.lmc
						+ '" id="tj_'
						+ tj.lmc
						+ '" value="'
						+ mrz
						+ '" uuid="' + tj.uuid + '" />' ;
				break;
				
			case "DATE"://日期
	
				$inp =  ' <input class="bootstrapDateUi" style="width:200px;" type="text" data-type="string" placeholder="'
						+ tj.xsgs
						+ '"  date-format="'
						+ tj.xsgs
						+ '"   name="'
						+ tj.lmc
						+ '" id="tj_'
						+ tj.lmc
						+ '" value="'
						+ mrz
						+ '" uuid="' + tj.uuid + '"  >' 
	
				break;
				
			case "NUMBER"://数值
	
				break;
			case "SELECT"://单选
	
				$inp = getCombData(tj);
				break;
				
			case "MULTI": //下拉多选
	
				$inp =  '<div class="input-group combo_box border" id="combo_' + tj.lmc + '" style="display:static;" style="" mouseIn="0" > '
						+ ' 	<input id="tj_' + tj.lmc + '"  data-type="string" name="'+ tj.lmc + '" value="' + mrz + '" class="combo_real " type="hidden" /> '
						+ '     <input class="combo_inp form-control"  value="" id="combo_' + tj.lmc+ '" readonly="readonly" />'
						+ '     <div class="dropdown"></div>'
						+ '     <div class="combo_content ztree " id="combo_' + tj.lmc + '_content"  ></div> ' 
						+'</div> ' ;
	
				break;
				
			case "SINGLETREE"://单选树
				 $inp = ' <div class="input-group combo_box" id="combo_'+tj.lmc+'" style="display:static;" style="" mouseIn="0" > ' +
			 	  ' 	<input id="tj_'+tj.lmc+'"  data-type="string" name="'+tj.lmc+'" value="'+mrz+'" class="combo_real " type="hidden" /> ' +
			 	  '     <input class="combo_inp " value="" id="combo_'+tj.lmc+'" readonly="readonly" />'+
			 	  '     <div class="dropdown"></div>'+
		          '     <div class="combo_content ztree " id="combo_'+tj.lmc+'_content"  >123</div> '+
			      ' </div> ' ;
				break;
				
			case "TREE"://多选树
				
				  $inp = ' <div class="input-group combo_box" id="combo_'+tj.lmc+'" style="display:static;" style="" mouseIn="0" > ' +
			 	  ' 	<input id="tj_'+tj.lmc+'"  data-type="string" name="'+tj.lmc+'" value="'+mrz+'" class="combo_real " type="hidden" /> ' +
			 	  '     <input class="combo_inp " value="" id="combo_'+tj.lmc+'" readonly="readonly" />'+
			 	  '     <div class="dropdown"></div>'+
		          '     <div class="combo_content ztree " id="combo_'+tj.lmc+'_content"  >123</div> '+
			      ' </div> ' ;
//				 $inp = ' <div class="input-group combo_box" id="combo_'+tj.lmc+'" style="display:static;" style="" mouseIn="0" > ' +
//					 	  ' 	<input id="tj_'+tj.lmc+'"  data-type="string" name="'+tj.lmc+'" value="'+mrz+'" class="combo_real " type="hidden" /> ' +
//					 	  '     <input class="combo_inp " value="" id="combo_'+tj.lmc+'" readonly="readonly" />'+
//					 	  '     <div class="dropdown"></div>'+
//				          '     <div class="combo_content ztree " id="combo_'+tj.lmc+'_content"  >123</div> '+
//					      ' </div> ';
				break;
				
			case "ORGTREE"://机关-行政树
				
				$inp =    ' <div class="input-group dropdown " id="combo_' + tj.lmc + '" style="width: 100%;padding-right: 26px;position: relative;"  mouseIn="0" > '
						+ ' 	<input id="tj_' + tj.lmc + '" data-type="string" name="' + tj.lmc + '" value="' + mrz + '" class="combo_real " type="hidden"  /> '
						+ '     <input class="combo_inp form-control" value="" id="combo_' + tj.lmc + '" readonly="readonly" data-toggle="dropdown" /> '
						+ '     <span class="add glyphicon glyphicon-plus "  title="多选" data-toggle="dropdown"> </span> '
						+ '     <div class="combo_content ztree dropdown-menu" id="combo_' + tj.lmc + '_content"  style="width:300px;" ></div> '
						+ ' </div> ' ;
				
				break;
				
			case "ZNJGTREE"://机关-职能树
	
				break;
				
			case "SWSSELECT"://税务所-单选
	
				break;
				
			case "SWSDXSELECT"://税务所-多选
	
				break;
				
			case "SWJGTREE"://机关树
	
				break;
				
			case "HSJGTREE"://核算机关树
	
				break;
				
			case "SERCHSINGTREE"://可过滤-查询树
	
				break;
				
			case "SERCHTREE"://可过滤-查询树
	
				break;
				
		}

		return $inp;

	}
	
	function initDate() {
			 
			$("input.bootstrapDateUi").each(function(){

						var format = $(this).attr("date-format");
						var id=$(this).attr("id");
						if(format=="null"){
							format="Y-m-d";
						}
						//转换控件支持的日期格式
						var curr_time=new Date();
						 $("#"+id).datetimepicker({
								dayOfWeekStart:1,
								lang:'ch',
								timepicker:false,
								format:format,
								formatDate:format
							});

			 });
		}

		function getCombData(tj) {

			var mrz;
			if (tj.mrz != null && tj.mrz != "") {
				mrz = tj.mrz.split('||')[0]
			}
			
//			var $inp = '<select  name="' + tj.lmc + '" id="tj_' + tj.lmc
//					+ '" value="' + mrz + '"  data-type="string" class="form-control" ></select>';
			var $inp = '<select  name="' + tj.lmc + '" id="tj_' + tj.lmc
			+ '" value="' + mrz + '"  data-type="string" class="combo-select-arrow" ></select>';
			 
			$.ajax({ 
				url:ctx+"tykf/request?tld=Tycx002DzcxService_getCombData&a=" 	+ Math.random(),
				async : false,
				data : {
					"uuid" : tj.uuid,
					"zdycs" : tj.zdycs,
					"sjymc" : tj.sjymc,
					"llx" : tj.llx,
					"jsonType" : "01"
				},
				dataType : 'json',
				success : function(data, textStatus, jqXHR) {
					// alert(JSON.stringify(data))
					if(data) {
						$inp='<div class="form-control">';
						$inp =$inp+' <select  name="' + tj.lmc + '" id="tj_' + tj.lmc + '" value="' + mrz + '"  style="width:100%" data-type="string" > \n';
						$inp = $inp + '<option   value="'   + '"  >'  + '</option> \n';
						
						for (var i = 0; i < data.length; i++) {
							var dat = data[i];
							if (dat.DM == mrz) {
								$inp = $inp + '<option value="' + dat.DM + '" selected="selected" >' + dat.MC + '</option> \n';
							} else {
								$inp = $inp + '<option value="' + dat.DM + '"  >' + dat.MC + '</option> \n';
							}
						}
						$inp = $inp + '</select> \n';
						$inp = $inp +'</div>';
					}
				},
				error : function(xhr, textStatus) {

				}
			});

			
 			return $inp;

		}

		function initZtree(cxtj) { //初始树			 
			function resetZtree(treeId) { // 设置input值
 				var treeObj = $.fn.zTree.getZTreeObj(treeId);
				var nodes = treeObj.getCheckedNodes(true);
               // alert(JSON.stringify(nodes))
				var combo_box = $("#" + treeId).closest(".combo_box");

				var nameArr = new Array();
				var idArr = new Array();
				$(nodes).each(function() {
					nameArr.push(this.text);
					idArr.push(this.id);
				});		
				$(combo_box).find(".combo_real").val(idArr.join(","));
				$(combo_box).find(".combo_inp").val(nameArr.join(",")).attr("title", nameArr.join(","));

			}

			var setTing = function(tj) {

				function radioCheck(event, treeId, treeNode) {
					resetZtree(treeId);
				} ;

				function zTreeOnClick(event, treeId, treeNode) {
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					treeObj.checkNode(treeNode);
					resetZtree(treeId);

				} ;

				function filter(treeId, parentNode, childNodes) {
					return childNodes;
				} ;

				var setting = {
					check : {
						chkStyle : 'checkbox'
					},					
					view : {
						showLine : false,
						selectedMulti : false,
						dblClickExpand : false
					},
					key : {
						name : 'text'
					},
					data : {
						simpleData : {
							enable : true,
							idKey : "id",
							pIdKey : "pid",
							rootPId : null
						}
					},
					callback : {
						onCheck : radioCheck,
						onClick : zTreeOnClick
					}

				} ; 

				if (tj.llx == "ORGTREE") {

					setting.check = {
						enable : true,
						chkStyle  : "radio",
						radioType : "all"
					}
					
					setting.async = {
						enable : true,
						url:ctx+"tykf/request?tld=Tycx002DzcxService_getTreeData",
						autoParam : ["id", "id=parentID"],
						otherParam : tj,
						dataFilter : filter 
					}
					

				} else if (tj.llx == "MULTI") {

					setting.check = {
						enable 	 : true,
						chkStyle : "checkbox"
					};

				} else if (tj.llx == "TREE") {  
					
					setting.check = {
						enable 	  : true,
						chkStyle  : "checkbox",
						radioType : "all"
					};
					
					tj.dmsql = '' ;
					setting.async = {
						enable : true,
						url:ctx+"tykf/request?tld=Tycx002DzcxService_getTreeData",
						autoParam : ["id","id=parentID"],
						otherParam : tj,
						dataFilter : filter
					}
					 
				}else if (tj.llx == "SINGLETREE") {  
					
					setting.check = {
						enable 	  : true,
						chkStyle  : "radio",
						radioType : "all"
					};
					
					tj.dmsql = '' ;
					setting.async = {
						enable : true,
						url:ctx+"tykf/request?tld=Tycx002DzcxService_getTreeData",
						autoParam : ["id","id=parentID"],
						otherParam : tj,
						dataFilter : filter
					}
					 
				}

				return setting;

			}

			function getTreeData(tj) {
				
				tj.dmsql = ''		;
				
				var treedata = null ;
				
				$.ajax({
					url   : ctx+"tykf/request?tld=Tycx002DzcxService_getTreeData&a=" + Math.random() ,
					async : false     ,
					type  : 'post' 	  ,
					data  :  tj 	  ,
					dataType : 'json' ,
					success : function(data, textStatus, jqXHR) {
 						if (data) {
 							
 							if(!data.message){
 								treedata = data;
 							}
 							
						}
 						
					},
					error : function(xhr, textStatus) {
						return null;
					}
				});

 				
				return treedata;

			}; 

			function initTree(id, setting, data) {
				$.fn.zTree.init($("#" + id), setting, data);
				resetZtree(id);
			};

			$(cxtj).each(function(index, tj) {

			   switch (tj.llx) {

			 	case "ORGTREE":
			 		
 					var data = getTreeData(tj);

					var setting = new setTing(tj);

					var id = 'combo_' + tj.lmc + '_content';

					if(data!=null){
						initTree(id, setting, data);
					}
					
					break;

				case "MULTI":

					var data = getTreeData(tj);
					var setting = new setTing(tj);
					var id = 'combo_' + tj.lmc + '_content';
					
					if(data!=null){
						initTree(id, setting, data);
					}
					
					break;
					
				case "TREE":

					var data = getTreeData(tj);
					var setting = new setTing(tj);
					var id = 'combo_' + tj.lmc + '_content';
					
					if(data!=null){
						initTree(id, setting, data);
					}
					
					break;
				case "SINGLETREE":

					var data = getTreeData(tj);
					var setting = new setTing(tj);
					var id = 'combo_' + tj.lmc + '_content';
					
					if(data!=null){
						initTree(id, setting, data);
					}
					
					break;
					
				}
			   var combo_box = $("#combo_" + tj.lmc + "_content").closest(".combo_box");
			     $(".dropdown",combo_box).click(function(){
			    	// $("#combo_" + tj.lmc + "_content").toggle();//暂时注释掉，点击弹框闪
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
				   },10000);
				   
				   
			    }
			
			});
			
		 
			
			$(".combo_box .dropdown").bind({
				
				click:function(){ 
			 
				   $(this).parent().toggleClass("isup");
				   $(this).parent().find(".combo_content").slideToggle();
				   
			    }
			
			
			});
		 
			
			$(document).mousedown(function(){
				 
				$(".combo_box").each(function(){  
					if($(this).attr("mouseIn")=="0"){
				    	$(this).find(".combo_content").eq(0).slideUp();
				    	$(this).removeClass("isup");
					} 
				});
			
			});
			
			
			$(".combo_inp").bind({
				
				click:function(){ 
				   $(this).parent().find(".combo_content").eq(0).slideDown(); 
				   $(this).parent().addClass("isup");
				},
				 
			});

	   }
	
}

function loadTjCxtj(sqlxh,cxlx){
	var fzlInp="<div class='dowebok'> ";
	fzlInp =fzlInp+' <select  name="fzl" id="tj_fzxm" class="multiselect" multiple="multiple"> \n';
	//fzlInp = fzlInp + '<option   value="'   + '"  >'  + '</option> \n';
	var zblInp="<div class='dowebok'> ";
	zblInp =zblInp+' <select  name="zbl" id="tj_zbxm"  class="multiselect" multiple="multiple" > \n';
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=Tycx002DzcxService_getCxjgl",//路径
		data:{
			'sqlxh':sqlxh			
		},
		success : function(result) {//返回数据根据结果进行相应的处理			
			for(var i=0;i<result.length;i++){
				var cxjg=result[i].pojo;
				// var tdParam=cxjg.trParams.pojo;		
				if(cxjg.llx=='VARCHAR2'){
					if((cxlx=='3'&&cxjg.jcbzdlx!='1')||cxlx=='2'){
						fzlInp = fzlInp + '<option value="' + cxjg.lmc + '"  selected>' + cxjg.lms + '</option> \n';
					}
				}else if(cxjg.llx=="NUMBER"){
					zblInp = zblInp + '<option value="' + cxjg.lmc + '" selected >' + cxjg.lms + '</option> \n';
				}
			}
			fzlInp=fzlInp+"</select> \n";
			fzlInp=fzlInp+"</div>";
			zblInp = zblInp + '</select> \n';
			zblInp = zblInp +'</div>';
		}  
	});
	var widthLen='100';
//	if(i%5==0){
//		widthLen='100';
//	} 
	var table = 
	 ' <table  style="float:left; height:auto;" cellpadding="0" cellspacing="0"   ><tbody> '+
     ' <tr>  					 ' +
     '   <td width='+widthLen+' align="center" > '+'分组列'+'</td>  ' +
     '   <td  width="179"   >	 ' +
      fzlInp  				   +
	     '   </td>					 ' +
     '  </tr>			         ' +
     ' </tbody> 				 ' +
     '</table>		 	 		 ' ;
	var zbl_table = 
		 ' <table  style="float:left; height:auto;" cellpadding="0" cellspacing="0"   ><tbody> '+
	     ' <tr>  					 ' +
	     '   <td width='+widthLen+' align="center" > '+'指标列'+'</td>  ' +
	     '   <td  width="179"   >	 ' +
	     zblInp  				   +
		     '   </td>					 ' +
	     '  </tr>			         ' +
	     ' </tbody> 				 ' +
	     '</table>		 	 		 ' ;
	 $("#tjcx_tj").append(table) ;		
	 $("#tjcx_tj").append(zbl_table) ;
	 $("#tjcxtj").show();
}
 
function getCxtjParams(cxtj){
	
	var cxtjParams = new Array();
	for(var i=0;i<cxtj.length;i++){
		var tj = cxtj[i];
		var obj = {
			"name":tj.lmc,
			"type":$("#tj_"+tj.lmc).attr("data-type"),
			"value":$("#tj_"+tj.lmc).val() 
		} ;
		cxtjParams.push(obj);
	} 		 
	if(cxtj.djxh){
		var obj = {
				"name":'DJXH',
				"type":'string',
				"value":djxh 
			} ;
		cxtjParams.push(obj);
	}
	return cxtjParams;	
}

//校验查询条件是否必填
function validateQueryParams(cxtj){
	var vali_bj=true;
	for(var i=0;i<cxtj.length;i++){
		var tj = cxtj[i];
		var tjxylx=tj.tjxylx;
		if(tjxylx=='1'){//必选条件
			var value=$("#tj_"+tj.lmc).val();
			if(value==null||value==''){
				vali_bj=false;
				return vali_bj;
			}
		}
	} 
	return vali_bj;
}
var parentParams = {};
function setJlValue(sjl,tj,tjuuid){
	parentParams[sjl] = $("#tj_"+sjl).val();
	$("#tj_"+tj).empty();
	reloadComb(tj,tjuuid);
};
function reloadComb(tj,tjuuid){

	$.ajax({
	      url:ctx+"tykf/request?tld=Tycx002DzcxService_getCombData&a="+Math.random(),
	      async:false,
	      data:{
	      	"uuid":tjuuid,
	      	"sjymc":cxdy.sjymc,
	      	"jsonType":"01",
	      	
	      	"parentParams":JSON.stringify(parentParams)
	      },
	      dataType:'json',
	      success:function(data,textStatus,jqXHR){  
	    	 // alert(JSON.stringify(data))
	      	  if(data){	     
	      		$("#tj_"+tj).append('<option value="'+'"  >'+'</option>');	
				for (var i = 0; i < data.length; i++) {
					var dat = data[i];
					$("#tj_"+tj).append('<option value="'+dat.DM+'"  >'+dat.MC+'</option>');	
					
				}

	      	  } 
	      	$("#tj_"+tj).comboSelect();
	      },
		  error:function(xhr,textStatus){
		    
		  }
	 });
	
}
function getCookieData(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return decodeURI(unescape(arr[2]));
	else
		return null;
}
