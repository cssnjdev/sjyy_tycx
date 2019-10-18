$(function() {
	 initFileInput("input-id");  
     var win_id = '<%=win_id%>';
     $("#colseid").click(function() {
     	if(win_id){
     		parent.Ext.getCmp(win_id).close();
			}
     });
});
function initFileInput(ctrlName) { 
    var control = $('#' + ctrlName); 
    var url = ctx + "test-fileupload/upload";
    control.fileinput({  
        language: 'zh', //设置语言  
        uploadUrl: url,  
        allowedFileExtensions: ['tif','jpg','png','gif','bmp','txt','html','xml','pdf','doc','xls','ppt','docx','xlsx','pptx','zip','rar','mp3','mov','avi','mpp'],//接收的文件后缀  
        maxFilesNum : 5,//上传最大的文件数量  
        //uploadExtraData:function(previewId, index) { return extraParams; }, 
        uploadAsync: true, //默认异步上传  
        showUpload: true, //是否显示上传按钮  
        showRemove : false, //显示移除按钮  
        showPreview : true, //是否显示预览  
        showCaption: false,//是否显示标题  
        browseClass: "btn btn-primary", //按钮样式  
        dropZoneEnabled: true,//是否显示拖拽区域  
        //minImageWidth: 50, //图片的最小宽度  
        //minImageHeight: 50,//图片的最小高度  
        //maxImageWidth: 300,//图片的最大宽度  
        //maxImageHeight: 200,//图片的最大高度  
        maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小  
        minFileCount: 0,  
        maxFileCount: 5, //表示允许同时上传的最大文件个数  
        enctype: 'multipart/form-data',  
        validateInitialCount:true,  
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",  
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",  

    }).on('filepreupload', function(event, data, previewId, index) {     //上传中  
        var form = data.form, files = data.files, extra = data.extra,  
        response = data.response, reader = data.reader;  
        console.log('文件正在上传');  
    }).on("fileuploaded", function (event, data, previewId, index) {    //一个文件上传成功  
        console.log('文件上传成功！'+data.id);  

    }).on('fileerror', function(event, data, msg) {  //一个文件上传失败  
        console.log('文件上传失败！'+data.id);  
    }).on('fileremoved', function(event, data) { 
        alert("fileremoved")
    }).on('filepreremove', function(event, data) { 
        alert("filepreremove")
    })
    }  