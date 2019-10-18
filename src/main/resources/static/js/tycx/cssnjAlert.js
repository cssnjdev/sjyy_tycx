/*
* 需要 jquery
* 需要 bootstrap
* 
* 属性opener 是指打开窗口对象的页面的window对象 
*   
* 
*/
(function($){

	try{
		using('draggable');
	}catch (e) {
		// TODO: handle exception
	}

	try{
		using('resizable');
	} catch (e) {
		// TODO: handle exception
	}


	$.cssnj={
		obj:{
			messager:$(


				"<div class='modal fade' tabindex='-1' role='dialog' style='padding-top:60px;'> "+
				"	<div class='modal-dialog' role='document' style='max-width:600px;min-width:600px;width:600px;margin:36px auto;'>"+
				"		<div class='modal-content' > "+
				" 		<div class='modal-header' style=\"height:35px; padding:9px 10px 9px 10px;\">"+
				"					<img src="+ctx+"img/image/mlzy/zt.png width='20' height='20' style='float:left;' /> \n" +
				"                 <button type='button' class='close' data-dismiss='modal' aria-label='Close' style='opacity: 1;' title='关闭'>" +
				"					 	<span aria-hidden='true' id='times'>×</span>" +
				"					</button> "+
				"					<h5 class='modal-title' id='myModalLabel' style='margin-left:22px;'>提示信息</h5>"+
				" 		</div>	" +
				"			<div class='modal-body' >"+
				"			<table border='0' cellpadding='0' cellspacing='3' width='100%' style='margin:0 auto;' >   "+
				"			 <tr>  "+
				"		 	   <td rowspan='2' width='40%' align='right' style='padding-right:10px;'> "+
				"			   	 <img th:src="+ctx+"tycx/img/image/mlzy/lu_ts.png id='image' width='60' height='60' /> "+
				"			   </td> "+
				"			   <td  width='60%'  align='left' style='overflow:auto;max-width:355px;'> "+
				"		 	     <font style='font-weight: bold;font-size: 18px;' id='modalInfo'>  "+
				"					     "+
				"				 </font> "+
				"		  		 </td> "+
				"			 </tr> "+
				"			 <tr> "+
				"		  		 <td valign='top'> "+
				"		  		   	<font style='font-size: 12px;color: gray;' id='modalts'>如果确定请点确定按钮，否则请取消。</font> "+
				"				   </td> "+
				"			 </tr> "+
				"		 	</table> "+
				"			</div>"+
				"			  <div class='modal-footer' style='border:none;width:auto;margin:0px auto;text-align:center;padding:10px 10px 30px 10px;'>"+
				"           </div> "+
				"      </div>"+
				"    </div>"+
				"</div>  "
			),
			OkButton: "<button type='button' name='queding' class='btn btn-primary OkButton' style='padding:5px 35px 5px 35px;'>确定</button>",
			CancelButton: "<button type='button' name='quxiao' class='btn btn-primary CancelButton' data-dismiss='modal' style='padding:5px 35px 5px 35px;'  > 取消</button>",
			DeleteButton:"<button  type='button' name='sanchu' class='btn btn-primary DeleteButton' style='padding:5px 35px 5px 35px;' >删除</button>",
			iframe:$(
				'<div class="modal fade cssnj_iframe_modal" tabindex="-1" role="dialog" backdrop="static" aria-labelledby="myModalLabel">'+
				'<div class="modal-dialog" role="document" style="width: 90%;margin:0 auto;height:100%;" >'+
				'<div class="modal-content " style="height:100%;">'+
				'<div class="modal-header qptab_modal-header" >'+
				'<button type="button" class="close iframe_close" aria-label="Close" data-dismiss="modal"  title="关闭" >'+
				'<span aria-hidden="true" class="glyphicon glyphicon-remove" style="font-size:14px;color:#5273D6;"></span>'+
				'</button>'+
				'<button type="button" class=" iframe_maxsize" title="全屏" >'+
				'<i class="fa fa-window-maximize"  style="font-size:14px;" ></i>'+
				'</button>'+
				'<button type="button" class="iframe_restore" title="退出全屏">'+
				'<i class="fa fa-window-restore"   style="font-size:14px;" ></i>'+
				'</button>'+
				'<div class="modal-title qptab_modal-title qptab_modal-title" id="myModalLabel">'+
				'<span class="glyphicon glyphicon-th-list" aria-hidden="true" style="margin-left: 5px;margin-right:5px;"></span>'+
				'<span class="iframe_title"></span>'+
				'</div>'+
				'</div>'+
				'<div class="modal-body" style="padding:0px 1px 0px 1px;height:95%; border-radius:none;">'+
				'<iframe class="cssnj_iframe_iframe" src="" style="width: 100%; height: 100%;" frameborder=0 > </iframe>'+
				'</div>'+
				'</div>'+
				'</div>'+
				'</div>		'
			)

		},
		messager:function(title,messager,item){ //自定义消息弹窗

			/*
             *
             * 使用方法
             * $.cssnj.messager("title","messager");
             * $.cssnj.messager("title","messager",{onOk:function(){alert(1);},showCancel:false});
             *
             */

			var obj=$.cssnj.obj.messager.clone();

			$(obj).find('.modal-title').html(title);
			$(obj).find('#modalInfo').html(messager);
			$(obj).find('#image').attr("src",ctx+'img/image/mlzy/lu_ts.png');
			$(obj).find('.modal-footer').html("");

			if(!item){
				$(obj).find('.modal-footer').append($.cssnj.obj.CancelButton);
				$(obj).modal("show");
				return obj;
			}

			item.obj = obj;

			if(item.top){
				$(obj).css("padding-top",item.top);
			}else{
				$(obj).css("padding-top","60px");
			}

			if(item.onBefore){
				onBefore();
			}

			if(item.showOk!=false){

				$(obj).find('.modal-footer').append($.cssnj.obj.OkButton);

				$(obj).find('.modal-footer .OkButton').unbind("click").bind("click",function(){
					$(obj).modal("hide");
				});

				if(item.onOk){
					$(obj).find('.modal-footer .OkButton').bind("click",function(){
						item.onOk(item);
					});
				}

				if(item.okText){
					$(obj).find('.modal-footer .OkButton').html(item.okText);
				}

			}

			if(item.showDel==true){

				$(obj).find('.modal-footer').append($.cssnj.obj.DeleteButton);

				$(obj).find('.modal-footer .DeleteButton').unbind("click").bind("click",function(){
					$(obj).modal("hide");
				});

				if(item.onDel){
					$(obj).find('.modal-footer .DeleteButton').bind("click",function(){
						item.onDel(item);
					});
				}

				if(item.delText){
					$(obj).find('.modal-footer .DeleteButton').html(item.delText);
				}

			}

			if(item.showCancel!=false){

				$(obj).find('.modal-footer').append($.cssnj.obj.CancelButton);

				if(item.oncancel){
					$(obj).find('.modal-footer .CancelButton').unbind("click").bind("click",function(){
						item.oncancel(item);
					});
				}

				if(item.cancelText){
					$(obj).find('.modal-footer .CancelButton').html(item.cancelText);
				}

			}


			if(item.showFooter==false){
				$(obj).find('.modal-footer').hide();
			}else{
				$(obj).find('.modal-footer').show();
			}


			if(item.type=='1'){
				$(obj).find('#image').attr("src",ctx+'img/image/mlzy/lu_ts.png');
			}else if(item.type=='2'){
				$(obj).find('#image').attr("src",'/img/image/mlzy/Lu_cg.png');
			}else if(item.type=='3'){
				$(obj).find('#image').attr("src",'/img/image/mlzy/Lu_sb.png');
			}

			if(item.showImage){
				$(obj).find('#image').attr("src",item.showImage);
			}

			if(item.helpInfo){
				$(obj).find("#modalts").html(item.helpInfo);
			}else{
				$(obj).find("#modalts").html("如果确定请点确定按钮，否则请取消。");
			}

			if(item.onShow){
				onShow();
			}

			if(item.noClose){
				$(obj).find('.close').remove();
			}



			setTimeout(function(){
				$(obj).modal({backdrop:'static'});
			},200);

			if(item.onAfter){
				onAfter();
			}

			return obj;

		},
		alert:function(title,messager,type){ //自定义alert

			if(!type){
				type=1;
			}
			if(!messager){
				messager=title;
				title="提示信息";
			}else if(title==null){
				title="提示信息";
			}

			var obj=$.cssnj.obj.messager.clone();
			$(obj).find('.modal-title').html(title);
			$(obj).find('#modalInfo').html(messager);
			$(obj).find("#modalts").html("");

			if(type=='1'){
				$(obj).find('#image').attr("src",ctx+'img/image/mlzy/lu_ts.png');
			}else if(type=='2'){
				$(obj).find('#image').attr("src",'/img/image/mlzy/Lu_cg.png');
			}else if(type=='3'){
				$(obj).find('#image').attr("src",'/img/image/mlzy/Lu_sb.png');
			}else{
				$(obj).find('#image').attr("src",ctx+'img/image/mlzy/lu_ts.png');
			}


			$(obj).find('.modal-footer').html("");
			$(obj).find('.modal-footer').show();

			$(obj).find('.modal-footer').append($.cssnj.obj.OkButton);
			$(obj).find('.modal-footer .OkButton').eq(0).unbind("click").bind("click",function(){
				$(obj).modal("hide");
			});

			setTimeout(function(){
				$(obj).modal({backdrop:'static'});
			},200);

			return obj

		},
		iframe:function(title,src,item_in){ //iframe弹窗方法

			var obj=$($.cssnj.obj.iframe).clone(); // 克隆对象

			var dialog = $(obj).find(".modal-dialog");

			var item = {  //默认值
				'width':'86%',
				'height':'80%',
				'top':'60px',
				'left':null, //不建议赋值,默认窗体水平居中
				'isMax':false, // 是否最大窗口
				'drag':false,  //可拖拽
				'opener':window,
				'onBeforeClosed':function(d){ //关闭前的事件 返回false 则不会关闭
					return true;
				},
				'onClosed':function(d){ //关闭后的事件
					return true;
				}
			}

			for(var key in item_in){ //查询赋值配置参数
				item[key] = item_in[key];
			}

			$(dialog).resizable();

			$(dialog).draggable({
				"handle":".modal-header",
				"disable":true,
				"onDrag":function(e){

				},
				"onStopDrag":function(e){
					var top = $(dialog).css('top').replace("px","");
					var left = $(dialog).css('left').replace("px","");
					if(top<1){
						$(dialog).css('top','0px');
					}
					if(left<1){
						$(dialog).css('left','0px');
					}
				}
			});



			var o={					//默认返回对象
				'item':item,
				'modal':obj,
				'contentWindow':null,
				'toMax':function(){

					item.left = $(dialog).css('left');
					item.top = $(dialog).css('top');

					$(obj).find(".iframe_restore").show() ;
					$(obj).find(".iframe_maxsize").hide() ;
					$(dialog).width('100%');
					$(dialog).height('100%');

					$(dialog).css("left","0");
					$(dialog).css("top","0");
					item.isMax=true;

					$(dialog).find(".modal-header").off("mouseover");

					$(dialog).resizable('disable');
					$(dialog).draggable('disable');

				},
				'restore':function(){

					$(obj).find(".iframe_restore").hide() ;
					$(obj).find(".iframe_maxsize").show() ;

					$(dialog).width(item.width);
					$(dialog).height(item.height);

					if(item.left){
						$(dialog).css("left",item.left);
					}
					$(dialog).css("top",item.top);
					item.isMax=false;

					if(item.drag){

						$(dialog).find(".modal-header").on("mouseover",function(){
							$(dialog).draggable('enable');
						}).on("mouseleave",function(){
							$(dialog).draggable('disable');
						});

					}

					$(dialog).resizable('enable');
				},
				'toggle':function(){
					if(this.isMax==true){
						this.restore();
					}else{
						this.toMax();
					}
				}
			}

			$(obj).find(".iframe_title").text(title);

			var drag = false;


			if(item.isMax==true){
				o.restore();
				o.toMax();
			}else{
				o.restore();
			}

			$(obj).find(".iframe_restore").bind("click",o.restore);

			$(obj).find(".iframe_maxsize").bind("click",o.toMax);


			$(obj).on('hide.bs.modal',function(){

				var b = item.onBeforeClosed(item);
				if(b!=false){
					b==true;
				}

				return b;

			});


			$(obj).on('hidden.bs.modal',function(){

				var b = item.onClosed(item);
				if(b!=false){
					$(obj).modal('hide');
				}

				$(obj).find("iframe").attr("src","");
				$(obj).remove();
			});


			$(obj).on('shown.bs.modal',function(){

				if(!item.left){
					item.left = ($(window).width()-$(dialog).width())/2;
				}

				if(!item.isMax){
					$(dialog).css("margin-left","0");
					$(dialog).css("left",item.left);
				}


				$(obj).find("iframe").attr("src",src);

				setTimeout(function(){

					var iframe_wd = $(obj).find("iframe")[0].contentWindow;
					o['contentWindow'] = iframe_wd;
					iframe_wd.cssnj_opener = item["opener"];
					iframe_wd.onload=function(){
						iframe_wd.document.oncontextmenu=function(){
							return false;
						};
					}

				},300);

			});

			$(obj).modal({backdrop:'static'}) ;

			return o;

		},
		window_index:0,
		window:function(src,opener){ // iframe 没有头部弹窗
			$.cssnj.window_index++;

			var index =$.cssnj.window_index+600;

			var id="cssnjWindowIframe_"+$.cssnj.window_index;

			var iframe =$('<iframe src="" id="'+id+'" class="cssnjWindowIframe"  style="width: 100%; height: 100%;position:fixed;top:0;left:0;z-index:'+index+';background:#fff;display:none;margin:0 auto;" frameborder=0 > </iframe>');

			$("body").append(iframe);

			$(iframe).fadeIn(300,function(){

				$(iframe).attr("src",src);

				setTimeout(function(){
					var iframe_wd =  document.getElementById(id).contentWindow;
					iframe_wd.cssnj_opener= opener;
				},300)

			});

			return iframe;

		}

	}



})(jQuery);


