<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${ctx}/res/images/favicon.ico">
<link rel="stylesheet" href="${ctx}/res/css/style.css">
<link href="${ctx}/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${ctx}/res/css/menu.css">
<link rel="stylesheet" href="${ctx}/res/css/bootstrap.css">
<link href="${ctx}/res/css/cropper.min.css" rel="stylesheet">
<link href="${ctx}/res/css/main.css" rel="stylesheet">
<script src="${ctx}/res/js/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/res/js/script.js"></script>
<script src="${ctx}/res/js/bootstrap.min.js"></script>
<script src="${ctx}/res/js/cropper.min.js"></script>
<script src="${ctx}/res/js/editPhoto.js"></script>
<title>修改照片</title>
<style type="text/css">
.logo>a:hover{ text-decoration:none; }
.addImage:hover{cursor:pointer;}
.imageInfo>input{
padding: 0;
border: none;
border-bottom: 1px solid #d1d1d1;
text-align:center;
margin: 15px auto 0 auto;
background: transparent;
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
	<button class="btn btn-primary" style="margin:10px 0 0 10%;text-align:center;width:20%;" onclick="reload()" title="直接修改数据库后需要点击重新加载数据" type="submit">重新加载</button>	
	<div class="container" style="margin:0 40px" id="crop-avatar"><p id="imageID" hidden></p>
		<c:forEach var="i" items="${applicationScope.images}" begin="0" end="${applicationScope.imagesNumber}">
			<div class="imageInfo" id="imageInfo${i.ID}" style="width:430px; height:500px;margin:20px 5%;display:inline-block;text-align:center">
				<div class="avatar-view" title="更改图片" style="margin:0;width: 100%; height: 60%;display:block;" onclick="selectImage(${i.ID})">
					<img id="head" class="image${i.ID}" src="${i.image}" style="width: 100%; height: 100%;display:block;" />
				</div>
				<input type="text" id="title${i.ID}" maxlength="20" placeholder="标题(不超过20字)" value="${i.title}" style="width:80%;text-align:center;" required="required">
				<input type="text" id="description${i.ID}" maxlength="30" placeholder="相关描述(可不填 不超过30字)" value="${i.description}" style="width:80%;text-align:center;">
				<button class="btn btn-danger avatar-save" style="width:40%;margin-top:10px;" onclick="deleteImage(${i.ID})" type="submit">删除</button>
				<button class="btn btn-primary avatar-save" style="width:40%;margin-top:10px;margin-left:10%" onclick="update(${i.ID})" type="submit">确认</button>
				<button class="btn btn-primary avatar-save" style="width:15%;margin-top:10px;" onclick="topImage(${i.ID})" type="submit">置顶</button>
				<button class="btn btn-primary avatar-save" style="width:15%;margin-top:10px;margin-left:10%" onclick="upImage(${i.ID})" type="submit">上移</button>
				<button class="btn btn-primary avatar-save" style="width:15%;margin-top:10px;margin-left:10%" onclick="downImage(${i.ID})" type="submit">下移</button>
				<button class="btn btn-primary avatar-save" style="width:15%;margin-top:10px;margin-left:10%" onclick="bottomImage(${i.ID})" type="submit">置底</button>
			</div>
		</c:forEach>
		<div class="addImage" style="width:430px; height:400px;margin:20px 5%;display:inline-block;text-align:center" title="添加图片" onclick="addImage()">
			<img alt="添加图片" src="${ctx}/res/images/add.jpg" style="width: 100%; height: 100%;display:block;">
		</div>
	
		 <!-- Cropping modal -->
	    <div class="modal fade" id="avatar-modal" style="margin-top:44px;" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
	      <div class="modal-dialog modal-lg">
	        <div class="modal-content">
	          <form class="avatar-form" action="${ctx}/admin/updateImageAjax" enctype="multipart/form-data" method="post">
	            <div class="modal-header">
	              <button class="close" data-dismiss="modal" type="button" onclick="close()">&times;</button>
	              <h4 class="modal-title" id="avatar-modal-label">更改头像</h4>
	            </div>
	            <div class="modal-body">
	              <div class="avatar-body">
	
	                <!-- Upload image and data -->
	                <div class="avatar-upload">
	                  <input class="avatar-src" name="avatar_src" type="hidden">
	                  <input class="avatar-data" name="avatar_data" type="hidden">
	                  <label for="avatarInput">本地上传</label>
	                  <input class="avatar-input" id="avatarInput" name="avatar_file" type="file" style="width:60%;margin:0;border:none;padding:0;display: inline-block;">
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
	</div>
</body>
<script type="text/javascript">

	function update(ID)
	{
		var title = $("#title"+ID).val();
		var description = $("#description"+ID).val();
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/updateImageInfoAjax',
			dataType : 'json',
			data : 
			{
				ID : ID,
				title : title,
				description : description,
				image : "",
			},
			success : function(data) 
			{
				if (data.flag == "1") 
				{
					alert("ok");
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
	
	function deleteImage(imageID)
	{
		var flag = confirm("确认删除此图片?");
		if(flag == false)
		{
			return;
		}
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/deleteIndexImageAjax',
			dataType : 'json',
			data : 
			{
				imageID : imageID,
			},
			success : function(data) 
			{
				if (data.flag == "1") 
				{
					$("#imageInfo"+imageID).remove();
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
	
	function addImage()
	{
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/addImageAjax',
			dataType : 'json',
			data : 
			{
			},
			success : function(data) 
			{
				if (data.flag == "1") 
				{
					var imageID = data.res.id;
					$(".addImage").before('<div class="imageInfo" id="imageInfo'+imageID+'" style="width:430px; height:500px;margin:20px 5%;display:inline-block;text-align:center">'+
							'<div class="avatar-view" id="new'+imageID+'" title="更改图片" style="margin:0;width: 100%; height: 60%;display:block;" onclick="selectImage('+imageID+')">'+
							'<img id="head" class="image'+imageID+'" src="'+data.res.image+'" style="width: 100%; height: 100%;display:block;"/></div>'+
						'<input type="text" id="title'+imageID+'" maxlength="20" placeholder="标题(不超过20字)" value="'+data.res.title+'" style="width:80%;text-align:center;" required="required">'+
						'<input type="text" id="description'+imageID+'" maxlength="30" placeholder="相关描述(可不填 不超过30字)" value="'+data.res.description+'" style="width:80%;text-align:center;">'+
						'<button class="btn btn-danger avatar-save" style="width:40%;margin-top:10px;" onclick="deleteImage('+imageID+')" type="submit">删除</button>'+
						'<button class="btn btn-primary avatar-save" style="width:40%;margin-top:10px;margin-left:10%" onclick="update('+imageID+')" type="submit">确认</button>'+
						'<button class="btn btn-primary avatar-save" style="width:15%;margin-top:10px;" onclick="topImage('+imageID+')" type="submit">置顶</button>'+
						'<button class="btn btn-primary avatar-save" style="width:15%;margin-top:10px;margin-left:10%" onclick="upImage('+imageID+')" type="submit">上移</button>'+
						'<button class="btn btn-primary avatar-save" style="width:15%;margin-top:10px;margin-left:10%" onclick="downImage('+imageID+')" type="submit">下移</button>'+
						'<button class="btn btn-primary avatar-save" style="width:15%;margin-top:10px;margin-left:10%" onclick="bottomImage('+imageID+')" type="submit">置底</button></div>');
				
					$("#new"+imageID).click(function()
					{
						$('#avatar-modal').modal("show");
						var url = $(".image"+imageID).attr('src');

					    var files = $('.avatar-input').prop('files');
					    if (files.length > 0) 
					    {
					    	file = files[0];

					        if (this.isImageFile(file)) 
					      	{
					          url = URL.createObjectURL(file);
					        }
					    }
					    $('.avatar-preview').empty().html('<img src="' + url + '">');
					});
				
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
	
	function topImage(imageID)
	{
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/topImageAjax',
			dataType : 'json',
			data : 
			{
				imageID : imageID,
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
	
	function upImage(imageID)
	{
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/upImageAjax',
			dataType : 'json',
			data : 
			{
				imageID : imageID,
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
	
	function downImage(imageID)
	{
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/downImageAjax',
			dataType : 'json',
			data : 
			{
				imageID : imageID,
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
	
	function bottomImage(imageID)
	{
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/bottomImageAjax',
			dataType : 'json',
			data : 
			{
				imageID : imageID,
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
	
	function selectImage(ID)
	{
		$("#imageID").text(ID);
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