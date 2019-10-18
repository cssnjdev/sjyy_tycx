var CommonService=function(serviceName,data,type){
	this.serviceName=serviceName;
	this.data=data;
	this.type=type;
	this.jsonObj={};
};

CommonService.prototype.setJson=function(json){
	this.jsonObj=json;
};

CommonService.prototype.getMessage=function(){
	if(!this.jsonObj.rtnStatus)return;
	return this.jsonObj.rtnStatus.rtnMsg;
};

CommonService.prototype.getCode=function(){
	if(!this.jsonObj.rtnStatus)return;
	return this.jsonObj.rtnStatus.rtnMsgCode;
};
CommonService.prototype.getResponse=function(){
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
    for (var i = 0; i < arrStr.length; i++) {
        var loc = arrStr[i].indexOf(argname + "=");
        if (loc != -1) {
            return arrStr[i].replace(argname + "=", "").replace("?", "");
            break;
        }
    }
    return "";
}

CommonService.prototype.doExtAjaxDmService=function(callback,hasParent,hiddenProg){
	var handler=this;
	if(!hiddenProg){
		if(hasParent){
			parent.waitDialog();
		}else{
			waitDialog();
		}
	}
	Ext.Ajax.request({
		url :getLocation()+"/xlroot/common/jsondmcommon.do?method=serviceDispatcher&serviceName="+this.serviceName,
		method : 'post',
		params : {"PARAM":Ext.util.JSON.encode(this.data)},
		success : function(response,ajaxObj){
			var responseArray = Ext.util.JSON.decode(response.responseText);                                             
			handler.jsonObj=responseArray;
			callback(responseArray);
			if(!hiddenProg){
				if(hasParent){
					parent.Ext.MessageBox.hide();
				}else{
					Ext.MessageBox.hide();
				}
			}
		},
		failure : function(e) {
			if(!hiddenProg){
				if(!hiddenProg) parent.Ext.MessageBox.hide();
				parent.iconDialog('系统异常', 'ERROR');
			}else{
				if(!hiddenProg) Ext.MessageBox.hide();
				iconDialog('系统异常', 'ERROR');
			}
		}
	});
}

/**
*
*
*/
CommonService.prototype.doExtAjaxService=function(callback,hasParent,hiddenProg){
	var handler=this;
	if(!hiddenProg){
		if(hasParent){
			parent.waitDialog();
		}else{
			waitDialog();
		}
	}
	Ext.Ajax.request({
		timeout :900000,
		url :getLocation()+"/xlroot/common/jsoncommon.do?method=serviceDispatcher&serviceName="+this.serviceName,
		method : 'post',
		params : {"PARAM":Ext.util.JSON.encode(this.data)},
		success : function(response,ajaxObj){
			var responseArray = Ext.util.JSON.decode(response.responseText);                                             
			handler.jsonObj=responseArray;
			callback(responseArray);
			if(!hiddenProg){
				if(hasParent){
					parent.Ext.MessageBox.hide();
				}else{
					Ext.MessageBox.hide();
				}
			}
		},
		failure : function(e) {
			if(hasParent){
				if(!hiddenProg) parent.Ext.MessageBox.hide();
				parent.iconDialog('系统异常', 'ERROR');
			}else{
				if(!hiddenProg) Ext.MessageBox.hide();
				iconDialog('系统异常', 'ERROR');
			}
		}
	});
}

function waitDialog(mes,proText){
	if(mes==null){mes="正在处理数据,请等待..."}
	if(proText==null){proText="处理中..."}
	Ext.MessageBox.show({
		msg:mes,
		progressText:proText,
		width:400,
		wait : true,
		icon:'ext-mb-download',
		waitConfig :{
			text : proText
		}
	})
}

function iconDialog(mes, type, callFn, mestitle) {
	if (mestitle == null) {
		mestitle = "信息对话框"
	}
	var exFn = callFn ||
	function() {};
	switch (type) {
	case "INFO":
		Ext.MessageBox.show({
			title:mestitle,
			width:400,
			msg: mes,
			buttons: Ext.MessageBox.OK,
			icon: Ext.MessageBox.INFO,
			fn: exFn
		});
		break;
	case "WARNING":
		Ext.MessageBox.show({
			title:mestitle,
			width:400,
			msg: mes,
			buttons: Ext.MessageBox.OK,
			icon: Ext.MessageBox.WARNING,
			fn: exFn
		});
		break;
	case "ERROR":
		Ext.MessageBox.show({
			title:mestitle,
			msg: mes,
			width:400,
			buttons: Ext.MessageBox.OK,
			icon: Ext.MessageBox.ERROR,
			fn: exFn
		});
		break;
	default:
		Ext.MessageBox.show({
			title:mestitle,
			msg: mes,
			width:400,
			buttons: Ext.MessageBox.OK,
			icon: Ext.MessageBox.INFO,
			fn: exFn
		})
	}
}