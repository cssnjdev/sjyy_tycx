

(function($){
	 	$.fn.cssnjGetFormData= function(){ //将页面form 转化为json串
	 		var array=$(this).serializeArray();
			var jsonform=new Object();
			for(var i=0;i<array.length;i++){
				jsonform[array[i].name]=array[i].value;
			}
			return jsonform;
		}

	 	$.fn.cssnjSetFormAsJson = function(json){  //将json数据初始化到form
	 		for(var item in json){
				$(this).find("[name='"+item+"']").val(json[item]);
			}
	 	}

 		$.fn.cssnjForm=function(json){

 			this.setValue=function(name,value){

			if($(this).find("[name='"+name+"']").length==0){
				$(this).append("<input name='"+name+"' type='hidden'>");
			}

			$(this).find("input:text[name='"+name+"']").val(value);
			$(this).find("input:hidden[name='"+name+"']").val(value);

			$(this).find("select[name='"+name+"']").val(value);
			$(this).find("textarea[name='"+name+"']").html(value);

			$(this).find("input:radio[name='"+name+"']").removeAttr("checked");

			$(this).find("input:radio[name='"+name+"']").each(function(){
				if($(this).attr("value")==value){
					$(this).attr("checked",true);
				}
			});

		}

		this.getValue=function(name){
			return $(this).find("[name='"+name+"']").val();
		}

		this.clear=function(){
			$(this).find("input").val(null);
			$(this).find("select").val(null);
			$(this).find("textarea").html("");
		}

		this.getData=function(){  //获取全部数据
			var array=$(this).serializeArray();
			var jsonform=new Object();
			for(var i=0;i<array.length;i++){
				jsonform[array[i].name]=array[i].value;
			}
			return jsonform;
		}

		this.setData=function(json){ //根据json 设置form多条数据
			var array=$(this).serializeArray();
			for(var i=0;i<array.length;i++){
				this.setValue(array[i].name,json[array[i].name]);
			}
		}

		if(json){
			this.setData(json);
		}

		return this;

 	}

 	window.cssnjDataObj = new Object();//


	window.cssnjGetData=function(dataName){

 		return cssnjDataObj[dataName];

 	}

	window.cssnjInitData = function(){  //

	 		$(".cssnj_data").each(function(){

	 			 var  url = $(this).attr("dataurl");

	 			 var dataname = $(this).attr("dataname");

	 			 if(url!=null&&url!=undefined){

	 				   try{

		 				    var datas = null;
			 				$.ajax({
			 					type : "POST",  //提交方式
			 					dataType:'json',
			 					async:false,
			 					url : url,//路径
			 					success : function(result) {//返回数据根据结果进行相应的处理
			 						datas = result ;
			 					}
			 				});

			 				cssnjDataObj[dataname] = datas;

	 				  }catch (e) {


	 				  }

	 			  } else{
	 				  var datajson1 = $("#"+dataname).text();
 	 				   // var datajson = $(this).attr("datajson").replace(/'/g,"\"");
 	 				   var datajson = datajson1.replace(/'/g,"\"");

   	 				   try{
		 				   if(JSON.parse(datajson)){
		 					  cssnjDataObj[dataname] = JSON.parse(datajson);

		 				   }
	 				   }catch (e) {
	 					    alert("前台初始化数据异常！")
 							// TODO: handle exception
					   }

	 			  }

	 		});

 	}


 	window.toggleCxtj = function(obj){

 		if($('#cxtj_box') ){

 			$('#cxtj_box').slideToggle();

 			if($(obj).text()=="隐藏条件"){
 				$(obj).text("展开条件");
 			}else{
 				$(obj).text("隐藏条件");
 			}

 		}

 	}


 	//页面布局 方法
 	window.LayOutBody =function(){

 		window.myLayout= $("body").layout({
 	           spacing_open:0,
 	           spacing_closed:0,
 	           closable:false,
 	           west__closable:true,
 	           west__spacing_open:5,
 	           west__spacing_closed:8,
 	           resizable:true,
 	           west__resizable:true,
 	           west__size:300,
 	           onresize_end : function(){
 	        	  dt_resize_dx();
               }
 	      });

 		$("body>.ui-layout-north").css("z-index","10");

 	}



 	$(function(){

 		cssnjInitData();  // 初始化数据

 		$(".jgTab.tab-content").each(function(){

 			$(this).find(">.tab-pane").each(function(index){
 				$(this).css("z-index",100-index);
 			});

 			$(this).find(">.tab-pane.active").css("z-index",9999);

 		});

 		$(".jgTab.nav-tabs").off("click",">li").on("click",">li",function(){

 			var beforeChange = $(this).attr("beforeChange"); // 跳转前

 			if(typeof window[beforeChange] =="function"){

 				var flag = window[beforeChange]();

 				if(flag==false){
 					return false;
 				}

 			}

 			var obj = $(this).find(">a").attr("href");

 			$(obj).parent().find(">.tab-pane").each(function(index){
 				$(this).css("z-index",100-index);
 			});

 			$(obj).css("z-index",9999);

 			var afterChange = $(this).attr("afterChange");

 			if(typeof window[afterChange] =="function"){
 				var flag =  window[afterChange]();
 			}

 		});

 		try{
 			easyloader.theme = 'bootstrap';
 		}catch(e){

 		}

 	});


 	window.cssnj_open_loading = function(item){

 		var div="<div></div>"


 	}

 	window.cssnj_close_loading = function(item){


 	}

})(jQuery);

