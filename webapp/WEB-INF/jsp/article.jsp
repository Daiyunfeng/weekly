<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="shortcut icon" href="${ctx}/res/images/favicon.ico">
<link href="${ctx}/res/css/bootstrap.css" rel='stylesheet' type='text/css'/>
<link rel="stylesheet" href="${ctx}/res/css/zerogrid.css">
<link rel="stylesheet" href="${ctx}/res/css/style.css">
<link href="${ctx}/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${ctx}/res/css/menu.css">
<link href='https://fonts.googleapis.com/css?family=Open+Sans:700,700italic,800,300,300italic,400italic,400,600,600italic' rel='stylesheet' type='text/css'>
<script src="${ctx}/res/js/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/res/js/script.js"></script>
<script src="${ctx}/res/js/date.js"></script>
<script src="${ctx}/res/js/bootstrap-paginator.min.js"> </script>
<link href="${ctx}/res/owl-carousel/owl.carousel.css" rel="stylesheet">
<link href="${ctx}/res/css/commentStyle.css" rel='stylesheet' type='text/css' />	
<style type="text/css">
.content {
	padding: 40px 120px;
	text-align: left;
}
table{ display: table }
tr{ display:table-row }
thead{ display:table-header-group }
tbody{ display:table-row-group }
tfoot{ display:table-footer-group }
col{ display:table-column }
colgroup{ display:table-column-group }
td, th{ display: table-cell;}
caption{ display: table-caption }
th{font-weight: bolder; text-align: center }
caption{ text-align: center }
table{ border-spacing: 2px;}
thead, tbody,tfoot { vertical-align:middle }
td, th { vertical-align:inherit }
</style>
</head>
<body>
	<jsp:include page="/nav"></jsp:include>
	<script type="text/javascript">
    $(".list").addClass("active");
	</script>
		<p id="ID" hidden>${ID}</p>

		</header>
		<!--////////////////////////////////////Container-->
		<section id="container">
		<div class="wrap-container">
			<!-----------------Content-Box-------------------->
			<article class="single-post zerogrid">
			<div class="row wrap-post">
				<!--Start Box-->
				<div class="entry-header">
					<h2 class="entry-title">
						
					</h2>
					<span class="author">
						<a id="author" href=""></a>
					</span>
					
					<span class="time"></span>
				</div>
				<div class="content">
					<div class="excerpt"></div>
				</div>
			</article>
			
			
			<div class="zerogrid">		
				<p id="token" hidden>${token}</p>
				<div class="comments-are">
					<p id="current" hidden>0</p>
					<div class="response" style="padding: 20px 30px 0;">
					
					</div>
					<nav style="text-align: center">
						<ul id='bp-element' class="pagination" style="text-align:center;"></ul>
					</nav>
					<div id="comment">
						<h3>留下评论吧</h3>
						<label>
							<span>内容(200字以内):</span>
							<textarea name="message" id="message" maxlength="200" onchange="this.value=this.value.substring(0, 200)" onkeydown="this.value=this.value.substring(0, 200)" onkeyup="this.value=this.value.substring(0, 200)"></textarea>
						</label>
						<center>
							<button style="margin: 0 auto; display: block;" id="submit" onclick="submit()">提交</button>
						</center>
					</div>
				</div>
			</div>
		</div>
		</section>
	</div>
<script type="text/javascript">
	
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
			    //getComment(data.res.commentNumber);
				$("title").html(data.res.title);
				$(".time").html("更新日期:"+new Date(data.res.updateTime).format("yyyy-MM-dd hh:mm:ss"));
				$(".entry-title").html(data.res.title);
				$("#author").html(data.res.author);
				$("#author").attr("href","${ctx}/user/"+data.res.userID); 
				$(".excerpt").html(data.res.content);
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
	/*function getComment(number)
	{
		var total = parseInt(number/SHOW_NUMBER);
		var i = number%SHOW_NUMBER==0?0:1;
		total+=i;
		if(total==0)
		{
			return;
		}
		var element = $('#bp-element');
		options = {
		    bootstrapMajorVersion:3, //对应的bootstrap版本
		    alignment:"center",
		    currentPage: 1 ,//当前页数
		    numberOfPages: 5, //每页页数
		    totalPages:total, //总页数
		    shouldShowPage:true,//是否显示该按钮
		    itemTexts: function (type, page, current) {//设置显示的样式，默认是箭头
		        switch (type) {
		            case "first":
		                return "首页";
		            case "prev":
		                return "上一页";
		            case "next":
		                return "下一页";
		            case "last":
		                return "末页";
		            case "page":
		                return page;
		        }
		    },
		    //点击事件
		    onPageClicked: function (event, originalEvent, type, page) {
		    	var current = $("#current").text();
		    	if(page==current)
	    		{
		    		return;
	    		}
		    	$("#current").text(page);
		    	showComment(page);
		    	
		    }
		};
		showComment(1);
		element.bootstrapPaginator(options);
	}*/
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
	function submit()
	{
		var ID = $("#ID").text();
		var message = $("#message").val();
		var token = $("#token").text();
		$.ajax(
		{
			type : 'post',
			url : '${ctx}/addNewCommentAjax',
			dataType : 'json',
			data : 
			{
				articleID : ID,
				content : message,
				token : token,
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