if(typeof top.jQuery.cssnj.messager == 'function'){


	window.cssnj_messager = function(title,messager,item){
		if(item){
			item["opener"]=window;
		}else{
			item ={"opener":window};
		}
		return top.jQuery.cssnj.messager(title,messager,item);
	}

	window.cssnj_alert = function(title,messager,type){
		return top.jQuery.cssnj.alert(title,messager,type);
	}

	window.cssnj_alertSave = function(messager,saveFunc){  //保存前提醒

		return top.jQuery.cssnj.messager("保存提示",messager,{
			"type":2,
			"opener":window,
			"showOk":true,
			"showDel":false,
			"onOk":saveFunc,
			"showCancel":true,
			"helpInfo":"如果保存请点确定按钮，否则请取消。"
		});

	}

	window.cssnj_alertDel = function(messager,delFunc){ //删除前提醒

		return top.jQuery.cssnj.messager("删除提示",messager,{
			"type":3,
			"opener":window,
			"showOk":false,
			"showDel":true,
			"onDel":delFunc,
			"showCancel":true,
			"helpInfo":"如果删除请点删除按钮，否则请取消。"
		});

	}

	//操作成功


	//操作失败

}else{


	window.cssnj_messager = function(title,messager,item){
		return jQuery.cssnj.messager(title,messager,item);
	}

	window.cssnj_alert = function(title,messager,type){
		return jQuery.cssnj.alert(title,messager,type);
	}

	window.cssnj_alertSave = function(messager,saveFunc){

		return jQuery.cssnj.messager("保存提示",messager,{
			"type":2,
			"opener":window,
			"showOk":true,
			"showDel":false,
			"onOk":saveFunc,
			"showCancel":true,
			"helpInfo":"如果保存请点确定按钮，否则请取消。"
		});

	}

	window.cssnj_alertDel = function(messager,delFunc){

		return jQuery.cssnj.messager("删除提示",messager,{
			"type":3,
			"opener":window,
			"showOk":false,
			"showDel":true,
			"onDel":delFunc,
			"showCancel":true,
			"helpInfo":"如果删除请点删除按钮，否则请取消。"
		});

	}

}




