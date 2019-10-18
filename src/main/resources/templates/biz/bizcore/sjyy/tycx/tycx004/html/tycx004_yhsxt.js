$(window).resize(function() {
    $("#main").height($(window).height()-$("#header").height())
});

$(document).ready(function(){
	
    $("#main").height($(window).height()-$("#header").height());
});	

window.data =null;

function getSwordData(name){ // 获取sword数据 
    
	
	try{
       
	   if(data==null){
			window.data = JSON.parse($("#SwordPageData").attr("data")).data;
	   }
		
	   for(var i =0;i<window.data.length;i++){
	   
	    	var dat = data[i];
		   	if(dat.name==name){
 		   	   return dat;
		   	}
	   
	   }
	   
	   return null;
	   
	}catch (e) {
		
		return null;
		
	}
}
var djxh,nsrsbh;
$(function(){
	$.ajax({  
		type : "POST",  //提交方式  
		dataType:'json',
		async:false,
		url:ctx+"tykf/request?tld=tycx004YhsService_init",//路径
		data:{'drillParams':'%255B%257B%2522name%2522:%2522DJXH%2522,%2522value%2522:%252210123208010000000227%2522,%2522displayValue%2522:%252210123208010000000227%2522,%2522type%2522:%2522number%2522%257D,%257B%2522name%2522:%2522NSRSBH%2522,%2522value%2522:%252291320800MA1MUL6B8G%2522,%2522displayValue%2522:%252291320800MA1MUL6B8G%2522,%2522type%2522:%2522string%2522%257D,%257B%2522name%2522:%2522ZGSWSKFJ_DM%2522,%2522value%2522:%252223208%2522,%2522type%2522:%2522string%2522,%2522displayValue%2522:%2522%255Cu6c5f%255Cu82cf%255Cu7701%255Cu6dee%255Cu5b89%255Cu5730%255Cu65b9%255Cu7a0e%255Cu52a1%255Cu5c40%2522%257D,%257B%2522name%2522:%2522ZNFW%2522,%2522value%2522:%252223208%2522%257D,%257B%2522name%2522:%2522DLJGDM%2522,%2522value%2522:%252223208000000%2522%257D%255D'},
		success : function(result) {//返回数据根据结果进行相应的处理		
			$.each(result,function(name,value){				
		         $("[dataName='"+name.toLowerCase()+"']").html(value);
		         if(name=='DJXH'){
		        	 djxh='10123205001047400000';
		         }else if(name=='NSRSBH'){
		        	 nsrsbh=value;
		         }
		     });
			 var attr ='&nsrsbh='+nsrsbh+'&djxh='+djxh+'&a='+Math.random();
			 $("#frame_yhscx").attr('src','/tykf/request_http?tld=tycx004YhsService_initYhscxView'+attr);
		},
		error:function(e){
			
		} 
 });
});


//$(document).ready(function(){   
//	
//	var djxh1="${djxh}";
//	 
//	 var jbxxForm = { "nsrztmc":{"value":"正常"}};//getSwordData("jbxxForm");
//	 if(jbxxForm){
//		 
//	     $.each(jbxxForm,function(name,value){
//	         $("[dataName='"+name+"']").html(value.value).attr("title",value.value);
//	          
//	     });
//	 }	 
////	 gwssswjg = getSwordData("gwssswjg").value;
////	 gwxh = getSwordData("gwxh").value;
////	 zndm = getSwordData("zndm").value;
//	 
//	 nsrsbh = jbxxForm.data.nsrsbh.value;
// 	 djxh = "${djxh}";
// 
// 	 var attr ='&nsrsbh='+nsrsbh+'&djxh='+djxh+'&gwssswjg='+gwssswjg+'&gwxh='+gwxh+'&zndm='+zndm+'&a='+Math.random()
// 	 var attr1 ='&NSRSBH='+nsrsbh+'&DJXH='+djxh+'&GWSSSWJG='+gwssswjg+'&GWXH='+gwxh+'&ZNDM='+zndm+'&a='+Math.random()
//
//	 $("#frame_yhscx").attr('src','/sword?ctrl=CX904YhscxwhCtrl_initYhscxView'+attr);
//	// $("#frame_blb").attr('src','/sword?ctrl=CX904YhscxwhCtrl_initYhsfxView'+attr);
// 	 $("#frame_blb").attr('src','/sword?ctrl=CX904YhscxwhCtrl_initBlbView'+attr);
// 	 $("#frame_glgxqy").attr('src','../../reportJsp/showReport.jsp?rpx=sq/glgxqy.rpx'+attr1);
// 	//$("#frame_zbfx").attr('src','../../ods/cx905/cx905_yhscxmx.html?sqlxh=00010011489&a=0.9884811772499233'+attr);
// 	// $("#frame_zbfx").attr('src','../../reportJsp/showReport.jsp?rpx=sq/zbfx.rpx'+attr1);
//	 $("#frame_bqhx").attr('src','/sword?ctrl=CX904YhscxwhCtrl_initBqhx&ymbj=1'+attr);
//	 $("#frame_mxsj").attr('src','/sword?ctrl=CX904YhscxwhCtrl_initBqhx&ymbj=0'+attr);	 
//     $("#Tab_header .tabH").bind("click",function(){
//     	   
//	    	 $("#Tab_header .tabH_active").removeClass("tabH_active");
//	         $(this).addClass("tabH_active");
//	      	 
//	         var tabName = $(this).attr("tabName");
//	          
//	         var tab = $("#tab_"+tabName);
//	         
//	         var xh = $(tab).index();
//	         
//	         $("#main div").hide();
//	         
//	         $(tab).fadeIn(1000)
//	        
//	        // $(tab).height(2).width('70%').show().animate({height:'100%',width:'100%'},1500,function(){ });
//	       
//	         /*
//	         if(xh>0){
//	        	 
//	        	 $(tab).height(2).width('70%').css("margin-bottom","0");
//	         	 $(tab).prependTo($("#main"));
//	         	 
//	         	 $(tab).css("border","2px solid #ddd");
//	        	 
//	        	 $("#main div").eq(1).animate({width:'70%'},1500);
//	        	 
//	        	 $(tab).animate({height:'100%',width:'100%','margin-bottom':'200px'},1500,function(){
//	            	 $(tab).css("border","0");
//	        		 $(tab).after($("#main_w"));
//	        	 });
//	        	 
//	         
//	         }
//           */
//	         
//     });
//     
//       
//     
//});

 

function toggleHeader(obj){
	
	 $("#qyzkQymc").fadeToggle(800);
	
	 $("#qyzk").slideToggle(800,function(){
	       $(obj).toggleClass("glyphicon-chevron-up");
	       $(obj).toggleClass("glyphicon-chevron-down");
	 	   $("#main").height($(window).height()-$("#header").height());
	 });
	
	
}