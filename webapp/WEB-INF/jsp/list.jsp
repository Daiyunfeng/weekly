<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>周报列表</title>
<link rel="shortcut icon" href="${ctx}/res/images/favicon.ico">
<link rel="stylesheet" href="${ctx}/res/css/menu.css">
<link rel="stylesheet" href="${ctx}/res/css/style.css">
<link rel="stylesheet" href="${ctx}/res/css/bootstrap.css">
<script src="${ctx}/res/js/jquery.min.js"></script>
<script src="${ctx}/res/js/bootstrap.js"></script>
<script src="${ctx}/res/js/bootstrap-table.js"></script>
<link rel="stylesheet" href="${ctx}/res/css/bootstrap-table.css">
<script src="${ctx}/res/fonts/bootstrap-table-zh-CN.js"></script>
<script src="${ctx}/res/js/date.js"></script>
<style type="text/css">
a:hover{ text-decoration:none; }
a.button
{
	background-color: transparent;
	padding: 0;
	color: #337ab7;
}
</style>
</head>
<body>
	<jsp:include page="/nav"></jsp:include>
	<script type="text/javascript">
    $(".list").addClass("active");
	</script>
	
	<div class="container">
		<div style="padding: 20px;">
			<input type="text" style="width:30%;display:inline;" class="form-control" id="title" placeholder="标题">
			<input type="text" style="width:30%;display:inline;" class="form-control" value="${ID}" id="ID" placeholder="学号" onkeydown="return GetInput()" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d]/g,'')">  
			<input type="text" style="width:30%;display:inline;" class="form-control" id="author" placeholder="作者"> 
			<table id="table"></table>
		</div>
	</div>
	<script type="text/javascript">
	$(function () {
		//1.初始化Table
	    var oTable = new TableInit();
	    oTable.Init();
	    //$('#table').bootstrapTable('hideColumn', 'articleID');
	    //$('#table').bootstrapTable('hideColumn', 'userID');

	    $('#ID').bind('keydown',submit); 
	    $('#title').bind('keydown',submit);  
	    $('#author').bind('keydown',submit);  
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
	            url: '${ctx}/searchArticleAjax',         //请求后台的URL（*）
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
	                field: 'title',
	                title: '标题',
		            formatter:articleFormatter
	            }, 
	            //{
	            //    field: 'userID'
	            //}, 
	            {
	                field: 'author',
	                title: '作者',
	                formatter:userFormatter
	            }, 
	            {
	                field: 'updateTime',
	                title: '最后更新',
	                width: '20%',
	            	formatter: timeFormatter //自定义方法
	            },
	            {
	                field: 'operate',
	                title: '操作',
	                width: '20%',
	                formatter: operateFormatter //自定义方法，添加操作按钮
	            },
	            ],
	        });

	    };


	    //得到查询的参数
	    oTableInit.queryParams = function (params) {
	        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
	    		rows: params.limit,   //页面大小
	    		page: params.offset/params.limit + 1,
	    		ID: $("#ID").val(),
	    		author: $("#author").val(),
	    		title:$("#title").val()
	        };
	        
	        return temp;
	    };
	    return oTableInit;
	};

	function responseHandler(result){
		if(result.flag==0)
		{
	        alert("错误:" + result.msg);
	        return;
	    }
		var userID = result.userID;
		for(var i=0;i<result.res.length;i++)
		{
			if(result.res[i].userID != userID)
			{
				result.res[i].status=0;
			}
		}
	    //如果没有错误则返回数据，渲染表格
	    return {
	        total : result.total,
	        rows : result.res 
	    };
	}
	
	function userFormatter(value, row, index)
	{
		return '<a href="${ctx}/user/'+ row['userID'] +'">'+ row['author'] +'</a>';
	}
	
	function articleFormatter(value, row, index)
	{
		return '<a href="${ctx}/article/'+ row['articleID'] +'">'+row['title']+'</a>';
	}
	
	function operateFormatter(value, row, index) {//赋予的参数
		if (row['status'] === 0) 
		{
	        return '';
	    }
	    return [
			'<span>'+
			'<a href="${ctx}/update/'+ row['articleID'] +'" class="button glyphicon glyphicon-edit">Edit</a>'+
			'<a href="#" onclick="deleteArticle('+ row['articleID']+',\''+row['title'] +'\')" style="margin-left:10px" class="glyphicon glyphicon-trash" title="Delete"></a>'+
			'</span>'].join('');
	}

	function timeFormatter(value, row, index)
	{
		return [
		        new Date(row['updateTime']).format("yyyy-MM-dd hh:mm:ss")].join('');
	}
	
	function deleteArticle(articleID,title)
	{
		var flag = confirm("确认删除文章: "+title+" ?");
		if(flag == false)
		{
			return;
		}
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/deleteArticleByIDAjax',
			dataType : 'json',
			data : 
			{
				articleID : articleID,
			},
			success : function(data) 
			{
				if (data.flag == "1") 
				{
					$('#table').bootstrapTable(('refresh'));
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