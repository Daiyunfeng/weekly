<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${ctx}/res/images/favicon.ico">
<link rel="stylesheet" href="${ctx}/res/css/bootstrap.css">
<link rel="stylesheet" href="${ctx}/res/css/zerogrid.css">
<link rel="stylesheet" href="${ctx}/res/css/style.css">
<link href="${ctx}/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${ctx}/res/css/menu.css">
<script src="${ctx}/res/js/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/res/js/script.js"></script>
<script src="${ctx}/res/js/date.js"></script>
<link href="${ctx}/res/owl-carousel/owl.carousel.css" rel="stylesheet">
<script src="${ctx}/res/js/bootstrap-paginator.min.js"> </script>
<link href="${ctx}/res/css/commentStyle.css" rel='stylesheet' type='text/css' />	
<script type="text/javascript" charset="utf-8" src="${ctx}/res/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/res/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/res/ueditor/lang/zh-cn/zh-cn.js"></script>
<title>修改周报</title>
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
	<jsp:include page="/nav"></jsp:include>
	<script type="text/javascript">
    $(".addArticle").addClass("active");
	</script>
	<section id="container">
	<div class="wrap-container">
		<!-----------------Content-Box-------------------->
		<p id="ID" hidden>${ID}</p>
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
		
		<div class="zerogrid">		
			<div class="comments-are">
				<p id="current" hidden>0</p>
				<div class="response" style="padding: 20px 30px 0;">
				
				</div>
				<nav style="text-align: center">
					<ul id='bp-element' class="pagination" style="text-align:center;"></ul>
				</nav>
			</div>
		</div>
	</div>
