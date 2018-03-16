<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${ctx}/res/images/favicon.ico">
<link rel="stylesheet" href="${ctx}/res/css/zerogrid.css">
<link rel="stylesheet" href="${ctx}/res/css/style.css">
<link href="${ctx}/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${ctx}/res/css/menu.css">
<link href="${ctx}/res/css/bootstrap.min.css" rel="stylesheet">
<script src="${ctx}/res/js/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/res/js/script.js"></script>
<link href="${ctx}/res/owl-carousel/owl.carousel.css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${ctx}/res/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/res/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/res/ueditor/lang/zh-cn/zh-cn.js"></script>
<title>添加周报</title>
<style type="text/css">
article.single-post .entry-header {
	padding: 20px 0;
}
input[type="text"] {
	width: 93.4%;
	padding: 10px;
	margin: 0 auto;
	background-color: transparent;
	border: none;
	font-size: 15px;
	border-bottom: 1px solid rgba(17, 17, 17, 0.41);
	outline: none;
	color: #000;
	text-align: center;
}
</style>
</head>
<body>
	<jsp:include page="/admin/nav"></jsp:include>
	<script type="text/javascript">
    $(".addArticle").addClass("active");
	</script>
	<section id="container">
	<div class="wrap-container">
		<!-----------------Content-Box-------------------->
		
		<p id="token" hidden>${token}</p>
		<article class="single-post zerogrid">
		<div class="row wrap-post">
			<!--Start Box-->
			<div class="entry-header">
				<input type="text" id="title" placeholder="请输入标题">
			</div>
			<div class="entry-content" style="width: 100%; height: 700px; padding: 30px 30px;">
				<div class="excerpt" style="width: 100%; height: 500px;">
					<script id="editor" type="text/plain" style="height: 500px;"></script>
				</div>
			</div>
			<button style="margin: 0 auto; display: block;" id="submit" onclick="submit()">提交</button>
		</div>
		</article>
	</div>
<script type="text/javascript">
   //实例化编辑器
   //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
   var ue = UE.getEditor('editor');
   function submit()
   {
		var title = $("#title").val();
		var content = UE.getEditor('editor').getContent();
		var token = $("#token").text();
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/addNewArticleAjax',
			dataType : 'json',
			data : 
			{
				title : title,
				"articleContent.content" : content,
				token : token,
			},
			success : function(data) 
			{
				if (data.flag == "1") 
				{
					var id = data.msg;
					window.location.href="${ctx}/admin/article/"+id; 
				} 
				else if (data.flag == "0") 
				{
					alert(data.msg);
				} 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) 
			{
				 alert(XMLHttpRequest.status+"	"+XMLHttpRequest.readyState+"	"+textStatus);
			}
		});
   }
</script>
</body>
</html>