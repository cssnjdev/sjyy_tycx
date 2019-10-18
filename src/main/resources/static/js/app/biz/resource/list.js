$(function () {
    $.post(ctx + 'resource/getResources', {}, function (r) {
        if (r.code === 0) {
            var data = JSON.parse(r.msg);
            var resource_list = data.ms;
            var resource_list_html = "";
            for (var i = 0; i < resource_list.length; i++) {
                resource_list_html += '<div class="col-xl-3 col-lg-3 col-sm-4 col-6">';
                resource_list_html += '<div class="groups__item">';
                resource_list_html += '<li class="resource-img aspectration" data-ratio="16:9" style="background-image:url(' + resource_list[i].img + ');"></li>';
                resource_list_html += '<div class="groups__info">';
                resource_list_html += '<strong>' + resource_list[i].tCn + '</strong>';
                resource_list_html += '<small>' + resource_list[i].resourceType + '</small>';
                resource_list_html += getScoreHtml(resource_list[i].r);
                resource_list_html += '</div>';
                resource_list_html += '<div class="dropdown-menu dropdown-menu-right resource-action" style="min-width:100px;background-color:rgba(255,255,255,.9);z-index:1">';
                resource_list_html += '<a class="dropdown-item" onclick="getMoiveDetail(\'' + resource_list[i].id + '\');" href="javascript:void(0)">查看详情</a>';
                resource_list_html += '<a class="dropdown-item" onclick="getMoiveComments(\'' + resource_list[i].id + '\',\'' + resource_list[i].tCn + '\');" href="javascript:void(0)">查看评论</a>';
                resource_list_html += '</div></div></div>';
            }
            $(".resource-list").html("").append(resource_list_html);
            $(".groups__item").each(function () {
                var $this = $(this);
                $this.mouseenter(function () {
                    $this.find(".resource-action").show();
                });
                $this.mouseleave(function () {
                    $this.find(".resource-action").hide();
                });
            });
        } else {
            $CWKS.n_danger(r.msg);
        }
    });
});

function getScoreHtml(score) {
    var html = '';
    if (score >= 9) {
        html = '<div class="widget-ratings__star">' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star active"></i>&nbsp;' + score + '</div>';
    } else if (score >= 8) {
        html = '<div class="widget-ratings__star">' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star"></i>&nbsp;' + score + '</div>';
    } else if (score >= 6) {
        html = '<div class="widget-ratings__star">' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star"></i>' +
            '<i class="zmdi zmdi-star"></i>&nbsp;' + score + '</div>';
    } else if (score >= 4) {
        html = '<div class="widget-ratings__star">' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star"></i>' +
            '<i class="zmdi zmdi-star"></i>' +
            '<i class="zmdi zmdi-star"></i>&nbsp;' + score + '</div>';
    } else if (score >= 2) {
        html = '<div class="widget-ratings__star">' +
            '<i class="zmdi zmdi-star active"></i>' +
            '<i class="zmdi zmdi-star"></i>' +
            '<i class="zmdi zmdi-star"></i>' +
            '<i class="zmdi zmdi-star"></i>' +
            '<i class="zmdi zmdi-star"></i>&nbsp;' + score + '</div>';
    } else {
        html = '<div class="widget-ratings__star">' +
            '<i class="zmdi zmdi-star"></i>' +
            '<i class="zmdi zmdi-star"></i>' +
            '<i class="zmdi zmdi-star"></i>' +
            '<i class="zmdi zmdi-star"></i>' +
            '<i class="zmdi zmdi-star"></i>&nbsp;<span style="font-size:1rem">暂无评分</span></div>';
    }
    return html;
}

function getResourceDetail(id) {
    $.post(ctx + "resource/detail", {"id": id}, function (r) {
        if (r.code === 0) {
            var data = JSON.parse(r.msg).data;
            var basic = data.basic;
            $("#img").attr("src", basic.img);
            var resourceName = basic.name;
            if (basic.nameEn) {
                resourceName += " / " + basic.nameEn;
            }
            $("#name").text("片名：" + resourceName);
            $("#director").text("导演：" + basic.director.name);
            var curr = basic.releaseDate;
            var dateStr = curr.substr(0, 4) + "-" + curr.substr(4, 2) + "-" + curr.substr(6, 2);
            $("#releaseDate").text("上映日期：" + dateStr);
            $("#releaseArea").text("上映地区 / 国家：" + basic.releaseArea);
            $("#mins").text("片长：" + basic.mins);
            $("#is3D").text("是否3D：" + (basic.is3D ? "是" : "否"));
            $("#isIMAX").text("是否IMAX：" + (basic.isIMAX ? "是" : "否"));
            $("#resource-story").text(basic.story);
            var actors = basic.actors;
            var actors_html = "主演：";
            for (var i = 0; i < actors.length; i++) {
                if (!actors[i].name) continue;
                if (i === 0)
                    actors_html += '<a href="' + actors[i].img + '" target="_blank">' + actors[i].name + '</a>';
                else
                    actors_html += ' / <a href="' + actors[i].img + '" target="_blank">' + actors[i].name + '</a>';
            }
            $("#actors_list").html(actors_html);
            var mx_html = '<a href="' + basic.video.hightUrl.replace("https", "http") + '" target="_blank">点击查看</a>';
            $("#previw span").html(mx_html);
            var $form = $('#resource-detail');
            $form.modal();
        } else {
            $CWKS.n_danger(r.msg);
        }
    });
}

function getResourceComments(id, title) {
    $.post(ctx + "resource/comments", {"id": id}, function (r) {
        var data = JSON.parse(r.msg).data;
        var mini = data.mini.list;
        var plus = data.plus.list;
        if (!mini.length && !plus.length) {
            $CWKS.n_warning("该资源暂无评论");
            return;
        }
        $("#resource-comments-modal-title").text("《" + title + "》资源评论");
        var comments_html = "";
        for (var i = 0; i < mini.length; i++) {
            comments_html += '<div class="listview__item">';
            comments_html += '<label class="custom-control custom-control--char todo__item">';
            comments_html += '<span class="custom-control-char"><img src="' + mini[i].headImg + '"/></span>';
            comments_html += '<div class="todo__info">';
            comments_html += '<span style="display:inline-block">' + mini[i].nickname + '</span>&nbsp;&nbsp;';
            comments_html += '<small style="display:inline-block">' + getDate(mini[i].commentDate) + '</small>';
            comments_html += '</div><div class="comments__info" style="padding: 6px 0">';
            comments_html += '<span>' + mini[i].content + '</span></div></label></div>';
        }
        for (var i = 0; i < plus.length; i++) {
            comments_html += '<div class="listview__item">';
            comments_html += '<label class="custom-control custom-control--char todo__item">';
            comments_html += '<span class="custom-control-char"><img src="' + plus[i].headImg + '"/></span>';
            comments_html += '<div class="todo__info">';
            comments_html += '<span style="display:inline-block">' + plus[i].nickname + '</span>&nbsp;&nbsp;';
            comments_html += '<small style="display:inline-block">' + getDate(plus[i].commentDate) + '</small>';
            comments_html += '</div><div class="comments__info" style="padding: 6px 0">';
            comments_html += '<span>' + plus[i].content + '</span></div></label></div>';
        }
        $(".listview--bordered").html("").append(comments_html);
        var $form = $('#resource-comments');
        $form.modal();
    });
}

function getDate(tm) {
    return new Date(tm * 1000).toLocaleString();
}