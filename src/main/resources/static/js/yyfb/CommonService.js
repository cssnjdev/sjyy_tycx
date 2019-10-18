jQuery.extend({ 
   /** 
    * @see 将javascript数据类型转换为json字符串 * 
    * @param 待转换对象,支持object,array,string,function,number,boolean,regexp * 
    * @return 返回json字符串 
    * */ 
   toJSON: function(object) { 
     var type = typeof object; 
     if ('object' == type) { 
       if (Array == object.constructor) type = 'array'; 
       else if (RegExp == object.constructor) type = 'regexp'; 
       else type = 'object'; 
     } 
     switch (type) { 
     case 'undefined': 
     case 'unknown': 
       return; 
       break; 
     case 'function': 
     case 'boolean': 
     case 'regexp': 
       return object.toString(); 
       break; 
     case 'number': 
       return isFinite(object) ? object.toString() : 'null'; 
       break; 
     case 'string': 
       return '"' + object.replace(/(\\|\")/g, "\\$1").replace(/\n|\r|\t/g, function() { 
         var a = arguments[0]; 
         return (a == '\n') ? '\\n': (a == '\r') ? '\\r': (a == '\t') ? '\\t': "" 
       }) + '"'; 
       break; 
     case 'object': 
       if (object === null) return 'null'; 
       var results = []; 
       for (var property in object) { 
         var value = jQuery.toJSON(object[property]); 
         if (value !== undefined) results.push(jQuery.toJSON(property) + ':' + value); 
       } 
       return '{' + results.join(',') + '}'; 
       break; 
     case 'array': 
       var results = []; 
       for (var i = 0; i < object.length; i++) { 
         var value = jQuery.toJSON(object[i]); 
         if (value !== undefined) results.push(value); 
       } 
       return '[' + results.join(',') + ']'; 
       break; 
     } 
   } 
});

var CommonService=function(serviceName,data,type)
{
	this.serviceName=serviceName;
	this.data=data;
	this.type=type;
	this.jsonObj={};
}
CommonService.prototype.doService=function()
{
	jQuery.ajaxSetup({
         async: false
     }); 
     var handler=this;
	jQuery.post(getLocation()+"/xlroot/common/jsoncommon.do?method=serviceDispatcher&serviceName="+this.serviceName,{"PARAM":JSON.stringify(this.data)},function(json)
	{
		handler.jsonObj=json;
	},this.type);
}
CommonService.prototype.doDmService=function()
{
	jQuery.ajaxSetup({
         async: false
     }); 
     var handler=this;
	jQuery.post(getLocation()+"/xlroot/common/jsondmcommon.do?method=serviceDispatcher&serviceName="+this.serviceName,{"PARAM":JSON.stringify(this.data)},function(json)
	{
	    
		handler.jsonObj=json;
	},this.type);
}
CommonService.prototype.doHsService=function()
{
	jQuery.ajaxSetup({
         async: false
     }); 
     var handler=this;
	jQuery.post(getLocation()+"/xlroot/common/jsonhscommon.do?method=serviceDispatcher&serviceName="+this.serviceName,{"PARAM":JSON.stringify(this.data)},function(json)
	{
	    
		handler.jsonObj=json;
	},this.type);
}
CommonService.prototype.setJson=function(json)
{
	this.jsonObj=json;
}
CommonService.prototype.getMessage=function()
{
	if(!this.jsonObj.rtnStatus)return;
	return this.jsonObj.rtnStatus.rtnMsg;
}
CommonService.prototype.getCode=function()
{
	if(!this.jsonObj.rtnStatus)return;
	return this.jsonObj.rtnStatus.rtnMsgCode;
}
CommonService.prototype.getResponse=function()
{
	if(!this.jsonObj.rtnData)return;
	return this.jsonObj.rtnData;
}

function getLocation(){
	var href=window.location.href;
	return href.substring(0,href.indexOf("/work"));
}
function tools_cmdquest_onclick(){
	top.fraMain.sss();
}
function Request(argname){
    var url = document.location.href;
    var arrStr = url.substring(url.indexOf("?") + 1).split("&");
    //return arrStr;
    for (var i = 0; i < arrStr.length; i++) {
        var loc = arrStr[i].indexOf(argname + "=");
        if (loc != -1) {
            return arrStr[i].replace(argname + "=", "").replace("?", "");
            break;
        }
    }
    return "";
}
