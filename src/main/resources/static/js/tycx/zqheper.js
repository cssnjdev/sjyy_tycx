

function modalQuIframe(url){  // 模态窗口iframe钻取
	
	
	$("#mlbox .modal-body").html(
	'<iframe src="'+url+'"   style="width: 100%; height: 100%;overflow: hidden;" frameborder=0 > </iframe> 	'				
	);
	
	$("#mlbox").modal("show");
	
}

function divQuIframe(url,obj){ // div 显示iframe钻取
	
	
	 
}