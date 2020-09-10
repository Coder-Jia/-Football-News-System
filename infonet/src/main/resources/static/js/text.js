$(function(){
    //评论框判断字数提交
    $("#commentBtn").click(function(){
        if( $("#Comment_content").val().length > 50){
            alert("字数统计："+$("#Comment_content").val().length+",大于50字，超限！");
        }else{
            $("#Commentform").submit();
        }
    })
})


//AJAX分页显示评论
//获得评论函数
function CommentList(newsId, pageSize, userID) {//pageSize为当前页显示多少条评论,userID标识：是否登录,若有，则为用户编号，没有则为0
    console.log("登陆结果：" + userID);
    $.get(
        "/infonet/CommentQueryByPage",
        {"newsID": newsId, "pageSize": pageSize},
        function (responsedata) {
            var result = responsedata.list;
            if (result.length > 0) {//有评论
                var HTML = '';
                $.each(result, function (index) {
                    var username = this.netuser.userName;
                    //将时间戳转化为字符串	var comments = result;//object
                    //var totalCount = comments.length;//object
                    var timestamp = this.commentDate;//获取时间戳的毫秒数
                    var d = new Date(timestamp); //根据时间戳生成的时间对象
                    var dateString = (d.getFullYear()) + "-" +
                        (d.getMonth() + 1) + "-" +
                        (d.getDate()) + " " +
                        (d.getHours()) + ":" +
                        (d.getMinutes()) + ":" +
                        (d.getSeconds());

                    //评论部分
                    HTML = HTML + '<!-- 网友评论    -->' +
                        '<div class="media" style="margin-top:35px;">' +
                        ' <div class="media-left  media-top">' +
                        '<a>' +
                        '<div class="media-object" style="width:100px;height:80px;">'+
                        '<img  src="/infonet/css/icon/'+ this.netuser.userSex +'.png" alt="网友头像" style="width:75px;">' +
                        '</div>'+
                        '</a>' +
                        '</div>' +
                        '<div class="media-body">' +
                        '<h4 class="media-heading">' +
                        '<b><i>' + username + '</i></b>' +
                        '<span class="badge" style="float:right;margin-left:2px;margin-top:4px;" id="Like_Comment_num' + this.commentId + '">' + this.commentLike + '</span>';
                        if(userID!=0){
                            HTML +=  '<img src="/infonet/css/icon/like.png" style="float:right;width:22px;height:22px;cursor:pointer;" title="点赞" id="Like_Comment' + this.commentId + '">'
                              + '<img src="/infonet/css/icon/reply.png" style="float:right;width:25px;height:25px;cursor:pointer;margin-right:20px;" title="回复" id="reply_Comment' + this.commentId + '">';
                        }else{
                            HTML +=  '<img src="/infonet/css/icon/chot.png" style="float:right;width:22px;height:22px;" title="热度" id="Like_Comment' + this.commentId + '">';
                        }
                         HTML+= '</h4>' +
                        '<textarea rows="5" readonly="readonly" style="width:90%;height:80px;resize:none;border:none;cursor:default;outline: none;">' + this.commentContent + '&#10;' + dateString + '</textarea>' +

                        '<!-- 回复评论窗口 -->' +
                        '<form action="/infonet/reply" method="post" class="hidden" id="replyConfirm' + this.commentId + '">' +
                        '<input type="hidden" value="' + this.commentId + '" name="commentId">' +//评论编号
                        '<input type="hidden" value="' + this.newsId + '" name="newsID">' +//资讯编号
                        '<input type="hidden" name="userId" id="userId' + this.commentId + '">' +//用户编号
                        '<input type="hidden" name="referer" value="'+window.document.URL+'">' +//返回地址
                        '<div class="media" style="border-top:1px solid lightgray;padding-top:10px;width:90%;">' +
                        '<div class="media-left  media-top">' +
                        '<img class="media-object" src="/infonet/css/images/Messy.jpg" alt="网友头像" style="width:100px;height:80px;">' +
                        '</div>' +
                        '<div class="media-body">' +
                        '<textarea rows="5" style="width:90%;height:80px;resize:none;" placeholder="输入你的回复.." class="form-control" name="replyContent" id="replyContent' + this.commentId + '"></textarea>' +
                        '<button type="button" class="btn btn-info btn-xs" id="replybtn' + this.commentId + '" style="margin-left:85%;">回复</button>' +
                        '</div>' +
                        '</div>' +
                        '</form>' +
                        '<script>' +
                        //回复图标点击事件      这里的命名页添加上id是因为后面同名的变量，会覆盖前面的变量
                        'var selector_replyConfirm' + this.commentId + ' = "#replyConfirm' + this.commentId + '";' +
                        'var selector_reply_Comment' + this.commentId + ' = "#reply_Comment' + this.commentId + '";' +
                        'var selector_userId' + this.commentId + ' = "#userId' + this.commentId + '";' +
                        'var selector_replyContent' + this.commentId + ' = "#replyContent' + this.commentId + '";' +
                        'var selector_replybtn' + this.commentId + ' = "#replybtn' + this.commentId + '";' +
                        //表单show
                        '$(selector_reply_Comment' + this.commentId + ').click(function(){' +
                        '$(selector_replyConfirm' + this.commentId + ').removeClass("hidden").addClass("show");' +
                        '});' +


                        //回复表单提交
                        '$(selector_replybtn' + this.commentId + ').click(function(e){' +
                        'if( $(selector_replyContent' + this.commentId + ').val().length == ""){' +
                        'alert("评论不能为空！");' +
                        '}else if($(selector_replyContent' + this.commentId + ').val().length > 50){' +
                        'alert("字数统计："+$(selector_replyContent' + this.commentId + ').val().length+"，大于50字，超限！");' +
                        '}else{' +
                        '$(selector_userId' + this.commentId + ').val(' + userID + ');' +
                        '$(selector_replyConfirm' + this.commentId + ').submit();' +
                        '}' +
                        '})' +
                        '</script>' +

                        '<!-- 网友回复评论 -->' +
                        '<div class="replyList">' + Reply(this.commentId) + '</div>' +
                        /*clickHTML+*/

                        '</div> ' +
                        '</div>  <!--  网友评论 -->';
                    //一句评论

                    //评论点赞  单击事件
                    HTML+=
                    '<script>'+
                    '$(function(){'+
                    'var selector = "#Like_Comment'+this.commentId+'";'+
                    'var ID ='+this.commentId+';'+ //用于点赞时使用，冗余了对不起
                    '$(selector).click(function (e) {'+
                        'console.log("===="+selector);'+
                        'e.preventDefault();'+
                        'if ('+userID+' != 0) {'+
                            'var src = "";'+
                            'var Like_Comment_num = parseInt($("#Like_Comment_num").text());'+
                            'var like = 0;'+
                            'if ($(selector).attr("src") == "/infonet/css/icon/liked_comment.png") {'+
                                'src = "/infonet/css/icon/like.png";'+
                                'like = -1;'+
                            '} else {'+
                                'src = "/infonet/css/icon/liked_comment.png";'+
                                'like = 1;'+
                            '}'+
                            '$.post('+
                                '"/infonet/commentLike",'+
                                '{"ID": ID, "like": like},'+
                                'function (result) {'+
                                    'if (result === "Like success") {'+
                                        '$(selector).attr("src", src);'+
                                        '$("#Like_Comment_num").text(Like_Comment_num + like);'+
                                        'var spanText = "#Like_Comment_num" + ID;'+
                                        '$(spanText).text(parseInt($(spanText).text()) + like);'+
                                    '} else {'+
                                        'alert(result);'+
                                    '}'+
                                '}'+
                            ');'+
                        '}'+//未登录
                    '});'+//click事件
                    '})'+
                    '</script>';

                });//遍历
                if (responsedata.hasNextPage) {
                    $("#CommentAndReply").html(HTML + "<a onclick='showMore(" + newsId + "," + (responsedata.size+5) + "," + userID + ")' style='cursor:pointer;width:120px;text-align:center;text-decoration:underline;position:absolute;left:50%;margin-left:-60px;'>点击显示更多</a>");
                } else {
                    $("#CommentAndReply").html(HTML + "<a style='width:200px;text-align:center;font-size:small;position:absolute;left:50%;margin-left:-100px;padding-bottom:50px;font-family:fantasy;font-weight:bold;'>暂无更多评论噢，快来建楼吧！</a>")
                }
            } else {
                $("#CommentAndReply").html("<a style='width:200px;text-align:center;font-size:small;position:absolute;left:50%;margin-left:-100px;padding-bottom:50px;font-family:fantasy;font-weight:bold;'>暂无评论噢，快来抢沙发！</a>")
            }
        }
    )
}

