$(function () {
    getGuide("");
    $(".prev-guide span").click(function () {
        location.href = "#";
        var date = $(this).attr("name");
        getGuide(date);
    });
    $(".next-Guide span").click(function () {
        location.href = "#";
        var date = $(this).attr("name");
        var today = $("#today").text();
        if (date > today) {
            $CWKS.n_success("明天还未到来，请耐心等待吧~");
            return;
        }
        getGuide(date);
    });
});

function getGuide(date) {
    $.post(ctx + "guide/query", {"date": date}, function (r) {
        if (r.code === 0) {
            var data = JSON.parse(r.msg);
            $(".card-title").html("").append(data.data.title);
            var curr = data.data.date.curr;
            var newDate = curr.substr(0, 4) + "-" + curr.substr(4, 2) + "-" + curr.substr(6, 2);
            var html = '<span>' + newDate +
                '&nbsp;&nbsp;' + data.data.author + '</span>' +
                '&nbsp;&nbsp;字数 ' + data.data.wc + '</span>';
            $(".card-subtitle").html("").append(html);
            $(".card-block").html("").append(data.data.content);
            $(".prev-guide span").attr("name", data.data.date.prev);
            $(".next-guide span").attr("name", data.data.date.next);
            if (date === "") $("#today").text(curr);
        } else {
            $CWKS.n_warning(r.msg);
        }
    });
}