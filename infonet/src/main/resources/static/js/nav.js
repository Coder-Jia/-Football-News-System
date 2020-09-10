$(function () {
    //定位登陆/注册前页面地址
    $("#validate").attr("href",$("#validate").attr("href")+"?referer="+window.document.URL );

    //用于显示导航栏类别
    $.getJSON(
        "/infonet/TypesForNav",
        function (result) {
            var count = parseInt(result.length);//得到类别总数
            var html = "";
            $.each(result, function (index) {
                if (index == 5) {
                    var head_html = "<li class='dropdown'><a class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'>更多分类  <span class='caret'></span></a><ul class='dropdown-menu'>";
                    html = html + head_html + "<li><a href=/infonet/NewsListWithType?TypeID=" + this.typeId + ">" + this.typeName + "</a></li>";
                } else if (index == count - 1) {
                    var tail_html = "</ul></li>";
                    html = html + "<li><a href=/infonet/NewsListWithType?TypeID=" + this.typeId + ">" + this.typeName + "</a></li>" + tail_html;
                } else {
                    html = html + "<li><a href=/infonet/NewsListWithType?TypeID=" + this.typeId + ">" + this.typeName + "</a></li>";
                }
            });
            $("#mytypelist").html(html);
            $(".navbar-form").removeClass("hidden");
        }
    )
})