/**
 * isCanvasSupported
 */
//判断是否存在画布
function isCanvasSupported() {
    var elem = document.createElement('canvas');
    return !!(elem.getContext && elem.getContext('2d'));
}

//压缩方法
function compress(event, callback) {
    if ( typeof (FileReader) === 'undefined') {
        console.log("当前浏览器内核不支持base64图标压缩");
        //调用上传方式  不压缩
    } else {
        try {
            var file = event.currentTarget.files[0];
            if(!/image\/\w+/.test(file.type)){
                alert("请确保文件为图像类型");
                return false;
            }
            var reader = new FileReader();
            reader.onload = function (e) {
                var image = $('<img/>');
                image.load(function () {
                    //console.log("开始压缩");
                    var square = 700;
                    var canvas = document.createElement('canvas');
                    canvas.width = square;
                    canvas.height = square;
                    var context = canvas.getContext('2d');
                    context.clearRect(0, 0, square, square);
                    var imageWidth;
                    var imageHeight;
                    var offsetX = 0;
                    var offsetY = 0;
                    if (this.width > this.height) {
                        imageWidth = Math.round(square * this.width / this.height);
                        imageHeight = square;
                        offsetX = - Math.round((imageWidth - square) / 2);
                    } else {
                        imageHeight = Math.round(square * this.height / this.width);
                        imageWidth = square;
                        offsetY = - Math.round((imageHeight - square) / 2);
                    }
                    context.drawImage(this, offsetX, offsetY, imageWidth, imageHeight);
                    var data = canvas.toDataURL('image/jpeg');
                    //压缩完成执行回调
                    callback(data);
                });
                image.attr('src', e.target.result);
            };
            reader.readAsDataURL(file);
        } catch(e) {
            console.log("压缩失败!");
            //调用上传方式  不压缩
        }
    }
}

function ImgToBase64(file, maxLen, callBack) {
    var img = new Image();

    var reader = new FileReader();//读取客户端上的文件
    reader.onload = function () {
        var url = reader.result;//读取到的文件内容.这个属性只在读取操作完成之后才有效,并且数据的格式取决于读取操作是由哪个方法发起的.所以必须使用reader.onload，
        img.src = url;//reader读取的文件内容是base64,利用这个url就能实现上传前预览图片
    };
    img.onload = function () {
        //生成比例
        var width = img.width, height = img.height;
        //计算缩放比例
        var rate = 1;
        if (width >= height) {
            if (width > maxLen) {
                rate = maxLen / width;
            }
        } else {
            if (height > maxLen) {
                rate = maxLen / height;
            }
        };
        img.width = width * rate;
        img.height = height * rate;
        //生成canvas
        var canvas = document.createElement("canvas");
        var ctx = canvas.getContext("2d");
        canvas.width = img.width;
        canvas.height = img.height;
        ctx.drawImage(img, 0, 0, img.width, img.height);
        var base64 = canvas.toDataURL('image/jpeg', 0.9);
        callBack(base64);
    };
    reader.readAsDataURL(file);
}

// function getBase64Image(img) {
//     var canvas = document.createElement("canvas");
//     canvas.width = img.width;
//     canvas.height = img.height;
//
//     var ctx = canvas.getContext("2d");
//     ctx.drawImage(img, 0, 0, img.width, img.height);
//     var ext = img.src.substring(img.src.lastIndexOf(".")+1).toLowerCase();
//     var dataURL = canvas.toDataURL("image/"+ext);
//     console.log(dataURL);
//     return dataURL.replace("data:image/jpeg;base64,", "");
// }

//传入图片路径，返回base64
function getBase64(img){
    function getBase64Image(img,width,height) {//width、height调用时传入具体像素值，控制大小 ,不传则默认图像大小
        var canvas = document.createElement("canvas");
        canvas.width = width ? width : img.width;
        canvas.height = height ? height : img.height;

        var ctx = canvas.getContext("2d");
        ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
        var dataURL = canvas.toDataURL();
        return dataURL;
    }
    var image = new Image();
    image.crossOrigin = 'anonymous';
    image.setAttribute('crossOrigin', 'anonymous');
    image.src = img;
    var deferred=$.Deferred();
    if(img){
        image.onload =function (){
            deferred.resolve(getBase64Image(image));//将base64传给done上传处理
        }
        return deferred.promise();//问题要让onload完成后再return sessionStorage['imgTest']
    }
}
