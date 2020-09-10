//根据评论编号查找评论,pageSize为一次显示数量
function Reply(comment_id,pageSize){
	var HTML = "";
	//获取回复
	$.getJSON(
		"ReplyQueryByPageServlet",
		{"comment_id":comment_id,"pageSize":pageSize},
		function(responseText){
			var json = eval(responseText);
			console.log(json);
		}
	)
}