//显示更多按键
function showMore(newsId, pageSize, userId) {
    CommentList(newsId, pageSize, userId);//调用显示评论函数
}

//根据评论编号查找回复,pageSize为一次显示数量
function Reply(comment_id) {
    HTML = ' ';
    $.ajaxSettings.async = false;//设置getJSON为同步
    //获取回复
    $.getJSON(
        "/infonet/ReplyQueryByPageServlet",
        {"comment_id": comment_id, "pageSize": 10},
        function (result) {
            if (result.length > 0) {//存在回复
                var replys = result;//得到回复
                var ifHidden = ' ';
                $.each(replys, function (index) {
                    var userName = this.netuser.userName;//得到网名
                    var userSex = this.netuser.userSex;//得到性别
                    if (index >= 2) {
                        ifHidden = ' hidden';
                        if (index == 2) {
                            HTML = HTML + '<a onclick="Replymore(this)" style="cursor:pointer;width:120px;text-align:center;text-decoration:underline;position:absolute;left:70%;margin-left:-60px;">show more..</a>';
                        }
                    }
                    //console.log("下标" + index);
                    //将时间戳转化为字符串
                    var timestamp = this.replyDate;//获取时间戳的毫秒数
                    var d = new Date(timestamp); //根据时间戳生成的时间对象
                    var dateString = (d.getFullYear()) + "-" +
                        (d.getMonth() + 1) + "-" +
                        (d.getDate()) + " " +
                        (d.getHours()) + ":" +
                        (d.getMinutes()) + ":" +
                        (d.getSeconds());

                    HTML = HTML + '<div class="media' + ifHidden + '" style="border-top:1px solid lightgray;padding-top:10px;width:90%;">' +
                        '<div class="media-left  media-top">' +
                        '<div style="width:90px;height:70px;">'+
                        '<img class="media-object" src="/infonet/css/icon/' + userSex + '_r.png" alt="网友头像" style="width:70px;">' +
                        '</div>'+
                        '</div>' +
                        '<div class="media-body">' +
                        '<h4 class="media-heading">' +
                        '<b><i>' + userName + '</i></b>' +
                        '</h4>' +
                        '<textarea rows="5" readonly="readonly" style="width:90%;height:80px;resize:none;border:none;cursor:default;outline: none;">' + this.replyContent + '&#10;' + dateString + '</textarea>' +
                        '</div>' +
                        '</div>';
                })
            }
        }
    );
    $.ajaxSettings.async = false;//恢复getJSON为异步
    return HTML;
}

function Replymore(element) {
    var $List = $(".replyList>div");
    for (var i = 0; i < $List.length; i++) {
        if ($($List[i]).hasClass("hidden")) {
            $($List[i]).removeClass("hidden");
        }
    }
    $(element).addClass("hidden");
}
