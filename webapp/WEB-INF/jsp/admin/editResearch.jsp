<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${ctx}/res/images/favicon.ico">
<link rel="stylesheet" href="${ctx}/res/css/style.css">
<link rel="stylesheet" href="${ctx}/res/css/menu.css">
<link rel="stylesheet" href="${ctx}/res/css/bootstrap.css">
<link href="${ctx}/res/css/main.css" rel="stylesheet">
<script src="${ctx}/res/js/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/res/js/script.js"></script>
<script src="${ctx}/res/js/bootstrap.min.js"></script>
<title>修改研究方向</title>
<style type="text/css">
.logo>a:hover{ text-decoration:none; }
input{
padding: 5px;
border: 1px solid #d1d1d1;
margin: 15px auto 0 auto;
}
</style>
</head>
<body>
	<div class="wrap-body">
		<jsp:include page="/admin/nav"></jsp:include>
		<script type="text/javascript">	
	    $(".edit").addClass("active");
		</script>
		<header class="">
			<div class="logo">
				<a href="#">DMI</a>
				<span>虚拟现实与数字媒体中心</span>
			</div>
		</header>
	</div>
	<div class="row" style="margin:0 40px;">
		<button class="btn btn-primary" style="margin:10px 0;text-align:center;width:20%;" onclick="reload()" title="直接修改数据库后需要点击重新加载数据" type="submit">重新加载</button>
		<div style="text-align:center;">
			<input type="text" id="content" placeholder="添加研究方向">
			<button class="btn btn-primary" style="margin:10px auto;text-align:center;width:40%;" onclick="submit()" type="submit">添加</button>
		</div>
		
		<table class="table">
		<tbody>
			<c:forEach var="i" items="${applicationScope.researchs}" begin="0" end="${applicationScope.researchsNumber}">
				<tr>
					<td style="width:85%">
						${i.content}
					</td>
					<td>
						<button style="margin-left:10%;display:inline-block" class="btn btn-danger btn-sm" onclick="deleteResearch(${i.ID},'${i.content}')">删除</button>
						<button style="margin-left:20px;display:inline-block" class="btn btn-default btn-sm" onclick="update(${i.ID},'${i.content}')">修改</button>	
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</body>
<script type="text/javascript">
	function submit()
	{
		var content = $("#content").val();
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/addResearchAjax',
			dataType : 'json',
			data : 
			{
				content : content,
			},
			success : function(data) 
			{
				if (data.flag == "1") 
				{
					location.reload();
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
	
	function deleteResearch(id,content)
	{
		var flag = confirm("确认删除此条信息: "+content+" ?");
		if(flag == false)
		{
			return;
		}
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/deleteResearchAjax',
			dataType : 'json',
			data : 
			{
				researchID : id,
			},
			success : function(data) 
			{
				if (data.flag == "1") 
				{
					location.reload();
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
	
	function update(id,content)
	{
		var change=prompt("输入修改后内容",content);
		if(change == null)
		{
			return;	
		}
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/updateResearchAjax',
			dataType : 'json',
			data : 
			{
				ID : id,
				content : change,
			},
			success : function(data) 
			{
				if (data.flag == "1") 
				{
					location.reload();
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
	
	function reload()
	{
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/reloadAjax',
			dataType : 'json',
			data : 
			{
			},
			success : function(data) 
			{
				if (data.flag == "1") 
				{
					location.reload();
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