<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>个人中心</title>
<link rel="shortcut icon" href="${ctx}/res/images/favicon.ico">
<link rel="stylesheet" href="${ctx}/res/css/zerogrid.css">
<link rel="stylesheet" href="${ctx}/res/css/style.css">
<link rel="stylesheet" href="${ctx}/res/css/menu.css">
<link href="${ctx}/res/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/res/css/cropper.min.css" rel="stylesheet">
<link href="${ctx}/res/css/main.css" rel="stylesheet">
<script src="${ctx}/res/js/jquery.min.js"></script>
<style type="text/css">
input{
	margin: 5px 0 20px 0;
}
</style>
</head>
<body>
	<jsp:include page="/nav"></jsp:include>
	<script type="text/javascript">
    $(".center").addClass("active");
	</script>
  <div class="container" id="crop-avatar">
	<p id="ID" hidden>${ID}</p>
    <!-- Current avatar -->
    <div style="margin:5% 5% 5% 10%;display: inline-block;height:400px;width:20%;text-align:center">
	    <div class="avatar-view" title="更改头像" style="margin:0;display: inline-block;">
			<img id="head" src="" alt="头像" style="width:100%;height: 100%">
	    </div>
     	<button class="btn btn-default" id="list" onclick="list()" style="margin:25% auto 0;width:80%;">查看已有周报</button>
     	<button class="btn btn-default btn-danger" id="logout" onclick="logout()" style="margin:15% auto 0;width:80%;">登出</button>
	</div>
	
	<div style="margin:5% 10% 5% 5%;display: inline-block;height:300px;width:45%;text-align:center">
		<p id="token" hidden>${token}</p>
		<p style="font-size:17px;font-family:'Merriweather', Georgia, serif;" id="info"></p>
		<p style="font-size:17px;font-family:'Merriweather', Georgia, serif;">修改个人信息</p>
		<input type="text" onfocus="this.type='password'" id="password" class="form-control" placeholder="请输入密码" required="">
		<input type="text" onfocus="this.type='password'" id="newPassword" class="form-control" placeholder="修改密码(可为空)">
		<input type="text" id="phone" onkeydown="return GetInput()" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d]/g,'') " class="form-control" placeholder="手机号码" required="">
		<input type="text" id="realname" class="form-control" placeholder="真实姓名(禁止特殊字符)" onkeyup="this.value=this.value.replace(/[ |<|>|(|)]/g,'')"  required="">
     	<button class="btn btn-default" id="update" onclick="update()" style="margin:30px auto 0;width:80%;">提交</button>
	
	</div>
    <!-- Cropping modal -->
    <div class="modal fade" id="avatar-modal" style="margin-top:44px" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <form class="avatar-form" action="${ctx}/updateUserHeadAjax" enctype="multipart/form-data" method="post">
            <div class="modal-header">
              <button class="close" data-dismiss="modal" type="button">&times;</button>
              <h4 class="modal-title" id="avatar-modal-label">更改头像</h4>
            </div>
            <div class="modal-body">
              <div class="avatar-body">

                <!-- Upload image and data -->
                <div class="avatar-upload">
                  <input class="avatar-src" name="avatar_src" type="hidden">
                  <input class="avatar-data" name="avatar_data" type="hidden">
                  <label for="avatarInput">本地上传</label>
                  <input class="avatar-input" id="avatarInput" name="avatar_file" type="file" style="width:auto;border:none;padding:0;">
                </div>

                <!-- Crop and preview -->
                <div class="row">
                  <div class="col-md-9">
                    <div class="avatar-wrapper"></div>
                  </div>
                  <div class="col-md-3">
                    <div class="avatar-preview preview-lg"></div>
                    <div class="avatar-preview preview-md"></div>
                    <div class="avatar-preview preview-sm"></div>
                  </div>
                </div>

                <div class="row avatar-btns">
                  <div class="col-md-9">
                    <div class="btn-group">
                      <button class="btn btn-primary" data-method="rotate" data-option="-90" type="button" title="Rotate -90 degrees">&nbsp;&nbsp;左转&nbsp;&nbsp;</button>
                      <button class="btn btn-primary" data-method="rotate" data-option="-15" type="button">&nbsp;&nbsp;-15°&nbsp;&nbsp;</button>
                      <button class="btn btn-primary" data-method="rotate" data-option="-30" type="button">&nbsp;&nbsp;-30°&nbsp;&nbsp;</button>
                      <button class="btn btn-primary" data-method="rotate" data-option="-45" type="button">&nbsp;&nbsp;-45°&nbsp;&nbsp;</button>
                    </div>
                    <div class="btn-group">
                      <button class="btn btn-primary" data-method="rotate" data-option="90" type="button" title="Rotate 90 degrees">&nbsp;&nbsp;右转&nbsp;&nbsp;</button>
                      <button class="btn btn-primary" data-method="rotate" data-option="15" type="button">&nbsp;&nbsp;15°&nbsp;&nbsp;</button>
                      <button class="btn btn-primary" data-method="rotate" data-option="30" type="button">&nbsp;&nbsp;30°&nbsp;&nbsp;</button>
                      <button class="btn btn-primary" data-method="rotate" data-option="45" type="button">&nbsp;&nbsp;45°&nbsp;&nbsp;</button>
                    </div>
                  </div>
                  <div class="col-md-3">
                    <button class="btn btn-primary btn-block avatar-save" type="submit">确认</button>
                  </div>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div><!-- /.modal -->

    <!-- Loading state -->
    <div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>
  </div>

  <script src="${ctx}/res/js/script.js"></script>
  <script src="${ctx}/res/js/bootstrap.min.js"></script>
  <script src="${ctx}/res/js/cropper.min.js"></script>
  <script src="${ctx}/res/js/main.js"></script>
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
				var str = data.res.role;
				if(str == "管理员")
				{
					str = '<a href="${ctx}/admin/center">'+str+'</a>'; 
				}
				$("#info").html("当前用户:"+data.res.userID+"&nbsp;&nbsp;&nbsp;"+str); 
				$("#head").attr("src",data.res.head); 
				$("#realname").val(data.res.realname);
				$("#phone").val(data.res.phone);
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
	function update()
	{
		var password = $("#password").val();
		var newPassword = $("#newPassword").val();
		var realname = $("#realname").val();
		var phone = $("#phone").val();
		var token = $("#token").text();
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/updateUserAjax',
			dataType : 'json',
			data : 
			{
				ID : ID,
				password : password,
				newPassword : newPassword,
				realname : realname,
				phone : phone,
				token : token,
			},
			success : function(data) 
			{
				if (data.flag == "1") 
				{
					alert("修改成功");
					$("#password").val(""); 
					$("#newPassword").val("");
				} 
				else if (data.flag == "0") 
				{
					alert(data.msg);
					$("#password").val(""); 
					$("#newPassword").val("");
				} 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) 
			{
				 alert(XMLHttpRequest.status+"	"+XMLHttpRequest.readyState+"	"+textStatus);
			}
		});
	}
	function logout()
	{
		window.location.href="${ctx}/logout";
	}
	function list()
	{
		window.location.href="${ctx}/list/"+ID;
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