<script type="text/javascript">

	var ue = UE.getEditor('editor');
	var ID = $("#ID").text();
	var SHOW_NUMBER = 6;	//每页显示
	$.ajax(
	{
		type : 'post',
		url : '${ctx}/findArticleByIDAjax',
		dataType : 'json',
		data : 
		{
			ID : ID,
		},
		success : function(data) 
		{
			if (data.flag == "1") 
			{
				var total = parseInt(data.res.commentNumber/SHOW_NUMBER);
				var i = data.res.commentNumber%SHOW_NUMBER==0?0:1;
				total+=i;
				if(total!=0)
				{
					CreatePagNav(total);
			    	showComment(1);
				}
				$("#title").val(data.res.title);
				insertHtml(data.res.content);
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
	function insertHtml(content) {
		ue.ready(function() {
			UE.getEditor('editor').execCommand('insertHtml', content)
	    });
    }
   function submit()
   {
		var title = $("#title").val();
		var content = UE.getEditor('editor').getContent();
		var token = $("#token").text();
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/updateArticleAjax',
			dataType : 'json',
			data : 
			{
				ID : ID,
				title : title,
				"articleContent.content" : content,
				token : token,
			},
			success : function(data) 
			{
				if (data.flag == "1") 
				{
					var id = data.msg;
					window.location.href="${ctx}/article/"+id; 
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
   function showComment(page)
	{
		var ID = $("#ID").text();
   	$.ajax(
		{
			type : 'post',
			url : '${ctx}/findCommentByPageAjax',
			dataType : 'json',
			data : 
			{
				articleID : ID,
				page:page,
				rows:SHOW_NUMBER,
			},
			success : function(data) 
			{
				if (data.flag == "1") 
				{
					print(data.res);
				} 
				else if (data.flag == "0") 
				{
					console.log(data.msg);
				} 
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) 
			{
				 alert(XMLHttpRequest.status+"	"+XMLHttpRequest.readyState+"	"+textStatus);
			}
		});
	}
	function print(res)
	{
		$(".response").children().remove();
		for(var i=0;i<res.length;i++)
		{
			var str;
			console.log(res[i].role);
			if(res[i].role == 1)
			{
				str = '<div class="media response-info">'
					+'<div class="media-left response-text-left">'
					+'<a><img class="media-object" style="width:80px;heigth:80px;" src="'+res[i].head+'" alt="头像"/></a>'
					+'<h5 style="margin:0;"><a href="'+'${ctx}/user/'+res[i].userID+'">'+res[i].author+'</a></h5></div>'
					+'<div class="media-body response-text-right">'
					+'<p style="word-break:break-all;">'+res[i].content+'</p>'
					+'<ul><li>'+new Date(res[i].updateTime).format("yyyy-MM-dd hh:mm:ss")+'</li></ul>'
					+'</div><div class="clearfix"> </div></div>';
			}
			else
			{
				str = '<div class="media response-info">'
					+'<div class="media-left response-text-left">'
					+'<a><img class="media-object" style="width:80px;heigth:80px;" src="'+res[i].head+'" alt="头像"/></a>'
					+'<h5 style="margin:0;"><a href="'+'${ctx}/user/'+res[i].userID+'">'+res[i].author+'</a></h5></div>'
					+'<div class="media-body response-text-right">'
					+'<p style="word-break:break-all;color:red;">'+res[i].content+'</p>'
					+'<ul><li>'+new Date(res[i].updateTime).format("yyyy-MM-dd hh:mm:ss")+'</li></ul>'
					+'</div><div class="clearfix"> </div></div>';
			}
			$(".response").append(str);
		}
	}
	//当前激活的页码对象，当前显示的最小页码，前一页、下一页、第一页、最后一页的对象
	var $thisPageNumber, thisShowMinNumber, $prePage, $nextPage, $firstPage, $lastPage;
	function CreatePagNav(totalPageNum ) {//totalPageNum：总页数
	  var $pagResult = $("#bp-element");
	  var initStr = [];
	  $pagResult.empty();//清空原有的页码
	  if (totalPageNum && totalPageNum >= 1) {//页码总数大于等于1时才显示页码
	    initStr.push('<li class="disabled" id="first-page"><span value="1">首页</span></li>');
	    initStr.push('<li class="disabled" id="pre-page"><span value="0">上一页</span></li>');
	    if (totalPageNum == 1) {//如果只有一页，则下一页和末页也要禁用
	      initStr.push('<li class="disabled" id="next-page"><span value="2">下一页</span></li>');
	      initStr.push('<li class="disabled" id="last-page"><span value="' + totalPageNum + '">末页</span></li>');
	    }
	    else {
	      initStr.push('<li id="next-page"><span value="2">下一页</span></li>');
	      initStr.push('<li id="last-page"><span value="' + totalPageNum + '">末页</span></li>');
	    }
	    //页面自定义跳转、包括输入框、页面总数的提示、和确定按钮，其中输入框默认显示当前选中的页码
	    initStr.push('<div class="input-page-div">当前第<input type="text" style="width:40px;padding:2px;margin:0;display:inline";" id="input-page" value="1" />页，共<span id="totalPage" style="margin: 2px; font-size:1.2em;color:#337ab7;">' + totalPageNum +
	      '</span>页<button class="btn btn-xs" id="input-page-btn">确定</button></div>');
	    $pagResult.append(initStr.join(""));//插入功能按键(首页、末页、前一页、后一页、页面自定义跳转)
	    //初始化变量
	    $prePage = $("#pre-page");
	    $nextPage = $("#next-page");
	    $firstPage = $("#first-page");
	    $lastPage = $("#last-page");
	    //生成具体的页码
	    CreatPageLi(1, 1, totalPageNum);
	    //显示页码（整个html）
	    $pagResult.parent().parent().show();
	    //绑定点击事件
	    //页码的点击事件
	    $("#bp-element>li").bind("click", pageClick);
	    //页面自定义跳转按钮的点击事件
	    $("#input-page-btn").bind("click", function () {
	      var numberPage = parseInt($("#input-page").val());//要转为数字类型
	      if (isNaN(numberPage)) return false;
	      GotoPage(numberPage);//跳转到页面
	    });
	  }
	  else {//如果页码总数小于1，则隐藏整个html标签
	    $pagResult.parent().parent().hide();
	  }
	}
	//无论是直接点击页码、还是上一页、下一页、首页、末页，或是自定义跳转都使用此函数
	//直接跳到指定页面
	function GotoPage(numberPage) {
	    numberPage = parseInt(numberPage);//要跳转的页码，注意要转换为数字类型
	    var maxNumber = parseInt($lastPage.children().attr("value"));//最大页码
	    var oldNumber = parseInt($nextPage.children().attr("value")) - 1;//当前页码
	    //确保页码正确跳转，跳转的页面不能小于等于0且不能大于总页数
	    if (numberPage <= 0) numberPage = 1;
	    else if (numberPage > maxNumber) numberPage = maxNumber;
	    //设置页码输入框的值
	    $("#input-page").val(numberPage);
	    //页码相同时不用操作，要跳转的页码就是当前页码
	    if (numberPage == oldNumber) return false;
	    //功能按钮的开启与关闭
	    //当跳转的页码为首页时，首页和上一页应禁止点击
	    if (numberPage == 1) {
	      $prePage.addClass("disabled");
	      $firstPage.addClass("disabled");
	    }
	    else {//否则应允许点击
	      $prePage.removeClass("disabled");
	      $firstPage.removeClass("disabled");
	    }
	    //当跳转的页码为末页时同理
	    if (numberPage == maxNumber) {
	      $nextPage.addClass("disabled");
	      $nextPage.next().addClass("disabled");
	    }
	    else {
	      $nextPage.removeClass("disabled");
	      $nextPage.next().removeClass("disabled");
	    }
	    //开始跳转
	    //修改上一页和下一页的值，详见之后的设计思想
	    $prePage.children().attr("value", numberPage - 1);
	    $nextPage.children().attr("value", numberPage + 1);
	    //计算起始页码
	    var starPage = computeStartPage(numberPage, maxNumber);
	    if (starPage == thisShowMinNumber) {//要显示的页码是相同的，不用重新生成页码
	      //去除上一个页码的激活状态
	      $thisPageNumber.removeClass("active");
	      $thisPageNumber = $("#commonNum" + (numberPage - thisShowMinNumber + 1));
	      //为跳转的页码加上激活状态
	      $thisPageNumber.addClass("active");
	      //页面跳转成功
	      //执行相应的动作
	    }
	    else {//需要重新生成页码
	      CreatPageLi(starPage, numberPage, maxNumber);
	     //页面跳转成功
	     //执行相应的动作
	     //重新绑定事件（执行完相应的动作后）
	     $(".commonNum").bind("click", pageClick);
	    }
	    showComment(numberPage);
	}
	//根据当前显示的最小页码、要跳转的页码和最大页码来计算要重新生成的起始页码
	//要显示的页码数量为6个
	function computeStartPage(numberPage, maxPage) {
	  var startPage;
	  if (maxPage <= 6) startPage = 1;
	  else {
	    if ((numberPage - thisShowMinNumber) >= 4) {//跳转到十页中的后三页或之后的页码时尽量显示后续页码
	      startPage = numberPage - 3;
	      if (startPage + 5 > maxPage) startPage = maxPage - 5;//边界修正
	    }
	    else if ((numberPage - thisShowMinNumber) <= 2) {//跳转到十页中的前三页或之前的页码时尽量显示更前页码
	      startPage = numberPage - 2;
	      if (startPage <= 0) startPage = 1;//边界修正
	    }
	    else {//不用改变页码
	      startPage = thisShowMinNumber;
	    }
	  }
	  return startPage;
	}
	//生成具体的页码
	function CreatPageLi(page, activePage, maxPage) {
	  page = parseInt(page);//起始页码
	  activePage = parseInt(activePage);//要激活的页码
	  maxPage = parseInt(maxPage);//最大页码
	  var initStr = [], j = 1;
	  thisShowMinNumber = page;//记录当前显示的最小页码
	  for (var i = 1; i <= maxPage && i <= 6; i++ , page++) {
	    if (page == activePage) {
	      initStr.push('<li class="commonNum active" id="commonNum' + i + '"><a href="javascript:" value="' + page + '">' + page + '</a></li>');
	      j = i;
	    }
	    else
	      initStr.push('<li class="commonNum" id="commonNum' + i + '"><a href="javascript:" value="' + page + '">' + page + '</a></li>');
	    }
	    $prePage.siblings(".commonNum").remove();//去除原有页码
	    $prePage.after(initStr.join(""));
	    $thisPageNumber = $("#commonNum" + j);//记录当前的页码对象
	}
	//具体页码和功能按键（前后首末页）的点击处理函数
	function pageClick() {
	  var $this = $(this);
	  //只有在页码不在激活状态并且不在禁用状态时才进行处理
	  if (!$this.hasClass("disabled") && !$this.hasClass("active"))
	    GotoPage($this.children().attr("value"));
	}
</script>
</body>
</html>