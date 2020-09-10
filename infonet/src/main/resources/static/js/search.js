/**
 * 搜索框js
 * @returns
 */
$(function(){
	$("#searchBtn").click(function(){
		if($("#queryString").val() != ""){
			$("#actionform").submit();
		}else{
			alert("请输入你想要查询的内容噢！");
		}
	})
})