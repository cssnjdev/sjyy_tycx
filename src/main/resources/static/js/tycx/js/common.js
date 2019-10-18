//获取form元素生成JSON
function getFormJSON(formId){
    var queryArray = $(formId).serializeArray();
    var paramString ="{";
    for (i=0,l=queryArray.length; i<l; i++){
        var field = queryArray[i];
        paramString +="'" + field.name + "':'" + field.value + "'" + (i<l-1 ? "," : "");
    }
    paramString +="}";
    //alert(paramString);
    return eval("(" + paramString + ")");
}

//初始化报表生成页面
function expChart(tid){
    var opts = $('#'+tid).datagrid('getColumnFields'); //这是获取到所有的FIELD
    var colPrm ="[";
    var colTypeStr ;
    var chartStr ;
    var chartName ;
    for(i=0;i<opts.length;i++) {
        col = $('#'+tid).datagrid( "getColumnOption" , opts[i] );
        chartStr = "";
        colTypeStr = "";
        chartName = "";
        if(!col.checkbox){
            if(typeof(col.type)!="undefined"){
                colTypeStr = ",'colType':'" + col.type + "'";
            }
            if(typeof(col.chart)!="undefined"){
                chartStr =",'chart':'" + col.chart + "'";
            }
            if(typeof(col.chartname)!="undefined"){
                chartName =",'chartName':'" + col.chartname + "'";
            }
            colPrm +="{'colField':'" + col.field + "','colTitle':'" + col.title + "'"+colTypeStr+chartStr+chartName+"}," ;
        }
    }
    colPrm=(colPrm.substring(colPrm.length-1)==',')?colPrm.substring(0,colPrm.length-1):colPrm;
    colPrm +="]";
    var colData = JSON.stringify($("#"+tid).datagrid(('getRows')));
    //alert($('#'+tid).datagrid("options").url);
    //window.open('/cssnj/http.action?tld=chartService_init&colPrm='+colPrm+'&colData='+colData);
    //创建虚拟表单并提交（post）
    var url = _cssnj_domain_path+"/cssnj/http.action?tld=chartService_init";
    var turnForm = document.createElement("form");
    document.body.appendChild(turnForm);
    turnForm.method = 'post';
    turnForm.action = url;
    turnForm.target = '_blank';
    var newElement = document.createElement("input");
    newElement.setAttribute("name","colPrm");
    newElement.setAttribute("type","hidden");
    newElement.setAttribute("value",colPrm);
    turnForm.appendChild(newElement);
    newElement = document.createElement("input");
    newElement.setAttribute("name","colData");
    newElement.setAttribute("type","hidden");
    newElement.setAttribute("value",colData);
    turnForm.appendChild(newElement);
    turnForm.submit();
}
