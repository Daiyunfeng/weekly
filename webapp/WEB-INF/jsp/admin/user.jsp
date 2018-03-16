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
    margin: 3% auto 3%;
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
	<jsp:include page="/admin/nav"></jsp:include>
	<script type="text/javascript">
    $(".userList").addClass("active");
	</script>
	<div class="container" id="crop-avatar">
		<p id="ID" hidden>${ID}</p>
		<div class="head">
			<img id="headImage" src="" alt="头像" style="width:100%;height: 100%">
		</div>
		<div class="info">
			<h2 id="userID" style = "font-family: 'Cabin', Helvetica, sans-serif;font-size: 24px;"></h2>
			<p id="realname"></p>
			<p id="hideonbush" hidden></p>
			<select id="role" class="form-control" style="width:20%; margin:0 auto;">
				<option value ="1">学生</option>
				<option value ="2">教师</option>
				<option value ="3" disabled="disabled">管理员</option>
			</select>
			<p id="phone"></p>
			<a id="number" href="#" style="display:block;"></a>
			<button class="btn btn-default btn-danger" id="reset" onclick="reset()" style="margin:20px auto 0;width:50%;">重置密码</button>
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
				if(data.res.role == "管理员")
				{
					$("#role").val("3");
					$("#role").attr("disabled",true);
				}
				if(data.res.role == "教师")
				{
					$("#role").val("2");
				}
				$("#hideonbush").text($("#role").val());
				$("#role").bind('change',changeRole);
				$("#phone").html(data.res.phone);
				$("#number").html("拥有"+data.res.articleNumber+"篇周报");
				$("#number").attr("href","${ctx}/admin/list/"+data.res.userID); 
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
	function changeRole()
	{
		var flag = confirm("确认将此用户 "+ $("#userID").text() +" 权限修改为 " + $("#role").find("option:selected").text());
		if(flag == false)
		{
			$("#role").val($("#hideonbush").text());
		}
		else
		{
			$("#hideonbush").text($("#role").val());
			var role = $("#role").val();
			$.ajax(
			{
				type : 'post',
				url : '${ctx}/admin/changeRoleAjax',
				dataType : 'json',
				data : 
				{
					userID : ID,
					roleID : role,
				},
				success : function(data) 
				{
					if (data.flag == "1") 
					{
						alert("修改成功")
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
	}
	
	
	function reset()
	{
		var flag = confirm("确认重置用户:  "+ ID +" 密码?");
		if(flag == false)
		{
			return;
		}
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/resetPasswordAjax',
			dataType : 'json',
			data : 
			{
				userID : ID,
			},
			success : function(data) 
			{
				if (data.flag == "1") 
				{
					alert("重置成功")
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
