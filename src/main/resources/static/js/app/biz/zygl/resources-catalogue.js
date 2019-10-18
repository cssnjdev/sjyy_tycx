$(function () {
    var $resourceTableForm = $(".resource-table-form");
    var settings = {
        url: ctx + "resources-catalogue/list",
        expandAll: true,
        expandColumn: "2",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                zymc: $resourceTableForm.find("input[name='zymc']").val(),
                zyid: $resourceTableForm.find("input[name='zyid']").val(),
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'zyid',
            visible: false
        }, {
            field: 'zymc',
            title: '资源名称'
        }, {
            field: 'sjzyid',
            title: '上级资源'
        }, {
            field: 'zyms',
            title: '资源描述'
        }, {
            field: 'lrrq',
            title: '录入时间'
        }]
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