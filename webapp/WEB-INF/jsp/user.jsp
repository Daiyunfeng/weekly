<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title></title>
<link rel="shortcut icon" href="${ctx}/res/images/favicon.ico">
<link rel="stylesheet" href="${ctx}/res/css/zerogrid.css">
<link rel="stylesheet" href="${ctx}/res/css/style.css">
<link rel="stylesheet" href="${ctx}/res/css/menu.css">
<link href="${ctx}/res/css/bootstrap.min.css" rel="stylesheet">
<script src="${ctx}/res/js/jquery.min.js"></script>
<style type="text/css">
.head{
    display: block;
    margin: 5% auto 5%;
    height: 290px;
    width: 220px;
    border: 3px solid #fff;
    border-radius: 5px;
    box-shadow: 0 0 5px rgba(0,0,0,.15);
    overflow: hidden;
}

.info{
	display: block;
	margin: 0 auto;
    width: 50%;
    text-align:center;
    font-size:17px;
    font-family:'Merriweather', Georgia, serif;
}
</style>
</head>
<body>
	<jsp:include page="/nav"></jsp:include>
	<script type="text/javascript">
    $(".userList").addClass("active");
	</script>
	<div class="container" id="crop-avatar">
		<p id="ID" hidden>${ID}</p>
		<div class="head">
			<img id="headImage" src="" alt="头像" style="width: 100%;height: 100%">
		</div>
		<div class="info">
			<h2 id="userID" style = "font-family: 'Cabin', Helvetica, sans-serif;font-size: 24px;"></h2>
			<p id="realname"></p>
			<p id="role"></p>
			<p id="phone"></p>
			<a id="number" href="#"></a>
		</div>
	</div>

  <script src="${ctx}/res/js/script.js"></script>
  <script src="${ctx}/res/js/bootstrap.min.js"></script>
<script type="text/javascript">
var ID = $("#ID").text();
	$.ajax(
	{
		type : 'post',
		url : '${ctx}/findUserByIdAjax',
		dataType : 'json',
		data : 
		{
			userID : ID,
		},
		success : function(data) 
		{
			if (data.flag == "1") 
			{
				$("title").html(data.res.realname);
				$("#headImage").attr("src",data.res.head); 
				$("#userID").html(data.res.userID); 
				$("#realname").html(data.res.realname);
				$("#role").html(data.res.role);
				$("#phone").html(data.res.phone);
				$("#number").html("拥有"+data.res.articleNumber+"篇周报");
				$("#number").attr("href","${ctx}/list/"+data.res.userID); 
			} 
			else if (data.flag == "0") 
			{
				alert(data.msg);
				window.history.back(-1);
			} 
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) 
		{
			 alert(XMLHttpRequest.status+"	"+XMLHttpRequest.readyState+"	"+textStatus);
		}
	});
</script>
</body>
</html>
