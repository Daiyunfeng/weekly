<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Mobile Specific Metas
================================================== -->
<link rel="shortcut icon" href="${ctx}/res/images/favicon.ico">
<!-- CSS
================================================== -->
<link rel="stylesheet" href="${ctx}/res/css/style.css">
<!-- Custom Fonts -->
<link href="${ctx}/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${ctx}/res/css/menu.css">
<link href="${ctx}/res/css/bootstrap.min.css" rel="stylesheet">
<script src="${ctx}/res/js/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/res/js/bootstrap.min.js"></script>
<script src="${ctx}/res/js/script.js"></script>
<title>虚拟现实与数字媒体中心</title>
</head>
<body>
	<div class="wrap-body">
		<jsp:include page="/nav"></jsp:include>
		<script type="text/javascript">	
	    $(".index").addClass("active");
		</script>
		<header class="">
			<div class="logo">
				<a href="#">DMI</a>
				<span>虚拟现实与数字媒体中心</span>
			</div>
		</header>
		<div class='container-fluid'>		
			<div class="span2  col-xs-12 col-sm-3 col-md-2">
                <p id="current" hidden>home</p>
                <ul class="nav nav-pills nav-stacked">
                    <li id="home" class="active"><a href="#" onclick="show('home')">主页</a></li>
                    <li id="research"><a href="#" onclick="show('research')">研究方向</a></li>
                    <li id="publications"><a href="#" onclick="show('publications')">发表</a></li>
                    <li id="teaching"><a href="#" onclick="show('teaching')">教学经历</a></li>
                    <li id="photos"><a href="#" onclick="show('photos')">照片</a></li>
                    <li id="people"><a href="#" onclick="show('people')">成员</a></li>
                </ul>
            </div>
            <div class="col-xs-12 col-md-10">
                <div class="home col-xs-12 col-md-12" >
                    <jsp:include page="/include/home"></jsp:include>
                </div>
                <div class="research col-xs-12 col-md-12 hidden" >
                    <jsp:include page="/include/research"></jsp:include>
                </div>
                <div class="publications col-xs-12 col-md-12 hidden" >
                    <jsp:include page="/include/publications"></jsp:include>
                </div>
                <div class="teaching col-xs-12 col-md-12 hidden" >
                    <jsp:include page="/include/teaching"></jsp:include>
                </div>
                <div class="photos col-xs-12 ol-md-12 hidden" >
                    <jsp:include page="/include/photos"></jsp:include>
                </div>
                <div class="people col-xs-12 col-md-12 hidden" >
                    <jsp:include page="/include/people"></jsp:include>
                </div>
            </div>
			  
		</div>
		<script>
		
		/*$(document).ready(function() {
		  $("#owl-slide").owlCarousel({
			autoPlay: 5000,
			items : 1,
			itemsDesktop : [1199,1],
			itemsDesktopSmall : [979,1],
			itemsTablet : [768, 1],
			itemsMobile : [479, 1],
			navigation: true,
			navigationText: ['<i class="fa fa-chevron-left fa-5x"></i>', '<i class="fa fa-chevron-right fa-5x"></i>'],
			pagination: false
		  });
		});*/
		function show(str)
		{
			var current = $("#current").text();
			if(current == str)
			{
				return;	
			}
			$("#"+str).addClass("active");
			$("#"+current).removeClass("active");

			$("."+str).removeClass("hidden");
			$("."+current).addClass("hidden");
			
			$("#current").text(str);
		}
		</script>
	</div>
</body>
</html>