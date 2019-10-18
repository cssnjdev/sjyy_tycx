var $navigation = $("#resouce-navigation");
$(document).ready(function () {
    //使用递归处理菜单
    var str = "";
    var forTree = function (o) {
        for (var i = 0; i < o.length; i++) {
            var urlstr = "";
            try {
                if (!o[i]["url"]) {
                    urlstr = "<div><span><i class='" + o[i]["icon"] + "'></i>&nbsp;&nbsp;" + o[i]["text"] + "</span><ul>";
                } else {
                    urlstr = "<div><span name=" + o[i]["url"] + " onclick='loadMain(this);'><i class='" + o[i]["icon"] + "'></i>&nbsp;&nbsp;" + o[i]["text"] + "</span><ul>";
                }
                str += urlstr;
                if (o[i]["children"].length !== 0) {
                    forTree(o[i]["children"]);
                }
                str += "</ul></div>";
            } catch (e) {
                console.log(e);
            }
        }
        return str;
    };

    var menuTree = function () {
        $navigation.find("ul").each(function (index, element) {
            var ulContent = $(element).html();
            var spanContent = $(element).siblings("span").html();
            if (ulContent) {
                $(element).siblings("span").html(spanContent)
            }
        });
        $navigation.find("div span").click(function () {
            var ul = $(this).siblings("ul").hide(300);
            if (ul.find("div").html() != null) {
                if (ul.css("display") === "none") {
                    ul.show(300);
                } else {
                    ul.hide(300);
                }
            }
        });
        $navigation.find("div>span").click(function () {
            var ul = $(this).parent().siblings().find(">ul");
            ul.hide(300);
        })
    };
    $.post(ctx + "menu/getUserMenu", {"userName": userName}, function (r) {
        if (r.code === 0) {
            var data = r.msg;
            var $crollbarInner = $(".scrollbar-inner");
            document.getElementById("resouce-navigation").innerHTML = forTree(data.children);
            menuTree();
            $crollbarInner[0] && $crollbarInner.scrollbar().scrollLock()
        } else {
            $CWKS.n_danger(r.msg);
        }
    });
});

$(function () {
    var $resourceTableForm = $(".resource-table-form");
    var settings = {
        url: ctx + "resources-register/query",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                resourceId: $resourceTableForm.find("input[name='resourceId']").val().trim(),
                zymc: $resourceTableForm.find("input[name='zymc']").val(),
                zyzt: $resourceTableForm.find("select[name='zyzt']").val(),
                yxBj: $resourceTableForm.find("select[name='yxBj']").val()
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'zyid',
            visible: false
        }, {
            field: 'resourceId',
            title: '资源调用id'
        }, {
            field: 'zymc',
            title: '资源名称'
        }, {
            field: 'zyyxqq',
            title: '资源有效期起'
        }, {
            field: 'zyyxqz',
            title: '资源有效期止'
        }, {
            field: 'zcrq',
            title: '注册时间'
        }, {
            field: 'zylx',
            title: '资源类型'
        }, {
            field: 'cslx',
            title: '传输类型'
        }, {
            field: 'fwlx',
            title: '服务类型'
        }, {
            field: 'zygsdq',
            title: '资源归属地区'
        }, {
            field: 'yxBj',
            title: '状态',
            formatter: function (value, row, index) {
                if (value === '1') return '<span class="badge badge-success">有效</span>';
                if (value === '0') return '<span class="badge badge-warning">锁定</span>';
            }
        }
        ]
    };
    $CWKS.initTable('resourcesTable', settings);
});

function search() {
    $CWKS.refreshTable('resourcesTable');
}

function refresh() {
    $(".user-table-form")[0].reset();
    $CWKS.refreshTable('resourcesTable');
}

function deleteUsers() {
    var selected = $("#resourcesTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $CWKS.n_warning('请勾选需要删除的资源！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].userId;
        if (i !== (selected_length - 1)) ids += ",";
        if (userName === selected[i].username) contain = true;
    }
    if (contain) {
        $CWKS.n_warning('无法删除！');
        return;
    }

    $CWKS.confirm({
        text: "确定删除选中资源？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'user/delete', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $CWKS.n_success(r.msg);
                refresh();
            } else {
                $CWKS.n_danger(r.msg);
            }
        });
    });
}

function exportResourceExcel() {
    $.post(ctx + "user/excel", $(".resource-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $CWKS.n_warning(r.msg);
        }
    });
}

function exportResourceCsv() {
    $.post(ctx + "user/csv", $(".resource-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $CWKS.n_warning(r.msg);
        }
    });
}

/**
 * 用于加载菜单内容
 * @param obj
 */
function loadMain(obj) {
    // 设置面包屑
    var $this = $(obj);
    $(".navigation").find("span").removeClass("navigation__active");
    $this.addClass("navigation__active").parents("ul").prev().addClass("navigation__active");

    var breadcrumnHtml = "";
    var target_text = $this.text();
    var text_arr = [];
    var parent = $this.parents("ul").prev().each(function () {
        var $this = $(this);
        text_arr.unshift($this.text());
    });
    for (var i = 0; i < text_arr.length; i++) {
        breadcrumnHtml += '<li class="breadcrumb-item">' + text_arr[i] + '</li>';
    }
    breadcrumnHtml += '<li class="breadcrumb-item">' + target_text + '</li>';
    $breadcrumb.html("").append(breadcrumnHtml);

    // 加载内容
    var $name = $this.attr("name");
    $.post(ctx + $name, {}, function (r) {
        //debugger
        if (typeof r.code != "undefined" && r.code != "") {
            $CWKS.n_info(r.code+":"+r.msg);
            return;
        }
        if (r.indexOf('账户登录') !== -1) {
            location = location;
            return;
        }
        clearInterval(rediskeysSizeInterval);
        clearInterval(redisMemoryInfoInterval);
        $main_content.html("").append(r);
    });
}