$(function () {
    var $userTableForm = $(".access-table-form");
    var settings = {
        url: ctx + "access-management/query",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                jrdsfid: $userTableForm.find("input[name='jrdsfid']").val().trim(),
                jrdmc: $userTableForm.find("select[name='jrdmc']").val(),
                yxBj: $userTableForm.find("select[name='yxBj']").val()
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'jrdid',
            visible: false
        }, {
            field: 'jrdsfid',
            title: '接入点身份'
        }, {
            field: 'jrdmc',
            title: '接入点名称'
        }, {
            field: 'yhid',
            title: '所属用户'
        }, {
            field: 'jrdip',
            title: '接入点ip'
        }, {
            field: 'lrrq',
            title: '创建时间'
        }, {
            field: 'xgrq',
            title: '修改时间'
        }, {
            field: 'sfysjkjr',
            title: '是否有数据库接入',
            formatter: function (value, row, index) {
                if (value === 'Y') return '有';
                if (value === 'N') return '无';
            }
        }, {
            field: 'sjklx',
            title: '数据库类型'
        }, {
            field: 'sjkdz',
            title: '数据库地址'
        }, {
            field: 'dlkl',
            title: '数据库登陆账户'
        }, {
            field: 'gsdq_dm',
            title: '接入点归属地区代码'
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
    $CWKS.initTable('accessTable', settings);
});