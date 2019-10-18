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


function contains(arr,obj){
	var i=arr.length;
	while(i--){
		if(arr[i]==obj){
			return true;
		}
	}
	return false;
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