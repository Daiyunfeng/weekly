<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="shortcut icon" href="${ctx}/res/images/favicon.ico">
<link rel="stylesheet" href="${ctx}/res/css/loginStyle.css" type="text/css" media="all">
<script src="${ctx}/res/js/jquery.min.js"></script>
<script src="${ctx}/res/js/bootstrap.min.js"></script>
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<title>登陆</title>
</head>
<div class="panel-body">

	<h1>欢迎</h1>

	<div class="container w3layouts agileits">
		<p id="token" hidden>${token}</p>
		<div class="login w3layouts agileits">
			<h2>登 录</h2>
			<form action="#" method="post">
				<input type="text" id="userID" onkeydown="return GetInput()" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d]/g,'') " placeholder="学号" required="">
				<input type="text" onfocus="this.type='password'" id="password" placeholder="密码" required="">
				<input id="captcha" type="text" class="form-control" placeholder="请输入验证码">
				<img id="captchaImg" src="${ctx}/pcrimg" onclick="reImg()" alt="点击刷新验证码">
			</form>
			<span id="error" style="color: #FF0000">${msg}</span>
			<div class="send-button w3layouts agileits">
				<form>
					<button style="margin: 0 auto; display: block;" onclick="login()" type="button">登 录</button>
				</form>
			</div>
			<!--<a href="#">忘记密码?</a>-->
			<div class="clear"></div>
		</div>

		<div class="register w3layouts agileits">
			<h2>注 册</h2>
			<form action="#" method="post">
				<input type="text" id="ID" onkeydown="return GetInput()" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d]/g,'') " placeholder="学号" required="">
				<input type="text" onfocus="this.type='password'" id="Password" placeholder="密码(6-20字符)" required="">
				<input type="text" id="Phone" onkeydown="return GetInput()" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d]/g,'') " placeholder="手机号码" required="">
				<input type="text" id="Realname" placeholder="真实姓名(禁止特殊字符)" onkeyup="this.value=this.value.replace(/[ |<|>|(|)]/g,'')" required="">
				<img style="width: 100%; heigth: 1px;">
			</form>
			<span id="error2" style="color: #FF0000"></span>
			<div class="send-button w3layouts agileits">
				<form>
					<button style="margin: 0 auto; display: block;" type="button" onclick="register()">注册</button>
				</form>
			</div>
			<div class="clear"></div>
		</div>

		<div class="clear"></div>

	</div>
</div>
<script type="text/javascript">
function login() 
{
	var userID = $("#userID").val();
	var password = $("#password").val();
	var captcha = $("#captcha").val();
	var token = $("#token").text();
	if(!isNumber(userID))
	{
		$("#error").html("学号必须为数字");
		return;
	}
	$("#error").html("");
	$.ajax(
	{
		type : 'post',
		url : '${ctx}/loginAjax',
		dataType : 'json',
		data : 
		{
			userID : userID,
			password : password,
			captcha : captcha,
			token : token,
		},
		success : function(data) 
		{
			if (data.flag == "1") 
			{
				if(data.role == 3)
				 {
					 window.location.href="${ctx}/admin"; 
				 }
				 else
				 {
					 window.location.href="${ctx}"; 	 
				 }
			} 
			else if (data.flag == "0") 
			{
				alert(data.msg);
				reImg();
			} 
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) 
		{
			 alert(XMLHttpRequest.status+"	"+XMLHttpRequest.readyState+"	"+textStatus);
		}
	});
}

function register() 
{
	var userID = $("#ID").val();
	var password = $("#Password").val();
	var realname = $("#Realname").val();
	var phone = $("#Phone").val();
	var token = $("#token").text();
	if(!isNumber(userID))
	{
		$("#error2").html("学号必须为数字");
		return;
	}
	if(!isNumber(phone))
	{
		$("#error2").html("手机号必须为数字");
		return;
	}
	$("#error2").html("");
	$.ajax(
	{
		type : 'post',
		url : '${ctx}/registerUserAjax',
		dataType : 'json',
		data : 
		{
			ID : userID,
			password : password,
			realname : realname,
			phone : phone,
			token : token,
		},
		success : function(data) 
		{
			if (data.flag == "1") 
			{
				 window.location.href="${ctx}"; 
			} 
			else if (data.flag == "0") 
			{
				alert(data.msg);
				reImg();
			} 
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) 
		{
			 alert(XMLHttpRequest.status+"	"+XMLHttpRequest.readyState+"	"+textStatus);
		}
	});
}

function reImg() 
{
	var img = $("#captchaImg");
	console.log(img.src);
	img.attr('src',"${ctx}/pcrimg?"+ Math.random());	//防止使用缓存
}
function isNumber(val)
{

    var regPos = /^\d+(\.\d+)?$/; //非负浮点数
    var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    if(regPos.test(val) || regNeg.test(val))
    {
        return true;
    }
    else
    {
        return false;
    }
}

function GetInput()
{
	var k = event.keyCode || event.which; //48-57是大键盘的数字键，96-105是小键盘的数字键，8是退格符 C 67 V 86
    if(event.ctrlKey && (k == 67 || k == 86))   //Ctrl + c v  
    {
        return true;
    }  
    if ((k <= 57 && k >= 48) || (k <= 105 && k >= 96) || (k== 8) || (k== 9) || (k==116))
    {
     return true;
    }
    else 
    {
     return false;
    }
}
</script>
</body>
</html>