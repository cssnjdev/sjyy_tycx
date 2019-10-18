var cxtj_sj;
var cxdy_sj;
var swryDm_sj;
function loadCxtj(cxtj,swryDm,cxdy){
//123
	this.cxtj_sj = cxtj;
	this.cxdy_sj = cxdy;
	this.swry_dm_sj = swryDm;
	$("#jb_tj").empty();
	$("#show_more_tj").empty();
	$("#show_more_tj").hide();
			
	if(cxtj.length<1){
		$("#cxtj_panel").hide();
		return false;
	}
 	
	for (var i=0;i<cxtj.length;i++) {	//绘制查询条件
	 
		var tj = cxtj[i]	;
 		var inptd = get(tj) ;
 		//处理级联列
		if(tj.sjtjl){
			var parentArray = tj.sjtjl.split("&");
			for(var j=0;j<parentArray.length;j++){
				var parentName = parentArray[j];
				var tjObj=document.getElementById("tj_"+parentName);
				// tjObj.setAttribute("onchange", "javascript:setJlValue('"+parentName+"','"+tj.sjtjl+"','"+tj.uuid+"')");
			}
		}

		var table = 
		 ' <table class="tj_table"   cellpadding="0" cellspacing="0"  ><tbody> '+
	     ' <tr>  					 ' +
	     '   <td width="100" class="right" > '+tj.tjmc+'</td>  ' +
	     '   <td  width="200"   >	 ' +
 	     		inptd  				   +
 	     '   </td>					 ' +
	     '  </tr>			         ' +
	     ' </tbody> 				 ' +
	     '</table>		 	 		 ' ;
		 
 		 if(tj.tjxylx=='2'){//隐藏条件
		 	 $("#yc_tj").append(table) ;	
		 }else if($("#jb_tj").children("table").length>3){
			 $("#show_more_tj").append(table) ;	
		 }else{
			 $("#jb_tj").append(table) ;	
		 }
		 
  	}		
	
	initDate();
	initZtree(cxtj);
	if($("#jb_tj").children("table").length>0){ //添加查询按钮
		$("#jb_tj").append( '<div id="tjBtn" style=" display: inline-block; height:100%;float:right;"  ></div>') ;
		$("#tjBtn").append('<span style=" display:inline-block;float:left;padding:3px 0 0 10px;"><a class="btn btn-xs btn-info no-radius" id="searchDataBtn"  onclick="queryMx();" style="float:left;padding:3px 8px 3px 8px;" ><i class="glyphicon glyphicon-search"></i>&nbsp;查询</a></span>');
		$("#cxtj_panel").show();
	}else{
		$("#cxtj_panel").hide();
	}
	if($("#show_more_tj").children("table").length>0){ //添加更多查询条件按钮
		$("#tjBtn").append('<span style="display:inline-block;height:32px;line-height:32px;padding-left:10px;"><a style="display:block;" id="showMoreTjBtn" onclick="$(\'#show_more_tj\').slideToggle();$(\'#chevron\').toggleClass(\'glyphicon-chevron-down\').toggleClass(\'glyphicon-chevron-up\');" href="#" style="float:left;" >更多条件 <span id="chevron" class="glyphicon glyphicon-chevron-down"></span></a></span>');
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
	
				$inp =  ' <input class="bootstrapDateUi form-control" style="width:200px;" type="text" data-type="string" placeholder="'
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
	
				$inp =  '<div class="input-group combo_box border" id="combo_' + tj.lmc + '_box" style="display:static;" style="" mouseIn="0"  tj_llx="'+tj.llx+'" > '
						+ ' 	<input id="tj_' + tj.lmc + '"  data-type="string" name="'+ tj.lmc + '" value="' + mrz + '" class="combo_real " type="hidden" /> '
						+ '     <input class="combo_inp form-control "  value="" id="combo_' + tj.lmc+ '" readonly="readonly" />'
						+ '     <div class="dropdown"></div>'
						+ '     <div class="combo_content ztree " style="position:absolute; height:400px;overflow:auto"  id="combo_' + tj.lmc + '_content"  tj_llx="'+tj.llx+'"  ></div> '
						+'</div> ' ;
	
				break;
				
			case "SINGLETREE"://单选树
				 $inp = ' <div class="input-group combo_box" id="combo_'+tj.lmc+'_box" style="display:static;" style="" mouseIn="0" tj_llx="'+tj.llx+'"> ' +
			 	  ' 	<input id="tj_'+tj.lmc+'"  data-type="string" name="'+tj.lmc+'" value="'+mrz+'" class="combo_real " type="hidden" /> ' +
			 	  '     <input class="combo_inp form-control" value="" id="combo_'+tj.lmc+'" readonly="readonly" />'+
			 	  '     <div class="dropdown"></div>'+
		          '     <div class="combo_content ztree " style="position:absolute; height:400px; overflow:auto"  id="combo_'+tj.lmc+'_content" style="height:400px;;"  tj_llx="'+tj.llx+'" ></div> '+
			      ' </div> ' ;
				break;
				
			case "TREE"://多选树
				
				  $inp = ' <div class="input-group combo_box" id="combo_'+tj.lmc+'_box" style="display:static;" style="" mouseIn="0" tj_llx="'+tj.llx+'" > ' +
			 	  ' 	<input id="tj_'+tj.lmc+'"  data-type="string" name="'+tj.lmc+'" value="'+mrz+'" class="combo_real " type="hidden" /> ' +
			 	  '     <input class="combo_inp form-control"  value="" id="combo_'+tj.lmc+'" readonly="readonly" />'+
			 	  '     <div class="dropdown"></div>'+
		          '     <div class="combo_content ztree " style="position:absolute; height:400px; overflow:auto" id="combo_'+tj.lmc+'_content" tj_llx="'+tj.llx+'" ></div> '+
			      ' </div> ' ;
 
				break;
				
			case "ORGTREE"://机关-行政树
				
				$inp =    ' <div class="input-group dropdown " id="combo_' + tj.lmc + '_box" style="width: 100%;padding-right: 26px;position: relative;"  mouseIn="0"  tj_llx="'+tj.llx+'" > '
						+ ' 	<input id="tj_' + tj.lmc + '" data-type="string" name="' + tj.lmc + '" value="' + mrz + '" class="combo_real " type="hidden"  /> '
						+ '     <input class="combo_inp form-control" value="" id="combo_' + tj.lmc + '" readonly="readonly" data-toggle="dropdown" /> '
						+ '     <span class="add glyphicon glyphicon-plus "  title="多选" data-toggle="dropdown"> </span> '
						+ '     <div class="combo_content ztree dropdown-menu" id="combo_' + tj.lmc + '_content"  style="width:300px;" tj_llx="'+tj.llx+'" ></div> '
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
 
			var $inp = '<select  class="form-control"  name="' + tj.lmc + '" id="tj_' + tj.lmc
			+ '" value="' + mrz + '"  data-type="string" class="combo-select-arrow" ></select>';
  			var data = tycx_service.getCombData({
				"uuid" : tj.uuid,
				"zdycs" : tj.zdycs,
				"sjymc" : tj.sjymc,
				"llx" : tj.llx,
				"jsonType": "01",
				"sqlxh" : tj.sqlxh
			});
 			 
 			if(data) {
				
				$inp='<div>';
				$inp =$inp+' <select   class="form-control" name="' + tj.lmc + '" id="tj_' + tj.lmc + '" value="' + mrz + '"  style="width:100%" data-type="string" > \n';
				 
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
			 
			
 			return $inp;

	}

	function initZtree(cxtj) { //初始树		
			
			function resetZtree(treeId) { // 设置input值
				// getTreeData(cxtj);
 				var treeObj = $.fn.zTree.getZTreeObj(treeId);
				var nodes = treeObj.getCheckedNodes(true);
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
					for(var i=0;i<cxtj.length;i++){
						if(cxtj[i].sjtjl==tj.lmc){
							setJlValue(tj,cxtj[i].lmc,tj.uuid)
						}
					}
				} ;

				function zTreeOnClick(event, treeId, treeNode) {
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					treeObj.checkNode(treeNode);

					resetZtree(treeId);
					for(var i=0;i<cxtj.length;i++){
						if(cxtj[i].sjtjl==tj.lmc){
							setJlValue(tj,cxtj[i].lmc,tj.uuid)
						}
					}
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
						radioType : "all",
						chkboxType: { "Y": "","N": ""}
					}
				 
				} else if (tj.llx == "MULTI") {

					setting.check = {
						enable 	 : true,
						chkStyle : "checkbox",
						chkboxType: { "Y": "","N": ""}
					};

				} else if (tj.llx == "TREE") {  
					
					setting.check = {
						enable 	  : true,
						chkStyle  : "checkbox",
						radioType : "all",
						chkboxType: { "Y": "","N": ""}
					};
					 
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
				if(tj.sjtjl){

					var sjtjmcs = $("#tj_"+tj.sjtjl).val();
					var sjtjmc = sjtjmcs.split(",");
					var param = [];
					for(var j=0;j<sjtjmc.length;j++){
						if(sjtjmc[j]==""||sjtjmc[j]==null){
							param.push("{"+tj.sjtjl+":null}");
						}else{
						param.push("{"+tj.sjtjl+":"+sjtjmc[j]+"}");
						}
					}
					tj.parentParams=param;

					// var sjtjl = $("#tj_"+tj.sjtjl).val();
					// tj.parentParams="{"+tj.sjtjl+":"+320115+"}";
					return tycx_service.getTreeData(tj);
				}
				tj.dmsql = '';
				var treedata = tycx_service.getTreeData(tj);
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

					if(data!=null&&data.length!=0){
						initTree(id, setting, data);
					}
					
					break;

				case "MULTI":

					var data = getTreeData(tj);
					if(data==undefined||data.messageCode==-1){
						return;
					}
					var setting = new setTing(tj);
					var id = 'combo_' + tj.lmc + '_content';
					
					if(data!=null&&data.length!=0){
						initTree(id, setting, data);
					}
					
					break;
					
				case "TREE":

					var data = getTreeData(tj);
					var setting = new setTing(tj);
  					
					var id = 'combo_' + tj.lmc + '_content';
					
					if(data!=null&&data.length!=0){
						initTree(id, setting, data);
					}
					
					break;
				case "SINGLETREE":

					var data = getTreeData(tj);
 					
					var setting = new setTing(tj);
 					
					var id = 'combo_' + tj.lmc + '_content';
					
					if(data!=null&&data.length!=0){
						initTree(id, setting, data);
					}
					
					break;
					
				}
			    var combo_box = $("#combo_" + tj.lmc + "_content").closest(".combo_box");
			    
			    $(".dropdown",combo_box).click(function(){
			    	// $("#combo_" + tj.lmc + "_content").toggle();//暂时注释掉，点击弹框闪
			    });
			     
			    //默认选中第一个节点
			     var treeObj = $.fn.zTree.getZTreeObj("combo_" + tj.lmc + "_content");
		         if(treeObj){
		        	   var node= treeObj.getNodes()[0];
		        	   if(node){
		        		   
		        		   treeObj.checkNode(node, true, true);
		        		   var name = node["name"];
		         		   $("#combo_"+ tj.lmc).val(name);
		         		   
		         		   var value = node["id"];
		        		   $("#tj_"+tj.lmc).val(value);

		        	   }
		         }
		         

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
// (cxtj,swryDm,cxdy){
function setJlValue(sjtjl,tj,uuid){
	for (var i=0;i<cxtj.length;i++) {
		if(cxtj[i].lmc==tj){
			var inptd = get(cxtj[i]) ;
			initDate();
			initZtree(cxtj[i]);
			switch (cxtj[i].llx) {
				case "SELECT":

					$("#tj_"+cxtj[i].lmc).append(inptd);
					break
				case "TREE":
					$("#combo_"+cxtj[i].lmc+"_box").innerHTML=inptd;
					break;
				case "MULTI":
					$("#combo_"+cxtj[i].lmc+"_content").innerHTML=inptd;
					break;
				case "SINGLETREE":
					$("#combo_"+cxtj[i].lmc+"_content").innerHTML=inptd;
					break;
				case "MULTI2":

					break;
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

				$inp =  ' <input class="bootstrapDateUi form-control" style="width:200px;" type="text" data-type="string" placeholder="'
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

				// $inp =  '<div class="input-group combo_box border" id="combo_' + tj.lmc + '_box" style="display:static;" style="" mouseIn="0"  tj_llx="'+tj.llx+'" > '
				// 	+ ' 	<input id="tj_' + tj.lmc + '"  data-type="string" name="'+ tj.lmc + '" value="' + mrz + '" class="combo_real " type="hidden" /> '
				// 	+ '     <input class="combo_inp form-control "  value="" id="combo_' + tj.lmc+ '" readonly="readonly" />'
				// 	+ '     <div class="dropdown"></div>'
				$inp= '     <div class="combo_content ztree " style="position:absolute; height:400px;overflow:auto"  id="combo_' + tj.lmc + '_content"  tj_llx="'+tj.llx+'"  ></div> ';
					// +'</div> ' ;

				break;

			case "SINGLETREE"://单选树
				// $inp = ' <div class="input-group combo_box" id="combo_'+tj.lmc+'_box" style="display:static;" style="" mouseIn="0" tj_llx="'+tj.llx+'"> ' +
				// 	' 	<input id="tj_'+tj.lmc+'"  data-type="string" name="'+tj.lmc+'" value="'+mrz+'" class="combo_real " type="hidden" /> ' +
				// 	'     <input class="combo_inp form-control" value="" id="combo_'+tj.lmc+'" readonly="readonly" />'+
				// 	'     <div class="dropdown"></div>'+
				$inp ='     <div class="combo_content ztree " style="position:absolute; height:400px; overflow:auto"  id="combo_'+tj.lmc+'_content" style="height:400px;;"  tj_llx="'+tj.llx+'" ></div> ';
					// ' </div> ' ;
				break;

			case "TREE"://多选树

				$inp = ' <div class="input-group combo_box" id="combo_'+tj.lmc+'_box" style="display:static;" style="" mouseIn="0" tj_llx="'+tj.llx+'" > ' +
					' 	<input id="tj_'+tj.lmc+'"  data-type="string" name="'+tj.lmc+'" value="'+mrz+'" class="combo_real " type="hidden" /> ' +
					'     <input class="combo_inp form-control"  value="" id="combo_'+tj.lmc+'" readonly="readonly" />'+
					'     <div class="dropdown"></div>'+
					'     <div class="combo_content ztree " style="position:absolute; height:400px; overflow:auto" id="combo_'+tj.lmc+'_content" tj_llx="'+tj.llx+'" ></div> '+
					' </div> ' ;

				break;

			case "ORGTREE"://机关-行政树

				$inp =    ' <div class="input-group dropdown " id="combo_' + tj.lmc + '_box" style="width: 100%;padding-right: 26px;position: relative;"  mouseIn="0"  tj_llx="'+tj.llx+'" > '
					+ ' 	<input id="tj_' + tj.lmc + '" data-type="string" name="' + tj.lmc + '" value="' + mrz + '" class="combo_real " type="hidden"  /> '
					+ '     <input class="combo_inp form-control" value="" id="combo_' + tj.lmc + '" readonly="readonly" data-toggle="dropdown" /> '
					+ '     <span class="add glyphicon glyphicon-plus "  title="多选" data-toggle="dropdown"> </span> '
					+ '     <div class="combo_content ztree dropdown-menu" id="combo_' + tj.lmc + '_content"  style="width:300px;" tj_llx="'+tj.llx+'" ></div> '
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
		var sjtjmcs = $("#tj_"+tj.sjtjl).val();
		var sjtjmc = sjtjmcs.split(",");
		var param = [];
		for(var j=0;j<sjtjmc.length;j++){

			param.push("{"+tj.sjtjl+":"+sjtjmc[j]+"}");
		}
		var $inp = '<select  class="form-control"  name="' + tj.lmc + '" id="tj_' + tj.lmc
			+ '" value="' + mrz + '"  data-type="string" class="combo-select-arrow" ></select>';
		var data = tycx_service.getCombData({
			"uuid" : tj.uuid,
			"zdycs" : tj.zdycs,
			"sjymc" : tj.sjymc,
			"llx" : tj.llx,
			"jsonType": "01",
			"sqlxh" : tj.sqlxh,
			"parentParams":param
		});

		if(data) {

			// $inp='<div>';
			$inp='';
			// $inp =$inp+' <select   class="form-control" name="' + tj.lmc + '" id="tj_' + tj.lmc + '" value="' + mrz + '"  style="width:100%" data-type="string" > \n';

			for (var i = 0; i < data.length; i++) {
				var dat = data[i];
				if (dat.DM == mrz) {
					$inp = $inp + '<option value="' + dat.DM + '" selected="selected" >' + dat.MC + '</option> \n';
				} else {
					$inp = $inp + '<option value="' + dat.DM + '"  >' + dat.MC + '</option> \n';
				}
			}
			// $inp = $inp + '</select> \n';
			// $inp = $inp +'</div>';
		}


		return $inp;

	}

	function initZtree(cxtj) { //初始树

		function resetZtree(treeId) { // 设置input值
			// getTreeData(cxtj);
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			var nodes = treeObj.getCheckedNodes(true);
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
					radioType : "all",
					chkboxType: { "Y": "","N": ""}
				}

			} else if (tj.llx == "MULTI") {

				setting.check = {
					enable 	 : true,
					chkStyle : "checkbox",
					chkboxType: { "Y": "","N": ""}
				};

			} else if (tj.llx == "TREE") {

				setting.check = {
					enable 	  : true,
					chkStyle  : "checkbox",
					radioType : "all",
					chkboxType: { "Y": "","N": ""}
				};

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
			if(tj.sjtjl){

				var sjtjmcs = $("#tj_"+tj.sjtjl).val();
				var sjtjmc = sjtjmcs.split(",");
				var param = [];
				for(var j=0;j<sjtjmc.length;j++){
					if(sjtjmc[j]==undefined||sjtjmc[j]==""){
						param.push("{"+tj.sjtjl+":null}");
					}else{
						param.push("{"+tj.sjtjl+":"+sjtjmc[j]+"}");
					}
				}
				tj.parentParams=param;

				// var sjtjl = $("#tj_"+tj.sjtjl).val();
				// tj.parentParams="{"+tj.sjtjl+":"+320115+"}";
				return tycx_service.getTreeData(tj);
			}
			tj.dmsql = '';
			var treedata = tycx_service.getTreeData(tj);
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

					if(data!=null&&data.length!=0){
						initTree(id, setting, data);
					}

					break;

				case "MULTI":

					var data = getTreeData(tj);
					if(data==undefined||data.messageCode==-1){
						return;
					}
					var setting = new setTing(tj);
					var id = 'combo_' + tj.lmc + '_content';

					if(data!=null&&data.length!=0){
						initTree(id, setting, data);
					}

					break;

				case "TREE":

					var data = getTreeData(tj);
					var setting = new setTing(tj);

					var id = 'combo_' + tj.lmc + '_content';

					if(data!=null&&data.length!=0){
						initTree(id, setting, data);
					}

					break;
				case "SINGLETREE":

					var data = getTreeData(tj);

					var setting = new setTing(tj);

					var id = 'combo_' + tj.lmc + '_content';

					if(data!=null&&data.length!=0){
						initTree(id, setting, data);
					}

					break;

			}
			var combo_box = $("#combo_" + tj.lmc + "_content").closest(".combo_box");

			$(".dropdown",combo_box).click(function(){
				// $("#combo_" + tj.lmc + "_content").toggle();//暂时注释掉，点击弹框闪
			});

			//默认选中第一个节点
			var treeObj = $.fn.zTree.getZTreeObj("combo_" + tj.lmc + "_content");
			if(treeObj){
				var node= treeObj.getNodes()[0];
				if(node){

					treeObj.checkNode(node, true, true);
					var name = node["name"];
					$("#combo_"+ tj.lmc).val(name);

					var value = node["id"];
					$("#tj_"+tj.lmc).val(value);

				}
			}


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
				"name" :'DJXH',
				"type" :'string',
				"value":djxh 
		};
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



function reloadComb(tj,tjuuid){
	var data = tycx_service.getCombData({
      	"uuid":tjuuid,
      	"sjymc":cxdy.sjymc,
      	"jsonType":"01",
		"sqlxh":cxdy.sqlxh,
      	"parentParams":JSON.stringify(parentParams)
     });
	
	 if(data){
   		$("#tj_JDXZ").append('<option value="'+'"  >'+'</option>');
			for (var i = 0; i < data.length; i++) {
				var dat = data[i];
				$("#tj_JDXZ").append('<option value="'+dat.DM+'"  >'+dat.MC+'</option>');

			}
   	 }
	 
      $("#tj_JDXZ").comboSelect();
}

function getCookieData(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return decodeURI(unescape(arr[2]));
	else
		return null;
}

function loadQueryParams(queryData){ //加载查询条件
	 
 	   if(!queryData){
 		   return false;
 	   }
 	
	   for(var i=0;i<queryData.length;i++){ 
		   
		   $("#tj_"+queryData[i].name).val(queryData[i].value);
	
		   var treeObj = $.fn.zTree.getZTreeObj("combo_" + queryData[i].name + "_content");
		   
           if(treeObj){
//        	   var llx = $("#combo_" + queryData[i].name + "_content").attr("tj_llx");
        	   var nodes= treeObj.getNodesByParam("id", queryData[i].value, null);
        	   if(nodes.length>0){
        		   var node = nodes[0]
        		   treeObj.checkNode(node, true, true);
        		   var name = node["name"];
         		   $("#combo_"+queryData[i].name).val(name);
        	   }
        	   
           }
           
           if(queryData[i].name=="fzxm"){
    		   
        	   setTjselect(queryData[i].value);
    		   
    	   }
		   
	   }
	   
	   
	  
	
}


function loadTjselect(coldata,tjfzfs){
	var arr = new Array();
	var title = new Array();
	
	var type = "checkbox";
	var do_click ='';
	if(tjfzfs!=1){
		type = "radio";
		do_click ='onclick="qdTjul();"';
	}
	
	var bj = 0;
	
  	if(coldata){
  		var fzlInp = "";
		for(var i=0;i<coldata.length;i++){
			var cxjg=coldata[i];
			
			
			if(cxjg.TJLX==1){ // 
				
				if(bj==0){
					fzlInp = fzlInp + '<li ><label><input name="tj_fzxx_dm" value="' + cxjg.LMC + '" type="'+type+'" title="' + cxjg.LMS + '" checked ="checked" '+do_click+' /> ' + cxjg.LMS + '</label></li>';
					arr.push(cxjg.LMC);
					title.push(cxjg.LMS);
					bj = 1;
				}else{
					fzlInp = fzlInp + '<li ><label><input name="tj_fzxx_dm" value="' + cxjg.LMC + '" type="'+type+'" title="' + cxjg.LMS + '"  '+do_click+' /> ' + cxjg.LMS + '</label></li>';
				}
				
			}
			
		}
		
		if(tjfzfs==1){
		 fzlInp = fzlInp + '<li><a class="btn btn-xs" onclick="qdTjul();">确定</a><a class="btn btn-xs" onclick="qxTjul();">取消</a></li>'
		}
  	}
	
 	$("#tj_ul").html(fzlInp);
 	
 	
	$("#tj_fzxm").val(arr);
 	$("#tj_fzxmmc").val(title);
	
 	$("#fzTool").show();
 	
}

function qdTjul(){
	$('#tj_ul').slideUp();
	$(".toggleDownAndUp").removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
	
	var arr = new Array();
	var title = new Array();
	
	$("input[name='tj_fzxx_dm']:checked").each(function(){
  		arr.push(this.value);
  		title.push(this.title);
 	});
 
 	$("#tj_fzxmmc").val(title);
 	$("#fzTool").attr("title",title);
	$("#tj_fzxm").val(arr);
  	
	if(typeof do_search =='function'){
		do_search();
	}
 	
}

function qxTjul(){
	
	$('#tj_ul').slideUp();
	$(".toggleDownAndUp").removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');

}

//设置统计条件选中值
function setTjselect(wds){
	
	var arr = new Array();
	var title = new Array();
	$("#tj_ul input").attr("checked",false);
	$("#tj_ul input").each(function(){
		if(wds.indexOf(this.value)>=0){
			this.checked=true;
 			arr.push(this.value);
			title.push(this.title);
		};
	});
	$("#tj_fzxm").val(arr);
 	$("#tj_fzxmmc").val(title);
	
}


//为查询参数添加统计查询条件
function addParamsTjcxtj(queryParams){
	
    var fzxm_obj = {
		"name":'fzxm',
		"type":$("#tj_fzxm").attr("data-type"),
		"value":$("#tj_fzxm").val()!=null? ($("#tj_fzxm").val()).toString():""
	}; 
  
	queryParams.push(fzxm_obj); 
	
	var zbxmArr = new Array(); 
	 
	if(coldata){
		var fzlInp = "";
		for(var i=0;i<coldata.length;i++){
			var cxjg = coldata[i];
			if(cxjg.TJLX==2||cxjg.TJLX==3||cxjg.TJLX==4||cxjg.TJLX==5){
				zbxmArr.push(cxjg.LMC);
			}
		}
	}
	
	var zbxm_obj = {
			"name":'zbxm',
			"type":"",
			"value":zbxmArr.join(",")
	};
	
	queryParams.push(zbxm_obj);
	 
	return queryParams;
}
