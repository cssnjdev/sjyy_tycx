 
var tycx_service={
		
	ajax:function(data,url){ //后台请求公用方法
		var result = null
		$.ajax({
			url : url,
			async : false,
			data : data,
			dataType : 'json',
			success : function(data, textStatus, jqXHR) {
				result = data;
			},
			error : function(xhr, textStatus) {

			}
		});
		return result;
	},
	selectCxdyAndCxtj:function(data){//获取查询定义和查询条件信息
		return this.ajax(data,ctx+"tykf/request?tld=Tycx002DzcxService_selectCxdyAndCxtj&a="+ Math.random());
	},
	getCombData:function(data){//获取查询条件 select 数据
 		return this.ajax(data,ctx+"tykf/request?tld=Tycx002DzcxService_getCombData&a="+ Math.random());
	},
	getTreeData:function(data){
 		return this.ajax(data,ctx+"tykf/request?tld=Tycx002DzcxService_getTreeData&a="+ Math.random());
	},
	getTreesData:function(data){
		return this.ajax(data,ctx+"tykf/request?tld=Tycx002DzcxService_getTreeData&a="+ Math.random());
	},
	getCxjgl:function(data){ //获取查询结果列
 		return this.ajax(data,ctx+"tykf/request?tld=Tycx002DzcxService_getCxjgl&a="+ Math.random());
	},
	getWdxx:function(data){
		return this.ajax(data,ctx+"tykf/request?tld=TycxInfoService_getWdxx&a="+ Math.random());
	},
	getTjtj:function(data){
		return this.ajax(data,ctx+"tykf/request?tld=TycxInfoService_getTjtj&a="+ Math.random());
	},
	getColdata:function(data){
		return this.ajax(data,ctx+"tykf/request?tld=TycxInfoService_getColdata&a="+ Math.random());
	},
	searchSington:function(data){
		return this.ajax(data,ctx+"tykf/request?tld=TycxInfoService_searchSington&a="+ Math.random());
	},
	testZqData:function(data){
		return this.ajax(data,ctx+"tykf/request?tld=TycxInfoService_testZqData&a="+ Math.random());
	},
	executeQuery:function(data){
		return this.ajax(data,ctx+"tykf/request?tld=Tycx002DzcxService_executeQuery&a="+ Math.random());
	}
		
}
 