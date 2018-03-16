<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${ctx}/res/images/favicon.ico">
<link rel="stylesheet" href="${ctx}/res/css/menu.css">
<link rel="stylesheet" href="${ctx}/res/css/style.css">
<link href="${ctx}/res/css/main.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/res/css/bootstrap.css">
<link rel="stylesheet" href="${ctx}/res/css/bootstrap-table.css">
<link href="${ctx}/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<script src="${ctx}/res/js/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/res/js/bootstrap.js"></script>
<script src="${ctx}/res/js/bootstrap-table.js"></script>
<script src="${ctx}/res/js/script.js"></script>
<script src="${ctx}/res/js/date.js"></script>
<script src="${ctx}/res/fonts/bootstrap-table-zh-CN.js"></script>
<title>修改成员</title>
<style type="text/css">
.logo>a:hover{ text-decoration:none; }
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
	<div class="container">
		<button class="btn btn-primary" style="margin:10px 0;text-align:center;width:20%;" onclick="reload()" title="直接修改数据库后需要点击重新加载数据" type="submit">重新加载</button>
		<div id="peoplePage">
		
		</div>
		<div class="table">
			<div style="padding: 20px;">
				<input type="text" style="width:30%;display:inline;" class="form-control" id="title" placeholder="标题">
				<input type="text" style="width:30%;display:inline;" class="form-control" value="${ID}" id="ID" placeholder="学号" onkeydown="return GetInput()" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d]/g,'')">  
				<input type="text" style="width:30%;display:inline;" class="form-control" id="author" placeholder="作者"> 
				<table id="table"></table>
			</div>
		</div>
		<script type="text/javascript">
		$(function () {
			$.ajax(
			{
				type : 'post',
				url : '${ctx}/findIndexUserAjax',
				dataType : 'json',
				data : 
				{
				},
				success : function(data) 
				{
					if (data.flag == "1") 
					{
						for (var i=0;i<data.res.length;i++)
						{
							$("#peoplePage").append('<div id="userInfo'+ data.res[i].userID +'" style="display:inline-block;text-align:center;width:240px; height:500px;margin-top:20px;margin-left:40px">'+
									'<img src="'+ data.res[i].head +'" style="width: 100%; height: 56%;display:block;" />'+
										'<a style="font-size:21px;" href="${ctx}/user/'+ data.res[i].userID +'">'+ data.res[i].realname +'</a>'+
										'<p style="text-align:center; font-size:19px;">'+ data.res[i].phone +'</p>'+
										'<button class="btn btn-danger" style="width:60%;margin:5px auto 0;" onclick="deleteUser('+ data.res[i].id +')" type="submit">删除</button></br>'+
										'<button class="btn btn-primary" style="padding: 6px 3px;width:20%;margin-top:10px;" onclick="topUser('+ data.res[i].id +')" type="submit">置顶</button>'+
										'<button class="btn btn-primary" style="padding: 6px 3px;width:20%;margin-top:10px;margin-left:4%" onclick="upUser('+ data.res[i].id +')" type="submit">上移</button>'+
										'<button class="btn btn-primary" style="padding: 6px 3px;width:20%;margin-top:10px;margin-left:4%" onclick="downUser('+ data.res[i].id +')" type="submit">下移</button>'+
										'<button class="btn btn-primary" style="padding: 6px 3px;width:20%;margin-top:10px;margin-left:4%" onclick="bottomUser('+ data.res[i].id +')" type="submit">置底</button></div>')
						}
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
			//1.初始化Table
		    var oTable = new TableInit();
		    oTable.Init();
		    
		    $('#ID').bind('keydown',submit);  
		    $('#realname').bind('keydown',submit);  
		});
		
		function submit(event)
		{
	        if(event.keyCode == "13") 
	        {
	        	$('#table').bootstrapTable(('refresh'));
	        }
		}
		//http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/
		var TableInit = function () {
		    var oTableInit = new Object();
		    //初始化Table
		    oTableInit.Init = function () {
		        $('#table').bootstrapTable({
		            url: '${ctx}/searchUserAjax',         //请求后台的URL（*）
		            method: 'post',                      //请求方式（*）
		            toolbar: '#toolbar',                //工具按钮用哪个容器
		            striped: true,                      //是否显示行间隔色
		            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		            pagination: true,                   //是否显示分页（*）
		            sortable: true,                     //是否启用排序
		            sortOrder: "asc",                   //排序方式
		            queryParams: oTableInit.queryParams,//传递参数（*）
		            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
		            pageNumber: 1,                       //初始化加载第一页，默认第一页
		            pageSize: 10,                       //每页的记录行数（*）
		            pageList: [10, 20, 30,40, 50],        //可供选择的每页的行数（*）
		            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
		            searchOnEnterKey: true,
		            searchAlign:"left",
		            buttonsAlign:"left",
		            smartDisplay:false,
		            contentType: "application/x-www-form-urlencoded",
		            strictSearch: true,
		            showColumns: false,                  //是否显示 内容列下拉框
		            showRefresh: false,                  //是否显示刷新按钮
		            minimumCountColumns: 2,             //最少允许的列数
		            clickToSelect: true,                //是否启用点击选中行
		            uniqueId: "no",                     //每一行的唯一标识，一般为主键列
		            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
		            cardView: false,                    //是否显示详细视图
		            detailView: false,                   //是否显示父子表
		            responseHandler:responseHandler,
		            columns: [
		            {
		                field: 'userID',
		                title: '学号',
			            formatter:userIDFormatter
		            }, 
		            {
		                field: 'realname',
		                title: '真实姓名'
		            }, 
		            {
		                field: 'phone',
		                title: '手机号'
		            }, 
		            {
		            	field: 'operate',
		                title: '操作',
	                	formatter:operateFormatter
		            },
		            ],
		        });
	
		    };
	
	
		    //得到查询的参数
		    oTableInit.queryParams = function (params) 
		    {
		        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		    		rows: params.limit,   //页面大小
		    		page: params.offset/params.limit + 1,
		    		ID: $("#ID").val(),
		    		realname:$("#realname").val()
		        };
		        
		        return temp;
		    };
		    return oTableInit;
		};
	
		function responseHandler(result)
		{
			if(result.flag==0)
			{
		        alert("错误:" + result.msg);
		        return;
		    }
		    return {
		        total : result.total,
		        rows : result.res 
		    };
		}
		
		function userIDFormatter(value, row, index)
		{
			return '<a href="${ctx}/admin/user/'+ row['userID'] +'">'+ row['userID'] +'</a>';
		}
		
		function operateFormatter(value, row, index)
		{
			if($("#userInfo"+ row['userID']).length > 0)
			{
				return '';
			}
			return '<span>'+
			'<a href="#" onclick="addUser('+ row['userID'] + ')" class="button glyphicon glyphicon-edit">显示在首页中</a>'+
			'</span>';
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
	</div>
</body>
<script type="text/javascript">

	function deleteUser(indexID)
	{
		var flag = confirm("确认删除此图片?");
		if(flag == false)
		{
			return;
		}
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/deleteIndexUserAjax',
			dataType : 'json',
			data : 
			{
				userID : indexID,
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
	
	function addUser(userID)
	{
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/addIndexUserAjax',
			dataType : 'json',
			data : 
			{
				userID:userID
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
	
	function topUser(indexID)
	{
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/topUserAjax',
			dataType : 'json',
			data : 
			{
				userID : indexID,
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
	
	function upUser(indexID)
	{
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/upUserAjax',
			dataType : 'json',
			data : 
			{
				userID : indexID,
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
	
	function downUser(indexID)
	{
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/downUserAjax',
			dataType : 'json',
			data : 
			{
				userID : indexID,
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
	
	function bottomUser(indexID)
	{
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/admin/bottomUserAjax',
			dataType : 'json',
			data : 
			{
				userID : indexID,
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