$(function(){
    $.getJSON(
        "/infonet/TypesForNav",
        function (result) {
            var count = parseInt(result.length);//得到类别总数
            var html = "";
            $.each(result, function () {
                html = html + "<option><a href=/infonet/news?option=-" + this.typeId + ">" + this.typeName + "</a></option>";
            });
            $("#myoption").html(html);
        }
    )
})


// //嵌套在页面中，文档初始化时加载。
// var menubox = document.getElementById("area"); //area为菜单栏的id
// var cli_on = document.getElementById("on"); //on为按钮
// var flag = false, timer = null, initime = null, r_len = 0;
//
// if(menubox.style.right=== 0){
//     flag = true;
// }
// else{
//     flag = false;
// }
// cli_on.onclick = function () {
//     //为on按钮绑定click事件
//     clearTimeout(initime);
//     //根据状态flag执开展开收缩
//     if (flag) {
//         r_len = 0;
//         timer = setInterval(slideright, 10);
//     } else {
//         r_len = -125;
//         timer = setInterval(slideleft, 10);
//     }
// }
// //展开
// function slideright() {
//     if (r_len <= -125) {
//         clearInterval(timer);
//         flag = !flag;
//         return false;
//     }else{
//         r_len -= 5;
//         menubox.style.right = r_len + 'px';
//     }
// }
// //收缩
// function slideleft() {
//     if (r_len >= 0) {
//         clearInterval(timer);
//         flag = !flag;
//         return false;
//     } else {
//         r_len += 5;
//         menubox.style.right = r_len + 'px';
//     }
// }
//
// //回车搜索
// function searchfor(element){
//     var event = window.event;
//     if(event.keyCode ==13){
//         var keyword = $(element).val();alert("/infonet/search?isMana=true&queryString="+keyword);
//         window.location.href = "/infonet/search?isMana=true&queryString="+keyword;
//     }
// }


function changeoption(element){
    var option = $(element).val();
    alert(option);
}