function cssnj_iframe(title,src,item){
	var o=null
	if(typeof jQuery.cssnj.iframe == 'function'){
		o = jQuery.cssnj.iframe(title,src,item);
	}
	return o;
}


function cssnj_top_iframe(title,src,item){

	if(item){
		item["opener"]=window;
	}else{
		item ={
			"opener":window,
			"top":'60px',
			"width":"86%",
			"height":"80%",
			"drag":false
		};
	}

	var o=null;

	if(typeof top.jQuery.cssnj.iframe == 'function'){
		o=top.jQuery.cssnj.iframe(title,src,item);
	} else{
		o=jQuery.cssnj.iframe(title,src,item);
	}

	return o;

}

function cssnj_window(src){
	if(typeof jQuery.cssnj.window == 'function'){
		jQuery.cssnj.window(src,window);
	}
}

function cssnj_top_window(src){

	var iframe = null;

	if(typeof top.jQuery.cssnj.window == 'function'){
		var iframe = top.jQuery.cssnj.window(src,window);
	} else{
		var iframe = jQuery.cssnj.window(src,window);
	}

	return iframe;
}


function cssnj_closeIframe(){ //当页面用cssnj_top_iframe，cssnj_iframe窗口打开时 页面内部 关闭窗口的方法

	var frameArr = parent.jQuery('iframe.cssnj_iframe_iframe');

	for(var i=0;i<frameArr.length;i++){
		if(frameArr[i].contentWindow.location.href === window.location.href){//从父页面找到指向当前窗的iframe
			parent.jQuery(frameArr[i]).closest(".cssnj_iframe_modal").modal("hide");
		}
	}

}

function cssnj_windowBack(opener_do){ //当页面用cssnj_window，cssnj_top_window 打开时 页面内部 关闭窗口的方法
	debugger;
	var frameArr = parent.jQuery('iframe.cssnjWindowIframe');

	try{

		for(var i=0;i<frameArr.length;i++){

			if(frameArr[i].contentWindow.location.href === window.location.href){//从父页面找到指向当前窗的iframe
				parent.jQuery(frameArr[i]).fadeOut(300,function(){
					parent.jQuery(frameArr[i]).attr("src","");
					parent.jQuery(frameArr[i]).remove();
				});
			}

		}

		if(typeof cssnj_opener =="object"){
			if(typeof cssnj_opener[opener_do]=="function"){
				cssnj_opener[opener_do]();
			}
		}

	}catch(e){


	}

}
 
 