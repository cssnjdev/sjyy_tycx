/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.height = 290;
	config.language = 'zh-cn';
    config.uiColor = '#AADC6E';
    config.image_previewText=' '; //预览区域显示内容
    //config.filebrowserUploadUrl="/uploadCtrl/upload.action?tld=UpLoadService_upload";
    config.filebrowserUploadUrl="/ImageUploadUtil/imageUpload.action?tld=UpLoadService_imageUpload";
};
